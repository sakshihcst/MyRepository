package com.searshc.mpuwebservice.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.util.DJUtilities;
import com.sears.dj.common.ws.DJServiceLocator;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebutil.util.CommonUtils;

@Controller
public class RoutingController {
	
	
	
	
	@Value("${INITIALSERVERURL}")
	private String internalServerUrl;
	
	@Value("${WEB_URL}")
	private String webUrl;
	
	
	@PostConstruct
	public void loadServerValue() {
		
		 if(StringUtils.isEmpty(DJServiceLocator.initialHostServer)) {
			 DJServiceLocator.initialHostServer = internalServerUrl;
		}
	}
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(RoutingController.class);
	
	@RequestMapping(method = RequestMethod.POST, value = "/router/v1/requests", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> createRequest(@RequestBody RequestDTO requestDTO) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		
		logger.info("Entering create request router", "");
		
		OrderDTO order=requestDTO.getOrder();
		String storeNumber="";
		if(order!=null){
			
			storeNumber=order.getStoreNumber();
			
			
		}
		
		
		try{
		if(!CommonUtils.isEmpty(storeNumber)){
		
			HttpEntity<RequestDTO> requestEntity = new HttpEntity<RequestDTO>(requestDTO);
			
			String url="/requests";
			
			String finalUrl= DJUtilities.concatString(webUrl,url);
			respEntity=(ResponseEntity<ResponseDTO>)DJServiceLocator.exchange(order.getStoreNumber(), finalUrl, requestEntity, HttpMethod.POST,ResponseDTO.class);
			
		
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);



		}
		}
		
		catch(Exception e){
			
			logger.error("Error in create request router", e);
			
			responseDTO.setResponseBody(e.getMessage());
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,false);
			
			
			
			
		}
		
		
		
	return respEntity;	
		
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST,value="/router/v1/updateVoidrequest/{requestNumber}/{store}" ,produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseDTO> updateVoidRequest(@PathVariable("store") String store ,
			@PathVariable("requestNumber") String requestNumber,
			@RequestBody RequestDTO requestDTO  ) throws DJException{
		logger.info("Entering cancel request router", "");
		String url="/updateVoidrequest"+"/" +requestNumber +"/"+store;
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		
		HttpEntity<RequestDTO> requestEntity = new HttpEntity<RequestDTO>(requestDTO);
		
		try{
			
			String finalUrl= DJUtilities.concatString(webUrl,url);
		
		respEntity=(ResponseEntity<ResponseDTO>)DJServiceLocator.exchange(store, finalUrl, requestEntity, HttpMethod.POST,ResponseDTO.class);
		
		
		respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,true);}
		
		catch(Exception e){
			logger.info("Error in  cancel request router", e);

			responseDTO.setResponseBody(e.getMessage());
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO,false);
			
		
	}
		
		return respEntity;
	}
	
	
	
	
	

}
