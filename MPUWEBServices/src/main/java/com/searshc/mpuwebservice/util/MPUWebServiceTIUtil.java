package com.searshc.mpuwebservice.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.targetinteraction.ResponseDTO;


public class MPUWebServiceTIUtil {
	
	/**
	 * This method is used for generating Response Entity.
	 * @param responseDTO
	 * @param state
	 * @return ResponseEntity<ResponseDTO>
	 */
	public static ResponseEntity<ResponseDTO> getResponseEntity(ResponseDTO responseDTO,boolean state){
		ResponseEntity<ResponseDTO> respEntity = null;
		if(state){
			responseDTO.setResponseCode("200");
			responseDTO.setResponseDesc(MpuWebConstants.SUCCESS_200);
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
		}else{
			responseDTO.setResponseCode("500");
			responseDTO.setResponseDesc(MpuWebConstants.ERROR_500);
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respEntity;
	}
	
	
	/**
	 * This method is used for generating Response Entity.
	 * @param responseDTO
	 * @param state
	 * @return ResponseEntity<ResponseDTO>
	 */
	public static ResponseEntity<ResponseDTO> getResponseEntity(ResponseDTO responseDTO,
			String responseCode,String responseDesc,Object responseBody,boolean state){
		ResponseEntity<ResponseDTO> respEntity = null;
		responseDTO.setResponseBody(responseBody);
		responseDTO.setResponseCode(responseCode);
		responseDTO.setResponseDesc(responseDesc);
		if(state){
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
		}else{
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respEntity;
	}
	
	
	
}
