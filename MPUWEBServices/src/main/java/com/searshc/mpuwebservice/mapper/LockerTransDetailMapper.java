package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.OrderDTO;

public class LockerTransDetailMapper implements RowMapper<OrderDTO>{
	
	public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {		
		OrderDTO order = new OrderDTO();
		order.setRqtId(rs.getString("rqt_id"));
		order.setTrans_id(Integer.parseInt(rs.getString("trans_id")));
		order.setStoreFormat(rs.getString("store_format"));
		order.setKioskName(rs.getString("kiosk"));
		return order;
	}

}
