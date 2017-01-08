package com.searshc.mpuwebservice.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sears.dej.interfaces.vo.ItemMeta;
import com.sears.dj.common.exception.DJException;
import com.sears.mpu.backoffice.domain.Customer;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderItem;
import com.searshc.mpuwebservice.bean.CustomerDetailDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.MPUAssociateReportDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PackageDTO;
import com.searshc.mpuwebservice.bean.PaymentDTO;
import com.searshc.mpuwebservice.bean.PickUpDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ShopinRequestDTO;
import com.searshc.mpuwebservice.bean.VehicleInfo;

public interface PickUpServiceDAO {
	
//	public List<Map<String, Object>> getAllItemsForCustomer(CustomerDTO customerDTO, String typeOfIdentification, String salesCheckNumber) throws DJException;

/*	*//**This method is used to get list of kiosk
	 * @param storeNum
	 * @throws DJException
	 *//*
	public List<Map<String, Object>> getKioskList(String storeNum) throws Exception;	*/
	
	public ArrayList<Order> getOrdersFromNPOS(PickUpDTO pickUpDTO, String numType, String identifierValue, String reqtype) throws DJException;

	public RequestDTO nPosCallForHelpRepair(CustomerDetailDTO customerDetailDTO) throws DJException;
	
	public List<OrderDTO> fetchOderForAssociate(String storeNumber, String kioskName) throws DJException;
	
	public List<ItemDTO> fetchItemForRqtid(String storeNumber, OrderDTO obj) throws DJException;

	public List<ItemDTO> getAllItemsForOrder(List<OrderDTO> listOrderDTO,PickUpDTO pickUpDTO) throws DJException;

	public List<PaymentDTO> getAllPaymentForOrder(List<OrderDTO> listOrderDTO,String storeNumber) throws DJException;

	public List<Map<String, Object>> getParentRqtId(String salesCheck,String storeNo,String customerId, String orderDate) throws DJException;
	
	public List<Map<String, Object>> getParentRqdId(String salesCheck,String storeNo,String div,String item, String sku,String itemSeq,String rqtId) throws DJException;
	
	public  List<Map<String, Object>>  checkPreviousRI5Progress(String storeNo, String returnAuthCode, String requestType, String requestStatus) throws DJException;

	public  Integer  cancelReturnin5(int rqtId,String requestStatus, String storeNo) throws DJException;

	public Integer initiatePickUpForItems(List<ItemDTO> itemList, String storeNum, String kioskName) throws DJException;
	
	public Integer initiatePickUpActivityForItems(List<ItemDTO> itemList, String storeNum,String nextStatus) throws DJException;

	public Integer updateActivityDetailDTO(String storeNumber, List<ItemDTO> items, String dateTime) throws DJException;

	public Integer updateActivityDTO(String storeNo, String rqtId, String transId, String dateTime,String requestType, String assignedUser, String searsSalesId)  throws DJException;

	public Integer completeItemDetails(ItemDTO item) throws DJException;

	public Integer completeOrderDetails(String storeNo, String rqtId, String dateTime) throws DJException;

	public Integer checkForQtyRemainingAndOpenItems(ItemDTO item) throws DJException;

	public Integer checkForOpenItems(ItemDTO item) throws DJException;

	//public List<ItemDTO> getAllItemsForPickUp(PickUpDTO pickUpDTO, String numType, String identifierValue) throws DJException;

	public Integer updateMpuItemDetails(ItemDTO action) throws DJException;
	public List<OrderItem> getOrderforNPOSMPU(int transId, String storeNo) throws DJException;
	
	public void assignUser(String storeNumber,	int transId, String associateId, String rqtId,String searsSalesId) throws DJException;

	public void putRelatedSalecheckForPickUp(String relatedSalesCheckNumber, String storeNumber, Integer transId) throws DJException;

	public List<ItemDTO> getDeliveredQtyAndLdapId(String storeNumber, String rqtId) throws DJException;
	
	/**
	 * @param storeNumber
	 * @param propertyName
	 * @param storeFormat
	 * @return
	 * @throws DJException
	 */
	public String getPropertyFromAdaptor(String storeNumber, String propertyName, String storeFormat) throws DJException;
	
	public boolean checkForEI5Order(String identifierValue,String IdentifierType,String storeNo) throws DJException;
	
	public Order searchTenderReturns(String returnAuthId ,String storeNo, String storeFormat) ;

	public List<OrderDTO> getTransDetail(String referenceId, String storeNo) throws DJException;
	
	public Integer insertVehiclInfo(VehicleInfo vehicleInfo,String notificationId, List<String> rqtIdToBeUpdated,String storeNumber) throws DJException;	

	public void insertNotDeliverReasonInDEJ(HashMap<String, String> notDeliverReasonCodeMap) throws DJException;
	
	public int insertDataForShopinReport(ShopinRequestDTO shopinRequestDTO) throws DJException;
	
	public Order fetchForPostVoid(String salescheck,String store) ;
	public void updateQueueDetails(String store,String status,String origQty,String delQty,String remQty,String rqdId, String location) throws DJException;
	public String updateVoidPickUpToNPOS(OrderAdaptorRequest request,String storeNumber,String storeFormat) throws DJException;

	public int completeLockerQueueOrder(String storeNumber, String rqtId) throws DJException;

	public int completeLockerQueueItem(String storeNumber, String rqtId) throws DJException;

	public int completeLockerMPUTrans(String storeNumber, String transId) throws DJException;

	public int completeLockerMPUDetail(String storeNumber, String transId) throws DJException;
	
	/**
	 * This methods fetches all the items of all the request mentioned in the list given
	 * @param storeNumber
	 * @param obj
	 * @return
	 * @throws DJException
	 */
	public List<ItemDTO> fetchAllItemsForAssociate(String storeNumber, List<String> transIdList) throws DJException;


	public int updateOriginalJson(Map<String, String> originalJsonMap, String storeNum) throws DJException;

	/**This methods fetches all the packages of all the request mentioned in the list given
	 * @param storeNumber
	 * @param transIdList
	 * @return
	 * @throws DJException
	 */
	public List<PackageDTO> getAllPackageInfo(String storeNumber,List<String> transIdList) throws DJException;


	public Integer updateTimeForMPUOrder(String storeNo, String rqtId, String dateTime) throws DJException;
	
		public ItemMeta getDivItem(String upc, String storeNo, String itemIdType) throws DJException;
		/**
		 * This method returns the rqd_id list of items which where never binned/staged
		 * @param itemListToBeUpdated
		 * @return
		 * @author nkhan6
		 */
		public List<String> getOpenItemsFromDb(List<ItemDTO> itemListToBeUpdated) throws DJException;

		public Integer updateSecuredDBFlag(Map<String, Boolean> securedFlagMap, String string) throws DJException;

		//public List<Map<String, Object>> checkForPaymentList(List<String> rqtIds, String storeNum) throws DJException;
		public int deleteAllPaymentInfo(List<String> rqtIdList,String storeNum) throws DJException;

		public int insertIntoMPUAssociateReport(MPUAssociateReportDTO associateReport, String currTime) throws DJException;

		public int updateDivOperation(List<String> rqdIdList, String storeNum) throws DJException;

		public List<Map<String, Object>> checkIfRequestAlreadyCompleted(String transId, String storeNo) throws DJException;

		public List<ItemDTO> getAllItemsForPickUpFromSalescheckList(String customerId, List<String> salescheckList, String storeNumber)  throws DJException;

		public List<Map<String, Object>> getPackageInfoForRqtList(List<String> rqtIdList,String storeNum) throws DJException;

		public int insertPackageInfo(List<PackageDTO> packageInfoList,String storeNum) throws DJException;

		public List<PackageDTO> getPackageInfoFromDB(List<String> salescheckPackageList, String storeNum) throws DJException;
		
		//Exchange related APIs
		//public ArrayList<Order> getOrdersFromEI5NPOS(PickUpDTO pickUpDTO, String numType, String identifierValue, String reqtype) throws DJException;
		
		public boolean checkForRI5Order(String returnAuthCode ,String storeNo) throws DJException;
		
		public int getReturnIn5QueueTrans(String returnAuthCode, String storeNumber) throws DJException;
		
		/**
		 * This method returns Customer information based on EmailID
		 * @param String emailID
		 * @param String storeNo
		 * @param String storeFormat
		 * @return Customer
		 * @author nkumar1
		 */
		public ArrayList<Customer> fetchCustomersfromNPOSByEmailID(String emailID ,String storeNo, String storeFormat) throws DJException;
		
		public String fetchNotificationIdByRqtId(String rqt_id, String storeNumber) throws DJException;
		
		public int pushNotificationToShopInDAO(String xmlStringForPushNotify);

		public List<Map<String, Object>> getMPUTransData(String storeNumber, Integer trans_id) throws DJException;
		
		public int updateUPC(Map<String,String> rqdIdUpcMap,String storeNum) throws DJException;
		
		public String getRetAuthCode(String storeNumber,String rqtId) throws DJException;

		public void updateQueueTrans(String store, String item_Status, String rqtId)throws DJException;
}