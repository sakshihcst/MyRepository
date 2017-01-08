package com.searshc.mpuwebservice.util;

import java.util.Map;

import com.searshc.mpuwebservice.constant.InterfaceConstants;



public class InterfaceUtil {
	
	
	
	//private static final DJLogger DJ_LOGGER = DJLoggerFactory.getLogger(InterfaceUtil.class);
	
	// private static final String idFormatDate = "yyyy-MM-dd";
	
//	private static final String idFormatTime = "hh:mm:ss.mmm";
	
	
	/**
	 * Populate Item and Stock Locator Details in ItemMeta object
	 * @param productInfoResponse
	 * @param stockLocatorResponse
	 * @return 
	 */
	
	public static String appendURLQueryParams(String baseUrl,
			Map<String, String> queryParams) {
		StringBuffer serviceUrl = new StringBuffer(baseUrl);
		serviceUrl.append(InterfaceConstants.QUERY_PARAM_MARKER.getValue());
		for (Map.Entry<String, String> entry : queryParams.entrySet()) {
			serviceUrl.append(entry.getKey());
			serviceUrl
					.append(InterfaceConstants.KEY_VALUE_SEPARATOR.getValue());
			serviceUrl.append(entry.getValue());
			serviceUrl.append(InterfaceConstants.QUERY_PARAM_SEPARATOR
					.getValue());
		}		
		return serviceUrl.toString();
	}
	
}
