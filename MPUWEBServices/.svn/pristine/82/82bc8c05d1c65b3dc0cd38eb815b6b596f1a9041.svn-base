/**
 * 
 */
package com.searshc.mpuwebservice.dao.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSIGNED_USER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TODAY_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATE_TIMESTAMP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.sears.dj.common.util.DJUtilities;
import com.searshc.mpuwebservice.bean.COMSearch;
import com.searshc.mpuwebservice.bean.ExceptionReportResults;
import com.searshc.mpuwebservice.bean.ReportResults;
import com.searshc.mpuwebservice.bean.ReportResultsDet;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.COMServiceDAO;
import com.searshc.mpuwebservice.mapper.ExceptionSearchReportMapper;
import com.searshc.mpuwebservice.mapper.JournelSearchReportDetMapper;
import com.searshc.mpuwebservice.mapper.JournelSearchReportMapper;
import com.searshc.mpuwebservice.mapper.KioskListMapper;
import com.searshc.mpuwebservice.util.PropertyUtils;

@Repository("comServiceDAOImpl")
public class COMServiceDAOImpl extends DJMPUCommonDAO implements COMServiceDAO {
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(COMServiceDAOImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateWFD;

	public List<ReportResults> getReports(COMSearch comSearch) throws DJException {

		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(STORE_NUMBER.name(),comSearch.getStoreNo());
		String query = "";
		if (comSearch.getActionValue().contains("Todays Journal")){
			Date dayDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(dayDate);
			paramMap.put(TODAY_DATE.name(),date);
			query=PropertyUtils.getProperty("select.com.report.jsr.all");
			query = DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.todayjournal")); 
		}
		else{
		if(null != comSearch.getStatus() && "Complete".equalsIgnoreCase(comSearch.getStatus())){
			query=PropertyUtils.getProperty("select.com.report.jsr.completed");
		}
		if(null != comSearch.getStatus() && "Not Shipped".equalsIgnoreCase(comSearch.getStatus())){
			query=PropertyUtils.getProperty("select.com.report.jsr.notdelivered");
		}
		if(null != comSearch.getStatus() && "All".equalsIgnoreCase(comSearch.getStatus())){
			query=PropertyUtils.getProperty("select.com.report.jsr.all");
		}
		if(null != comSearch.getSalesCheckNumber() && !("").equalsIgnoreCase(comSearch.getSalesCheckNumber())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.salescheckfilter"));
			paramMap.put("SALESCHECK",comSearch.getSalesCheckNumber());
		}
		if(null != comSearch.getCustomerFName() && !("").equalsIgnoreCase(comSearch.getCustomerFName())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.custfirstnamefilter"));
			paramMap.put("CUSTFIRSTNAME",comSearch.getCustomerFName());
		}
		if(null != comSearch.getCustomerLName() && !("").equalsIgnoreCase(comSearch.getCustomerLName())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.custlastnamefilter"));
			paramMap.put("CUSTLASTNAME",comSearch.getCustomerLName());
		}
		if(null != comSearch.getKiosk() && !("").equalsIgnoreCase(comSearch.getKiosk()) && !("All").equalsIgnoreCase(comSearch.getKiosk())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.kiosk"));
			paramMap.put("KIOSKNAME",comSearch.getKiosk());
		}
		if(null != comSearch.getDayDate() && null == comSearch.getToDate() && !("").equalsIgnoreCase(comSearch.getDayDate())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.daydatefilter"));
			paramMap.put("DAYDATE",comSearch.getDayDate());
		}
		if(null != comSearch.getDayDate() && null != comSearch.getToDate() && !("").equalsIgnoreCase(comSearch.getDayDate()) && !("").equalsIgnoreCase(comSearch.getToDate())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.daterangefilter"));
			paramMap.put("DAYDATE",comSearch.getDayDate());
			paramMap.put("TODATE", comSearch.getToDate());
		}
		if(null != comSearch.getDivision() &&  !("").equalsIgnoreCase(comSearch.getDivision())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.divfilter"));
			paramMap.put("DIVNUM",comSearch.getDivision());
		}
		if(null != comSearch.getItem() && !("").equalsIgnoreCase(comSearch.getItem())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.itemfilter"));
			paramMap.put("ITEM",comSearch.getItem());
		}
		if(null != comSearch.getSKU() && !("").equalsIgnoreCase(comSearch.getSKU())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.skufilter"));
			paramMap.put("SKU",comSearch.getSKU());
		}
		if(null != comSearch.getPlus4() && !("").equalsIgnoreCase(comSearch.getPlus4())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.plus4filter"));
			paramMap.put("PLUS4",comSearch.getPlus4());
		}
		if(null != comSearch.getAssociateNumber() &&  !("").equalsIgnoreCase(comSearch.getAssociateNumber())){
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.assocnofilter"));
			paramMap.put("SEARS_SALES_ID",comSearch.getAssociateNumber());
		}
		}

		query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.groupby"));

		logger.info("getReports", "getReports Sql : " + DJUtilities.createSQLCommand(query, paramMap));
		

		List<ReportResults> result = null;

		try {
				result = query(DJUtilities.leftPadding(comSearch.getStoreNo(), 5), query, paramMap, new JournelSearchReportMapper());
			logger.info("Query :-----------------------",query);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	public List<ReportResults> getHelpRepairReports(COMSearch comSearch) throws DJException {

		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(STORE_NUMBER.name(),comSearch.getStoreNo());
		List<ReportResults> result = null;
		try {
			String query = PropertyUtils.getProperty("select.com.report.jsr.help");
			if (comSearch.getActionValue().contains("Todays Journal")){
				Date dayDate = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String date = format.format(dayDate);
				paramMap.put(TODAY_DATE.name(),date);
				query = DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.todayjournal")); 
				
				paramMap.put(STORE_NUMBER.name(),comSearch.getStoreNo());
				logger.info("getHelpRepairReports", "getHelpRepairReports Sql : " + DJUtilities.createSQLCommand(query, paramMap));
				result = query(comSearch.getStoreNo(), query, paramMap, new JournelSearchReportMapper());
				return result;
			}
			else if((null != comSearch.getDivision() && !("").equalsIgnoreCase(comSearch.getDivision())) ||
					(null != comSearch.getItem() && !("").equalsIgnoreCase(comSearch.getItem())) ||
					(null != comSearch.getSKU() && !("").equalsIgnoreCase(comSearch.getSKU())) ||
					(null != comSearch.getPlus4() && !("").equalsIgnoreCase(comSearch.getPlus4()))){
				result = null;
				return result;
			
			}
			else if(("Not Shipped").equalsIgnoreCase(comSearch.getStatus())){
				result = null;
				return result;
			}
			else{
			if(null != comSearch.getSalesCheckNumber() && !("").equalsIgnoreCase(comSearch.getSalesCheckNumber())){
				query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.salescheckfilter1"));
				paramMap.put("SALESCHECK",comSearch.getSalesCheckNumber());
			}
			if(null != comSearch.getCustomerFName() && !("").equalsIgnoreCase(comSearch.getCustomerFName())){
				query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.custfirstnamefilter"));
				paramMap.put("CUSTFIRSTNAME",comSearch.getCustomerFName());
			}
			if(null != comSearch.getCustomerLName() && !("").equalsIgnoreCase(comSearch.getCustomerLName())){
				query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.custlastnamefilter"));
				paramMap.put("CUSTLASTNAME",comSearch.getCustomerLName());
			}
			if(null != comSearch.getKiosk() && !("").equalsIgnoreCase(comSearch.getKiosk()) && !("All").equalsIgnoreCase(comSearch.getKiosk())){
				query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.kiosk"));
				paramMap.put("KIOSKNAME",comSearch.getKiosk());
			}
			if(null != comSearch.getDayDate() && null == comSearch.getToDate() && !("").equalsIgnoreCase(comSearch.getDayDate())){
				query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.daydatefilter"));
				paramMap.put("DAYDATE",comSearch.getDayDate());
			}
			if(null != comSearch.getDayDate() && null != comSearch.getToDate() && !("").equalsIgnoreCase(comSearch.getDayDate()) && !("").equalsIgnoreCase(comSearch.getToDate())){
				query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.daterangefilter"));
				paramMap.put("DAYDATE",comSearch.getDayDate());
				paramMap.put("TODATE", comSearch.getToDate());
			}
			if(null != comSearch.getAssociateNumber() &&  !("").equalsIgnoreCase(comSearch.getAssociateNumber())){
				query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.jsr.assocnofilter"));
				paramMap.put("SEARS_SALES_ID",comSearch.getAssociateNumber());
			}
			}
			paramMap.put(STORE_NUMBER.name(),comSearch.getStoreNo());
			logger.info("getHelpRepairReports", "getHelpRepairReports Sql : " + DJUtilities.createSQLCommand(query, paramMap));
			
			result = query(comSearch.getStoreNo(), query, paramMap, new JournelSearchReportMapper());
			logger.info("Query :-----------------------",query);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	public String dateConversion(String date) {
		SimpleDateFormat fromFormat = new SimpleDateFormat("MMM dd yyyy");
		SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dayDateString = "";
		try {
			if(null!=date){
			Date dayDate = fromFormat.parse(date);
			dayDateString = toFormat.format(dayDate);
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return dayDateString;
	}
	private String dateConvertor(String date){
		try{
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(date));
		c.add(Calendar.DATE, 1);
		date = sdf.format(c.getTime());
		return date;
		}
		catch(Exception e) {			
			
		}
		return null;	
		
	}


	public List<ReportResultsDet> getReportsDetails(COMSearch comSearch)  throws DJException {
		String query=PropertyUtils.getProperty("select.com.report.jsrd") ;
		List<ReportResultsDet> result = new ArrayList<ReportResultsDet>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {		
		//	String jDate = dateConversion(comSearch.getjDate());
			param.put(RQT_ID.name(), comSearch.getWorkId());
			
			logger.info("getReportsDetails", "getReportsDetails Sql : " + DJUtilities.createSQLCommand(query, param));
			
			if(null!=comSearch && null!=comSearch.getStoreNo()){
				
			/*	query = query + " and rqt.rqt_id= " + comSearch.getWorkId() + " and rqt.salescheck= " + comSearch.getOrderNumber() + " rqt.update_timestamp= " + jDate
						+ " group by rqd_id ";*/
												
						
		
			result = query(comSearch.getStoreNo(),query,param, new JournelSearchReportDetMapper());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
		
	}
	
	
	//Exception
	
	
	public List<ExceptionReportResults> getExceptionReportData(COMSearch comSearch) throws DJException {

		List<ExceptionReportResults> result = null;
		Map<String, Object> param = new HashMap<String, Object>();
		String query=PropertyUtils.getProperty("select.com.report.exception.mpu");
		try {
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.exception.wherempu"));
			
//			if (comSearch.getStoreNo() != null && comSearch.getStoreNo() != "") {
//				query = query + " and detail.store_number= " + comSearch.getStoreNo();
//
//			}
//
//			/*if (comSearch.getActionValue() != null
//					&& comSearch.getActionValue().equals("Today's Exception")) {*/
//			if (comSearch.getActionValue() != null
//						&& comSearch.getActionValue().equals("Todays Exception")) {
//
//				Date dayDate = new Date();
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//				String date = format.format(dayDate);
//				comSearch.setDayDate(date);
//			}
//
//			if (comSearch.getStatus() != null
//					&& !comSearch.getStatus().equals("")) {
//
//				if (comSearch.getStatus().toUpperCase().equals("W")) {
//
//					param.put("isActive", "N");
//
//					if (comSearch.getDayDate() != null
//							&& comSearch.getDayDate() != "") {
//						query = query
//								+ " and substring(trans.create_timestamp,1,10)= '"
//								+ comSearch.getDayDate() + "'";
//					}
//
//				} else if (comSearch.getStatus().toUpperCase().equals("NW")) {
//
//					param.put("isActive", "Y");
//					
//					if (comSearch.getDayDate() != null
//							&& comSearch.getDayDate() != "") {
//						query = query
//								+ " and substring(trans.create_timestamp,1,10)= '"
//								+ comSearch.getDayDate() + "'";
//					}
//
//				}
//
//			}

			// not clear about addition
			/*query = query
					+ " and trans.type in ('HELP','PICKUP','RETURN','STAGE','CANCEL','VOID','REPAIRDROP','REPAIRPICK','RETURNIN5') and " +
					"trans.type in ('PICKUP','POSMESSAGE','CANCEL','MSG','LOCKER_PIC','HOLD&GO') " +
					"group by trans.type";*/
			param.put("SELECT_DATE", comSearch.getDayDate());
			param.put("STORE_NUMBER", comSearch.getStoreNo());
			if(comSearch.getStatus().equalsIgnoreCase("W")){
				param.put("IS_ACTIVE","N");	
			}
			else if(comSearch.getStatus().equalsIgnoreCase("NW")){
				param.put("IS_ACTIVE","Y");	
			}
			
			logger.info("getExceptionReportData", "getExceptionReportData Sql : " + DJUtilities.createSQLCommand(query, param));
			result =	query(comSearch.getStoreNo(), query,param, new ExceptionSearchReportMapper());
				
			logger.info("Query :-----------------------",query);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	public List<ExceptionReportResults> getExceptionReportQueueData(COMSearch comSearch) throws DJException {

		List<ExceptionReportResults> result = null;
		Map<String, Object> param = new HashMap<String, Object>();
		String query=PropertyUtils.getProperty("select.com.report.exception.queue");
		try { 
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.exception.wherestagequeue"));
			query =  DJUtilities.concatString(query ,MpuWebConstants.SPACE, PropertyUtils.getProperty("select.com.report.exception.wherecancelclosedqueue"));
			param.put("STORE_NUMBER", comSearch.getStoreNo());
			param.put("SELECT_DATE", comSearch.getDayDate());
			if(comSearch.getStatus().equalsIgnoreCase("W")){
				param.put("IS_ACTIVE","N");	
			}
			else if(comSearch.getStatus().equalsIgnoreCase("NW")){
				param.put("IS_ACTIVE","Y");	
			}
			logger.info("getExceptionReportQueueData", "getExceptionReportQueueData Sql : " + DJUtilities.createSQLCommand(query, param));
			result =	query(comSearch.getStoreNo(), query,param, new ExceptionSearchReportMapper());
				
			logger.info("Query :-----------------------",query);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	public Integer insertExceptionUpdate(COMSearch comSearch) throws DJException {

		String query=PropertyUtils.getProperty("select.com.report.update");
		try {
			List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
			List<String> rqdIdList = (List<String>)Arrays.asList(comSearch.getRqdId());
			
			for(String rqdId : rqdIdList){
				HashMap<String, ? super Object> param = new HashMap<String, Object>();
				param.put("RQD_ID", rqdId);
				param.put("STORE_NUMBER", comSearch.getStoreNo());
				param.put("IS_ACTIVE", "N");
				paramList.add(param);
			}
			Map<String,Object> paramArray[] = new HashMap[paramList.size()];
			
			 int noOfRows = batchUpdate(DJUtilities.leftPadding(comSearch.getStoreNo(), 5), query, paramList.toArray(paramArray)).length;
			 
			 logger.info("insertExceptionUpdate" ,"Exit insertExceptionUpdate noOfRows : "+ noOfRows);
			 
			 return noOfRows;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}
	
	
	public List<String> getKioskList(String store) throws DJException {
		
		String sql = PropertyUtils.getProperty("select.com.kiosklist");
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("storeNumber", store);
		return (List<String>) namedParameterJdbcTemplateWFD.query(sql, paramMap,new KioskListMapper());
	}
	
	public int updateItemDetailForCOM(String[] rqdIds, String user, String store)
			throws DJException {
		int size = rqdIds.length;
	    Map<String, ? super Object> param[] = new HashMap[size];
	    for(int i=0;i<size;i++){
	    	Map<String, ? super Object> params = new HashMap<String, Object>();
	    	params.put(RQD_ID.name(),rqdIds[i]);
	    	params.put(ASSIGNED_USER.name(),user);
	    	param[i]=params;
	    }
	    String sql = PropertyUtils.getProperty("update.into.item.for.com");
		logger.info("Existing MPUWebServiceDAOImpl.updateItemDetailForCOM","");
		return super.batchUpdate(store, sql, param).length;
		
	}
	
	public int updateOrderDetailForCOM(String[] rqtIds, String store)
			throws DJException {
		int size = rqtIds.length;
	    Map<String, ? super Object> param[] = new HashMap[size];
	    for(int i=0;i<size;i++){
	    	Map<String, ? super Object> params = new HashMap<String, Object>();
	    	params.put(RQT_ID.name(),rqtIds[i]);
	    	params.put(UPDATE_TIMESTAMP.name(),DJUtilities.dateToString(Calendar.getInstance().getTime(),MpuWebConstants.DATE_FORMAT));
	    	param[i]=params;
	    }
	    String sql = PropertyUtils.getProperty("update.into.trans.for.com");
		logger.info("Existing MPUWebServiceDAOImpl.updateOrderDetailForCOM","");
		return super.batchUpdate(store, sql, param).length;
		
	}
	


}