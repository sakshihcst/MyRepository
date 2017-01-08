package com.searshc.mpuwebservice.processor.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.util.web.client.RestTemplate;


public class NPOSUpdateServiceThread implements Runnable{

	private static Logger logger = Logger.getLogger(NPOSUpdateServiceThread.class.getName());
	Order order = null;
	String serverURL = null;		
	@Autowired
	RestTemplate aRestTemplate;
	
	public NPOSUpdateServiceThread(){
		super();
	}
	
	public NPOSUpdateServiceThread(
			Order order,
			String serverURL) {
		super();
		this.order = order;
		this.serverURL = serverURL;
	}
	/**
	 * Method to update NPOS
	 */
	public void run() {
		try {
			updateNPOS(this.order,this.serverURL);		
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Send update to NPOS using server URL
	 * @param order
	 * @param serverURL
	 */
	public void updateNPOS(Order order, String serverURL)
	{
		int retryCount=1;
		try {
			OrderAdaptorRequest request=new OrderAdaptorRequest();
			request.setRequestOrder(order);
			logger.info("ServerURL for  update NPOS ..."+serverURL);
			HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
			aRestTemplate = new RestTemplate();
			while(retryCount<=3){
				try{
					logger.info("ServerURL for  update NPOS try ..."+serverURL);
					aRestTemplate.put(serverURL, requestEntity);
					retryCount=4;
				}catch(Exception exception){
					retryCount++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
