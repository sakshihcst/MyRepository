package com.searshc.mpuwebservice.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.searshc.mpuwebservice.dao.TIServiceDAO;
import com.searshc.targetinteraction.ItemOrderConfirmDTO;
import com.searshc.targetinteraction.OrderConfirmRequestDTO;
import com.searshc.targetinteraction.OrderConfirmResponseDTO;


/**
 * @description This class contains implementation for TIServiceDAO methods for TIService.
 * @author gasolek
 *
 */
@Repository("TIServiceDAOImpl")
public class TIServiceDAOImpl implements TIServiceDAO {
	
	private static final Logger logger=Logger.getLogger(TIServiceDAOImpl.class);
	
	@Autowired
	RestTemplate restTemplate;	

	public OrderConfirmResponseDTO getTIServiceResposne(OrderConfirmRequestDTO orderConfirmRequestDTO,String sywr)	throws Exception {
		logger.info("Inside TIServiceDAOImpl.getTIServiceResposne: "+orderConfirmRequestDTO);
		ResponseEntity<OrderConfirmResponseDTO> response = null;
		OrderConfirmResponseDTO orderConfirmResponseDTO= null;
		String serverURL = com.searshc.mpuwebservice.util.PropertyUtils.getProperty("TIServiceURL");		
		logger.info("Server URL for TI Service :"+serverURL);						
		HttpEntity<OrderConfirmRequestDTO> requestEntity = new HttpEntity<OrderConfirmRequestDTO>(orderConfirmRequestDTO);
		logger.info("Calling TI Service");
		((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(1000*20);
		response = restTemplate.postForEntity(serverURL, requestEntity, OrderConfirmResponseDTO.class);	
		orderConfirmResponseDTO = response.getBody();		
		logger.info("Exiting successfully from TI Service"+orderConfirmResponseDTO.toString());
		return orderConfirmResponseDTO;
		
	}

	public OrderConfirmResponseDTO saveTIOffers(String sywrNumber,String channel, String client, String offerUuids) throws Exception {
		logger.info("Inside TIServiceDAOImpl.saveTIOffers");		
		ResponseEntity<OrderConfirmResponseDTO> response = null;		
		String serverURL = com.searshc.mpuwebservice.util.PropertyUtils.getProperty("saveTIOffersURL")+"?sywrNumber="+sywrNumber+"&channel="+channel+"&client="+client+"&offerUuids="+offerUuids;
		logger.info("ServerURL for saveTIOffers Service :"+serverURL);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		response = restTemplate.exchange(serverURL, HttpMethod.POST, entity, OrderConfirmResponseDTO.class);
		if(response !=null){
			logger.info("Exit from TIServiceDAOImpl.saveTIOffers with successful response"+response.getBody().toString());
			return response.getBody();
		}else{
			logger.info("Exit from TIServiceDAOImpl.saveTIOffers with successful response null");
			return null;
		}		
	}
	
	private OrderConfirmResponseDTO dummyResponseForTI(){
		ResponseEntity<OrderConfirmResponseDTO> response = null;	
		String serverURL = "http://sywr-qa1.qa.ch3.s.com/offersapi/r/v1/offers?client=checkout&channel=sears.com&sywrNumber=7081040617097632";	
		OrderConfirmRequestDTO orderConfirmRequestDTO = new OrderConfirmRequestDTO();		
		orderConfirmRequestDTO.setPaymentType("SY,VS"); //get from UI ******
		orderConfirmRequestDTO.setMaxMemberOfferCount("");
		orderConfirmRequestDTO.setChannel("******");
		orderConfirmRequestDTO.setMaxOfferCount("2"); 
		orderConfirmRequestDTO.setOrderId("645043204"); //get from UI ******
		orderConfirmRequestDTO.setOrderTotal(14.07); // need confirmation on this
		orderConfirmRequestDTO.setEmail("jenjenpk00@yahoo.com");
		orderConfirmRequestDTO.setClient("MPU");
		orderConfirmRequestDTO.setUserId("7081040617097632");
		List<ItemOrderConfirmDTO> itemOrderConfirmDTOList = new ArrayList<ItemOrderConfirmDTO>();
		ItemOrderConfirmDTO itemOrderConfirmDTO = new ItemOrderConfirmDTO();				
		itemOrderConfirmDTO.setCategory1("054");
		itemOrderConfirmDTO.setCategory2("40");
		itemOrderConfirmDTO.setCategory3("10");
		itemOrderConfirmDTO.setPrice("14.99000");
		itemOrderConfirmDTO.setQuantity(1);
		itemOrderConfirmDTO.setPartNumber("05473329566");
		itemOrderConfirmDTO.setFmChannel("SPU");  // Need to confirm
		itemOrderConfirmDTO.setFmStoreId("0001088");
		itemOrderConfirmDTOList.add(itemOrderConfirmDTO);
		orderConfirmRequestDTO.setItems(itemOrderConfirmDTOList);
		
		HttpEntity<OrderConfirmRequestDTO> requestEntity = new HttpEntity<OrderConfirmRequestDTO>(orderConfirmRequestDTO);
		logger.info("Calling TI Service");
		response = restTemplate.postForEntity(serverURL, requestEntity, OrderConfirmResponseDTO.class);	
		if(response.getBody() !=null){
			return response.getBody();
		}else{
			return null;
		}		
	}	
}