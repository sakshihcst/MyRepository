package com.searshc.mpuwebservice.dao.impl;


import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ACTIVE_KIOSK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ACTIVE_USER_FLAG;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CAPTAIN_FLAG;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CUSTOMER_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DATE_FROM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DATE_TO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DISTRICT;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.END_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.INRANGE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LOGGED_IN_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LOGGED_OUT_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.MOD_FLAG;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKEDUP_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_REQUEST_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REGION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQ_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SOCKET_HOST;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.START_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.USER_FNAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.USER_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.USER_LNAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.WEBSOCKET_PORT;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

import com.sears.dj.common.dao.DJRepository;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.sears.dj.common.util.DJUtilities;
import com.searshc.mpuwebservice.bean.ActivityUserEntity;
import com.searshc.mpuwebservice.bean.MpuStaticParamEntity;
import com.searshc.mpuwebservice.bean.SellUnitDTO;
import com.searshc.mpuwebservice.bean.ShopInReportDTO;
import com.searshc.mpuwebservice.dao.MCPDBDAO;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.mapper.ActiveUserMapper;
import com.searshc.mpuwebservice.mapper.MpuStaticParamMapper;
import com.searshc.mpuwebservice.mapper.SellUnitMapper;
import com.searshc.mpuwebservice.mapper.ShopinReportMapper;
import com.searshc.mpuwebservice.util.PropertyUtils;
import com.searshc.mpuwebservice.util.SHCDateUtils;
@DJRepository("mCPDBDAO")
public class MCPDBDAOImpl extends DJMPUCommonDAO implements MCPDBDAO {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateWFD;
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(MCPDBDAOImpl.class);
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	
	public List<Map<String,Object>> getModActiveEntity(String storeNo,Integer mod) throws DJException {
		
		logger.info("getModActiveEntity" ,"Enter getModActiveEntity  -- storeNo : " + storeNo + " -- mod : " + mod);
		Timestamp logouttime = Timestamp.valueOf(PropertyUtils.getProperty("com.mpu.defaulttimestamp"));
		
		//Timestamp logouttime = Timestamp.valueOf("2000-01-01 00:00:00");
		HashMap<String,Object> parameterMap = new HashMap<String,Object>();
		
		String sql = PropertyUtils.getProperty("select.from.modactive");
		
		parameterMap.put(STORE_NUMBER.name(), DJUtilities.leftPadding(storeNo, 5));
		parameterMap.put(MOD_FLAG.name(), mod);
		parameterMap.put(LOGGED_OUT_TIME.name(), logouttime);
		
		logger.info("getModActiveEntity", "getModActiveEntity Sql : " + DJUtilities.createSQLCommand(sql, parameterMap));
		
		List<Map<String,Object>>  modActiveEntityList = namedParameterJdbcTemplateWFD.queryForList(sql, parameterMap);		
		
		return modActiveEntityList;
}
	
	// Starts Sakshi
	
	public List<Map<String,Object>> getKioskDetailList(final String storeNo)throws DJException {
		logger.info("Entering MPUWebServiceDAOImpl.getKioskDetailList	storeNo:",storeNo);
		String sql=PropertyUtils.getProperty("select.from.kiosk");
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
	
	
	/**This method is used to get list of kiosk
	 * @param storeNum
	 * @throws Exception
	 */
	public List<Map<String, Object>> getKioskList(String storeNum) throws DJException {
		
		logger.info("getKioskList", "Entering getKioskList DAO storeNum : " + storeNum);
		
		Map<String,String> paramMap = new HashMap<String,String>();
		
		paramMap.put("STORE_NUM", storeNum);
		String sql = PropertyUtils.getProperty("select.from.mcpstorekiosk.kiosklist"); 
		
		logger.info("getAllItemsForPickUp", "getAllItemsForPickUp Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		List<Map<String, Object>> kioskList =(List<Map<String, Object>>) namedParameterJdbcTemplateWFD.queryForList(sql, paramMap);
		
		return kioskList;
	}
	
	// Ends Sakshi
	//Starts - Gaurav
	
	//Insert ActivityUserEntity
	/*INSERT INTO mcp_active_user (user_id,store_no,captain_flag,active_kiosk,inrange,socket_host,logged_in_time,logged_out_time,mod_flag,web_socket_port,user_fname,user_lname,active_user_flag,associate_id)
	VALUES (:USER_ID,:STORE_NUMBER,:CAPTAIN_FLAG,:ACTIVE_KIOSK,:INRANGE,:SOCKET_HOST,:LOGGED_IN_TIME,:LOGGED_OUT_TIME,:MOD_FLAG,:WEBSOCKET_PORT,:USER_FNAME,:USER_LNAME,:ACTIVE_USER_FLAG,:ASSOCIATE_ID);*/
	public int insertUser(ActivityUserEntity activeUser)  throws DJException  {
		logger.info("insertUser Inside insertUser activeUser: ",activeUser);
		int noOfRows = 0;
		java.util.Date date= new java.util.Date();
		Map<String, ? super Object> params = new HashMap<String, Object>();
		if (null != activeUser) {
			params.put(USER_ID.name(), activeUser.getUserId());
			params.put(STORE_NUMBER.name(), activeUser.getStoreNo());
			params.put(CAPTAIN_FLAG.name(), activeUser.isCaptainFlag());
			params.put(ACTIVE_KIOSK.name(), activeUser.getActiveKiosk());
			params.put(INRANGE.name(), activeUser.isInrange());
			params.put(SOCKET_HOST.name(), activeUser.getSocketHost());
			
			//Date date=new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String loggedInTime=null;
			
			 Map<String,Object> storeInfo=mpuWebServiceDAOImpl.getStoreDetails(activeUser.getStoreNo());
		try {
			loggedInTime=SHCDateUtils.convertFromLocalToStore(dateFormat.format(date),(String) storeInfo.get("timeZone"));
			} catch (Exception e) {
				logger.error("insertUser", "insertUser exception : " + e);
				DJException djx = new DJException();
				djx.setMessage(e.getMessage());
				throw djx;
			}
			params.put(LOGGED_IN_TIME.name(), null != loggedInTime ? loggedInTime : new Timestamp(date.getTime()));
			params.put(LOGGED_OUT_TIME.name(), null != activeUser.getLoggedOutTime() ? activeUser.getLoggedOutTime() : "2000-01-01 00:00:00");
			params.put(MOD_FLAG.name(), activeUser.isModFlag());
			params.put(WEBSOCKET_PORT.name(), activeUser.getWebSocketPort());
			params.put(USER_FNAME.name(), activeUser.getUserFname());
			params.put(USER_LNAME.name(), activeUser.getUserLname());
			params.put(ACTIVE_USER_FLAG.name(), activeUser.getActiveUserFlag());
			params.put(ASSOCIATE_ID.name(), null != activeUser.getAssociateId() ? activeUser.getAssociateId() : "000000");

			String sql = PropertyUtils.getProperty("insert.into.mcpActiveUser"); 
			
			logger.info("insertUser", "Sql : " + DJUtilities.createSQLCommand(sql, params));
			noOfRows = namedParameterJdbcTemplateWFD.update(sql, params);
		}
		return noOfRows;
	}

	//Get ActivityUserEntity on the basis of USER_ID
	public ActivityUserEntity getActiveUserForUserId(String userId,String storeNumber) throws DJException {
		logger.info("getActiveUserForUserId", "Entering getActiveUserForUserId DAO userId : " + userId);
		
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(USER_ID.name(), userId);
		paramMap.put("store_number",storeNumber);
		String sql = PropertyUtils.getProperty("select.from.mcpActiveUser"); 
		
		logger.info("getActiveUserForUserId", "Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		List<ActivityUserEntity> result = (List<ActivityUserEntity>)namedParameterJdbcTemplateWFD.query(sql, paramMap, new ActiveUserMapper());
		
		return (null != result && result.size() > 0) ? result.get(0) : null;
	}

	//Update ACTIVE_USER_FLAG
	public int updateActiveUserFlag(String userId, String loggedInTime, String activeUserFlag) throws DJException {
		logger.info("updateActiveUserFlag", "Entering updateActiveUserFlag DAO userId & loggedInTime : " + userId + "  & " + loggedInTime);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		params.put(USER_ID.name(), userId);
		params.put(LOGGED_IN_TIME.name(), loggedInTime);
		params.put(ACTIVE_USER_FLAG.name(), activeUserFlag);
		String sql = PropertyUtils.getProperty("update.activeUserFlag"); 
		
		logger.info("updateActiveUserFlag", "Sql : " + DJUtilities.createSQLCommand(sql, params));
		return namedParameterJdbcTemplateWFD.update(sql, params);
	}

	//Update MOD_FLAG
	public int updateModFlag(String userId, String loggedInTime, boolean modFlag) throws DJException {
		logger.info("updateModFlag", "Entering updateModFlag DAO userId & loggedInTime : " + userId + "  & " + loggedInTime);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		params.put(USER_ID.name(), userId);
		params.put(LOGGED_IN_TIME.name(), loggedInTime);
		params.put(MOD_FLAG.name(), modFlag);
		String sql = PropertyUtils.getProperty("update.modFlag"); 
		
		logger.info("updateModFlag", "Sql : " + DJUtilities.createSQLCommand(sql, params));
		return namedParameterJdbcTemplateWFD.update(sql, params);
	}

	//Update LOGGED_OUT_TIME
	public int updateLoggedOutTime(String userId, String activeUser, String loggedOutTime) throws DJException {
		logger.info("updateLoggedOutTime", "Entering updateLoggedOutTime DAO userId & loggedInTime : " + userId + "  & " + activeUser);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		params.put(USER_ID.name(), userId);
		params.put(ACTIVE_USER_FLAG.name(), activeUser);
		params.put(LOGGED_OUT_TIME.name(), loggedOutTime);
		String sql = PropertyUtils.getProperty("update.loggedOutTime"); 
		
		logger.info("updateLoggedOutTime", "Sql : " + DJUtilities.createSQLCommand(sql, params));
		return namedParameterJdbcTemplateWFD.update(sql, params);
	}
	//Ends - Gaurav	
	
	
	@SuppressWarnings("deprecation")
	public int getMaxShopInPickupId() throws DJException {
		
		logger.info("getMaxShopInPickupId", "Entering getMaxShopInPickupId MCPDBDAOImpl ");
		
		String sql = PropertyUtils.getProperty("select.max.shopin.pickupId"); 
		
		logger.info("getAllItemsForPickUp", "getAllItemsForPickUp Sql : " + DJUtilities.createSQLCommand(sql,null));
		
		return namedParameterJdbcTemplateWFD.queryForInt(sql, new HashMap<String,Object>());	
	}
	
	@SuppressWarnings("unchecked")
	public int insertShopinReportRecords(List<ShopInReportDTO> ShopInReportDTOList, String storeNum) throws DJException {
		
		logger.info("insertShopinReportRecords", "Entering insertShopinReportRecords MCPDBDAOImpl ");
		
		String sql = PropertyUtils.getProperty("insert.shopin.report.record");
		
		Map<String,Object> paramArray[] = new HashMap[ShopInReportDTOList.size()];
		
		for(int i=0;i<ShopInReportDTOList.size();i++){			
			HashMap<String, ? super Object> param = new HashMap<String, Object>();
			param.put(SALESCHECK.name(), ShopInReportDTOList.get(i).getSalescheck());
			param.put(STORE_NUMBER.name(), ShopInReportDTOList.get(i).getStoreNo());
			param.put(DISTRICT.name(), ShopInReportDTOList.get(i).getDistrict());
			param.put(REGION.name(), ShopInReportDTOList.get(i).getRegion());
			param.put(PICKUP_REQUEST_TYPE.name(), ShopInReportDTOList.get(i).getPickupRequestType());
			param.put(PICKUP_ID.name(), ShopInReportDTOList.get(i).getPickupId());
			param.put(REQ_QUANTITY.name(), ShopInReportDTOList.get(i).getReqQuantity());
			param.put(START_TIME.name(), ShopInReportDTOList.get(i).getStartDate());
			param.put(RQT_ID.name(), ShopInReportDTOList.get(i).getWorkId());
			paramArray[i]=(Map<String, Object>) param;
		}
		
		int []resultArray = namedParameterJdbcTemplateWFD.batchUpdate(sql, paramArray);
		return resultArray.length;
	}	
	
	public int updateShopinReportRecord(ShopInReportDTO shopInReportDTO) throws DJException {
		
		logger.info("updateShopinReportRecord", "Entering updateShopinReportRecord MCPDBDAOImpl for SCN: "+ shopInReportDTO.getSalescheck());
		
		String sql = PropertyUtils.getProperty("update.shopin.report.record"); 
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		
		params.put(SALESCHECK.name(), shopInReportDTO.getSalescheck());		
		params.put(PICKEDUP_QUANTITY.name(), shopInReportDTO.getPickedupQuantity());
		params.put(STATUS.name(), shopInReportDTO.getStatus());
		params.put(CUSTOMER_NAME.name(), shopInReportDTO.getCustomerName());
		params.put(ASSOCIATE_NAME.name(), shopInReportDTO.getAssociateName());
		params.put(END_TIME.name(), shopInReportDTO.getEndDate());	
		
		logger.info("updateShopinReportRecord", "Sql : " + DJUtilities.createSQLCommand(sql, params));
		
		return namedParameterJdbcTemplateWFD.update(sql, params);
	}
	
	@SuppressWarnings("unchecked")
	public List<SellUnitDTO> loadRecordsFromSellUnitTable() throws DJException{
		
		logger.info("getRecordsFromSellUnitTable", "Entering getRecordsFromSellUnitTable MCPDBDAOImpl");
		
		String sql = PropertyUtils.getProperty("load.sellunit.records");		
	
		List<SellUnitDTO> sellUnitDTOs=namedParameterJdbcTemplateWFD.query(sql, new HashMap(), new SellUnitMapper());
		
		logger.info("getRecordsFromSellUnitTable", "Exit getRecordsFromSellUnitTable MCPDBDAOImpl");
		
		return sellUnitDTOs;		
	}
	
	
	public SellUnitDTO getSellUnitRecordByStoreNo(String storeNumber) throws DJException {
		
		logger.info("getSellUnitRecordByStoreNo", "Entering getSellUnitRecordByStoreNo MCPDBDAOImpl for storeNumber: "+storeNumber);
		
		String sql = PropertyUtils.getProperty("get.sellunit.record");	
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		
		params.put(STORE_NO.name(), storeNumber);
		
		List<SellUnitDTO> sellUnitDTOs = namedParameterJdbcTemplateWFD.query(sql, params, new SellUnitMapper());
		
		logger.info("getSellUnitRecordByStoreNo", "Exit getSellUnitRecordByStoreNo MCPDBDAOImpl for storeNumber: "+storeNumber);
	
		return sellUnitDTOs.get(0);
	}
	
	public List<Map<String, Object>> getIsLockerEligibleFlag(String storeNumber, String kioskName) throws DJException {
		
		logger.error("getIsLockerEligibleFlag", "Enter getIsLockerEligibleFlag storeNumber : " + storeNumber + " -- kioskName : " + kioskName);
		
		Map<String,String> paramMap = new HashMap<String,String>();

		String sql = PropertyUtils.getProperty("select.from.locker_flag");
		
		//paramMap.put("KIOSK_NAME", kioskName);
		paramMap.put("STORE_NUMBER", storeNumber);
		
		//logger.info("getParentRqtId", "getParentRqtId Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return (List<Map<String, Object>>) namedParameterJdbcTemplateWFD.queryForList(sql, paramMap);
		
		//return null;
	}
	
	public List<ShopInReportDTO> fetchRecordsForShopinReport(String dateFrom, String dateTo, String storeNo, String region, String district)
			throws DJException {		
		
		logger.info("fetchRecordsForShopinReport", "Entering fetchRecordsForShopinReport MCPDBDAOImpl for storeNumber: "+storeNo);
        
        String sql = PropertyUtils.getProperty("get.shopinreport");
        
        Map<String, ? super Object> params = new HashMap<String, Object>();
        
        if(storeNo != null && !"00000".equals(storeNo) && "00000".equals(region) && "00000".equals(district)){
              sql = sql+PropertyUtils.getProperty("get.shopinreport.byStoreAndDate");
              params.put(STORE_NO.name(), storeNo);              
        }
        if(region != null && !"00000".equals(region)  && "00000".equals(district)){
              sql = sql+PropertyUtils.getProperty("get.shopinreport.byRegionAndDate");
              params.put(REGION.name(), region);            
        }
        if (district != null && !"00000".equals(district)){
              sql = sql+PropertyUtils.getProperty("get.shopinreport.byDistrictAndDate");
              params.put(DISTRICT.name(), district);
        }
        params.put(DATE_FROM.name(), dateFrom);
        params.put(DATE_TO.name(), dateTo);
        logger.info("SQL: ", sql);
        
        List<ShopInReportDTO> shopinReportList = namedParameterJdbcTemplateWFD.query(sql, params, new ShopinReportMapper());
        
        return shopinReportList;
		
	}
	
	
	public int getActiveUserCount(String storeNumber) throws DJException {
		logger.info("getActiveUser", "Entering getActiveUser DAO");
		
		Map<String,String> paramMap=new HashMap<String,String>();
		
		paramMap.put(STORE_NUMBER.name(),storeNumber);
		String sql = PropertyUtils.getProperty("select.all.from.mcpActiveUser"); 
		
		logger.info("getActiveUser", "Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		return namedParameterJdbcTemplateWFD.queryForInt(sql, paramMap); 
	}
	
	public List<ActivityUserEntity> getAllModActiveUsers() throws Exception {
		logger.info("getAllModActiveUsers", "Entering getAllModActiveUsers DAOImpl");
		String sql = PropertyUtils.getProperty("get.active.modusers");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(ACTIVE_USER_FLAG.name(), "Y");
		paramMap.put(MOD_FLAG.name(), "1");
		List<ActivityUserEntity> activeUserList =  namedParameterJdbcTemplateWFD.query(sql, paramMap,new ActiveUserMapper());		
		return activeUserList;
	}
	
	public List<MpuStaticParamEntity> getCSMLoggingOffStoreList() throws Exception {
		logger.info("getCSMLoggingOffStoreList", "Entering getCSMLoggingOffStoreList DAOImpl");
		String sql = PropertyUtils.getProperty("get.mpu.static.param.value");
		Map<String,String> paramMap=new HashMap<String,String>();
		List<MpuStaticParamEntity> mpuStaticParamEntities = namedParameterJdbcTemplateWFD.query(sql, paramMap,new MpuStaticParamMapper());
		return mpuStaticParamEntities;
	}
	
	@SuppressWarnings("unchecked")
	public int updateActiveUsers(List<ActivityUserEntity> activeUserToUpdateList)	throws Exception {
		logger.info("updateActiveUsers", "Entering updateActiveUsers MCPDBDAOImpl ");		
		String sql = PropertyUtils.getProperty("update.loggedOutTime");		
		Map<String,Object> paramArray[] = new HashMap[activeUserToUpdateList.size()];
		for(int i=0;i<activeUserToUpdateList.size();i++){
			HashMap<String, ? super Object> param = new HashMap<String, Object>();
			param.put(USER_ID.name(), activeUserToUpdateList.get(i).getUserId());
			param.put(LOGGED_OUT_TIME.name(), activeUserToUpdateList.get(0).getLoggedOutTime());
			param.put(ACTIVE_USER_FLAG.name(), "N");
			paramArray[i]=(Map<String, Object>) param;
		}
		int []resultArray = namedParameterJdbcTemplateWFD.batchUpdate(sql, paramArray);
		return resultArray.length;
	}
	
	public boolean isRepairEnabled(String store){
		logger.info("getStoreReturnFlag", "Entering getStoreReturnFlag MCPDBDAOImpl ");	
		String sql = PropertyUtils.getProperty("get.return.flag");
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("store", store);
		int count = namedParameterJdbcTemplateWFD.queryForInt(sql, params);
		if(count>0){
			return true;
		}
		return false;
		
	}
}