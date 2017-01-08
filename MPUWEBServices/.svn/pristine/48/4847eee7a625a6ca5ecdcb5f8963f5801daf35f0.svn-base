package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;

public class PickUpOrdersMapper implements RowMapper<ItemDTO>{
	
	public ItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		ItemDTO pickUpOrders =  new ItemDTO();
		
		pickUpOrders.setItemSeq(rs.getString("item_seq"));
		
		pickUpOrders.setRequestType(rs.getString("request_type"));
		
		pickUpOrders.setRqtId(rs.getString("rqt_id"));
		
		pickUpOrders.setRqdId(rs.getString("rqd_id"));
		
		pickUpOrders.setSalescheck(rs.getString("salescheck"));
		
		pickUpOrders.setDivNum(rs.getString("div_num"));
		
		pickUpOrders.setItem(rs.getString("item"));
		
		pickUpOrders.setSku(rs.getString("sku"));
		
		pickUpOrders.setLayawayType(rs.getString("layaway_type"));
		
		pickUpOrders.setItemStatus(rs.getString("item_status"));
		
		pickUpOrders.setQty(rs.getString("qty"));
		
		pickUpOrders.setQtyRemaining(rs.getString("qty_remaining"));
		
		pickUpOrders.setToLocation(rs.getString("to_location"));
		
		pickUpOrders.setFromLocation(rs.getString("from_location"));
		
		pickUpOrders.setStockLocation(rs.getString("stock_location"));
		
		pickUpOrders.setCustomerId(rs.getString("customer_id"));
		
		pickUpOrders.setRequestStatus((rs.getString("request_status")));

			
		return pickUpOrders;
		
	}

}
