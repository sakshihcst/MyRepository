package com.searshc.mpuwebservice.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sears.mpu.backoffice.domain.OrderItem;

public class NPOSItemDetailsMapper implements RowMapper<OrderItem> {

	
	public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		OrderItem item = new OrderItem();
		
		item.setSequenceNo(Integer.parseInt(rs.getString("item_seq")));
		item.setItemNo(rs.getString("item")); 
		item.setItemDescr(rs.getString("thumbnail_desc"));
		item.setItemStatus("Returned");
		item.setItemQty(Integer.parseInt(rs.getString("requested_quantity")));
		item.setItemDivision(rs.getString("div_num"));
		item.setItemSku(rs.getString("sku"));
		item.setItemInquiryType(rs.getString("from_location")+"TO"+rs.getString("to_location"));
		item.setItemDeliveryTypeCode(" ");
		item.setItemTransactionType(rs.getString("transaction_type"));
		item.setItemBinNumber("0000000000");
		item.setItemStockState('1');
		item.setItemQuantityAvailable(Integer.parseInt(rs.getString("qty")));
		item.setItemQuantityRequested(Integer.parseInt(rs.getString("requested_quantity")));
		item.setItemQuantityActual(Integer.parseInt(rs.getString("requested_quantity"))); // delivered_quantity
		item.setItemQuantityUnconfirmed(0);
		item.setItemSource(rs.getString("from_location"));
		item.setItemDestination(rs.getString("to_location"));
		item.setAgeRestriction(0);
		item.setSku991SequenceNumber(0);
		item.setKioskItemId(0);
		item.setItemLocation(rs.getString("stock_location"));
		item.setItemSellingAssociateId("");
		item.setItemSaleOrigin(' ');
		item.setSku991SellingPrice("");
		
		
		
		// stock location
	
		
		
		
		
		
		
		
////		item.setItemNoType("");
		
////		item.setItemPriority("");
//		item.setItemLocation(rs.getString("stock_location"));
////		item.setItemLocationType("");
//		item.setItemType(rs.getString("stock_location"));
	 // TODO to ask
//		item.setItemDestination(rs.getString("to_location"));
////		item.setItemPrice(0.0);
////		item.setItemIdentifiers(""); // TODO to ask
//		item.setItemUpc(rs.getString("upc"));

//		item.setItemSkuPlus4(rs.getString("plus4"));
////		item.setItemLine("");
////		item.setItemSubLine("");
////		item.setItemSubLineVariable("");
		
////		item.setItemStatusCode(' ');
////		item.setItemPromisedDate(null);
		
////		item.setItemSellingAssociateId("");
////		item.setItemReturnReason("");

////		item.setItemStockState(' ');
 // TODO to ask
		 // TODO to ask
//		item.setItemCreatedDate(null);
////		item.setReductionsList(null);
////		item.setHighValueItem(false);
////		item.setCommentList(null);
		
//		item.setReturnQty("0");
////		item.setReturnTimeStamp(null);
		 // TODO
////		item.setSku991SellingPrice

//		item.setItemImageUrl(rs.getString("item_image"));
////		item.setItemIdentifiers
		
		
		
		return item;
	}


}
