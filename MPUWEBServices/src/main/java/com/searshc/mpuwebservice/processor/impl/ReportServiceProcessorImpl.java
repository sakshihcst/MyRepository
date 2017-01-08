package com.searshc.mpuwebservice.processor.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DIRECT_PICKUP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.HELP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REPAIR_DROPOFF;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REPAIR_PICKUP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RETURNED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RETURNIN5;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STAGED_PICKUP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sears.dej.interfaces.vo.UserVO;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.HGEntityVO;
import com.searshc.mpuwebservice.bean.HGSummary;
import com.searshc.mpuwebservice.bean.MpuReportDetailVO;
import com.searshc.mpuwebservice.bean.PickupReportDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ShopInReportDTO;
import com.searshc.mpuwebservice.bean.StageOrdersReportDTO;
import com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO;
import com.searshc.mpuwebservice.dao.ReportServiceDAO;
import com.searshc.mpuwebservice.processor.PickUpServiceProcessor;
import com.searshc.mpuwebservice.processor.ReportServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;

/**
 * 
 * @author nkumar1
 *
 */
@Service("reportServiceProcessorImpl")
public class ReportServiceProcessorImpl implements ReportServiceProcessor{
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(ReportServiceProcessorImpl.class);
	
	@Autowired
	@Qualifier("reportServiceDAOImpl")
	private ReportServiceDAO reportServiceDAOImpl;
	
	@Autowired
	private AssociateActivityServiceDAO associateActivityServiceDAOImpl;
	
	@Autowired
	PickUpServiceProcessor pickUpServiceProcessor;
	
	

	
	/**
	 * This method returns the Pickup report details
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @param storeNumber String
	 * @return List<PickupReportDTO>
	 * @throws DJException
	 */
	public List<PickupReportDTO> getPickupReport(String reportFromDate,
			String reportToDate, String storeNumber) throws DJException {
		logger.info("Start getPickupReport() for store:"+storeNumber+":reportFromDate:"+reportFromDate+":reportToDate:",reportToDate);
		List<PickupReportDTO> pickupReportDTO=reportServiceDAOImpl.getPickupReport(reportFromDate,reportToDate,storeNumber);
		logger.info("End getPickupReport() for store:"+storeNumber+":reportFromDate:"+reportFromDate+":reportToDate:",reportToDate);
		return pickupReportDTO;
	}
	
	/**
	 * This method returns the StageOrders report details
	 * @param reportFromDate String
	 * @param reportToDate String
	 * @return List<StageOrdersReportDTO>
	 * @throws DJException
	 */
	public List<StageOrdersReportDTO> getStageOrdersReport(String reportFromDate, String reportToDate) throws DJException {
		logger.info("Start getStageOrdersReport() for reportFromDate:"+reportFromDate+":reportToDate:",reportToDate);
		TreeMap<String,StageOrdersReportDTO> stageOrdersReportMap = new TreeMap<String, StageOrdersReportDTO>();
		
		List<Map<String,Object>> pickupForStageOrdersList = reportServiceDAOImpl.getPickupForStageOrdersReport(reportFromDate,reportToDate);
		populatePickupForStageOrders(stageOrdersReportMap,pickupForStageOrdersList);
		
		List<Map<String,Object>> returnForStageOrdersList = reportServiceDAOImpl.getReturnForStageOrdersReport(reportFromDate,reportToDate);
		populateReturnForStageOrders(stageOrdersReportMap,returnForStageOrdersList);
		
		/*List<StageOrdersReportDTO> stageOrdersReportDTO = new ArrayList<StageOrdersReportDTO>();
		populateStageOrders(stageOrdersReportDTO,stageOrdersReportList);*/
		List<StageOrdersReportDTO> stageOrdersReportDTO=new ArrayList<StageOrdersReportDTO>(stageOrdersReportMap.values());
		logger.info("End getStageOrdersReport() for reportFromDate:"+reportFromDate+":reportToDate:",reportToDate);
		return stageOrdersReportDTO;
	}
	
	/**
	 * This method populates total pickup for StageOrders report
	 * @param TreeMap<String, StageOrdersReportDTO>
	 * @param List<Map<String, Object>> pickupForStageOrdersList
	 * @return void
	 */
	private void populatePickupForStageOrders(TreeMap<String, StageOrdersReportDTO> stageOrdersReportMap,List<Map<String, Object>> pickupForStageOrdersList) {
		String storeNumber=null;
		BigDecimal directPickup;
		BigDecimal stagedPickup;
		BigDecimal totalPickup;
		StageOrdersReportDTO stageOrdersReportDTO=null;
		for(Map<String, Object> pickupForStageOrdersMap:pickupForStageOrdersList){
			if(MPUWebServiceUtil.checkNullOrEmpty(String.valueOf((Integer)pickupForStageOrdersMap.get(STORE_NUMBER.name())))) {
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)pickupForStageOrdersMap.get(STORE_NUMBER.name())));
				String searchKey=storeNumber;
				if(!stageOrdersReportMap.containsKey(searchKey)){
					stageOrdersReportDTO=new StageOrdersReportDTO();
					directPickup = MPUWebServiceUtil.nullToZero((BigDecimal)pickupForStageOrdersMap.get(DIRECT_PICKUP.name()));
					stagedPickup = MPUWebServiceUtil.nullToZero((BigDecimal)pickupForStageOrdersMap.get(STAGED_PICKUP.name()));
					totalPickup = directPickup.add(stagedPickup);
					stageOrdersReportDTO.setStoreNo(storeNumber);
					stageOrdersReportDTO.setDirectPickup(directPickup);
					stageOrdersReportDTO.setStagedPickup(stagedPickup);
					stageOrdersReportDTO.setTotalPickup(totalPickup);
					stageOrdersReportMap.put(searchKey, stageOrdersReportDTO);
				}
			}
		}
	}
	
	/**
	 * This method populates total returns and return in five for StageOrders report
	 * @param TreeMap<String, StageOrdersReportDTO>
	 * @param List<Map<String, Object>>
	 * @return viod
	 */
	private void populateReturnForStageOrders(TreeMap<String, StageOrdersReportDTO> stageOrdersReportMap,List<Map<String, Object>> pickupForStageOrdersList) {
		String storeNumber=null;
		BigDecimal directPickup;
		BigDecimal stagedPickup;
		BigDecimal totalPickup;
		StageOrdersReportDTO stageOrdersReportDTO=null;
		for(Map<String, Object> returnForStageOrdersMap:pickupForStageOrdersList){
			if(MPUWebServiceUtil.checkNullOrEmpty(String.valueOf((Integer)returnForStageOrdersMap.get(STORE_NUMBER.name())))){ 
				storeNumber=MPUWebServiceUtil.getFormattedStoreNo(String.valueOf((Integer)returnForStageOrdersMap.get(STORE_NUMBER.name())));
				directPickup = MPUWebServiceUtil.nullToZero((BigDecimal)returnForStageOrdersMap.get(DIRECT_PICKUP.name()));
				stagedPickup = MPUWebServiceUtil.nullToZero((BigDecimal)returnForStageOrdersMap.get(STAGED_PICKUP.name()));
				totalPickup = directPickup.add(stagedPickup);
				String searchKey=storeNumber;
				stageOrdersReportDTO=getStageOrdersReportDTO(stageOrdersReportMap,storeNumber,directPickup,stagedPickup,totalPickup,searchKey);
				stageOrdersReportDTO.setReturned(MPUWebServiceUtil.nullToZero((BigDecimal)returnForStageOrdersMap.get(RETURNED.name())));
				stageOrdersReportDTO.setReturnInFive(MPUWebServiceUtil.nullToZero((BigDecimal)returnForStageOrdersMap.get(RETURNIN5.name())));
				stageOrdersReportDTO.setHelp(MPUWebServiceUtil.nullToZero((BigDecimal)returnForStageOrdersMap.get(HELP.name())));
				stageOrdersReportDTO.setRepairPickup(MPUWebServiceUtil.nullToZero((BigDecimal)returnForStageOrdersMap.get(REPAIR_PICKUP.name())));
				stageOrdersReportDTO.setRepairDropOff(MPUWebServiceUtil.nullToZero((BigDecimal)returnForStageOrdersMap.get(REPAIR_DROPOFF.name())));
				stageOrdersReportMap.put(searchKey,stageOrdersReportDTO);
			}
		}
		
	}
	
	/**
	 * This method returns StageOrdersReportDTO which is in map otherwise it will create a new DTO object
	 * @param Map<String, StageOrdersReportDTO>
	 * @param String storeNumber
	 * @param BigDecimal directPickup
	 * @param BigDecimal stagedPickup
	 * @param BigDecimal totalPickup
	 * @param String searchKey
	 * @return StageOrdersReportDTO
	 */
	private StageOrdersReportDTO getStageOrdersReportDTO(Map<String, StageOrdersReportDTO> stageOrdersReportMap, String storeNumber, BigDecimal directPickup, BigDecimal stagedPickup, BigDecimal totalPickup,String searchKey){
		StageOrdersReportDTO stageOrdersReportDTO=null;
		
		if(stageOrdersReportMap.containsKey(searchKey)){
			stageOrdersReportDTO=stageOrdersReportMap.get(searchKey);
		}else{
			stageOrdersReportDTO=new StageOrdersReportDTO();	
			stageOrdersReportDTO.setStoreNo(storeNumber);
			stageOrdersReportDTO.setDirectPickup(directPickup);
			stageOrdersReportDTO.setStagedPickup(stagedPickup);
			stageOrdersReportDTO.setTotalPickup(totalPickup);
		}
		return stageOrdersReportDTO;
	}
	
	/**
	 * This method populates total help requests placed in store into associatesActivityMap
	 * @param associatesActivityMap TreeMap<String, ActivityReportDTO>
	 * @param helpRequestList List<Map<String, Object>>
	 * @param reportDate String
	 */
	private void populateStageOrders(List<StageOrdersReportDTO> stageOrdersReportDTO, List<Map<String,Object>> stageOrdersReportList) {
		StageOrdersReportDTO tempStageOrdersReportDTO;
		String storeNumber = "";
		BigDecimal directPickup;
		BigDecimal stagedPickup;
		for(Map<String, Object> stageOrdersReportMap:stageOrdersReportList){
			storeNumber = String.valueOf((Integer)stageOrdersReportMap.get(STORE_NUMBER.name()));
			if(storeNumber != null && !storeNumber.trim().equals("") && !storeNumber.equalsIgnoreCase("null")){
			tempStageOrdersReportDTO = new StageOrdersReportDTO();
			tempStageOrdersReportDTO.setStoreNo(MPUWebServiceUtil.getFormattedStoreNo(storeNumber));
			directPickup = (BigDecimal)stageOrdersReportMap.get(DIRECT_PICKUP.name());
			stagedPickup = (BigDecimal)stageOrdersReportMap.get(STAGED_PICKUP.name());
			tempStageOrdersReportDTO.setDirectPickup(directPickup);
			tempStageOrdersReportDTO.setStagedPickup(stagedPickup);
			tempStageOrdersReportDTO.setTotalPickup(directPickup.add(stagedPickup));
			stageOrdersReportDTO.add(tempStageOrdersReportDTO);
			}
		}
	}

	public HGSummary getHGOrdersReport(String reportFromDate,String reportToDate, String store) throws DJException {
		logger.info("getHGOrdersReport entering", "reportFromDate"+ reportFromDate +"reportToDate"+ reportToDate +"store"+store);
		HGSummary hgSummary = new HGSummary();
		List<Map<String,Object>> hgOrdersList = reportServiceDAOImpl.getHGOrdersReport(reportFromDate, reportToDate, store);
		logger.info("hgOrdersList", hgOrdersList);
		if(!CollectionUtils.isEmpty(hgOrdersList) &&  hgOrdersList.size()>0){
			hgSummary.setHGEntityVOList(populatePickupForHGOrders(hgOrdersList));	
		}
		logger.info("hgsummary", hgSummary);
		logger.info("getHGOrdersReport", "exiting");
			return hgSummary;
	}
	
	private List<HGEntityVO> populatePickupForHGOrders(List<Map<String, Object>> hgOrdersList) throws DJException{
		HGEntityVO hgEntityVO = null;
		List<HGEntityVO> hgEntityVOs = new ArrayList<HGEntityVO>();
		for(Map<String, Object> map : hgOrdersList){
			hgEntityVO=new HGEntityVO();
			hgEntityVO.setCreateDate(map.get("create_timestamp").toString());
			hgEntityVO.setDiv(map.get("div_num")!=null ? map.get("div_num").toString() : "");
			hgEntityVO.setItem(map.get("item")!=null ? map.get("item").toString() : "");
			hgEntityVO.setItemStatus(map.get("status")!=null ? map.get("status").toString() : "");
			hgEntityVO.setQuantity(map.get("qty")!=null ? map.get("qty").toString() : "");
			hgEntityVO.setRequestNum(map.get("request_number")!=null ? map.get("request_number").toString() : "");
			hgEntityVO.setSeq(map.get("item_seq")!=null ? map.get("item_seq").toString() : "");
			hgEntityVO.setSku(map.get("sku")!=null ? map.get("sku").toString() : "");
			
			hgEntityVO.setAssociate(pickUpServiceProcessor.getAssociateName(map.get("assigned_user")!=null ? map.get("assigned_user").toString() : ""));
			String rqtId = map.get("rqt_id")!=null ? map.get("rqt_id").toString() : "";
			String store = map.get("store_number")!=null ? map.get("store_number").toString() : "";
			
			String openTime=  map.get("create_timestamp")!=null ? map.get("create_timestamp").toString() : "";
			String closeTime= map.get("update_timestamp")!=null ? map.get("update_timestamp").toString() : "";
			String timeElapsed = "";
			hgEntityVO.setCustomer(getCustomerName(rqtId,store));
			hgEntityVO.setStore(store);
			
			hgEntityVO.setTime_open(StringUtils.hasText(openTime) ? getTimeFormat(openTime) : "");
			hgEntityVO.setTime_close(StringUtils.hasText(closeTime) ? getTimeFormat(closeTime) : "");
			try{
				if(StringUtils.hasText(openTime) && StringUtils.hasText(closeTime)){
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date openDate = dateFormat.parse(openTime);
					Date closeDate = dateFormat.parse(closeTime);
					timeElapsed = getElapsedTimeHoursMinutesSeconds(closeDate.getTime() - openDate.getTime());
				}
			}catch(Exception ex){} 
			
			hgEntityVO.setTotal_time_worked(timeElapsed);
			
			hgEntityVOs.add(hgEntityVO);
		}
		return hgEntityVOs;
		
	}
	
	
	private String getCustomerName(String rqtId,String store) throws DJException{
		return reportServiceDAOImpl.getCustomerName(rqtId, store);
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
	
	private String getAssociateName(String ldapId){
		if(StringUtils.hasText(ldapId)){
			UserVO associateInfo = associateActivityServiceDAOImpl.getAssociateInfo(ldapId);
			if(associateInfo!=null){
				return associateInfo.getGivenName()+" "+associateInfo.getLastName(); 
			}
		}
		return "";
	}
	
	private String getTimeFormat(String date ) {
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date2 = dateFormat.parse(date);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd h:mm a");
			return sdf.format(date2);

		}catch(Exception exception){}
		return null;
		
	}

	public List<MpuReportDetailVO> getShopInReport(RequestDTO requestDTO) throws DJException {
		logger.info("getShopInReport", requestDTO);
		String startDate  = requestDTO.getOrder().getStart_time();
		String endDate  = requestDTO.getOrder().getEnd_time();
		String storeNumber  = requestDTO.getOrder().getStoreNumber();
		String storeFormat  = requestDTO.getOrder().getStoreFormat();
		List<MpuReportDetailVO> shopInReportList = reportServiceDAOImpl.getShopInReport(startDate,endDate,storeNumber,storeFormat);
		for(MpuReportDetailVO shopInReport : shopInReportList) {
			if("PICKED_UP".equalsIgnoreCase(shopInReport.getStatus())) {
				shopInReport.setStatus("PICKUP COMPLETED");
			}
			shopInReport.setPickupRequestType("SYW");
			if(null != shopInReport.getAssociateName())
				shopInReport.setAssociateName(pickUpServiceProcessor.getAssociateName(shopInReport.getAssociateName()));
			else
				shopInReport.setAssociateName("");
			
		}
		logger.info("exiting getShopInReport", shopInReportList);
		return shopInReportList;
	}
}
