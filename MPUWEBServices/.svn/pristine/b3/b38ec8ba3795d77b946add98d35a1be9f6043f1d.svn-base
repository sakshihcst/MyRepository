package com.searshc.mpuwebservice.processor.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.NotDeliverReportDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.dao.KioskSOADao;
import com.searshc.mpuwebservice.dao.NotDeliverServiceDAO;
import com.searshc.mpuwebservice.dao.StoreLocalTimeServiceDAO;
import com.searshc.mpuwebservice.processor.AssociateActivityServicesProcessor;
import com.searshc.mpuwebservice.processor.NotDeliverServiceProcessor;
import com.searshc.mpuwebservice.util.PropertyUtils;
import com.searshc.mpuwebservice.util.SHCDateUtils;

@Service("notDeliverServiceProcessorImpl")
public class NotDeliverServiceProcessorImpl implements NotDeliverServiceProcessor {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(NotDeliverServiceProcessorImpl.class);
	
	@Autowired
	@Qualifier("notDeliverServiceDAO")
	NotDeliverServiceDAO notDeliverServiceDAO;
	
	@Autowired
	KioskSOADao kioskSOADao;
	
	@Autowired
	StoreLocalTimeServiceDAO storeLocalTimeServiceDAO;
	
	@Autowired
	@Qualifier("associateActivityServicesProcessorImpl")
	private AssociateActivityServicesProcessor associateActivityServicesProcessorImpl;
	
	public List<NotDeliverReportDTO> fetchRecordsForNotDeliverReport(String dateFrom, String dateTo, String storeNo) throws DJException {
		
		logger.info("Inside NotDeliverServiceProcessorImpl.fetchRecordsForNotDeliverReport for dateFrom: ", dateFrom+" dateTo: "+dateTo+" storeNo: "+storeNo);
		
		List<NotDeliverReportDTO> notDeliverReportDTOs = null;
		
		if(storeNo == null || storeNo.equals("") || storeNo.equals("00000")){
			storeNo = "AllStore";
		}		
		
		notDeliverReportDTOs  = notDeliverServiceDAO.getNotDeliverReportRecords(SHCDateUtils.notDlvrReportDateFormat(dateFrom), SHCDateUtils.notDlvrReportDateFormat(dateTo), storeNo);
		
		logger.info("Exit NotDeliverServiceProcessorImpl.fetchRecordsForNotDeliverReport for dateFrom: ", dateFrom+" dateTo: "+dateTo+" storeNo: "+storeNo);
		
		return notDeliverReportDTOs;
	}

	
	public int insertNotDeliverReasonCodeToDejService(String notDeliverReason, String rqdId, String storeNumber, String requestedQty, String deliverQty) throws DJException {
		
		logger.info("Inside NotDeliverServiceProcessorImpl.insertNotDeliverReasonCodeToDejService for notDeliverReason: ", notDeliverReason+" rqdId: "+rqdId+" storeNumber: "+storeNumber+ "requestedQty: "+ requestedQty + "deliverQty: "+ deliverQty);
		String message = "";
		int responseCode = 0;
		HashMap<String, String> notDeliverReasonCodeMap = new HashMap<String, String>();
		OrderDTO orderDTO = null ;
		orderDTO = notDeliverServiceDAO.getOrderDetailsByRqdId(storeNumber, rqdId);
		//Below method call changed to fix JIRA-25355
		//ItemDTO itemDTO = kioskSOADao.getItemDetail(Integer.parseInt(rqdId));
		ItemDTO itemDTO = notDeliverServiceDAO.getItemDetailsByRqdId(storeNumber, rqdId);
		if(orderDTO !=null && itemDTO !=null){
			String associateName = null;
			if(orderDTO.getAssociate_id() != null && !orderDTO.getAssociate_id().equals("")){
			logger.info("Inside NotDeliverServiceProcessorImpl.insertNotDeliverReasonCodeToDejService : associate Id : ",orderDTO.getAssociate_id());
			associateName = associateActivityServicesProcessorImpl.getUserName(orderDTO.getAssociate_id());
			logger.info("associate name for associate id :", associateName + ","+orderDTO.getAssociate_id());
			}
			message = orderDTO.getOriginalIdentifier()+"_"+ itemDTO.getDivNum()+itemDTO.getItem()+itemDTO.getSku() + "_" + requestedQty + "_" + deliverQty + "_" + notDeliverReason + "_" + orderDTO.getCustomer_name() + "_" + associateName;
			logger.info("Inside NotDeliverServiceProcessorImpl.insertNotDeliverReasonCodeToDejService with msg :"+message, "");
			
			String appName = PropertyUtils.getProperty("NOT_DELIVER_APP_NAME");
			notDeliverReasonCodeMap.put("appName", appName);			
			
			String dejApplEventTypeName = PropertyUtils.getProperty("NOT_DELIVER_DEJ_EVENT_TYPE");
			notDeliverReasonCodeMap.put("dejApplEventTypeName",	dejApplEventTypeName);			
			
			String deviceId =  PropertyUtils.getProperty("PUBLISHER_DEVICE_NAME");
			notDeliverReasonCodeMap.put("deviceId",deviceId);			
			
			String storeEventTimeStamp= storeLocalTimeServiceDAO.getstoreLocalTimeStamp(storeNumber).toString();
			notDeliverReasonCodeMap.put("eventTimeStamp",storeEventTimeStamp);			
			
			notDeliverReasonCodeMap.put("message",message);				
			notDeliverReasonCodeMap.put("storeNumber",storeNumber);		
			
			//String userName = orderDTO.getAssociate_id();
			//String userName = associateActivityServicesProcessorImpl.getUserName(orderDTO.getAssociate_id());
			notDeliverReasonCodeMap.put("userName", associateName);
			
			logger.info("appName: " + appName + " dejApplEventTypeName: " + dejApplEventTypeName + " deviceId: " + deviceId + " storeEventTimeStamp: " + storeEventTimeStamp + " userName: " + associateName,"");
			
			responseCode = notDeliverServiceDAO.insertNotDeliverReasonCodeToDejService(notDeliverReasonCodeMap);
		}		
		logger.info("Exit NotDeliverServiceProcessorImpl.insertNotDeliverReasonCodeToDejService for notDeliverReason: ", notDeliverReason+" rqdId: "+rqdId+" storeNumber: "+storeNumber);
		return responseCode;
	}

	
	
	

}
