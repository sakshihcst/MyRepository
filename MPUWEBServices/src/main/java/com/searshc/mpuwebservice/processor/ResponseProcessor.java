package com.searshc.mpuwebservice.processor;

import java.util.List;
import java.util.Map;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.ItemDTO;

public interface ResponseProcessor {
	public void sendFinalResponse(String storeNum,String rqtId,String requestNumber,String actionFlag) 
			throws DJException;
	public void removeItemsFromCache(List<String> rqdList,String storeNum,String queueType);
	
	public void sendNoResponseToOBUForHG(List<Map<String, ItemDTO>> expiredHGItems)  throws DJException;
}
