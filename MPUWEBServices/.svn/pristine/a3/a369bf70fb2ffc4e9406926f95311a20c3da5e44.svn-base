package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.MpuStaticParamEntity;

public class MpuStaticParamMapper implements RowMapper <MpuStaticParamEntity>{
	
	public MpuStaticParamEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MpuStaticParamEntity mpuStaticParamEntity = new MpuStaticParamEntity();		
		mpuStaticParamEntity.setMpuStaticParamId(Integer.parseInt(rs.getString("mpu_static_Param_id")));
		mpuStaticParamEntity.setParamKey(rs.getString("param_key"));
		mpuStaticParamEntity.setParamValue(rs.getString("param_value"));
		mpuStaticParamEntity.setStoreFormat(rs.getString("store_format"));
		mpuStaticParamEntity.setStoreNo(rs.getString("store_no"));
		return mpuStaticParamEntity;
	}

}
