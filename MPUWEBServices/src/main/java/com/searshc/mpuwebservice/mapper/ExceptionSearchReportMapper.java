package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ExceptionReportResults;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CUSTOMER_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DIV_NUM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SKU;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PLUS4;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.THUMBNAIL_DESC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.START_TIME; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSIGNEDUSER; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY_SHIPPED; 
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY_REMAINING;

public class ExceptionSearchReportMapper implements RowMapper<ExceptionReportResults> {

	public ExceptionReportResults mapRow(ResultSet rs, int rowNum) throws SQLException {	
		
		
		ExceptionReportResults reportResult = new ExceptionReportResults();
		
		reportResult.setSalesCheckNumber(rs.getString(SALESCHECK.name()));
		reportResult.setCustomerName(rs.getString(CUSTOMER_NAME.name()));
		reportResult.setDivision(rs.getString(DIV_NUM.name()));
		reportResult.setItem(rs.getString(ITEM.name()));
		reportResult.setSku(rs.getString(SKU.name()));
		reportResult.setPlus4(rs.getString(PLUS4.name()));
		reportResult.setQuantity(rs.getString(QTY.name()));
		reportResult.setRequestType(rs.getString(REQUEST_TYPE.name()));
		reportResult.setStatus(rs.getString(ITEM_STATUS.name()));
		reportResult.setRqdId(rs.getString(RQD_ID.name()));
		reportResult.setDescription(rs.getString(THUMBNAIL_DESC.name()));
		reportResult.setStartTime(rs.getString(START_TIME.name()));
		reportResult.setQtyShipped(rs.getString(QTY_SHIPPED.name()));
		reportResult.setQtyRemaining(rs.getString(QTY_REMAINING.name()));
		
		return reportResult;
	}

}
