package com.searshc.mpuwebservice.processor.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sears.dj.common.exception.DJException;
import com.sears.mpu.backoffice.domain.Order;
import com.searshc.mpuhgservice.queueservice.JMSUpdateSender1;
import com.searshc.mpuhgservice.queueservice.JMSUpdateSender2;
import com.searshc.mpuwebservice.bean.HGRequestDTO;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.processor.OBUUpdateProcessor;
import com.searshc.mpuwebservice.queues.MPUWebServiceAckSender1;
import com.searshc.mpuwebservice.queues.MPUWebServiceAckSender2;

@Service("OBUUpdateProcessorImpl")
public class OBUUpdateProcessorImpl implements OBUUpdateProcessor {
	
	@Autowired
	private MPUWebServiceAckSender1 sender1;
	
	@Autowired
	private MPUWebServiceAckSender2 sender2;
	
	@Autowired
	private JMSUpdateSender1 updateSender1;
	
	@Autowired
	private JMSUpdateSender2 updateSender2;
	
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	
	static Logger logger = Logger.getLogger(OBUUpdateProcessorImpl.class.getName());

	@Async
	public void updateOBU(Order order) throws JsonGenerationException, JsonMappingException, IOException{
		logger.info("Entering "+this.getClass().getName()+":processMessage");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
		mapper.setDateFormat(dateFormat);
		
		String ack = mapper.writeValueAsString(order);
		int queueResp = sender1.sendMessages(ack);
		logger.info("====queueResp1===="+queueResp);
		if(queueResp == 0){
			queueResp = sender2.sendMessages(ack);
		}
		
		/**
		 * If writing response to queue fails due to some exception
		 * save the response status to DB as FAILED
		 */
		if(queueResp == 0){
			String storeNum = formatStoreNum(order.getStoreNo());
			try {
				mpuWebServiceDAOImpl.setResponseStatusFailed(storeNum,order.getOrderNumber());
			} catch (DJException e) {
				logger.error("Exception in final response status = "+e);
			}
		}
		
		
		logger.info("Exiting "+this.getClass().getName()+":processMessage");
	}
	
	public void updateHGOBU(HGRequestDTO order) throws JsonGenerationException, JsonMappingException, IOException{
		logger.info("Entering "+this.getClass().getName()+":processMessage");
		int queueResp = updateSender1.sendMessages(order);
		if(queueResp == 0){
			updateSender2.sendMessages(order);
		}
		logger.info("Exiting "+this.getClass().getName()+":processMessage");
	}
	
	private String formatStoreNum(String strNum){
		String formattedStrNum=strNum;
		if(strNum!=null){
			if (strNum.length()>5){
				formattedStrNum=strNum.substring(2);
			}
		}
		return formattedStrNum;
		
	}
}
