package com.searshc.mpuwebservice.processor.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.util.PropertyUtils;
import com.searshc.mpuwebservice.constant.MpuWebConstants;

@Service
public class MpuWebDlvryStatusServiceImpl implements MpuWebDlvryStatusService {

	@Autowired
	private RestTemplate restTemplate;
	
	Logger logger =Logger.getLogger(MpuWebDlvryStatusService.class.getName());
	

	public void sendMpuInStorePurchaseNotification(String sywrId, String store,
			String salescheck){
			try{
				String postStr = null;
	 			logger.info("enter in sendMpuInStorePurchaseNotification for sywrId :"+sywrId+ " storeId:"+store+" salesCheckNumber :"+salescheck);
	 		postStr = PropertyUtils.getProperty("MPU_STORE_PURCHASE_NOTIFICATION_API_URL");
	 		String xmlString ="{" +	"\"sywrId\": \""+sywrId+"\"," + "\"storeId\": \""+store+"\"," +	"\"salesCheckNumber\": \""+salescheck+"\"" +"}";
	 		HttpHeaders headers = new HttpHeaders();
			headers.setContentType( MediaType.APPLICATION_JSON ); 
	 		HttpEntity request = new HttpEntity(xmlString, headers ); 
			ResponseEntity<String> response = restTemplate.postForEntity(postStr, request, String.class );
			int responseStatusCode = response.getStatusCode().value();
			if (responseStatusCode == 200) {
				logger.info("Response String: " + response.toString());
			} else {
				logger.info("Error in call sywr notification service:"+ responseStatusCode);
			}
			logger.info("Response code: "+response.getStatusCode().value());
	 		
		logger.info("Exit from sendMpuInStorePurchaseNotification");				
			}catch(Exception exception){
				logger.error("in the exception block of sendMpuInStorePurchaseNotification"+exception.getMessage());
			}
			
				}

}
