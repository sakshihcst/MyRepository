package com.searshc.mpuwebservice.processor;

import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.ActivityReportDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDTO;
import com.searshc.mpuwebservice.bean.OHMDetailDTO;
import com.searshc.mpuwebservice.bean.WebActitivtyDTO;
import com.searshc.mpuwebservice.bean.WebResponseDTO;

public interface AssociateActivityServicesProcessor {
		
	/**
	 * This API will return all the web activity report data present in the Database
	 * @param reportDate String
	 * @return List<WebResponseDTO>
	 * @throws DJException
	 */
	public List<WebResponseDTO> fetchWebActivityRecordsForDate(String reportDate) throws DJException;
	
	/**
	 * This API will insert all the webActivity Records into web_response_associate_activity_report table
	 * @param webActitivtyDTOs List<WebActitivtyDTO> 
	 * @return int
	 * @throws DJException
	 */
	public int insertWebActivityRecords(List<WebActitivtyDTO> webActitivtyDTOs) throws DJException;
	
	/**
	 * This API will insert all the activities done by associate at MPU queue into database 
	 * @param mpuActivityDTOs List<MPUActivityDTO>
	 * @return 
	 * @throws DJException
	 */
	public List<Integer> insertMPUActivityData(List<MPUActivityDTO> mpuActivityDTOs) throws DJException;
	
	/**
	 * This API validates the input passed,according to that it will send the validation message 
	 * @param webActitivtyDTOs List<WebActitivtyDTO>
	 * @return String
	 * @throws DJException
	 */
	public String validateWebActivityInsertInputs(List<WebActitivtyDTO> webActitivtyDTOs) throws DJException;
	
	/**
	 * This API validates the passed input
	 * @param mpuActivityDTOs List<MPUActivityDTO>
	 * @return String
	 * @throws DJException
	 */
	public String validateMPUActivityInsertInputs(List<MPUActivityDTO> mpuActivityDTOs) throws DJException;

	/**
	 * This API returns to display the order and customer details in OHM at store 
	 * @param storeNumber String
	 * @param date String
	 * @param kioskName String
	 * @return List<OHMDetailDTO>
	 * @throws DJException
	 */
	public List<OHMDetailDTO> fetchOHMData(String storeNumber, String date, String kioskName) throws DJException;

	/**
	 * This method updates the MPU request when any action taken by associate at MPU queue
	 * @param mpuActivityDTO MPUActivityDTO
	 * @return int
	 * @throws DJException
	 */
	public int updateMPURequest(MPUActivityDTO mpuActivityDTO)throws DJException;

	/**
	 * This method retrieves activities done by all associates those who are using MPU Application as per passed date
	 * @param reportDate String
	 * @return List<ActivityReportDTO>
	 * @throws DJException
	 */
	public List<ActivityReportDTO> fetchMPUActivityRecordsForDate(String reportDate) throws DJException;
	/*get username against user id
	 * */
	
	public String getUserName(String userId);
	

}
