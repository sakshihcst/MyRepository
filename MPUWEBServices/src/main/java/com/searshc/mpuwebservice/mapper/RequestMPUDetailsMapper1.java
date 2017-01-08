/*

 * @(#)RequestMPUDetailsMapper.java	Thu Jun 12 17:33:33 IST 2014
*
* Copyright 2014 Tata Consultancy Services, Inc. All rights reserved.
* TCS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

package com.searshc.mpuwebservice.mapper;


//import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSIGNED_USER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DELIVERED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DIV_NUM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ESCALATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ESCALATION_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FROM_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FULL_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.IS_ACTIVE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_IMAGE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_SALE_ORIGIN;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_SALE_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_SEQ;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.KSN;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LAYAWAY_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LOCKER_ELIGIBLE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.MODIFIED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.NOT_DELIVERED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PLUS4;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY_REMAINING;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUESTED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RETURN_PARENT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SKU;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STOCK_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STOCK_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.THUMBNAIL_DESC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TO_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANSACTION_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANS_DETAIL_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANS_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.VER;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ItemDTO;

/**

** This contains one to one mapping with table columns 

* @Copyright by TCS
*/


public class RequestMPUDetailsMapper1 implements RowMapper<ItemDTO> {
	
//	private static transient DJLogger logger = DJLoggerFactory.getLogger(RequestMPUDetailsMapper.class);
	
	public ItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ItemDTO itemDTO = new ItemDTO();
		
		itemDTO.setVersion(rs.getString(VER.name()));
		itemDTO.setItemCreatedDate(rs.getString(CREATE_TIMESTAMP.name()));
		itemDTO.setRequestType(rs.getString(TRANSACTION_TYPE.name()));
		itemDTO.setCreatedBy(rs.getString(CREATED_BY.name()));
		itemDTO.setRqdId(rs.getString(RQD_ID.name()));
		itemDTO.setUpc(rs.getString(UPC.name()));
		itemDTO.setSalescheck(rs.getString(SALESCHECK.name()));
		itemDTO.setPlus4(rs.getString(PLUS4.name()));
		itemDTO.setItemStatus(rs.getString(ITEM_STATUS.name()));
		itemDTO.setQty(rs.getString(QTY.name()));
		itemDTO.setSku(rs.getString(SKU.name()));
		itemDTO.setRequestNumber(rs.getString(REQUEST_NUMBER.name()));
		itemDTO.setDivNum(rs.getString(DIV_NUM.name()));
		itemDTO.setStockQuantity(rs.getString(STOCK_QUANTITY.name()));
		itemDTO.setItem(rs.getString(ITEM.name()));
		itemDTO.setFullName(rs.getString(FULL_NAME.name()));
		itemDTO.setEscalation(rs.getString(ESCALATION.name()));
		itemDTO.setItemSeq(rs.getString(ITEM_SEQ.name()));
		itemDTO.setDeliveredQuantity(rs.getInt(DELIVERED_QUANTITY.name())); 
		itemDTO.setRqtId(rs.getString(RQT_ID.name()));
		itemDTO.setStockLocation(rs.getString(STOCK_LOCATION.name()));
		itemDTO.setFromLocation(rs.getString(FROM_LOCATION.name()));
		itemDTO.setToLocation(rs.getString(TO_LOCATION.name()));
//		itemDTO.setAssignedUser(rs.getString(ASSIGNED_USER.name()));
		itemDTO.setEscalationTime(rs.getString(ESCALATION_TIME.name()));
		itemDTO.setItemImage(rs.getString(ITEM_IMAGE.name()));
		itemDTO.setThumbnailDesc(rs.getString(THUMBNAIL_DESC.name()));
		itemDTO.setModifiedBy(rs.getString(MODIFIED_BY.name()));
		itemDTO.setKsn(rs.getString(KSN.name()));
		itemDTO.setSaleType(rs.getString(ITEM_SALE_TYPE.name()));
		itemDTO.setItemTransactionType(rs.getString(ITEM_SALE_TYPE.name()));
		itemDTO.setItemSaleOrigin(rs.getString(ITEM_SALE_ORIGIN.name()));
		itemDTO.setIsLockerValid(rs.getString(LOCKER_ELIGIBLE.name()));
		
		itemDTO.setTrans_detail_id(rs.getInt(TRANS_DETAIL_ID.name()));
		itemDTO.setTrans_id(rs.getInt(TRANS_ID.name()));
		itemDTO.setRequested_quantity(rs.getString(REQUESTED_QUANTITY.name()));
		itemDTO.setItemQuantityNotDelivered(rs.getString(NOT_DELIVERED_QUANTITY.name()));
		itemDTO.setUpdateIimestamp(rs.getString(UPDATE_TIMESTAMP.name()));
		itemDTO.setItemId(rs.getString(ITEM_ID.name()));
		itemDTO.setQtyRemaining(rs.getString(QTY_REMAINING.name()));
		itemDTO.setReturnParentId(rs.getString(RETURN_PARENT_ID.name()));
		itemDTO.setIsActive(rs.getString(IS_ACTIVE.name()));
		itemDTO.setCreateTime(rs.getString(CREATE_TIME.name()));
		itemDTO.setLayawayType(rs.getString(LAYAWAY_TYPE.name()));
		//itemDTO.setPinNo(rs.getString(PIN_NO.name()));
		
		/*
		 * Check if the store number is of five digit
		 * if not apply left padding to it 
		 */
		if(null!=rs.getString(STORE_NUMBER.name())){
			String storeNum = rs.getString(STORE_NUMBER.name());
			if(storeNum.length()<5){
				storeNum = StringUtils.leftPad(storeNum, 5, '0');
			}
			itemDTO.setStoreNumber(storeNum);
		}
		
		return itemDTO;
	} 
}



