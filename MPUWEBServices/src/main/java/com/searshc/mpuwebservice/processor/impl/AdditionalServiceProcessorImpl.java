package com.searshc.mpuwebservice.processor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.controller.AddnServiceController;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.processor.AdditionalServiceProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;

import edu.emory.mathcs.backport.java.util.Collections;

@Service("additonalServicesProcessorImpl")
public class AdditionalServiceProcessorImpl implements
		AdditionalServiceProcessor {
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	
	@Autowired
	@Qualifier("webServicesProcessorImpl")
	private MPUWebServiceProcessor webServicesProcessor;
	private static transient DJLogger logger = DJLoggerFactory.getLogger(AdditionalServiceProcessorImpl.class);
	
	public void sendFinalResponseManual(String[] salescheckList, String storeNum)
			throws DJException {
		// TODO Auto-generated method stub
		List<String> scList = new ArrayList<String>();
		Collections.addAll(scList, salescheckList);
		List<String> rqtList = mpuWebServiceDAOImpl.getRqtIdList(storeNum,scList);
		if(null!=rqtList){
			for(String rqtId:rqtList){
				logger.error("sendFinalResponseManual = "+rqtId, "");
				webServicesProcessor.sendFinalResponse(storeNum, rqtId, null, "COMPLETED",null);
			}
		}

	}

}
