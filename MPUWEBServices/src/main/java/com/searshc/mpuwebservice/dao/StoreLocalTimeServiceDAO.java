package com.searshc.mpuwebservice.dao;

import java.sql.Timestamp;
import java.util.Date;

import com.sears.dj.common.exception.DJException;

public interface StoreLocalTimeServiceDAO {

	public Date getStoreLocalTime(String storeNumber) throws DJException;
	
	public String getDistrict(String storeNo) throws DJException;
	
	public String getRegion(String storeNo) throws DJException;
	
	public Timestamp getstoreLocalTimeStamp(String storeNumber) throws DJException;
	
}
