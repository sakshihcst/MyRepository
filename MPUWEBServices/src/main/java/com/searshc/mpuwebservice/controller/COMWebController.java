package com.searshc.mpuwebservice.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.COMSearch;
import com.searshc.mpuwebservice.bean.ExceptionReportResults;
import com.searshc.mpuwebservice.bean.ReportResults;
import com.searshc.mpuwebservice.bean.ReportResultsDet;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.processor.impl.COMServiceProcessorImpl;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;

/**
 * 
 * @author 826152
 *
 */
@Controller
public class COMWebController {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(COMWebController.class);
	
	@Autowired
	@Qualifier("comServiceProcessorImpl")
	private COMServiceProcessorImpl comServiceProcessorImpl;
	
	/**
	 * 
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "v1/com/report/{reportType}",consumes = "application/json", produces = "application/json")
	@ResponseBody
	 public ResponseEntity<?> getReportData(@PathVariable("reportType") String reportType,@RequestBody COMSearch comSearch) {
		logger.info("entered COMWebController.getReportData", reportType);
		ResponseEntity<?> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		//COMSearch comSearch = new COMSearch();//testing , should be removed
		try{			
			if("JSR".equalsIgnoreCase(reportType)){		
				List<ReportResults> result = comServiceProcessorImpl.getReport(comSearch);
				if(null==result || result.isEmpty())
					respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", null, null, true);
					else{
					responseDTO.setResponseBody(result);	
					respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
					}
			}
			else{
				/* {"jDate":"005062014","orderNumber":"010179048038","workId":"19","storeNo":"01818"}*/
							
				if("JSRD".equalsIgnoreCase(reportType)){	
				List<ReportResultsDet> result = comServiceProcessorImpl.getReportDetails(comSearch);						
				if(null==result || result.isEmpty())
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", null, null, true);
				else{
				responseDTO.setResponseBody(result);	
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
				}
			}
		}
	} catch (DJException djEx) {
		logger.error("getReportData",djEx);
		responseDTO.setResponseBody(djEx);
		respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
	} catch (Exception e) {
		logger.error("getReportData",e);
		responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
		respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
	}
		return respEntity;
	}	
	
	
	
	/**
	 * 
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "v1/com/exception",consumes = "application/json", produces = "application/json")
	@ResponseBody
	 public ResponseEntity<?> getExceptionReportData(@RequestBody COMSearch comSearch) {
		logger.info("entered COMWebController.getExceptionReportData", "start");
		ResponseEntity<?> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		//COMSearch comSearch = new COMSearch();//testing , should be removed
		try {

			List<ExceptionReportResults> result = comServiceProcessorImpl.getExceptionReportData(comSearch);
			if (null == result || result.isEmpty())
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,
						"404", null, null, true);
			else {
				responseDTO.setResponseBody(result);
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,
						true);
			}
		} catch (DJException djEx) {
			logger.error("getExceptionReportData",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getExceptionReportData",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
	}
	
	/**
	 * 
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "v1/com/exception/update",consumes = "application/json", produces = "application/json")
	@ResponseBody
	 public ResponseEntity<?> insertExceptionUpdate(@RequestBody COMSearch comSearch) {
		logger.info("entered COMWebController.insertExceptionUpdate", "start");
		ResponseEntity<?> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {

			Integer result = comServiceProcessorImpl.insertExceptionUpdate("dateString",comSearch);
			if (null == result)
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,
						"404", null, null, true);
			else {
				responseDTO.setResponseBody(result);
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,
						true);
			}
		} catch (DJException djEx) {
			logger.error("insertExceptionUpdate",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("insertExceptionUpdate",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
	}
	
	
	
	
	//Exception
	
	/** This service is used to get list for COM exception
	 * @param storeNumber
	 * @param date
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/report/KioskList/{storeNumber}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getKioskList(@PathVariable("storeNumber") String storeNumber) {
		logger.info("entered COMWebController.getKioskList", "start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(comServiceProcessorImpl.getKioskList(storeNumber));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getKioskList",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getKioskList",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting getCOMExceptionList", "");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/com/sellingassoc/{assignedUser}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getSellingAssoc(@PathVariable("assignedUser") String assignedUser) {
		logger.info("entered COMWebController.getSellingAssoc", "start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(comServiceProcessorImpl.getSellingAssoc(assignedUser));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getKioskList",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getKioskList",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting getCOMExceptionList", "");
		return respEntity;
	}
	
	
	
	
	
}
