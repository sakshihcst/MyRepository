package com.searshc.mpuwebservice.processor;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.dao.DataAccessException;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.mpu.bean.DDMeta;
import com.sears.dj.common.mpu.exception.DDRMetaException;
import com.sears.dj.common.print.vo.TICouponKioskPrintVO;
import com.sears.mpu.backoffice.domain.Order;
import com.searshc.mpuwebservice.bean.CustomerDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PaymentDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.TaskDTO;
import com.searshc.targetinteraction.OrderConfirmResponseDTO;

public interface MPUWebServiceProcessor {
	/**
	 * This API is used for inserting Request data in Order,Identifier,Item,Activity and Payment 
	 * @param OrderDTO order
	 * @param CustomerDTO customer
	 * @param List<ItemDTO> itemList
	 * @param List<PaymentDTO> paymentList
	 * @param TaskDTO task
	 * @return boolean
	 * @throws DJException
	 * @throws DDRMetaException 
	 * @throws DataAccessException 
	 * @throws ParseException 
	 */
	public RequestDTO createRequest(OrderDTO order, CustomerDTO customer,
			List<ItemDTO> itemList, List<PaymentDTO> paymentList, TaskDTO task, String... notInMpu)
					throws DJException, DataAccessException, DDRMetaException;


	/**
	 * This API is used to update the request_queue_trans and request_queue_details table 
	 * Also creates an insert in the request_activity table
	 * @param requestNum
	 * @param itemNum
	 * @param action
	 * @return
	 * @throws DJException
	 */

	public int updateOrderRequest(String requestNum,ItemDTO action) throws DJException;



	/**
	 * This API is used for fetching all Item list
	 * @param storeNumber
	 * @param queueType
	 * @param kiosk
	 * @return
	 * @throws DJException
	 * @throws ParseException 
	 */
	public List<ItemDTO> getAllItemList(String storeNumber, String queueType, String kiosk,String isRequestListNonDej,String isUserAssigned)throws DJException;


	/**
	 * This API is used for fetching all Item list
	 * @param salesCheckNumber
	 * @param storeNumber
	 * @param fields
	 * @return
	 * @throws DJException
	 */
	public RequestDTO getRequestData(String salesCheckNumber,String storeNumber, String fields,String itemID,List<String> status)throws DJException;


	/**This APi reopens the EXPIRED/VOID/EXPIRE requests
	 * 
	 * @param orderDTO
	 * @param itemDTO
	 * @throws DJException
	 */
	public void reopenExpiredOrder(RequestDTO requestDTO,String expireTime) throws DJException;

	/**
	 * This method is used to clear the cache
	 * @return
	 */
	public boolean clearCache(String refreshAll);

	public void createValidOrder(RequestDTO requestDTO) throws DJException, DataAccessException, DDRMetaException;

	/**
	 * This Method returns the store's queue specific hashmap stored in EhCache  
	 * @param storeNumber
	 * @param queueType
	 * @param kiosk
	 * @return
	 * @throws DJException
	 */
	public Map<String,ItemDTO> getAllCachedContent(String storeNumber, String queueType, String kiosk)throws DJException;

	/**The method returns the store details for the given store number
	 * 
	 * @param storeNumber
	 * @return
	 * @throws DJException
	 */
	public Map<String,Object> getstoreDetails(String storeNumber)throws DJException;


	public boolean assignUser(String store,String rqdId,String assignedUser,String requestType,String rqtId, String searsSalesId)throws DJException;

	public List<String> printLabel(String storeNum,String printerId,String rqdId,String type,String user,String queueType,String requestId, boolean reprintFlag)throws DJException;

	public List<String> printPackageLabel(String storeNum,String printerId,String pkgNumbers,String type,String user,String queueType,String requestId)throws DJException;

	public List<String> insertPackageDetails(String userId, String storeNo,
			String rqtId, String numberOfPackages, String fromLocation, String salescheck)throws DJException;


	public String updatePackageDetails(String storeNo, String packageNumber,
			String binNumber)throws DJException;

	public boolean checkOrderExpired(String storeNo,String rqtId) throws DJException;
	public Map<String,String> getHostServers() throws DJException;
	Map<String, String> selectQueuestats(String storeno, String kiosk) throws DJException;

	public boolean addToEhCache(List<ItemDTO> itemList,Object[] rqdId,String storeNumber,String requestType,String fullName);

	public List<ItemDTO> getAllItemFromEhCache(String storeNumber, String queueType,String kiosk,boolean postProcessingFlag) throws DJException;

	public boolean updateVoidRequest(String requestNumber,String store,RequestDTO requestDTO) throws DJException;

	/**API to make the request as complete if the pickup is initiated from the curbside/kiosk
	 *@param requestDTO 
	 */
	boolean updateRequestForPickUp(RequestDTO requestDTO) throws DJException;

	/**
	 * @param store
	 * @param requestNumber
	 * @param status
	 * @return
	 * @throws DJException
	 */
	public long cancelExpireRequest(RequestDTO requestDTO ,String status) throws DJException;

	/**
	 * @param requestDTO
	 * @return
	 * @throws DJException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException

	 */

	public List<Object[]> getCOMExceptionList(String store,String date, String status) throws DJException;
	public boolean cancelItems(RequestDTO requestDTO) throws DJException;
	public List<String> printLockerTicket(String storeNum, 
			String lockerPin, String customerName, String kiosk) throws DJException;	
	public String printCustomerCareCoupon(String customerName,String store,String totalTimeElapsed,
			String couponType,String lang, String kiosk)throws DJException;

	public boolean updateToEhCache(ItemDTO action,boolean cacheRemoveFlag) throws DJException;

	/*API to get the binning status of the web items at the time of initiating their pickup from the 
	 * kiosk monitor
	 */
	public List<String> getItemBinStatus(String storeNumber,String salesCheck,String divItemSku) throws DJException;


	/**
	 * @param reqInfo
	 * @return boolean
	 * @throws DJException
	 */
	public boolean updateRequestForCOM(HashMap<String, String> reqInfo,String user,String store)throws DJException;

	/**
	 * @param itemId
	 * @return qty,status
	 */
	public int getItemQty(String store,String itemId)throws DJException;


	public Map<String,String> checkStatus(String storeId, String requestNumber) throws DJException;

	public void markNoResponse(String storeId, String requestNumber, String status) throws DJException;

	public List<String> printReturnTicket(String storeNum, String printerId, String transId, String type, String user, String queueType, String kioskName, String salesCheck)throws DJException;

	public List<String> printReturnTypeTicket(String storeNum, String printerId, String transId, String type, String user, String queueType, boolean reprintFlag, String kioskName, String salesCheck)throws DJException;

	public List<String> beepToPrinter(String storeNum, String printerId,
			String storeFormat) throws DJException;

	public boolean isPickedUp(String storeNumber, String requestId, String itemId) throws DJException;


	public Map<String,Object> getNoOfPackages(String store, String salesCheck) throws DJException;
	
	public String getRequestIdbySalescheck(String store,String requestNumber) throws DJException;


	public Boolean updateCardSwipedFlagInBlob(RequestDTO requestDTO) throws DJException;


	public void printTICoupon(TICouponKioskPrintVO tiCouponKioskPrintVO,
			String store, String kioskName) throws DJException;

	public OrderConfirmResponseDTO getTIOffers(String storeNo, String readyworkIds, String sywrId, String kioskName, String storeFormat) throws DJException;
	
	public String updateFailedReason(ItemDTO actionDTO) throws DJException;
	
	public String isPlaformStore(String storeNumber) throws DJException;
	
	public String getAppServer(String storeNumber) throws DJException ;
	public String getAppServerPlatform(String storeNumber) throws DJException ;
	
	public boolean gethealthCheck(RequestDTO requestDTO) throws DJException ;
	
	public List<String> getKioskList(String storeID) throws DJException;
	public boolean sendPickedUpResponse(Order pickedUpOrder) throws DJException;
	public void nposManualUpdate(String storeNum,String rqtId) throws DJException;
	/**
	 * 
	 * @param storeNumber
	 * @param listNumber
	 * @return
	 * @throws DJException
	 */
	public long checkIsActiveRequestExisting(String storeNumber, String listNumber) 
			throws DJException;
	/**
	 * For Getting DDR Cache
	 * @return
	 */
	public List<DDMeta> getDDRMetaCache();
	/**
	 * For Refreshing the DDR Meta cache
	 * @return
	 */
	public boolean refreshDDRMetaCache();
	
	/**
	 * 
	 * @param storeNum
	 * @return
	 * @throws DJException
	 */
	public   Map<String,String> getRequestQueueMap(String storeNum) throws DJException;
	
	/**
	 * 
	 * @param rqdList
	 * @param storeNum
	 * @param queueType
	 */
	public void removeItemsFromCache(List<String> rqdList,String storeNum,String queueType);
	
	
	/**
	 * For Getting App Cache
	 * @return
	 */
	public Map<String,String> getAppMetaCache();
	/**
	 * For Refreshing the App Meta cache
	 * @return
	 */
	public boolean refreshAppMetaCache();

	public void sendFinalResponse(String storeNum,String rqtId,String requestNumber,String actionFlag,OrderDTO orderDTO) 
			throws DJException;
	//public void updateHFMbin() throws DJException;
}