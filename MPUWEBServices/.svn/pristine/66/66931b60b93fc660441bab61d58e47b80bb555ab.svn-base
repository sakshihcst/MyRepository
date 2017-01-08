package com.searshc.mpuwebservice.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.searshc.mpuwebservice.bean.ActivityUserEntity;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.processor.ActiveUserProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebutil.constant.MPUWEBUtilConstants;

@Controller
public class ActiveUserController {
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(ActiveUserController.class);
	
	@Autowired
	private ActiveUserProcessor activeUserProcessor;
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/insertUser", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> insertUser(@RequestBody ActivityUserEntity activityUserEntity) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.info("Entering ActiveUserController.insertUser ","");
			logger.debug("Entering ActiveUserController.insertUser - startTime",startTime);
			activeUserProcessor.insertUser(activityUserEntity);
			
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("insertUser","End insertUser took time:"+(endTime-startTime));
		} catch (DJException djEx) {
			logger.error("insertUser",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("insertUser",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"ActiveUserController.insertUser");
		return respEntity;
	}

	
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/updateActiveUserFlag/{userId}/{loggedInTime}/{activeUserFlag}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateActiveUserFlag(@PathVariable("userId") String userId,
			@PathVariable("loggedInTime") String loggedInTime,
			@PathVariable("activeUserFlag") String activeUserFlag) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.info("Entering ActiveUserController.updateActiveUserFlag ","");
			logger.debug("Entering ActiveUserController.updateActiveUserFlag - startTime",startTime);
			activeUserProcessor.updateActiveUserFlag(userId, loggedInTime, activeUserFlag);
			
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("updateActiveUserFlag","End updateActiveUserFlag took time:"+(endTime-startTime));
		} catch (DJException djEx) {
			logger.error("updateActiveUserFlag",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateActiveUserFlag",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"ActiveUserController.updateActiveUserFlag");
		return respEntity;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/v1/updateModFlag/{userId}/{loggedInTime}/{modFlag}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateModFlag(@PathVariable("userId") String userId,
			@PathVariable("loggedInTime") String loggedInTime,
			@PathVariable("modFlag") boolean modFlag) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.info("Entering ActiveUserController.updateModFlag ","");
			logger.debug("Entering ActiveUserController.updateModFlag - startTime",startTime);
			activeUserProcessor.updateModFlag(userId, loggedInTime, modFlag);
			
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("updateModFlag","End updateModFlag took time:"+(endTime-startTime));
		} catch (DJException djEx) {
			logger.error("updateModFlag",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateModFlag",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"ActiveUserController.updateModFlag");
		return respEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/v1/updateLoggedOutTime/{userId}/{activeUser}/{loggedOutTime}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateLoggedOutTime(@PathVariable("userId") String userId,
			@PathVariable("activeUser") String activeUser,
			@PathVariable("loggedOutTime") String loggedOutTime) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.info("Entering ActiveUserController.updateLoggedOutTime ","");
			logger.debug("Entering ActiveUserController.updateLoggedOutTime - startTime",startTime);
			activeUserProcessor.updateLoggedOutTime(userId, activeUser, loggedOutTime);
			
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("updateLoggedOutTime","End updateLoggedOutTime took time:"+(endTime-startTime));
		} catch (DJException djEx) {
			logger.error("updateLoggedOutTime",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateLoggedOutTime",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"ActiveUserController.updateLoggedOutTime");
		return respEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/v1/getActiveUserForUserId/{userId}/{storeNum}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getActiveUserForUserId(@PathVariable("userId") String userId,@PathVariable("storeNum") String storeNum) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.info("Entering ActiveUserController.getActiveUserForUserId ","");
			logger.debug("Entering ActiveUserController.getActiveUserForUserId - startTime",startTime);
			boolean isActiveUser=false;
			
		
					
					if(activeUserProcessor.getActiveUserForUserId(userId,storeNum)!=null)
						isActiveUser=true;
					
			responseDTO.setResponseBody(isActiveUser);
			
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("getActiveUserForUserId","End getActiveUserForUserId took time:"+(endTime-startTime));
		} catch (DJException djEx) {
			logger.error("getActiveUserForUserId",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getActiveUserForUserId",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"ActiveUserController.getActiveUserForUserId");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/activeModUsers/{storeNo}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getActiveUserForMOD(@PathVariable("storeNo")String storeNo){
		logger.info("getActiveUserForMOD","Entering PickUpServiceController.getActiveUserForMOD");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try{
			String modUsers = null;
			modUsers  = activeUserProcessor.getActiveUserForMOD(storeNo);
			responseDTO.setResponseBody(modUsers);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);	
		} catch (DJException djEx) {

			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {

			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "getActiveUserForMOD", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting" ,"PickUpServiceController.getActiveUserForMOD");
		return respEntity;
	}

	
}
