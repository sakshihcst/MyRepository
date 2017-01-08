package com.searshc.mpuwebservice.util;

import static com.searshc.mpuwebservice.constant.MpuWebConstants.DEFAULT_ASSOCIATE;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.ActivityDTO;
import com.searshc.mpuwebservice.bean.CustomerDTO;
import com.searshc.mpuwebservice.bean.IdentifierDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.vo.StoreworkinhHours;
import com.searshc.mpuwebutil.constant.MPUWEBUtilConstants;


/**
 * This class is used for providing utility methods to be used in MPU WebService
 * @author Tata Consultancy Services
 */
public class MPUWebServiceUtil {
	
	/**
	 * This method is used for Populating CloseMap
	 * @param StoreworkinhHours
	 * @return Map<String,String>
	 */
	public static Map<String,String> getCloseMap(StoreworkinhHours workingHours){
		Map<String,String> closeMap=new HashMap<String,String>();
		if(workingHours.getMonclosetime()!=null){
			closeMap.put("2",workingHours.getMonclosetime());
		}
		else{
			closeMap.put("2", workingHours.getWeekdayclosetime());
		}
		
		if(workingHours.getTueclosetime()!=null){
			closeMap.put("3", workingHours.getTueclosetime());
		}
		else{
			closeMap.put("3", workingHours.getWeekdayclosetime());
			
		}
		if(workingHours.getWedclosetime()!=null){
			closeMap.put("4", workingHours.getWedclosetime());
		}
		else{
			closeMap.put("4", workingHours.getWeekdayclosetime());
		}
		if(workingHours.getThuclosetime()!=null){
			closeMap.put("5", workingHours.getThuclosetime());
		}
		else{
			closeMap.put("5", workingHours.getWeekdayclosetime());
		}
		if(workingHours.getFriclosetime()!=null){
			closeMap.put("6", workingHours.getFriclosetime());
		}
		else{
			closeMap.put("6", workingHours.getWeekdayclosetime());
		}
		if(workingHours.getSatclosetime()!=null){
			closeMap.put("7", workingHours.getSatclosetime());
		}
		else{
			closeMap.put("7", workingHours.getWeekdayclosetime());
			
		}
		if(workingHours.getSunclosetime()!=null){
			closeMap.put("1", workingHours.getSunclosetime());	
		}
		else{
			closeMap.put("1", workingHours.getWeekdayclosetime());
		}
		return closeMap;
	}
	
	
	
	
	/**
	 * This method is used for Populating OpenMap
	 * @param StoreworkinhHours
	 * @return Map<String,String>
	 */
	public static Map<String,String> getOpenMap(StoreworkinhHours workingHours){
		Map<String,String> openMap=new HashMap<String,String>();
		if(workingHours.getMonopentime()!=null){
			openMap.put("2",workingHours.getMonopentime());
		}
		else{
			openMap.put("2", workingHours.getWeekdayopentime());
		}
		
		if(workingHours.getTueopentime()!=null){
			openMap.put("3", workingHours.getTueopentime());
		}
		else{
			openMap.put("3", workingHours.getWeekdayopentime());
			
		}
		if(workingHours.getWedopentime()!=null){
			openMap.put("4", workingHours.getWedopentime());
		}
		else{
			openMap.put("4", workingHours.getWeekdayopentime());
		}
		if(workingHours.getThuopentime()!=null){
			openMap.put("5", workingHours.getThuopentime());
		}
		else{
			openMap.put("5", workingHours.getWeekdayopentime());
		}
		if(workingHours.getFriopentime()!=null){
			openMap.put("6", workingHours.getFriopentime());
		}
		else{
			openMap.put("6", workingHours.getWeekdayopentime());
		}
		if(workingHours.getSatopentime()!=null){
			openMap.put("7", workingHours.getSatopentime());
		}
		else{
			openMap.put("7", workingHours.getWeekdayopentime());
			
		}
		if(workingHours.getSunopentime()!=null){
			openMap.put("1", workingHours.getSunopentime());	
		}
		else{
			openMap.put("1", workingHours.getWeekdayopentime());
		}
		return openMap;
	}
	
	/**
	 * This method is used for generating Response Entity.
	 * @param responseDTO
	 * @param state
	 * @return ResponseEntity<ResponseDTO>
	 */
	public static ResponseEntity<ResponseDTO> getResponseEntity(ResponseDTO responseDTO,boolean state){
		ResponseEntity<ResponseDTO> respEntity = null;
		if(state){
			responseDTO.setResponseCode("200");
			responseDTO.setResponseDesc(MpuWebConstants.SUCCESS_200);
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
		}else{
			responseDTO.setResponseCode("500");
			responseDTO.setResponseDesc(MpuWebConstants.ERROR_500);
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respEntity;
	}
	
	
	/**
	 * This method is used for generating Response Entity.
	 * @param responseDTO
	 * @param state
	 * @return ResponseEntity<ResponseDTO>
	 */
	public static ResponseEntity<ResponseDTO> getResponseEntity(ResponseDTO responseDTO,
			String responseCode,String responseDesc,Object responseBody,boolean state){
		ResponseEntity<ResponseDTO> respEntity = null;
		responseDTO.setResponseBody(responseBody);
		responseDTO.setResponseCode(responseCode);
		responseDTO.setResponseDesc(responseDesc);
		if(state){
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
		}else{
			respEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respEntity;
	}
	
	/**
	 * This method is used for Converting IdentifierDTO to CustomerDTO
	 * @param List<IdentifierDTO> identifierDTOs
	 * @return List<CustomerDTO> 
	 */
	public static List<CustomerDTO> convertIdentifierToCustomer(List<IdentifierDTO> identifierDTOs){
		List<CustomerDTO> customerDTOs = null;
		if(null !=identifierDTOs ){
			customerDTOs = new ArrayList<CustomerDTO>();
			for(int counter=0;counter <identifierDTOs.size();counter = counter+11 ){
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setFirstName(identifierDTOs.get(counter).getValue());
				customerDTO.setLastName(identifierDTOs.get(counter+1).getValue());
				customerDTO.setEmail(identifierDTOs.get(counter+2).getValue());
				customerDTO.setPhone(identifierDTOs.get(counter+3).getValue());
				customerDTO.setSywNumber(identifierDTOs.get(counter+4).getValue());
				customerDTO.setSywStatus(identifierDTOs.get(counter+5).getValue());
				customerDTO.setAddress1(identifierDTOs.get(counter+6).getValue());
				customerDTO.setAddress2(identifierDTOs.get(counter+7).getValue());
				customerDTO.setZipcode(identifierDTOs.get(counter+8).getValue());
				customerDTO.setCustomerId(identifierDTOs.get(counter+9).getValue());
				customerDTO.setAccountNumber(identifierDTOs.get(counter+10).getValue());
				customerDTOs.add(customerDTO);
			}
		}
		return customerDTOs;
	}
	
	/**
	 * This method is used for Converting Activity Bean to ActionDTO
	 * @param actionDTO
	 * @param seqNum
	 * @param activityDesc
	 * @return
	 */
	public static ActivityDTO convertActionToActivity(ItemDTO actionDTO,String seqNum,String activityDesc){
		ActivityDTO activityDTO=new ActivityDTO();
		activityDTO.setActionSeq(seqNum);
		activityDTO.setActivityDescription(activityDesc);
		if(actionDTO.getAssociateID()!=null){
			activityDTO.setAssignedUser(actionDTO.getAssociateID());
		}else{
			activityDTO.setAssignedUser(actionDTO.getAssignedUser());
		}
		if(null!=actionDTO.getCreatedBy()){
			activityDTO.setCreatedBy(actionDTO.getCreatedBy());
		}else{
			activityDTO.setCreatedBy(MpuWebConstants.CREATED_BY);
		}
		activityDTO.setType(actionDTO.getRequestType());
		activityDTO.setFromLocation(actionDTO.getFromLocation());
		activityDTO.setToLocation(actionDTO.getToLocation());
		activityDTO.setStore(actionDTO.getStoreNumber());
		return activityDTO; 
	}
	
	
	public static String getDNSForStore(String storeNumber,String storeFormat) {

		String dnsForStore = "";
		/*logger.info("Store Number  " + storeNumber);
		logger.info("storeFormat  " + storeFormat);*/
		
			if (storeNumber.length() < 4) {
				storeNumber = StringUtils.leftPad(storeNumber, 4, '0');
			} else if (storeNumber.length() > 4) {
				storeNumber = StringUtils.removeStart(storeNumber, "0");
			}
			
			
			/*if(storeFormat==null||"".equals(storeFormat)){
				storeFormat="SearsRetail";
			}*/
			//Check if application is getting DSN name for QA
			
					if (! "prod".equalsIgnoreCase(System.getProperty(MpuWebConstants.ENVIRONMENT)) 
							&& ! "beta".equalsIgnoreCase(System.getProperty(MpuWebConstants.ENVIRONMENT))
							&& !"stress".equalsIgnoreCase(System.getProperty(MpuWebConstants.ENVIRONMENT))) {
						dnsForStore =PropertyUtils.getProperty("ticketing.printer.sears.dns."+storeNumber);
					}
			
				else if (storeFormat.equals(PropertyUtils.getProperty("Sears.StoreFormat"))) {
					dnsForStore =PropertyUtils.getProperty("ticketing.printer.sears.dns");
					dnsForStore = dnsForStore.replace("{0}", storeNumber);
				} else if (storeFormat.equals(PropertyUtils.getProperty("Kmart.StoreFormat"))) {
					dnsForStore = PropertyUtils.getProperty("ticketing.printer.kmart.dns");
					dnsForStore = dnsForStore.replace("{0}", storeNumber);
				}
				
			
			
		return dnsForStore;
	}
	
	 public static String getdnsForSOA(String key){
	        String dns="";
	        try{
	                dns=PropertyUtils.getProperty(key);
	                
	        }catch(Exception exception){
	            exception.printStackTrace();
	        }
	        return dns;
	    }
	public static String nullToBlank(String s){
		if(s==null){
			return "";
		}
		
		else {
			return s;
		}
	}
	
	/**
	 * This method is used to  convert BigDecimal from null to 0
	 * @param BigDecimal
	 * @return BigDecimal
	 */
	public static BigDecimal nullToZero(BigDecimal s){
		if(s==null){
			return new BigDecimal(0);
		}
		
		else {
			return s;
		}
	}
	
	/**
	 * This method is used to check null or empty string
	 * @param String
	 * @return String
	 */
	public static boolean checkNullOrEmpty(String s){
		if(s == null || s.trim().equals("") || s.equalsIgnoreCase("null")){
			return false;
		}
		
		else {
			return true;
		}
	}
	
	public static String getFormattedStoreNo(String storeNo){
		return StringUtils.leftPad(storeNo, 5, '0');
	}
	
	
	public static String getFormattedAssociateId(String associateId){
		if(associateId!=null){
			return StringUtils.leftPad(associateId, 6, '0');
		}else{
			return DEFAULT_ASSOCIATE;
		}
	}
	
	/**
	 * This method is used to  convert date from "yyyy-MM-dd hh:mm:ss" to "MM/dd/yyyy HH:mm:ss"
	 * @param String date
	 * @return String formatedDate
	 */
	public static String getFormattedDate(String date){
		String formatedDate = "";
		if(date != null && !date.equals("null") && !date.equals("")){
			try{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date tempDate = format.parse(date);
				format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				formatedDate = format.format(tempDate);
			} catch (Exception e) { }
		}
		return formatedDate;
	}
	
	/**
	 * This method is used to  format DivItemSkull
	 * @param String date
	 * @return String formatedDate
	 */
	public static String getFormattedDivItemSkull(String div, String item, String skull){
		StringBuffer divItemSkull = new StringBuffer();
		
		if(div == null || div.equalsIgnoreCase("null") || div.trim().equals("")) {
			divItemSkull.append("000");
		} else {
			divItemSkull.append(StringUtils.leftPad(div, 3, '0'));
		}
		divItemSkull.append("/");
		
		if(item == null || item.equalsIgnoreCase("null") || item.trim().equals("")) {
			divItemSkull.append("00000000");
		} else {
			divItemSkull.append(StringUtils.leftPad(item, 8, '0'));
		}
		divItemSkull.append("/");
		
		if(skull == null || skull.equalsIgnoreCase("null") || skull.trim().equals("")) {
			divItemSkull.append("000");
		} else {
			divItemSkull.append(StringUtils.leftPad(skull, 3, '0'));
		}
		return divItemSkull.toString();
	}
	
//	public String getDNSForStore() {
//
//		String dnsForStore = "";
//		logger.info("Store Number  " + storeNumber);
//		logger.info("storeFormat  " + storeFormat);
//		try {
//			if (storeNumber.length() < 4) {
//				storeNumber = StringUtils.leftPad(storeNumber, 4, '0');
//			} else if (storeNumber.length() > 4) {
//				storeNumber = StringUtils.removeStart(storeNumber, "0");
//			}
//			//Check if application is getting DSN name for QA
//			if(propertiesImpl.getValueById("GetDSNForQA").equalsIgnoreCase("N"))
//			{
//				if (storeFormat.equals(propertiesImpl.getValueById("Sears.StoreFormat")) || storeFormat.equals(propertiesImpl.getValueById("SGD.StoreFormat"))) {
//					dnsForStore = propertiesImpl.getValueById("ticketing.printer.sears.dns");
//				} else if (storeFormat.equals(propertiesImpl.getValueById("Kmart.StoreFormat"))) {
//					dnsForStore = propertiesImpl.getValueById("ticketing.printer.kmart.dns");
//				}
//				dnsForStore = dnsForStore.replace("{0}", storeNumber);
//			}
//			else
//			{
//				dnsForStore = getDSNForQA(storeNumber);
//			}
//			logger.info("DNS For Store " + dnsForStore);
//		} catch (GenericUtilException e) {
//			logger.error("Errro while getting the DNS details for store" + e.getMessage());
//		}
//		return dnsForStore;
//	}
	
	public static DJException getDJExceptionFromException (Exception ex) {
		return new DJException(MPUWEBUtilConstants.UNKNOWN_ERROR_CODE, MPUWEBUtilConstants.UNKNOWN_ERROR_CODE , MPUWEBUtilConstants.ERROR_MESSAGE_GEN + MPUWEBUtilConstants.UNKNOWN_ERROR_MESSAGE);
	}
	
	public static Boolean revIsStoreTrasferWebOrder (String salesCheckNumber,String requestType) {
		if(requestType=="H&G"){
			return false;
		}
		String salescheckNo = salesCheckNumber; 
		String orderStoreNo = "09300";
		if(salescheckNo != null && salescheckNo.length() >= 5){
			orderStoreNo = salescheckNo.substring(0, 5);
		}
		if (MpuWebConstants.ONLINESTORELIST.indexOf(orderStoreNo) == -1) {
			return true;
		} 
		return false;
		
	}

}
