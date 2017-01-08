package com.searshc.mpuwebservice.mapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.Transaction;


public class OriginalOrderExtractor implements ResultSetExtractor{
	private static transient DJLogger logger = DJLoggerFactory.getLogger(OriginalOrderExtractor.class);
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
        String originalJson=null;
        Order order=null;
		if(rs!=null){
			while(rs.next()){
				originalJson=rs.getString("originalJson");
		      }
		}	
		ObjectMapper mapper=new ObjectMapper();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			order=mapper.readValue(originalJson, Order.class);
			
			JsonNode tree=mapper.readTree(originalJson);
			
			if(tree.get("transaction")!=null){
				Transaction transaction=mapper.readValue(tree.get("transaction"), Transaction.class);	
				order.setTransaction(transaction);
			}
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			logger.error("exception extracting order", e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			logger.error("exception extracting order", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("exception extracting order", e);
		}
       return order;     
    }
}
