package com.searshc.mpuwebservice.controller;

import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.NotDeliverReportDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.processor.NotDeliverServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;


@Controller()
public class NotDeliverReportController {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(NotDeliverReportController.class);
	
	@Autowired
	@Qualifier("notDeliverServiceProcessorImpl")
	private NotDeliverServiceProcessor notDeliverServiceProcessor;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/fetchNotDeliveryReport/{dateFrom}/{dateTo}/{storeNo}")
	@ResponseBody
	public ResponseEntity<ResponseDTO> fetchNotDeliveryReport(@PathVariable("dateFrom") String dateFrom,
			@PathVariable("dateTo") String dateTo ,@PathVariable("storeNo") String storeNo){
		
		logger.info("Inside", " NotDeliverReportController.fetchRecordsForShopinReport dateFrom: "+dateFrom+" dateTo: "+ dateTo+" storeNo: "+storeNo);
		List<NotDeliverReportDTO> notDeliverReportList = null;	
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			notDeliverReportList = notDeliverServiceProcessor.fetchRecordsForNotDeliverReport(dateFrom,dateTo,storeNo);
			responseDTO.setResponseBody(notDeliverReportList);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}  catch (DJException djEx) {
			logger.error("fetchNotDeliveryReport",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("fetchNotDeliveryReport",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("Exit", " NotDeliverReportController.fetchRecordsForShopinReport dateFrom: "+dateFrom+" dateTo: "+ dateTo+" storeNo: "+storeNo);
		return respEntity;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/insertNotDeliverReasonInDEJ/{notDeliverReason}/{rqdId}/{storeNumber}/{requestedQty}/{deliverQty}")
	@ResponseBody
	public ResponseEntity<ResponseDTO> insertNotDeliverReasonInDEJ(@PathVariable("notDeliverReason") String notDeliverReason,@PathVariable("rqdId") String rqdId ,@PathVariable("storeNumber") String storeNumber,@PathVariable("requestedQty") String requestedQty, @PathVariable("deliverQty") String deliverQty){
		logger.info("Inside NotDeliverReportController.insertNotDeliverReasonInDEJ notDeliverReason "+notDeliverReason+" for rqdId "+rqdId + "","storeNumber "+storeNumber + "requestedQty: "+ requestedQty + "deliverQty: "+ deliverQty);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		Integer response = 0;
		try {
			notDeliverReason = URLDecoder.decode(notDeliverReason);
			response = notDeliverServiceProcessor.insertNotDeliverReasonCodeToDejService(notDeliverReason, rqdId, storeNumber,requestedQty, deliverQty);			
			responseDTO.setResponseBody(response);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("insertNotDeliverReasonInDEJ",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("insertNotDeliverReasonInDEJ",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		logger.info("Inside NotDeliverReportController.insertNotDeliverReasonInDEJ notDeliverReason "+notDeliverReason+" for rqdId "+rqdId + "","storeNumber "+storeNumber);
		return respEntity;
	}
	
}

