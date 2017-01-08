package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.SellUnitDTO;

public class SellUnitMapper implements RowMapper<SellUnitDTO>{	
	
	public SellUnitDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SellUnitDTO sellUnitDTO = new SellUnitDTO();
		
		sellUnitDTO.setShcUnitId(rs.getString("SHC_UNIT_ID"));
		sellUnitDTO.setStoreNumber(rs.getString("ORIG_UNIT_ID"));
		//sellUnitDTO.setShcUnitOpnDt(rs.getTimestamp("SHC_UNIT_OPN_DT"));
		sellUnitDTO.setShcUnitName(rs.getString("SHC_UNIT_NAME"));
		sellUnitDTO.setShcRegion(rs.getString("SHC_UNIT_REGION"));
		sellUnitDTO.setShcDistrict(rs.getString("SHC_UNIT_DISTRICT"));
		sellUnitDTO.setAddress(rs.getString("ADDR_LN"));
		sellUnitDTO.setCity(rs.getString("CTY_NM"));
		sellUnitDTO.setState(rs.getString("STATE_CD"));
		sellUnitDTO.setCounty(rs.getString("COUNTY_NM"));
		sellUnitDTO.setPostalCode(rs.getString("PSTL_CD"));
		sellUnitDTO.setPhoneNumber(rs.getString("PHONE_NBR"));
		sellUnitDTO.setTimeZone(rs.getString("TIME_ZONE"));
		sellUnitDTO.setShcUnitType(rs.getString("shc_unit_typ"));
		sellUnitDTO.setShcUnitSubType(rs.getString("SHC_UNIT_SUB_TYP"));		
		
		return sellUnitDTO;
	}
	
}
