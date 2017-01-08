package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.COUPON_GENERATED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CUSTOMER_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.END_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORIGINAL_SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.START_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RETURN_AUTH_CODE;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.OHMDetailDTO;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;


public class OHMDetailMapper implements RowMapper<OHMDetailDTO> {

	public OHMDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		OHMDetailDTO ohmDetailDTO = new OHMDetailDTO();
		ohmDetailDTO.setAssociateId(rs.getString(ASSOCIATE_ID.name()));
		ohmDetailDTO.setCustomerName(rs.getString(CUSTOMER_NAME.name()));
		ohmDetailDTO.setStartTime(rs.getTimestamp(START_TIME.name()));
		ohmDetailDTO.setEnd_time(rs.getTimestamp(END_TIME.name()));
		ohmDetailDTO.setRequestType(rs.getString(TYPE.name()));
		
		if(rs.getString(ORIGINAL_SALESCHECK.name())!=null ){
		ohmDetailDTO.setSalesCheck(rs.getString(ORIGINAL_SALESCHECK.name()));
		}else{
		ohmDetailDTO.setSalesCheck(rs.getString(SALESCHECK.name()));
		}
		ohmDetailDTO.setRequestStatus(rs.getString(REQUEST_STATUS.name()));
		ohmDetailDTO.setAssociateName(rs.getString(ASSOCIATE_NAME.name()));
		ohmDetailDTO.setCoupon(rs.getBoolean(COUPON_GENERATED.name()));
		ohmDetailDTO.setReturnAuthCode(rs.getString(RETURN_AUTH_CODE.name()));
		return ohmDetailDTO;
		
	}

	
}
