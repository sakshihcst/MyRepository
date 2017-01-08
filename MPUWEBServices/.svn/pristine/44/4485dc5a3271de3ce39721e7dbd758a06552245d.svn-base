package com.searshc.mpuwebservice.dao.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CARD_SWIPED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CUSTOMER_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DELIVERED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.END_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_IDENTIFIER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.KIOSK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.NOT_DELIVERED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORIGINAL_SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUESTED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK_NO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SEARCH_METHOD;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SEARCH_VALUE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.START_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANS_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.WORK_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RETURNAUTHCODE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORIGINALJSON;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.COUPON_GENERATED;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_END_DATE;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_END_TIME;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_START_DATE;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_START_TIME;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.SPACE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_SOURCE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SC_SCAN;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.NOT_DELIVERED_QTY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sears.dej.interfaces.vo.UserVO;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.sears.dj.common.util.DJUtilities;
import com.sears.util.web.client.RestTemplate;
import com.searshc.mpuwebservice.bean.MPUActivityDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDetailDTO;
import com.searshc.mpuwebservice.bean.OHMDetailDTO;
import com.searshc.mpuwebservice.bean.WebActitivtyDTO;
import com.searshc.mpuwebservice.bean.WebResponseDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO;
import com.searshc.mpuwebservice.mapper.OHMDetailMapper;
import com.searshc.mpuwebservice.mapper.WebResponseMapper;
import com.searshc.mpuwebservice.util.PropertyUtils;



@Repository("associateActivityServiceDAOImpl")
public class AssociateActivityServiceDAOImpl  extends DJMPUCommonDAO implements AssociateActivityServiceDAO {
	
	@Autowired
	private RestTemplate restTemplate;
	@Value("${LdapUser_url}")
	private String userServiceUrl;
	private static transient DJLogger logger = DJLoggerFactory.getLogger(AssociateActivityServiceDAOImpl.class);	
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#fetchWebActivityRecordsForDate(java.lang.String)
	 */
	public List<WebResponseDTO> fetchWebActivityRecordsForDate(String reportDate)throws DJException{
		logger.info("Starting of fetchWebActivityRecordsForDate() for date:==== ", reportDate);
		List<NamedParameterJdbcTemplate> namedParameterJdbcTemplates= getNamedParameterJdbcTemplates();
		List<WebResponseDTO> webResponseDTOs = new ArrayList<WebResponseDTO>();
		List<WebResponseDTO> tempWebResonseDTOs=null;
		Map<String,Object> parameterMap=new HashMap<String,Object>();
		parameterMap.put(REPORT_START_DATE,reportDate+REPORT_START_TIME);
		parameterMap.put(REPORT_END_DATE,reportDate+REPORT_END_TIME);
		String sql= PropertyUtils.getProperty("select.from.webResponse.sql");
		for(NamedParameterJdbcTemplate namedParameterJdbcTemplate:namedParameterJdbcTemplates){
				tempWebResonseDTOs=(List<WebResponseDTO>)namedParameterJdbcTemplate.query(sql, parameterMap, new WebResponseMapper());
				if(tempWebResonseDTOs!=null && tempWebResonseDTOs.size()>0){
						webResponseDTOs.addAll(tempWebResonseDTOs);
				}
		}
		logger.info("Ending of fetchWebActivityRecordsForDate() for date:==== ", reportDate);
		return webResponseDTOs;
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor#insertWebActivityRecords(java.util.List)
	 */
	public int insertWebActivityRecords(List<WebActitivtyDTO> webActitivtyDTOs) throws DJException {
		logger.debug("insertWebActivityRecords", "Start ");
		String sql=PropertyUtils.getProperty("insert.into.web_response_associate_activity_report") ;
		List<Map<String,Object>> params = new ArrayList<Map<String,Object>>();
		String storeNumber=null;
		for(WebActitivtyDTO webActitivtyDTO:webActitivtyDTOs){
			storeNumber=webActitivtyDTO.getStoreNumber();
			params.add(getWebResponseActivityColumns(webActitivtyDTO));
		}
		logger.info("insertWebActivityRecords", "List of map pf parameter created ");
		@SuppressWarnings("unchecked")
		Map<String,Object> paramsTemp[] = new HashMap[0];
		int insertCount= batchUpdate(storeNumber, sql,params.toArray(paramsTemp)).length;
		logger.info("Ending of insertWebActivityRecords() with :===",insertCount+":==== number of rows inserted ");
		return insertCount;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#insertMPUActivityData(com.searshc.mpuwebservice.bean.MPUActivityDTO)
	 */
	public int insertMPUActivityData(MPUActivityDTO mpuActivityDTO) throws DJException {
		logger.debug("Start Of insertMPUActivityData()", mpuActivityDTO);
		logger.info("Start Of", " insertMPUActivityData()");
		String sql=PropertyUtils.getProperty("insert.into.request_mpu_trans");
		int transId= super.updateWithKeyHolder(mpuActivityDTO.getStoreNumber(), sql, getMPUActivityMapToInsert(mpuActivityDTO)).intValue();
		logger.info("End Of ", " insertMPUActivityData()");
		logger.debug("End Of insertMPUActivityData() having transId:"+transId, mpuActivityDTO);
		return transId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#fetchOHMData(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<OHMDetailDTO> fetchOHMData(String storeNumber, String date,String kioskName) throws DJException {
		logger.debug("Start Of fetchOHMData() storeNo:date:kioskName"+"::"+storeNumber+":"+date+":",kioskName);
		logger.info("Start Of", " fetchOHMData()");
		Map<String,Object> params=new HashMap<String, Object>();
		String sqlOpen=PropertyUtils.getProperty("select.ohm.details_open");
		String sqlCompleted=PropertyUtils.getProperty("select.ohm.details_completed");
		params.put(STORE_NUMBER.name(),storeNumber);
		params.put(KIOSK.name(), kioskName);
		params.put(REPORT_START_DATE, date+REPORT_START_TIME);
		params.put(REPORT_END_DATE,date+REPORT_END_TIME);
		List<OHMDetailDTO> ohmDetailDTOsOpen=query(storeNumber, sqlOpen, params,new OHMDetailMapper());
		List<OHMDetailDTO> ohmDetailDTOsCompleted=query(storeNumber, sqlCompleted, params,new OHMDetailMapper());
		
		//merege two lists
		ohmDetailDTOsOpen.addAll(ohmDetailDTOsCompleted);
		logger.info("End Of", " fetchOHMData()");
		logger.debug("End Of fetchOHMData() storeNo:date:kioskName"+"::"+storeNumber+":"+date+":",kioskName);
		return ohmDetailDTOsOpen;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#insertMPUActivityDetail(java.util.List)
	 */
	public int insertMPUActivityDetail(List<MPUActivityDetailDTO> itemDetailList) throws DJException {
		
		logger.debug("Start Of insertMPUActivityDetail() ",itemDetailList);
		logger.info("Start Of", " fetchOHMData()");
		String sql=PropertyUtils.getProperty("insert.into.request_mpu_details");
		String storeNumber=itemDetailList.get(0).getStoreNumber();
		List<Map<String,Object>> params = new ArrayList<Map<String,Object>>();
		@SuppressWarnings("unchecked")
		Map<String,Object> paramsTemp[] = new HashMap[0];
		for(MPUActivityDetailDTO mpuActivityDetailDTO:itemDetailList){
			params.add(getMPUDetailMapToInsert(mpuActivityDetailDTO));
		}
		
		DJUtilities.createSQLCommand(sql, params.toArray(paramsTemp), logger, "itemDetailList");
		
		int insertCount= batchUpdate(storeNumber, sql,params.toArray(paramsTemp)).length;
		logger.info("End Of", " insertMPUActivityDetail()");
		logger.debug("End Of insertMPUActivityDetail() ",itemDetailList);
		return insertCount;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#updateMPUDetailRequest(java.util.List)
	 */
	public int updateMPUDetailRequest(List<MPUActivityDetailDTO> mpuActivityDetailDTOs)throws DJException {
		logger.debug("Start Of updateMPUDetailRequest() ",mpuActivityDetailDTOs);
		logger.info("Start Of", " updateMPUDetailRequest()");
		String sql=PropertyUtils.getProperty("update.request_mpu_details")+SPACE+PropertyUtils.getProperty("get_store_number");
		List<Map<String,Object>> params= new ArrayList<Map<String,Object>>() ;
		String storeNumber=mpuActivityDetailDTOs.get(0).getStoreNumber();
		@SuppressWarnings("unchecked")
		Map<String,Object> paramsTemp[] = new HashMap[0];
		for(MPUActivityDetailDTO mpuActivityDetailDTO:mpuActivityDetailDTOs){
			params.add(getMPUDetailMapToUpdate(mpuActivityDetailDTO));
		}
		
		DJUtilities.createSQLCommand(sql, params.toArray(paramsTemp), logger, "updateMPUDetailRequest");
		
		int[] rowsUpdated= batchUpdate(storeNumber, sql,params.toArray(paramsTemp));
		logger.info("End Of", " updateMPUDetailRequest()");
		logger.debug("End Of updateMPUDetailRequest() ",mpuActivityDetailDTOs);
		return rowsUpdated[0];
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#updateMPURequest(com.searshc.mpuwebservice.bean.MPUActivityDTO)
	 */
	public int updateMPURequest(MPUActivityDTO mpuActivityDTO)throws DJException {
		String sql=PropertyUtils.getProperty("update.request_mpu_trans");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put(RQT_ID.name(), mpuActivityDTO.getRqtId());
		params.put(STORE_NUMBER.name(), mpuActivityDTO.getStoreNumber());
		params.put(END_TIME.name(), mpuActivityDTO.getEndTime());
		params.put(REQUEST_STATUS.name(), mpuActivityDTO.getRequestStatus());
		params.put(COUPON_GENERATED.name(),mpuActivityDTO.isCouponGenerated());
		if(mpuActivityDTO.getSalesCheck() != null){
			sql=sql+PropertyUtils.getProperty("update.salescheck");
			params.put(SALESCHECK.name(), mpuActivityDTO.getSalesCheck());
		}
		if(mpuActivityDTO.getAssociateId()!=null){
			sql=sql+PropertyUtils.getProperty("update.associate_Id");
			params.put(ASSOCIATE_ID.name(), mpuActivityDTO.getAssociateId());
		}
		if(mpuActivityDTO.getAssociateName()!=null){
			sql=sql+PropertyUtils.getProperty("update.associate_Name");
			params.put(ASSOCIATE_NAME.name(),mpuActivityDTO.getAssociateName());
		}
		sql=sql+SPACE+PropertyUtils.getProperty("get_store_number");
		int rowsUpdated=update(mpuActivityDTO.getStoreNumber(), sql, params);
		return rowsUpdated;
	}
	
	/**
	 * This method build the map of named parameters which will pass to query to insert the data in request_mpu_detail table
	 * @param mpuActivityDetailDTO MPUActivityDetailDTO
	 * @return Map<String,Object>
	 */
	private Map<String,Object> getMPUDetailMapToInsert(MPUActivityDetailDTO mpuActivityDetailDTO) {
		HashMap<String,Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(TRANS_ID.name(), mpuActivityDetailDTO.getTransId());
		parametersMap.put(RQD_ID.name(), mpuActivityDetailDTO.getRqdId());
		parametersMap.put(REQUESTED_QUANTITY.name(), mpuActivityDetailDTO.getRequestedQuantity());
		parametersMap.put(STORE_NUMBER.name(), mpuActivityDetailDTO.getStoreNumber());
		parametersMap.put(CREATE_TIMESTAMP.name(),mpuActivityDetailDTO.getCreatedTimeStamp());
		parametersMap.put(ITEM_STATUS.name(), mpuActivityDetailDTO.getItemStatus());
		parametersMap.put(NOT_DELIVERED_QTY.name(), mpuActivityDetailDTO.getNotDelieveredQty());
		return parametersMap;
	}

	/**
	 * This method build the map of named parameters which will pass to query to update the data in request_mpu_detail table
	 * @param mpuActivityDetailDTO
	 * @return
	 */
	private Map<String,Object> getMPUDetailMapToUpdate(MPUActivityDetailDTO mpuActivityDetailDTO) {
		HashMap<String,Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(RQD_ID.name(), mpuActivityDetailDTO.getRqdId());
		parametersMap.put(NOT_DELIVERED_QUANTITY.name(), mpuActivityDetailDTO.getNotDelieveredQty());
		parametersMap.put(DELIVERED_QUANTITY.name(), mpuActivityDetailDTO.getDeliveredQuantity());
		parametersMap.put(UPDATE_TIMESTAMP.name(),mpuActivityDetailDTO.getUpdatedTimeStamp());
		parametersMap.put(STORE_NUMBER.name(), mpuActivityDetailDTO.getStoreNumber());
		parametersMap.put(ITEM_STATUS.name(), mpuActivityDetailDTO.getItemStatus());
		return parametersMap;
	}
	
	/**
	 * This method builds the parameterMap for corresponding column as key and value
	 * @param webActitivtyDTO WebActitivtyDTO
	 * @return Map<String,Object>
	 */
	private Map<String,Object> getWebResponseActivityColumns(WebActitivtyDTO webActitivtyDTO){
		HashMap<String,Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(STORE_NUMBER.name(), webActitivtyDTO.getStoreNumber());
		parametersMap.put(ASSOCIATE_ID.name(), webActitivtyDTO.getAssociateId());
		parametersMap.put(CREATED_DATE.name(),webActitivtyDTO.getCreatedDate());
		parametersMap.put(SALESCHECK_NO.name(), webActitivtyDTO.getSalescheck());
		parametersMap.put(ITEM_IDENTIFIER.name(), webActitivtyDTO.getItemIdentifier());
		parametersMap.put(ITEM_STATUS.name(), webActitivtyDTO.getItemStatus());
		parametersMap.put(WORK_STATUS.name(), webActitivtyDTO.getWorkStatus());
		return parametersMap;
	}
	
	/**
	 * This method build the map of named parameters which will pass to query to insert the data in request_mpu_trans table
	 * @param mpuActivityDTO
	 * @return
	 */
	private Map<String,Object> getMPUActivityMapToInsert(MPUActivityDTO mpuActivityDTO){
		
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(ASSOCIATE_ID.name(), mpuActivityDTO.getAssociateId());
		parametersMap.put(STORE_NUMBER.name(), mpuActivityDTO.getStoreNumber());
		parametersMap.put(REQUEST_STATUS.name(), mpuActivityDTO.getRequestStatus());
		parametersMap.put(START_TIME.name(), mpuActivityDTO.getStartTime());
		parametersMap.put(RQT_ID.name(), mpuActivityDTO.getRqtId());
		parametersMap.put(SEARCH_METHOD.name(), mpuActivityDTO.getSearchMethod());
		if(null == mpuActivityDTO.getKiosk() && (null != mpuActivityDTO.getRequestType() 
				&& MpuWebConstants.MPU_PAGE_REGISTER.equalsIgnoreCase(mpuActivityDTO.getRequestType()))) {
			//Added for JIRA-25084 (platform pickup- pages from register are not coming to MPU)
			parametersMap.put(KIOSK.name(), MpuWebConstants.KIOSK_MPU1);
		} else {
			parametersMap.put(KIOSK.name(), mpuActivityDTO.getKiosk());
		}
		parametersMap.put(ORIGINAL_SALESCHECK.name(), mpuActivityDTO.getOriginalSalescheck());
		parametersMap.put(SEARCH_VALUE.name(), mpuActivityDTO.getSearchValue());
		parametersMap.put(TYPE.name(), mpuActivityDTO.getRequestType());
		parametersMap.put(CARD_SWIPED.name(), mpuActivityDTO.isCardSwiped());
		parametersMap.put(CUSTOMER_NAME.name(), mpuActivityDTO.getCustomerName());
		parametersMap.put(SALESCHECK.name(), mpuActivityDTO.getSalesCheck());
		parametersMap.put(RETURNAUTHCODE.name(), mpuActivityDTO.getReturnAuthCode());
		parametersMap.put(ORIGINALJSON.name(), mpuActivityDTO.getOriginalJson());
		if(null != mpuActivityDTO.getPickup_source()) {
			parametersMap.put(PICKUP_SOURCE.name(), mpuActivityDTO.getPickup_source());
		} else if (null != mpuActivityDTO.getRequestType() 
				&& MpuWebConstants.MPU_PAGE_REGISTER.equalsIgnoreCase(mpuActivityDTO.getRequestType())) {
			//Added for JIRA-25084 (platform pickup- pages from register are not coming to MPU)
			parametersMap.put(PICKUP_SOURCE.name(), MpuWebConstants.ORDER_ADAPTOR);
		} else{
			parametersMap.put(PICKUP_SOURCE.name(), MpuWebConstants.KIOSK);
		}
		parametersMap.put(SC_SCAN.name(), mpuActivityDTO.getSc_scan());
		
		return parametersMap;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#getTotalTenderReturnsByAssociate(java.lang.String)
	 */
	public List<Map<String, Object>> getTotalTenderReturnsByAssociate(String reportDate) throws DJException {
		logger.info("Start getTotalTenderReturnsByAssociate() for date:", reportDate);
		String sql = PropertyUtils.getProperty("select.totalTenderReturnsByAssociate");
		logger.info("End getTotalTenderReturnsByAssociate() for date:", reportDate);
		return getQueryResult(reportDate, sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#getTotalExchanges(java.lang.String)
	 */
	public List<Map<String, Object>> getTotalExchanges(String reportDate) throws DJException {
		logger.info("Start getTotalExchanges() for date:", reportDate);
		String sql = PropertyUtils.getProperty("select.totalExchanges");
		logger.info("End getTotalExchanges() for date:", reportDate);
		return getQueryResult(reportDate, sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#getTotalExchangeCouponsSummary(java.lang.String)
	 */
	public List<Map<String, Object>> getTotalExchangeCouponsSummary(String reportDate) throws DJException {
		logger.info("Start getTotalExchangeCouponsSummary() for date:", reportDate);
		String sql = PropertyUtils.getProperty("select.totalExchangeCouponsSummary");
		logger.info("Start getTotalExchangeCouponsSummary() for date:", reportDate);
		return getQueryResult(reportDate, sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#getTotalTenderReturnsByStore(java.lang.String)
	 */
	public List<Map<String, Object>> getTotalTenderReturnsByStore(String reportDate) throws DJException {
		logger.info("Start getTotalTenderReturnsByStore() for date:", reportDate);
		String sql = PropertyUtils.getProperty("select.totalTenderReturnsByStore");
		logger.info("End getTotalTenderReturnsByStore() for date:", reportDate);
		return getQueryResult(reportDate, sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#getTotalTenderStoreReturnsCancelled(java.lang.String)
	 */
	public List<Map<String, Object>> getTotalTenderStoreReturnsCancelled(String reportDate) throws DJException {
		logger.info("Start getTotalTenderStoreReturnsCancelled() for date:", reportDate);
		String sql = PropertyUtils.getProperty("select.totalTenderStoreReturnsCancelled");
		logger.info("End getTotalTenderStoreReturnsCancelled() for date:", reportDate);
		return getQueryResult(reportDate, sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#getAllCouponsForPickup(java.lang.String)
	 */
	public List<Map<String, Object>> getAllCouponsForPickup(String reportDate) throws DJException {
		logger.info("Start getAllCouponsForPickup() for date:", reportDate);
		String sql = PropertyUtils.getProperty("select.couponsForPickup");
		logger.info("End getAllCouponsForPickup() for date:", reportDate);
		return getQueryResult(reportDate, sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#getAllPickUpOrders(java.lang.String)
	 */
	public List<Map<String, Object>> getAllPickUpOrders(String reportDate) throws DJException {
		logger.info("Start getAllPickUpOrders() for date:", reportDate);
		String sql = PropertyUtils.getProperty("select.pickUpOrders");			
		logger.info("End getAllPickUpOrders() for date:", reportDate);
		return getQueryResult(reportDate, sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO#getAllHelpRequests(java.lang.String)
	 */
	public List<Map<String, Object>> getAllHelpRequests(String reportDate) throws DJException {
		logger.info("Start getAllHelpRequests() for date:", reportDate);
		String sql = PropertyUtils.getProperty("select.helpRequests");
		logger.info("End getAllHelpRequests() for date:", reportDate);
		return getQueryResult(reportDate, sql);
	}
	

	/**
	 * This method returns the result according to passed query from all schema
	 * @param reportDate String
	 * @param sql String
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> getQueryResult(String reportDate, String sql) {
		logger.info("Start of getQueryResult() SQL:======= ", sql);
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(REPORT_START_DATE, reportDate + REPORT_START_TIME);
		parameterMap.put(REPORT_END_DATE, reportDate + REPORT_END_TIME);
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		List<NamedParameterJdbcTemplate> namedParameterJdbcTemplates = getNamedParameterJdbcTemplates();
		
		for (NamedParameterJdbcTemplate namedParameterJdbcTemplate : namedParameterJdbcTemplates) {
			List<Map<String, Object>> tempResultList = null;
			try{
				tempResultList=(List<Map<String, Object>>) namedParameterJdbcTemplate.queryForList(sql, parameterMap);
				if(tempResultList!=null && tempResultList.size()>0)
				resultList.addAll(tempResultList);
			}catch(Exception exp){
				logger.error("Exception occured in getQueryResult() for:"+namedParameterJdbcTemplate+"  due to:===", exp);
			}
		}
		
		logger.info("End of getQueryResult() SQL:======= ", sql);
		return resultList;
	}
	
	public UserVO getAssociateInfo(String associateId)  {
		UserVO userVo = null;
		try{
		
		//String interfaceURL ="http://hfdvsbointjbos1.vm.itg.corp.us.shldcorp.com:8180//DEJInterfaces" ;
		
		String url = DJUtilities.concatString(userServiceUrl, associateId);
		logger.info("User service url is",url);
		
		ResponseEntity<?>  responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		userVo = (UserVO)DJUtilities.createStringToObject(responseEntity, new TypeReference<UserVO>() { });
		/*Object object = (Object) responseEntity.getBody().getResponseBody();
		logger.info("exiting WebQDAOImpl.getItemDetail");
		ResponseEntity<?> rspE = DJServiceLocator.get((interfaceURL+ "/v1/users/ldapID/{ldapId}", String.class,assignedUser);	
		userVo = (UserVO)DJUtilities.createStringToObject(rspE, new TypeReference<UserVO>() { });	*/	
		
		}
		catch(Exception exception)
		{logger.error("Exception occured while fetching user name",exception);
			
		}
		return userVo;
	}
	
}
