package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.util.StringUtils;

import com.sears.dj.common.mapper.DJRowMapper;
import com.searshc.mpuwebservice.bean.CustomerDTO;
public class IdentifierDTOMapper implements DJRowMapper<CustomerDTO>{
private CustomerDTO customerDTO;

public CustomerDTO mapRow(ResultSet rs, int rowNum)
		throws SQLException {
	customerDTO = new CustomerDTO();
/*	customerDTO.setFirstName(rs.getString(FIRST_NAME.name()));
	customerDTO.setLastName(rs.getString(LAST_NAME.name()));
	customerDTO.setEmail(rs.getString(EMAIL.name()));
	customerDTO.setPhone(rs.getString(PHONE.name()));
	customerDTO.setSywNumber(rs.getString(SYW_NUMBER.name()));
	customerDTO.setSywStatus(rs.getString(SYW_STATUS.name()));
	customerDTO.setAddress1(rs.getString(ADDRESS1.name()));
	customerDTO.setAddress2(rs.getString(ADDRESS2.name()));
	customerDTO.setZipcode(rs.getString(ZIPCODE.name()));
	customerDTO.setCustomerId(rs.getString(CUSTOMER_ID.name()));
	customerDTO.setAccountNumber(rs.getString(ACCOUNT_NUMBER.name()));
*/	//customerDTO.setsalescheck();//TODO add sales check field
	do{
		if("1".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setFirstName(rs.getString("value"));
		}else if("2".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setLastName(rs.getString("value"));
		}else if("3".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setEmail(rs.getString("value"));
		}else if("4".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setPhone(rs.getString("value"));
		}else if("5".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setSywNumber(rs.getString("value"));
		}else if("6".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setSywStatus(rs.getString("value"));
		}else if("7".equalsIgnoreCase(rs.getString("type"))&& StringUtils.hasText(rs.getString("value"))){
			customerDTO.setAddress1(rs.getString("value"));
		}else if("8".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setAddress2(rs.getString("value"));
		}else if("9".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setZipcode(rs.getString("value"));
		}else if("10".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setCustomerId(rs.getString("value"));
		}else if("11".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setAccountNumber(rs.getString("value"));
		}else if("13".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setPhoneNumber(rs.getString("value"));
		}else if("14".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setAltPhoneNumber(rs.getString("value"));
		}else if("15".equalsIgnoreCase(rs.getString("type")) && StringUtils.hasText(rs.getString("value"))){
			customerDTO.setAreaCode(rs.getString("value"));
		}else if("16".equalsIgnoreCase(rs.getString("type"))&& StringUtils.hasText(rs.getString("value"))){
			customerDTO.setAltAreaCode(rs.getString("value"));
		}else if("18".equalsIgnoreCase(rs.getString("type"))&& StringUtils.hasText(rs.getString("value"))){
			customerDTO.setVehicleInd(rs.getString("value"));				
		}else if("19".equalsIgnoreCase(rs.getString("type"))&& StringUtils.hasText(rs.getString("value"))){
			customerDTO.setVehicleYear(rs.getString("value"));				
		}else if("20".equalsIgnoreCase(rs.getString("type"))&& StringUtils.hasText(rs.getString("value"))){
			customerDTO.setVehicleType(rs.getString("value"));
		}else if("21".equalsIgnoreCase(rs.getString("type"))&& StringUtils.hasText(rs.getString("value"))){
			customerDTO.setVehicleModel(rs.getString("value"));
		}else if("22".equalsIgnoreCase(rs.getString("type"))&& StringUtils.hasText(rs.getString("value"))){
			customerDTO.setVehicleColor(rs.getString("value"));
		}else if("23".equalsIgnoreCase(rs.getString("type"))&& StringUtils.hasText(rs.getString("value"))){
			customerDTO.setShopinNotificationId(rs.getString("value"));
		}
		
	}while(rs.next());

	return customerDTO;
}
}
