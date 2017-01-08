package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DIV_ITEM_SKV;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.END_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.START_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.PickupReportDTO;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;

public class PickupReportDTOMapper implements RowMapper<PickupReportDTO> {

	public PickupReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {		
		PickupReportDTO pickupReportDTO = new PickupReportDTO();
		pickupReportDTO.setStoreNo(MPUWebServiceUtil.getFormattedStoreNo(rs.getString(STORE_NUMBER.name())));
		pickupReportDTO.setSalescheck(MPUWebServiceUtil.nullToBlank(rs.getString(SALESCHECK.name())));
		pickupReportDTO.setItemId(MPUWebServiceUtil.nullToBlank(rs.getString(RQD_ID.name())));
		pickupReportDTO.setDivItemSkv(MPUWebServiceUtil.nullToBlank(rs.getString(DIV_ITEM_SKV.name())));
		pickupReportDTO.setCreatedDate(MPUWebServiceUtil.getFormattedDate(rs.getString(START_TIME.name())));
		pickupReportDTO.setEndedDate(MPUWebServiceUtil.getFormattedDate(rs.getString(END_TIME.name())));
		//pickupReportDTO.setDuration(rs.getTime(DURATION.name())+"");
		pickupReportDTO.setDuration("6:56:24");
		pickupReportDTO.setAssociateName(MPUWebServiceUtil.nullToBlank(rs.getString(ASSOCIATE_NAME.name())));
		return pickupReportDTO;
	}

}