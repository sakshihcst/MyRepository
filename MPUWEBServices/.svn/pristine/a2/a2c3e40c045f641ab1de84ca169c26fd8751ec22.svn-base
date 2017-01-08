/**
 * 
 */
package com.searshc.mpuwebservice.dao;

import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.LockerDTO;

/**
 * @author ssingh6
 *
 */
public interface LockerServiceDAO {
	/**
	 * 
	 * @param lockerDTO
	 * @return
	 * @throws DJException
	 * @author ssingh6
	 */
	public boolean insertLockerDetail(LockerDTO lockerDTO)  throws DJException;
	
	/**
	 * 
	 * @param lockerDTO
	 * @return
	 * @throws DJException
	 * @author ssingh6
	 */
	public int updateLockerPin(LockerDTO lockerDTO)  throws DJException;
	
	/**
	 * 	
	 * @param salescheckNum
	 * @param storeNumber
	 * @param transactionDate
	 * @return pinNum
	 * @throws DJException
	 * @author gasolek
	 */
	public LockerDTO getPinNumberFromSalescheck(String salescheckNum,String storeNumber,String transactionDate) throws DJException;

	/**
	 * This method updates the locker order status in mpu_locker table as per passed lockerDTO object
	 * @param lockerDTO LockerDTO
	 * @return int
	 * @throws DJException
	 */
	public int updateLockerOrderStatus(LockerDTO lockerDTO) throws DJException;
	
	public Boolean isOrderKeptInLocker(Integer reqID, String storeNumber) throws DJException;

	/**
	 * This method returns the locker report data
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @param storeNumber String
	 * @return List<LockerDTO>
	 * @throws DJException
	 */

	public List<LockerDTO> getLockerReport(String reportFromDate, String reportToDate,String storeNumber) throws DJException;
	
}
