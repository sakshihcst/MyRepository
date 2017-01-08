package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSIGNED_USER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DIV_NUM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ESCALATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ESCALATION_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FROM_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FULL_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_IMAGE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_SEQ;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.KSN;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.MODIFIED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PLUS4;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SKU;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STOCK_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STOCK_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.THUMBNAIL_DESC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TO_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.VER;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ItemDTO;

public class PickUpItemDTOMapper  implements RowMapper<ItemDTO> {

private static transient DJLogger logger = DJLoggerFactory.getLogger(PickUpItemDTOMapper.class);

public ItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	
	ItemDTO itemdto = new ItemDTO();
	itemdto.setVersion(rs.getString(VER.name()));
	itemdto.setRqdId(rs.getString(RQD_ID.name()));
	itemdto.setUpc(rs.getString(UPC.name()));
	itemdto.setSalescheck(rs.getString(SALESCHECK.name()));
	

	
	itemdto.setItemStatus(rs.getString(ITEM_STATUS.name()));
	itemdto.setQty(rs.getString(QTY.name()));
	itemdto.setSku(rs.getString(SKU.name()));
	itemdto.setItem(rs.getString(ITEM.name()));
	
	/*
	 * Check if the store number is of five digit
	 * if not apply left padding to it 
	 */
	
	itemdto.setDivNum(rs.getString(DIV_NUM.name()));

	//itemdto.setDeliveredQuantity(Integer.parseInt(rs.getString(DELIVERED_QUANTITY.name()))); 
	itemdto.setRqtId(rs.getString(RQT_ID.name()));

	itemdto.setAssignedUser(rs.getString(ASSIGNED_USER.name()));
	itemdto.setThumbnailDesc(rs.getString(THUMBNAIL_DESC.name()));

	itemdto.setKsn(rs.getString(KSN.name()));
	

	
	/*
	 * Added by Nasir for Newly added attributes
	 */

	return itemdto;
	
}

}
