/**
 * 
 */
package com.searshc.mpuwebservice.dao.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.DEFAULT_STORE;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_END_DATE;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_END_TIME;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_START_DATE;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.REPORT_START_TIME;
import static com.searshc.mpuwebservice.constant.MpuWebConstants.SPACE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.sears.dj.common.util.DJUtilities;
import com.searshc.mpuwebservice.bean.CustomerDTO;
import com.searshc.mpuwebservice.bean.MpuReportDetailVO;
import com.searshc.mpuwebservice.bean.PaymentDTO;
import com.searshc.mpuwebservice.bean.PickupReportDTO;
import com.searshc.mpuwebservice.bean.ShopInReportDTO;
import com.searshc.mpuwebservice.bean.StageOrdersReportDTO;
import com.searshc.mpuwebservice.dao.ReportServiceDAO;
import com.searshc.mpuwebservice.mapper.IdentifierDTOMapper;
import com.searshc.mpuwebservice.mapper.MpuReportDetailVOMapper;
import com.searshc.mpuwebservice.mapper.PackageInfoMapper;
import com.searshc.mpuwebservice.mapper.PaymentDTOMapper;
import com.searshc.mpuwebservice.mapper.PickupReportDTOMapper;
import com.searshc.mpuwebservice.util.PropertyUtils;

/**
 * @author nkumar1
 *
 */
@Repository("reportServiceDAOImpl")
public class ReportServiceDAOImpl extends DJMPUCommonDAO implements ReportServiceDAO{
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateRead;
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(ReportServiceDAOImpl.class);

	/**
	 * This method is used to get records for Pickup report details from database
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @param storeNumber String
	 * @return List<PickupReportDTO>
	 * @throws DJException
	 */
	public List<PickupReportDTO> getPickupReport(String reportFromDate,String reportToDate, String storeNumber) throws DJException {
		HashMap<String,Object> parameterMap=new HashMap<String,Object>();
		List<PickupReportDTO> pickupReportDTO = new ArrayList<PickupReportDTO>();
		String selectSql=null;
		selectSql=PropertyUtils.getProperty("get_pickup_peport");
		parameterMap.put(STORE_NUMBER.name(), storeNumber);
		parameterMap.put(REPORT_START_DATE, reportFromDate+SPACE+REPORT_START_TIME);
		parameterMap.put(REPORT_END_DATE, reportToDate+SPACE+REPORT_END_TIME);
		pickupReportDTO=(List<PickupReportDTO>) query(storeNumber,selectSql, parameterMap,new PickupReportDTOMapper());
		
		return pickupReportDTO;
	}
	
	/**
	 * This method is used to get total pickup for StageOrders report details from database
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @return List<StageOrdersReportDTO>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getPickupForStageOrdersReport(String reportFromDate,String reportToDate) throws DJException {
		logger.info("Start getPickupForStageOrdersReport() for reportFromDate:", reportFromDate);
		logger.info("Start getPickupForStageOrdersReport() for reportToDate:", reportToDate);
		String sql=PropertyUtils.getProperty("get_pickup_for_stage_orders_peport");
		return getQueryResult(reportFromDate, reportToDate, sql);
	}
	
	/**
	 * This method is used to get total return and return in five for StageOrders report details from database
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @return List<StageOrdersReportDTO>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getReturnForStageOrdersReport(String reportFromDate,String reportToDate) throws DJException {
		logger.info("Start getReturnForStageOrdersReport() for reportFromDate:", reportFromDate);
		logger.info("Start getReturnForStageOrdersReport() for reportToDate:", reportToDate);
		String sql=PropertyUtils.getProperty("get_return_for_stage_orders_peport");
		return getQueryResult(reportFromDate, reportToDate, sql);
	}
	
	/**
	 * This method returns the result according to passed query from all schema
	 * @param reportDate String
	 * @param sql String
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> getQueryResult(String reportFromDate,String reportToDate, String sql) {
		logger.info("Start of getQueryResult() SQL:======= ", sql);
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(REPORT_START_DATE, reportFromDate + REPORT_START_TIME);
		parameterMap.put(REPORT_END_DATE, reportToDate + REPORT_END_TIME);
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		List<NamedParameterJdbcTemplate> namedParameterJdbcTemplates = getNamedParameterJdbcTemplates();
		
		for (NamedParameterJdbcTemplate namedParameterJdbcTemplate : namedParameterJdbcTemplates) {
			List<Map<String, Object>> tempResultList = (List<Map<String, Object>>) namedParameterJdbcTemplate.queryForList(sql, parameterMap);
			if(tempResultList!=null && tempResultList.size()>0)
			resultList.addAll(tempResultList);			
		}
		
		logger.info("End of getQueryResult() SQL:======= ", sql);
		return resultList;
	}

	public List<Map<String, Object>> getHGOrdersReport(String reportFromDate,String reportToDate, String store) throws DJException {
		logger.info("getHGOrdersReport", "entering");
		String sql=PropertyUtils.getProperty("get_holdgo_records");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(REPORT_START_DATE, reportFromDate + REPORT_START_TIME);
		parameterMap.put(REPORT_END_DATE, reportToDate + REPORT_END_TIME);
		logger.info("qry",DJUtilities.createSQLCommand(sql, parameterMap));
		if(!StringUtils.isEmpty(store) && store.equalsIgnoreCase("00000")){
			logger.info("getHGOrdersReport", "exiting");
			return getQueryResult(reportFromDate, reportToDate, sql);
		}else{
			if(store.length() < 5){
				store = StringUtils.leftPad(store, 5, '0');
			}
			sql = sql + " and rqt.store_number="+store;
			logger.info("getHGOrdersReport", "exiting");
			return (List<Map<String, Object>>) queryForList(store, sql, parameterMap) ;
		}
	}
	
	public String getCustomerName(String rqtId,String store) throws DJException{
		String sql=PropertyUtils.getProperty("get_customer_name");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(RQT_ID.name(),rqtId);
		if(store.length()<5){
			store = StringUtils.leftPad(store, 5, '0'); 
		}
		return queryForObject(store, sql, parameterMap, String.class);
		
	}

	public List<MpuReportDetailVO> getShopInReport(String startDate,String endDate, String storeNumber, String storeFormat) throws DJException {
		logger.info("getShopInReport", "entering");
		String sql=PropertyUtils.getProperty("get_shopin_records");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(REPORT_START_DATE, startDate + REPORT_START_TIME);
		parameterMap.put(REPORT_END_DATE, endDate + REPORT_END_TIME);
		
		List<MpuReportDetailVO> shopInReport = new ArrayList<MpuReportDetailVO>();
		
		if(!StringUtils.isEmpty(storeNumber) && null != storeNumber){
			if(storeNumber.length() < 5){
				storeNumber = StringUtils.leftPad(storeNumber, 5, '0');
			}
			parameterMap.put(STORE_NUMBER.name(), storeNumber);
			sql = sql + PropertyUtils.getProperty("get_shopin_records_where_clause");
			sql = sql + PropertyUtils.getProperty("get_shopin_records_group_by");
			
			logger.info("qry", DJUtilities.createSQLCommand(sql, parameterMap));
			shopInReport = (List<MpuReportDetailVO>) query(storeNumber, sql, parameterMap, new MpuReportDetailVOMapper());
			return shopInReport;	
			
		} else {
			
			sql = sql + PropertyUtils.getProperty("get_shopin_records_group_by");
			logger.info("qry", DJUtilities.createSQLCommand(sql, parameterMap));
			List<NamedParameterJdbcTemplate> namedParameterJdbcTemplates = getNamedParameterJdbcTemplates();
			
			for (NamedParameterJdbcTemplate namedParameterJdbcTemplate : namedParameterJdbcTemplates) {
					List<MpuReportDetailVO> shopInReportTemp = (List<MpuReportDetailVO>) namedParameterJdbcTemplate.query(sql, parameterMap,  new MpuReportDetailVOMapper());
					shopInReport.addAll(shopInReportTemp);
			}
			logger.info("shopInReport", shopInReport);
			return shopInReport;
		}	
	}
	
}