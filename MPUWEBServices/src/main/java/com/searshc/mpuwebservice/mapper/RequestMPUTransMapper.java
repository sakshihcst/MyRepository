/*

 * @(#)OrderDTOMapper.java	Thu Jun 12 17:33:33 IST 2014
*
* Copyright 2014 Tata Consultancy Services, Inc. All rights reserved.
* TCS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

package com.searshc.mpuwebservice.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.OrderDTO;

/**

** This contains one to one mapping with table columns 

* @Copyright by TCS
*/


public class RequestMPUTransMapper implements RowMapper<OrderDTO> {
	
//	private static transient DJLogger logger = DJLoggerFactory.getLogger(OrderDTOMapper.class);
	
	public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		OrderDTO OrderDTOMapper = new OrderDTO();
		
		OrderDTOMapper.setRequestType(rs.getString("type"));
		if(null != rs.getString("start_time") && !"null".equals(rs.getString("start_time")) && !"".equals(rs.getString("start_time")))
			OrderDTOMapper.setStart_time(rs.getString("start_time").substring(0, rs.getString("start_time").lastIndexOf('.')));
		OrderDTOMapper.setEnd_time(rs.getString("end_time"));
		OrderDTOMapper.setAssociate_id(rs.getString("associate_id"));
		OrderDTOMapper.setSearch_method(rs.getString("search_method"));
		OrderDTOMapper.setSearch_value(rs.getString("search_value"));
		OrderDTOMapper.setKioskName(rs.getString("kiosk"));
		OrderDTOMapper.setStoreNumber(rs.getInt("store_number") + "");
		OrderDTOMapper.setSalescheck(rs.getString("salescheck"));
		OrderDTOMapper.setOriginalIdentifier(rs.getString("original_salescheck"));
		OrderDTOMapper.setRequestStatus(rs.getString("request_status"));
		OrderDTOMapper.setCustomer_name(rs.getString("CUSTOMER_NAME"));
		OrderDTOMapper.setRqtId(rs.getInt("rqt_id") + "");
		OrderDTOMapper.setTrans_id(rs.getInt("trans_id"));
		OrderDTOMapper.setStoreFormat(rs.getString("store_format"));
		OrderDTOMapper.setOriginalJson(rs.getString("originalJson"));
		OrderDTOMapper.setCustomerId(rs.getString("customer_id"));
		OrderDTOMapper.setOrderSource(rs.getString("order_source"));
		OrderDTOMapper.setLayaway(rs.getString("layaway_flag"));
		OrderDTOMapper.setSc_scan(rs.getString("sc_scan"));
		OrderDTOMapper.setPickup_source(rs.getString("pickup_source"));
		
		if(rs.getInt("card_swiped") == 1) {
			OrderDTOMapper.setCardSwiped(Boolean.TRUE);
		} else {
			OrderDTOMapper.setCardSwiped(Boolean.FALSE);
		}
		if("Y".equalsIgnoreCase(rs.getString("installer_flag"))) {
			OrderDTOMapper.setInstallerPickOrder(Boolean.TRUE);
		} else {
			OrderDTOMapper.setInstallerPickOrder(Boolean.FALSE);
		}
		if("N".equalsIgnoreCase(rs.getString("unsecured_flag"))) {
			OrderDTOMapper.setSecureIndicator(Boolean.TRUE);
		} else {
			OrderDTOMapper.setSecureIndicator(Boolean.FALSE);
		}
		
		OrderDTOMapper.setRequestQueueType(rs.getString("request_type"));
		OrderDTOMapper.setIdentifierType(rs.getString("return_auth_code"));
		return OrderDTOMapper;
	}
}



