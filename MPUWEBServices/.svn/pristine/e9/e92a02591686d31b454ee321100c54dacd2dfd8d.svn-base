package com.searshc.mpuwebservice.processor.impl;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSIGNED_USER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DELIVERED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DIV_NUM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ESCALATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ESCALATION_TIME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FFM_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FROM_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FULL_NAME;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_IMAGE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_SEQ;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.KSN;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.LOCKER_ELIGIBLE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.MODIFIED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PLUS4;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SKU;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STOCK_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STOCK_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.THUMBNAIL_DESC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TO_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPC;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.VER;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.MDC;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.sears.creditcard.util.CreditCard;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.bean.DDMeta;
import com.sears.dj.common.mpu.exception.DDRMetaException;
import com.sears.dj.common.print.vo.CouponKioskPrintVO;
import com.sears.dj.common.print.vo.ItemPrintVO;
import com.sears.dj.common.print.vo.LockerKioskPrintVO;
import com.sears.dj.common.print.vo.MPUItemVO;
import com.sears.dj.common.print.vo.PackageVO;
import com.sears.dj.common.print.vo.TICouponKioskPrintVO;
import com.sears.dj.common.util.DJUtilities;
import com.sears.dj.common.ws.DJServiceLocator;
import com.sears.mpu.backoffice.domain.Coupon;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderItem;
import com.sears.mpu.backoffice.domain.Payment;
import com.sears.mpu.backoffice.domain.Reduction;
import com.sears.mpu.backoffice.domain.StoreInfo;
import com.sears.mpu.backoffice.domain.TICoupon;
import com.searshc.mpuwebservice.bean.ActivityDTO;
import com.searshc.mpuwebservice.bean.CustomerDTO;
import com.searshc.mpuwebservice.bean.HGRequestDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.LockerDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PackageDTO;
import com.searshc.mpuwebservice.bean.PaymentDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.TaskDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO;
import com.searshc.mpuwebservice.dao.KioskSOADao;
import com.searshc.mpuwebservice.dao.MCPDBDAO;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.dao.PickUpServiceDAO;
import com.searshc.mpuwebservice.processor.AssociateActivityProcessor;
import com.searshc.mpuwebservice.processor.ExpiredOrderProcessor;
import com.searshc.mpuwebservice.processor.LockerServiceProcessor;
import com.searshc.mpuwebservice.processor.MODNotificationProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceMODProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.processor.NPOSUpdateProcessor;
import com.searshc.mpuwebservice.processor.OBUUpdateProcessor;
import com.searshc.mpuwebservice.processor.PickUpServiceProcessor;
import com.searshc.mpuwebservice.processor.TIServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;
import com.searshc.mpuwebservice.util.SHCDateUtils;
import com.searshc.mpuwebservice.vo.PrintServiceResponse;
import com.searshc.mpuwebutil.util.CommonUtils;
import com.searshc.targetinteraction.OrderConfirmResponseDTO;
import com.searshc.targetinteraction.TIRequestDTO;

@Service("webServicesProcessorImpl")
public class MPUWebServiceProcessorImpl implements MPUWebServiceProcessor {
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;

	@Autowired
	private EhCacheCacheManager cacheManager; 

	@Autowired
	LockerServiceProcessor lockerServiceProcessor; 

	@Autowired
	PickUpServiceProcessor pickUpServiceProcessor; 

	@Autowired
	private NPOSUpdateProcessor nPOSUpdateProcessorImpl;

	@Autowired
	@Qualifier("MPUWebServiceMODProcessorImpl")
	MPUWebServiceMODProcessor webServiceMODProcessor;

	@Autowired
	private MODNotificationProcessor modNotificationProcessorImpl;

	@Autowired
	private PickUpServiceDAO pickUpServiceDAO;

	@Autowired
	private OBUUpdateProcessor oBUUpdateProcessor;

	@Autowired
	private KioskSOADao kioskSOADaoImpl;

	@Autowired
	MCPDBDAO mCPDBDAO;

	@Autowired
	AssociateActivityServiceDAO activityServiceDAO;

	@Autowired
	AssociateActivityProcessor associateActivityProcessor;

	@Autowired
	MpuWebDlvryStatusService mpuWebDlvryStatusService;

	@Autowired
	@Qualifier("TIServiceProcessorImpl")
	private TIServiceProcessor tiServiceProcessorImpl;	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ExpiredOrderProcessor expiredOrderProcessorImpl;

	private static transient DJLogger logger = DJLoggerFactory.getLogger(MPUWebServiceProcessorImpl.class);
	private static final int TGI = 7;

	private enum ProcessFlag {
		INSERT, REOPEN, DISCARD;
	}

	private Map<String, String> mODNotification = new HashMap<String, String>();
	
	
	//private List<Map<String, ItemDTO>>   expiredHGItems = new ArrayList<Map<String,ItemDTO>>();

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.MPUWebServiceProcessor#createRequest(com.searshc.mpuwebservice.bean.OrderDTO, com.searshc.mpuwebservice.bean.CustomerDTO, java.util.List, java.util.List, com.searshc.mpuwebservice.bean.TaskDTO)
	 */
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)	
	public RequestDTO createRequest(OrderDTO order, CustomerDTO customer, 
			List<ItemDTO> itemList, List<PaymentDTO> paymentList, TaskDTO task, String... notInMpu)
					throws DJException, DataAccessException, DDRMetaException {

		logger.info("Entering MPUWebServiceProcessorImpl.createRequest	order:",order.getSalescheck()+": customer: "+customer +": itemList: "+itemList +": paymentList: "+paymentList 
				+": task: "+task );

		RequestDTO requestDTO = new RequestDTO();

		requestDTO.setOrder(order);
		requestDTO.setCustomer(customer);
		requestDTO.setItemList(itemList);
		requestDTO.setPaymentList(paymentList);
		requestDTO.setTask(task);


		boolean isLockerEligible = Boolean.FALSE;

		if((MpuWebConstants.BINWEB).equalsIgnoreCase(order.getRequestType())) {

			//				RequestDTO requestDTO = new RequestDTO();
			requestDTO.setCustomer(customer);
			requestDTO.setItemList(itemList);
			requestDTO.setPaymentList(paymentList);
			requestDTO.setOrder(order);
			//requestDTO.setTask(task);
			isLockerEligible=lockerServiceProcessor.isOrderEligibleForLocker(requestDTO); //- Commented because target url is not available.
		}


		String customerFullName = MpuWebConstants.EMPTY_STRING;
		String storeNumber = order.getStoreNumber();
		if(null != order.getRequestType() && (MpuWebConstants.RETURN.equalsIgnoreCase(order.getRequestType()) || MpuWebConstants.RETURNIN5.equalsIgnoreCase(order.getRequestType()))){
			order.setRequestStatus(MpuWebConstants.COMPLETED);
		}
		else{
			order.setRequestStatus(MpuWebConstants.OPENSTATUS);
		}
		String requestType = order.getRequestType();
		String action = MpuWebConstants.CREATE;
		String currentStatus="";
		Date currentTime=Calendar.getInstance().getTime();
		String createTime = DJUtilities.dateToString(currentTime,MpuWebConstants.DATE_FORMAT);
		order.setCreateTimestamp(createTime);
		
		if(!StringUtils.hasText(order.getStoreFormat())){
			order.setStoreFormat(MpuWebConstants.SEARSRETAIL);
		}
		//String createTime = DJUtilities.dateToString(currentTime,MpuWebConstants.DATE_FORMAT);
		//	String createTime = pickUpServiceProcessor.getTimeAccToTimeZone(date +"", order.getStoreNumber(), MpuWebConstants.DATE_FORMAT);
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


		//order.setCreateTimestamp(dateFormat.format(date));
		long rqtId = mpuWebServiceDAOImpl.createOrderDTO(order);	//	Create Order in DB REQUEST_QUEUE_TRANS

		requestDTO.getOrder().setRqtId(rqtId + "");

		//logger.debug("createRequest","createOrderDTO completed");

		mpuWebServiceDAOImpl.createIdentifierDTO(customer, rqtId, paymentList, order.getSalescheck());	//	Insert data into DB REQUEST_IDENTIFIER 

		//logger.debug("createRequest","createIdentifierDTO completed");

		if(MpuWebConstants.REPAIRDROP.equalsIgnoreCase(order.getRequestType()) || 
				(MpuWebConstants.REPAIRPICK).equalsIgnoreCase(order.getRequestType()) || 
				(MpuWebConstants.HELP).equalsIgnoreCase(order.getRequestType())) {


			itemList = new ArrayList<ItemDTO>();
			ItemDTO itemDTO = new ItemDTO();

			//				itemDTO.setAssigneeFullName(assigneeFullName);
			itemDTO.setRqtId(order.getRqtId());
			itemDTO.setDivNum("000");
			itemDTO.setItem("0");
			itemDTO.setSku("000");
			itemDTO.setQty("0");
			itemDTO.setItemStatus(MpuWebConstants.OPENSTATUS);
			itemDTO.setStoreNumber(order.getStoreNumber());
			itemDTO.setFullName(customer.getLastName()); 
			itemDTO.setCreatedBy(order.getCreatedBy());
			itemDTO.setSalescheck(order.getSalescheck());
			itemDTO.setPlus4("0");
			itemDTO.setUpc("0");
			itemDTO.setQty("0");
			itemDTO.setItemSeq("0");
			itemDTO.setItemId("0");
			itemDTO.setSaleType("0");
			itemDTO.setItemTransactionType("0");
			itemDTO.setItemSaleOrigin("0");
			itemDTO.setLayawayFlag("");

			itemList.add(itemDTO);

		}

		
		if(MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(order.getRequestType())){
			/***** code changed due to cuncurrentModificationException***/
			/*Iterator<ItemDTO> itr = itemList.iterator();
			while(itr.hasNext()) {

				ItemDTO itemDTO = (ItemDTO)itr.next();
				if(MpuWebConstants.EDIT.equalsIgnoreCase(itemDTO.getItemStatus())){ 
					itemList.remove(itemDTO);
				}
			}*/
			int itemListSize = itemList.size();
			List<ItemDTO> filteredList = new ArrayList<ItemDTO>();
			for(int i=0;i<itemListSize;i++){
				ItemDTO itemDTO = itemList.get(i);
				if(!MpuWebConstants.EDIT.equalsIgnoreCase(itemDTO.getItemStatus())){ 
					filteredList.add(itemDTO);
				}
			}
			itemList = filteredList;
		}

		Object[] rqdId = null;
		if(null!=itemList && !itemList.isEmpty()){
			rqdId  = mpuWebServiceDAOImpl.createItemList(itemList, rqtId, order.getWeb_ExpireTime(), 
					storeNumber, requestType, isLockerEligible, createTime,order.getOrderSource(),notInMpu);	//	Add Item into DB REQUEST_QUEUE_DETAILS

			int cnt = 0;
			for(Object obj : rqdId) {

				requestDTO.getItemList().get(cnt).setRqdId(obj + "");
				requestDTO.getItemList().get(cnt).setRqtId(rqtId + "");
				cnt++;
			}

			//logger.debug("createRequest","createItemList completed");

		}

		List<ActivityDTO> activityDTOs = new ArrayList<ActivityDTO>();

		String actionSeq = (String) mpuWebServiceDAOImpl.getNextAction(requestType, currentStatus, action, MpuWebConstants.SEQUENCE,storeNumber);
		String activityDescription = (String) mpuWebServiceDAOImpl.getNextAction(requestType, currentStatus, action, MpuWebConstants.ACTIVITY,storeNumber);

		if(null!=itemList && !itemList.isEmpty()){
			for (ItemDTO itemDTO : itemList) {
				ActivityDTO activity = new ActivityDTO();	
				//activity.setActionSeq("setItemStatus");
				
				if(actionSeq==null) actionSeq=(String)mpuWebServiceDAOImpl.getNextAction(requestType+(StringUtils.hasText(itemDTO.getLayawayFlag()) ? itemDTO.getLayawayFlag() : ""), currentStatus, action, MpuWebConstants.SEQUENCE,storeNumber);
				//activity.setActionSeq();
				activity.setActionSeq(actionSeq);
				activity.setAssignedUser(null);
				activity.setCreatedBy(order.getCreatedBy());
				activity.setType(requestType);
				activity.setFromLocation(itemDTO.getStockLocation());
				
				if(activityDescription==null) activityDescription=(String)mpuWebServiceDAOImpl.getNextAction(requestType+(StringUtils.hasText(itemDTO.getLayawayFlag()) ? itemDTO.getLayawayFlag() : ""), currentStatus, action, MpuWebConstants.ACTIVITY,storeNumber);
				//activity.setActivityDescription("setItemStatus");
				//activity.setActivityDescription(mpuWebServiceDAOImpl.getNextAction(requestType, currentStatus, action, "activity",storeNumber));
				activity.setActivityDescription(activityDescription);
				activity.setStore(storeNumber);
				activityDTOs.add(activity);
			}

			mpuWebServiceDAOImpl.createActivity(activityDTOs, rqtId, rqdId,storeNumber);	//	Insert data into DB REQUEST_ACTIVITY
		}

		if(null!=paymentList && !paymentList.isEmpty()){
			//logger.debug("createRequest","createActivity completed");

			mpuWebServiceDAOImpl.createPaymentList(paymentList, rqtId, storeNumber);	//	Insert data into DB REQUEST_QUEUE_PAYMENT
			//logger.debug("createRequest","createPaymentList completed");
		}
		//mpuWebServiceDAOImpl.createTaskDTO(task, "taskType");
		//logger.debug("createRequest","createTaskDTO completed");

		/*
		 * Updating the cache
		 */
		if(null!=customer){
			customerFullName = customer.getFirstName()+MpuWebConstants.SPACE+customer.getLastName();
		}
		if(null != itemList && !itemList.isEmpty()){
			if(null!=notInMpu && notInMpu.length>0 && 
					MpuWebConstants.NOT_IN_MPU.equalsIgnoreCase(notInMpu[0])){
				//logger.info("Should not be added to cache as this is a notInMpuOrder==", notInMpu[0]);
			}else{
				boolean isCacheUpdated = addToEhCache(itemList, rqdId,storeNumber, requestType, customerFullName);

				logger.info(" isCacheUpdated = ", isCacheUpdated);
				/**
				 * Cache refresh flag to be set after creation of new request
				 */
				EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
				String tempReqType;
				if(MpuWebConstants.LAYAWAY.equalsIgnoreCase(requestType)){
					tempReqType = requestType+"S";
				}else{
					tempReqType = requestType;
				}
				/******* changes done to insert data in cache for LAYAWAY case  **/
				Map<String, String> requestTypeQueueMap = getRequestQueueMap(storeNumber);
				//String queueKey = storeNumber + "-"+requestTypeQueueMap.get(tempReqType);
				if(isCacheUpdated){
					String cacheRefreshKey = MpuWebConstants.CACHE_REFRESH_FLAG+"-"+org.apache.commons.lang3.StringUtils.leftPad(storeNumber, 5, '0')+"-"+requestTypeQueueMap.get(tempReqType);
					requestQueueCache.put(cacheRefreshKey,String.valueOf(isCacheUpdated));
				}
				
			}
		}

		/*code for mod notification when no user is logged-in begin 
		 * 
		 */
		sendMPUserviceWarningMsg(storeNumber);	
		/*	code for mod notification when no user is logged-in end	
		 */

		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.createRequest requestDTO : " + "");

		return requestDTO;
	}

	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.MPUWebServiceProcessor#updateOrderRequest(java.lang.String, com.searshc.mpuwebservice.bean.ActionDTO)
	 */
	public int updateOrderRequest(String requestNum, ItemDTO action) throws DJException{
		//logger.debug("Entering MPUWebServiceProcessorImpl.updateOrderRequest	requestNum:",requestNum +": action: "+action  );
		logger.info("Entering MPUWebServiceProcessorImpl.updateOrderRequest	requestNum:",requestNum);
		if(MpuWebConstants.S991.equalsIgnoreCase(action.getRequestType()) ||MpuWebConstants.BINWEB.equalsIgnoreCase(action.getRequestType()) || isStage(action) || MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(action.getRequestType())){
			ArrayList<String> statusList=new ArrayList<String>();
			statusList.add(action.getItemStatus());
			logger.info("Exiting MPUWebServiceProcessorImpl.updateOrderRequest","action.getRequestType() : " + action.getRequestType());
			return getOrderUpdated(action, statusList);
		}
		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.updateOrderRequest");
		return 0;
	}



	private boolean isStage(ItemDTO itemDTO){
		if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.STAGE)){
			return true;
		}else if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.BINSTAGE)){
			return true;
		}else if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.LAYAWAYS)){
			return true;
		}else if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.LAYAWAYF)){
			return true;
		}
		return false;
	}

	/**
	 * This method is used to get updated Order
	 * @param ActionDTO action
	 * @param List<String> statusList
	 * @throws DJException
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)	
	private int getOrderUpdated(ItemDTO action,List<String> statusList) throws DJException {
		//logger.debug("Entering MPUWebServiceProcessorImpl.getOrderUpdated	action:",action.toString()  +": statusList: "+statusList );
		logger.info("Entering MPUWebServiceProcessorImpl.getOrderUpdated","" );
		//Map<String,Object> stkLocation = new HashMap<String,Object>();
		int noOfRows=0;
		String actionFlag = "";
		
		/*******/
		//for handling the duplicate bin numbers
		if(StringUtils.hasText(action.getToLocation()) && action.getToLocation().contains(MpuWebConstants.BIN)){
			String [] binArray = ((action.getToLocation()).trim()).split(" ");
			if(binArray.length>1){
				String finalBinloc = binArray[0]+" "+binArray[binArray.length-1];
				action.setToLocation(finalBinloc);
			}
		}
		
		/*******/
		
		
		boolean cacheRemoveFlag = false;
		//logger.debug("getOrderUpdated","action:::"+action.toString());
		logger.info("getOrderUpdated", "action.getRequestType() : " + action.getRequestType());
		String nextStatus=(String)mpuWebServiceDAOImpl.getNextAction(action.getRequestType(),action.getItemStatus(),action.getActionId() ,
				MpuWebConstants.STATUS, action.getStoreNumber());
		String activityDesc=(String)mpuWebServiceDAOImpl.getNextAction(action.getRequestType(),action.getItemStatus(),action.getActionId() ,
				MpuWebConstants.ACTIVITY, action.getStoreNumber());
		String seq=(String)mpuWebServiceDAOImpl.getNextAction(action.getRequestType(),action.getItemStatus(),action.getActionId() ,
				MpuWebConstants.SEQUENCE, action.getStoreNumber());
		//logger.info("the values are==", "nextStatus=="+nextStatus+"activityDesc=="+activityDesc+"seq=="+seq);
		long rqdId = 0;
		long rqtId = 0;
		if(StringUtils.isEmpty(nextStatus)){
		//Defensive fix to prevent item status being null	
			if(MpuWebConstants.CANCEL.equals(action.getActionId())){
				nextStatus=MpuWebConstants.CANCELLED;
			}
			else if(MpuWebConstants.BIN.equals(action.getActionId())){
				nextStatus=MpuWebConstants.BINNED;
			}
			else if(MpuWebConstants.BINPENDING.equals(action.getItemStatus())){
				
				if(MpuWebConstants.MANUAL_ENTRY.equals(action.getActionId())||MpuWebConstants.SCAN.equals(action.getActionId())){
					nextStatus=MpuWebConstants.BINPENDING;	
				}
				
			}else{
                logger.error("Empty or Null  nextStatus in getOrderUpdated == nextStatus =="+nextStatus+" == itemDto == "+action.toString(), "");
                throw new DJException("Next Status is null");
          }
			
		}
		//whenever db is updated version number should be updated in cache also
		if(StringUtils.hasText(action.getRqdId()) && !"0".equalsIgnoreCase(action.getRqdId())){
			rqdId = Integer.parseInt(action.getRqdId());
		}else{
			rqdId = mpuWebServiceDAOImpl.getRequestDetailId(action.getRequestNumber(),action,statusList);
		}

		if(StringUtils.hasText(action.getRqtId()) && !"0".equalsIgnoreCase(action.getRqtId())){
			rqtId = Integer.parseInt(action.getRqtId());
		}else{
			rqtId =   mpuWebServiceDAOImpl.checkIsActiveRequestExisting(action.getStoreNumber(),action.getRequestNumber());
		}

		action.setRqdId(String.valueOf(rqdId));
		action.setRqtId(String.valueOf(rqtId));
		/*if(action.getVersion()==null){
			action.setAssignedUser(null);
		}*/
		noOfRows=mpuWebServiceDAOImpl.updateItemDetails(action.getRequestNumber(),action,nextStatus);

	//	logger.debug("\n\n\n=======noOfRows=====JustAfterUpdation====", noOfRows);
		//logger.debug("getOrderUpdated","updateItemDetails Completed");

		/*
			Code for csm miss
		 */
		if( action.getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB) && MpuWebConstants.BINPENDING.equalsIgnoreCase(nextStatus)){
			//isReopenedFromCSMMiss(action);
			action.setCsmMissFLag("N");
			if("Y".equalsIgnoreCase(action.getCsmMissFLag())){
				if(action.getSalescheck().startsWith(MpuWebConstants.WEB_SALE_SALESCHECK)){
					modNotificationProcessorImpl.updateCsmTask(8, action);
				}else{
					modNotificationProcessorImpl.updateCsmTask(11, action);
				}
			}
		}
		//end for csm miss
		mpuWebServiceDAOImpl.createActivity(MPUWebServiceUtil.convertActionToActivity(action, seq, activityDesc),rqtId,rqdId);
	//	logger.debug("getOrderUpdated","createActivity Completed");

		if(noOfRows>0){
			if(MpuWebConstants.BINNED.equalsIgnoreCase(nextStatus)){
				actionFlag = "COMPLETE";
				addLocker(action);
				if(!MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(action.getCreatedBy())){
					updateBinToNPOS(action);
				}
				processRequestComplete(action,actionFlag);
				sendMpuInStorePurchaseNotification(action);
				if(/*!action.getRequestType().equalsIgnoreCase("BINSTAGE") &&*/ !"FLR".equalsIgnoreCase(action.getFromLocation())){
					mpuWebServiceDAOImpl.decrementItemStock(action);
				}						
				cacheRemoveFlag = true;
			}
			/*			else if(MpuWebConstants.CLOSED.equalsIgnoreCase(nextStatus)){
					actionFlag = "COMPLETE";
				processRequestComplete(action,actionFlag);
				cacheRemoveFlag = true;
			}*/
			else if(MpuWebConstants.CLOSED.equalsIgnoreCase(nextStatus) || MpuWebConstants.STAGED_STATUS.equalsIgnoreCase(nextStatus)){
				actionFlag = "COMPLETE";
				processRequestComplete(action,actionFlag);
				sendMpuInStorePurchaseNotification(action);
				cacheRemoveFlag = true;
				if(!"FLR".equalsIgnoreCase(action.getFromLocation())){
					mpuWebServiceDAOImpl.decrementItemStock(action);
				}
			}else if((MpuWebConstants.CANCELLED.equalsIgnoreCase(nextStatus)) || (MpuWebConstants.RESTOCKED.equalsIgnoreCase(nextStatus))){
				actionFlag = "CANCEL";
				processRequestComplete(action,actionFlag);
				cacheRemoveFlag = true;
				if(null!=action && MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(action.getRequestType())&&MpuWebConstants.RESTOCK_PENDING.equalsIgnoreCase(action.getItemStatus())){
					int activeItemCount = mpuWebServiceDAOImpl.getActiveItemCount(action.getStoreNumber(),action.getRqtId());
					if(activeItemCount<=0){
						mpuWebServiceDAOImpl.cancelExpireRequest(action.getStoreNumber(), Long.parseLong(action.getRqtId()), MpuWebConstants.CANCELLED);
					}
				}
			}else if(MpuWebConstants.VOIDED.equalsIgnoreCase(nextStatus)){
				actionFlag = "VOID";
				processRequestComplete(action,actionFlag);
				cacheRemoveFlag = true;
			}else if(MpuWebConstants.RESTOCK_PENDING.equalsIgnoreCase(nextStatus)){
				ArrayList<String> rqtList=new ArrayList<String>();
				rqtList.add(String.valueOf(rqtId));
				mpuWebServiceDAOImpl.updateOrder(action.getStoreNumber(),rqtList,MpuWebConstants.COMPLETED, MpuWebConstants.OPEN,null,false);
			}else if(MpuWebConstants.AVAILABLE.equalsIgnoreCase(nextStatus)){
				/*****Steps to complete the H&G order when the item is completed***/
				actionFlag = MpuWebConstants.HOLDGO;
				processRequestComplete(action, actionFlag);
/*				boolean complete = mpuWebServiceDAOImpl.checkRequestComplete(action.getStoreNumber(), action.getRqtId(), action.getRequestType());
				if(complete){
				mpuWebServiceDAOImpl.updateOrder(action.getStoreNumber(),Arrays.asList(action.getRqtId()),MpuWebConstants.WIP, MpuWebConstants.COMPLETED,null,false);
				associateActivityProcessor.createAssociateActivity( MpuWebConstants.COMPLETED,action.getRqtId(),action.getStoreNumber());
				}*/
				mpuWebServiceDAOImpl.decrementItemStock(action);
				cacheRemoveFlag = true;
			}else if(MpuWebConstants.NOTAVAILABLE.equalsIgnoreCase(nextStatus)){
				actionFlag = MpuWebConstants.HOLDGO;
				processRequestComplete(action, actionFlag);

/*				boolean complete = mpuWebServiceDAOImpl.checkRequestComplete(action.getStoreNumber(), action.getRqtId(), action.getRequestType());
				if(complete){
				mpuWebServiceDAOImpl.updateOrder(action.getStoreNumber(),Arrays.asList(action.getRqtId()),MpuWebConstants.WIP, MpuWebConstants.COMPLETED,null,false);
				associateActivityProcessor.createAssociateActivity( MpuWebConstants.COMPLETED,action.getRqtId(),action.getStoreNumber());
				}
*/				
				cacheRemoveFlag = true;
			}else if(MpuWebConstants.COMPLETED.equalsIgnoreCase(nextStatus) && "S991".equalsIgnoreCase(action.getRequestType())){
				actionFlag = "COMPLETE";
				processRequestComplete(action, actionFlag);
			
				cacheRemoveFlag = true;
			}else if(MpuWebConstants.NORESPONSE.equalsIgnoreCase(nextStatus)||MpuWebConstants.EXPIRED.equalsIgnoreCase(nextStatus)){
				cacheRemoveFlag = true;
			}
			else if(MpuWebConstants.MOD_VERIFY.equalsIgnoreCase(nextStatus)){
				//cacheRemoveFlag = true;
				if(action.getSalescheck().startsWith("093")){
					modNotificationProcessorImpl.sendMODNotification(action,8);
				}else{
					modNotificationProcessorImpl.sendMODNotification(action,11);
				}
				//TODO
				//need to create an entry in the MOD_notification_task_detail table
			}
		}

		/**
		 * Updating the Cache
		 * By Nasir assign
		 */
		//whenever db is updated version number should be updated in cache alsor
		ItemDTO itemCache=null;
		if(action.getVersion()==null){
			itemCache=getItemFromCache(action.getRequestType(), action.getStoreNumber(), action.getRqdId());	
			if(itemCache!=null){
				itemCache.setAssignedUser(null);
				action=itemCache;					
			}
		}

		action.setItemStatus(nextStatus);
		if(StringUtils.hasText(action.getRequestType()) && MpuWebConstants.BINWEB.equalsIgnoreCase(action.getRequestType())){
			//isReopenedFromCSMMiss(action);
			action.setCsmMissFLag("N");
		}
		if(noOfRows>0){

			if(StringUtils.hasText(action.getVersion())){
				long version=Long.parseLong(action.getVersion());
				//	version=version+1;
				action.setVersion(new Long(version+1).toString());
			}
			if(null!=action.getRequestType() && "LAYAWAYF".equalsIgnoreCase(action.getRequestType())){
				updateLayawayfItems(action,cacheRemoveFlag);
			}else{
				updateToEhCache(action,cacheRemoveFlag);
			}
			
		}
		logger.info("\n\n\n=======noOfRows=====JustBeforeReturn====", noOfRows);
		return noOfRows;
	}

	private void updateLayawayfItems(ItemDTO action,boolean cacheRemoveFlag ) throws DJException{
	//	getRequestData(requestId,strNum,fields,itemId,status)
		List<String> status = Arrays.asList(MpuWebConstants.ALL,MpuWebConstants.EXPIRED);
		List<ItemDTO> itemdtoList = mpuWebServiceDAOImpl.getItemList(action.getStoreNumber(),action.getRqtId(),null,status,false) ;
		for(ItemDTO itemDTO:itemdtoList){
		if(null!=itemDTO.getRequestType() && "LAYAWAYF".equalsIgnoreCase(itemDTO.getRequestType())){
			updateToEhCache(itemDTO,cacheRemoveFlag);
		}
		}
	}
	
	
	/**
	 * This method is used for updating request for MOD Action
	 * @param String storeNum
	 * @param String reqNum
	 * @throws DJException
	 *//*
		private void updateRequestForMODAction(ActionDTO actionDTO) throws DJException{
			    logger.debug("updateRequestForMODAction","updateRequestForMODAction Entered");
				List<Map<String,Object>> itemStatusList = mpuWebServiceDAOImpl.checkItemStatus(actionDTO.getStoreNumber(),
						actionDTO.getRqtId());
				boolean complete=true;
				for(Map<String,Object> status : itemStatusList){
					String item_status=(String)status.get(MpuWebConstants.ITEM_STATUS);
					if(!(MpuWebConstants.OPENSTATUS.equals(item_status))){
						complete=false;
						break;
					}
				}
				if(complete){
					mpuWebServiceDAOImpl.updateOrder(actionDTO.getStoreNumber(),actionDTO.getRequestNumber(),
							MpuWebConstants.WIP, MpuWebConstants.OPENSTATUS);
					logger.debug("updateRequestForMODAction","Order Updated");
				}
		}*/


	private ItemDTO 	getItemFromCache(String requestType,String store,String rqdId){

		ItemDTO item=null;
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		Map<String, String> requestTypeQueueMap = getRequestQueueMap(store);
		if(null!=requestType && requestType.equalsIgnoreCase(MpuWebConstants.LAYAWAY)){
			/**
			 * Adding arbitary layaway type as both layawayf and layaways are in same STAGE queue
			 */
			requestType = requestType.concat("F");
		}
		String queueKey = store+ "-"+requestTypeQueueMap.get(requestType);
		if(null!=requestQueueCache.get(queueKey)){
			Map<String,ItemDTO> queueMap = (Map<String, ItemDTO>) requestQueueCache.get(queueKey).get();
			if(null!=queueMap){

				item=(ItemDTO)queueMap.get(rqdId);

			}
		}
		return item;
	}


	/**
	 * This method is used to process request complete.
	 * @param String storeNum
	 * @param String request_number
	 * @return boolean
	 * @throws DJException
	 */
	private boolean processRequestComplete(ItemDTO actionDTO, String actionFlag) throws DJException{
		logger.info("Entering MPUWebServiceProcessorImpl.processRequestComplete","");
		//logger.info("Entering MPUWebServiceProcessorImpl.processRequestComplete	actionDTO:",actionDTO +": actionFlag: "+actionFlag  );
		boolean complete = false;
		if(MpuWebConstants.VOID.equalsIgnoreCase(actionFlag)){
			complete = mpuWebServiceDAOImpl.checkRequestVoid(actionDTO.getStoreNumber(), actionDTO.getRqtId(), actionDTO.getRequestType());				
		}else if(MpuWebConstants.CANCEL.equalsIgnoreCase(actionFlag)){
			complete = mpuWebServiceDAOImpl.checkRequestCancel(actionDTO.getStoreNumber(), actionDTO.getRqtId(), actionDTO.getRequestType());
		}else{
			complete = mpuWebServiceDAOImpl.checkRequestComplete(actionDTO.getStoreNumber(), actionDTO.getRqtId(), actionDTO.getRequestType());
		}
		//logger.info("complete flag in  processRequestComplete = " ,complete  );
		
		OrderDTO orderDTO = mpuWebServiceDAOImpl.getAllOrderDetails(actionDTO.getStoreNumber(), actionDTO.getRqtId());
		
		if("true".equalsIgnoreCase(PropertyUtils.getProperty("itemLevelResponse"))){
			//if(MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(orderDTO.getCreatedBy()) && complete){
			//item check changed to order level filtering
			if(MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(orderDTO.getOrderSource()) && complete){
				sendFinalResponse(actionDTO.getStoreNumber(),actionDTO.getRqtId(),actionDTO.getRequestNumber(),actionFlag,orderDTO); //TODO Include Flag for Sending Cancel
			}else if(!MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(orderDTO.getOrderSource())){
				sendFinalResponse(actionDTO.getStoreNumber(),actionDTO.getRqtId(),actionDTO.getRequestNumber(),actionFlag,orderDTO);
			}
		}else{
			if(complete){
				sendFinalResponse(actionDTO.getStoreNumber(),actionDTO.getRqtId(),actionDTO.getRequestNumber(),actionFlag,orderDTO); //TODO Include Flag for Sending Cancel
			}
		}
		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.processRequestComplete");
		return complete;
	}

	/**
	 * This method is used for sending response 
	 * @param storeNum
	 * @param requestNumber
	 * @throws DJException
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void sendFinalResponse(String storeNum,String rqtId,String requestNumber,String actionFlag,OrderDTO orderDTO) 
				throws DJException{
			logger.info("sendFinalResponse",storeNum + ":" + rqtId +":"+requestNumber +":" + actionFlag );
			boolean complete = false;
			// start change for Direct to MPU
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if(orderDTO==null){
				 orderDTO = mpuWebServiceDAOImpl.getAllOrderDetails(storeNum, rqtId);
			}
			
			if(MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(orderDTO.getOrderSource())){
				try {
					String originalJSON = orderDTO.getOriginalJson(); 
					List<ItemDTO> itemDTOs = mpuWebServiceDAOImpl.getOrderItemList(storeNum,rqtId);

					if(MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(orderDTO.getRequestType())){
						HGRequestDTO hgOrder = (HGRequestDTO)mapper.readValue(originalJSON, new TypeReference<HGRequestDTO>(){});
					//	HGRequestDTO hgOrder = (HGRequestDTO)createStringToObject(originalJSON, new TypeReference<HGRequestDTO>(){});
						hgOrder  =  getFinalHGResponse(hgOrder, itemDTOs);
					//	logger.info("****final HG OrderResponse** = ", hgOrder.toString());
					//	logger.info("start OBU HG update",""+Calendar.getInstance().getTimeInMillis());
						
						oBUUpdateProcessor.updateHGOBU(hgOrder);
						
					}
					
				else{
//					String originalJSON = orderDTO.getOriginalJson(); 
					Order order = (Order)mapper.readValue(originalJSON, new TypeReference<Order>(){});
					//Order order = (Order)createStringToObject(originalJSON, new TypeReference<Order>(){});
//					List<ItemDTO> itemDTOs = mpuWebServiceDAOImpl.getOrderItemList(storeNum,rqtId);
					Order finalOrderResponse = getFinalOrderResponse(order,itemDTOs);
					//logger.info("****finalOrderResponse** = ", finalOrderResponse.toString());
					//logger.info("start OBU update",""+Calendar.getInstance().getTimeInMillis());
					
					oBUUpdateProcessor.updateOBU(finalOrderResponse);
					/**
					 * Save the final Response sent to OBU in DB
					 */
					mpuWebServiceDAOImpl.updateFinalResponseInDb(finalOrderResponse,rqtId,storeNum);
				}
				} catch (Exception e) {
					logger.error("Exception in sendFinalResponse:", e);
					throw new DJException(e.getMessage());
				}
				//logger.info("end OBU update",""+Calendar.getInstance().getTimeInMillis());
			}else {
				// end change for Direct to MPU
				OrderAdaptorRequest request=mpuWebServiceDAOImpl.getOriginalJSON(rqtId, storeNum,null);
				Map<String,Object> itemStatusMap=mpuWebServiceDAOImpl.checkItemStatus(storeNum, rqtId);
				Map<String,Object> finalStatusMap=new HashMap<String, Object>();
				/**
				 * For CrossFormat Orders
				 */
				if(null!=itemStatusMap){
					for(String key:itemStatusMap.keySet()){
						String []strArr = key.split(",");
						if(strArr.length>2 && !StringUtils.isEmpty(strArr[2]) && strArr[2].startsWith("LM")){
							//If this is a cross format order
							String []itemIdSeqArray = strArr[strArr.length-1].split("-");
							String cfkey = strArr[0]+","+strArr[2]+"-"+itemIdSeqArray[1];
							
							finalStatusMap.put(cfkey, itemStatusMap.get(key));
						}else{
							finalStatusMap.put(key, itemStatusMap.get(key));
						}
					}
				}
				
				if(request.getRequestOrder()!=null){
				//	logger.info("Inside Request Order","");
					Order originalOrder=request.getRequestOrder();
					List<OrderItem> nposItemsList=request.getRequestOrder().getItems();
					List<OrderItem> nposItemsListFinal=new ArrayList<OrderItem>();
					
					for(OrderItem item:nposItemsList){
						//logger.info("Inside Request nposItemsList","");
						String status= (String) finalStatusMap.get(item.getItemIdentifiers()+"-"+item.getSequenceNo());
					//	logger.info("Item Status ",status);
						if("BINNED".equals(status)){
							item.setItemStockState('B');
							//logger.info("Setting Item Bin State : B","");
							//nposItemsListFinal.add(item);
						}
						else if("CLOSED".equals(status)){
							item.setItemStockState('O');
							//logger.info("Setting Item Bin State : O","");
							//nposItemsListFinal.add(item);
						}
						nposItemsListFinal.add(item);
						
					}
					originalOrder.setItems(nposItemsListFinal);
					request.setRequestOrder(originalOrder);
				}
					//logger.info("start npos update","RQT-ID "+rqtId+":"+Calendar.getInstance().getTimeInMillis());
					nPOSUpdateProcessorImpl.updateNPOS(request, "complete");
				//logger.info("end npos update",""+"RQT-ID "+rqtId+":"+Calendar.getInstance().getTimeInMillis());
			}
			ArrayList<String> rqtList=new ArrayList<String>();
			rqtList.add(rqtId);
			if(MpuWebConstants.CANCEL.equalsIgnoreCase(actionFlag)){
				mpuWebServiceDAOImpl.updateOrder(storeNum,rqtList,MpuWebConstants.OPEN, MpuWebConstants.CANCELLED,null,false);
				associateActivityProcessor.createAssociateActivity( MpuWebConstants.CANCELLED,rqtId,storeNum);
			}else if (MpuWebConstants.VOID.equalsIgnoreCase(actionFlag)){
				mpuWebServiceDAOImpl.updateOrder(storeNum,rqtList,MpuWebConstants.OPEN, MpuWebConstants.VOIDED,null,false);
				associateActivityProcessor.createAssociateActivity( MpuWebConstants.VOIDED,rqtId,storeNum);
			}else if (MpuWebConstants.HOLDGO.equalsIgnoreCase(actionFlag)){
				mpuWebServiceDAOImpl.updateOrder(storeNum,rqtList,MpuWebConstants.WIP, MpuWebConstants.COMPLETED,null,false);
			}else{
				complete = mpuWebServiceDAOImpl.checkRequestComplete(orderDTO.getStoreNumber(), orderDTO.getRqtId(), orderDTO.getRequestType());
				if(complete){
					mpuWebServiceDAOImpl.updateOrder(storeNum,rqtList,MpuWebConstants.OPEN, MpuWebConstants.COMPLETED,null,false);
					associateActivityProcessor.createAssociateActivity( MpuWebConstants.COMPLETED,rqtId,storeNum);
				}
			}
			
				
		}
	
	
	/**To send the final response to OBU team for Reserve IT
	 * 
	 * @param order
	 * @param itemDTOs
	 * @return
	 */
	private HGRequestDTO getFinalHGResponse(HGRequestDTO order,List<ItemDTO>itemDTOs){
		logger.info("entering method===", "getFinalHGResponse");
		HGRequestDTO finalResponseOrder = order;
		HashMap<String,String> itemStatus = new HashMap<String, String>();
		ArrayList<HashMap<String,String>> modifiedItemsList=new ArrayList<HashMap<String,String>>();
		/****  map to contain the item status of the Db items ****/
		for(ItemDTO itemDTO : itemDTOs){
			/*String key = (StringUtils.isEmpty(itemDTO.getDivNum()) ? "" : itemDTO.getDivNum()) +
			(StringUtils.isEmpty(itemDTO.getItem()) ? "" : itemDTO.getItem()) +
			(StringUtils.isEmpty(itemDTO.getSku()) ? "" : itemDTO.getSku()) +","+
			itemDTO.getItemSeq();*/
			String key = (StringUtils.isEmpty(itemDTO.getDivNum()) ? "" : itemDTO.getDivNum()) +
					(StringUtils.isEmpty(itemDTO.getItem()) ? "" : itemDTO.getItem()) +
					(StringUtils.isEmpty(itemDTO.getSku()) ? "" : itemDTO.getSku()) ;
			key = key.replaceAll("\\s","");
			//logger.info("the key value is ===","key=="+key+"value==="+itemDTO.getItemStatus() );
			itemStatus.put(key, itemDTO.getItemStatus());
			
		}
		
		/****  proces  the items in the HG json on the basis of DB items ****/
		for(HashMap<String , String> obuItems : order.getItemsList()){
			//String keyFinder = obuItems.get("divNum")+obuItems.get("item")+obuItems.get("sku")+","+obuItems.get("sequenceNumber");
			String keyFinder = obuItems.get("divNum")+obuItems.get("item")+obuItems.get("sku");
			keyFinder = keyFinder.replaceAll("\\s","");
			//logger.info("the key value is ===","key=="+keyFinder);
			String status = itemStatus.get(keyFinder);
			if(MpuWebConstants.AVAILABLE.equalsIgnoreCase(status)){
				obuItems.put("itemStatus", "A");	
			}else if(MpuWebConstants.NOTAVAILABLE.equalsIgnoreCase(status)){
				obuItems.put("itemStatus", "NA");
			}else if(MpuWebConstants.EXPIRED.equalsIgnoreCase(status)){
				obuItems.put("itemStatus", "NR");
			}else if(MpuWebConstants.CANCELLED.equalsIgnoreCase(status)){
				obuItems.put("itemStatus", "CNR");
			}
			
			//modifiedItemsList.add(itemStatus);
			modifiedItemsList.add(obuItems);
		}
		
		order.setItemsList(modifiedItemsList);
		order.setStatus(MpuWebConstants.COMPLETE);
		return finalResponseOrder;
	}

	
	/**
	 * 
	 * @param store
	 * @param queueType
	 */
	
	
	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.MPUWebServiceProcessor#getAllItemList(java.lang.String, java.lang.String, java.lang.String)
	 */
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<ItemDTO> getAllItemList(String storeNumber, String queueType,String kiosk, String isRequestListNonDej,String isUserAssigned)throws DJException {
	//	logger.debug("Entering MPUWebServiceProcessorImpl.getAllItemList","");
		logger.info("Entering MPUWebServiceProcessorImpl.getAllItemList	storeNumber:",storeNumber +": queueType: "+queueType +": kiosk: "+kiosk  );
		List<ItemDTO> modEnablItems = new ArrayList<ItemDTO>();
		boolean postProcessingFlag = expiredOrderProcessorImpl.filterItemthread( storeNumber, queueType);
		List<Map<String, ItemDTO>>   expiredHGItems = new ArrayList<Map<String,ItemDTO>>();
		if(queueType!=null){
			queueType=queueType.toUpperCase();
		}
		long cacheStartTime = Calendar.getInstance().getTimeInMillis();
		List<ItemDTO>  itemList=null;
		//if(!"STAGE".equalsIgnoreCase(queueType) && !"H&G".equalsIgnoreCase(queueType)){
		//if(!"H&G".equalsIgnoreCase(queueType)){
			itemList=getAllItemFromEhCache(storeNumber, queueType, kiosk,postProcessingFlag);
		//}
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		String cacheRefreshFlag="false";
		
		
		String cacheRefreshKey = MpuWebConstants.CACHE_REFRESH_FLAG+"-"+org.apache.commons.lang3.StringUtils.leftPad(storeNumber, 5, '0')+"-"+queueType;
		//logger.info("cacheRefreshKey == ", cacheRefreshKey);
		if(null== requestQueueCache.get(cacheRefreshKey)){
			cacheRefreshFlag="true";
		}else{
			 
			cacheRefreshFlag = (String) requestQueueCache.get(cacheRefreshKey).get();
		}
		//logger.info("==cacheRefreshFlag==", cacheRefreshFlag);
		
		try {
			if(( null == itemList || itemList.isEmpty()) && cacheRefreshFlag.equalsIgnoreCase("true")){
				logger.info("Not Getting Items from EhCache","");
			}

			//itemList=null;
			List<ItemDTO>  output=itemList;
			ArrayList<String> rqdIdList=new ArrayList<String>();



			Object[] rqdIdArray=null; 
			if(null==itemList){
				itemList = new ArrayList<ItemDTO>();//So that null is not returned
			}

			if((null == itemList || itemList.isEmpty()) && cacheRefreshFlag.equalsIgnoreCase("true")){
				//logger.info("Getting Items from Database","");
				List<Map<String, Object>> itemMap=mpuWebServiceDAOImpl.getAllItemList(storeNumber, queueType, kiosk,isRequestListNonDej,isUserAssigned);
				ArrayList<String> expireRqtList=new ArrayList<String>();
				ArrayList<String> expireRqdList=new ArrayList<String>();
				
				
				
				itemList = new ArrayList<ItemDTO>();
				output=new ArrayList<ItemDTO>();
				HashMap<String,String> mapping=new HashMap<String,String>();
				HashMap<String,String> requestTypeMap=new HashMap<String,String>();
				SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				List<ItemDTO> escalationUpdatedItemList = new ArrayList<ItemDTO>();

				for(Map<String, Object> map:itemMap){
					Map<String, ItemDTO> expiredHGItemList = new HashMap<String, ItemDTO>();
					boolean expireCheck=false;
					boolean escalationUpdated = false;
					ItemDTO itemdto=new ItemDTO();
					if(null != itemdto.getAssignedUser() && !"".equals(itemdto.getAssignedUser().trim())){
						itemdto.setAssigneeFullName(pickUpServiceProcessor.getAssociateName(itemdto.getAssignedUser()));
					}
					itemdto.setItemId((null != map.get(ITEM_ID.name())) ? map.get(ITEM_ID.name()).toString() : "0");
					itemdto.setIsLockerValid(map.get(LOCKER_ELIGIBLE.name()).toString());
					itemdto.setVersion(map.get(VER.name()).toString());
					itemdto.setCreateTime(map.get(CREATE_TIME.name()).toString().substring(0,19));
					itemdto.setRequestType((String)map.get(REQUEST_TYPE.name()));
					itemdto.setCreatedBy((String)map.get(CREATED_BY.name()));
					String rqdId=map.get(RQD_ID.name()).toString();
					itemdto.setRqdId(rqdId);
					itemdto.setUpc((String)map.get(UPC.name()));
					String salesCheck=(String)map.get(SALESCHECK.name());
					itemdto.setSalescheck(salesCheck);
					itemdto.setPlus4((String)map.get(PLUS4.name()));
					itemdto.setItemStatus((String)map.get(ITEM_STATUS.name()));
					itemdto.setQty(map.get(QTY.name()).toString());
					itemdto.setSku((String)map.get(SKU.name()));
					itemdto.setRequestNumber((String)map.get(REQUEST_NUMBER.name()));
					//itemdto.setItemQuantityRemaining((String)map.get("qty_remaining"));
					//itemdto.setItemQuantityActual((String)map.get(QTY.name()));
					itemdto.setItemQuantityRemaining(map.get("qty_remaining") + "");
					itemdto.setItemQuantityActual(map.get("qty") + "");
					//to hold Original quantity
					itemdto.setItemQuantityRequested(map.get("qty") + "");
					/*
					 * Check if the store number is of five digit
					 * if not apply left padding to it 
					 */
					if(null!=map.get(STORE_NUMBER.name())){
						String storeNum = String.valueOf(map.get(STORE_NUMBER.name()));
						if(null!=storeNum && storeNum.length()<5){
							/*
							 * Cannot import the package as spring stringutils is already imported and used
							 */
							storeNum =  org.apache.commons.lang3.StringUtils.leftPad(storeNum, 5, '0');
						}

						itemdto.setStoreNumber(storeNum);
					}


					itemdto.setDivNum((String)map.get(DIV_NUM.name()));
					itemdto.setStockQuantity(map.get(STOCK_QUANTITY.name())+"");
					itemdto.setItem((String)map.get(ITEM.name()));
					String identifier = (String)map.get(FULL_NAME.name());
					itemdto.setFullName((String)map.get(FULL_NAME.name()));
					itemdto.setEscalation(map.get(ESCALATION.name())+"");
					itemdto.setItemSeq(map.get(ITEM_SEQ.name()).toString());
					itemdto.setItemCreatedDate(map.get(CREATE_TIME.name()) + "");
					if(null != map.get(DELIVERED_QUANTITY.name())){
						itemdto.setItemQuantityActual((String)map.get(DELIVERED_QUANTITY.name())); //changed for 23549	
					}else{
						itemdto.setItemQuantityActual("0");
					}
					String rqtId=map.get(RQT_ID.name()).toString();
					itemdto.setRqtId(rqtId);
					itemdto.setStockLocation((String)map.get(STOCK_LOCATION.name()));
					itemdto.setFromLocation((String)map.get(FROM_LOCATION.name()));
					itemdto.setToLocation((String)map.get(TO_LOCATION.name()));
					itemdto.setAssignedUser(StringUtils.hasText((String)map.get(ASSIGNED_USER.name()))  ? ((String)map.get(ASSIGNED_USER.name())).trim() : "" );
					if(null != rqtId){
						CustomerDTO customerDTO = mpuWebServiceDAOImpl.getCustomerData(storeNumber, rqtId);
						if(null != customerDTO ){
							itemdto.setAltPhoneNumber(customerDTO.getAltPhoneNumber());
							itemdto.setPhoneNumber(customerDTO.getPhoneNumber());
						}
					}
					itemdto.setEscalationTime((String)map.get(ESCALATION_TIME.name()));
					itemdto.setAssignedUser((String)map.get(ASSIGNED_USER.name()));
					if(null != itemdto.getAssignedUser()){
						itemdto.setAssigneeFullName(pickUpServiceProcessor.getAssociateName(itemdto.getAssignedUser()));
					}
					String escTime=(String)map.get(ESCALATION_TIME.name());
					Date escalationTime=date.parse(escTime);
					Date currentTime=Calendar.getInstance().getTime();



					/**
					 * For ffm type
					 */
					
					itemdto.setFfmType((String)map.get(FFM_TYPE.name()));
					/**
					 * Need to get orderSource from rqt_id of the item
					 */

					List<String> status = Arrays.asList(MpuWebConstants.OPEN,MpuWebConstants.WIP);
					OrderDTO order = mpuWebServiceDAOImpl.getOrderDetails(storeNumber, rqtId, status);
					/**
					 * Start escalation logic for Direct2MPU
					 *  	
					 */
					String currentEscalation = itemdto.getEscalation();
					if(null!=order && MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(order.getOrderSource()) 
							&& (("WEB".equalsIgnoreCase(queueType)&&(salesCheck.startsWith("09300")|| salesCheck.startsWith("09322")))
									||(MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(queueType)))){
						escalationUpdated = isItemNotExpire(itemdto);
						
						if(!escalationUpdated &&  MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(queueType)){
							expiredHGItemList.put(itemdto.getRqtId(),itemdto);
							expiredHGItems.add(expiredHGItemList);
						}
						
						//logger.info("==RQD_ID ==escalationUpdated==", itemdto.getRqdId()+"=="+escalationUpdated);
						escalationTime = date.parse(itemdto.getEscalationTime());
						}
					
					/**
					 * For Making escalationUpdateItemlist
					 */
					if(escalationUpdated && null!=currentEscalation && !currentEscalation.equalsIgnoreCase(itemdto.getEscalation())){
						if("-1".equals(itemdto.getEscalation())){
							itemdto.setEscalation(currentEscalation);
						}
						escalationUpdatedItemList.add(itemdto);
					}
					
					
					if(!escalationUpdated 
							&& (("WEB".equalsIgnoreCase(queueType)&&(salesCheck.startsWith("09300")|| 
									salesCheck.startsWith("09322")))
							||(MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(queueType)))){
						 expireCheck=true;
					 }
					 boolean addFlag=true;
					// logger.info("==expireCheck=", expireCheck);
					 if(expireCheck){
						 
						 if( escalationTime.before(currentTime)){
							if (!expireRqtList.contains(rqtId)){
								expireRqtList.add(rqtId);
								addFlag=false;
								//call cancellations
							}

							if (!expireRqdList.contains(rqdId)){
								expireRqdList.add(rqdId);
								//call cancellations
							}

							mapping.put(rqdId, rqtId);
							requestTypeMap.put(rqdId, itemdto.getRequestType());

						}
					}

					itemdto.setItemImage((String)map.get(ITEM_IMAGE.name()));
					itemdto.setThumbnailDesc((String)map.get(THUMBNAIL_DESC.name()));
					itemdto.setReturnReason((String)map.get("return_reason"));
					if(StringUtils.hasText((String)map.get("short_desc"))){
						String completeDesc = "";
						if(StringUtils.hasText((String)map.get(THUMBNAIL_DESC.name()))){
							completeDesc=(String)map.get(THUMBNAIL_DESC.name());
						}
						itemdto.setThumbnailDesc((String)map.get("short_desc") +completeDesc);
					}
					itemdto.setModifiedBy((String)map.get(MODIFIED_BY.name()));
					itemdto.setKsn((String)map.get(KSN.name()));
					getExpiredTime(itemdto);

					/*
					 Code to check if item re-opened from csm missed
					 */
					if(itemdto.getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB)){
						itemdto.setCsmMissFLag("N");
						//isReopenedFromCSMMiss(itemdto);
					}
					//end
					//send MOD Start
					if((salesCheck.startsWith(MpuWebConstants.WEB_SALE_SALESCHECK) && itemdto.getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB) && 
							!MpuWebConstants.REPORT_START_TIME.equalsIgnoreCase(itemdto.getExpireTime())) || 
							(itemdto.getRequestType().equalsIgnoreCase(MpuWebConstants.HG_REQUEST_TYPE) && !MpuWebConstants.REPORT_START_TIME.equalsIgnoreCase(itemdto.getExpireTime()))){
						modEnablItems.add(itemdto);
					//	notifyMOD(itemdto);
					}
					//send MOD ends					
					if(addFlag){
						itemList.add(itemdto);
						rqdIdList.add(itemdto.getRqdId());
					}


					if(addFlag&&!MpuWebConstants.MOD_VERIFY.equals(itemdto.getItemStatus())){
						//get color updated
						updateItemColor(itemdto);
						output.add(itemdto);

					}
					//Required for adding ItemList to cache

				}//End of  For Loop
				
				/**
				 * Update escalation of requests 
				 */
				if(!escalationUpdatedItemList.isEmpty()){
					int rowsUpdated = mpuWebServiceDAOImpl.updateEsacaltionList(storeNumber,escalationUpdatedItemList);
					logger.info("Escalation Update - rowsUpdated = ", rowsUpdated);
				}
				
				
				if(!rqdIdList.isEmpty()){
					rqdIdArray = rqdIdList.toArray();
				}
				boolean isCacheUpdated = false;

				if(null!=itemList && !itemList.isEmpty()){
					isCacheUpdated = addToEhCache(itemList, rqdIdArray, storeNumber, itemList.get(0).getRequestType(),itemList.get(0).getFullName());
				}
				logger.debug("isCacheUpdated = ", isCacheUpdated);
				long cacheEndTime = Calendar.getInstance().getTimeInMillis();
				long timeTaken = cacheEndTime - cacheStartTime;
				//logger.debug(" timeTaken == ", timeTaken);
				
				/*if(!expireRqdList.isEmpty()){
					//logger.info("==expireRqtList==", expireRqtList);
					logger.info("the expired order list is ", expireRqtList.size());
					logger.info("the expired item list is ", expireRqdList.size());
					logger.info("the expired HG list is ", expiredHGItems.size());
					expiredOrderProcessorImpl.expireOrder(expireRqtList,expireRqdList,mapping,storeNumber,requestTypeMap,queueType,expiredHGItems);
				}*/
				
				if(postProcessingFlag){
					modNotificationProcessorImpl.postProcessing(expireRqtList,expireRqdList,mapping,storeNumber,requestTypeMap,queueType,expiredHGItems,modEnablItems);
				}
				itemList=output;
				//if(isCacheUpdated){
					requestQueueCache.put(cacheRefreshKey, "false");
				//}
			}
		}catch (ParseException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.getAllItemList");
		
		/*if(!CollectionUtils.isEmpty(itemList)){
			getColorUpdated(itemList);
		}*/
		
		return itemList;
	}



	/**
		 * This method is used to implement the escalation logic
		 * @param ItemDTO itemdto
		 * @throws DJException, ParseException
		 */
		//public void escalationLogic(){
		public boolean isItemNotExpire(ItemDTO itemdto) throws DJException, ParseException{
			Map<String, ItemDTO> expiredList = new HashMap<String, ItemDTO>();
			logger.debug("itemdto==", itemdto.toString());
			SimpleDateFormat storeDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//int escalation=(Integer)map.get(ESCALATION.name());//Integer.parseInt((String)map.get(ESCALATION.name()));
			int escalation=Integer.parseInt(itemdto.getEscalation());//Integer.parseInt((String)map.get(ESCALATION.name()));
			String escalation_time=itemdto.getEscalationTime();
			Date currentTime=Calendar.getInstance().getTime();
			SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			boolean escalationUpdated = false;
			String rqdId = itemdto.getRqdId();
			String storeNumber = itemdto.getStoreNumber();
			HashMap<String,Object> storeInfo=(HashMap<String, Object>)mpuWebServiceDAOImpl.getStoreDetails(storeNumber);
			
			//storeDate.setTimeZone(TimeZone.getTimeZone((String)storeInfo.get("timeZone")));
			int escalation_time_val=Integer.parseInt((String)(storeInfo.get(MpuWebConstants.ESCALATION_TIME)));
			 Date escalationTime = date.parse(itemdto.getEscalationTime());
			  
			 
			  //STORE IS OPEN
		      // logger.info("Esc time"," is=="+escalation_time_val);
		       if (SHCDateUtils.isStoreOpen(storeInfo)){
		    	   double diff = (currentTime.getTime() - escalationTime.getTime());
			       double diffmm=diff/(1000*60);
			      // logger.debug("Store is open and"," diff is == "+diffmm);  
			    		   
			       if(diffmm>escalation_time_val){
			    	   if(MpuWebConstants.SFS.equalsIgnoreCase(itemdto.getFfmType())){
			    		   //Expire the SFS order in the first hour
			    		   escalationUpdated = false;
			    	   }else if(escalation<2){
			    		 //  logger.info("increase ","escalation");
			    		   String dateString=date.format(currentTime);
			    		   escalation_time=SHCDateUtils.calculateEscalationTime(dateString, (HashMap<String, Object>) storeInfo);
			    		   escalationTime=date.parse(escalation_time);
			    		   escalation = escalation+1;
			    		   //mpuWebServiceDAOImpl.updateEscalation(storeNumber,rqdId,escalation,escalation_time);
			    		   
			    		   itemdto.setEscalation(String.valueOf(escalation));
			    		   itemdto.setEscalationTime(storeDate.format(escalationTime));
			    		   /*refresh the cache after updating the time*/
			    		   //updateToEhCache(itemdto, false);
			    		   logger.info("RQD_ID = escalationUpdated == "+itemdto.getRqdId()+" = "+escalationUpdated,"");
			    		   escalationUpdated = true;
			    	   }
			    	   /*****to send mail incase an HG Request is NR*****/
			    	   else if(escalation == 2 && itemdto.getRequestType().equalsIgnoreCase(MpuWebConstants.HG_REQUEST_TYPE)){
			    		   /*expiredList.put(itemdto.getRqdId(), itemdto);
			    		   expiredHGItems.add(expiredList);*/
			    		   return false;
			    	   }
			    	   
			       //}else if(diffmm>0 && diffmm<escalation_time_val){
			       }else if(diffmm<escalation_time_val){  
			    	   escalationUpdated = true;
			       }
		       }else{
		    	  //Store is closed 
		    	   double diff;
		    	   if(Calendar.getInstance().getTime().after(SHCDateUtils.getStoreCloseHour(storeInfo))){
		    		  // logger.info("Store is closed for receieving request..","Request pulled up before midnight");
		    		   diff = SHCDateUtils.getStoreCloseHour(storeInfo).getTime()-escalationTime.getTime();
		    	   }else{
		    		  // logger.info("Store is closed for receieving request..","Request pulled up after midnight");
		    		   diff = SHCDateUtils.getLastStoreClose(storeInfo).getTime()-escalationTime.getTime();
		    	   }
		    		   int diffmm=(int) (diff/(1000*60));
				      // logger.info("diff"," is"+diffmm);
				     //if one grade of escalation as exceeded
				       if(diffmm>escalation_time_val){
				    	   if(MpuWebConstants.SFS.equalsIgnoreCase(itemdto.getFfmType())){
				    		   //Expire the SFS order in the first hour
				    		   escalationUpdated = false;
				    	   }else if(escalation<2){
					    		//logger.info("increase"," escalation");
					    		String dateString=date.format(currentTime);
					    		   escalation_time=SHCDateUtils.calculateEscalationTime(dateString, (HashMap<String, Object>) storeInfo);
					    		   escalationTime=date.parse(escalation_time);
					    		   escalation = escalation+1;
					    		  // mpuWebServiceDAOImpl.updateEscalation(storeNumber,rqdId,escalation,escalation_time);
					    		   
					    		   itemdto.setEscalation(String.valueOf(escalation));
					    		   itemdto.setEscalationTime(storeDate.format(escalationTime));
					    		   /*refresh the cache after updating the time*/
					    		   //updateToEhCache(itemdto, false);
					    		   escalationUpdated = true;
				    	   }else if(escalation == 2 && itemdto.getRequestType().equalsIgnoreCase(MpuWebConstants.HG_REQUEST_TYPE)){
				    		//   expiredList.put(itemdto.getRqdId(), itemdto);
				    		//   expiredHGItems.add(expiredList);
				    		   return false;
				    	   }
		    		   
				      // }else if(diffmm>0&&diffmm<=escalation_time_val){
				       }else if(diffmm<escalation_time_val){  
				    	  //String escltn_date= SHCDateUtils.getDateSubstractFromString(SHCDateUtils.calculateEscalationTime(date.format(currentTime), storeInfo),diffmm);
				    	  /*String escltn_date= SHCDateUtils.calculateEscalationTime(date.format(currentTime), storeInfo);
				    	  mpuWebServiceDAOImpl.updateEscalation(storeNumber,rqdId,escalation,escltn_date);
				    	  itemdto.setEscalation(String.valueOf(escalation));
				    	   itemdto.setEscalationTime(storeDate.format(escltn_date));
				    	  */
				    	   /**
				    	    * For orders created before last hour of the store but 
				    	    * within escalation limit for the store
				    	    */
				    	   if(diffmm>60){
				    		   String dateString=date.format(currentTime);
				    		   escalation_time=SHCDateUtils.calculateEscalationTime(dateString, (HashMap<String, Object>) storeInfo);
				    		   escalationTime=date.parse(escalation_time);
				    		   //escalation=-1;//escalation is set to -1 to identify this is a special scenario
				    		   itemdto.setEscalation("-1");
				    		   itemdto.setEscalationTime(storeDate.format(escalationTime));
				    		   
				    	   }
				    	  escalationUpdated = true;
				       }
		       }

		       return escalationUpdated;
		}



	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.MPUWebServiceProcessor#getRequestData(java.lang.String, java.lang.String, java.lang.String)
	 */
	public RequestDTO getRequestData(String requestId,String storeNumber, String fields,String itemId,List<String> status) throws DJException {
		//logger.debug("Entering MPUWebServiceProcessorImpl.getRequestData","");
		logger.info("Entering MPUWebServiceProcessorImpl.getRequestData	salesCheckNumber:",requestId  +": storeNumber: "+storeNumber +": fields: "+fields
				+": itemId: "+itemId +": status: "+status);
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setOrder(mpuWebServiceDAOImpl.getOrderDetails(storeNumber,requestId,status));
		if(null != fields && !fields.isEmpty()){
			fields=fields.toUpperCase();
			if(fields.matches("(.*)"+MpuWebConstants.ITEM+"(.*)")){
				List<ItemDTO> itemdtoList = mpuWebServiceDAOImpl.getItemList(storeNumber,requestId,itemId,status,false) ;
				for(ItemDTO itemdto: itemdtoList){
					if(null != itemdto.getAssignedUser() && !"".equals(itemdto.getAssignedUser().trim())){
						itemdto.setAssigneeFullName(pickUpServiceProcessor.getAssociateName(itemdto.getAssignedUser()));
					}
					getExpiredTime(itemdto);
				}
				getColorUpdated(itemdtoList);
				requestDTO.setItemList(itemdtoList);
			} if(fields.matches("(.*)"+MpuWebConstants.IDENTIFIER+"(.*)")){
				requestDTO.setCustomer(mpuWebServiceDAOImpl.getCustomerData(storeNumber, requestId));
			} if(fields.matches("(.*)"+MpuWebConstants.PAYMENT+"(.*)")){
				requestDTO.setPaymentList(mpuWebServiceDAOImpl.getPaymentList(storeNumber,requestId));
			} if(fields.matches("(.*)"+MpuWebConstants.PACKAGE+"(.*)")){
				requestDTO.setPackageList(mpuWebServiceDAOImpl.getPackageList(storeNumber,requestId));
			}

		}
		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.getRequestData");
		return requestDTO;
	}



	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.MPUWebServiceProcessor#reopenExpiredOrder(com.searshc.mpuwebservice.bean.RequestDTO, java.lang.String)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void reopenExpiredOrder(RequestDTO requestDTO,String expireTime) throws DJException{
		logger.info("Entering MPUWebServiceProcessorImpl.reopenExpiredOrder","");
		//logger.debug("Entering MPUWebServiceProcessorImpl.reopenExpiredOrder	requestDTO:", requestDTO +": expireTime: "+expireTime   );
		String currentStatus="";
		String storeNumber = requestDTO.getOrder().getStoreNumber();
		String requestType = requestDTO.getOrder().getRequestType();
		String requestStatus = requestDTO.getOrder().getRequestStatus();
		String rqtID = requestDTO.getOrder().getRqtId();
		String originalJson = requestDTO.getOrder().getOriginalJson();
		Boolean reopenFlag = true;

		List<ItemDTO> itemDTOs = requestDTO.getItemList();
		List<String> rqdIDs = new ArrayList<String>();
		List<ActivityDTO> activityDTOs = new ArrayList<ActivityDTO>();

		String activityDescription = (String)mpuWebServiceDAOImpl.getNextAction(requestType,
				currentStatus,MpuWebConstants.CREATE,
				MpuWebConstants.ACTIVITY,
				storeNumber);
		String actionSeq = (String)mpuWebServiceDAOImpl.getNextAction(requestType,
				currentStatus,MpuWebConstants.CREATE,
				MpuWebConstants.SEQUENCE,
				storeNumber);
		for(ItemDTO itemDTO : itemDTOs){
			rqdIDs.add(itemDTO.getRqdId());
			ActivityDTO activityDTO = new ActivityDTO();
			activityDTO.setActivityDescription(activityDescription);
			activityDTO.setActionSeq(actionSeq) ;
			activityDTO.setType(requestType);
			activityDTO.setStore(storeNumber);
			activityDTO.setFromLocation(itemDTO.getStockLocation());
			activityDTOs.add(activityDTO);
			/**
			 * For JIRA-STORESYS-23966
			 */
			if(null!=requestDTO.getCustomer()){
				itemDTO.setFullName(requestDTO.getCustomer().getFirstName()+" "+requestDTO.getCustomer().getLastName());
				itemDTO.setPhoneNumber(requestDTO.getCustomer().getPhoneNumber());
				itemDTO.setAltPhoneNumber(requestDTO.getCustomer().getAltPhoneNumber());
			}
		}
		mpuWebServiceDAOImpl.updateAllItems(rqtID,expireTime,storeNumber,rqdIDs);
		//logger.debug("reopenExpiredOrder","updateAllItems Completed");

		/*
		 * For updating the EhCache
		 */
		Calendar calendar = Calendar.getInstance();
		long milliSeconds= Long.parseLong(expireTime);
		calendar.setTimeInMillis(milliSeconds);
		String escalationTime = DJUtilities.dateToString(calendar.getTime(),MpuWebConstants.DATE_FORMAT);
		
		 List<ItemDTO> finalItemList = new ArrayList<ItemDTO>();
		 rqdIDs.clear();

		for(ItemDTO item : itemDTOs){
			int escalation = Integer.valueOf(item.getEscalation());
			if(!MpuWebConstants.BINPENDING.equalsIgnoreCase(item.getItemStatus())){
				item.setAssignedUser(null);
			}
			//item.setToLocation(null);
			item.setItemStatus(MpuWebConstants.OPEN);
			item.setEscalationTime(escalationTime);
			item.setEscalation(String.valueOf(++escalation));
			
			/**For making final item List****/
			List<String> statusList = new ArrayList<String>();
			statusList.add("OPEN");
			List <ItemDTO> itemList = mpuWebServiceDAOImpl.getItemList(item.getStoreNumber(),item.getRqtId(), item.getRqdId(), statusList, false);
			if(null!=itemList && null!=itemList.get(0)){
				if(!MpuWebConstants.BINNED.equalsIgnoreCase(itemList.get(0).getItemStatus()) &&
						!MpuWebConstants.CLOSED.equalsIgnoreCase(itemList.get(0).getItemStatus())){
					finalItemList.add(itemList.get(0));
					rqdIDs.add(itemList.get(0).getRqdId());
				}
			}
		}
		String fullName = "";
		Object[] rqdIdArray = null;
		if(null!=rqdIDs){
			rqdIdArray = rqdIDs.toArray();
		}
		boolean isCacheUpdated = addToEhCache(finalItemList, rqdIdArray, storeNumber, requestType, fullName);
		//logger.debug("isCacheUpdated = ", isCacheUpdated);

		mpuWebServiceDAOImpl.createActivity(activityDTOs,Integer.parseInt(rqtID), rqdIDs.toArray(),storeNumber);
		//logger.debug("reopenExpiredOrder","createActivity Completed");
		ArrayList<String> rqtList=new ArrayList<String>();
		rqtList.add(rqtID);
		mpuWebServiceDAOImpl.updateOrder(storeNumber, rqtList,requestStatus,MpuWebConstants.OPENSTATUS,originalJson,reopenFlag);
		logger.info("reopenExpiredOrder","updateOrder Completed");
	}


	private void updateBinToNPOS(ItemDTO  itemDTO) throws DJException{
		logger.info("entering updateBinToNPOS", "itemDTO=="+"");
		OrderAdaptorRequest request=	mpuWebServiceDAOImpl.getOriginalJSON(itemDTO.getRqtId(), itemDTO.getStoreNumber(),null);
		if(request.getRequestOrder()!=null){
			Order originalOrder=request.getRequestOrder();
			List<OrderItem> nposItemsList=request.getRequestOrder().getItems();
			List<OrderItem> nposItemsListFinal=new ArrayList<OrderItem>();

			for(OrderItem item:nposItemsList){

				String itemIdentifier= MPUWebServiceUtil.nullToBlank(itemDTO.getDivNum())+MPUWebServiceUtil.nullToBlank(itemDTO.getItem())
						+MPUWebServiceUtil.nullToBlank(itemDTO.getSku())+MPUWebServiceUtil.nullToBlank(itemDTO.getPlus4())
						+","+MPUWebServiceUtil.nullToBlank(itemDTO.getKsn())+","+MPUWebServiceUtil.nullToBlank(itemDTO.getUpc())+",";

				if(null!=itemDTO.getItemId() 
						&& "".equalsIgnoreCase(MPUWebServiceUtil.nullToBlank(itemDTO.getItemId().trim()))){
					itemIdentifier = itemIdentifier + MPUWebServiceUtil.nullToBlank(itemDTO.getItemId()) +",-"+MPUWebServiceUtil.nullToBlank(itemDTO.getItemSeq());
				}else{
					itemIdentifier = itemIdentifier + MPUWebServiceUtil.nullToBlank(itemDTO.getItemId())+"-"+MPUWebServiceUtil.nullToBlank(itemDTO.getItemSeq());
				}
				
				/**
				 * For CrossFormat Orders
				 * @author nkhan6
				 */
				if(null!=itemDTO.getUpc() && itemDTO.getUpc().startsWith("LM")){
					itemIdentifier = MPUWebServiceUtil.nullToBlank(itemDTO.getDivNum())+MPUWebServiceUtil.nullToBlank(itemDTO.getItem())
							+MPUWebServiceUtil.nullToBlank(itemDTO.getSku())+","+MPUWebServiceUtil.nullToBlank(itemDTO.getUpc())
							+"-"+MPUWebServiceUtil.nullToBlank(itemDTO.getItemSeq()); 
				}
				
				String itemIdentifierNPOS=item.getItemIdentifiers()+"-"+item.getSequenceNo();

				logger.info("Identifier Info for BIN :",itemIdentifier + " : " + itemIdentifierNPOS );


				if(itemIdentifier.equals(itemIdentifierNPOS)){
					String bin="";
					if(itemDTO.getToLocation()!=null){
						bin=itemDTO.getToLocation().replaceAll("BIN", "");
					}
					logger.info("BinNumber to NPOS :",bin);
					item.setItemBinNumber(bin);

					//For JIRA-STORESYS-23915
					nposItemsListFinal.add(item);
				}

				//nposItemsListFinal.add(item);
			}
			originalOrder.setItems(nposItemsListFinal);
			originalOrder.setRingingAssociateCode(getAssociateId());


			request.setRequestOrder(originalOrder);


		}

		nPOSUpdateProcessorImpl.updateNPOS(request, "updateBin");
		logger.info("exiting updateBinToNPOS", "updated");

	}



	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void expireOrder(List<String> rqtList,List<String> rqdList,HashMap<String,String> mapping,String strNum,HashMap<String,String> requestTypeMap,String queueType) throws DJException{
		//logger.debug("Entering MPUWebServiceProcessorImpl.expireOrder","");
		logger.info("Entering MPUWebServiceProcessorImpl.expireOrder	rqtList:",rqtList +": rqdList: "+rqdList +": mapping: "+mapping  +": strNum: "+strNum
				+": requestTypeMap: "+requestTypeMap +": queueType: "+queueType  );
		//mpuWebServiceDAOImpl.updateOrder(strNum, rqtList, "OPEN", "EXPIRED",null,false);
		/** STORESYS-25531  */
		//mpuWebServiceDAOImpl.updateOrder(strNum, rqtList, "WIP", "EXPIRED",null,false);
		/** STORESYS-25531  */
		/*EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		List<String> expiredOrderList=null;
		if(null!=requestQueueCache.get("ExpiredOrderList")){
			expiredOrderList = (ArrayList<String>) requestQueueCache.get("ExpiredOrderList").get();
		}else {
			expiredOrderList = new ArrayList<String>();
		}*/
		
		
		HashMap<String,Object> nextSteps=(HashMap<String,Object>)mpuWebServiceDAOImpl.getNextAction("", "", "EXPIRE", null, strNum);
		mpuWebServiceDAOImpl.cancelExpireItems(rqdList, strNum,(String)nextSteps.get("status"));

		mpuWebServiceDAOImpl.insertBulkActivities(rqtList, rqdList, mapping, (String)nextSteps.get("seq"), (String)nextSteps.get("activity"), strNum, requestTypeMap);

		/*
		 * For direct request we need to send the response to OBU queue with status as PROCESSED
		 * and item level status as NORESPONSE for item which is  expired
		 * by Nasir
		 */
		List<String> completeRqtList = new ArrayList<String>();
		List<String> expiredRqtList = new ArrayList<String>();
		
		ArrayList<String> expiredOrderList=getExpiredList();
		for(String rqtId : rqtList){
			/*
			 * the status of the order would be expired now in database.Since that is already executed
			 */
			if(!expiredOrderList.contains(rqtId)){
				String status = MpuWebConstants.EXPIRED;
				List<String> statusList = new ArrayList<String>();
				statusList.add(status);
				statusList.add("OPEN");
				statusList.add("WIP");
				OrderDTO order = mpuWebServiceDAOImpl.getOrderDetails(strNum, rqtId, statusList);
				
				/**
				 * JIRA-STORESYS-25599
				 */
				List<String> itemStatusList = new ArrayList<String>();
				itemStatusList.add("ALL");
				itemStatusList.add(MpuWebConstants.EXPIRED);
				
				List<ItemDTO> itemList = mpuWebServiceDAOImpl.getItemList(strNum, rqtId, null, itemStatusList, false);
				String requestStatus = "EXPIRED";
				boolean completeFlag = false;
				if(null!=itemList){
					for(ItemDTO item:itemList){
						if(MpuWebConstants.BINNED.equalsIgnoreCase(item.getItemStatus()) || MpuWebConstants.CLOSED.equalsIgnoreCase(item.getItemStatus())){
							requestStatus = "COMPLETED";
							completeFlag = true;
							completeRqtList.add(rqtId);
							
							break;
						}
					}
				}
				if(!completeFlag){
					expiredRqtList.add(rqtId);
				}
				
				if(null!=order && MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(order.getOrderSource())){
					this.sendFinalResponse(strNum, rqtId, null, requestStatus,order);
				}
				
			/*	if(null!=requestQueueCache.get("ExpiredOrderList")){
					expiredOrderList = (ArrayList<String>) requestQueueCache.get("ExpiredOrderList").get();
					expiredOrderList.add(rqtId);
				}else{
					expiredOrderList.add(rqtId);
				}
				
				requestQueueCache.put("ExpiredOrderList", expiredOrderList);*/
			}
		}
		if(!completeRqtList.isEmpty()){
			mpuWebServiceDAOImpl.updateOrderList(strNum, completeRqtList,"COMPLETED",null,false);
		}
		if(!expiredRqtList.isEmpty()){
			mpuWebServiceDAOImpl.updateOrderList(strNum, expiredRqtList,"EXPIRED",null,false);
		}
		//Remove the items from cache itemList 
		removeItemsFromCache(rqdList,strNum,queueType);
		
		/** to send the NA response notification to OBU**/
		sendNoResponseToOBUForHG();

	}
	

/**Service to determaine if there is any expired item dfor which the no response notification need
 * 	to be sent or not
 * @throws DJException
 */
	private void sendNoResponseToOBUForHG() {/*
		try{
			logger.info("entering the sendNoResponseForHG","");
			List<ItemDTO> finalExpiredItems = new ArrayList<ItemDTO>();
			HashSet<String> expiredOrdrIds = new HashSet<String>();
			if(!CollectionUtils.isEmpty(expiredHGItems)){
				logger.info("the expired HG list size is=== ",expiredHGItems.size());
				for(Map<String, ItemDTO> itemMap : expiredHGItems){
					ItemDTO itemDTO = (ItemDTO)itemMap;
					if(expiredOrdrIds.add(itemDTO.getRqtId())){
						//logger.info("adding the item rqd == ", itemDTO.getRqdId());
						finalExpiredItems.add(itemDTO);
					}
					
				}
			}
			
			expiredHGItems.clear();
			for(ItemDTO itemDTO :  finalExpiredItems){
				if(mpuWebServiceDAOImpl.checkRequestComplete(itemDTO.getStoreNumber(), itemDTO.getRqtId(), itemDTO.getRequestType())){
					sendFinalResponse(itemDTO.getStoreNumber(), itemDTO.getRqtId(), itemDTO.getRequestNumber(), MpuWebConstants.HOLDGO);	
				}
				
			}
		}catch(Exception exception){
			logger.error("in the catch block of sendNoResponseToOBUForHG",exception.getMessage() );
		}
		logger.info("exiting the sendNoResponseForHG","");
	*/}
	
	public   Map<String,String> getRequestQueueMap(String storeNum){
		Map<String,String> requestQueueMap = new HashMap<String, String>();
		try {
			requestQueueMap = mpuWebServiceDAOImpl.getRequestQueueMeta(storeNum);
		} catch (DJException e) {
			logger.error("Exception in Obtaining RequestQueueMetadata = ", e);
		}
		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.expireOrder");
		return requestQueueMap;
	}

	/**
	 * This method add itemlist to ehcache
	 * @param itemList
	 * @param rqdId
	 * @param storeNumber
	 * @param requestType
	 * @param fullName
	 * @return
	 */
	public boolean addToEhCache(List<ItemDTO> itemList,Object[] rqdId,String storeNumber,String requestType,String fullName){
		//logger.debug("addToEhCache ::","Entering");
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		/*if(null!=requestType && requestType.contains(MpuWebConstants.LAYAWAY)){
			requestType = requestType.concat(itemList.get(0).getLayawayType());
		}*/
		
		/******* changes done to insert data in cache for LAYAWAY case  **/
		String tempReqType = null;
		if(MpuWebConstants.LAYAWAY.equalsIgnoreCase(requestType)){
			tempReqType = requestType+"S";
		}else{
			tempReqType = requestType;
		}
		/******* changes done to insert data in cache for LAYAWAY case  **/
		Map<String, String> requestTypeQueueMap = getRequestQueueMap(storeNumber);
		String queueKey = storeNumber + "-"+requestTypeQueueMap.get(tempReqType);
		ConcurrentHashMap<String,ItemDTO> queueMap=null;
		//logger.info("addToEhCache ==","queueKey = "+ queueKey+"==rqdId = "+rqdId.toString());
		if(null!=requestQueueCache && null!=requestQueueCache.get(queueKey)){
			queueMap = (ConcurrentHashMap<String, ItemDTO>) requestQueueCache.get(queueKey).get();
		}

		if(null==queueMap || queueMap.isEmpty()){

			queueMap = new ConcurrentHashMap<String, ItemDTO>();

		}
		if(null!=rqdId){
			for(Object obj : rqdId){
				String reqDetId = String.valueOf(obj);
				ItemDTO item = itemList.get(Arrays.asList(rqdId).indexOf(obj));
				if(null == item.getRqdId()){
					item.setRqdId(reqDetId);
				}
				if(null == item.getFullName()){
					item.setFullName(fullName);
				}
				if(null == item.getRequestType()){
					if(requestType.indexOf(MpuWebConstants.LAYAWAY)!=-1 && StringUtils.hasText(item.getLayawayFlag())){
						if("S".equalsIgnoreCase(item.getLayawayFlag())){
							requestType = MpuWebConstants.LAYAWAYS;
						}else if("F".equalsIgnoreCase(item.getLayawayFlag())){
							requestType = MpuWebConstants.LAYAWAYF;
						}
							
				}
					item.setRequestType(requestType);
			}
				
				if(null==item.getCreatedBy() && MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(item.getModifiedBy())){
					item.setCreatedBy(MpuWebConstants.MPU_DIRECT);
				}
				queueMap.put(reqDetId,item);
			}
			requestQueueCache.put(queueKey, queueMap);
			logger.info("addToEhCache ::","Leaving");
			return true;
		}else{
			return false;
		}

	}
	
	
	

	public Boolean addRequestToEhCache(List<?> lRequestDTO, String storeNo, String kiosk, String reqType) throws Exception {

		logger.info("addToEhCache ::","Entering addRequestToEhCache lRequestDTO : " + " -- storeNo : " + storeNo + " -- kiosk : " + kiosk);

		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");

		requestQueueCache.put(DJUtilities.concatString(storeNo, "-", kiosk, "-", reqType), lRequestDTO);

		logger.info("addToEhCache ::","Exiting addRequestToEhCache");

		return Boolean.TRUE;
	}

	/**
	 * This method updates the ehcache
	 * @param action
	 * @param cacheRemoveFlag
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public boolean updateToEhCache(ItemDTO action,boolean cacheRemoveFlag){

		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		if(null!=action){
		Map<String, String> requestTypeQueueMap = getRequestQueueMap(action.getStoreNumber());
			String requestType = action.getRequestType();
			if(null!=requestType && requestType.equalsIgnoreCase(MpuWebConstants.LAYAWAY)){
				requestType = requestType.concat(action.getLayawayType());
			}
			String queueKey = action.getStoreNumber() + "-"+requestTypeQueueMap.get(requestType);
			//logger.info("==updateToEhCache====queueKey==RqdId==", queueKey+"=="+action.getRqdId());
		if(requestQueueCache.get(queueKey)!=null){
			ConcurrentHashMap<String,ItemDTO> queueMap = (ConcurrentHashMap<String, ItemDTO>) requestQueueCache.get(queueKey).get();
			if(null!=queueMap){
				if(cacheRemoveFlag){
					queueMap.remove(action.getRqdId());
				}else{
					if(null==action.getCreatedBy() && MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(action.getModifiedBy())){
						action.setCreatedBy(MpuWebConstants.MPU_DIRECT);
					}
					queueMap.put(action.getRqdId(),action);
				}
			}

			requestQueueCache.put(queueKey, queueMap);
			}

		else {
			//logger.info("item not found in cache", action.getRqdId());
			return false;
		}

		return true;
		}else{
			//logger.info("item is null = ", action);
			return false;
		}

	}



	@SuppressWarnings("unchecked")
	public List<ItemDTO> getAllItemFromEhCache(String storeNumber, String queueType,String kiosk,boolean postProcessingFlag) throws DJException{

		List<ItemDTO> modEligibleList = new ArrayList<ItemDTO>();
		//boolean postProcessingFlag = expiredOrderProcessorImpl.filterItemthread( storeNumber, queueType);
		List<Map<String, ItemDTO>>   expiredHGItemList = new ArrayList<Map<String,ItemDTO>>();
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		String queueKey = storeNumber + "-"+queueType;
		Date currentTime=Calendar.getInstance().getTime();
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HashMap<String,String> mapping=new HashMap<String,String>();
		HashMap<String,String> requestTypeMap=new HashMap<String,String>();
		List<ItemDTO> itemList=new ArrayList<ItemDTO>();
		ConcurrentHashMap<String,ItemDTO> queueMap;

		List<String> expiredRqtIdList = new ArrayList<String>();
		List<String> expiredRqdIdList = new ArrayList<String>();
		
		
		logger.info("getAllItemFromEhCache==queueType===storeNumber===queueKey==", queueType+"==="+storeNumber+"==="+queueKey);
		if(null != requestQueueCache.get(queueKey)){

			queueMap = (ConcurrentHashMap<String, ItemDTO>) requestQueueCache.get(queueKey).get();
			List<ItemDTO> escalationUpdatedItemList = new ArrayList<ItemDTO>();
			/*
			 * making list of expired items from cache
			 */
			for(String key : queueMap.keySet()){
				ItemDTO item = queueMap.get(key);
				String escalation_time = item.getEscalationTime();
				logger.info("getAllItemFromEhCache", "item.getRequestType() : " + item.getRequestType());
				getExpiredTime(item);
				boolean addFlag=true;
				boolean itemNotExpired=false;
				int escalationLevel = Integer.parseInt(item.getEscalation());
				Map<String, ItemDTO> expiredHGItem = new HashMap<String, ItemDTO>();
				try {
					Date escalationTime=date.parse(escalation_time);

					String currentEscalation = item.getEscalation();
					if(((item.getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB) && (item.getSalescheck().startsWith("09300") || item.getSalescheck().startsWith("09322")))
							|| (MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(item.getRequestType()))) && 
							escalationTime.before(currentTime)){
						//OrderDTO order = mpuWebServiceDAOImpl.getAllOrderDetails(storeNumber, item.getRqtId());
						//if(null!=order && MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(order.getOrderSource())){
						if(MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(item.getCreatedBy()) || "MPU_HG".equalsIgnoreCase(item.getCreatedBy())){	
							itemNotExpired = isItemNotExpire(item); 
							
						}else{
							itemNotExpired=false;
						}
						
						/**
						 * For Making escalationUpdateItemlist
						 */
						if(itemNotExpired && null!=currentEscalation && !currentEscalation.equalsIgnoreCase(item.getEscalation())){
							if("-1".equals(item.getEscalation())){
								item.setEscalation(currentEscalation);
							}
							escalationUpdatedItemList.add(item);
						}
					//	logger.debug("=getAllItemFromEhCache==", item.toString());
					//	logger.info("getAllItemFromEhCache::itemNotExpired==", itemNotExpired);
						if(!itemNotExpired){

							if( escalationTime.before(currentTime)){
								if (!expiredRqtIdList.contains(item.getRqtId())){
									expiredRqtIdList.add(item.getRqtId());
									//call cancellations
								}

								if (!expiredRqdIdList.contains(item.getRqdId())){
									expiredRqdIdList.add(item.getRqdId());
									//call cancellations
								}

								mapping.put(item.getRqdId(), item.getRqtId());
								requestTypeMap.put(item.getRqdId(), item.getRequestType());

								if("MPU_HG".equalsIgnoreCase(item.getCreatedBy())){
									expiredHGItem.put(item.getStoreNumber()+"_"+item.getRqtId(),item);
									expiredHGItemList.add(expiredHGItem);
								}
							}
						}else{
							//itemList.add(item);
							if("MOD_VERIFY".equals(item.getItemStatus())){
								if( Integer.parseInt(item.getEscalation()) > escalationLevel){
									item.setAssignedUser(null);
									item.setActionId(MpuWebConstants.MOD_DISAPPROVE);
									updateOrderRequest(item.getRequestNumber(),item);
								}
								addFlag=false;
							}else{
								/**
								 * Added for Mod Notofication
								 * @author nkhan6
								 */
								modEligibleList.add(item);
								//notifyMOD(item);
							}
							if(addFlag){
								//get color updated
								updateItemColor(item);
								itemList.add(item);
							}
						}
					}else{

						if(((item.getSalescheck().startsWith("09300") || item.getSalescheck().startsWith("09322")) && 
								item.getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB)) ||
								item.getRequestType().equalsIgnoreCase(MpuWebConstants.HG_REQUEST_TYPE))
						{
							modEligibleList.add(item);
							//notifyMOD(item);
						}

						if("MOD_VERIFY".equals(item.getItemStatus())){
							
							addFlag=false;
						}
						if(addFlag){
							itemList.add(item);
						}
					}
				}

				catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception = ", e);

				}
			}

			/**
			 * Update escalation of requests 
			 */
			if(!escalationUpdatedItemList.isEmpty()){
				int rowsUpdated = mpuWebServiceDAOImpl.updateEsacaltionList(storeNumber,escalationUpdatedItemList);
				logger.info("Escalation Update - rowsUpdated = ", rowsUpdated);
			}
			//modNotificationProcessorImpl.notifyMODForEligibleList(storeNumber,modEligibleList);
			
			/*
			 * Removing the expired items from DB and cache both
			 */
			
			logger.info("the expired order list is ", expiredRqtIdList.size());
			logger.info("the expired item list is ", expiredRqdIdList.size());
			logger.info("the expired HG list is ", expiredHGItemList.size());
			logger.info("==getAllItemFromEhCache==expiredRqdIdList==", expiredRqdIdList);
/*			if(!expiredRqdIdList.isEmpty()){
				expiredOrderProcessorImpl.expireOrder(expiredRqtIdList,expiredRqdIdList,mapping,storeNumber,requestTypeMap,queueType,expiredHGItemList);										
			}*/
			
			if(postProcessingFlag){
				logger.error("Creating async thread ","");
				modNotificationProcessorImpl.postProcessing(expiredRqtIdList, expiredRqdIdList, mapping, storeNumber, requestTypeMap,
						queueType, expiredHGItemList, modEligibleList);
			}
		}else{
			return null;
		}

		return itemList;

	}
	/**
	 * This method clears the ehcache as well as the Metadata stored
	 */
	public boolean clearCache(String refreshAll) {
		logger.info("Entering MPUWebServiceProcessorImpl.clearCache","" );
		// TODO Auto-generated method stub
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		requestQueueCache.clear();
		if("true".equalsIgnoreCase(refreshAll)){
			mpuWebServiceDAOImpl.clearMetadata();
		}
		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.clearCache");
		return true;
	}

	/**
	 * This method removes the expired items from ehcache
	 * @param rqdList
	 * @param storeNum
	 * @param queueType
	 */
	@SuppressWarnings("unchecked")
	public void removeItemsFromCache(List<String> rqdList,String storeNum,String queueType){
		//logger.debug("Entering MPUWebServiceProcessorImpl.removeItemsFromCache","");
		logger.info("Entering MPUWebServiceProcessorImpl.removeItemsFromCache	rqdList:",rqdList +": storeNum: "+storeNum +": queueType: "+queueType );
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		String queueKey = storeNum+"-"+queueType;
		if(requestQueueCache.get(queueKey)!=null)
		{
			Map<String,ItemDTO> queueMap = (Map<String, ItemDTO>) requestQueueCache.get(queueKey).get();
			for(String key:rqdList){
				queueMap.remove(key);
			}
			requestQueueCache.put(queueKey, queueMap);
		}
		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.removeItemsFromCache");
	}


	/* (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.MPUWebServiceProcessor#getRequestData(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void createValidOrder(RequestDTO requestDTO) throws DJException, DataAccessException, DDRMetaException {
		logger.info("Entering MPUWebServiceProcessorImpl.createValidOrder","");
		//logger.debug("Entering MPUWebServiceProcessorImpl.createValidOrder	requestDTO:",requestDTO  );
		try {
			List<Map<String,Object>> validCheckList;
			String dupOrderStatus = null;
			String dupOrderCreateTime = null;
			String processFlag = null;
			String storeTimeZone=null;
			String createStoreTime = null;
			RequestDTO receivedDTO = null;
			String queue = null;

		//	logger.debug("Entering MPUWebServiceProcessorImpl.createValidOrder	RequestType:",requestDTO.getOrder().getRequestType()  );
			//logger.info("Entering MPUWebServiceProcessorImpl.createValidOrder	RequestType:",requestDTO.getOrder().getRequestType()  );
			// Changes for Issue STORESYS-24129 - Start
			if(requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB)){
				queue = MpuWebConstants.QUEUE_TYPE_WEB;
			}else if(requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.STAGE) || requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.BINSTAGE) || requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.S991)){
				queue = MpuWebConstants.STAGE;
			}else if(requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.HG_REQUEST_TYPE)){
				queue = MpuWebConstants.HG_REQUEST_TYPE;
			}else if(requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.MOD_REG_VISIT)){
				// MOD Regulatory Visit STORESYS-24663 - Start
				int taskTypeId = Integer.parseInt(MpuWebConstants.TASKTYPE_REG_VISIT);  
				String storeNo = requestDTO.getOrder().getStoreNumber();
				String regulatoryMessage = requestDTO.getOrder().getRegisterMessage();
				webServiceMODProcessor.createMODNotificationFromNPOS(taskTypeId,storeNo,regulatoryMessage);	
				return;
				// MOD Regulatory Visit STORESYS-24663 - Ends
			}else if(requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.MOD_PAGE_REGISTER)){
				// MOD Page From Register - Start
				int taskTypeId = Integer.parseInt(MpuWebConstants.TASKTYPE_PAGE_REG);  
				String storeNo = requestDTO.getOrder().getStoreNumber();
				String regulatoryMessage = requestDTO.getOrder().getRegisterMessage();
				webServiceMODProcessor.createMODNotificationFromNPOS(taskTypeId,storeNo,regulatoryMessage);	
				return;
				// MOD Page From Register - Start
			}else if(requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.MPU_PAGE_REGISTER)){
				//Added for JIRA-25084 (platform pickup- pages from register are not coming to MPU)
				//logger.debug("Entering pickUpServiceProcessor.orderPosMessage."," test");
				//logger.info("Entering pickUpServiceProcessor.orderPosMessage."," test");
				pickUpServiceProcessor.orderPosMessage(requestDTO);
				return;
			}

			getAllItemList(requestDTO.getOrder().getStoreNumber(),queue,null,"N","");
			//Changes for Issue STORESYS-24129 - Ends
			validCheckList =  mpuWebServiceDAOImpl.checkValidOrder(requestDTO);

			if(null != validCheckList && !validCheckList.isEmpty()){
				Object rqt_id = validCheckList.get(0).get(validCheckList.get(0).keySet().toArray()[0]);
				Object key = validCheckList.get(0).keySet().toArray()[1];
				dupOrderStatus = validCheckList.get(0).get(key).toString().split(";")[0];
				List<String> dupOrderStatusList = Arrays.asList(dupOrderStatus);
				//start add for reserve it changes
				if(MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(requestDTO.getOrder().getRequestType())){
					if(MpuWebConstants.OPEN.equalsIgnoreCase(dupOrderStatus)||MpuWebConstants.WIP.equalsIgnoreCase(dupOrderStatus)||
							MpuWebConstants.CANCELLED.equalsIgnoreCase(dupOrderStatus)||MpuWebConstants.COMPLETED.equalsIgnoreCase(dupOrderStatus)){
						processFlag = "DISCARD";
					}else{
						processFlag = "INSERT";
					}
				}else if(MpuWebConstants.BINWEB.equalsIgnoreCase(requestDTO.getOrder().getRequestType()) 
						&& MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(requestDTO.getOrder().getOrderSource())){
					/**
					 * Discard duplicate requests for direct orders
					 */
					

					/**
					 * Discard duplicate requests for direct orders
					 */
					logger.error("Duplicate salescheck found"+requestDTO.getOrder().getSalescheck()+"="+requestDTO.getOrder().getStoreNumber(),"");
					
					dupOrderCreateTime = validCheckList.get(0).get(key).toString().split(";")[1];
					
					
					SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date dupOrderCreateDate=date.parse(dupOrderCreateTime);
					
					Date currentTime=new Date();
					
					
					int diff=Math.abs(SHCDateUtils.getDateDiff(currentTime,dupOrderCreateDate));
					
					
					if(diff<=0){
					
					processFlag = "DISCARD";
					logger.error("Same day duplicate salescheck for same store"+requestDTO.getOrder().getStoreNumber(),"");
					
					}
					
					
					else if (diff>0){ processFlag="INSERT";
					logger.error("Different day duplicate salescheck for same store"+requestDTO.getOrder().getStoreNumber(),"");
					
					}
					
				}else{
					//end add for reserve it changes
					dupOrderCreateTime = validCheckList.get(0).get(key).toString().split(";")[1];

					Map<String,Object> storeInfo=mpuWebServiceDAOImpl.getStoreDetails(requestDTO.getOrder().getStoreNumber());
					//logger.debug("createValidOrder : storeInfo", storeInfo);
					if(storeInfo!=null){
						storeTimeZone=(String) storeInfo.get("timeZone");
					}


					createStoreTime = SHCDateUtils.convertFromLocalToStore(dupOrderCreateTime, storeTimeZone);

					SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date createTime=date.parse(createStoreTime);
					//String converted to OLong to avoid deprecated warning
					Date expireTime = null;
					//To resolve the issue when NPOS sends web_ExpireTime as null
					//if(requestDTO.getTask().getTaskType().equalsIgnoreCase("104") && 
					if(	(requestDTO.getOrder().getWeb_ExpireTime() == null || requestDTO.getOrder().getWeb_ExpireTime().equalsIgnoreCase("null"))){

						SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
						format.setTimeZone(TimeZone.getDefault());			
						Date tempDate=format.parse(requestDTO.getTask().getTimeReceived());
						long millis = tempDate.getTime() + (mpuWebServiceDAOImpl.getEscalationTime(requestDTO.getOrder().getStoreNumber())* 60 *1000);	
						expireTime = new Date(Long.parseLong(millis+""));
					}
					else{
						expireTime=new Date(Long.parseLong(requestDTO.getOrder().getWeb_ExpireTime()));
					}
					int diff=Math.abs(SHCDateUtils.getDateDiff(createTime,expireTime));

					if(!"EXPIRED".equalsIgnoreCase(dupOrderStatus)){
						processFlag = "DISCARD";
					}else{

						if(diff <= 1 ){
							String fields = MpuWebConstants.ITEM+","+MpuWebConstants.IDENTIFIER;
							receivedDTO = getRequestData(rqt_id.toString(), requestDTO.getOrder().getStoreNumber(),fields,null,dupOrderStatusList);
							receivedDTO.getOrder().setOriginalJson(requestDTO.getOrder().getOriginalJson());
							processFlag = "REOPEN";
						}else{
							// Considering the Order will be expired after 2 days of Inactivity
							processFlag = "INSERT";
						}
					}
				}
			}
			else{
				processFlag = "INSERT";
			}

			ProcessFlag pFlag = ProcessFlag.valueOf(processFlag);

			switch(pFlag){

			case INSERT:			createRequest(requestDTO.getOrder(), requestDTO.getCustomer(), requestDTO.getItemList(), requestDTO.getPaymentList(), requestDTO.getTask());
			break;

			case REOPEN:			reopenExpiredOrder(receivedDTO,requestDTO.getOrder().getWeb_ExpireTime());
			break;

			case DISCARD : 		break;

			default :					break;
			}

		}catch (ParseException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.createValidOrder");  
	}

	@SuppressWarnings("unchecked")
	public Map<String, ItemDTO> getAllCachedContent(String storeNumber,
			String queueType, String kiosk) throws DJException {
		//logger.debug("Entering MPUWebServiceProcessorImpl.getAllCachedContent","");
		logger.info("Entering MPUWebServiceProcessorImpl.getAllCachedContent	storeNumber:",storeNumber 	+": queueType: "+queueType 	+": kiosk: "+kiosk);
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		String queueKey = storeNumber+"-"+queueType;
		ConcurrentHashMap<String,ItemDTO> queueMap = new ConcurrentHashMap<String, ItemDTO>(); 
		if(null!=requestQueueCache.get(queueKey)){		
			queueMap = (ConcurrentHashMap<String, ItemDTO>) requestQueueCache.get(queueKey).get();
		}
		logger.info("Exiting" ,"MPUWebServiceProcessorImpl.getAllCachedContent");
		return queueMap;
	}

	public Map<String,Object> getstoreDetails(String storeNumber)throws DJException{
		return mpuWebServiceDAOImpl.getStoreDetails(storeNumber);
	}


	public boolean assignUser(String store,String rqdId,String assignedUser,String requestType,String rqtId, String searsSalesId) throws DJException{
		logger.info("entering mpuwebserviceProcessorimpl.assignUser", "store=="+store+"rqdId=="+rqdId+"assignedUser=="+assignedUser+"requestType=="+requestType+"rqtId=="+rqtId);
		int result=mpuWebServiceDAOImpl.assignUser(store,rqdId,assignedUser,requestType,rqtId, searsSalesId);
		//version number should be updated in cache if db update is done
		if(result>0){

			ItemDTO item=getItemFromCache(requestType, store, rqdId);
			if(item!=null){
				item.setAssignedUser(assignedUser);
				// update user name as well as ldapId in cache
				item.setAssigneeFullName(pickUpServiceProcessor.getAssociateName(assignedUser));
				if(requestType!=null && MpuWebConstants.HG_REQUEST_TYPE.equalsIgnoreCase(requestType) && null!=assignedUser && MpuWebConstants.OPENSTATUS.equalsIgnoreCase(item.getItemStatus())){
					item.setItemStatus(MpuWebConstants.ASSIGNED);
				}
				if(item.getVersion()!=null){
					long version=Long.parseLong(item.getVersion());
					//version=version+1;
					item.setVersion(new Long(version+1).toString());}
				updateToEhCache(item, false);
				
				logger.info("entry into request_activity table for H&G request assignment", item.getRequestType());
				// entry into request_activity table for H&G request assignment
				if("H&G".equalsIgnoreCase(item.getRequestType())){
					logger.info("assignUser create activity req type", item.getRequestType());
				String seq=(String)mpuWebServiceDAOImpl.getNextAction(item.getRequestType(),"OPEN","ASSIGN" ,
						MpuWebConstants.SEQUENCE, item.getStoreNumber());
				logger.info("assignUser create activity seq", seq);
				String activityDesc=(String)mpuWebServiceDAOImpl.getNextAction(item.getRequestType(),"OPEN","ASSIGN" ,
						MpuWebConstants.ACTIVITY, item.getStoreNumber());
				logger.info("assignUser create activity activitydesc", activityDesc);
				mpuWebServiceDAOImpl.createActivity(MPUWebServiceUtil.convertActionToActivity(item, seq, activityDesc),Long.valueOf(rqtId),Long.valueOf(rqdId));
				}
			}
			return true;
		}
		logger.info("exiting mpuwebserviceProcessorimpl.assignUser", "user updated");
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<String> printLabel(String storeNum, String printerId,
			String rqdId, String type, String userId,String queueType,String requestId,boolean reprintFlag) throws DJException {
		//logger.debug("MPUWebServiceProcessorImpl:printLabel", "->Entering" + (null != MDC.get("txnId") ? " [txId:" +(String) MDC.get("txnId") + "]" : ""));
		List<String> response = new ArrayList<String>();
		try {
			EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
			String queueKey = storeNum+"-"+queueType;
			Map<String,ItemDTO> queueMap = new ConcurrentHashMap<String, ItemDTO>(); 
			if(null!=requestQueueCache.get(queueKey)){		
				queueMap = (ConcurrentHashMap<String, ItemDTO>) requestQueueCache.get(queueKey).get();
			}
			ItemDTO itemDTO = queueMap.get(rqdId);
			//String status = "OPEN";
			List<String> status  = Arrays.asList("ALL");
			String itemId=rqdId;
			CustomerDTO custDTO = mpuWebServiceDAOImpl.getCustomerDataForPrint(storeNum, requestId); 
			SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int notificationCount=0;

			/*
			 * If the cache does not contain the request
			 * Which is unlikely to happen
			 */
			if(null==itemDTO){
				List<ItemDTO> itemList = mpuWebServiceDAOImpl.getItemList(storeNum,requestId,itemId,status,true);
				itemDTO = (null != itemList && !itemList.isEmpty()) ? itemList.get(0) : null;
			}
			if(null!=itemDTO){
				MPUItemVO mpuItemVO = new MPUItemVO();
				
				if (("SKU991SendToPMT".equalsIgnoreCase(type))) {
					mpuItemVO.setReturnReason(null!=itemDTO.getReturnReason()?itemDTO.getReturnReason():"");
					mpuItemVO.setStoreNumber(storeNum);
					mpuItemVO.setTaskType("PMTTicket");
					String salescheck = itemDTO.getSalescheck();
					if (null != salescheck && !"".equalsIgnoreCase(salescheck) && salescheck.length()==12) {
						mpuItemVO.setRegisterNum(salescheck.substring(5, 8));
						mpuItemVO.setTranNum(salescheck.substring(8));
					} else {
						mpuItemVO.setRegisterNum("");
						mpuItemVO.setTranNum("");
					}
				}
				
				mpuItemVO.setAssociateNumber(userId);
				mpuItemVO.setReprint(reprintFlag);
				mpuItemVO.setDivision(itemDTO.getDivNum());
				mpuItemVO.setItemNum(itemDTO.getItem());
				if(StringUtils.hasText(itemDTO.getThumbnailDesc()) && itemDTO.getThumbnailDesc().length()>30){
					mpuItemVO.setItemDescription(itemDTO.getThumbnailDesc().substring(0,30));	
				}else if(!StringUtils.hasText(itemDTO.getThumbnailDesc())){
					mpuItemVO.setItemDescription(itemDTO.getFullName());
				}else{
					mpuItemVO.setItemDescription(itemDTO.getThumbnailDesc());
				}
				mpuItemVO.setQuantity(Integer.valueOf(itemDTO.getQty()));

				/*
				 * Customer Info
				 */
				if(null!=custDTO){
					mpuItemVO.setFirstName(custDTO.getFirstName());
					mpuItemVO.setLastName(custDTO.getLastName());

					/*
					 * For JIRA-23395
					 */
					if(null!=custDTO.getPhoneNumber() && custDTO.getPhoneNumber().length()>=10){
						mpuItemVO.setAreaCode(custDTO.getPhoneNumber().substring(0, 3));
						mpuItemVO.setPhoneNumber(custDTO.getPhoneNumber().substring(3, 10));
					}

					if(null!=custDTO.getAltPhoneNumber() && custDTO.getAltPhoneNumber().length()>=10){
						mpuItemVO.setAltAreaCode(custDTO.getAltPhoneNumber().substring(0, 3));
						mpuItemVO.setAltPhoneNumber(custDTO.getAltPhoneNumber().substring(3, 10));
					}

				}
				mpuItemVO.setSku(itemDTO.getSku());
				mpuItemVO.setRequestId(itemDTO.getRequestNumber());
				mpuItemVO.setItemCount(Integer.valueOf(itemDTO.getQty()));
				if(StringUtils.hasText(itemDTO.getStockLocation())){
					if(itemDTO.getStockLocation().equalsIgnoreCase(MpuWebConstants.NA)){
						mpuItemVO.setStockLocation("");		
					}else{
						mpuItemVO.setStockLocation(itemDTO.getStockLocation());
					}
				}

				mpuItemVO.setItemlocation(itemDTO.getFromLocation());
				mpuItemVO.setDestination(itemDTO.getToLocation());
				mpuItemVO.setBinnumber(itemDTO.getItemBinNumber());
				mpuItemVO.setTransactiontype(itemDTO.getItemTransactionType());

				/*
				 * Delivery type code is set to print the item Description
				 * For time being the delivery type code is set to transaction type
				 */
				mpuItemVO.setDeliveryType(itemDTO.getItemTransactionType());

				mpuItemVO.setOrigin(itemDTO.getItemSaleOrigin());	
				mpuItemVO.setOrderId(itemDTO.getSalescheck());
				mpuItemVO.setPlus4(itemDTO.getPlus4());
				mpuItemVO.setSaleType(itemDTO.getSaleType());
				Date escalationTime=date.parse(itemDTO.getEscalationTime());
				/*Date timeRecieved = Calendar.getInstance().getTime();
					mpuItemVO.setTimeRecieved(timeRecieved);*/

				HashMap<String,Object> storeInfo=(HashMap<String, Object>)mpuWebServiceDAOImpl.getStoreDetails(storeNum);
				int escalation_time_val=Integer.parseInt((String)(storeInfo.get(MpuWebConstants.ESCALATION_TIME)));
				mpuItemVO.setExpirationTime(escalationTime);

				/**
				 * For JIRA-STORESYS-23405
				 */

				if(MpuWebConstants.QUEUE_TYPE_WEB.equalsIgnoreCase(queueType) && null!=itemDTO.getSalescheck() && itemDTO.getSalescheck().startsWith("093")){
					if(null!=itemDTO.getExpireTime()){
						mpuItemVO.setExpirationTime(escalationTime);
						//logger.info("escalationTime--For Npos Order==", escalationTime);
					}



					if(MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(itemDTO.getCreatedBy())){					
						long expireTime = escalation_time_val*60*1000 + (escalationTime.getTime());
						Date itemExpiryTime = new Date(expireTime);
						//logger.info("escalationTime--For Direct Order==", itemExpiryTime);
						mpuItemVO.setExpirationTime(itemExpiryTime);
					}

					long createDateInMin=0;
					long currentDateInMin=0;
					if(null!=itemDTO.getItemCreatedDate()){
						Date createDate=date.parse(itemDTO.getItemCreatedDate());
						createDateInMin = createDate.getTime()/(60*1000);
						currentDateInMin = Calendar.getInstance().getTimeInMillis()/(60*1000);
						//logger.info("========createDateInMin=========", createDateInMin);
						//logger.info("========currentDateInMin=========", currentDateInMin);
					}




					if(MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(itemDTO.getCreatedBy())){
						notificationCount = Integer.parseInt(itemDTO.getEscalation());
					}else{
						notificationCount = (int) ((currentDateInMin - createDateInMin)/escalation_time_val);
					}

				//	logger.info("========notificationCount=========", notificationCount);
					mpuItemVO.setNotifyCount(String.valueOf(notificationCount+1));


					/*
					 * For PageTime
					 */
					if(null!=itemDTO.getItemCreatedDate()){
						Date timeRecieved = date.parse(itemDTO.getItemCreatedDate());
						//logger.info("==timeRecieved==", timeRecieved);
						Date escTime = escalationTime;
						long pageTime=0;
						Calendar cal = new GregorianCalendar();
						Date currentTime = cal.getTime();
						if(MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(itemDTO.getCreatedBy())){
							if(currentTime.before(escTime) && notificationCount>0){
								pageTime = escTime.getTime(); 
							}else{
								pageTime = timeRecieved.getTime() + notificationCount*escalation_time_val*60*1000;
							}
						}else{
							pageTime = timeRecieved.getTime() + notificationCount*escalation_time_val*60*1000;
						}
						//long 
						timeRecieved.setTime(pageTime);
						mpuItemVO.setTimeRecieved(timeRecieved);
					}
				}

				//String url="http://hfdvsbointjbos1.vm.itg.corp.us.shldcorp.com:8180/DEJInterfaces/v1/print/"+printerId+"/"+storeNum+"?type="+type;
				/****** for the HG items the type need to be different****/
				if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.HG_REQUEST_TYPE)){
					type="HoldAndGo";
				}
				String url = PropertyUtils.getProperty("PrintService_url")+printerId+"/"+storeNum+"?type="+type;

			//	logger.debug("the url for printLabelForItem is as :",url + (null != MDC.get("txnId") ? " [txId:" +(String) MDC.get("txnId") + "]" : ""));
				for(int i=0;i<Integer.parseInt(itemDTO.getQty());i++){
					mpuItemVO.setItemIndex(i);
					//mpuItemVO.setNotifyCount(String.valueOf(i+1));

					PrintServiceResponse printServResp = mpuWebServiceDAOImpl.printLabel(mpuItemVO,url);
					response.add(printServResp.getWsResponeMeta().getErrorCode());

				}
			}
		}catch (ParseException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
		logger.info("MPUWebServiceProcessorImpl:printLabel", "->Exiting" + (null != MDC.get("txnId") ? " [txId:" +(String) MDC.get("txnId") + "]" : ""));
		return response;
	}



	/*		public static int getModNotificationType(int elapsedMinutes){
	          int type=0;
	                    if (elapsedMinutes>=30){

	          int remainder=(elapsedMinutes/15)%4;


	          if(remainder==0||remainder==1){
	              type=2;//60-90 warning range
	          }
	          else if(remainder==2){
	              type=6;   //30-45 warning range
	          }
	          else if(remainder==3){
	              type=7;//45-60 warning range
	          }

	          }

	          return type;

	     }
	 */	

/*	private synchronized void sendMODNotification(ItemDTO itemDTO,int taskType) throws DJException{
		if(itemDTO.getStoreNumber().length()>4){
				   String StoreNumber=itemDTO.getStoreNumber().substring(1,5);
				   itemDTO.setStoreNumber(StoreNumber);
			   }
		logger.debug("Send MOD for :",itemDTO.getRqtId());
		HashMap<String,String> map = getMapFromItemDTO(itemDTO);
		if(mpuWebServiceDAOImpl.isModActive(itemDTO.getStoreNumber())){
			String mapKey=itemDTO.getRqdId();
			String mapStatus = this.mODNotification.get(mapKey);

			if((taskType==8) || (taskType ==11)){
				//OOS actionID
				CSMTaskDTO csmTaskDTO = webServiceMODProcessor.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
				webServiceMODProcessor.insertCSMTask(csmTaskDTO);
			}
			else if(taskType==6){
				if(mapStatus==null || ("3").equalsIgnoreCase(mapStatus)){
					//30-45 warning range
					CSMTaskDTO csmTaskDTO = webServiceMODProcessor.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
					webServiceMODProcessor.insertCSMTask(csmTaskDTO);
					this.mODNotification.put(mapKey,"1");
				}
			}else if(taskType==7){
				if(("1").equalsIgnoreCase(mapStatus)){
					//45-60 warning range
					CSMTaskDTO csmTaskDTO = webServiceMODProcessor.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
					webServiceMODProcessor.insertCSMTask(csmTaskDTO);
					this.mODNotification.put(mapKey,"2");
				}
			}else if(taskType==2){
				if(("2").equalsIgnoreCase(mapStatus)){
					//60-90 warning range
					CSMTaskDTO csmTaskDTO = webServiceMODProcessor.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
					webServiceMODProcessor.insertCSMTask(csmTaskDTO);
					this.mODNotification.put(mapKey,"3");
				}
			}  
		}
	}*/


	private int getMinFromTime(String timeElapsed){
		String[] timeArray=timeElapsed.split(":");
		return Integer.parseInt(timeArray[0])*60+(Integer.parseInt(timeArray[1]))+(Integer.parseInt(timeArray[2]))/60;

	}

	private HashMap<String, String> getMapFromItemDTO(ItemDTO itemDTO) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("FIRST_NAME", itemDTO.getFullName());
		map.put("LAST_NAME",""); 
		map.put("SALESCHECK", itemDTO.getSalescheck()); 
		map.put("ITEM_DESC", itemDTO.getThumbnailDesc()); 
		map.put("ITEM",itemDTO.getDivNum()+itemDTO.getItem()+itemDTO.getSku());  
		map.put("MESSAGE", "");
		map.put("REQ_QUANTITY", itemDTO.getQty());
		map.put("CREATED_DATE", itemDTO.getItemCreatedDate());
		map.put("ITEM_ID", itemDTO.getRqdId());
		return map;
	}

	private void getExpiredTime(ItemDTO itemdto){
		logger.info("getExpiredTime :itemdto.getItemCreatedDate() :", itemdto.getItemCreatedDate());
		try{
			SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentTime=Calendar.getInstance().getTime();
			Date createdate = date.parse(itemdto.getItemCreatedDate());


			//long result=(currentTime.getTime()-createdate.getTime())+(3600*Integer.parseInt(itemdto.getEscalation())*1000);  //Commented to Correct the TimeStamp.Should be checked for DirectToMPU
			if(MpuWebConstants.MPU_DIRECT.equalsIgnoreCase(itemdto.getCreatedBy()) ||
					"MPU_HG".equalsIgnoreCase(itemdto.getCreatedBy())){ 
				long escalationTime = date.parse(itemdto.getEscalationTime()).getTime();
				int escalation=Integer.parseInt(itemdto.getEscalation());
				HashMap<String,Object> storeInfo=(HashMap<String, Object>)mpuWebServiceDAOImpl.getStoreDetails(itemdto.getStoreNumber());

				int escalationLimit=Integer.parseInt((String)(storeInfo.get(MpuWebConstants.ESCALATION_TIME)));


				if(!currentTime.before(date.parse(itemdto.getEscalationTime()))){
					long result = (currentTime.getTime()-escalationTime)+(escalation*escalationLimit*60*1000);
					itemdto.setExpireTime(getElapsedTimeHoursMinutesSeconds(result));
				}else{
					itemdto.setExpireTime("00:00:00");
				}

			}else{
				long result=(currentTime.getTime()-createdate.getTime());
				itemdto.setExpireTime(getElapsedTimeHoursMinutesSeconds(result));
			}

		}catch(Exception exception){
			logger.error("getMapFromItemDTO",exception);
			itemdto.setExpireTime("00:00:00");
		}
	}
	public String getTimeDifference(String createDate, String store) throws DJException {
		Map<String ,Object> map =  getstoreDetails(store);
		String storeTimeZoneString = (String)map.get("timeZone");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date createdDate = null;
		try{
			createdDate = dateFormat.parse(createDate);    
		}catch(Exception exception){}
		long elapsedTime = CommonUtils.getStoreLocalTime(storeTimeZoneString).getTime()-createdDate.getTime();
		return getElapsedTimeHoursMinutesSeconds(elapsedTime);
	}

	public String getStoreTimeZone(String StoreNumber){

		try {
			Map<String, Object> map = getstoreDetails(StoreNumber);
			return (String) map.get("timeZone");
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	/*	    private Date getStoreLocalTime(String storeTimeZoneString) {
		            Calendar zoneCal = new GregorianCalendar(TimeZone.getTimeZone(storeTimeZoneString));
		            Calendar cal = Calendar.getInstance();
		            cal.set(Calendar.YEAR, zoneCal.get(Calendar.YEAR));
		            cal.set(Calendar.MONTH, zoneCal.get(Calendar.MONTH));
		            cal.set(Calendar.DAY_OF_MONTH, zoneCal.get(Calendar.DAY_OF_MONTH));
		            cal.set(Calendar.HOUR_OF_DAY, zoneCal.get(Calendar.HOUR_OF_DAY));
		            cal.set(Calendar.MINUTE, zoneCal.get(Calendar.MINUTE));
		            cal.set(Calendar.SECOND, zoneCal.get(Calendar.SECOND));
		            cal.set(Calendar.MILLISECOND, zoneCal.get(Calendar.MILLISECOND));
		            return new Date(cal.getTimeInMillis());
		        }

		    private Date getStoreLocalTimeForExpire(String storeTimeZoneString,Date date) {
		            Calendar zoneCal = new GregorianCalendar(TimeZone.getTimeZone(storeTimeZoneString));
		            zoneCal.setTime(date);
		            Calendar cal = Calendar.getInstance();
		            cal.set(Calendar.YEAR, zoneCal.get(Calendar.YEAR));
		            cal.set(Calendar.MONTH, zoneCal.get(Calendar.MONTH));
		            cal.set(Calendar.DAY_OF_MONTH, zoneCal.get(Calendar.DAY_OF_MONTH));
		            cal.set(Calendar.HOUR_OF_DAY, zoneCal.get(Calendar.HOUR_OF_DAY));
		            cal.set(Calendar.MINUTE, zoneCal.get(Calendar.MINUTE));
		            cal.set(Calendar.SECOND, zoneCal.get(Calendar.SECOND));
		            cal.set(Calendar.MILLISECOND, zoneCal.get(Calendar.MILLISECOND));
		            return new Date(cal.getTimeInMillis());
		        } */

	private String getElapsedTimeHoursMinutesSeconds(long elapsedTime) { 
		logger.info("elapsedTime :", elapsedTime);
		String format = String.format("%%0%dd", 2);  
		elapsedTime = elapsedTime / 1000;  
		String seconds = String.format(format, elapsedTime % 60);  
		String minutes = String.format(format, (elapsedTime % 3600) / 60);  
		String hours = String.format(format, elapsedTime / 3600);  
		String time =  hours + ":" + minutes + ":" + seconds;  
		return time;  
	}



	private void notifyMOD(ItemDTO itemdto) throws DJException{
		logger.info("entering mpuwebserviceprocessorImpl.notifyMOD","");
		int elapsedMinutes=getMinFromTime(itemdto.getExpireTime());
		Map<String,Object> storeInfo=mpuWebServiceDAOImpl.getStoreDetails(itemdto.getStoreNumber());
		//logger.debug("notifyMOD : storeInfo", storeInfo);
		String escalationTime="";
		int escalation_Time=60;
		if(storeInfo!=null){
			escalationTime=(String) storeInfo.get(MpuWebConstants.ESCALATION_TIME);
		}
		if(escalationTime!=null){
			escalation_Time= Integer.parseInt(escalationTime);

		}
		int notificationType=CommonUtils.getModNotificationType(elapsedMinutes,escalation_Time,itemdto.getRequestType());
		if(notificationType!=0){
			//logger.debug("Sending MOD Notification for ITEM","itemdto=="+itemdto.toString());
			modNotificationProcessorImpl.sendMODNotification(itemdto,notificationType);
		}
		logger.info("exiting mpuwebserviceprocessorImpl.notifyMOD","");
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<String> insertPackageDetails(String userId, String storeNo,
			String rqtId, String numberOfPackages, String fromLocation, String salescheck) throws DJException {
		List<String> insertStatus = new ArrayList<String>();
		insertStatus.add(0, "Success");
		try{
			String[] pkgNbr = new String[Integer.parseInt(numberOfPackages)];
			String packageNumber = "";
			String lastFourSalescheck = salescheck.substring(salescheck.length() - 4, salescheck.length());
			int rowUpdated = 0;
			for (int i = 0; i < Integer.parseInt(numberOfPackages); i++){
				packageNumber = lastFourSalescheck + ((i < 9) ? "0" + (i+1) : (i+1)+"");
				pkgNbr[i] = packageNumber;
				insertStatus.add(packageNumber);
			}
			mpuWebServiceDAOImpl.clearPreviousPackage(rqtId,storeNo);
			rowUpdated = mpuWebServiceDAOImpl.insertPackageDetails(Long.parseLong(rqtId), userId, storeNo, fromLocation, pkgNbr);
			if (rowUpdated <= 0) {
				insertStatus.add(0, "Failure");
			}
		}catch(Exception exception){
			logger.error("insertPackageDetails",exception);
			insertStatus.add(0, "Failure");
		}
		return insertStatus;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public String updatePackageDetails(String storeNo,
			String packageNumber, String binNumber) throws DJException {
		String updateStatus = "Success";
		try{
			mpuWebServiceDAOImpl.updatePackageDetails(packageNumber, binNumber, storeNo);
		}catch(Exception exception){
			logger.error("updatePackageDetails",exception);
			updateStatus = "Failure";
		}
		return updateStatus;
	}


	public List<String> printPackageLabel(String storeNum, String printerId, String pkgNumbers, String type, String user, String queueType, String requestId)
			throws DJException {
		logger.info("entering the printPackageLabel","store"+ storeNum+"printerId"+ printerId+"pkgNumbers"+ pkgNumbers+"type"+ type+"user"+ user+ "queueType"+ queueType+ "requestId"+ requestId);
		List<String> response = new ArrayList<String>();
		try {
			MPUItemVO mpuItemVO = new MPUItemVO();
			ArrayList<PackageVO> listOfPackages = new ArrayList<PackageVO>();
			ArrayList<ItemPrintVO> listOfItems =  new ArrayList<ItemPrintVO>();
			//STORESYS-23922 Fix Starts
			List<PackageDTO> totalPackages = mpuWebServiceDAOImpl.getPackageList(storeNum, requestId);
			mpuItemVO.setNumberOfPackages(String.valueOf(null != totalPackages ? totalPackages.size() : 0));     //Setting total number of packages	
			//STORESYS-23922 Fix Ends
			String[] packageNbrs=pkgNumbers.split("-");
			//mpuItemVO.setNumberOfPackages(String.valueOf(packageNbrs.length));     //Setting number of packages	

			String pkgSeq = "";
			for(String pkgNumber:Arrays.asList(packageNbrs)) {
				pkgSeq = (Integer.parseInt(pkgNumber.substring(pkgNumber.length() - 2)) > 9) ? pkgNumber.substring(pkgNumber.length() - 2) : pkgNumber.substring(pkgNumber.length() - 1);
				listOfPackages.add(new PackageVO(pkgSeq, pkgNumber));
			}
			mpuItemVO.setListOfPackages(listOfPackages);							//Setting list of packages
			//STORESYS-23914 Fix Starts
			List<ItemDTO> rawItemDTOs = mpuWebServiceDAOImpl.getItemListForOrder(storeNum,requestId);
			List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
			for (ItemDTO item : rawItemDTOs) {
				if (null!=item.getRequestType() && "LAYAWAYF".equalsIgnoreCase(item.getRequestType())) {
					itemDTOs.add(item);
				}
			}
			//STORESYS-23914 Fix Ends
			for(ItemDTO item : itemDTOs) {
				ItemPrintVO itemPrintVO = new ItemPrintVO();

				itemPrintVO.setDiv(item.getDivNum());
				itemPrintVO.setItemNo(item.getItem());
				itemPrintVO.setSku(item.getSku());
				itemPrintVO.setPlus4(item.getPlus4());
				if(StringUtils.hasText(item.getThumbnailDesc()) && item.getThumbnailDesc().length()>30){
					itemPrintVO.setItemDesc(item.getThumbnailDesc().substring(0,30));	
				}else{
					itemPrintVO.setItemDesc(item.getThumbnailDesc());
				}
				itemPrintVO.setActualQty(item.getQty());

				listOfItems.add(itemPrintVO);
			}
			mpuItemVO.setListOfItems(listOfItems);										//Setting list of items

			CustomerDTO custDTO = mpuWebServiceDAOImpl.getCustomerDataForPrint(storeNum, requestId); 
			SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			/*
			 * Customer Info
			 */
			if(null!=custDTO){
				mpuItemVO.setFirstName(custDTO.getFirstName());
				mpuItemVO.setLastName(custDTO.getLastName());

				/*
				 * For defect
				 * STORESYS-23435
				 */
				if(null!=custDTO.getPhoneNumber() && custDTO.getPhoneNumber().length()>=10){
					mpuItemVO.setAreaCode(custDTO.getPhoneNumber().substring(0, 3));
					mpuItemVO.setPhoneNumber(custDTO.getPhoneNumber().substring(3, 10));
				}

				if(null!=custDTO.getAltPhoneNumber() && custDTO.getAltPhoneNumber().length()>=10){
					mpuItemVO.setAltAreaCode(custDTO.getAltPhoneNumber().substring(0, 3));
					mpuItemVO.setAltPhoneNumber(custDTO.getAltPhoneNumber().substring(3, 10));
				}
			}

			mpuItemVO.setAssociateNumber(user);
			mpuItemVO.setRequestId(requestId);
			mpuItemVO.setItemCount(Integer.valueOf(itemDTOs.size()));
			mpuItemVO.setItemlocation(!itemDTOs.isEmpty() && itemDTOs.get(0)!=null ? itemDTOs.get(0).getFromLocation() : "");
			mpuItemVO.setDestination(!itemDTOs.isEmpty() && itemDTOs.get(0)!=null ? itemDTOs.get(0).getToLocation() : "");
			mpuItemVO.setTransactiontype(!itemDTOs.isEmpty() && itemDTOs.get(0)!=null ? itemDTOs.get(0).getItemTransactionType() : "");
			mpuItemVO.setOrigin(!itemDTOs.isEmpty() && itemDTOs.get(0)!=null ? itemDTOs.get(0).getItemSaleOrigin() : "");	
			mpuItemVO.setOrderId(!itemDTOs.isEmpty() && itemDTOs.get(0)!=null ? itemDTOs.get(0).getSalescheck() : "");
			mpuItemVO.setSaleType(!itemDTOs.isEmpty() && itemDTOs.get(0)!=null ? itemDTOs.get(0).getSaleType() : "");
			mpuItemVO.setStoreNumber(storeNum);

			Date escalationTime=date.parse(!itemDTOs.isEmpty() && itemDTOs.get(0)!=null ? itemDTOs.get(0).getEscalationTime() : "");
			Date timeRecieved = Calendar.getInstance().getTime();
			mpuItemVO.setTimeRecieved(timeRecieved);
			mpuItemVO.setExpirationTime(escalationTime);

			/*
			 * Delivery type code is set to print the item Description
			 * For time being the delivery type code is set to transaction type
			 * For Layaway orders the transaction type is J
			 */
			mpuItemVO.setDeliveryType("J");

			//				String url="http://hfdvsbointjbos1.vm.itg.corp.us.shldcorp.com:8180/DEJInterfaces/v1/print/"+printerId+"/"+storeNum+"?type="+type;
			String url = PropertyUtils.getProperty("PrintService_url")+printerId+"/"+storeNum+"?type="+type;

			//logger.debug("the url for printPackageLabel is as :",url);
			mpuItemVO.setItemIndex(0);
			mpuItemVO.setNotifyCount(String.valueOf(1));

			PrintServiceResponse printServResp = mpuWebServiceDAOImpl.printLabel(mpuItemVO,url);
			response.add(printServResp.getWsResponeMeta().getErrorCode());
		} catch (ParseException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
		logger.info("MPUWebServiceProcessorImpl:printPackageLabel", "->Exiting");
		return response;
	}


	public boolean checkOrderExpired(String storeNo,String rqtId) throws DJException{
		logger.info("entering MPUWebServiceProcessorImpl.checkOrderExpired", "store=="+storeNo+"rqtId=="+rqtId);
		try {
			Map<String,Object> map = mpuWebServiceDAOImpl.checkExpiredOrder(rqtId, storeNo);
			if(map!=null &&  StringUtils.hasText((String)map.get("requestNumber"))){
				if(((String)map.get("requestNumber")).startsWith("09300")||((String)map.get("requestNumber")).startsWith("09322")){
					Date currentTime=Calendar.getInstance().getTime();
					SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date escalTime=date.parse((String)map.get("escTime"));
					
					if(MpuWebConstants.MPU_DIRECT.equalsIgnoreCase((String)map.get("created_by"))){
						HashMap<String,Object> storeInfo=(HashMap<String, Object>)mpuWebServiceDAOImpl.getStoreDetails(storeNo);
						int escalation_time_val=Integer.parseInt((String)(storeInfo.get(MpuWebConstants.ESCALATION_TIME)));
						escalTime = new Date(escalTime.getTime() + escalation_time_val*60*1000);
						//logger.info("=checkOrderExpireds==escalTime==", escalTime);
					}
					
					return escalTime.before(currentTime);
				}else{
					return false;
				}
			}else{
				return true;
			}
		} catch (ParseException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
	}

	private void addLocker(ItemDTO itemDTO) throws DJException{
		logger.info("netering MPUWebServiceProcessorImpl.addLocker", "itemDTO");
		if(itemDTO.getIsLockerValid().equalsIgnoreCase("Y")){
			//	if(itemDTO.getToLocation().startsWith("L") && itemDTO.getToLocation().length()>1 && itemDTO.getToLocation().length()<4){
			if(StringUtils.hasText(itemDTO.getLockerLoc())) {

				LockerDTO lockerDTO = new LockerDTO();
				lockerDTO.setReferenceId(itemDTO.getRqdId());
				lockerDTO.setStoreNo(itemDTO.getStoreNumber());
				lockerDTO.setSalescheckNo(itemDTO.getSalescheck());
				lockerDTO.setLockerNo(itemDTO.getLockerLoc());
				lockerDTO.setStatus(MpuWebConstants.BINNED);
				lockerDTO.setCustomerName(itemDTO.getFullName());
				lockerDTO.setCreatedBy(itemDTO.getAssignedUser());
				lockerDTO.setTransactionDate(itemDTO.getItemCreatedDate());
				lockerDTO.setUpdatedBy(itemDTO.getAssignedUser());
				lockerServiceProcessor.addLocker(lockerDTO);

				logger.info("exiting MPUWebServiceProcessorImpl.addLocker", "locker added");	
			}
		}

	}

	public Map<String,String> selectQueuestats(String storeno, String kiosk) throws DJException {
		boolean holdGoFlag = false;
		holdGoFlag = getHoldGoflag(storeno, kiosk);
		//				List<ItemDTO> mpuList = getAllItemList(storeno, "MPU", kiosk); 
		List<ItemDTO> webList = getAllItemList(storeno, "WEB", kiosk,"N","");
		int stageList = getFilteredListForLayaway(getAllItemList(storeno, "STAGE", kiosk,"N",""));
		Map<String,String> queStatMap = new HashMap<String,String>();
		//List<OrderDTO> mpuList = pickUpServiceDAO.fetchOderForAssociate(storeno, kiosk);
		List<RequestDTO> mpuList = pickUpServiceProcessor.fetchOderForAssociate(storeno, kiosk, "ALL");
		int count = pickUpServiceProcessor.getFilteredEI5List(mpuList);
		queStatMap.put("WEB", (String) (null!=webList?webList.size()+"":"0"));
		queStatMap.put("STAGE",  String.valueOf(stageList));
		queStatMap.put("MPU", (String) (null!=mpuList?(mpuList.size() - count)+"":"0"));
		if(holdGoFlag){
//		int size=webList.size();
		List<ItemDTO> hgList = getAllItemList(storeno, "H&G", kiosk,"N","");
		int totalSize=(null!=webList?webList.size():0)+(null!=hgList?hgList.size():0);
			queStatMap.put("WEB", totalSize+"");
		}
		queStatMap.put("holdGoFlag", holdGoFlag+"");
		return queStatMap ;
	}

	/**service to handle the void/Cancel request received from the NPOS
	 * @param requestNumber
	 * @param storeNumber
	 * @param requestDTO
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean updateVoidRequest(String requestNumber,String store,RequestDTO requestDTO) throws DJException{
		logger.info("entering updateVoidRequest", "requestNumber"+requestNumber+"store"+store);
		boolean result=false;
		String action="";
		String requestType="";
		String rqtId="";
		String currentOrderStatus="";
		if(null!= requestDTO && null!=requestDTO.getOrder()){
			logger.info("original json after cancel", requestDTO.getOrder().getOriginalJson());
			logger.info("order is ", requestDTO.getOrder());
		}
		Map<String,Object> requestDetails= mpuWebServiceDAOImpl.getRequestIdbySalescheck(store, requestNumber);
		if(null!=requestDetails){
			rqtId=requestDetails.get("rqt_id").toString();
			currentOrderStatus=(String)requestDetails.get("request_status");
			requestType=requestDetails.get("request_type").toString();
			logger.info("rqtId:: ", rqtId +"::currentOrderStatus:: "+ currentOrderStatus +":::requestType:: "+ requestType  );
		}
		if(StringUtils.hasText(rqtId)){
			List<ItemDTO> itemList= mpuWebServiceDAOImpl.getItemListForOrder(store, rqtId);
			
			/**Check whether the request type is CANCEL(incase of cancel of stage orders from NPOS)
			 * or the request type is BINWEB and status is CANCELLED (incase of cancel from OBU)
			 */
			if((null!=requestDTO.getOrder().getRequestType() && requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.CANCEL))
					|| (requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB) &&
							requestDTO.getOrder().getRequestStatus().equalsIgnoreCase(MpuWebConstants.CANCELLED))){
				logger.info("Check whether the request type is CANCEL or the request type is BINWEB and status is CANCELLED","");
				for(int i=0;i<requestDTO.getItemList().size();i++){
					ItemDTO dto=requestDTO.getItemList().get(i);
					action=MpuWebConstants.CANCEL;
					String dtoDivision=dto.getDivNum()+dto.getItem()+dto.getSku();
					//Adding sequence number to the identifiers to make sure that correct item is cancelled
					if(null!=dto.getItemSeq()){
						dtoDivision = dtoDivision+"-"+dto.getItemSeq();
					}

					for(ItemDTO itemDTO : itemList){
						requestType=itemDTO.getRequestType();
						logger.info("Check itemDTO "+itemDTO +"::getRequestType::"+itemDTO.getRequestType(),"");
						String itemDivision=itemDTO.getDivNum()+itemDTO.getItem()+itemDTO.getSku();
/*						if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.LAYAWAYF)||itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.LAYAWAYS)){
							boolean layawayfCompleted = false;
							if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.LAYAWAYF) && !layawayfCompleted){
								getItemCancelled(itemDTO,dto);
								layawayfCompleted = true;
							}else if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.LAYAWAYS)){
								getItemCancelled(itemDTO,dto);
							}
						}
						*///else {
						//Adding sequence number to the identifiers to make sure that correct item is cancelled
						if(null!=itemDTO.getItemSeq()){
							itemDivision=itemDivision+"-"+itemDTO.getItemSeq();
						}
						if(itemDivision.equalsIgnoreCase(dtoDivision)){
							if(getItemCancelled(itemDTO,dto)){
								break;	
							}

						}
					//	}
					}
				}
			}
			// to VOID the order as a whole
			else if(null!=requestDTO.getOrder().getRequestType() && requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.VOID)){
				action=MpuWebConstants.VOID;
				for(ItemDTO itemDTO : itemList){
					requestType=itemDTO.getRequestType();
					itemDTO.setActionId(MpuWebConstants.VOID);
					String nextStatus=(String)mpuWebServiceDAOImpl.getNextAction(itemDTO.getRequestType(),itemDTO.getItemStatus(),itemDTO.getActionId() ,
							MpuWebConstants.STATUS, itemDTO.getStoreNumber());
					String desc=(String)mpuWebServiceDAOImpl.getNextAction(itemDTO.getRequestType(),itemDTO.getItemStatus(),itemDTO.getActionId() ,
							MpuWebConstants.ACTIVITY, itemDTO.getStoreNumber());
					String seqNo=(String)mpuWebServiceDAOImpl.getNextAction(itemDTO.getRequestType(),itemDTO.getItemStatus(),itemDTO.getActionId() ,
							MpuWebConstants.SEQUENCE, itemDTO.getStoreNumber());

					if(itemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.BINNED) || itemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.STAGED_STATUS)){
						String fromLoc=itemDTO.getFromLocation();
						String toLoc=itemDTO.getToLocation();
						itemDTO.setFromLocation(toLoc);
						itemDTO.setToLocation(fromLoc);
						itemDTO.setItemCreatedDate(getCurrentTime());
						itemDTO.setAssignedUser(" ");
					}
					mpuWebServiceDAOImpl.updateItemDetails(requestNumber, itemDTO, nextStatus);
					itemDTO.setItemStatus(nextStatus);
					if(nextStatus.equalsIgnoreCase(MpuWebConstants.RSTK_PEND_VOID)){
						itemDTO.setVersion(itemDTO.getVersion()+1);
						updateToEhCache(itemDTO,false);	
					}else{
						updateToEhCache(itemDTO,true);
					}
					insertActivity(itemDTO,desc,seqNo);
				}
			}

			boolean complete=false;

			if(MpuWebConstants.VOID.equalsIgnoreCase(action)){
				complete = mpuWebServiceDAOImpl.checkRequestVoid(store,rqtId,requestType);				
			}else if(MpuWebConstants.CANCEL.equalsIgnoreCase(action)){
				complete = mpuWebServiceDAOImpl.checkRequestCancel(store,rqtId,requestType);
			}				

			List<String> rqtList=new ArrayList<String>();
			rqtList.add(rqtId);
			logger.info("Check whether order completed action::"+action +"::store::"+store+"::rqtList::"+rqtList+"::currentOrderStatus::"+currentOrderStatus ,"");
			if(complete){
				if(MpuWebConstants.VOID.equalsIgnoreCase(action)){
					mpuWebServiceDAOImpl.updateOrder(store,rqtList,currentOrderStatus, MpuWebConstants.VOIDED,null,false);
					result=true;
				}else if(MpuWebConstants.CANCEL.equalsIgnoreCase(action)){
					mpuWebServiceDAOImpl.updateOrder(store,rqtList,currentOrderStatus, MpuWebConstants.CANCELLED,null,false);
					sendWebResponseCncl(store,rqtId);
					result=true;
				}	
			}else{
				if(mpuWebServiceDAOImpl.checkRequestComplete(store,rqtId,requestType)){
					mpuWebServiceDAOImpl.updateOrder(store,rqtList,currentOrderStatus, MpuWebConstants.COMPLETED,null,false);
				}else{
					mpuWebServiceDAOImpl.updateOrder(store,rqtList,currentOrderStatus, MpuWebConstants.OPEN,null,false);
				}
				result=true;
			}
			
			
			EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
			String tempReqType;
			if(MpuWebConstants.LAYAWAY.equalsIgnoreCase(requestType)){
				tempReqType = requestType+"S";
			}else{
				tempReqType = requestType;
			}
			/******* changes done to insert data in cache for LAYAWAY case  **/
			Map<String, String> requestTypeQueueMap = getRequestQueueMap(store);
			String cacheRefreshKey = MpuWebConstants.CACHE_REFRESH_FLAG+"-"+org.apache.commons.lang3.StringUtils.leftPad(store, 5, '0')+"-"+requestTypeQueueMap.get(tempReqType);
			requestQueueCache.evict(cacheRefreshKey);

			return result;
		}else{
			return false;
		}
}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private synchronized Integer getFilteredListForLayaway(List<ItemDTO> allHgItems) {
		List<ItemDTO> itemDTOLayaway = new ArrayList<ItemDTO>();
		List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
		if(null!=allHgItems){
			for(ItemDTO itemDTO : allHgItems){
				if("LAYAWAYF".equalsIgnoreCase(itemDTO.getRequestType())){
					boolean flag = false;
					for(ItemDTO object : itemDTOLayaway){
						if(object.getRqtId().equalsIgnoreCase(itemDTO.getRqtId())){
							flag = true;
							break;
						}
					}
					if(!flag){
						itemDTOLayaway.add(itemDTO);
					}
				}else{
					itemDTOs.add(itemDTO);
				}
			}
		}
		return itemDTOs.size() + itemDTOLayaway.size();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private boolean getItemCancelled(ItemDTO origItem,ItemDTO cancldItem) throws DJException{
		boolean result=false;
		origItem.setActionId(MpuWebConstants.CANCEL);
		String nextStatus=(String)mpuWebServiceDAOImpl.getNextAction(origItem.getRequestType(),origItem.getItemStatus(),origItem.getActionId() ,
				MpuWebConstants.STATUS, origItem.getStoreNumber());
		String desc=(String)mpuWebServiceDAOImpl.getNextAction(origItem.getRequestType(),origItem.getItemStatus(),origItem.getActionId() ,
				MpuWebConstants.ACTIVITY, origItem.getStoreNumber());
		String seqNo=(String)mpuWebServiceDAOImpl.getNextAction(origItem.getRequestType(),origItem.getItemStatus(),origItem.getActionId() ,
				MpuWebConstants.SEQUENCE, origItem.getStoreNumber());
		int origQty=Integer.parseInt(origItem.getQty());
		int cnclQty=Integer.parseInt(cancldItem.getQty());

		//to cancel the items that are not BINNED/STAGED/Cancelled 
		if(!(origItem.getItemStatus().equalsIgnoreCase(MpuWebConstants.BINNED) || 
				origItem.getItemStatus().equalsIgnoreCase(MpuWebConstants.STAGED_STATUS) ||
				origItem.getItemStatus().equalsIgnoreCase(MpuWebConstants.CANCELLED) || 
				(MpuWebConstants.BINPENDING.equalsIgnoreCase(origItem.getItemStatus()) && MpuWebConstants.LAYAWAYF.equalsIgnoreCase(origItem.getRequestType())))){

			if(origQty==cnclQty){
				origItem.setQty("0");
				origItem.setActionId(MpuWebConstants.CANCEL);
				String fromLoc=origItem.getFromLocation();
				String toLoc=origItem.getToLocation();
				origItem.setFromLocation(toLoc);
				origItem.setToLocation(fromLoc);
				mpuWebServiceDAOImpl.updateItemDetails(origItem.getSalescheck(), origItem, nextStatus);
				origItem.setItemStatus(nextStatus);
				updateToEhCache(origItem,true);
				insertActivity(origItem,desc,seqNo);
				result=true;
			}else if(origQty > cnclQty){
				int finalQty=origQty-cnclQty;
				String qty=String.valueOf(finalQty);
				mpuWebServiceDAOImpl.updateItemQty(origItem.getStoreNumber(),origItem.getRqdId(),qty,origItem.getItemStatus(),origItem.getFromLocation(),origItem.getToLocation(),origItem.getItemCreatedDate(),origItem.getAssignedUser());
				origItem.setQty(qty);
				origItem.setItemQuantityRequested(String.valueOf(origQty));
				origItem.setItemQuantityActual(String.valueOf(cnclQty));
				updateToEhCache(origItem,false);
				insertActivity(origItem,desc,seqNo);
				result=true;
			}
		}

		//to cancel the item that is already in BINNED/STAGED status
		else if(origItem.getItemStatus().equalsIgnoreCase(MpuWebConstants.BINNED) || 
				origItem.getItemStatus().equalsIgnoreCase(MpuWebConstants.STAGED_STATUS) ||
				(MpuWebConstants.BINPENDING.equalsIgnoreCase(origItem.getItemStatus()) && MpuWebConstants.LAYAWAYF.equalsIgnoreCase(origItem.getRequestType()))){
			if(origQty==cnclQty){
				origItem.setActionId(MpuWebConstants.CANCEL);
				String fromLoc=origItem.getFromLocation();
				String toLoc=origItem.getToLocation();
				if(! MpuWebConstants.LAYAWAYF.equalsIgnoreCase(origItem.getRequestType())){
					origItem.setFromLocation(toLoc);
					origItem.setToLocation(fromLoc);
				}
				origItem.setItemCreatedDate(getCurrentTime());
				origItem.setAssignedUser(" ");
				mpuWebServiceDAOImpl.updateItemDetails(origItem.getSalescheck(), origItem, nextStatus);
				origItem.setItemStatus(nextStatus);
				origItem.setVersion(origItem.getVersion()+1);
				updateToEhCache(origItem,false);
				insertActivity(origItem,desc,seqNo);
				result=true;
			}else if(origQty > cnclQty){
				int finalQty=origQty-cnclQty;
				String qty=String.valueOf(finalQty);
				mpuWebServiceDAOImpl.updateItemQty(origItem.getStoreNumber(),origItem.getRqdId(),qty,nextStatus,origItem.getToLocation(),origItem.getFromLocation(),getCurrentTime()," ");
				origItem.setQty(qty);
				origItem.setItemQuantityRequested(String.valueOf(origQty));
				origItem.setItemQuantityActual(String.valueOf(cnclQty));
				origItem.setItemStatus(nextStatus);
				updateToEhCache(origItem,false);
				insertActivity(origItem,desc,seqNo);
				
				result=true;
			}
		}
		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean updateRequestForPickUp(RequestDTO requestDTO) throws DJException{
		logger.info("entering MPUWebServiceProcessorImpl.updateRequestForPickUp", "requestDTO");
		boolean matchFlag;
		boolean itemupdated=false;
		String requestType="";
		String store="";
		String rqtId="";
		String currentOrderStatus="";
		if(null!=requestDTO && null!=requestDTO.getOrder()){
			store=requestDTO.getOrder().getStoreNumber();
			Map<String,Object> getOrderForSales=mpuWebServiceDAOImpl.getRequestIdbySalescheck(store,
					requestDTO.getOrder().getSalescheck());
			if(null!=getOrderForSales){
				rqtId= getOrderForSales.get("rqt_id").toString();
				currentOrderStatus=getOrderForSales.get("request_status").toString();
				requestType=getOrderForSales.get("request_type").toString();
				List<ItemDTO> itemList= mpuWebServiceDAOImpl.getItemListForOrder(store, rqtId);

				if(null!=requestDTO.getItemList() && requestDTO.getItemList().size()>0){
					for(ItemDTO itemDTO : requestDTO.getItemList()){
						String itemDivItemSku=itemDTO.getDivNum()+itemDTO.getItem()+itemDTO.getSku();
						String itemUPC=itemDTO.getUpc();
						String itemksn=itemDTO.getKsn();
						int noOfRows;
						for(ItemDTO existingItemDTO : itemList){
							matchFlag=false;
							String divItemSku=existingItemDTO.getDivNum()+existingItemDTO.getItem()+existingItemDTO.getSku();
							String UPC=existingItemDTO.getUpc();
							String ksn=existingItemDTO.getKsn();
							requestType=existingItemDTO.getRequestType();
							if(StringUtils.hasText(itemDivItemSku) && StringUtils.hasText(divItemSku) && itemDivItemSku.equalsIgnoreCase(divItemSku)){
								matchFlag=true;
							}
							else if(StringUtils.hasText(itemUPC) && StringUtils.hasText(UPC) && itemUPC.equalsIgnoreCase(UPC)){
								matchFlag=true;
							}
							else if(StringUtils.hasText(itemksn) && StringUtils.hasText(ksn) && itemksn.equalsIgnoreCase(ksn)){
								matchFlag=true;
							}
							if(matchFlag){

								if(requestType.equalsIgnoreCase(MpuWebConstants.STAGE)){
									noOfRows = mpuWebServiceDAOImpl.updateItemDetails(existingItemDTO.getSalescheck(), existingItemDTO, MpuWebConstants.STAGED_STATUS);
								//	logger.debug("No of Rows Updated :",noOfRows);
									if(noOfRows>0)
										itemupdated=true;
								}
								else{
									noOfRows = mpuWebServiceDAOImpl.updateItemDetails(existingItemDTO.getSalescheck(), existingItemDTO, MpuWebConstants.BINNED);
								//	logger.debug("No of Rows Updated :",noOfRows);
									if(noOfRows>0)
										itemupdated=true;
								}
								// Changes for Issue 24049
								if(!"FLR".equalsIgnoreCase(existingItemDTO.getFromLocation()) 
										&& !existingItemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.BINNED) && !existingItemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.STAGED_STATUS)){
									mpuWebServiceDAOImpl.decrementItemStock(existingItemDTO);
								}
								//logger.debug("Inserting Activity for Pickup","");
								insertActivity(existingItemDTO,MpuWebConstants.PICK_UP_STATUS,MpuWebConstants.PICK_UP_SEQ);
								updateToEhCache(existingItemDTO, true);
								break;
							}

						}
					}
				}
			}
		}
		if(itemupdated){
			boolean complete = mpuWebServiceDAOImpl.checkRequestComplete(store,rqtId,requestType);
			if(complete==true){
				List<String> rqtList=new ArrayList<String>();
				rqtList.add(rqtId);
				mpuWebServiceDAOImpl.updateOrder(store,rqtList,currentOrderStatus, MpuWebConstants.COMPLETED,null,false);
			}
		}
		logger.info("exiting MPUWebServiceProcessorImpl.updateRequestForPickUp", "pick up initiated");
		return itemupdated;
	}
	private void activeUserModNotification(ItemDTO itemDTO){
		/*
		 * IsUserActive returns true if there are/is active users/user or Mod notification is 
		 * not allowed for that store
		 * This return false if there is no active users and mod notification is allowed
		 * for that store
		 */

		boolean isUserActive;
		try {
			isUserActive = mpuWebServiceDAOImpl.isUserActive(itemDTO);
			logger.info("\n\n===isUserActive==",isUserActive);
			if(!isUserActive){
				modNotificationProcessorImpl.sendMODNotification(itemDTO, 9);
			}else{
				logger.debug("activeUserModNotification : ","Mod Notification not required");
			}
		} catch (DJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public List<Object[]> getCOMExceptionList(String store,String date,String status) throws DJException{
		return mpuWebServiceDAOImpl.getCOMExceptionList(store, date,status);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean updateRequestForCOM(HashMap<String, String> reqInfo,String user,String store)
			throws DJException {
		logger.debug("entering MPUWebServiceProcessorImpl.updateRequestForCOM", "reqInfo=="+reqInfo.toString()+"user=="+user+"store=="+store);
		String rqtId = reqInfo.get("rqtId").toString();
		String rqdId = reqInfo.get("rqdId").toString();
		String[] rqtIds = rqtId.split(",");
		String[] rqdIds = rqdId.split(",");
		int rows = 0;
		rows = mpuWebServiceDAOImpl.updateItemDetailForCOM(rqdIds,user,store);
		if(rows > 0){
			rows += mpuWebServiceDAOImpl.updateOrderDetailForCOM(rqtIds,store);	
		}
		if(rows > 0){
			logger.debug("exiting MPUWebServiceProcessorImpl.updateRequestForCOM", true);
			return true;
		}else{
			logger.debug("exiting MPUWebServiceProcessorImpl.updateRequestForCOM",false);
			return false;
		}

	}

	public int getItemQty(String store,String itemId)throws DJException{
		//logger.debug("entering MPUWebServiceProcessorImpl.getItemQty", "store=="+store+"itemId=="+itemId);
		return mpuWebServiceDAOImpl.getItemQty(store,itemId);
	}


	private void insertActivity(ItemDTO itemDTO,String desc,String seqNo) throws DJException{
		List<ActivityDTO> activityDTOs=new ArrayList<ActivityDTO>(); 	
		List<String> rqdIDs = new ArrayList<String>();

		rqdIDs.add(itemDTO.getRqdId());
		ActivityDTO activityDTO = new ActivityDTO();
		if(StringUtils.hasText(itemDTO.getFromLocation())){
			activityDTO.setFromLocation(itemDTO.getFromLocation());
		}
		if(StringUtils.hasText(itemDTO.getToLocation())){
			activityDTO.setToLocation(itemDTO.getToLocation());
		}
		if(StringUtils.hasText(itemDTO.getAssignedUser())){
			activityDTO.setAssignedUser(itemDTO.getAssignedUser());
		}

		activityDTO.setActivityDescription(desc);
		activityDTO.setActionSeq(seqNo) ;
		activityDTO.setType(itemDTO.getRequestType());
		activityDTO.setStore(itemDTO.getStoreNumber());
		activityDTO.setCreatedBy(itemDTO.getCreatedBy());
		activityDTOs.add(activityDTO);

		mpuWebServiceDAOImpl.createActivity(activityDTOs,Integer.parseInt(itemDTO.getRqtId()), rqdIDs.toArray(),itemDTO.getStoreNumber());

	}

	private String getCurrentTime(){
		Date currentTime=Calendar.getInstance().getTime();
		Timestamp ts_now = new Timestamp(currentTime.getTime());
		return ts_now.toString();

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)		
	public long cancelExpireRequest(RequestDTO requestDTO,String store) throws DJException{
		logger.info("INSIDE CANCEL EXPIRE HG REQUEST status:","");
		List<String> statusListReq = new ArrayList<String>();
		String requestNumber = requestDTO.getOrder().getRequestNumber();
		long rqt_id=0;
		statusListReq.add("OPEN");
		statusListReq.add("WIP");
		statusListReq.add("COMPLETED");
		
		rqt_id = mpuWebServiceDAOImpl.getOpenRequestId(store,requestNumber,statusListReq);
		if(rqt_id>0){
			mpuWebServiceDAOImpl.cancelExpireRequestItems(store,requestNumber,MpuWebConstants.EXPIRED);
			mpuWebServiceDAOImpl.cancelExpireRequest(store,rqt_id,MpuWebConstants.EXPIRED);	
			
		}
		return rqt_id;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)	
	public boolean cancelItems(RequestDTO requestDTO) throws DJException{
		logger.debug("Entering MPUWebServiceProcessorImpl.cancelItems",requestDTO);
		try {
			List<String> reqStatusList = Arrays.asList("OPEN","WIP","COMPLETED");
			long existingActive_rqt_id = 0;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			boolean isOrderCncRequired = true;
			existingActive_rqt_id = mpuWebServiceDAOImpl.getOpenRequestId(requestDTO.getOrder().getStoreNumber(),requestDTO.getOrder().getRequestNumber(),reqStatusList);
			if(existingActive_rqt_id >= 0){
				List<ItemDTO> itemList = requestDTO.getItemList();
				Map<String,String> cancelRestockItemsMap = new HashMap<String,String>();
				/*
				 * Making requeted cancelRestockItemMap taking items from the
				 * Request with CANCEL or EDIT as status
				 */
				if(null!=itemList && !itemList.isEmpty()) {
					for(ItemDTO item : itemList) {
						if(MpuWebConstants.CNR.equalsIgnoreCase(item.getItemStatus())
								||MpuWebConstants.EDIT.equalsIgnoreCase(item.getItemStatus())) {
							cancelRestockItemsMap.put(item.getDivNum()+"-"+item.getItem()+"-"+item.getSku()+"-"+item.getUpc()+"-"+item.getItemSeq(), item.getItemStatus()); 
						}
					}
				}
				/*
				 * Making items map from database against the
				 * requested order which contains cancel or edit or both items 
				 */
				Map<String,String> activeItemsMap = new HashMap<String, String>();
				List<ItemDTO> itemListOrig= mpuWebServiceDAOImpl.getItemListForOrder(requestDTO.getOrder().getStoreNumber(),String.valueOf(existingActive_rqt_id));
				if(null!=itemListOrig && !itemListOrig.isEmpty()) {
					for(ItemDTO item : itemListOrig) {
						if(!MpuWebConstants.CANCELLED.equalsIgnoreCase(item.getItemStatus())) {
							activeItemsMap.put(item.getDivNum()+"-"+item.getItem()+"-"+item.getSku()+"-"+item.getUpc()+"-"+item.getItemSeq(), item.getRqdId());
							activeItemsMap.put(item.getRqdId(), mapper.writeValueAsString(item));
						}
					}
				}
				/*
				 * cancelRestockItemsList-List of RqdId of items to be cancelled
				 */
				List<String> cancelRestockItemsList = new ArrayList<String>();
				for(String cancelRestockItemKey : cancelRestockItemsMap.keySet()){
					if(null!=activeItemsMap && activeItemsMap.containsKey(cancelRestockItemKey)){
						cancelRestockItemsList.add(activeItemsMap.get(cancelRestockItemKey));
						/*
						 * Inserting record in request activity table
						 */
						ItemDTO cancItemDTO = mapper.readValue(activeItemsMap.get(activeItemsMap.get(cancelRestockItemKey)), ItemDTO.class);
						if(MpuWebConstants.AVAILABLE.equalsIgnoreCase(cancItemDTO.getItemStatus())){
							isOrderCncRequired = false;
						}
						if(null!=activeItemsMap.get(cancelRestockItemKey)){
							String nextStatus=(String)mpuWebServiceDAOImpl.getNextAction(cancItemDTO.getRequestType(),cancItemDTO.getItemStatus(),cancItemDTO.getActionId() ,
									MpuWebConstants.STATUS, cancItemDTO.getStoreNumber());
							String desc=(String)mpuWebServiceDAOImpl.getNextAction(cancItemDTO.getRequestType(),cancItemDTO.getItemStatus(),MpuWebConstants.CANCEL_REQUEST ,
									MpuWebConstants.ACTIVITY, cancItemDTO.getStoreNumber());
							String seqNo=(String)mpuWebServiceDAOImpl.getNextAction(cancItemDTO.getRequestType(),cancItemDTO.getItemStatus(),MpuWebConstants.CANCEL_REQUEST ,
									MpuWebConstants.SEQUENCE, cancItemDTO.getStoreNumber());
							if(cancItemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.AVAILABLE)){
								String fromLoc=cancItemDTO.getFromLocation();
								String toLoc=cancItemDTO.getToLocation();
								cancItemDTO.setFromLocation(toLoc);
								cancItemDTO.setToLocation(fromLoc);
								cancItemDTO.setItemStatus(nextStatus);
								cancItemDTO.setEscalation("0");
								SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date currentDate = Calendar.getInstance().getTime();
								String escalationTime = date.format(currentDate);
								cancItemDTO.setCreateTime(escalationTime);
								cancItemDTO.setEscalationTime(escalationTime);
								cancItemDTO.setAssignedUser(null);
								updateToEhCache(cancItemDTO,false);
							}else{
								updateToEhCache(cancItemDTO,true);
							}
							insertActivity(cancItemDTO,desc,seqNo);
						}
					}
				}	
				mpuWebServiceDAOImpl.cancelRestockItemsOfRequest(requestDTO.getOrder().getStoreNumber(),cancelRestockItemsList);
				
				// order will be marked as cancelled once all the items status change to cancelled.
				if(isOrderCncRequired){
					if(null!=cancelRestockItemsList && !cancelRestockItemsList.isEmpty() && null!=activeItemsMap && !activeItemsMap.isEmpty()
							&& cancelRestockItemsList.size() == (activeItemsMap.size()/2)){
						mpuWebServiceDAOImpl.cancelExpireRequest(requestDTO.getOrder().getStoreNumber(),existingActive_rqt_id,MpuWebConstants.CANCELLED);
					}
				}else{
					mpuWebServiceDAOImpl.updateOrder(requestDTO.getOrder().getStoreNumber(),
							Arrays.asList(String.valueOf(existingActive_rqt_id)),MpuWebConstants.COMPLETED, MpuWebConstants.WIP,null,false);
					
				}
			}
		} catch (JsonGenerationException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		} catch (JsonMappingException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		} catch (IOException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
		logger.info("Exiting MPUWebServiceProcessorImpl.cancelItems","");
		return true;
	}


	public List<String> printLockerTicket(String storeNum, 
			String lockerPin, String customerName, String kiosk) throws DJException {
		logger.info("Entering in printLockerTicket to print locker ticket for lockerPin:", lockerPin +" customer:"+customerName);
		List<String> response = new ArrayList<String>();
		String status = null;
		if (isSBOPrinterEnabled(storeNum,kiosk)){
			status =  printLockerTicketBySBO(storeNum,lockerPin,customerName);
			if (null == status || !"Success".equalsIgnoreCase(status)) {
				logger.error("Locker Ticket printing by SBO failed", status);
				throw new DJException("Locker Ticket printing by SBO failed");
			}
		} else {		
			status = printLockerTicketByAdaptor(storeNum,lockerPin,customerName,kiosk);
			if (null == status || !"200".equalsIgnoreCase(status)) {
				logger.error("Locker Ticket printing by Adaptor failed", status);
				throw new DJException("Locker Ticket printing by Adaptor failed");
			}
		}				
		response.add(status);
		return response;
	}

	public String printLockerTicketByAdaptor(String storeNo, String pinNo, String customerName,String kiosk) throws DJException {
		String serverURL = null;
		OrderAdaptorRequest request=new OrderAdaptorRequest();
		request.setRequestType(pinNo);
		request.setRequestvalue(customerName);
		Order order = new Order();
		order.setKioskName(kiosk);
		request.setRequestOrder(order);

		String dnsName = MPUWebServiceUtil.getDNSForStore(storeNo,PropertyUtils.getProperty("Sears.StoreFormat"));			
		serverURL = dnsName+"/printLockerTicket";
		return mpuWebServiceDAOImpl.printLockerTicketByAdaptor(request, serverURL);
	}

	public String printLockerTicketBySBO(String storeNum, String lockerPin, String customerName) throws DJException {
		LockerKioskPrintVO lockerKioskPrintVO = new LockerKioskPrintVO();
		String printType = "locker";
		String[] customer = customerName.split(" ");
		if(customer.length == 1){
			lockerKioskPrintVO.setFirstName(customer[0]);
			lockerKioskPrintVO.setLastName("");
		}else if(customer.length > 1){
			lockerKioskPrintVO.setFirstName(customer[0]);
			lockerKioskPrintVO.setLastName(customer[1]);
		}
		lockerKioskPrintVO.setPinNumber(lockerPin);

		//String url="http://hfdvsbointjbos1.vm.itg.corp.us.shldcorp.com:8180/DEJInterfaces/v1/print/lockerTicket/"+storeNum+"/"+printType;
		String url = PropertyUtils.getProperty("LockerPrintService_url")+storeNum+"/"+printType;

		logger.info("the url for printLockerTicketBySBO is as :",url);
		PrintServiceResponse printServResp = mpuWebServiceDAOImpl.printLockerTicket(lockerKioskPrintVO,url);
		return printServResp.getWsResponeMeta().getErrorCode();
	}

	/**
	 * This Method print the Customer care coupons
	 * 
	 */
	public String printCustomerCareCoupon(String customerName,String storeNum,String totalTimeElapsed,
			String couponType,String lang, String kiosk)
					throws DJException{
		logger.info("MPUWebServiceProcessorImpl:printCustomerCareCoupon::","Entering->\n");				
		String status="Failure";
		//CustomerDTO custDTO = mpuWebServiceDAOImpl.getCustomerDataForPrint(storeNum, rqtId);
		char couponLang='E';
		if("Spanish".equalsIgnoreCase(lang)){
			couponLang='S';
		}
		//String customerName = (null!=custDTO ? custDTO.getFirstName() + " " : "") + (null!=custDTO ? custDTO.getLastName() : "");

		List<Map<String, Object>> kioskDetailEntityList = kioskSOADaoImpl.getKioskEntityList(storeNum);
	//	logger.info(">>>>>>>>>>>> kioskDetailEntityList : ", kioskDetailEntityList); //Don't remove this logger
		if(null != kioskDetailEntityList && !CollectionUtils.isEmpty(kioskDetailEntityList)){
			char sboPrinterFlag = 'N';
			for (Map<String, Object> kioskDetailEntity : kioskDetailEntityList) {
				if (null != kiosk && kiosk.equalsIgnoreCase((String)kioskDetailEntity.get("kiosk_name"))) {
					sboPrinterFlag = null != kioskDetailEntity.get("sbo_printer_flag") ? ((String)kioskDetailEntity.get("sbo_printer_flag")).charAt(0) : 'N';
					break;
				}
			}
			if ('Y' == sboPrinterFlag) {
				//Hard-wired Printing
				status = printCustomerCareCouponBySBO(customerName, totalTimeElapsed, couponType, storeNum);
			} else {
				//CSM Printing
				status = printCustomerCareCouponByAdaptor(customerName, totalTimeElapsed, couponType, storeNum, couponLang, kiosk);
				status = "200".equalsIgnoreCase(status) ? "Success" : "Failure";
			}
		}

		logger.info("MPUWebServiceProcessorImpl:printCustomerCareCoupon::","Exiting->\n");
		return status;
	}
	public String printCustomerCareCouponBySBO(String customerName, String totalTimeElapsed,String couponType,String storeNum){
		logger.info("Inside printCustomerCareCouponBySBO method"," ...");
		String status=null;
		try {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 90);
			Date date=new Date(calendar.getTimeInMillis());
			SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
			CouponKioskPrintVO coupon = new CouponKioskPrintVO();
			coupon.setCustomerName(customerName);
			coupon.setExpiryDate((sdf.format(date)).toString());
			setCouponAmount(coupon,totalTimeElapsed,couponType);
			String url = PropertyUtils.getProperty("LockerPrintService_url")+storeNum+"/coupon";
			logger.info("Server URL for locker order to print = ", url);
			status = mpuWebServiceDAOImpl.printCouponBySBO(coupon,url);
			//logger.debug("Print ticket process completed!","");
		} catch (Exception e) {
			logger.error("Error at Printing ticket",e);
			status= "Failure";
		}
		return status;
	}		

	public void setCouponAmount(CouponKioskPrintVO coupon, String totalTimeElapsed,String couponType){
		if("normalCoupon".equals(couponType)){
			coupon.setAmount("5");
		}else if("bonusCoupon".equals(couponType) && totalTimeElapsed!=null){
			String[] timeArray=totalTimeElapsed.split(",");
			if(timeArray.length==2 && timeArray[0]!=null && timeArray[1]!=null){
				int hours=Integer.parseInt(timeArray[0]);
				int minutes=Integer.parseInt(timeArray[1]);
				int totalMinutes=hours*60+minutes;
				if(totalMinutes>10){
					int couponAmount=((totalMinutes-10)/10)*10+10;
					coupon.setAmount(String.valueOf(couponAmount));
				}
			}
		}
	}

	public String printCustomerCareCouponByAdaptor(String customerName, String totalTimeElapsed,String couponType,String storeNum,char language, String kiosk) throws DJException {
		logger.info("Inside printCustomerCareCouponByAdaptor method ...",couponType);
		String serverURL = null; 
		Order order = new Order();
		OrderAdaptorRequest request=new OrderAdaptorRequest();
		Coupon coupon = new Coupon();

		coupon.setCustomerName(customerName);
		coupon.setTotalTimeElapsed(totalTimeElapsed);
		/*coupon.setTaskID(order.getTask().getTaskId()); In new Data model we don't have task ID */
		coupon.setCouponLanguage(language);
		coupon.setCouponType(couponType);
		request.setCoupon(coupon);
		order.setKioskName(kiosk);
		request.setRequestOrder(order);

		String dnsName = MPUWebServiceUtil.getDNSForStore(storeNum,PropertyUtils.getProperty("Sears.StoreFormat"));			
		serverURL = dnsName+"/printCoupon";
		logger.info("the server url in printCustomerCareCouponByAdaptor", serverURL);
		return mpuWebServiceDAOImpl.printLockerTicketByAdaptor(request, serverURL);
	}

	private static Object createStringToObject(String str,TypeReference<?> typeReference) {//throws DJException{
		Object response = null;
		ObjectMapper mapper = new ObjectMapper();
		
		
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			if(null != str){
			//	logger.debug("createStringToObject==Original Json Length=", str.length());
				response = mapper.readValue(str,typeReference);
			}else{
				logger.debug("createStringToObject : ","String is empty");
			}
		} catch (Exception ex) {
			logger.error("createStringToObject :",ex);
		}
		return response;
	}

	public Map<String,String> getHostServers() throws DJException{
		return mpuWebServiceDAOImpl.getHostServers();
	}

	public List<String> getItemBinStatus(String storeNumber,String salesCheck,String divItemSku) throws DJException{
		List<String> response=new ArrayList<String>();
		logger.info("entering MPUWebServiceProcessorImpl.getItemBinStatus", "storeNumber=="+storeNumber+"salesCheck=="+salesCheck+"divItemSku=="+divItemSku);
		String rqtId="";
		response.add(0,"NOTFOUND");
		Map<String,Object> requestDetails= mpuWebServiceDAOImpl.getRequestIdbySalescheck(storeNumber, salesCheck);
		if(null!=requestDetails){
			if(null!=requestDetails.get("rqt_id")){
				rqtId=requestDetails.get("rqt_id").toString();	
			}else{
				response.set(0,"NOTFOUND");
				return response;
			}
		}
		List<ItemDTO> itemList= mpuWebServiceDAOImpl.getItemListForOrder(storeNumber, rqtId);
		if(!CollectionUtils.isEmpty(itemList)){
			String toLoc = "";
			for(ItemDTO itemDTO : itemList){
				String identifier=itemDTO.getDivNum()+itemDTO.getItem()+itemDTO.getSku();
				if(null != divItemSku && !"null".equalsIgnoreCase(divItemSku))
				{
					if(identifier.equalsIgnoreCase(divItemSku) && itemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.CLOSED) 
							&& itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB)){
						response.set(0,"CLOSED");
						break;
					}
					else if(identifier.equalsIgnoreCase(divItemSku) && itemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.BINNED)){
						toLoc = itemDTO.getToLocation();
						response.set(0,"BINNED");
						response.add(toLoc);
						break;
					}
					else if(identifier.equalsIgnoreCase(divItemSku) && !itemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.BINNED) && salesCheck.substring(0,5).equalsIgnoreCase("09300")
							&& requestDetails.get("request_type").toString().equalsIgnoreCase("BINWEB")){
						response.set(0,"NOT_BINNED_WEB");
						break;
					}
					else if(identifier.equalsIgnoreCase(divItemSku) && !itemDTO.getItemStatus().equalsIgnoreCase("STAGED") && !itemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.BINNED)){
						response.set(0,"NOT_STAGED");
						break;
					}
					else{
						response.set(0,"NOT_BINNED");
					}
				}else{
					if( (itemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.BINPENDING) || itemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.OPEN) ||
							itemDTO.getItemStatus().equalsIgnoreCase(MpuWebConstants.MOD_VERIFY)) && salesCheck.substring(0,5).equalsIgnoreCase("09300") && requestDetails.get("request_type").toString().equalsIgnoreCase("BINWEB")){
						response.set(0,"NOT_BINNED_WEB");
						break;
					}
					else{
						response.set(0,"NOT_BINNED");
					}
				}
			}
		}
		logger.info("exiting MPUWebServiceProcessorImpl.getItemBinStatus", false);
		return response;
	}


	public List<String> printReturnTicket(String storeNum, String printerId, String transId, String type, String user, String queueType, 
			String kioskName, String salesCheck) throws DJException {

		logger.info("MPUWebServiceProcessorImpl:printReturnTicket", "->Entering");
		List<String> response = new ArrayList<String>();
		//Print call for MerchandiseTag
		response.addAll(printReturnTypeTicket(storeNum, printerId, transId, "MerchandiseTag", user, queueType, false, kioskName,salesCheck));
		//Print call for ReturnClaimCheck
		response.addAll(printReturnTypeTicket(storeNum, printerId, transId, "ReturnClaimCheck", user, queueType, false, kioskName,salesCheck));
		logger.info("MPUWebServiceProcessorImpl:printReturnTicket", "->Exiting");
		return response;
	}

	public List<String> printReturnTypeTicket(String storeNum, String printerId, String transId, String type, String user, String queueType, boolean reprintFlag, 
			String kioskName, String salesCheck) throws DJException {

		logger.info("MPUWebServiceProcessorImpl:printReturnTypeTicket", "->Entering");
		List<String> response = new ArrayList<String>();
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setTrans_id(Integer.parseInt(transId));
		List<ItemDTO> itemDTOs = pickUpServiceDAO.fetchItemForRqtid(storeNum, orderDTO);

		/**
		 * For returnInFive
		 * @author nkhan6
		 */
		OrderAdaptorRequest request = mpuWebServiceDAOImpl.getOriginalJSON(null, storeNum, Integer.parseInt(transId));
		Order order = request.getRequestOrder();
		List<OrderItem> orderItems = order.getItems();

		if ("MerchandiseTag".equalsIgnoreCase(type)) {
			/*if(null!= order && "RETURNIN5".equalsIgnoreCase(order.getOrderType())){
						type = "MerchandiseTenderTag";
					}*/
			response = getMercTagPrint(itemDTOs, kioskName, storeNum, user,
					reprintFlag,salesCheck,printerId,type);
		} else {
			MPUItemVO mpuItemVO = getMPUItemVOForReturnTicket(itemDTOs, kioskName, 
					storeNum, user, reprintFlag,salesCheck,orderItems);
			//For ReturnInFive Requests
			if(null!= order && "RETURNIN5".equalsIgnoreCase(order.getOrderType())){
				type = "ReturnCustomerReceipt";
				mpuItemVO = addReturnInFiveInfo(mpuItemVO,order);
			}
			if (null != mpuItemVO) {
				mpuItemVO.setTaskType("ReturnTask");
				//String url="http://hfdvsbointjbos1.vm.itg.corp.us.shldcorp.com:8180/DEJInterfaces/v1/print/"+printerId+"/"+storeNum+"?type="+type;
				String url = PropertyUtils.getProperty("PrintService_url")+printerId+"/"+storeNum+"?type="+ type;
			//	logger.debug("the url for printReturnTicket is as :",url);
				PrintServiceResponse printServResp = mpuWebServiceDAOImpl.printLabel(mpuItemVO,url);
				response.add(printServResp.getWsResponeMeta().getErrorCode());
			}
		}
		logger.info("MPUWebServiceProcessorImpl:printReturnTypeTicket", "->Exiting");
		return response;
	}

	private List<String> getMercTagPrint(List<ItemDTO> itemDTOs, String kioskName, String storeNum, String searsSalesID, boolean reprintFlag,String salesCheck,String printerId, String type) throws DJException {
		logger.info("MPUWebServiceProcessorImpl:getMPUItemVOForMercTag", "->Entering");
		List<String> response = new ArrayList<String>();
		if (null != itemDTOs && !itemDTOs.isEmpty()) {
			MPUItemVO mpuItemVO = new MPUItemVO();
			ItemPrintVO itemPrintVO = null;
			CustomerDTO custDTO = mpuWebServiceDAOImpl.getCustomerDataForPrint(storeNum, itemDTOs.get(0).getRqtId()); 
			/*
			 * Customer Info
			 */
			if(null!=custDTO){
				mpuItemVO.setFirstName(custDTO.getFirstName());
				mpuItemVO.setLastName(custDTO.getLastName());
				mpuItemVO.setPhoneNumber(custDTO.getPhoneNumber());
			}
			mpuItemVO.setStockLocation(kioskName);
			mpuItemVO.setItemIndex(0);
			mpuItemVO.setNotifyCount(String.valueOf(1));
			mpuItemVO.setAssociateNumber(searsSalesID);
			mpuItemVO.setReprint(reprintFlag);

			mpuItemVO.setOrderId(itemDTOs.get(0).getSalescheck());  //Original salescheck 
			mpuItemVO.setStoreNumber(storeNum);
			mpuItemVO.setItemCount(itemDTOs.size());
			for(ItemDTO item : itemDTOs) {
				itemPrintVO = new ItemPrintVO();
				if("STK".equalsIgnoreCase(item.getFromLocation())){
					itemPrintVO.setDiv("2"+item.getDivNum());
				}else if("FLR".equals(item.getFromLocation())){
					itemPrintVO.setDiv("3"+item.getDivNum());
				}else{
					itemPrintVO.setDiv("4"+item.getDivNum());
				}
				itemPrintVO.setItemNo(item.getItem());
				itemPrintVO.setSku(item.getSku());
				itemPrintVO.setPlus4(item.getPlus4());
				if(StringUtils.hasText(item.getThumbnailDesc()) && item.getThumbnailDesc().length()>30){
					itemPrintVO.setItemDesc(item.getThumbnailDesc().substring(0,30));	
				}else{
					itemPrintVO.setItemDesc(item.getThumbnailDesc());
				}
				itemPrintVO.setActualQty(item.getRequested_quantity());
				itemPrintVO.setFromLocation(item.getFromLocation());
				itemPrintVO.setToLocation(item.getToLocation());
				itemPrintVO.setReturnReason(null);
				itemPrintVO.setTransactionType(item.getItemTransactionType());
				itemPrintVO.setOrigin(item.getItemSaleOrigin()); 

				mpuItemVO.setListOfItems(new ArrayList<ItemPrintVO>());
				mpuItemVO.getListOfItems().add(itemPrintVO);
				int reqQty = Integer.parseInt(item.getRequested_quantity());
				for (int i =0; i<reqQty;i++) {
					mpuItemVO.setItemIndex(i);
					mpuItemVO.setItemCount(reqQty);
					mpuItemVO.setTaskType("ReturnTask");
					//String url="http://hfdvsbointjbos1.vm.itg.corp.us.shldcorp.com:8180/DEJInterfaces/v1/print/"+printerId+"/"+storeNum+"?type="+type;
					String url = PropertyUtils.getProperty("PrintService_url")+printerId+"/"+storeNum+"?type="+ type;
					logger.debug("the url for printReturnTicket is as :",url);
					PrintServiceResponse printServResp = mpuWebServiceDAOImpl.printLabel(mpuItemVO,url);
					response.add(printServResp.getWsResponeMeta().getErrorCode());
				}
			}
		} 
		logger.info("MPUWebServiceProcessorImpl:getMPUItemVOForMercTag", "->Exiting ");
		return response;
	}


	private MPUItemVO getMPUItemVOForReturnTicket(List<ItemDTO> itemDTOs, String kioskName,
			String storeNum, String searsSalesID, boolean reprintFlag,
			String salesCheck,List<OrderItem> orderItems) throws DJException {
		logger.info("MPUWebServiceProcessorImpl:getMPUItemVOForReturnTicket", "->Entering");
		if (null != itemDTOs && !itemDTOs.isEmpty()) {
			MPUItemVO mpuItemVO = new MPUItemVO();
			ItemPrintVO itemPrintVO = null;
			ArrayList<ItemPrintVO> listOfItems =  new ArrayList<ItemPrintVO>();
			CustomerDTO custDTO = mpuWebServiceDAOImpl.getCustomerDataForPrint(storeNum, itemDTOs.get(0).getRqtId()); 
			/*
			 * Customer Info
			 */
			if(null!=custDTO){
				mpuItemVO.setFirstName(custDTO.getFirstName());
				mpuItemVO.setLastName(custDTO.getLastName());
				mpuItemVO.setPhoneNumber(custDTO.getPhoneNumber());
			}
			mpuItemVO.setStockLocation(kioskName);
			mpuItemVO.setItemIndex(0);
			mpuItemVO.setNotifyCount(String.valueOf(1));
			mpuItemVO.setAssociateNumber(searsSalesID);
			mpuItemVO.setReprint(reprintFlag);

			mpuItemVO.setOrderId(itemDTOs.get(0).getSalescheck());  //Original salescheck 
			mpuItemVO.setStoreNumber(storeNum);
			for(ItemDTO item : itemDTOs) {
				if (Integer.parseInt(item.getRequested_quantity()) != 0) {
					itemPrintVO = new ItemPrintVO();
					if("STK".equalsIgnoreCase(item.getFromLocation())){
						itemPrintVO.setDiv("2"+item.getDivNum());
					}else if("FLR".equals(item.getFromLocation())){
						itemPrintVO.setDiv("3"+item.getDivNum());
					}else{
						itemPrintVO.setDiv("4"+item.getDivNum());
					}
					//itemPrintVO.setDiv(item.getDivNum());
					itemPrintVO.setItemNo(item.getItem());
					itemPrintVO.setSku(item.getSku());
					itemPrintVO.setPlus4(item.getPlus4());
					if(StringUtils.hasText(item.getThumbnailDesc()) && item.getThumbnailDesc().length()>30){
						itemPrintVO.setItemDesc(item.getThumbnailDesc().substring(0,30));	
					}else{
						itemPrintVO.setItemDesc(item.getThumbnailDesc());
					}
					itemPrintVO.setActualQty(item.getRequested_quantity());
					itemPrintVO.setFromLocation(item.getFromLocation());
					itemPrintVO.setToLocation(item.getToLocation());
					itemPrintVO.setReturnReason(null);
					itemPrintVO.setTransactionType(item.getItemTransactionType());
					itemPrintVO.setOrigin(item.getItemSaleOrigin()); 

					/**
					 * For Return in Five 
					 */

					itemPrintVO.setSequenceNumber(item.getItemSeq());
					String itemIdentifier = item.getDivNum()+item.getItem()+item.getSku();
					if (null != orderItems) {				//null check added because for Return request 'orderItems' will be null
						for(OrderItem orderItem:orderItems){
							if(itemIdentifier.equalsIgnoreCase(orderItem.getItemNo())
									&& item.getItemSeq().equals(String.valueOf(orderItem.getSequenceNo()))){
								//itemPrintVO.setTaxAmount(orderItem.getTaxAmount());
								itemPrintVO.setSellPrice(String.valueOf(orderItem.getItemPrice()));
								List<Reduction>reductions = orderItem.getReductionsList();
								List<Map<String,String>> reductionMapList = new ArrayList<Map<String,String>>();
								if(null!=reductions){
									for(Reduction reduction : reductions){
										Map<String,String> reductionMap = new HashMap<String, String>();
										reductionMap.put("ReductionType", reduction.getReductionType());
										reductionMap.put("ReductionAmount", reduction.getReductionAmount());
										reductionMap.put("MultiItemCoupon", reduction.isMultiItemCoupon() == true ? "Y" : "N");
										reductionMap.put("ReductionAccountNumber", reduction.getReductionAmount());

										reductionMap.put("ReductionDate", reduction.getReductionDate());

										reductionMap.put("ReductionQty", String.valueOf(reduction.getReductionQty()));

										reductionMapList.add(reductionMap);
									}
								}
								itemPrintVO.setMiscAccount(orderItem.getMiscAccount());
								itemPrintVO.setReturnReason(orderItem.getItemReturnReason());
							}
						}
					}
					listOfItems.add(itemPrintVO);
				}
			}
			if (listOfItems.size() == 0) {
				return null;
			} else {
				mpuItemVO.setItemCount(listOfItems.size());
				mpuItemVO.setListOfItems(listOfItems);										//Setting list of items
			//	logger.debug("MPUWebServiceProcessorImpl:getMPUItemVOForReturnTicket", "->Exiting ");
				return mpuItemVO;
			}
		} else {
			logger.info("MPUWebServiceProcessorImpl:getMPUItemVOForReturnTicket", "->Exiting with null");
			return null;
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void markNoResponse(String storeId, String requestNumber, String status) throws DJException{
		logger.info("Entring MPUWebServiceProcessorImpl.markNoResponse status :",status);
		List<String> statusListReq = new ArrayList<String>();
		long rqt_id=0;
		statusListReq.add("OPEN");
		statusListReq.add("WIP");
		statusListReq.add("COMPLETE");
		rqt_id = mpuWebServiceDAOImpl.getOpenRequestId(storeId,requestNumber,statusListReq);
		if(rqt_id>0){
			List<ItemDTO> itemList = mpuWebServiceDAOImpl.getOrderItemList(storeId,String.valueOf(rqt_id));
			if(null!=itemList && !itemList.isEmpty()){
				for(ItemDTO itemDTO:itemList){
					String desc=(String)mpuWebServiceDAOImpl.getNextAction(itemDTO.getRequestType(),itemDTO.getItemStatus(),MpuWebConstants.NORESPONSE ,
							MpuWebConstants.ACTIVITY, itemDTO.getStoreNumber());
					String seqNo=(String)mpuWebServiceDAOImpl.getNextAction(itemDTO.getRequestType(),itemDTO.getItemStatus(),MpuWebConstants.NORESPONSE ,
							MpuWebConstants.SEQUENCE, itemDTO.getStoreNumber());
					insertActivity(itemDTO,desc,seqNo);
					mpuWebServiceDAOImpl.cancelExpireRequestItems(itemDTO.getStoreNumber(),itemDTO.getRequestNumber(),status);
					updateToEhCache(itemDTO,true);
				}
				if(MpuWebConstants.NORESPONSE.equals(status)){
					sendFinalResponse(storeId,String.valueOf(rqt_id),requestNumber,MpuWebConstants.COMPLETED,null);
				}
				mpuWebServiceDAOImpl.cancelExpireRequest(storeId,rqt_id,status);	
			}
		}
	}
	public boolean isPickedUp(String storeNumber, String requestId, String itemId) throws DJException{
		return mpuWebServiceDAOImpl.isPickedUp(storeNumber, requestId,itemId);
	}



	private void isReopenedFromCSMMiss(ItemDTO itemdto)throws DJException {
		int count = mpuWebServiceDAOImpl.isCSMMissed(itemdto.getStoreNumber(),itemdto.getRqtId(),itemdto.getRqdId());
		if(count > 0){
			itemdto.setCsmMissFLag("Y");
		}else{
			itemdto.setCsmMissFLag("N");
		}
	}



	public Map<String,Object> getNoOfPackages(String store, String salesCheck) throws DJException{
		Map<String,Object> responseMap = null;
		int packageCount=0;
		Integer rqtId = mpuWebServiceDAOImpl.isLayaway(store,salesCheck);
		if(rqtId != null && rqtId.intValue() > 0 ){
			List<PackageDTO> packageList =  mpuWebServiceDAOImpl.getPackageList(store, String.valueOf(rqtId.intValue()));
			if(!CollectionUtils.isEmpty(packageList)){
				packageCount=packageList.size();
			}else{
				packageCount=1;
			}

			responseMap=new HashMap<String,Object>();
			responseMap.put("STATUS",true);
			responseMap.put("PACKAGES",Integer.valueOf(packageCount));

		}else{
			responseMap=new HashMap<String,Object>();
			responseMap.put("STATUS",false);
			responseMap.put("PACKAGES",Integer.valueOf(packageCount));
		}

		return responseMap;
	}

	public String getRequestIdbySalescheck(String store,String requestNumber) throws DJException{
		Map<String,Object> requestDetails= mpuWebServiceDAOImpl.getRequestIdbySalescheck(store, requestNumber);
		if(null!=requestDetails){
			//logger.debug("the return for getRequestIdbySalescheck==", requestDetails.get("rqt_id").toString());
			return requestDetails.get("rqt_id").toString();
		}
		logger.info("exiting getRequestIdbySalescheck","");
		return null;
	}

	/**Service to send the response to the findyourWay for the completed STAGE Orders
	 * @param itemDTO
	 * @throws Exception
	 */
	private void sendMpuInStorePurchaseNotification(ItemDTO itemDTO) throws DJException{
		if(!itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB)){
			if(mpuWebServiceDAOImpl.checkRequestComplete(itemDTO.getStoreNumber(), itemDTO.getRqtId(), itemDTO.getRequestType())){
				CustomerDTO customerDTO = mpuWebServiceDAOImpl.getCustomerData(itemDTO.getStoreNumber(), itemDTO.getRqtId());
				if(StringUtils.hasText(customerDTO.getSywNumber())){
					mpuWebDlvryStatusService.sendMpuInStorePurchaseNotification(customerDTO.getSywNumber(),itemDTO.getStoreNumber(),
							itemDTO.getSalescheck());			
				}
			}
		}

	}


	public List<String> beepToPrinter(String storeNum,
			String printerId, String storeFormat) throws DJException {
		logger.info("beepToPrinter", "->Entering");
		List<String> response = new ArrayList<String>();
		//String url="http://hfdvsbointjbos1.vm.itg.corp.us.shldcorp.com:8180/DEJInterfaces/v1/print/testPrinter/{printerID}/{storeNum}/{storeFormat};
		String url = PropertyUtils.getProperty("PrintService_url")+ "testPrinter/" +printerId+"/"+storeNum+"/"+storeFormat;
	//	logger.debug("the url for beepToPrinter is as :",url);
		PrintServiceResponse printServResp = mpuWebServiceDAOImpl.beepToPrinter(url);
		response.add(null != printServResp ? printServResp.getWsResponeMeta().getErrorMessage() : "Failure");
		logger.info("MPUWebServiceProcessorImpl:beepToPrinter", "->Exiting");
		return response;
	}

	public Map<String,String> checkStatus(String storeId,String reqNum) throws DJException{
		return mpuWebServiceDAOImpl.checkStatus(storeId,reqNum);
	}

	private Map<String,Object> getKioskEntity(String storeNo, String kiosk) throws DJException{
		
		String queueKey = org.apache.commons.lang3.StringUtils.leftPad(storeNo, 5, '0')+"_"+kiosk;
		EhCacheCache storeDeatilQueueCache =  (EhCacheCache) cacheManager.getCache("storeDetailCache");
		logger.info("the cache is ", storeDeatilQueueCache);
		Map<String,Object> kioskEntity = null;
		if(null!=storeDeatilQueueCache && null!=storeDeatilQueueCache.get(queueKey)){
			logger.info("the value is from cache", "");
			kioskEntity = (Map<String,Object>)storeDeatilQueueCache.get(queueKey).get();
			
		}else{
			List<Map<String,Object>>  kioskEntityList = mCPDBDAO.getKioskDetailList(storeNo);
			if(!CollectionUtils.isEmpty(kioskEntityList)){
				for(Map<String,Object> kioskEntities: kioskEntityList){
					String storeNumdb=kioskEntities.get("store_no").toString();
					String keyValue = org.apache.commons.lang3.StringUtils.leftPad(storeNumdb, 5, '0')+"_"+kioskEntities.get("kiosk_name");
					storeDeatilQueueCache.put(keyValue, kioskEntities);
					if(keyValue.equalsIgnoreCase(queueKey)){
						kioskEntity = kioskEntities;
						logger.info("the value is from database", "");
					}
				}
			}
		}
		
		if(null!=kioskEntity){
			return kioskEntity;
		}else{
			return null;
		}
	}	
		
	private boolean getHoldGoflag(String storeNo, String kiosk) throws DJException{
		boolean holdGoFlag = false;
		Map<String,Object> kioskEntity = getKioskEntity(storeNo, kiosk);
		if(null!=kioskEntity){
			if("1".equalsIgnoreCase(kioskEntity.get("hold_go_flag").toString())) {
				holdGoFlag = true;
			}
		}
		
		return holdGoFlag;
	}
		
/*		boolean holdGoFlag = false;
		List<Map<String,Object>> kioskEntityList = mCPDBDAO.getKioskDetailList(storeNo);
		if(!CollectionUtils.isEmpty(kioskEntityList)){
			for(Map<String,Object>kioskEntity: kioskEntityList){
				if("1".equalsIgnoreCase(kioskEntity.get("hold_go_flag").toString()) && null!=kiosk && kiosk.equalsIgnoreCase(kioskEntity.get("kiosk_name").toString())){
					holdGoFlag = true;
					break;
				}
			}
		}
		return holdGoFlag;
*/	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Boolean updateCardSwipedFlagInBlob(RequestDTO requestDTO)
			throws DJException {
		logger.info("entering MPUWebServiceProcessorImpl.updateCardSwipedFlagInBlob", "requestDTO");
		int noOfRows = 0;
		Boolean itemupdated=false;

		noOfRows = mpuWebServiceDAOImpl.updateCardSwipedFlagInBlob(requestDTO.getOrder().getRqtId(), requestDTO.getOrder().getStoreNumber(), requestDTO.getOrder().getOriginalJson());
		//logger.debug("No of Rows Updated :",noOfRows);
		if(noOfRows>0) {
			itemupdated=true;
		}
		logger.info("exiting MPUWebServiceProcessorImpl.updateCardSwipedFlagInBlob itemupdated: ", itemupdated);
		return itemupdated;
	}

	public void printTICoupon(TICouponKioskPrintVO tiCouponKioskPrintVO,
			String store, String kioskName) throws DJException {
		logger.info("printTICoupon::","");
		String status = null;
		if (isSBOPrinterEnabled(store,kioskName)){
			status = printTICouponBySBO(tiCouponKioskPrintVO,store);
			if (null == status || !"Success".equalsIgnoreCase(status)) {
				logger.error("TI Coupon printing by SBO failed", status);
				throw new DJException("TI Coupon printing by SBO failed");
			}
		} else {		
			status = printTICouponByAdaptor(tiCouponKioskPrintVO, kioskName, store);
			if (null == status || !"200".equalsIgnoreCase(status)) {
				logger.error("TI Coupon printing by Adaptor failed", status);
				throw new DJException("TI Coupon printing by Adaptor failed");
			}
		}				
	}

	public boolean isSBOPrinterEnabled(final String storeNo, String kiosk) throws DJException{
		logger.info("in isSBOPrinterEnabled storeNo:: ",storeNo);
		List<Map<String, Object>> kioskDetailEntityList = kioskSOADaoImpl.getKioskEntityList(storeNo);
		char sboPrinterFlag = 'N';
		if(null != kioskDetailEntityList && !CollectionUtils.isEmpty(kioskDetailEntityList)){
			for (Map<String, Object> kioskDetailEntity : kioskDetailEntityList) {
				if (null != kiosk && kiosk.equalsIgnoreCase((String)kioskDetailEntity.get("kiosk_name"))) {
					sboPrinterFlag = null != kioskDetailEntity.get("sbo_printer_flag") ? ((String)kioskDetailEntity.get("sbo_printer_flag")).charAt(0) : 'N';
					break;
				}
			}
		}
		if ('Y' == sboPrinterFlag) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTIEnabledForStore(final String storeNo, String kiosk) throws DJException{
		logger.info("in isTIEnabledForStore storeNo:: ",storeNo);
		List<Map<String, Object>> kioskDetailEntityList = kioskSOADaoImpl.getKioskEntityList(storeNo);
		char tiEnabledFlag = 'N';
		if(null != kioskDetailEntityList && !CollectionUtils.isEmpty(kioskDetailEntityList)){
			for (Map<String, Object> kioskDetailEntity : kioskDetailEntityList) {
				if (null != kiosk && kiosk.equalsIgnoreCase((String)kioskDetailEntity.get("kiosk_name"))) {
					tiEnabledFlag = null != kioskDetailEntity.get("ti_enabled") ? ((String)kioskDetailEntity.get("ti_enabled")).charAt(0) : 'N';
					break;
				}
			}
		}
		if ('Y' == tiEnabledFlag) {
			return true;
		} else {
			return false;
		}
	}

	public String printTICouponBySBO(TICouponKioskPrintVO tiCouponKioskPrintVO,String store){
		logger.info("Inside printTICouponBySBO method"," ...");
		String status=null;
		try {
			String url = PropertyUtils.getProperty("LockerPrintService_url")+store+"/tiCoupon";
			//logger.debug("Server URL for TI coupon to print = ", url);
			status = mpuWebServiceDAOImpl.printCouponBySBO(tiCouponKioskPrintVO,url);
		//	logger.debug("Print ticket process completed!","");
		} catch (Exception e) {
			logger.error("Error at Printing TI coupon",e);
			status= "Failure";
		}
		return status;
	}

	public TIRequestDTO getBasicInfoForTI(String rqtIds,String storeNo)
	{
		TIRequestDTO tiRequestDTO = null;
		try
		{
			if(rqtIds != null)
			{
				List<String> sRqtIdsList = new ArrayList<String>(Arrays.asList(rqtIds.split(",")));
				int count = 0;
				int i = 0;
				int j = 0;
				int quantities=0;
				String saleschecks = "";
				String paymentTypes = "";
				String itemTypeIds="";
				String emailId=null;
				for(String rqt:sRqtIdsList) {
					OrderAdaptorRequest orderAdaptorRequest = mpuWebServiceDAOImpl.getOriginalJSON(rqt, storeNo, null);

					if (null != orderAdaptorRequest && null != orderAdaptorRequest.getRequestOrder()) {
						//Getting salescheck
						if(count != 0)
							saleschecks += ",";
						count++;
						saleschecks += orderAdaptorRequest.getRequestOrder().getIdentifier();
						//Getting PaymentTypeCode 
						List<Payment> payments = orderAdaptorRequest.getRequestOrder().getPaymentList();
						for (Payment pmt : payments) {
							String paymentAbb = null;
							paymentAbb= PropertyUtils.getProperty("payment.type.code."+String.valueOf(pmt.getPaymentTypeCode()));
							if(paymentAbb != null)
							{
								if(i != 0)
									paymentTypes+=",";
								i++;
								if(paymentTypes.indexOf(paymentAbb) == -1)
									paymentTypes+=paymentAbb;
							}
						}
						//Getting Item details 
						List<OrderItem> orderItems = orderAdaptorRequest.getRequestOrder().getItems();
						for(OrderItem item : orderItems)
						{
							if(j!=0)
							{
								itemTypeIds += "-";
							}
							j++;
							itemTypeIds+=(item.getItemDivision() + item.getItemNo().substring(3, 8) + item.getItemSku());
							quantities+=item.getItemQuantityActual();
						}
						//Getting Email IDs
						if(null == emailId)
						{
							String tempEmailId = null != orderAdaptorRequest.getRequestOrder().getCustomer() ? orderAdaptorRequest.getRequestOrder().getCustomer().getEmailAddress() : null;
							if(null != tempEmailId && !tempEmailId.equalsIgnoreCase("null") && !tempEmailId.trim().equalsIgnoreCase(""))
								emailId=tempEmailId;
						}
					}
				}
				tiRequestDTO = new TIRequestDTO();
				tiRequestDTO.setDivItemSku(itemTypeIds);
				tiRequestDTO.setEmailId(emailId);
				tiRequestDTO.setQuantity(String.valueOf(quantities));
				tiRequestDTO.setPaymentType(paymentTypes);
				tiRequestDTO.setOrderId(saleschecks);
			}
		}
		catch(Exception e)
		{
			logger.error("Exception occured while getting the tiRequestDTOs for TI: ", e);
		}
		return tiRequestDTO;
	}

	public OrderConfirmResponseDTO getTIOffers(String storeNo, String readyworkIds,
			String sywrId, String kioskName, String storeFormat) throws DJException {
		logger.info("start of getTIOffers readyworkIds:", readyworkIds+" SYWR ID :"+sywrId);	
		OrderConfirmResponseDTO orderConfirmResponseDTO =null ;
		if(isTIEnabledForStore(storeNo, kioskName))
		{
			try
			{	
				if(readyworkIds != null)
				{
					TIRequestDTO tiRequestDTO = getBasicInfoForTI(readyworkIds,storeNo);
					if(tiRequestDTO != null)
					{
						String fmChannel =null;
						Set<String> orderType = new HashSet<String>();
						String[] orderList = tiRequestDTO.getOrderId().split(",");
						String[] webOrderList =   PropertyUtils.getProperty("online.storeList").split(",");

						for(String order:orderList){
							String checkOrderType = order.substring(0, 5);
							for(String webOrder : webOrderList){
								if (webOrder.equals(checkOrderType)){
									orderType.add("SPU");
								}else{
									orderType.add("HFM");
								}
							}
						}
						if(orderType.size() > 0){
							if(orderType.contains("SPU")){
								fmChannel = "SPU";
							}else{
								fmChannel = "HFM";
							}
						}
						tiRequestDTO.setStoreNumber(storeNo);
						tiRequestDTO.setItemIdType("DIV-ITEM-SKU");
						tiRequestDTO.setSywrNumber(sywrId);
						tiRequestDTO.setOrderTotal("0");
						tiRequestDTO.setStoreFormat(storeFormat);
						tiRequestDTO.setFmChannel(fmChannel);
						//logger.info("fmChannelList: " + orderType.toString() + "size: " + orderType.size() + " tiRequestDTO: ", tiRequestDTO);
						orderConfirmResponseDTO = tiServiceProcessorImpl.getTIResponse(tiRequestDTO.getDivItemSku(),
								tiRequestDTO.getStoreNumber(),tiRequestDTO.getItemIdType(),tiRequestDTO.getSywrNumber(),tiRequestDTO.getPaymentType(),tiRequestDTO.getQuantity(),tiRequestDTO.getOrderId(),tiRequestDTO.getOrderTotal(),tiRequestDTO.getEmailId(),tiRequestDTO.getFmChannel());
					}
				}
			}
			catch(Exception e)
			{
				logger.error("Exception occured while getting TI offers:", e);				
			}
		}
		logger.info("End of getTIOffers readyworkIds:", readyworkIds+" SYWR ID :"+sywrId);
		return orderConfirmResponseDTO;
	}

	public String updateFailedReason(ItemDTO actionDTO) throws DJException {
		String reason ="";
		ItemDTO itemDTO = new ItemDTO();
		List<String> status = Arrays.asList(MpuWebConstants.ALL,MpuWebConstants.EXPIRED);
		List<ItemDTO> itemdtoList = mpuWebServiceDAOImpl.getItemList(actionDTO.getStoreNumber(),actionDTO.getRqtId(),actionDTO.getRqdId(),status,false) ;
		if (null!=itemdtoList || !itemdtoList.isEmpty()){
			itemDTO= itemdtoList.get(0);
			if(itemDTO.getItemStatus().equalsIgnoreCase("PICKUP_INITIATED")){
				reason = "Pickup is initiated, Customer is at "+itemDTO.getToLocation();
			}else  if(itemDTO.getItemStatus().equalsIgnoreCase("PICKED_UP")){
				reason = "Pickup is initiated, Customer is at "+itemDTO.getToLocation();
			}else  if(itemDTO.getItemStatus().equalsIgnoreCase("EXPIRED")){
				reason = "Order is Expired";
			}else  if(itemDTO.getItemStatus().equalsIgnoreCase("CANCELLED") ||itemDTO.getItemStatus().equalsIgnoreCase("VOIDED")){
				reason = "Order is voided/cancelled";
			}else  {
				reason = "Item has been acted upon by other associate. ";
			}
		}


		return reason;
	}


	public String isPlaformStore(String storeNumber) throws DJException{

		return mpuWebServiceDAOImpl.isPlaformStore(storeNumber);

	}
	public String getAssociateId(){
		String associateId=(String)MDC.get("associateId");
		logger.info("associateId from MDC", associateId);
		if(associateId==null||"".equals(associateId)){
			associateId="000075";
		}
		return associateId;

	}

	// Code to send service time warning to mod
	private void sendMPUserviceWarningMsg(String storeNumber ) throws DJException{
		logger.info("sendMPUserviceWarningMsg "+storeNumber, "Entering " + storeNumber);
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

	public String printTICouponByAdaptor(TICouponKioskPrintVO tiCouponKioskPrintVO,String kioskName, String store) throws DJException{
		logger.info("Inside printTICoupons","");
		OrderAdaptorRequest request=new OrderAdaptorRequest();
		Order order = new Order();
		order.setKioskName(kioskName);
		request.setRequestOrder(order);
		String serverURL = null; 
		TICoupon tiCoupon = new TICoupon();
		tiCoupon.setTitle(tiCouponKioskPrintVO.getTitle());
		tiCoupon.setEligibility(tiCouponKioskPrintVO.getEligibility());
		tiCoupon.setCouponDate(tiCouponKioskPrintVO.getStartDate().concat(" - ").concat(tiCouponKioskPrintVO.getEndDate()));			
		tiCoupon.setCso(tiCouponKioskPrintVO.getCso());
		tiCoupon.setStoreNo(store);
		request.setTiCoupon(tiCoupon);
		String dnsName = MPUWebServiceUtil.getDNSForStore(store,PropertyUtils.getProperty("Sears.StoreFormat"));			
		serverURL = dnsName+"/printTICoupon";
		return mpuWebServiceDAOImpl.printTICouponByAdaptor(request, serverURL);
	}


	public String getAppServer(String storeNumber) throws DJException {
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		String mapKey = storeNumber+"AppServer";
		if(null!=requestQueueCache.get(mapKey)){
			return (String)requestQueueCache.get(mapKey).get(); 
		}else{
			String appServer =  mpuWebServiceDAOImpl.getAppServer(storeNumber);
			requestQueueCache.put(mapKey, appServer);
			return appServer;
		}
	}
	
	public String getAppServerPlatform(String storeNumber) throws DJException {
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
		String mapKey = storeNumber+"AppServerPlatform";
		if(null!=requestQueueCache.get(mapKey)){
			return (String)requestQueueCache.get(mapKey).get(); 
		}else{
			String appServer =  mpuWebServiceDAOImpl.getAppServerPlatform(storeNumber);
			requestQueueCache.put(mapKey, appServer);
			return appServer;
		}
	}

	/**
	 * For adding order related info in case of return in 5 receipt
	 * @param mpuItemVO
	 * @param order
	 * @return
	 * @author nkhan6
	 */
	private MPUItemVO addReturnInFiveInfo(MPUItemVO mpuItemVO,Order order){
		try {
			StoreInfo storeInfo = mpuWebServiceDAOImpl.getStoreInformation(mpuItemVO.getStoreNumber());
			Map<String,String> storeInfoMap = new HashMap<String, String>();
			storeInfoMap.put("storeName", storeInfo.getType());
			storeInfoMap.put("storeAddress", storeInfo.getAddress1()+","+storeInfo.getCity()+","+storeInfo.getState());
			storeInfoMap.put("storeZipCode", storeInfo.getZipCode());
			storeInfoMap.put("storePhoneNumber", storeInfo.getPhone());

			mpuItemVO.setStoreInfoMap(storeInfoMap);

			if(null==mpuItemVO.getLastName() || mpuItemVO.getLastName().isEmpty()){
				mpuItemVO.setLastName(order.getCustomer().getName().split(" ")[1]);
			}
			mpuItemVO.setRelatedTransactionId(order.getIdentifier());
			mpuItemVO.setOriginalIdentifier(order.getOriginalIdentifier());
			mpuItemVO.setTransactionDate(order.getTransaction().getDate());
			final String RESTOCKING_FEE = "RF";
			final String ASSOC_DISCOUNT = "AD";

			BigDecimal restockingFee = getReductions(RESTOCKING_FEE,order);
			BigDecimal assocDiscount = getReductions(ASSOC_DISCOUNT,order);
			BigDecimal subtotal = new BigDecimal(0.0);


			mpuItemVO.setRestockingFee(restockingFee);
			mpuItemVO.setAssocDiscount(assocDiscount);
			mpuItemVO.setTaxAmount(getTaxAmount(order.getItems()));

			List<Map<String,String>>paymentMapList = new ArrayList<Map<String,String>>();
			if(null!=order.getPaymentList() && !order.getPaymentList().isEmpty()){
				for(Payment payment : order.getPaymentList()){
					Map<String,String> paymentMap = new HashMap<String, String>();
					if(PropertyUtils.getProperty("mpu.paymentTypes.giftcard").equals(String.valueOf(payment.getPaymentTypeCode()))){
						paymentMap.put("isGiftCard", "true");
					}else{
						paymentMap.put("isGiftCard", "false");
					}

					if(PropertyUtils.getProperty("mpu.paymentTypes.payPal").equals(String.valueOf(payment.getPaymentTypeCode()))){
						paymentMap.put("isPayPal", "true");
					}else{
						paymentMap.put("isPayPal", "false");
					}
					
					//logger.info("payment.getAccountNumber()==", payment.getAccountNumber());
					//logger.info("payment.getAccountNumber().substring(payment.getAccountNumber().length()-4)==", payment.getAccountNumber().substring(payment.getAccountNumber().length()-4));
				//	logger.info("paymentMap.get('isGiftCard')==",paymentMap.get("isGiftCard"));
					
					if(StringUtils.isEmpty(payment.getAccountNumber()) ||
							("0000").equals(payment.getAccountNumber().substring(payment.getAccountNumber().length()-4))
							|| "true".equalsIgnoreCase(paymentMap.get("isGiftCard"))){
						paymentMap.put("isCredit", "false");
					}else{
						paymentMap.put("isCredit", "true");
					}

					if(Boolean.valueOf(paymentMap.get("isCredit"))){
						String cardType = CreditCard.getCardType(payment.getPaymentTypeCode(), payment.getAccountNumber());
						int limitLength = Integer.valueOf(PropertyUtils.getProperty("cardTypeLength"));

						cardType = cardType.toUpperCase().substring(0,cardType.length() < limitLength ? cardType.length() : limitLength);
						paymentMap.put("CreditCardType", cardType);
						String truncatedAcctNumber = payment.getAccountNumber().substring(payment.getAccountNumber().length()-4);
						paymentMap.put("TruncatedAccountNumber", truncatedAcctNumber);

					}
					if(null!=payment.getAccountNumber()){
						paymentMap.put("PaymentAccountNumber", payment.getAccountNumber());
					}

					paymentMap.put("PaymentAmount", String.valueOf(payment.getAmount()));


					paymentMapList.add(paymentMap);
				}
			}
			mpuItemVO.setPaymentMapList(paymentMapList);
			mpuItemVO.setDotComOrderNumber(order.getDotComOrderNumber());
			mpuItemVO.setEmailAddress(order.getCustomer().getEmailAddress());
			mpuItemVO.setExchangeFlag(order.isExchangeFlag());

		} catch (DJException e) {
			logger.error("Exception in addReturnInFiveInfo = ",e);
		}
		return mpuItemVO;
	}

	/**
	 * Get the total reduction amount across all items for the given reduction type.
	 * @param reductionType Reduction Type 
	 * @return BigDecimal Total Reduction Amount for all items for the given reduction type, or 0.0 if none
	 */

	public BigDecimal getReductions(String reductionType,Order order) { 
		BigDecimal totalReductions = new BigDecimal(0.0);
		List<OrderItem> lineItems = order.getItems();
		for (int i = 0; i < lineItems.size(); i++) {	
			try {
				OrderItem lineItem = (OrderItem) lineItems.get(i);
				if(lineItem.getReductionsList()!=null && lineItem.getReductionsList().size()>0){
					for (int j = 0; j < lineItem.getReductionsList().size(); j++) {
						Reduction reduction = lineItem.getReductionsList().get(j);
						if (reduction.getReductionType().equals(reductionType)) {
							BigDecimal reductionAmount = new BigDecimal(reduction.getReductionAmount());
							totalReductions = totalReductions.add(reductionAmount);
						}
					}
				}
			} catch (ClassCastException ex) {

			}
		}    	
		return totalReductions;
	}

	/**
	 * Get the Tax Amount (gets each tax from each item and sums them up)
	 * @return BigDecimal taxAmount or 0.0 if no tax
	 */
	public BigDecimal getTaxAmount(List<OrderItem> lineItems) {
		BigDecimal totalTax = new BigDecimal(0.0);
		for (int i = 0; i < lineItems.size(); i++) {	
			try {
				OrderItem lineItem = (OrderItem) lineItems.get(i);
				BigDecimal tax = lineItem.getTaxAmount() == null ? new BigDecimal(0.0) : lineItem.getTaxAmount();
				totalTax = totalTax.add(tax);	
			} catch (ClassCastException ex) {

			}
		}    	
		return totalTax;

	}




	public boolean gethealthCheck(RequestDTO requestDTO) throws DJException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		if(requestDTO.getOrder().getRequestType().equalsIgnoreCase(MpuWebConstants.HEALTHCHECK)){
			try{
				Order order = mapper.readValue(requestDTO.getOrder().getOriginalJson(), Order.class);

				if( mpuWebServiceDAOImpl.gethealthCheck()){
					order.setStatus(MpuWebConstants.COMPLETED);
					oBUUpdateProcessor.updateOBU(order);
					/*update dtm flag call*/
					mpuWebServiceDAOImpl.updateDtmFlag();
					return true;
			}else{
				order.setStatus(MpuWebConstants.ERROR);
				oBUUpdateProcessor.updateOBU(order);
				return false;
			}
			}catch(Exception exception){
				
			}
		}else{
			if( mpuWebServiceDAOImpl.gethealthCheck()){
				try{
					HGRequestDTO hgRequestDTO = mapper.readValue(requestDTO.getOrder().getOriginalJson(), HGRequestDTO.class);
					hgRequestDTO.setStatus(MpuWebConstants.SUCCESS);
					oBUUpdateProcessor.updateHGOBU(hgRequestDTO);
					return true;
				}catch(Exception ex){
					return false;
				}
			}else{
				return false;
			}
			
		}
		return false;
	}
	public List<String> getKioskList(String store_no){
		logger.info("MPUWebServiceProcessorImpl:getKioskList", "->Entering");
		List<String> kioskList = new ArrayList<String>();
		//List<KioskDetailDTO> kioskEntityList;
		List<Map<String,Object>> kioskEntityList;
		//logger.info("the store_no for getKioskList is as :",store_no);
		try {
			kioskEntityList = mpuWebServiceDAOImpl.getKioskDetailList(store_no);
			/*if(kioskEntityList!=null && kioskEntityList.size()>0){
				for (Iterator<KioskDetailDTO> iterator = kioskEntityList.iterator(); iterator.hasNext();) {
					KioskDetailDTO kioskDetailDTO= (KioskDetailDTO) iterator.next();
					kioskList.add(kioskDetailDTO.getKiosk());
				}
			}*/
			if(kioskEntityList!=null && kioskEntityList.size()>0){
				for(Map<String,Object>kioskMap : kioskEntityList){
					if(null!=kioskMap.get("store_no"))
						kioskList.add(kioskMap.get("store_no").toString());
				}
			}
		} catch (DJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("MPUWebServiceProcessorImpl:getKioskList", "->Exiting");
		return kioskList;
	}
	
	public boolean sendPickedUpResponse(Order pickedUpOrder)
			throws DJException {
		logger.info("MpuWebServiceProcessorImpl::","sendPickedUpResponse Entered");
		try {
			oBUUpdateProcessor.updateOBU(pickedUpOrder);
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		logger.info("MpuWebServiceProcessorImpl::","sendPickedUpResponse Exiting");
		return true;
	}
	
	public void nposManualUpdate(String storeNum,String rqtId)    throws DJException{
        logger.info("updating NPOS manually","for rqt_id="+rqtId+"store="+storeNum);
             OrderAdaptorRequest request=mpuWebServiceDAOImpl.getOriginalJSON(rqtId, storeNum,null);
             Map<String,Object> itemStatusMap=mpuWebServiceDAOImpl.checkItemStatus(storeNum, rqtId);
             if(request.getRequestOrder()!=null){
                  Order originalOrder=request.getRequestOrder();
                  List<OrderItem> nposItemsList=request.getRequestOrder().getItems();
                  List<OrderItem> nposItemsListFinal=new ArrayList<OrderItem>();
                  
                  for(OrderItem item:nposItemsList){
                       char a='B';
                       String status= (String) itemStatusMap.get(item.getItemIdentifiers()+"-"+item.getSequenceNo());
                       if("BINNED".equals(status)){
                           item.setItemStockState('B');
                       }
                       else if("CLOSED".equals(status)){
                           item.setItemStockState('O');
                       }
                       nposItemsListFinal.add(item);
                  }
                  originalOrder.setItems(nposItemsListFinal);
                  request.setRequestOrder(originalOrder);
             }
                  
                  //nPOSUpdateProcessorImpl.updateNPOS(request, "complete");
             
             String baseUrl=MPUWebServiceUtil.getDNSForStore(storeNum, "SearsRetail");
             String finalResponseUrl=baseUrl+"/processWebOrderItemAcknowledgment";
             
             HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);


             try{
                  /*result=*/
               //   logger.info("calling NPOS", "START"+Calendar.getInstance().getTimeInMillis());
                  long startTime=Calendar.getInstance().getTimeInMillis();
                  //restTemplate.setMessageConverters(messageConverters);
                  logger.error("NPOS UPDATE URL : ",finalResponseUrl);
                  restTemplate.put(finalResponseUrl, requestEntity, new HashMap<String,String>());
               
                  long endTime=Calendar.getInstance().getTimeInMillis();
                //  logger.info("calling NPOS", "END"+(endTime-startTime));
             }
             catch(Exception e){
                  logger.error("Failed to update NPOS", e);
                  
             }                     
   }
	
	private Order getFinalOrderResponse(Order order,List<ItemDTO>itemDTOs){
		Order finalResponseOrder = order;
		for(OrderItem obuItem:finalResponseOrder.getItems()){
			String stringArr[] = obuItem.getItemIdentifiers().split(",");
			String obuIdentifier=null;
			if(null!=stringArr && stringArr.length>3){
				obuIdentifier = stringArr[0]+","+stringArr[1]+","+stringArr[2];
			}
			if(null!=obuIdentifier){
				obuIdentifier = obuIdentifier+"-"+obuItem.getSequenceNo();
			}
			
			logger.error("==obuIdentifier== " +obuIdentifier,"");
			for(ItemDTO mpuItem:itemDTOs){
				
				//for handling the duplicate bin numbers
				if(StringUtils.hasText(mpuItem.getToLocation()) && mpuItem.getToLocation().contains(MpuWebConstants.BIN)){
					String [] binArray = ((mpuItem.getToLocation()).trim()).split(" ");
					if(binArray.length>1){
						String finalBinloc = binArray[0]+" "+binArray[binArray.length-1];
						mpuItem.setToLocation(finalBinloc);
					}
				}
				
				
				
				String mpuIdentifier = mpuItem.getDivNum()+mpuItem.getItem()+mpuItem.getSku();
				/**
				 * For Crossformat orders
				 */
				if(null!=mpuItem.getUpc() && mpuItem.getUpc().startsWith("LM")){
					mpuIdentifier = mpuIdentifier +","+mpuItem.getUpc()+"-"+mpuItem.getItemSeq();
				}else{
					/*mpuIdentifier = mpuIdentifier +","+mpuItem.getKsn()+","+mpuItem.getUpc()
						+","+mpuItem.getItemId()+"-"+mpuItem.getItemSeq();*/
					mpuIdentifier = mpuIdentifier +","+mpuItem.getKsn()+","+mpuItem.getUpc()
							+"-"+mpuItem.getItemSeq();
				}
				
				logger.error("==mpuIdentifier== "+mpuIdentifier,"");
				if(mpuIdentifier.equalsIgnoreCase(obuIdentifier)){
				//	logger.info("Identifier ==", "Matched");
					String status = mpuItem.getItemStatus();
					
					if(MpuWebConstants.BINNED.equals(status)){
						obuItem.setItemStatus(MpuWebConstants.AVAILABLE);
						/**
						 * For defect-JIRA-STORESYS-24299 & STORESYS-24168
						 */
						String bin=null;
						if(mpuItem.getToLocation()!=null){
							bin=mpuItem.getToLocation().replaceAll("BIN", "");
							}
						//logger.info("BinNumber to OBU :",bin);
						//obuItem.setItemBinNumber(mpuItem.getToLocation());
						obuItem.setItemBinNumber(bin);
					} else if(MpuWebConstants.CLOSED.equals(status)){
						obuItem.setItemStatus((MpuWebConstants.OOS));
					} else if(MpuWebConstants.EXPIRED.equals(status) || MpuWebConstants.BINPENDING.equalsIgnoreCase(status)){
						//added bin_pending items also to NORESPONSE JIRA-STORESYS-25636
						obuItem.setItemStatus(MpuWebConstants.NORESPONSE);
					}else if(MpuWebConstants.CANCELLED.equalsIgnoreCase(status) || StringUtils.isEmpty(status)){
						//For Cancelled items
						obuItem.setItemStatus(MpuWebConstants.CANCELLED);
					}
				}
			}
		}
		finalResponseOrder.setStatus(MpuWebConstants.PROCESSED);
		return finalResponseOrder;
	}
	
	//code moved from service Layer to DAO layer
	
		/* (non-Javadoc)
		 * @see com.searshc.mpuwebservice.dao.MPUWebServiceDAO#isModActive(java.lang.String)
		 */
	/*	public boolean isModActive(String strNum) throws DJException{
			logger.debug("Entering MPUWebServiceDAOImpl.isModActive	strNum:",strNum);
			String finalUrl = PropertyUtils.getProperty(System.getProperty("env")+"."+"modbase_url") +"/isMODActive/"+strNum;
			try{
				logger.debug("the url for MPUWebServiceDAOImpl.isModActive",finalUrl);
			ResponseEntity<Boolean> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, Boolean.class);
			logger.debug("Exit MPUWebServiceDAOImpl.isModActive","");
			return (Boolean)response.getBody().booleanValue();
			}
			catch(RestClientException re){
				logger.error("isModActive=="+re, "");
				return false;
			}
		}*/
		
		public long checkIsActiveRequestExisting(String storeNumber, String listNumber) 
				throws DJException{
			
			return mpuWebServiceDAOImpl.checkIsActiveRequestExisting(storeNumber, listNumber);
			
		}
		
		public List<DDMeta> getDDRMetaCache(){
			return mpuWebServiceDAOImpl.getDDRMetaCache();
		}
		
		public boolean refreshDDRMetaCache(){
			return mpuWebServiceDAOImpl.refreshDDRMetaCache();
		}
		
		private void getColorUpdated(List<ItemDTO> itemList) throws DJException{
			/**
			 * For color coding with respect to the escalation time limit of stores
			 */
			HashMap<String,Object> storeInfo=null;
			int escalation_time_val = 60;//default to 60 minutes
			if(null!=itemList && !itemList.isEmpty() && null!=itemList.get(0)){
				storeInfo=(HashMap<String, Object>)mpuWebServiceDAOImpl.getStoreDetails(itemList.get(0).getStoreNumber());
			}
			if(null!=storeInfo){
				escalation_time_val=Integer.parseInt((String)(storeInfo.get(MpuWebConstants.ESCALATION_TIME)));
			//	logger.info("getColorUpdated = ", escalation_time_val);
			}
			
			for(ItemDTO objItem : itemList){
				
				/*if(StringUtils.hasText(objItem.getRequestType()) && MpuWebConstants.BINWEB.equalsIgnoreCase(objItem.getRequestType())){
					//isReopenedFromCSMMiss(objItem);
					objItem.setCsmMissFLag("N");
					updateToEhCache(objItem, false);
				}
				*/
				String a[] = objItem.getExpireTime().split(":");//: "18:10:53"
				int TotalSeconds = (Integer.parseInt(a[0])) * 60 * 60 + (+Integer.parseInt(a[1])) * 60 + (+Integer.parseInt(a[2]));
				if(objItem.getRequestType().equalsIgnoreCase("BINWEB") && MPUWebServiceUtil.revIsStoreTrasferWebOrder(objItem.getSalescheck(),objItem.getRequestType())){
					objItem.setItemColorCode("");
					
				}else if (objItem.getRequestType().equalsIgnoreCase("BINWEB") || objItem.getRequestType().equalsIgnoreCase("H&G")){
					/*int redColorThreshold=2700;
					int yellowColorThreshold=1800;*/
					/**
					 * For color coding with respect to the escalation time limit of stores
					 */
					int redColorThreshold = (escalation_time_val-15)*60;
					int yellowColorThreshold = (escalation_time_val-30)*60;
					
					
					int greyColorThreshold = 0;
					if (TotalSeconds>=redColorThreshold) {
						objItem.setItemColorCode("red");
					}else if (TotalSeconds >= yellowColorThreshold){
						objItem.setItemColorCode("yellow");
					}else if (TotalSeconds >= greyColorThreshold){
						objItem.setItemColorCode("");
					}
					
					
				}
				
				
			}
			
		}

		/* Gaming Enhancement no. 5 start*/
		
		/*
		
		public void updateHFMbin() throws DJException {
			mpuWebServiceDAOImpl.updateHFMbin();
		}
		
		*/
		
		/* Gaming Enhancement no. 5 end*/
		
		/**Service to send the response to the OBU team once the request is completed 
		 * just after cancellation of the items
		 * 
		 * @param storeNum
		 * @param rqtId
		 */
		private void sendWebResponseCncl(String storeNum,String rqtId){
			logger.info("entering sendWebResponseCncl", "storeNum=="+storeNum+"rqtId==="+rqtId);
			try{
				OrderDTO orderDTO = mpuWebServiceDAOImpl.getAllOrderDetails(storeNum, rqtId);
				/****check whether the order is WEB order and is sent by OBU ****/
				
				if(MpuWebConstants.BINWEB.equalsIgnoreCase(orderDTO.getRequestType()) && 
						MpuWebConstants.ORDER_SOURCE_DIRECT.equalsIgnoreCase(orderDTO.getOrderSource())){
					String originalJSON = orderDTO.getOriginalJson(); 
					List<ItemDTO> itemDTOs = mpuWebServiceDAOImpl.getOrderItemList(storeNum,rqtId);
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					Order order = (Order)mapper.readValue(originalJSON,new TypeReference<Order>(){});
					//Order order = (Order)createStringToObject(originalJSON, new TypeReference<Order>(){});
					Order finalOrderResponse = getFinalOrderResponse(order,itemDTOs);
					//logger.info("****finalOrderResponse** = ", finalOrderResponse.toString());
				//	logger.info("start OBU update",""+Calendar.getInstance().getTimeInMillis());
					oBUUpdateProcessor.updateOBU(finalOrderResponse);
				//	logger.info("exiting sendWebResponseCncl","");
				}else{
					logger.info("The response to OBU is not sent","");
				}
			}catch(Exception exception){
				logger.error("in the catch block of sendWebResponseCncl", exception.getMessage());
			}
		}
		
		public Map<String,String> getAppMetaCache(){
			return DJServiceLocator.getAllAppRoutingMetaCache();
		}
		
		public boolean refreshAppMetaCache(){
			return DJServiceLocator.refreshAllAppRoutingMetaCache();
		}
		
		private void updateItemColor(ItemDTO item) throws DJException{
			/**
			 * For color coding with respect to the escalation time limit of stores
			 */
			HashMap<String,Object> storeInfo=null;
			int escalation_time_val = 60;//default to 60 minutes
			if(null!=item){
				storeInfo=(HashMap<String, Object>)mpuWebServiceDAOImpl.getStoreDetails(item.getStoreNumber());
			}
			if(null!=storeInfo){
				escalation_time_val=Integer.parseInt((String)(storeInfo.get(MpuWebConstants.ESCALATION_TIME)));
				//logger.info("getColorUpdated = ", escalation_time_val);
			}
			String a[] = item.getExpireTime().split(":");//: "18:10:53"
			int TotalSeconds = (Integer.parseInt(a[0])) * 60 * 60 + (+Integer.parseInt(a[1])) * 60 + (+Integer.parseInt(a[2]));
			if(item.getRequestType().equalsIgnoreCase("BINWEB") && MPUWebServiceUtil.revIsStoreTrasferWebOrder(item.getSalescheck(),item.getRequestType())){
				item.setItemColorCode("");
				
			}else if (item.getRequestType().equalsIgnoreCase("BINWEB") || item.getRequestType().equalsIgnoreCase("H&G")){
				/*int redColorThreshold=2700;
				int yellowColorThreshold=1800;*/
				/**
				 * For color coding with respect to the escalation time limit of stores
				 */
				int redColorThreshold = (escalation_time_val-15)*60;
				int yellowColorThreshold = (escalation_time_val-30)*60;
				
				
				int greyColorThreshold = 0;
				if (TotalSeconds>=redColorThreshold) {
					item.setItemColorCode("red");
				}else if (TotalSeconds >= yellowColorThreshold){
					item.setItemColorCode("yellow");
				}else if (TotalSeconds >= greyColorThreshold){
					item.setItemColorCode("");
				}
				
				
			}
			
		}
		
		
		public synchronized ArrayList<String> getExpiredList(){
			
			EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
			ArrayList<String> expiredOrderList=null;
			
			if(null!=requestQueueCache.get("ExpiredOrderList")){
				expiredOrderList = (ArrayList<String>) requestQueueCache.get("ExpiredOrderList").get();
			}else {
				expiredOrderList = new ArrayList<String>();
			}
			
			return expiredOrderList;
		}
		
public synchronized void  setExpiredList(String rqtId){
	EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("mpuQueueCache");
	ArrayList<String> expiredOrderList=null;
			
	if(null!=requestQueueCache.get("ExpiredOrderList")){
		expiredOrderList = (ArrayList<String>) requestQueueCache.get("ExpiredOrderList").get();
		expiredOrderList.add(rqtId);
	}else{
		
		expiredOrderList=new ArrayList<String>();
		expiredOrderList.add(rqtId);
	}
	
	requestQueueCache.put("ExpiredOrderList", expiredOrderList);
}
		}
