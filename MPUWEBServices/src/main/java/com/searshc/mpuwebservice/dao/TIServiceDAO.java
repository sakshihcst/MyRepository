package com.searshc.mpuwebservice.dao;

import com.searshc.targetinteraction.OrderConfirmRequestDTO;
import com.searshc.targetinteraction.OrderConfirmResponseDTO;

public interface TIServiceDAO {	
	
	public OrderConfirmResponseDTO getTIServiceResposne(OrderConfirmRequestDTO orderConfirmRequestDTO,String sywr)throws Exception;	
	
	public OrderConfirmResponseDTO saveTIOffers(String sywrNumber,String channel,String client,String offerUuids) throws Exception;	
	
}
