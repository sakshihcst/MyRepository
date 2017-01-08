package com.searshc.mpuwebservice.processor;

import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.HGSummary;
import com.searshc.mpuwebservice.bean.PickupReportDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.StageOrdersReportDTO;

/**
 * 
 * @author nkumar1
 *
 */
public interface ReportServiceProcessor {
	
	/**
	 * This method returns the Pickup report details
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @param storeNumber String
	 * @return List<PickupReportDTO>
	 * @throws DJException
	 */
	public List<PickupReportDTO> getPickupReport(String reportFromDate, String reportToDate,String storeNumber) throws DJException;
	
	/**
	 * This method returns the StageOrders report details
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @return List<StageOrdersReportDTO>
	 * @throws DJException
	 */
	public List<StageOrdersReportDTO> getStageOrdersReport(String reportFromDate, String reportToDate) throws DJException;
	
	/**
	 * This method returns the HGOrders report details
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @return List<HGSummart>
	 * @throws DJException
	 */
	public HGSummary getHGOrdersReport(String reportFromDate, String reportToDate,String store) throws DJException;

	public Object getShopInReport(RequestDTO requestDTO) throws DJException;


}
