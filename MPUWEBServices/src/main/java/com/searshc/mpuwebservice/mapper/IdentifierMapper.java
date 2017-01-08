package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sears.dj.common.mapper.DJRowMapper;
import com.searshc.mpuwebservice.bean.IdentifierDTO;
public class IdentifierMapper implements DJRowMapper<IdentifierDTO>{
	
private IdentifierDTO identifierDTO;

	public IdentifierDTO mapRow(ResultSet rs, int rowNum)
			throws SQLException {
	
		IdentifierDTO identifierDTO = new IdentifierDTO();
		
		identifierDTO.setRqtId(rs.getInt("rqt_id"));
		identifierDTO.setStoreNumber(String.valueOf(rs.getInt("store_number")));
		identifierDTO.setType(String.valueOf(rs.getInt("type")));
		identifierDTO.setValue(rs.getString("value"));
		
		return identifierDTO;
	}
}
