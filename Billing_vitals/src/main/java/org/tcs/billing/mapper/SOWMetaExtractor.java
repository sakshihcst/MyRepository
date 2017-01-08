package org.tcs.billing.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


@SuppressWarnings("rawtypes")
public class SOWMetaExtractor implements ResultSetExtractor{

    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
    	
    	HashMap<String,Object> meta=new HashMap<String,Object>();

		if(rs!=null){
			while(rs.next()){
				meta.put("id", rs.getString("id"));
				meta.put("invoiceDetails", rs.getString("sowName"));
				meta.put("sowStartDate", rs.getString("sow_start_dt"));
				meta.put("sowEndDate", rs.getString("sow_end_dt"));
				meta.put("activeStatus", rs.getString("activeStatus"));
				meta.put("sowNumber", rs.getString("sowNumber"));
				meta.put("purchaseOrderNumber", rs.getString("purchaseOrderNumber"));
		      }
		}	
		return meta;
            
    }
}
