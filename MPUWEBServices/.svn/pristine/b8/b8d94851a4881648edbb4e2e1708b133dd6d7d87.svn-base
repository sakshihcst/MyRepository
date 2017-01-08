package com.searshc.mpuwebservice.processor.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderItem;
import com.searshc.mpuwebservice.bean.HGRequestDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.processor.AssociateActivityProcessor;
import com.searshc.mpuwebservice.processor.NPOSUpdateProcessor;
import com.searshc.mpuwebservice.processor.OBUUpdateProcessor;
import com.searshc.mpuwebservice.processor.ResponseProcessor;

@Service("responseProcessor")
public class ResponseProcessorImpl implements ResponseProcessor {
	private static transient DJLogger logger = DJLoggerFactory.getLogger(MPUWebServiceProcessorImpl.class);
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	
	@Autowired
	private NPOSUpdateProcessor nPOSUpdateProcessorImpl;
	
	@Autowired
	private OBUUpdateProcessor oBUUpdateProcessor;
	
	@Autowired
	AssociateActivityProcessor associateActivityProcessor;

	public void sendFinalResponse(String storeNum, String rqtId,
			String requestNumber, String actionFlag) throws DJException {
		// TODO Auto-generated method stub
		logger.error("ResponseProcessorImpl-sendFinalResponse == "+storeNum + ":" + rqtId +":"+requestNumber +":" + actionFlag ,"");
		boolean complete = false;
		// start change for Direct to MPU
		
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		OrderDTO orderDTO = mpuWebServiceDAOImpl.getAllOrderDetails(storeNum, rqtId);
		if(MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(orderDTO.getOrderSource())){
			try {
				String originalJSON = orderDTO.getOriginalJson(); 
				List<ItemDTO> itemDTOs = mpuWebServiceDAOImpl.getOrderItemList(storeNum,rqtId);

				if(MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(orderDTO.getRequestType())){
					HGRequestDTO hgOrder = (HGRequestDTO)mapper.readValue(originalJSON, new TypeReference<HGRequestDTO>(){});
					
					hgOrder  =  getFinalHGResponse(hgOrder, itemDTOs);
				//	logger.info("****final HG OrderResponse** = ", hgOrder.toString());
				//	logger.info("start OBU HG update",""+Calendar.getInstance().getTimeInMillis());
					
					oBUUpdateProcessor.updateHGOBU(hgOrder);
					
				}
				
			else{
				
//				String originalJSON = orderDTO.getOriginalJson(); 
				Order order = (Order)mapper.readValue(originalJSON, new TypeReference<Order>(){});
//				List<ItemDTO> itemDTOs = mpuWebServiceDAOImpl.getOrderItemList(storeNum,rqtId);
				Order finalOrderResponse = getFinalOrderResponse(order,itemDTOs);
				//logger.info("****finalOrderResponse** = ", finalOrderResponse.toString());
				//logger.info("start OBU update",""+Calendar.getInstance().getTimeInMillis());
				
				oBUUpdateProcessor.updateOBU(finalOrderResponse);
				
			}
			} catch (Exception e) {
				logger.error("Exception in sendFinalResponse:", e);
				throw new DJException(e.getMessage());
			}
			//logger.info("end OBU update",""+Calendar.getInstance().getTimeInMillis());
		}else {
			// end change for Direct to MPU
			OrderAdaptorRequest request=mpuWebServiceDAOImpl.getOriginalJSON(rqtId, storeNum,null);
			Map<String,Object> itemStatusMap=mpuWebServiceDAOImpl.checkItemStatus(storeNum, rqtId);
			Map<String,Object> finalStatusMap=new HashMap<String, Object>();
			/**
			 * For CrossFormat Orders
			 */
			if(null!=itemStatusMap){
				for(String key:itemStatusMap.keySet()){
					String []strArr = key.split(",");
					if(strArr.length>2 && !StringUtils.isEmpty(strArr[2]) && strArr[2].startsWith("LM")){
						//If this is a cross format order
						String []itemIdSeqArray = strArr[strArr.length-1].split("-");
						String cfkey = strArr[0]+","+strArr[2]+"-"+itemIdSeqArray[1];
						
						finalStatusMap.put(cfkey, itemStatusMap.get(key));
					}else{
						finalStatusMap.put(key, itemStatusMap.get(key));
					}
				}
			}
			
			if(request.getRequestOrder()!=null){
			//	logger.info("Inside Request Order","");
				Order originalOrder=request.getRequestOrder();
				List<OrderItem> nposItemsList=request.getRequestOrder().getItems();
				List<OrderItem> nposItemsListFinal=new ArrayList<OrderItem>();
				
				for(OrderItem item:nposItemsList){
					//logger.info("Inside Request nposItemsList","");
					String status= (String) finalStatusMap.get(item.getItemIdentifiers()+"-"+item.getSequenceNo());
				//	logger.info("Item Status ",status);
					if("BINNED".equals(status)){
						item.setItemStockState('B');
						logger.info("Setting Item Bin State : B","");
					}
					else if("CLOSED".equals(status)){
						item.setItemStockState('O');
						logger.info("Setting Item Bin State : O","");
					}
					nposItemsListFinal.add(item);
					
				}
				originalOrder.setItems(nposItemsListFinal);
				request.setRequestOrder(originalOrder);
			}
				//logger.info("start npos update","RQT-ID "+rqtId+":"+Calendar.getInstance().getTimeInMillis());
				nPOSUpdateProcessorImpl.updateNPOS(request, "complete");
			//logger.info("end npos update",""+"RQT-ID "+rqtId+":"+Calendar.getInstance().getTimeInMillis());
		}
		ArrayList<String> rqtList=new ArrayList<String>();
		rqtList.add(rqtId);
		if(MpuWebConstants.CANCEL.equalsIgnoreCase(actionFlag)){
			mpuWebServiceDAOImpl.updateOrder(storeNum,rqtList,MpuWebConstants.OPEN, MpuWebConstants.CANCELLED,null,false);
			associateActivityProcessor.createAssociateActivity( MpuWebConstants.CANCELLED,rqtId,storeNum);
		}else if (MpuWebConstants.VOID.equalsIgnoreCase(actionFlag)){
			mpuWebServiceDAOImpl.updateOrder(storeNum,rqtList,MpuWebConstants.OPEN, MpuWebConstants.VOIDED,null,false);
			associateActivityProcessor.createAssociateActivity( MpuWebConstants.VOIDED,rqtId,storeNum);
		}else if (MpuWebConstants.HOLDGO.equalsIgnoreCase(actionFlag)){
			mpuWebServiceDAOImpl.updateOrder(storeNum,rqtList,MpuWebConstants.WIP, MpuWebConstants.COMPLETED,null,false);
		}else{
			complete = mpuWebServiceDAOImpl.checkRequestComplete(orderDTO.getStoreNumber(), orderDTO.getRqtId(), orderDTO.getRequestType());
			if(complete){
				mpuWebServiceDAOImpl.updateOrder(storeNum,rqtList,MpuWebConstants.OPEN, MpuWebConstants.COMPLETED,null,false);
				associateActivityProcessor.createAssociateActivity( MpuWebConstants.COMPLETED,rqtId,storeNum);
			}
		}
		
		
	}

	@SuppressWarnings("unchecked")
	public void removeItemsFromCache(List<String> rqdList, String storeNum,
			String queueType) {
		// TODO Auto-generated method stub
		logger.info("Entering ResponseProcessorImpl.removeItemsFromCache	rqdList:",rqdList +": storeNum: "+storeNum +": queueType: "+queueType );
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		String queueKey = storeNum+"-"+queueType;
		if(requestQueueCache.get(queueKey)!=null)
		{
			Map<String,ItemDTO> queueMap = (Map<String, ItemDTO>) requestQueueCache.get(queueKey).get();
			for(String key:rqdList){
				queueMap.remove(key);
			}
			requestQueueCache.put(queueKey, queueMap);
		}
		logger.info("Exiting" ,"ResponseProcessorImpl.removeItemsFromCache");
		
	}
	
	private static Object createStringToObject(String str,TypeReference<?> typeReference) {//throws DJException{
		Object response = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			if(null != str){
			//	logger.debug("createStringToObject==Original Json Length=", str.length());
				response = mapper.readValue(str,typeReference);
			}else{
				logger.debug("createStringToObject : ","String is empty");
			}
		} catch (Exception ex) {
			logger.error("createStringToObject :",ex);
		}
		return response;
	}
	
	private Order getFinalOrderResponse(Order order,List<ItemDTO>itemDTOs){
		Order finalResponseOrder = order;
		for(OrderItem obuItem:finalResponseOrder.getItems()){
			String stringArr[] = obuItem.getItemIdentifiers().split(",");
			String obuIdentifier=null;
			if(null!=stringArr && stringArr.length>3){
				obuIdentifier = stringArr[0]+","+stringArr[1]+","+stringArr[2];
			}
			if(null!=obuIdentifier){
				obuIdentifier = obuIdentifier+"-"+obuItem.getSequenceNo();
			}
			
			logger.error("obuIdentifiers =="+ obuIdentifier,"");
			for(ItemDTO mpuItem:itemDTOs){
				
				//for handling the duplicate bin numbers
				if(StringUtils.hasText(mpuItem.getToLocation()) && mpuItem.getToLocation().contains(MpuWebConstants.BIN)){
					String [] binArray = ((mpuItem.getToLocation()).trim()).split(" ");
					if(binArray.length>1){
						String finalBinloc = binArray[0]+" "+binArray[binArray.length-1];
						mpuItem.setToLocation(finalBinloc);
					}
				}
				
				
				
				String mpuIdentifier = mpuItem.getDivNum()+mpuItem.getItem()+mpuItem.getSku();
				/**
				 * For Crossformat orders
				 */
				if(null!=mpuItem.getUpc() && mpuItem.getUpc().startsWith("LM")){
					mpuIdentifier = mpuIdentifier +","+mpuItem.getUpc()+"-"+mpuItem.getItemSeq();
				}else{
					/*mpuIdentifier = mpuIdentifier +","+mpuItem.getKsn()+","+mpuItem.getUpc()
						+","+mpuItem.getItemId()+"-"+mpuItem.getItemSeq();*/
					mpuIdentifier = mpuIdentifier +","+mpuItem.getKsn()+","+mpuItem.getUpc()
							+"-"+mpuItem.getItemSeq();
				}
				
				logger.error("mpuIdentifier =="+ mpuIdentifier,"");
				if(mpuIdentifier.equalsIgnoreCase(obuIdentifier)){
				//	logger.info("Identifier ==", "Matched");
					String status = mpuItem.getItemStatus();
					
					if(MpuWebConstants.BINNED.equals(status)){
						obuItem.setItemStatus(MpuWebConstants.AVAILABLE);
						/**
						 * For defect-JIRA-STORESYS-24299 & STORESYS-24168
						 */
						String bin=null;
						if(mpuItem.getToLocation()!=null){
							bin=mpuItem.getToLocation().replaceAll("BIN", "");
							if(null!=bin){
								bin = bin.trim();
							}
						}
						logger.info("BinNumber to OBU :",bin);
						//obuItem.setItemBinNumber(mpuItem.getToLocation());
						obuItem.setItemBinNumber(bin);
					} else if(MpuWebConstants.CLOSED.equals(status)){
						obuItem.setItemStatus((MpuWebConstants.OOS));
					} else if(MpuWebConstants.EXPIRED.equals(status) || MpuWebConstants.BINPENDING.equalsIgnoreCase(status)){
						//added bin_pending items also to NORESPONSE JIRA-STORESYS-25636
						obuItem.setItemStatus(MpuWebConstants.NORESPONSE);
					}else if(MpuWebConstants.CANCELLED.equalsIgnoreCase(status)){
						//For Cancelled items
						obuItem.setItemStatus(MpuWebConstants.CANCELLED);
					}
				}
			}
		}
		finalResponseOrder.setStatus(MpuWebConstants.PROCESSED);
		return finalResponseOrder;
	}
	
	/**To send the final response to OBU team for Reserve IT
	 * 
	 * @param order
	 * @param itemDTOs
	 * @return
	 */
	private HGRequestDTO getFinalHGResponse(HGRequestDTO order,List<ItemDTO>itemDTOs){
		logger.info("entering method===", "getFinalHGResponse");
		HGRequestDTO finalResponseOrder = order;
		HashMap<String,String> itemStatus = new HashMap<String, String>();
		ArrayList<HashMap<String,String>> modifiedItemsList=new ArrayList<HashMap<String,String>>();
		/****  map to contain the item status of the Db items ****/
		for(ItemDTO itemDTO : itemDTOs){
			/*String key = (StringUtils.isEmpty(itemDTO.getDivNum()) ? "" : itemDTO.getDivNum()) +
			(StringUtils.isEmpty(itemDTO.getItem()) ? "" : itemDTO.getItem()) +
			(StringUtils.isEmpty(itemDTO.getSku()) ? "" : itemDTO.getSku()) +","+
			itemDTO.getItemSeq();*/
			String key = (StringUtils.isEmpty(itemDTO.getDivNum()) ? "" : itemDTO.getDivNum()) +
					(StringUtils.isEmpty(itemDTO.getItem()) ? "" : itemDTO.getItem()) +
					(StringUtils.isEmpty(itemDTO.getSku()) ? "" : itemDTO.getSku()) ;
			key = key.replaceAll("\\s","");
			//logger.info("the key value is ===","key=="+key+"value==="+itemDTO.getItemStatus() );
			itemStatus.put(key, itemDTO.getItemStatus());
			
		}
		
		/****  proces  the items in the HG json on the basis of DB items ****/
		for(HashMap<String , String> obuItems : order.getItemsList()){
			//String keyFinder = obuItems.get("divNum")+obuItems.get("item")+obuItems.get("sku")+","+obuItems.get("sequenceNumber");
			String keyFinder = obuItems.get("divNum")+obuItems.get("item")+obuItems.get("sku");
			keyFinder = keyFinder.replaceAll("\\s","");
			//logger.info("the key value is ===","key=="+keyFinder);
			String status = itemStatus.get(keyFinder);
			if(MpuWebConstants.AVAILABLE.equalsIgnoreCase(status)){
				obuItems.put("itemStatus", "A");	
			}else if(MpuWebConstants.NOTAVAILABLE.equalsIgnoreCase(status)){
				obuItems.put("itemStatus", "NA");
			}else if(MpuWebConstants.EXPIRED.equalsIgnoreCase(status)){
				obuItems.put("itemStatus", "NR");
			}else if(MpuWebConstants.CANCELLED.equalsIgnoreCase(status)){
				obuItems.put("itemStatus", "CNR");
			}
			
			//modifiedItemsList.add(itemStatus);
			modifiedItemsList.add(obuItems);
		}
		
		order.setItemsList(modifiedItemsList);
		order.setStatus(MpuWebConstants.COMPLETE);
		return finalResponseOrder;
	}


	public void sendNoResponseToOBUForHG(List<Map<String, ItemDTO>> expiredHGItems)  throws DJException{
		try{
			logger.info("entering the sendNoResponseForHG","");
			List<ItemDTO> finalExpiredItems = new ArrayList<ItemDTO>();
			HashSet<String> expiredOrdrIds = new HashSet<String>();
			if(!CollectionUtils.isEmpty(expiredHGItems)){
				logger.info("the expired HG list size is=== ",expiredHGItems.size());
				for(Map<String, ItemDTO> itemMap : expiredHGItems){
						//logger.info("adding the item rqd == ", itemDTO.getRqdId());
						finalExpiredItems.add((ItemDTO)itemMap);
					
				}
			}
			

			for(ItemDTO itemDTO :  finalExpiredItems){
				if(mpuWebServiceDAOImpl.checkRequestComplete(itemDTO.getStoreNumber(), itemDTO.getRqtId(), itemDTO.getRequestType())){
					sendFinalResponse(itemDTO.getStoreNumber(), itemDTO.getRqtId(), itemDTO.getRequestNumber(), MpuWebConstants.HOLDGO);	
				}
				
			}
		}catch(Exception exception){
			logger.error("in the catch block of sendNoResponseToOBUForHG",exception.getMessage() );
		}
		logger.info("exiting the sendNoResponseForHG","");
	}
	
}
