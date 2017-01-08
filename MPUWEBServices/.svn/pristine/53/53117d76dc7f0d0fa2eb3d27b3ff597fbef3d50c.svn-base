package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.MpuRequestTrans;

public class MPUTransMapper  implements RowMapper<MpuRequestTrans>{
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(MPUTransMapper.class);
	
	public MpuRequestTrans mapRow(ResultSet rs, int rowNum) throws SQLException {
		MpuRequestTrans trans = new MpuRequestTrans();
		
		trans.setTrans_id(rs.getInt("trans_id"));
		trans.setKiosk(rs.getString("kiosk"));
		trans.setStoreFormat(rs.getString("store_format"));
		return trans;
	}

}
