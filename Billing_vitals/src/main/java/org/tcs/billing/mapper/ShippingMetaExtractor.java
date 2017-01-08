package org.tcs.billing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


@SuppressWarnings("rawtypes")
public class ShippingMetaExtractor implements ResultSetExtractor{

    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
    	
    	HashMap<Object,Object> shippingMeta = new HashMap<Object,Object>();
		
		if(rs!=null){
			while(rs.next()){
				shippingMeta.put("id", rs.getString("id"));
				shippingMeta.put("shipToName", rs.getString("shipToName"));
				shippingMeta.put("shipToAddress", rs.getString("shipToAddress"));
				shippingMeta.put("shipToCity", rs.getString("shipToCity"));
				shippingMeta.put("shipToState", rs.getString("shipToState"));
				shippingMeta.put("shipToZip", rs.getString("shipToZip"));
				shippingMeta.put("activeStatus", rs.getString("activeStatus"));
		      }
		}	
		return shippingMeta;
            
    }
}
