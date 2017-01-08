package com.searshc.mpuwebservice.processor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.processor.CacheClearProcessor;

@Service
public class CacheClearProcessorImpl implements CacheClearProcessor{

	@Autowired
	private EhCacheCacheManager cacheManager;
	private static transient DJLogger logger = DJLoggerFactory.getLogger(CacheClearProcessorImpl.class);
	
	public boolean clearStoreDetailCache() {
		EhCacheCache storeQueueCache =  (EhCacheCache) cacheManager.getCache("storeDetailCache");
		logger.info("start clearing ", "storeDetailCache");
		if(null!=storeQueueCache){
			storeQueueCache.clear();
		}
			
		logger.info("end clearing ", "storeDetailCache");
		return true;
	}

	
	public void clearQueueType(String store,String queueType){
		logger.info("start clearQueueType ", "store=="+store+"queueType=="+queueType);
		EhCacheCache mpuQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		String queueKey = store+"-"+queueType;
		if(null!=mpuQueueCache){
			if(null!=mpuQueueCache.get(queueKey)){
				mpuQueueCache.evict(queueKey);
				String cacheRefreshKey = MpuWebConstants.CACHE_REFRESH_FLAG+"-"+org.apache.commons.lang3.StringUtils.leftPad(store, 5, '0')+"-"+queueType;
				mpuQueueCache.evict(cacheRefreshKey);
				}
		}
		logger.info("end clearing ", "clearQueueType");
	}
	
	public void clearMODCache(String store){
		EhCacheCache modlistcache =  (EhCacheCache) cacheManager.getCache("modlistcache");
		if(null!=modlistcache){
			String csmListKey = store+"_CSM";
			String cacheDirtyKey = store+"_isCSMCacheDirty";
			modlistcache.evict(csmListKey);
			modlistcache.put(cacheDirtyKey, true);
		}
	}

}
