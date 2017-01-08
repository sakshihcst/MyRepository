package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CUSTOMER_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LOCKER_NO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKEDUP_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKEDUP_INITIATED_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PIN_NO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PIN_RECIEVED_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REFERENCE_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK_NO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANSACTION_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATED_DATE;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.LockerDTO;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;

public class LockerDTOMapper implements RowMapper<LockerDTO> {

	public LockerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {		
		LockerDTO lockerDTO = new LockerDTO();
		lockerDTO.setReferenceId(rs.getString(REFERENCE_ID.name()));
		lockerDTO.setStoreNo(MPUWebServiceUtil.getFormattedStoreNo(rs.getString(STORE_NUMBER.name())));
		lockerDTO.setSalescheckNo(rs.getString(SALESCHECK_NO.name()));
		lockerDTO.setTransactionDate(rs.getString(TRANSACTION_DATE.name()));
		lockerDTO.setLockerNo(rs.getString(LOCKER_NO.name()));
		lockerDTO.setPinNo(rs.getString(PIN_NO.name()));
		lockerDTO.setStatus(rs.getString(STATUS.name()));
		lockerDTO.setPinRecievedDate(rs.getString(PIN_RECIEVED_DATE.name()));
		lockerDTO.setPickedupInitiatedDate(rs.getString(PICKEDUP_INITIATED_DATE.name()));
		lockerDTO.setPickedupDate(rs.getString(PICKEDUP_DATE.name()));
		lockerDTO.setCustomerName(rs.getString(CUSTOMER_NAME.name()));
		lockerDTO.setCreatedDate(rs.getString(CREATED_DATE.name()));
		lockerDTO.setCreatedBy(rs.getString(CREATED_BY.name()));
		lockerDTO.setUpdatedDate(rs.getString(UPDATED_DATE.name()));
		lockerDTO.setUpdatedBy(rs.getString(UPDATED_BY.name()));
		return lockerDTO;
	}

}