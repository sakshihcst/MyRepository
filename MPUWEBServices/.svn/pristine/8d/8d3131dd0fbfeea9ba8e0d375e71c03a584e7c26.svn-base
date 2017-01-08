package com.searshc.mpuwebservice.processor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.searshc.mpuwebservice.dao.TIServiceDAO;
import com.searshc.mpuwebservice.processor.SBOProcessor;
import com.searshc.mpuwebservice.processor.TIServiceProcessor;
import com.searshc.targetinteraction.ItemOrderConfirmDTO;
import com.searshc.targetinteraction.OrderConfirmRequestDTO;
import com.searshc.targetinteraction.OrderConfirmResponseDTO;
import com.searshc.targetinteraction.PriceDetailsDTO;
import com.searshc.targetinteraction.SBOServiceResponse;


/**
 * @author gasolek
 * @description This is business layer for TI Service implementation
 */
@Service("TIServiceProcessorImpl")
public class TIServiceProcessorImpl implements TIServiceProcessor {
	
	
	private static final Logger logger=Logger.getLogger(TIServiceProcessorImpl.class);
	
	@Autowired
	@Qualifier("TIServiceDAOImpl")
	private TIServiceDAO TIServiceDAOImpl;
	
	@Autowired
	@Qualifier("SBOProcessorImpl")
	private SBOProcessor sboProcessorImpl;
	

	public OrderConfirmResponseDTO getTIServiceResposne(List<SBOServiceResponse>sboServiceResponses,String sywrNumber,String storeNumber,String paymentType,String quantity,String orderId,String orderTotal,String emailId,String fmChannel) throws Exception {
		logger.info("Inside TIServiceProcessorImpl.getTIServiceResposne");
		OrderConfirmResponseDTO orderConfirmResponseDTO = null;				
		if(sboServiceResponses.size()>0){
			logger.info("creating request obj for TI Service");
			OrderConfirmRequestDTO orderConfirmRequestDTO = new OrderConfirmRequestDTO();
			List<ItemOrderConfirmDTO> itemOrderConfirmDTOList = new ArrayList<ItemOrderConfirmDTO>();
			SortedSet<Double> priceAmount = new TreeSet<Double>();
			List<Double>orderTotalList = new ArrayList<Double>();
			
			for(SBOServiceResponse sboServiceResponse:sboServiceResponses){
				ItemOrderConfirmDTO itemOrderConfirmDTO = new ItemOrderConfirmDTO();				
				itemOrderConfirmDTO.setCategory1(sboServiceResponse.getItemDetailsList().get(0).getDiv());
				itemOrderConfirmDTO.setCategory2(sboServiceResponse.getItemDetailsList().get(0).getLine());
				itemOrderConfirmDTO.setCategory3(sboServiceResponse.getItemDetailsList().get(0).getSubLine());		
				List<PriceDetailsDTO> priceDetailList = sboServiceResponse.getItemDetailsList().get(0).getPriceDetailsList();
				if(priceDetailList.size()>0){
					for(PriceDetailsDTO priceDetail:priceDetailList){
						priceAmount.add(priceDetail.getAmount());
					}
				}			
				itemOrderConfirmDTO.setPrice(String.valueOf(priceAmount.first()));
				orderTotalList.add(priceAmount.first());
				priceAmount.clear();
				itemOrderConfirmDTO.setQuantity(Integer.parseInt(quantity));				
				itemOrderConfirmDTO.setPartNumber(sboServiceResponse.getItemDetailsList().get(0).getDiv()+sboServiceResponse.getItemDetailsList().get(0).getItem()+sboServiceResponse.getItemDetailsList().get(0).getSku());				
				itemOrderConfirmDTO.setFmChannel(fmChannel);
				itemOrderConfirmDTO.setFmStoreId(storeNumber);
				itemOrderConfirmDTOList.add(itemOrderConfirmDTO);
				
			}
			orderConfirmRequestDTO.setItems(itemOrderConfirmDTOList);	
			orderConfirmRequestDTO.setPaymentType(paymentType);
			orderConfirmRequestDTO.setMaxMemberOfferCount(com.searshc.mpuwebservice.util.PropertyUtils.getProperty("ti.max_member_offer_count.property.value"));		
			orderConfirmRequestDTO.setChannel(com.searshc.mpuwebservice.util.PropertyUtils.getProperty("ti.channel.property.value"));
			orderConfirmRequestDTO.setMaxOfferCount(com.searshc.mpuwebservice.util.PropertyUtils.getProperty("ti.max_offer_count.property.value")); 
			orderConfirmRequestDTO.setOrderId(orderId);		
			orderConfirmRequestDTO.setOrderTotal(calculateOrderTotal(orderTotalList));
			orderConfirmRequestDTO.setClient(com.searshc.mpuwebservice.util.PropertyUtils.getProperty("ti.client.property.value"));			
			 
			if("0".equals(sywrNumber)){
				orderConfirmRequestDTO.setUserId("");
				orderConfirmRequestDTO.setEmail(com.searshc.mpuwebservice.util.PropertyUtils.getProperty("ti.non.sywr.member.email.id"));
			}else{
				orderConfirmRequestDTO.setUserId(sywrNumber);
				orderConfirmRequestDTO.setEmail("");
			}			 
			logger.info("Calling TIServiceDAOImpl.getTIServiceResposne");
			orderConfirmResponseDTO = TIServiceDAOImpl.getTIServiceResposne(orderConfirmRequestDTO,sywrNumber);			
		}
		if(orderConfirmResponseDTO !=null){
			logger.info("Successfully exiting from TIServiceProcessorImpl.getTIServiceResposne:");
			return orderConfirmResponseDTO;
		}
		else{
			logger.info("Successfully exiting from TIServiceProcessorImpl.getTIServiceResposne:NULL");
			return null;
		}
	}
	
	private double calculateOrderTotal(List<Double> orderTotalList){
		double sum = 0;
		for( double orderPriceObj : orderTotalList) {
		    sum += orderPriceObj;
		}
		return sum;		
	}
	
	public OrderConfirmResponseDTO saveTIOffers(String sywrNumber,String channel, String client, String offerUuids)	throws Exception {		
		return TIServiceDAOImpl.saveTIOffers(sywrNumber, channel, client, offerUuids);
	}


	public OrderConfirmResponseDTO getTIResponse(String divItemSku, String storeNumber, String itemIdType,String sywrNumber,String paymentType,String quantity,String orderId,String orderTotal,String emailId,String fmChannel) throws Exception {		
		logger.info("Inside TIServiceProcessorImpl.getTIResponse");
		List<SBOServiceResponse> sboServiceResponse = null;	
		OrderConfirmResponseDTO orderConfirmResponseDTO = null;
		ObjectMapper objmapp = new ObjectMapper();
		List<SBOServiceResponse> sboServiceDTOs= sboProcessorImpl.getSBOServiceResponse(divItemSku, storeNumber, itemIdType);
		sboServiceResponse =objmapp.convertValue(sboServiceDTOs, new TypeReference<List<SBOServiceResponse>>() { });		
		if(sboServiceResponse.size() > 0){
			orderConfirmResponseDTO =getTIServiceResposne(sboServiceResponse,sywrNumber,storeNumber,paymentType,quantity,orderId,orderTotal,emailId,fmChannel);
			return orderConfirmResponseDTO;
		}		
		return null;		
	}	
}
