/**
 * 
 */
package com.searshc.mpuwebservice.dao;

import java.util.List;
import java.util.Map;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.MpuReportDetailVO;
import com.searshc.mpuwebservice.bean.PickupReportDTO;
import com.searshc.mpuwebservice.bean.ShopInReportDTO;
import com.searshc.mpuwebservice.bean.StageOrdersReportDTO;

/**
 * @author nkumar1
 *
 */
public interface ReportServiceDAO {
	
	/**
	 * This method is used to get records for Pickup report from database
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @param storeNumber String
	 * @return List<PickupReportDTO>
	 * @throws DJException
	 */
	public List<PickupReportDTO> getPickupReport(String reportFromDate, String reportToDate,String storeNumber) throws DJException;
	
	/**
	 * This method is used to get records for StageOrders report from database
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @return List<StageOrdersReportDTO>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getPickupForStageOrdersReport(String reportFromDate, String reportToDate) throws DJException;
	
	/**
	 * This method is used to get records for StageOrders report from database
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @return List<StageOrdersReportDTO>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getReturnForStageOrdersReport(String reportFromDate, String reportToDate) throws DJException;

	
	/**
	 * This method is used to get records for HoldGo report from database
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @return List<StageOrdersReportDTO>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getHGOrdersReport(String reportFromDate, String reportToDate,String store) throws DJException;
	public String getCustomerName(String rqtId,String store) throws DJException;

	public List<MpuReportDetailVO> getShopInReport(String startDate,String endDate, String storeNumber, String storeFormat) throws DJException;

}
