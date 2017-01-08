package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ExceptionReportResults;

public class KioskListMapper implements RowMapper<String> {

	public String mapRow(ResultSet rs, int rowNum) throws SQLException {		
		String reportResult = new String();
		reportResult = rs.getString("kiosk_name");
		
		//reportResult.setName(rs.getString("name"));
		//reportResult.setType(rs.getInt("type"));
	//	reportResult.setLastName(rs.getString("salescheck"));
		return reportResult;
	}

}
