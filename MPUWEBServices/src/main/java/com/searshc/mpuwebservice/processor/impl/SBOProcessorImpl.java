package com.searshc.mpuwebservice.processor.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.searshc.mpuwebservice.dao.SBOServiceDAO;
import com.searshc.mpuwebservice.processor.SBOProcessor;
import com.searshc.targetinteraction.SBOServiceResponse;

/**
 * @author gasolek
 * @description This is business layer for SBO Service implementation
 */
@Service("SBOProcessorImpl")
public class SBOProcessorImpl implements SBOProcessor {	
	
	private static final Logger logger=Logger.getLogger(SBOProcessorImpl.class);
	
	@Autowired
	@Qualifier("sboServiceDAOImpl")
	private SBOServiceDAO sboServiceDAOImpl;
	

	public List<SBOServiceResponse> getSBOServiceResponse(String divItemSku, String storeNumber,String itemIdType) throws Exception {
		logger.info("Inside layer SBOProcessorImpl.getSBOServiceResponse");
		List<SBOServiceResponse> sboServiceResponseList = new ArrayList<SBOServiceResponse>();
		String[] divItemSkuArr = divItemSku.split("-");
		for(String divItemSkuElement:divItemSkuArr){
			SBOServiceResponse sboServiceResponse = new SBOServiceResponse();
			logger.info("Calling SBOServiceDAOImpl.getSBOServiceResponse for divItemSku:"+divItemSkuElement +" storeNumber:"+storeNumber+" itemIdType:"+itemIdType);
			sboServiceResponse = sboServiceDAOImpl.getSBOServiceResponse(divItemSkuElement, storeNumber, itemIdType);
			if(sboServiceResponse !=null){
				logger.info("Response got from SBOServiceDAOImpl.getSBOServiceResponse for divItemSku:"+divItemSkuElement +" storeNumber:"+storeNumber+" itemIdType:"+itemIdType);
				sboServiceResponseList.add(sboServiceResponse);	
			}					
		}
		if(sboServiceResponseList.size()>0){
			logger.info("Exiting from SBOProcessorImpl.getSBOServiceResponse with size:"+ sboServiceResponseList.size());
			return sboServiceResponseList;
		}else{
			logger.info("Exiting from SBOProcessorImpl.getSBOServiceResponse with size:0");
			return null;
		}		
	}
}