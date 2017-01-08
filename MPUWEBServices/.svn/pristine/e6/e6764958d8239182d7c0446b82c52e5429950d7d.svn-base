/**
 * 
 */
package com.searshc.mpuwebservice.dao.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CUSTOMER_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LOCKER_NO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKEDUP_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKEDUP_INITIATED_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PIN_NO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REFERENCE_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK_NO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANSACTION_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.DEFAULT_STORE;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.PICKED_UP;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.PICKUP_INITIATED;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_END_DATE;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_END_TIME;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_START_DATE;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_START_TIME;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.SPACE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.sears.dj.common.util.DJUtilities;
import com.searshc.mpuwebservice.bean.LockerDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.LockerServiceDAO;
import com.searshc.mpuwebservice.mapper.LockerDTOMapper;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;

/**
 * @author ssingh6
 *
 */
@Repository("lockerServiceDAOImpl")
public class LockerServiceDAOImpl extends DJMPUCommonDAO implements LockerServiceDAO{
	private static transient DJLogger logger = DJLoggerFactory.getLogger(MPUWebServiceDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.LockerServiceDAO#insertLockerDetail(com.searshc.mpuwebservice.bean.LockerDTO)
	 */
	public boolean insertLockerDetail(LockerDTO lockerDTO) throws DJException {
		boolean insertFlag = false;
		
	logger.debug(" Locker Details to Insert in mpu_locker table: ", lockerDTO.getSalescheckNo()+"_"+lockerDTO.getStoreNo()+"_"+lockerDTO.getTransactionDate());
		Map<String, ? super Object> params = new HashMap<String, Object>();
		Date currentTime=Calendar.getInstance().getTime();
		String createTime = DJUtilities.dateToString(currentTime,MpuWebConstants.DATE_FORMAT);
		
		params.put(REFERENCE_ID.name(),lockerDTO.getReferenceId());
		params.put(STORE_NUMBER.name(),lockerDTO.getStoreNo());
		params.put(SALESCHECK.name(),lockerDTO.getSalescheckNo());
		params.put(TRANSACTION_DATE.name(),lockerDTO.getTransactionDate());
		params.put(LOCKER_NO.name(),lockerDTO.getLockerNo());
		String lockerStatus = PropertyUtils.getProperty("locker.status.binned");
		params.put(STATUS.name(),lockerStatus);
		//custo
		params.put(CUSTOMER_NAME.name(),lockerDTO.getCustomerName());
		params.put(CREATE_TIMESTAMP.name(),createTime);
		params.put(CREATED_BY.name(),lockerDTO.getCreatedBy());
		params.put(UPDATE_TIMESTAMP.name(),createTime); 
		params.put(UPDATED_BY.name(),lockerDTO.getUpdatedBy());
		
		String sql = PropertyUtils.getProperty("insert.into.locker");
		int keyValue=0;
		keyValue=  super.updateWithKeyHolder(lockerDTO.getStoreNo(), sql, params).intValue();
		if(keyValue != 0){
			insertFlag = true;
		}
		  logger.debug("exiting" ,"LockerServiceDAOImpl.insertLockerDetail");
		return insertFlag;
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.LockerServiceDAO#updateLockerPin(com.searshc.mpuwebservice.bean.LockerDTO)
	 */
	public int updateLockerPin(LockerDTO lockerDTO) throws DJException {
		String salescheck = lockerDTO.getSalescheckNo();
		String storeNo = lockerDTO.getStoreNo();
		String lockerNo= lockerDTO.getLockerNo();
		String pinNo = lockerDTO.getPinNo();
		String status = lockerDTO.getStatus();
		logger.debug("updateLockerPin salescheck : ", salescheck + " store_no : " +storeNo +" locker_no : " +lockerNo +" pin_no : "+pinNo);
		
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		String updateSql = "";
		updateSql = PropertyUtils.getProperty("update_mpu_locker_pin1");
		updateSql = updateSql + MpuWebConstants.SPACE + PropertyUtils.getProperty("update_mpu_locker_pin2");
		
		parameterMap.put(PIN_NO.name(),pinNo);
		parameterMap.put(LOCKER_NO.name(),lockerNo);
		parameterMap.put(SALESCHECK.name(),salescheck);
		parameterMap.put(STORE_NUMBER.name(),storeNo);	
		parameterMap.put(STATUS.name(),status);
		 logger.debug("exiting" ,"LockerServiceDAOImpl.updateLockerPin");
		return update(storeNo, updateSql,parameterMap);		
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.LockerServiceDAO#getPinNumberFromSalescheck(java.lang.String, java.lang.String, java.lang.String)
	 */
	public LockerDTO getPinNumberFromSalescheck(String salescheckNum,	String storeNumber, String transactionDate) throws DJException {
		logger.debug("getPinNumberFromSalescheck salescheck : ", salescheckNum + " store_no : " +storeNumber +" transactionDate : " +transactionDate);
		
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		String selectSql = "";
		selectSql = PropertyUtils.getProperty("get_pin_from_salescheck1");
		selectSql = selectSql + MpuWebConstants.SPACE + PropertyUtils.getProperty("get_pin_from_salescheck2");
		String transDate = "";
		
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		try {
			Date transDate1 = dateFormat1.parse(transactionDate.toString());
			transDate = dateFormat2.format(transDate1);
		} catch (ParseException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}	
		
		parameterMap.put(SALESCHECK_NO.name(),salescheckNum);
		parameterMap.put(STORE_NUMBER.name(),storeNumber);
		parameterMap.put(TRANSACTION_DATE.name(),transDate);	
		
		List<LockerDTO> lockerDTOs =  (List<LockerDTO>) query(storeNumber,selectSql, parameterMap,new LockerDTOMapper());
		logger.debug("exiting" ,"LockerServiceDAOImpl.getPinNumberFromSalescheck");
		if(null != lockerDTOs && !lockerDTOs.isEmpty()){
			return lockerDTOs.get(0);
		}
		return null;		
	}	
	
	/*(non-Javadoc)
	 *@see com.searshc.mpuwebservice.dao.LockerServiceDAO#updateLockerOrderStatus(com.searshc.mpuwebservice.bean.LockerDTO)
	 */
	public int updateLockerOrderStatus(LockerDTO lockerDTO) throws DJException {
		String salescheck = lockerDTO.getSalescheckNo();
		String storeNo = lockerDTO.getStoreNo();
		String lockerNo= lockerDTO.getLockerNo();
		String status = lockerDTO.getStatus();
		
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		String updateSql = "";
		updateSql = PropertyUtils.getProperty("update_mpu_locker_status1");
		if(PICKUP_INITIATED.equalsIgnoreCase(status)){
		updateSql = updateSql + PropertyUtils.getProperty("update_pickup_initiateDate") +PropertyUtils.getProperty("update_customerName");
		parameterMap.put(PICKEDUP_INITIATED_DATE.name(), lockerDTO.getPickedupInitiatedDate());
		parameterMap.put(CUSTOMER_NAME.name(), lockerDTO.getCustomerName());
		}else if(PICKED_UP.equalsIgnoreCase(status)){
			updateSql = updateSql + PropertyUtils.getProperty("update_pickupDate");
			parameterMap.put(PICKEDUP_DATE.name(), lockerDTO.getPickedupDate());
		}
		updateSql=updateSql+MpuWebConstants.SPACE+PropertyUtils.getProperty("update_mpu_locker_status2");
		parameterMap.put(LOCKER_NO.name(),lockerNo);
		parameterMap.put(SALESCHECK.name(),salescheck);
		parameterMap.put(STORE_NUMBER.name(),storeNo);
		parameterMap.put(STATUS.name(),status);
		return update(storeNo, updateSql,parameterMap);		
	}
	
	
	public Boolean isOrderKeptInLocker(Integer reqID, String storeNumber) throws DJException {
		
		logger.debug("Entering" ,"LockerServiceDAOImpl.isOrderKeptInLocker");
		
		Boolean isOrderKeptInLocker = Boolean.FALSE;
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		String selectSql = "";
		selectSql = PropertyUtils.getProperty("is_order_kept_in_locker");
	
		parameterMap.put(STORE_NUMBER.name(),storeNumber);
		parameterMap.put(REFERENCE_ID.name(),reqID);
			
		LockerDTO lockerDTO  =  (LockerDTO)query(storeNumber,selectSql, parameterMap, new LockerDTOMapper());
		
		logger.debug("exiting" ,"LockerServiceDAOImpl.isOrderKeptInLocker lockerDTO : " + lockerDTO);
		
		if(lockerDTO !=null && lockerDTO.getLockerNo()!=null)
		{
			isOrderKeptInLocker = Boolean.TRUE;
		}
		
		return isOrderKeptInLocker;		
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.LockerServiceDAO#getLockerReport(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<LockerDTO> getLockerReport(String reportFromDate,String reportToDate, String storeNumber) throws DJException {
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		List<LockerDTO> lockerDTOs = new ArrayList<LockerDTO>();
		List<LockerDTO> tempLockerDTOs=null;
		String selectSql=null;
		if(storeNumber!=null && !DEFAULT_STORE.equals(storeNumber)){
			selectSql=PropertyUtils.getProperty("get_pin_from_salescheck1")+SPACE+PropertyUtils.getProperty("get_locker_report_date")+SPACE+PropertyUtils.getProperty("get_store_number");
			parameterMap.put(STORE_NUMBER.name(), storeNumber);
			parameterMap.put(REPORT_START_DATE, reportFromDate+SPACE+REPORT_START_TIME);
			parameterMap.put(REPORT_END_DATE, reportToDate+SPACE+REPORT_END_TIME);
			lockerDTOs=(List<LockerDTO>) query(storeNumber,selectSql, parameterMap,new LockerDTOMapper());
		}else{
			parameterMap.put(REPORT_START_DATE, reportFromDate+SPACE+REPORT_START_TIME);
			parameterMap.put(REPORT_END_DATE, reportToDate+SPACE+REPORT_END_TIME);
			selectSql=PropertyUtils.getProperty("get_pin_from_salescheck1")+SPACE+PropertyUtils.getProperty("get_locker_report_date");
			List<NamedParameterJdbcTemplate> namedParameterJdbcTemplates= getNamedParameterJdbcTemplates();
			for(NamedParameterJdbcTemplate namedParameterJdbcTemplate:namedParameterJdbcTemplates){
				tempLockerDTOs=(List<LockerDTO>)namedParameterJdbcTemplate.query(selectSql, parameterMap, new LockerDTOMapper());
				if(tempLockerDTOs!=null && tempLockerDTOs.size()>0){
					lockerDTOs.addAll(tempLockerDTOs);
				}
			}
		}
		
		return lockerDTOs;
	}
	
}