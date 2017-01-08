package com.searshc.mpuwebservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.ws.DJController;
import com.searshc.mpuwebservice.bean.MpuPickUpReportResposne;
import com.searshc.mpuwebservice.bean.ProcessReturnIn5Request;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.bean.ShopInReportDTO;
import com.searshc.mpuwebservice.bean.ShopinRequestDTO;
import com.searshc.mpuwebservice.processor.ShopInServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;

@DJController
public class ShopInController {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(ShopInController.class);
	
	@Autowired
	private ShopInServiceProcessor shopInServiceProcessor; 
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/insertRecordForShopInReport")
	@ResponseBody	
	public MpuPickUpReportResposne insertRecordForShopInReport(@RequestBody ShopinRequestDTO request){
		logger.info("Inside", " ShopInController.insertRecordForShopInReport: "+request);
		MpuPickUpReportResposne mpuPickUpReportResposne = null;
		try {
			mpuPickUpReportResposne = shopInServiceProcessor.insertRecordForShopInReport(request);
		} catch (Exception e) {
			logger.error("insertRecordForShopInReport",e);			
		}
		logger.info("Exiting", " ShopInController.insertRecordForShopInReport.");
		return mpuPickUpReportResposne;
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/updateShopInReport/{storeNo}/{salesCheckNumber}/{pickedUpQty}/{status}/{customerName}/{associateName}/{pickupEndTime}")
	@ResponseBody
	public MpuPickUpReportResposne updateShopInReport(@PathVariable("storeNo") String storeNo, @PathVariable("salesCheckNumber") String salesCheckNumber,
			@PathVariable("pickedUpQty") String pickedUpQty ,@PathVariable("status") String status,@PathVariable("customerName") String customerName,
			@PathVariable("associateName") String associateName, @PathVariable("pickupEndTime") String pickupEndTime){
			
		logger.info("Inside", " ShopInController.updateShopInReport SCN: "+salesCheckNumber+" pickedUpQty: "+ pickedUpQty+" customerName: "+customerName+" associateName: "+associateName+" pickupEndTime:"+ pickupEndTime);
		MpuPickUpReportResposne mpuPickUpReportResposne = null;
		try {
			mpuPickUpReportResposne = shopInServiceProcessor.updateShopInReport(storeNo, salesCheckNumber, pickedUpQty, status, customerName, associateName, pickupEndTime);
		} catch (Exception e) {	
			logger.error("updateShopInReport",e);	
		}		
		logger.info("Exiting", " ShopInController.updateShopInReport SCN: "+salesCheckNumber+" pickedUpQty: "+ pickedUpQty+" customerName: "+customerName+" associateName: "+associateName+" pickupEndTime:"+ pickupEndTime);
		return mpuPickUpReportResposne;		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/fetchRecordsForShopinReport/{dateFrom}/{dateTo}/{storeNo}/{region}/{district}")
	@ResponseBody
	public ResponseEntity<ResponseDTO> fetchRecordsForShopinReport(@PathVariable("dateFrom") String dateFrom,
			@PathVariable("dateTo") String dateTo ,@PathVariable("storeNo") String storeNo,@PathVariable("region") String region,
			@PathVariable("district") String district){
		
		logger.info("Inside", " ShopInController.fetchRecordsForShopinReport dateFrom: "+dateFrom+" dateTo: "+ dateTo+" storeNo: "+storeNo+" region: "+region+" district:"+ district);
		List<ShopInReportDTO> shopinReportList = null;	
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			shopinReportList = shopInServiceProcessor.fetchRecordsForShopinReport(dateFrom,dateTo,storeNo,region,district);
			responseDTO.setResponseBody(shopinReportList);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("fetchRecordsForShopinReport",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("fetchRecordsForShopinReport",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("Exit", " ShopInController.fetchRecordsForShopinReport dateFrom: "+dateFrom+" dateTo: "+ dateTo+" storeNo: "+storeNo+" region: "+region+" district:"+ district);
		return respEntity;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/getResponseForRI5Shopin" , consumes = "text/xml" ,produces = "application/xml")
	@ResponseBody
	public com.searshc.mpuwebservice.bean.ProcessReturnIn5Response getResponseForRI5Shopin(@RequestBody ProcessReturnIn5Request processReturnIn5Request){		
		logger.info("Inside ShopInController.getResponseForRI5Shopin : "+processReturnIn5Request,"");
		com.searshc.mpuwebservice.bean.ProcessReturnIn5Response ri5Response = null;
		try {
			ri5Response = shopInServiceProcessor.getResponseForRI5Shopin(processReturnIn5Request);
		} catch (Exception e) {
			logger.info("Exception occurred inside getResponseForRI5Shopin: ", e);			
		}
		logger.info("Inside ShopInController.getResponseForRI5Shopin","");
		return ri5Response;		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pushNotificationToShopInApplication/{storeNumber}/{rqtId}/{salesCheck}/{orderStatus}/{searsSalesId}/{associateName}/{timeTakenForPickup}")
	@ResponseStatus(value = HttpStatus.OK)
	public void pushNotificationToShopInApplication(@PathVariable("storeNumber") String storeNumber,@PathVariable("rqtId") String rqtId,@PathVariable("salesCheck")String salesCheck,@PathVariable("orderStatus")String orderStatus,
				@PathVariable("searsSalesId")String searsSalesId,@PathVariable("associateName")String associateName,
				@PathVariable("timeTakenForPickup") String timeTakenForPickup) {
		
		logger.info("Inside shopinServiceProcessorImpl rqtId:" + rqtId +" salesCheck:" +salesCheck +
				" orderStatus:"+ orderStatus+" searsSalesId :" + searsSalesId +" associateName : " + associateName + " timeTakenForPickup: "+timeTakenForPickup+
				" storeNumber: "+ storeNumber, "");
		try {
			shopInServiceProcessor.pushNotificationToShopIn(rqtId, salesCheck, orderStatus, searsSalesId, associateName, timeTakenForPickup, storeNumber);
		} catch (DJException e) {
			logger.error("Exception in pushNotificationToShopInApplication :: ", e);			
		}		
	}	
}
