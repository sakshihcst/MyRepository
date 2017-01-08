package com.searshc.mpuwebservice.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sears.dj.common.print.vo.TICouponKioskPrintVO;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.processor.TIServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceTIUtil;
import com.searshc.targetinteraction.OrderConfirmResponseDTO;
import com.searshc.targetinteraction.RequestDTO;
import com.searshc.targetinteraction.ResponseDTO;
import com.searshc.targetinteraction.SBOServiceResponse;
import com.searshc.targetinteraction.TIRequestDTO;

/**
 * @author 677829
 * @description contains method for TI Service.
 */

@Controller
public class TIController {
	
	@Autowired
	@Qualifier("TIServiceProcessorImpl")
	private TIServiceProcessor tiServiceProcessorImpl;		

	@Autowired
	private MPUWebServiceProcessor webServiceProcessor;		

	private static final Logger logger=Logger.getLogger(TIController.class);	
	
	/**
	 * @description returns JSON response for TI Service
	 * @param  RequestDTO 
	 * @return JSON response with status code and description
	 */	
	@RequestMapping(method=RequestMethod.POST, value="/v1/TI/getTIServiceResponse/{sywrNumberString}/{storeNumber}/{paymentType}/{quantity}/{orderId}/{storeFormat}/{emailId}/{fmChannel}",produces = "application/json",consumes="application/json")
	@ResponseBody	
	ResponseEntity<ResponseDTO> getTIServiceResponse(@RequestBody RequestDTO requestDTO,@PathVariable("sywrNumber") String sywrNumber,
			@PathVariable("storeNumber") String storeNumber,
			@PathVariable("paymentType") String paymentType,
			@PathVariable("quantity") String quantity,
			@PathVariable("orderId") String orderId,
			@PathVariable("storeFormat") String storeFormat,
			@PathVariable("orderTotal") String orderTotal,
			@PathVariable("emailId") String emailId,
			@PathVariable("fmChannel") String fmChannel){		
		logger.info("Entering TIController.getTIResponse sywrNumber/storeNumber/paymentType/quantity/orderId/storeFormat/emailId: " 
				+sywrNumber+"/"+storeNumber+"/"+paymentType+"/"+quantity+"/"+orderId+"/"+storeFormat+"/"+emailId);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();				
		try {
			List<SBOServiceResponse>serviceResponseList =  requestDTO.getSboServiceResponseList();			
			if(serviceResponseList == null || serviceResponseList.size() ==0){
				respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, "404", com.searshc.mpuwebservice.util.PropertyUtils.getProperty("mpu.invalid.data.not.found"), null, true);
			}else{
				OrderConfirmResponseDTO orderConfirmResponseDTO = tiServiceProcessorImpl.getTIServiceResposne(serviceResponseList,sywrNumber,storeNumber,paymentType,quantity,orderId,storeFormat,emailId,fmChannel);
				if(orderConfirmResponseDTO == null){
					respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, "404", com.searshc.mpuwebservice.util.PropertyUtils.getProperty("mpu.invalid.data.not.found"), null, true);
				}else{
					responseDTO.setResponseBody(orderConfirmResponseDTO);
					respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, true);
				}
			}
		} catch (Exception e) {			
			logger.error("getTIServiceResponse",e);			
			respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, false);
		}		
		logger.info("exiting TIController.getTIServiceResponse");
		return respEntity;		
	}	
	

	/**
	 * @description returns JSON response for saveTIOffers as OrderConfirmResponseDTO.
	 * @param  sywrNumber 
	 * @param  channel 
	 * @param  client 
	 * @param  offerUuids 
	 * @return JSON response with status code and description
	 */	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/TI/saveTIOffers/{sywrNumber}/{channel}/{client}/{offerUuids}", produces = "application/json" ,consumes = "application/x-www-form-urlencoded")
	public @ResponseBody	
	ResponseEntity<ResponseDTO> saveTIOffers(@PathVariable("sywrNumber") String sywrNumber,			
			@PathVariable("channel") String channel,
			@PathVariable("client") String client,
			@PathVariable("offerUuids") String offerUuids){
		
		logger.info("Entering TIController.saveTIOffers sywrNumber/channel/client/offerUuids: " +sywrNumber+"/"+channel+"/"+client+"/"+offerUuids);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();			
		try {
			OrderConfirmResponseDTO orderConfirmResponseDTO =tiServiceProcessorImpl.saveTIOffers(sywrNumber,channel,client,offerUuids);			
			if(orderConfirmResponseDTO == null){
				respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, "404", com.searshc.mpuwebservice.util.PropertyUtils.getProperty("mpu.invalid.data.not.found"), null, true);				    
			}else{				
				responseDTO.setResponseBody(orderConfirmResponseDTO);
				respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, true);
			}				
		} catch (Exception e) {
			logger.error("getResponseForSBO",e);			
			respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("exiting TIController.saveTIOffers");
		return respEntity;		
	}
	
	
	/**
	 * @description returns JSON response for TI Service
	 * @param  RequestDTO 
	 * @return JSON response with status code and description
	 */	
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/getTIResponse", produces = "application/json" ,consumes = "application/json")
	public @ResponseBody	
	ResponseEntity<ResponseDTO> getTIResponse(@RequestBody TIRequestDTO tiRequestDTO){
		logger.info("Entering TIController.getTIResponse divItemSku/storeNumber/itemIdType/sywrNumber/paymentType/quantity/orderId/storeFormat/orderTotal/email/fmChannel:"
				+tiRequestDTO.getDivItemSku()+"/"+tiRequestDTO.getStoreNumber()+"/"+tiRequestDTO.getItemIdType()+"/"+tiRequestDTO.getSywrNumber()+"/"+tiRequestDTO.getPaymentType()+"/"+tiRequestDTO.getQuantity()+"/"+tiRequestDTO.getOrderId()+"/"+tiRequestDTO.getStoreFormat()+"/"+tiRequestDTO.getOrderTotal()+"/"+tiRequestDTO.getEmailId()+"/"+tiRequestDTO.getFmChannel());
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();		
		try {
			OrderConfirmResponseDTO orderConfirmResponseDTO = tiServiceProcessorImpl.getTIResponse(tiRequestDTO.getDivItemSku(),
			tiRequestDTO.getStoreNumber(),tiRequestDTO.getItemIdType(),tiRequestDTO.getSywrNumber(),tiRequestDTO.getPaymentType(),tiRequestDTO.getQuantity(),tiRequestDTO.getOrderId(),tiRequestDTO.getOrderTotal(),tiRequestDTO.getEmailId(),tiRequestDTO.getFmChannel());
			if(orderConfirmResponseDTO == null){
				respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, "404", com.searshc.mpuwebservice.util.PropertyUtils.getProperty("mpu.invalid.data.not.found"), null, true);
			}else{
				responseDTO.setResponseBody(orderConfirmResponseDTO);
				respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, true);
			}
		} catch (Exception e) {
			logger.error("getTIResponse",e);			
			respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, false);
		}	
		logger.info("exiting TIController.getTIResponse");
		return respEntity;
		
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/testTIService")
	public @ResponseBody	
	ResponseEntity<ResponseDTO> testTIService(){
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, "200", com.searshc.mpuwebservice.util.PropertyUtils.getProperty("mpu.webservice.success"), null, true);
		return respEntity;
		
	}
	
	/**
	 * Print TI Coupon
	 * 
	 * @param store
	 * @param kiosk
	 * @param TICouponKioskPrintVO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/printTICoupon/{store}/{kioskName}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> printTICoupon(@RequestBody TICouponKioskPrintVO tiCouponKioskPrintVO,
			@PathVariable("store") String store,
			@PathVariable("kioskName") String kioskName) {
		logger.info("printTICoupon:: Entering->\n tiCouponKioskPrintVO / store / kioskName " + tiCouponKioskPrintVO + " / " + store + " / " + kioskName );
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try{
			webServiceProcessor.printTICoupon(tiCouponKioskPrintVO,store, kioskName);
			respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, true);
		} catch (Exception e) {
			logger.error("printTICoupon::" + e.getStackTrace());
			respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, false);
		}
		return respEntity;
	}

	/**
	 * Get TI offers
	 * 
	 * @param sywrId
	 * @param readyworkIds
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/getTIOffers/{storeNo}/{readyworkIds}/{sywrId}/{kioskName}/{storeFormat}", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> getTIOffers(@PathVariable("storeNo") String storeNo,
			@PathVariable("readyworkIds") String readyworkIds,
			@PathVariable("sywrId") String sywrId,
			@PathVariable("kioskName") String kioskName,
			@PathVariable("storeFormat") String storeFormat) {
		logger.info("getTIOffers:: Entering->\n sywrId / readyworkIds / storeNo " + sywrId + " / " + readyworkIds + " / " + storeNo );
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();		
		try {
			OrderConfirmResponseDTO orderConfirmResponseDTO = webServiceProcessor.getTIOffers(storeNo, readyworkIds, sywrId, kioskName, storeFormat);
			if(orderConfirmResponseDTO == null){
				respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, "404", com.searshc.mpuwebservice.util.PropertyUtils.getProperty("mpu.invalid.data.not.found"), null, true);
			}else{
				responseDTO.setResponseBody(orderConfirmResponseDTO);
				respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, true);
			}
		} catch (Exception e) {
			logger.error("getTIOffers",e);			
			respEntity = MPUWebServiceTIUtil.getResponseEntity(responseDTO, false);
		}	
		logger.info("exiting TIController.getTIOffers");
		return respEntity;
	}
	
}