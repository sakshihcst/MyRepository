package com.searshc.mpuwebservice.dao.impl;
/*
 * All the constants in MPUWebServiceColumnConstants should be imported rather than 
 * importing individual constants
 */
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ACCOUNT_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ACKNOWLEDGEMENT;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ACTION_SEQ;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ACTIVITY_DESCRIPTION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ADDRESS1;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ADDRESS2;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ALT_AREA_CODE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ALT_PHONE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.AMOUNT;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.AREA_CODE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSIGNED_USER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.COMPLETEDTIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CUSTOMER_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DIV_NUM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.EMAIL;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ENTRY_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ESCALATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ESCALATION_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.EXPIRATION_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.EXPIRETIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.EXPIRETIMESTRING;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FIRST_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FROM_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FULL_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.INSTALLER_FLAG;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.IS_ACTIVE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_IDENTIFIER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_IMAGE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_SEQ;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.KIOSKLOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.KIOSK_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.KSN;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LANGUAGE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LAST_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LAYAWAY_FLAG;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.MODIFIED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.NOT_DELIVERED_QTY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORDER_SOURCE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORIGINALJSON;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PACKAGE_BIN;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PACKAGE_NUM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PARENT_SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PAYMENT_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PHONE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PHONE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_ASSIGNEE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_END_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_START_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PKG_NBR;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PLUS4;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PRIORITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PUBLISHERDEVICENAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RETURN_AUTH_CODE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RETURN_PARENT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SEARS_SALES_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SKU;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STOCK_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STOCK_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_FORMAT;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SYW_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SYW_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TARGETDEVICENAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TARGETROLE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TASKLOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TASKTYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.THUMBNAIL_DESC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TIMERECEIVED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TO_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UNSECURED_FLAG;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.VALUE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.VER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ZIPCODE;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.MDC;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sears.dej.interfaces.vo.ItemMasterResponse;
import com.sears.dej.interfaces.vo.ItemMeta;
import com.sears.dej.interfaces.vo.StockLocator;
import com.sears.dj.common.dao.DJRepository;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.exceptionhandler.FileExceptionHandler;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.bean.DDMeta;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.sears.dj.common.mpu.exception.DDRMetaException;
import com.sears.dj.common.print.vo.LockerKioskPrintVO;
import com.sears.dj.common.print.vo.MPUItemVO;
import com.sears.dj.common.print.vo.PrintKoiskVO;
import com.sears.dj.common.util.DJUtilities;
import com.sears.dj.common.ws.DJServiceLocator;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderAdaptorResponse;
import com.sears.mpu.backoffice.domain.StoreInfo;
import com.searshc.mpuwebservice.bean.ActivityDTO;
import com.searshc.mpuwebservice.bean.CustomerDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PackageDTO;
import com.searshc.mpuwebservice.bean.PaymentDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.TaskDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.mapper.ActionMetaExtractor;
import com.searshc.mpuwebservice.mapper.COMExcecptionMapper;
import com.searshc.mpuwebservice.mapper.IdentifierDTOMapper;
import com.searshc.mpuwebservice.mapper.IdentifierMetaExtractor;
import com.searshc.mpuwebservice.mapper.ItemDTOMapper;
import com.searshc.mpuwebservice.mapper.ItemStatusExtractor;
import com.searshc.mpuwebservice.mapper.OrderDTOMapper;
import com.searshc.mpuwebservice.mapper.OriginalOrderExtractor;
import com.searshc.mpuwebservice.mapper.PackageDTOMapper;
import com.searshc.mpuwebservice.mapper.PaymentDTOMapper;
import com.searshc.mpuwebservice.processor.ActiveUserProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;
import com.searshc.mpuwebservice.util.SHCDateUtils;
import com.searshc.mpuwebservice.vo.PrintServiceResponse;
import com.searshc.mpuwebservice.vo.RequestQueueMetaExtractor;
import com.searshc.mpuwebservice.vo.StoreFinderResponsev2;
import com.searshc.mpuwebservice.vo.StoreInfoVo;
import com.searshc.mpuwebservice.vo.StoreworkinhHours;
import com.searshc.mpuwebutil.util.CommonUtils;





@SuppressWarnings("unchecked")
@DJRepository("mpuWebServiceDAOImpl")
public class MPUWebServiceDAOImpl  extends DJMPUCommonDAO implements MPUWebServiceDAO    {
	
	@Autowired
	private RestTemplate restTemplate;
	private static transient DJLogger logger = DJLoggerFactory.getLogger(MPUWebServiceDAOImpl.class);
	private Map<String,Object> storeInfoMap = new HashMap<String,Object>();
	private Map<String,Object> actionMeta = new HashMap<String,Object>();
	private Map<String,Object> identifierMeta = new HashMap<String,Object>();
	private Map<String,String> requestQueueMeta = new HashMap<String, String>();
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateWFD;
	@Autowired
	private ActiveUserProcessor activeUserProcessor;
	
	/*@Autowired
	public void setDataSource(final DataSource dataSource) {
		//this.jdbcTemplateWorkFlowDS = new JdbcTemplate(dataSource);
		//this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.namedParameterJdbcTemplateWFD = new NamedParameterJdbcTemplate(dataSource);
	}*/
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getActionStatusMeta()
	 */
	public  Map<String,Object> getActionStatusMeta(String strNum)throws DJException{
		logger.info("Entering MPUWebServiceDAOImpl.getActionStatusMeta	strNum:","");
		logger.info("Entering MPUWebServiceDAOImpl.getActionStatusMeta	strNum:",strNum);
		if(actionMeta.isEmpty()){
		String sql= PropertyUtils.getProperty("select.from.action.meta");
		logger.info("sql for getActionStatusMeta== ",sql);
			//String sql ="SELECT concat(request_type,'-',current_status,'-',action_id) as KEY_ACT,next_status,activity,seq, action_id as action_type,mod_required FROM request_action_status_meta";
		actionMeta=(Map<String,Object>)query(strNum, sql,new HashMap<String,Object>(), new ActionMetaExtractor());
	     }
		logger.info("exiting" ,"MPUWebServiceDAOImpl.getActionStatusMeta actionMeta"+ actionMeta.toString());
		logger.info("exiting" ," MPUWebServiceDAOImpl.getActionStatusMeta");
		return actionMeta;
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebrOgetvice.dao.MPUWebServiceDAO#getStoreDetails(java.lang.String)
	 */
	public Map<String,Object> getStoreDetails(String storeNum) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.getStoreDetails	:","storeNum=="+storeNum);
		logger.debug("Entering MPUWebServiceDAOImpl.getStoreDetails	storeNum: ",storeNum);
		
			if(!storeInfoMap.containsKey(storeNum)){
				
				Map<String,Object> storeMap = new HashMap<String,Object>();	
				String escTime = getEscalationTime(storeNum).toString();
				storeMap.put(MpuWebConstants.ESCALATION_TIME, escTime);
				
				/*
				 * Check if the store number is of five digit
				 * if not apply left padding to it 
				 */
				if(null!=storeNum && storeNum.length()<5){
					/*
					 * Cannot import the package as spring stringutils is already imported and used
					 */
					storeNum = org.apache.commons.lang3.StringUtils.leftPad(storeNum, 5, '0');
				}
				
			    String url=PropertyUtils.getProperty("store_service_url");
			    logger.debug("getStoreDetails","Store URL:" + url);
		    	//response=restTemplate.exchange(url+"/"+storeNum, HttpMethod.GET, null, StoreFinderResponsev2.class);
			    try{
			    	/**
			    	 * For Test Only on Store close hours
			    	 */
			    	//storeNum = "01178";
				    ResponseEntity<StoreFinderResponsev2> response = (ResponseEntity<StoreFinderResponsev2>)DJServiceLocator.get(url+"/"+storeNum, StoreFinderResponsev2.class);
				    if(null != response &&  null != response.getBody()){
						List<StoreInfoVo> storeList = (List<StoreInfoVo>)response.getBody().getStoreDetailsList();
						if(null != storeList && !storeList.isEmpty()){
							StoreInfoVo storeInfo=storeList.get(0);
							if(storeInfo!= null){
								if(storeInfo.getStoreInfo()!= null){
									storeMap.put(MpuWebConstants.TIME_ZONE, storeInfo.getStoreInfo().getTimeZone());
									storeMap.put(MpuWebConstants.ZIP, storeInfo.getStoreInfo().getZip());
								}
								if(storeInfo.getStoreWorkingHrs()!= null){
									StoreworkinhHours workingHours = storeInfo.getStoreWorkingHrs();
									storeMap.put(MpuWebConstants.OPEN, MPUWebServiceUtil.getOpenMap(workingHours));
									storeMap.put(MpuWebConstants.CLOSE, MPUWebServiceUtil.getCloseMap(workingHours));
								}
							}
						storeInfoMap.put(storeNum, storeMap);	
						}
				     }
			    }catch(Exception e){
			    	/**
				      * If the store finder service fails 
				      */
			    	logger.error("getStoreDetails",e);    
				    	 String storeTimeZone = Calendar.getInstance().getTimeZone().getDisplayName();
				    	 logger.debug("=====storeTimeZone====",storeTimeZone);
				    	 storeMap.put(MpuWebConstants.TIME_ZONE, storeTimeZone);
				    	 storeInfoMap.put(storeNum, storeMap);
				    	 return (Map<String,Object>)storeInfoMap.get(storeNum);
				     
			    }
			}
			logger.debug("Exiting MPUWebServiceDAOImpl.getStoreDetails" ,(Map<String,Object>)storeInfoMap.get(storeNum));
			return (Map<String,Object>)storeInfoMap.get(storeNum);
		}
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#updateEscalation(java.lang.String,java.lang.Integer,java.lang.String)
	 */
	public int updateEscalation(String storeNumber,String rqd_id,int escalation,String time)throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.updateEscalation	rqd_id:"+ rqd_id, " escalation:" + escalation + " time:" + time);
			
			String sql=PropertyUtils.getProperty("update_escalation");
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put("escalation", new Integer(escalation).toString());
			paramMap.put("escalation_time", time);
			paramMap.put("rqd_id", rqd_id);
			
			logger.debug("Exiting" ,"MPUWebServiceDAOImpl.updateEscalation");
			return super.update(storeNumber,sql,paramMap);
		
	}
	
	//Added for Direct2MPU
		/* (non-Javadoc)
		 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getOpenRequestId(java.lang.String,java.lang.List<String>)
		 */
	public long getOpenRequestId(String requestNum, List<String> status) {

			long rqt_id=0;
		
		   	logger.info("Entering MPUWebServiceDAOImpl.getOpenRequestId	requestNum:",requestNum);
			Map<String, ? super Object> params = new HashMap<String, Object>();
			params.put(STATUS.name(),status);
			try{
				rqt_id = queryForInt(requestNum, PropertyUtils.getProperty("select_open_request"), params);
			}catch(Exception e){
				logger.error("Status of requets already changed","Its no longer available"+requestNum);
			}
			params.clear();
			return rqt_id;	
		
		
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getEscalationTime(java.lang.String)
	 */
	public Integer getEscalationTime(String strNum) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.getEscalationTime","");
		logger.debug("Entering MPUWebServiceDAOImpl.getEscalationTime	strNum:",strNum);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		params.put(STORE_NUMBER.name(),strNum);
		Integer escalationTime = 60;
		logger.debug("the query for getEscalationTime==", PropertyUtils.getProperty("select.from.esc.time"));
		try{
			escalationTime = queryForInt(strNum, PropertyUtils.getProperty("select.from.esc.time"), params);
		}catch(Exception e){
			//default escalation time is 60
			logger.error("getEscalationTime",e);
			return escalationTime;
		}
		logger.debug("Exit MPUWebServiceDAOImpl.getEscalationTime","");
		return escalationTime;
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getStockLocator(com.searshc.mpuwebservice.bean.RequestDTO, long)
	 */
	public List<ItemDTO> getStockLocator(RequestDTO requestDTO, long rqtId) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.getStockLocator	:","requestDTO"+requestDTO.toString()+": rqt_id: "+rqtId);
	   	logger.debug("Entering MPUWebServiceDAOImpl.getStockLocator	requestDTO:",requestDTO +": rqt_id: "+rqtId);
		List<ItemDTO> itemDTOs=new ArrayList<ItemDTO>();
		List<ItemDTO> itemsList = requestDTO.getItemList();
		if (null != itemsList && !itemsList.isEmpty()) {
			for (ItemDTO item : itemsList) {
				int maxStockQty = 0;
				String location=MpuWebConstants.NA;
				ResponseEntity<ItemMasterResponse> response = null;
				// Class<Object> responseType=new Object();

				String itemServiceurl = PropertyUtils.getProperty("stocklocator_url");
				
				String itemNum = item.getDivNum() + item.getItem()+ item.getSku();
				
				String itemNumType = MpuWebConstants.DIV_ITEM_SKU;
				
				if ((item.getDivNum() == null || "".equals(item
						.getDivNum())) && (item.getUpc() != null)) {
					itemNum = item.getUpc();
					itemNumType = MpuWebConstants.UPC;
				}

				String storeNumber = requestDTO.getOrder().getStoreNumber();

				if ("01333".equals(storeNumber)
						&& "qa2".equalsIgnoreCase(System.getProperty(MpuWebConstants.ENVIRONMENT))) {
					storeNumber = PropertyUtils.getProperty("qa_default_store");
				}else {
					storeNumber = requestDTO.getOrder().getStoreNumber();
				}
				String finalUrl = itemServiceurl + "/" + itemNum + "/"+ storeNumber + "?itemIdType=" + itemNumType+ "&fields=LocDetails";
				logger.debug("getStockLocator","Final URL:" + finalUrl);
				try{
				response = (ResponseEntity<ItemMasterResponse>)DJServiceLocator.get(finalUrl,ItemMasterResponse.class);
				//response = restTemplate.exchange(finalUrl, HttpMethod.GET,null, ItemMasterResponse.class);
						if(response.getStatusCode() == HttpStatus.OK){
								ItemMasterResponse itemDetails = response.getBody();
								if (null != itemDetails) {
									List<ItemMeta> itemList = itemDetails.getItemDetailsList();
									if (itemList != null & !itemList.isEmpty()) {
										ItemMeta meta = itemList.get(0);
										if (meta != null) {
											List<StockLocator> stockList = meta.getStockLocatorDetails();
											for (StockLocator stockLocation : stockList) {
												if (stockLocation.getQty() > maxStockQty) {
													maxStockQty = stockLocation.getQty();
													location = stockLocation.getLocationId();
													
												}
			
											}
			
										}
									}
								}
						}else{
							ItemMasterResponse itemDetails = response.getBody();
							itemDetails.getWsResponeMeta().getErrorCode();
							itemDetails.getWsResponeMeta().getErrorMessage();
							logger.error("getStockLocator","Could not get stock location from service.Exception during service call");
						}
			    }catch(DJException djException){
					//Consuming the exception so that it does not propagate and cause rollback.Flow should go without stock location
					logger.error("getStockLocator"+djException.getMessage(),"");
					throw FileExceptionHandler.logAndThrowDJException(djException, logger, "getStockLocator");
				}catch(Exception exception){
					//Consuming the exception so that it does not propagate and cause rollback.Flow should go without stock location
					logger.error("getStockLocator"+exception.getMessage(),"");
					throw FileExceptionHandler.logAndThrowDJException(exception, logger, "getStockLocator");
				}
						//This is done in order to map stock location with store and item. If not required, please make the necessary changes
				item.setStoreNumber(storeNumber);
				item.setStockLocation(location);
				itemDTOs.add(item);
						
						
			}
		}
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.getStockLocator");
		return itemDTOs;
	}
	
	/**
	 * This method is used for inserting data in REQUEST_QUEUE_TRANS Table
	 * 
	 * @param order
	 * @return
	 * @throws DJException
	 */
	public long createOrderDTO(OrderDTO order)
			throws DJException{
		logger.info("Entering MPUWebServiceDAOImpl.createOrderDTO","order"+order.toString());
		logger.debug("Entering MPUWebServiceDAOImpl.createOrderDTO	order:",order);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		
		Date currentTime = Calendar.getInstance().getTime();
	//	String createTime = DJUtilities.dateToString(currentTime, MpuWebConstants.DATE_FORMAT);
		params.put(REQUEST_TYPE.name(), order.getRequestType());
		params.put(STORE_NUMBER.name(), order.getStoreNumber());
		params.put(STORE_FORMAT.name(), order.getStoreFormat());
		params.put(CREATE_TIMESTAMP.name(),	order.getCreateTimestamp());
		params.put(UPDATE_TIMESTAMP.name(),	null);
		params.put(SALESCHECK.name(), order.getSalescheck());
		params.put(REQUEST_NUMBER.name(), order.getRequestNumber());
		String kioskName=order.getKioskName();
		
		if((kioskName!=null&&!"".equals(kioskName))){
			if(kioskName.length()>4){
				
				kioskName="MPU1";			}
		}
		params.put(KIOSK_NAME.name(),kioskName);
		params.put(PICKUP_STATUS.name(), order.getPickupStatus());
		params.put(PICKUP_START_TIME.name(), order.getPickupStartTime());
		params.put(PICKUP_END_TIME.name(), order.getPickupEndTime());
		params.put(PICKUP_ASSIGNEE.name(), order.getPickupAssignee());
		params.put(REQUEST_STATUS.name(), order.getRequestStatus());
		params.put(ORIGINALJSON.name(),order.getOriginalJson());
		params.put(PARENT_SALESCHECK.name(), order.getOriginalIdentifier());	
		params.put(RETURN_PARENT_ID.name(),order.getReturnParentId());	
		params.put(RETURN_AUTH_CODE.name(), order.getIdentifierType());
		if(order.isInstallerPickOrder()) {
			params.put(INSTALLER_FLAG.name(), "Y");
		} else {
			params.put(INSTALLER_FLAG.name(), "N");
		}
		if(order.isSecureIndicator()) {
			params.put(UNSECURED_FLAG.name(), "N");
		} else {
			params.put(UNSECURED_FLAG.name(), "Y");
		}
		
		if(null != order.getCreatedBy()) {
			
			params.put(CREATED_BY.name(), order.getCreatedBy());
			params.put(MODIFIED_BY.name(), order.getCreatedBy());
		
		} else {
			
			params.put(CREATED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);
			params.put(MODIFIED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);	
		}
		params.put(CUSTOMER_ID.name(),order.getCustomerId());
		if(MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(order.getOrderSource())){
			params.put(ORDER_SOURCE.name(),order.getOrderSource());
		}else{
			params.put(ORDER_SOURCE.name(),MpuWebConstants.ORDER_SOURCE_NPOS);
		}
		params.put(LAYAWAY_FLAG.name(),order.getLayaway());
		
		/**
		 * For saving the order_number in DB 
		 * @author nkhan6
		 */
		params.put("ORDER_ID",order.getOrderNumber());
		
		//String sql="INSERT INTO REQUEST_QUEUE_TRANS (REQUEST_TYPE, STORE_NUMBER, CREATE_TIMESTAMP, UPDATE_TIMESTAMP, SALESCHECK, REQUEST_NUMBER, KIOSK_NAME,  PICKUP_STATUS, PICKUP_START_TIME, PICKUP_END_TIME, PICKUP_ASSIGNEE, REQUEST_STATUS, ORIGINALJSON, CREATED_BY, MODIFIED_BY) VALUES (:REQUEST_TYPE, :STORE_NUMBER, :CREATE_TIMESTAMP, :UPDATE_TIMESTAMP, :SALESCHECK, :REQUEST_NUMBER, :KIOSK_NAME,  :PICKUP_STATUS, :PICKUP_START_TIME, :PICKUP_END_TIME, :PICKUP_ASSIGNEE, :REQUEST_STATUS, :ORIGINALJSON, :CREATED_BY, :MODIFIED_BY)";
		String sql = PropertyUtils.getProperty("insert.into.trans");
		DJUtilities.createSQLCommand(sql, params);
		
		logger.info("Exiting" ,"MPUWebServiceDAOImpl.createOrderDTO sql : " + DJUtilities.createSQLCommand(sql, params));
		logger.info("Exiting" ,"order.getStoreNumber() : " + order.getStoreNumber());
		
		return super.updateWithKeyHolder(order.getStoreNumber(), sql, params).longValue();
		
	}
	
	/**
	 * This method is used for inserting data in REQUEST_IDENTIFIER Table
	 * 
	 * @param customer
	 * @return
	 * @throws DJException
	 */
	public int createIdentifierDTO(CustomerDTO customer, long rqtId,List<PaymentDTO> paymentList,String salesCheck)
			throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.createIdentifierDTO	customer:",customer +": paymentList: "+paymentList +": salesCheck: "+salesCheck);
		logger.debug("Entering MPUWebServiceDAOImpl.createIdentifierDTO	customer:",customer +": paymentList: "+paymentList +": salesCheck: "+salesCheck);
		
		Map<String,Object> identifierMap = getIdentifierMeta(customer.getStoreNumber());
		List<Map<String, ? super Object>> params = new ArrayList<Map<String,? super Object>>();
		if(!StringUtils.isEmpty(customer.getFirstName())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(FIRST_NAME.name())),customer.getFirstName(),rqtId,customer.getStoreNumber()));
		}
		if(!StringUtils.isEmpty(customer.getLastName())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(LAST_NAME.name())),customer.getLastName(),rqtId,customer.getStoreNumber()));
		}
		if(!StringUtils.isEmpty(customer.getEmail())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(EMAIL.name())),customer.getEmail(),rqtId,customer.getStoreNumber()));
	    }
		if(!StringUtils.isEmpty(customer.getPhoneNumber())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(PHONE.name())),customer.getPhoneNumber(),rqtId,customer.getStoreNumber()));
			//params.add(getColumnDetails(String.valueOf(identifierMap.get(PHONE.name())),customer.getPhone(),rqtId,customer.getStoreNumber()));
	    }
		
		if(!StringUtils.isEmpty(customer.getSywNumber())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(SYW_NUMBER.name())),customer.getSywNumber(),rqtId,customer.getStoreNumber()));
	    }
		
		if(!StringUtils.isEmpty(customer.getSywStatus())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(SYW_STATUS.name())),customer.getSywStatus(),rqtId,customer.getStoreNumber()));
	    }
		
		if(!StringUtils.isEmpty(customer.getAddress1())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(ADDRESS1.name())),customer.getAddress1(),rqtId,customer.getStoreNumber()));
	    }
		
		if(!StringUtils.isEmpty(customer.getAddress2())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(ADDRESS2.name())),customer.getAddress2(),rqtId,customer.getStoreNumber()));
	    }
		
		if(!StringUtils.isEmpty(customer.getZipcode())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(ZIPCODE.name())),customer.getZipcode(),rqtId,customer.getStoreNumber()));
	    }
		
		if(!StringUtils.isEmpty(customer.getCustomerId())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(CUSTOMER_ID.name())),customer.getCustomerId(),rqtId,customer.getStoreNumber()));
	    }
		
		if(!StringUtils.isEmpty(salesCheck)){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(SALESCHECK.name())),salesCheck,rqtId,customer.getStoreNumber()));
	    }
		/*to enter the account number for the customer
		 * Multiple records should be there for multiple payment methods*/
		if(null!=paymentList && !paymentList.isEmpty()){
		for(PaymentDTO payment:paymentList){
			if(!StringUtils.isEmpty(payment.getAccountNumber())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(ACCOUNT_NUMBER.name())),payment.getAccountNumber(),rqtId,customer.getStoreNumber()));
			
			}
		}
		}
		
		/*
		 * Adding additional customer info for ticket printing
		 * By Nasir
		 */
		
		if(!StringUtils.isEmpty(customer.getAreaCode())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(AREA_CODE.name())),customer.getAreaCode(),rqtId,customer.getStoreNumber()));
	    }
		if(!StringUtils.isEmpty(customer.getAltAreaCode())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(ALT_AREA_CODE.name())),customer.getAltAreaCode(),rqtId,customer.getStoreNumber()));
	    }
		if(!StringUtils.isEmpty(customer.getPhoneNumber())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(PHONE_NUMBER.name())),customer.getPhoneNumber(),rqtId,customer.getStoreNumber()));
	    }
		if(!StringUtils.isEmpty(customer.getAltPhoneNumber())){
			params.add(getColumnDetails(String.valueOf(identifierMap.get(ALT_PHONE_NUMBER.name())),customer.getAltPhoneNumber(),rqtId,customer.getStoreNumber()));
	    }
		
		//String sql = "INSERT INTO REQUEST_IDENTIFIER(RQT_ID,VALUE,TYPE,STORE_NUMBER) VALUES (:RQT_ID,:VALUE,:TYPE,:STORE_NUMBER)";
		Map<String, ? super Object> paramsTemp[] = new HashMap[0];
		String sql = PropertyUtils.getProperty("insert.into.identifier");
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.createIdentifierDTO");
		return super.batchUpdate(customer.getStoreNumber(), sql,params.toArray(paramsTemp)).length;
		
	}
	
	/** This API is used for populating row in Identifier table.
	 * @param columnIdentifier
	 * @param coulmnValue
	 * @param rqtId
	 * @param storeNumber
	 * @return
	 */
	private Map<String, Object> getColumnDetails(String columnIdentifier, Object coulmnValue,long rqtId,String storeNumber){
		logger.debug("Entering MPUWebServiceDAOImpl.getColumnDetails	columnIdentifier:",columnIdentifier +": coulmnValue: "+coulmnValue  +": rqtId: "+rqtId  +": storeNumber: "+storeNumber);
		logger.debug("Entering MPUWebServiceDAOImpl.getColumnDetails	columnIdentifier:",columnIdentifier +": coulmnValue: "+coulmnValue  +": rqtId: "+rqtId  +": storeNumber: "+storeNumber);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(RQT_ID.name(), rqtId);
		param.put(VALUE.name(), coulmnValue);
		param.put(TYPE.name(), columnIdentifier);
		param.put(STORE_NUMBER.name(), storeNumber);
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.getColumnDetails");
		return param;
	}
	
	/**
	 * This method is used for inserting data in REQUEST_QUEUE_DETAILS Table
	 * 
	 * @param itemList
	 * @return
	 * @throws DJException
	 */
	public int createItemList(ItemDTO item, long rqtId,String expireTime)
			throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.createItemList","");
		logger.debug("Entering MPUWebServiceDAOImpl.createItemList	item:",item +": rqtId: "+rqtId +": expireTime: "+expireTime);
		Map<String, ? super Object> params = new HashMap<String, Object>();
		
		/* to get the stock location and max qty for the given item.*/
		
		getItemStockLocAndQty(item);   //Commenting the call for the time being as NPOS is sending these data.Will open if required .
		
	    
		params.put(RQT_ID.name(), rqtId);
		params.put(ASSIGNED_USER.name(), item.getAssignedUser());
		params.put(DIV_NUM.name(), item.getDivNum());
		params.put(ITEM.name(), item.getItem());
		params.put(SKU.name(), item.getSku());
		params.put(PLUS4.name(), item.getPlus4());
		params.put(UPC.name(), item.getUpc());
		params.put(KSN.name(), item.getKsn());
		params.put(QTY.name(), item.getQty());
		params.put(ITEM_STATUS.name(), item.getItemStatus());
		params.put(FROM_LOCATION.name(), item.getStockLocation());
		params.put(TO_LOCATION.name(), item.getToLocation());
		params.put(ITEM_IMAGE.name(), item.getItemImage());
		params.put(SALESCHECK.name(), item.getSalescheck());
		params.put(STORE_NUMBER.name(), item.getStoreNumber());
		params.put(STOCK_LOCATION.name(),item.getStockLocation());
		params.put(STOCK_QUANTITY.name(),item.getItemQuantityAvailable());
		params.put(THUMBNAIL_DESC.name(),item.getThumbnailDesc());
		params.put(FULL_NAME.name(), item.getFullName());
		params.put(REQUEST_NUMBER.name(),item.getRequestNumber());
		params.put(ESCALATION.name(), item.getEscalation());
		
		if(null != expireTime && !("0".equalsIgnoreCase(expireTime))){
		    Calendar calendar = Calendar.getInstance();
		    long milliSeconds= Long.parseLong(expireTime);
		    calendar.setTimeInMillis(milliSeconds);
		    String escalationTime = DJUtilities.dateToString(calendar.getTime(),MpuWebConstants.DATE_FORMAT);
		    params.put(ESCALATION_TIME.name(),escalationTime);
		}else{
		    Date currentTime=Calendar.getInstance().getTime();
		    String escalationTime = DJUtilities.dateToString(currentTime,MpuWebConstants.DATE_FORMAT);
			params.put(ESCALATION_TIME.name(),escalationTime);
		}
		params.put(ITEM_SEQ.name(),item.getItemSeq());
		if(null != item.getCreatedBy()){
			params.put(CREATED_BY.name(), item.getCreatedBy());
			params.put(MODIFIED_BY.name(), item.getCreatedBy());
		}else{
			params.put(CREATED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);
			params.put(MODIFIED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);
		}
		params.put(ITEM_ID.name(), item.getItemId());
		//String sql = "INSERT INTO REQUEST_QUEUE_DETAILS (RQT_ID, ASSIGNED_USER, DIV_NUM, ITEM, SKU,  PLUS4, UPC, KSN, QTY, ITEM_STATUS, FROM_LOCATION, TO_LOCATION, ITEM_IMAGE, STORE_NUMBER, STOCK_LOCATION, STOCK_QUANTITY, THUMBNAIL_DESC,  FULL_NAME, REQUEST_NUMBER, ESCALATION, ESCALATION_TIME, ITEM_SEQ, CREATED_BY, SALESCHECK)  VALUES (:RQT_ID, :ASSIGNED_USER, :DIV_NUM, :ITEM, :SKU,  :PLUS4, :UPC, :KSN, :QTY, :ITEM_STATUS, :FROM_LOCATION, :TO_LOCATION, :ITEM_IMAGE, :STORE_NUMBER, :STOCK_LOCATION, :STOCK_QUANTITY, :THUMBNAIL_DESC,  :FULL_NAME, :REQUEST_NUMBER, :ESCALATION, :ESCALATION_TIME, :ITEM_SEQ, :CREATED_BY,:SALESCHECK)";
		String sql = PropertyUtils.getProperty("insert.into.item");
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.createItemList");
		return super.updateWithKeyHolder(item.getStoreNumber(), sql, params).intValue();
		
	}
	
	/**
	 * Developed by - ramesh
	 * 
	 * @param paymentList
	 * @return
	 * @throws DJException
	 */
	public int createPaymentList(PaymentDTO payment, long rqtId)
			throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.createPaymentList	payment:",payment.toString());
		logger.debug("Entering MPUWebServiceDAOImpl.createPaymentList	payment:",payment);
		Map<String, ? super Object> params = new HashMap<String, Object>();
		
		params.put(RQT_ID.name(), rqtId);
		params.put(TYPE.name(), payment.getType());
		params.put(ACCOUNT_NUMBER.name(), payment.getAccountNumber());
		params.put(AMOUNT.name(), payment.getAmount());
		params.put(STATUS.name(), payment.getStatus());
		params.put(PAYMENT_DATE.name(), payment.getPaymentDate());//TODO
		params.put(EXPIRATION_DATE.name(), payment.getExpirationDate());//TODO
		params.put(STORE_NUMBER.name(), payment.getStoreNumber());
		if(null != payment.getCreatedBy()){
			params.put(CREATED_BY.name(), payment.getCreatedBy());
			params.put(MODIFIED_BY.name(),payment.getCreatedBy());
		}else{
			params.put(CREATED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);
			params.put(MODIFIED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);	
		}
		//String sql = "INSERT INTO REQUEST_QUEUE_PAYMENT (RQT_ID, TYPE, ACCOUNT_NUMBER, AMOUNT, STATUS, PAYMENT_DATE, EXPIRATION_DATE, STORE_NUMBER, CREATED_BY, MODIFIED_BY) VALUES (:RQT_ID, :TYPE, :ACCOUNT_NUMBER, :AMOUNT, :STATUS, :PAYMENT_DATE, :EXPIRATION_DATE, :STORE_NUMBER, :CREATED_BY, :MODIFIED_BY)";
		String sql = PropertyUtils.getProperty("insert.into.payment");
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.createPaymentList");
		return super.updateWithKeyHolder(payment.getStoreNumber(),sql, params).intValue();
		
	}	
	


	
	
	
	/**
	 * Developed by - ramesh
	 * 
	 * @param task
	 * @return
	 * @throws DJException
	 */
	public int createTaskDTO(TaskDTO task, String taskType)
			throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.createTaskDTO	task:",task.toString() +": taskType: "+taskType);
		logger.debug("Entering MPUWebServiceDAOImpl.createTaskDTO	task:",task +": taskType: "+taskType);
		Map<String, ? super Object> params = new HashMap<String, Object>();
		
		params.put(TASKTYPE.name(), taskType);
		params.put(ACKNOWLEDGEMENT.name(), task.getAcknowledgeMent());
		params.put(TIMERECEIVED.name(), task.getTimeReceived());
		params.put(EXPIRETIMESTRING.name(), task.getExpireTimeString());
		/*params.put(COUPONISSUED.name(), task.getCouponIssued());*/
		params.put(PUBLISHERDEVICENAME.name(), task.getPublisherDeviceName());
		params.put(TARGETDEVICENAME.name(), task.getTargetDeviceName());
		params.put(EXPIRETIME.name(), task.getExpireTime());
		params.put(TARGETROLE.name(), task.getTargetRole());
		params.put(TASKLOCATION.name(), task.getTaskLocation());
		params.put(KIOSKLOCATION.name(), task.getKioskLocation());
		/*params.put(PAGINGFAILED.name(), task.getPagingFailed());*/
		/*params.put(PRINTINGFAILED.name(), task.getPrintingFailed());*/
		/*params.put(NPOSUNAVAILABLE.name(), task.getNposUnavailable());*/
		params.put(COMPLETEDTIME.name(), task.getCompletedTime());
		params.put(LANGUAGE.name(), task.getLanguage());
		params.put(PRIORITY.name(), task.getPriority());
		params.put(STATE.name(), task.getState());
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.createTaskDTO");
		logger.debug("the query for createTaskDTO===", PropertyUtils.getProperty("insert.into.task"));
		return super.updateWithKeyHolder(task.getTaskId(), PropertyUtils.getProperty("insert.into.task"), params).intValue();
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#createActivity(com.searshc.mpuwebservice.bean.ActivityDTO, int, int)
	 */
	public int createActivity(ActivityDTO activity, long rqtId, long rqdId)
			throws DJException{
		logger.info("createActivity", "Inside createActivity");
		Map<String, ? super Object> params = new HashMap<String, Object>();
		logger.info("Entering MPUWebServiceDAOImpl.createActivity	activity:",activity +": rqtId: "+rqtId+": rqdId: "+rqdId);
		params.put(ACTION_SEQ.name(), activity.getActionSeq());
		params.put(ACTIVITY_DESCRIPTION.name(), activity.getActivityDescription());
		params.put(ASSIGNED_USER.name(), activity.getAssignedUser());
		
		
		Date currentTime=Calendar.getInstance().getTime();
	    String createTime = DJUtilities.dateToString(currentTime,MpuWebConstants.DATE_FORMAT);
		params.put(CREATE_TIMESTAMP.name(),	createTime);//TODO
		
		if(null != activity.getCreatedBy()){
			params.put(CREATED_BY.name(), activity.getCreatedBy());
			params.put(MODIFIED_BY.name(),activity.getCreatedBy());
		}else{
			params.put(CREATED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);
			params.put(MODIFIED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);	
		}
		params.put(ENTRY_TYPE.name(), activity.getType());
		params.put(FROM_LOCATION.name(), activity.getFromLocation());
		params.put(RQD_ID.name(), rqdId);
		params.put(RQT_ID.name(), rqtId);
		params.put(STORE.name(), activity.getStore());
		if(StringUtils.hasText(activity.getToLocation())){
			params.put(TO_LOCATION.name(), activity.getToLocation());
		}else{
			params.put(TO_LOCATION.name(), null); //suggested by sandeep
		}
		params.put(TYPE.name(), activity.getType());
	
		//String sql = "INSERT INTO REQUEST_ACTIVITY (RQT_ID,RQD_ID,CREATE_TIMESTAMP,TYPE,ASSIGNED_USER,STORE_NUMBER,FROM_LOCATION,TO_LOCATION,CREATED_BY) VALUES(:RQT_ID,:RQD_ID,:CREATE_TIMESTAMP,:TYPE,:ASSIGNED_USER,:STORE,:FROM_LOCATION,:TO_LOCATION,:CREATED_BY)";
		String sql = PropertyUtils.getProperty("insert.into.activity");
		logger.info("qry", DJUtilities.createSQLCommand(sql, params));
		logger.info("createActivity", "exit createActivity");
		return super.updateWithKeyHolder(activity.getStore(),
				 sql, params).intValue();
	}
	
	
	
	
	
	/**
	 * @param item
	 * @throws DJException
	 */
	public void getItemStockLocAndQty(ItemDTO item) {
		logger.debug("Entering MPUWebServiceDAOImpl.getItemStockLocAndQty	item:",item.toString());
		logger.debug("Entering MPUWebServiceDAOImpl.getItemStockLocAndQty	item:",item);
		ResponseEntity<ItemMasterResponse> response = null;
		try{
		
		String stockQty = "";
			String location=MpuWebConstants.NA;
			
			// Class<Object> responseType=new Object();
	
			String itemServiceurl = PropertyUtils.getProperty("stocklocator_url");
			
		
		String itemNum = item.getDivNum() + item.getItem()+ item.getSku();
		
		String itemNumType = MpuWebConstants.DIV_ITEM_SKU;
		
		if ((item.getDivNum() == null || "".equals(item
				.getDivNum())) && (item.getUpc() != null)) {
			itemNum = item.getUpc();
			itemNumType = MpuWebConstants.UPC;
		}

		String storeNumber = DJUtilities.leftPadding(item.getStoreNumber(),5,'0') ;
		logger.info("==getItemStockLocAndQty=storeNumber== ", storeNumber);

		/**
		 * Below if block is for dev and qa testing purpose only 
		 */
		if (("01333".equals(storeNumber) || "02990".equals(storeNumber) || "01430".equals(storeNumber) || "01148".equals(storeNumber))
				&& ("qa".equalsIgnoreCase(System.getProperty(MpuWebConstants.ENVIRONMENT))
						|| "dev".equalsIgnoreCase(System.getProperty(MpuWebConstants.ENVIRONMENT)))){
			storeNumber = PropertyUtils.getProperty("qa_default_store");
		}
		String finalUrl = itemServiceurl + "/" + itemNum + "/"+ storeNumber + "?itemIdType=" + itemNumType+ "&fields=LocDetails";
		logger.info("getStockLocator","Final URL:" + finalUrl);
		try{
		response = (ResponseEntity<ItemMasterResponse>)DJServiceLocator.get(finalUrl,ItemMasterResponse.class);
		//response = restTemplate.exchange(finalUrl, HttpMethod.GET,null, ItemMasterResponse.class);
				if(response.getStatusCode() == HttpStatus.OK){
						ItemMasterResponse itemDetails = response.getBody();
						if (null != itemDetails) {
							List<ItemMeta> itemList = itemDetails.getItemDetailsList();
							if (itemList != null & !itemList.isEmpty()) {
								ItemMeta meta = itemList.get(0);
								if (meta != null) {
									// Multi Location Stock Changes Start
									List<StockLocator> stockList = meta.getStockLocatorDetails();
									int index=0;
									int countSum = 0;
									location = "";
									//commented because of compilation error -
									//Collections.sort(stockList);
									for (StockLocator stockLocation : stockList) {
											if((countSum=countSum+stockLocation.getQty()) < Integer.parseInt(item.getQty())){
												if(stockList.size() != 1)
													index++;
											
											}
		
										}
										if(countSum < Integer.parseInt(item.getQty())){
											if(stockList.size() != 1)
												index--;
										
										}
										countSum = 0;
										int stockNum = 0;
										for (int i=0;i<=index;i++) {
											countSum = countSum + stockList.get(i).getQty();
											if(countSum <= Integer.parseInt(item.getQty())){
												stockQty = stockQty + stockList.get(i).getQty() + ",";
											}else{
												if(i == 0){
													if(Integer.parseInt(item.getQty()) > stockList.get(0).getQty()){
														stockQty = stockQty + (Integer.parseInt(item.getQty()) - stockList.get(0).getQty()) + ",";
													}
													else{
														stockQty = stockQty + (Integer.parseInt(item.getQty())) + ",";
													}
												}else{
													StringTokenizer stockStr = new StringTokenizer(stockQty,",");
													while (stockStr.hasMoreElements()) {
														stockNum = stockNum +  Integer.parseInt((String)stockStr.nextElement());
													}
													stockQty = stockQty + (Integer.parseInt(item.getQty()) - stockNum) + ",";
												}
												
											}
											location = location + stockList.get(i).getLocationId() + ",";
										}
										location = location.substring(0, location.length()-1);
										stockQty = stockQty.substring(0, stockQty.length()-1);
										// Multi Location Stock Changes Ends
									}
								}
							}
				}else{
					ItemMasterResponse itemDetails = response.getBody();
					itemDetails.getWsResponeMeta().getErrorCode();
					itemDetails.getWsResponeMeta().getErrorMessage();
					throw new DJException(itemDetails.getWsResponeMeta().getErrorCode(), itemDetails.getWsResponeMeta().getErrorMessage());
					//logger.error("getStockLocator","Could not get stock location from service.Exception during service call");
				}
				//This is done in order to map stock location with store and item. If not required, please make the necessary changes
					item.setStockQuantity(stockQty);
					item.setStockLocation(location);
				}catch(DJException djException){
					//Consuming the exception so that it does not propagate and cause rollback.Flow should go without stock location
					logger.error("getStockLocator",djException);
					//throw FileExceptionHandler.logAndThrowDJException(djException, logger, "getStockLocator");
				}catch(Exception exception){
					//Consuming the exception so that it does not propagate and cause rollback.Flow should go without stock location
					logger.error("getStockLocator",exception);
				//	throw FileExceptionHandler.logAndThrowDJException(exception, logger, "getStockLocator");
				}
				//This is done in order to map stock location with store and item. If not required, please make the necessary changes
			
		}
		catch(Exception e){
			logger.error("Error in getStockLocator :"+response +" : ", e);
		}
				logger.debug("Exit MPUWebServiceDAOImpl.getItemStockLocAndQty","");
    }
	
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getRequestDetailId(java.lang.String, com.searshc.mpuwebservice.bean.ActionDTO, java.util.List)
	 */
	public int getRequestDetailId(String requestNum,ItemDTO action,List<String> status) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.getRequestDetailId	requestNum:",requestNum +": action: "+action +": status: "+status);
		logger.debug("Entering MPUWebServiceDAOImpl.getRequestDetailId	requestNum:",requestNum +": action: "+action +": status: "+status);
		Map<String,Object> parameterMap=new HashMap<String,Object>();
		String selectSql = "";
		//String divNum="",item="",sku="",ksn="",upc="",itemNumber="";
		int rqdId;
		parameterMap=new HashMap<String,Object>();
		//itemNumber = action.getItemNumber();
		selectSql = PropertyUtils.getProperty("select.item.detail.id1");
		parameterMap.put("requestNum",action.getRequestNumber());
		parameterMap.put("openStatus",status);
		parameterMap.put("storeNumber",action.getStoreNumber());
		
		if(action.getDivNum()!=null&&action.getItem()!=null&&action.getSku()!=null){
				
			parameterMap.put("divNum",action.getDivNum());
			parameterMap.put("itemNum",action.getItem());
			parameterMap.put("sku",action.getSku());
			selectSql = selectSql + MpuWebConstants.SPACE + PropertyUtils.getProperty("select.item.detail.id2");
			
		}else if(action.getUpc()!=null){
			selectSql = selectSql + MpuWebConstants.SPACE + PropertyUtils.getProperty("select.item.detail.id4");
			parameterMap.put("upc", action.getUpc());
		}
		else if(action.getKsn()!=null){
			selectSql = selectSql + MpuWebConstants.SPACE + PropertyUtils.getProperty("select.item.detail.id3");
			parameterMap.put("ksn", action.getKsn());				
		}
		
		if(action.getItemSeq() != null && !"".equals(action.getItemSeq())){
			selectSql = selectSql + MpuWebConstants.SPACE + PropertyUtils.getProperty("select.item.detail.id5");
			parameterMap.put("item_seq", action.getItemSeq());
		}
		logger.debug("the query for getRequestDetailId== ", selectSql);
		rqdId = queryForInt(action.getStoreNumber(),selectSql,parameterMap);
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.getRequestDetailId");
			return rqdId;
	}
	
	
	public int updateItemDetails(String requestNum,ItemDTO action,String finalStatus) throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.updateItemDetails","");
	 	logger.debug("Entering MPUWebServiceDAOImpl.updateItemDetails	requestNum:",requestNum 
	 			+": action: "+action 	+": finalStatus: "+finalStatus );
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		if (null!=action.getRequestType() && MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(action.getRequestType())){
			if(null!=finalStatus){
				if(MpuWebConstants.AVAILABLE.equalsIgnoreCase(finalStatus)){
					action.setFromLocation("STK");
					action.setToLocation("RACK");
				}else if(MpuWebConstants.NOTAVAILABLE.equalsIgnoreCase(finalStatus)){
					action.setFromLocation(null);
					action.setToLocation(null);
				}else if(MpuWebConstants.CANCELLED.equalsIgnoreCase(finalStatus) && MpuWebConstants.RESTOCK_PENDING.equalsIgnoreCase(action.getItemStatus())){
					action.setFromLocation("RACK");
					action.setToLocation("STK");
				}
			}
		}
		String updateSql = "";
		//Changes to prevent current data overwritten by null		
		updateSql = PropertyUtils.getProperty("update.item.details.mpu1");
		//query changed
		if(action.getAssignedUser()!=null&&!"".equals(action.getAssignedUser()) || action.getItemStatus().equalsIgnoreCase(MpuWebConstants.MOD_VERIFY)){
			updateSql=updateSql+",rqd.assigned_user = :ASSIGNED_USER";
			parameterMap.put(ASSIGNED_USER.name(),action.getAssignedUser());}
		if(action.getFromLocation()!=null&&!"".equals(action.getFromLocation())){
			updateSql=updateSql+",rqd.from_location = :FROM_LOCATION";
			parameterMap.put(FROM_LOCATION.name(),action.getFromLocation());}
		
		if(action.getToLocation()!=null&&!"".equals(action.getToLocation())){
			updateSql=updateSql+",rqd.to_location = :TO_LOCATION";
			parameterMap.put(TO_LOCATION.name(),action.getToLocation());}
		
		if(action.getItemCreatedDate()!=null&&!"".equals(action.getItemCreatedDate())){
			updateSql=updateSql+",rqd.create_time = :CREATE_TIME";
			parameterMap.put(CREATE_TIME.name(),action.getItemCreatedDate());}
		
		//changes end
		if(action.getExpireTime() != null && ! "".equals(action.getExpireTime())){
			updateSql = updateSql + MpuWebConstants.SPACE + PropertyUtils.getProperty("update.item.details.mpu8");
			parameterMap.put(ESCALATION_TIME.name(), action.getEscalationTime());
		}
		if(MpuWebConstants.LAYAWAYF.equalsIgnoreCase(action.getRequestType())){
			updateSql = updateSql + MpuWebConstants.SPACE + PropertyUtils.getProperty("update.item.details.mpu4");
		}else{
			updateSql = updateSql + MpuWebConstants.SPACE + PropertyUtils.getProperty("update.item.details.mpu3");
		}
		//updateSql = updateSql + MpuWebConstants.space + PropertyUtils.getProperty("update_item_details_mpu3");
		if (null!=action.getRequestType()&& !MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(action.getRequestType()) && action.getVersion() != null) {
			updateSql = updateSql + " and rqd.ver=:VER";
		}
		//parameterMap.put(TO_LOCATION.name(),action.getToLocation());
		parameterMap.put(QTY.name(),action.getQty());
		parameterMap.put(STATUS.name(),finalStatus);
		//parameterMap.put(ASSIGNED_USER.name(),action.getAssignedUser());
		//parameterMap.put(FROM_LOCATION.name(),action.getFromLocation());
		parameterMap.put(STORE.name(),action.getStoreNumber());
		parameterMap.put("currentStatus",action.getItemStatus());
		parameterMap.put(RQD_ID.name(),action.getRqdId());
		parameterMap.put(RQT_ID.name(), action.getRqtId());
		parameterMap.put(NOT_DELIVERED_QTY.name(), action.getItemQuantityNotDelivered());
		
		if (action.getVersion() != null) {
			parameterMap.put(VER.name(), action.getVersion());
		}
/*		if(ItemNumValidator.isDivItemSku(itemNumber)){
			divNum = action.getItemNumber().substring(0, 3);
			item = action.getItemNumber().substring(3, 8);
			sku = action.getItemNumber().substring(8, 11);		
			parameterMap.put("divNum",divNum);
			parameterMap.put("itemNum",item);
			parameterMap.put("sku",sku);
			updateSql = updateSql + MpuWebConstants.space + PropertyUtils.getProperty("update_item_details_mpu4");
		}else if(ItemNumValidator.isKSN(itemNumber)){
			updateSql = updateSql + MpuWebConstants.space + PropertyUtils.getProperty("update_item_details_mpu5");
			parameterMap.put("ksn", action.getItemNumber());				
		}else if(ItemNumValidator.isUpc(itemNumber)){
			updateSql = updateSql + MpuWebConstants.space + PropertyUtils.getProperty("update_item_details_mpu6");
			parameterMap.put("upc", action.getItemNumber());
		}*/	
/*		if(action.getItemSeqNum() != null && !action.getItemSeqNum().equals("")){
			updateSql = updateSql + MpuWebConstants.space + PropertyUtils.getProperty("update_item_details_mpu7");
			parameterMap.put("item_seq", action.getItemSeqNum());
		}
		
*/		logger.debug("Exit MPUWebServiceDAOImpl.updateItemDetails",""); 
		return update(action.getStoreNumber(), updateSql,parameterMap);
	}
	
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#checkIsActiveRequestExisting(java.lang.String, java.lang.String)
	 */
	
	//TODO
	
	public long checkIsActiveRequestExisting(String storeNumber, String listNumber) throws DJException{
		logger.info("Entering MPUWebServiceDAOImpl.checkIsActiveRequestExisting	storeNumber:",storeNumber 	+": listNumber: "+listNumber);
			String sql = PropertyUtils.getProperty("check.duplicate.request");
			Map<String,String> parameterMap=new HashMap<String,String>();
			parameterMap.put(MpuWebConstants.REQUEST_NUMBER,listNumber);
			parameterMap.put(MpuWebConstants.STORENUMBER,storeNumber);
			long rqtId=0;
			try{
				logger.debug("the query for checkIsActiveRequestExisting==", sql);
				rqtId=queryForInt( storeNumber,sql, parameterMap);
			}
			catch(EmptyResultDataAccessException eRse){
			logger.error("checkIsActiveRequestExisting", eRse);
			rqtId=-1;
			}
			logger.debug("Exiting" ,"MPUWebServiceDAOImpl.checkIsActiveRequestExisting");
			return rqtId;
			
		}
	
	
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#updateOrder(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int updateOrder(String storeNumber,List<String> rqtList,String originalStatus, String orderStatus,String originalJson,Boolean reopenFlag) throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.updateOrder:",""); 
		logger.debug("Entering MPUWebServiceDAOImpl.updateOrder	storeNumber:",storeNumber +": rqtList: "+rqtList
				+": originalStatus: "+originalStatus +": orderStatus: "+orderStatus +": originalJson: "+originalJson +": reopenFlag: "+reopenFlag);
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		String updateSql = PropertyUtils.getProperty("update_order_status1");
		if(reopenFlag){
			updateSql =updateSql +  PropertyUtils.getProperty("update_order_status4");
			parameterMap.put(CREATE_TIMESTAMP.name(),DJUtilities.dateToString(Calendar.getInstance().getTime(),MpuWebConstants.DATE_FORMAT));
		}
		if(StringUtils.hasText(originalJson)){
			updateSql = updateSql + PropertyUtils.getProperty("update_order_status2");
			parameterMap.put("originalJson",originalJson);
		}
		updateSql =updateSql +  PropertyUtils.getProperty("update_order_status3");
		parameterMap.put("completeStatus",orderStatus);
		parameterMap.put("rqtList",rqtList);
		parameterMap.put("openStatus",originalStatus);
		parameterMap.put(MpuWebConstants.STORENUMBER,storeNumber);
		parameterMap.put("updateTime",DJUtilities.dateToString(Calendar.getInstance().getTime(),MpuWebConstants.DATE_FORMAT));
		logger.debug("query for  MPUWebServiceDAOImpl.updateOrder:",updateSql);
		logger.debug("Existing MPUWebServiceDAOImpl.updateOrder:",""); 
		return update(storeNumber,updateSql, parameterMap);
	}
	
	public int updateOrderList(String storeNumber,List<String> rqtList,String orderStatus,String originalJson,Boolean reopenFlag)
			throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.updateOrderList:",""); 
		logger.debug("Entering MPUWebServiceDAOImpl.updateOrder	storeNumber:",storeNumber +": rqtList: "+rqtList
				+": originalStatus: " +": orderStatus: "+orderStatus +": originalJson: "+originalJson +": reopenFlag: "+reopenFlag);
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		String updateSql = PropertyUtils.getProperty("update_order_status1");
		if(reopenFlag){
			updateSql =updateSql +  PropertyUtils.getProperty("update_order_status4");
			parameterMap.put(CREATE_TIMESTAMP.name(),DJUtilities.dateToString(Calendar.getInstance().getTime(),MpuWebConstants.DATE_FORMAT));
		}
		if(StringUtils.hasText(originalJson)){
			updateSql = updateSql + PropertyUtils.getProperty("update_order_status2");
			parameterMap.put("originalJson",originalJson);
		}
		updateSql =updateSql +  PropertyUtils.getProperty("new_update_order_status3");
		parameterMap.put("completeStatus",orderStatus);
		parameterMap.put("rqtList",rqtList);
		//parameterMap.put("openStatus",originalStatus);
		parameterMap.put(MpuWebConstants.STORENUMBER,storeNumber);
		parameterMap.put("updateTime",DJUtilities.dateToString(Calendar.getInstance().getTime(),MpuWebConstants.DATE_FORMAT));
		logger.debug("query for  MPUWebServiceDAOImpl.updateOrder:",updateSql);
		logger.debug("Existing MPUWebServiceDAOImpl.updateOrder:",""); 
		return update(storeNumber,updateSql, parameterMap);
	}
	
	public int updateCurbsideOrder(String storeNumber,String rqtId,String requestType,String requestStatus) throws DJException {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		String updateSql = PropertyUtils.getProperty("update_order_curbside_status1");

		updateSql =updateSql +  PropertyUtils.getProperty("update_order_curbside_status2");
		parameterMap.put("rqtId",rqtId);
		parameterMap.put("requestType",requestType);
		parameterMap.put("requestStatus",requestStatus);
		parameterMap.put(MpuWebConstants.STORENUMBER,storeNumber);
		parameterMap.put("updateTime",DJUtilities.dateToString(Calendar.getInstance().getTime(),MpuWebConstants.DATE_FORMAT));
		return update(storeNumber,updateSql, parameterMap);
	}
	
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getItemStockQuantity(java.lang.String, long)
	 */
	public Map<String,Object>  getItemStockQuantity(String storeNum,long rqdID) throws DJException{
	  	logger.info("Entering MPUWebServiceDAOImpl.getItemStockQuantity	storeNum:",storeNum +": rqdID: "+rqdID);
		String updateSql = PropertyUtils.getProperty("select.stock.location");
		Map<String,Object> parameterMap=new HashMap<String,Object>();
		parameterMap.put("rqd_id",rqdID);
		parameterMap.put("storeNum",storeNum);
		Map<String,Object> stknQuantity = new HashMap<String, Object>();
		logger.debug("the query for MPUWebServiceDAOImpl.getItemStockQuantity==", updateSql);
		List<Map<String,Object>> stkQtyList = (List<Map<String,Object>>)query(storeNum,updateSql, parameterMap,new ColumnMapRowMapper());
		if(null != stkQtyList && !stkQtyList.isEmpty()){
			stknQuantity = stkQtyList.get(0);
			storeNum = (String) stknQuantity.get("store_number");
			/*
			 * Check if the store number is of five digit
			 * if not apply left padding to it 
			 */
			if(null!=storeNum && storeNum.length()<5){
				/*
				 * Cannot import the package as spring stringutils is already imported and used
				 */
				storeNum =  org.apache.commons.lang3.StringUtils.leftPad(storeNum, 5, '0');
			}
			
		}
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.getItemStockQuantity");
		return stknQuantity;
	}
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#checkItemStatus(java.lang.String, java.lang.String)
	 */
	public Map<String,Object> checkItemStatus(String storeNum,String rqtID) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.checkItemStatus	","");
	 	logger.debug("Entering MPUWebServiceDAOImpl.checkItemStatus	storeNum:",storeNum +": rqtID: "+rqtID);
		String sql=PropertyUtils.getProperty("check_item_status");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("rqtId", rqtID);
		paramMap.put("strNum", storeNum);
		logger.debug("quer for MPUWebServiceDAOImpl.checkItemStatus	",sql);
		return (Map<String, Object>)query(storeNum,sql, paramMap,new ItemStatusExtractor());
		
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getPaymentList(java.lang.String, java.lang.String)
	 */
	public List<PaymentDTO> getPaymentList(String storeNumber, String requestId) throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.getPaymentList	storeNumber:",storeNumber +": requestId: "+requestId);
		String sql=PropertyUtils.getProperty("select.from.metatranspayment");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
		paramMap.put(RQT_ID.name(), requestId);
		logger.debug("the query for MPUWebServiceDAOImpl.getPaymentList",sql);
		return (List<PaymentDTO>) query(storeNumber,sql, paramMap,new PaymentDTOMapper());
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getAllItemList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> getAllItemList(String storeNumber, String queueType,String kiosk, String isRequestListNonDej,String isUserAssigned) throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.getAllItemList	","");
		logger.debug("Entering MPUWebServiceDAOImpl.getAllItemList	storeNumber:",storeNumber +": queueType: "+queueType +": kiosk: "+kiosk);
		String sql=null;
		if(null!=isRequestListNonDej && "Y".equalsIgnoreCase(isRequestListNonDej)){
			if(null!=isUserAssigned && "N".equalsIgnoreCase(isUserAssigned)){
				sql=PropertyUtils.getProperty("select.from.metatransitem.hg.open");
			}else{
				sql=PropertyUtils.getProperty("select.from.metatransitem.hg.wip");
			}
		}else{
			sql=PropertyUtils.getProperty("select.from.metatransitem");
		}
		//sql+=(!salesCheckNumber.isEmpty())?PropertyUtils.getProperty("fetch.allitem.where.with.salescheck"):PropertyUtils.getProperty("fetch.allitem.where.without.salescheck");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
		paramMap.put(MpuWebConstants.QUEUE, queueType);
		logger.debug("the query for MPUWebServiceDAOImpl.getAllItemList	",sql);
		return (List<Map<String, Object>>) queryForList(storeNumber, sql, paramMap);
		
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getCustomerData(java.lang.String, java.lang.String)
	 */
	public CustomerDTO getCustomerData(String storeNumber,String requestId) throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.getCustomerData","");
		logger.debug("Entering MPUWebServiceDAOImpl.getCustomerData	storeNumber:",storeNumber +": requestId: "+requestId);
		String sql=PropertyUtils.getProperty("select.from.metatransidentifier");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
		paramMap.put(RQT_ID.name(), requestId);
		/*List<IdentifierDTO> identifierDTOs = (List<IdentifierDTO>) query(storeNumber,sql, paramMap,new IdentifierDTOMapper());
		List<CustomerDTO> customerDTOs = MPUWebServiceUtil.convertIdentifierToCustomer(identifierDTOs);*/
		logger.debug("the query for  MPUWebServiceDAOImpl.getCustomerData:",sql);
		List<CustomerDTO> customerDTOs = (List<CustomerDTO>) query(storeNumber,sql, paramMap,new IdentifierDTOMapper());
		if(null != customerDTOs && !customerDTOs.isEmpty() ){//TODO change when a single response
			return customerDTOs.get(0);
		}else{
			return null;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getOrderDetails(java.lang.String, java.lang.String)
	 */
	public OrderDTO getOrderDetails(String storeNumber, String requestId,List<String> status)throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.getOrderDetails","");
		logger.debug("Entering MPUWebServiceDAOImpl.getOrderDetails	storeNumber:",storeNumber +": requestId: "+requestId +": status: "+status);
		String sql=PropertyUtils.getProperty("select.from.metatrans");
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
		paramMap.put(RQT_ID.name(), requestId);
		paramMap.put(MpuWebConstants.STATUS,status);
		logger.debug("the query for MPUWebServiceDAOImpl.getOrderDetails	:",sql);
		List<OrderDTO> orderDTOs =  (List<OrderDTO>) query(storeNumber,sql, paramMap,new OrderDTOMapper());
		if(null != orderDTOs && !orderDTOs.isEmpty()){//TODO when ensured of single resposnse
			return orderDTOs.get(0);
		}else{
			return null;
		}
		
		
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getItemList(java.lang.String, java.lang.String)
	 */
	public List<ItemDTO> getItemList(String storeNumber, String requestId, String itemId, List<String> status, boolean printFlag)	throws DJException {

		logger.debug("Entering MPUWebServiceDAOImpl.getItemList	storeNumber:",storeNumber +": requestId: "+requestId +": itemId: "+itemId +": status: "+status);
		
		if(!printFlag){
			
			String sql=PropertyUtils.getProperty("select.from.metatransitem.with.salescheck1");
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
			
			if(StringUtils.hasText(itemId)){
				sql = sql +MpuWebConstants.SPACE +PropertyUtils.getProperty("select.from.metatransitem.with.salescheck2");
				paramMap.put(RQD_ID.name(),itemId);
			
			} else {
				
				if(null!=status && !status.isEmpty() && status.contains("ALL")) {
					
					sql = sql +MpuWebConstants.SPACE+ PropertyUtils.getProperty("select.from.metatransitem.with.salescheck3.withoutStatus");
					
				} else {
					
					sql = sql + MpuWebConstants.SPACE + PropertyUtils.getProperty("select.from.metatransitem.with.salescheck3");
					paramMap.put(MpuWebConstants.STATUS, status);
				}
				paramMap.put(RQT_ID.name(), requestId);
			}
			if(null!=status && !status.isEmpty() && !status.contains(MpuWebConstants.EXPIRED))
				sql = sql +MpuWebConstants.SPACE +PropertyUtils.getProperty("select.from.metatransitem.with.salescheck4");
			logger.info("the mpuwebservicedaoimpl.getItemList is ", sql);
			return (List<ItemDTO>) query(storeNumber,sql, paramMap,new ItemDTOMapper());
			
		} else {
			
			String sql=PropertyUtils.getProperty("select.from.metatransitem.with.salescheck1");
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
			if(StringUtils.hasText(itemId)){
				sql = sql +MpuWebConstants.SPACE+ PropertyUtils.getProperty("select.from.metatransitem.with.salescheck2");
				paramMap.put(RQD_ID.name(),itemId);
			}
			logger.debug("the query for MPUWebServiceDAOImpl.getItemList	:",sql);
			return (List<ItemDTO>) query(storeNumber,sql, paramMap,new ItemDTOMapper());
		}

		
	}	
	
	
	//code moved from service Layer to DAO layer
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#isModActive(java.lang.String)
	 */
	public boolean isModActive(String strNum) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.isModActive","");
		strNum = DJUtilities.leftPadding(strNum, 5);
		logger.info("Entering MPUWebServiceDAOImpl.isModActive	strNum:",strNum);
		String finalUrl = PropertyUtils.getProperty("modbase_url") +"/isMODActive/"+strNum;
		try{
			logger.debug("the url for MPUWebServiceDAOImpl.isModActive",finalUrl);
		ResponseEntity<Boolean> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, Boolean.class);
		logger.debug("Exit MPUWebServiceDAOImpl.isModActive","");
		return (Boolean)response.getBody().booleanValue();
		}
		catch(RestClientException re){
			logger.error("isModActive", re);
			return false;
		}
	} 
	
	//Please test is at our side , if working correct please use thid instead of above method.
	
	/*public boolean isModActive(String strNum){
		String finalUrl = PropertyUtils.getProperty("modbase_url")+"/isMODActive/"+strNum;
		ResponseEntity<Boolean> response = (ResponseEntity<Boolean>)DJServiceLocator.get(finalUrl, Boolean.class);
		return (Boolean)response.getBody();
	} */
	
	

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getNextAction(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public Object getNextAction(String requestType, String currentStatus,String action,String type,String strNum) throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.getNextAction","");
		logger.info("Entering MPUWebServiceDAOImpl.getNextAction	requestType:",requestType +": currentStatus: "+currentStatus +
				": action: " +action +": type: "+type +": strNum: "+strNum);
		Map<String, Object> actionStatusMeta = getActionStatusMeta(strNum);
		//boolean isModActive = false;
		String modActive = "NA";
		Map<String, Object> activityMap = (Map<String, Object>)actionStatusMeta.get("activityMap");
		Map<String, Object> statusMap = (Map<String, Object>)actionStatusMeta.get("statusMap");
		Map<String, Object> modMap = (Map<String, Object>)actionStatusMeta.get("modMap");
		Map<String, Object> seqMap = (Map<String, Object>)actionStatusMeta.get("seqMap");

		String modRequired = (String) modMap.get(requestType + "-" + action);
		if ("Y".equals(modRequired)) {
			//needs to check user active table.For testing purpose you can always return as false
			String activeUsers = activeUserProcessor.getActiveUserForMOD(strNum);
			boolean isModActive = (null != activeUsers && !"".equalsIgnoreCase(activeUsers)) ? true : false;
			if (isModActive) {				modActive = "Y";
			} else {
				modActive = "N";
			}
		}
		String activity= (String) activityMap.get(requestType + "-" + currentStatus + "-"
				+ action + "-" + modActive);
		String status= (String) statusMap.get(requestType + "-" + currentStatus + "-"
				+ action + "-" + modActive);
		String seq= (String) seqMap.get(requestType + "-" + currentStatus + "-"
				+ action + "-" + modActive);
		
		logger.info("status : " ,status);
		String modNotify= modActive;
		Map<String,String> nextSteps=new HashMap<String,String>();
		nextSteps.put("activity",activity);
		nextSteps.put("status",status);
		nextSteps.put("seq",seq);
		nextSteps.put("modNotify",modNotify);
		if ("activity".equals(type)){
			return activity;
		}else if("status".equals(type)){
		    return status;
		}else if("seq".equals(type)){
		   return seq;
		}else if("modNotify".equals(type)){
			logger.debug("Existing MPUWebServiceDAOImpl.getNextAction","");
			return modNotify;
		}else{
			logger.debug("Existing MPUWebServiceDAOImpl.getNextAction","");
			return nextSteps;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#decrementItemStock(com.searshc.mpuwebservice.bean.ActionDTO)
	 */
	public void  decrementItemStock( ItemDTO actionDTO) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.decrementItemStock","");
		logger.info("Entering MPUWebServiceDAOImpl.decrementItemStock	actionDTO:",actionDTO);
		String decrementUrl=PropertyUtils.getProperty("stockDecrement_url");
		String decrementUrl2 = "";
		String stockLocation = actionDTO.getStockLocation(); // from location will be the stock location
		String qtyToRemove = actionDTO.getQty(); //this will be the scanned qty
		String stockQuantity = actionDTO.getStockQuantity();
		String storeNum="";
		String originalStoreNum=actionDTO.getStoreNumber();
		
		if((originalStoreNum.contains("1333") || originalStoreNum.contains("1430") || originalStoreNum.contains("2990") || originalStoreNum.contains("1148")) 
				&& ("qa".equalsIgnoreCase(System.getProperty(MpuWebConstants.ENVIRONMENT)) 
						|| "dev".equalsIgnoreCase(System.getProperty(MpuWebConstants.ENVIRONMENT)))){
			storeNum=PropertyUtils.getProperty("qa_default_store_decrement");
		}else{
			if(originalStoreNum!=null){
				if(originalStoreNum.length()==5){
					storeNum=originalStoreNum.substring(1);
				}else{
					storeNum=originalStoreNum;
				}
			}
		}
		String storeFormat = MpuWebConstants.STOREFORMAT;
		String userID = actionDTO.getAssignedUser();
		String divDept = actionDTO.getDivNum();
		String item = actionDTO.getItem();
		String sku = actionDTO.getSku();
		//String divDept = actionDTO.getItemNumber().substring(0, 3);
		//String item = actionDTO.getItemNumber().substring(3,8);
		//String sku = actionDTO.getItemNumber().substring(8,11);
		String sku991Plus4="";
		String ksn="0.0";
		String areaCode="";
		String areaNumber="";
		String sectionID="";
		String rowID="";
		String inputType="item";
		// Multi Location Stock Changes Starts
		//na INCASE OF RI
		if(stockLocation != null && !stockLocation.equalsIgnoreCase("") && !stockLocation.equalsIgnoreCase("NA"))
		{
			StringTokenizer locationStr = new StringTokenizer(stockLocation,",");
			StringTokenizer stockQtyStr = new StringTokenizer(stockQuantity,",");
			while (locationStr.hasMoreElements() && stockQtyStr.hasMoreElements()) {
//				decrementUrl=PropertyUtils.getProperty(System.getProperty(MpuWebConstants.ENVIRONMENT)+ MpuWebConstants.DOT+"stockDecrement_url");
				decrementUrl=PropertyUtils.getProperty("stockDecrement_url");
				decrementUrl2 = "";
				stockLocation = (String)locationStr.nextElement();
				qtyToRemove = (String)stockQtyStr.nextElement();
				// Multi Location Stock Changes Ends
				if(stockLocation!=null && !"".equalsIgnoreCase(stockLocation)){
					if(stockLocation.contains(":")){
						int index = stockLocation.indexOf(":");
						stockLocation = stockLocation.substring(0, index);
					}
					StringTokenizer tokenizer = new StringTokenizer(stockLocation,"-");
					
					areaCode  = (String) tokenizer.nextElement();
					areaNumber  = (String) tokenizer.nextElement();
					sectionID  = (String) tokenizer.nextElement();
					rowID  = (String) tokenizer.nextElement();
				}
				decrementUrl2 = "?storeNo="+storeNum+"&storeFormat="+storeFormat+"&userID="+userID+"&divDept="+divDept+"&item="+item+"&sku="+sku
											+"&sku991Plus4="+sku991Plus4+"&KSN="+ksn+"&areaCode="+areaCode+"&areaNumber="+areaNumber+"&sectionID="+sectionID+"&rowID="+rowID
											+"&qtyToRemove="+qtyToRemove+"&inputType="+inputType;
				
				decrementUrl = decrementUrl + decrementUrl2;
				logger.info("check URL " ,"decrementUrl: "+decrementUrl);
				//response=restTemplate.exchange(decrementUrl, HttpMethod.GET, null, ItemMasterResponse.class);
				/*ResponseEntity<ItemMasterResponse> response = (ResponseEntity<ItemMasterResponse>)*/
				logger.info("Existing MPUWebServiceDAOImpl.decrementItemStock","");
				try{
					DJServiceLocator.get(decrementUrl, String.class);
				}catch(Exception exp){
					logger.info("In CatchBlock of decrementItemStock ", exp.getStackTrace());
				}
			}
		}
		
	}
	
	// Method Overloaded to support Batch Update /Insert
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#createActivity(java.util.List, int, int[], java.lang.String)
	 */
	public int createActivity(List<ActivityDTO> activityDTOs, long rqtId, Object [] rqdId,String storeNumber) throws DJException {
		
		logger.debug("Entering MPUWebServiceDAOImpl.createActivity	activityDTOs:",activityDTOs +": rqtId: "+rqtId +": rqdId: "+rqdId +": storeNumber: "+storeNumber);
		
	    int size = activityDTOs.size();
	    Map<String, ? super Object> param[] = new HashMap[size];
	    int i = 0;
		for(ActivityDTO activity : activityDTOs) {
			
			Map<String, ? super Object> params = new HashMap<String, Object>();
			logger.debug("createActivity", "Inside createActivity");
			params.put(ACTION_SEQ.name(), activity.getActionSeq());
			params.put(ACTIVITY_DESCRIPTION.name(), activity.getActivityDescription());
			params.put(ASSIGNED_USER.name(), activity.getAssignedUser());
			Date currentTime=Calendar.getInstance().getTime();
		    String createTime = DJUtilities.dateToString(currentTime,MpuWebConstants.DATE_FORMAT);
			params.put(CREATE_TIMESTAMP.name(),	createTime);//TODO
			if(null != activity.getCreatedBy()){
				params.put(CREATED_BY.name(), activity.getCreatedBy());
				params.put(MODIFIED_BY.name(),activity.getCreatedBy());
			}else{
				params.put(CREATED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);
				params.put(MODIFIED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);	
			}
			params.put(ENTRY_TYPE.name(), activity.getType());
			params.put(FROM_LOCATION.name(), activity.getFromLocation());
			params.put(RQD_ID.name(), rqdId[i]);
			params.put(RQT_ID.name(), rqtId);
			params.put(STORE.name(), activity.getStore());
			if(StringUtils.hasText(activity.getToLocation())){
				params.put(TO_LOCATION.name(), activity.getToLocation());
			}else{
				params.put(TO_LOCATION.name(), null); //suggested by sandeep
			}
			params.put(TYPE.name(), activity.getType());
			param[i++]=params;
		}
		
		//String sql = "INSERT INTO REQUEST_ACTIVITY (RQT_ID,RQD_ID,CREATE_TIMESTAMP,TYPE,ASSIGNED_USER,STORE_NUMBER,FROM_LOCATION,TO_LOCATION,CREATED_BY) VALUES(:RQT_ID,:RQD_ID,:CREATE_TIMESTAMP,:TYPE,:ASSIGNED_USER,:STORE,:FROM_LOCATION,:TO_LOCATION,:CREATED_BY)";
		String sql = PropertyUtils.getProperty("insert.into.activity");
		
		logger.debug("Existing MPUWebServiceDAOImpl.createActivity","");
		
		return super.batchUpdate(storeNumber, sql, param).length;
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#createPaymentList(java.util.List, int, java.lang.String)
	 */
	public int createPaymentList(List<PaymentDTO> paymentDTOs, long rqtId,String storeNumber) throws DJException {
		
		logger.debug("Entering MPUWebServiceDAOImpl.createPaymentList	paymentDTOs:",paymentDTOs +": rqtId: "+rqtId +": storeNumber: "+storeNumber);
		
	    int size = paymentDTOs.size();
	    Map<String, ? super Object> param[] = new HashMap[size];
	    int i = 0;
		for(PaymentDTO payment : paymentDTOs) {
		    String amtStr = "";
			Map<String, ? super Object> params = new HashMap<String, Object>();
			
			if(rqtId==0){
				params.put(RQT_ID.name(), payment.getRqtId());
			}
			else{
				params.put(RQT_ID.name(), rqtId);
				
			}
			BigDecimal amt = payment.getAmount();
			if (null != amt) {
				amtStr = amt.toPlainString();
				amtStr = (null != amtStr && amtStr.length() > 12) ? amtStr.substring(0, 12) : amtStr;
			}
			//params.put(RQT_ID.name(), rqtId);
			params.put(TYPE.name(), payment.getType());
			params.put(ACCOUNT_NUMBER.name(), payment.getAccountNumber());
			params.put(AMOUNT.name(), amtStr);
			params.put(STATUS.name(), payment.getStatus());
			params.put(PAYMENT_DATE.name(), payment.getPaymentDate());
			params.put(EXPIRATION_DATE.name(), payment.getExpirationDate());
			params.put(STORE_NUMBER.name(),storeNumber);
			if(null != payment.getCreatedBy()){
				params.put(CREATED_BY.name(), payment.getCreatedBy());
				params.put(MODIFIED_BY.name(),payment.getCreatedBy());
			}else{
				params.put(CREATED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);
				params.put(MODIFIED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);	
			}
			param[i] = params;
			i++;
		}
		//String sql = "INSERT INTO REQUEST_QUEUE_PAYMENT (RQT_ID, TYPE, ACCOUNT_NUMBER, AMOUNT, STATUS, PAYMENT_DATE, EXPIRATION_DATE, STORE_NUMBER, CREATED_BY, MODIFIED_BY) VALUES (:RQT_ID, :TYPE, :ACCOUNT_NUMBER, :AMOUNT, :STATUS, :PAYMENT_DATE, :EXPIRATION_DATE, :STORE_NUMBER, :CREATED_BY, :MODIFIED_BY)";
		String sql = PropertyUtils.getProperty("insert.into.payment");
		
		logger.debug("Existing MPUWebServiceDAOImpl.createPaymentList","");
		
		return super.batchUpdate(storeNumber, sql, param).length;
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#createItemList(java.util.List, int, java.lang.String, java.lang.String)
	*/
	/*public Object[] createItemList(List<ItemDTO> itemDTOs, long rqtId, String expireTime, String storeNumber, String requestType, boolean isLockerEligible)
			throws DJException, ParseException{

		logger.debug("Entering MPUWebServiceDAOImpl.createItemList	itemDTOs: ",itemDTOs	+" : rqtId: "+rqtId	+" : expireTime: "+expireTime +" : storeNumber: "+ storeNumber 
				+" : requestType: "+requestType);
		
		List<Object[]> paramList = new ArrayList<Object[]>();
		 to get the stock location and max qty for the given item.
		Map<String,Object> storeInfo = getStoreDetails(storeNumber);	//	Fetch Store Details
		String storeTimeZone = null;
		if(storeInfo != null){
			storeTimeZone = (String) storeInfo.get("timeZone");
		}
		
		String escalationTime = null;
		
		if(null != expireTime && !("0".equalsIgnoreCase(expireTime))) {
		    
		    long milliSeconds= Long.parseLong(expireTime);
		    escalationTime = SHCDateUtils.convertFromStoreToLocal(milliSeconds, storeTimeZone);
		
		} else {
		    //Date currentTime=Calendar.getInstance().getTime();
		    //long milliSeconds= Long.parseLong(expireTime);
			
			 * For stage and binstage type of request
			 * Expiretime is current time as there stage queue request does not 
			 * expire
			 
			long milliSeconds =0;
			if("BINSTAGE".equalsIgnoreCase(requestType)	|| "STAGE".equalsIgnoreCase(requestType)) {
				
				milliSeconds = Calendar.getInstance().getTimeInMillis();
			}
		    escalationTime = SHCDateUtils.convertFromStoreToLocal(milliSeconds, storeTimeZone);
		}
		 
		 String currentStatus = "";
		 String action = "CREATE";
		 
		 logger.debug("createItemList", "No of Item in itemDTO size::: "+ itemDTOs.size());
		 
		 for(ItemDTO item : itemDTOs) {
			 
			List<Object> objParams = new ArrayList<Object>();

			item.setItemStatus((String) getNextAction(requestType, currentStatus, action, "status",storeNumber));
			item.setStoreNumber(storeNumber);
			//getItemStockLocAndQty(item);   //Commenting the call for the time being as NPOS is sending these data.Will open if required .
			objParams.add(rqtId);
			objParams.add(item.getAssignedUser());
			objParams.add(item.getDivNum());
			objParams.add(item.getItem());
			objParams.add(item.getSku());
			objParams.add(item.getPlus4());
			objParams.add(item.getUpc());
			objParams.add(item.getKsn());
			objParams.add(item.getQty());
			objParams.add(item.getItemStatus());
			objParams.add(item.getFromLocation());
			objParams.add(item.getToLocation());
			objParams.add(item.getItemImage());
			objParams.add(item.getStoreNumber());
			objParams.add(item.getStockLocation());
			objParams.add(item.getStockQuantity());
			objParams.add(item.getThumbnailDesc());
			objParams.add(item.getFullName());
			objParams.add(item.getRequestNumber());
			objParams.add(item.getEscalation());
			objParams.add(item.getItemSeq());
			
			if(null != item.getCreatedBy()) {
				objParams.add(item.getCreatedBy());
				objParams.add(item.getCreatedBy());
			
			} else {
				
				objParams.add(MpuWebConstants.ORDER_ADAPTOR);
				objParams.add(MpuWebConstants.ORDER_ADAPTOR);
			}
			objParams.add(item.getSalescheck());
			objParams.add(item.getItemId());
			
			
			 * Added by Nasir
			 
			objParams.add(item.getSaleType());
			objParams.add(item.getItemTransactionType());
			objParams.add(item.getItemSaleOrigin());
			
			*//** to populate locker indicator for web Request
			 * @throws DDRMetaException 
			 * @throws DataAccessException **//*
			if(isLockerEligible){
				
				objParams.add("Y");
				
			} else {
				objParams.add("N");
			}
			
			Object[] objArray = objParams.toArray();
			paramList.add(objArray);
		}
		
		String sql = PropertyUtils.getProperty("insert.into.item_batch_update");
		logger.debug("createItemList", "sql:::" + sql);
		logger.debug("createItemList", "paramList:::" + paramList);
		Object[] objArray =  super.batchUpdateWithKeyHolder(storeNumber, sql, paramList);
		logger.debug("createItemList", "objArray:::"+objArray);
		logger.debug("Existing MPUWebServiceDAOImpl.createItemList","");
		
		return objArray;
	}
*/
	public Object[] createItemList(List<ItemDTO> itemDTOs, long rqtId,String expireTime,
			String storeNumber,String requestType,
			boolean isLockerEligible,String createTime,String orderSource,String... notInMpu)
			throws DJException, DataAccessException, DDRMetaException{
		
		logger.debug("Entering MPUWebServiceDAOImpl.createItemList	itemDTOs:",itemDTOs 	+": rqtId: "+rqtId
				+": expireTime: "+expireTime 	+": storeNumber: "+storeNumber 	+": requestType: "+requestType);
		List<Object[]> paramList = new ArrayList<Object[]>();
		Object[] responseArray;
		try {
		/* to get the stock location and max qty for the given item.*/
		Map<String,Object> storeInfo=getStoreDetails(storeNumber);
		logger.debug("createItemList : storeInfo", storeInfo);
		String storeTimeZone=null;
		if(storeInfo!=null){
			storeTimeZone=(String) storeInfo.get("timeZone");
		}
		
		String escalationTime=null;
		
		if(null != expireTime && !("0".equalsIgnoreCase(expireTime))){
		    
		    long milliSeconds= Long.parseLong(expireTime);
		    escalationTime = SHCDateUtils.convertFromStoreToLocal(milliSeconds, storeTimeZone);
		}else{
		    //Date currentTime=Calendar.getInstance().getTime();
		    //long milliSeconds= Long.parseLong(expireTime);
			/*
			 * For stage and binstage type of request
			 * Expiretime is current time as there stage queue request does not 
			 * expire
			 */
			long milliSeconds =0;
			/*
			 * For direct order source the escalation time should be calculated
			 */
			if(null!=orderSource && MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(orderSource)){
				SimpleDateFormat dateNewFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date currentTime= Calendar.getInstance().getTime();
				String dateString=dateNewFormat.format(currentTime);
				escalationTime=SHCDateUtils.calculateEscalationTime(dateString, (HashMap<String, Object>) storeInfo);	
				logger.info("==========dateString============", dateString+"\n\n");
				logger.info("==========escalationTime========",escalationTime );
				
				
			}else if("BINSTAGE".equalsIgnoreCase(requestType)
					|| "STAGE".equalsIgnoreCase(requestType)){
				milliSeconds = Calendar.getInstance().getTimeInMillis();
				escalationTime = SHCDateUtils.convertFromStoreToLocal(milliSeconds, storeTimeZone);
			}else{
				escalationTime = SHCDateUtils.convertFromStoreToLocal(milliSeconds, storeTimeZone);
			}
		}
		
		
		 
		 String currentStatus="";
		 String action="CREATE";
		 int size=itemDTOs.size();
		 logger.info("createItemList", "size:::"+size);
		 for(int i=0;i<size;i++){
			ItemDTO item =itemDTOs.get(i);
			List<Object> objParams = new ArrayList<Object>();
			try{
				if(MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(item.getRequestType())){
					String thumbNail = new String(Base64.decodeBase64(item.getThumbnailDesc()),"UTF-8");
					item.setThumbnailDesc(thumbNail);
					
					String fullName = new String(Base64.decodeBase64(item.getFullName()),"UTF-8");
					item.setFullName(fullName);
				}
			}catch(UnsupportedEncodingException exception){}
			
			//itemDTO.setItemStatus("setItemStatus");
			/**For on orders or orders which should be only on mpu queue when pickup
			 * is innitiated
			 */
			if(null!=notInMpu && notInMpu.length>0 && 
					MpuWebConstants.NOT_IN_MPU.equalsIgnoreCase(notInMpu[0])){
				item.setItemStatus(MpuWebConstants.PICKUP_INITIATED);
				/*if("BIN".equals(item.getFromLocation())){
					item.setFromLocation(item.getFromLocation()+" "+CommonUtils.nullToBlank(item.getItemBinNumber()));
					
				}*/
				
				
				if("BIN".equalsIgnoreCase(item.getToLocation()) || !StringUtils.isEmpty(item.getItemBinNumber())){
					item.setToLocation("BIN"+" "+CommonUtils.nullToBlank(item.getItemBinNumber()));
					
				}
			}else{
				String status=(String)getNextAction(requestType+(StringUtils.hasText(item.getLayawayFlag()) ? item.getLayawayFlag() : ""), currentStatus, action, "status",storeNumber);
				if(status==null||"".equals(status))
				{
					status="OPEN";
				}
				else if(null != item.getRequestType() && (MpuWebConstants.RETURN.equalsIgnoreCase(item.getRequestType()) || MpuWebConstants.RETURNIN5.equalsIgnoreCase(item.getRequestType()))){
					status = MpuWebConstants.RETURNED;
				}
				item.setItemStatus(status);
			}
			item.setStoreNumber(storeNumber);
			//start add for reserve it changes
			// uncommented for H&G
			//if(MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(requestType) || (!"FLR".equalsIgnoreCase(item.getFromLocation()) && !"OTH".equalsIgnoreCase(item.getFromLocation()) )){

			if(!"FLR".equalsIgnoreCase(item.getFromLocation())){
				getItemStockLocAndQty(item);
			}
			
			if(MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(requestType)){
				if(!StringUtils.hasText(item.getStockLocation())){
					item.setStockLocation("NA");
					item.setFromLocation("NA");
					item.setStockQuantity("0");
				}
			}
			//end add for reserve it changes
			item.setRqtId(String.valueOf(rqtId));
			objParams.add(rqtId);
			objParams.add(item.getAssignedUser());
			objParams.add(item.getDivNum());
			objParams.add(item.getItem());
			objParams.add(item.getSku());
			objParams.add(item.getPlus4());
			objParams.add(item.getUpc());
			objParams.add(item.getKsn());
			objParams.add(item.getQty());
			objParams.add(item.getItemStatus());
			objParams.add(item.getFromLocation());
			objParams.add(item.getToLocation());
			objParams.add(item.getItemImage());
			objParams.add(item.getStoreNumber());
			objParams.add(item.getStockLocation());
			objParams.add(item.getStockQuantity());
			objParams.add(item.getThumbnailDesc());
			objParams.add(item.getFullName());
			objParams.add(item.getRequestNumber());
			objParams.add(item.getEscalation());
			objParams.add(escalationTime);
			/*
			 * Adding escalation time & RQT_ID to items
			 */
			item.setEscalationTime(escalationTime);
			item.setRqtId(String.valueOf(rqtId));
			item.setItemCreatedDate(createTime);
			item.setVersion("0");
			
			/**
			 * For Setting thumbnail + fullname in thumbnail desc in item
			 */
			if(MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(item.getCreatedBy()) && !StringUtils.isEmpty(item.getFullName())){
				String completeDesc  = "";
				if(!StringUtils.isEmpty(item.getThumbnailDesc())){
					completeDesc = item.getThumbnailDesc();
				}
				
				item.setThumbnailDesc(item.getFullName()+completeDesc);
			}
			
			
			objParams.add(item.getItemSeq());
			if(null != item.getCreatedBy()){
				objParams.add(item.getCreatedBy());
				objParams.add(item.getCreatedBy());
			}else{
				item.setCreatedBy(MpuWebConstants.ORDER_ADAPTOR);
				objParams.add(MpuWebConstants.ORDER_ADAPTOR);
				objParams.add(MpuWebConstants.ORDER_ADAPTOR);
			}
			objParams.add(item.getSalescheck());
			objParams.add(item.getItemId());
			
			/*
			 * Added by Nasir
			 */
			objParams.add(item.getSaleType());
			objParams.add(item.getItemTransactionType());
			objParams.add(item.getItemSaleOrigin());
			
			/** to populate locker indicator for web Request**/
			if(isLockerEligible){
				item.setIsLockerValid("Y");
				objParams.add("Y");
			}else{
				item.setIsLockerValid("N");
				objParams.add("N");
			}
			item.setItemCreatedDate(createTime);
				item.setVersion("0");
				
			objParams.add(createTime);
			objParams.add(item.getLayawayFlag()); // change for layaway
			objParams.add(item.getFfmType());
			
			/**For on orders or orders which should be only on mpu queue when pickup
			 * is innitiated
			 */
			if(null!=notInMpu && notInMpu.length>0 && 
					MpuWebConstants.NOT_IN_MPU.equalsIgnoreCase(notInMpu[0])){
				item.setQtyRemaining(item.getQty());
			}else{
				item.setQtyRemaining("0");
			}
			objParams.add(item.getQtyRemaining());
			objParams.add(item.getReturnReason());
			
			Object[] objArray = objParams.toArray();
			paramList.add(objArray);
		}
		String sql = PropertyUtils.getProperty("insert.into.item_batch_update");
			responseArray =  super.batchUpdateWithKeyHolder(storeNumber, sql, paramList);
		} catch (ParseException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
		
		logger.debug("createItemList", "responseArray:::"+responseArray);
		logger.debug("Existing MPUWebServiceDAOImpl.createItemList","");
		
		return responseArray;
	}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getIdentifierMeta(java.lang.String)
	 */
	public  Map<String,Object> getIdentifierMeta(String storeNumber)throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.getIdentifierMeta","");
		logger.debug("Entering MPUWebServiceDAOImpl.getIdentifierMeta	storeNumber:",storeNumber);
		if(identifierMeta.isEmpty()){
		String sql= PropertyUtils.getProperty("select.from.identifier.meta");
		logger.debug("the query for MPUWebServiceDAOImpl.getIdentifierMeta:",sql);
		identifierMeta=(Map<String,Object>)query(storeNumber, sql,new HashMap<String,Object>(), new IdentifierMetaExtractor());
	    }
		logger.debug("getIdentifierMeta", identifierMeta.toString());
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.getIdentifierMeta");
		return identifierMeta;
	}
	

	public void updateAllItems(String rqtId,String expireTime,String storeNumber,List<String> rqdIdList)
			throws DJException{
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.updateAllItems");
		logger.debug("Entering MPUWebServiceDAOImpl.updateAllItems	rqtId:",rqtId +": expireTime: "+expireTime +": storeNumber: "+storeNumber +": rqdIdList: "+rqdIdList);
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		
		parameterMap.put(ASSIGNED_USER.name(),null);
		parameterMap.put(ITEM_STATUS.name(),MpuWebConstants.OPENSTATUS);
		//parameterMap.put(TO_LOCATION.name(),null);
		
		if(null != expireTime && !("0".equalsIgnoreCase(expireTime))){
		    Calendar calendar = Calendar.getInstance();
		    long milliSeconds= Long.parseLong(expireTime);
		    calendar.setTimeInMillis(milliSeconds);
		    String escalationTime = DJUtilities.dateToString(calendar.getTime(),MpuWebConstants.DATE_FORMAT);
		    parameterMap.put(ESCALATION_TIME.name(),escalationTime);
		}else{
		    Date currentTime=Calendar.getInstance().getTime();
		    String escalationTime = DJUtilities.dateToString(currentTime,MpuWebConstants.DATE_FORMAT);
		    parameterMap.put(ESCALATION_TIME.name(),escalationTime);
		}
		
		parameterMap.put(RQD_ID.name(),rqdIdList);
		String updateSql=PropertyUtils.getProperty("update_item_open");
		logger.debug("the query for MPUWebServiceDAOImpl.updateAllItems:",updateSql);
		update(storeNumber,updateSql,parameterMap);
		logger.debug("Existing" ,"MPUWebServiceDAOImpl.updateAllItems");
		
	} 
	
	public OrderAdaptorRequest getOriginalJSON(String rqtId, String strNum, Integer transId) throws DJException {
		
		logger.debug("getOriginalJSON", "Entering MPUWebServiceDAOImpl.getOriginalJSON rqt_id : " + rqtId +"	-- strNum : " + strNum + " -- transId : " + transId);
		
		HashMap<String,Object> parameterMap = new HashMap<String,Object>();
		
		String sql = "";
		
		if(null == transId) {
			
			sql = "select originalJson from request_queue_trans where rqt_id = :rqt_id";
			parameterMap.put("rqt_id", rqtId);
			
		} else {
			
			sql = "SELECT originalJson FROM REQUEST_MPU_TRANS WHERE TRANS_ID = :TRANS_ID";
			parameterMap.put("TRANS_ID", transId);
		}
		
		Order order = (Order) query(strNum, sql,parameterMap, new OriginalOrderExtractor());
		
		OrderAdaptorRequest request = new OrderAdaptorRequest();
		request.setRequestOrder(order);
		
		logger.debug("getOriginalJSON", "Exiting MPUWebServiceDAOImpl.getOriginalJSON");
		
		return request;
	
	}
	public boolean checkRequestComplete(String strNum,String rqtId,String requestType) throws DJException{
		logger.info("Entering MPUWebServiceDAOImpl.checkRequestComplete	strNum:",strNum 
				+": rqtId: "+rqtId +": requestType: "+requestType );
		String sql=PropertyUtils.getProperty("check.request.complete");
		boolean complete=false;
        HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		
		parameterMap.put("rqtId",rqtId);
		parameterMap.put("strNum",strNum);
		parameterMap.put("request_type",requestType);
		logger.debug("the query for MPUWebServiceDAOImpl.checkRequestComplete	:",sql);
		int count=queryForInt(strNum, sql, parameterMap);
		if(count==0){
			complete=true;
		}
		logger.debug("Existing" ,"MPUWebServiceDAOImpl.checkRequestComplete");
		return complete;
	}
	
	public boolean checkRequestCancel(String strNum,String rqtId,String requestType) throws DJException{
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.checkRequestCancel");
		logger.info("Entering MPUWebServiceDAOImpl.checkRequestCancel	strNum:",strNum 
				+": rqtId: "+rqtId +": requestType: "+requestType );
		String sql=PropertyUtils.getProperty("check_request_cancel");
		boolean cancel=false;
        HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		
		parameterMap.put("rqtId",rqtId);
		parameterMap.put("strNum",strNum);
		parameterMap.put("request_type",requestType);
		logger.debug("the query for MPUWebServiceDAOImpl.checkRequestCancel	strNum:",sql);
		int count=queryForInt(strNum, sql, parameterMap);
		if(count==0){
			cancel=true;
		}
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.checkRequestCancel");
		return cancel;
	}

	
	public boolean checkRequestVoid(String strNum,String rqtId,String requestType) throws DJException{
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.checkRequestVoid");
		logger.info("Entering MPUWebServiceDAOImpl.checkRequestVoid	strNum:",strNum 
				+": rqtId: "+rqtId +": requestType: "+requestType );
		String sql=PropertyUtils.getProperty("check_request_void");
		boolean voidresult=false;
        HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		
		parameterMap.put("rqtId",rqtId);
		parameterMap.put("strNum",strNum);
		parameterMap.put("request_type",requestType);
		logger.debug("the query for MPUWebServiceDAOImpl.checkRequestCancel	:",sql);
		int count=queryForInt(strNum, sql, parameterMap);
		if(count==0) voidresult=true;
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.checkRequestVoid");
		return voidresult;
	}
	
	public int cancelExpireItems(List<String> rqdList,String storeNumber,String status) throws DJException{
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.cancelExpireItems");
		logger.debug("Entering MPUWebServiceDAOImpl.cancelExpireItems	rqdList:",rqdList +": storeNumber: "+storeNumber +": status: "+status);
		String sql=PropertyUtils.getProperty("expire_items");
		
		 HashMap<String,Object> parameterMap=new HashMap<String,Object>();
			/***********fix for STORESYS-24103 **************/
			List<String> statusList = new ArrayList<String>();
			statusList.add("BIN_PENDING");
			sql = sql + "and item_status  not in  (:statusList)";
			parameterMap.put("statusList",statusList);
			/***********fix for STORESYS-24103 **************/
		 
		 parameterMap.put("item_status",status);
		// parameterMap.put("update_timestamp",DJUtilities.dateToString(Calendar.getInstance().getTime(),MpuWebConstants.DATE_FORMAT));
		 parameterMap.put("rqdList",rqdList);
		 logger.debug("the query for MPUWebServiceDAOImpl.cancelExpireItems	:",sql);
			return update(storeNumber,sql,parameterMap);
	}
public Map<String, String> getRequestQueueMeta(String storeNum) throws DJException {
	logger.debug("Entering" ,"MPUWebServiceDAOImpl.getRequestQueueMeta");
	logger.debug("Entering MPUWebServiceDAOImpl.getRequestQueueMeta	storeNum:",storeNum);
		if(requestQueueMeta.isEmpty()){
			String sql = PropertyUtils.getProperty("select.from.requestqueuemeta");
			requestQueueMeta=(Map<String,String>)query(storeNum, sql,new HashMap<String,String>(), new RequestQueueMetaExtractor());
		}
		logger.debug("requestQueueMeta = ", requestQueueMeta);
		logger.debug("Existing" ,"MPUWebServiceDAOImpl.getRequestQueueMeta");
		return requestQueueMeta;
	}


public void clearMetadata() {
	// TODO Auto-generated method stub
	 
	storeInfoMap.clear();
	actionMeta.clear();
	identifierMeta.clear();
	requestQueueMeta.clear();
}

public int insertBulkActivities(List<String> rqtList,List<String> rqdList,HashMap<String,String> mapping,String actionSeq,String activity,String strNum,HashMap<String,String> requestType) throws DJException{
	logger.debug("Entering" ,"MPUWebServiceDAOImpl.insertBulkActivities");
	logger.debug("Entering MPUWebServiceDAOImpl.insertBulkActivities	rqtList:",rqtList 
			+": rqdList: "+rqdList +": mapping: "+mapping +": actionSeq: "+actionSeq +": activity: "+activity +": strNum: "+strNum
			+": requestType: "+requestType );
	 
	    ArrayList<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
		for(String rqdId:rqdList){
		   // ActivityDTO activity = activityDTOs.get(i);
			HashMap<String, ? super Object> params = new HashMap<String, Object>();
			logger.debug("createActivity", "Inside createActivity");
			params.put(ACTION_SEQ.name(), actionSeq);
			params.put(ACTIVITY_DESCRIPTION.name(), activity);
			params.put(ASSIGNED_USER.name(), null);
			Date currentTime=Calendar.getInstance().getTime();
		    String createTime = DJUtilities.dateToString(currentTime,MpuWebConstants.DATE_FORMAT);
			params.put(CREATE_TIMESTAMP.name(),	createTime);//TODO
			
				params.put(CREATED_BY.name(), "SYSTEM");	
			
			//params.put(ENTRY_TYPE.name(), activity.getType());
			params.put(FROM_LOCATION.name(), null);
			params.put(RQD_ID.name(), rqdId);//TODO
			params.put(RQT_ID.name(),mapping.get(rqdId));
			params.put(STORE.name(), strNum);
			
				params.put(TO_LOCATION.name(), null); //suggested by sandeep
			
			params.put(TYPE.name(),requestType.get(rqdId));
			paramList.add(params);
		}
		
		//String sql = "INSERT INTO REQUEST_ACTIVITY (RQT_ID,RQD_ID,CREATE_TIMESTAMP,TYPE,ASSIGNED_USER,STORE_NUMBER,FROM_LOCATION,TO_LOCATION,CREATED_BY) VALUES(:RQT_ID,:RQD_ID,:CREATE_TIMESTAMP,:TYPE,:ASSIGNED_USER,:STORE,:FROM_LOCATION,:TO_LOCATION,:CREATED_BY)";
		String sql = PropertyUtils.getProperty("insert.into.activity");	
		HashMap<String,Object> paramArray[]=new HashMap[paramList.size()];
		logger.debug("Existing" ,"MPUWebServiceDAOImpl.insertBulkActivities");
		return super.batchUpdate(strNum, sql, paramList.toArray(paramArray)).length;


}
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#getOrderDetails(java.lang.String, java.lang.String)
	 */
	public List<Map<String,Object>> checkValidOrder(RequestDTO requestDTO) throws DJException {
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.checkValidOrder");
		logger.debug("Entering MPUWebServiceDAOImpl.checkValidOrder	requestDTO:",requestDTO);
		String sql=PropertyUtils.getProperty("check_valid_order");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(MpuWebConstants.STORE_NUMBER, requestDTO.getOrder().getStoreNumber());
		paramMap.put(MpuWebConstants.SALESCHECK, requestDTO.getOrder().getSalescheck());
		paramMap.put(MpuWebConstants.CUSTOMER_ID, requestDTO.getCustomer().getCustomerId());
		logger.debug("the query for MPUWebServiceDAOImpl.checkValidOrder	:",sql);
		List<Map<String,Object>> validCheckList =  (List<Map<String,Object>>) queryForList(requestDTO.getOrder().getStoreNumber(),sql, paramMap);
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.checkValidOrder");
		return validCheckList;
		
	}
	
	public int assignUser(String store,String rqdId,String user,String requestType,String rqtId, String searsSalesId) throws DJException{
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.assignUser");
		logger.debug("Entering MPUWebServiceDAOImpl.assignUser	requestDTO:"+rqdId,user);
		String sql = null;
		if(null != requestType && MpuWebConstants.LAYAWAYF.equalsIgnoreCase(requestType)){
			 sql = PropertyUtils.getProperty("update_assigned_user1");
		} else if (null != requestType && MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(requestType)){
			if(null==user || user ==""){
				sql = PropertyUtils.getProperty("update_assigned_user3");
				updateOrderStatus(store,Long.valueOf(rqtId),MpuWebConstants.OPEN);// for updating H&G request status back to OPEN when back button is clicked.
			}else {
				sql = PropertyUtils.getProperty("update_assigned_user2");
				cancelExpireRequest(store,Long.valueOf(rqtId),MpuWebConstants.WIP);// for updating H&G request status to WIP and item status to ASSIGNED.
			}
		} else {
			 sql = PropertyUtils.getProperty("update_assigned_user");
		}
			
		//String sql=PropertyUtils.getProperty("update_assigned_user");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(ASSIGNED_USER.name(), user);
		paramMap.put(RQD_ID.name(),rqdId);
		paramMap.put(RQT_ID.name(),rqtId);
		paramMap.put(STORE_NUMBER.name(),store);
		String searsId=null;
		if(!CommonUtils.isEmpty(user)){searsId= getAssociateId();}
		paramMap.put(SEARS_SALES_ID.name(), searsId);
		logger.debug("the query for MPUWebServiceDAOImpl.assignUser	:",sql);
		return update(store, sql,paramMap);
		
	}

	public PrintServiceResponse printLabel(MPUItemVO mpuItemVO,String url) throws DJException {
		logger.debug("MPUWebServiceDAOImpl:printLabel", "->Entering" + (null != MDC.get("txnId") ? " [txId:" +(String) MDC.get("txnId") + "]" : ""));
		HttpEntity<MPUItemVO> requestEntity=new HttpEntity<MPUItemVO>(mpuItemVO);
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.debug("mpuItemVO JSON",mapper.writeValueAsString(mpuItemVO) + (null != MDC.get("txnId") ? " [txId:" +(String) MDC.get("txnId") + "]" : ""));
		} catch (JsonGenerationException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		} catch (JsonMappingException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		} catch (IOException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
		ResponseEntity<PrintServiceResponse> enResponse=restTemplate.exchange(url, HttpMethod.POST,requestEntity, PrintServiceResponse.class);
		logger.debug("MPUWebServiceDAOImpl:printLabel", "->Exiting" + (null != MDC.get("txnId") ? " [txId:" +(String) MDC.get("txnId") + "]" : ""));
		return enResponse.getBody();
		
	}
	
	//Starts 677829

	/**
	 * This method is used to insert records in the request_package table
	 * @param rqt_id
	 * @param user
 	 * @param storeNumber
 	 * @param fromLocation
	 * @param toLocation
	 * @return noOfRowsAffected
	 * @throws DJException
	 */
/*	public int insertPackageDetails(long rqtId, String user,
			String storeNumber, String fromLocation)
			throws DJException {
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.insertPackageDetails");
		logger.debug("Entering MPUWebServiceDAOImpl.insertPackageDetails	rqt_id:", rqtId 
				+": user: "+ user +": storeNumber: "+ storeNumber +": fromLocation: "+ fromLocation );
		Map<String, ? super Object> params = new HashMap<String, Object>();
		params.put(RQT_ID.name(), rqtId);
		params.put(FROM_LOCATION.name(), fromLocation);
		params.put(CREATED_BY.name(), user);
		params.put(STORE_NUMBER.name(), storeNumber);
		
		String sql = PropertyUtils.getProperty("insert.into.package");
		
		logger.debug("Exiting" , "MPUWebServiceDAOImpl.insertPackageDetails");
		return super.updateWithKeyHolder(storeNumber, sql, params).intValue();
		}
*/
	/**
	 * This method is used to update the to_location field value in the request_package table
	 * @param packageNumber
	 * @param binNumber
	 * @return noOfRowsUpdated
	 * @throws DJException
	 */
	public int updatePackageDetails(String packageNumber, String binNumber, String storeNumber)
			throws DJException {
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.updatePackageDetails");
		logger.debug("Entering MPUWebServiceDAOImpl.updatePackageDetails	packageNumber:"+ packageNumber, " binNumber:" + binNumber + " storeNumber:" + storeNumber);
		
		String sql=PropertyUtils.getProperty("update_package_bin");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(PACKAGE_NUM.name(),packageNumber);
		paramMap.put(PACKAGE_BIN.name(),binNumber);
		
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.updatePackageDetails");
		return super.update(storeNumber, sql,paramMap);
	}

	//@Override
	public List<PackageDTO> getPackageList(String storeNumber, String requestId) throws DJException {
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.getPackageList");
		logger.debug("Entering MPUWebServiceDAOImpl.getPackageList	storeNumber:",storeNumber +": requestId: "+requestId);
		String sql=PropertyUtils.getProperty("select.from.package");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(STORE_NUMBER.name(), storeNumber);
		paramMap.put(RQT_ID.name(), requestId);
		logger.debug("Existing" ,"MPUWebServiceDAOImpl.getPackageList");
		return (List<PackageDTO>) query(storeNumber,sql, paramMap,new PackageDTOMapper());
	}
	
	//Ends 677829
	
	public Map<String,Object> checkExpiredOrder(String rqtId,String storeNo) throws DJException{
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.checkExpiredOrder");
		String sql=PropertyUtils.getProperty("get_expired_order");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(STORE_NUMBER.name(), storeNo);
		paramMap.put(RQT_ID.name(), rqtId);
		List<Map<String, Object>> list = (List<Map<String, Object>>)queryForList(storeNo, sql, paramMap);
		if(!CollectionUtils.isEmpty(list)){
			logger.debug("Existing" ,"MPUWebServiceDAOImpl.checkExpiredOrder");
			return list.get(0);
		}
		logger.debug("Existing" ,"MPUWebServiceDAOImpl.checkExpiredOrder");
		return null;
	}

	public int updateNotDeliver(String storeNumber, String rqtId, String rqdId, String qty) throws DJException{
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.updateNotDeliver");
		String sql=PropertyUtils.getProperty("set_not_deliver");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(STORE_NUMBER.name(), storeNumber);
		paramMap.put(RQT_ID.name(), rqtId);
		paramMap.put(RQD_ID.name(), rqdId);
		paramMap.put(QTY.name(), qty);
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.updatePackageDetails");
		return super.update(storeNumber, sql,paramMap);
	}
	

	
	public int assignMPUUser(String storeNumber, String rqtId,
			String assignedUser,List<String> itemIds)
			throws DJException {
		
			logger.debug("Entering" ,"MPUWebServiceDAOImpl.assignMPUUser");
			logger.debug("Entering MPUWebServiceDAOImpl.assignMPUUser	requestDTO:"+itemIds, itemIds);
			String sql = null;
			//sql = PropertyUtils.getProperty("update_assigned_user1");
				
			sql=PropertyUtils.getProperty("update_assigned_user");
			ArrayList<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
			
			for(String rqdId:itemIds){
				HashMap<String, ? super Object> param = new HashMap<String, Object>();
				param.put(ASSIGNED_USER.name(), assignedUser);
				param.put(RQD_ID.name(),rqdId);
				param.put(RQT_ID.name(),rqtId);
				param.put(STORE_NUMBER.name(),storeNumber);
				paramList.add(param);
				logger.debug("Exiting" ,"MPUWebServiceDAOImpl.assignUser");
			}
			HashMap<String,Object> paramArray[]=new HashMap[paramList.size()];
			logger.debug("Existing" ,"MPUWebServiceDAOImpl.insertBulkActivities");
			return super.batchUpdate(storeNumber, sql, paramList.toArray(paramArray)).length;
		
		
	}

	public int insertPackageDetails(long rqtId, String user,
			String storeNumber, String fromLocation, String[] pkgNbr)
					throws DJException {
				
				logger.debug("Entering" ,"MPUWebServiceDAOImpl.insertPackageDetails");
				logger.debug("Entering MPUWebServiceDAOImpl.insertPackageDetails	rqt_id:", rqtId 
						+": user: "+ user +": storeNumber: "+ storeNumber +": fromLocation: "+ fromLocation +": pkgNbr: "+ pkgNbr );

				int size = pkgNbr.length;
			    Map<String, ? super Object> param[] = new HashMap[size];
			    for(int i=0;i<size;i++){
			    	Map<String, ? super Object> params = new HashMap<String, Object>();
					params.put(RQT_ID.name(), rqtId);
					params.put(FROM_LOCATION.name(), fromLocation);
					params.put(CREATED_BY.name(), user);
					params.put(STORE_NUMBER.name(), storeNumber);
					params.put(PKG_NBR.name(), pkgNbr[i]);
					params.put(TO_LOCATION.name(), null);
			    	param[i]=params;
			    }
			    
				String sql = PropertyUtils.getProperty("insert.into.package");
				logger.debug("Exiting" , "MPUWebServiceDAOImpl.insertPackageDetails");
				return super.batchUpdate(storeNumber, sql, param).length;
	}

	
	
	public List<ItemDTO> getItemListForOrder(String storeNumber, String requestId)throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.getItemListForOrder","storeNumber=="+storeNumber+"requestId=="+requestId);
		String sql=PropertyUtils.getProperty("select.from.metatransitem.with.salescheck1");
		sql=sql+MpuWebConstants.SPACE+PropertyUtils.getProperty("select.from.metatransitem.with.salescheck3");
		List<String> status=new ArrayList<String>();
		status.add(MpuWebConstants.OPENSTATUS);
		status.add(MpuWebConstants.COMPLETED);
		status.add(MpuWebConstants.CANCELLED);
		status.add(MpuWebConstants.AVAILABLE);
		status.add(MpuWebConstants.NOTAVAILABLE);
		status.add(MpuWebConstants.RESTOCK_PENDING);
		status.add(MpuWebConstants.WIP);
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
		paramMap.put(RQT_ID.name(), requestId);
		paramMap.put(MpuWebConstants.STATUS,status);
			logger.debug("the query for MPUWebServiceDAOImpl.getItemListForOrder",sql);
		return (List<ItemDTO>) query(storeNumber,sql, paramMap,new ItemDTOMapper());
	}
	
	public Map<String,Object> getRequestIdbySalescheck(String store,String salescheck) throws DJException{
		logger.debug("entering getRequestIdbySalescheck","store=="+store+"salescheck=="+salescheck);
		String sql = PropertyUtils.getProperty("get_requestId_from_salescheck");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Map<String,String> parameterMap=new HashMap<String,String>();
		parameterMap.put(MpuWebConstants.REQUEST_NUMBER,salescheck);
		parameterMap.put(MpuWebConstants.STORENUMBER,store);
		try{
			logger.debug("the query for getRequestIdbySalescheck",sql);
			List<Map<String,Object>> requestlist = (List<Map<String,Object>>)query(store, sql, parameterMap,new ColumnMapRowMapper());
			if(null!=requestlist && !requestlist.isEmpty()){
				if(null!=requestlist.get(0).get("rqt_id"))
				resultMap=requestlist.get(0);
			}
		}
		catch(EmptyResultDataAccessException eRse){
		logger.error("inside catch block", eRse);
		}
		logger.debug("exiting getRequestIdbySalescheck","");
		return resultMap;
		
	}
	
	public String getRqtIdForPostVoid(String store,String salescheck) throws DJException{
		logger.debug("entering getRqtIdForPostVoid","store=="+store+"salescheck=="+salescheck);
		String sql = PropertyUtils.getProperty("get_requestId_from_salescheck");
		//Map<String,Object> resultMap=new HashMap<String,Object>();
		String postVoidRqtId = null;
		Map<String,String> parameterMap=new HashMap<String,String>();
		parameterMap.put(MpuWebConstants.REQUEST_NUMBER,salescheck);
		parameterMap.put(MpuWebConstants.STORENUMBER,store);
		try{
			logger.debug("the query for getRqtIdForPostVoid",sql);
			List<Map<String,Object>> requestlist = (List<Map<String,Object>>)query(store, sql, parameterMap,new ColumnMapRowMapper());
			if(null!=requestlist && !requestlist.isEmpty()){
				for(Map<String,Object> resultMap : requestlist){
					if(null!=resultMap.get("rqt_id")){
						String rqtId =  String.valueOf(resultMap.get("rqt_id"));
						logger.info("the rqt ids are as ===", rqtId);

						Map<String,String> parameterMap1=new HashMap<String,String>();
						parameterMap1.put(MpuWebConstants.STORENUMBER,store);
						parameterMap1.put(RQT_ID.name(),rqtId);
						String pickUpquery = PropertyUtils.getProperty("select_post_void_count");
						logger.info("the query to determine eligibility of rqtId for postVoid is ", pickUpquery);
						int count =0;
						try{
							count = queryForInt(store, pickUpquery, parameterMap1);
						}catch(Exception ex){
							count =0;
						}
						
						if(count>0){
							postVoidRqtId = rqtId;
							break;
						}}}}
		}
		catch(EmptyResultDataAccessException eRse){
		logger.error("inside catch block", eRse);
		postVoidRqtId = null;
		}
		logger.debug("exiting getRequestIdbySalescheck","");
		return postVoidRqtId;
		
	}

	public boolean isUserActive(ItemDTO itemDTO) throws DJException {
		logger.debug( "Entering MPUWebServiceDAOImpl.isUserActive:", "itemDTO=="+itemDTO.toString());
		String mcpWebServiceUrl = PropertyUtils.getProperty("isUserActive.targetURL");
		mcpWebServiceUrl=mcpWebServiceUrl+"/"+itemDTO.getStoreNumber();
		boolean isUserActive =false;
		logger.debug("\n\n\n\n========URL For Checking User Activity ========", mcpWebServiceUrl);
		try{
			logger.debug( "the url for MPUWebServiceDAOImpl.isUserActive:", mcpWebServiceUrl);	
		ResponseEntity<Boolean> response = restTemplate.exchange(mcpWebServiceUrl, HttpMethod.GET, null, Boolean.class);
		logger.debug( "the response for MPUWebServiceDAOImpl.isUserActive:", response);
		isUserActive = response.getBody();
		logger.debug( "Exiting MPUWebServiceDAOImpl.isUserActive:", isUserActive);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isUserActive;
	}
	
	public int updateItemQty(String store,String rqdId,String qty,String status,String source,String dest,String createTime,String user) throws DJException{
		logger.info( "Entering MPUWebServiceImpl.updateItemQty:", "store=="+store+"rqdId=="+rqdId+
				"qty=="+qty+"status=="+status+"source=="+source+"dest=="+dest+"createTime=="+createTime+"user=="+user);
		String updateSql=PropertyUtils.getProperty("update_item_qty");
		Map<String ,Object> parameterMap=new HashMap<String, Object>();
		parameterMap.put(STORE.name(), store);
		parameterMap.put(RQD_ID.name(), rqdId);
		parameterMap.put(QTY.name(), qty);
		parameterMap.put(ITEM_STATUS.name(), status);
		parameterMap.put(FROM_LOCATION.name(), source);
		parameterMap.put(TO_LOCATION.name(), dest);
		parameterMap.put(CREATE_TIME.name(), createTime);
		parameterMap.put(ASSIGNED_USER.name(), user);
		logger.debug( "the query for MPUWebServiceImpl.updateItemQty:",updateSql );
		return update(store, updateSql,parameterMap);
	}
	
	public int cancelExpireRequestItems(String store,String reqNum,String status) throws DJException{
		String sql=PropertyUtils.getProperty("cancel_items"); 
		HashMap<String,String> parameterMap=new HashMap<String,String>();
		parameterMap.put("request_number", reqNum);
		parameterMap.put("item_status", status);
		return update(store, sql,parameterMap);
	}	
	
	public int cancelExpireRequest(String store,long rqt_id,String status ) throws DJException{
		String sql=PropertyUtils.getProperty("cancel_request");
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		parameterMap.put("rqt_id", rqt_id);
		parameterMap.put("req_status",status);
		int cancelCount=update(store,sql,parameterMap);
		return cancelCount;
		
	}
	
	
	private void updateOrderStatus(String store,long rqt_id,String status) throws DJException{
		String sql=PropertyUtils.getProperty("update_order_status");
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		parameterMap.put("rqt_id", rqt_id);
		parameterMap.put("store_number",store);
		update(store,sql,parameterMap);
	}
	
	
	public long getOpenRequestId(String store, String requestNum, List<String> status) throws DJException {
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		String updateSql = null;
		updateSql = PropertyUtils.getProperty("select_open_request");
		parameterMap.put("requestNum",requestNum);
		parameterMap.put("openStatus",status);
		long rqt_id=0;
		try{
		 rqt_id = queryForInt(store,updateSql,parameterMap);
		}
		catch(EmptyResultDataAccessException e){
			logger.error("Status of requets already changed.Its no longer available",requestNum);
		}
		parameterMap.clear();
		return rqt_id;
	}
	
	public void cancelRestockItemsOfRequest(String store, List<String> rqdList){
		logger.debug("Entering MPUWebServiceDAOImpl.cancelRestockItemsOfRequest\n",rqdList);
		try{
			String sql = PropertyUtils.getProperty("cancel_restock_Items");
			SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ArrayList<HashMap<String,Object>> itemParamList = new ArrayList<HashMap<String,Object>>();
			Date currentDate = Calendar.getInstance().getTime();
			String escalationTime = date.format(currentDate);
			for(String rqd : rqdList){
				HashMap<String,Object> param=new HashMap<String,Object>();
				param.put("rqd_id",rqd);
				param.put("escalationTime",escalationTime);
				itemParamList.add(param)	;
			}
			if(itemParamList.size()>0){
				Map<String,Object>[] listSize = new HashMap[itemParamList.size()];
				batchUpdate(store, sql, itemParamList.toArray(listSize));
			}
		}catch(Exception ex){
			logger.error("Error in MPUWebServiceDAOImpl.cancelRestockItemsOfRequest = ",ex);
		}
		logger.debug("Exiting HGServicesDAOImpl.cancelRestockItemsOfRequest\n","");
	}
	public PrintServiceResponse printLockerTicket(LockerKioskPrintVO lockerKioskPrintVO, String url)
			throws DJException {
		logger.debug("MPUWebServiceDAOImpl:printLockerTicket", "->Entering");
		HttpEntity<LockerKioskPrintVO> requestEntity=new HttpEntity<LockerKioskPrintVO>(lockerKioskPrintVO);
		ResponseEntity<PrintServiceResponse> enResponse=restTemplate.exchange(url, HttpMethod.POST,requestEntity, PrintServiceResponse.class);
		logger.debug("MPUWebServiceDAOImpl:printLockerTicket", "->Exiting");
		return enResponse.getBody();
		
	}

	public List<ItemDTO> getOrderItemList(String storeNumber,String requestId)	throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.getOrderItemList	storeNumber:",storeNumber +": requestId: "+requestId);
		String sql=PropertyUtils.getProperty("select.from.requestqueuedetails");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
		paramMap.put(RQT_ID.name(), requestId);
		return (List<ItemDTO>) query(storeNumber,sql, paramMap,new ItemDTOMapper());
	}	
	public OrderDTO getAllOrderDetails(String storeNumber, String requestId)throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.getOrderDetails	storeNumber:",storeNumber +": requestId: "+requestId);
		String sql=PropertyUtils.getProperty("select.all.from.metatrans");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
		paramMap.put(RQT_ID.name(), requestId);
		//paramMap.put(MpuWebConstants.STATUS,status);
		List<OrderDTO> orderDTOs = (List<OrderDTO>) query(storeNumber,sql, paramMap,new OrderDTOMapper());
		if(null != orderDTOs && orderDTOs.size()!=0){
			return orderDTOs.get(0);
		}else{
			return null;
		}
		
		
	}
	
		public Map<String,String> getHostServers() throws DJException{
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.getHostServers");
		return getAllDDRMeta();
	}
	
		public List<Object[]> getCOMExceptionList(String store,String date,String status) throws DJException{
		logger.debug( "Entering MPUWebServiceImpl.getCOMExceptionList:", "store=="+store+"date=="+date+"status=="+status);
		String sql = PropertyUtils.getProperty("select.from.com.exception");
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put(STORE_NUMBER.name(), store);
		paramMap.put(CREATED_DATE.name(), date);
		if("Worked".equalsIgnoreCase(status)){
			paramMap.put(IS_ACTIVE.name(), "N");	
		}else{
			paramMap.put(IS_ACTIVE.name(), "Y");
		}
		logger.debug( "the query for MPUWebServiceImpl.getCOMExceptionList:",sql );
		return (List<Object[]>) query(store,sql, paramMap,new COMExcecptionMapper());
	}

	public int updateItemDetailForCOM(String[] rqdIds, String user, String store)
			throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.updateItemDetailForCOM","");
		int size = rqdIds.length;
	    Map<String, ? super Object> param[] = new HashMap[size];
	    for(int i=0;i<size;i++){
	    	Map<String, ? super Object> params = new HashMap<String, Object>();
	    	params.put(RQD_ID.name(),rqdIds[i]);
	    	params.put(ASSIGNED_USER.name(),user);
	    	param[i]=params;
	    }
	    String sql = PropertyUtils.getProperty("update.into.item.for.com");
		logger.debug("Existing MPUWebServiceDAOImpl.updateItemDetailForCOM","");
		return super.batchUpdate(store, sql, param).length;
		
	}

	public int updateOrderDetailForCOM(String[] rqtIds, String store)
			throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.updateOrderDetailForCOM","");
		int size = rqtIds.length;
	    Map<String, ? super Object> param[] = new HashMap[size];
	    for(int i=0;i<size;i++){
	    	Map<String, ? super Object> params = new HashMap<String, Object>();
	    	params.put(RQT_ID.name(),rqtIds[i]);
	    	params.put(UPDATE_TIMESTAMP.name(),DJUtilities.dateToString(Calendar.getInstance().getTime(),MpuWebConstants.DATE_FORMAT));
	    	param[i]=params;
	    }
	    String sql = PropertyUtils.getProperty("update.into.trans.for.com");
		logger.debug("Existing MPUWebServiceDAOImpl.updateOrderDetailForCOM","");
		return super.batchUpdate(store, sql, param).length;
		
	}
	
	
	public int getItemQty(String store,String itemId)throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.getItemQty","store=="+store+"itemId=="+itemId);
		String sql = PropertyUtils.getProperty("select_item_qty_status");
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put(STORE.name(), store);
		paramMap.put(RQD_ID.name(), itemId);
		logger.debug("the query for MPUWebServiceDAOImpl.getItemQty",sql);
		return queryForInt(store, sql, paramMap);
		
	}
	
	public boolean checkMultipleItems(String strNum,String rqtId,String identifier) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.checkMultipleItems	strNum:",strNum 
				+": rqtId: "+rqtId +": identifier: "+identifier );
		String sql=PropertyUtils.getProperty("check_multi_item");
		boolean complete=false;
        HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		
		parameterMap.put(RQT_ID.name(),rqtId);
		parameterMap.put(STORE.name(),strNum);
		parameterMap.put(ITEM_IDENTIFIER.name(),identifier);
		int count=queryForInt(strNum, sql, parameterMap);
		if(count==1){
			complete=true;
		}
		logger.debug("Existing" ,"MPUWebServiceDAOImpl.checkMultipleItems");
		return complete;
	}

	public void clearPreviousPackage(String rqtId, String storeNo)
			throws DJException {
		String sql=PropertyUtils.getProperty("clear.previous.package");
		 HashMap<String,Object> parameterMap=new HashMap<String,Object>();
			
			parameterMap.put(RQT_ID.name(),rqtId);
			parameterMap.put(STORE_NUMBER.name(), storeNo);
			update(storeNo, sql,parameterMap);
			
	}
	
	public String getAssociateId(String store,String rqtId,String rqdId) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.getAssociateId:","store=="+store+"rqtId=="+rqtId+"rqdId=="+rqdId );
		String sql=PropertyUtils.getProperty("select_associate_id");
		 HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		 	parameterMap.put(RQD_ID.name(),rqdId);
			parameterMap.put(RQT_ID.name(),rqtId);
			parameterMap.put(STORE.name(), store);
			return (String)query(store, sql, parameterMap, new ResultSetExtractor<Object>() {
				 public Object extractData(ResultSet resultSet) {
					 try{
						 if(resultSet.next()){
							 return resultSet.getString(1);	 
						 }
					 }catch(Exception exception){
						 exception.printStackTrace();
						 
					 }
					 return null;
				}
			});
	}

	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO
	 * #getCustomerDataForPrint(java.lang.String, java.lang.String)
	 */
	
	public CustomerDTO getCustomerDataForPrint(String storeNumber, String requestId)
			throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.getCustomerDataForPrint	storeNumber:",storeNumber +": requestId: "+requestId);
		String sql=PropertyUtils.getProperty("select.from.metatransidentifier.print");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(MpuWebConstants.STORE_NUMBER, storeNumber);
		paramMap.put(RQT_ID.name(), requestId);
		/*List<IdentifierDTO> identifierDTOs = (List<IdentifierDTO>) query(storeNumber,sql, paramMap,new IdentifierDTOMapper());
		List<CustomerDTO> customerDTOs = MPUWebServiceUtil.convertIdentifierToCustomer(identifierDTOs);*/
		List<CustomerDTO> customerDTOs = (List<CustomerDTO>) query(storeNumber,sql, paramMap,new IdentifierDTOMapper());
		if(null != customerDTOs && !customerDTOs.isEmpty() ){//TODO change when a single response
			return customerDTOs.get(0);
		}else{
			return null;
		}
	}
	
	
	public Map<String,String> checkStatus(String storeId,String reqNum) throws DJException{
		String status = null;
		Map<String,String> paramMap = new HashMap<String, String>();
		Map<String,String> requestStatus = new HashMap<String, String>();
		paramMap.put("reqNum", reqNum);
		String sql = PropertyUtils.getProperty("check_status");
		if(null!=reqNum && reqNum!=""){
			try{
				status = queryForObject(storeId,sql, paramMap,String.class);
			}catch(Exception exception){
				throw FileExceptionHandler.logAndThrowDJException(exception, logger, "checkStatus", MpuWebConstants.CHK_STATUS_MSG);
			}
		}
		requestStatus.put("requestStatus", status);
		return requestStatus;
	}

	public PrintServiceResponse beepToPrinter(String url) throws DJException {
		try {
			logger.debug("MPUWebServiceDAOImpl:beepToPrinter", "->Entering");
			ResponseEntity<PrintServiceResponse> enResponse = (ResponseEntity<PrintServiceResponse>) DJServiceLocator.get(url, PrintServiceResponse.class);
			logger.debug("MPUWebServiceDAOImpl:beepToPrinter", "->Exiting" + enResponse.getBody().getWsResponeMeta().getErrorMessage());
			return enResponse.getBody();
		} catch (Exception exc) {
			logger.error("PrintServiceResponse", exc);
			return null;
		}
	}
	
	public int isCSMMissed(String storeNumber, String rqtId, String rqdId)	throws DJException {
		logger.debug("Entering MPUWebServiceDAOImpl.isCSMMissed	storeNumber:",storeNumber +": requestId: "+rqtId);
		String sql=PropertyUtils.getProperty("csm.miss.confirm.check");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(RQT_ID.name(), rqtId);
		paramMap.put(RQD_ID.name(), rqdId);
		paramMap.put(STORE.name(), storeNumber);
		return queryForInt(storeNumber, sql, paramMap);
	}

	
	public int isLayaway(String store, String salesCheck) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.isLayaway	storeNumber:",store +": salesCheck: "+salesCheck);
		String sql=PropertyUtils.getProperty("check_order_layaway");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(SALESCHECK.name(), salesCheck);
		try{
			return queryForInt(store, sql, paramMap);	
		}catch(Exception exception){
			return 0;
		}
		
		
	}
	
	public boolean isPickedUp(String storeNumber, String requestId, String itemId) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.isPickedUp	storeNumber:",storeNumber +": requestId: "+requestId);
		String sql=PropertyUtils.getProperty("select_pickup_activity");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(RQT_ID.name(), requestId);
		paramMap.put(RQD_ID.name(), itemId);
		
		int resultNo = update(storeNumber, sql, paramMap);
		if(resultNo==0){
			return false;
		}return true;
	}

	public int getActiveItemCount(String storeNumber, String rqtId) throws DJException{
		logger.debug("Entering MPUWebServiceDAOImpl.getActiveItemCount	storeNumber:",storeNumber +": rqtId: "+rqtId);
		String sql=PropertyUtils.getProperty("select_active_item_count");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(RQT_ID.name(), rqtId);
		return queryForInt(storeNumber, sql, paramMap);	
	}

	public String printCouponBySBO(PrintKoiskVO printKoiskVO,
			String url) throws DJException {
		logger.debug("MPUWebServiceDAOImpl:printCouponBySBO", "->Entering");
		String status = null;
		ResponseEntity<String> sboResponse=null;
		HttpEntity<PrintKoiskVO> requestEntity = new HttpEntity<PrintKoiskVO>(printKoiskVO);
		sboResponse = restTemplate.postForEntity(url, requestEntity,String.class);
		if ( sboResponse!= null) {
			HttpStatus statusCode = sboResponse.getStatusCode();
			logger.debug("Response from printer "+statusCode," while printing coupon ");
			if(statusCode == HttpStatus.OK){
				status="Success";
			}else {
				status="Failure";
			}
		}
		logger.debug("MPUWebServiceDAOImpl:printCouponBySBO", "->Exiting");
		return status;
	}

	public int updateCardSwipedFlagInBlob(String rqtId, String storeNumber,
			String originalJson) throws DJException {
		logger.debug("Entering MPiUWebServiceDAOImpl.updateCardSwipedFlagInBlob"," rqtId: "+rqtId + " storeNumber: "+storeNumber + " originalJson: "+originalJson);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		
    	params.put(RQT_ID.name(), rqtId);
    	params.put(ORIGINALJSON.name(), originalJson);
		params.put(MODIFIED_BY.name(), MpuWebConstants.ORDER_ADAPTOR);	
			
		//String sql="UPDATE REQUEST_QUEUE_TRANS SET originalJson =:ORIGINALJSON, update_timestamp = NOW(), modified_by =:MODIFIED_BY WHERE rqt_id =:RQT_ID";
		String sql = PropertyUtils.getProperty("update.CardSwiped.Flag.In.Trans");
		logger.debug("Exiting" ,"MPUWebServiceDAOImpl.createOrderDTO sql : " + DJUtilities.createSQLCommand(sql, params));
		return super.update(storeNumber, sql, params); 
	}
	
	public String isPlaformStore(String storeNumber) {
		logger.info("Entering MPUWebServiceDAOImpl.isPlaformStore","");
		String isPlatform="false";
		String ui_server=null;
		try {
		String sql=PropertyUtils.getProperty("check.platform.store");
        HashMap<String,Object> parameterMap=new HashMap<String,Object>();	
		parameterMap.put("STORE",storeNumber);
		logger.info("the query for MPUWebServiceDAOImpl.isPlaformStore	:",sql);
		ui_server = (String)query(storeNumber, sql, parameterMap, new ResultSetExtractor<Object>() {
				public Object extractData(ResultSet resultSet) {
				 try{
					 if(resultSet.next()){
						 return resultSet.getString(1);
					 }
				 }catch(Exception exception){
					 exception.printStackTrace();
					 
				 }
				 return null;
			}
		});
		if(ui_server == null){
			isPlatform="false;";
		}else{
			isPlatform="true";
			isPlatform = isPlatform + ";" + ui_server;
		}
		} catch (DJException e) {
			logger.info("Exception isPlatform" ,e.getMessage());
		}
		logger.info("Existing" ,"MPUWebServiceDAOImpl.isPlaformStore");
		return isPlatform;
	}

	public String printTICouponByAdaptor(OrderAdaptorRequest request, String url) throws DJException {
		logger.info("ServerURL for printTICouponByAdaptor ...",url);
		ResponseEntity<OrderAdaptorResponse> response = null;			
		HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
		response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, OrderAdaptorResponse.class);
		if (response != null) {
			HttpStatus statusCode = response.getStatusCode();
			if(statusCode == HttpStatus.OK){
				if (response.getBody().getStatus() != null) {
					return String.valueOf(response.getBody().getStatus().getCode());
				}
			}
		}
		return null;
	}

	public String printLockerTicketByAdaptor(OrderAdaptorRequest request, String url)
			throws DJException {
		logger.info("ServerURL for printLockerTicketByAdaptor ...",url);
		ResponseEntity<OrderAdaptorResponse> response = null;			
		HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
		response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, OrderAdaptorResponse.class);
		if (response != null) {
			HttpStatus statusCode = response.getStatusCode();
			if(statusCode == HttpStatus.OK){
				if (response.getBody().getStatus() != null) {
					return String.valueOf(response.getBody().getStatus().getCode());
				}
			}
		}
		return null;
	}

	
	public List<DDMeta> getDDRMetaCache(){
		logger.info("Inside" ,"MPUWebServiceDAOImpl.getDDRMetaCache");
		return getAllDDRMetaCache();
	}
	
	public boolean refreshDDRMetaCache(){
		logger.info("Inside" ,"MPUWebServiceDAOImpl.refreshDDRMetaCache");
		return refreshAllDDRMetaCache();
	}
	
	public String getAppServer(String storeNumber) throws DJException{ 
		logger.info("Entering MPUWebServiceDAOImpl.getAppServer", ""); 
		String serverFound = "false";
		String appServer = ""; 
		 try { 
			 String sql = PropertyUtils.getProperty("get.appserver.store"); 
		 HashMap<String, Object> parameterMap = new HashMap<String, Object>(); 
		 parameterMap.put("STORE", storeNumber); 
		 logger.info("the query for MPUWebServiceDAOImpl.getAppServer\t:", sql); 
		 appServer = (String)query(storeNumber, sql, parameterMap, new ResultSetExtractor<Object>() {
			 public Object extractData(ResultSet resultSet) {
				 try{
					 if(resultSet.next()){
						 return resultSet.getString(1);	 
					 }
				 }catch(Exception exception){
					 exception.printStackTrace();
					 
				 }
				 return null;
			}
		});
		 if(appServer == null) { 
		 serverFound = "false;";
		 } else { 
		 serverFound = "true";
		 serverFound = (new StringBuilder()).append(serverFound).append(";").append(appServer).toString();
		 } } 
		 catch(DJException e) {
		 logger.info("Exception getAppServer", e.getMessage()); 
		 } 
		 logger.info("Existing", "MPUWebServiceDAOImpl.getAppServer");
		 return serverFound;
		 }
	
	public String getAppServerPlatform(String storeNumber) throws DJException{ 
		logger.info("Entering MPUWebServiceDAOImpl.getAppServer", ""); 
		String serverFound = "false";
		String appServer = ""; 
		 try { 
			 String sql = PropertyUtils.getProperty("get.appserver.store"); 
		 HashMap<String, Object> parameterMap = new HashMap<String, Object>(); 
		 parameterMap.put("STORE", storeNumber); 
		 logger.info("the query for MPUWebServiceDAOImpl.getAppServer\t:", sql); 
		 appServer = (String)query(storeNumber, sql, parameterMap, new ResultSetExtractor<Object>() {
			 public Object extractData(ResultSet resultSet) {
				 try{
					 if(resultSet.next()){
						 return resultSet.getString(1)+";"+resultSet.getString(2);	 
					 }
				 }catch(Exception exception){
					 exception.printStackTrace();
					 
				 }
				 return null;
			}
		});
		 if(appServer == null) { 
		 serverFound = "false;";
		 } else { 
		 serverFound = "true";
		 serverFound = (new StringBuilder()).append(serverFound).append(";").append(appServer).toString();
		 } } 
		 catch(DJException e) {
		 logger.info("Exception getAppServer", e.getMessage()); 
		 } 
		 logger.info("Existing", "MPUWebServiceDAOImpl.getAppServer");
		 return serverFound;
		 }
	
	
	 public boolean gethealthCheck() throws DJException {
		 List<NamedParameterJdbcTemplate> namedParameterJdbcTemplates = getNamedParameterJdbcTemplates();
		 if(!CollectionUtils.isEmpty(namedParameterJdbcTemplates)){
			 String sql ="select now()";
//			 try{
				boolean flag = (Boolean)namedParameterJdbcTemplates.get(0).query(sql, new ResultSetExtractor<Object>() {			
							public Object extractData(ResultSet resultSet)  {
							 try{
								 if(resultSet.next()){
									 return true;	 
								 }
							 }catch(Exception exception){
								 logger.info("in catch block","health check");
							 }
							 return false;
						}});
				
				if(flag){
					return true;
				}
/*			 }
			 catch(Exception exception){
				 return false;
			 }
*/			 
		 }else{
			 return false; 
		 }
		 return false;
	 }

	 
	 public int updateDtmFlag() throws DJException {
		 List<NamedParameterJdbcTemplate> namedParameterJdbcTemplates = getNamedParameterJdbcTemplates();
		 if(!CollectionUtils.isEmpty(namedParameterJdbcTemplates)){
			 String sql = PropertyUtils.getProperty("update.dtm.flag"); 
			// String sql ="UPDATE DJ_BUSINESS_DASHBOARD SET DTM_STATUS='Y', DTM_LASTUPDATE_TIME = NOW()";
//			 try{
			 Map<String,String> paramMap=new HashMap<String,String>();
			 return namedParameterJdbcTemplates.get(0).update(sql, paramMap);
				
	 }else{
		 return 0;
	 }
	 }
	 
	/**
	 * To get store information required during NPOS update of 
	 * ReturnIn5 requests
	 * @author nkhan6
	 */
	public StoreInfo getStoreInformation(String storeNumber){
		/*
		 * Check if the store number is of five digit
		 * if not apply left padding to it 
		 */
		if(null!=storeNumber && storeNumber.length()<5){
			/*
			 * Cannot import the package as spring stringutils is already imported and used
			 */
			storeNumber = org.apache.commons.lang3.StringUtils.leftPad(storeNumber, 5, '0');
		}
		StoreInfo storeInfo = new StoreInfo();;
		String url=PropertyUtils.getProperty("store_service_url");
		logger.info("getStoreInformation","Store URL:" + url);	
		
		
		try{
		    ResponseEntity<StoreFinderResponsev2> response = (ResponseEntity<StoreFinderResponsev2>)DJServiceLocator.get(url+"/"+storeNumber, StoreFinderResponsev2.class);
		    if(null != response &&  null != response.getBody()){
				List<StoreInfoVo> storeList = (List<StoreInfoVo>)response.getBody().getStoreDetailsList();
				if(null != storeList && !storeList.isEmpty()){
					StoreInfoVo storeInfoList=storeList.get(0);
					if(storeInfoList!= null){
						
						storeInfo.setAddress1(storeInfoList.getStoreInfo().getAddress());
						storeInfo.setCity(storeInfoList.getStoreInfo().getCity());
						storeInfo.setPhone(storeInfoList.getStoreInfo().getPhNbr());
						storeInfo.setState(storeInfoList.getStoreInfo().getState());
						storeInfo.setStoreFormat(storeInfoList.getStoreInfo().getUnitType());
						if("KmartRetail".endsWith(storeInfoList.getStoreInfo().getUnitType())){
							storeInfo.setStoreType("KMART");
							storeInfo.setType("KMART");
						}else{
							storeInfo.setStoreType("SEARS");
							storeInfo.setType("SEARS");
						}			
						storeInfo.setZipCode(storeInfoList.getStoreInfo().getZip());
						storeInfo.setNumber(Integer.parseInt(storeNumber));
					}
					
				}
		     }
	    }catch(Exception e){
	    	logger.error("Exception in getStoreInformation", e);
	    }
		
		return storeInfo;
	}
	
	public List<Map<String,Object>> getKioskDetailList(final String storeNo)throws DJException {
		logger.info("Entering MPUWebServiceDAOImpl.getKioskDetailList	storeNo:",storeNo);
		String sql=PropertyUtils.getProperty("select.from.mpudirect");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("store_number", storeNo);
		
		logger.info("store_number==", storeNo);
		logger.info("sql==", sql);
		
		List<Map<String,Object>> kioskDetailList = null;
		try {
			kioskDetailList= namedParameterJdbcTemplateWFD.queryForList(sql, paramMap);
		
			//return (List<KioskDetailDTO>) query(storeNo,sql, paramMap,new KioskDetailDTOMapper());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Exiting" ,"MPUWebServiceDAOImpl.getKioskDetailList");
		return kioskDetailList;
	}
	
	public String getAssociateId(){
		String associateId=(String)MDC.get("associateId");
		logger.info("associateId from MDC", associateId);
		if(associateId==null||"".equals(associateId)){
			associateId="000075";
		}
		return associateId;

	}
	
	public List<ItemDTO> getAllItemsForPostVoid(String store,String rqtId) throws DJException{
		String sql  = PropertyUtils.getProperty("select.from.metatransitem.with.salescheck1");
		sql = sql +" "+PropertyUtils.getProperty("select.from.metatransitem.with.salescheck3.withoutStatus");
		HashMap<String, Object> parameterMap = new HashMap<String, Object>(); 
		parameterMap.put(MpuWebConstants.STORE_NUMBER, store);
		parameterMap.put(RQT_ID.name(), rqtId);
		return (List<ItemDTO>) query(store,sql, parameterMap,new ItemDTOMapper());
		
	}

	public List<String> getRqtIdList(String storeNum,
			List<String> salescheckList) throws DJException {
		// TODO Auto-generated method stub
		String sql = "select rqt_id from request_queue_trans where store_number=:storeNum and salescheck in (:salescheckList)";
		HashMap<String, Object> parameterMap = new HashMap<String, Object>(); 
		parameterMap.put("storeNum", storeNum);
		parameterMap.put("salescheckList", salescheckList);
		return (List<String>) query(storeNum,sql, parameterMap,new RowMapper() {
		      public Object mapRow(ResultSet resultSet, int i) throws SQLException {
		          return resultSet.getString(1);
		        }
		      });
		
	}

	public int updateEsacaltionList(String store,List<ItemDTO> escalationUpdatedItemList)
			throws DJException {
		// TODO Auto-generated method stub
		logger.info("Entering MPUWebServiceDAOImpl.updateEsacaltionList","");
		int size = escalationUpdatedItemList.size();
		Map<String, ? super Object> param[] = new HashMap[size];
		int i=0;
		for(ItemDTO item:escalationUpdatedItemList){
			Map<String, ? super Object> params = new HashMap<String, Object>();
			params.put("escalation", item.getEscalation());
			params.put("escalation_time", item.getEscalationTime());
			params.put("rqd_id", item.getRqdId());
			
			param[i++]=params;
		}
		
		String sql = PropertyUtils.getProperty("update.item.escalation");
		
		logger.info("Existing MPUWebServiceDAOImpl.updateEsacaltionList","");
		
		return super.batchUpdate(store, sql, param).length;
		
	}

	public int updateFinalResponseInDb(Order finalOrderResponse,String rqtId,String storeNum)
			throws DJException, JsonGenerationException, JsonMappingException, IOException {

		logger.info("Entering updateFinalResponseInDb","");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
		mapper.setDateFormat(dateFormat);
		
		String finalResponse = mapper.writeValueAsString(finalOrderResponse);
		String sql = PropertyUtils.getProperty("update.final.response");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("finalResponse", finalResponse);
		paramMap.put("rqtId", rqtId);
		paramMap.put("storeNum", storeNum);
		
		return super.update(storeNum, sql, paramMap);
		
	}

	public int setResponseStatusFailed(String storeNum, String orderNumber)
			throws DJException {
		String sql = PropertyUtils.getProperty("update.finalResponse.status");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("storeNum", storeNum);
		paramMap.put("orderNumber", orderNumber);
		return super.update(storeNum, sql, paramMap);
	}

	/* Gaming Enhancement no. 5 start*/
/*	public void updateHFMbin() throws DJException{
		String sql  = PropertyUtils.getProperty("update_sameDay_hfm_bin");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(MpuWebConstants.ITEM_STATUS,MpuWebConstants.STAGED_STATUS);
		param.put(MpuWebConstants.REQUEST_STATUS, MpuWebConstants.COMPLETED);
		param.put(MpuWebConstants.REQUEST_TYPE, MpuWebConstants.STAGE);
		
		List<NamedParameterJdbcTemplate> namedParameterJdbcTemplates = getNamedParameterJdbcTemplates();
		int noOfRows = 0;
		for (NamedParameterJdbcTemplate namedParameterJdbcTemplate : namedParameterJdbcTemplates) {
			noOfRows+=namedParameterJdbcTemplate.update(sql, param);			
		}
		
		logger.info("updateHFMbin : ", noOfRows +"rows updated");
	}
	*/
	/* Gaming Enhancement no. 5 start*/

}


