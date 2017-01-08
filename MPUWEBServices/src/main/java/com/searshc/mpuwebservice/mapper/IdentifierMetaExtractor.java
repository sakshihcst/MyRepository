package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


public class IdentifierMetaExtractor implements ResultSetExtractor{

    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String,Object> identifierMetaMap=new HashMap<String,Object>();
		if(rs!=null){
			while(rs.next()){
				identifierMetaMap.put(rs.getString("identifier_type"), rs.getInt("identifier_id"));
		      }
		}	
		return identifierMetaMap;
            
    }
}
