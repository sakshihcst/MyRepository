package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sears.dj.common.mapper.DJRowMapper;

public class COMExcecptionMapper implements DJRowMapper<Object[]>{
	public Object[] mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		Object[] objects = new Object[23];
		for(int i =0;i<23;i++){
			objects[i] =  rs.getString(i+1);
		}
		
		return objects;
	}
}
