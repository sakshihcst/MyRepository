package com.searshc.mpuwebservice.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
import com.searshc.mpuwebservice.bean.LockerDTO;
import com.searshc.mpuwebservice.bean.MPUResponseStatusDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.processor.LockerServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.MPUWebServiceValidator;
import com.searshc.mpuwebservice.util.PropertyUtils;

/**
 * 
 * @author ssingh6
 *
 */
@Controller
public class LockerServiceController {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(LockerServiceController.class);
	
	@Autowired
	@Qualifier("lockerServiceProcessorImpl")
	private LockerServiceProcessor lockerServiceProcessorImpl;
	
	
	/**
	 * To Insert Locker DTO data records in mpu_locker Table.
	 * @param lockerDTO
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/v1/locker/add", produces = "application/json")
	@ResponseBody	
	public ResponseEntity<ResponseDTO> createRequest(@RequestBody LockerDTO lockerDTO) {
		logger.info("Entering LockerServiceController.createRequest",lockerDTO);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		boolean inserted = false;
		try{
			LockerDTO mpuLockerDetails = new LockerDTO();
			
			//Reference ID
			if(lockerDTO.getReferenceId()== null || lockerDTO.getReferenceId().isEmpty()){
				 respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.null.input.refernceId"), null, true);	
				 return respEntity;
			}else{
				mpuLockerDetails.setReferenceId(lockerDTO.getReferenceId());
			}
			
			//Salescheck Number
			if(MPUWebServiceValidator.isSalescheckNumberValid(lockerDTO.getSalescheckNo())){
				mpuLockerDetails.setSalescheckNo(lockerDTO.getSalescheckNo());
		}else{
				return respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.invalid.salescheck"), null, true);				
			}
			
			//Transaction Date
			String transactionDate = lockerDTO.getTransactionDate();
			if(transactionDate==null || transactionDate.isEmpty()){
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.null.input.transactionDate"), null, true);
				return respEntity ;
			}
			else{
				mpuLockerDetails.setTransactionDate(transactionDate);
			}
			
			//Store Number
			if(MPUWebServiceValidator.isStoreNumberValid(lockerDTO.getStoreNo())){
				mpuLockerDetails.setStoreNo(StringUtils.leftPad(lockerDTO.getStoreNo(), 5, "0"));
			}else{
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.invalid.storeNo"), null, true);
				return respEntity;
				}
			
			// Locker Number
			if(MPUWebServiceValidator.isLockerNumberValid(lockerDTO.getLockerNo()))	{
				mpuLockerDetails.setLockerNo(lockerDTO.getLockerNo());
			}else{
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.invalid.lockerNo"), null, true);
				return respEntity;
			  }
			
			// Created By and Updated By
			if(lockerDTO.getCreatedBy()==null || lockerDTO.getCreatedBy().isEmpty() ){
				 respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.null.input.userName"), null, true);
				return respEntity ;
				
			}else{
				mpuLockerDetails.setCreatedBy(lockerDTO.getCreatedBy());
				mpuLockerDetails.setUpdatedBy(lockerDTO.getCreatedBy());
			}
			
			// Inserting lockerDTO data into mpu_locker table 
			inserted=lockerServiceProcessorImpl.addLocker(mpuLockerDetails);
			
			if(inserted){
				logger.info("createRequest Inserted lockerDTO record in mpu_locker table successfully. ",lockerDTO);
			}	
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);
		} catch (DJException djEx) {
			logger.error("LockerServiceController.createRequest",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("LockerServiceController.createRequest",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"LockerServiceController.createRequest");
		return respEntity;
	}
	
	
	/**
	 * This Service is used to update pinNumber and status of locker
	 * @param mpuLockerVOList
	 * @return
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/v1/locker/updateLockerInfo")
	@ResponseBody	
	public MPUResponseStatusDTO updateLockerInfo(@RequestBody List <Map> mpuLockerVOList) {
		logger.info("Entering LockerServiceController.updateLockerInfo mpuLockerVOList:",mpuLockerVOList);
		MPUResponseStatusDTO mpuResponseStatus = new MPUResponseStatusDTO();		
		try {
			for (Iterator iterator = mpuLockerVOList.iterator(); iterator.hasNext();) {
				Map mpuLockerMap = (Map) iterator.next();
				validateLockerInput(mpuLockerMap);
				if((mpuLockerMap.get("pinNo") == null ) || "".equals(mpuLockerMap.get("pinNo"))){
					throw new Exception("pin no can not be null.");
				}			
				LockerDTO lockerDTO = new LockerDTO();
				lockerDTO.setSalescheckNo((String)mpuLockerMap.get("salescheckNo"));
				lockerDTO.setLockerNo(mpuLockerMap.get("lockerNo").toString());
				lockerDTO.setPinNo((String)mpuLockerMap.get("pinNo"));
				lockerDTO.setStoreNo((String)mpuLockerMap.get("storeNo"));
				lockerDTO.setStatus((String)mpuLockerMap.get("status"));				
				lockerServiceProcessorImpl.updateLocker(lockerDTO);
			}
			mpuResponseStatus.setResposeneCode(200);
			mpuResponseStatus.setStatus("success");
			 logger.info("exiting" ,"LockerServiceController.updateLockerInfo");
			return mpuResponseStatus;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			mpuResponseStatus.setResposeneCode(500);
			mpuResponseStatus.setStatus("failed");
			mpuResponseStatus.setErrorMessage(e.getMessage());
			if (e instanceof DJException) {
				DJException djEx = (DJException) e;
				mpuResponseStatus.setErrorMessage( null == djEx.getMessage() ? djEx.getDevloperMessage() : djEx.getMessage());
			} else {
				mpuResponseStatus.setErrorMessage(e.toString());
			}
			 logger.info("exiting" ,"LockerServiceController.updateLockerInfo");
			return mpuResponseStatus;
		}
	}
	
	
	@RequestMapping(method=RequestMethod.PUT, value="/v1/locker/updateLockerOrderStatus")
	@ResponseBody	
	public ResponseEntity<ResponseDTO> updateLockerOrderStatus(@RequestBody LockerDTO lockerDTO) {
		logger.info("Entering LockerServiceController.updateLockerOrderStatus lockerDTO:",lockerDTO);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			int noOfRows=lockerServiceProcessorImpl.updateLockerOrderStatus(lockerDTO);
			if(noOfRows==0){
				responseDTO.setResponseCode(MpuWebConstants.UPDATE_FAIL_DESC_CODE);
				responseDTO.setResponseDesc(MpuWebConstants.UPDATE_FAIL_DESC);
			}else{
				responseDTO.setResponseCode(MpuWebConstants.UPDATE_DESC_CODE);
				responseDTO.setResponseDesc(MpuWebConstants.UPDATE_DESC);
			}
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
		} catch (DJException djEx) {
			logger.error("LockerServiceController.updateLockerOrderStatus",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("LockerServiceController.updateLockerOrderStatus",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"LockerServiceController.updateLockerOrderStatus");
		 return respEntity;
	}
	
	
	/**
	 * This Service is used to retrieve lockerRecord from mpu_locker table on the basis of store_no,salescheck_no and transaction_date
	 * @param lockerDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/v1/locker/getLockerInfo/{salescheckNum}/{strNum}/{transactionDate}", produces = "application/json")
	public @ResponseBody	
	ResponseEntity<ResponseDTO> getLockerInfo(@PathVariable("salescheckNum") String salescheckNum,			
			@PathVariable("strNum") String strNum,
			@PathVariable("transactionDate") String transactionDate) {		
		logger.info("Entering LockerServiceController.getLockerInfo	salescheckNum/strNum/transactionDate: " ,""+salescheckNum+"/"+strNum+"/"+transactionDate);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try{
			LockerDTO lockerDTOValidated = new LockerDTO();
			
			if(MPUWebServiceValidator.isSalescheckNumberValid(salescheckNum)){
				lockerDTOValidated.setSalescheckNo(salescheckNum);
			}else{
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.invalid.salescheck"), null, true);	
				return respEntity ;
			}
			
			if(MPUWebServiceValidator.isStoreNumberValid(strNum)){
				lockerDTOValidated.setStoreNo(strNum);
			}else{
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.invalid.storeNo"), null, true);	
				return respEntity ;
			}
			
			if(transactionDate == null || transactionDate.isEmpty()){
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.null.input.transactionDate"), null, true);			
				return respEntity ;
			}else{
				lockerDTOValidated.setTransactionDate(transactionDate);
			}
							
			LockerDTO lockerDTOFound =  lockerServiceProcessorImpl.getPinNumberFromSalescheck(lockerDTOValidated.getSalescheckNo(),lockerDTOValidated.getStoreNo(),lockerDTOValidated.getTransactionDate());
			
			if(lockerDTOFound == null){
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, "404", PropertyUtils.getProperty("mpu.invalid.data.not.found"), null, true);				    
			}else{				
				responseDTO.setResponseBody(lockerDTOFound);
				respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			}			
		} catch (DJException djEx) {
			logger.error("LockerServiceController.getLockerInfo",djEx);
			responseDTO.setResponseBody(djEx);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("LockerServiceController.getLockerInfo",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"LockerServiceController.getLockerInfo");
		return respEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/locker/getLockerReport/{fromDate}/{toDate}/{storeNumber}", produces = "application/json")
	public @ResponseBody	
	ResponseEntity<ResponseDTO> getLockerReport(
			@PathVariable("fromDate") String reportFromDate,
			@PathVariable("toDate") String reportToDate,
			@PathVariable("storeNumber") String storeNumber) {
		ResponseEntity<ResponseDTO> responseEntity=null;
		ResponseDTO responseDTO=new ResponseDTO();
		try{
			responseDTO.setResponseBody(lockerServiceProcessorImpl.getLockerReport(reportFromDate,reportToDate,storeNumber));
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (DJException djEx) {
			logger.error("getLockerReport",djEx);
			responseDTO.setResponseBody(djEx);
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		} catch (Exception e) {
			logger.error("getLockerReport",e);
			responseDTO.setResponseBody(MPUWebServiceUtil.getDJExceptionFromException(e));
			responseEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		
		return responseEntity;
	}
	
	/**
	 * This method is used to validate LockerInputs
	 * @param mpuLockerMap
	 * @return
	 * @throws DJException
	 */
	private boolean validateLockerInput(Map mpuLockerMap) throws DJException{
		logger.info("Entering LockerServiceController.validateLockerInput	mpuLockerMap:",mpuLockerMap);
		if(mpuLockerMap != null){
			if((mpuLockerMap.get("storeNo") == null ) || "".equals(mpuLockerMap.get("storeNo"))){
				throw new DJException("store No can not be null.");
			}
			if((mpuLockerMap.get("salescheckNo") == null ) || "".equals(mpuLockerMap.get("salescheckNo"))){
				throw new DJException("salescheck No can not be null.");
			}
			if(mpuLockerMap.get("lockerNo") != null ){
				if((Integer)mpuLockerMap.get("lockerNo") < 1 ||((Integer)mpuLockerMap.get("lockerNo") > 23)){
					throw new DJException("Invalid locker No : "+ mpuLockerMap.get("lockerNo"));
				}	
			}else{
				throw new DJException("locker No can not be null.");
			}
			if((mpuLockerMap.get("status") == null ) || "".equals(mpuLockerMap.get("status"))){
				throw new DJException("status can not be null.");
			}
		}
		 logger.info("exiting" ,"LockerServiceController.validateLockerInput");
		return true;		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/isOrderKeptInLocker/{reqId}{storeNum}" ,produces = "application/json")
	@ResponseBody
	public boolean isOrderKeptInLocker(@PathVariable("reqId") String reqId,@PathVariable("strNum") String strNum){
		 logger.info("entering" ,"LockerServiceController.isOrderKeptInLocker");
		try {
			if(reqId != null){
				boolean isOrderKept = lockerServiceProcessorImpl.isOrderKeptInLocker(Integer.parseInt(reqId), strNum);
				return isOrderKept;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	} 
}
