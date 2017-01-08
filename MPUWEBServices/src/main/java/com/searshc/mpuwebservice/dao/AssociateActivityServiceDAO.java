package com.searshc.mpuwebservice.dao;

import java.util.List;
import java.util.Map;

import com.sears.dej.interfaces.vo.UserVO;
import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.MPUActivityDetailDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDTO;
import com.searshc.mpuwebservice.bean.OHMDetailDTO;
import com.searshc.mpuwebservice.bean.WebActitivtyDTO;
import com.searshc.mpuwebservice.bean.WebResponseDTO;


public interface AssociateActivityServiceDAO {
	
	/**
	 * This API will return all the web activity report data present in the Database
	 * @param reportDate String
	 * @return List<WebResponseDTO>
	 * @throws DJException
	 */
	public List<WebResponseDTO> fetchWebActivityRecordsForDate(String reportDate)throws DJException;
	
	/**
	 * This API will insert all the webActivity Records into web_response_associate_activity_report table
	 * @param webActitivtyDTOs List<WebActitivtyDTO> 
	 * @return int
	 * @throws DJException
	 */
	public int insertWebActivityRecords(List<WebActitivtyDTO> webActitivtyDTOs) throws DJException;
	
	
	/**
	 * This API designed to insert the Order details when Pickup is initiated by customer
	 * @param mpuActivityDTO MPUActivityDTO
	 * @return int
	 * @throws DJException
	 */
	public int insertMPUActivityData(MPUActivityDTO mpuActivityDTO) throws DJException;
	
	/**
	 * This API functionality is insert the item details i.e.(for how many items or quantity the customer initiated the pickup) from an order
	 * @param mpuActivityDetailDTOs List<MPUActivityDetailDTO>
	 * @return int
	 * @throws DJException
	 */
	public int insertMPUActivityDetail(List<MPUActivityDetailDTO> mpuActivityDetailDTOs) throws DJException;
	
	/**
	 * This API designed to return the customer and customer order details in OHM at store
	 * @param storeNumber String
	 * @param date String
	 * @param kioskName String
	 * @return List<OHMDetailDTO>
	 * @throws DJException
	 */
	public List<OHMDetailDTO> fetchOHMData(String storeNumber, String date, String kioskName) throws DJException;

	/**
	 * This API designed to update the MPU request when any action taken by associate at MPU 
	 * @param mpuActivityDTO MPUActivityDTO
	 * @return int
	 * @throws DJException
	 */
	public int updateMPURequest(MPUActivityDTO mpuActivityDTO) throws DJException;
	
	/**
	 * This API designed to update the item details(i.e. for how many items or quantities the customer picked up at MPU) from an order 
	 * @param mpuActivityDetailDTOs List<MPUActivityDetailDTO>
	 * @return int
	 * @throws DJException
	 */
	public int updateMPUDetailRequest(List<MPUActivityDetailDTO> mpuActivityDetailDTOs)throws DJException ;
	
	/**
	 * This API is designed for retrieving returns done by all associates from all schemas
	 * @param reportDate String
	 * @return List<Map<String, Object>>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getTotalTenderReturnsByAssociate(String reportDate) throws DJException;
	
	/**
	 * This API is designed for retrieving total exchanges done by store from all schemas
	 * @param reportDate String
	 * @return List<Map<String, Object>>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getTotalExchanges(String reportDate) throws DJException;
	
	/**
	 * This API is designed for retrieving total exchanges summary done by store from all schemas
	 * @param reportDate String
	 * @return List<Map<String, Object>>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getTotalExchangeCouponsSummary(String reportDate) throws DJException;
	
	/**
	 * This API is designed for retrieving total returns done by store from all schemas
	 * @param reportDate String
	 * @return List<Map<String, Object>>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getTotalTenderReturnsByStore(String reportDate) throws DJException;
	
	/**
	 * This API is designed for retrieving total returns cancelled by store from all schemas
	 * @param reportDate String
	 * @return List<Map<String, Object>>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getTotalTenderStoreReturnsCancelled(String reportDate) throws DJException;
	
	/**
	 * This API is designed for retrieving total coupons generated during pickup by associate at store from all schemas
	 * @param reportDate String
	 * @return List<Map<String, Object>>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getAllCouponsForPickup(String reportDate) throws DJException;
	
	/**
	 * This API is designed for retrieving total pickup information by store from all schemas
	 * @param reportDate String
	 * @return List<Map<String, Object>>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getAllPickUpOrders(String reportDate) throws DJException;
	
	/**
	 * This API is designed for retrieving total help requests placed in store from all schemas
	 * @param reportDate String
	 * @return List<Map<String, Object>>
	 * @throws DJException
	 */
	public List<Map<String, Object>> getAllHelpRequests(String reportDate) throws DJException;

	public UserVO getAssociateInfo(String associateId);
	
}
