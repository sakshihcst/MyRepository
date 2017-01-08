package com.searshc.mpuwebservice.controller;


import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.mpu.backoffice.domain.Order;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;

@Controller

public class WebServicesController {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(WebServicesController.class);

	@Autowired
	@Qualifier("webServicesProcessorImpl")
	private MPUWebServiceProcessor webServicesProcessor;

	/**The Entry point for the order adaptor to create the web request.
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/requests", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> createRequest(@RequestBody RequestDTO requestDTO) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.info("Entering WebServicesController.createRequest ","");
			logger.debug("Entering WebServicesController.createRequest - startTime",startTime);
			webServicesProcessor.createValidOrder(requestDTO);
			
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("createRequest","End createRequest took time:"+(endTime-startTime));
		} catch (DJException djEx) {
			logger.error("createRequest",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("createRequest",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"WebServicesController.createRequest");
		return respEntity;
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "/item/{store}/{salescheck}", produces = "application/json")
	public void testWebRequest(@PathVariable("store") String store,@PathVariable("salescheck") String salescheck){
		try{
			webServicesProcessor.getTestResponse(store,salescheck);	
		}catch(Exception exception){
			
		}
		
	}*/

	/**This handles the update requests from the UI layer.
	 * @param actionDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/requests", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> updateRequest(@RequestBody ItemDTO actionDTO) {
		logger.info("Entering WebServicesController.updateRequest: actionDTO","actionDTO : " + actionDTO);
		logger.info("Entering WebServicesController.updateRequest: actionDTO","actionDTO.getRequestType() : " + actionDTO.getRequestType());
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			//long startTime=Calendar.getInstance().getTimeInMillis();
			int noOfRows=webServicesProcessor.updateOrderRequest(actionDTO.getRequestNumber(), actionDTO);
			logger.info("\n\n===WebServicesController.updateRequest===noOfRows======", noOfRows);
			if(noOfRows==0){
				responseDTO.setResponseBody(webServicesProcessor.updateFailedReason(actionDTO));
				responseDTO.setResponseCode(MpuWebConstants.UPDATE_FAIL_DESC_CODE);
				responseDTO.setResponseDesc(MpuWebConstants.UPDATE_FAIL_DESC);
			}else{
				responseDTO.setResponseCode(MpuWebConstants.UPDATE_DESC_CODE);
				responseDTO.setResponseDesc(MpuWebConstants.UPDATE_DESC);
			}
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
			
			//long endTime=Calendar.getInstance().getTimeInMillis();
			//responseDTO.setResponseBody("Update completed successfully");
//			respEntity= MPUWebServiceUtil.getResponseEntity(responseDTO,true);
		} catch (DJException djEx) {
			logger.error("updateRequest",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateRequest",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"WebServicesController.updateRequest");
		return respEntity;

	}
	
	/**
	 * This API is used for getting RequestDTO based on storeNumber and RequestNumber
	 * @param String requestNumber
	 * @param String storeNumber
	 * @param String fields optional
	 * @return ResponseEntity<ResponseDTO>
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/request/{requestId}/{strNum}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getRequest(@PathVariable("requestId") String requestId,
			@PathVariable("strNum") String strNum,
			@RequestParam(value="fields",required=false) String fields,@RequestParam(value="itemId",required=false) String itemId) {
		logger.debug("Entering WebServicesController.getRequest	requestNumber: ",requestId +": strNum: "+strNum +": fields: "+fields +": itemId: "+itemId);
		//requestNumber can be used as salesCheck number
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		List<String> status = Arrays.asList("OPEN","WIP");
		try {
			responseDTO.setResponseBody(webServicesProcessor.getRequestData(requestId,strNum,fields,itemId,status));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getRequest",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getRequest",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"WebServicesController.getRequest");
		return respEntity;
	}
	
	/**
	 * This API is used for getting List<ItemDTO> based on storeNumber and queueType
	 * @param String storeNumber
	 * @param String queueType
	 * @param String kiosk optional
	 * @return ResponseEntity<ResponseDTO>
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/items/{storeNumber}/{queueType}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getAllItemList(@PathVariable("storeNumber") String storeNumber,
			@PathVariable("queueType") String queueType,
			@RequestParam(value="kiosk",required=false) String kiosk,@RequestParam(value="isRequestListNonDej",required=false, defaultValue="N") String isRequestListNonDej,
			@RequestParam(value="isUserAssigned",required=false) String isUserAssigned) {
		logger.info("Entering WebServicesController.getAllItemList	storeNumber :","");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(webServicesProcessor.getAllItemList(storeNumber,queueType,kiosk,isRequestListNonDej,isUserAssigned));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getAllItemList",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getAllItemList",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting" ,"WebServicesController.getAllItemList");
		return respEntity;
	}
	 
	/**
	 * This method is used for clear the EhCache
	 * @param storeNumber
	 * @param queueType
	 * @param kiosk
	 * @return
	 */
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/items/refreshEhCache", produces = "application/json")
	public @ResponseBody boolean refreshEhCache(@RequestParam(value="refreshAll",required=false) String refreshAll) {
		logger.debug("Entering", "WebServicesController.refreshEhCache");
		boolean isCacheCleared = false;
		isCacheCleared = webServicesProcessor.clearCache(refreshAll);
		logger.info("isCacheCleared = ", isCacheCleared);
		 logger.debug("exiting" ,"WebServicesController.refreshEhCache");
		return isCacheCleared;
	}


	/**
	 * This Service provides the data cached in EhCache
	 * @param storeNumber
	 * @param queueType
	 * @param kiosk
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/items/getAllCachedContent/{storeNumber}/{queueType}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getAllCachedContent(@PathVariable("storeNumber") String storeNumber,
			@PathVariable("queueType") String queueType,
			@RequestParam(value="kiosk",required=false)String kiosk) {
		logger.info("Entering WebServicesController.getAllCachedContent:","");
		logger.debug("Entering WebServicesController.getAllCachedContent	storeNumber:",storeNumber +": queueType: "+queueType  +": kiosk: "+kiosk);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(webServicesProcessor.getAllCachedContent(storeNumber,queueType,kiosk));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getAllCachedContent",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getAllCachedContent",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		  logger.info("exiting" ,"WebServicesController.getAllCachedContent");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/storeDetails/{storeNumber}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getStoreDetails(@PathVariable("storeNumber") String storeNum){
		logger.info("Entering WebServicesController.getStoreDetails:","");
		logger.debug("Entering WebServicesController.getStoreDetails	storeNum:",storeNum);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(webServicesProcessor.getstoreDetails(storeNum));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getStoreDetails",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getStoreDetails",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting" ,"WebServicesController.getStoreDetails");
		return respEntity;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/assignUser/{store}/{itemId}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateAssignedUser(@PathVariable("store") String store,
			@PathVariable("itemId") String rqdId,
			@RequestParam(value="assignedUser",required=false) String user,
			@RequestParam(value="requestType",required=false) String requestType,
			@RequestParam(value="rqtId",required=false) String rqtId,
			@RequestParam(value="searsSalesId",required=false) String searsSalesId){
		logger.info("Entering WebServicesController.updateAssignedUser:","");
		logger.debug("Entering WebServicesController.updateAssignedUser	rqdId:",rqdId + "user:" +user  + "requestType:" +requestType  + "rqtId:" +rqtId );
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(webServicesProcessor.assignUser(store,rqdId,user,URLDecoder.decode(requestType),rqtId,searsSalesId));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("updateAssignedUser",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateAssignedUser",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting" ,"WebServicesController.updateAssignedUser");
		return respEntity;
	}
	
	/**
	 * This service is used for printing tickets
	 * @param store
	 * @param rqdId
	 * @param user
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/print/{store}/{printerId}/{rqdId}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> printLabel(@PathVariable("store") String storeNum,
			@PathVariable("printerId") String printerId,
			@PathVariable("rqdId") String rqdId,
			@RequestParam(value="userId",required=false) String user,
			@RequestParam(value="queueType",required=false) String queueType,
			@RequestParam(value="type") String type,
			@RequestParam(value="rqtId",required=false) String requestId,
			@RequestParam(value="reprintFlag",required=false) boolean reprintFlag){
		logger.debug("Entering WebServicesController.printLabel	storeNum:",storeNum + "printerId:" +printerId  + "rqdId:" +rqdId  + "user:" +user  + "queueType:" +queueType  + "type:" +type 
				+ "requestId:" +requestId + "reprintFlag:" +reprintFlag);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		queueType = URLDecoder.decode(queueType);
		try {
			if(type != null && ("StageTicket".equalsIgnoreCase(type)||("HoldAndGo".equalsIgnoreCase(type))||("SKU991SendToPMT".equalsIgnoreCase(type)))) {
				responseDTO.setResponseBody(webServicesProcessor.printLabel(storeNum,printerId,rqdId,type,user,queueType,requestId,reprintFlag));
			} else if(type != null && "LayawayPackageTickets".equalsIgnoreCase(type)) {
				//In this scenario, rqdId contains package numbers separated by delimiter '-'
				responseDTO.setResponseBody(webServicesProcessor.printPackageLabel(storeNum, printerId, rqdId, type, user, queueType, requestId));
			}
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("printLabel",e + (null != MDC.get("txnId") ? " [txId:" +(String) MDC.get("txnId") + "]" : ""));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting" ,"WebServicesController.printLabel"+ (null != MDC.get("txnId") ? " [txId:" +(String) MDC.get("txnId") + "]" : ""));
		return respEntity;
	}

    /**Call to create Layaway packages
     * @param userId
     * @param storeNo
     * @param rqtId
     * @param numberOfPackages
     * @param fromLocation
     * @param toLocation
     * @return ResponseEntity<ResponseDTO>
     */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/createLayawayPackages/{userId}/{storeNo}/{rqtId}/{numberOfPackages}/{fromLocation}/{salescheck}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> insertPackageDetails(@PathVariable("userId") String userId,@PathVariable("storeNo") String storeNo,
    		@PathVariable("rqtId") String rqtId,
    		@PathVariable("numberOfPackages") String numberOfPackages,
    		@PathVariable("fromLocation") String fromLocation,@PathVariable("salescheck") String salescheck){
		logger.debug("entering WebServicesController.insertPackageDetails  : ","userId"+userId+
				" storeNo"+storeNo+
				" rqtId"+rqtId+
				" numberOfPackages"+numberOfPackages+
				" fromLocation"+fromLocation+
				" salescheck"+salescheck);
		
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			
			responseDTO.setResponseBody(webServicesProcessor.insertPackageDetails(userId,storeNo,rqtId,numberOfPackages,fromLocation,salescheck));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("insertPackageDetails",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("insertPackageDetails",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
	}

    /**Call to update bin location of Layaway package
     * @param packageNumber
     * @param storeNo
     * @param binNumber
     * @return ResponseEntity<ResponseDTO>
     */
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/updatePackageDetails/{storeNo}/{packageNumber}/{binNumber}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updatePackageDetails(@PathVariable("storeNo") String storeNo,
    		@PathVariable("packageNumber") String packageNumber,
    		@PathVariable("binNumber") String binNumber){
		logger.debug("entering WebServicesController.updatePackageDetails  : ","packageNumber"+packageNumber+
				" storeNo"+storeNo+
				" binNumber"+binNumber);
		
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			
			responseDTO.setResponseBody(webServicesProcessor.updatePackageDetails(storeNo,packageNumber,binNumber));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("updatePackageDetails",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updatePackageDetails",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
	}
	
	/*Service to get the status of the request
	 * @param storeNumber
	 * @param requestId
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value="v1/isOrderExpire/{storeNumber}/{rqtId}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> isOrderExpire(@PathVariable("storeNumber") String storeNumber,
			@PathVariable("rqtId") String rqtId){
		logger.info("entering" ,"WebServicesController.isOrderExpire");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(webServicesProcessor.checkOrderExpired(storeNumber, rqtId));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("isOrderExpire",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("isOrderExpire",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting" ,"WebServicesController.isOrderExpire");
		return respEntity;
		
	}
	
		@RequestMapping(method = RequestMethod.GET,value="v1/getAllHostServer" ,produces = "application/json")
	public @ResponseBody ResponseEntity<Map<String,String>> getHostServers() throws DJException{
		ResponseEntity<Map<String,String>> respEntity = null;
		Map<String,String> responseDTO = new HashMap<String, String>();
		logger.debug("entering getHostServers", "");
		try {
			responseDTO = (webServicesProcessor.getHostServers());
			respEntity = new ResponseEntity<Map<String,String>>(responseDTO, HttpStatus.OK);
		}catch (Exception e) {
			logger.error("getHostServers",e);
			respEntity = new ResponseEntity<Map<String,String>>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug("exiting getHostServers", "");
		return respEntity;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/v1/queuestats", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> selectQueuestats(@RequestParam String storeno,@RequestParam String kiosk )
			throws SQLException {

		ResponseEntity<?> responseRequestQueueTranss = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			
			responseDTO.setResponseBody(webServicesProcessor.selectQueuestats(storeno, kiosk));
			responseRequestQueueTranss = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("selectQueuestats",djEx);
			responseDTO.setResponseBody(djEx);
			responseRequestQueueTranss = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
			
			djEx.printStackTrace();
		} catch (Exception e) {
			logger.error("selectQueuestats",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			responseRequestQueueTranss = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
			e.printStackTrace();
		}
		return responseRequestQueueTranss;

	}
	
	@RequestMapping(method = RequestMethod.POST,value="v1/updateVoidrequest/{requestNumber}/{store}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateVoidRequest(@PathVariable("store") String store ,
			@PathVariable("requestNumber") String requestNumber,
			@RequestBody RequestDTO requestDTO  ) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(webServicesProcessor.updateVoidRequest(requestNumber, store,requestDTO));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("updateVoidRequest",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateVoidRequest",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
		
	}
	
	@RequestMapping(method = RequestMethod.PUT,value="v1/cancelExpireHGRequest/{requestNumber}/{store}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> cancelExpireHGRequest(@PathVariable("store") String store ,
			@PathVariable("requestNumber") String requestNumber,
			@RequestBody RequestDTO requestDTO) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering WebServicesController.cancelExpireHGRequest - startTime",startTime);
			if(null!=requestDTO && null!=requestDTO.getOrder()){
				if(MpuWebConstants.CANCEL_REQUEST.equalsIgnoreCase(requestDTO.getOrder().getRequestStatus()) ||
						MpuWebConstants.EDIT.equalsIgnoreCase(requestDTO.getOrder().getRequestStatus())){
					responseDTO.setResponseBody(webServicesProcessor.cancelItems(requestDTO));
				}else{
					webServicesProcessor.cancelExpireRequest(requestDTO,store);
				}
			}
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("cancelExpireHGRequest","End cancelExpireHGRequest took time:"+(endTime-startTime));
		} catch (DJException djEx) {
			logger.error("cancelExpireHGRequest",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("cancelExpireHGRequest",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
		
	}
	/**
	 * This service is used for printing locker tickets
	 * @param store
	 * @param lockerPin
	 * @param customerName
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/print/printLockerTicket/{store}/{lockerPin}/{customerName}/{kiosk}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> printLockerTicket(@PathVariable("store") String storeNum,
			@PathVariable("lockerPin") String lockerPin,
			@PathVariable("customerName") String customerName,
			@PathVariable("kiosk") String kiosk){
		logger.info("Entering WebServicesController.printLockerTicket:","");
		logger.debug("Entering WebServicesController.printLockerTicket	storeNum:",storeNum + "lockerPin:" +lockerPin  + "customerName:" +customerName);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(webServicesProcessor.printLockerTicket(storeNum,lockerPin,customerName,kiosk));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("printLockerTicket",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("printLockerTicket",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting" ,"WebServicesController.printLockerTicket");
		return respEntity;
	}
	
		/**
	 * This Service updates the item whose pick up has been initiated from kiosk/curbside
	 * @param requestDTO
	 * @return boolean
	 */
	@RequestMapping(method = RequestMethod.POST,value="v1/updateRequestForPickUp/" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateRequestForPickUp(@RequestBody RequestDTO requestDTO) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering updateRequestForPickUp", "requestDTO=="+requestDTO.toString());
		try {
			responseDTO.setResponseBody(webServicesProcessor.updateRequestForPickUp(requestDTO));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("updateRequestForPickUp",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateRequestForPickUp",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting updateRequestForPickUp ", "");
		return respEntity;
		
	}
	
/*	*//** This service is used to get list for COM exception
	 * @param storeNumber
	 * @param date
	 * @return
	 *//*
	@RequestMapping(method = RequestMethod.GET, value = "/v1/report/COMException/{storeNumber}/{date}/{status}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getCOMExceptionList(@PathVariable("storeNumber") String storeNumber,
			@PathVariable("date") String date,@PathVariable("status") String status) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering getCOMExceptionList", "storeNumber=="+storeNumber+"date=="+date+"status=="+status);
		try {
			responseDTO.setResponseBody(webServicesProcessor.getCOMExceptionList(storeNumber, date,status));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("getAllItemList",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting getCOMExceptionList", "");
		return respEntity;
	}*/
	
/*	@RequestMapping(method = RequestMethod.PUT,value="v1/updateRequestForCOM/{user}/{store}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateRequestForCOM(@RequestBody HashMap<String, String> reqInfo,@PathVariable("user") String user,
			@PathVariable("store") String store) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering updateRequestForCOM", "reqInfo=="+reqInfo.toString()+"user=="+user+"store"+store);
		try {
			responseDTO.setResponseBody(webServicesProcessor.updateRequestForCOM(reqInfo,user,store));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("updateRequestForPickUp",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting updateRequestForCOM", "");
		return respEntity;
		
	}*/
	
	@RequestMapping(method = RequestMethod.GET,value="v1/getItemStatusQty/{store}/{itemId}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateRequestForCOM(@PathVariable("store") String store,
			@PathVariable("itemId") String itemId) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering updateRequestForCOM ", "store=="+store+"itemId=="+itemId);
		try {
			responseDTO.setResponseBody(webServicesProcessor.getItemQty(store, itemId));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("updateRequestForCOM",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("updateRequestForCOM",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		logger.info("exiting" ,"WebServicesController.updateRequestForPickUp");
		return respEntity;
		
	}
	
	/**
	 * Print Customer Care Coupon
	 * 
	 * @param store
	 * @param printerId
	 * @param customerName
	 * @param totalTimeElapsed
	 * @param couponType
	 * @param lang
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,
			value = "/v1/printCustomerCareCoupon/{store}/{customerName}/{totalTimeElapsed}/{couponType}/{lang}/{kiosk}",
			produces = "application/json")
	public @ResponseBody String printCustomerCareCoupon(@PathVariable("customerName") String customerName,
			@PathVariable("store") String store,
			@PathVariable("totalTimeElapsed") String totalTimeElapsed,
			@PathVariable("couponType") String couponType,
			@PathVariable("lang") String lang,
			@PathVariable("kiosk") String kiosk) {
		logger.info("WebServicesController:PrintCustomerCareCoupon::", "Entering->\n");
		String status="Failure";
		try{
			status = webServicesProcessor.printCustomerCareCoupon(customerName,store,totalTimeElapsed,couponType,lang,kiosk);
			logger.info("PrintCustomerCareCoupon::status==", status);
		}catch (Exception e) {
			logger.error("PrintCustomerCareCoupon",e);
		}
		logger.info("WebServicesController:PrintCustomerCareCoupon::", "Exiting->\n");
		return status;
	}
	
	/*Service to get the status of the item at the time of fetching from the NPOS
	 * @param storeNumber
	 * @param salescheck
	 * @param identifier
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value="v1/getItemStatus/{storeNo}/{salesCheck}/{divItemSku}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getItemBinStatus(@PathVariable("storeNo") String storeNumber,
			@PathVariable("salesCheck") String salesCheck,@PathVariable("divItemSku") String divItemSku){
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering getItemBinStatus", "storeNumber=="+storeNumber+"salesCheck=="+salesCheck+"divItemSku=="+divItemSku);
		try {
			responseDTO.setResponseBody(webServicesProcessor.getItemBinStatus(storeNumber, salesCheck,divItemSku));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getItemBinStatus",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getItemBinStatus",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting getItemBinStatus", "");
		return respEntity;
		
	}	
	
	/***
	 * Service that will validate if store id DEJ or NON DEJ Store.
	 * @param storeID
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/validateDEJStore/{storeID}")
	public @ResponseBody boolean validateDEJStore(@PathVariable("storeID")String storeID)
	{
		logger.info("entering" ,"WebServicesController.validateDEJStore");
		boolean isDEJStore = false;
		List<String> kioskList = new ArrayList<String>();
		try {
		kioskList = webServicesProcessor.getKioskList(storeID);
		}catch (Exception e) {
			logger.error("validateDEJStore:",e);
			//respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		if(kioskList.size() > 0)
		{
			isDEJStore = true;
		}
		logger.info("exiting" ,"WebServicesController.validateDEJStore");
		return isDEJStore;

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/updateObuAfterPickUp", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> sendPickedUpResponse(@RequestBody Order order) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		boolean sendStatus=false;
		try {
			
			sendStatus = webServicesProcessor.sendPickedUpResponse(order);
			responseDTO.setResponseBody(sendStatus);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, sendStatus);
		}
		catch (Exception e) {
			logger.info("In Catch block of WebServicesController.sendPickedUpResponse==", e.getStackTrace());
			logger.error("sendPickedUpResponse==",e);
			responseDTO.setResponseBody(sendStatus);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,false);
		}
		 logger.info("exiting" ,"WebServicesController.sendPickedUpResponse");
		return respEntity;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/nposUpdateManual/{storeID}/{rqtId}")
	public @ResponseBody boolean nposUpdateManual(@PathVariable("storeID")String storeID,
			@PathVariable("rqtId")String rqtId)
	{
		logger.info("entering" ,"WebServicesController.nposUpdateManual");
		boolean nposUpdate = true;
		try{
			webServicesProcessor.nposManualUpdate(storeID, rqtId);
		}catch(Exception exp){
			logger.error("Exception = ", exp);
			return false;
		}
		logger.info("exiting" ,"WebServicesController.nposUpdateManual");
		return nposUpdate;

	}
	@RequestMapping(method = RequestMethod.GET, value = "v1/requestStatus", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> checkStatus(@RequestParam(value="storeId",required=true) String storeId,@RequestParam(value="requestId",required=true) String requestId) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO  responseDTO=new ResponseDTO();
		logger.debug("entering checkStatus", "storeId=="+storeId+"salesCheck=="+requestId);
		try{
			responseDTO.setResponseBody(webServicesProcessor.checkStatus(storeId,requestId));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("checkStatus",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("checkStatus",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting checkStatus", "");
		return respEntity;

}

	/**
	 * This service is used for printing Return tickets
	 * @param store
	 * @param transId
	 * @param printerId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/printReturnTicket/{store}/{printerId}/{transId}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> printReturnTicket(@PathVariable("store") String storeNum,
			@PathVariable("printerId") String printerId,
			@PathVariable("transId") String transId,
			@RequestParam(value="userId",required=true) String user,
			@RequestParam(value="queueType",required=true) String queueType,
			@RequestParam(value="type",required=true) String type,
			@RequestParam(value="reprintFlag",required=false) boolean reprintFlag,
			@RequestParam(value="kioskName",required=true) String kioskName,
			@RequestParam(value="salesCheck",required=true) String salesCheck){
		logger.debug("Entering WebServicesController.printReturnTicket	storeNum:",storeNum + "printerId:" +printerId  + "transId:" +transId  + "user:" +user  + "queueType:" +queueType  + "type:" +type  + "reprintFlag:" +reprintFlag);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			if(type != null && "ReturnTicket".equalsIgnoreCase(type)) {
				//Call service method to print both return tickets
				responseDTO.setResponseBody(webServicesProcessor.printReturnTicket(storeNum,printerId,transId,type,user,queueType,kioskName,salesCheck));
			} else if(type != null && ("MerchandiseTag".equalsIgnoreCase(type) || "ReturnClaimCheck".equalsIgnoreCase(type))) {
				//Call service method to print either Merchandise tag or Claim Check
				responseDTO.setResponseBody(webServicesProcessor.printReturnTypeTicket(storeNum, printerId, transId, type, user, queueType,reprintFlag,kioskName,salesCheck));
			}
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("printReturnTicket",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("printReturnTicket",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting" ,"WebServicesController.printReturnTicket");
		return respEntity;
	}

	
	@RequestMapping(method = RequestMethod.GET,value="v1/markNoResponse/{store}/{requestNumber}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> markNoResponse(@PathVariable("store") String store ,
			@PathVariable("requestNumber") String requestNumber) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering WebServicesController.markNoResponse - startTime",startTime);
			logger.info("Marking request as no respons for",requestNumber);
			webServicesProcessor.markNoResponse(store,requestNumber,MpuWebConstants.NORESPONSE);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("markNoResponse","End markNoResponse took time:"+(endTime-startTime));
		} catch (DJException djEx) {
			logger.error("markNoResponse",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("markNoResponse",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
		
	}
	/**
	 * This service is used for testing the printer
	 * @param store
	 * @param transId
	 * @param printerId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/beepToPrinter/{store}/{printerId}/{storeFormat}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> beepToPrinter(@PathVariable("store") String storeNum,
			@PathVariable("printerId") String printerId,
			@PathVariable("storeFormat") String storeFormat){
		logger.debug("Entering WebServicesController.beepToPrinter	storeNum:",storeNum + "printerId:" +printerId  + "storeFormat:" +storeFormat);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(webServicesProcessor.beepToPrinter(storeNum,printerId,storeFormat));
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
		logger.debug("exiting" ,"WebServicesController.beepToPrinter");
		return respEntity;
	}

	/**
 * This Service updates CardSwiped Flag In Blob
 * @param requestDTO
 * @return boolean
 */
@RequestMapping(method = RequestMethod.PUT,value="/v1/updateCardSwipedFlagInBlob" ,produces = "application/json")
public @ResponseBody ResponseEntity<ResponseDTO> updateCardSwipedFlagInBlob(@RequestBody RequestDTO requestDTO) throws DJException{
	ResponseEntity<ResponseDTO> respEntity = null;
	ResponseDTO responseDTO = new ResponseDTO();
	logger.debug("entering updateCardSwipedFlagInBlob", "requestDTO=="+requestDTO.toString());
	try {
		responseDTO.setResponseBody(webServicesProcessor.updateCardSwipedFlagInBlob(requestDTO));
		respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
	} catch (DJException djEx) {
		logger.error("updateCardSwipedFlagInBlob",djEx);
		responseDTO.setResponseBody(djEx);
		respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
	} catch (Exception e) {
		logger.error("updateCardSwipedFlagInBlob",e);
		responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
		respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
	}
	logger.debug("exiting updateCardSwipedFlagInBlob ", respEntity);
	return respEntity;
}

/**
 * this method will update the from location of all the staged items that have not been picked up with the default bin# at EOD
 * @return
 */

// @RequestMapping(method = RequestMethod.GET, value = "/v1/updateSameDayHFMbin/{storeNum}",produces = "application/json")
//public ResponseEntity<ResponseDTO> updateSameDayHFMbin(@PathVariable String storeNum) throws DJException {	
//	ResponseEntity<ResponseDTO> respEntity = null;
//	ResponseDTO responseDTO = new ResponseDTO();
//	logger.info("updateHFMbin","Entering WebServicesController.updateHFMbin" );
//	try {
//	responseDTO.setResponseBody(webServicesProcessor.updateHFMbin(storeNum));
//	respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
//	logger.debug("exiting" ,"WebServicesController.updateHFMbin");
//	}
//	catch (DJException dje) {
//		logger.error("updateHFMbin",dje);
//		responseDTO.setResponseBody(dje);
//		respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
//	} catch (Exception e) {
//		logger.error("updateHFMbin",e);
//		responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
//		respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
//	}
//
//	return respEntity;
//}


}
