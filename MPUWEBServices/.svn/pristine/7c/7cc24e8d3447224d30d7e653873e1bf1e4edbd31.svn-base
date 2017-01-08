package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.PaymentDTO;

/**

** This contains one to one mapping with table columns 

* @Copyright by TCS
* @author Ramesh Prasad
* @version 1.0 dated Mon Mar 10 12:56:58 IST 2014
*/


public class PaymentDTOMapper implements RowMapper<PaymentDTO> {
public PaymentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	PaymentDTO paymentdto = new PaymentDTO();
paymentdto.setCreatedBy(rs.getString(CREATED_BY.name()));
paymentdto.setPaymentDate(rs.getString(PAYMENT_DATE.name()));
paymentdto.setExpirationDate(rs.getString(EXPIRATION_DATE.name()));

/*
 * Check if the store number is of five digit
 * if not apply left padding to it 
 */
if(null!=rs.getString(STORE_NUMBER.name())){
	String storeNum = rs.getString(STORE_NUMBER.name());
	if(storeNum.length()<5){
		storeNum = StringUtils.leftPad(storeNum, 5, '0');
	}
	paymentdto.setStoreNumber(storeNum);
}


paymentdto.setModifiedBy(rs.getString(MODIFIED_BY.name()));
paymentdto.setStatus(rs.getString(STATUS.name()));
paymentdto.setAccountNumber(rs.getString(ACCOUNT_NUMBER.name()));
paymentdto.setType(rs.getString(TYPE.name()));
paymentdto.setAmount(rs.getBigDecimal(AMOUNT.name()));
paymentdto.setRqtId(rs.getString(RQT_ID.name()));
//paymentdto.setRqtId(rs.getString(RQT_ID.name()));
return paymentdto;
}
}