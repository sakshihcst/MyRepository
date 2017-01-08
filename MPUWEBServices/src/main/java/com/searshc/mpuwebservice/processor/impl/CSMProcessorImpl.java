package com.searshc.mpuwebservice.processor.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderItem;
import com.searshc.mpuwebservice.bean.ActivityDTO;
import com.searshc.mpuwebservice.bean.ActivityUserEntity;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.MCPDBDAO;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.dao.MpuStaticParamDAO;
import com.searshc.mpuwebservice.dao.PickUpServiceDAO;
import com.searshc.mpuwebservice.dao.StoreLocalTimeServiceDAO;
import com.searshc.mpuwebservice.dao.impl.MODWebServiceDAOImpl;
import com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor;
import com.searshc.mpuwebservice.processor.CSMProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.processor.PickUpServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebutil.util.ConversionUtils;

@Service
public class CSMProcessorImpl implements CSMProcessor{
	
	
	@Autowired
	MPUWebServiceDAO mpuWebServiceDAO;
	
	
	@Autowired
	PickUpServiceDAO pickUpServiceDAO;
	
	@Autowired
	private MODWebServiceDAOImpl modWebServiceDAPImpl;
	
	@Autowired
	MPUWebServiceProcessor mpuWebServiceProcessor;
	
	@Autowired
	AssociateActivityServicesProcessor associateActivityServicesProcessor;
	
	@Autowired
	PickUpServiceProcessor pickUpServiceProcessor;
	
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	
	@Autowired
	MCPDBDAO mcpdbdao;
	
	@Autowired
	@Qualifier("mpuStaticParamDAOImpl")
	MpuStaticParamDAO mpuStaticParamDAO;
	
	@Autowired
	StoreLocalTimeServiceDAO storeLocalTimeServiceDAO;
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(CSMProcessorImpl.class);
	
	
	public String getTimeDifference(String createDate, String store) throws DJException {
/*		Map<String ,Object> map =  mpuWebServiceDAO.getStoreDetails(store);
		String storeTimeZoneString = (String)map.get("timeZone");*/
		Date currentTime=Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date createdDate = null;
		try {
			createdDate = dateFormat.parse(createDate);
		} catch (ParseException e) {
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}	
		//long elapsedTime = getStoreLocalTime(storeTimeZoneString).getTime() - getStoreLocalTimeForExpire(storeTimeZoneString, createdDate).getTime();
		long elapsedTime = currentTime.getTime() - createdDate.getTime();
		return getElapsedTimeHoursMinutesSeconds(elapsedTime);
	}
	
	private Date getStoreLocalTime(String storeTimeZoneString) {
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
		} 
	
	private String getElapsedTimeHoursMinutesSeconds(long elapsedTime) { 
	    String format = String.format("%%0%dd", 2);  
	    elapsedTime = elapsedTime / 1000;  
	    String seconds = String.format(format, elapsedTime % 60);  
	    String minutes = String.format(format, (elapsedTime % 3600) / 60);  
	    String hours = String.format(format, elapsedTime / 3600);  
	    String time =  hours + ":" + minutes + ":" + seconds;  
	    return time;  
	}

	
	public RequestDTO getPostVoidOrder(String store, String salescheck)	throws DJException {
		//String rqtId = mpuWebServiceProcessor.getRequestIdbySalescheck(store, salescheck);
		/****changes done for STORESYS-25892****/
		String rqtId = mpuWebServiceDAO.getRqtIdForPostVoid(store, salescheck);
		logger.info("the rqtId received for post void is ===", rqtId);
		List<String> statusList = new ArrayList<String>();
		RequestDTO requestDTO = null;
		List<ItemDTO> itemDTOs=new ArrayList<ItemDTO>();
		Map<String ,Object> map =  mpuWebServiceDAO.getStoreDetails(store);
		String storeTimeZoneString = (String)map.get("timeZone");
		Order order =null;
		boolean orderFound = false;

		Date date =  getStoreLocalTime(storeTimeZoneString);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = simpleDateFormat.format(date);


		order = pickUpServiceDAO.fetchForPostVoid(salescheck,store);
		if(order!=null){
			logger.info("the order from npos is ", "true");
		}
		if(order!=null && order.getTransaction().getCode()==null){
			statusList.add("ALL");
			statusList.add(MpuWebConstants.COMPLETED);
			statusList.add(MpuWebConstants.PICKED_UP);
			statusList.add(MpuWebConstants.OPENSTATUS);
			
			if(!StringUtils.isEmpty(rqtId)){
				//String fields = MpuWebConstants.ITEM+","+MpuWebConstants.IDENTIFIER;
				requestDTO  = mpuWebServiceProcessor.getRequestData(rqtId, store, null, null, statusList);
				if(requestDTO!=null && null!=requestDTO.getOrder()){
					List<ItemDTO> itemList =  mpuWebServiceDAO.getAllItemsForPostVoid(store, rqtId);
					requestDTO.setItemList(itemList);
					//logger.debug("the order from the database is ", requestDTO.toString());
					if(!CollectionUtils.isEmpty(requestDTO.getItemList())){
						for(ItemDTO itemDTO : requestDTO.getItemList()){
							if(MpuWebConstants.PICKED_UP.equalsIgnoreCase(itemDTO.getItemStatus())
									|| MpuWebConstants.PICKUP_INITIATED.equalsIgnoreCase(itemDTO.getItemStatus())){
								itemDTOs.add(itemDTO);
							}
						}
					}
					requestDTO.setItemList(itemDTOs);
					String updateTime = requestDTO.getOrder().getUpdateTimestamp();
					logger.info("the updateTimeupdateTime from the database is ", updateTime);
					if(!StringUtils.isEmpty(updateTime)){
						String updateDate= updateTime.substring(0,updateTime.indexOf(" "));
						if(todayDate.equalsIgnoreCase(updateDate)){
							orderFound=true;
						}
					}
				}
			}
		}

		if(orderFound){
			return requestDTO;
		}else{
			return null;
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public RequestDTO updatePostVoid(String store, RequestDTO requestDTO)throws DJException {
		String status=MpuWebConstants.BINNED;
		String CurrTime = pickUpServiceProcessor.getTimeAccToTimeZone(new Date() + "", store, "dd/MM/yyyy HH:mm:ss");
		requestDTO.getOrder().setCreateTimestamp(CurrTime);
		requestDTO.getOrder().setReturnParentId(requestDTO.getOrder().getRqtId());
		if(requestDTO.getCustomer()!=null){
			requestDTO.getOrder().setCustomer_name(requestDTO.getCustomer().getFirstName()+" "+requestDTO.getCustomer().getLastName());	
		}
		requestDTO.getOrder().setOriginalIdentifier(requestDTO.getOrder().getSalescheck());
		if(requestDTO!=null){
			for(ItemDTO itemDTO : requestDTO.getItemList()){
				itemDTO.setReturnParentId(itemDTO.getRqdId());
				itemDTO.setItemQuantityNotDelivered(itemDTO.getQtyRemaining());
				itemDTO.setDeliveredQuantity(itemDTO.getDeliveredQuantity()!=null ? itemDTO.getDeliveredQuantity() : new Integer(0));
				itemDTO.setCreateTime(CurrTime);
				itemDTO.setUpdateIimestamp(CurrTime);
				String deliveredQty=null;
				String item_Status=MpuWebConstants.COMPLETED;
				if(null!=itemDTO.getQty() && null!= itemDTO.getQtyRemaining()){
					int delQty = Integer.parseInt(itemDTO.getQty()) - Integer.parseInt(itemDTO.getQtyRemaining());
					deliveredQty = String.valueOf(delQty);
				}
				
				if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.STAGE)){
					status = MpuWebConstants.STAGED_STATUS;
				}
				String location = "BIN "+itemDTO.getSalescheck().substring(itemDTO.getSalescheck().length()-1);
				pickUpServiceDAO.updateQueueDetails(store, status, itemDTO.getQty(),deliveredQty,itemDTO.getQtyRemaining() ,itemDTO.getRqdId(),location);
				pickUpServiceDAO.updateQueueTrans(store, item_Status ,itemDTO.getRqtId());
				ActivityDTO activity = new ActivityDTO ();
				activity.setActivityDescription(MpuWebConstants.ITEM_POST_VOID_DESC);
				activity.setAssignedUser(itemDTO.getAssignedUser());
				activity.setCreatedBy(itemDTO.getCreatedBy());
				activity.setType(itemDTO.getRequestType());
				activity.setFromLocation(itemDTO.getFromLocation());
				activity.setStore(itemDTO.getStoreNumber());
				activity.setToLocation(itemDTO.getToLocation());
				activity.setType(itemDTO.getRequestType());
				
				mpuWebServiceDAOImpl.createActivity(activity, Long.parseLong(itemDTO.getRqtId()), Long.parseLong(itemDTO.getRqdId()));
				
			}
		}
		List<RequestDTO> requestList = new ArrayList<RequestDTO>();
		requestList.add(requestDTO);
		
		List<MPUActivityDTO> mpuActivityDTOs =  ConversionUtils.convertRequestDTOtoMPUActivityDTO(requestList);
		if(!CollectionUtils.isEmpty(mpuActivityDTOs)){
			for(MPUActivityDTO dto : mpuActivityDTOs){
				dto.setRequestType(MpuWebConstants.POSTVOID);
			}
			associateActivityServicesProcessor.insertMPUActivityData(mpuActivityDTOs);
		}
		
		updateVoidPostToNPOS(requestDTO);
		
		return null;
	}
	
	
	private void updateVoidPostToNPOS(RequestDTO requestDTO) throws DJException{
		String rqtId = requestDTO.getOrder().getRqtId();
		String storeNumber = requestDTO.getOrder().getStoreNumber();
		String storeFormat = requestDTO.getOrder().getStoreFormat();
		OrderAdaptorRequest request = mpuWebServiceDAO.getOriginalJSON(rqtId, storeNumber, null);
		Map<String, Integer> itemQty = new HashMap<String, Integer>();
		if(!CollectionUtils.isEmpty(requestDTO.getItemList())){
			for(ItemDTO itemDTO : requestDTO.getItemList()){
				String key = (StringUtils.isEmpty(itemDTO.getDivNum()) ? "" : itemDTO.getDivNum()) +
				(StringUtils.isEmpty(itemDTO.getItem()) ? "" : itemDTO.getItem()) +
				(StringUtils.isEmpty(itemDTO.getSku()) ? "" : itemDTO.getSku()) +","+
				(StringUtils.isEmpty(itemDTO.getKsn()) ? "" : itemDTO.getKsn()) +","+
				(StringUtils.isEmpty(itemDTO.getUpc()) ? "" : itemDTO.getUpc()) +","+
				(StringUtils.isEmpty(itemDTO.getItemId()) ? "" : itemDTO.getItemId()) +","+
				itemDTO.getItemSeq();
				key = key.replaceAll("\\s","");
				//logger.info("the key value is ", key +"==="+new Integer(itemDTO.getQtyRemaining()));
				//Nasir
				//itemQty.put(key, new Integer(itemDTO.getQty()));
				itemQty.put(key, new Integer(itemDTO.getQtyRemaining()));
			}
		}
		
		request.setRequestType(OrderAdaptorRequest.PUSH_ORDER);
		Order originalOrder=request.getRequestOrder();
		List<OrderItem> nposItemsList=request.getRequestOrder().getItems();
		List<OrderItem> nposItemsListFinal=new ArrayList<OrderItem>();
		for(OrderItem orderItem : nposItemsList){
			String nposIdentifier = orderItem.getItemIdentifiers();
			int commaCount = StringUtils.countMatches(nposIdentifier,",");
			if( commaCount>3){
				nposIdentifier = orderItem.getItemIdentifiers().substring(0,orderItem.getItemIdentifiers().length()-1 );
			}
			nposIdentifier = nposIdentifier.replaceAll("\\s","");
			String mapKey = nposIdentifier+","+orderItem.getSequenceNo();
			//logger.info("key to be searched is ", mapKey);
			if(null != itemQty.get(mapKey)){
				int qty = (Integer)itemQty.get(mapKey).intValue();
				logger.info("===qty===", qty);
				orderItem.setItemQty(qty);
				orderItem.setItemQuantityAvailable(qty);
				orderItem.setItemQuantityRequested(qty);
				orderItem.setItemQuantityActual(qty);
				orderItem.setItemStatus("OPEN");
				nposItemsListFinal.add(orderItem);
			}
			
			if(StringUtils.isEmpty(originalOrder.getRingingAssociateCode())){
				//originalOrder.setRingingAssociateCode(orderItem.getItemsellingAssociateId());
				/**
				 * For Testing purpose sending a hardcode value
				 */
				originalOrder.setRingingAssociateCode("000075");
			}
			originalOrder.getTask().setTaskType(new Integer(101));
		}
		
		originalOrder.setItems(nposItemsListFinal);
		request.setRequestOrder(originalOrder);
		

		logger.info("the update void pickUpt npos is going on ","");
		pickUpServiceDAO.updateVoidPickUpToNPOS(request, storeNumber, storeFormat);
		
	}

	public List<String> beepToPrinter(String store, String storeFmt,String printerId, String kiosk) throws DJException {
		
		return modWebServiceDAPImpl.beepToPrinter(store, storeFmt, printerId, kiosk);
	}
	public String subscribeCSMToNPOS(String store, String storeFmt) throws DJException {
		return modWebServiceDAPImpl.subscribeCSMToNPOS(store, storeFmt);
	}

	@Transactional
	public Set<String> logoutModActiveUsers() throws Exception{
		logger.info("Inside CSMProcessorImpl.logoutModActiveUsers", "");					
		Set<String> storeSet = new HashSet<String>();
		
		List<ActivityUserEntity> activiteUserEntities = mcpdbdao.getAllModActiveUsers();		
		
		if(activiteUserEntities != null && activiteUserEntities.size()>0){
			logger.info("Inside CSMProcessorImpl.logoutModActiveUsers","active users are available");				
			
			for(ActivityUserEntity activeUserEntity : activiteUserEntities){
				storeSet.add(activeUserEntity.getStoreNo());
				try {					
					activeUserEntity.setLoggedOutTime(storeLocalTimeServiceDAO.getstoreLocalTimeStamp(activeUserEntity.getStoreNo()));
					//logger.info("Inside CSMProcessorImpl.logoutModActiveUsers"," updating active user for store: "+activeUserEntity.getStoreNo());
					mcpdbdao.updateLoggedOutTime(activeUserEntity.getUserId(), "N", activeUserEntity.getLoggedOutTime().toString());
				} catch (Exception e) {					
					logger.error("Inside CSMProcessorImpl.logoutModActiveUsers", " Exception occurred while updating active user for store: "+activeUserEntity.getStoreNo());
				}
			}					
		}else{
			logger.info("Inside CSMProcessorImpl.logoutModActiveUsers ","NO active users available");
		}
		return storeSet;		
	}
	
	
	public void updateNPOSToLogoutModActiveUsers (Set<String> activeMODStores){
		logger.info("Inside CSMProcessorImpl.updateNPOSToLogoutModActiveUsers"," for stores: "+activeMODStores);					
		int threadPoolSize = 5;
		ExecutorService executor = null;
		
		try {
			if(activeMODStores.size()>0){
				executor = Executors.newFixedThreadPool(threadPoolSize);
				for(String activeMODStore: activeMODStores){
					Order order = new Order();
					String dnsName = MPUWebServiceUtil.getDNSForStore(activeMODStore, MpuWebConstants.STOREFORMAT);
					String serverURL = dnsName+"/modUnSubScribe";
					//logger.info("Inside CSMProcessorImpl.updateNPOSToLogoutModActiveUsers NPOS ServerURL :"+serverURL,"");
					NPOSUpdateServiceThread workRunner = new NPOSUpdateServiceThread(order, serverURL);			
					try {
						executor.execute(workRunner);
					} catch (Exception e) {						
						logger.error("Inside CSMProcessorImpl.updateNPOSToLogoutModActiveUsers for store:"+activeMODStore," Exception happend in thread "+e.getMessage());
					}
				}
			}
		} catch (Exception e) {			
			logger.error("Exception occurred Inside CSMProcessorImpl.updateNPOSToLogoutModActiveUsers for stores: "+activeMODStores," while updateNPOS "+e.getMessage());
		}
		finally{
			logger.info("Inside CSMProcessorImpl.updateNPOSToLogoutModActiveUsers ","executor shutdown");
			if(executor!=null)
				executor.shutdown();
		}
		logger.info("Exiting CSMProcessorImpl.updateNPOSToLogoutModActiveUsers"," for stores: "+activeMODStores);
	}
}
