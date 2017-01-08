package com.searshc.mpuwebservice.processor;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.sears.dj.common.exception.DJException;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderAdaptorResponse;

public interface NPOSUpdateProcessor {
	
	public void updateNPOS(OrderAdaptorRequest request,String responseType) throws DJException;

	public void updateNPOSForPickUp(OrderAdaptorRequest request, String reqUrl, Integer transId) throws DJException;

	/**
	 * This method is used for confirming tender Return for RETURNIN5 requests
	 * @param request
	 * @param responseType
	 * @throws DJException
	 * @author nkhan6
	 * @return 
	 */
	public OrderAdaptorResponse confirmTenderReturn(OrderAdaptorRequest request, String responseType) throws DJException;

}
