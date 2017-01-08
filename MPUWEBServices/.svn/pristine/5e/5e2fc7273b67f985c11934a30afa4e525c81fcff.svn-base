package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ActivityUserEntity;

public class ActivityUserEntityMapper implements RowMapper<ActivityUserEntity> {

	public ActivityUserEntity mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ActivityUserEntity activityUserEntity = new ActivityUserEntity();
		
		activityUserEntity.setUserFname(rs.getString("user_fname"));
		activityUserEntity.setUserLname(rs.getString("user_lname"));
		
		
		
		return activityUserEntity;
	}

}
