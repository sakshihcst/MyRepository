package com.searshc.mpuwebservice.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.ItemDTO;

public interface MODNotificationProcessor {
	
	public void sendMODNotification(ItemDTO itemDTO,int taskType) throws DJException;
	
	 void updateCsmTask(int taskType,ItemDTO itemDTO) throws DJException;
	 public void notifyMODForEligibleList(String store,List<ItemDTO> itemdtoList) throws DJException;
	 
	 
	 public void postProcessing(List<String> rqtList,List<String> rqdList,HashMap<String,String> mapping,String strNum,HashMap<String,String> requestTypeMap,String queueType,
				List<Map<String, ItemDTO>> expiredHGItems ,List<ItemDTO> modlist) throws DJException ;

}
