package com.searshc.mpuwebservice.dao;

import com.searshc.targetinteraction.SBOServiceResponse;

public interface SBOServiceDAO {

	
	public SBOServiceResponse getSBOServiceResponse(String divItemSku,String storeNumber,String itemIdType)throws Exception;
	
}
