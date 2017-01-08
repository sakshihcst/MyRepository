package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSIGNED_USER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIME;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ItemDTO;

/**

** This contains one to one mapping with table columns 

* @Copyright by TCS
* @author Ramesh Prasad
* @version 1.0 dated Fri Mar 07 17:12:15 IST 2014
*/


public class SOAItemMapper implements RowMapper<ItemDTO> {
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(SOAItemMapper.class);
	
public ItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	ItemDTO itemdto = new ItemDTO();
	
	itemdto.setRqtId(rs.getString(RQT_ID.name()));
	itemdto.setRqdId(rs.getString(RQD_ID.name()));
	itemdto.setAssignedUser(rs.getString(ASSIGNED_USER.name()));
	itemdto.setDivNum(rs.getString(DIV_NUM.name()));
	itemdto.setItem(rs.getString(ITEM.name()));
	itemdto.setSku(rs.getString(SKU.name()));
	itemdto.setPlus4(rs.getString(PLUS4.name()));
	itemdto.setSalescheck(rs.getString(SALESCHECK.name()));
	itemdto.setItemStatus(rs.getString(ITEM_STATUS.name()));
	itemdto.setFromLocation(rs.getString(FROM_LOCATION.name()));
	itemdto.setToLocation(rs.getString(TO_LOCATION.name()));
	itemdto.setItemImage(rs.getString(ITEM_IMAGE.name()));
	/*
	 * Check if the store number is of five digit
	 * if not apply left padding to it 
	 */
	if(null!=rs.getString(STORE_NUMBER.name())){
		String storeNum = rs.getString(STORE_NUMBER.name());
		if(storeNum.length()<5){
			storeNum = StringUtils.leftPad(storeNum, 5, '0');
		}
		itemdto.setStoreNumber(storeNum);
	}	
	itemdto.setStockLocation(rs.getString(STOCK_LOCATION.name()));
	itemdto.setStockQuantity(rs.getString(STOCK_QUANTITY.name()));
	itemdto.setThumbnailDesc(rs.getString(THUMBNAIL_DESC.name()));
	itemdto.setFullName(rs.getString(FULL_NAME.name()));
	itemdto.setRequestNumber(rs.getString(REQUEST_NUMBER.name()));
	itemdto.setEscalation(rs.getString(ESCALATION.name()));
	itemdto.setEscalationTime(rs.getString(ESCALATION_TIME.name()));
	itemdto.setItemSeq(rs.getString(ITEM_SEQ.name()));
	itemdto.setCreatedBy(rs.getString(CREATED_BY.name()));
	itemdto.setModifiedBy(rs.getString(MODIFIED_BY.name()));
	
	
	itemdto.setVersion(rs.getString(VER.name()));
	itemdto.setItemCreatedDate(rs.getString(CREATE_TIME.name()));
	itemdto.setUpc(rs.getString(UPC.name()));
	itemdto.setQty(rs.getString(QTY.name()));
	//itemdto.setDeliveredQuantity(Integer.parseInt(rs.getString(DELIVERED_QUANTITY.name()))); 
	itemdto.setKsn(rs.getString(KSN.name()));
	
	/*
	 * Added by Nasir for Newly added attributes
	 */
	itemdto.setSaleType(rs.getString("item_sale_type"));
	itemdto.setItemTransactionType(rs.getString("transaction_type"));
	itemdto.setItemSaleOrigin(rs.getString("item_sale_origin"));
	itemdto.setIsLockerValid(rs.getString("locker_eligible"));
	getExpiredTime(itemdto);
	return itemdto;
}


private void getExpiredTime(ItemDTO itemdto){
	try{
    	SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date currentTime=Calendar.getInstance().getTime();
    	Date createdate = date.parse(itemdto.getItemCreatedDate());
		long result=(currentTime.getTime()-createdate.getTime())+(3600*Integer.parseInt(itemdto.getEscalation())*1000);
		itemdto.setExpireTime(getElapsedTimeHoursMinutesSeconds(result));
	}catch(Exception exception){
		logger.error("getStockLocator",exception);
		itemdto.setExpireTime("00:00:00");
	}
}

private String getElapsedTimeHoursMinutesSeconds(long elapsedTime) { 
    String format = String.format("%%0%dd", 2);  
    elapsedTime = elapsedTime / 1000;  
    String seconds = String.format(format, elapsedTime % 60);  
    String minutes = String.format(format, (elapsedTime % 3600) / 60);  
    String hours = String.format(format, elapsedTime / 3600);  
    String time =  hours + ":" + minutes + ":" + seconds;  
    return time;  
} 
}



