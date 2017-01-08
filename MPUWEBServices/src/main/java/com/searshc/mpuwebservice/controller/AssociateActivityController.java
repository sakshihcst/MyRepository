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
import com.searshc.mpuwebservice.bean.MPUActivityDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.bean.WebActitivtyDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;

@Controller
public class AssociateActivityController {
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(WebServicesController.class);

	@Autowired
	@Qualifier("associateActivityServicesProcessorImpl")
	private AssociateActivityServicesProcessor associateActivityServicesProcessorImpl;

	@RequestMapping(method = RequestMethod.GET, value = "/v1/request/getWebActivityRecords/{reportDate}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> fetchWebActivityRecordsForDate(@PathVariable("reportDate") String reportDate) {
		logger.info("fetchWebActivityRecordsForDate()", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(associateActivityServicesProcessorImpl.fetchWebActivityRecordsForDate(reportDate));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("fetchWebActivityRecordsForDate",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("fetchWebActivityRecordsForDate",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("fetchWebActivityRecordsForDate()", "End");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/request/insertWebActivityRecords", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> insertWebActivityRecords(@RequestBody RequestDTO requestDTO) {
		logger.info("insertWebActivityRecords()", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		String validationMessage=null;
		try {
			List<WebActitivtyDTO> webActitivtyDTOs = requestDTO.getWebActitivtyList();
			validationMessage=associateActivityServicesProcessorImpl.validateWebActivityInsertInputs(webActitivtyDTOs);
			String validationOK=PropertyUtils.getProperty("mpu.web.validation.ok");
			if(! (validationOK.equalsIgnoreCase(validationMessage)) ){
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity ;
			}
			if(MpuWebConstants.WEB_TRANSFER.equalsIgnoreCase(requestDTO.getTask().getTaskType())){
				associateActivityServicesProcessorImpl.insertWebActivityRecords(webActitivtyDTOs);
			}
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("insertWebActivityRecords",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("insertWebActivityRecords",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("insertWebActivityRecords()", "End");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/requests/insertMPUActivity", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> insertMPUActivityRecords(@RequestBody RequestDTO requestDTO) {
		logger.info("insertMPUActivityRecords()", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		String validationMessage=null;
		try {
			List<MPUActivityDTO> mpuActivityList=requestDTO.getMpuActivityList();
			validationMessage=associateActivityServicesProcessorImpl.validateMPUActivityInsertInputs(mpuActivityList);
			String validationOK=PropertyUtils.getProperty("mpu.web.validation.ok");
			if(! (validationOK.equalsIgnoreCase(validationMessage)) ){
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity ;
			}
			associateActivityServicesProcessorImpl.insertMPUActivityData(mpuActivityList);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("insertMPUActivityRecords",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("insertMPUActivityRecords",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("insertMPUActivityRecords()", "End");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/requests/getOHMData/{storeNumber}/{date}/{kioskName}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getOHMData(@PathVariable("storeNumber")String storeNumber,@PathVariable("date")String date,@PathVariable("kioskName")String kioskName) {
		logger.info("Start getOHMData() storeNo:date:kioskName:"+storeNumber+":"+date+":",kioskName);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try{
			responseDTO.setResponseBody(associateActivityServicesProcessorImpl.fetchOHMData(storeNumber,date,kioskName));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getOHMData",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getOHMData",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("End getOHMData() storeNo:date:kioskName:"+storeNumber+":"+date+":",kioskName);
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/requests/updateMPURequest", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> updateMPURequest(@RequestBody RequestDTO requestDTO) {
		logger.info("Start updateMPURequest() ",requestDTO);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		String validationMessage=null;
		try{
			List<MPUActivityDTO> mpuActivityList=requestDTO.getMpuActivityList();
			validationMessage=associateActivityServicesProcessorImpl.validateMPUActivityInsertInputs(mpuActivityList);
			String validationOK=PropertyUtils.getProperty("mpu.web.validation.ok");
			if(! (validationOK.equalsIgnoreCase(validationMessage)) ){
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity ;
			}
			responseDTO.setResponseBody(associateActivityServicesProcessorImpl.updateMPURequest(mpuActivityList.get(0)));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("updateMPURequest",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateMPURequest",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("End updateMPURequest() ",requestDTO);
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/request/getMPUActivityRecords/{reportDate}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> fetchMPUActivityRecordsForDate(@PathVariable("reportDate") String reportDate) {
		logger.info("fetchMPUActivityRecordsForDate()", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(associateActivityServicesProcessorImpl.fetchMPUActivityRecordsForDate(reportDate));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("fetchMPUActivityRecordsForDate",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("fetchMPUActivityRecordsForDate",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("fetchMPUActivityRecordsForDate()", "End");
		return respEntity;
	}

}
