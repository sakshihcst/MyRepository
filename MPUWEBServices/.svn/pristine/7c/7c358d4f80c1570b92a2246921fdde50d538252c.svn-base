package com.searshc.mpuwebservice.controller;

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
import com.sears.dj.common.exceptionhandler.FileExceptionHandler;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.processor.ReportServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebutil.constant.MPUWEBUtilConstants;

/**
 * @author nkumar1
 * @description This controller is used to publish restful services for business and support reports
 *
 */
@Controller
public class ReportServiceController {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(ReportServiceController.class);
	
	@Autowired
	@Qualifier("reportServiceProcessorImpl")
	private ReportServiceProcessor reportServiceProcessorImpl;
	
	/**
	 * This method is the entry point for Pickup Report.
	 * This method gives all the records for PickupReport.
	 * This method produces response in "application/json" format
	 * @param request
	 * @return ResponseDTO
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/pickup/getPickupReport/{fromDate}/{toDate}/{storeNumber}", produces = "application/json")
	public @ResponseBody	
	ResponseEntity<ResponseDTO> getPickupReport(
			@PathVariable("fromDate") String reportFromDate,
			@PathVariable("toDate") String reportToDate,
			@PathVariable("storeNumber") String storeNumber) {
		logger.info("entering ", "ReportServiceController.getPickupReport");
		ResponseEntity<ResponseDTO> responseEntity=null;
		ResponseDTO responseDTO=new ResponseDTO();
		try{
			responseDTO.setResponseBody(reportServiceProcessorImpl.getPickupReport(reportFromDate,reportToDate,storeNumber));
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch(Exception exp){
			logger.error("Exception in getPickupReport() for store:"+storeNumber,exp);			
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting ", "ReportServiceController.getPickupReport");
		return responseEntity;
	}
	
	
	/**
	 * This method is the entry point for Stage Orders Report.
	 * This method gives all the records for PickupReport.
	 * This method produces response in "application/json" format
	 * @param request
	 * @return ResponseDTO
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/stageOrders/getStageOrdersReport/{fromDate}/{toDate}", produces = "application/json")
	public @ResponseBody	
	ResponseEntity<ResponseDTO> getStageOrdersReport(
			@PathVariable("fromDate") String reportFromDate,
			@PathVariable("toDate") String reportToDate) {
		logger.info("entering ", "ReportServiceController.getStageOrdersReport");
		ResponseEntity<ResponseDTO> responseEntity=null;
		ResponseDTO responseDTO=new ResponseDTO();
		try{
			responseDTO.setResponseBody(reportServiceProcessorImpl.getStageOrdersReport(reportFromDate,reportToDate));
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch(Exception exp){
			logger.error("Exception in getStageOrdersReport() method",exp);			
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting ", "ReportServiceController.getStageOrdersReport");
		return responseEntity;
	}
	
	
	/**
	 * This method is the entry point for Stage Orders Report.
	 * This method gives all the records for PickupReport.
	 * This method produces response in "application/json" format
	 * @param request
	 * @return ResponseDTO
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/getHGOrdersReport/{fromDate}/{toDate}/{store}", produces = "application/json")
	public @ResponseBody	
	ResponseEntity<ResponseDTO> getHGOrdersReport(
			@PathVariable("fromDate") String reportFromDate,
			@PathVariable("toDate") String reportToDate,@PathVariable("store") String store) {
		logger.info("entering ", "ReportServiceController.getHGOrdersReport");
		ResponseEntity<ResponseDTO> responseEntity=null;
		ResponseDTO responseDTO=new ResponseDTO();
		try{
			responseDTO.setResponseBody(reportServiceProcessorImpl.getHGOrdersReport(reportFromDate, reportToDate,store));
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (DJException djEx) {
			responseDTO.setResponseBody(djEx);
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		catch(Exception e){
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "getHGOrdersReport", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting ", "ReportServiceController.getHGOrdersReport");
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/getShopInReport", produces = "application/json")
	public @ResponseBody	
	ResponseEntity<ResponseDTO> getShopInReport(@RequestBody RequestDTO requestDTO) {
		logger.info("entering ", "ReportServiceController.getShopInReport requestDTO : " + requestDTO);
		ResponseEntity<ResponseDTO> responseEntity=null;
		ResponseDTO responseDTO=new ResponseDTO();
		try{
			responseDTO.setResponseBody(reportServiceProcessorImpl.getShopInReport(requestDTO));
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (DJException djEx) {
			responseDTO.setResponseBody(djEx);
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		catch(Exception e){
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "getShopInReport", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting ", "ReportServiceController.getShopInReport");
		return responseEntity;
	}
	
}
