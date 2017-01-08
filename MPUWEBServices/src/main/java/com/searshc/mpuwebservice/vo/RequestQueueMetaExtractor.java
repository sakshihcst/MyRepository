package com.searshc.mpuwebservice.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class RequestQueueMetaExtractor implements ResultSetExtractor{

	public Object extractData(ResultSet rs) throws SQLException,DataAccessException {
		// TODO Auto-generated method stub
		HashMap<String,String> queueMeta=new HashMap<String,String>();
		
		if(rs!=null){
			while(rs.next()){
				queueMeta.put(rs.getString("request_type"), rs.getString("queue"));
		      }
		}
		
		return queueMeta;
	}

}
