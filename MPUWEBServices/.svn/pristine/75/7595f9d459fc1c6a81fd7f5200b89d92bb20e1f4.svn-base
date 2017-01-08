package com.searshc.mpuwebservice.processor;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.mpu.exception.DDRMetaException;
import com.searshc.mpuwebservice.bean.CustomerDetailDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDTO;
import com.searshc.mpuwebservice.bean.MPUAssociateReportDTO;
import com.searshc.mpuwebservice.bean.PickUpDTO;
import com.searshc.mpuwebservice.bean.PickUpReturnDTO;
import com.searshc.mpuwebservice.bean.PickUpSelectedItems;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.SalesCheckDetails;
import com.searshc.mpuwebservice.bean.ShopinRequestDTO;
import com.searshc.mpuwebservice.bean.ShopinServiceResponse;

public interface PickUpServiceProcessor {
	
//public List<ItemDTO> getAllItemsForCustomer(CustomerDTO customerDTO,String typeOfIdentification, String salesCheckNumber) throws DJException, ParseException;

/**This method is used to get list of kiosk
	 * @param storeNum
	 * @throws DJException
	 */
public List<String> getKioskList(String storeNum) throws DJException;	
	
/**This method is used to create item for MPU Return Request
 * @param requestDTO
 * @throws DJException
 * @throws DDRMetaException 
 * @throws DataAccessException 
 */
public void addItemInReturn(RequestDTO requestDTO) throws DJException, DataAccessException, DDRMetaException;

/**This method is used to assign MPU request to a user
 * @param requestDTO
 * @throws DJException
 */
public void assignMpuRequest(RequestDTO requestDTO) throws DJException;

/**This method is used to update item for MPU Return Request
 * @param requestDTO
 * @return
 * @throws DJException
 */
public void updateMpuReturnItemList(RequestDTO requestDTO) throws DJException;

public void updateMODNotificationPickupReturn(PickUpSelectedItems obj) throws DJException;

public String completeRequest(List<RequestDTO> requestDTOList) throws DJException;

public void orderHelpRepair(CustomerDetailDTO customerDetailDTO) throws DJException;

public List<RequestDTO> fetchOderForAssociate(String storeNumber, String kioskName, String transId) throws DJException;

public String getAssociateName(String associateID) throws DJException;

//public void updateCurbsideItems(List<RequestDTO> requestDTOs) throws Exception;
public PickUpReturnDTO getAllItemsForPickUp(String pickUpLoc,String numType, String step, String identifierValue, PickUpDTO pickUpDTO)  throws DJException;

public void createReturnKiosk(List<RequestDTO> requestDTOs) throws DJException, DataAccessException, DDRMetaException;

public PickUpReturnDTO getAllItemsForReturn(String numType,String step, String identifierValue, PickUpDTO pickUpDTO)  throws DJException;

public List<String> initiatePickUpForItemsAndGetLockerItems(PickUpSelectedItems obj) throws DJException;

public List<MPUActivityDTO> createActivityDTO(PickUpSelectedItems obj, List<String> kioskLockerItems, List<RequestDTO> requestDTOListTemp) throws DJException;;

public void cancelReturnin5(RequestDTO requestDTO) throws DJException;

public String getTimeAccToTimeZone(String dateTime, String storNo, String dateFormat) throws DJException;

/*
public String getActiveUserForMOD(String storeNo) throws DJException;*/

public String getPropertyFromAdaptor(String storeNum, String propertyName,String storeFormat) throws DJException;

public String updateStatusToNPOS(String storeNumber, String rqtId, String reqType, String custSign, String reqUrl, Integer transId, String originalIdentifier,
		 String storeFormat, String startTime, String endTime, String kioskName) throws DJException;

/**This code is to notify mod of a coupon Bypass
 * @param storeNo
 * @param itemDTO
 * @return
 * @throws DJException
 */
public String notifyModForCouponBypass(String storeNo, ItemDTO itemDTO)throws DJException;

/**This code is to notify mod of a Signature override
 * @param storeNo
 * @param itemDTO
 * @return
 * @throws DJException
 */
public String notifyModForSignOverride(String storeNo, ItemDTO itemDTO)throws DJException;

public SalesCheckDetails getVehicleInformation(PickUpReturnDTO pickUpItems,String salesCheckno) throws DJException;

public ShopinServiceResponse initiatePickupForShopin(ShopinRequestDTO shopinRequestDTO) throws DJException;

public void insertNotDeliverReasonInDEJ(String storeNumber, String userName, String itemId, String salescheck) throws DJException;

/**   
 * To Clear the cache for Pick up queue
 * @param storeNum
 * @param kioskName
 * @param refreshAll
 * @return
 * @throws DJException
 */
public boolean clearCache(String storeNum,String kioskName,boolean refreshAll)throws DJException;

public List<RequestDTO> getCachedContent(String storeNumber,String kiosk);
public String getExpiredTime(String dateTime, String escalation, String currDateAccToStore) ;

public String initiatePickUpForItems(PickUpSelectedItems obj) throws DJException;


public void insertIntoMPUAssociateReport(MPUAssociateReportDTO associateReport) throws DJException;

public void updateDivOperation(List<String> rqdIdList, String storeNum) throws DJException;

/**
 * @description Insert POS Message in to MPU queue
 * @param RequestDTO requestDTO
 * @return void
 * @throws DJException
 */
public void orderPosMessage(RequestDTO requestDTO)	throws DJException;

//Exchange related API
public int getFilteredEI5List(List<RequestDTO> lRequestDTO) throws DJException;
public List<RequestDTO> getAllRequestsFromEhCache(String storeNumber, String requestType, String kiosk, String initiatedFrom, String numVal, String numType) throws DJException ;
public Boolean addRequestToEhCache(List<?> lRequestDTO, String storeNo, String kiosk, String reqType, String initiatedFrom, String numVal, String numType) throws Exception;

/** The service to verify if the store 
 * has repair pickup/dropoff option
 * @param storeNum String
 * @return ResponseEntity<ResponseDTO>
 */
public boolean isRepairEnabled(String storeNum)	throws DJException;

public PickUpDTO getPickUpDTO(String salesCheckno, String storeNo, String storeFormat, String notificationId, String sywrId) throws DJException;

}