package com.searshc.mpuwebservice.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.ws.DJController;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.processor.AdditionalServiceProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;


@DJController
public class AddnServiceController {
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(AddnServiceController.class);
	@Autowired
	private MPUWebServiceProcessor webServicesProcessorImpl;
	
	@Autowired
	private AdditionalServiceProcessor additonalServicesProcessorImpl;
	
	
	
	/*Service to get the pick up status of item(This is a throw away service)
	 * @param storeNumber
	 * @param salescheck
	 * @param identifier
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value="v1/isPickedUp/{storeNo}/{requestId}/{itemId}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> isPickedUp(@PathVariable("storeNo") String storeNumber,
			@PathVariable("requestId") String requestId,@PathVariable("itemId") String itemId){
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering getItemBinStatus", "storeNumber=="+storeNumber+"requestId=="+requestId+"itemId=="+itemId);
		try {
			responseDTO.setResponseBody(webServicesProcessorImpl.isPickedUp(storeNumber, requestId,itemId));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("isPickedUp",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("isPickedUp",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting getItemBinStatus", "");
		return respEntity;
		
	}
	
	
	
	/*Service to get the status of the item at the time of fetching from the NPOS
	 * @param storeNumber
	 * @param salescheck
	 * @param identifier
	 * 
	 */
/*	@RequestMapping(method = RequestMethod.GET,value="v1/getItemStatus/{storeNo}/{salesCheck}/{divItemSku}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getItemBinStatus(@PathVariable("storeNo") String storeNumber,
			@PathVariable("salesCheck") String salesCheck,@PathVariable("divItemSku") String divItemSku){
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering getItemBinStatus", "storeNumber=="+storeNumber+"salesCheck=="+salesCheck+"divItemSku=="+divItemSku);
		try {
			responseDTO.setResponseBody(webServicesProcessor.getItemBinStatus(storeNumber, salesCheck,divItemSku));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("isOrderExpire",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting getItemBinStatus", "");
		return respEntity;
		
	}*/
	
	/*
	 * This Service updates the item whose pick up has been initiated from kiosk/curbside
	 * @param requestDTO
	 * @return boolean
	 */
/*	@RequestMapping(method = RequestMethod.POST,value="v1/updateRequestForPickUp/" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateRequestForPickUp(@RequestBody RequestDTO requestDTO, @RequestParam("stockDecrement") String stockDecrement) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering updateRequestForPickUp", "requestDTO=="+requestDTO.toString());
		try {
			responseDTO.setResponseBody(webServicesProcessor.updateRequestForPickUp(requestDTO));
			// Commented for Rollback 24049
			responseDTO.setResponseBody(webServicesProcessor.updateRequestForPickUp(requestDTO,stockDecrement));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("updateRequestForPickUp",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting updateRequestForPickUp ", "");
		return respEntity;
		
	}*/
	
	

	
	/* This service is used to get list for COM exception
	 * @param storeNumber
	 * @param date
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/report/COMException/{storeNumber}/{date}/{status}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getCOMExceptionList(@PathVariable("storeNumber") String storeNumber,
			@PathVariable("date") String date,@PathVariable("status") String status) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering getCOMExceptionList", "storeNumber=="+storeNumber+"date=="+date+"status=="+status);
		try {
			responseDTO.setResponseBody(webServicesProcessorImpl.getCOMExceptionList(storeNumber, date,status));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getCOMExceptionList",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getCOMExceptionList",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.debug("exiting getCOMExceptionList", "");
		return respEntity;
	}
	
	
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
	
/*	@RequestMapping(method = RequestMethod.GET,value="v1/getItemStatusQty/{store}/{itemId}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateRequestForCOM(@PathVariable("store") String store,
			@PathVariable("itemId") String itemId) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering updateRequestForCOM ", "store=="+store+"itemId=="+itemId);
		try {
			responseDTO.setResponseBody(webServicesProcessor.getItemQty(store, itemId));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("updateRequestForPickUp",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		logger.info("exiting" ,"WebServicesController.updateRequestForPickUp");
		return respEntity;
		
	}*/
	

/*	@RequestMapping(method = RequestMethod.GET,value="v1/getNoOfPkgs/{store}/{salesCheck}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getNoOfPackages(@PathVariable("store") String store,
			@PathVariable("salesCheck") String salesCheck) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering updateRequestForCOM ", "store=="+store+"salesCheck=="+salesCheck);
		try {
			responseDTO.setResponseBody(webServicesProcessor.getNoOfPackages(store, salesCheck));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("updateRequestForPickUp",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		logger.info("exiting" ,"WebServicesController.updateRequestForPickUp");
		return respEntity;
		
	}*/
	
	
	@RequestMapping(method = RequestMethod.GET,value="v1/gerRequestIDForSC/{store}/{salesCheck}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getRequestIDForSC(@PathVariable("store") String store,
			@PathVariable("salesCheck") String salesCheck,
			@RequestParam(value="isKiosk",required=false) boolean isKiosk) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering gerRequestIDForSC ", "store=="+store+"salesCheck=="+salesCheck);
		try {
			if(isKiosk){
				responseDTO.setResponseBody(webServicesProcessorImpl.checkIsActiveRequestExisting(store, salesCheck));
			}else{
				responseDTO.setResponseBody(webServicesProcessorImpl.getRequestIdbySalescheck(store, salesCheck));
			}
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getRequestIDForSC",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getRequestIDForSC",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		logger.info("exiting" ,"WebServicesController.gerRequestIDForSC");
		return respEntity;
		
	}
	
	/*
	 *To check if a store is platform store
	 *
	 */
	
	@RequestMapping(method = RequestMethod.GET,value="v1/isPlatformStore/{store}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> isPlaformStore(@PathVariable("store") String storeNumber) throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering getPlaformStoreList ", "");
		try {
			/*
			 * Check if the store number is of five digit
			 * if not apply left padding to it 
			 */
			if(null!=storeNumber && storeNumber.length()<5){
				storeNumber = StringUtils.leftPad(storeNumber, 5, '0');
			}
			responseDTO.setResponseBody(webServicesProcessorImpl.isPlaformStore(storeNumber));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("gerRequestIDForSC",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		logger.info("exiting" ,"isPlaformStore");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET,value="v1/getDDRMetaCache" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getDDRMetaCache() throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering getDDRMetaCache ", "");
		try {
			responseDTO.setResponseBody(webServicesProcessorImpl.getDDRMetaCache());
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("getDDRMetaCache",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		logger.info("exiting" ,"getDDRMetaCache");
		return respEntity;
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value="v1/refreshDDRMetaCache" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> refreshDDRMetaCache() throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering getDDRMetaCache ", "");
		try {
			responseDTO.setResponseBody(webServicesProcessorImpl.refreshDDRMetaCache());
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("getDDRMetaCache",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		logger.info("exiting" ,"getDDRMetaCache");
		return respEntity;
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value="v1/getAppServer/{store}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getAppServer(@PathVariable("store") String storeNumber)  {
		ResponseEntity<ResponseDTO> respEntity = null; 
		ResponseDTO responseDTO = new ResponseDTO(); 
		logger.debug("entering getAppServer ", ""); 
		try { 
		if(null != storeNumber && storeNumber.length() < 5){
			storeNumber = StringUtils.leftPad(storeNumber, 5, '0'); 
		}
		
		responseDTO.setResponseBody(webServicesProcessorImpl.getAppServer(storeNumber));
		 respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		 } catch(Exception e) { 
		 logger.error("getAppServer", e); 
		 respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false); } 
		 logger.info("exiting", "getAppServer"); 
		 
		 return respEntity;
	
	}
	
	@RequestMapping(method = RequestMethod.GET,value="v2/getAppServer/{store}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getAppServerPlatform(@PathVariable("store") String storeNumber)  {
		ResponseEntity<ResponseDTO> respEntity = null; 
		ResponseDTO responseDTO = new ResponseDTO(); 
		logger.debug("entering getAppServer ", ""); 
		try { 
		if(null != storeNumber && storeNumber.length() < 5){
			storeNumber = StringUtils.leftPad(storeNumber, 5, '0'); 
		}
		
		responseDTO.setResponseBody(webServicesProcessorImpl.getAppServerPlatform(storeNumber));
		 respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		 } catch(Exception e) { 
		 logger.error("getAppServer", e); 
		 respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false); } 
		 logger.info("exiting", "getAppServer"); 
		 
		 return respEntity;
	
	}
	
	
	@RequestMapping(method = RequestMethod.POST,value="v1/healthCheck" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> gethealthCheck(@RequestBody RequestDTO requestDTO) throws DJException {
		ResponseEntity<ResponseDTO> respEntity = null; 
		ResponseDTO responseDTO = new ResponseDTO(); 
		logger.debug("entering getAppServer ", ""); 
		try { 
		
		responseDTO.setResponseBody(webServicesProcessorImpl.gethealthCheck(requestDTO));
		 respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		 } catch(Exception e) {
		 respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false); 
		 }
		 
		 return respEntity;
	
	}
	
	@RequestMapping(method = RequestMethod.GET,value="v1/getAppMetaCache" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getAppMetaCache() throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering getAppMetaCache ", "");
		try {
			responseDTO.setResponseBody(webServicesProcessorImpl.getAppMetaCache());
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("getAppMetaCache",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		logger.info("exiting" ,"getAppMetaCache");
		return respEntity;
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value="v1/refreshAppMetaCache" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> refreshAppMetaCache() throws DJException{
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		logger.debug("entering refreshAppMetaCache ", "");
		try {
			responseDTO.setResponseBody(webServicesProcessorImpl.refreshAppMetaCache());
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}catch (Exception e) {
			logger.error("refreshAppMetaCache",e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}

		logger.info("exiting" ,"refreshAppMetaCache");
		return respEntity;
		
	}
	
	@RequestMapping(method = RequestMethod.POST,value="v1/sendManualResponse/{env}/{store}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> sendManualResponse(@PathVariable("store") String storeNum,@PathVariable("env") String env,@RequestBody String inquiry) throws DJException {
		 
		logger.error("sendManualResponse", "");
		logger.error("recieved request body inquiry = "+inquiry,"");
		String[] salescheckList = inquiry.split(",");
		
		additonalServicesProcessorImpl.sendFinalResponseManual(salescheckList, storeNum);
		return null; 
		
	
	}
	
	
}
