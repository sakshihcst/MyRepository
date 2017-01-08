package com.searshc.mpuwebservice.queues;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.processor.impl.MPUWebServiceProcessorImpl;


/**
 * This class sends object to MQ.
 * 
 * @author TCS
 * @version 1.0
 * @since
 */

public class MPUWebServiceAckSender1 {

	private JmsTemplate jmsTemplateSender= null;
	private static transient DJLogger logger = DJLoggerFactory.getLogger(MPUWebServiceAckSender1.class);
	
	public void setJmsTemplateSender(JmsTemplate jmsTemplateSender) {
		this.jmsTemplateSender = jmsTemplateSender;
	}

	public int sendMessages(final String queueMessage){
		int retval = 1;
		logger.info("Entering sendMessages-->", this.getClass().getName());
		for (int i=0;i<1;i++){
		try {
			jmsTemplateSender.send(new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					logger.error("Sending Message to OBU :"+queueMessage,"");
					
					//return session.createObjectMessage(queueMessage);
					return session.createTextMessage(queueMessage);
				}
			});

		} catch (JmsException jmsexp) {
			retval = 0;
			logger.error("In Catch Block of sendMessages ::", jmsexp);
		} catch (Exception exp) {
			logger.error("In Catch Block of sendMessages ::", exp);
			exp.printStackTrace();
			retval = 0;
		}}
		return retval;
	}
}
