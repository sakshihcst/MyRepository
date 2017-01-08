/**
 * 
 */
package com.searshc.mpuwebservice.dao;

import java.util.List;
import java.util.Map;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.ActivityUserEntity;
import com.searshc.mpuwebservice.bean.MpuStaticParamEntity;
import com.searshc.mpuwebservice.bean.SellUnitDTO;
import com.searshc.mpuwebservice.bean.ShopInReportDTO;

/**
 * @author 547460
 *
 */
public interface MCPDBDAO {
	
	public List<Map<String,Object>> getModActiveEntity(String storeNo,Integer mod) throws DJException;
	
	public int insertUser(ActivityUserEntity activeUser) throws DJException;
	
	public List<Map<String,Object>> getKioskDetailList(final String storeNo)throws DJException;
	
	public List<Map<String, Object>> getKioskList(String storeNum) throws DJException;	
	
	public ActivityUserEntity getActiveUserForUserId(String userId,String storeNumber) throws DJException;

	public int updateActiveUserFlag(String userId, String loggedInTime, String activeUserFlag) throws DJException;
	
	public int updateModFlag(String userId, String loggedInTime, boolean modFlag) throws DJException;
	
	public int updateLoggedOutTime(String userId, String loggedInTime, String loggedOutTime) throws DJException;
	
	public int getMaxShopInPickupId () throws DJException;
	
	public int insertShopinReportRecords(List<ShopInReportDTO>ShopInReportDTOList, String storeNum) throws DJException;
	
	public int updateShopinReportRecord(ShopInReportDTO shopInReportDTO) throws DJException;
	
	public List<SellUnitDTO> loadRecordsFromSellUnitTable() throws DJException;
	
	public SellUnitDTO getSellUnitRecordByStoreNo(String storeNumber) throws DJException;
	
	public List<Map<String, Object>> getIsLockerEligibleFlag(String storeNumber, String kioskName) throws DJException;
	
	public List<ShopInReportDTO> fetchRecordsForShopinReport(String dateFrom, String dateTo, String storeNo, String region, String district) throws DJException;
	
	/**This code is used to get the number of users that are active for a particular store number 
	 * @param storeNumber
	 * @return
	 * @throws DJException
	 */
	public int getActiveUserCount(String storeNumber) throws DJException;
	
	public List<ActivityUserEntity> getAllModActiveUsers() throws Exception;
	
	public List<MpuStaticParamEntity> getCSMLoggingOffStoreList() throws Exception;
	
	public int updateActiveUsers(List<ActivityUserEntity> activeUserToUpdateList) throws Exception;

	/** The service to verify if the store 
	 * has repair pickup/dropoff option
	 * @param storeNum String
	 * @return ResponseEntity<ResponseDTO>
	 */
	public boolean isRepairEnabled(String storeNum)	throws DJException;
}
