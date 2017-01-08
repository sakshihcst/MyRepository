package com.searshc.mpuwebservice.processor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.service.DJService;
import com.sears.mpu.backoffice.domain.Order;
import com.searshc.mpuwebservice.bean.AuthIdResponse;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.MPUActivityDTO;
import com.searshc.mpuwebservice.bean.MpuPickUpReportResposne;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PickUpSelectedItems;
import com.searshc.mpuwebservice.bean.ProcessReturnIn5Request;
import com.searshc.mpuwebservice.bean.ProcessReturnIn5Response;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ShopInReportDTO;
import com.searshc.mpuwebservice.bean.ShopinItemDetailDTO;
import com.searshc.mpuwebservice.bean.ShopinRequestDTO;
import com.searshc.mpuwebservice.bean.VehicleInfo;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.MCPDBDAO;
import com.searshc.mpuwebservice.dao.NotDeliverServiceDAO;
import com.searshc.mpuwebservice.dao.PickUpServiceDAO;
import com.searshc.mpuwebservice.dao.StoreLocalTimeServiceDAO;
import com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.processor.ShopInServiceProcessor;
import com.searshc.mpuwebservice.util.SHCDateUtils;
import com.searshc.mpuwebutil.util.ConversionUtils;

@DJService("shopInServiceProcessor")
public class ShopInServiceProcessorImpl implements ShopInServiceProcessor{
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(ShopInServiceProcessorImpl.class);
	
	@Autowired
	private MCPDBDAO mcpdbdao;
	@Autowired
	private StoreLocalTimeServiceDAO storeLocalTimeServiceDAO;
	@Autowired
	private NotDeliverServiceDAO notDeliverServiceDAO;
	@Autowired
	private PickUpServiceProcessorImpl pickUpServiceProcessorImpl;
	@Autowired
	private MPUWebServiceProcessor mpuWebServiceProcessorImpl;
	@Autowired
	private PickUpServiceDAO pickUpServiceDAO;
	@Autowired
	private AssociateActivityServicesProcessor activityServicesProcessor;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public MpuPickUpReportResposne insertRecordForShopInReport(ShopinRequestDTO shopinRequestDTO) throws DJException {		
		logger.info("Inside","ShopInServiceProcessorImpl.insertRecordForShopInReport");
		MpuPickUpReportResposne mpuPickUpReportResposne = new MpuPickUpReportResposne();
		int shopinReportStatus = 0;
		List<ShopInReportDTO>ShopInReportDTOList = null;
		TreeMap<String,ShopinItemDetailDTO> mapForQtyInfo =  new TreeMap<String,ShopinItemDetailDTO>();
		
		if(shopinRequestDTO!= null){
			Collection<ShopinItemDetailDTO>ShopinItemDetailList = shopinRequestDTO.getCurbsideItems();
			
			for(ShopinItemDetailDTO ShopinItemDetailDTO: ShopinItemDetailList){
				if(mapForQtyInfo.containsKey(ShopinItemDetailDTO.getItemId())){
					ShopinItemDetailDTO shopInItemFromMap = mapForQtyInfo.get(ShopinItemDetailDTO.getItemId());
					shopInItemFromMap.setReqQuantity(ShopinItemDetailDTO.getReqQuantity()+shopInItemFromMap.getReqQuantity());
					mapForQtyInfo.put(ShopinItemDetailDTO.getItemId(),shopInItemFromMap);
				}else{
					mapForQtyInfo.put(ShopinItemDetailDTO.getItemId(),ShopinItemDetailDTO);
				}
			}
			
			ShopinItemDetailList = mapForQtyInfo.values();
			
			if(ShopinItemDetailList != null){
				ShopInReportDTOList = getShopInReportDTO(ShopinItemDetailList, shopinRequestDTO);
				shopinReportStatus = mcpdbdao.insertShopinReportRecords(ShopInReportDTOList, shopinRequestDTO.getStoreNo());
				logger.info("shopinReportStatus: ", +shopinReportStatus);
			}
		}	
		if(shopinReportStatus!=0){
			mpuPickUpReportResposne.setResponseCode(200);
		}else{
			mpuPickUpReportResposne.setResponseCode(500);
		}
		logger.info("Exit","ShopInServiceProcessorImpl.insertRecordForShopInReport");
		return mpuPickUpReportResposne;		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public MpuPickUpReportResposne updateShopInReport(String storeNo, String salesCheckNumber,String pickedUpQty, String status, String customerName,
			String associateName, String pickupEndTime) throws DJException {
		
		logger.info("Inside","ShopInServiceProcessorImpl.updateShopInReport for salescheck: "+salesCheckNumber);
		MpuPickUpReportResposne mpuPickUpReportResposne = new MpuPickUpReportResposne();
		ShopInReportDTO shopInReportDTO = new ShopInReportDTO();
		shopInReportDTO.setSalescheck(salesCheckNumber);
		shopInReportDTO.setPickedupQuantity(Integer.parseInt(pickedUpQty));
		shopInReportDTO.setStatus(status.replace("_", " "));
		shopInReportDTO.setCustomerName(customerName);
		shopInReportDTO.setAssociateName(associateName);
		shopInReportDTO.setStoreNo(storeNo);
		//JIRA: 26002, Fix Production Issues Status (MPU Platform and MPU Rev) - Oct/27
		//shopInReportDTO.setEndDate(SHCDateUtils.convertToDate(pickupEndTime));
		logger.info("Inside updateShopInReport ","shopInReportDTO.setEndDate: " + storeLocalTimeServiceDAO.getstoreLocalTimeStamp((shopInReportDTO.getStoreNo())));
		shopInReportDTO.setEndDate(storeLocalTimeServiceDAO.getstoreLocalTimeStamp((shopInReportDTO.getStoreNo())));
		int update = mcpdbdao.updateShopinReportRecord(shopInReportDTO);
		if(update !=0){
			mpuPickUpReportResposne.setResponseCode(200);
		}else{
			mpuPickUpReportResposne.setResponseCode(500);
		}
		logger.info("Exit","ShopInServiceProcessorImpl.updateShopInReport for salescheck: "+salesCheckNumber);
		return mpuPickUpReportResposne;
	}
	
	private List<ShopInReportDTO> getShopInReportDTO(Collection<ShopinItemDetailDTO>ShopinItemDetailList,ShopinRequestDTO shopinRequestDTO) throws DJException{
		logger.info("Inside getShopInReportDTO","");
		List<ShopInReportDTO>ShopInReportDTOList = new ArrayList<ShopInReportDTO>();
		String salesCheckNo = null;
		OrderDTO orderDTO = null;
		for(ShopinItemDetailDTO shopinItem: ShopinItemDetailList){	
			ShopInReportDTO shopInReportDTO = new ShopInReportDTO();
			
			String itemId = String.valueOf(shopinItem.getItemId());
			if(StringUtils.contains(itemId, "_")){
				salesCheckNo = itemId.substring(0, Math.min(itemId.length(), 12));
			}else{
				orderDTO = notDeliverServiceDAO.getOrderDetailsByRqdId(shopinRequestDTO.getStoreNo(), itemId);
				if(orderDTO !=null){
					salesCheckNo = orderDTO.getSalescheck();
				}
				
			}		
			int pickupTypeId = mcpdbdao.getMaxShopInPickupId();
			
			shopInReportDTO.setSalescheck(salesCheckNo);
			shopInReportDTO.setStoreNo(shopinRequestDTO.getStoreNo());
			shopInReportDTO.setPickupRequestType(shopinRequestDTO.getAppEnv());
			shopInReportDTO.setReqQuantity(shopinItem.getReqQuantity());
			shopInReportDTO.setPickupId(pickupTypeId+1);
			//JIRA: 26002, Fix Production Issues Status (MPU Platform and MPU Rev) - Oct/27
			logger.info("Inside getShopInReportDTO ","shopInReportDTO.setStartDate: " + storeLocalTimeServiceDAO.getstoreLocalTimeStamp((shopinRequestDTO.getStoreNo())));
			shopInReportDTO.setStartDate(storeLocalTimeServiceDAO.getstoreLocalTimeStamp((shopinRequestDTO.getStoreNo())));
			shopInReportDTO.setDistrict(storeLocalTimeServiceDAO.getDistrict(shopinRequestDTO.getStoreNo()));
			shopInReportDTO.setRegion(storeLocalTimeServiceDAO.getRegion(shopinRequestDTO.getStoreNo()));
			if(orderDTO !=null){
				//JIRA 26002 - Shop in Report MPU platform (set WorkId as 0 if its NULL)
				if(orderDTO.getRqtId() == null || orderDTO.getRqtId().trim().equals("")) {
					shopInReportDTO.setWorkId(0);
				} else {
					shopInReportDTO.setWorkId(Integer.parseInt(orderDTO.getRqtId()));
				}
				
			} else {
				//JIRA 26002 - Shop in Report MPU platform (set WorkId as 0 in steed of default NULL value)
				shopInReportDTO.setWorkId(0);
			}
			ShopInReportDTOList.add(shopInReportDTO);
		}
		logger.info("Exiting getShopInReportDTO","");
		return ShopInReportDTOList;
	}
	
	public List<ShopInReportDTO> fetchRecordsForShopinReport(String dateFrom, String dateTo, String storeNo, String region, String district) throws DJException {
		logger.info("Inside ShopInServiceProcessorImpl.fetchRecordsForShopinReport","");	
		List<ShopInReportDTO> shopInReportDTOList=null;
		shopInReportDTOList =mcpdbdao.fetchRecordsForShopinReport(SHCDateUtils.shopinReportDateFormat(dateFrom), SHCDateUtils.shopinReportDateFormat(dateTo), storeNo, region, district);
		logger.info("Exit ShopInServiceProcessorImpl.fetchRecordsForShopinReport","");	
		return shopInReportDTOList ;
	}
	
	
	public ProcessReturnIn5Response getResponseForRI5Shopin(ProcessReturnIn5Request processReturnIn5Request) throws DJException {	
		logger.info("Inside shopinInServiceProcessorImpl.getResponseForRI5Shopin : ", processReturnIn5Request);
		ProcessReturnIn5Response ri5Response = new ProcessReturnIn5Response(); // Creating Response object
		List<AuthIdResponse> returnAuthIdResponse = new ArrayList<AuthIdResponse>();
		com.searshc.mpuwebservice.bean.ResponseHeader responseHeader = new com.searshc.mpuwebservice.bean.ResponseHeader();
		
		VehicleInfo vehicleInfo = processReturnIn5Request.getVehicleInfo(); // Requests objects
		String storeNo= processReturnIn5Request.getStoreNo();
		String storeFormat = processReturnIn5Request.getStoreFormat();
		String kioskName = processReturnIn5Request.getKioskName();
		String location = processReturnIn5Request.getLocation();
		ri5Response.setNotificationID(processReturnIn5Request.getNotificationID());
		String appEnv = processReturnIn5Request.getAppEnv();
		String sywrId = processReturnIn5Request.getSywrId();
		List<String> authdIds = new ArrayList<String>();
		authdIds = processReturnIn5Request.getReturnAuthIdList().getReturnAuthId();
		
		
		if(authdIds != null && authdIds.size()>0){
			logger.info("size of returnauthid list is :"+authdIds.size(),"");
			for(String authId:authdIds){
				ArrayList<Order> orders=null;
				AuthIdResponse authIdResponse = new AuthIdResponse();				
				try {					
					Order order = pickUpServiceProcessorImpl.searchTenderReturns(authId, storeNo, kioskName, storeFormat);
					if(order !=null){
						orders = insertRI5DataToDb(order, vehicleInfo, processReturnIn5Request.getNotificationID(), storeNo, appEnv, sywrId ,kioskName ,location, storeFormat, authId);
					}
					if(order != null){
						logger.info("total orders return auth id :"+authId +" is :"+orders.size(),"");
						authIdResponse.setReturnAuthId(authId);
						authIdResponse.setResponseDescription("SUCCESS");
						authIdResponse.setResponseCode("200");
					}else{
						logger.info("no orders found for for return auth id :"+authId,"");
						authIdResponse.setReturnAuthId(authId);
						authIdResponse.setResponseDescription("FAILURE");
						authIdResponse.setResponseCode("404");
					}
				} catch (Exception e) {
					logger.error("exception has been occured in getResponseForRI5Shopin method of  ShopinReturnIN5Service for return auth id :"+authId,e);
					authIdResponse.setReturnAuthId(authId);
					authIdResponse.setResponseDescription("FAILURE");
					authIdResponse.setResponseCode("500");
				}
				returnAuthIdResponse.add(authIdResponse);			
			}
			com.searshc.mpuwebservice.bean.AuthIdResponseList responseReturnAuthIdList = new com.searshc.mpuwebservice.bean.AuthIdResponseList();
			responseReturnAuthIdList.setReturnAuthIdResponse(returnAuthIdResponse);
			ri5Response.setResponseReturnAuthIdList(responseReturnAuthIdList);		
			responseHeader.setResponseCode(200);
			responseHeader.setResponseDescription("SUCCESS");
			ri5Response.setResponseHeader(responseHeader);	
			
		}else {			
			com.searshc.mpuwebservice.bean.AuthIdResponseList responseReturnAuthIdList = new com.searshc.mpuwebservice.bean.AuthIdResponseList();
			responseReturnAuthIdList.setReturnAuthIdResponse(returnAuthIdResponse);
			ri5Response.setResponseReturnAuthIdList(responseReturnAuthIdList);		
			responseHeader.setResponseCode(200);
			responseHeader.setResponseDescription("SUCCESS");
			ri5Response.setResponseHeader(responseHeader);			
			logger.info("Something went wrong when processing RI5. Please chek the request sent.", "");
		}
		logger.info("exit from shopinInServiceProcessorImpl.getResponseForRI5Shopin","");
		return ri5Response;		
	}	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public  ArrayList<Order> insertRI5DataToDb(Order order, VehicleInfo vehicleInfo,String notificationId,String storeNo,String appEnv,String sywrId,String kioskName,String location,String storeFormat, String returnAuthCode) throws Exception{		
		ArrayList<Order> orders=new ArrayList<Order>();
		RequestDTO returnRequestDTO = null;
		int shopinReportStatus = 0;
		List<ItemDTO> itemList = new ArrayList<ItemDTO>();		
		List<String>rqtIdToBeUpdated = new ArrayList<String>();
		List<ShopinItemDetailDTO> curbsideItems = new ArrayList<ShopinItemDetailDTO>();
		List<MPUActivityDTO> activityDTOList = new ArrayList<MPUActivityDTO>();		
		List<String> shopInLockerItems = new ArrayList<String>();
		List<RequestDTO> requestDTOList = new ArrayList<RequestDTO>();
		PickUpSelectedItems obj = null;
		Map<String,String> rqtIdOrgJsonMap = new HashMap<String, String>();
		if(order != null && "RETURNIN5".equals(order.getOrderType())){
			
			// insert RI order into db.
	    	 returnRequestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(order);
	    	 requestDTOList.add(returnRequestDTO);
	    	 mpuWebServiceProcessorImpl.createRequest(returnRequestDTO.getOrder(), returnRequestDTO.getCustomer(), returnRequestDTO.getItemList(), returnRequestDTO.getPaymentList(), returnRequestDTO.getTask());	
	    	 orders.add(order);	
	    	 rqtIdToBeUpdated.add(returnRequestDTO.getOrder().getRqtId());		
	    	 
	    	 //Code to insert vehicleInfo for RI5
	    	 if(CollectionUtils.isNotEmpty(rqtIdToBeUpdated) && null !=vehicleInfo){ 	
	    		 pickUpServiceDAO.insertVehiclInfo(vehicleInfo, notificationId, rqtIdToBeUpdated, storeNo);
	    	 }// end for insert vehicleInfo for RI5
	    	 
	    	 
	    	 for (ItemDTO item : returnRequestDTO.getItemList()){	    		 
	    		if(item !=null){
	    			item.setCurrentStatus(MpuWebConstants.OPEN);
	    			itemList.add(item);
	    		}	    		
	    	 }
	    	 
	    	 if(CollectionUtils.isNotEmpty(itemList)){
	    		 for(ItemDTO item : itemList){
	    			 ShopinItemDetailDTO detailDTO = new ShopinItemDetailDTO();
	    			 detailDTO.setItemId(item.getRqdId());
	    			 
	    			 if("0".equals(item.getQty())) {
	    					item.setQty("1");
	    			}    			 
	    			 String qty = item.getItemQuantityActual();
	    			 if(qty !=null){
	    				 detailDTO.setReqQuantity(Integer.parseInt(item.getQty()));
	    			 }
	    			 curbsideItems.add(detailDTO);
	    		 }
	    		 
	    		 ShopinRequestDTO shopinRequestDTO = new ShopinRequestDTO();
		    	 shopinRequestDTO.setVehicleInfo(vehicleInfo);
		    	 shopinRequestDTO.setSywrId(sywrId);
		    	 shopinRequestDTO.setStoreNo(storeNo);
		    	 shopinRequestDTO.setCurbsideItems(curbsideItems);
		    	 shopinRequestDTO.setNotificationID(notificationId);
		    	 shopinRequestDTO.setStoreFormat(storeFormat);
		    	 shopinRequestDTO.setLocation(location);
		    	 shopinRequestDTO.setKioskName(kioskName);
		    	 obj = pickUpServiceProcessorImpl.convertShopinRequestDTOToPickUpSelectedItems(shopinRequestDTO, itemList);  
	    		 
		    	 if(itemList.size()>0 && null !=itemList){
		    		int noOfRows = pickUpServiceDAO.initiatePickUpForItems(itemList,storeNo,shopinRequestDTO.getKioskName());
		    		if (!(noOfRows == 0)){
		    			noOfRows = pickUpServiceDAO.initiatePickUpActivityForItems(itemList, storeNo,"CREATE");
		    			
						List<MPUActivityDTO> activityList= pickUpServiceProcessorImpl.createActivityDTO(obj,shopInLockerItems ,requestDTOList);
		    			
		    			for(MPUActivityDTO activityDTO : activityList){
							activityDTO.setPickup_source(MpuWebConstants.SHOPIN);
							activityDTO.setRequestType(MpuWebConstants.RETURNIN5);
							activityDTO.setReturnAuthCode(returnAuthCode);							
							activityDTOList.add(activityDTO);
						}						
		    			activityServicesProcessor.insertMPUActivityData(activityList);
		    		}	    		 
		    	 }
		    	 
		    	 for(RequestDTO request :requestDTOList){
		    		 if(null!=request.getOrder()){
		    			 rqtIdOrgJsonMap.put(request.getOrder().getRqtId(), request.getOrder().getOriginalJson());
		    		 }
		    	 }
		    	 int rowsUpdated = pickUpServiceDAO.updateOriginalJson(rqtIdOrgJsonMap, obj.getStoreNum());
		    	 logger.info("Original JSON updated successfully with noOfRecords: ", rowsUpdated);		    	 
	    	 }  	 
	    	 
	    	// insert record for shopin RI5 report. 
	 		try {
	 			//Production Issues Status (MPU Platform and MPU Rev) - Oct/27. Added parameter appEnv
	 			logger.info("appEnv to be updated in Shopin report: ", appEnv);
	 			shopinReportStatus = insertShopInReport(returnRequestDTO,appEnv);
	 			logger.info("Shopin RI5 report records updated : ", shopinReportStatus);
	 		} catch (Exception e) {
	 			logger.info("Exception occurred while inserting shopin RI5 record: ", e);	 			
	 		}// end for insert record for shopin RI5 report.
		}
		
		return orders;			
	}
	
	// methdod to insert shopin RI5 Report.
	@Transactional
	private int insertShopInReport(RequestDTO requestDTO, String appEnv) throws Exception{
		int shopinReportStatus = 0;
		List<ShopInReportDTO>ShopInReportDTOList = new ArrayList<ShopInReportDTO>();
		if(null !=requestDTO){
			ShopInReportDTO shopInReportDTO = new ShopInReportDTO();
			int requestedQty = 0;
			List<ItemDTO> itemDTOList= requestDTO.getItemList();
			int pickupTypeId = mcpdbdao.getMaxShopInPickupId();			
			shopInReportDTO.setSalescheck(requestDTO.getOrder().getSalescheck());
			shopInReportDTO.setStoreNo(requestDTO.getOrder().getStoreNumber());
			shopInReportDTO.setPickupRequestType(appEnv);
			
			for(ItemDTO itemDTO:itemDTOList){
				String rqQty = itemDTO.getItemQuantityActual();
				if(rqQty !=null){
					requestedQty = requestedQty + Integer.parseInt(rqQty);
				}				
			}			
			
			shopInReportDTO.setReqQuantity(requestedQty);		
			shopInReportDTO.setPickupId(pickupTypeId+1);
			shopInReportDTO.setStartDate(storeLocalTimeServiceDAO.getstoreLocalTimeStamp(requestDTO.getOrder().getStoreNumber()));
			shopInReportDTO.setDistrict(storeLocalTimeServiceDAO.getDistrict(requestDTO.getOrder().getStoreNumber()));
			shopInReportDTO.setRegion(storeLocalTimeServiceDAO.getRegion(requestDTO.getOrder().getStoreNumber()));
			ShopInReportDTOList.add(shopInReportDTO);
			shopinReportStatus = mcpdbdao.insertShopinReportRecords(ShopInReportDTOList, requestDTO.getOrder().getStoreNumber());
		}
		return shopinReportStatus;
	}
	
	public void pushNotificationToShopIn(String rqtId, String salesCheck,
			String orderStatus, String searsSalesId, String associateName,
			String timeTakenForPickup,String storeNumber) throws DJException {
		
		int status = 0;
		
		String notificationId = fetchNotificationIdByRqtId(rqtId,storeNumber);
		
		if(notificationId !=null){
			
			String xmlString = "{" + "\"salesCheckNumber\": \"" + salesCheck+ "\"," + "\"status\": \"" + orderStatus + "\","+ 
					"\"pushNotificationID\": \"" + notificationId + "\","+ "\"associateID\": \"" + searsSalesId + "\","+"\"associateName\": \"" 
							+ associateName + "\","+"\"timeTakenForPickup\": \"" + timeTakenForPickup + "\""+"}";	
					
			status = pickUpServiceDAO.pushNotificationToShopInDAO(xmlString);
		}	
		logger.info("Exiting from pushNotificationToShopIn service with status : ", status);		
	}
	
	
	public String fetchNotificationIdByRqtId(String rqtId,String storeNumber) throws DJException{
		logger.info("Inside shopinServiceProcessorImpl.fetchNotificationIdByRqtId for rqtId: "+rqtId +" storeNumber :"+storeNumber, "");
		return pickUpServiceDAO.fetchNotificationIdByRqtId(rqtId, storeNumber);		
	}
}
