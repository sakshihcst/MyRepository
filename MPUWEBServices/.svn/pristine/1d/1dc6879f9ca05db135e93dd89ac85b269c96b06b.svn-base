package com.searshc.mpuwebservice.processor;

import java.util.HashMap;
import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.COMSearch;
import com.searshc.mpuwebservice.bean.ExceptionReportResults;
import com.searshc.mpuwebservice.bean.ReportResults;
import com.searshc.mpuwebservice.bean.ReportResultsDet;

/**
 * 
 * @author ssingh6
 *
 */
public interface COMServiceProcessor {
	

	public List<ReportResults> getReport(COMSearch comSearch)throws DJException;
	
	public List<ExceptionReportResults> getExceptionReportData(COMSearch comSearch)throws DJException;
	
	/**
	 * @param store
	 * @param date
	 * @param status 
	 * @return List<Object[]>
	 * @throws DJException
	 */
	public List<String> getKioskList(String store) throws DJException;


	/**
	 * @param reqInfo
	 * @return boolean
	 * @throws DJException
	 */
	public boolean updateRequestForCOM(HashMap<String, String> reqInfo,String user,String store)throws DJException;
	
	public ReportResultsDet getSellingAssoc(String assignedUser) throws DJException;
	
}
