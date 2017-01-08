package com.searshc.mpuwebservice.processor;

import java.text.ParseException;
import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.LockerDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;

/**
 * 
 * @author ssingh6
 *
 */
public interface LockerServiceProcessor {
	
	
	/**
	 * 
	 * @param lockerDTO
	 * @return
	 * @throws DJException
	 * @throws ParseException
	 * @author ssingh6
	 */
	public boolean addLocker(LockerDTO lockerDTO)throws DJException;
	
	/**
	 * 	
	 * @param lockerDTO
	 * @return
	 * @throws DJException
	 * @author ssingh6
	 * @throws Exception 
	 */
	public int updateLocker(LockerDTO lockerDTO) throws DJException;
	
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
	 * This method returns lockerFlag as true or false according to passed order
	 * @param requestDTO RequestDTO
	 * @return boolean
	 * @throws DJException
	 */
	public boolean isOrderEligibleForLocker(RequestDTO requestDTO) throws DJException;
	
	
	/**
	 * This method updated the locker order Status as per passed lockerDTO
	 * @param lockerDTO LockerDTO
	 * @return int
	 */
	public int updateLockerOrderStatus(LockerDTO lockerDTO) throws DJException;
	
	public Boolean isOrderKeptInLocker(Integer reqID,String storeNumber) throws DJException;

	/**
	 * This method returns the locker report details
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @param storeNumber String
	 * @return List<LockerDTO>
	 * @throws DJException
	 */
	public List<LockerDTO> getLockerReport(String reportFromDate, String reportToDate,String storeNumber) throws DJException;

}
