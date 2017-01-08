package org.tcs.billing.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


@SuppressWarnings("rawtypes")
public class EmployeeDetailsExtractor implements ResultSetExtractor{

    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
    	
    	HashMap<String,Object> meta=new HashMap<String,Object>();
		
		if(rs!=null){
			while(rs.next()){
				meta.put("id", rs.getString("id"));
				meta.put("emp_id", rs.getString("emp_id"));
				meta.put("emp_name", rs.getString("emp_name"));
				meta.put("billing_start_dt", rs.getString("billing_start_dt"));
				meta.put("billing_end_dt", rs.getString("billing_end_dt"));
				meta.put("won_number", rs.getString("won_number"));
				meta.put("email", rs.getString("email"));
				meta.put("phone", rs.getString("phone"));
				meta.put("unit_price", rs.getString("unit_price"));
				meta.put("activeStatus", rs.getString("activeStatus"));
		      }
		}	
		return meta;
            
    }
}
