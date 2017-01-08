package com.searshc.mpuwebservice.processor.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.annotations.Synchronize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.processor.ExpiredOrderProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.processor.ResponseProcessor;

@Service("expiredOrderProcessorImpl")
public class ExpiredOrderProcessorImpl implements ExpiredOrderProcessor{

	private static transient DJLogger logger = DJLoggerFactory.getLogger(ExpiredOrderProcessorImpl.class);
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	
/*	@Autowired
	@Qualifier("webServicesProcessorImpl")
	private MPUWebServiceProcessor webServicesProcessor;*/
	@Autowired
	private EhCacheCacheManager cacheManager; 
	
	@Autowired
	private ResponseProcessor responseProcessor;
	
	private final ReentrantLock lock = new ReentrantLock();
	
	
	private static  Map<String, Long> threadFilter = new HashMap<String, Long>();
//	@Async("expiryExecutor")
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void expireOrder(List<String> rqtList,List<String> rqdList,HashMap<String,String> mapping,String strNum,HashMap<String,String> requestTypeMap,String queueType,
			List<Map<String, ItemDTO>> expiredHGItems ) 	throws DJException{
		try{
		expireRequests(rqtList, rqdList, mapping, strNum, requestTypeMap, queueType,expiredHGItems);}
		catch(Exception e){
			logger.error("Error in expire order", e);
		}

	}
	
	private synchronized void expireRequests(List<String> rqtList,List<String> rqdList,HashMap<String,String> mapping,String strNum,HashMap<String,String> requestTypeMap,
			String queueType,List<Map<String, ItemDTO>> expiredHGItems) throws DJException{
		logger.info("Entering ExpiredOrderProcessorImpl.expireOrder","");
		
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("expiredListCache");
		List<String> expiredOrderList=null;
		String cacheKey = "ExpiredOrderList-"+strNum;
		
		/**
		 * Need to expire the items before sending the response 
		 * so that current status is sent to OBU
		 */
		HashMap<String,Object> nextSteps=(HashMap<String,Object>)mpuWebServiceDAOImpl.getNextAction("", "", "EXPIRE", null, strNum);
		mpuWebServiceDAOImpl.cancelExpireItems(rqdList, strNum,(String)nextSteps.get("status"));

		
		
		if(null!=requestQueueCache && null!= requestQueueCache.get(cacheKey)){
			expiredOrderList = (ArrayList<String>) requestQueueCache.get(cacheKey).get();
		}
		
		
		
		if(null==expiredOrderList){
			expiredOrderList = new ArrayList<String>();
		}
		
		
		
		
		

		/*
		 * For direct request we need to send the response to OBU queue with status as PROCESSED
		 * and item level status as NORESPONSE for item which is  expired
		 * by Nasir
		 */
		String requestStatus = "EXPIRED";
		List<String> completeRqtList = new ArrayList<String>();
		List<String> expiredRqtList = new ArrayList<String>();
		logger.info("the epire item size===", rqtList.size());
		for(String rqtId : rqtList){
			/*
			 * the status of the order would be expired now in database.Since that is already executed
			 * 
			 */
			if(!expiredOrderList.contains(rqtId)){
				logger.info("the rqt for expire is ===", rqtId);
				String status = MpuWebConstants.EXPIRED;
				List<String> statusList = new ArrayList<String>();
				statusList.add(status);
				statusList.add(status);
				statusList.add("OPEN");
				statusList.add("WIP");
				OrderDTO order = mpuWebServiceDAOImpl.getOrderDetails(strNum, rqtId, statusList);
				
				/**
				 * JIRA-STORESYS-25599
				 * 
				 */
				List<String> itemStatusList = new ArrayList<String>();
				itemStatusList.add("ALL");
				itemStatusList.add(MpuWebConstants.EXPIRED);
				List<ItemDTO> itemList = mpuWebServiceDAOImpl.getItemList(strNum, rqtId, null, itemStatusList, false);
				
				boolean completeFlag = false;
				if(null!=itemList){
					for(ItemDTO item:itemList){
						if(MpuWebConstants.BINNED.equalsIgnoreCase(item.getItemStatus()) || MpuWebConstants.CLOSED.equalsIgnoreCase(item.getItemStatus())){
							completeRqtList.add(rqtId);
							requestStatus = "COMPLETED";
							completeFlag = true;
							//mpuWebServiceDAOImpl.updateOrder(strNum, newRqtList, MpuWebConstants.EXPIRED, requestStatus,null,false);
							break;
						}
					}
				}
				if(!completeFlag){
					expiredRqtList.add(rqtId);
				}
				
				if(null!=order && MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(order.getOrderSource())){
					responseProcessor.sendFinalResponse(strNum, rqtId, null, requestStatus);
				}
				
				if(null!=requestQueueCache && null!=requestQueueCache.get(cacheKey)){
					expiredOrderList = (ArrayList<String>) requestQueueCache.get(cacheKey).get();
					expiredOrderList.add(rqtId);
				}else{
					expiredOrderList.add(rqtId);
				}
				
				requestQueueCache.put(cacheKey, expiredOrderList);
			}
		}
		if(!completeRqtList.isEmpty()){
			mpuWebServiceDAOImpl.updateOrderList(strNum, completeRqtList,"COMPLETED",null,false);
		}
		if(!expiredRqtList.isEmpty()){
			mpuWebServiceDAOImpl.updateOrderList(strNum, expiredRqtList,"EXPIRED",null,false);
		}
		
		if(!completeRqtList.isEmpty() || !expiredRqtList.isEmpty()){
			mpuWebServiceDAOImpl.insertBulkActivities(rqtList, rqdList, mapping, (String)nextSteps.get("seq"), (String)nextSteps.get("activity"), strNum, requestTypeMap);
		}
		
		

		//Remove the items from cache itemList 
		responseProcessor.removeItemsFromCache(rqdList,strNum,queueType);
		
		/** to send the NA response notification to OBU**/
		if(!CollectionUtils.isEmpty(expiredHGItems)){
			
			String hgCacheKey = "ExpiredHGOrderList-"+strNum;
			List<String> hgExpireList = new ArrayList<String>();
			if(null!=requestQueueCache && null!= requestQueueCache.get(hgCacheKey)){
				hgExpireList = (ArrayList<String>) requestQueueCache.get(hgCacheKey).get();
			}
			
			else{
				hgExpireList= new ArrayList<String>();
				
			}
			List<Map<String,ItemDTO>> finalHGExpiredList = new ArrayList<Map<String,ItemDTO>>();
			for(Map<String,ItemDTO> item: expiredHGItems){
				for(String rqt:item.keySet()){
					if(null==hgExpireList || (null!=hgExpireList && !hgExpireList.contains(rqt))){
						finalHGExpiredList.add(item);
						hgExpireList.add(rqt);
					}
				}
			}
			
			if(null!=requestQueueCache){
				requestQueueCache.put(hgCacheKey, hgExpireList);
			}
			if(!CollectionUtils.isEmpty(finalHGExpiredList)){
				responseProcessor.sendNoResponseToOBUForHG(finalHGExpiredList);
			}
		}
		
	}
	
	
	
	
	public synchronized  boolean filterItemthread(String store,String queueType){
		
		//EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("expiredListCache");
		String threadFilterKey = store+"_"+queueType+"_"+MpuWebConstants.FILTER;
		Long currentdate = Calendar.getInstance().getTimeInMillis();
		Long earlierUpdatedDate =  threadFilter.get(threadFilterKey);
		if(null!=earlierUpdatedDate && earlierUpdatedDate.longValue()>0){
			//Long earlierUpdatedDate =   (Long)requestQueueCache.get(threadFilterKey).get();
			if(((currentdate-earlierUpdatedDate.longValue())/1000)>20){
				logger.error("the queue is refreshing at"+threadFilterKey,"" );
				threadFilter.put(threadFilterKey, currentdate);
				return true;
			}
		}else{
			threadFilter.put(threadFilterKey, currentdate);
			return true;
		}
		return false;
	}
}
