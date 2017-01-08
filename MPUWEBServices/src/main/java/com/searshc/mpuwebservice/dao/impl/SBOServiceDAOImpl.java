package com.searshc.mpuwebservice.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.searshc.mpuwebservice.dao.SBOServiceDAO;
import com.searshc.targetinteraction.SBOServiceResponse;

/**
 * @description This class contains implementation for SBOServiceDAO methods for SBOService.
 * @author gasolek
 *
 */
@Repository("sboServiceDAOImpl")
public class SBOServiceDAOImpl implements SBOServiceDAO {
	
	private static final Logger logger=Logger.getLogger(SBOServiceDAOImpl.class);
	
	@Autowired
	RestTemplate restTemplate;

	public SBOServiceResponse getSBOServiceResponse(String divItemSku, String storeNumber,
			String itemIdType) {
		logger.info("Inside SBOServiceDAOImpl.getSBOServiceResponse divItemSku:"+divItemSku +" storeNumber:"+storeNumber+" itemIdType:"+itemIdType);
		SBOServiceResponse response = null;		
		String serverURL = com.searshc.mpuwebservice.util.PropertyUtils.getProperty("SBOServiceURL")+divItemSku+"/"+storeNumber+"?itemIdType="+itemIdType;
		//serverURL ="http://hfdvsbointjbos1.vm.itg.corp.us.shldcorp.com:8180/DEJInterfaces/v1/items/02213073000/01008?itemIdType=DIV-ITEM-SKU";
		logger.info("ServerURL for SBO Service :"+ serverURL);
		try {
			response = restTemplate.getForObject(serverURL, SBOServiceResponse.class);
		} catch (Exception e) {			
			logger.info("getSBOServiceResponse", e);
		}
		if(response !=null){
			logger.info("Exit from SBOServiceDAOImpl.getSBOServiceResponse with successful response");
			return response;
		}else{
			logger.info("Exit from SBOServiceDAOImpl.getSBOServiceResponse with successful response null");
			return null;
		}		
	}
}
