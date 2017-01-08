package com.searshc.mpuwebservice.processor.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.MDC;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.sears.dej.interfaces.vo.ItemMeta;
import com.sears.dej.interfaces.vo.UserVO;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.exceptionhandler.FileExceptionHandler;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.exception.DDRMetaException;
import com.sears.dj.common.service.DJService;
import com.sears.dj.common.util.DJUtilities;
import com.sears.mpu.backoffice.domain.Customer;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderAdaptorResponse;
import com.sears.mpu.backoffice.domain.OrderItem;
import com.sears.mpu.backoffice.domain.Payment;
import com.sears.mpu.backoffice.domain.StoreInfo;
import com.searshc.mpuwebservice.bean.CustomerDTO;
import com.searshc.mpuwebservice.bean.CustomerDetailDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.LockerDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDetailDTO;
import com.searshc.mpuwebservice.bean.MPUAssociateReportDTO;
import com.searshc.mpuwebservice.bean.MpuPickUpReportResposne;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PackageDTO;
import com.searshc.mpuwebservice.bean.PaymentDTO;
import com.searshc.mpuwebservice.bean.PickUpDTO;
import com.searshc.mpuwebservice.bean.PickUpReturnDTO;
import com.searshc.mpuwebservice.bean.PickUpSelectedItems;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.SalesCheckDetails;
import com.searshc.mpuwebservice.bean.SalesCheckWorkDetail;
import com.searshc.mpuwebservice.bean.SalesCheckWorkItemDetail;
import com.searshc.mpuwebservice.bean.ShopinItemDetailDTO;
import com.searshc.mpuwebservice.bean.ShopinRequestDTO;
import com.searshc.mpuwebservice.bean.ShopinServiceResponse;
import com.searshc.mpuwebservice.bean.VehicleInfo;
import com.searshc.mpuwebservice.bean.VehicleInformation;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO;
import com.searshc.mpuwebservice.dao.LockerServiceDAO;
import com.searshc.mpuwebservice.dao.MCPDBDAO;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.dao.PickUpServiceDAO;
import com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor;
import com.searshc.mpuwebservice.processor.MODNotificationProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.processor.NPOSUpdateProcessor;
import com.searshc.mpuwebservice.processor.PickUpServiceProcessor;
import com.searshc.mpuwebservice.processor.ShopInServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;
import com.searshc.mpuwebutil.constant.MPUWEBUtilConstants;
import com.searshc.mpuwebutil.util.CommonUtils;
import com.searshc.mpuwebutil.util.ConversionUtils;
import com.shc.schema.CONTROLAREA;
import com.shc.schema.RetrieveVehicleInfoRequest;
import com.shc.schema.RetrieveVehicleInfoResponse;
import com.shc.schema.SalescheckInfo;
import com.shc.schema.SalescheckInfoRequest;
import com.shc.schema.SalescheckInfoRes;
@DJService("pickUpServiceProcessor")
public class PickUpServiceProcessorImpl implements PickUpServiceProcessor {

	private static transient DJLogger logger = DJLoggerFactory
			.getLogger(PickUpServiceProcessorImpl.class);
	@Autowired
	private PickUpServiceDAO pickUpServiceDAO;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	@Autowired
	private EhCacheCacheManager cacheManager;
	@Autowired
	private MPUWebServiceProcessor webServicesProcessorImpl;
	@Autowired
	private AssociateActivityServicesProcessor associateActivityServicesProcessorImpl;
	@Autowired
	private AssociateActivityServiceDAO associateActivityServicesDAOImpl;
	@Autowired
	private MODNotificationProcessor modNotificationProcessorImpl;
	/*@Autowired
	private LockerServiceProcessor lockerServiceProcessor;*/
	@Autowired
	private NPOSUpdateProcessor nPOSUpdateProcessorImpl;
	@Autowired
	private MPUWebServiceProcessor webServicesProcessor;
	@Autowired
	private MCPDBDAO mCPDBDAO;
	@Autowired
	private AssociateActivityServicesProcessor associateActivityServicesProcessor;
	@Autowired
	private AssociateActivityServiceDAO associateActivityServiceDAOImpl;
	@Autowired
	private ShopInServiceProcessor shopInServiceProcessor;
	@Autowired
	private LockerServiceDAO lockerServiceDAOImpl;
	
	private Map<String, Object> storeInfo = new HashMap<String, Object>();


//	public List<ItemDTO> getAllItemsForCustomer(CustomerDTO customerDTO, String typeOfIdentication, String salesCheckNumber) throws DJException {
//		
//		logger.info("getAllItemsForCustomer", "Entering MPUWebServiceProcessorImpl.getAllItemList customerDTO : " + customerDTO + " -- typeOfIdentication : " + 
//		typeOfIdentication + "  -- salesCheckNumber : " + salesCheckNumber);
//
//		List<Map<String, Object>> itemMap = pickUpServiceDAO.getAllItemsForCustomer(customerDTO, typeOfIdentication, salesCheckNumber);
//
//		List<ItemDTO> listItemDTO = new ArrayList<ItemDTO>();
//		
//		for (Map<String, Object> map : itemMap) {
//			
//			ItemDTO itemdto = new ItemDTO();
//			
//			itemdto.setVersion(map.get(VER.name()).toString());
//			itemdto.setItemCreatedDate(map.get(CREATE_TIMESTAMP.name()) + "");
//			itemdto.setRequestType((String) map.get(REQUEST_TYPE.name()));
//			itemdto.setCreatedBy((String) map.get(CREATED_BY.name()));
//			itemdto.setRqdId(map.get(RQD_ID.name()) + "");
//			itemdto.setRqtId(map.get(RQT_ID.name()) + "");
//			itemdto.setUpc((String) map.get(UPC.name()));
//			itemdto.setSalescheck((String) map.get(SALESCHECK.name()));
//			itemdto.setPlus4((String) map.get(PLUS4.name()));
//			itemdto.setItemStatus((String) map.get(ITEM_STATUS.name()));
//			itemdto.setQty(map.get(QTY.name())+ "");
//			itemdto.setQtyRemaining(map.get("qty_remaining") + "");
//			itemdto.setSku((String) map.get(SKU.name()));
//			itemdto.setRequestNumber((String) map.get(REQUEST_NUMBER.name()));
//
//			listItemDTO.add(itemdto);
//		}
//		
//		logger.info("getAllItemsForCustomer", "Exit getAllItemsForCustomer listItemDTO : " + listItemDTO);
//
//		return listItemDTO;
//	}


	/**This method is used to create item for MPU Return Request
	 * @param requestDTO
	 * @throws Exception
	 */
	public List<String> getKioskList(String storeNum) throws DJException {
		
		logger.info("getKioskList", "Entering getKioskList processor storeNum : " + storeNum);
		
		List<Map<String, Object>> kioskListMap = mCPDBDAO.getKioskList(storeNum);
		List<String> kioskList = new ArrayList<String>();
		
		for (Map<String, Object> mapList : kioskListMap) {
			kioskList.add(mapList.get("kiosk_name") + "");
		}
		
		logger.info("getKioskList", "Exit getKioskList processor PickUpReturnDTO : " +kioskList);
		
		return kioskList;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void addItemInReturn(RequestDTO requestDTO) throws DJException, DataAccessException, DDRMetaException {
		
		logger.info("addItemInReturn", "Entering addItemInReturn requestDTO : " + requestDTO);
		
		OrderDTO orderDTO = requestDTO.getOrder();
		List<ItemDTO> itemList = requestDTO.getItemList();
		
		if (null!= itemList && !itemList.isEmpty() && null != orderDTO) {
			
			// check if upc is there and div, item is null
			// extract div item from a service
			if(null != itemList.get(0).getUpc()) {
			ItemMeta meta = pickUpServiceDAO.getDivItem(itemList.get(0).getUpc(), orderDTO.getStoreNumber(), MpuWebConstants.UPC);
			if(null!=meta){
			itemList.get(0).setDivNum(meta.getDiv());
			itemList.get(0).setItem(meta.getItem());
			itemList.get(0).setSku(meta.getSku());
			}
			}
			// insert item into original queue details table

			Object[] rqdId;
			try {
				rqdId = mpuWebServiceDAOImpl.createItemList(itemList, Long.valueOf(orderDTO.getRqtId()), orderDTO.getWeb_ExpireTime(), orderDTO.getStoreNumber(), 
						orderDTO.getRequestType(), Boolean.FALSE, DJUtilities.dateToString(new Date(), MpuWebConstants.DATE_FORMAT),orderDTO.getOrderSource());
			} catch (NumberFormatException e) {
				
				throw FileExceptionHandler.logAndThrowDJException(e,  logger, "addItemInReturn", MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE);
			}

			// taking reference from previous insertion , insert into mpu queue details table

			// 1.	convert item dto into mpudetails dto
			List<MPUActivityDetailDTO> mpuActivityDetailDTOs = new ArrayList<MPUActivityDetailDTO>();
			List<ItemDTO> itemDTOs = requestDTO.getItemList();

			// 2. add parent id , we know we have a single item only here 
			String CurrTime = getTimeAccToTimeZone(new Date() + "", requestDTO.getOrder().getStoreNumber(), "dd/MM/yyyy HH:mm:ss");

			for(ItemDTO item : itemDTOs) {

				item.setReturnParentId(rqdId[0] + "");

				item.setCreateTime(CurrTime);
				item.setUpdateIimestamp(CurrTime);

				MPUActivityDetailDTO mpuActivity = ConversionUtils.convertItemDTOtoMPUActivityDetailDTO(item);
				mpuActivityDetailDTOs.add(mpuActivity);
			}

			associateActivityServicesDAOImpl.insertMPUActivityDetail(mpuActivityDetailDTOs);
			pickUpServiceDAO.initiatePickUpActivityForItems(itemDTOs, requestDTO.getOrder().getStoreNumber(),"ADD");
			
			/*try{
				
				EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
				String formattedStoreNo = DJUtilities.leftPadding(requestDTO.getOrder().getStoreNumber(), 5);
				logger.info("completeRequest", "completeRequest formattedStoreNo : " + formattedStoreNo + " -- KioskName : " + requestDTO.getOrder().getKioskName());
				String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+requestDTO.getOrder().getKioskName();
				logger.info("completeRequest", "completeRequest cacheDirtyKey : " + cacheDirtyKey);
				
				if(null != requestQueueCache) {
					requestQueueCache.put(cacheDirtyKey, "true");
					logger.info("completeRequest", "completeRequest cache is dirty.");
				}
				
			} catch(Exception exp){
				
				logger.error("completeRequest", exp);
			}*/
			
			/**
			 * Declare that the Cache is no longer clean
			 */
			setCacheDirty(requestDTO);
			
			
		}
		
		logger.info("addItemInReturn", "Exit addItemInReturn");
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void assignMpuRequest(RequestDTO requestDTO) throws DJException {
		logger.info("assignMpuRequest", "Entering assignMpuRequest requestDTO : " + requestDTO);
		if (null != requestDTO) {
			
			OrderDTO order = requestDTO.getOrder();

			pickUpServiceDAO.assignUser(order.getStoreNumber(),	order.getTrans_id(), order.getAssociate_id(), order.getRqtId(),order.getSearsSalesId());

			/**
			 * Declare that the Cache is no longer clean
			 */
			setCacheDirty(requestDTO);
			
		}
		logger.info("assignMpuRequest", "Exit assignMpuRequest");
	}
	
	@SuppressWarnings("unused")
	private Boolean isMPU(String requestType) {
		logger.info("isMPU", "Entering isMPU : ");
		if(requestType.equalsIgnoreCase(MpuWebConstants.PICKUP)) {
			logger.info("isMPU requestType : "+MpuWebConstants.PICKUP, "Exit isMPU true"+ Boolean.TRUE);
			return Boolean.TRUE;
			
		} else if(requestType.equalsIgnoreCase(MpuWebConstants.HELP)) {
			logger.info("isMPU requestType : "+MpuWebConstants.HELP, "Exit isMPU true"+ Boolean.TRUE);
			return Boolean.TRUE;
			
		} else if(requestType.equalsIgnoreCase(MpuWebConstants.REPAIRPICK)) {
			logger.info("isMPU requestType : "+MpuWebConstants.REPAIRPICK, "Exit isMPU true"+ Boolean.TRUE);
			return Boolean.TRUE;
			
		} else if(requestType.equalsIgnoreCase(MpuWebConstants.REPAIRDROP)) {
			logger.info("isMPU requestType : "+MpuWebConstants.REPAIRDROP, "Exit isMPU true"+ Boolean.TRUE);
			return Boolean.TRUE;
			
		} else if(requestType.equalsIgnoreCase(MpuWebConstants.RETURN)) {
			logger.info("isMPU requestType : "+MpuWebConstants.RETURN, "Exit isMPU true"+ Boolean.TRUE);
			return Boolean.TRUE;
		}
		logger.info("isMPU", "Exit isMPU false : "+ Boolean.FALSE);
		return Boolean.FALSE;
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void updateMpuReturnItemList(RequestDTO requestDTO) throws DJException {
		
		logger.info("updateMpuReturnItemList", "Entering updateMpuReturnItemList requestDTO : " + requestDTO);
		
		if (null != requestDTO) {
				
			for(ItemDTO item : requestDTO.getItemList()) {
				
				pickUpServiceDAO.updateMpuItemDetails(item);	// update item in mpu_queue_details  :: update requestedQty
			}
			
			/*try{
				
				EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
				String formattedStoreNo = DJUtilities.leftPadding(requestDTO.getOrder().getStoreNumber(), 5);
				logger.info("completeRequest", "completeRequest formattedStoreNo : " + formattedStoreNo + " -- KioskName : " + requestDTO.getOrder().getKioskName());
				String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+requestDTO.getOrder().getKioskName();
				logger.info("completeRequest", "completeRequest cacheDirtyKey : " + cacheDirtyKey);
				
				if(null != requestQueueCache) {
					requestQueueCache.put(cacheDirtyKey, "true");
					logger.info("completeRequest", "completeRequest cache is dirty.");
				}
				
			} catch(Exception exp){
				
				logger.error("completeRequest", exp);
			}*/
			/**
			 * Declare that the Cache is no longer clean
			 */
				setCacheDirty(requestDTO);
			
		}
		
		int rows = pickUpServiceDAO.initiatePickUpActivityForItems(requestDTO.getItemList(), requestDTO.getOrder().getStoreNumber(),"EDIT");
		
		
		logger.info("Exiting updateMpuReturnItemList", " rows Updated = "+rows);
	}

//	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	public void updateCurbsideItems(List<RequestDTO> requestDTOs) throws Exception {
//		
//		for (RequestDTO requestDTO : requestDTOs) {
//			if (null != requestDTO) {
//				String storeNo = "";
//				String rqtId = "";
//				OrderDTO order = requestDTO.getOrder();
//				List<ItemDTO> itemDTOs = requestDTO.getItemList();
//				if (null != itemDTOs && !itemDTOs.isEmpty()) {
//					for (ItemDTO item : itemDTOs) {
//						mpuWebServiceDAOImpl.updateItemDetails(
//								item.getRequestNumber(), item,
//								item.getItemStatus());
//						storeNo = item.getStoreNumber();
//						rqtId = item.getRqtId();
//					}
//					ArrayList<String> rqtList = new ArrayList<String>();
//					rqtList.add(rqtId);
//					mpuWebServiceDAOImpl.updateCurbsideOrder(storeNo, rqtId,
//							order.getRequestType(), order.getRequestStatus());
//				}
//			}
//		}
//	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public String completeRequest(List<RequestDTO> requestDTOListConverted) throws DJException {
		if (null != requestDTOListConverted) {
			String reponse = "true";
			/*ObjectMapper theObjectMapper = new ObjectMapper();
			ArrayList<RequestDTO> requestDTOListConverted = theObjectMapper.convertValue(requestDTOList, new TypeReference<List<RequestDTO>>(){});*/
			for(RequestDTO requestDTO : requestDTOListConverted) {
				logger.info("completeRequest", "Entering completeRequest requestDTO : " + requestDTO);
				int noOfRows = 0;	
				if (null != requestDTO) {
					
					String storeNo = "";
					String rqtId = "";
					String transId = "";
					String startTime = "";
					String timeTakenForPickUp = "";
					List<ItemDTO> itemDTOs = requestDTO.getItemList();
					OrderDTO orderDTO = requestDTO.getOrder();
					
					if ("NOT_DELIVER".equalsIgnoreCase(requestDTO.getItemList().get(0).getActionId())) {
						// Send MOD Notification
						modNotificationProcessorImpl.sendMODNotification(requestDTO.getItemList().get(0),4);
						/**
						 * JIRA-STORESYS-25678
						 */
						if(null!=requestDTO.getItemList().get(0)){
							storeNo = requestDTO.getItemList().get(0).getStoreNumber();
						}
		
					} else {
						
						if (null != itemDTOs && !itemDTOs.isEmpty()) {
		
							storeNo = itemDTOs.get(0).getStoreNumber();
		
							rqtId = itemDTOs.get(0).getRqtId();
							
							transId = itemDTOs.get(0).getTrans_id() + "";
							
							
		
							// Check if already completed
							List<Map<String, Object>> isalreadyCompleted = pickUpServiceDAO.checkIfRequestAlreadyCompleted(transId, storeNo);
							
							if("PICKED_UP".equalsIgnoreCase((String) isalreadyCompleted.get(0).get("request_status")) 
									|| "COMPLETED".equalsIgnoreCase((String) isalreadyCompleted.get(0).get("request_status"))
									|| "RETURNED".equalsIgnoreCase((String) isalreadyCompleted.get(0).get("request_status")))  {
								
								DJException djException = FileExceptionHandler.logAndThrowDJException(null,  logger, "completeRequest", "Some other user has worked on this request");
								djException.setCode(MPUWEBUtilConstants.BUSSINESS_ERROR_CODE);
								throw djException;
							} else {
								logger.info("completeRequest","Start Time : " + isalreadyCompleted.get(0).get("start_time"));
								String time = isalreadyCompleted.get(0).get("start_time").toString();
								startTime = time.toString().substring(0, time.lastIndexOf('.'));
								//startTime = (String) isalreadyCompleted.get(0).get("start_time");
								//timeTakenForPickUp = CommonUtils.getExpiredTime(startTime, "");
								timeTakenForPickUp = getExpiredTime(startTime, "",getTimeAccToTimeZone(new Date().toString(), storeNo, "yyyy-MM-dd HH:mm:ss"));
							}
							
							
							// Update activity item details					
							if(requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.PICKUP)) {
								
								noOfRows = updateActivityDetailDTO(itemDTOs);
								
								/**
								 * Declare that the Cache is no longer clean
								 */
								setCacheDirty(requestDTO);
								
								
								if (!(noOfRows == 0)) {
									for (ItemDTO item : itemDTOs) {
										int updateFlag = 1;
										
										// Check for qty remaining and open items in request_mpu_details
										if(item.getItemQuantityNotDelivered().equalsIgnoreCase("0"))
											updateFlag = pickUpServiceDAO.checkForQtyRemainingAndOpenItems(item);
																			
										if (updateFlag != 0) {
											
											//update in request_queue_details
											noOfRows = completeMPUItem(item);
											if(noOfRows!=0){
												updateTimeForMPUOrder(storeNo, rqtId);
											}
										}
									}
									// Update request_queue_details
								}
		
								// Update request_mpu_trans table
								noOfRows = updateActivityDTO(storeNo, rqtId,transId,orderDTO.getRequestType(),itemDTOs.get(0).getAssignedUser(),requestDTO.getOrder().getSearsSalesId());
								
								// Update request_queue_trans
								if (noOfRows != 0) {
									// Check for qty remaining and open items in
									// request_mpu_details in request_queue_details
									int updateFlag = pickUpServiceDAO.checkForOpenItems(itemDTOs.get(0));
									if (updateFlag != 0) {
										noOfRows = completeMPUOrder(storeNo, rqtId);
									}
								
							}
								int requestedQty = 0;
								int pickedUpQty = 0;
								boolean nposFlag = false;
								for (ItemDTO item : itemDTOs) {
									requestedQty += Integer.parseInt(item.getItemQuantityRequested());
									pickedUpQty += Integer.parseInt(item.getItemQuantityRemaining());
									if(null != requestDTO.getOrder().getRelatedtransaction() && !"".equalsIgnoreCase(requestDTO.getOrder().getRelatedtransaction())){
										item.setRequestType(MpuWebConstants.EXCHANGEIN5);
									}
									if(!("FULL_NOT_DELIVER").equalsIgnoreCase(item.getCommentList())) {
										// insert activity table 
										List<ItemDTO> lItemDTO = new ArrayList<ItemDTO>();
										item.setCreatedBy(requestDTO.getOrder().getPickup_source());
										lItemDTO.add(item);
										noOfRows = pickUpServiceDAO.initiatePickUpActivityForItems(lItemDTO, storeNo,"PICKED_UP");
										nposFlag = true;
									} else {
										// insert activity table 
										List<ItemDTO> lItemDTO = new ArrayList<ItemDTO>();
										item.setCreatedBy(requestDTO.getOrder().getPickup_source());
										lItemDTO.add(item);
										noOfRows = pickUpServiceDAO.initiatePickUpActivityForItems(lItemDTO, storeNo,"NOT_PICKED_UP");
									}
								}
							//Code for STORESYS-25234 Starts	
							if (null != orderDTO && null != orderDTO.getPickup_source() && orderDTO.getPickup_source().equalsIgnoreCase("SHOPIN")) {
								String associateName = getAssociateName(orderDTO.getAssociate_id());
								String pickupEndTime = getTimeAccToTimeZone(new Date().toString(), storeNo, "yyyy-MM-dd HH:mm:ss"); 
								String status = (requestedQty == pickedUpQty) ? "PICKUP_COMPLETED" : "PICKUP_PARTIAL"; 
								if(!nposFlag) 
									status = "PICKUP_CANCELLED";
								String salescheck="";
							//	String associateName="";
								if(!itemDTOs.isEmpty()){
									//salescheck=itemDTOs.get(0).getSalescheck();
									associateName=itemDTOs.get(0).getAssignedUser();									
								}
								salescheck = orderDTO.getSalescheck();
								logger.info("Calling updateShopInReport with: ",salescheck + " / " + String.valueOf(pickedUpQty) + " / " + status + " / " + orderDTO.getCustomer_name() + " / " + associateName + " / " + pickupEndTime);
								//JIRA: 26002, Fix Production Issues Status (MPU Platform and MPU Rev) - Oct/27
								shopInServiceProcessor.updateShopInReport(storeNo, salescheck, String.valueOf(pickedUpQty), status, orderDTO.getCustomer_name(), associateName, pickupEndTime);

								shopInServiceProcessor.pushNotificationToShopIn(rqtId, orderDTO.getSalescheck(), status, requestDTO.getOrder().getSearsSalesId(), associateName, timeTakenForPickUp, storeNo);
							}
							//Code for STORESYS-25234 Ends	
							if(nposFlag)
							updateStatusToNPOS(storeNo,rqtId, requestDTO.getOrder().getRequestType(), requestDTO.getOrder().getCustomerSignature(), "updatePickCompletedToNPOS", Integer.parseInt(transId),"",requestDTO.getOrder().getStoreFormat(),"","",requestDTO.getOrder().getKioskName());
							
							//Getting property from order adaptor for coupon printing
							// getting printExtendedAmountCouponFlag from adaptor
							
							logger.info("getPropertyFromAdaptor","completed npos update and now fetching value of printExtendedAmountCouponFlag from property file");
							
							
							
						} else if(requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.HELP) || 
								requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.REPAIRDROP) || 
								requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.REPAIRPICK)) {
							
							noOfRows = updateActivityDTO(storeNo, rqtId,transId,requestDTO.getOrder().getRequestType(),itemDTOs.get(0).getAssignedUser(), requestDTO.getOrder().getSearsSalesId());
							
							// insert activity table
							pickUpServiceDAO.initiatePickUpActivityForItems(itemDTOs, requestDTO.getOrder().getStoreNumber(),"COMPLETED");
							if (!(noOfRows == 0))
							{
								OrderDTO order = requestDTO.getOrder();
								String CurrTime = getTimeAccToTimeZone(new Date() + "", order.getStoreNumber(), "yyyy-MM-dd HH:mm:ss");
								order.setEnd_time(CurrTime);
								String reqUrl = "closeHelpOrder";
								updateStatusToNPOS(storeNo,rqtId, requestDTO.getOrder().getRequestType(), requestDTO.getOrder().getCustomerSignature(), reqUrl,Integer.parseInt(transId),
										itemDTOs.get(0).getSalescheck(),requestDTO.getOrder().getStoreFormat(),order.getStart_time(), order.getEnd_time(),requestDTO.getOrder().getKioskName());
								
							}
							
						} else if (requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.MPU_PAGE_REGISTER)) {
							//this block added for JIRA-25084 (platform pickup- pages from register are not coming to MPU)
							noOfRows = updateActivityDTO(storeNo, rqtId,transId,requestDTO.getOrder().getRequestType(),itemDTOs.get(0).getAssignedUser(), requestDTO.getOrder().getSearsSalesId());
							// insert activity table
							pickUpServiceDAO.initiatePickUpActivityForItems(itemDTOs, requestDTO.getOrder().getStoreNumber(),"COMPLETED");
						} else {
							logger.info("Calling RETURNIN5 block","");
							if(!orderDTO.isReturnNotFound()){
								noOfRows = updateActivityDetailDTO(itemDTOs);	
							}
							
								noOfRows = updateActivityDTO(storeNo, rqtId,transId,orderDTO.getRequestType(),itemDTOs.get(0).getAssignedUser(), requestDTO.getOrder().getSearsSalesId());
							
							// insert activity table
								if(requestDTO.getOrder().getExchangeFlag()){
									for(ItemDTO itemDTO : itemDTOs){
										itemDTO.setRequestType(MpuWebConstants.EXCHANGEIN5RETURN);
									}
								}
							pickUpServiceDAO.initiatePickUpActivityForItems(itemDTOs, requestDTO.getOrder().getStoreNumber(),"RETURNED");
							
							OrderDTO order = requestDTO.getOrder();
							String CurrTime = getTimeAccToTimeZone(new Date() + "", order.getStoreNumber(), "yyyy-MM-dd HH:mm:ss");
							order.setEnd_time(CurrTime);
							if(null != orderDTO && null != orderDTO.getPickup_source()) {
								logger.info("Calling RETURNIN5 block", "orderDTO.getPickup_source(): " + orderDTO.getPickup_source());
							} else {
								logger.info("Calling RETURNIN5 block", "orderDTO.getPickup_source(): null");
							}
							
							if (null != orderDTO && null != orderDTO.getPickup_source() && ("SHOPIN").equalsIgnoreCase(orderDTO.getPickup_source())) {
								logger.info("Calling RETURNIN5 for SHOPIN", "orderDTO.getPickup_source(): " + orderDTO.getPickup_source());
								String retAuthCode=null;
								try{
									retAuthCode=pickUpServiceDAO.getRetAuthCode(storeNo,orderDTO.getRqtId());
								}catch(DJException dje){
									logger.error("completeRequest()",dje);
								}								
								
								//Production Issues Status (MPU Platform and MPU Rev) - Oct/27. Add a call to method updateShopInReport to update Shopin report
								int returnQuantity = 0;
								String associateName = getAssociateName(orderDTO.getAssociate_id());
								String returnEndTime = getTimeAccToTimeZone(new Date().toString(), storeNo, "yyyy-MM-dd HH:mm:ss"); 
								for (ItemDTO currentItemDTO: itemDTOs) {
									returnQuantity += Integer.parseInt(currentItemDTO.getItemQuantityRequested());
								}
								String salescheck="";
								//String associateName="";
								salescheck=orderDTO.getSalescheck();
								associateName = orderDTO.getAssociate_id();
								
								logger.info("Calling updateShopInReport for return with: ",salescheck+ " / " + String.valueOf(returnQuantity) + " / " + orderDTO.getCustomer_name() + " / " + associateName + " / " + returnEndTime);
								//JIRA: 26002, Fix Production Issues Status (MPU Platform and MPU Rev) - Oct/27
								shopInServiceProcessor.updateShopInReport(order.getStoreNumber(),salescheck, String.valueOf(returnQuantity), "RETURN COMPLETED", orderDTO.getCustomer_name(), associateName, returnEndTime);
								logger.info("Called updateShopInReport for return with: ",orderDTO.getSalescheck() + " / " + String.valueOf(returnQuantity) + " / " + orderDTO.getCustomer_name() + " / " + associateName + " / " + returnEndTime);
								shopInServiceProcessor.pushNotificationToShopIn(rqtId, retAuthCode, "RETURN COMPLETED", requestDTO.getOrder().getSearsSalesId(), itemDTOs.get(0).getAssignedUser(), timeTakenForPickUp, storeNo);
							}
							reponse = updateStatusToNPOS(order.getStoreNumber(), null, order.getRequestType(), "", "updateReturnToNPOS", order.getTrans_id(), 
											order.getOriginalIdentifier(), order.getStoreFormat(), order.getStart_time(), order.getEnd_time(),requestDTO.getOrder().getKioskName());
							
						}
		
					}
		
				}
				try{
					
					EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
					String formattedStoreNo = DJUtilities.leftPadding(storeNo, 5);
					logger.info("completeRequest", "completeRequest formattedStoreNo : " + formattedStoreNo + " -- KioskName : " + requestDTO.getOrder().getKioskName());
					String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+requestDTO.getOrder().getKioskName();
					logger.info("completeRequest", "completeRequest cacheDirtyKey : " + cacheDirtyKey);
					String ohmCacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+requestDTO.getOrder().getKioskName();
					if(null != requestQueueCache) {
						requestQueueCache.put(cacheDirtyKey, "true");
						requestQueueCache.put(ohmCacheDirtyKey, "true");
						logger.info("completeRequest", "completeRequest cache is dirty.");
					}
					
				} catch(Exception exp){
					
					logger.error("completeRequest", exp);
				}
			}
		
			}
			logger.info("completeRequest", "Exit completeRequest");
			return reponse;
		}
		return null;
	}

	// cancel returnin5 and update to echcache accordingly
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void cancelReturnin5(RequestDTO requestDTO) throws DJException {
		
		logger.info("cancelReturnin5", "Entering cancelReturnin5 requestDTO : " + requestDTO);
		
		if (null != requestDTO) {
			OrderDTO order = requestDTO.getOrder();
			pickUpServiceDAO.cancelReturnin5(order.getTrans_id(),
					"CANCELLED", order.getStoreNumber());
			
			// insert in activity table
				if(requestDTO.getOrder().getExchangeFlag()) {
					for(ItemDTO item: requestDTO.getItemList()) {
						item.setRequestType(MpuWebConstants.EXCHANGEIN5RETURN);
					}
				}
				pickUpServiceDAO.initiatePickUpActivityForItems(requestDTO.getItemList(), requestDTO.getOrder().getStoreNumber(),"CANCELLED");
				
				//Called new shopin service
				if (null != order && null != order.getPickup_source() && ("SHOPIN").equalsIgnoreCase(order.getPickup_source())) {
					
					List<Map<String, Object>> transData= pickUpServiceDAO.getMPUTransData(order.getStoreNumber(),order.getTrans_id());
					String time = transData.get(0).get("start_time").toString();
					time = time.toString().substring(0, time.lastIndexOf('.'));
					String timeTakenForPickUp = CommonUtils.getExpiredTime(time, "");
					shopInServiceProcessor.pushNotificationToShopIn(order.getRqtId(), order.getSalescheck(), "RETURN CANCELLED", requestDTO.getOrder().getSearsSalesId(), requestDTO.getItemList().get(0).getAssignedUser(), timeTakenForPickUp, order.getStoreNumber());
				}

			/**
			 * Update NPOS As ReturnIn5 is Cancelled
			 * @author nkhan6
			 */
			
			OrderAdaptorRequest request = mpuWebServiceDAOImpl.getOriginalJSON(null, requestDTO.getOrder().getStoreNumber(), requestDTO.getOrder().getTrans_id());
			request.setRequestType(OrderAdaptorRequest.PUSH_ORDER);
			Order oldOrder = request.getRequestOrder();
			oldOrder.setOriginalIdentifier(order.getOriginalIdentifier());
			oldOrder.setStoreNo(order.getStoreNumber());
			oldOrder.setStoreFormat(order.getStoreFormat());
			oldOrder.setCustomerSignature("");
			oldOrder.setKioskName(order.getKioskName());
		
			oldOrder.setRingingAssociateCode(getAssociateId()); // item selling associate id // to be set
			oldOrder.getTransaction().setTranscationDateAsString(getTimeAccToTimeZone(new Date() + "", order.getStoreNumber(), "MM/dd/yyyy HH:mm:ss"));

			Map<String, Order> orderMap = new HashMap<String, Order>();
			orderMap.put(oldOrder.getIdentifier(), oldOrder);
			request.setOrdersMap(orderMap);
			if(!order.getExchangeFlag())
			nPOSUpdateProcessorImpl.updateNPOS(request, "cancelTenderReturn");
			
			/**
			 * Declare that the Cache is no longer clean
			 */
				setCacheDirty(requestDTO);
			/*try{
				
				EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
				String formattedStoreNo = DJUtilities.leftPadding(requestDTO.getOrder().getStoreNumber(), 5);
				logger.info("completeRequest", "completeRequest formattedStoreNo : " + formattedStoreNo + " -- KioskName : " + requestDTO.getOrder().getKioskName());
				String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+requestDTO.getOrder().getKioskName();
				logger.info("completeRequest", "completeRequest cacheDirtyKey : " + cacheDirtyKey);
				
				if(null != requestQueueCache) {
					requestQueueCache.put(cacheDirtyKey, "true");
					logger.info("completeRequest", "completeRequest cache is dirty.");
				}
				
			} catch(Exception exp){
				
				logger.error("completeRequest", exp);
			}*/
			
			
		}
		
		logger.info("cancelReturnin5", "Exit cancelReturnin5");
	}

	// Manish
	
	private List<RequestDTO> customerDuplicacyChk(List<RequestDTO> requestDTOListTemp, PickUpDTO pickUpDTO) throws Exception {

		String values=(StringUtils.hasText(pickUpDTO.getCustomerId()) ?  pickUpDTO.getCustomerId() : "")+","+
				(StringUtils.hasText(pickUpDTO.getAddressId()) ? pickUpDTO.getAddressId() : "")+","+
				(StringUtils.hasText(String.valueOf(pickUpDTO.getIdStsCd())) ? pickUpDTO.getIdStsCd() : "");
		
			return resolveDuplicacy(requestDTOListTemp,values , "customer");
	}
	// Get all pick up items
	
	public PickUpReturnDTO getAllItemsForPickUp(String pickUpLoc,String numType, String step,
			String identifierValue, PickUpDTO pickUpDTO) throws DJException {
		logger.info("getAllItemsForPickUp", "Entering getAllItemsForPickUp numType : " + numType + "step : " + step + "identifierValue : "+ identifierValue + "pickUpDTO : "+ pickUpDTO);
		
		List<RequestDTO> requestDTOList = new ArrayList<RequestDTO>();
		PickUpReturnDTO pickUpReturnDTO = new PickUpReturnDTO();
		try {
		pickUpReturnDTO.setReqType(MpuWebConstants.PICKUP);

		// Step 1 Unique SC check starts ----
		if ((step == null || "".equalsIgnoreCase(step)) && !(MpuWebConstants.NAME_ADDRESS.equalsIgnoreCase(numType))) {

			//listOrderDTO = pickUpServiceDAO.getAllOrdersForPickUp(pickUpDTO,numType, identifierValue); // List of Order fron DB
			
			List<Order> orderListNPOS = pickUpServiceDAO.getOrdersFromNPOS(pickUpDTO, numType,	identifierValue, MpuWebConstants.PICKUP); // List of order from NPOS
			//List<Order> orderListNPOS = pickUpServiceDAO.getOrdersFromEI5NPOS(pickUpDTO, numType,	identifierValue, MpuWebConstants.PICKUP); // List of order from NPOS
						
			 if(null == orderListNPOS || orderListNPOS.isEmpty()) {
			 
			  pickUpReturnDTO.setReturnDesc("No Data Found."); 
			  return  pickUpReturnDTO; 
			  
			 }else{

				 logger.info("orderListNPOS.size() = ", orderListNPOS.size());	
			 }
			 

			// Converting Data from NPOS into List of Request DTOs

			for (Order itemMapNPOSs : orderListNPOS) {

				RequestDTO requestDTO = new RequestDTO();
				requestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(itemMapNPOSs);
				requestDTOList.add(requestDTO);
			}

			// Adding to EhCache
			// For shopin value for kiosk is set as sywrId for uniqueness in the EhCache.
			
			if(pickUpLoc.equalsIgnoreCase("SHOPIN")){
				addRequestToEhCache(requestDTOList, pickUpDTO.getStoreNumber(), pickUpDTO.getKiosk(), MpuWebConstants.PICK_UP_REQUEST, pickUpLoc.toUpperCase(), "", numType);

			}else{
				addRequestToEhCache(requestDTOList, pickUpDTO.getStoreNumber(), pickUpDTO.getKiosk(), MpuWebConstants.PICK_UP_REQUEST, pickUpLoc.toUpperCase(), identifierValue, numType);
			}
			
			// Checking for multiple customers
			if(! pickUpLoc.equalsIgnoreCase("SHOPIN")) {
				if (isDuplicateSalescheck(requestDTOList, numType)) {
	
						HashSet<CustomerDTO> hs = new HashSet<CustomerDTO>();
						HashSet<String> addressId = new HashSet<String>();
						HashSet<String> customerId = new HashSet<String>();
						
						for (RequestDTO obj2 : requestDTOList) {
							
							if(addressId.add(obj2.getCustomer().getAddressId()) || customerId.add(obj2.getCustomer().getCustomerId())) {
								customerId.add(obj2.getCustomer().getCustomerId());
								addressId.add(obj2.getCustomer().getAddressId());
								hs.add(obj2.getCustomer());
							}
						}
	
						if (numType.equalsIgnoreCase(MpuWebConstants.PHONE)) {
							pickUpReturnDTO.setReturnDesc("Multiple Customer Exception for PHONE");
						} else {
							pickUpReturnDTO.setReturnDesc("Multiple Customer Exception.");
						}
						pickUpReturnDTO.setResponseData(new ArrayList<CustomerDTO>(hs));	
	
						return pickUpReturnDTO;
					}
				}
			}
		// Step 1 Unique SC check over -----

		// Step 2 resolve duplicacy start -------------------------
		if (("RESOLVE_DUPLICACY").equalsIgnoreCase(step) && 
				!pickUpLoc.equalsIgnoreCase(MpuWebConstants.CURBSIDE)) {

			// Fetch item list from cache
			List<RequestDTO> requestDTOListTemp = getAllRequestsFromEhCache(pickUpDTO.getStoreNumber(),	MpuWebConstants.PICK_UP_REQUEST, pickUpDTO.getKiosk(), pickUpLoc.toUpperCase(), identifierValue, numType);
			
		 if (null != pickUpDTO.getPurchaseDate() &&
				 !(pickUpDTO.getPurchaseDate().equalsIgnoreCase("")) && 
				 !(pickUpDTO.getPurchaseDate().isEmpty())) {

				requestDTOList = resolveDuplicacy(requestDTOListTemp, pickUpDTO.getPurchaseDate(), "date");

			} /*else if(null!=pickUpDTO.getAddress1() && pickUpDTO.getAddress1()!=""){

				String address = pickUpDTO.getAddress1();
				requestDTOList = resolveDuplicacy(requestDTOListTemp, address, "address");
			} */else {
				requestDTOList = customerDuplicacyChk(requestDTOListTemp, pickUpDTO);
			}
			//return the list of orders from the cache on the basis of customerDTO for the curbside pick up
			
			if (isDuplicateSalescheck(requestDTOList, numType)) {

				pickUpReturnDTO.setReturnDesc("Multiple Customer Exception. Help Request should be Raised.");
				return pickUpReturnDTO;

			}
		} else if(("RESOLVE_DUPLICACY").equalsIgnoreCase(step) && 
				pickUpLoc.equalsIgnoreCase(MpuWebConstants.CURBSIDE)) {
			
			List<RequestDTO> requestDTOListTemp = getAllRequestsFromEhCache(pickUpDTO.getStoreNumber(),	MpuWebConstants.PICK_UP_REQUEST, pickUpDTO.getKiosk(), pickUpLoc.toUpperCase(), identifierValue, numType);
			
			//return the Single customer found for the salescheck on the basis of pickuplocation
			requestDTOList = customerDuplicacyChk(requestDTOListTemp, pickUpDTO);
					
		}
		// check duplicacy for shopin pickup
		else if (step == null || "".equalsIgnoreCase(step) && 
				pickUpLoc.equalsIgnoreCase(MpuWebConstants.SHOPIN)){
			List<RequestDTO> requestDTOListTemp = getAllRequestsFromEhCache(pickUpDTO.getStoreNumber(),	MpuWebConstants.PICK_UP_REQUEST, pickUpDTO.getKiosk(), pickUpLoc, "", numType);
			requestDTOList = resolveDuplicacy(requestDTOListTemp,pickUpDTO.getSywrId() , "sywrNumber");
		}

		
		//return the Single customer found for the salescheck on the basis of pickuplocation
		
		// Step 2 resolve duplicacy Ends -------------------------

		// Step 3 Fetching of data start -------------------------

		//pickUpDTO = new PickUpDTO();

		List<Order> orderListNPOS = new ArrayList<Order>();
		
		if(MpuWebConstants.NAME_ADDRESS.equalsIgnoreCase(numType)) {			
			orderListNPOS = pickUpServiceDAO.getOrdersFromNPOS(pickUpDTO, MpuWebConstants.MATCHKEY,	"", MpuWebConstants.PICKUP); // List of order from NPOS
		} else {
			pickUpDTO.setAddressId(requestDTOList.get(0).getCustomer().getAddressId());
			pickUpDTO.setAddress1(requestDTOList.get(0).getCustomer().getAddress1());
			pickUpDTO.setAddress2(requestDTOList.get(0).getCustomer().getAddress2());
			if(!requestDTOList.get(0).getCustomer().getIdStsCd().equalsIgnoreCase(null))
			pickUpDTO.setIdStsCd(requestDTOList.get(0).getCustomer().getIdStsCd().charAt(0));
			pickUpDTO.setCustomerId(requestDTOList.get(0).getCustomer().getCustomerId());
			
			orderListNPOS = pickUpServiceDAO.getOrdersFromNPOS(pickUpDTO, MpuWebConstants.CUSTOMERID, requestDTOList.get(0).getCustomer().getCustomerId(),  MpuWebConstants.PICKUP); // List of order from NPOS
		}
		
		List<RequestDTO> exchangeOrderList = new ArrayList<RequestDTO>();		
		 if(!pickUpLoc.equalsIgnoreCase(MpuWebConstants.CURBSIDE)) {
			 for(Order order : orderListNPOS) {
				 if(/*orderListNPOS.size() == 1 && */order.isExchangeFlag()){
					 
					    //Order order = orderListNPOS.get(0);
					    //check if the order already exists in database
					    boolean isEI5OrderAlreadyinDB = pickUpServiceDAO.checkForEI5Order(order.getIdentifier(), order.getIdentifierType(), pickUpDTO.getStoreNumber());
					    logger.info("isEI5OrderAlreadyinDB = ", isEI5OrderAlreadyinDB);
					    RequestDTO mainOrderRequestDTO = null;
					    if(! isEI5OrderAlreadyinDB){
					    	mainOrderRequestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(order);
					    	 webServicesProcessorImpl.createRequest(mainOrderRequestDTO.getOrder(), mainOrderRequestDTO.getCustomer(), mainOrderRequestDTO.getItemList(),
					    			mainOrderRequestDTO.getPaymentList(), mainOrderRequestDTO.getTask());
							    exchangeOrderList.add(mainOrderRequestDTO);
					    }
					    
					    boolean isRI5OrderAlreadyPresent = pickUpServiceDAO.checkForRI5Order(order.getIdentifierType(), pickUpDTO.getStoreNumber());
					    logger.info("isRI5OrderAlreadyPresent = ", isRI5OrderAlreadyPresent);
					    
					    if(! isRI5OrderAlreadyPresent ){
					    	
					    	 Order returnOrder = searchTenderReturns(order.getIdentifierType(),pickUpDTO.getStoreNumber(),pickUpDTO.getKiosk(),pickUpDTO.getStoreFormat());
					    	 
					    	 if(returnOrder!=null){
						    	 RequestDTO returnRequestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(returnOrder);
						    	 webServicesProcessorImpl.createRequest(returnRequestDTO.getOrder(), returnRequestDTO.getCustomer(), returnRequestDTO.getItemList(), returnRequestDTO.getPaymentList(), returnRequestDTO.getTask());	
					    	 }
					    	/* else{
					    		return pickUpReturnDTO.setReturnDesc("No Data Found."); 
					    	 }*/
					    	 
					    	 //returnParentId = parentReqDTO.getOrder().getRqtId();
					    	// returnRequestDTO  = createReturnin5(returnRequestDTO, numType, identifierValue, pickUpDTO.getKiosk(), pickUpDTO.getStoreNumber());
					    }
					 } 
			 }
		 }	

		List<String> salescheckList = new ArrayList<String>();
		// Converting Data from NPOS into List of Request DTOs
		requestDTOList = new ArrayList<RequestDTO>();
		for (Order itemMapNPOSs : orderListNPOS) {

			RequestDTO requestDTO = new RequestDTO();
			requestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(itemMapNPOSs);
			requestDTOList.add(requestDTO);
			salescheckList.add(requestDTO.getOrder().getSalescheck());
			
		}
		
		if(MpuWebConstants.NAME_ADDRESS.equalsIgnoreCase(numType)) {
			pickUpDTO.setAddress1(requestDTOList.get(0).getCustomer().getAddress1());
			pickUpDTO.setAddress2(requestDTOList.get(0).getCustomer().getAddress2());
			pickUpDTO.setCustomerId(requestDTOList.get(0).getCustomer().getCustomerId());
		}

		//List<ItemDTO> listOrderDTO = pickUpServiceDAO.getAllItemsForPickUp(pickUpDTO,numType, identifierValue);	// List of Order from DB
				List<ItemDTO> listOrderDTO = pickUpServiceDAO.getAllItemsForPickUpFromSalescheckList(pickUpDTO.getCustomerId(),salescheckList,pickUpDTO.getStoreNumber());
		
		List<RequestDTO> lRequestTemp = new ArrayList<RequestDTO>();
		if (null == listOrderDTO || listOrderDTO.isEmpty() || listOrderDTO.size() == 0) {

			for (RequestDTO requestDTONPOS : requestDTOList) {
				//To prevent on orders from being created in DB if there is no entry in db against search
				if (!(isOnOrder(requestDTONPOS))){
					//nasir
				RequestDTO request = webServicesProcessorImpl.createRequest(requestDTONPOS.getOrder(), requestDTONPOS.getCustomer(), requestDTONPOS.getItemList(),
								requestDTONPOS.getPaymentList(), requestDTONPOS.getTask(),MpuWebConstants.NOT_IN_MPU);
				

				request.setCsmTaskDTO(requestDTONPOS.getCsmTaskDTO());
				request.setPackageList(requestDTONPOS.getPackageList());
				request.setWebActitivtyList(requestDTONPOS.getWebActitivtyList());
				request.setMpuActivityList(requestDTONPOS.getMpuActivityList());
				lRequestTemp.add(request);}
				
				else{
				lRequestTemp.add(requestDTONPOS);
			}

		}
			} else {

			int cnt = 0;
			int counter = 0;
			//Map<String, String> originalJsonMap = new HashMap<String, String>();
			for (RequestDTO requestDTONPOS : requestDTOList) {
				
				Boolean flag = Boolean.FALSE;
				for (ItemDTO order : listOrderDTO) {
					
 					if (requestDTONPOS.getOrder().getSalescheck().equalsIgnoreCase(order.getSalescheck())) {

						requestDTONPOS.getOrder().setRqtId(order.getRqtId());
						cnt = 0;
//						RequestDTO request = webServicesProcessorImpl.getRequestData(order.getRqtId(),
//										requestDTONPOS.getOrder().getStoreNumber(), "ITEM", "", "ALL");

						for (ItemDTO obj : requestDTONPOS.getItemList()) {
							counter = 0;
							for (ItemDTO iObj : listOrderDTO) {

								if (iObj.getSalescheck().equalsIgnoreCase(order.getSalescheck()) && order.getRqtId().equals(iObj.getRqtId()) && obj.getDivNum().equals(iObj.getDivNum()) && obj.getItem().equals(iObj.getItem()) 
										&& obj.getSku().equals(iObj.getSku()) &&  null != obj.getItemSeq() && obj.getItemSeq().equals(iObj.getItemSeq())) {
									
									requestDTONPOS.getItemList().get(cnt).setRqtId(order.getRqtId());
									requestDTONPOS.getItemList().get(cnt).setRqdId(iObj.getRqdId());
									requestDTONPOS.getItemList().get(cnt).setItemStatus(iObj.getItemStatus());
									requestDTONPOS.getItemList().get(cnt).setCurrentStatus(iObj.getItemStatus());
									requestDTONPOS.getItemList().get(cnt).setQtyRemaining(iObj.getQtyRemaining());
									requestDTONPOS.getItemList().get(cnt).setToLocation(iObj.getToLocation());
									requestDTONPOS.getItemList().get(cnt).setFromLocation(iObj.getFromLocation());
									requestDTONPOS.getItemList().get(cnt).setStockLocation(iObj.getStockLocation());
									requestDTONPOS.getItemList().get(cnt).setRequestStatus(iObj.getRequestStatus());
									
									/**
									 * JIRA-STORESYS-25041
									 * @author nkhan6
									 */
									requestDTONPOS.getItemList().get(cnt).setQty(iObj.getQty());
									
									if(null != iObj.getRequestType() && MpuWebConstants.LAYAWAY.equalsIgnoreCase(iObj.getRequestType())) {
										requestDTONPOS.getOrder().setRequestType(iObj.getRequestType());
										requestDTONPOS.getItemList().get(cnt).setRequestType(iObj.getRequestType());
										requestDTONPOS.getItemList().get(cnt).setLayawayFlag("Y");
										requestDTONPOS.getItemList().get(cnt).setLayawayType(iObj.getLayawayType());
									}
									
									if (requestDTONPOS.getCustomer().getCustomerId().equalsIgnoreCase(iObj.getCustomerId())) {
										logger.info("Customer Id found in DB", "");
										counter = 1;
										break;
									} else {
										logger.info("Customer Id not found in DB", "counter :" + counter);
										counter++;
									}
								}
							}
							
							if (counter > 1) {
								pickUpReturnDTO.setReturnDesc("Multiple Customer Exception. Help Request should be Raised.");
								return pickUpReturnDTO;
							}
							
							cnt++;
						}
						
						List<ItemDTO> lItemDTO = new ArrayList<ItemDTO>();
						for(ItemDTO iObj : requestDTONPOS.getItemList()) {
							
							if(!((MpuWebConstants.PICKED_UP).equals(iObj.getItemStatus()) 
									
									|| (MpuWebConstants.PICKUP_INITIATED.equals(iObj.getItemStatus()) && Integer.parseInt(iObj.getQtyRemaining()) == 0) 
									
									|| MpuWebConstants.CLOSED.equalsIgnoreCase(iObj.getItemStatus()) 
									
									|| MpuWebConstants.MOD_VERIFY.equalsIgnoreCase(iObj.getItemStatus())
									
									|| MpuWebConstants.EXPIRED.equalsIgnoreCase(iObj.getItemStatus())
									
									|| (MpuWebConstants.BINPENDING.equalsIgnoreCase(iObj.getItemStatus()) && MpuWebConstants.EXPIRED.equalsIgnoreCase(iObj.getRequestStatus())))) {
								
								if(null != iObj.getRqtId() && null != iObj.getRqtId())
								lItemDTO.add(iObj);
								
								//Generate Map for updating original_json
								//originalJsonMap.put(requestDTONPOS.getOrder().getRqtId(), requestDTONPOS.getOrder().getOriginalJson());
							}
						}
							
						if(lItemDTO.size() > 0) {
							
							requestDTONPOS.getItemList().clear();
							requestDTONPOS.setItemList(lItemDTO);
							
							lRequestTemp.add(requestDTONPOS);
						}
						
						flag = Boolean.TRUE;
						
						break;
					}
			}

				if (!flag) {
					
					Boolean onOrderFlag = Boolean.FALSE;
					for (ItemDTO obj1 : requestDTONPOS.getItemList()) {
						
						/*SimpleDateFormat dateStore = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
						String CurrTime = getTimeAccToTimeZone(new Date() + "", requestDTONPOS.getOrder().getStoreNumber(), "EEE MMM d HH:mm:ss zzz yyyy");
						Date CurrStoreTime = dateStore.parse(CurrTime);
						
						Date promiseDate = dateStore.parse(obj1.getItemPromisedDate());*/
						
						if((obj1.getItemPromisedDate()!=null && (StringUtils.isEmpty(obj1.getItemBinNumber())) && 
								("2".equalsIgnoreCase(obj1.getItemStatusCode()) || "3".equalsIgnoreCase(obj1.getItemStatusCode())))) {
							
							onOrderFlag = Boolean.TRUE;
							break;
						}
					}
					
					if(!onOrderFlag) {
						//nasir
						RequestDTO request = new RequestDTO();
						if(!requestDTONPOS.getOrder().getExchangeFlag() 
								&& !("RETURNIN5").equalsIgnoreCase(requestDTONPOS.getOrder().getRequestType()) 
								&& !("PICKED_UP").equalsIgnoreCase(requestDTONPOS.getOrder().getRequestStatus())) {
							request = webServicesProcessorImpl.createRequest(requestDTONPOS.getOrder(), requestDTONPOS.getCustomer(), requestDTONPOS.getItemList(),
									requestDTONPOS.getPaymentList(), requestDTONPOS.getTask(),MpuWebConstants.NOT_IN_MPU);
		
							request.setCsmTaskDTO(requestDTONPOS.getCsmTaskDTO());
							request.setPackageList(requestDTONPOS.getPackageList());
							request.setWebActitivtyList(requestDTONPOS.getWebActitivtyList());
							request.setMpuActivityList(requestDTONPOS.getMpuActivityList());
							lRequestTemp.add(request);
						}
						
					} else {
						
						lRequestTemp.add(requestDTONPOS);
					}

					
			}
		}
		
		lRequestTemp.addAll(exchangeOrderList);
		
			
			
		// Update original_json in Database
			
		//int noOfRows = pickUpServiceDAO.updateOriginalJson(originalJsonMap,pickUpDTO.getStoreNumber());
			
		}
		
		requestDTOList = new ArrayList<RequestDTO>();
		requestDTOList = lRequestTemp;
		
		//Checking for On Order
		for(RequestDTO obj: requestDTOList)
		{
			for(ItemDTO obj1 : obj.getItemList())
			{
				Boolean flag = Boolean.FALSE;
				if(!MpuWebConstants.LAYAWAY.equalsIgnoreCase(obj1.getRequestType()) && null != obj1.getItemPromisedDate()) {
					/*SimpleDateFormat dateStore = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
					String CurrTime = getTimeAccToTimeZone(new Date() + "", obj.getOrder().getStoreNumber(), "EEE MMM d HH:mm:ss zzz yyyy");
					Date CurrStoreTime = dateStore.parse(CurrTime);
					
					Date promiseDate = dateStore.parse(obj1.getItemPromisedDate());
					
					if((promiseDate.after(CurrStoreTime) || promiseDate.equals(CurrStoreTime)) && (StringUtils.isEmpty(obj1.getItemBinNumber())) && 
							("2".equalsIgnoreCase(obj1.getItemStatusCode()) || "3".equalsIgnoreCase(obj1.getItemStatusCode()))) {
						obj1.setItemStatus("ON ORDER");
						flag = Boolean.TRUE;
					}*/
					
					if((obj1.getItemPromisedDate()!=null && (StringUtils.isEmpty(obj1.getItemBinNumber())) && 
							("2".equalsIgnoreCase(obj1.getItemStatusCode()) || "3".equalsIgnoreCase(obj1.getItemStatusCode())))) {
						obj1.setItemStatus("ON ORDER");
						flag = Boolean.TRUE;
						/**
						 * For Multiline Item OnOrders
						 */
						//break;
					}
				} 
				if(!flag) {
					flag = Boolean.TRUE;
					//if (MpuWebConstants.LAYAWAY.equalsIgnoreCase(obj1.getRequestType())) {
					//Removed the check to calculate balance due of online layaway, as they come as web order
					
						
							BigDecimal amt = CommonUtils.getLayawayBalance(obj.getPaymentList());
							
							if(!amt.equals(BigDecimal.ZERO)) {
								obj1.setItemStatus("BALANCE DUE");
								flag = Boolean.FALSE;
							//}
					} 
					
					if(flag) {
						if (null != obj1.getItemStatus() 
								&& (MpuWebConstants.OPEN.equalsIgnoreCase(obj1.getItemStatus()) || MpuWebConstants.BINPENDING.equalsIgnoreCase(obj1.getItemStatus()))
								&& MpuWebConstants.WEB_SALE_SALESCHECK.equalsIgnoreCase(obj1.getSalescheck().substring(0, 3))) {
							
							obj1.setItemStatus("IN PROGRESS");
						} else {
							obj1.setItemStatus("AVAILABLE");
						}
					}
				}
				
			}
		}
		
		
		
		Collections.sort(requestDTOList, new Comparator<RequestDTO>() {

			public int compare(RequestDTO o1, RequestDTO o2) {
				SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
				try {
					if(null != o1.getOrder().getTimeAssigned() && null != o2.getOrder().getTimeAssigned())
					return df.parse(o2.getOrder().getTimeAssigned()).compareTo(df.parse(o1.getOrder().getTimeAssigned()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return 0;
				
			}
	    });


		// Adding to EhCache
		if(pickUpLoc.equalsIgnoreCase(MpuWebConstants.SHOPIN)){
			addRequestToEhCache(requestDTOList, pickUpDTO.getStoreNumber(), pickUpDTO.getKiosk(), MpuWebConstants.PICK_UP_REQUEST, pickUpLoc.toUpperCase(), "", numType);
		}else{
			addRequestToEhCache(requestDTOList, pickUpDTO.getStoreNumber(), pickUpDTO.getKiosk(), MpuWebConstants.PICK_UP_REQUEST, pickUpLoc.toUpperCase(), identifierValue, numType);
		}
		
		List<RequestDTO> itemDTOReturn = new ArrayList<RequestDTO>();
		
		// check for shopin orders.		
		if(pickUpLoc.equalsIgnoreCase(MpuWebConstants.SHOPIN)){
			for (RequestDTO requestDTO : requestDTOList){
				itemDTOReturn.add(requestDTO);
			}
			pickUpReturnDTO.setReturnDesc("Shopin Pickup");
			pickUpReturnDTO.setResponseData(itemDTOReturn);
			return pickUpReturnDTO;
		}//shopin check ends with returning orders
		
		if (MpuWebConstants.SALESCHECK.equalsIgnoreCase(numType)) {

			// Checking for installer pick up
			for (RequestDTO requestDTO : requestDTOList) {

				if (requestDTO.getOrder().getSalescheck().equalsIgnoreCase(identifierValue)	&& requestDTO.getOrder().isInstallerPickOrder()) {

					itemDTOReturn.add(requestDTO);
					break;
				}
			}

			// Checking for third party pick up
			for (RequestDTO requestDTO : requestDTOList) {

				if (requestDTO.getOrder().getSalescheck().equalsIgnoreCase(identifierValue) && identifierValue.substring(0, 3).equalsIgnoreCase("093")
						&& requestDTO.getOrder().isSecureIndicator()) {

					itemDTOReturn.add(requestDTO);
				}

			}
		}
		
		if (itemDTOReturn.size() != 0) {

			pickUpReturnDTO.setReturnDesc("Installer-3rdParty");
			pickUpReturnDTO.setResponseData(itemDTOReturn);

			return pickUpReturnDTO;

		}
		
		//Removing installer pick up orders and third party pick up orders while searching from other salescheck and phone numbers
			
		Iterator<RequestDTO> requestDTO = requestDTOList.iterator();
		while(requestDTO.hasNext()) {
			
		    if(requestDTO.next().getOrder().isInstallerPickOrder())
		    requestDTO.remove();
		}
		
		
		
//		// Skip credit card flow in case of curbside
//		if (pickUpLoc.equalsIgnoreCase(MpuWebConstants.CURBSIDE)) {
//			return pickUpReturnDTO;
//		}

		if (MpuWebConstants.SALESCHECK.equalsIgnoreCase(numType)	|| MpuWebConstants.PHONE.equalsIgnoreCase(numType) || MpuWebConstants.NAME_ADDRESS.equalsIgnoreCase(numType)) {

			List<String> maximumTenderCardList = getMaximumTenderCard(requestDTOList, numType, identifierValue); // Checking for maximum tender card

			if (!"".equals(maximumTenderCardList.get(0))) {

				String exceptionValue = "CC Swipe Exception.~";
				
				if("true".equalsIgnoreCase(maximumTenderCardList.get(1)))
					exceptionValue = "CC Swipe Exception with other cards.~";
				
				pickUpReturnDTO.setReturnDesc(DJUtilities.concatString(exceptionValue, maximumTenderCardList.get(0)));
				pickUpReturnDTO.setResponseData(getAllSecuredAndTopTenderCardUnsecuredItems(maximumTenderCardList.get(0), requestDTOList));

				return pickUpReturnDTO;

			} else {

				for (RequestDTO rqt : requestDTOList) {
					itemDTOReturn.add(rqt);
				}
				pickUpReturnDTO.setReturnDesc("Show Item Details.");
				pickUpReturnDTO.setResponseData(itemDTOReturn);

				return pickUpReturnDTO;
			}
		}

		// Numtype = Credit Card
		pickUpReturnDTO.setReturnDesc("Show Item Details for Credit Card.");
		
		pickUpReturnDTO.setResponseData(getAllSecuredAndTopTenderCardUnsecuredItems(identifierValue, requestDTOList));
		
		} catch (DJException djEx) {
			
			throw djEx;
		
		} catch (Exception e) {
			logger.error("getAllItemsForPickUp",e);
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
		
		logger.info("getAllItemsForPickUp", "Exit getAllItemsForPickUp pickUpReturnDTO :" + pickUpReturnDTO);
		
		return pickUpReturnDTO;

		// Step 3 Fetching of data ends -------------------------
	}

	public boolean isDuplicateSalescheck(List<RequestDTO> requestDTOList, String numType) {
		logger.info("isDuplicateSalescheck", "Entering isDuplicateSalescheck requestDTOList : " + requestDTOList + "numType : " + numType);
		HashSet<String> hs = new HashSet<String>();
/*		
		if(MpuWebConstants.PHONE.equalsIgnoreCase(numType)) {
			for (RequestDTO requestDTO : requestDTOList) {

				if (hs.add(requestDTO.getCustomer().getAddressId()) || hs.add(requestDTO.getCustomer().getCustomerId())) {
					if(hs.size()>1){
						logger.info("isDuplicateSalescheck", "Exit isDuplicateSalescheck true :"+ Boolean.TRUE);
						return Boolean.TRUE;
					}
				}
			}
			logger.info("isDuplicateSalescheck", "Exit isDuplicateSalescheck false : "+ Boolean.FALSE);
			return Boolean.FALSE;
			
		} else */
		if(MpuWebConstants.SALESCHECK.equalsIgnoreCase(numType)){

			for (RequestDTO requestDTO : requestDTOList) {
	
				if (!(hs.add(requestDTO.getOrder().getSalescheck()))) {
					logger.info("isDuplicateSalescheck", "Exit isDuplicateSalescheck true :"+ Boolean.TRUE);
					return Boolean.TRUE;
				}
			}
			logger.info("isDuplicateSalescheck", "Exit isDuplicateSalescheck false : "+ Boolean.FALSE);
			return Boolean.FALSE;
		} else {
						
			HashSet<CustomerDTO> hsCust = new HashSet<CustomerDTO>();
			HashSet<String> addressId = new HashSet<String>();
			HashSet<String> customerId = new HashSet<String>();
			
			for (RequestDTO obj2 : requestDTOList) {
				
				if(addressId.add(obj2.getCustomer().getAddressId()) || customerId.add(obj2.getCustomer().getCustomerId())) {
					customerId.add(obj2.getCustomer().getCustomerId());
					addressId.add(obj2.getCustomer().getAddressId());
					hsCust.add(obj2.getCustomer());
					if(hsCust.size()>1){
						logger.info("isDuplicateSalescheck", "Exit isDuplicateSalescheck true :"+ Boolean.TRUE);
						return Boolean.TRUE;
					}
				}
				
			}
			
			return Boolean.FALSE;
		}
	}
		

	public List<RequestDTO> resolveDuplicacy(List<RequestDTO> requestDTOList, String value, String type) throws Exception {

		logger.info("resolveDuplicacy",	"Entering resolveDuplicacy requestDTOList : " + requestDTOList	+ " -- value : " + value + " -- type : " + type);

		List<RequestDTO> listRequestDTOTemp = new ArrayList<RequestDTO>();
		if (type.equalsIgnoreCase("date")) {

			for (RequestDTO requestDTO : requestDTOList) {
				if(null != requestDTO.getItemList().get(0).getItemCreatedDate() || !StringUtils.isEmpty(requestDTO.getItemList().get(0).getItemCreatedDate())) {
				    String createTime = getTimeAccToTimeZone(requestDTO.getItemList().get(0).getItemCreatedDate(), requestDTOList.get(0).getOrder().getStoreNumber(), "MM/dd/yyyy");
	//				logger.info("resolveDuplicacy", "createTime : " + createTime);
				
				    if (value.equals(createTime)) {
						listRequestDTOTemp.add(requestDTO);
					}
				}
			}
			logger.info("resolveDuplicacy", "Exit resolveDuplicacy listRequestDTOTemp : "+ listRequestDTOTemp);
			return listRequestDTOTemp;
		} /*else if(type.equalsIgnoreCase("address")){

			for (RequestDTO requestDTO : requestDTOList) {

				String sAddress = "";
				if(null != requestDTO.getCustomer().getAddress1() && !"".equals(requestDTO.getCustomer().getAddress1())) {
					sAddress = DJUtilities.concatString(requestDTO.getCustomer().getAddress1(), " ");
				}
				if(null != requestDTO.getCustomer().getAddress2() && !"".equals(requestDTO.getCustomer().getAddress2())) {
					sAddress = DJUtilities.concatString(sAddress, requestDTO.getCustomer().getAddress2());
				}

				if (value.equalsIgnoreCase(sAddress.trim())) {

					listRequestDTOTemp.add(requestDTO);
				}
			}
			logger.info("resolveDuplicacy", "Exit resolveDuplicacy listRequestDTOTemp : "+ listRequestDTOTemp);
			return listRequestDTOTemp;
		}*/
		//fetch all the orders for a customer
		else if(type.equalsIgnoreCase("customer")){
			for (RequestDTO requestDTO : requestDTOList) {
				String[] values=value.split(",");
				if (requestDTO.getCustomer().getCustomerId().equalsIgnoreCase(values[0]) || requestDTO.getCustomer().getAddressId().equalsIgnoreCase(values[1])) {
					
					listRequestDTOTemp.add(requestDTO);
				}
			}
			return listRequestDTOTemp;
		}
		
		//remove duplicate orders with SCN on the basis of sywr.	
		else if(type.equalsIgnoreCase("sywrNumber")){   // remove on duplicate orders on the basis of sywrNumber for shopin.			
			boolean duplicateSalescheck = isDuplicateSalescheck(requestDTOList,"SALESCHECK");			
			for (RequestDTO requestDTO : requestDTOList) {
				String sywrNumber = "";
				if(null != requestDTO.getCustomer().getSywNumber() && !"".equals(requestDTO.getCustomer().getSywNumber())) {
					sywrNumber = DJUtilities.concatString(requestDTO.getCustomer().getSywNumber(), " ");
				}
				listRequestDTOTemp.add(requestDTO);
				if(duplicateSalescheck){
					if (value.equalsIgnoreCase(sywrNumber.trim())) {
						listRequestDTOTemp.add(requestDTO);
					}
				}				
			}
			return listRequestDTOTemp;
		}
		return null;
	}

	public List<String> getMaximumTenderCard(List<RequestDTO> requestDTOList,String numType, String identifierValue) {
		
		List<String> maximumTenderCardList = new ArrayList<String>();
		String multipleCards = "false";
		logger.info("getMaximumTenderCard",	"Entering getMaximumTenderCard requestDTOList : " + requestDTOList	+ " -- numType : " + numType + " -- identifierValue : " + identifierValue);
		String maximumTenderCard = "";
		Boolean unsecuredFlag = Boolean.FALSE;
		
		if (numType.equalsIgnoreCase(MpuWebConstants.SALESCHECK))
			for (RequestDTO requestDTO : requestDTOList) {
				if (requestDTO.getOrder().getSalescheck().equals(identifierValue)) {
					if(!requestDTO.getOrder().isSecureIndicator()) {
						maximumTenderCard = CommonUtils.getTopTenderCard(requestDTO.getPaymentList());
						unsecuredFlag = Boolean.FALSE;
						break;
					} else {
						unsecuredFlag = Boolean.TRUE;
						break;
					}
				} else {
					unsecuredFlag = Boolean.TRUE;
				}
			}

		if (numType.equalsIgnoreCase(MpuWebConstants.PHONE) || numType.equalsIgnoreCase(MpuWebConstants.NAME_ADDRESS) || unsecuredFlag) {
			List<PaymentDTO> paymentListTemp = new ArrayList<PaymentDTO>();
			for (RequestDTO requestDTO : requestDTOList) {
				if(!(requestDTO.getOrder().isSecureIndicator()))
				paymentListTemp.addAll(requestDTO.getPaymentList());
			}
			maximumTenderCard = CommonUtils.getTopTenderCard(paymentListTemp);
		}

		logger.info("getMaximumTenderCard", "Exit getMaximumTenderCard maximumTenderCard : "+ maximumTenderCard);

		
		//Checking for multiple credit card
		HashSet<String> hs = new HashSet<String>();
		for (RequestDTO requestDTO : requestDTOList) {
			List<PaymentDTO> paymentListTemp = new ArrayList<PaymentDTO>();
			paymentListTemp.addAll(requestDTO.getPaymentList());
			
				for(PaymentDTO payment : paymentListTemp) {
					if(!(requestDTO.getOrder().isSecureIndicator())) {
						if(hs.add(payment.getAccountNumber())) {
							if(hs.size()>1)
							multipleCards = "true";						
						}
					}
				}
		}


		maximumTenderCardList.add(maximumTenderCard);
		maximumTenderCardList.add(multipleCards);
		
		return maximumTenderCardList;

	}
	
	public Boolean addRequestToEhCache(List<?> lRequestDTO, String storeNo, String kiosk, String reqType, String initiatedFrom, String numVal, String numType) throws Exception {
		
		logger.debug("addToEhCache ::","Entering addRequestToEhCache storeNo : " + " -- storeNo : " + storeNo + " -- kiosk : " + kiosk+ " -- reqType : " + reqType 
				+ " -- initiatedFrom : " + initiatedFrom + " -- numVal : " + numVal+ " -- numType : " + numType);
		
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("pickupCache");
		
		requestQueueCache.put(DJUtilities.concatString(storeNo, "-", kiosk, "-", reqType, "-", initiatedFrom, "-", numVal,  "-", numType), lRequestDTO);
		
		logger.debug("addToEhCache ::","Exiting addRequestToEhCache");
		
		return Boolean.TRUE;
	}
	
	@SuppressWarnings("unchecked")
	public List<RequestDTO> getAllRequestsFromEhCache(String storeNumber, String requestType, String kiosk, String initiatedFrom, String numVal, String numType) throws DJException {
		
		logger.info("getAllRequestsFromEhCache",	"Entering getAllRequestsFromEhCache storeNumber : " + storeNumber	+ " -- requestType : " + requestType + " -- kiosk : " 
		+ kiosk + " -- initiatedFrom : " + initiatedFrom + " -- numVal : " + numVal+ " -- numType : " + numType);
		
		EhCacheCache requestQueueCache = (EhCacheCache) cacheManager.getCache("pickupCache");

		String queueKey = DJUtilities.concatString(storeNumber, "-", kiosk,"-", requestType, "-", initiatedFrom, "-", numVal,  "-", numType);
		
		List<RequestDTO> requestDTOList = new ArrayList<RequestDTO>();

		if (null != requestQueueCache.get(queueKey)) {

			requestDTOList = (List<RequestDTO>) requestQueueCache.get(queueKey).get();
		}
		
		logger.info("getAllRequestsFromEhCache", "Exit getAllRequestsFromEhCache requestDTOList :" + requestDTOList);
		
		return requestDTOList;
	}

	// Checking if order secured or not
	public boolean isUnSecuredOrder(RequestDTO requestDTO) {
		logger.info("isUnSecuredOrder",	"Entering isUnSecuredOrder requestDTO : " + requestDTO);
		boolean isUnSecured = false;
		try {
			if (requestDTO != null
					&& !requestDTO.getOrder().isSecureIndicator()) {
				if (requestDTO.getPaymentList() != null
						&& requestDTO.getPaymentList().size() > 0) {
					for (PaymentDTO payment : requestDTO.getPaymentList()) {
						if (payment.getType().equalsIgnoreCase("1")
								|| payment.getType().equalsIgnoreCase("2")
								|| payment.getType().equalsIgnoreCase("8")
								|| payment.getType().equalsIgnoreCase("D")
								|| payment.getType().equalsIgnoreCase("I")
								|| payment.getType().equalsIgnoreCase("J")
								|| payment.getType().equalsIgnoreCase("P")) {
							logger.info("Order is unsecured "
									+ requestDTO.getOrder().getSalescheck(),
									payment);
							isUnSecured = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception occured :", e);
		}
		logger.info("isUnSecuredOrder", "Exit isUnSecuredOrder isUnSecured : "+ isUnSecured);
		return isUnSecured;
	} 

	public List<RequestDTO> getAllSecuredAndTopTenderCardUnsecuredItems(String maximumTenderCard, List<RequestDTO> listRequestDTOTemp) {
		
		logger.info("getAllSecuredAndTopTenderCardUnsecuredItems",	"Entering getAllSecuredAndTopTenderCardUnsecuredItems listRequestDTOTemp : " + listRequestDTOTemp + "maximumTenderCard : " +maximumTenderCard);
		List<RequestDTO> itemListDTO = new ArrayList<RequestDTO>();
		
		for (RequestDTO requestDTO : listRequestDTOTemp) {

			if (!(requestDTO.getOrder().isSecureIndicator()) && !(maximumTenderCard.equalsIgnoreCase(""))) {
				
				for(PaymentDTO payment : requestDTO.getPaymentList()) {
					
					String maskedMaximumTenderCard = CommonUtils.getMaskedCardNumber(maximumTenderCard);
					
					if (maskedMaximumTenderCard.equalsIgnoreCase(payment.getAccountNumber())) {
						itemListDTO.add(requestDTO);
						break;
					}
				}
				
			} else {
				itemListDTO.add(requestDTO);
			}
		}
		logger.info("getAllSecuredAndTopTenderCardUnsecuredItems", "Exit getAllSecuredAndTopTenderCardUnsecuredItems itemListDTO : " + itemListDTO);
		return itemListDTO;
	}

	public List<ItemDTO> getAllSecuredItems(List<RequestDTO> listRequestDTOTemp) {
		logger.info("getAllSecuredItems",	"Entering getAllSecuredItems listRequestDTOTemp : " + listRequestDTOTemp );
		List<ItemDTO> itemListDTO = new ArrayList<ItemDTO>();
		for (RequestDTO requestDTO : listRequestDTOTemp) {

			if (requestDTO.getOrder().isSecureIndicator()) {
				itemListDTO.addAll(requestDTO.getItemList());
			}
		}
		logger.info("getAllSecuredItems", "Exit getAllSecuredItems itemListDTO : "+ itemListDTO);
		return itemListDTO;
	}

	// Calculating Top Tender Card
	
	/*public String getTopTenderCard(List<PaymentDTO> paymentList) {
		logger.info("getTopTenderCard",	"Entering getTopTenderCard paymentList : " + paymentList );
		String topTenderCard = "";
		if (paymentList != null) {
			logger.info("getTopTenderCard", topTenderCard);
			if (paymentList.size() == 1) {
				topTenderCard = paymentList.get(0).getAccountNumber();
			} else {
				Map<String, BigDecimal> paymentMap = new HashMap<String, BigDecimal>();
				for (PaymentDTO payment : paymentList) {
					if (!payment.getType().equalsIgnoreCase("9") && !payment.getAccountNumber().equalsIgnoreCase("000000XXXXXX0000")) {
						if (!paymentMap.containsKey(payment.getAccountNumber())) {
							paymentMap.put(payment.getAccountNumber(),
									payment.getAmount());
						} else {
							BigDecimal newAmt = paymentMap.get(payment.getAccountNumber()).add(payment.getAmount());
							paymentMap.remove(payment.getAccountNumber());
							paymentMap.put(payment.getAccountNumber(), newAmt);
						}
					}
				}

				if (paymentMap != null) {
					BigDecimal maxAmt = new BigDecimal(0);
					for (Map.Entry<String, BigDecimal> map : paymentMap
							.entrySet()) {
						if (map.getValue().compareTo(maxAmt) == 1) {
							maxAmt = map.getValue();
							topTenderCard = map.getKey();
						}
					}
				}
			}
		}
		logger.info("getTopTenderCard", "Exit getTopTenderCard topTenderCard : " + topTenderCard);
		return topTenderCard;
	}*/

//	private boolean updateRequestToEhCache(List<RequestDTO> lRequestDTO,
//			String storeNumber, String requestType, String kiosk) {
//
//		EhCacheCache requestQueueCache = (EhCacheCache) cacheManager
//				.getCache("mpuQueueCache");
//
//		String queueKey = DJUtilities.concatString(storeNumber, "-", kiosk,
//				"-", requestType);
//
//		Map<String, List<RequestDTO>> queueMap = new HashMap<String, List<RequestDTO>>();
//		queueMap.put(queueKey, lRequestDTO);
//
//		requestQueueCache.put(queueKey, queueMap);
//
//	return true;
//	}

	// private boolean clearRequestToEhCache(List<RequestDTO>
	// lRequestDTO,boolean cacheRemoveFlag,String storeNumber,String
	// requestType, String kiosk){
	//
	// Map<String, List<RequestDTO>> queueMap;
	// EhCacheCache requestQueueCache = (EhCacheCache)
	// cacheManager.getCache("mpuQueueCache");
	// String queueKey = storeNumber + "-" + kiosk + "-" + requestType;
	// queueMap = (HashMap<String, List<RequestDTO>>) requestQueueCache
	// .get(queueKey).get();
	// if(null!=queueMap){
	// if(cacheRemoveFlag){
	// queueMap.remove(queueKey);
	// }else{
	// queueMap.put(queueKey, lRequestDTO);
	// }
	// }

	// requestQueueCache.put(queueKey, queueMap);
	// return true;
	//
	// }

	public boolean checkForAnyUnsecureOrder(List<RequestDTO> listRequestDTOTemp) {
		logger.info("checkForAnyUnsecureOrder",	"Entering checkForAnyUnsecureOrder listRequestDTOTemp : " + listRequestDTOTemp );
		for (RequestDTO requestDTO : listRequestDTOTemp) {

			if (!(requestDTO.getOrder().isSecureIndicator())) {
				logger.info("checkForAnyUnsecureOrder", "Exit checkForAnyUnsecureOrder true : " + true);
				return true;
			}
		}
		logger.info("checkForAnyUnsecureOrder", "Exit checkForAnyUnsecureOrder false : " + false);
		return false;
	}

	// Initiate Pick up for items
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<String> initiatePickUpForItemsAndGetLockerItems(PickUpSelectedItems obj) throws DJException {
		/*boolean insertFlag = false;
		String RI5AuthCode = null;*/

		ObjectMapper mapper=new ObjectMapper();
		try {
			logger.info("PickUpSelectedItems is",mapper.writeValueAsString(obj));
		} catch (Exception e) {
			logger.error("initiatePickUpForItemsAndGetLockerItems", e);
		}
		
		List<RequestDTO> requestDTOListTemp = getAllRequestsFromEhCache(obj.getStoreNum(), MpuWebConstants.PICK_UP_REQUEST, obj.getKiosk(), obj.getPickUpSource().toUpperCase(), obj.getSearchValue(), obj.getSearchMethod());			
			
		Map<String, Boolean> securedFlagMap = new HashMap<String, Boolean>(); 
		List<PaymentDTO> allPaymentList=new ArrayList<PaymentDTO>();
		List<String>  rqtIdList = new ArrayList<String>();
		
		/**
		 * For Refreshing the original JSON from the one which come from NPOS
		 * @author nkhan6
		 */
		Map<String,String> rqtIdOrgJsonMap = new HashMap<String, String>();
		Map<String,String> rqdIdUpcMap = new HashMap<String, String>();
		
		for (String items : obj.getItemsSelectedForPickUp()) {		
			if(!securedFlagMap.containsKey(items.split(";")[0])) {
				for(RequestDTO request : requestDTOListTemp) {		
					String rqtId=request.getOrder().getRqtId();
					if(null != request && items.split(";")[0].trim().equalsIgnoreCase(rqtId)) {
						securedFlagMap.put(rqtId, request.getOrder().isSecureIndicator());
						
						
						
						if(!rqtIdList.contains(rqtId)){
							rqtIdList.add(rqtId);
							List<PaymentDTO> paymentList=request.getPaymentList();
							ArrayList<PaymentDTO> paymentFinal=new ArrayList<PaymentDTO>();
							
							if(paymentList!=null){
								for(PaymentDTO payment:paymentList){
									payment.setRqtId(rqtId);
									paymentFinal.add(payment);
									
									
								}
								
							}
							if(!paymentFinal.isEmpty()){
								allPaymentList.addAll(paymentFinal);
							}
							
							/**
							 * For Refreshing the original JSON from the one which come from NPOS
							 * @author nkhan6
							 */
							if(null!=request.getOrder()){
								rqtIdOrgJsonMap.put(rqtId, request.getOrder().getOriginalJson());
							}
							
							if(null!=request.getItemList()){
								for(ItemDTO itm:request.getItemList()){
									String[] itemArray=items.split(";");
									if(itemArray.length>7){
										if(itemArray[4].equals(itm.getDivNum()) && itemArray[5].equals(itm.getItem()) 
												&& itemArray[6].equals(itm.getSku()) && null!=itm.getUpc()){
											rqdIdUpcMap.put(itemArray[1], itm.getUpc());
										}
									}
								}
							}
							
						}
						break;
					}				
				}
			}
		}
		if(!rqtIdList.isEmpty()) {
			pickUpServiceDAO.updateSecuredDBFlag(securedFlagMap,obj.getStoreNum());
			pickUpServiceDAO.deleteAllPaymentInfo(rqtIdList, obj.getStoreNum());
			mpuWebServiceDAOImpl.createPaymentList(allPaymentList, 0, obj.getStoreNum());
			
			/**
			 * For refreshing the OriginalJson Coming from MPU
			 */
			int rowsUpdated = pickUpServiceDAO.updateOriginalJson(rqtIdOrgJsonMap, obj.getStoreNum());
			logger.info("Rows updated for Refreshing originalJSON = ", rowsUpdated);
			if(!rqdIdUpcMap.isEmpty()){
				
				int upcUpdated = pickUpServiceDAO.updateUPC(rqdIdUpcMap,obj.getStoreNum());
				logger.info("Rows updated for upcUpdate = ", upcUpdated);
			}
		
				
			/* Insertint package if not present */
			
			List<Map<String, Object>> packageList = pickUpServiceDAO.getPackageInfoForRqtList(rqtIdList,obj.getStoreNum());
			if(null != packageList && !packageList.isEmpty()) {
				for(Map<String, Object> map1 : packageList) {
					rqtIdList.remove(map1.get("rqt_id") + "");
				}
			}
			
			List<String> salescheckPackageList = new ArrayList<String>();
			Map<String, String> rqtSalescheckMap = new HashMap<String,String>();
			
			for(String rqtId : rqtIdList) {
				for(RequestDTO req : requestDTOListTemp) {
					logger.info("initiatePickUpForItemsAndGetLockerItems ==req.getOrder().getRqtId()===req.getOrder().getRequestType()==", req.getOrder().getRqtId()+"=="+req.getOrder().getRequestType());
					if(null!=req.getOrder().getRqtId() && req.getOrder().getRqtId().equalsIgnoreCase(rqtId) && MpuWebConstants.LAYAWAY.equalsIgnoreCase(req.getOrder().getRequestType())) {
						salescheckPackageList.add(req.getOrder().getSalescheck());
						rqtSalescheckMap.put(rqtId, req.getOrder().getSalescheck());
						break;
					}				
				}
			}
			
			// Fetch Package List from mcp_workflow
			List<PackageDTO> packageInfoList = new ArrayList<PackageDTO>();
			if(!salescheckPackageList.isEmpty()) {
				packageInfoList = pickUpServiceDAO.getPackageInfoFromDB(salescheckPackageList,obj.getStoreNum());
			}
			
			if(null!=packageInfoList && !packageInfoList.isEmpty()) {
				
				for(PackageDTO packageInfo : packageInfoList) {
					for(Map.Entry<String, String> map : rqtSalescheckMap.entrySet()) {
						if(map.getValue().equalsIgnoreCase(packageInfo.getSalescheck())) {
							packageInfo.setRqtId(map.getKey());
							break;
						}
					}
				}
				
				// Insert into request_package
				pickUpServiceDAO.insertPackageInfo(packageInfoList, obj.getStoreNum());
			}
		
		}
		
		/* Insertint package if not present ends*/ 
			
			/*List<String> rqtIds = new ArrayList<String>();
			for (String items : obj.getItemsSelectedForPickUp()) {
				rqtIds.add(items.split(";")[0]);
			}
			List<Map<String, Object>> insertFlag = pickUpServiceDAO.checkForPaymentList(rqtIds,obj.getStoreNum());*/
			
			//noOfRows = mpuWebServiceDAOImpl.createMultiplePaymentList(paymentListMap,obj.getStoreNum());
		
		
		// If Cache is empty , fetching data from DB
		if(null == requestDTOListTemp || requestDTOListTemp.isEmpty()) {
			
			List<String> status = Arrays.asList(MpuWebConstants.OPEN,MpuWebConstants.EXPIRED,MpuWebConstants.COMPLETED);
			
			for (String obj1 : obj.getItemsSelectedForPickUp()) {
				String fields = MpuWebConstants.ITEM + "," + MpuWebConstants.IDENTIFIER;
				RequestDTO request = webServicesProcessor.getRequestData(obj1.split(";")[0], obj.getStoreNum(), fields, obj1.split(";")[1], status);
				
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				try {
					Date createDate1 = dateFormat2.parse(request.getOrder().getCreateTimestamp().substring(0, 19));
					request.getOrder().setTimeAssigned(dateFormat1.format(createDate1));
				} catch (ParseException e) {
					DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
					throw djException;
				}
				
				requestDTOListTemp.add(request);
			}
		}
		
		
		String storeNum = obj.getStoreNum();
		List<String> kioskLockerItems = new ArrayList<String>();
		List<ItemDTO> itemListToBeUpdated = new ArrayList<ItemDTO>();
		
		/**
		 * For local testing by Nasir
		 * Please do not delete
		 */
		/*String rqd = obj.getItemsSelectedForPickUp().get(0).split(";")[1];
		List<String> status = new ArrayList<String>();
		status.add("OPEN");
		itemListToBeUpdated = mpuWebServiceDAOImpl.getItemList(obj.getStoreNum(),
				obj.getSearchValue(), rqd,status, false);*/
		/*******End for local testing********/
		
		for (String items : obj.getItemsSelectedForPickUp()) {
			Boolean workedFlag =  Boolean.FALSE;
			for (RequestDTO request : requestDTOListTemp) {		
				for (ItemDTO item : request.getItemList()) {
					if (null != item && items.split(";")[1].equalsIgnoreCase(item.getRqdId())) {
						
					

						//end code
						item.setItemQuantityRequested(items.split(";")[3]);
						item.setRequestType(request.getOrder().getRequestType());
						//Exchange related code
						if(request.getOrder().getExchangeFlag()) {
/*							insertFlag = true;
							RI5AuthCode = request.getOrder().getIdentifierType(); 
							exchangeItems.add(item);*/							
							item.setRequestType(MpuWebConstants.EXCHANGEIN5);
							item.setCommentList(request.getOrder().getIdentifierType());
						}
						item.setCreatedBy(obj.getPickUpSource());
						itemListToBeUpdated.add(item);						
						LockerDTO lockerInfo = lockerServiceDAOImpl.getPinNumberFromSalescheck(item.getSalescheck(),item.getStoreNumber(),request.getOrder().getTimeAssigned());
						if(null != lockerInfo)
						{
							if(null != lockerInfo.getPinNo()) {
								webServicesProcessorImpl.printLockerTicket(lockerInfo.getStoreNo(),lockerInfo.getPinNo(),lockerInfo.getCustomerName(),obj.getKiosk());
							} else {
								// Create Help Request
							}
							
							// Updating locker table
							LockerDTO lockerDTO = new LockerDTO();
							lockerDTO.setStatus(MpuWebConstants.PICKUP_INITIATED);
							lockerDTO.setSalescheckNo(item.getSalescheck());
							lockerDTO.setLockerNo(lockerInfo.getLockerNo());
							lockerDTO.setStoreNo(lockerInfo.getStoreNo());
							String CurrTime = getTimeAccToTimeZone(new Date() + "", item.getStoreNumber(), "yyyy-MM-dd HH:mm:ss");
							lockerDTO.setPickedupInitiatedDate(CurrTime);
							lockerDTO.setCustomerName(lockerInfo.getCustomerName());
							lockerServiceDAOImpl.updateLockerOrderStatus(lockerDTO);
							
							if(MpuWebConstants.N.equalsIgnoreCase(obj.getCurbside())) 
							kioskLockerItems.add(item.getRqdId());
						}	
						workedFlag = Boolean.TRUE;
						break;
					}
				}
				if(workedFlag)
				break;
			}
		}
		
		/**
		 * Code for decrementing stock of items whose pick up is initiated
		 * before the item is BINNED or STAGED.
		 * @author nkhan6 
		 */
		/*
		 * openItems list contains rqd_id list of item which were never binned or staged
		 */
		
		List<String> openItems = pickUpServiceDAO.getOpenItemsFromDb(itemListToBeUpdated);
		logger.info("==openItems==", Arrays.toString(openItems.toArray()));
		for(ItemDTO itm :itemListToBeUpdated){
				if(null!=openItems && openItems.contains(itm.getRqdId())){
					ItemDTO itemCpy = itm;
					/**
					 * Since we need to decrement stock by 
					 * the qty for which pickup is initiated
					 */
					itemCpy.setQty(itm.getItemQuantityRequested());
					
					logger.info("initiatePickUpForItemsAndGetLockerItems==StockLocation decrement For " +
							"- RqdId=", itemCpy.getRqdId()+" Status="+itemCpy.getItemStatus()+
							" qty ="+itemCpy.getQty());
					mpuWebServiceDAOImpl.decrementItemStock(itemCpy);
				}
				
			
		}
		
		
		logger.info("PickUpSource == ", obj.getPickUpSource().toUpperCase());
		if("CURBSIDE".equals(obj.getPickUpSource().toUpperCase())) {
			logger.info("Curbside localtion= ", obj.getCurbside());
			pickUpServiceDAO.initiatePickUpForItems(itemListToBeUpdated,storeNum,obj.getCurbside()); 
		} else {
			logger.info("Kiosk localtion= ", obj.getKiosk());
			pickUpServiceDAO.initiatePickUpForItems(itemListToBeUpdated,storeNum,obj.getKiosk());
		}
		
		/**
		 * Getting requestType and queueType Mapping
		 */
		Map<String, String> requestTypeQueueMap = webServicesProcessorImpl.getRequestQueueMap(storeNum);
		
		// Changing request type to PICKUP
		for(ItemDTO item : itemListToBeUpdated) {
			
			/**
			 * Removing open items from Ehcache as this should not 
			 * be shown in queue list
			 */
			List<String> rqdList = new ArrayList<String>();
			rqdList.add(item.getRqdId());
			if(MpuWebConstants.LAYAWAY.equalsIgnoreCase(item.getRequestType())){
				//setting the request type as LAYAWAYS since both LAYAWAYS and LAYAWAYF both are in the same queue
				//except the online layaway which has request type as BINWEB
				item.setRequestType(MpuWebConstants.LAYAWAYS);
			}
			String queueType = requestTypeQueueMap.get(item.getRequestType());
			webServicesProcessorImpl.removeItemsFromCache(rqdList, storeNum, queueType);
			
			/******Removing from cache ended here*******/
			
/*			if(insertFlag){
				item.setRequestType(MpuWebConstants.EXCHANGEIN5);
			}
			else{
				item.setRequestType(MpuWebConstants.PICKUP);
			}*/
			
		}
		pickUpServiceDAO.initiatePickUpActivityForItems(itemListToBeUpdated, storeNum,"CREATE");

		/*code for mod notification when no user is logged-in begin 
		 * 
		*/
			sendMPUserviceWarningMsg(obj.getStoreNum());	
		/*	code for mod notification when no user is logged-in end	
		*/
		
		// Creating ActivityDTO
		List<MPUActivityDTO> activityList = createActivityDTO(obj,kioskLockerItems,requestDTOListTemp);
		
		//Inserting into request_mpu_trans and request_mpu_details
		associateActivityServicesProcessor.insertMPUActivityData(activityList);
		
/*		//Inserting into request_activity table for Exchange in 5
		for(RequestDTO requestDTO : requestDTOListTemp){
			
			pickUpServiceDAO.initiatePickUpActivityForItems(requestDTO.getItemList(), requestDTO.getOrder().getStoreNumber(),"CREATE");
		}*/
		
		
		logger.info("EI5 pick up","Starting the insertion of Return Order in MPU tables if Insert Flag is true");
		
		List<String> ei5ProcessedRqtId =  new ArrayList<String>(); 
		for(ItemDTO items : itemListToBeUpdated) {
			if(MpuWebConstants.EXCHANGEIN5.equalsIgnoreCase(items.getRequestType()) && null != items.getCommentList() && !ei5ProcessedRqtId.contains(items.getRqtId())){
				logger.info("InsertFlag value is true : ", "");
				RequestDTO returnRequestDTO= null;
				List<RequestDTO> listRequestDTO = new ArrayList<RequestDTO>();
				
				// Order returnOrder = searchTenderReturns(RI5AuthCode,obj.getStoreNum(),obj.getKiosk(),"SearsRetail");
				
				int rqt_id = pickUpServiceDAO.getReturnIn5QueueTrans(items.getCommentList(), obj.getStoreNum());
				if(rqt_id>0){
				
				ei5ProcessedRqtId.add(items.getRqtId());
				String fields = MpuWebConstants.ITEM + "," + MpuWebConstants.IDENTIFIER;
				List<String> status = Arrays.asList(MpuWebConstants.OPEN, MpuWebConstants.EXPIRED, MpuWebConstants.COMPLETED);
				returnRequestDTO = webServicesProcessorImpl.getRequestData(String.valueOf(rqt_id), obj.getStoreNum(), fields, null,status);
				
				//setting the required things in the itemDTO
				String CurrTime = getTimeAccToTimeZone(new Date() + "", returnRequestDTO.getOrder().getStoreNumber(), "dd/MM/yyyy HH:mm:ss");
				if(null!=returnRequestDTO.getItemList() && !returnRequestDTO.getItemList().isEmpty()){
					for (ItemDTO item : returnRequestDTO.getItemList()) {
						// getting parent rqdId
					
						/*item.setReturnParentId(getParentRqdId(returnRequestDTO.getOrder().getSalescheck(),	returnRequestDTO.getOrder().getStoreNumber(), item.getDivNum(), item.getItem(), item.getSku(), item.getItemSeq(),
								returnRequestDTO.getOrder().getReturnParentId()));*/
						item.setReturnParentId(item.getRqdId());
						item.setDeliveredQuantity(0);
						item.setItemQuantityNotDelivered("0");
						item.setCreateTime(CurrTime);
						item.setUpdateIimestamp(CurrTime);
						item.setRequested_quantity(item.getQty());
						//item.setRqtId(item.getReturnParentId());
						item.setItemStatus("OPEN");
						item.setStoreNumber(obj.getStoreNum());
						if("0".equals(item.getQty())) {
							item.setQty("1");
						}
						item.setRequested_quantity(item.getQty());

					}
				}
				
				//setting the required things in the orderDTO
				returnRequestDTO.getOrder().setCreateTimestamp( getTimeAccToTimeZone(new Date() + "",obj.getStoreNum(), "dd/MM/yyyy HH:mm:ss"));
				returnRequestDTO.getOrder().setIdentifierType(items.getCommentList());
				returnRequestDTO.getOrder().setCustomer_name( returnRequestDTO.getCustomer().getFirstName() + " "+ returnRequestDTO.getCustomer().getLastName());
				returnRequestDTO.getOrder().setOriginalIdentifier(returnRequestDTO.getOrder().getOriginalIdentifier());
				returnRequestDTO.getOrder().setReturnParentId(String.valueOf(rqt_id));
				//This is set as the return order is in mpu trans and with the associate, change the status to close when associate receives the order.
				returnRequestDTO.getOrder().setRequestStatus("OPEN");
				listRequestDTO.add(returnRequestDTO);

		    	/* try {
		    		 returnRequestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(returnOrder);
		    		 returnRequestDTO.getOrder().setCreateTimestamp("2014/09/10 09:19:05");
		    		
				} catch (Exception e) {
					DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
					throw djException;
				} */
		    	 
		    	List<MPUActivityDTO> mpuActivityDTOs = ConversionUtils.convertRequestDTOtoMPUActivityDTO(listRequestDTO);
					
				associateActivityServicesProcessorImpl.insertMPUActivityData(mpuActivityDTOs);
				
				//Inserting into the request_activity table for RETURN in 5
				for(RequestDTO requestDTO : listRequestDTO){
					for(ItemDTO itemDTO : requestDTO.getItemList()){
						itemDTO.setRequestType(MpuWebConstants.EXCHANGEIN5RETURN);
					}
					
					pickUpServiceDAO.initiatePickUpActivityForItems(requestDTO.getItemList(), requestDTO.getOrder().getStoreNumber(),"CREATE");
				}
				
			} else {
				kioskLockerItems.add("NO RETURN FOR EXCHANGE");
			}
		  }
		}
		
		return kioskLockerItems;
	}
	
	private void sendMPUserviceWarningMsg(String storeNumber ) throws DJException{
		if(null != storeNumber){
			int result = mCPDBDAO.getActiveUserCount(storeNumber);
			if(result == 0){// no user logged in so send mod notification service time warning 
				ItemDTO itemDTO = new ItemDTO();
				itemDTO.setStoreNumber(storeNumber);
				itemDTO.setRqtId("0");
				modNotificationProcessorImpl.sendMODNotification(itemDTO, 9);
			}
		}
	}

	private int completeMPUItem(ItemDTO item) throws DJException {
		logger.info("completeMPUItem",	"Entering completeMPUItem ItemDTO : " + item );
		String CurrTime = getTimeAccToTimeZone(new Date() + "", item.getStoreNumber(), "yyyy-MM-dd HH:mm:ss");
		
		item.setUpdateIimestamp(CurrTime);
		int noOfRows = pickUpServiceDAO.completeItemDetails(item);
		logger.info("completeMPUItem", "Exit completeMPUItem noOfRows : " + noOfRows);
		return noOfRows;
	}

	private int completeMPUOrder(String storeNo, String rqtId)
			throws DJException {
		logger.info("completeMPUOrder",	"Entering completeMPUOrder storeNo : " + storeNo + " rqtId : "+ rqtId  );
		String CurrTime = getTimeAccToTimeZone(new Date() + "", storeNo, "yyyy-MM-dd HH:mm:ss");
		int noOfRows = pickUpServiceDAO.completeOrderDetails(storeNo, rqtId, CurrTime);
		logger.info("completeMPUOrder", "Exit completeMPUOrder noOfRows : " + noOfRows);
		return noOfRows;
	}
	
	private int updateTimeForMPUOrder(String storeNo, String rqtId)
			throws DJException {
		logger.info("updateTimeForMPUOrder",	"Entering updateTimeForMPUOrder storeNo : " + storeNo + " rqtId : "+ rqtId  );
		String CurrTime = getTimeAccToTimeZone(new Date() + "", storeNo, "yyyy-MM-dd HH:mm:ss");
		int noOfRows = pickUpServiceDAO.updateTimeForMPUOrder(storeNo, rqtId, CurrTime);
		logger.info("updateTimeForMPUOrder", "Exit updateTimeForMPUOrder noOfRows : " + noOfRows);
		return noOfRows;
	}

	private int updateActivityDTO(String storeNo, String rqtId, String transId,String requestType, String assignedUser, String searsSalesId)
			throws DJException {
		logger.info("updateActivityDTO",	"Entering updateActivityDTO storeNo : " + storeNo + " rqtId : "+ rqtId + " transId : "+ transId  );
		String CurrTime = getTimeAccToTimeZone(new Date() + "", storeNo, "yyyy-MM-dd HH:mm:ss");
		int noOfRows = pickUpServiceDAO.updateActivityDTO(storeNo, rqtId,transId,CurrTime,requestType,assignedUser,searsSalesId);
		logger.info("updateActivityDTO", "Exit updateActivityDTO noOfRows : " + noOfRows);
		return noOfRows;
	}

	private int updateActivityDetailDTO(List<ItemDTO> items) throws DJException {
		logger.info("updateActivityDetailDTO",	"Entering updateActivityDetailDTO items : " + items );
		String storeNumber = items.get(0).getStoreNumber();
		
		String CurrTime = getTimeAccToTimeZone(new Date() + "", storeNumber, "yyyy-MM-dd HH:mm:ss");	
		int noOfRows = pickUpServiceDAO.updateActivityDetailDTO(storeNumber, items, CurrTime);
		logger.info("updateActivityDetailDTO", "Exit updateActivityDetailDTO noOfRows : " + noOfRows);
		
		return noOfRows;
	}

	// Manish ends
	/************** Pritika ***************/

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void orderHelpRepair(CustomerDetailDTO customerDetailDTO)	throws DJException {
		logger.info("getCustomerDetails",	"Entering getCustomerDetails customerDetailDTO : " + customerDetailDTO );
		RequestDTO requestDTO = pickUpServiceDAO.nPosCallForHelpRepair(customerDetailDTO);

		String CurrTime = getTimeAccToTimeZone(new Date() + "", requestDTO.getOrder().getStoreNumber(), "dd/MM/yyyy HH:mm:ss");
		
		requestDTO.getOrder().setCreateTimestamp(CurrTime);
		requestDTO.getOrder().setRequestStatus(MpuWebConstants.OPEN);
		
		for(ItemDTO obj : requestDTO.getItemList()) {
			
			obj.setCreateTime(CurrTime);
			obj.setUpdateIimestamp(CurrTime);
		}

//		webServicesProcessorImpl.createRequest(requestDTO.getOrder(), requestDTO.getCustomer(), requestDTO.getItemList(),  requestDTO.getPaymentList(),  requestDTO.getTask());

		List<RequestDTO> lRequestDTO = new ArrayList<RequestDTO>();
		lRequestDTO.add(requestDTO);
		
		List<MPUActivityDTO> activityList = ConversionUtils.convertRequestDTOtoMPUActivityDTO(lRequestDTO);
		
//		for(MPUActivityDTO obj: activityList) {
//			obj.setSc_scan("0");
//		}
//		
		/*code for mod notification when no user is logged-in begin 
		 * 
		*/
			sendMPUserviceWarningMsg(customerDetailDTO.getStoreNo());	
		/*	code for mod notification when no user is logged-in end	
		*/
		
		List<Integer> helpRepairTransId = associateActivityServicesProcessorImpl.insertMPUActivityData(activityList);	
		
		// insert into activity table
		List<ItemDTO> helpRepairAction = new ArrayList<ItemDTO>();
		
		ItemDTO helpRepairActionItem = new ItemDTO();
		
		helpRepairActionItem.setRequestType(activityList.get(0).getRequestType());
		helpRepairActionItem.setStoreNumber(lRequestDTO.get(0).getOrder().getStoreNumber());
		helpRepairActionItem.setRqtId(String.valueOf(helpRepairTransId.get(0)));
		helpRepairActionItem.setCreatedBy(MpuWebConstants.KIOSK);
		
		helpRepairAction.add(helpRepairActionItem);
		
		pickUpServiceDAO.initiatePickUpActivityForItems(helpRepairAction, requestDTO.getOrder().getStoreNumber(),"CREATE");
		
		logger.info("getCustomerDetails",	"Going to execute updateMODNotificationHelpRepair for Store: " + customerDetailDTO.getStoreNo() );
		updateMODNotificationHelpRepair(customerDetailDTO.getStoreNo(),lRequestDTO.get(0).getOrder().getKioskName(),helpRepairActionItem.getRqtId());
		logger.info("getCustomerDetails",	"Successfully executed updateMODNotificationHelpRepair for Store: " + customerDetailDTO.getStoreNo() );
		
		logger.info("getCustomerDetails", "Exit getCustomerDetails");
	}
	
	public String getExpiredTime(String dateTime, String escalation, String currDateAccToStore) {
		
//		logger.info("getExpiredTime", "Entering getExpiredTime dateTime : "+ dateTime + " -- currDateAccToStore : " + currDateAccToStore + "  -- escalation : " + escalation);
		
		try{
			
	    	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	Date currentTime = date.parse(currDateAccToStore);
	    	Date createdate = date.parse(dateTime);
	    	
	    	long result = 0;
	    			
	    	if(!"".equals(escalation)) {
//	    		logger.info("getExpiredTime", "Entering getExpiredTime createdate.getTime() : "+ createdate.getTime() + " -- currentTime.getTime() : " + currentTime.getTime());
	    		result = (currentTime.getTime() - createdate.getTime()) + (3600 * Integer.parseInt(escalation) * 1000);
	    		
	    	} else {
//	    		logger.info("getExpiredTime", "Entering getExpiredTime createdate.getTime() : "+ createdate.getTime() + " -- currentTime.getTime() : " + currentTime.getTime());
	    		result = (currentTime.getTime()-createdate.getTime());
	    	}
			
//	    	if(result < 0) {
//	    		result = result * -1;
//	    	}
	    	
	    	logger.info("getExpiredTime", "Exit getExpiredTime dateTime : "+ dateTime + " -- currDateAccToStore : " + currDateAccToStore + "  -- escalation : " + escalation 
	    			+ " -- result : " + result);
	    			
			return CommonUtils.getElapsedTimeHoursMinutesSeconds(result);
			
		} catch(Exception exception) {
//			logger.error("getStockLocator",exception);
			return "00:00:00";
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<RequestDTO> fetchOderForAssociate(String storeNumber, String kioskName, String transId) throws DJException {
		
		logger.info("fetchOderForAssociate", "Entering fetchOderForAssociate storeNumber : " + storeNumber + " -- kioskName : " + kioskName + " -- transId : " + transId);
		
		/**
		 * Cache implementation
		 * By Nasir
		 */
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
		String formattedStoreNo = DJUtilities.leftPadding(storeNumber, 5);
		String listKey = "PICKUP_"+formattedStoreNo+"_"+kioskName;
		String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+kioskName;
		boolean isCacheDirty = Boolean.TRUE;
		
		//if(!"TRUE".equalsIgnoreCase(choice)) {
			
			if(null!=requestQueueCache && null!=requestQueueCache.get(cacheDirtyKey) && null!=requestQueueCache.get(cacheDirtyKey).get()) {
				
				isCacheDirty = Boolean.valueOf(requestQueueCache.get(cacheDirtyKey).get().toString());
				logger.info("===isCacheDirty===", isCacheDirty);
			}
		//}
		
		List<RequestDTO> requestDtoList = new ArrayList<RequestDTO>();
		String curDate = getTimeAccToTimeZone(new Date().toString(), storeNumber, "yyyy-MM-dd HH:mm:ss");
		
		if(isCacheDirty || null==requestQueueCache.get(listKey)) {
			
			logger.info("Getting Data from DB", "--Cache Is Dirty");
			double startTime=Calendar.getInstance().getTimeInMillis();
			
			List<OrderDTO> lOrder = pickUpServiceDAO.fetchOderForAssociate(storeNumber, kioskName);
			
			if(null!=lOrder){
				List<String> transIdList = new ArrayList<String>();
				
				for(OrderDTO order: lOrder) {
					
					if(null != order.getTrans_id() && !MpuWebConstants.HELP.equalsIgnoreCase(order.getRequestType()) && 
							!MpuWebConstants.REPAIRPICK.equalsIgnoreCase(order.getRequestType()) && 
							!MpuWebConstants.REPAIRDROP.equalsIgnoreCase(order.getRequestType())) {
						
						transIdList.add(order.getTrans_id().toString());
					}
				}
				List<ItemDTO> allItemList = new ArrayList<ItemDTO>();
				List<PaymentDTO> allPaymentList =  new ArrayList<PaymentDTO>();
				List<PackageDTO> allPackageList =  new ArrayList<PackageDTO>();
				
				if(null != transIdList && transIdList.size() > 0)
				{
					
					allItemList = pickUpServiceDAO.fetchAllItemsForAssociate(storeNumber, transIdList);
					allPaymentList = pickUpServiceDAO.getAllPaymentForOrder(lOrder, storeNumber);
					allPackageList = pickUpServiceDAO.getAllPackageInfo(storeNumber, transIdList);
				}
				
				/*
				 * Fetching all item and payment information for all the orders once
				 */
				
				for(OrderDTO order : lOrder) {
					
					RequestDTO requestDTO = new RequestDTO();
					
					String expireTime = getExpiredTime(order.getStart_time(),"",curDate);
					
//					logger.info("fetchOderForAssociate", "fetchOderForAssociate escalation_time : " + expireTime);
					
					order.setExpireTime(expireTime);
					
					if(null != order.getAssociate_id() && !"".equals(order.getAssociate_id().trim())) {
						order.setAssociate_Name(getAssociateName(order.getAssociate_id()));
					}
					
					if(null != order.getTrans_id() && !MpuWebConstants.HELP.equalsIgnoreCase(order.getRequestType()) && 
							!MpuWebConstants.REPAIRPICK.equalsIgnoreCase(order.getRequestType()) && 
							!MpuWebConstants.REPAIRDROP.equalsIgnoreCase(order.getRequestType())) {
					
						/*
						 * Make ItemList for this particular Order from AllItemList
						 */
						List<ItemDTO> orderItemList = new ArrayList<ItemDTO>();
						for(ItemDTO item: allItemList) {
							
							if(order.getTrans_id().equals(item.getTrans_id())) {
								item.setExpireTime(expireTime);
								
								if(null == item.getPinNo())
								item.setPinNo("");
								
								/**
								 * For setting full name + thumbnail in Item
								 */
								if(StringUtils.hasText(item.getFullName())){
									String completeDesc = "";
									if(StringUtils.hasText(item.getThumbnailDesc())){
										completeDesc=item.getThumbnailDesc();
									}
									item.setThumbnailDesc(item.getFullName() +completeDesc);
								}
								
								orderItemList.add(item);
							}
							
							/*if("STK".equalsIgnoreCase(item.getFromLocation())
									&& null!=item.getStockLocation()){
								item.setFromLocation(item.getStockLocation());
							}*/
						}
						
						/*
						 * Make PaymentList for this particular Order from allPaymentList
						 */
						List<PaymentDTO> orderPaymentList = new ArrayList<PaymentDTO>();
						for(PaymentDTO paymentDto:allPaymentList){
							if(order.getRqtId().equals(paymentDto.getRqtId())){
								orderPaymentList.add(paymentDto);
							}
						}
						
						/*
						 * Making package list
						*/
						if(MpuWebConstants.LAYAWAY.equalsIgnoreCase(order.getRequestQueueType()) && null != allPackageList){
							List<PackageDTO> packageDTOs = new ArrayList<PackageDTO>();
							for(PackageDTO packageDTO : allPackageList){
								if(order.getRqtId().equalsIgnoreCase(packageDTO.getRqtId())){
									packageDTOs.add(packageDTO);
								}
							}
							requestDTO.setPackageList(packageDTOs); // setting package list to request dto
						}
						/*
						 * Setting TopTenderCard for Order
						 */
						String topTenderCard = CommonUtils.getTopTenderCard(orderPaymentList);
						order.setTopTenderCard(topTenderCard);
						
						
						// Fetch Card Type
						String cardType = "";
						if(null != topTenderCard && "" != topTenderCard) {
							for(PaymentDTO payment : orderPaymentList) {
								if(topTenderCard.equalsIgnoreCase(payment.getAccountNumber())) {
									cardType = PropertyUtils.getProperty("payment.type.code."+String.valueOf(payment.getType()));
									break;
								}
							}
						} 
						order.setCardType(cardType);
						
						/*
						 * Set order,itemlist and paymentlist to requestdto
						 */
						
						requestDTO.setItemList(orderItemList);
						requestDTO.setPaymentList(orderPaymentList);
						requestDTO.setCustomer(mpuWebServiceDAOImpl.getCustomerData(storeNumber, order.getRqtId()));
					} else {
						
						List<ItemDTO> orderItemList = new ArrayList<ItemDTO>();
						List<PaymentDTO> orderPaymentList = new ArrayList<PaymentDTO>();
						requestDTO.setItemList(orderItemList);
						requestDTO.setPaymentList(orderPaymentList);
						
					}
					
					requestDTO.setOrder(order);
					
					/*
					 * Set requestdto to final requestdtolist
					 */
					requestDtoList.add(requestDTO);
					
				}
			}
			// Code for EI5
			getFilteredEI5List(requestDtoList);
			
			/*
			 * Setting the list in Cache
			 */
			
			requestQueueCache.put(listKey, requestDtoList);
			requestQueueCache.put(cacheDirtyKey, "false");
			
			double endTime=Calendar.getInstance().getTimeInMillis();
			logger.info("Time Taken for fetching orders from DB===", endTime-startTime);

		} else {
			
			logger.info("Getting Data from Cache", "--Cache Is Clean");
			double startTime=Calendar.getInstance().getTimeInMillis();
			List<RequestDTO> updatedRequestList = (List<RequestDTO>)requestQueueCache.get(listKey).get();
			
			if(null != updatedRequestList) {
				
				for(RequestDTO request : updatedRequestList) {
					
					String expireTime = getExpiredTime(request.getOrder().getStart_time(),"",curDate);
					RequestDTO updatedRequest = new RequestDTO();
					OrderDTO updatedOrder = request.getOrder();
					List<ItemDTO>updatedItemList = new ArrayList<ItemDTO>();
					
					updatedOrder.setExpireTime(expireTime);
					
					if(null != request.getOrder().getTrans_id() && !MpuWebConstants.HELP.equalsIgnoreCase(request.getOrder().getRequestType()) && 
							!MpuWebConstants.REPAIRPICK.equalsIgnoreCase(request.getOrder().getRequestType()) && 
							!MpuWebConstants.REPAIRDROP.equalsIgnoreCase(request.getOrder().getRequestType())) {
					
						for(ItemDTO item:request.getItemList()){
							item.setExpireTime(expireTime);
							updatedItemList.add(item);
						}
					}
					
					updatedRequest.setOrder(updatedOrder);
					updatedRequest.setItemList(updatedItemList);
					updatedRequest.setPaymentList(request.getPaymentList());
					updatedRequest.setPackageList(request.getPackageList());
					updatedRequest.setCustomer(request.getCustomer());
					requestDtoList.add(updatedRequest);
				}
			}
			
			double endTime=Calendar.getInstance().getTimeInMillis();
			logger.info("Time Taken for fetching orders from Cache===", endTime-startTime);
			
//			return updatedRequestList;
			
		}
		
//		logger.info("fetchOderForAssociate", "Exit fetchOderForAssociate lRequestDTO :"+ requestDtoList.size());
		
		if(!"ALL".equals(transId)) {
			List<RequestDTO> requestDtoList1 = new ArrayList<RequestDTO>();
			for(RequestDTO obj : requestDtoList) {
				
				if((Integer.parseInt(transId)) == obj.getOrder().getTrans_id()) {
					
					requestDtoList1.add(obj);
					return requestDtoList1;
				}
			}
		}
		getColorUpdated(requestDtoList);
		return requestDtoList;

	}
	
	
	private void getColorUpdated (List<RequestDTO> requestDtoList){
		for (RequestDTO requestDTO: requestDtoList){
			if(null!=requestDTO.getOrder()) {
				String expireTime =	requestDTO.getOrder().getExpireTime();
				String a[] = expireTime.split(":");//: "18:10:53"
				int totalSeconds = (Integer.parseInt(a[0])) * 60 * 60 + (+Integer.parseInt(a[1])) * 60 + (+Integer.parseInt(a[2]));
				int  PICK_GREY_TIME=0;
				int PICK_YELLOW_TIME=120;
				int PICK_RED_TIME=240;
				if (totalSeconds>=PICK_RED_TIME) {
					requestDTO.getOrder().setColorCode("red");
				}else if (totalSeconds >= PICK_YELLOW_TIME){
					requestDTO.getOrder().setColorCode("yellow");
				}else if (totalSeconds >= PICK_GREY_TIME){
					requestDTO.getOrder().setColorCode("");
				}
			}
		}
		
	}
	public String getAssociateName(String associateID) throws DJException {
		
		logger.info("getAssociateName", "Enter getAssociateName associateID : "+ associateID);
		
		String associateName = "";
		if(associateID==null ||"".equalsIgnoreCase(associateID)||" ".equalsIgnoreCase(associateID))
		{
			return associateName;
		}
		associateID = associateID.toUpperCase();
		
		EhCacheCache associateCache =  (EhCacheCache) cacheManager.getCache("associateCache");
		
		if(null != associateCache && null != associateCache.get(associateID)) {
			
			associateName = (String) associateCache.get(associateID).get();
		
		} else {
			
			UserVO associateInfo = associateActivityServiceDAOImpl.getAssociateInfo(associateID);	
			
			if(null != associateInfo && null != associateInfo.getGivenName()) {
				
				associateName = associateInfo.getGivenName();
				associateCache.put(associateID, associateName);
			
			} else {
				
				associateName = associateID;
			}
		}
		
		logger.info("getAssociateName", "Exit getAssociateName associateName :"+ associateName);
		
		return associateName;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public String updateStatusToNPOS(String storeNumber, String rqtId, String reqType, String custSign, String reqUrl, Integer transId, String originalIdentifier,
		 String storeFormat, String startTime, String endTime, String kioskName) throws DJException {
		
		logger.error("updateStatusToNPOS", "Entering updateStatusToNPOS storeNumber : " + storeNumber + " -- rqtId : " + rqtId + "	-- reqType : " + reqType.trim()	
				+ "	-- transId : " + transId);
		
		String response = "true";
		storeNumber = DJUtilities.leftPadding(storeNumber, 5);
		
		OrderAdaptorRequest request = new OrderAdaptorRequest();
		
		reqType = reqType.trim();
		
		if(MpuWebConstants.PICKUP.equalsIgnoreCase(reqType) || MpuWebConstants.LOCKER_PICKUP.equalsIgnoreCase(reqType)) {
			
			request = mpuWebServiceDAOImpl.getOriginalJSON(rqtId, storeNumber, null);
			
		} else {
				
			request = mpuWebServiceDAOImpl.getOriginalJSON(null, storeNumber, transId);
		}
		

		request.setRequestType(OrderAdaptorRequest.PUSH_ORDER);
		

		// set identifier, storeNo, storeformat, task, transaction, timeassigned , timecompleted, ringingassociatecode from database
		Order order = request.getRequestOrder();
		order.setOriginalIdentifier(originalIdentifier);
		order.setStoreNo(storeNumber);
		order.setStoreFormat(storeFormat);
		order.setCustomerSignature(custSign);
		order.setKioskName(kioskName);
	
		order.setRingingAssociateCode(getAssociateId()); // item selling associate id // to be set
		
		// to be removed
		//order.setRegisterLocation("null");
		//order.setRegisterMessage("null");
		//order.setRegisterNumber("null");
		//order.setStoreNo("01818");
		//order.setStoreFormat("SearsRetail");
		//order.setWeb_ExpireTime("null");

		
		order.getTransaction().setTranscationDateAsString(getTimeAccToTimeZone(new Date() + "", storeNumber, "MM/dd/yyyy HH:mm:ss"));

		if(MpuWebConstants.RETURN.equalsIgnoreCase(reqType)) {
			
			order.setTimeAssigned(ConversionUtils.convertStringToDate(startTime, "dd-MM-yyyy HH:mm:ss"));
			order.setTimeCompleted(ConversionUtils.convertStringToDate(endTime, "dd-MM-yyyy HH:mm:ss"));
			
			request.getRequestOrder().setItems(getItemsForNPOSMPU(transId, storeNumber));
			// request.getRequestOrder().getTask().setTaskId(""); to be set :: dont know what to set?
			request.getRequestOrder().getTask().setTaskType(102);
			
		} else if(MpuWebConstants.RETURNIN5.equalsIgnoreCase(reqType)){
			/**
			 * For returnInFive update to Npos
			 * @author nkhan6
			 */
			Map<String, Order> orderMap = new HashMap<String, Order>();
			orderMap.put(order.getIdentifier(), order);
			request.setOrdersMap(orderMap);
			
			StoreInfo storeInfo = mpuWebServiceDAOImpl.getStoreInformation(storeNumber);
			request.setStoreInfo(storeInfo);
			
			reqUrl="confirmTenderReturn";
			
			
		}
		else if(MpuWebConstants.HELP.equalsIgnoreCase(reqType)|| MpuWebConstants.REPAIRDROP.equalsIgnoreCase(reqType)|| MpuWebConstants.REPAIRPICK.equalsIgnoreCase(reqType)){
			
			order.setTimeAssigned(ConversionUtils.convertStringToDate(startTime, "dd-MM-yyyy HH:mm:ss"));
			order.setTimeCompleted(ConversionUtils.convertStringToDate(endTime, "dd-MM-yyyy HH:mm:ss"));
			
			request.getRequestOrder().setItems(getItemsForHelp(kioskName));
		}
		else if(MpuWebConstants.PICKUP.equalsIgnoreCase(reqType) || MpuWebConstants.LOCKER_PICKUP.equalsIgnoreCase(reqType)) {
			
			Map<String,Object> itemStatusMap = mpuWebServiceDAOImpl.checkItemStatus(storeNumber, rqtId);
			List<ItemDTO> items = pickUpServiceDAO.getDeliveredQtyAndLdapId(storeNumber, String.valueOf(transId));
			List<OrderItem> nposItemsListFinal=new ArrayList<OrderItem>();
			Map<String,Object> finalStatusMap=new HashMap<String, Object>();
			/**
			 * For CrossFormat Orders
			 */
			if(null!=itemStatusMap){
				for(String key:itemStatusMap.keySet()){
					String []strArr = key.split(",");
					//if(strArr.length>2 && !StringUtils.isEmpty(strArr[2]) && strArr[2].startsWith("LM")){
					if(key.contains("LM")){
						//If this is a cross format order
						String []itemIdSeqArray = strArr[strArr.length-1].split("-");
						//String cfkey = strArr[0]+","+strArr[2]+"-"+itemIdSeqArray[1];
						String cfkey = strArr[0]+"-"+itemIdSeqArray[1];
						
						finalStatusMap.put(cfkey, itemStatusMap.get(key));
					}else{
						if(strArr.length>3){
							String []itemIdSeqArray = strArr[strArr.length-1].split("-");
							String cfkey = strArr[0]+","+strArr[1]+","+strArr[2]+"-"+itemIdSeqArray[1];
							finalStatusMap.put(cfkey, itemStatusMap.get(key));
						}
						
					}
				}
			}
			
			
			for(OrderItem item: request.getRequestOrder().getItems()) {
				
				/*if(item.getItemIdentifiers().split(",").length > 3) {
					item.setItemIdentifiers((item.getItemIdentifiers().substring(0, item.getItemIdentifiers().lastIndexOf(','))));
				}*/
				
				/**
				 * For removing spaces in place of plus4
				 */
				String itmIdentifierString = item.getItemIdentifiers();
				itmIdentifierString = itmIdentifierString.replace("    ", "");
				String []strArr = itmIdentifierString.split(",");
				if(itmIdentifierString.contains("LM")){
					itmIdentifierString = strArr[0];
					
				}else if(strArr.length>3){
					itmIdentifierString = strArr[0]+","+strArr[1]+","+strArr[2];
				}
					itmIdentifierString = itmIdentifierString+"-"+item.getSequenceNo();
				
				//String status = (String) finalStatusMap.get(DJUtilities.concatString(item.getItemIdentifiers(), "-", item.getSequenceNo() + ""));
				String status = (String) finalStatusMap.get(itmIdentifierString);
				
				logger.error("updateStatusToNPOS updateStatusToNPOS status " + status,"");
				
				if(StringUtils.isEmpty(status)) {
					logger.info("updateStatusToNPOS", "updateStatusToNPOS Status is blank as item_id is blank from NPOS");
				}
				
				//logger.info("updateStatusToNPOS", "updateStatusToNPOS npos identifier : " + DJUtilities.concatString(item.getItemIdentifiers(), "-", item.getSequenceNo() + ""));
				logger.error(" updateStatusToNPOS npos identifier : " + itmIdentifierString,"");
				
				for(ItemDTO itemQty : items) {
					
					/**
					 * For Cross Format
					 */
					if(null!=itemQty.getItemIdentifiers() && itemQty.getItemIdentifiers().contains("LM")){
						String []identifierArr =  itemQty.getItemIdentifiers().split(",");
						String itemIdentifier;
						if(null!=identifierArr && identifierArr.length>0){
							itemIdentifier = identifierArr[0];
							String []itemIdSeqArray = identifierArr[identifierArr.length-1].split("-");
							//if(identifierArr.length>2 && itemIdSeqArray.length>1){
							if(itemIdSeqArray.length>1){
								//itemIdentifier = itemIdentifier + ","+identifierArr[2]+"-"+itemIdSeqArray[1];
								itemIdentifier = itemIdentifier +"-"+itemIdSeqArray[1];
								itemQty.setItemIdentifiers(itemIdentifier);
							}
							
							
						}
						
					}
					logger.error("updateStatusToNPOS  db identifier : " + itemQty.getItemIdentifiers(),"");
					
					if(null!=itmIdentifierString && itmIdentifierString.equalsIgnoreCase(itemQty.getItemIdentifiers())) {

						item.setItemQuantityActual(Integer.parseInt(itemQty.getItemQuantityActual()));
						
						item.setStatus(status);
						item.setItemsellingAssociateId(getAssociateId());
						item.setItemSellingAssociateId(getAssociateId());
						
						nposItemsListFinal.add(item);
						
				
							//need to remove
							order.setRingingAssociateCode(getAssociateId());
					
						
						//order.setRingingAssociateCode(item.getItemsellingAssociateId());
								
						break;
					}
				}
				
			}
			
			request.getRequestOrder().setItems(nposItemsListFinal);
			request.getRequestOrder().getTask().setTaskType(101);
			
		} else if(MpuWebConstants.LOCKER_PICKUP.equalsIgnoreCase(reqType)){
			request.getRequestOrder().getTask().setTaskType(101);
		}
		
		if(null != request.getRequestOrder().getPaymentList())
			for(Payment obj : request.getRequestOrder().getPaymentList()) {
				
				if(null == obj.getAccountNumber()){
					obj.setAccountNumber("000000XXXXXX0000");
				}
				if(null == obj.getExpireDate()){
					obj.setExpireDate("0000");
				}
				if(null == obj.getStatus()){
					obj.setStatus("2");
				}
			}
		
		if(MpuWebConstants.PICKUP.equalsIgnoreCase(reqType) || MpuWebConstants.LOCKER_PICKUP.equalsIgnoreCase(reqType)) {
			
			nPOSUpdateProcessorImpl.updateNPOSForPickUp(request, reqUrl, transId);
			
		} else {
			
			logger.info("updateStatusToNPOS", "start npos update : " + Calendar.getInstance().getTimeInMillis());
			
			if("confirmTenderReturn".equalsIgnoreCase(reqUrl)){
				OrderAdaptorResponse nposResponse = null;
				nposResponse = nPOSUpdateProcessorImpl.confirmTenderReturn(request, reqUrl);
				/**
				 * Throwing DJException for if ReturnIn5 is not successful
				 */
				if(null!=nposResponse && null!=nposResponse.getStatus()){
					logger.info("updateStatusToNPOS === response==", nposResponse.getStatus().getCode());
					
					if (nposResponse.getStatus().getCode() != 200){
						response = "false";
						
						pickUpServiceDAO.cancelReturnin5(transId,"CANCELLED", storeNumber);
						EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
						String formattedStoreNo = org.apache.commons.lang3.StringUtils.leftPad(storeNumber, 5, '0');
						String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+kioskName;
						String ohmCacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+kioskName;
						logger.info("***cacheDirtyKey***", cacheDirtyKey);
						if(null!=requestQueueCache){
							requestQueueCache.put(cacheDirtyKey, "true");
							requestQueueCache.put(ohmCacheDirtyKey, "true");
						}
						
					}
				}
			}else{
				nPOSUpdateProcessorImpl.updateNPOS(request, reqUrl);
			}
			
			logger.info("updateStatusToNPOS", "end npos update : " + Calendar.getInstance().getTimeInMillis());
		}
		
		logger.info("updateStatusToNPOS", "Exiting from updateStatusToNPOS === response=="+response);
		return response;
	}

	/************** Pritika 
	 * @throws DDRMetaException 
	 * @throws DataAccessException ***************/

	// shipra
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void createReturnKiosk(List<RequestDTO> requestDTOs) throws DJException, DataAccessException, DDRMetaException {
		logger.info("createReturnKiosk", "Entering createReturnKiosk requestDTOs : " + requestDTOs );
		// insert it into NPOS and insert the request into database
		ObjectMapper objMap = new ObjectMapper();
		requestDTOs = objMap.convertValue(requestDTOs, new TypeReference<List<RequestDTO>>() {});
		RequestDTO parentReqDTO =null;
		String returnParentId = "";
		String storeNumber =null;
		for (RequestDTO requestDTO : requestDTOs) {
			
			OrderDTO orderDTO = requestDTO.getOrder();
			storeNumber = orderDTO.getStoreNumber();
			if(!orderDTO.isReturnNotFound()){
			// first check if Order exists in database or not,take it from session and then insert it into database
			returnParentId = getParentRqtId(orderDTO.getSalescheck(), orderDTO.getStoreNumber(), orderDTO.getCustomerId(), orderDTO.getTimeAssigned());
			
			if(null == returnParentId || "".equalsIgnoreCase(returnParentId)) {
				
				List<RequestDTO> requestDTOsTemp = getAllRequestsFromEhCache(orderDTO.getStoreNumber(), MpuWebConstants.RETURN, orderDTO.getKioskName(), "RETURN", orderDTO.getSearch_value(), orderDTO.getSearch_method());
				for(RequestDTO request : requestDTOsTemp) {
					OrderDTO order = request.getOrder();
					if(orderDTO.getSalescheck().equalsIgnoreCase(order.getSalescheck())) {
						parentReqDTO = new RequestDTO();
						request.getOrder().setRequestType(MpuWebConstants.RETURN);
						if(null != request.getItemList() && !request.getItemList().isEmpty()){
							for(ItemDTO itemDTO : request.getItemList()){
								itemDTO.setRequestType(MpuWebConstants.RETURN);
							}
						}
							parentReqDTO = webServicesProcessorImpl.createRequest(request.getOrder(), request.getCustomer(), request.getItemList(),request.getPaymentList(), request.getTask());
						returnParentId = parentReqDTO.getOrder().getRqtId();
						break;
					}
				}
			}
			}
			CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
			customerDetailDTO.setStoreNo(orderDTO.getStoreNumber());
			customerDetailDTO.setCustLastName(orderDTO.getCustomerId());
			customerDetailDTO.setStoreName(orderDTO.getKioskName());
			customerDetailDTO.setStoreFormat(orderDTO.getStoreFormat());
			customerDetailDTO.setRequestType(MpuWebConstants.HELP);
			customerDetailDTO.setLanguage('0');
			
			
			RequestDTO request = pickUpServiceDAO.nPosCallForHelpRepair(customerDetailDTO);	// Create help request in NPOS
			
			// return item not found, taking the new salescheck as reference, do a dummy insert on request_queue_trans
			if(orderDTO.isReturnNotFound()){
				// if return not found, take 1st order from NPOS, and take customer from that npos and orginal json from npos
				List<RequestDTO> requestDTOsTemp = getAllRequestsFromEhCache(orderDTO.getStoreNumber(), MpuWebConstants.RETURN, orderDTO.getKioskName(), "RETURN", orderDTO.getSearch_value(), orderDTO.getSearch_method());
				if(null != requestDTOsTemp && !requestDTOsTemp.isEmpty()){
					if(null!=requestDTOsTemp.get(0).getCustomer()){
						requestDTO.setCustomer(requestDTOsTemp.get(0).getCustomer());
					}
				}
				// if return item not found, do a dummy insert on request_queue_trans 
			requestDTO.getOrder().setSalescheck(request.getOrder().getSalescheck());
			requestDTO.getOrder().setOriginalJson(request.getOrder().getOriginalJson());
			parentReqDTO = webServicesProcessorImpl.createRequest(requestDTO.getOrder(), requestDTO.getCustomer(), requestDTO.getItemList(),requestDTO.getPaymentList(), requestDTO.getTask());
			requestDTO.getOrder().setRequestStatus(MpuWebConstants.OPEN);
			returnParentId = parentReqDTO.getOrder().getRqtId();
			}
			
			String CurrTime = getTimeAccToTimeZone(new Date() + "", requestDTO.getOrder().getStoreNumber(), "dd/MM/yyyy HH:mm:ss");
			// getting the parent rqtId

			orderDTO.setReturnParentId(returnParentId);

			if(null!=requestDTO.getItemList() && !requestDTO.getItemList().isEmpty()){
			for (ItemDTO item : requestDTO.getItemList()) {
				// getting parent rqdId
				if(null != parentReqDTO){
					if(null!= parentReqDTO.getItemList() && !parentReqDTO.getItemList().isEmpty()){
					for(ItemDTO itemObj : parentReqDTO.getItemList()){
						if(itemObj.getDivNum().equals(item.getDivNum()) && itemObj.getItem().equals(item.getItem()) && itemObj.getSku().equals(item.getSku()) 
								&& itemObj.getItemSeq().equals(item.getItemSeq())) {
							item.setReturnParentId(itemObj.getRqdId());
							break;
						}
					}
					}
				} else {
				item.setReturnParentId(getParentRqdId(orderDTO.getSalescheck(),	orderDTO.getStoreNumber(), item.getDivNum(), item.getItem(), item.getSku(), item.getItemSeq(),
						orderDTO.getReturnParentId()));
				}
				item.setDeliveredQuantity(0);
				item.setItemQuantityNotDelivered("0");
				item.setCreateTime(CurrTime);
				item.setUpdateIimestamp(CurrTime);

			}
			}
			// things: set new saleshceck and old salescheck and map it
			
			// in database
			if (null != request && null != request.getOrder()) {

				orderDTO.setOriginalIdentifier(orderDTO.getSalescheck());
				orderDTO.setSalescheck(request.getOrder().getSalescheck());
				orderDTO.setRequestNumber(request.getOrder().getSalescheck());
				orderDTO.setOriginalJson(request.getOrder().getOriginalJson());
				orderDTO.setCreateTimestamp(CurrTime);
				orderDTO.setPickup_source("KIOSK");

			}
		}

		// convert RequestDTO to MPUActivityDTO
		List<MPUActivityDTO> activityList = ConversionUtils.convertRequestDTOtoMPUActivityDTO(requestDTOs);
		
		/*code for mod notification when no user is logged-in begin 
		 * 
		*/
			sendMPUserviceWarningMsg(storeNumber);	
		/*	code for mod notification when no user is logged-in end	
		*/
		
		associateActivityServicesProcessorImpl.insertMPUActivityData(activityList);
		
		for(RequestDTO requestDTO : requestDTOs){
			pickUpServiceDAO.initiatePickUpActivityForItems(requestDTO.getItemList(), requestDTO.getOrder().getStoreNumber(),"CREATE");
		}
		
		
		logger.info("createReturnKiosk", "Exit createReturnKiosk");
	}

	public PickUpReturnDTO getAllItemsForReturn(String numType, String step, String identifierValue, PickUpDTO pickUpDTO) throws DJException {
		
		logger.info("getAllItemsForReturn",	"Entering getAllItemsForReturn numType : " + numType + "step : "+step + "identifierValue : "+ identifierValue + "pickUpDTO : "+ pickUpDTO );
				
			try {
				
				PickUpReturnDTO requestDTOList = new PickUpReturnDTO();
				List<RequestDTO> requestDTOs = new ArrayList<RequestDTO>();
				List<Order> orderListNPOS = new ArrayList<Order>();
				requestDTOList.setReqType("RETURN");

				// check if previous RI5 in progress ,

				 if(checkPreviousRI5Progress(pickUpDTO.getStoreNumber(), identifierValue, "RETURNIN5", "OPEN")){
				 requestDTOList.setReturnDesc("Returnin5 in progress");
				 return requestDTOList;
				 }
				 
				 //checking if the exchange request is already open
				 if(checkPreviousRI5Progress(pickUpDTO.getStoreNumber(), identifierValue, "EXCHANGEIN5", "OPEN")){
					 requestDTOList.setReturnDesc("Exchangein5 in Progress");
					 return requestDTOList;
				 }
				// getting return items from NPOS

				if (step == null || step.equalsIgnoreCase("")) {
					if(numType.equalsIgnoreCase(MpuWebConstants.NAME_ADDRESS))
						orderListNPOS = pickUpServiceDAO.getOrdersFromNPOS(pickUpDTO, MpuWebConstants.MATCHKEY,identifierValue, "RETURN");
					else
						orderListNPOS = pickUpServiceDAO.getOrdersFromNPOS(pickUpDTO, numType,identifierValue, "RETURN");
					
					// checking for RI5, if exists then return RI5 order, otherwise
					// normal return
					if (orderListNPOS != null && !orderListNPOS.isEmpty()) {
						
						// Converting Data from NPOS into List of Request DTOs
						for (Order itemMapNPOSs : orderListNPOS) {
							
							itemMapNPOSs.setStoreNo(pickUpDTO.getStoreNumber());	//	added to change store number to kiosk store number
							RequestDTO requestDTO = new RequestDTO();
							requestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(itemMapNPOSs);
							requestDTOs.add(requestDTO);
						}
						
						Collections.sort(requestDTOs, new Comparator<RequestDTO>() {

							public int compare(RequestDTO o1, RequestDTO o2) {
								SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
								try {
									if(null != o1.getOrder().getTimeAssigned() && null != o2.getOrder().getTimeAssigned())
									return df.parse(o2.getOrder().getTimeAssigned()).compareTo(df.parse(o1.getOrder().getTimeAssigned()));
								} catch (ParseException e) {
									e.printStackTrace();
								}
								return 0;
								
							}
					    });
						
						addRequestToEhCache(requestDTOs, pickUpDTO.getStoreNumber(), pickUpDTO.getKiosk(), MpuWebConstants.RETURN, "RETURN", identifierValue, numType);	// Adding to EhCache
						
						Order order = orderListNPOS.get(0);
						if (null != order && "RETURNIN5".equals(order.getOrderType())) {
							
							RequestDTO requestDTO = requestDTOs.get(0);
							
							requestDTO  = createReturnin5(requestDTO, numType, identifierValue, pickUpDTO.getKiosk(), pickUpDTO.getStoreNumber());	// insert RI5 order in DB
							
							requestDTOs.remove(0);
							requestDTOs.add(0, requestDTO);
							
							List<MPUActivityDTO> mpuActivityDTOs = ConversionUtils.convertRequestDTOtoMPUActivityDTO(requestDTOs);
							
							associateActivityServicesProcessorImpl.insertMPUActivityData(mpuActivityDTOs);
							
							// insert in activity table
							for(RequestDTO request : requestDTOs){
								if(null != request.getItemList() && !request.getItemList().isEmpty())
									{
									request.getItemList().get(0).setRequestType("RETURNIN5");
									pickUpServiceDAO.initiatePickUpActivityForItems(request.getItemList(),pickUpDTO.getStoreNumber(),"CREATE");
									}
							}
							
							requestDTOList.setReturnDesc("RETURNIN5");
							
						}else if(null != order && order.isExchangeFlag() ){ 
							
							RequestDTO requestDTO = requestDTOs.get(0);
							
							requestDTO  = createExchangein5(requestDTO, numType, identifierValue, pickUpDTO.getKiosk(), pickUpDTO.getStoreNumber());	// insert EI5 order in DB
							
							requestDTOs.add(requestDTO);
							
							List<MPUActivityDTO> mpuActivityDTOs = ConversionUtils.convertRequestDTOtoMPUActivityDTO(requestDTOs);
							
							associateActivityServicesProcessorImpl.insertMPUActivityData(mpuActivityDTOs);
							
							requestDTOList.setReturnDesc("EXCHANGEIN5");
							
						}				
						else {
							// check duplicacy
							if (isDuplicateSalescheck(requestDTOs, numType)) {
										
									HashSet<CustomerDTO> hs = new HashSet<CustomerDTO>();
									HashSet<String> addressId = new HashSet<String>();
									HashSet<String> customerId = new HashSet<String>();
									
									for (RequestDTO obj2 : requestDTOs) {
										
										if(addressId.add(obj2.getCustomer().getAddressId()) || customerId.add(obj2.getCustomer().getCustomerId())) {
											customerId.add(obj2.getCustomer().getCustomerId());
											addressId.add(obj2.getCustomer().getAddressId());
											hs.add(obj2.getCustomer());
										}
										
									}
				
									if (numType.equalsIgnoreCase(MpuWebConstants.PHONE)) {
										requestDTOList.setReturnDesc("Multiple Customer Exception for PHONE");
									} else {
										requestDTOList.setReturnDesc("Multiple Customer Exception.");
									}
									requestDTOList.setResponseData(new ArrayList<CustomerDTO>(hs));	
				
									return requestDTOList;
							
							}
							requestDTOList.setReturnDesc("RETURN");
						}
						requestDTOList.setResponseData(requestDTOs);
						}
				}
				
				// resolve duplicacy
				if (("RESOLVE_DUPLICACY").equalsIgnoreCase(step)) {

					// Fetch item list from cache
					
					List<RequestDTO> requestDTOListTemp = getAllRequestsFromEhCache(pickUpDTO.getStoreNumber(),	MpuWebConstants.RETURN, pickUpDTO.getKiosk(), "RETURN", identifierValue, numType);
					
				 if (null != pickUpDTO.getPurchaseDate() && !(pickUpDTO.getPurchaseDate().equalsIgnoreCase("")) && !(pickUpDTO.getPurchaseDate().isEmpty())) {

						requestDTOs = resolveDuplicacy(requestDTOListTemp, pickUpDTO.getPurchaseDate(), "date");


					} else {
						requestDTOs = customerDuplicacyChk(requestDTOListTemp, pickUpDTO);
					}
					//return the list of orders from the cache on the basis of customerDTO for the curbside pick up
					
					if (isDuplicateSalescheck(requestDTOs, numType)) {


						requestDTOList.setReturnDesc("Multiple Customer Exception. Help Request should be Raised.");
						return requestDTOList;

					}
				
					pickUpDTO.setAddressId(requestDTOs.get(0).getCustomer().getAddressId());
					pickUpDTO.setAddress1(requestDTOs.get(0).getCustomer().getAddress1());
					pickUpDTO.setAddress2(requestDTOs.get(0).getCustomer().getAddress2());
					if(!requestDTOs.get(0).getCustomer().getIdStsCd().equalsIgnoreCase(null))
					pickUpDTO.setIdStsCd(requestDTOs.get(0).getCustomer().getIdStsCd().charAt(0));
					pickUpDTO.setCustomerId(requestDTOs.get(0).getCustomer().getCustomerId());
					
					orderListNPOS = pickUpServiceDAO.getOrdersFromNPOS(pickUpDTO, MpuWebConstants.CUSTOMERID, requestDTOs.get(0).getCustomer().getCustomerId(),  MpuWebConstants.RETURN); // List of order from NPOS
					
					requestDTOs.clear();
					for (Order itemMapNPOSs : orderListNPOS) {
						
						RequestDTO requestDTO = new RequestDTO();
						requestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(itemMapNPOSs);
						requestDTOs.add(requestDTO);
					}
					
					// sorting
					
					Collections.sort(requestDTOs, new Comparator<RequestDTO>() {

						public int compare(RequestDTO o1, RequestDTO o2) {
							SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
							try {
								if(null != o1.getOrder().getTimeAssigned() && null != o2.getOrder().getTimeAssigned())
								return df.parse(o2.getOrder().getTimeAssigned()).compareTo(df.parse(o1.getOrder().getTimeAssigned()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							return 0;
							
						}
				    });
					
					// adding to echcache
					
					addRequestToEhCache(requestDTOs, pickUpDTO.getStoreNumber(), pickUpDTO.getKiosk(), MpuWebConstants.RETURN, "RETURN", identifierValue, numType);	// Adding to EhCache
					
					requestDTOList.setReturnDesc("RETURN");
					requestDTOList.setResponseData(requestDTOs);
					
				} 
				
				logger.info("getAllItemsForReturn", "Exit getAllItemsForReturn requestDTOList : "+ requestDTOList);
				return requestDTOList;
			} catch (Exception e) {
				logger.error("getAllItemsForReturn",e);
				DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
				throw djException;
			}
	}

	private boolean checkPreviousRI5Progress(String storeNo,
			String returnAuthCode, String requestType, String requestStatus)
			throws DJException {
		logger.info("checkPreviousRI5Progress",	"Entering checkPreviousRI5Progress storeNo : " + storeNo + "returnAuthCode : "+returnAuthCode + "requestType : "+ requestType + "requestStatus : "+ requestStatus );
		List<Map<String, Object>> mapList = pickUpServiceDAO
				.checkPreviousRI5Progress(storeNo, returnAuthCode, requestType,
						requestStatus);
		for (Map<String, Object> map : mapList) {
			String rqtId = map.get(RQT_ID.name())+"";

			if (null != rqtId) {
				logger.info("checkPreviousRI5Progress", "Exit checkPreviousRI5Progress true : "+ Boolean.TRUE);
				return true;
			}
		}
		logger.info("checkPreviousRI5Progress", "Exit checkPreviousRI5Progress false : "+ Boolean.FALSE);
		return false;
	}
	
	private String getParentRqtId(String salesCheck,String storeNo,String customerId, String orderDate) throws DJException{
		logger.info("getParentRqtId",	"Entering getParentRqtId salesCheck : " + salesCheck + "storeNo : "+storeNo + "customerId : "+ customerId + "orderDate : "+ orderDate );
		String rqtId = null;
		if(null!=orderDate)
		orderDate = getTimeAccToTimeZone(orderDate, storeNo, "yyyy-MM-dd");
		List<Map<String, Object>> mapList =	pickUpServiceDAO.getParentRqtId(salesCheck, storeNo, customerId, orderDate);
		for (Map<String, Object> map : mapList) {
		rqtId=map.get(RQT_ID.name()) + "";
		}
		logger.info("getParentRqtId", "Exit getParentRqtId rqtId : "+ rqtId);
		return rqtId;
	}

	private String getParentRqdId(String salesCheck, String storeNo,
			String div, String item, String sku, String itemSeq, String rqtId)
			throws DJException {
		logger.info("getParentRqdId",	"Entering getParentRqdId salesCheck : " + salesCheck + "storeNo : "+storeNo + "div : "+ div + "item : "+ item + "sku : "+ sku + "itemSeq : "+ itemSeq +"rqtId"+rqtId);
		String rqdId = null;
		List<Map<String, Object>> mapList = pickUpServiceDAO.getParentRqdId(
				salesCheck, storeNo, div, item, sku, itemSeq, rqtId);
		for (Map<String, Object> map : mapList) {
			rqdId = map.get(RQD_ID.name()) + "";
		}
		logger.info("getParentRqdId", "Exit getParentRqdId rqdId : "+ rqdId);
		return rqdId;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private RequestDTO createReturnin5(RequestDTO requestDTO,String searchMethod, String searchValue,String kiosk,String storeNumber) throws Exception{
		logger.info("createReturnin5",	"Entering createReturnin5 requestDTO : " + requestDTO + "searchMethod : "+searchMethod + "searchValue : "+ searchValue + "kiosk : "+ kiosk + "storeNumber : "+ storeNumber);
		String salescheck = "";
		RequestDTO	parentReqDTO = null;
		OrderDTO orderDTO = requestDTO.getOrder();
		List<ItemDTO> itemList = requestDTO.getItemList();
		String CurrTime = getTimeAccToTimeZone(new Date() + "", requestDTO.getOrder().getStoreNumber(), "dd/MM/yyyy HH:mm:ss") + "";
		// customerId sometimes comes blank . how to calculate parentrqtId then?
		// getting parent rqtId for this request and then setting all the reqd details
		String returnParentId = getParentRqtId( orderDTO.getOriginalIdentifier(), orderDTO.getStoreNumber(), orderDTO.getCustomerId(), orderDTO.getTimeAssigned());
		
		// if parentId is null or blank , then create a request in db, ie in queue_trans and queue_details
		if(null == returnParentId || "".equals(returnParentId) ){
			salescheck = requestDTO.getOrder().getSalescheck();
			if(null != requestDTO.getOrder().getOriginalIdentifier()){
				requestDTO.getOrder().setSalescheck(requestDTO.getOrder().getOriginalIdentifier());
				requestDTO.getOrder().setRequestNumber(requestDTO.getOrder().getOriginalIdentifier());
			}
			requestDTO.getOrder().setRequestType(MpuWebConstants.RETURNIN5);
			if(null != requestDTO.getItemList() && !requestDTO.getItemList().isEmpty()){
				for(ItemDTO itemDTO : requestDTO.getItemList()){
					itemDTO.setRequestType(MpuWebConstants.RETURNIN5);
				}
			}
		parentReqDTO = webServicesProcessorImpl.createRequest(requestDTO.getOrder(), requestDTO.getCustomer(), requestDTO.getItemList(), requestDTO.getPaymentList(), requestDTO.getTask());	
		returnParentId = parentReqDTO.getOrder().getRqtId();
		}
		orderDTO.setReturnParentId(returnParentId);
		orderDTO.setRequestType("RETURNIN5");
		orderDTO.setPickup_source("KIOSK");
		orderDTO.setRequestStatus("OPEN"); // manually setting it OPEN
		if(null != orderDTO.getCustomer_name()){
		orderDTO.setCustomer_name(orderDTO.getCustomer_name().toUpperCase());
		}
		else{
			orderDTO.setCustomer_name(null);
		}
		orderDTO.setSearch_method(searchMethod);
		orderDTO.setSearch_value(searchValue);
		orderDTO.setKioskName(kiosk);
		orderDTO.setCreateTimestamp(CurrTime);
		
		// setting the required details for item
		for(ItemDTO item : itemList){
			if(null != parentReqDTO){
				for(ItemDTO itemObj : parentReqDTO.getItemList()){
					if(itemObj.getDivNum().equals(item.getDivNum()) && itemObj.getItem().equals(item.getItem()) && itemObj.getSku().equals(item.getSku()) 
							&& itemObj.getItemSeq().equals(item.getItemSeq())) {
						item.setReturnParentId(itemObj.getRqdId());
						break;
					}
				}
			} else {
			item.setReturnParentId(getParentRqdId(orderDTO.getSalescheck(),	orderDTO.getStoreNumber(), item.getDivNum(), item.getItem(), item.getSku(), item.getItemSeq(),
					orderDTO.getReturnParentId()));
			}
			
			item.setRqtId(returnParentId);
			item.setDeliveredQuantity(0);
			item.setItemQuantityNotDelivered("0");
			item.setItemStatus("OPEN");
			item.setCreateTime(CurrTime);
			item.setUpdateIimestamp(CurrTime);
			item.setStoreNumber(storeNumber);
			if("0".equals(item.getQty())) {
				item.setQty("1");
			}
			item.setRequested_quantity(item.getQty());
			
			
			// item status, store number and qty already set from order that come from NPOS, to test : data not coming from NPOS
		}
		requestDTO.getOrder().setSalescheck(salescheck);
		requestDTO.getOrder().setRequestNumber(salescheck);
		logger.info("createReturnin5", "Exit createReturnin5 requestDTO : "+ requestDTO);
		return requestDTO;
	}
	
	
	
	
	// shipra end
	public List<MPUActivityDTO> createActivityDTO(PickUpSelectedItems obj, List<String> kioskLockerItems, List<RequestDTO> requestDTOListTemp) throws DJException {

		//List<RequestDTO> requestDTOListTemp = getAllRequestsFromEhCache(obj.getStoreNum(), MpuWebConstants.PICK_UP_REQUEST,obj.getKiosk(), obj.getPickUpSource().toUpperCase(), obj.getSearchValue(), obj.getSearchMethod());
		List<MPUActivityDetailDTO> detailsList = null;
		List<MPUActivityDTO> ordersList = new ArrayList<MPUActivityDTO>();
		List<String> transIdList = new ArrayList<String>();

		for (String itemToUpdate : obj.getItemsSelectedForPickUp()) {
			MPUActivityDTO trans = new MPUActivityDTO();
			MPUActivityDetailDTO detail = new MPUActivityDetailDTO();
			detailsList = new ArrayList<MPUActivityDetailDTO>();

			String CurrTime = getTimeAccToTimeZone(new Date() + "", obj.getStoreNum(), "dd/MM/yyyy HH:mm:ss");
			
			Timestamp currentTimeTimestamp = new Timestamp(ConversionUtils.convertStringToDate(CurrTime, "dd/MM/yyyy HH:mm:ss").getTime());

			for (RequestDTO request : requestDTOListTemp) {
				for (ItemDTO item : request.getItemList()) {
					if (itemToUpdate.split(";")[1].equalsIgnoreCase(item.getRqdId())) {
						
						detail.setRqdId(itemToUpdate.split(";")[1]);
						
						if(Integer.parseInt(itemToUpdate.split(";")[3]) == 0) {
							logger.error("updatePickUpForItems", "updatePickUpForItems Qty can not be zero. searchValue : " + obj.getSearchValue() + " --- itemToUpdate : " + 
						itemToUpdate + "  --- obj.getPickUpSource() : " + obj.getPickUpSource());
							
							DJException dJException = new DJException(); 
							dJException.setDevloperMessage(MPUWEBUtilConstants.LOGICAL_ERROR_CODE + " - Qty can not be zero. " + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE);
							throw dJException;
						} else {
							detail.setRequestedQuantity(Integer.parseInt(itemToUpdate.split(";")[3]));
						}
						
						if(kioskLockerItems.contains(item.getRqdId())) {
							detail.setItemStatus(MpuWebConstants.LOCKER_OPEN);
							trans.setRequestStatus(MpuWebConstants.LOCKER_OPEN);
						} else {
							detail.setItemStatus(MpuWebConstants.OPEN);
							trans.setRequestStatus(MpuWebConstants.OPEN);
						}
						
						detail.setStoreNumber(obj.getStoreNum());
						detail.setCreatedTimeStamp(currentTimeTimestamp);
						
						trans.setRqtId(item.getRqtId());
						trans.setOriginalSalescheck(item.getSalescheck());
						trans.setStoreNumber(obj.getStoreNum());
						if(request.getOrder().getExchangeFlag()){
							trans.setRequestType(MpuWebConstants.EXCHANGEIN5);
							trans.setReturnAuthCode(request.getOrder().getIdentifierType());
						}else{
							trans.setRequestType(MpuWebConstants.PICKUP);
						}
						//trans.setAssociateId(obj.getAssociateId());
						trans.setSearchMethod(obj.getSearchMethod());
						trans.setSearchValue(obj.getSearchValue());
						trans.setOriginalJson(request.getOrder().getOriginalJson());
						if(MpuWebConstants.SHOPIN.equalsIgnoreCase(obj.getPickUpSource())){
							
							
							trans.setKiosk(obj.getAssociateId());
						}else{
							trans.setKiosk(obj.getKiosk());
						}						
						trans.setCustomerName(request.getCustomer().getFirstName()+" "+request.getCustomer().getLastName());
						trans.setCardSwiped(obj.getCardSwiped());
						trans.setStartTime(currentTimeTimestamp);
						trans.setPickup_source(obj.getPickUpSource());
						
						if(null != obj.getSc_scan()) {							
							if("1".equalsIgnoreCase(obj.getSc_scan()) && obj.getSearchValue().equalsIgnoreCase(item.getSalescheck()))
								trans.setSc_scan(obj.getSc_scan());
							else
								trans.setSc_scan("0");
						} else {
							trans.setSc_scan("0");
						}
						
				
						break;
					}
				}
			}

			detailsList.add(detail);
			if (!(transIdList.contains(trans.getRqtId()))) {
				ordersList.add(trans);
				ordersList.get(ordersList.indexOf(trans)).setMpuActivityDetailList(detailsList);
				transIdList.add(trans.getRqtId());
			} else {
				ordersList.get(transIdList.indexOf(trans.getRqtId())).getMpuActivityDetailList().add(detail);
			}
		}

		return ordersList;
	}

	@SuppressWarnings("unchecked")
	public String getTimeAccToTimeZone(String dateTime, String storNo, String dateFormat) throws DJException {
		
		try {
			
			logger.info("getTimeAccToTimeZone",	"Entering getTimeAccToTimeZone dateTime : " + dateTime + "storNo : "+ storNo + "dateFormat : "+ dateFormat);
			
			storNo = DJUtilities.leftPadding(storNo, 5);
			Map<String, Object> storeInfotemp = new HashMap<String, Object>();
			
			if(null != storeInfo && null != storeInfo.get(storNo.toString())) {
				storeInfotemp = (Map<String, Object>) storeInfo.get(storNo.toString());
			
			} else {
				
				storeInfotemp = mpuWebServiceDAOImpl.getStoreDetails(storNo);
				if(null != storeInfotemp) {
					storeInfo.put(storNo.toString(), storeInfotemp);
				}
			}

			SimpleDateFormat date = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
			
			Date local = date.parse(dateTime);
			
			String storeTimeZone = (String) storeInfotemp.get("timeZone");
			
		    SimpleDateFormat dateZone = new SimpleDateFormat(dateFormat);
		    
		    dateZone.setTimeZone(TimeZone.getTimeZone(storeTimeZone));
			
			logger.info("getTimeAccToTimeZone", "Exit getTimeAccToTimeZone date : "+ dateZone.format(local));
			
			return dateZone.format(local);
		
		} catch(DJException djEx) {
			
			throw djEx;
			
		} catch(Exception ex) {
			
			logger.error("getTimeAccToTimeZone", ex);
			
			DJException djEx = new DJException("Error", ex.hashCode()+"", ex.getMessage());
			throw djEx;
		}
	}

	private List<OrderItem> getItemsForNPOSMPU(Integer transId, String storeNo) throws DJException {	// get item details corresponding to transId
		
		logger.info("getItemsForNPOSMPU", "Entering getItemsForNPOSMPU storeNo : " + storeNo + " -- transId : " + transId);
		
		List<OrderItem> orderItems = pickUpServiceDAO.getOrderforNPOSMPU(transId, storeNo);
		logger.info("getTimeAccToTimeZone", "Exit getTimeAccToTimeZone orderItems : "+ orderItems);
		return orderItems;
		
	}

	public List<ItemDTO> getAllItemsForCustomer(CustomerDTO customerDTO,
			String typeOfIdentification, String salesCheckNumber)
			throws DJException, ParseException {
		return null;
	}

	// shipra end

/*	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public String getActiveUserForMOD(String storeNo) throws DJException{
		String  ListModUser = null;
		Integer mod = 1;
		try{
			List<ActivityUserEntity> modActiveUserList = new ArrayList<ActivityUserEntity>();
			List<Map<String,Object>> modActiveUserMap = mCPDBDAO.getModActiveEntity(storeNo, mod);
			for (Map<String, Object> map : modActiveUserMap) {
				ActivityUserEntity modActiveUser = new ActivityUserEntity();
				modActiveUser.setUserFname(map.get("user_fname") + "");
				modActiveUser.setUserLname(map.get("user_lname") + "");
				modActiveUserList.add(modActiveUser);
				}
		if(modActiveUserList != null && modActiveUserList.size()>0){
			ListModUser = "";
			for (Iterator<ActivityUserEntity> iterator = modActiveUserList.iterator(); iterator.hasNext();) {
				ActivityUserEntity activeUserkEntity = (ActivityUserEntity) iterator.next();
				if(activeUserkEntity.getUserFname()!= null && activeUserkEntity.getUserFname().trim() != "" && !"null".equalsIgnoreCase(activeUserkEntity.getUserFname())){
				ListModUser = ListModUser+activeUserkEntity.getUserFname()+ " "+activeUserkEntity.getUserLname()+",";
		}
		}
//		if(ListModUser != null && !ListModUser.trim().equals("")){
		if(StringUtils.hasText(ListModUser)){
		ListModUser = ListModUser.substring(0, ListModUser.length()-1);
		}
		}
		}catch(Exception e){
				e.printStackTrace();
			}
		return ListModUser;
	}
*/


/*	private int getParentServerPort()
	{
		int port = 8080;
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		try {
		    ObjectName http =
		        new ObjectName("jboss.as:socket-binding-group=standard" +
		            "-sockets,socket-binding=http");
		    port =  (Integer) mBeanServer.getAttribute(http, "boundPort");
		} catch (Exception e) {
		    logger.error(e.getMessage(),e);
		}
		return port;
	}*/
	

	public String getPropertyFromAdaptor(String storeNum, String propertyName,
			String storeFormat) throws DJException {
		
		logger.info("getPropertyFromAdaptor","Entering PickUpServiceProcessor.getPropertyFromAdaptor");
		
		String propertyValue = pickUpServiceDAO.getPropertyFromAdaptor(storeNum, propertyName, storeFormat);
		
		logger.info("getPropertyFromAdaptor","Entering PickUpServiceProcessor.getPropertyFromAdaptor");
		
		return propertyValue;
	}

	//Puneet : This is a new method
		public Order searchTenderReturns(String returnAuthId ,String storeNo,String kioskName,String storeFormat) {
			Order returnOrder = null;
			try{
				
				/*try{
					String json = readAFile("D:\\test\\nposjson.txt");
					System.out.println("json:::"+json);
					returnOrder = (Order)createStringToObject(json, new TypeReference<Order>(){});
					//RequestDTO requestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(order);
					
					
						} catch (Exception e) {
							System.out.println("eeeeeeeeee"+e);
							e.printStackTrace();
						}*/
				returnOrder = pickUpServiceDAO.searchTenderReturns(returnAuthId,storeNo,storeFormat);
				if(null != returnOrder){
					//Setting order status as EXCHANGE to identify that this order has detail about return items
					//for exchange order.
					/*returnOrder.setStatus("EXCHANGE");
					returnOrder.setExchangeFlag(true);*/
					returnOrder.setKioskName(kioskName);
					//recieveWorkService.recieveWork(order,status,stringBuffer);
					 /*webServicesProcessorImpl.createRequest(requestDTONPOS.getOrder(), requestDTONPOS.getCustomer(), requestDTONPOS.getItemList(),
								requestDTONPOS.getPaymentList(), requestDTONPOS.getTask())
					workId=stringBuffer.toString().trim();*/
					//recieveWorkService.modStateChanged(workId,order);
				}			
			}catch (Exception e) {
				logger.error(e.getMessage(),e);			
			}
			return returnOrder;
		}
		@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		private RequestDTO createExchangein5(RequestDTO requestDTO,String searchMethod, String searchValue,String kiosk,String storeNumber) throws Exception{

			OrderDTO orderDTO = requestDTO.getOrder();
			List<ItemDTO> itemList = requestDTO.getItemList();
			String CurrTime = getTimeAccToTimeZone(new Date() + "", requestDTO.getOrder().getStoreNumber(), "dd/MM/yyyy HH:mm:ss") + "";
			// customerId sometimes comes blank . how to calculate parentrqtId then?
			// getting parent rqtId for this request and then setting all the reqd details
			String returnParentId = getParentRqtId( orderDTO.getSalescheck(), orderDTO.getStoreNumber(), orderDTO.getCustomerId(), orderDTO.getTimeAssigned());
			
			// if parentId is null or blank , then create a request in db, ie in queue_trans and queue_details
			if(null == returnParentId || "".equals(returnParentId) ){
			webServicesProcessorImpl.createRequest(requestDTO.getOrder(), requestDTO.getCustomer(), requestDTO.getItemList(), requestDTO.getPaymentList(), requestDTO.getTask());	
			}
			orderDTO.setReturnParentId(returnParentId);
			orderDTO.setRequestType("EXCHANGEIN5");
			orderDTO.setRequestStatus("OPEN"); // manually setting it OPEN
			orderDTO.setCustomer_name(orderDTO.getCustomer_name());
			orderDTO.setSearch_method(searchMethod);
			orderDTO.setSearch_value(searchValue);
			orderDTO.setKioskName(kiosk);
			orderDTO.setCreateTimestamp(CurrTime);
			
			// setting the required details for item
			for(ItemDTO item : itemList){
				item.setReturnParentId(getParentRqdId(orderDTO.getSalescheck(),	orderDTO.getStoreNumber(), item.getDivNum(), item.getItem(), item.getSku(), item.getItemSeq(),
						orderDTO.getReturnParentId()));
				item.setDeliveredQuantity(0);
				item.setItemQuantityNotDelivered("0");
				item.setCreateTime(CurrTime);
				item.setUpdateIimestamp(CurrTime);
				item.setStoreNumber(storeNumber);
				// item status, store number and qty already set from order that come from NPOS, to test : data not coming from NPOS
			}
			
			return requestDTO;
		}
		
		public List<OrderItem> getItemsForHelp(String kioskName){
			List<OrderItem> itemList = new ArrayList<OrderItem>();
			OrderItem item = new OrderItem();
			
			item.setItemDescr("No description found");
			item.setItemDestination(kioskName);
			item.setItemDivision("000");
			item.setItemInquiryType("null"+" TO "+kioskName);
			item.setItemNo("00000");
			item.setItemQty(0);
			item.setItemQuantityActual(0);
			item.setItemQuantityAvailable(0);
			item.setItemQuantityRequested(0);
			item.setItemQuantityUnconfirmed(0);
			item.setItemSku("000");
			item.setItemStatus("completed");
			item.setItemSource("null");		
			itemList.add(item);
			return itemList;
		}
		
		public String notifyModForCouponBypass(String storeNo, ItemDTO itemDTO)
				throws DJException {
			modNotificationProcessorImpl.sendMODNotification(itemDTO,16);
			return "Success";
		}
		
		public String notifyModForSignOverride(String storeNo, ItemDTO itemDTO)
				throws DJException {
			modNotificationProcessorImpl.sendMODNotification(itemDTO,15);
			return "Success";
		}

		
	public SalesCheckDetails getVehicleInformation(PickUpReturnDTO pickUpItems, String salesCheckno)throws DJException {
		logger.info("Inside PickupserviceProcessorImpl.getVehicleInformation for salescheck: ", salesCheckno);
		List<?> orders= pickUpItems.getResponseData();
		ObjectMapper obj = new ObjectMapper();		
		List<RequestDTO>orderList = obj.convertValue(orders, new TypeReference<List<RequestDTO>>() {});
		SalesCheckDetails salesCheckDetails = new SalesCheckDetails();
		if(CollectionUtils.isNotEmpty(orderList)){			
			List<VehicleInfo>vehicleInfoList = new ArrayList<VehicleInfo>();
			List<SalesCheckWorkDetail>workList= new ArrayList<SalesCheckWorkDetail>(); 
			VehicleInformation vehicleInformation= new VehicleInformation();
			int getVehicleInfoByFlagCount = 0;
			for (RequestDTO order:orderList){			
				VehicleInfo vehicleInfo = getVehicleInfoByFlag(order.getOrder().getSalescheck(),convertToDate(order.getOrder().getTimeAssigned()));
				if (vehicleInfo != null	&& vehicleInfo.isShopInOptedFlag()) {
					getVehicleInfoByFlagCount = getVehicleInfoByFlagCount +1 ;
					VehicleInfo vInfo= new VehicleInfo();
					vInfo = vehicleInfo;
					vehicleInfoList.add(vInfo);
				}
			}
			for (RequestDTO order:orderList){
				SalesCheckWorkDetail workDetail = new SalesCheckWorkDetail();
				BigDecimal amt = CommonUtils.getLayawayBalance(order.getPaymentList());
				
				
				if(order.getOrder().isBalanceDueFlag() || !amt.equals(BigDecimal.ZERO)){ 
					workDetail.setErrorDescription("Balance Due :" +CommonUtils.getLayawayBalance(order.getPaymentList()));
					workDetail.setOrderStatus("false");
				}
				/*if(order.getOrder().){
					workDetail.setErrorDescription("pick can not be create because customer did not send confirmation ");
				}*/
				logger.info("Check for non null values for orderList", "");
				String work_id = order.getOrder().getRqtId();
				if(org.apache.commons.lang.StringUtils.isNotBlank(work_id)){
					workDetail.setWorkId(Integer.parseInt(order.getOrder().getRqtId()));
					workDetail.setSalesCheckNumber(order.getOrder().getSalescheck());	
					List<SalesCheckWorkItemDetail> workItems = new ArrayList<SalesCheckWorkItemDetail>();
					int itemsNotReadyForPickup = 0;
					for(ItemDTO item:order.getItemList()){
						boolean isItemReadyForPickup = true;
						String rqt_id  = item.getRqdId();
						//JIRA-25562 (Shop in : Not delivered partial qty for shop in orders. delivered orders is also available for pick up)
						//String item_qty = item.getQty();
						String item_qty = item.getItemQuantityAvailable();
						//String item_qty = item.getQtyRemaining();

						String divItemSku  = item.getDivNum()+item.getItem()+item.getSku();
						
						if(org.apache.commons.lang.StringUtils.isNotBlank(rqt_id) && 
								org.apache.commons.lang.StringUtils.isNotBlank(item_qty) && 
								org.apache.commons.lang.StringUtils.isNotBlank(divItemSku)){
							SalesCheckWorkItemDetail sywrItem = new SalesCheckWorkItemDetail();
							sywrItem.setItemId(Integer.parseInt(rqt_id));
							sywrItem.setItemDesc(item.getThumbnailDesc());
							sywrItem.setNewQty(Integer.parseInt(item_qty));
							sywrItem.setDivItemSku(divItemSku);
							if(item.getItemPromisedDate() != null && "ON ORDER".equalsIgnoreCase(item.getItemStatus())){
								sywrItem.setItemStatus("false");
								isItemReadyForPickup = false;
								sywrItem.setErrorDescription("Item Unavailable-Promise date : " + item.getItemPromisedDate());
							}
							
							if ("BALANCE DUE".equalsIgnoreCase(item.getItemStatus())) {
								sywrItem.setErrorDescription("Balance Due on item");
								sywrItem.setItemStatus("false");
							}
							if ("IN PROGRESS".equalsIgnoreCase(item.getItemStatus()) || "ON ORDER".equalsIgnoreCase(item.getItemStatus())) {
								sywrItem.setErrorDescription("Not Ready for Pickup");
								sywrItem.setItemStatus("false");
								isItemReadyForPickup = false;
							}
							
							if("044".equalsIgnoreCase(item.getDivNum())) {
								sywrItem.setItemStatus("false");
								isItemReadyForPickup = false;
								sywrItem.setErrorDescription("Item Unavailable-Jewelry Item");
							}
							workItems.add(sywrItem);
							
						}
						/*if(item.getItemPromisedDate()!= null){
							workDetail.setErrorDescription("order as on order and will not allow to create pickup");
						}*/
						if (isItemReadyForPickup == false) {
							itemsNotReadyForPickup++;
							isItemReadyForPickup = true;
						}
						
					}
					if(null != order.getItemList() && order.getItemList().size() > 0 && itemsNotReadyForPickup == order.getItemList().size()){
						workDetail.setErrorDescription("None of the items qualify for pickup");
						workDetail.setOrderStatus("false");
					}
					workDetail.setItems(workItems);
					workList.add(workDetail);
				}			
				
			}
			List<VehicleInfo> vehicleInfoLisDuplicateRemoved = removeDuplicate(vehicleInfoList);		
			vehicleInformation.setVehicleInfo(vehicleInfoLisDuplicateRemoved);
			salesCheckDetails.setWorkDetails(workList);	
			salesCheckDetails.setVehicleInformation(vehicleInformation);
		}else {
			salesCheckDetails.setErrorMessage("No pickup Items is available for salescheck :"+salesCheckno);
		}
		if(orderList.size() == 0){
			salesCheckDetails.setErrorMessage("No pickup Items is available for salescheck :"+salesCheckno);
		}
		logger.info("Exit PickUpServiceProcessorImpl.getVehicleInformation for salescheck :",salesCheckno);
		return salesCheckDetails;
		}
		
		public VehicleInfo getVehicleInformation(String salesCheck, Date date, String url){
			logger.info("enter in PickUpServiceProcessorImpl.getVehicleInformation for Kana with salesCheck :" + salesCheck	+ " and date :" + date,"");
			VehicleInfo vehicleInfo = null;
			try {			
				RetrieveVehicleInfoRequest request = new RetrieveVehicleInfoRequest();
				ResponseEntity<RetrieveVehicleInfoResponse> response = null;
				SalescheckInfoRequest salescheckReq = new SalescheckInfoRequest();
				SalescheckInfo salesCheckInfo = new SalescheckInfo();
				salesCheckInfo.setSaleDate(date);
				List<SalescheckInfo> salesCheckInfoList = new ArrayList<SalescheckInfo>();
				salesCheckInfoList.add(salesCheckInfo);		
				salesCheckInfo.setSalesCheckNumber(salesCheck);
				salescheckReq.setSalescheckInfo(salesCheckInfoList);
				request.setDATAAREA(salescheckReq);
				CONTROLAREA controlArea = new CONTROLAREA();
				controlArea.setTransactionID("1111");
				request.setCONTROLAREA(controlArea);
				logger.info("call service for get vehicle information for salescheck :"+salesCheck+ "and date "+date,"");
				logger.info("Url for get vehicle information is :"+url,"");
				response = restTemplate.postForEntity(url, request, RetrieveVehicleInfoResponse.class);		
				List<SalescheckInfoRes> salesCheckInfos = response.getBody().getDATAAREA().getSalescheckInfoRes();
				for(SalescheckInfoRes salesCheckInformation :salesCheckInfos){
					logger.info("get shopin opted falg :" + salesCheckInformation.getShopInOpted(),"");
					com.shc.schema.VehicleInfo KanaVehicleInfo = salesCheckInformation.getVehicleInfo();
					logger.info("vehicle info for salescheck :"+salesCheck+" shopin Opted Flag :"+salesCheckInformation.getShopInOpted(),"");
					if(salesCheckInformation.getShopInOpted().equalsIgnoreCase("Y")){
						vehicleInfo = new VehicleInfo();
						vehicleInfo.setShopInOptedFlag(true);
						vehicleInfo.setColor(KanaVehicleInfo.getVehicleColor());
						vehicleInfo.setMake(KanaVehicleInfo.getVehicleMake());
						vehicleInfo.setType(KanaVehicleInfo.getVehicleType());
						vehicleInfo.setVehicleYear(KanaVehicleInfo.getVehicleYear());
					}
				}
				
			} catch (Exception e) {
				logger.error("Exception in PickUpServiceProcessorImpl.getVehicleInformation from Kana: ", e);
			}
			logger.info("exit PickUpServiceProcessorImpl.getVehicleInformation for Kana with salesCheck :" + salesCheck	+ " and date :" + date,"");
			return vehicleInfo;
		}

		
		public VehicleInfo getVehicleInfoByFlag(String salesCheck, String createdTime) {
			logger.info("Inside PickUpServiceProcessorImpl.getVehicleInfoByFlag for salescheck: ", salesCheck+" createdTime: "+createdTime);
			boolean checkVehicleFlag  = false;
			VehicleInfo vehicleInfo = new VehicleInfo();
			try {
				checkVehicleFlag = Boolean.parseBoolean(PropertyUtils.getProperty("CHECK_VEHICLE_INFO_FLAG"));
			} catch (Exception e) {
				logger.error("Exception while getting property CHECK_VEHICLE_INFO_FLAG ", e);			
			}
			logger.info("vehicle flag is "+checkVehicleFlag ,"");
			if(checkVehicleFlag){
				vehicleInfo = getVehicleInfo(salesCheck,createdTime);
			}else{
				vehicleInfo.setShopInOptedFlag(true);
			}
			logger.info("Exit PickUpServiceProcessorImpl.getVehicleInfoByFlag for salescheck: ", salesCheck+" createdTime: "+createdTime);
			return vehicleInfo;	
		}
		
		public VehicleInfo getVehicleInfo(String salesCheck, String createdTime) {
			logger.info("Enter in PickUpServiceProcessorImpl.getVehicleInfo for salescheck :"+salesCheck+"and date :"+createdTime,"");
	 		VehicleInfo vehicleInfo = null; 
	 		String url = null;	
			DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {		
				url = PropertyUtils.getProperty("SHOPIN_URL_FOR_VEHICLE_INFO");
				date = writeFormat.parse(createdTime);	
				vehicleInfo = getVehicleInformation(salesCheck, date, url);
			} catch (Exception e) {			
				logger.info("Exception occourred inside getVehicleInfo", e);
			}
			
	 		if(vehicleInfo == null){
	 			logger.info("no vehicle information is available for salescheck :"+salesCheck+ "and date :"+date,"");
	 			vehicleInfo = new VehicleInfo();
	 			vehicleInfo.setShopInOptedFlag(false);
	 		}
	 		logger.info("Exit getVehicleInfo for salescheck :"+salesCheck+"and date :"+createdTime,"");
	 		return vehicleInfo;			
		}	
			
		private String convertToDate(String dateStr ){
			logger.info("Inside PickUpServiceProcessorImpl.convertToDate: ", dateStr);
		    DateFormat writeFormat = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy");
		    Date date = null;
		    String formatttedDate=null;
		    try {
		       date = writeFormat.parse(dateStr);
		       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		       formatttedDate=format.format(date);
		    } catch ( ParseException e ) {
		    	logger.info("Exception in PickUpServiceProcessorImpl.convertToDate: ", e);		        
		    }
		    logger.info("Exit PickUpServiceProcessorImpl.convertToDate: ", formatttedDate);
		    return formatttedDate;
		}
		
		private List<VehicleInfo> removeDuplicate(List<VehicleInfo> inputList){
			logger.info("Inside PickUpServiceProcessorImpl.removeDuplicate", "");
			HashSet<VehicleInfo> h = new HashSet<VehicleInfo>(inputList);
			inputList.clear();
			inputList.addAll(h);
			logger.info("Exit PickUpServiceProcessorImpl.removeDuplicate", "");  
			return inputList;
		}	
		
		@Transactional
		public ShopinServiceResponse initiatePickupForShopin(ShopinRequestDTO shopinRequestDTO) throws DJException {
			logger.info("Inside PickUpServiceProcessorImpl.initiatePickupForShopin", ""); 
			ShopinServiceResponse shopinServiceResponse = null;
			Map<String, RequestDTO> exchangeIn5Map = new HashMap<String, RequestDTO>();			
			//Additional lookup option IVPU - search based on email Starts
			String numType = MpuWebConstants.SHOPIN_SALESCHECK;
			if(shopinRequestDTO != null && shopinRequestDTO.getSywrId() != null && shopinRequestDTO.getSywrId().contains("@")) {
				numType = MpuWebConstants.CUSTOMERID;
			}
			//List<RequestDTO> requestDTOListTemp = getAllRequestsFromEhCache(shopinRequestDTO.getStoreNo(), MpuWebConstants.PICK_UP_REQUEST,	shopinRequestDTO.getSywrId(), MpuWebConstants.SHOPIN, "", "SALESCHECK");
			List<RequestDTO> requestDTOListTemp = getAllRequestsFromEhCache(shopinRequestDTO.getStoreNo(), MpuWebConstants.PICK_UP_REQUEST,	shopinRequestDTO.getSywrId(), MpuWebConstants.SHOPIN, "", numType);
			//Additional lookup option IVPU - search based on email Ends
			
			String queueKey = DJUtilities.concatString(shopinRequestDTO.getStoreNo(), "-", shopinRequestDTO.getSywrId(),"-", MpuWebConstants.PICK_UP_REQUEST, "-", MpuWebConstants.SHOPIN, "-", "",  "-", numType);
			logger.info("Inside PickUpServiceProcessorImpl.initiatePickupForShopin", "queueKey ======================== : " + queueKey);
			String storeNum = shopinRequestDTO.getStoreNo();
			Set<String> saleschckList = new HashSet<String>();
			int noOfRows = 0;	
			
			List<ItemDTO> itemList = new ArrayList<ItemDTO>();
			List<ItemDTO> itemListToBeUpdated = new ArrayList<ItemDTO>();
			List<String> rqtIdToBeUpdated = new ArrayList<String>();
			List<String> shopInLockerItems = new ArrayList<String>();
			List<MPUActivityDTO> activityDTOList = new ArrayList<MPUActivityDTO>();		
			Map<String,String> rqdIdUpcMap = new HashMap<String, String>();
			
			
			if(requestDTOListTemp.size()>0){
				for (RequestDTO request : requestDTOListTemp){	
					for (ItemDTO item : request.getItemList()){
						for (ShopinItemDetailDTO items : shopinRequestDTO.getCurbsideItems()){
							if(org.apache.commons.lang.StringUtils.isNotBlank(item.getRqdId()) && org.apache.commons.lang.StringUtils.isNotBlank(items.getItemId())){
								if (item.getRqdId().equalsIgnoreCase(items.getItemId())) {
									itemList.add(item);	
									
								}
							}					
						}
					}
					saleschckList.add(request.getOrder().getSalescheck());
				}
				
				//converting shopin request to kiosk pickup format
				PickUpSelectedItems obj  = convertShopinRequestDTOToPickUpSelectedItems(shopinRequestDTO,itemList);	
				
				
				
				
				/**
				 * Code for secured indicator flag   JIRA-25157
				 * 
				 * */
				Map<String, Boolean> securedFlagMap = new HashMap<String, Boolean>(); 
				List<PaymentDTO> allPaymentList=new ArrayList<PaymentDTO>();
				ArrayList<String>  rqtIdList=new ArrayList<String>();
				
				/**
				 * For Refreshing the original JSON from the one which come from NPOS
				 * @author nkumar1
				 */
				Map<String,String> rqtIdOrgJsonMap = new HashMap<String, String>();
				
				for (String items : obj.getItemsSelectedForPickUp()){		
					if(!securedFlagMap.containsKey(items.split(";")[0])) {
						for(RequestDTO request : requestDTOListTemp) {		
							String rqtId=request.getOrder().getRqtId();
							if(null != request && items.split(";")[0].equalsIgnoreCase(rqtId)) {
								securedFlagMap.put(rqtId, request.getOrder().isSecureIndicator());						
								
								
								if(!rqtIdList.contains(rqtId)){
									rqtIdList.add(rqtId);
									List<PaymentDTO> paymentList=request.getPaymentList();
									ArrayList<PaymentDTO> paymentFinal=new ArrayList<PaymentDTO>();
									
									if(paymentList!=null){
										for(PaymentDTO payment:paymentList){
											payment.setRqtId(rqtId);
											paymentFinal.add(payment);
											
											
										}
										
									}
									
									if(!paymentFinal.isEmpty())	{
										allPaymentList.addAll(paymentFinal);
									}
									
									/**
									 * For Refreshing the original JSON from the one which come from NPOS
									 * @author nkumar1
									 */
									if(null!=request.getOrder()){
										rqtIdOrgJsonMap.put(rqtId, request.getOrder().getOriginalJson());
									}
									
									if(null!=request.getItemList()){
										for(ItemDTO itm:request.getItemList()){
											String[] itemArray=items.split(";");
											if(itemArray.length>7){
												if(itemArray[4].equals(itm.getDivNum()) && itemArray[5].equals(itm.getItem()) 
														&& itemArray[6].equals(itm.getSku()) && null!=itm.getUpc()){
													rqdIdUpcMap.put(itemArray[1], itm.getUpc());
												}
											}
										}
									}
								}
								break;
							}				
						}
					}
				}
				
				noOfRows = pickUpServiceDAO.updateSecuredDBFlag(securedFlagMap,obj.getStoreNum());
				pickUpServiceDAO.deleteAllPaymentInfo(rqtIdList, obj.getStoreNum());
				mpuWebServiceDAOImpl.createPaymentList(allPaymentList, 0, obj.getStoreNum());	
				if(!rqdIdUpcMap.isEmpty()){
					
					int upcUpdated = pickUpServiceDAO.updateUPC(rqdIdUpcMap,obj.getStoreNum());
					logger.info("Rows updated for upcUpdate = ", upcUpdated);
				}
				//Code for secured indicator flag
				
				/**
				 * For refreshing the OriginalJson Coming from MPU
				 * @author nkumar1
				 */
				int rowsUpdated = pickUpServiceDAO.updateOriginalJson(rqtIdOrgJsonMap, obj.getStoreNum());
				logger.info("Rows updated for Refreshing originalJSON = ", rowsUpdated);				
				
				// check for locker order
				for (RequestDTO request : requestDTOListTemp) {				
					for (ItemDTO item : request.getItemList()) {
						for (String items : obj.getItemsSelectedForPickUp()) {
							if (null != item && items.split(";")[1].equalsIgnoreCase(item.getRqdId())) {
								exchangeIn5Map.put(item.getRqtId(), request); // EI5 change								 
								item.setItemQuantityRequested(items.split(";")[3]);
								item.setRequestType(request.getOrder().getRequestType());
								
								//Exchange related code
								if(request.getOrder().getExchangeFlag()) {
									item.setRequestType(MpuWebConstants.EXCHANGEIN5);
									item.setCommentList(request.getOrder().getIdentifierType());
								}
								
								itemListToBeUpdated.add(item);	
								rqtIdToBeUpdated.add(item.getRqtId());
									if(MpuWebConstants.N.equalsIgnoreCase(obj.getCurbside())) {
										LockerDTO lockerInfo = lockerServiceDAOImpl.getPinNumberFromSalescheck(item.getSalescheck(),item.getStoreNumber(),request.getOrder().getTimeAssigned());
										if(null != lockerInfo)
										{
											webServicesProcessorImpl.printLockerTicket(lockerInfo.getStoreNo(),lockerInfo.getPinNo(),lockerInfo.getCustomerName(), shopinRequestDTO.getKioskName());
											shopInLockerItems.add(item.getRqdId());
										}
									}								
								break;
							}
						}
					}
				}// end of check for locker
				
				if(itemListToBeUpdated.size()>0 && null !=itemListToBeUpdated){
					noOfRows = pickUpServiceDAO.initiatePickUpForItems(itemListToBeUpdated,storeNum,shopinRequestDTO.getKioskName());
					
					if (!(noOfRows == 0)) {
						
						noOfRows = pickUpServiceDAO.initiatePickUpActivityForItems(itemListToBeUpdated, storeNum,"CREATE");
						
						List<MPUActivityDTO> activityList= createActivityDTO(obj,shopInLockerItems,requestDTOListTemp);
						
						for(MPUActivityDTO activityDTO : activityList){
							activityDTO.setPickup_source(MpuWebConstants.SHOPIN);
							activityDTOList.add(activityDTO);
						}						
						associateActivityServicesProcessor.insertMPUActivityData(activityList);
						
					}
					
				}			
				
				//Exchange in5 check...
				if(CollectionUtils.isNotEmpty(exchangeIn5Map.values())){					
					List<RequestDTO> exchangeList =new ArrayList<RequestDTO>(exchangeIn5Map.values());
					if(CollectionUtils.isNotEmpty(exchangeList)){
						for(RequestDTO requestDTO: exchangeList){
							try {
								checkAndUpdateExchangeIn5(requestDTO);
							} catch (Exception e) {
								logger.error("Exception for checkAndUpdateExchangeIn5 in shopin :: ", e);								
							}
						}
					}
				}				
				
				//Vehicle information insert or update
				VehicleInfo vehicleInfo = shopinRequestDTO.getVehicleInfo();	
				try {
					if(vehicleInfo !=null){
						logger.info("VehicleInformation present : ","YES");
						pickUpServiceDAO.insertVehiclInfo(vehicleInfo, shopinRequestDTO.getNotificationID(),rqtIdToBeUpdated,shopinRequestDTO.getStoreNo());
					}else{
						logger.info("VehicleInformation present : ","NO");				
					}
				} catch (Exception e) {
					logger.info("Exception while insert/update vehicle info", e);				
				}//end of vehicle info
			}	
			
			//shopin report change			
			try {		
				if(noOfRows!= 0){
				MpuPickUpReportResposne  shopinPickReportResp =shopInServiceProcessor.insertRecordForShopInReport(shopinRequestDTO);
				logger.info("Inside PickUpServiceProcessorImpl.initiatePickupForShopin shopin report service response: ", shopinPickReportResp.toString());
				}
			} catch (Exception e) {	
				logger.info("Inside PickUpServiceProcessorImpl.initiatePickupForShopin exception for shopin report : ", e);
			}	//end for shopin report.	
			
			if(noOfRows!= 0){
				shopinServiceResponse = setShopinResposne(MpuWebConstants.SHOPIN_SUCCESS, shopinRequestDTO.getNotificationID(), MpuWebConstants.SHOPIN_SUCCESS_RESPOSNE);
			}else{
				shopinServiceResponse = setShopinResposne(MpuWebConstants.SHOPIN_FAIL, shopinRequestDTO.getNotificationID(), MpuWebConstants.SHOPIN_FAIL_RESPONSE);
			}
			logger.info("Exit PickUpServiceProcessorImpl.initiatePickupForShopin", "");
			
			//Shopin issues 1820
			EhCacheCache requestQueueCache = (EhCacheCache) cacheManager.getCache("pickupCache");
			requestQueueCache.evict(queueKey);
			//Shopin issues 1820
			
			return shopinServiceResponse;			
		}	
		
		
		
		public void checkAndUpdateExchangeIn5(RequestDTO request) throws Exception, JsonMappingException, IOException{
			logger.info("Inside checkAndUpdateExchangeIn5 : ", "");
			boolean exchangeFlag = request.getOrder().getExchangeFlag();
			logger.info("ExchangeFlag for salescheck: "+request.getOrder().getSalescheck()+"is :", exchangeFlag);	
			
			RequestDTO mainOrderRequestDTO = null;
			List<String> ei5ProcessedRqtId =  new ArrayList<String>(); 
			if(exchangeFlag){		
				String returnAuthId = "" ;			
				try {			
					for(ItemDTO itemDTO : request.getItemList()){
						RequestDTO returnRequestDTO= null;						
						int rqt_id = pickUpServiceDAO.getReturnIn5QueueTrans(itemDTO.getCommentList(), itemDTO.getStoreNumber());
						if(rqt_id>0){
							ei5ProcessedRqtId.add(itemDTO.getRqtId());
							String fields = MpuWebConstants.ITEM + "," + MpuWebConstants.IDENTIFIER;
							List<String> status = Arrays.asList(MpuWebConstants.OPEN, MpuWebConstants.EXPIRED, MpuWebConstants.COMPLETED);
							returnRequestDTO = webServicesProcessorImpl.getRequestData(String.valueOf(rqt_id), itemDTO.getStoreNumber(), fields, null,status);
							returnAuthId  = returnRequestDTO.getOrder().getReturnInFive().getPendingReturnAuthNumber() + "";
							logger.info("ReturnAuthCode is : ", returnAuthId);
							insertReturnIntoMPUTrans(mainOrderRequestDTO,returnAuthId);
						}
					}		
				} catch (DJException e) {
					logger.error("Exception inside checkAndUpdateExchangeIn5 :: ", e);					
				}					
			}
			logger.info("Exiting from checkAndUpdateExchangeIn5 : ", "");
		}

		
		
		public PickUpSelectedItems convertShopinRequestDTOToPickUpSelectedItems(ShopinRequestDTO shopinRequestDTO, List<ItemDTO> itemList){
			PickUpSelectedItems pickUpSelectedItems = new PickUpSelectedItems();			
			List<String> itemsSelectedForPickUpList = new ArrayList<String>();
			List<ShopinItemDetailDTO>  shopinItemDetailDTOList = shopinRequestDTO.getCurbsideItems();		
			
			pickUpSelectedItems.setStoreNum(shopinRequestDTO.getStoreNo());
			pickUpSelectedItems.setKiosk(shopinRequestDTO.getSywrId());
			pickUpSelectedItems.setSearchMethod("SALESCHECK");
			pickUpSelectedItems.setSearchValue("");
			pickUpSelectedItems.setPickUpSource("SHOPIN");	
			pickUpSelectedItems.setCurbside("N");
			pickUpSelectedItems.setCardSwiped(false);
			pickUpSelectedItems.setAssociateId(shopinRequestDTO.getKioskName());			
			
			for(ShopinItemDetailDTO shopinItemDetailDTO:shopinItemDetailDTOList){
				for(ItemDTO itemDTO :itemList){
					if(itemDTO.getRqdId().equals(shopinItemDetailDTO.getItemId())){
					String itemsSelectedForPickUp = itemDTO.getRqtId()+";"+ itemDTO.getRqdId()+";"+itemDTO.getSalescheck()+";"+shopinItemDetailDTO.getReqQuantity()+";"+itemDTO.getDivNum()+";"+itemDTO.getItem()+";"+itemDTO.getSku()+";"+itemDTO.getItemSeq()+";";
					itemsSelectedForPickUpList.add(itemsSelectedForPickUp);
					}
				}
			}	
			
			pickUpSelectedItems.setItemsSelectedForPickUp(itemsSelectedForPickUpList);			
			return pickUpSelectedItems;			
		}

			public void insertNotDeliverReasonInDEJ(String storeNumber,String userName, String itemId, String salescheck) throws DJException {
				
				HashMap<String, String> NotDeliverReasonCodeMap = new HashMap<String, String>();
				
				String appName = "MPU";
				NotDeliverReasonCodeMap.put("appName", appName);
				
				String dejApplEventTypeName = "MPUNotDeliverReason";
				NotDeliverReasonCodeMap.put("dejApplEventTypeName",	dejApplEventTypeName);
				
				String deviceId = "SNC002";
				NotDeliverReasonCodeMap.put("deviceId",deviceId);
				
				
				String storeEventTimeStamp = getTimeAccToTimeZone(new Date() + "", storeNumber, "dd/MM/yyyy HH:mm:ss");
				NotDeliverReasonCodeMap.put("eventTimeStamp",storeEventTimeStamp);
				
				String message = salescheck + "_" + itemId + "_" + "Card Not Present";
				NotDeliverReasonCodeMap.put("message",message);
				
				NotDeliverReasonCodeMap.put("storeNumber",storeNumber);

				NotDeliverReasonCodeMap.put("userName", userName);
				
				pickUpServiceDAO.insertNotDeliverReasonInDEJ(NotDeliverReasonCodeMap);
				
				
			}
			

		
		private ShopinServiceResponse setShopinResposne(String resposneCode,String notificationId,String resposneDetail){
			ShopinServiceResponse shopinServiceResponse = new ShopinServiceResponse();
			shopinServiceResponse.setResponseCode(Integer.parseInt(resposneCode));
			shopinServiceResponse.setNotificationId(notificationId);
			shopinServiceResponse.setResponseDetail(resposneDetail);
			return shopinServiceResponse;
		}

		public boolean clearCache(String storeNum, String kioskName,
				boolean refreshAll) throws DJException {
			EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
			if(refreshAll){
				requestQueueCache.clear();
			}else{
				String listKey = "PICKUP_"+storeNum+"_"+kioskName;
				logger.info("listKey....clearCache---", listKey);
				requestQueueCache.evict(listKey);
			}
			return true;
		}

		@SuppressWarnings("unchecked")
		public List<RequestDTO> getCachedContent(String storeNumber,
				String kiosk){
			List<RequestDTO> requestList = new ArrayList<RequestDTO>();
			String listKey = "PICKUP_"+storeNumber+"_"+kiosk;
			EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
			if(null!=requestQueueCache.get(listKey)){
				requestList = (ArrayList<RequestDTO>) requestQueueCache.get(listKey).get();
			}
			return requestList;
		}
	
		private void setCacheDirty(RequestDTO requestDTO){
			if(null!=requestDTO.getOrder()){
				EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
				String formattedStoreNo = org.apache.commons.lang3.StringUtils.leftPad(requestDTO.getOrder().getStoreNumber(), 5, '0');
				String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+requestDTO.getOrder().getKioskName();
				String ohmCacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+requestDTO.getOrder().getKioskName();
				logger.info("***cacheDirtyKey***", cacheDirtyKey);
				if(null!=requestQueueCache){
					requestQueueCache.put(cacheDirtyKey, "true");
					requestQueueCache.put(ohmCacheDirtyKey, "true");
				}
			}
		}
		
		@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public void updateMODNotificationPickupReturn(PickUpSelectedItems obj) throws DJException{
			Map<String,String> queueCount=webServicesProcessor.selectQueuestats(obj.getStoreNum(), obj.getKiosk());
			Integer mpuQueueCnt=Integer.parseInt(queueCount.get("MPU"));
			if(null!=mpuQueueCnt)
			{
				if(mpuQueueCnt > 10)
				{
					ItemDTO itemDTO=new ItemDTO();
					itemDTO.setRqtId(obj.getItemsSelectedForPickUp().get(0).split(";")[0]);
					itemDTO.setStoreNumber(obj.getStoreNum());
					itemDTO.setFullName("");
					itemDTO.setCommentList("There are "+ mpuQueueCnt +" no. of customers waiting at MPU and service time may be exceeded. Please check MPU coverage and call for assistance if needed.");
					List<ItemDTO> itemDTOs =new ArrayList<ItemDTO>();
					itemDTOs.add(itemDTO);
					modNotificationProcessorImpl.sendMODNotification(itemDTOs.get(0),10);
				}
			}
		}
/*		private int updateOriginalJson(Map<String,String> originalJsonMap, String storeNum) {
			
			//Get Original Json from DB
			
			List<String> rqtIds = new ArrayList<String>();
			for(Map.Entry<String, String> map : originalJsonMap.entrySet()) {
				rqtIds.add(map.getKey());
			}
			
			Map<String,String> originalJsonList = pickUpServiceDAO.getOriginalJson(rqtIds,storeNum);
			
			
			return 0;
			
		}*/

		//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public String initiatePickUpForItems(PickUpSelectedItems obj) throws DJException {
			
			
			List<String> kioskLockerItems = initiatePickUpForItemsAndGetLockerItems(obj);	
			
			String pickUpInitiation = "SUCCESS";
			if(kioskLockerItems.contains("NO RETURN FOR EXCHANGE")) {
				pickUpInitiation = "SUCCESS WITH NO RETURN FOR EXCHANGE";
			} else if(null != kioskLockerItems && !kioskLockerItems.isEmpty()) {
				pickUpInitiation = "SUCCESS WITH LOCKER ITEMS";
			}
			updateMODNotificationPickupReturn(obj);
			return pickUpInitiation;
			
		}
		public boolean isOnOrder(RequestDTO order){
			Boolean onOrderFlag = Boolean.FALSE;
			for (ItemDTO obj1 : order.getItemList()) {

				if((obj1.getItemPromisedDate()!=null && (StringUtils.isEmpty(obj1.getItemBinNumber())) && 
						("2".equalsIgnoreCase(obj1.getItemStatusCode()) || "3".equalsIgnoreCase(obj1.getItemStatusCode())))) {
					
					onOrderFlag = Boolean.TRUE;
					break;
				}
		}
			return onOrderFlag;
}
		
		public String getAssociateId(){
			String associateId=(String)MDC.get("associateId");
			logger.info("associateId from MDC", associateId);
			if(associateId==null||"".equals(associateId)){
				associateId="000075";
			}
			return associateId;
			
		}
		
		public void updateMODNotificationHelpRepair(String storeNo,String kiosk,String rqtId) throws DJException{
			
			logger.info("updateMODNotificationHelpRepair",	"Entering updateMODNotificationHelpRepair for Store: " + storeNo );
			Map<String,String> queueCount=webServicesProcessor.selectQueuestats(storeNo, kiosk);
			Integer mpuQueueCnt=Integer.parseInt(queueCount.get("MPU"));
			logger.info("updateMODNotificationHelpRepair",	"mpuQueueCnt: " + mpuQueueCnt);
			if(null!=mpuQueueCnt)
			{
				if(mpuQueueCnt > 10)
				{
					ItemDTO itemDTO=new ItemDTO();
					itemDTO.setRqtId(rqtId);
					itemDTO.setStoreNumber(storeNo);
					itemDTO.setFullName("");
					itemDTO.setCommentList("There are "+ mpuQueueCnt +" no. of customers waiting at MPU and service time may be exceeded. Please check MPU coverage and call for assistance if needed.");
					List<ItemDTO> itemDTOs =new ArrayList<ItemDTO>();
					itemDTOs.add(itemDTO);
					logger.info("updateMODNotificationHelpRepair",	"itemITOs: " + itemDTOs.get(0));
					modNotificationProcessorImpl.sendMODNotification(itemDTOs.get(0),10);
				}
			}
		}

		public void insertIntoMPUAssociateReport(MPUAssociateReportDTO associateReport) throws DJException {
			logger.info("insertIntoMPUAssociateReport",	"Entering insertIntoMPUAssociateReport associateReport: " + associateReport );
			String CurrTime = getTimeAccToTimeZone(new Date() + "", associateReport.getStoreNum(), "yyyy-MM-dd HH:mm:ss");
			pickUpServiceDAO.insertIntoMPUAssociateReport(associateReport,CurrTime);
			logger.info("updateMODNotificationHelpRepair",	"Entering updateMODNotificationHelpRepair associateReport");
			
		}

		public void updateDivOperation(List<String> rqdIdList, String storeNum)throws DJException {
			logger.info("updateDivOperation",	"Entering updateDivOperation rqdIdList " + rqdIdList );
			pickUpServiceDAO.updateDivOperation(rqdIdList, storeNum);	
			logger.info("updateDivOperation",	"Entering updateDivOperation");
		}
		
		/**
		 * @description Insert POS Message in to MPU queue - JIRA-25084
		 * @param RequestDTO requestDTO
		 * @return void
		 * @throws DJException
		 */
		@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public void orderPosMessage(RequestDTO requestDTO)	throws DJException {
			logger.info("orderPosMessage",	"Entering orderPosMessage requestDTO : " + requestDTO );
			//RequestDTO requestDTO = pickUpServiceDAO.nPosCallForHelpRepair(customerDetailDTO);

			String CurrTime = getTimeAccToTimeZone(new Date() + "", requestDTO.getOrder().getStoreNumber(), "dd/MM/yyyy HH:mm:ss");
			
			requestDTO.getOrder().setCreateTimestamp(CurrTime);
			requestDTO.getOrder().setRequestStatus(MpuWebConstants.OPEN);
			
			for(ItemDTO obj : requestDTO.getItemList()) {
				
				obj.setCreateTime(CurrTime);
				obj.setUpdateIimestamp(CurrTime);
			}
			
			if(requestDTO.getOrder().getKioskName() == null || requestDTO.getOrder().getKioskName().trim().equals("")) {
				requestDTO.getOrder().setKioskName(MpuWebConstants.KIOSK_MPU1);
			}

			List<RequestDTO> lRequestDTO = new ArrayList<RequestDTO>();
			lRequestDTO.add(requestDTO);
			
			List<MPUActivityDTO> activityList = ConversionUtils.convertRequestDTOtoMPUActivityDTO(lRequestDTO);
			
			sendMPUserviceWarningMsg(requestDTO.getCustomer().getStoreNumber());	
			
			List<Integer> posMessageTransId = associateActivityServicesProcessorImpl.insertMPUActivityData(activityList);	
			
			// insert into activity table
			List<ItemDTO> posMessageAction = new ArrayList<ItemDTO>();
			
			ItemDTO posMessageActionItem = new ItemDTO();
			
			posMessageActionItem.setRequestType(activityList.get(0).getRequestType());
			posMessageActionItem.setStoreNumber(lRequestDTO.get(0).getOrder().getStoreNumber());
			posMessageActionItem.setRqtId(String.valueOf(posMessageTransId.get(0)));
			posMessageActionItem.setCreatedBy(MpuWebConstants.SYSTEMUSER);
			
			posMessageAction.add(posMessageActionItem);
			
			pickUpServiceDAO.initiatePickUpActivityForItems(posMessageAction, requestDTO.getOrder().getStoreNumber(),"CREATE");
			
			updateMODNotificationHelpRepair(requestDTO.getCustomer().getStoreNumber(),lRequestDTO.get(0).getOrder().getKioskName(),posMessageActionItem.getRqtId());
			
			logger.info("orderPosMessage", "Exit orderPosMessage");
			
			try{
				
				EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("associateCache");
				String formattedStoreNo = DJUtilities.leftPadding(requestDTO.getOrder().getStoreNumber(), 5);
				logger.info("completeRequest", "completeRequest formattedStoreNo : " + formattedStoreNo + " -- KioskName : " + requestDTO.getOrder().getKioskName());
				String cacheDirtyKey = "isCacheDirty_"+formattedStoreNo+"_"+requestDTO.getOrder().getKioskName();
				logger.info("completeRequest", "completeRequest cacheDirtyKey : " + cacheDirtyKey);
				String ohmCacheDirtyKey = "isCacheDirty_OHM_"+formattedStoreNo+"_"+requestDTO.getOrder().getKioskName();
				if(null != requestQueueCache) {
					requestQueueCache.put(cacheDirtyKey, "true");
					requestQueueCache.put(ohmCacheDirtyKey, "true");
					logger.info("completeRequest", "completeRequest cache is dirty.");
				}
				
			} catch(Exception exp){
				
				logger.error("completeRequest", exp);
			}
		}
		
		//Exchange related API
	
		public int getFilteredEI5List(List<RequestDTO> lRequestDTO) throws DJException{
			int countOfFilter =0;
			for(RequestDTO objEI5 : lRequestDTO){
				if("EXCHANGEIN5".equalsIgnoreCase(objEI5.getOrder().getRequestType())){
					for(RequestDTO objRI5 : lRequestDTO){
						if("RETURNIN5".equalsIgnoreCase(objRI5.getOrder().getRequestType()) 
								&& objEI5.getOrder().getIdentifierType().equalsIgnoreCase(objRI5.getOrder().getIdentifierType()) ){
							
							objRI5.getOrder().setExchangeFlag(true); // exchangeFlag true for ReturnIN5 requesttype
							String relatedtransId = String.valueOf(objRI5.getOrder().getTrans_id());
							objEI5.getOrder().setRelatedtransaction(relatedtransId);
							countOfFilter++;
							break;
						}
					}
				}
			}
			return countOfFilter;
		}
		
		/** The service to verify if the store 
		 * has repair pickup/dropoff option
		 * @param storeNum String
		 * @return ResponseEntity<ResponseDTO>
		 */
		public boolean isRepairEnabled(String storeNum)	throws DJException{
			boolean isRepairFlag=mCPDBDAO.isRepairEnabled(storeNum);
			return isRepairFlag;
		}
		
		public PickUpDTO getPickUpDTO(String salesCheckno, String storeNo, String storeFormat, String notificationId, String sywrId) throws DJException{
			PickUpDTO pickUpDTO =new PickUpDTO();
			pickUpDTO.setStoreFormat(storeFormat);
			pickUpDTO.setStoreNumber(storeNo);
			pickUpDTO.setSywrId(sywrId);
			pickUpDTO.setKiosk(sywrId);
			pickUpDTO.setNotificationId(notificationId);
			
			if(sywrId != null && sywrId.contains("@")){
				//This method will get CustomerID corresponding to MailID
				ArrayList<Customer> customerList = pickUpServiceDAO.fetchCustomersfromNPOSByEmailID(pickUpDTO.getSywrId(), pickUpDTO.getStoreNumber(), pickUpDTO.getStoreFormat());
				if(customerList != null && customerList.size() > 0) {
					pickUpDTO.setAddressId(customerList.get(0).getAddressId());
					pickUpDTO.setCustomerId(customerList.get(0).getCustomerId());
					pickUpDTO.setIdStsCd(customerList.get(0).getIdStsCd());
				}
			}
			return pickUpDTO;
		}
		
		@Transactional
		private void insertReturnIntoMPUTrans(RequestDTO returnRequestDTO, String returnAuthCode) throws DJException {
			//setting the required things in the itemDTO
			String CurrTime = getTimeAccToTimeZone(new Date() + "", returnRequestDTO.getOrder().getStoreNumber(), "dd/MM/yyyy HH:mm:ss");
			if(null!=returnRequestDTO.getItemList() && !returnRequestDTO.getItemList().isEmpty()){
				for (ItemDTO item : returnRequestDTO.getItemList()) {
					// getting parent rqdId
				
					/*item.setReturnParentId(getParentRqdId(returnRequestDTO.getOrder().getSalescheck(),	returnRequestDTO.getOrder().getStoreNumber(), item.getDivNum(), item.getItem(), item.getSku(), item.getItemSeq(),
							returnRequestDTO.getOrder().getReturnParentId()));*/
					item.setReturnParentId(item.getRqdId());
					item.setDeliveredQuantity(0);
					item.setItemQuantityNotDelivered("0");
					item.setCreateTime(CurrTime);
					item.setUpdateIimestamp(CurrTime);
					item.setRequested_quantity(item.getQty());
					//item.setRqtId(item.getReturnParentId());
					item.setItemStatus("OPEN");
					item.setStoreNumber(returnRequestDTO.getOrder().getStoreNumber());
					if("0".equals(item.getQty())) {
						item.setQty("1");
					}
					item.setRequested_quantity(item.getQty());

				}
			}
			
			//setting the required things in the orderDTO
			returnRequestDTO.getOrder().setCreateTimestamp( getTimeAccToTimeZone(new Date() + "",returnRequestDTO.getOrder().getStoreNumber(), "dd/MM/yyyy HH:mm:ss"));
			returnRequestDTO.getOrder().setIdentifierType(returnAuthCode);
			returnRequestDTO.getOrder().setCustomer_name( returnRequestDTO.getCustomer().getFirstName() + " "+ returnRequestDTO.getCustomer().getLastName());
			returnRequestDTO.getOrder().setOriginalIdentifier(returnRequestDTO.getOrder().getOriginalIdentifier());
			returnRequestDTO.getOrder().setReturnParentId(String.valueOf(returnRequestDTO.getOrder().getRqtId()));
			//This is set as the return order is in mpu trans and with the associate, change the status to close when associate receives the order.
			returnRequestDTO.getOrder().setRequestStatus("OPEN");
			List<RequestDTO> listRequestDTO = new ArrayList<RequestDTO>();
			listRequestDTO.add(returnRequestDTO);

	    	/* try {
	    		 returnRequestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(returnOrder);
	    		 returnRequestDTO.getOrder().setCreateTimestamp("2014/09/10 09:19:05");
	    		
			} catch (Exception e) {
				DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
				throw djException;
			} */
	    	 
	    	List<MPUActivityDTO> mpuActivityDTOs = ConversionUtils.convertRequestDTOtoMPUActivityDTO(listRequestDTO);
				
			associateActivityServicesProcessorImpl.insertMPUActivityData(mpuActivityDTOs);
			
			//Inserting into the request_activity table for RETURN in 5
			for(RequestDTO requestDTO : listRequestDTO){
				for(ItemDTO itemDTO : requestDTO.getItemList()){
					itemDTO.setRequestType(MpuWebConstants.EXCHANGEIN5RETURN);
				}
				
				pickUpServiceDAO.initiatePickUpActivityForItems(requestDTO.getItemList(), requestDTO.getOrder().getStoreNumber(),"CREATE");
			}
		}
		
	
		
}