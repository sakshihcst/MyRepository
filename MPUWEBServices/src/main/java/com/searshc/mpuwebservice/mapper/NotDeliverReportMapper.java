package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.OrderDTO;

public class NotDeliverReportMapper implements RowMapper<OrderDTO>{
	
	public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderDTO OrderDTOMapper = new OrderDTO();
		OrderDTOMapper.setRqtId(rs.getInt("rqt_id") + ""); 	
		OrderDTOMapper.setCustomer_name(rs.getString("CUSTOMER_NAME"));
		OrderDTOMapper.setAssociate_Name(rs.getString("associate_name"));
		OrderDTOMapper.setOriginalIdentifier(rs.getString("original_salescheck"));
		OrderDTOMapper.setAssociate_id(rs.getString("associate_id"));
		return OrderDTOMapper;
	}

}
