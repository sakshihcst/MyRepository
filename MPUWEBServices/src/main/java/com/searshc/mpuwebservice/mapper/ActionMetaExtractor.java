package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


public class ActionMetaExtractor implements ResultSetExtractor{

	
	
	
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
    	
HashMap<String,Object> actionMeta=new HashMap<String,Object>();
		
		HashMap<String,String> activityMap=new HashMap<String,String>();
		HashMap<String,String> statusMap=new HashMap<String,String>();
		HashMap<String,String> seqMap=new HashMap<String,String>();
		HashMap<String,String> modMap=new HashMap<String,String>();
		
		if(rs!=null){
			while(rs.next()){
				activityMap.put(rs.getString("key_act"), rs.getString("activity"));
				statusMap.put(rs.getString("key_act"), rs.getString("next_status"));
				seqMap.put(rs.getString("key_act"), rs.getString("seq"));
				modMap.put(rs.getString("action_type"), rs.getString("mod_required"));
		      }
		}	
		actionMeta.put("activityMap", activityMap);
		actionMeta.put("statusMap", statusMap);
		actionMeta.put("seqMap", seqMap);
		actionMeta.put("modMap", modMap);
		return actionMeta;
            
    }
}
