package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


public class ItemStatusExtractor implements ResultSetExtractor{

    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
    	
HashMap<String,Object> itemStatus=new HashMap<String,Object>();
		
		
		
		if(rs!=null){
			while(rs.next()){
				itemStatus.put(rs.getString("item_identifier"), rs.getString("item_status"));
				
		      }
		}	
		
		return itemStatus;
            
    }
}
