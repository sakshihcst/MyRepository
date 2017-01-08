package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ShopInReportDTO;

public class ShopinReportMapper implements RowMapper<ShopInReportDTO>{
	
	public ShopInReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {	
		
		ShopInReportDTO shopInReportDTO = new ShopInReportDTO();
		shopInReportDTO.setPickupId(Integer.parseInt(rs.getString("PICKUP_ID")));
		shopInReportDTO.setStoreNo(rs.getString("STORE_NO"));
		shopInReportDTO.setRegion(rs.getString("REGION"));
		shopInReportDTO.setDistrict(rs.getString("DISTRICT"));
		shopInReportDTO.setPickupRequestType(rs.getString("PICKUP_REQUEST_TYPE"));
		shopInReportDTO.setWorkId(Integer.parseInt(rs.getString(("WORK_ID"))));
		shopInReportDTO.setSalescheck(rs.getString("SALESCHECK"));
		shopInReportDTO.setReqQuantity(Integer.parseInt(rs.getString("REQ_QUANTITY")));
		shopInReportDTO.setStartDate(rs.getTimestamp("START_TIME"));
		shopInReportDTO.setEndDate(rs.getTimestamp("END_TIME"));
		shopInReportDTO.setStatus(rs.getString("PICKUP_STATUS"));
		shopInReportDTO.setCustomerName(rs.getString("customer_name"));
		shopInReportDTO.setAssociateName(rs.getString("associate_name"));
		return shopInReportDTO;
	}

}
