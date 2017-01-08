package com.searshc.mpuwebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.processor.CacheClearProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;

@Controller
public class CacheRefreshController {
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(WebServicesController.class);

	@Autowired
	@Qualifier("webServicesProcessorImpl")
	private MPUWebServiceProcessor webServicesProcessor;
	
	
	@Autowired
	CacheClearProcessor cacheClearProcessor;
	
	//Constant crr = cacheRefreshRate;
	/**
	 * This method is used for clear the EhCache
	 * @param storeNumber
	 * @param queueType
	 * @param kiosk
	 * @return
	 */
	/*@Scheduled(fixedRateString = "${refresh.delay.property}")
	public void refreshEhCache() {
		logger.debug("Entering", "CacheRefreshController.refreshEhCache");
		boolean isCacheCleared = false;
		isCacheCleared = webServicesProcessor.clearCache("false");
		logger.info("isCacheCleared = ", isCacheCleared);
		 logger.debug("exiting" ,"CacheRefreshController.refreshEhCache");
		
	}*/
	
	/**
	 * This method will clear all the data from the store detail cache after every 30 mins
	 * 
	 */
	@Scheduled(fixedRateString = "${storeCacheCleanInterval}")
	public void refreshStoreDetailsEhcache() {
		logger.debug("Entering", "CacheRefreshController.refreshStoreDetailsEhcache");
		boolean isCacheCleared = false;
		isCacheCleared = cacheClearProcessor.clearStoreDetailCache();
		logger.info("refreshStoreDetailsEhcache = ", isCacheCleared);
		 logger.debug("exiting" ,"CacheRefreshController.refreshStoreDetailsEhcache");
		
	}
	
	
	/**
	 * This method is used for clear the EhCache
	 * @param storeNumber
	 * @param queueType
	 * @param kiosk
	 * @return
	 */
	@Scheduled(fixedRateString = "${refresh.storemeta.property}")
	public void refreshStoreEhCache() {
		logger.info("Entering", "refreshStoreEhCache.refreshEhCache");
		boolean isCacheCleared = false;
		isCacheCleared = webServicesProcessor.clearCache("true");
		logger.info("isCacheCleared = ", isCacheCleared);
		 logger.info("exiting" ,"refreshStoreEhCache.refreshEhCache");
		
	}
	
	/* Gaming Enhancement no. 5 start*/

	
	/** 
	 * this method will update the from location of all the staged items that have not been picked up with the default bin# at EOD
	 * @return
	 */
	/*	
	 * @Scheduled(cron="${cron.schedule.property}")
	@RequestMapping(method = RequestMethod.GET, value = "/v1/updateSameDayHFMbin",produces = "application/json")
	public void scheduledStageBinning() {	
		boolean isbinningComplete = false;
		logger.info("scheduledStageBinning","Entering CacheRefreshController.scheduledStageBinning" );
		try {
			webServicesProcessor.updateHFMbin();
		} catch (DJException e) {
			logger.error("scheduledStageBinning",e);
		}
		catch (Exception ex) {
			logger.error("scheduledStageBinning",ex);
		}
		logger.info("scheduledStageBinning = ", isbinningComplete);
		logger.debug("exiting" ,"CacheRefreshController.scheduledStageBinning");
	}
	*/
	
	/* Gaming Enhancement no. 5 end*/
	

	@RequestMapping(method = RequestMethod.GET, value = "/v1/clearRequestType/{store}/{queueType}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> clearRequestType(@PathVariable("store") String store,@PathVariable("queueType") String queueType){
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			cacheClearProcessor.clearQueueType(store, queueType);
			responseDTO.setResponseBody(true);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}  catch (Exception e) {
			logger.error("createRequest",e);
			responseDTO.setResponseBody(e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"WebServicesController.createRequest");
		return respEntity;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/clearMODCache/{store}", produces = "application/json")
	public @ResponseBody
	ResponseEntity<ResponseDTO> clearMODCache(@PathVariable("store") String store){
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			cacheClearProcessor.clearMODCache(store);
			responseDTO.setResponseBody(true);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		}  catch (Exception e) {
			logger.error("createRequest",e);
			responseDTO.setResponseBody(e);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, false);
		}
		 logger.info("exiting" ,"WebServicesController.createRequest");
		return respEntity;
	}
	
	
	
}
