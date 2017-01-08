package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ReportResultsDet;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORIGINAL_SALESCHECK; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.START_TIME; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_ID; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANS_ID; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANS_DETAIL_ID; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.NOT_DELIVERED_QUANTITY; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORIGINALJSON; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TYPE; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUESTED_QUANTITY; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DELIVERED_QUANTITY; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TIME; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DIV_NUM; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SKU; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PLUS4; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.THUMBNAIL_DESC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_SEQ;



public class JournelSearchReportDetMapper implements RowMapper<ReportResultsDet> {

	public ReportResultsDet mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReportResultsDet reportResultDet = new ReportResultsDet();
		
		reportResultDet.setRelatedSalescheck(rs.getString(SALESCHECK.name()));
		reportResultDet.setSalesCheckNumber(rs.getString(ORIGINAL_SALESCHECK.name()));
		reportResultDet.setUpdateTimeStamp(rs.getString(START_TIME.name()));
		reportResultDet.setAssignedUser(rs.getString(ASSOCIATE_ID.name()));
		reportResultDet.setDivNumber(rs.getString(DIV_NUM.name()));
		reportResultDet.setItem(rs.getString(ITEM.name()));
		reportResultDet.setSku(rs.getString(SKU.name()));
		reportResultDet.setPlus4(rs.getString(PLUS4.name()));
		reportResultDet.setQty(rs.getString(REQUESTED_QUANTITY.name()));
		reportResultDet.setDeliveredQty(rs.getString(DELIVERED_QUANTITY.name()));
		reportResultDet.setRequestStatus(rs.getString(ITEM_STATUS.name()));
		reportResultDet.setRequestType(rs.getString(TYPE.name()));
		reportResultDet.setRqtId(rs.getString(RQT_ID.name()));
		reportResultDet.setDescription(rs.getString(THUMBNAIL_DESC.name()));
		reportResultDet.setTransId(rs.getString(TRANS_ID.name()));
		reportResultDet.setTransDetailId(rs.getString(TRANS_DETAIL_ID.name()));
		reportResultDet.setNotDeliveredQty(rs.getString(NOT_DELIVERED_QUANTITY.name()));
		reportResultDet.setOriginalJson(rs.getString(ORIGINALJSON.name()));
		reportResultDet.setTime(rs.getString(TIME.name()));
		reportResultDet.setItemSeq(rs.getString(ITEM_SEQ.name()));
		reportResultDet.setTimeStart(rs.getString("time_start"));
		
		return reportResultDet;
	}

}
