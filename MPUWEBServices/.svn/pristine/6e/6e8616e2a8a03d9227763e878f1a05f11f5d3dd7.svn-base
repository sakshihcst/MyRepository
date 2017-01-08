package com.searshc.mpuwebservice.processor;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.ActivityUserEntity;

public interface ActiveUserProcessor {

	int insertUser(ActivityUserEntity activityUserEntity) throws DJException;

	int updateActiveUserFlag(String userId, String loggedInTime,
			String activeUserFlag) throws DJException;

	int updateModFlag(String userId, String loggedInTime, boolean modFlag) throws DJException;

	int updateLoggedOutTime(String userId, String loggedInTime,
			String loggedOutTime) throws DJException;

	ActivityUserEntity getActiveUserForUserId(String userId,String storeNumber) throws DJException;
	public String getActiveUserForMOD(String storeNo) throws DJException;

}
