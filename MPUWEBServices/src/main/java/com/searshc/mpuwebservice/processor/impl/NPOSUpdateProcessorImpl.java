package com.searshc.mpuwebservice.processor.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.exceptionhandler.FileExceptionHandler;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.util.DJUtilities;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderAdaptorResponse;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.PickUpServiceDAO;
import com.searshc.mpuwebservice.processor.NPOSUpdateProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebutil.constant.MPUWEBUtilConstants;


@Service("nPOSUpdateProcessorImpl")
public class NPOSUpdateProcessorImpl implements NPOSUpdateProcessor {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PickUpServiceDAO pickUpServiceDAO;
	
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(NPOSUpdateProcessorImpl.class);

	@Async("nposExecutor")
	public void updateNPOS(OrderAdaptorRequest request, String responseType) throws DJException {

		logger.error("Thread name is for bin NPOS update",Thread.currentThread().getName());
		ObjectMapper mapper=new ObjectMapper();
		
	try {
		logger.error("updateNPOSEntering updateNPOS request : " + mapper.writeValueAsString(request) +"	-- responseType : " + responseType,"");
	} catch (JsonGenerationException e1) {
		// TODO Auto-generated catch block
		logger.error("Error in converting originalJson",e1);
	} catch (JsonMappingException e1) {
		// TODO Auto-generated catch block
		logger.error("Error in converting originalJson",e1);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		logger.error("Error in converting originalJson",e1);
	}
	 
	String storeNumber = request.getRequestOrder().getStoreNo();
	String storeFormat = request.getRequestOrder().getStoreFormat();
	String baseUrl = MPUWebServiceUtil.getDNSForStore(storeNumber, storeFormat);
	//String baseUrl="http://10.109.108.11:8080/mcp_order_adaptor";
	
	String url = "";
	
	if("updateBin".equals(responseType)) {
		
		url = DJUtilities.concatString(baseUrl + "/updateBinNumber");
		
	} else if("complete".equals(responseType)) {
		
		url = DJUtilities.concatString(baseUrl + "/processWebOrderItemAcknowledgment");
	
	}	else {
		
		url = DJUtilities.concatString(baseUrl, "/", responseType);
	}

	HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);

	try {
		
		logger.error("updateNPOS calling NPOS START : " + Calendar.getInstance().getTimeInMillis(),"");
		logger.error(" updateNPOS :--URL---"+ url,"");

		long startTime = Calendar.getInstance().getTimeInMillis();
	
		restTemplate.put(url, requestEntity, new HashMap<String,String>());
	
		long endTime = Calendar.getInstance().getTimeInMillis();
		
		logger.error("updateNPOS calling NPOS END : " + (endTime-startTime),"");
	
	}	catch(Exception e){
		
		logger.error("Failed to update NPOS", e);
	}

	logger.debug("Exiting" ,"NPOSUpdateProcessorImpl.updateNPOS");
	}
	
	@Async("nposExecutor")
	public void updateNPOSForPickUp(OrderAdaptorRequest request, String reqUrl, Integer transId) throws DJException {
		
		logger.error("updateNPOSForPickUp Entering updateNPOSForPickUp request : " + request + "	-- reqUrl : " + reqUrl + " -- transId : " + transId,"");
		try{
			
			String storeNumber = request.getRequestOrder().getStoreNo();
			String storeFormat = request.getRequestOrder().getStoreFormat();
			String baseUrl = MPUWebServiceUtil.getDNSForStore(storeNumber, storeFormat);
			//String baseUrl="http://10.109.108.11:8080/mcp_order_adaptor";
			
			logger.error("Thread name is for pickup NPOS update"+Thread.currentThread().getName(),"");
			
			String url = DJUtilities.concatString(baseUrl, "/", reqUrl);
	
			HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
	
	
				ObjectMapper mapper=new ObjectMapper();
				logger.error("URL to NPOS is:"+url,"");
				try {
					logger.error(" updateNPOSForPickUp Input to NPOS is : " + mapper.writeValueAsString(request),"");
				} catch (Exception e) {
					logger.error("Exception in updateNPOSForPickUp", e);
				}
				
				
				logger.error("updateNPOSForPickUp calling NPOS START For Pickup: " + Calendar.getInstance().getTimeInMillis(),"");
	
				long startTime = Calendar.getInstance().getTimeInMillis();
			
				ResponseEntity<OrderAdaptorResponse> response = restTemplate.postForEntity(url, requestEntity, OrderAdaptorResponse.class);
				
				logger.error("updateNPOSForPickUp updateNPOSForPickUp response : " + response.getBody(),"");
			
				long endTime = Calendar.getInstance().getTimeInMillis();
				
				String relatedSalesCheckNumber = response.getBody().getRelatedSalecheckNo();
				
				if("".equalsIgnoreCase(relatedSalesCheckNumber) || null == relatedSalesCheckNumber || "null".equalsIgnoreCase(relatedSalesCheckNumber)) {
					DJException dj = new DJException();
					dj.setMessage("NPOS not updated");
					throw dj;
				}
				
				pickUpServiceDAO.putRelatedSalecheckForPickUp(relatedSalesCheckNumber, storeNumber, transId);
				
				/**
				 * Declare that the Cache is no longer clean
				 */
				if(null!=request && null!=request.getRequestOrder()){
					EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
					String formattedStoreNo = org.apache.commons.lang3.StringUtils.leftPad(request.getRequestOrder().getStoreNo(), 5, '0');
					String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+request.getRequestOrder().getKioskName();
					String ohmCacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+request.getRequestOrder().getKioskName();
					
					
					logger.error("***cacheDirtyKey***", cacheDirtyKey);
					if(null!=requestQueueCache){
						requestQueueCache.put(cacheDirtyKey, "true");
						requestQueueCache.put(ohmCacheDirtyKey, "true");
					}
				}
				logger.error("updateNPOS calling NPOS END For Pick up: " + (endTime-startTime),"");
				

		}catch (Exception e) {
			// TODO: handle exception
			//logger.error("Exception in updateNPOSForPickUp", e);
			throw FileExceptionHandler.logAndThrowDJException(e,  logger, "updateNPOSForPickUp", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE);
		}
		logger.info("updateNPOSForPickUp", "Exiting updateNPOSForPickUp request : " + request +"	-- reqUrl : " + reqUrl);
		
		
	}
	
	/**
	 * This method is used for confirming tender Return for RETURNIN5 requests
	 * @param request
	 * @param responseType
	 * @throws DJException
	 */
	public OrderAdaptorResponse confirmTenderReturn(OrderAdaptorRequest request, String responseType) throws DJException{
		logger.info("confirmTenderReturn==", "Entering");
		ObjectMapper mapper=new ObjectMapper();
		
		try {
			logger.error("updateNPOS Entering updateNPOS request : " + mapper.writeValueAsString(request) +"	-- responseType : " + responseType,"");
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			logger.error("Error in converting originalJson",e1);
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			logger.error("Error in converting originalJson",e1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Error in converting originalJson",e1);
		}
		
		String storeNumber = request.getRequestOrder().getStoreNo();
		String storeFormat = request.getRequestOrder().getStoreFormat();
		String baseUrl = MPUWebServiceUtil.getDNSForStore(storeNumber, storeFormat);
		//String baseUrl="http://10.109.108.11:8080/mcp_order_adaptor";
		
		String url = DJUtilities.concatString(baseUrl, "/", responseType);
		HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
		ResponseEntity<OrderAdaptorResponse> response = null;
		logger.error("confirmTenderReturn == url==", url);
		try{
			response = restTemplate.postForEntity(url, requestEntity, OrderAdaptorResponse.class);
			if(null!=response){
				logger.error("NPOSUpdateProcessorImpl====confirmTenderReturn==response==", response.getBody().getStatus().getCode());
			}
			
			
			return response.getBody();
			
			
		}catch(Exception ex){
			DJException djException = FileExceptionHandler.logAndThrowDJException(ex,  logger, "confirmTenderReturn", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MpuWebConstants.RETURNIN5_ERROR_MESSAGE);
			throw djException;
		}
		
	}
}
