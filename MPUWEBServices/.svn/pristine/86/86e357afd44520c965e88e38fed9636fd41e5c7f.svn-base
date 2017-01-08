package com.searshc.mpuwebservice.processor;

import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.targetinteraction.OrderConfirmResponseDTO;
import com.searshc.targetinteraction.SBOServiceResponse;

public interface TIServiceProcessor {
	
	/**
	 * @description returns SBOServiceResponse for SBO Service.
	 * @param sboServiceResponses	
	 * @return OrderConfirmResponseDTO
	 * @throws DJException
	 */	
	public OrderConfirmResponseDTO getTIServiceResposne(List<SBOServiceResponse> sboServiceResponses,String sywrNumber,String storeNumber,String paymentType,String quantity,String orderId,String orderTotal,String emailId,String fmChannel)throws Exception;	
	
	/**
	 * @description returns Response for saveTIOffers as OrderConfirmResponseDTO.
	 * @param sywrNumber	
	 * @param channel
	 * @param client
	 * @param offerUuids
	 * @return OrderConfirmResponseDTO
	 * @throws DJException
	 */		
	public OrderConfirmResponseDTO saveTIOffers(String sywrNumber,String channel,String client,String offerUuids) throws Exception;
	
	
	
	public OrderConfirmResponseDTO getTIResponse(String divItemSku,String storeNumber,String itemIdType,String sywrNumber,String paymentType,String quantity,String orderId,String orderTotal,String emailId,String fmChannel)throws Exception;
	
	
	
}
