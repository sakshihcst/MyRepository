package com.searshc.mpuwebservice.processor.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DELIVERED_ITEMS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.HELP_REQUESTS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LONGEST_DURATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PERIOD_CODE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUESTED_ITEMS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SHORTEST_DURATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TOTAL_CANCELLED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TOTAL_COUPONS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TOTAL_DURATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TOTAL_EXCHANGES;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TOTAL_EXCHANGES_COMPLETED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TOTAL_EXCHANGES_REQUESTED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TOTAL_ON_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TOTAL_PICKUP_ORDERS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TOTAL_RETURNS;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.COMMA;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.KEY_SEPARATOR;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sears.dej.interfaces.vo.UserVO;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.util.DJUtilities;
import com.searshc.mpuwebservice.bean.ActivityReportDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDetailDTO;
import com.searshc.mpuwebservice.bean.OHMDetailDTO;
import com.searshc.mpuwebservice.bean.WebActitivtyDTO;
import com.searshc.mpuwebservice.bean.WebResponseDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO;
import com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor;
import com.searshc.mpuwebservice.processor.PickUpServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;
import com.searshc.mpuwebservice.util.SHCDateUtils;

@Service("associateActivityServicesProcessorImpl")
public class AssociateActivityServicesProcessorImpl implements AssociateActivityServicesProcessor {
	
	
	@Autowired
	private AssociateActivityServiceDAO associateActivityServiceDAOImpl;
		
	@Autowired
	private PickUpServiceProcessor pickUpServiceProcessor;
	
	
	@Autowired
	private EhCacheCacheManager cacheManager; 
	
	

	
	//private HashMap<String,String> associateInfo=new HashMap<String,String>();


	private static transient DJLogger logger = DJLoggerFactory.getLogger(AssociateActivityServicesProcessorImpl.class);
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#insertWebActivityRecords(java.util.List)
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public int insertWebActivityRecords(List<WebActitivtyDTO> webActitivtyDTOs)throws DJException{

		int count = associateActivityServiceDAOImpl.insertWebActivityRecords(webActitivtyDTOs);

		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#fetchWebActivityRecordsForDate(java.lang.String)
	 */
	public List<WebResponseDTO> fetchWebActivityRecordsForDate(String reportDate) throws DJException {

		List<WebResponseDTO> webResponseDTOs=null;
		webResponseDTOs=associateActivityServiceDAOImpl.fetchWebActivityRecordsForDate(reportDate);

		return webResponseDTOs;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#insertMPUActivityData(java.util.List)
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public List<Integer> insertMPUActivityData(List<MPUActivityDTO> mpuActivityDTOs) throws DJException {
		logger.info("Starting of insertMPUActivityData() with data: ","");
		StringBuffer transIdList=new StringBuffer();
		List<Integer> transIds = new ArrayList<Integer>();
		for(MPUActivityDTO mpuActivityDTO:mpuActivityDTOs){
			int transId=associateActivityServiceDAOImpl.insertMPUActivityData(mpuActivityDTO);
			transIdList.append(transId).append(COMMA);
			transIds.add(transId);
			List<MPUActivityDetailDTO> mpuActivityDetailDTOs=mpuActivityDTO.getMpuActivityDetailList();
			if(mpuActivityDetailDTOs!=null && mpuActivityDetailDTOs.size()>0){
				for(MPUActivityDetailDTO mpuActivityDetailDTO:mpuActivityDetailDTOs){
					mpuActivityDetailDTO.setTransId(transId);
				}
				int noOfItems=associateActivityServiceDAOImpl.insertMPUActivityDetail(mpuActivityDetailDTOs);
				logger.info("No.of items inserted for transId:"+transId+" is:", noOfItems);
				
				
				
			}
			
			/**
			 * Declare that the Cache is no longer clean
			 */
			if(null!=mpuActivityDTO){
				EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
				String formattedStoreNo = org.apache.commons.lang3.StringUtils.leftPad(mpuActivityDTO.getStoreNumber(), 5, '0');
				String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+mpuActivityDTO.getKiosk();
				String ohmCacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+mpuActivityDTO.getKiosk();
				logger.info("***cacheDirtyKey***", cacheDirtyKey);
				if(null!=requestQueueCache){
					requestQueueCache.put(cacheDirtyKey, "true");
					requestQueueCache.put(ohmCacheDirtyKey, "true");
				}
			}
		}
		logger.info("End of insertMPUActivityData() with transIdList:","");
		
		return transIds; 
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#fetchOHMData(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<OHMDetailDTO> fetchOHMData(String storeNumber, String date,String kioskName) throws DJException {
		logger.info("Start fetchOHMData() storeNo:date:kisokName:"+storeNumber+":"+date+":",kioskName);
		List<OHMDetailDTO> ohmDetailDTOs = new ArrayList<OHMDetailDTO>();
		/**
		 * Cache implementation
		 * By Nasir
		 */
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
		String formattedStoreNo = DJUtilities.leftPadding(storeNumber, 5);
		String cacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+kioskName;
		logger.debug("==fetchOHMData cacheDirtyKey==", cacheDirtyKey);
		boolean isCacheDirty = Boolean.TRUE;
		String listKey = "OHM_"+formattedStoreNo+"_"+kioskName+"_"+date;
		logger.info("==fetchOHMData listKey==", listKey);
		
		if(null!=requestQueueCache && null!=requestQueueCache.get(cacheDirtyKey) && null!=requestQueueCache.get(cacheDirtyKey).get()) {
			
			isCacheDirty = Boolean.valueOf(requestQueueCache.get(cacheDirtyKey).get().toString());
			logger.debug("===isCacheDirty===", isCacheDirty);
		}
		
		if(!isCacheDirty && null!=requestQueueCache.get(listKey)){
			logger.debug("Getting Data from Cache", "--Cache Is Clean");
			ohmDetailDTOs=(List<OHMDetailDTO>)requestQueueCache.get(listKey).get();
		}else{
			ohmDetailDTOs=associateActivityServiceDAOImpl.fetchOHMData(storeNumber, date, kioskName);
			/*
			 * Setting the list in Cache
			 */
			
			//START : For EI5 orders , we need to show only one order , instead of showing return order as well
			List<OHMDetailDTO> ohmDetailDTOs2 =  ohmDetailDTOs;
			List<OHMDetailDTO> listOfReturnIn5OrdersTobeRemoved = new ArrayList<OHMDetailDTO>(); 
			for(OHMDetailDTO ohmDetailDTO : ohmDetailDTOs){
				if(ohmDetailDTO.getRequestType().equalsIgnoreCase("EXCHANGEIN5")){
					
					//fetch the return_auth_code
					String return_auth_code = ohmDetailDTO.getReturnAuthCode();
					if(null != return_auth_code && ! return_auth_code.isEmpty()){
						for(OHMDetailDTO ohmDetailDTO2 : ohmDetailDTOs2){
							
							String request_type2 = ohmDetailDTO2.getRequestType();
							String return_auth_code2 = ohmDetailDTO2.getReturnAuthCode();
							
							if(request_type2.equalsIgnoreCase("RETURNIN5") && return_auth_code.equalsIgnoreCase(return_auth_code2)){
								listOfReturnIn5OrdersTobeRemoved.add(ohmDetailDTO2);
							}
						}
					}
					
					
				}
				
			}
			ohmDetailDTOs.removeAll(listOfReturnIn5OrdersTobeRemoved);
			
			//END : For EI5 orders , we need to show only one order , instead of showing return order as well
			
			requestQueueCache.put(listKey, ohmDetailDTOs);
			requestQueueCache.put(cacheDirtyKey, "false");
		}
		
		if(!ohmDetailDTOs.isEmpty()) {
		
		for(OHMDetailDTO ohmDetailDTO : ohmDetailDTOs){
			//String formattedDate = SHCDateUtils.getFormatedDate(new Date().toString(),"EEE MMM d HH:mm:ss zzz yyyy","yyyy-MM-dd HH:mm:ss");
			String strDate = "";
			long result=0L;
			if(MpuWebConstants.COMPLETE_STATUS_LIST.contains(ohmDetailDTO.getRequestStatus())){
				result=ohmDetailDTO.getEnd_time().getTime()-ohmDetailDTO.getStartTime().getTime();
        	} else {
        		try{
        			strDate = pickUpServiceProcessor.getTimeAccToTimeZone(new Date().toString(), storeNumber, "yyyy-MM-dd HH:mm:ss");
        			/***********/
        			SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	    	Date currentTime = date1.parse(strDate);
        	    	Date createdate = date1.parse(date1.format(ohmDetailDTO.getStartTime()));
        	    	result = (currentTime.getTime()-createdate.getTime());
        	    	/***********/
        			//result=(ConversionUtils.convertStringToDate(strDate,"yyyy-MM-dd HH:mm:ss").getTime())-ohmDetailDTO.getStartTime().getTime();
        		}catch(Exception exception){}
        	}
			String elspeseTime = SHCDateUtils.getElapsedTimeHoursMinutesSecondsString(result);
			List<String> hourMinSecList = new ArrayList<String>(Arrays.asList(elspeseTime.split(":")));
			if(hourMinSecList.size()>=3)
				ohmDetailDTO.setWaitTime(hourMinSecList.get(1)+":"+hourMinSecList.get(2));	
			ohmDetailDTO.setTotalSeconds(result/1000); 
			ohmDetailDTO.setSortTime(result/1000);
			String associateName=null;
			EhCacheCache associateCache =  (EhCacheCache) cacheManager.getCache("associateCache");
			//String associateName=null;
			
			if(ohmDetailDTO.getAssociateId()!=null&&!(ohmDetailDTO.getAssociateId().equals("000000"))&&!("".equals(ohmDetailDTO.getAssociateId())||" ".equals(ohmDetailDTO.getAssociateId()))){
			if( associateCache.get(ohmDetailDTO.getAssociateId())!=null){
				associateName=(String) associateCache.get(ohmDetailDTO.getAssociateId()).get();
				
			}
			
			if(associateName==null||associateName.equals("")){
				
				
				UserVO associateInfo = associateActivityServiceDAOImpl.getAssociateInfo(ohmDetailDTO.getAssociateId());
				if(associateInfo!=null){
				if(associateInfo.getGivenName()==null){
					associateInfo.setGivenName("");
				}
				
				associateName=associateInfo.getGivenName();
				associateCache.put(ohmDetailDTO.getAssociateId().toUpperCase(), associateName);}
				
				
			}
			}
			
			/*if(associateInfo!=null)
			{
				
				associateName=associateInfo.get(ohmDetailDTO.getAssociateId());
				
				if(associateName==null||associateName.equals("")){
					UserVO associateDetail = associateActivityServiceDAOImpl.getAssociateInfo(ohmDetailDTO.getAssociateId());	
					associateName=associateDetail.getGivenName();
					
					associateInfo.put(ohmDetailDTO.getAssociateId(),associateName);
					
					
				}
				
			}
			*/
			if(associateName==null) associateName="";
				ohmDetailDTO.setAssociateName(associateName);
			
			if(MpuWebConstants.COMPLETE_STATUS_LIST.contains(ohmDetailDTO.getRequestStatus())){
				ohmDetailDTO.setSortTime(0L);
	        }
		}
	}
		return ohmDetailDTOs;
	}
	
	

	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#updateMPURequest(com.searshc.mpuwebservice.bean.MPUActivityDTO)
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public int updateMPURequest(MPUActivityDTO mpuActivityDTO) throws DJException {
		logger.info("Start updateMPURequest()", "");
		int rowsUpdated=0;
		if(mpuActivityDTO.getMpuActivityDetailList()!=null && mpuActivityDTO.getMpuActivityDetailList().size()>0){
			rowsUpdated=associateActivityServiceDAOImpl.updateMPUDetailRequest(mpuActivityDTO.getMpuActivityDetailList());
			rowsUpdated=rowsUpdated+associateActivityServiceDAOImpl.updateMPURequest(mpuActivityDTO);
		}else{
			rowsUpdated=associateActivityServiceDAOImpl.updateMPURequest(mpuActivityDTO);
		}
		
		/**
		 * Declare that the Cache is no longer clean
		 */
		if(null!=mpuActivityDTO){
			EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
			String formattedStoreNo = org.apache.commons.lang3.StringUtils.leftPad(mpuActivityDTO.getStoreNumber(), 5, '0');
			String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+mpuActivityDTO.getKiosk();
			String ohmCacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+mpuActivityDTO.getKiosk();
			logger.info("***cacheDirtyKey***", cacheDirtyKey);
			if(null!=requestQueueCache){
				requestQueueCache.put(cacheDirtyKey, "true");
				requestQueueCache.put(ohmCacheDirtyKey, "true");
			}
		}
		logger.info("End updateMPURequest() rowsUpdated:","");
		return rowsUpdated;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#fetchMPUActivityRecordsForDate(java.lang.String)
	 */
	public List<ActivityReportDTO> fetchMPUActivityRecordsForDate(String reportDate) throws DJException {
		logger.info("Starting of fetchMPUActivityRecordsForDate() ", reportDate);
		TreeMap<String,ActivityReportDTO> associatesActivityMap = new TreeMap<String, ActivityReportDTO>();
		
		List<Map<String,Object>> pickupOrderList=associateActivityServiceDAOImpl.getAllPickUpOrders(reportDate);
		populateAllPickupOrders(associatesActivityMap,pickupOrderList,reportDate);
		
		List<Map<String,Object>> helpRequestList=associateActivityServiceDAOImpl.getAllHelpRequests(reportDate);
		populateAllHelpRequests(associatesActivityMap,helpRequestList,reportDate);
		
		List<Map<String,Object>> pickCouponsList=associateActivityServiceDAOImpl.getAllCouponsForPickup(reportDate);
		populateAllCouponsForPickup(associatesActivityMap,pickCouponsList,reportDate);
		
		List<Map<String,Object>> totalExchangesList=associateActivityServiceDAOImpl.getTotalExchanges(reportDate);
		populateTotalExchanges(associatesActivityMap,totalExchangesList,reportDate);
		
		List<Map<String,Object>> exchangeCouponsList=associateActivityServiceDAOImpl.getTotalExchangeCouponsSummary(reportDate);
		populateTotalExchangeCouponsSummary(associatesActivityMap,exchangeCouponsList,reportDate);
		
		List<Map<String,Object>> returnsCancelledList=associateActivityServiceDAOImpl.getTotalTenderStoreReturnsCancelled(reportDate);
		populateAllStoreReturnsCancelled(associatesActivityMap,returnsCancelledList,reportDate);
		
		List<Map<String,Object>> associateReturnsList=associateActivityServiceDAOImpl.getTotalTenderReturnsByAssociate(reportDate);
		populateTotalTenderReturnsByAssociate(associatesActivityMap,associateReturnsList,reportDate);
		
		List<Map<String,Object>> storeReturnsList=associateActivityServiceDAOImpl.getTotalTenderReturnsByStore(reportDate);
		populateTotalTenderReturnsByStore(associatesActivityMap,storeReturnsList,reportDate);
		
		List<WebResponseDTO> webResponseDTOs=associateActivityServiceDAOImpl.fetchWebActivityRecordsForDate(reportDate);
		populateWebActivityForAllAssociates(associatesActivityMap,webResponseDTOs,reportDate);
		
		List<ActivityReportDTO> activityReportDTOs=new ArrayList<ActivityReportDTO>(associatesActivityMap.values());
	
		logger.info("Ending of fetchMPUActivityRecordsForDate() ", reportDate);
		return activityReportDTOs;
	}

	/**
	 * This populates all web activities done by associates into associatesActivityMap
	 * @param associatesActivityMap Map<String, ActivityReportDTO>
	 * @param associatesWebDetailsRecord List<WebResponseDTO>
	 * @param reportDate String
	 */
	private void populateWebActivityForAllAssociates(Map<String, ActivityReportDTO> associatesActivityMap,List<WebResponseDTO> associatesWebDetailsRecord,String reportDate) {
		ActivityReportDTO activityReportDTO=null;
		String storeNumber=null;
		String associateCode=null;
		String periodCode = null;
		try{
			for(WebResponseDTO webResponseDTO:associatesWebDetailsRecord){
				storeNumber=webResponseDTO.getStoreNumber();
				associateCode=MPUWebServiceUtil.getFormattedAssociateId(webResponseDTO.getAssociateId());
				periodCode=webResponseDTO.getPeriod();
				String searchKey=getSearchKey(storeNumber, associateCode, periodCode);
				activityReportDTO=getActivityReportDTO(associatesActivityMap,storeNumber,associateCode,periodCode,reportDate,searchKey);
				activityReportDTO.setTotalOntimeWebReqeusts(webResponseDTO.getBinnedCount());
				activityReportDTO.setTotalOS(webResponseDTO.getOutOfStockCount());
				activityReportDTO.setTotalNR(webResponseDTO.getNoResponseCount());
				associatesActivityMap.put(searchKey, activityReportDTO);
			}
		}catch(Exception exp){
			logger.error("Exception occured in populateWebActivityForAllAssociates() due to:==", exp.getMessage());
		}
	}

	/**
	 * This populates total exchanges summary made in store into associatesActivityMap
	 * @param associatesActivityMap TreeMap<String, ActivityReportDTO> associatesActivityMap
	 * @param exchangeCouponsList List<Map<String, Object>>
	 * @param reportDate String
	 */
	private void populateTotalExchangeCouponsSummary(TreeMap<String, ActivityReportDTO> associatesActivityMap,List<Map<String, Object>> exchangeCouponsList, String reportDate) {
		String storeNumber=null;
		String associateCode=null;
		String periodCode = null;
		ActivityReportDTO activityReportDTO=null;
		try{
			for(Map<String, Object> exchangeCouponMap:exchangeCouponsList){
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)exchangeCouponMap.get(STORE_NUMBER.name())));
				associateCode=MPUWebServiceUtil.getFormattedAssociateId((String)exchangeCouponMap.get(ASSOCIATE_ID.name()));
				periodCode=(String)exchangeCouponMap.get(PERIOD_CODE.name());
				String searchKey=getSearchKey(storeNumber, associateCode, periodCode);
				activityReportDTO=getActivityReportDTO(associatesActivityMap,storeNumber,associateCode,periodCode,reportDate,searchKey);
				activityReportDTO.setTotalExchangesRequested((Long)exchangeCouponMap.get(TOTAL_EXCHANGES_REQUESTED.name()));
				activityReportDTO.setTotalExchangesCompleted((BigDecimal)exchangeCouponMap.get(TOTAL_EXCHANGES_COMPLETED.name()));
				activityReportDTO.setTotalExchangeCoupons((BigDecimal)exchangeCouponMap.get(TOTAL_COUPONS.name()));
				activityReportDTO.setTotalExchangeOntime((BigDecimal)exchangeCouponMap.get(TOTAL_ON_TIME.name()));
				activityReportDTO.setTotalExchangesCancelled((BigDecimal)exchangeCouponMap.get(TOTAL_CANCELLED.name()));
				associatesActivityMap.put(searchKey,activityReportDTO);
			}
		}catch(Exception exp){
			logger.error("Exception occured in populateTotalExchangeCouponsSummary() due to:==", exp.getMessage());
		}
		
	}

	/**
	 * This method populates total returns done by store into associatesActivityMap
	 * @param associatesActivityMap TreeMap<String, ActivityReportDTO>
	 * @param storeReturnsList List<Map<String, Object>>
	 * @param reportDate String
	 */
	private void populateTotalTenderReturnsByStore(TreeMap<String, ActivityReportDTO> associatesActivityMap,List<Map<String, Object>> storeReturnsList, String reportDate) {
		String storeNumber=null;
		String associateCode=null;
		String periodCode = null;
		ActivityReportDTO activityReportDTO=null;
		try{
			for(Map<String, Object> storeReturnMap:storeReturnsList){
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)storeReturnMap.get(STORE_NUMBER.name())));
				associateCode=MPUWebServiceUtil.getFormattedAssociateId((String)storeReturnMap.get(ASSOCIATE_ID.name()));
				periodCode=(String)storeReturnMap.get(PERIOD_CODE.name());
				String searchKey=getSearchKey(storeNumber, associateCode, periodCode);
				activityReportDTO=getActivityReportDTO(associatesActivityMap,storeNumber,associateCode,periodCode,reportDate,searchKey);
				activityReportDTO.setTotalTenderStoreReturns((Long)storeReturnMap.get(TOTAL_RETURNS.name()));
				activityReportDTO.setTotalTenderStoreReturnTime((String)storeReturnMap.get(TOTAL_DURATION.name()));
				activityReportDTO.setMaxTenderStoreReturnTime((String)storeReturnMap.get(LONGEST_DURATION.name()));
				activityReportDTO.setMinTenderStoreReturnTime((String)storeReturnMap.get(SHORTEST_DURATION.name()));
				activityReportDTO.setTotalStoreReturnCoupons((BigDecimal)storeReturnMap.get(TOTAL_COUPONS.name()));
				activityReportDTO.setTotalStoreReturnOntime((BigDecimal)storeReturnMap.get(TOTAL_ON_TIME.name()));
				associatesActivityMap.put(searchKey,activityReportDTO);
			}
		}catch(Exception exp){
			logger.error("Exception occured in populateTotalTenderReturnsByStore() due to:==", exp.getMessage());
		}
		
	}

	/**
	 * This method populates total returns done by associate at store to associatesActivityMap
	 * @param associatesActivityMap TreeMap<String, ActivityReportDTO>
	 * @param associateReturnsList List<Map<String, Object>>
	 * @param reportDate String
	 */
	private void populateTotalTenderReturnsByAssociate(TreeMap<String, ActivityReportDTO> associatesActivityMap,List<Map<String, Object>> associateReturnsList, String reportDate) {
		String storeNumber=null;
		String associateCode=null;
		String periodCode = null;
		ActivityReportDTO activityReportDTO=null;
		try{
			for(Map<String, Object> associateReturnMap:associateReturnsList){
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)associateReturnMap.get(STORE_NUMBER.name())));
				associateCode=MPUWebServiceUtil.getFormattedAssociateId((String)associateReturnMap.get(ASSOCIATE_ID.name()));
				periodCode=(String)associateReturnMap.get(PERIOD_CODE.name());
				String searchKey=getSearchKey(storeNumber, associateCode, periodCode);
				activityReportDTO=getActivityReportDTO(associatesActivityMap,storeNumber,associateCode,periodCode,reportDate,searchKey);
				activityReportDTO.setTotalTenderReturns((Long)associateReturnMap.get(TOTAL_RETURNS.name()));
				activityReportDTO.setTotalTenderReturnTime((String)associateReturnMap.get(TOTAL_DURATION.name()));
				activityReportDTO.setMaxTenderReturnTime((String)associateReturnMap.get(LONGEST_DURATION.name()));
				activityReportDTO.setMinTenderReturnTime((String)associateReturnMap.get(SHORTEST_DURATION.name()));
				activityReportDTO.setTotalReturnCoupons((BigDecimal)associateReturnMap.get(TOTAL_COUPONS.name()));
				activityReportDTO.setTotalReturnOntime((BigDecimal)associateReturnMap.get(TOTAL_ON_TIME.name()));
				associatesActivityMap.put(searchKey,activityReportDTO);
			}
		}catch(Exception exp){
			logger.error("Exception occured in populateTotalTenderReturnsByAssociate() due to:==", exp.getMessage());
		}
		
	}

	/**
	 * This method populates total coupons generated for pickup at store to associatesActivityMap
	 * @param associatesActivityMap TreeMap<String, ActivityReportDTO>
	 * @param pickCouponsList List<Map<String, Object>>
	 * @param reportDate String
	 */
	private void populateAllCouponsForPickup(TreeMap<String, ActivityReportDTO> associatesActivityMap,List<Map<String, Object>> pickCouponsList, String reportDate) {
		String storeNumber=null;
		String associateCode=null;
		String periodCode = null;
		String searchKey=null;
		ActivityReportDTO activityReportDTO=null;
		try{
			for(Map<String, Object> pickupCouponMap:pickCouponsList){
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)pickupCouponMap.get(STORE_NUMBER.name())));
				associateCode=MPUWebServiceUtil.getFormattedAssociateId((String)pickupCouponMap.get(ASSOCIATE_ID.name()));
				periodCode=(String)pickupCouponMap.get(PERIOD_CODE.name());
				searchKey=getSearchKey(storeNumber, associateCode, periodCode);
				activityReportDTO=getActivityReportDTO(associatesActivityMap,storeNumber,associateCode,periodCode,reportDate,searchKey);
				activityReportDTO.setTotalCouponsForPickup((BigDecimal)pickupCouponMap.get(TOTAL_COUPONS.name()));
				activityReportDTO.setTotalOntimeRequests((BigDecimal)pickupCouponMap.get(TOTAL_ON_TIME.name()));
				associatesActivityMap.put(searchKey,activityReportDTO);
			}
		}catch(Exception exp){
			logger.error("Exception occured in populateAllCouponsForPickup() due to:==", exp.getMessage());
		}
		
	}

	/**
	 * This method populates total exchanges made in store into  associatesActivityMap
	 * @param associatesActivityMap TreeMap<String, ActivityReportDTO>
	 * @param totalExchangesList List<Map<String, Object>>
	 * @param reportDate String
	 */
	private void populateTotalExchanges(TreeMap<String, ActivityReportDTO> associatesActivityMap,List<Map<String, Object>> totalExchangesList, String reportDate) {
		String storeNumber=null;
		String associateCode=null;
		String periodCode = null;
		ActivityReportDTO activityReportDTO=null;
		try{
			for(Map<String, Object> exchangeMap:totalExchangesList){
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)exchangeMap.get(STORE_NUMBER.name())));
				associateCode=MPUWebServiceUtil.getFormattedAssociateId((String)exchangeMap.get(ASSOCIATE_ID.name()));
				periodCode=(String)exchangeMap.get(PERIOD_CODE.name());
				String searchKey=getSearchKey(storeNumber, associateCode, periodCode);
				activityReportDTO=getActivityReportDTO(associatesActivityMap,storeNumber,associateCode,periodCode,reportDate,searchKey);
				activityReportDTO.setTotalExchanges((Long)exchangeMap.get(TOTAL_EXCHANGES.name()));
				activityReportDTO.setTotalExchangeTime((String)exchangeMap.get(TOTAL_DURATION.name()));
				activityReportDTO.setMaxExchangeTime((String)exchangeMap.get(LONGEST_DURATION.name()));
				activityReportDTO.setMinExchangeTime((String)exchangeMap.get(SHORTEST_DURATION.name()));
				associatesActivityMap.put(searchKey,activityReportDTO);
			}
		}catch(Exception exp){
			logger.error("Exception occured in populateTotalExchanges() due to:==", exp.getMessage());
		}
		
	}

	/**
	 * This method populates total returns cancelled by store into associatesActivityMap
	 * @param associatesActivityMap TreeMap<String, ActivityReportDTO>
	 * @param returnsCancelledList List<Map<String, Object>>
	 * @param reportDate String
	 */
	private void populateAllStoreReturnsCancelled(TreeMap<String, ActivityReportDTO> associatesActivityMap,List<Map<String, Object>> returnsCancelledList, String reportDate) {
		String storeNumber=null;
		String associateCode=null;
		String periodCode = null;
		ActivityReportDTO activityReportDTO=null;
		try{
			for(Map<String, Object> returnCancelMap:returnsCancelledList){
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)returnCancelMap.get(STORE_NUMBER.name())));
				associateCode=MPUWebServiceUtil.getFormattedAssociateId((String)returnCancelMap.get(ASSOCIATE_ID.name()));
				periodCode=(String)returnCancelMap.get(PERIOD_CODE.name());
				String searchKey=getSearchKey(storeNumber, associateCode, periodCode);
				activityReportDTO=getActivityReportDTO(associatesActivityMap,storeNumber,associateCode,periodCode,reportDate,searchKey);
				activityReportDTO.setTotalTenderStoreReturnsCancelled((Long)returnCancelMap.get(TOTAL_CANCELLED.name()));
				associatesActivityMap.put(searchKey,activityReportDTO);
			}
		}catch(Exception exp){
			logger.error("Exception occured in populateAllStoreReturnsCancelled() due to:==", exp.getMessage());
		}
		
	}

	/**
	 * This method populates total help requests placed in store into associatesActivityMap
	 * @param associatesActivityMap TreeMap<String, ActivityReportDTO>
	 * @param helpRequestList List<Map<String, Object>>
	 * @param reportDate String
	 */
	private void populateAllHelpRequests(TreeMap<String, ActivityReportDTO> associatesActivityMap,List<Map<String, Object>> helpRequestList, String reportDate) {
		String storeNumber=null;
		String associateCode=null;
		String periodCode = null;
		try{
			ActivityReportDTO activityReportDTO=null;
			for(Map<String, Object> helpRequestMap:helpRequestList){
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)helpRequestMap.get(STORE_NUMBER.name())));
				associateCode=MPUWebServiceUtil.getFormattedAssociateId((String)helpRequestMap.get(ASSOCIATE_ID.name()));
				periodCode=(String)helpRequestMap.get(PERIOD_CODE.name());
				String searchKey=getSearchKey(storeNumber, associateCode, periodCode);
				activityReportDTO=getActivityReportDTO(associatesActivityMap,storeNumber,associateCode,periodCode,reportDate,searchKey);
				activityReportDTO.setTotalHelpPages((BigDecimal)helpRequestMap.get(HELP_REQUESTS.name()));
				associatesActivityMap.put(searchKey,activityReportDTO);
			}
		}catch(Exception exp){
			logger.error("Exception occured in populateAllHelpRequests() due to:==", exp.getMessage());
		}
		
		
	}

	/**
	 * This method populates total pickup information into associatesActivityMap
	 * @param associatesActivityMap TreeMap<String, ActivityReportDTO>
	 * @param pickupOrderList List<Map<String, Object>>
	 * @param reportDate String
	 */
	private void populateAllPickupOrders(TreeMap<String, ActivityReportDTO> associatesActivityMap,List<Map<String, Object>> pickupOrderList, String reportDate) {
		String storeNumber=null;
		String associateCode=null;
		String periodCode = null;
		ActivityReportDTO activityReportDTO=null;
		try{
			for(Map<String, Object> pickupOrderMap:pickupOrderList){
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)pickupOrderMap.get(STORE_NUMBER.name())));
				associateCode=MPUWebServiceUtil.getFormattedAssociateId((String)pickupOrderMap.get(ASSOCIATE_ID.name()));
				periodCode=(String)pickupOrderMap.get(PERIOD_CODE.name());
				String searchKey=getSearchKey(storeNumber,associateCode,periodCode);
				if(!associatesActivityMap.containsKey(searchKey)){
					activityReportDTO=new ActivityReportDTO();
					activityReportDTO.setStoreNumber(storeNumber);
					activityReportDTO.setAssociateId(associateCode);
					activityReportDTO.setPeriodCode(periodCode);
					activityReportDTO.setReportDate(reportDate);
					activityReportDTO.setTotalOrders((Long)pickupOrderMap.get(TOTAL_PICKUP_ORDERS.name()));
					activityReportDTO.setTotalRequestedItems((BigDecimal)pickupOrderMap.get(REQUESTED_ITEMS.name()));
					activityReportDTO.setTotalDeliveredItems((BigDecimal)pickupOrderMap.get(DELIVERED_ITEMS.name()));
					activityReportDTO.setTotalDuration((String)pickupOrderMap.get(TOTAL_DURATION.name()));
					activityReportDTO.setLongestDuration((String)pickupOrderMap.get(LONGEST_DURATION.name()));
					activityReportDTO.setShortestDuration((String)pickupOrderMap.get(SHORTEST_DURATION.name()));
					associatesActivityMap.put(searchKey, activityReportDTO);
				}
			}
		}catch(Exception exp){
			logger.error("Exception occured in populateAllPickupOrders() due to:===", exp.getMessage());
		}
		
	}

	/**
	 * This method returns ActivityReportDTo which is in map otherwise it will create a new DTO object
	 * @param associatesActivityMap Map<String, ActivityReportDTO> associatesActivityMap
	 * @param storeNumber String
	 * @param associateCode String
	 * @param periodCode String
	 * @param reportDate String
	 * @param searchKey String
	 * @return ActivityReportDTO
	 */
	private ActivityReportDTO getActivityReportDTO(Map<String, ActivityReportDTO> associatesActivityMap, String storeNumber, String associateCode, String periodCode, String reportDate,String searchKey){
		ActivityReportDTO activityReportDTO=null;
		
		if(associatesActivityMap.containsKey(searchKey)){
			activityReportDTO=associatesActivityMap.get(searchKey);
		}else{
			activityReportDTO=new ActivityReportDTO();	
			activityReportDTO.setStoreNumber(storeNumber);
			activityReportDTO.setAssociateId(associateCode);
			activityReportDTO.setPeriodCode(periodCode);
			activityReportDTO.setReportDate(reportDate);
		}
		return activityReportDTO;
	}
	
	/**
	 * It returns the search key according to storeNumber,associateId,periodCode 
	 * @param storeNumber String
	 * @param associateCode String
	 * @param periodCode String
	 * @return String
	 */
	private String getSearchKey(String storeNumber, String associateCode,String periodCode) {
		return storeNumber+KEY_SEPARATOR+associateCode+KEY_SEPARATOR+periodCode;
	}

	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#validateMPUActivityInsertInputs(java.util.List)
	 */
	public String validateMPUActivityInsertInputs(List<MPUActivityDTO> mpuActivityDTOs) throws DJException {
		String validationOK=PropertyUtils.getProperty("mpu.web.validation.ok");
		if(mpuActivityDTOs==null){
			return PropertyUtils.getProperty("mpu.activity.input.MPUActivityDTO");
		}if(mpuActivityDTOs.size()==0){
			return PropertyUtils.getProperty("mpu.activity.input.empty.MPUActivityDTO");
		}
		return validationOK;
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#validateWebActivityInsertInputs(java.util.List)
	 */
	public String validateWebActivityInsertInputs(List<WebActitivtyDTO> webActitivtyDTOs) throws DJException {
		String validationOK=PropertyUtils.getProperty("mpu.web.validation.ok");
		if(webActitivtyDTOs==null){
			return PropertyUtils.getProperty("mpu.web.input.WebActitivtyDTO");
		}if(webActitivtyDTOs.size()==0){
			return PropertyUtils.getProperty("mpu.web.input.empty.WebActitivtyDTO");
		}
		return validationOK;
	}
	public String getUserName(String userId){
		String userName=null;
		try{
		EhCacheCache associateCache =  (EhCacheCache) cacheManager.getCache("associateCache");
		//String associateName=null;
	
		
		if( userId!=null&&!"".equals(userId)){
			userName=(String) associateCache.get(userId).get();
		if(userName==null||userName.equals("")){
			
			
			UserVO associateInfo = associateActivityServiceDAOImpl.getAssociateInfo(userId);
			
			if(associateInfo!=null)
			{
			if(associateInfo.getGivenName()!=null){
				userName=associateInfo.getGivenName();
				
			}
		}
			
			
			
			logger.info("getting data from service: "+ userId+":",userName);
			
			associateCache.put(userId.toUpperCase(), userName);
			
		}
		}
		}
		catch(Exception e){
			logger.error("Exception while fetching user name",e);
		}
		if (userName==null) userName="";
		return userName;
		
		
}
}
