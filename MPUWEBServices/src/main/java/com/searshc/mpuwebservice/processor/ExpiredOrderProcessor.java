package com.searshc.mpuwebservice.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.ItemDTO;

public interface ExpiredOrderProcessor {
	public void expireOrder(List<String> rqtList,List<String> rqdList,HashMap<String,String> mapping,String strNum,HashMap<String,String> requestTypeMap,String queueType,
			List<Map<String, ItemDTO>> expiredHGItems)throws DJException;
	
	public   boolean filterItemthread(String store,String queueType);
}
