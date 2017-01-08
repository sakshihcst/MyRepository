package com.searshc.mpuwebservice.dao;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.dao.DataAccessException;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.mpu.bean.DDMeta;
import com.sears.dj.common.mpu.exception.DDRMetaException;
import com.sears.dj.common.print.vo.LockerKioskPrintVO;
import com.sears.dj.common.print.vo.MPUItemVO;
import com.sears.dj.common.print.vo.PrintKoiskVO;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.StoreInfo;
import com.searshc.mpuwebservice.bean.ActivityDTO;
import com.searshc.mpuwebservice.bean.CustomerDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PackageDTO;
import com.searshc.mpuwebservice.bean.PaymentDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.TaskDTO;
import com.searshc.mpuwebservice.vo.PrintServiceResponse;



	public interface MPUWebServiceDAO {
	/**
	 * This method is used for accessing Action status Meta data
	 * 
	 * @return Map<String,Object>
	 * @throws DJException
	 */
	 Map<String, Object> getActionStatusMeta(String strNum) throws DJException;

	/**
	 * This method is used of Fetching Store Details
	 * 
	 * @param String
	 *            storeNum
	 * @return Map<String,Object>
	 */
	 Map<String, Object> getStoreDetails(String storeNum)
			throws DJException;

	/**
	 * This method is used for calculating Escalation Time
	 * 
	 * @param String
	 *            strNum
	 * @return Integer
	 */
	 Integer getEscalationTime(String strNum) throws DJException;


	/**
	 * Developed by - ramesh
	 * 
	 * @param order
	 * @return
	 * @throws DJException
	 */
	 long createOrderDTO(OrderDTO order) throws DJException;

	/**
	 * Developed by - ramesh
	 * 
	 * @param customer
	 * @return
	 * @throws DJException
	 */
	 int createIdentifierDTO(CustomerDTO customer, long rqtId,List<PaymentDTO> paymentList,String salesCheck)
			throws DJException;

	/**
	 * Developed by - ramesh
	 * 
	 * @param itemList
	 * @return
	 * @throws DJException
	 */
	 int createItemList(ItemDTO item, long rqtId,String expireTime) throws DJException;
	
	
	/**
	 * @param itemDTOs
	 * @param rqtId
	 * @param expireTime
	 * @return
	 * @throws DJException
	 * @throws DDRMetaException 
	 * @throws DataAccessException 
	 * @throws ParseException 
	 */
	 Object[] createItemList(List<ItemDTO> itemDTOs, long rqtId,String expireTime,
			 String storeNumber,String requestType,
			 boolean isLockerEligible,String createTime, String orderSource,String... notInMpu ) throws  DJException, DataAccessException, DDRMetaException;

	/**
	 * Developed by - ramesh
	 * 
	 * @param paymentList
	 * @return
	 * @throws DJException
	 */
	 int createPaymentList(PaymentDTO payment, long rqtId)
			throws DJException;
    
	
	/**
	 * @param paymentDTOs
	 * @param rqtId
	 * @param storeNumber
	 * @return
	 * @throws DJException
	 */
	 int createPaymentList(List<PaymentDTO> paymentDTOs, long rqtId,String storeNumber) throws DJException;
	
	/**
	 * Developed by - ramesh
	 * 
	 * @param task
	 * @return
	 * @throws DJException
	 */
	 int createTaskDTO(TaskDTO task, String taskType) throws DJException;

	/**
	 * Developed by - ramesh
	 * 
	 * @param task
	 * @return
	 * @throws DJException
	 */
	 int createActivity(ActivityDTO activity, long rqtId, long rqdId)
			throws DJException;

	/**
	 * @param activityDTOs
	 * @param rqtId
	 * @param rqdId
	 * @param storeNumber
	 * @return
	 * @throws DJException
	 */
	 int createActivity(List<ActivityDTO> activityDTOs, long rqtId, Object [] rqdId,String storeNumber)
			throws DJException;
	
	/**
	 * This method is used for fetching stock location
	 * @param requestDTO
	 * @param rqt_id
	 * @return List<ItemDTO>
	 * @throws DJException
	 */
	 List<ItemDTO> getStockLocator(RequestDTO requestDTO, long rqtid) throws DJException;
	
	/**
	 * This method is used for fetching the rqd_id from the request_detail table
	 * @param actionDTO
	 * @param requestNumber
	 * @param statusList
	 * @return List<ItemDTO>
	 * @throws DJException
	 */
	//public int getRequestDetailId(String requestNum,ActionDTO action,List<String> status) throws DJException;
	
	/**
	 * This method is used for updating the status and the assigned user of the item in the details table
	 * @param actionDTO
	 * @param requestNumber
	 * @param statusList
	 * @return List<ItemDTO>
	 * @throws DJException
	 */
	 int updateItemDetails(String requestNum,ItemDTO action,String activity) throws DJException;
	
	/**
	 * This method is used to get the rqt_id from request_trans table
	 * @param requestNumber
	 * @param storeNumber
	 * @return rqt_id
	 * @throws DJException
	 */
	 long checkIsActiveRequestExisting(String storeNumber,String reqNumber) throws DJException;

	/**
	 * This method is used to insert record in the request_activity table
	 * @param rqt_id
	 * @param rqd_id
 	 * @param activity
 	 * @param action
	 * @return void
	 * @throws DJException
	 */
//	public void insertActivityItem(long rqt_id, long rqd_id,ActionDTO action,String activityDesc,String seqNum) throws DJException;

	
	/**
	 * This method is used to update the status of the record in the request_trans table
	 * @param storeNumber
	 * @param requestNumber
 	 * @param previousStatus
 	 * @param newStatus
	 * @return noofRowsUpdated
	 * @throws DJException
	 */
	 int updateOrder(String storeNum,List<String> rqtList,String originalStatus, String orderStatus,String originalJson,Boolean reopenFlag) throws DJException ;

	
	/**
	 * This method is used to get  the stock quantity from request_queue_details
	 * @param storeNumber
	 * @param rqdId
	 * @return ItemQuantity
	 * @throws DJException
	 */
	 Map<String,Object>  getItemStockQuantity(String storeNum,long rqdID) throws DJException;

	
	
	/**
	 * This method is used to get the items in OPEN and WIP status
	 * @param storeNumber
	 * @param requestNumber
	 * @return listOfItems
	 * @throws DJException
	 */
	 Map<String, Object> checkItemStatus(String storeNum,String requestNumber) throws DJException;

	

	/**
	 * @param storeNumber
	 * @param queueType
	 * @param salesCheckNumber
	 * @return
	 * @throws DJException
	 */
	 List<PaymentDTO> getPaymentList(String storeNumber, String salesCheckNumber)throws DJException;
    
	/**
	 * @param requestType
	 * @param currentStatus
	 * @param action
	 * @param type
	 * @param strNum
	 * @return
	 * @Description:This method takes request type,current status and action as input returns next status depending upon MOD 
	 * requirement. type=activity,status,seq,mod_notify
	 * @throws DJException
	 */
	 Object getNextAction(String requestType, String currentStatus,String action,String type,String strNum) throws DJException;
	
	/**
	 * @param actionDTO
	 * @throws DJException
	 */
	 void  decrementItemStock( ItemDTO actionDTO)throws DJException;
	
	/**
	 * @param strNum
	 * @return
	 * @throws DJException
	 */
	 boolean isModActive(String strNum)throws DJException;


	/**
	 * @param storeNumber
	 * @param queueType
	 * @param kiosk
	 * @return
	 * @throws DJException
	 */
	 List<Map<String, Object>> getAllItemList(String storeNumber, String queueType,String kiosk, String isRequestListNonDej,String isUserAssigned)throws DJException;

	/**
	 * @param storeNumber
	 * @param salesCheckNumber
	 * @return
	 * @throws DJException
	 */
	 OrderDTO getOrderDetails(String storeNumber,String salesCheckNumber,List<String> status)throws DJException;


	/**
	 * @param storeNumber
	 * @param salesCheckNumber
	 * @return
	 * @throws DJException
	 */
	 List<ItemDTO> getItemList(String storeNumber, String salesCheckNumber,String itemID,List<String> status,boolean printFlag)throws DJException;
	
	/**
	 * @param storeNumber
	 * @param salesCheckNumber
	 * @return
	 * @throws DJException
	 */
	 CustomerDTO getCustomerData(String storeNumber, String salesCheckNumber)throws DJException;
    
	/**
	 * This method is used for Identifier MetaData
	 * 
	 * @return Map<String,Object>
	 * @throws DJException
	 */
	 Map<String, Object> getIdentifierMeta(String storeNumber) throws DJException;
	
	/**The API updates all the items of an EXPIRED/VOID/NR request
	 * 
	 * @param rqtId
	 * @param expireTime
	 * @param storeNumber
	 * @param rqdIdList
	 * @throws DJException
	 */
	 void updateAllItems(String rqtId,String expireTime,String storeNumber,List<String> rqdIdList) throws DJException;

	 public OrderAdaptorRequest getOriginalJSON(String rqtId, String strNum, Integer transId) throws DJException;
	
	 boolean checkRequestComplete(String strNum,String requestNumber,String requestType) throws DJException;
	
	 boolean checkRequestCancel(String strNum,String requestNumber,String requestType) throws DJException;
	 boolean checkRequestVoid(String strNum,String requestNumber,String requestType) throws DJException;
	 int getRequestDetailId(String requestNumber, ItemDTO action,
			List<String> statusList) throws DJException;
	 int cancelExpireItems(List<String> rqdList,String storeNumber,String status) throws DJException;


	
	/**
	 * This method gives the map containing requesttype and queue name 
	 * @return
	 * @throws DJException
	 */
	 Map<String,String> getRequestQueueMeta(String storeNum) throws DJException;
	 int insertBulkActivities(List<String> rqtList,List<String> rqdList,HashMap<String,String> mapping,String actionSeq,String activity,String strNum,HashMap<String,String> requestType) throws DJException;
	
	/**
	 * This method clears the Metadata stored in cache
	 */
	 void clearMetadata();
	
	
	 List<Map<String,Object>> checkValidOrder(RequestDTO requestDTO) throws DJException;
	
	 int assignUser(String store,String rqdId,String user, String requestType,String rqtId, String searsSalesId) throws DJException;	
	 PrintServiceResponse printLabel(MPUItemVO mpuItemVO,String url) throws DJException ;
	
	//Starts 677829
	/**
	 * This method is used to insert records in the request_package table
	 * @param rqt_id
	 * @param user
 	 * @param storeNumber
 	 * @param fromLocation
	 * @param toLocation
	 * @return noOfRowsAffected
	 * @throws DJException
	 */
	 int insertPackageDetails(long rqtId, String user, String storeNumber, String fromLocation, String[] pkgNbr) throws DJException;

	/**
	 * This method is used to update the to_location field value in the request_package table
	 * @param packageNumber
	 * @param binNumber
	 * @return noOfRowsUpdated
	 * @throws DJException
	 */
	int updatePackageDetails(String packageNumber, String binNumber, String storeNumber) throws DJException ;
	
	//Added for Direct2MPU
	/**
	 * This method is used to update the escalation field value in the request_queue_details table
	 * @param rqd_id
	 * @param escalation
	 * @return time
	 * @throws DJException
	 */
	int updateEscalation(String storeNumber,String rqd_id, int escalation, String time) throws DJException ;

	/**
	 * @param storeNumber
	 * @param requestId
	 * @return List<PackageDTO>
	 * @throws DJException
	 */
	 List<PackageDTO> getPackageList(String storeNumber, String requestId) throws DJException;
	
	//Ends 677829
	/**
	 * @param storeNumber
	 * @param requestId
	 * @throws DJException*/
	 Map<String,Object> checkExpiredOrder(String rqtId,String storeNo) throws DJException;

	int updateNotDeliver(String storeNumber, String rqtId, String rqdId, String qty) throws DJException;

	int assignMPUUser(String storeNumber, String rqtId, String assignedUser,List<String> itemIds) throws DJException;
	
	int updateCurbsideOrder(String storeNumber,String rqtId,String requestType,String requestStatus) throws DJException;
	
		 /*
	  * service to get all DDR meta
	  */
	 public Map<String,String> getHostServers() throws DJException;
	 
	 List<ItemDTO> getItemListForOrder(String storeNumber, String requestId)throws DJException;
	
	public Map<String,Object> getRequestIdbySalescheck(String store,String salescheck) throws DJException;	
	
	public boolean isUserActive(ItemDTO itemDTO) throws DJException;
		 
	 /**Service the item quantity.Used for partial cancellation
	  * @param store
	  * @param rqdId
	  * @param qty
	  * @return
	  */
	 
	 public int cancelExpireRequestItems(String store,String reqNum,String status) throws DJException;
	 
	 public int cancelExpireRequest(String store,long rqt_id,String status ) throws DJException;
	 
	 public long getOpenRequestId(String store, String requestNum, List<String> status) throws DJException;
	 
	 public void cancelRestockItemsOfRequest(String store, List<String> rqdList) throws DJException;
	
	 PrintServiceResponse printLockerTicket(LockerKioskPrintVO lockerKioskPrintVO, String url) throws DJException;

	 String printCouponBySBO(PrintKoiskVO printKoiskVO, String url) throws DJException;

	 String printTICouponByAdaptor(OrderAdaptorRequest orderAdaptorRequest, String url) throws DJException;

	 String printLockerTicketByAdaptor(OrderAdaptorRequest orderAdaptorRequest, String url) throws DJException;

	public List<ItemDTO> getOrderItemList(String storeNumber,String requestId)throws DJException;
	/**
	 * 
	 * @param storeNum
	 * @param requestId
	 * @return
	 * @throws DJException
	 */
	public OrderDTO getAllOrderDetails(String storeNumber, String requestId)throws DJException ;
	
		 /**Service the item quantity.Used for partial cancellation
	  * @param store
	  * @param rqdId
	  * @param qty
	  * @return
	  */
	 
	 public int updateItemQty(String store,String rqdId,String qty,String status,String source,String dest,String createTime,String user) throws DJException;

	 /**
		 * @param store
		 * @param date
	 * @param status 
		 * @return List<Object[]>
		 * @throws DJException
		 */
		public List<Object[]> getCOMExceptionList(String store,String date, String status) throws DJException;

	/**
	 * @param rqdIds
	 * @param user
	 * @param store
	 * @return
	 * @throws DJException
	 */
	int updateItemDetailForCOM(String[] rqdIds, String user, String store) throws DJException;

	/**
	 * @param rqtIds
	 * @param store
	 * @return
	 * @throws DJException
	 */
	int updateOrderDetailForCOM(String[] rqtIds, String store)throws DJException;
	
		public int getItemQty(String store,String itemId)throws DJException;
	public boolean checkMultipleItems(String strNum,String rqtId,String identifier) throws DJException;

	/** This code is used to set the rqt_id null for already existing rqt_id in package table
	 * @param rqtId
	 * @param storeNo
	 * @throws DJException
	 */
	void clearPreviousPackage(String rqtId, String storeNo) throws DJException;
	/*
	 * get the associateID from the activity table for the given itemid
	 */
	public String getAssociateId(String store,String rqtId,String rqdID) throws DJException;
	/**
	 * 
	 * @param storeNum
	 * @param requestId
	 * @return
	 * @throws DJException
	 */
	public CustomerDTO getCustomerDataForPrint(String storeNum,String requestId) throws DJException;
	
	public Map<String,String> checkStatus(String storeId,String reqNum) throws DJException;

	PrintServiceResponse beepToPrinter(String url) throws DJException;
	public boolean isPickedUp(String storeNumber, String requestId, String itemId) throws DJException;
	public int isCSMMissed(String storeNumber, String rqtId, String rqdId)	throws DJException ;
	public int isLayaway(String store, String salesCheck) throws DJException;
	public int getActiveItemCount(String store, String rqtId) throws DJException;

	int updateCardSwipedFlagInBlob(String rqtId, String storeNumber,
			String originalJson) throws DJException;
	public String isPlaformStore(String storeNumber);

	public List<DDMeta> getDDRMetaCache();
	public boolean refreshDDRMetaCache();
	public String getAppServer(String storeNumber) throws DJException;
	public String getAppServerPlatform(String storeNumber) throws DJException;
	
	public boolean gethealthCheck() throws DJException ;

	/**
	 * 
	 * @param storeNumber
	 * @return
	 * @throws DJException
	 */
	public StoreInfo getStoreInformation(String storeNumber) throws DJException;
	public List<Map<String,Object>> getKioskDetailList(String storeNumber) throws DJException;
	public List<ItemDTO> getAllItemsForPostVoid(String store,String rqtId) throws DJException;
	
	public String getRqtIdForPostVoid(String store,String salescheck) throws DJException;

	public int updateDtmFlag() throws DJException;
	public int updateOrderList(String storeNumber,List<String> rqtList,String orderStatus,String originalJson,Boolean reopenFlag) throws DJException ;

	
	public List<String> getRqtIdList(String storeNum,List<String> salescheckList)throws DJException;
//	void updateHFMbin() throws DJException;
	
	public int updateEsacaltionList(String store,List<ItemDTO> escalationUpdatedItemList) throws DJException;
	
	public int updateFinalResponseInDb(Order finalOrderResponse,String rqtId,String storeNum) throws DJException, JsonGenerationException, JsonMappingException, IOException;
	
	public int setResponseStatusFailed(String storeNum,String orderNumber) throws DJException;
	}