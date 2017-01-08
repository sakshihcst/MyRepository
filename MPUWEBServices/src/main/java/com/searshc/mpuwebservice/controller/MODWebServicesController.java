package com.searshc.mpuwebservice.controller;



import java.util.Set;

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
import com.searshc.mpuwebservice.bean.CSMTaskDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.processor.CSMProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceMODProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;


/**
 * @author gauti
 * @description contains methods to add/retrieve/update CSM task
 */
@Controller
public class MODWebServicesController {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(MODWebServicesController.class);
	
	@Autowired
	@Qualifier("MPUWebServiceMODProcessorImpl")
	private MPUWebServiceMODProcessor modProcesor;
	
	
	@Autowired
	CSMProcessor csmProcessor;
	
	
	
	/**
	 * @description inserts new CSM task into database and returns JSON response
	 * @param requestDTO created with CSMTaskDTO 
	 * @return JSON response with status code and description if success also includes newly created task id
	 */
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/CSM/insertCSMTask", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> insertCSMTask(@RequestBody RequestDTO requestDTO) {
		logger.info("MODWebServicesController.insertCSMTask", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		String validationMessage="";
		try {
			CSMTaskDTO csmTaskDTO=requestDTO.getCsmTaskDTO();
			
			validationMessage=modProcesor.validateCSMInsertInputs(csmTaskDTO);
			String validationOK=PropertyUtils.getProperty("mpu.csm.validation.ok");
			if(! (validationOK.equalsIgnoreCase(validationMessage)) ){
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity ;
			}
			
			int taskId=modProcesor.insertCSMTask(csmTaskDTO) ;
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);
			
		} catch (DJException djEx) {
			logger.error("insertCSMTask",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("insertCSMTask",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("MODWebServicesController.insertCSMTask", "ends");
		return respEntity;
	}
	
	/**
	 * @description returns all CSM task with details for mentioned store number and status
	 * @param storeNumber as String
	 * @param status as String
	 * @return JSON response containing all CSM task of specified storeNumber and status
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/CSM/getTaskList/{storeNumber}/{status}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getCSMTaskList(@PathVariable("storeNumber") String storeNumber,@PathVariable("status") String status) {
		logger.info("MODWebServicesController.getCSMTaskList", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {
			if(storeNumber==null ||storeNumber.trim().isEmpty()){
				String validationMessage=PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.storeNo");
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity;
			}
			if(status==null || status.trim().isEmpty()){
				String validationMessage=PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.status");
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity ;
			}
			logger.info("getTaskList", "Started for StoreNo/Status "+storeNumber+"/"+status);
			responseDTO.setResponseBody(modProcesor.getCSMTaskList(storeNumber,status)) ;
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			logger.info("getTaskList", "Ended successfully");
		} catch (DJException djEx) {
			logger.error("getCSMTaskList",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getCSMTaskList",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("getCSMTaskList", "exiting");
		return respEntity;
	}
	
	/**
	 * @description update the status of task id in CSM_TASK table
	 * @param taskId as int 
	 * @param storeNumber as String
	 * @param status as String
	 * @return JSON response with status code and description if success also includes updated task id
	 */
	
	@RequestMapping(method = RequestMethod.PUT, value = "v1/CSM/updateCSMTask", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> updateCSMTask(@RequestBody RequestDTO requestDTO){
		logger.info("MODWebServicesController.updateCSMTask", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			logger.info("updateCSMTask", "Start ");
			if(null==requestDTO || requestDTO.getCsmTaskDTO() ==null){
				String validationMessage=PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.DTOS");
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity ;
			}
			CSMTaskDTO csmDTO=requestDTO.getCsmTaskDTO();
			String validationOK=PropertyUtils.getProperty("mpu.csm.validation.ok");
			
			String validationMessage=modProcesor.validateCSMUpdateInputs(csmDTO);
			if(! (validationOK.equalsIgnoreCase(validationMessage)) ){
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity;
			}
			
			logger.info("updateCSMTask", "Start");
			int updatedTaskId=modProcesor.updateCSMTask(csmDTO.getTaskId(), csmDTO.getStoreNo() , csmDTO.getCsmTaskStatus(),csmDTO.getUpDatedBy(),csmDTO.getUpDatedDate());
			responseDTO.setResponseBody(updatedTaskId);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			
		} catch (DJException djEx) {
			logger.error("updateCSMTask",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateCSMTask",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("updateCSMTask", "exiting");
		return respEntity;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "v1/CSM/getCSMTaskDetail/{storeNumber}/{taskId}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getCSMTaskDetail(@PathVariable("storeNumber") String storeNumber,@PathVariable("taskId") int taskId){
		logger.info("MODWebServicesController.getCSMTaskDetail", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {
			if(storeNumber==null ||storeNumber.trim().isEmpty()){
				String validationMessage=PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.storeNo");
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity ;
			}
			if(taskId<=0){
				String validationMessage=PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.taskId");
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", validationMessage, null, true);
				return respEntity ;
			}
			logger.info("getCSMTaskDetail", "Started for StoreNo/taskId "+storeNumber+"/"+taskId);
			responseDTO.setResponseBody(modProcesor.getCSMTaskDetail(storeNumber,taskId)) ;
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			logger.info("getCSMTaskDetail", "Ended OK");
		} catch (DJException djEx) {
			logger.error("getCSMTaskDetail",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getCSMTaskDetail",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("MODWebServicesController.getCSMTaskDetail", "ends");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "v1/requests/CSM/completeAllCSMOpenTasks", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> completeAllCSMOpenTasks(){
		logger.info("Starting of " , "completeAllCSMOpenTasks()");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(modProcesor.closeAllOpenCSMTasks()) ;
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("completeAllCSMOpenTasks",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("completeAllCSMOpenTasks",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("Ending of " , "completeAllCSMOpenTasks()");
		return respEntity;
	}
	
	/**API to get the elapsed time between the order create time and store current time
	 * @param store
	 * @param date
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/requests/CSM/elapsedTime/{store}/{date}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getElapsedTime(@PathVariable("store")String store,@PathVariable("date") String date){
		logger.info("MODWebServicesController.getElapsedTime", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(csmProcessor.getTimeDifference(date, store)) ;
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getElapsedTime",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getElapsedTime",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("MODWebServicesController.getElapsedTime", "ends");
		return respEntity;
	}
	
	/**API to get the order for POST void salescheck number
	 * @param store
	 * @param salescheck
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/requests/CSM/getPostVoidOrder/{store}/{salescheck}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getPostVoidOrder(@PathVariable("store")String store,@PathVariable("salescheck") String salescheck){
		logger.info("MODWebServicesController.getPostVoidOrder", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(csmProcessor.getPostVoidOrder(store, salescheck)) ;
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getPostVoidOrder",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getPostVoidOrder",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("MODWebServicesController.beepToPrinter", "ends");
		return respEntity;
	}

	/**API to update the order for the post void action 
	 * @param store
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/requests/CSM/updatePostVoid/{store}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> updatePostVoid(@PathVariable("store")String store,@RequestBody RequestDTO requestDTO){
		logger.info("MODWebServicesController.updatePostVoid", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(csmProcessor.updatePostVoid(store, requestDTO));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("updatePostVoid",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updatePostVoid",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("MODWebServicesController.beepToPrinter", "ends");
		return respEntity;
	}

	/**API to beep the printer and print the test ticket by MOD
	 * @param store
	 * @param storeFmt
	 * @param printerId
	 * @param kiosk
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/requests/CSM/beepToPrinter/{store}/{storeFmt}/{printerId}/{kiosk}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> beepToPrinter(@PathVariable("store") String store,@PathVariable("storeFmt") String storeFmt,
			@PathVariable("printerId") String printerId,@PathVariable("kiosk") String kiosk) {
		
		logger.info("MODWebServicesController.beepToPrinter", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(csmProcessor.beepToPrinter(store, storeFmt, printerId, kiosk));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("beepToPrinter",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("beepToPrinter",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("MODWebServicesController.beepToPrinter", "ends");
		return respEntity;
	}
	/**API to CSM subscribe to NPOS
	 * @param store
	 * @param storeFmt
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/requests/CSM/subscribeCSMToNPOS/{store}/{storeFmt}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> subscribeCSMToNPOS(@PathVariable("store") String store,@PathVariable("storeFmt") String storeFmt) {
		logger.info("MODWebServicesController.subscribeCSMToNPOS", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(csmProcessor.subscribeCSMToNPOS(store, storeFmt));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("subscribeCSMToNPOS",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("subscribeCSMToNPOS",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("MODWebServicesController.subscribeCSMToNPOS", "ends");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/requests/CSM/logoutModActiveUsers")
	ResponseEntity<ResponseDTO> logoutModActiveUsers(){
		logger.info("MODWebServicesController.logoutModActiveUsers", "Start");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			Set<String> activeModStores = csmProcessor.logoutModActiveUsers();
			csmProcessor.updateNPOSToLogoutModActiveUsers(activeModStores);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (Exception e) {
			logger.error("Exception occured:", e);
			responseDTO.setResponseBody(e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("MODWebServicesController.logoutModActiveUsers","ends");
		return respEntity;
	}
	

}
