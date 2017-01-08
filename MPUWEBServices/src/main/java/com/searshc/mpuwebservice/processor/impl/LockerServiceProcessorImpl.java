package com.searshc.mpuwebservice.processor.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LOCKER_ENABLED;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.LockerDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PaymentDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.LockerServiceDAO;
import com.searshc.mpuwebservice.dao.MCPDBDAO;
import com.searshc.mpuwebservice.dao.PickUpServiceDAO;
import com.searshc.mpuwebservice.processor.LockerServiceProcessor;
import com.searshc.mpuwebservice.processor.PickUpServiceProcessor;
import com.searshc.mpuwebservice.util.PropertyUtils;

/**
 * 
 * @author ssingh6
 *
 */
@Service("lockerServiceProcessorImpl")
public class LockerServiceProcessorImpl implements LockerServiceProcessor{
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(LockerServiceProcessorImpl.class);
	
	@Autowired
	@Qualifier("lockerServiceDAOImpl")
	private LockerServiceDAO lockerServiceDAOImpl;
	
	@Autowired
	private PickUpServiceProcessor pickUpServiceProcessor;
	
	@Autowired
	private PickUpServiceDAO pickUpServiceDAO;
	
	@Autowired
	private MCPDBDAO mCPDBDAO;
	
	@Autowired
	private EhCacheCacheManager cacheManager;
	

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.LockerServiceProcessor#addLocker(com.searshc.mpuwebservice.bean.LockerDTO)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean addLocker(LockerDTO lockerDTO) throws DJException {
	 	logger.info("Entering LockerServiceProcessorImpl.addLocker	lockerDTO:","");
		boolean addLockerFlag = false;
		addLockerFlag = lockerServiceDAOImpl.insertLockerDetail(lockerDTO);
		logger.info("Exiting" ,"LockerServiceProcessorImpl.addLocker");
		return addLockerFlag;
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.LockerServiceProcessor#updateLocker(com.searshc.mpuwebservice.bean.LockerDTO)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int updateLocker(LockerDTO lockerDTO) throws DJException {
		logger.info("Entering LockerServiceProcessorImpl.updateLocker	lockerDTO:","");
		
		/*if(MpuWebConstants.PICKED_UP.equalsIgnoreCase(lockerDTO.getStatus())){
			logger.info("PICKED_UP"," closing locker order where workID ");
			
			//List<OrderDTO> order = pickUpServiceDAO.getTransDetail(lockerDTO.getReferenceId(), lockerDTO.getStoreNo());
			List<OrderDTO> order = pickUpServiceDAO.getTransDetail(lockerDTO.getSalescheckNo(), lockerDTO.getStoreNo());
			
			if(order != null)
			{
				pickUpServiceProcessor.completeRequestForLocker(lockerDTO.getStoreNo(),order.get(0).getTrans_id(),order.get(0).getRqtId());
				pickUpServiceProcessor.updateStatusToNPOS(lockerDTO.getStoreNo(),order.get(0).getRqtId(), MpuWebConstants.LOCKER_PICKUP, "000000", "updatePickCompletedToNPOS", order.get(0).getTrans_id(),"",order.get(0).getStoreFormat(),"","",order.get(0).getKioskName());
				
				*//**
				 * Declare that the Cache is no longer clean
				 *//*
				EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
				String formattedStoreNo = org.apache.commons.lang3.StringUtils.leftPad(order.get(0).getStoreNumber(), 5, '0');
				String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+order.get(0).getKioskName();
				String ohmCacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+order.get(0).getKioskName();
				
				logger.info("***cacheDirtyKey***", cacheDirtyKey);
				if(null!=requestQueueCache){
					requestQueueCache.put(cacheDirtyKey, "true");
					requestQueueCache.put(ohmCacheDirtyKey, "true");
				}
			
			}	
			return 1;		
		} else {*/
			return lockerServiceDAOImpl.updateLockerPin(lockerDTO);	
		//}
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.MPUWebServiceProcessor#getPinNumberFromSalescheck(java.lang.String, java.lang.String, java.lang.String)
	 */	
	public LockerDTO getPinNumberFromSalescheck(String salescheckNum,String storeNumber, String transactionDate) throws DJException {
		logger.info("Entering LockerServiceProcessorImpl.getPinNumberFromSalescheck	salescheckNum:",salescheckNum +": storeNumber: "+storeNumber +": transactionDate: "+transactionDate);
		return lockerServiceDAOImpl.getPinNumberFromSalescheck(salescheckNum,storeNumber,transactionDate);
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.LockerServiceProcessor#isOrderEligibleForLocker(com.searshc.mpuwebservice.bean.RequestDTO)
	 */	
	public boolean isOrderEligibleForLocker(RequestDTO requestDTO) throws DJException {
		logger.info("Start isOrderEligibleForLocker() ","");
		//System.out.println("requestDTO::"+requestDTO);
		boolean lockerFlag = false;
		String lockerValue = "N";
		String isLockerAllowedForStore = "Y";
		boolean paymentByCard=true;
		BigDecimal amountPaid=BigDecimal.ZERO;
		OrderDTO orderDTO = requestDTO.getOrder();
		List<PaymentDTO> paymentDTOList=requestDTO.getPaymentList();
		String cardType=PropertyUtils.getProperty("com.mpu.web.cardType");
	//	System.out.println("cardType::"+cardType);
		for(PaymentDTO paymentDTO:paymentDTOList){
			//amountPaid=amountPaid+Float.parseFloat(paymentDTO.getAmount());
			amountPaid=amountPaid.add(paymentDTO.getAmount());
			if(!cardType.contains(paymentDTO.getType())){
				paymentByCard=false;
			}
		}
		
		if (orderDTO != null) {
			//logger.error("Payment done by card is:=== "+paymentByCard+" for Salescheck:",orderDTO.getSalescheck());
		//	logger.info("paymentByCard::"+paymentByCard+"::::Salescheck::"+orderDTO.getSalescheck() +"::::amountPaid::"+amountPaid,"");
			//logger.error("Total amount paid in card is:"+amountPaid+"for Salescheck:",orderDTO.getSalescheck());
			
			/* Commenting as locker egibility for store is now getting retrieve from DB Table
			*RestTemplate restTemplate = new RestTemplate();		
			String url = PropertyUtils.getProperty("isLocker.targetURL")+orderDTO.getStoreNumber();
			ResponseEntity<String> responseEntity = restTemplate.exchange(url,HttpMethod.GET, null, String.class);
		
			logger.error("Locker Allowed for store locker URL:",url);
			
			String lockerValue = responseEntity.getBody();*/
		
			List<Map<String, Object>> mapList= mCPDBDAO.getIsLockerEligibleFlag(orderDTO.getStoreNumber(), orderDTO.getKioskName());
			for (Map<String, Object> map : mapList) {
				lockerValue=map.get(LOCKER_ENABLED.name()) + "";
				}
			//String lockerValue = "true";
		//	logger.error("lockerValue:",lockerValue);
			if (lockerValue != null && lockerValue.contains(isLockerAllowedForStore)) {
				String webOrderStartWith = PropertyUtils.getProperty("com.mpu.web.orderStartswith");
				//String webOrderStartWith ="09300" ;
				if(orderDTO.getSalescheck().startsWith(webOrderStartWith) && paymentByCard){
					List<ItemDTO> itemList = requestDTO.getItemList();
					if (itemList != null && itemList.size() ==1) {
						String divList = PropertyUtils.getProperty("com.mpu.web.locker.divList");
						//String divList ="003,044,057,058" ;
						String workItemTransactionTypeCodeList = PropertyUtils.getProperty("com.web.locker.workItemTransactionTypeCode");
						//String workItemTransactionTypeCodeList ="J,O,F";
						ItemDTO itemDTO = itemList.get(0);
						int quantity = Integer.parseInt(itemDTO.getQty());
						BigDecimal lockerPrice = new BigDecimal(PropertyUtils.getProperty("com.mpu.web.locker.max.amount"));
						//BigDecimal lockerPrice = new BigDecimal("75");
						//float lockerPrice = Float.parseFloat(PropertyUtils.getProperty("com.mpu.web.locker.max.amount"));
						logger.info("amountPaid::::"+amountPaid, "::::lockerPrice::::"+lockerPrice);
						if (!(itemDTO.getDivNum().contains(divList)) && !((workItemTransactionTypeCodeList.contains(itemDTO.getItemTransactionType())))
								&& quantity == 1 &&  amountPaid.compareTo(lockerPrice)<0){ //  amountPaid <= lockerPrice) {
							lockerFlag = true;
						}
					}
				}
			}
			logger.info("Locker eligibility is: "+lockerFlag+"for Salescheck:", orderDTO.getSalescheck());
		}
	//	logger.debug("End isOrderEligibleForLocker() ", requestDTO);
		logger.info("exiting isOrderEligibleForLocker","");
		return lockerFlag;
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.LockerServiceProcessor#updateLockerOrderStatus(com.searshc.mpuwebservice.bean.LockerDTO)
	 */	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int updateLockerOrderStatus(LockerDTO lockerDTO) throws DJException {
		
		if(MpuWebConstants.PICKED_UP.equalsIgnoreCase(lockerDTO.getStatus())){
			logger.info("PICKED_UP"," closing locker order where workID ");
			
			//List<OrderDTO> order = pickUpServiceDAO.getTransDetail(lockerDTO.getReferenceId(), lockerDTO.getStoreNo());
			List<OrderDTO> order = pickUpServiceDAO.getTransDetail(lockerDTO.getSalescheckNo(), lockerDTO.getStoreNo());
			
			if(order != null)
			{
				completeRequestForLocker(lockerDTO.getStoreNo(),order.get(0).getTrans_id(),order.get(0).getRqtId());
				pickUpServiceProcessor.updateStatusToNPOS(lockerDTO.getStoreNo(),order.get(0).getRqtId(), MpuWebConstants.LOCKER_PICKUP, "000000", "updatePickCompletedToNPOS", order.get(0).getTrans_id(),"",order.get(0).getStoreFormat(),"","",order.get(0).getKioskName());
				
				//Declare that the Cache is no longer clean
				
				EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
				String formattedStoreNo = org.apache.commons.lang3.StringUtils.leftPad(order.get(0).getStoreNumber(), 5, '0');
				String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+order.get(0).getKioskName();
				String ohmCacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+order.get(0).getKioskName();
				
				//logger.info("***cacheDirtyKey***", cacheDirtyKey);
				if(null!=requestQueueCache){
					requestQueueCache.put(cacheDirtyKey, "true");
					requestQueueCache.put(ohmCacheDirtyKey, "true");
				}
			
			}			
		}
		return lockerServiceDAOImpl.updateLockerOrderStatus(lockerDTO);
	}
	
	

	public Boolean isOrderKeptInLocker(Integer reqID,String storeNumber) throws DJException{
		logger.info("Starting of isOrderKeptInLocker() with Order Details ===== ",reqID);
		boolean isOrderKept = false;
		
			if(reqID!=null){
				 isOrderKept = lockerServiceDAOImpl.isOrderKeptInLocker(reqID, storeNumber);
				
			}
		
		return isOrderKept;

	}

	public List<LockerDTO> getLockerReport(String reportFromDate,
			String reportToDate, String storeNumber) throws DJException {
		logger.info("Start getLockerReport() for store:"+storeNumber+":reportFromDate:"+reportFromDate+":reportToDate:",reportToDate);
		List<LockerDTO> lockerDTOs=lockerServiceDAOImpl.getLockerReport(reportFromDate,reportToDate,storeNumber);
		logger.info("End getLockerReport() for store:"+storeNumber+":reportFromDate:"+reportFromDate+":reportToDate:",reportToDate);
		return lockerDTOs;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void completeRequestForLocker(String storeNumber, int transId, String rqtId) throws DJException {
		logger.info("completeRequestForLocker", "Entering completeRequestForLocker transId : " + transId);
		
		int noOfRows=0;
		
		noOfRows = pickUpServiceDAO.completeLockerQueueOrder(storeNumber, rqtId);
		
		if(noOfRows != 0) {
			noOfRows = pickUpServiceDAO.completeLockerQueueItem(storeNumber, rqtId);
		}
		
		if(noOfRows != 0) {
			noOfRows = pickUpServiceDAO.completeLockerMPUTrans(storeNumber, String.valueOf(transId));
		}
		
		if(noOfRows != 0) {
			noOfRows = pickUpServiceDAO.completeLockerMPUDetail(storeNumber, String.valueOf(transId));
		}
		
		
		logger.info("completeRequestForLocker", "Exiting completeRequestForLocker transId : " + transId);
	}

}
