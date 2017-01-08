/**
 * 
 */
package com.searshc.mpuwebservice.dao;

import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.COMSearch;
import com.searshc.mpuwebservice.bean.ExceptionReportResults;
import com.searshc.mpuwebservice.bean.ReportResults;
import com.searshc.mpuwebservice.bean.ReportResultsDet;

/**
 * @author ssingh6
 *
 */
public interface COMServiceDAO {

	public List<ReportResults> getReports(COMSearch comSearch)  throws DJException;

	public List<ReportResultsDet> getReportsDetails(COMSearch comSearch)  throws DJException;
	
	public List<ExceptionReportResults> getExceptionReportData(COMSearch comSearch) throws DJException;
	
	public List<String> getKioskList(String store) throws DJException;
	
	public int updateItemDetailForCOM(String[] rqdIds, String user, String store) throws DJException;
	
	public int updateOrderDetailForCOM(String[] rqtIds, String store) throws DJException;
	
	public Integer insertExceptionUpdate(COMSearch comSearch) throws DJException;
	
	public List<ReportResults> getHelpRepairReports(COMSearch comSearch) throws DJException ;
	
	public List<ExceptionReportResults> getExceptionReportQueueData(COMSearch comSearch) throws DJException;
}
