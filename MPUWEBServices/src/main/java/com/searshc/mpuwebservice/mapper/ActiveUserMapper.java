package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ActivityUserEntity;

public class ActiveUserMapper implements RowMapper<ActivityUserEntity> {

	public ActivityUserEntity mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ActivityUserEntity activityUserEntity = new ActivityUserEntity();
		
		activityUserEntity.setUserId(rs.getString("user_id"));
		activityUserEntity.setStoreNo(rs.getString("store_no"));
		activityUserEntity.setCaptainFlag(rs.getInt("captain_flag")==0?false:true);
		activityUserEntity.setActiveKiosk(rs.getString("active_kiosk"));
		activityUserEntity.setInrange(rs.getInt("inrange")==0?false:true);
		activityUserEntity.setSocketHost(rs.getString("socket_host"));
		activityUserEntity.setLoggedInTime(rs.getTimestamp("logged_in_time"));
		activityUserEntity.setLoggedOutTime(rs.getTimestamp("logged_out_time"));
		activityUserEntity.setModFlag(rs.getInt("mod_flag")==0?false:true);
		activityUserEntity.setWebSocketPort(rs.getInt("web_socket_port"));
		activityUserEntity.setUserFname(rs.getString("user_fname"));
		activityUserEntity.setUserLname(rs.getString("user_lname"));
		activityUserEntity.setActiveUserFlag(rs.getString("active_user_flag"));
		activityUserEntity.setAssociateId(rs.getString("associate_id"));
		
		return activityUserEntity;
	}

}