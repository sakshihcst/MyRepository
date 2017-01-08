package com.searshc.mpuwebservice.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.MpuStaticParamEntity;
import com.searshc.mpuwebservice.dao.MCPDBDAO;
import com.searshc.mpuwebservice.dao.MpuStaticParamDAO;

@Service("mpuStaticParamDAOImpl")
public class MpuStaticParamDAOImpl implements MpuStaticParamDAO {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(MpuStaticParamDAO.class);
	
	@Autowired
	private MCPDBDAO mcpdbdao;
	
	private List<String> csmLoggingList = null;
	
	@PostConstruct
	public void loadCSMLoggingOffStoreList() throws Exception{
		logger.info("Inside","StoreLocalTimeServiceDAOImpl.loadSellUnitMap");
		csmLoggingList = new ArrayList<String>();
		getCSMLoggingList();
		logger.info("Exit","StoreLocalTimeServiceDAOImpl.loadSellUnitMap");
	}
	
	
	public List<String> getCSMLoggingOffStoreList() throws Exception {	
		List<String> storeList = null;
		if(null !=csmLoggingList){
			storeList = csmLoggingList;
		}
		return storeList;
	}
	
	private List<String> getCSMLoggingList() throws Exception{
		List<MpuStaticParamEntity> MpuStaticParamList  = mcpdbdao.getCSMLoggingOffStoreList();
		if(null != MpuStaticParamList && MpuStaticParamList .size()>0){
			for(MpuStaticParamEntity mpuStaticParamEntity:MpuStaticParamList){
				csmLoggingList.add(mpuStaticParamEntity.getStoreNo());
			}
		}
		return  null;
	}

}
