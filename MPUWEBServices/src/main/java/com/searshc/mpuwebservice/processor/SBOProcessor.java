package com.searshc.mpuwebservice.processor;

import java.util.List;

import com.searshc.targetinteraction.SBOServiceResponse;

public interface SBOProcessor {
	
	/**
	 * @description returns SBOServiceResponse for SBO Service.
	 * @param divItemSku
	 * @param storeNumber
	 * @param itemIdType
	 * @return sboServiceResponse
	 * @throws Exception
	 */
	public List<SBOServiceResponse> getSBOServiceResponse(String divItemSku,String storeNumber,String itemIdType)throws Exception;
	
	
}
