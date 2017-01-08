package org.tcs.billing.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


@SuppressWarnings("rawtypes")
public class BillingMetaExtractor implements ResultSetExtractor{

    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
    	
    	HashMap<String,Object> billingMeta=new HashMap<String,Object>();
		
		if(rs!=null){
			while(rs.next()){
				billingMeta.put("id", rs.getString("id"));
				billingMeta.put("customerOB10", rs.getString("customerOB10"));
				billingMeta.put("billToCustomerNumber", rs.getString("billToCustomerNumber"));
				billingMeta.put("deliveryNoteNumber", rs.getString("deliveryNoteNumber"));
				billingMeta.put("customerName", rs.getString("customerName"));
				billingMeta.put("customerAddress", rs.getString("customerAddress"));
				billingMeta.put("customerCity", rs.getString("customerCity"));
				billingMeta.put("customerState", rs.getString("customerState"));
				billingMeta.put("customerZipCode", rs.getString("customerZipCode"));
				billingMeta.put("activeStatus", rs.getString("activeStatus"));
		      }
		}	
		return billingMeta;
            
    }
}
