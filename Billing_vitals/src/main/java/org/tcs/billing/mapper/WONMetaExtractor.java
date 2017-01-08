package org.tcs.billing.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


@SuppressWarnings("rawtypes")
public class WONMetaExtractor implements ResultSetExtractor{

    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
    	
    	HashMap<String,Object> meta=new HashMap<String,Object>();
		
		if(rs!=null){
			while(rs.next()){
				meta.put("id", rs.getString("id"));
				meta.put("won", rs.getString("workOrderNumber"));
				meta.put("wonStartDate", rs.getString("won_start_dt"));
				meta.put("wonEndDate", rs.getString("won_end_dt"));
				meta.put("wonOwnerId", rs.getString("won_owner_id"));
				meta.put("wonLocation", rs.getString("won_location"));
				meta.put("activeStatus", rs.getString("activeStatus"));
				meta.put("sowNumber", rs.getString("sowNumber"));
		      }
		}	
		return meta;
            
    }
}
