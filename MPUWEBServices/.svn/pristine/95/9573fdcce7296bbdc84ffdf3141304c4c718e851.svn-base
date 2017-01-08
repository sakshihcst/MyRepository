package com.searshc.mpuhgservice.queueservice;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.searshc.mpuwebservice.bean.HGRequestDTO;




public class JMSUpdateSender1 {
	
	
	
private JmsTemplate jmsTemplateSender= null;
private static final Logger LOGGER=Logger.getLogger(JMSUpdateSender1.class);	
	
@Autowired
public void setJmsTemplateSender(JmsTemplate jmsTemplateSender) {
		this.jmsTemplateSender = jmsTemplateSender;
	}

	public int sendMessages(final HGRequestDTO dto){
		int retval = 1;

		
		try {
			jmsTemplateSender.send(new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					String jsonMessage="";
					try {
						jsonMessage = mapper.writeValueAsString(dto);
						
						LOGGER.error("Sending final response to OBU"+jsonMessage);
					} catch (JsonGenerationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return session.createTextMessage(jsonMessage);
				}
			});

		} catch (JmsException jmsexp) {
			
			LOGGER.error("Error sending final response to queue"+jmsexp.getStackTrace());
			retval = 0;
		} catch (Exception exp) {
			retval = 0;
			LOGGER.error("Error sending final response to queue"+exp.getStackTrace());
		}
		return retval;
	}
	
	

}
