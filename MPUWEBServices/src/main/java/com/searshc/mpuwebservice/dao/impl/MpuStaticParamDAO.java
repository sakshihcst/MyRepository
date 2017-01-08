package com.searshc.mpuwebservice.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

public interface MpuStaticParamDAO {

	@PostConstruct
	public  void loadCSMLoggingOffStoreList() throws Exception;

	public  List<String> getCSMLoggingOffStoreList() throws Exception;

}