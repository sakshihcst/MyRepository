package com.searshc.mpuwebservice.processor.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ActivityUserEntity;
import com.searshc.mpuwebservice.dao.MCPDBDAO;
import com.searshc.mpuwebservice.processor.ActiveUserProcessor;

@Service("activeUserProcessorImpl")
public class ActiveUserProcessorImpl implements ActiveUserProcessor {
	@Autowired
	private MCPDBDAO mCPDBDAO;
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(ActiveUserProcessorImpl.class);
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int insertUser(ActivityUserEntity activityUserEntity)
			throws DJException {
		return mCPDBDAO.insertUser(activityUserEntity);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int updateActiveUserFlag(String userId, String loggedInTime,
			String activeUserFlag) throws DJException {
		return mCPDBDAO.updateActiveUserFlag(userId, loggedInTime, activeUserFlag);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int updateModFlag(String userId, String loggedInTime, boolean modFlag)
			throws DJException {
		return mCPDBDAO.updateModFlag(userId, loggedInTime, modFlag);
	}

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int updateLoggedOutTime(String userId, String activeUser,
			String loggedOutTime) throws DJException {
		return mCPDBDAO.updateLoggedOutTime(userId, activeUser, loggedOutTime);
	}

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public ActivityUserEntity getActiveUserForUserId(String userId,String storeNumber)
			throws DJException {
		return mCPDBDAO.getActiveUserForUserId(userId,storeNumber);
	}
	
	public String getActiveUserForMOD(String storeNo) throws DJException{
		String  ListModUser = null;
		Integer mod = 1;
		try{
			List<ActivityUserEntity> modActiveUserList = new ArrayList<ActivityUserEntity>();
			List<Map<String,Object>> modActiveUserMap = mCPDBDAO.getModActiveEntity(storeNo, mod);
			for (Map<String, Object> map : modActiveUserMap) {
				ActivityUserEntity modActiveUser = new ActivityUserEntity();
				modActiveUser.setUserFname(map.get("user_fname") + "");
				modActiveUser.setUserLname(map.get("user_lname") + "");
				modActiveUserList.add(modActiveUser);
				}
		if(modActiveUserList != null && modActiveUserList.size()>0){
			ListModUser = "";
			for (Iterator<ActivityUserEntity> iterator = modActiveUserList.iterator(); iterator.hasNext();) {
				ActivityUserEntity activeUserkEntity = (ActivityUserEntity) iterator.next();
				if(activeUserkEntity.getUserFname()!= null && activeUserkEntity.getUserFname().trim() != "" && !"null".equalsIgnoreCase(activeUserkEntity.getUserFname())){
				ListModUser = ListModUser+activeUserkEntity.getUserFname()+ " "+activeUserkEntity.getUserLname()+",";
		}
		}
//		if(ListModUser != null && !ListModUser.trim().equals("")){
		if(StringUtils.hasText(ListModUser)){
		ListModUser = ListModUser.substring(0, ListModUser.length()-1);
		}
		}
		}catch(Exception e){
				
			}
		return ListModUser;
	}

}
