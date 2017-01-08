package com.searshc.mpuwebservice.dao.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.NotDeliverReportDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.dao.NotDeliverServiceDAO;
import com.searshc.mpuwebservice.mapper.NotDeliverReportMapper;
import com.searshc.mpuwebservice.mapper.OrderDTOMapper;
import com.searshc.mpuwebservice.mapper.RequestMPUTransMapper;
import com.searshc.mpuwebservice.mapper.SOAItemMapper;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;

@Repository("notDeliverServiceDAO")
public class NotDeliverServiceDAOImpl extends DJMPUCommonDAO implements NotDeliverServiceDAO{

	private static transient DJLogger logger = DJLoggerFactory.getLogger(NotDeliverServiceDAOImpl.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	public List<NotDeliverReportDTO> getNotDeliverReportRecords(String dateFrom, String dateTo, String storeNo) throws DJException {
		
		logger.info("Inside NotDeliverServiceDAOImpl.getNotDeliverReportRecords for dateFrom: ", dateFrom+" dateTo: "+dateTo+" storeNo: "+storeNo);
		
		List<NotDeliverReportDTO> notDeliverReportList =null;
		
		String serverURL = PropertyUtils.getProperty("retrieve.mpu.notdeliver.reasoncode.dejservice");
		
		serverURL = serverURL+ "/" + dateFrom + "/" + dateTo + "/" + storeNo;
		
		logger.info("ServerURL for getNotDeliverReportRecords: ",serverURL);
		
		String response = restTemplate.getForObject(serverURL, String.class);
		
		if(!response.isEmpty()){
			logger.info("[retrieveNotDeliverReasonCodeFromDejService] Response String: " + response.toString(),"");
			ObjectMapper mapper= new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				notDeliverReportList = mapper.readValue(response, new TypeReference<List<NotDeliverReportDTO>>(){});
			} catch (JsonParseException e) {
				DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
				throw djException;
			} catch (JsonMappingException e) {
				DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
				throw djException;
			} catch (IOException e) {
				DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
				throw djException;
			}
		}
		else{
			logger.info("[retrieveNotDeliverReasonCodeFromDejService] Error in retrieving notdeliver message code to Dej service:","");
		}		
		logger.info("Exit NotDeliverServiceDAOImpl.getNotDeliverReportRecords for dateFrom: ", dateFrom+" dateTo: "+dateTo+" storeNo: "+storeNo);
		
		return notDeliverReportList;		
	}
	
	public OrderDTO getOrderDetailsByRqdId(String storeNumber, String rqd_Id) throws DJException {
		
		logger.info("Inside NotDeliverServiceDAOImpl.getOrderDetailsByRqdId for dateFrom: ", storeNumber+" rqd_Id: "+rqd_Id);	
		
		String sql = PropertyUtils.getProperty("get.orderdetails.by_req_id");
	        
	    Map<String, ? super Object> params = new HashMap<String, Object>();
	        
	    params.put(STORE_NO.name(), storeNumber);
	    
        params.put(RQD_ID.name(), rqd_Id);        
        
        List<OrderDTO> orderDTOs =  (List<OrderDTO>) query(storeNumber,sql, params,new NotDeliverReportMapper());
        
		if(null != orderDTOs && !orderDTOs.isEmpty()){
			return orderDTOs.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * @description This method is used to get ItemDTO to update DEJ Database for Not Delivered Report
	 * @param storeNumber String
	 * @param rqd_Id String
	 * @return ItemDTO
	 * @throws DJException
	 */
	public ItemDTO getItemDetailsByRqdId(String storeNumber, String rqd_Id) throws DJException {
		
		logger.info("Inside NotDeliverServiceDAOImpl.getItemDetailsByRqdId for dateFrom: ", storeNumber+" rqd_Id: "+rqd_Id);	
		
		//List<ItemDTO> listItemDTO = null;
		String sql = PropertyUtils.getProperty("get.itemdetails.by_req_id");
	        
	    Map<String, ? super Object> params = new HashMap<String, Object>();
	        
	    params.put(STORE_NO.name(), storeNumber);
	    
        params.put(RQD_ID.name(), rqd_Id);        
        
        List<ItemDTO> itemDTOs =  (List<ItemDTO>) query(storeNumber,sql, params,new SOAItemMapper());
        
		if(null != itemDTOs && !itemDTOs.isEmpty()){
			return itemDTOs.get(0);
		}else{
			return null;
		}
	}
	
	public int insertNotDeliverReasonCodeToDejService(HashMap<String, String> notDeliverReasonCodeMap) throws DJException {		
		logger.info("Inside NotDeliverServiceDAOImpl.insertNotDeliverReasonCodeToDejService","");
		String serverURL = PropertyUtils.getProperty("mpu.notdeliver.reasoncode.dejservice");
		logger.info("serverURL: " + serverURL,"");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> input;
		try {
			input = new HttpEntity<String>(mapper.writeValueAsString(notDeliverReasonCodeMap),headers);
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
		
		ResponseEntity<String> response = restTemplate.postForEntity(serverURL, input, String.class);
		return response.getStatusCode().value();	
	}

}
