package com.searshc.mpuwebservice.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.exceptionhandler.FileExceptionHandler;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.ws.DJController;
import com.searshc.mpuwebservice.bean.CustomerDetailDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.MPUAssociateReportDTO;
import com.searshc.mpuwebservice.bean.PickUpDTO;
import com.searshc.mpuwebservice.bean.PickUpReturnDTO;
import com.searshc.mpuwebservice.bean.PickUpSelectedItems;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.bean.SalesCheckDetails;
import com.searshc.mpuwebservice.bean.ShopinRequestDTO;
import com.searshc.mpuwebservice.bean.ShopinServiceResponse;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.processor.PickUpServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebutil.constant.MPUWEBUtilConstants;

@DJController
public class PickUpServiceController {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(PickUpServiceController.class);
	
	@Autowired
	private PickUpServiceProcessor pickUpServiceProcessor;
	
	
//	@Autowired
//	private AssociateActivityServicesProcessor associateActivityServicesProcessor;
	
	
	/**The method to retrieve kiosk number on the basis of
	 * store number.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/kioskList/{storeNum}",produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getKioskList(@PathVariable String storeNum) {
		
		logger.info("getKioskList","Entering PickUpServicesController.getKioskList--storeNumber: " + storeNum);
		//requestNumber can be used as salesCheck number
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {	
			List<String> kioskList = pickUpServiceProcessor.getKioskList(storeNum);
			responseDTO.setResponseBody(kioskList);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			
		} catch (DJException djEx) {
			
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
			
		} catch (Exception exception) {
			
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(exception,  logger, "getKioskList", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"PickUpServicesController.getKioskList");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/propertyFromAdaptor/{storeNumber}/{propertyName}/{storeFormat}",produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getPropertyFromAdaptor(@PathVariable String storeNumber, @PathVariable String propertyName, @PathVariable String storeFormat){
		logger.info("getPropertyFromAdaptor","Entering PickUpServiceController.getPropertyFromAdaptor");
		//requestNumber can be used as salesCheck number
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {	
			
			String propertyValue = pickUpServiceProcessor.getPropertyFromAdaptor(storeNumber,propertyName,storeFormat);
			
			responseDTO.setResponseBody(propertyValue);

			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {

			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "getPropertyFromAdaptor", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("getPropertyFromAdaptor","Entering PickUpServiceController.getPropertyFromAdaptor");
		return respEntity;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/itemsForKiosk/{identifierValue}",produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getAllItemsForPickUp(@RequestParam("pickUpLoc") String pickUpLoc, @RequestParam("numType") String numType, @RequestParam("step") String step, 
			@PathVariable("identifierValue") String identifierValue, @RequestBody PickUpDTO pickUpDTO){

		logger.info("getAllItemsForPickUp","Entering PickUpServicesController.getAllItemsForPickUp -- pickUpLoc"+pickUpLoc + "numType : "+numType + "step : "+ step +"identifierValue : "+ identifierValue + "pickUpDTO : "+ pickUpDTO);
		
		if("RETURN".equals(pickUpLoc)) {
			
			return getAllItemsForReturn(numType, step, identifierValue, pickUpDTO);
		}
		//requestNumber can be used as salesCheck number
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {	
			
			PickUpReturnDTO pickUpItems = pickUpServiceProcessor.getAllItemsForPickUp(pickUpLoc,numType,step,identifierValue,pickUpDTO);
			
			responseDTO.setResponseBody(pickUpItems);

			//responseDTO.setResponseCode(((ItemDTO) pickUpItems.get(0)).getCommentList());
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
			
		} catch (Exception e) {

			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "getAllItemsForPickUp", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		 logger.info("exiting" ,"PickUpServicesController.getAllItemsForPickUp");

		return respEntity;
	}
	
	private ResponseEntity<ResponseDTO> getAllItemsForReturn(String numType, String step, String identifierValue, PickUpDTO pickUpDTO) {
		
		logger.info("getAllItemsForReturn","Entering PickUpServiceController.getAllItemsForReturn : numType "+ numType + "step :"+ step +"identifierValue : "+ identifierValue + "pickUpDTO :"+ pickUpDTO);
		//requestNumber can be used as salesCheck number
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {	
			PickUpReturnDTO returnItems = pickUpServiceProcessor.getAllItemsForReturn(numType,step,identifierValue,pickUpDTO);
			//responseDTO.setResponseBody(returnItems.getResponseData());
			responseDTO.setResponseBody(returnItems);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {

			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {

			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "getAllItemsForReturn", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"PickUpServiceController.getAllItemsForReturn");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/initiatePickUpForItems",produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> initiatePickUpForItems(@RequestBody PickUpSelectedItems obj){
		
		
		logger.info("initiatePickUpForItems","Entering PickUpServicesController.initiatePickUpForItems PickUpSelectedItems : "+ obj);
		//requestNumber can be used as salesCheck number
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {	
			
			String pickUpInitiation = pickUpServiceProcessor.initiatePickUpForItems(obj);
			responseDTO.setResponseBody(pickUpInitiation);
			//responseDTO.setResponseCode(((ItemDTO) pickUpItems.get(0)).getCommentList());
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {

			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {

			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "initiatePickUpForItems", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"PickUpServicesController.initiatePickUpForItems");
		return respEntity;
	}
	
	
	

	/**This method is used to update item for a return request in case of add item in Return scenario of KIOSK
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/returnMPUItemRequest", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> addItemInReturn(@RequestBody RequestDTO requestDTO, @RequestParam String action) {
		logger.info("Entering PickUpServiceController.addItemInReturn	requestDTO:",requestDTO);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			if(null!=action && ("createItem").equalsIgnoreCase(action))
			{
			pickUpServiceProcessor.addItemInReturn(requestDTO);
			}
			else if(null!=action && ("updateItem").equalsIgnoreCase(action)){
			pickUpServiceProcessor.updateMpuReturnItemList(requestDTO);	
			}
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.info("createMpuItem","End addItemInReturn took time:"+(endTime-startTime));
		} catch (DJException djEx) {

			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {

			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "addItemInReturn", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"PickUpServiceController.addItemInReturn");
		return respEntity;
	}
	
	/**This method is used to update the assigned user in the MPU Item List
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/assignMpuRequest", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> assignMpuRequest(@RequestBody RequestDTO requestDTO) {
		logger.info("Entering PickUpServiceController.assignMpuRequest	requestDTO: ",requestDTO);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			pickUpServiceProcessor.assignMpuRequest(requestDTO);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "assignMpuRequest", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"PickUpServiceController.assignMpuRequest");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/handHeldMPURequest", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> completeRequest(@RequestBody List<RequestDTO> requestDTOList, @RequestParam String action) {
		
		logger.info("completeRequest", "Entering PickUpServiceController.completeRequest action : " + action + " -- requestDTOList: " + requestDTOList);
		
		ObjectMapper theObjectMapper = new ObjectMapper();
		ArrayList<RequestDTO> requestDTOListConverted = theObjectMapper.convertValue(requestDTOList, new TypeReference<List<RequestDTO>>(){});
		
		if("CANCEL".equals(action)) {
			return cancelReturnin5(requestDTOListConverted.get(0));
		}
		
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		
		
		try {
			String response = pickUpServiceProcessor.completeRequest(requestDTOListConverted);
			if("true".equalsIgnoreCase(response)){
				respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
			}else{
				respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (DJException djEx) {
			
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "completeRequest", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"PickUpServiceController.completeRequest");
		return respEntity;

	}
	
	private ResponseEntity<ResponseDTO> cancelReturnin5(RequestDTO requestDTO) {
		
		logger.info("Entering PickUpServiceController.cancelReturnin5	requestDTO: ",requestDTO);
		
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			pickUpServiceProcessor.cancelReturnin5(requestDTO);
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
		} catch (DJException djEx) {
			
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {

			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "cancelReturnin5", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"PickUpServiceController.cancelReturnin5");
		return respEntity;

	}

/* Code was written in start of Dev but not being used now - Shipra
  	@RequestMapping(method = RequestMethod.POST, value = "/v1/updateCurbsideItems", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> updateCurbsideItems(@RequestBody List<RequestDTO> requestDTOs) {
		logger.info("Entering PickUpServiceController.updateCurbsideItems	requestDTO:",requestDTOs);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
//			pickUpServiceProcessor.updateCurbsideItems(requestDTOs);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);

		}catch (Exception e) {
			logger.error("createMpuItem",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,false);
		}
		 logger.info("exiting" ,"PickUpServiceController.updateCurbsideItems");
		return respEntity;
	}
*/	
	
	/**************Pritika ********************/
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/orderHelpRepair", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> orderHelpRepair(@RequestBody CustomerDetailDTO customerDetailDTO) {
		
		logger.info("Entering PickUpServiceController.orderHelpRepair	customerDetailDTO:",customerDetailDTO);
		
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			pickUpServiceProcessor.orderHelpRepair(customerDetailDTO);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			
		} catch (DJException djEx) {

			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {

			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "orderHelpRepair", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		
		logger.info("exiting" ,"orderHelpRepair");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/handHeldMPURequest/{storeNum}/{kioskName}/{transId}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> fetchOderForAssociate(@PathVariable String storeNum, @PathVariable String kioskName, @PathVariable String transId) {
		
		logger.info("Entering PickUpServiceController.fetchOderForAssociate","storeNum: " + storeNum + " -- kioskName : "+ kioskName);
		
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			
			responseDTO.setResponseBody(pickUpServiceProcessor.fetchOderForAssociate(storeNum, kioskName, transId));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			
		} catch (DJException djEx) {

			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {

			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "fetchOderForAssociate", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"fetchOderForAssociate");
		return respEntity;
		
	}
	
	/**************Pritika ********************/
	// shipra
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/createReturnKiosk", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> createRetunKiosk(@RequestBody List<RequestDTO> requestDTOs) {

		logger.info("Entering PickUpServiceController.createRetunKiosk","requestDTOs:" + requestDTOs);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			pickUpServiceProcessor.createReturnKiosk(requestDTOs);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "createRetunKiosk", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting" ,"PickUpServiceController.createRetunKiosk");
		return respEntity;
	}
	
	// get active mod users
	/*@RequestMapping(method = RequestMethod.GET, value = "/v1/activeModUsers/{storeNo}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> getActiveUserForMOD(@PathVariable("storeNo")String storeNo){
		logger.info("getActiveUserForMOD","Entering PickUpServiceController.getActiveUserForMOD");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try{
			String modUsers = null;
			modUsers  = pickUpServiceProcessor.getActiveUserForMOD(storeNo);
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
	}*/

	// insert user 
	/**This code is to notify mod of a coupon Bypass
	 * @param itemDTO
	 * @param storeNo
	 * @return
	 * @throws DJException
	 */
	@RequestMapping(method = RequestMethod.PUT,value="/v1/modNotificationForCouponBypass/{storeNo}" ,produces = "application/json")
	public @ResponseBody String notifyModForCouponBypass(@RequestBody ItemDTO itemDTO,@PathVariable("storeNo")String storeNo) throws DJException{
		String respEntity = "Success";
		
		logger.info("entering PickUpServiceController.notifyModForCouponBypass", "itemDTO=="+itemDTO.toString());
		try {
			respEntity = pickUpServiceProcessor.notifyModForCouponBypass(storeNo,itemDTO);
		}catch (Exception e) {
			//Exception is handled this way because no action is taken on Success or Failure at other end - Rohit
			logger.error("notifyModForCouponBypass",e);
			respEntity = "Failure";
		}
		logger.info("exiting notifyModForCouponBypass ", "");
		return respEntity;
		
	}
	
	/**This code is to notify mod of a coupon Bypass
	 * @param itemDTO
	 * @param storeNo
	 * @return
	 * @throws DJException
	 */
	@RequestMapping(method = RequestMethod.POST,value="/v1/modNotificationForSignOverride", produces = "application/json")
	public @ResponseBody String notifyModForSignOverride(@RequestBody ItemDTO itemDTO) throws DJException{
		String respEntity = "Success";
		
		logger.debug("entering notifyModForSignOverride", "itemDTO=="+itemDTO.toString());
		try {
			respEntity = pickUpServiceProcessor.notifyModForSignOverride(itemDTO.getStoreNumber(),itemDTO);
		}catch (Exception e) {
			//Exception is handled this way because no action is taken on Success or Failure at other end - Rohit
			logger.error("notifyModForSignOverride",e);
			respEntity = "Failure";
		}
		logger.debug("exiting notifyModForSignOverride ", "");
		return respEntity;
		
	}
	
	//getOrderList for shopin
	@RequestMapping(method = RequestMethod.GET, value = "/v1/getOrderListForShopin/{salesCheckno}/{storeNo}/{storeFormat}/{notificationId}/{sywrId:.+}", produces = "application/json")
	public @ResponseBody
	SalesCheckDetails getOrderListForShopin(@PathVariable("salesCheckno") String salesCheckno,@PathVariable("storeNo") String storeNo, @PathVariable("storeFormat") String storeFormat, 
			@PathVariable("notificationId") String notificationId, @PathVariable("sywrId") String sywrId){	
		logger.info("getOrderListForShopin","Entering PickUpServiceController.getOrderListForShopin salesCheckno: "+salesCheckno+"storeNo: "+storeNo+"storeFormat: "+storeFormat+"notificationId: "+notificationId+"sywrId: "+sywrId);
		//requestNumber can be used as salesCheck number		
		SalesCheckDetails salesCheckDetails = null;
		String numType = MpuWebConstants.SHOPIN_SALESCHECK;
		String identifierValue = salesCheckno;
		try {
			PickUpDTO pickUpDTO = pickUpServiceProcessor.getPickUpDTO(salesCheckno, storeNo, storeFormat, notificationId, sywrId);
			if(sywrId != null && sywrId.contains("@")){
				identifierValue = pickUpDTO.getAddressId();
				numType = MpuWebConstants.CUSTOMERID;
			}
			PickUpReturnDTO pickUpItems = pickUpServiceProcessor.getAllItemsForPickUp("SHOPIN",numType,"",identifierValue,pickUpDTO);	
			salesCheckDetails =pickUpServiceProcessor.getVehicleInformation(pickUpItems,salesCheckno);		
		}catch (Exception e) {
			logger.error("getOrderListForShopin",e);
			salesCheckDetails = new SalesCheckDetails();
			salesCheckDetails.setErrorMessage("No pickup Items is available for salescheck :"+salesCheckno);
		}
		 logger.info("exiting" ,"PickUpServicesController.getOrderListForShopin.");
		return salesCheckDetails;
	}
	
	//to create pickup from shopin
	@RequestMapping(method = RequestMethod.POST, value = "/v1/createPickForShopppingCurbside")
	@ResponseBody
	public ShopinServiceResponse createPickForShopIn(@RequestBody ShopinRequestDTO request){
		logger.info("createPickForShopIn","Entering PickUpServiceController.createPickForShopIn : "+request);
		ShopinServiceResponse shopinServiceResponse = null;
		try {
			shopinServiceResponse = pickUpServiceProcessor.initiatePickupForShopin(request);
		} catch (Exception e) {			
			logger.error("createPickForShopIn",e);	
		}
		logger.info("Exiting","PickUpServiceController.createPickForShopIn.");
		return shopinServiceResponse;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/insertNotDeliverReasonInDEJ/{storeNumber}/{userName}/{itemId}/{salescheck}")
	@ResponseBody public 
	ResponseEntity<ResponseDTO> insertNotDeliverReasonInDEJ(@PathVariable("storeNumber") String storeNumber,@PathVariable("userName") String userName,@PathVariable("itemId") String itemId,
			@PathVariable("salescheck") String salescheck) {
		
		logger.info("insertNotDeliverReasonInDEJ","Entering PickUpServiceController.insertNotDeliverReasonInDEJ : ");
		
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			
			pickUpServiceProcessor.insertNotDeliverReasonInDEJ(storeNumber,userName,itemId,salescheck);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);	
			
		} catch (DJException djEx) {
			
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		
		} catch (Exception e) {
			
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "insertNotDeliverReasonInDEJ", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("Exiting","PickUpServiceController.insertNotDeliverReasonInDEJ.");
		return respEntity;
	}
	
	
	/**
	 * This method is used for clear the EhCache
	 * @param storeNumber
	 * @param queueType
	 * @param kiosk
	 * @return
	 */
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/refreshAssociateCache/{storeNum}/{kioskName}", produces = "application/json")
	public @ResponseBody boolean refreshEhCache(@PathVariable String storeNum,
			@PathVariable String kioskName,
			@RequestParam(value="refreshAll",required=false) boolean refreshAll) {
		logger.info("Entering", "PickUpServicesController.refreshAssociateCache");
		boolean isCacheCleared = false;
		
		try {
			isCacheCleared = pickUpServiceProcessor.clearCache(storeNum,kioskName,refreshAll);
		} catch (DJException e) {
			logger.error("refreshEhCache",e);
		}
		logger.info("isCacheCleared = ", isCacheCleared);
		 
		return isCacheCleared;
	}
	
	/**
	 * This Service provides the data cached in EhCache
	 * @param storeNumber
	 * @param queueType
	 * @param kiosk
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/getCachedContent/{storeNumber}/{kiosk}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getCachedContent(@PathVariable("storeNumber") String storeNumber,
			@PathVariable("kiosk") String kiosk) {
		logger.info("Entering PickUpServicesController.getCachedContent	storeNumber:",storeNumber +": kiosk: "+kiosk);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(pickUpServiceProcessor.getCachedContent(storeNumber,kiosk));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "getCachedContent", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
	}
	
	/**
	 * This Service inserts data into mpu_associate_report
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/insertIntoMPUAssociateReport", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> insertIntoMPUAssociateReport(@RequestBody MPUAssociateReportDTO associateReport) {
		logger.info("Entering PickUpServicesController.insertIntoMPUAssociateReport request: ",associateReport);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			pickUpServiceProcessor.insertIntoMPUAssociateReport(associateReport);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		
		} catch (Exception e) {
			
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "insertIntoMPUAssociateReport", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
	}
	
	/**
	 * This Service updates mpu_associate_report
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/updateDivOperation/{storeNum}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateDivOperation(@PathVariable("storeNum") String storeNum,@RequestBody List<String> rqdIdList) {
		logger.info("Entering PickUpServicesController.updateDivOperation rqdIdList: ",rqdIdList);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			pickUpServiceProcessor.updateDivOperation(rqdIdList,storeNum);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			
		} catch (Exception e) {
			
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "updateDivOperation", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
	}
	
	/** The service to verify if the store 
	 * has repair pickup/dropoff option
	 * @param storeNum String
	 * @return ResponseEntity<ResponseDTO>
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/isRepairEnabled/{storeNum}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> isRepairEnabled(@PathVariable("storeNum") String storeNum) {
		logger.info("isRepairEnabled", "Entering PickUpServicesController.isRepairEnabled storeNum: " + storeNum);
		boolean repairEnabledFlag;
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			repairEnabledFlag = pickUpServiceProcessor.isRepairEnabled(storeNum);
			responseDTO.setResponseBody(repairEnabledFlag);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			
			logger.info("isRepairEnabled", "Exit PickUpServicesController.isRepairEnabled repairEnabledFlag: " + repairEnabledFlag);
			
		} catch (Exception e) {
			responseDTO.setResponseBody(FileExceptionHandler.logAndThrowDJException(e,  logger, "isRepairEnabled", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
	}
	
	
}