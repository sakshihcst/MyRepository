package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.KIOSK_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.MODIFIED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_ASSIGNEE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_END_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_START_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_FORMAT;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORIGINALJSON;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORDER_SOURCE;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.OrderDTO;

/**

** This contains one to one mapping with table columns 

* @Copyright by TCS
* @author Ramesh Prasad
* @version 1.0 dated Mon Mar 10 12:56:52 IST 2014
*/


public class OrderDTOMapper implements RowMapper<OrderDTO> {
public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	OrderDTO orderdto = new OrderDTO();
orderdto.setCreatedBy(rs.getString(CREATED_BY.name()));
orderdto.setRequestStatus(rs.getString(REQUEST_STATUS.name()));
orderdto.setStoreFormat(rs.getString(STORE_FORMAT.name()));
orderdto.setOriginalJson(rs.getString(ORIGINALJSON.name()));
orderdto.setKioskName(rs.getString(KIOSK_NAME.name()));
orderdto.setSalescheck(rs.getString(SALESCHECK.name()));
orderdto.setCreateTimestamp(rs.getString(CREATE_TIMESTAMP.name()));
orderdto.setRequestNumber(rs.getString(REQUEST_NUMBER.name()));
/*
 * Check if the store number is of five digit
 * if not apply left padding to it 
 */
if(null!=rs.getString(STORE_NUMBER.name())){
	String storeNum = rs.getString(STORE_NUMBER.name());
	if(storeNum.length()<5){
		storeNum = StringUtils.leftPad(storeNum, 5, '0');
	}
	orderdto.setStoreNumber(storeNum);
}
orderdto.setPickupStartTime(rs.getString(PICKUP_START_TIME.name()));
orderdto.setModifiedBy(rs.getString(MODIFIED_BY.name()));
orderdto.setUpdateTimestamp(rs.getString(UPDATE_TIMESTAMP.name()));
orderdto.setPickupStatus(rs.getString(PICKUP_STATUS.name()));
orderdto.setPickupEndTime(rs.getString(PICKUP_END_TIME.name()));
orderdto.setPickupAssignee(rs.getString(PICKUP_ASSIGNEE.name()));
orderdto.setRequestType(rs.getString(REQUEST_TYPE.name()));
orderdto.setRqtId(rs.getString(RQT_ID.name()));
//orderdto.setOriginalTransId(rs.getString(ORIGINAL_TRANS_ID.name()));
orderdto.setOrderSource(rs.getString(ORDER_SOURCE.name()));
return orderdto;
}
}

