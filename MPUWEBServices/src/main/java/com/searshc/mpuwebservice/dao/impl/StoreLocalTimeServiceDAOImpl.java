package com.searshc.mpuwebservice.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.SellUnitDTO;
import com.searshc.mpuwebservice.dao.MCPDBDAO;
import com.searshc.mpuwebservice.dao.StoreLocalTimeServiceDAO;

@Service("storeLocalTimeServiceDAOImpl")
public class StoreLocalTimeServiceDAOImpl implements StoreLocalTimeServiceDAO{
	
private static transient DJLogger logger = DJLoggerFactory.getLogger(StoreLocalTimeServiceDAO.class);
	
	@Autowired
	private MCPDBDAO mcpdbdao;
	
	
	private Map<String, SellUnitDTO> sellUnitMap = null;
	
	@PostConstruct
	public void loadSellUnitMap() throws DJException{
		logger.info("Inside","StoreLocalTimeServiceDAOImpl.loadSellUnitMap");
		sellUnitMap = new HashMap<String, SellUnitDTO>();
		getStoresDetails();
		logger.info("Exit","StoreLocalTimeServiceDAOImpl.loadSellUnitMap");
	}

	private void getStoresDetails() throws DJException{
		List<SellUnitDTO> sellUnitDTOList  = mcpdbdao.loadRecordsFromSellUnitTable();
		if(sellUnitDTOList !=null && sellUnitDTOList.size()>0){
			for(SellUnitDTO sellUnitDTO :sellUnitDTOList){
				sellUnitMap.put(sellUnitDTO.getShcUnitId(), sellUnitDTO);
			}
		}
	}	
	
	public Date getStoreLocalTime(String storeNumber) throws DJException {
		logger.info("Inside StoreLocalTimeServiceDAOImpl.getStoreLocalTime() :"+ storeNumber,"");
		storeNumber = storeNumber != null ? StringUtils.leftPad(storeNumber.trim(), 5, "0") : null;		
		String storeTimeZoneString = null;
		SellUnitDTO sellUnitTbl = sellUnitMap.get(storeNumber);
		
		if(sellUnitTbl != null){
			storeTimeZoneString =sellUnitTbl.getTimeZone();
		}else{
			sellUnitTbl = mcpdbdao.getSellUnitRecordByStoreNo(storeNumber);			
			if(sellUnitTbl != null){
				storeTimeZoneString = sellUnitTbl.getTimeZone();
				sellUnitMap.put(sellUnitTbl.getShcUnitId(), sellUnitTbl);
			}					
		}
		
		logger.info("TimeZone for store " + storeNumber + " is: "+ storeTimeZoneString,"");
		if (storeTimeZoneString == null || storeTimeZoneString.length() == 0) {
			logger.info("Timezone is not available for store " + storeNumber+ ". Server time will be used.","");
			return new Date();
		} else {
			Calendar zoneCal = new GregorianCalendar(TimeZone.getTimeZone(storeTimeZoneString));
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, zoneCal.get(Calendar.YEAR));
			cal.set(Calendar.MONTH, zoneCal.get(Calendar.MONTH));
			cal.set(Calendar.DAY_OF_MONTH, zoneCal.get(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.HOUR_OF_DAY, zoneCal.get(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, zoneCal.get(Calendar.MINUTE));
			cal.set(Calendar.SECOND, zoneCal.get(Calendar.SECOND));
			cal.set(Calendar.MILLISECOND, zoneCal.get(Calendar.MILLISECOND));
			logger.info("Timezone in server cal: " + cal.getTimeZone(),"");
			logger.info("Timezone in local store zoneCal: " + zoneCal.getTimeZone(),"");
			logger.info("Exiting StoreLocalTimeServiceDAOImpl.getStoreLocalTime with Timestamp:" + new Timestamp(cal.getTimeInMillis()),"");
			return new Date(cal.getTimeInMillis());
		}
	}
	
	public String getDistrict(String storeNo) throws DJException {	
		logger.info("Inside StoreLocalTimeServiceDAOImpl.getDistrict() :"+ storeNo,"");
		SellUnitDTO sellUnitTbl = sellUnitMap.get(storeNo);		
		if(sellUnitTbl != null){
			return sellUnitTbl.getShcDistrict();
		}else{
			sellUnitTbl = mcpdbdao.getSellUnitRecordByStoreNo(storeNo);	
			if(sellUnitTbl != null){
				sellUnitMap.put(sellUnitTbl.getShcUnitId(), sellUnitTbl	);
			}					
		}	
		logger.info("Exit StoreLocalTimeServiceDAOImpl.getDistrict() :"+ storeNo,"");
		return sellUnitTbl.getShcDistrict();		
	}
	
	public String getRegion(String storeNo) throws DJException {
		logger.info("Inside StoreLocalTimeServiceDAOImpl.getRegion() :"+ storeNo,"");
		SellUnitDTO sellUnitTbl = sellUnitMap.get(storeNo);		
		if(sellUnitTbl != null){
			return sellUnitTbl.getShcRegion();
		}else{
			sellUnitTbl = mcpdbdao.getSellUnitRecordByStoreNo(storeNo);	
			if(sellUnitTbl != null){
				sellUnitMap.put(sellUnitTbl.getShcUnitId(), sellUnitTbl	);
			}					
		}
		logger.info("EXit StoreLocalTimeServiceDAOImpl.getRegion() :"+ storeNo,"");
		return sellUnitTbl.getShcRegion();	
	}

	
	public Timestamp getstoreLocalTimeStamp(String storeNumber)	throws DJException {
		logger.info("Inside StoreLocalTimeServiceDAOImpl.getstoreLocalTimeStamp() of store number :"+storeNumber,"");
		java.util.Date date = new java.util.Date();	
		Timestamp currentTimeStamp = new Timestamp(date.getTime());
		if(storeNumber != null)
		{
		Date currentDate = getStoreLocalTime(storeNumber);
		currentTimeStamp = new Timestamp(currentDate.getTime());
		}
		logger.info("Inside StoreLocalTimeServiceDAOImpl.getstoreLocalTimeStamp() currentTimeStamp :"+currentTimeStamp,"");
		return currentTimeStamp;
	}
}
