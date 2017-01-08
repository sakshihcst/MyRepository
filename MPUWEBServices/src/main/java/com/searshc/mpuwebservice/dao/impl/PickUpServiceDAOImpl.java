package com.searshc.mpuwebservice.dao.impl;


import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CLOSED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.COMPLETED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DELIVERED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FROM_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.IDENTIFIER_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.IDENTIFIER_VALUE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.INPUT_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS1;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_STATUS2;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.KIOSK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.MPU_ASSOCIATE_REPORT_CREATED_TS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.NOT_DELIVERED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.OPERATION_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.OPERATION_VALUE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORDER_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ORIGINALJSON;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKED_UP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PICKUP_INITIATED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PKG_NBR;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.QTY_REMAINING;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REMAINING_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUESTED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RETURNED;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SALESCHECK;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.SEARS_SALES_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TO_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANSDETAILID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANS_DETAIL_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TRANS_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UNSECURED_FLAG;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.WORK_ITEM_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CUSTOMER_ID;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RETURN_AUTH_CODE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.REQUEST_TYPE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPC;

import java.io.StringReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.MDC;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sears.dej.interfaces.vo.ItemMasterResponse;
import com.sears.dej.interfaces.vo.ItemMeta;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.sears.dj.common.util.DJUtilities;
import com.sears.dj.common.ws.DJServiceLocator;
import com.sears.mpu.backoffice.domain.CreditCard;
import com.sears.mpu.backoffice.domain.Customer;
import com.sears.mpu.backoffice.domain.CustomerID;
import com.sears.mpu.backoffice.domain.MatchKey;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderAdaptorResponse;
import com.sears.mpu.backoffice.domain.OrderItem;
import com.sears.mpu.backoffice.domain.PhoneNum;
import com.sears.mpu.backoffice.domain.SalesCheck;
import com.sears.mpu.backoffice.domain.Status;
import com.sears.mpu.backoffice.domain.Transaction;
import com.searshc.mpuwebservice.bean.CustomerDTO;
import com.searshc.mpuwebservice.bean.CustomerDetailDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.MPUAssociateReportDTO;
import com.searshc.mpuwebservice.bean.MpuPickUpReportResposne;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PackageDTO;
import com.searshc.mpuwebservice.bean.PaymentDTO;
import com.searshc.mpuwebservice.bean.PickUpDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.ShopinRequestDTO;
import com.searshc.mpuwebservice.bean.VehicleInfo;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.PickUpServiceDAO;
import com.searshc.mpuwebservice.mapper.IdentifierDTOMapper;
import com.searshc.mpuwebservice.mapper.ItemQuantityAndLdapIdMapper;
import com.searshc.mpuwebservice.mapper.LockerTransDetailMapper;
import com.searshc.mpuwebservice.mapper.NPOSItemDetailsMapper;
import com.searshc.mpuwebservice.mapper.PackageDTOMapper;
import com.searshc.mpuwebservice.mapper.PackageInfoMapper;
import com.searshc.mpuwebservice.mapper.PaymentDTOMapper;
import com.searshc.mpuwebservice.mapper.PickUpItemDTOMapper;
import com.searshc.mpuwebservice.mapper.PickUpOrdersMapper;
import com.searshc.mpuwebservice.mapper.RequestMPUDetailsMapper;
import com.searshc.mpuwebservice.mapper.RequestMPUDetailsMapper1;
import com.searshc.mpuwebservice.mapper.RequestMPUTransMapper;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;
import com.searshc.mpuwebutil.util.CommonUtils;
import com.searshc.mpuwebutil.util.ConversionUtils;

@SuppressWarnings("unchecked")
@Repository("pickUpServiceDAO")
public class PickUpServiceDAOImpl extends DJMPUCommonDAO implements PickUpServiceDAO {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateWFD;
	
//	@Autowired
//	public void setDataSource(final DataSource dataSource) {
//		this.namedParameterJdbcTemplateWFD = new NamedParameterJdbcTemplate(dataSource);
//	}
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(PickUpServiceDAOImpl.class);
	
//	public List<Map<String, Object>> getAllItemsForCustomer(CustomerDTO customerDTO, String typeOfIdentification, String salesCheckNumber) throws DJException{
//		
//		logger.info("getAllItemsForCustomer","Entering getAllItemsForCustomer customerDTO : " + customerDTO + " -- typeOfIdentification : " + typeOfIdentification + " -- salesCheckNumber : " + salesCheckNumber);
//		
//		Map<String,String> paramMap=new HashMap<String,String>();
//		
//		String sql = DJUtilities.concatString(PropertyUtils.getProperty("select.from.details0"), PropertyUtils.getProperty("select.from.details1"), PropertyUtils.getProperty("select.from.details2"), 
//				PropertyUtils.getProperty("select.from.details3"), PropertyUtils.getProperty("select.from.details4"), PropertyUtils.getProperty("select.from.details5"), 
//				PropertyUtils.getProperty("select.from.details6"), PropertyUtils.getProperty("select.from.details7"), PropertyUtils.getProperty("select.from.details8"), 
//				PropertyUtils.getProperty("select.from.details9"));
//		
//		if(typeOfIdentification.equalsIgnoreCase("PHONE")) {
//			
//			sql = DJUtilities.concatString(sql, PropertyUtils.getProperty("select.from.details10"));
//			
//			paramMap.put("identifier_type", "PHONE");
//			paramMap.put("value", customerDTO.getPhoneNumber());
//			
//		} else if(typeOfIdentification.equalsIgnoreCase("SALESCHECKNUMBER")) {
//			
//			sql = DJUtilities.concatString(sql, PropertyUtils.getProperty("select.from.details10"));
//			
//			paramMap.put("identifier_type", "SALESCHECK");
//			paramMap.put("value", salesCheckNumber);
//			
//		} else if(typeOfIdentification.equalsIgnoreCase("NAMEADDRESSZIP")) {
//			
//			paramMap.put("ziptype", "ZIPCODE");
//			paramMap.put("zipvalue", customerDTO.getZipcode());
//			paramMap.put("address1type", "ADDRESS1");
//			paramMap.put("address1value", customerDTO.getAddress1() );
//			paramMap.put("lastnametype", "LAST_NAME");
//			paramMap.put("lastnamevalue", customerDTO.getLastName());
//			
//			sql = PropertyUtils.getProperty("select.from.details_naz1");
//			sql = DJUtilities.concatString(sql, PropertyUtils.getProperty("select.from.details_naz2"), PropertyUtils.getProperty("select.from.details_naz3"), 
//					PropertyUtils.getProperty("select.from.details_naz4") + PropertyUtils.getProperty("select.from.details_naz5"), 
//					PropertyUtils.getProperty("select.from.details10"));
//		} else {
//			
//			DJException dje = new DJException();
//			dje.setMessage("Invalid Search Type.Search Type is needed");
//			throw dje;
//		}
//		
//		logger.info("getAllItemsForCustomer", "getAllItemsForCustomer Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
//		
//		return (List<Map<String, Object>>) queryForList(customerDTO.getStoreNumber(), sql, paramMap);
//	}
	
	//moved this method to MCPDBDAOImpl to fetch data from OLD DB
	
/*	*//**This method is used to get list of kiosk
	 * @param storeNum
	 * @throws Exception
	 *//*
	public List<Map<String, Object>> getKioskList(String storeNum) throws Exception{
		logger.info("getKioskList", "Entering getKioskList DAO storeNum : " + storeNum);
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("STORE_NUM", storeNum);
		String sql = PropertyUtils.getProperty("select.from.mcpstorekiosk.kiosklist"); 
		
		logger.info("getAllItemsForPickUp", "getAllItemsForPickUp Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		List<Map<String, Object>> kioskList =(List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(storeNum, 5), sql, paramMap);
		
		return kioskList;
	}*/
	
	//Manish
	public ArrayList<Order> getOrdersFromNPOS(PickUpDTO pickUpDTO, String numType, String identifierValue, String reqType) {
		
		logger.info("getAllOrdersForPickUpNPOS","Entering getAllOrdersForPickUpNPOS pickUpDTO : " + pickUpDTO + " -- numType : " + numType + " -- identifierValue : " + identifierValue+ " -- reqType : " + reqType);
		ArrayList<Order> orderList = new ArrayList<Order>();
		OrderAdaptorRequest request = new OrderAdaptorRequest();
		String storeFormat = pickUpDTO.getStoreFormat();
		String serverURL = MPUWebServiceUtil.getDNSForStore(pickUpDTO.getStoreNumber(),storeFormat);
		Integer status = 2;
		if("RETURN".equalsIgnoreCase(reqType)) {
			
			serverURL = DJUtilities.concatString(serverURL, "/fetchReturnOrdersfromNPOS");
			status = 1;
			
		} else {
			status = 2;
			serverURL = DJUtilities.concatString(serverURL, "/fetchOrdersfromNPOS");
		}
		
		if(numType.equalsIgnoreCase("PHONE")) {
			
			PhoneNum phoneNum = new PhoneNum(identifierValue);
			phoneNum.setStatusCode(status);
			request.setRequestType("PHONENUM");
			request.setPhoneNum((PhoneNum)phoneNum);
			
		} else if(numType.equalsIgnoreCase("SALESCHECK")) {
			
			SalesCheck saleCheck = new SalesCheck(identifierValue);
			saleCheck.setStatusCode(status);
			request.setRequestType("SALESCHECKNO");
			request.setSalesCheck((SalesCheck)saleCheck);
			
		} else if(numType.equalsIgnoreCase("ACCOUNT_NUMBER")) {
			
			CreditCard creditCard = new CreditCard(identifierValue);
			creditCard.setStatusCode(status);
			request.setRequestType("CRRDITCARD");
			request.setCreditCard((CreditCard)creditCard);
			
		} else if(numType.equalsIgnoreCase("CUSTOMERID")) {
			
			CustomerID customerID = new CustomerID(identifierValue,pickUpDTO.getAddressId(),pickUpDTO.getIdStsCd());
			customerID.setStatusCode(status);
			request.setRequestType("CUSTOMERID");
			request.setCustomerID((CustomerID)customerID);
			
		} else if(numType.equalsIgnoreCase("MATCHKEY")) {
			
			MatchKey matchKey = new MatchKey();

			matchKey.setLastName(pickUpDTO.getCustomerName());
			matchKey.setStAddress1(pickUpDTO.getAddress1());
			matchKey.setZipCode(pickUpDTO.getZipcode());
			matchKey.setStatusCode(status);
			request.setRequestType("MATCHKEY");
			request.setMatchKey(matchKey);
		}
		
		logger.info("getAllOrdersForPickUpNPOS serverURL",serverURL);
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.info("request = ", mapper.writeValueAsString(request));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
		logger.info("getAllOrdersForPickUpNPOS requestEntity",requestEntity);
		ResponseEntity<OrderAdaptorResponse> response = restTemplate.postForEntity(serverURL, requestEntity, OrderAdaptorResponse.class);
		logger.info("getAllOrdersForPickUpNPOS response",response);
		if(!StringUtils.isEmpty(response) && !StringUtils.isEmpty(response.getBody().getStatus())) {
			
			if(Status.STATUS_OK == response.getBody().getStatus().getCode()) {
				
				orderList = (ArrayList<Order>) response.getBody().getOrders();
				
				logger.info("getAllOrdersForPickUpNPOS", "response status : "+Status.STATUS_OK);
			}
			else{
				logger.info("getAllOrdersForPickUpNPOS", "response status : "+Status.NOTFOUND);
			}
		}
		else{
			logger.info("getAllOrdersForPickUpNPOS", "response from fetchOrdersfromNPOS service is null");
		}
		
		logger.debug("getAllOrdersForPickUpNPOS", "Exit getAllOrdersForPickUpNPOS orderList : " + orderList);
		
		return  orderList;
	}

	// Do Not Delete (Retained as back Up) --  Manish
	
	/*public List<ItemDTO> getAllItemsForPickUp(PickUpDTO pickUpDTO, String numType, String identifierValue) throws DJException {
		
		logger.info("getAllItemsForPickUp","Entering getAllItemsForPickUp pickUpDTO : " + pickUpDTO + " -- numType : " + numType + " -- identifierValue : " + identifierValue);
		
		Map<String,String> paramMap=new HashMap<String,String>();

		String sql = PropertyUtils.getProperty("select.from.details.order11"); 
		
		paramMap.put("identifier_type", numType);
		paramMap.put("value", identifierValue);
		paramMap.put(STORE_NUMBER.name(), pickUpDTO.getStoreNumber());
		paramMap.put(CUSTOMER_TYPE.name(), MpuWebConstants.CUSTOMER_TYPE);
		paramMap.put(ADDRESS1_TYPE.name(), MpuWebConstants.ADDRESS1_TYPE);
		paramMap.put(ADDRESS2_TYPE.name(), MpuWebConstants.ADDRESS2_TYPE);
		paramMap.put(CUSTOMER_ID.name(), pickUpDTO.getCustomerId());
		
		if(!StringUtils.isEmpty(pickUpDTO.getAddress1()) && !StringUtils.isEmpty(pickUpDTO.getAddress2())) {
			
			paramMap.put(ADDRESS1.name(), pickUpDTO.getAddress1());
			paramMap.put(ADDRESS2.name(), pickUpDTO.getAddress1());	
			sql = DJUtilities.concatString(sql, PropertyUtils.getProperty("select.from.details.order13"), PropertyUtils.getProperty("select.from.details.order14"), 
					PropertyUtils.getProperty("select.from.details.order17")); 
		}
		else if(!StringUtils.isEmpty(pickUpDTO.getAddress1())) {
			
			paramMap.put(ADDRESS1.name(), pickUpDTO.getAddress1());
			sql = DJUtilities.concatString(sql, PropertyUtils.getProperty("select.from.details.order13"), PropertyUtils.getProperty("select.from.details.order15")); 
		}		
		else if(!StringUtils.isEmpty(pickUpDTO.getAddress2())) {
			
			paramMap.put(ADDRESS2.name(), pickUpDTO.getAddress2());
			sql = DJUtilities.concatString(sql, PropertyUtils.getProperty("select.from.details.order13"), PropertyUtils.getProperty("select.from.details.order15"));
		}		
		
		logger.info("getAllItemsForPickUp", "getAllItemsForPickUp Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		List<ItemDTO> pickUpOrdersDTO = (List<ItemDTO>) query(DJUtilities.leftPadding(pickUpDTO.getStoreNumber(), 5), sql, paramMap, new PickUpOrdersMapper());
		
		logger.debug("getAllItemsForPickUp", "Exit getAllItemsForPickUp pickUpOrdersDTO : " + pickUpOrdersDTO);
		
		return pickUpOrdersDTO;
	}*/
	
	public List<ItemDTO> getAllItemsForOrder(List<OrderDTO> listOrderDTO, PickUpDTO pickUpDTO) throws DJException {
		
		logger.info("getAllItemsForOrder","Entering getAllItemsForOrder listOrderDTO : " + listOrderDTO + "	---	pickUpDTO : " + pickUpDTO);
		
		List<String> rqtIdList = new ArrayList<String>();

		Map<String,Object> paramMap = new HashMap<String,Object>();

		for(OrderDTO order: listOrderDTO)
		{
			if(!StringUtils.isEmpty(order.getRqtId()))
				rqtIdList.add(order.getRqtId());
		}
		
		String sql = PropertyUtils.getProperty("select.from.details.from.order");
		
		paramMap.put(RQT_ID.name(), rqtIdList);
		paramMap.put(STORE_NUMBER.name(), pickUpDTO.getStoreNumber());
		
		logger.info("getAllItemsForOrder", "getAllItemsForOrder Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
			
		List<ItemDTO> pickUpItemsDTO = (List<ItemDTO>) query(DJUtilities.leftPadding(pickUpDTO.getStoreNumber(), 5),sql, paramMap, new PickUpItemDTOMapper());
		
		logger.info("getAllItemsForOrder", "Exit getAllItemsForOrder pickUpItemsDTO : " + pickUpItemsDTO);
		
		return pickUpItemsDTO;
	
	}
	
	public List<PaymentDTO> getAllPaymentForOrder(List<OrderDTO> listOrderDTO, String storeNumber) throws DJException {
		
		logger.info("getAllPaymentForOrder","Entering getAllPaymentForOrder listOrderDTO : " + listOrderDTO + " -- storeNumber : " + storeNumber);
		
		List<String> rqtIdList= new ArrayList<String>();
		
		Map<String,Object> paramMap=new HashMap<String,Object>();

		for(OrderDTO order: listOrderDTO)
		{
			if(!StringUtils.isEmpty(order.getRqtId()))
				rqtIdList.add(order.getRqtId());
		}
		
		String sql = PropertyUtils.getProperty("select.from.payment.from.order");
		
		paramMap.put(RQT_ID.name(), rqtIdList);
		paramMap.put(STORE_NUMBER.name(), storeNumber);
		
		logger.info("getAllPaymentForOrder", "getAllPaymentForOrder Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
			
		List<PaymentDTO> pickUpPaymentDTO = (List<PaymentDTO>) query(DJUtilities.leftPadding(storeNumber, 5),sql, paramMap,new PaymentDTOMapper());
		
		logger.info("getAllPaymentForOrder", "Exit getAllPaymentForOrder pickUpPaymentDTO : " + pickUpPaymentDTO);
		
		return pickUpPaymentDTO;
	
	}
	
	public Integer initiatePickUpForItems(List<ItemDTO> itemList, String storeNum, String kioskName) throws DJException {
		
		logger.info("Entering","Entering initiatePickUpForItems itemList : " + itemList + "	-- storeNum : " + storeNum);
		
		String sql = PropertyUtils.getProperty("initiate_pickup");

		List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
		
		for(ItemDTO item: itemList)
		{
			HashMap<String, ? super Object> param = new HashMap<String, Object>();
			
			
			param.put(RQD_ID.name(), item.getRqdId());
			param.put(STORE_NUMBER.name(), item.getStoreNumber());
			param.put(KIOSK.name(), kioskName);
			
			logger.info("==item.getCurrentStatus()==",item.getCurrentStatus());
			if(MpuWebConstants.PICKUP_INITIATED.equalsIgnoreCase(item.getCurrentStatus()) 
					|| (MpuWebConstants.OPEN).equalsIgnoreCase(item.getCurrentStatus())
					|| item.getToLocation().toUpperCase().startsWith("MPU")
					|| item.getToLocation().toUpperCase().startsWith("CURBSIDE")
					|| MpuWebConstants.BINPENDING.equalsIgnoreCase(item.getCurrentStatus())){
					param.put(FROM_LOCATION.name(), item.getFromLocation());
			}
			else{
				param.put(FROM_LOCATION.name(), item.getToLocation());
			}
			
			param.put(PICKUP_INITIATED.name(), MpuWebConstants.PICKUP_INITIATED);
			param.put(REQUESTED_QUANTITY.name(), item.getItemQuantityRequested());
			logger.info("==param==", param);
			paramList.add(param);
		}
		
		Map<String,Object> paramArray[] = new HashMap[paramList.size()];
		
		logger.info("Existing" ,"PickUpWebServiceDAOImpl.initiatePickUpForItems");
		
		//DJUtilities.createSQLCommand(sql, paramList.toArray(paramArray), logger, "initiatePickUpForItems");
				
		return batchUpdate(DJUtilities.leftPadding(storeNum, 5), sql, paramList.toArray(paramArray)).length;
	}
	
	
	/*public int[] initiatePickUpOrders(List<String> rqtIds,String storeNumber,PickUpOrderDTO orders) throws DJException
	{
		String sql= "initiate_pickup_orders";
		List<String> rqdIdList= new ArrayList<String>();

		ArrayList<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
		
		Date currentTime=Calendar.getInstance().getTime();
	    String createTime = DJUtilities.dateToString(currentTime,MpuWebConstants.DATE_FORMAT);
		
		
		for(String rqtId: rqtIds)
		{
			HashMap<String, ? super Object> param = new HashMap<String, Object>();
			param.put(RQT_ID.name(), rqtId);
			param.put(STORE_NUMBER.name(), storeNumber);
			param.put(TYPE.name(), orders.getType());
			param.put(CREATE_TIMESTAMP.name(),createTime);
			param.put(ASSOCIATE_ID.name(),orders.getAssociateId());
			param.put(SEARCH_METHOD.name(),orders.getSearchMethod());
			param.put(SEARCH_VALUE.name(),orders.getSearchValue());
			param.put(KIOSKLOCATION.name(), orders.getKiosk());
			
			for(ItemDTO item : orders.getItemList())
			{
				if(item.getRqtId().equalsIgnoreCase(rqtId))
				{
					param.put(SALESCHECK.name(), item.getSalescheck());
				}
			}
			param.put(REQUEST_STATUS.name(), MpuWebConstants.OPEN);
			param.put(CARD_SWIPED.name(), orders.getCardSwiped());
			
			paramList.add(param);
		}
		
		HashMap<String,Object> paramArray[]=new HashMap[paramList.size()];
		logger.info("Existing" ,"PickUpWebServiceDAOImpl.initiatePickUpForItems");
		return batchUpdate(storeNumber, sql, paramList.toArray(paramArray));
		
	}*/
	
	public void putRelatedSalecheckForPickUp(String relatedSalesCheckNumber, String storeNumber, Integer transId) throws DJException {
		
		logger.info("putRelatedSalecheckForPickUp","Entering putRelatedSalecheckForPickUp relatedSalesCheckNumber : " + relatedSalesCheckNumber + " -- storeNumber : " + storeNumber + " -- transId : " + transId);
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		String sql = PropertyUtils.getProperty("update.related.saleschecknumber.to.request_mpu_trans");
		
		paramMap.put(SALESCHECK.name(), relatedSalesCheckNumber);
		paramMap.put(TRANS_ID.name(), transId);
		paramMap.put(STORE_NUMBER.name(), storeNumber);
		
		int rows = update(DJUtilities.leftPadding(storeNumber, 5), sql, paramMap);
		logger.info("putRelatedSalecheckForPickUp", "putRelatedSalecheckForPickUp Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
			
		logger.info("putRelatedSalecheckForPickUp", "Exit putRelatedSalecheckForPickUp update Count : " + rows);
	}
	
	//Manish ends
	

	
	public RequestDTO nPosCallForHelpRepair(CustomerDetailDTO customerDetailDTO) throws DJException{

		logger.info("nPosCallForHelpRepair", "Enter nPosCallForHelpRepair customerDetailDTO : " + customerDetailDTO); 
		
		List <Order> orderList = new ArrayList<Order>();
		Order orderToUpdate = new Order();
		OrderAdaptorResponse theOrderResponse = new OrderAdaptorResponse();
		RequestDTO requestDTO = null;
				
		try {
			
			orderToUpdate.setIdentifierType(customerDetailDTO.getRequestType());
			orderToUpdate.setOrderType(customerDetailDTO.getRequestType());
			orderToUpdate.setStoreNo(customerDetailDTO.getStoreNo());	
			orderToUpdate.setKioskName(customerDetailDTO.getStoreName());
			orderToUpdate.setStoreFormat(customerDetailDTO.getStoreFormat());
			
			Customer customer=new Customer();
			
			customer.setName(customerDetailDTO.getCustLastName());
			orderToUpdate.setCustomer(customer);
			OrderAdaptorRequest aRequest = new OrderAdaptorRequest();
			aRequest.setRequestOrder(orderToUpdate);
			aRequest.setRequestType(OrderAdaptorRequest.RETRIEVE_ORDER);
			
			HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(aRequest);
			String dnsName = MPUWebServiceUtil.getDNSForStore(customerDetailDTO.getStoreNo(), orderToUpdate.getStoreFormat());
			String target = "";
			
			
			if("REPAIRDROP".equals(customerDetailDTO.getRequestType())) {
			
				target = DJUtilities.concatString(dnsName, "/getAnOrderForKioskRepairDropoff");
			
			} else if("REPAIRPICK".equals(customerDetailDTO.getRequestType())) {
				
				target = DJUtilities.concatString(dnsName, "/getAnOrderForKioskRepairPickUp");
				
			} else { // HELP
				
				target = DJUtilities.concatString(dnsName + "/getAnOrderForKioskHelp");
			}		
			
			ResponseEntity<OrderAdaptorResponse> response = restTemplate.exchange(target, HttpMethod.POST, requestEntity, OrderAdaptorResponse.class);
			
			theOrderResponse = response.getBody();
			orderList = theOrderResponse.getOrders();
		    orderToUpdate = orderList.get(0);
		    orderToUpdate = checkWorkOrderIdentifier(orderToUpdate);
	        orderToUpdate.getTask().setLanguage(customerDetailDTO.getLanguage());
	        orderToUpdate.setReturnTimeStamp("");
		    requestDTO	= ConversionUtils.convertNPOSOrderToRequestDTO(orderToUpdate);
	
		    requestDTO.getCustomer().setLastName(requestDTO.getCustomer().getFirstName());

	
		} catch (Exception e) {
			
			logger.error("nPosCallForHelpRepair : ", "Exception nPosCallForHelpRepair : " + e);
			DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
			throw djException;
		}
		
		logger.info("nPosCallForHelpRepair", "Exit nPosCallForHelpRepair requestDTO : " + requestDTO); 
		
		return requestDTO;
	}
		
	private Order checkWorkOrderIdentifier(Order orderToUpdate) {
		
		String OrderIdentifier=orderToUpdate.getIdentifier();
		String transdata="";
		if(OrderIdentifier.length()>=8 && OrderIdentifier.length()<=12 ){
			
			 transdata=OrderIdentifier.substring(8, OrderIdentifier.length());
			 if(transdata.length()>0 && transdata.length()<=1){
		        	
		        	transdata="000"+transdata;
		        }
		        
		        if(transdata.length()>1 && transdata.length()<=2){
		        	
		        	transdata="00"+transdata;
		        }
		        if(transdata.length()>2 && transdata.length()<=3){
		        	
		        	transdata="0"+transdata;
		        }
		}
		String identifier=OrderIdentifier.substring(0, 8);
		OrderIdentifier=identifier+transdata;
		orderToUpdate.setIdentifier(OrderIdentifier);
		Transaction transaction=new Transaction();
		transaction.setNumber(Integer.parseInt(transdata));
		transaction.setDate(new Date());
		transaction.setId("0");
		transaction.setRelaxedTransactionId(0);
		
		orderToUpdate.setTransaction(transaction);
		
		return orderToUpdate;
	}
	
	public List<OrderDTO> fetchOderForAssociate(String storeNumber, String kioskName) throws DJException {
		
		logger.info("fetchOderForAssociate", "Enter fetchOderForAssociate storeNumber : " + storeNumber + " -- kioskName : " + kioskName);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(KIOSK.name(), kioskName);
		param.put(STORE_NUMBER.name(), storeNumber);
		
		String sql = PropertyUtils.getProperty("select.from.request_mpu_trans");
		
		logger.info("fetchOderForAssociate", "fetchOderForAssociate Sql : " + DJUtilities.createSQLCommand(sql, param));
		
		List<OrderDTO> lOrderDTO = (List<OrderDTO>) query(DJUtilities.leftPadding(storeNumber, 5), sql, param, new RequestMPUTransMapper());
		
		logger.debug("fetchOderForAssociate", "Exit fetchOderForAssociate lOrderDTO : " );
		
		return lOrderDTO;
		
	}
	
	public List<ItemDTO> fetchItemForRqtid(String storeNumber, OrderDTO obj) throws DJException {
		
		logger.info("fetchItemForRqtid", "Enter fetchItemForRqtid storeNumber : " + storeNumber + " -- OrderDTO : " + obj);
	
		Map<String, Object> param = new HashMap<String, Object>();
		
		String sql = PropertyUtils.getProperty("select.from.request_mpu_details");
		param.put(TRANS_ID.name(), obj.getTrans_id());
		
		logger.info("fetchOderForAssociate", "fetchOderForAssociate Sql : " + DJUtilities.createSQLCommand(sql, param));
		
		List<ItemDTO> lOrderDTO = (List<ItemDTO>) query(DJUtilities.leftPadding(storeNumber, 5), sql, param, new RequestMPUDetailsMapper1());
		
		logger.info("fetchItemForRqtid", "Exit fetchItemForRqtid lOrderDTO : " + lOrderDTO);
		
		return lOrderDTO;
	}

	public List<Map<String, Object>> getParentRqtId(String salesCheck,String storeNo,String customerId, String orderDate) throws DJException {
		
		logger.info("getParentRqtId", "Enter getParentRqtId salesCheck : " + salesCheck + " -- storeNo : " + storeNo + " -- customerId : " + customerId + " --- orderDate : " + orderDate);
		
		Map<String,String> paramMap = new HashMap<String,String>();

		String sql = PropertyUtils.getProperty("select.from.parentrqtid");
		
		paramMap.put("SALESCHECK", salesCheck);
		paramMap.put("CUSTOMERID", customerId);
		paramMap.put("STORENO", storeNo);
		paramMap.put("SALESCHECK", salesCheck);
		paramMap.put("ORDER_DATE", orderDate);
		
		logger.info("getParentRqtId", "getParentRqtId Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return (List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(storeNo, 5), sql, paramMap);
	}
	
	public List<Map<String, Object>> getParentRqdId(String salesCheck, String storeNo, String div, String item, String sku, String itemSeq, String rqtId) throws DJException {
		
		logger.info("getParentRqdId", "Enter getParentRqdId salesCheck : " + salesCheck + " -- storeNo : " + storeNo + " -- div : " + div + " --- item : " + item + "  -- sku : " 
		+ sku + "  -- itemSeq : " + itemSeq + " --rqtId : " + rqtId);
		
		Map<String,String> paramMap = new HashMap<String,String>();
		String sql = PropertyUtils.getProperty("select.from.parentrqdid");
		
		paramMap.put("SALESCHECK", salesCheck);
		paramMap.put("DIV", div);
		paramMap.put("ITEM", item);
		paramMap.put("SKU", sku);
		paramMap.put("ITEMSEQ", itemSeq);
		paramMap.put("RQTID",rqtId);
		
		logger.info("getParentRqdId", "getParentRqdId Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return (List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(storeNo, 5), sql, paramMap);
	}
	
	public List<Map<String, Object>> checkPreviousRI5Progress(String storeNo, String returnAuthCode, String requestType, String requestStatus) throws DJException {
		
		logger.info("checkPreviousRI5Progress", "Enter checkPreviousRI5Progress storeNo : " + storeNo + " -- returnAuthCode : " + returnAuthCode + " -- requestType : " 
		+ requestType + " --- requestStatus : " + requestStatus);
		
		Map<String,String> paramMap = new HashMap<String,String>();
		String sql = PropertyUtils.getProperty("select.from.previousri5progress");
		
		paramMap.put("RETURN_AUTH_CODE", returnAuthCode);
		paramMap.put("REQUEST_TYPE", requestType);
		paramMap.put("REQUEST_STATUS", requestStatus);

		logger.info("checkPreviousRI5Progress", "checkPreviousRI5Progress Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return (List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(storeNo, 5), sql, paramMap);
	}
	
	public Integer cancelReturnin5(int transId, String requestStatus, String storeNo) throws DJException {
		
		logger.info("cancelReturnin5", "Enter cancelReturnin5 storeNo : " + storeNo + " -- transId : " + transId + " -- requestStatus : " + requestStatus);
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		String sql = PropertyUtils.getProperty("update.cancelreturn");
		
		paramMap.put("TRANSID", transId);
		paramMap.put("STORENO", storeNo);
		paramMap.put("REQUESTSTATUS", requestStatus);
		
		logger.info("cancelReturnin5", "cancelReturnin5 Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return update(DJUtilities.leftPadding(storeNo, 5),sql, paramMap);
	}

	private List<Map<String, Object>> getRequestActivityId(String requestType, String nextStatus,String storeNo) throws DJException{
		
		logger.info("getRequestActivityId", "Enter getRequestActivityId storeNo : " + storeNo + " -- requestType : " + requestType + " -- nextStatus : " + nextStatus);
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String sql = PropertyUtils.getProperty("select.request.activityid");
		
		paramMap.put("NEXT_STATUS", nextStatus);
		paramMap.put("REQUEST_TYPE", requestType);
		
		logger.info("getRequestActivityId", "getRequestActivityId Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return  (List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(storeNo, 5), sql, paramMap);
	}
	
	public Integer initiatePickUpActivityForItems(List<ItemDTO> itemList, String storeNum,String nextStatus) throws DJException {
		 int rows = 0;
		logger.info("Entering","Entering initiatePickUpActivityForItems itemList : " + itemList + " -- storeNum : " + storeNum + "-- nextStatus -- "+ nextStatus);
		
		if (null != itemList && !itemList.isEmpty()){
		List<Map<String, Object>> mapList  =  getRequestActivityId(itemList.get(0).getRequestType(), nextStatus, storeNum);
		
		String activityId = "";
		String activityDescription = "";
		for (Map<String, Object> map : mapList) {
			activityId = map.get("request_action_status_id") + "";
			activityDescription = map.get("activity") + "";
		}
		
		String sql = PropertyUtils.getProperty("initiate_mpu_activity");

		List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
		
		for(ItemDTO item: itemList)
		{
			HashMap<String, ? super Object> param = new HashMap<String, Object>();
			
			
			
			param.put(RQT_ID.name(), item.getRqtId());
			if(null != item.getRqdId()){
				param.put(RQD_ID.name(), item.getRqdId());
			}
			else{
				param.put(RQD_ID.name(), item.getReturnParentId());	
			}
			param.put(STORE_NUMBER.name(), item.getStoreNumber());
			param.put(CREATE_TIMESTAMP.name(), new Date());
			param.put(TYPE.name(), item.getRequestType());
			param.put(STORE_NUMBER.name(), storeNum);
			//TEMOVE BLOCKEREMPORARY FIX BY ARGHYA TO R
			if(item.getCreatedBy()==null) item.setCreatedBy("KIOSK");
			param.put(CREATED_BY.name(),item.getCreatedBy());
			param.put("ACTION_SEQ", activityId);
			param.put("ACTIVITY_DESCRIPTION",activityDescription);
			param.put("ASSIGNED_USER", item.getAssignedUser());
			param.put("FROM_LOCATION", item.getFromLocation());
			param.put("TO_LOCATION", item.getToLocation());
			
			if(null != item.getItemQuantityRequested() && null != item.getItemQuantityNotDelivered())
				param.put(DELIVERED_QUANTITY.name(), String.valueOf(Integer.parseInt(item.getItemQuantityRequested()) - Integer.parseInt(item.getItemQuantityNotDelivered())));
			else if(null != item.getItemQuantityRequested())
				param.put(DELIVERED_QUANTITY.name(),item.getItemQuantityRequested());
			else
				param.put(DELIVERED_QUANTITY.name(),"0");
			paramList.add(param);
		}
		
		Map<String,Object> paramArray[] = new HashMap[paramList.size()];
		
		 rows = batchUpdate(DJUtilities.leftPadding(storeNum, 5), sql, paramList.toArray(paramArray)).length;
		}
		System.out.println("rows"+rows);
		return rows ;
	}

	public Integer insertActitivtyHelp(RequestDTO requestDTO, String storeNum,String nextStatus) throws DJException {
		 int rows = 0;
		logger.info("Entering","Entering insertActitivtyHelp requestDTO : " + requestDTO + " -- storeNum : " + storeNum  + "-- nextStatus -- "+ nextStatus);
		
		if (null != requestDTO){
			OrderDTO orderDTO = requestDTO.getOrder();
		List<Map<String, Object>> mapList  =  getRequestActivityId(requestDTO.getOrder().getRequestType(), nextStatus, storeNum);
		
		String activityId = "";
		String activityDescription = "";
		for (Map<String, Object> map : mapList) {
			activityId = map.get("request_action_status_id") + "";
			activityDescription = map.get("activity") + "";
		}
		
		String sql = PropertyUtils.getProperty("initiate_mpu_activity");

		List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
		
			HashMap<String, ? super Object> param = new HashMap<String, Object>();
			
			param.put(RQT_ID.name(), orderDTO.getTrans_id());
			param.put(STORE_NUMBER.name(), orderDTO.getStoreNumber());
			param.put(CREATE_TIMESTAMP.name(), new Date());
			param.put(TYPE.name(), orderDTO.getRequestType());
			param.put(STORE_NUMBER.name(), storeNum);
			param.put(CREATED_BY.name(),orderDTO.getCreatedBy());
			param.put("ACTION_SEQ", activityId);
			param.put("ACTIVITY_DESCRIPTION",activityDescription);
			param.put("ASSIGNED_USER", orderDTO.getAssociate_id());
			
			paramList.add(param);
		
		Map<String,Object> paramArray[] = new HashMap[paramList.size()];
		
		 rows = batchUpdate(DJUtilities.leftPadding(storeNum, 5), sql, paramList.toArray(paramArray)).length;
		}
		System.out.println("rows"+rows);
		return rows ;
	}
	
	public Integer updateActivityDetailDTO(String storeNumber, List<ItemDTO> items, String dateTime) throws DJException {
		
		logger.info("updateActivityDetailDTO" ,"Enter updateActivityDetailDTO storeNumber : " + storeNumber + " -- items : " + items + " -- dateTime : " + dateTime);
		
		String sql = PropertyUtils.getProperty("complete_mpu_item_request");
		
		List<HashMap<String, ? super Object>> paramList = new ArrayList<HashMap<String, ? super Object>>();
		
		for(ItemDTO item: items)
		{
			HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put(RQD_ID.name(), item.getRqdId());
			paramMap.put(STORE_NUMBER.name(), storeNumber);
			paramMap.put(DELIVERED_QUANTITY.name(), Integer.parseInt(item.getItemQuantityRequested()) - Integer.parseInt(item.getItemQuantityNotDelivered()));
			paramMap.put(NOT_DELIVERED_QUANTITY.name(), item.getItemQuantityNotDelivered());
			
			paramMap.put(UPDATE_TIMESTAMP.name(), dateTime);
			paramMap.put(TRANS_DETAIL_ID.name(), item.getTrans_detail_id());
			paramMap.put(TRANS_ID.name(), item.getTrans_id());
			if(("RETURN".equalsIgnoreCase(item.getRequestType())) || ("RETURNIN5".equalsIgnoreCase(item.getRequestType()))){
				paramMap.put(ITEM_STATUS.name(), "RETURNED");
			}
			else if("PICKUP".equalsIgnoreCase(item.getRequestType())){
				if(item.getItemQuantityNotDelivered().equalsIgnoreCase(item.getItemQuantityRequested()))
					paramMap.put(ITEM_STATUS.name(), MpuWebConstants.NOT_DELIVER);	
				else
					paramMap.put(ITEM_STATUS.name(), MpuWebConstants.PICKED_UP);
			}
			else{
			paramMap.put(ITEM_STATUS.name(), "COMPLETED");
			}
			
			paramList.add(paramMap);
		}
		
		Map<String,Object> paramArray[] = new HashMap[paramList.size()];
		
		//DJUtilities.createSQLCommand(sql, paramList.toArray(paramArray), logger, "updateActivityDetailDTO");
		
		return batchUpdate(DJUtilities.leftPadding(storeNumber, 5), sql, paramList.toArray(paramArray)).length;
	}

	public Integer updateActivityDTO(String storeNumber, String rqtId, String transId, String dateTime,String requestType, String assignedUser, String searsSalesId)	throws DJException {

		logger.info("updateActivityDTO" ,"Entering updateActivityDTO storeNumber : " + storeNumber + " -- rqtId : " + rqtId + " -- transId : " + transId + " -- dateTime : " 
		+ dateTime);
		
		String sql = PropertyUtils.getProperty("complete_mpu_request");
		
		Map<String, Object> paramMap=new HashMap<String, Object>();
		
		paramMap.put(RQT_ID.name(), rqtId);
		paramMap.put(STORE_NUMBER.name(), storeNumber);
		if(("RETURN".equalsIgnoreCase(requestType)) || ("RETURNIN5".equalsIgnoreCase(requestType))){
			paramMap.put(REQUEST_STATUS.name(), "RETURNED");
		}
		else if("PICKUP".equalsIgnoreCase(requestType)) {
			paramMap.put(REQUEST_STATUS.name(), MpuWebConstants.PICKED_UP);
		}
		else{
			paramMap.put(REQUEST_STATUS.name(), "COMPLETED");
		}
		
		paramMap.put(ASSOCIATE_ID.name(),assignedUser);
		paramMap.put(UPDATE_TIMESTAMP.name(), new Timestamp(ConversionUtils.convertStringToDate(dateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
		paramMap.put(TRANS_ID.name(), transId);
		//Changes Done By Nasir
		searsSalesId = getAssociateId();
		logger.info("searsSalesId = ", searsSalesId);
		
		paramMap.put(SEARS_SALES_ID.name(), searsSalesId);
		
		
		logger.info("updateActivityDTO", "updateActivityDTO Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return update(DJUtilities.leftPadding(storeNumber, 5), sql,paramMap);
	}

	public Integer completeItemDetails(ItemDTO item) throws DJException {
		
		logger.info("completeItemDetails" ,"Enter completeItemDetails item : " + item);
		
		String sql = PropertyUtils.getProperty("complete_queue_item_request");

		Map<String,String> paramMap=new HashMap<String,String>();
		
		paramMap.put(RQD_ID.name(), item.getRqdId());
		paramMap.put(STORE_NUMBER.name(), item.getStoreNumber());
		

		paramMap.put(ITEM_STATUS1.name(), MpuWebConstants.PICKED_UP); 
		
		/*else if((Integer.parseInt(item.getItemQuantityRequested()) - Integer.parseInt(item.getItemQuantityNotDelivered()))>0)
			paramMap.put(ITEM_STATUS.name(), MpuWebConstants.NOT_DELIVER);*/
		
		
		//paramMap.put(ITEM_STATUS2.name(), MpuWebConstants.OPEN);
		paramMap.put(UPDATE_TIMESTAMP.name(), item.getUpdateIimestamp());
		paramMap.put(QTY_REMAINING.name(), item.getItemQuantityNotDelivered());

		logger.info("completeItemDetails", "completeItemDetails Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return update(DJUtilities.leftPadding(item.getStoreNumber(), 5), sql, paramMap);
		
	}

	public Integer completeOrderDetails(String storeNo, String rqtId, String dateTime) throws DJException {
		
		logger.info("completeOrderDetails" ,"Enter completeOrderDetails storeNo : " + storeNo + " --- rqtId : " + rqtId + " -- dateTime : " + dateTime);
		
		String sql = PropertyUtils.getProperty("complete_queue_request");
		Map<String,String> paramMap = new HashMap<String,String>();
		
		paramMap.put(RQT_ID.name(), rqtId);
		paramMap.put(STORE_NUMBER.name(), storeNo);
		paramMap.put(UPDATE_TIMESTAMP.name(), dateTime);
		
		paramMap.put(REQUEST_STATUS.name(), MpuWebConstants.PICKED_UP);

		logger.info("completeOrderDetails", "completeOrderDetails Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return update(DJUtilities.leftPadding(storeNo, 5), sql,paramMap);
	}

	public Integer checkForQtyRemainingAndOpenItems(ItemDTO item) throws DJException {
		
		logger.info("checkForQtyRemainingAndOpenItems" ,"Enter checkForQtyRemainingAndOpenItems item : " + item);
		
		Map<String,String> paramMap=new HashMap<String,String>();
		
		String sql = PropertyUtils.getProperty("checkForQtyRemaining");
		
		paramMap.put(RQD_ID.name(), item.getRqdId());
		paramMap.put(STORE_NUMBER.name(), item.getStoreNumber());
		paramMap.put(ITEM_STATUS.name(), MpuWebConstants.OPEN);
		
		logger.info("checkForQtyRemainingAndOpenItems", "checkForQtyRemainingAndOpenItems qtyRemaining Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		Integer qtyRemaining = queryForInt(DJUtilities.leftPadding(item.getStoreNumber(), 5), sql, paramMap);	//Check For Qty Remaining
		
		logger.info("checkForQtyRemainingAndOpenItems" ,"checkForQtyRemainingAndOpenItems qtyRemaining : " + qtyRemaining);

		if(qtyRemaining.intValue() == 0) {
			
			sql = PropertyUtils.getProperty("checkForOpenActivityDetails");
			
			logger.info("checkForQtyRemainingAndOpenItems", "checkForQtyRemainingAndOpenItems map Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
			
			List<Map<String, Object>> map =(List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(item.getStoreNumber(), 5), sql, paramMap);
			if(map.size()==0) {
				
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public Integer checkForOpenItems(ItemDTO item) throws DJException {
		
		logger.info("checkForOpenItems" ,"Enter checkForOpenItems item : " + item);
		
		Map<String,String> paramMap = new HashMap<String,String>();
		String sql = PropertyUtils.getProperty("checkForOpenItems");
		
		paramMap.put(RQT_ID.name(), item.getRqtId());
		paramMap.put(STORE_NUMBER.name(), item.getStoreNumber());
		paramMap.put(ITEM_STATUS1.name(), MpuWebConstants.PICKED_UP);
		paramMap.put(ITEM_STATUS2.name(), MpuWebConstants.CLOSED);
		
		logger.info("checkForOpenItems", "checkForOpenItems Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		List<Map<String, Object>> map =(List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(item.getStoreNumber(), 5), sql, paramMap);
		
		if(map.size()==0) {
			return 1;
		} else {
			return 0;
		}
	}

	public Integer updateMpuItemDetails(ItemDTO item) throws DJException {
		
		logger.info("updateMpuItemDetails" ,"Enter updateMpuItemDetails item : " + item);
		
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		
		String sql = PropertyUtils.getProperty("update_item_details_return");
		
		paramMap.put(QTY.name(),item.getQty());
		paramMap.put(TRANSDETAILID.name(),item.getTrans_detail_id());
		
		logger.info("updateMpuItemDetails", "updateMpuItemDetails Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		return update(DJUtilities.leftPadding(item.getStoreNumber(), 5), sql, paramMap);
	}


	public List<OrderItem> getOrderforNPOSMPU(int transId, String storeNo) throws DJException{
		
		logger.info("getOrderforNPOSMPU" ,"Enter getOrderforNPOSMPU transId : " + transId+ " --- storeNo : " + storeNo);
		
		HashMap<String,Object> parameterMap = new HashMap<String,Object>();
		
		String sql = PropertyUtils.getProperty("select.from.item_details.npos");
		parameterMap.put(TRANS_ID.name(),transId);
		
		logger.info("getOrderforNPOSMPU", "getOrderforNPOSMPU Sql : " + DJUtilities.createSQLCommand(sql, parameterMap));
		
		List<OrderItem> orderItems = (List<OrderItem>) query(DJUtilities.leftPadding(storeNo, 5), sql, parameterMap, new NPOSItemDetailsMapper());
		
		return orderItems;	
	}

	public void assignUser(String storeNumber, int transId, String associateId, String rqtId,String searsSalesId) throws DJException {
		
		logger.info("assignUser" ,"Enter assignUser associateId : " + associateId + " -- transId : " + transId + " -- storeNumber : " + storeNumber);
		
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		
		String sql = PropertyUtils.getProperty("assign_mpu_associate");
		
		paramMap.put(STORE_NUMBER.name(), storeNumber);
		paramMap.put(TRANS_ID.name(),transId);
		paramMap.put(ASSOCIATE_ID.name(),associateId);
		paramMap.put(RQT_ID.name(),rqtId);
		
		String searsId=null;
		if(!CommonUtils.isEmpty(associateId)){searsId= getAssociateId();}
		paramMap.put(SEARS_SALES_ID.name(),searsId);
		
		logger.info("assignUser", "assignUser Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		update(DJUtilities.leftPadding(storeNumber, 5), sql, paramMap);
		
	}


	public List<ItemDTO> getDeliveredQtyAndLdapId(String storeNumber, String transId) throws DJException {
		
		logger.info("getDeliveredQty" ,"Enter getDeliveredQty  -- transId : " + transId + " -- storeNumber : " + storeNumber);

		HashMap<String,Object> parameterMap = new HashMap<String,Object>();
		
		String sql = PropertyUtils.getProperty("select.status.qty.from.details");
		
		parameterMap.put(STORE_NUMBER.name(), storeNumber);
		parameterMap.put(TRANS_ID.name(),transId);
		
		logger.info("getDeliveredQty", "getDeliveredQty Sql : " + DJUtilities.createSQLCommand(sql, parameterMap));
		
		List<ItemDTO> items = (List<ItemDTO>) query(DJUtilities.leftPadding(storeNumber, 5), sql, parameterMap, new ItemQuantityAndLdapIdMapper());
		
		return items;
	}
	
	public String getPropertyFromAdaptor(String storeNumber, String propertyName, String storeFormat) throws DJException {
		
		logger.info("getPropertyFromAdaptor" ,"Enter getPropertyFromAdaptor  -- storenumber : " + storeNumber + " -- propertyName : " + propertyName + " -- storeFormat : " + storeFormat);
		
		OrderAdaptorRequest request=new OrderAdaptorRequest();
		
		String dnsName = MPUWebServiceUtil.getDNSForStore(storeNumber, storeFormat);
		
		String serverURL = DJUtilities.concatString(dnsName + "/getPropertyValue");
		
		request.setPropertyName(propertyName);
		
		logger.info("server URL to get "+propertyName+" value is ", serverURL);
		
		HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
		ResponseEntity<OrderAdaptorResponse> response = restTemplate.postForEntity(serverURL, requestEntity, OrderAdaptorResponse.class);
		String propertyValue = response.getBody().getPropertyValue();
		
		
		
		logger.info("getPropertyFromAdaptor" ,"Exit getPropertyFromAdaptor  -- storenumber : " + storeNumber + " -- propertyName : " + propertyName + " -- storeFormat : " + storeFormat);
		
		return propertyValue; 
		
	}
	
public boolean checkForEI5Order(String EI5Salescheck,String IdentifierType,String storeNo) throws DJException{
	
	boolean EI5Flag = false;
	
	final String queryString = PropertyUtils.getProperty("select.order.via.salescheck");
	HashMap<String,Object> parameterMap = new HashMap<String,Object>();
	parameterMap.put(SALESCHECK.name().toLowerCase(), EI5Salescheck);
	//parameterMap.put(REQUEST_STATUS.name().toLowerCase(),"OPEN");
	parameterMap.put(STORE_NUMBER.name().toLowerCase(),storeNo);
	
	int noOfRows = queryForInt(DJUtilities.leftPadding(storeNo, 5), queryString, parameterMap);
	if(noOfRows > 0){
		EI5Flag = true;
	}
	return EI5Flag;
	//List<OrderDTO> orders = (List<OrderDTO>) query(DJUtilities.leftPadding(storeNo, 5), queryString, parameterMap, new OrderDTOMapper());
	/*List<Map<String, Object>> listMap  = query(DJUtilities.leftPadding(storeNo, 5), queryString, parameterMap, new ColumnMapRowMapper());
	if(null == listMap || listMap.size() == 0){
		return false;
	}else{
		return true;
	}*/
	//String payLoad  = listMap.get(0).get("originaljson").toString();
	
	//parse the json string to find out whether EI5 flag is enabled
	//String payLoad = order.getOriginalJson();
	/*ObjectMapper mapper = new ObjectMapper();
	try {
		Order nPOSorder = mapper.readValue(payLoad, Order.class);
		
		if(nPOSorder!=null)
		{
			if(nPOSorder.isExchangeFlag())
				return true;
		}
		
	} catch (JsonParseException e) {
		throw new DJException("Not able to parse the JSON String : JSONMapping Exception");
	} catch (JsonMappingException e) {
		throw new DJException("Not able to map the JSON String : JSONMapping Exception");
	} catch (IOException e) {
		throw new DJException("IOException Exception");
	}*/
}
	
	/**
	 * 
	 * @param salecheckNum
	 * @param storeNo
	 * @param storeFormat
	 * @return ResponseEntity
	 * @author ssingh6
	 */
	public Order searchTenderReturns(String returnAuthId ,String storeNo, String storeFormat) {
		Order order = null;
		ResponseEntity<OrderAdaptorResponse> response = null;
		OrderAdaptorRequest request=new OrderAdaptorRequest();
		SalesCheck salecheckObj= new SalesCheck(returnAuthId);
		salecheckObj.setStatusCode(1);	
		request.setRequestType("SALESCHECKNO");
		request.setSalesCheck(salecheckObj);
		/*storeDetailUtil.setStoreNumber(storeNo);
		storeDetailUtil.setStoreFormat(storeFormat);
		String dnsName = storeDetailUtil.getDNSForStore();*/
		String dnsName = MPUWebServiceUtil.getDNSForStore(storeNo, storeFormat);
		String serverURL =  dnsName + "/searchTenderReturns";
//		String serverURL = "http://localhost:8080/mcp_order_adaptor/confirmTenderReturn";
		if (serverURL != null) {
			logger.info("serverURL = ", serverURL);
			ObjectMapper mapper = new ObjectMapper();
			try {
				logger.info("Request = ", mapper.writeValueAsString(request));
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
			response = restTemplate.postForEntity(serverURL, requestEntity, OrderAdaptorResponse.class);
		}
		
		if(response!=null && null!=response.getBody() && null!=response.getBody().getStatus()){
			logger.info("searchTenderReturns response = ", response.getBody().getStatus().getCode());
		} else {
			logger.info("searchTenderReturns response = ", "null");
		}
		
		if(response != null && response.getBody().getStatus().getCode() == Status.STATUS_OK){
			ArrayList<Order> orders = (ArrayList<Order>) response.getBody().getOrders();
			if(orders != null && orders.size() > 0){
				order = orders.get(0);
				ObjectMapper mapper = new ObjectMapper();
				try {
					logger.info("searchTenderReturns  order = ", mapper.writeValueAsString(order));
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return order;
	}

	public List<OrderDTO> getTransDetail(String salescheck, String storeNo) throws DJException {
		
		logger.info("getTransId" ,"Enter getTransId salescheck : " + salescheck+ " --- storeNo : " + storeNo);
		
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		
		String sql = PropertyUtils.getProperty("select.trans.from.salescheck");
		parameterMap.put(SALESCHECK.name(),salescheck);
		parameterMap.put(STORE_NUMBER.name(),storeNo);
		
		logger.info("getTransId", "getTransId Sql : " + DJUtilities.createSQLCommand(sql, parameterMap));
		
		List<OrderDTO> order = (List<OrderDTO>) query(DJUtilities.leftPadding(storeNo, 5), sql, parameterMap, new LockerTransDetailMapper());
		
		return order;	

	}

	// insert or update vehicleInformation in db
	public Integer insertVehiclInfo(VehicleInfo vehicleInfo ,String notificationId,List<String>rqtIdToBeUpdated,String storeNumber) throws DJException {		
		logger.info("Inside PickUpServiceDAOImpl.insertVehiclInfo for notificationId: "+notificationId," storeNumber: "+storeNumber);
		boolean vehicleInfoPresent = false;
		String sql=null;
		List<Map<String,? super Object>> maps= new ArrayList<Map<String,? super Object>>();
		
		if(CollectionUtils.isNotEmpty(rqtIdToBeUpdated)){			
			for(String rqtId :rqtIdToBeUpdated){

				vehicleInfoPresent = checkVehicleInfoPresentInDB(storeNumber,rqtId,notificationId);
				logger.info("Vehicle Info is present in DB : ", vehicleInfoPresent);
				if(vehicleInfoPresent){// if vehicleInfo present in db got for updating vehicle info
					logger.info("Updating vehicleInfo  : ", "");
					sql=PropertyUtils.getProperty("update.into.request_identifier");
				}else{ // in vehicleinfo not present got for insert vehicle info for each rqt_id
					logger.info("Inserting vehicleInfo  : ", "");
					sql=PropertyUtils.getProperty("insert.into.request_identifier");
				}
				maps.add(getRequestIdentifierDetailsColumns(rqtId, vehicleInfo.getVehicleYear(),MpuWebConstants.VEHICLE_YEAR , storeNumber));
				maps.add(getRequestIdentifierDetailsColumns(rqtId, vehicleInfo.getMake(),MpuWebConstants.VEHICLE_MAKE , storeNumber));
				maps.add(getRequestIdentifierDetailsColumns(rqtId, vehicleInfo.getColor(),MpuWebConstants.VEHICLE_COLOR , storeNumber));
				maps.add(getRequestIdentifierDetailsColumns(rqtId, vehicleInfo.getType(),MpuWebConstants.VECHICLE_TYPE , storeNumber));
				maps.add(getRequestIdentifierDetailsColumns(rqtId, notificationId ,MpuWebConstants.SHOPIN_NOTIFICATION_ID , storeNumber));			
		
			}
			logger.info("insertVehiclInfo SQL: ", sql);
			Map<String,Object> paramArray[] = new HashMap[maps.size()];	
			logger.info("Exit PickUpServiceDAOImpl.insertVehiclInfo for notificationId: "+notificationId," storeNumber: "+storeNumber);
			return batchUpdate(DJUtilities.leftPadding(storeNumber, 5), sql, maps.toArray(paramArray)).length;			
		}
		return 0;					
	}
	
	//check vehicleInfomation available for rqt_id in db
	public boolean checkVehicleInfoPresentInDB(String storeNumber,String rqt_id,String notificationId) throws DJException{
		logger.info("Inside PickUpServiceDAOImpl.checkVehicleInfoPresentInDB for storeNumber: "+storeNumber," rqt_id: "+rqt_id);
		boolean vehicleInfoPresent = false;		
		CustomerDTO	 customerDTO = null;
		String sql = PropertyUtils.getProperty("select.from.details.order3");
		HashMap<String,Object> parameterMap = new HashMap<String,Object>();		
		parameterMap.put(RQT_ID.name(), rqt_id);
		parameterMap.put(STORE_NUMBER.name(), storeNumber);
		logger.info("checkVehicleInfoPresentInDB SQL: ", sql);
		List<CustomerDTO> customerDTOList =  query(DJUtilities.leftPadding(storeNumber, 5), sql, parameterMap, new IdentifierDTOMapper());
		if(customerDTOList.size()>0){
			customerDTO = customerDTOList.get(0);
		}		
		if(customerDTO != null){
			if(customerDTO.getVehicleYear()!=null && customerDTO.getVehicleType() !=null 
					&& customerDTO.getVehicleColor()!=null && customerDTO.getVehicleModel()!=null && customerDTO.getShopinNotificationId() !=null){
				
				String notificationIdDB = customerDTO.getShopinNotificationId();
				if(notificationIdDB != customerDTO.getVehicleType()){
					vehicleInfoPresent = true;
				}
								
			}
		}
		logger.info("Exit PickUpServiceDAOImpl.checkVehicleInfoPresentInDB for storeNumber: "+storeNumber," rqt_id: "+rqt_id+" vehicleInfoPresent: "+vehicleInfoPresent);
		return vehicleInfoPresent;		
	}	
	
	private Map<String, Object> getRequestIdentifierDetailsColumns(String rqt_id,String value,String type,String storeNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(RQT_ID.name(), rqt_id);
		param.put(STORE_NUMBER.name(), storeNo);
		param.put(IDENTIFIER_TYPE.name(), type);
		param.put(IDENTIFIER_VALUE.name(), value);
		return param;
	}	
	

	public void insertNotDeliverReasonInDEJ(HashMap<String, String> notDeliverReasonCodeMap) throws DJException {
		ObjectMapper mapper = new ObjectMapper();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> input = null;
		try {
			input = new HttpEntity<String>(mapper.writeValueAsString(notDeliverReasonCodeMap),headers);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		String dejServiceUrl = PropertyUtils.getProperty("mpu.notdeliver.reasoncode.dejservice");
		
		if(null != input) {
			/*ResponseEntity<String> response =*/ restTemplate.postForEntity(dejServiceUrl, input, String.class);
		}		
	}
	
	public int insertDataForShopinReport(ShopinRequestDTO shopinRequestDTO) throws DJException {
		logger.info("Inside PickUpServiceDAOImpl.insertDataForShopinReport for notification: "+ shopinRequestDTO.getNotificationID()+" storeNo: "+ shopinRequestDTO.getStoreNo(),"");
		ResponseEntity<MpuPickUpReportResposne> response = null;
		ShopinRequestDTO request=new ShopinRequestDTO();		
		String serverURL = PropertyUtils.getProperty("insert.shopin.report.url");
		logger.info("shopin report URL : "+serverURL,"");
		if(serverURL != null){
			HttpEntity<ShopinRequestDTO> requestEntity = new HttpEntity<ShopinRequestDTO>(request);
			response = restTemplate.postForEntity(serverURL, requestEntity, MpuPickUpReportResposne.class);
		}
		logger.info("Exit PickUpServiceDAOImpl.insertDataForShopinReport for notification: "+ shopinRequestDTO.getNotificationID()+" storeNo: "+ shopinRequestDTO.getStoreNo(),"");
		return response.getBody().getResponseCode();	
	}


	
/*********************for Post void *************/
/************************************************/
		public Order fetchForPostVoid(String salescheck,String store) {
			Order orderFromNPOS = null;
			try{
				OrderAdaptorRequest request=new OrderAdaptorRequest();
				request.setRequestType("SALESCHECKNO");
				request.setSalesCheck(new SalesCheck(salescheck));
				ResponseEntity<OrderAdaptorResponse> response = null;
				String serverURL = null; 
				String dnsName = MPUWebServiceUtil.getDNSForStore(store,"SearsRetail");
				//String dnsName = storeDetailUtil.getDNSForStore(storeNo,storeLocalTimeService.getStoreInformation(storeNo).getStoreFormat());			
				serverURL = dnsName+"/fetchVoidItemsForSalesCheckFromNPOS";
				logger.info("ServerURL for  finding Item with salescheck ...",serverURL);
				HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
				response = restTemplate.exchange(serverURL, HttpMethod.POST, requestEntity, OrderAdaptorResponse.class);
				orderFromNPOS = response.getBody().getOrders().get(0);
			}catch(Exception exception){}
			return orderFromNPOS;
		}

		public void updateQueueDetails(String store, String status, String origQty,
				String delQty, String remQty, String rqdId, String location) throws DJException {
				
				String sql = PropertyUtils.getProperty("update_post_void_item");
				HashMap<String,Object> parameterMap = new HashMap<String,Object>();	
				parameterMap.put(RQD_ID.name(), rqdId);
				parameterMap.put(STORE_NUMBER.name(), store);
				parameterMap.put(ITEM_STATUS.name(), status);
				parameterMap.put(QTY.name(), origQty);
				parameterMap.put(DELIVERED_QUANTITY.name(), delQty);
				parameterMap.put(REMAINING_QUANTITY.name(), remQty);
				parameterMap.put(FROM_LOCATION.name(), location);
				
				logger.info("==updateQueueDetails===parameterMap===", parameterMap);
				update(store, sql, parameterMap);
				
			}

		public String updateVoidPickUpToNPOS(OrderAdaptorRequest request,String storeNumber,String storeFormat) throws DJException{
			String relatedSalecheckNo=null;
			try {
					ResponseEntity<OrderAdaptorResponse> response = null;
			
					String serverURL = null; 
						String dnsName = MPUWebServiceUtil.getDNSForStore(storeNumber,storeFormat);
						serverURL = dnsName+"/updateVoidPickupToNPOS";
					logger.info("serverURL for updateVoidPickupToNPOS is ",serverURL);
					HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
					response = restTemplate.postForEntity(serverURL, requestEntity, OrderAdaptorResponse.class);
					if(response.getBody()!=null && response.getBody().getStatus()!=null ){
						if(Status.STATUS_OK == response.getBody().getStatus().getCode()){
							relatedSalecheckNo= (String) response.getBody().getRelatedSalecheckNo();
							logger.info("updateVoidPickUpToNPOS: Void of Pick up is SUCCESSFUL, related salecheck is ",relatedSalecheckNo);
						}else{
							logger.error("updateVoidPickUpToNPOS: Response status is not ok for VOID of Pick up status is ",response.getBody().getStatus().getCode());
						}
					}else{
						logger.error("error in response returned from ADAPTOR or NPOS","");
					}
					
				}catch ( Exception  ex){
					logger.error("Exception occured in updateVoidPickUpToNPOS while updating:" , ex.getMessage());
					logger.error("exception detail:", ex);
				}  
			
			return relatedSalecheckNo;
	}

		
		public int completeLockerQueueOrder(String storeNumber, String rqtId)
				throws DJException {
			
			String sql = PropertyUtils.getProperty("update_locker_queue_order");
			HashMap<String,Object> parameterMap = new HashMap<String,Object>();	
			parameterMap.put(RQT_ID.name(), rqtId);
			parameterMap.put(STORE_NUMBER.name(), storeNumber);
			parameterMap.put(STATUS.name(), "PICKED_UP");
			
			return update(DJUtilities.leftPadding(storeNumber, 5), sql,parameterMap);
		}

		
		public int completeLockerQueueItem(String storeNumber, String rqtId)
				throws DJException {
			String sql = PropertyUtils.getProperty("update_locker_queue_item");
			HashMap<String,Object> parameterMap = new HashMap<String,Object>();	
			parameterMap.put(RQT_ID.name(), rqtId);
			parameterMap.put(STORE_NUMBER.name(), storeNumber);
			parameterMap.put(STATUS.name(), "PICKED_UP");
			
			return update(DJUtilities.leftPadding(storeNumber, 5), sql,parameterMap);
		}

		
		public int completeLockerMPUTrans(String storeNumber, String transId)
				throws DJException {
			String sql = PropertyUtils.getProperty("update_locker_mpu_order");
			HashMap<String,Object> parameterMap = new HashMap<String,Object>();	
			parameterMap.put(TRANS_ID.name(), transId);
			parameterMap.put(STORE_NUMBER.name(), storeNumber);
			parameterMap.put(STATUS.name(), "PICKED_UP");
			
			return update(DJUtilities.leftPadding(storeNumber, 5), sql,parameterMap);
		}

		
		public int completeLockerMPUDetail(String storeNumber, String transId)
				throws DJException {
			String sql = PropertyUtils.getProperty("update_locker_mpu_details");
			HashMap<String,Object> parameterMap = new HashMap<String,Object>();	
			parameterMap.put(TRANS_ID.name(), transId);
			parameterMap.put(STORE_NUMBER.name(), storeNumber);
			parameterMap.put(STATUS.name(), "PICKED_UP");
			
			return update(DJUtilities.leftPadding(storeNumber, 5), sql,parameterMap);
		}


		public int updateOriginalJson(Map<String, String> originalJsonMap, String storeNum)
				throws DJException {
			logger.info("updateOriginalJson", "Enter updateOriginalJson storeNumber : " + storeNum + " -- Map : " + originalJsonMap.toString());
			
			String sql = PropertyUtils.getProperty("update_original_json");

			List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
			
			for (Map.Entry<String, String> map : originalJsonMap.entrySet()) {
				
				HashMap<String,Object> parameterMap = new HashMap<String,Object>();
				
				parameterMap.put(RQT_ID.name(),map.getKey());
				parameterMap.put(ORIGINALJSON.name(), map.getValue());
				parameterMap.put(STORE_NUMBER.name(), storeNum);
				
				paramList.add(parameterMap);
				
			}
			
			Map<String,Object> paramArray[] = new HashMap[paramList.size()];
			
			logger.info("updateOriginalJson", "Exit updateOriginalJson storeNumber : " + storeNum + " -- Map : " + originalJsonMap.toString());
			
			return batchUpdate(DJUtilities.leftPadding(storeNum, 5), sql, paramList.toArray(paramArray)).length;
		}


		public List<ItemDTO> fetchAllItemsForAssociate(String storeNumber,
				List<String> transIdList) throws DJException {
			logger.info("fetchAllItemsForAssociate", "Enter fetchAllItemsForAssociate storeNumber : " + storeNumber + " -- transIdList : " + transIdList);
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			String sql = PropertyUtils.getProperty("select.all.request_mpu_details");
			param.put(TRANS_ID.name(), transIdList);
			
			logger.info("fetchAllItemsForAssociate", "fetchAllItemsForAssociate Sql : " + DJUtilities.createSQLCommand(sql, param));
			
			List<ItemDTO> allItemList = (List<ItemDTO>) query(DJUtilities.leftPadding(storeNumber, 5), sql, param, new RequestMPUDetailsMapper());
			
			logger.info("Exiting", "fetchAllItemsForAssociate allItemList : " + allItemList);
			
			return allItemList;
			
		}

		public List<PackageDTO> getAllPackageInfo(String storeNumber,List<String> transIdList) throws DJException {

			logger.info("getAllPackageInfo", "Enter getAllPackageInfo storeNumber : " + storeNumber + " -- transIdList : " + transIdList);
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			String sql = PropertyUtils.getProperty("select.all.from.package");
			param.put(TRANS_ID.name(), transIdList);
			param.put(STORE_NUMBER.name(), storeNumber);
			
			logger.debug("fetchOderForAssociate", "fetchOderForAssociate Sql : " + DJUtilities.createSQLCommand(sql, param));
			
			List<PackageDTO> allPackageList = (List<PackageDTO>) query(DJUtilities.leftPadding(storeNumber, 5), sql, param, new PackageDTOMapper());
			
			logger.info("Exiting", "getAllPackageInfo ");
			
			return allPackageList;
			
		
		}

		public Integer updateTimeForMPUOrder(String storeNo, String rqtId,String dateTime) throws DJException {
			logger.info("updateTimeForMPUOrder" ,"Enter updateTimeForMPUOrder storeNo : " + storeNo + " --- rqtId : " + rqtId + " -- dateTime : " + dateTime);
			
			String sql = PropertyUtils.getProperty("update_queue_request");
			Map<String,String> paramMap = new HashMap<String,String>();
			
			paramMap.put(RQT_ID.name(), rqtId);
			paramMap.put(STORE_NUMBER.name(), storeNo);
			paramMap.put(UPDATE_TIMESTAMP.name(), dateTime);

			logger.info("updateTimeForMPUOrder", "updateTimeForMPUOrder Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
			
			return update(DJUtilities.leftPadding(storeNo, 5), sql,paramMap);
			
		}

		/*@Override
		public Map<String, String> getOriginalJson(List<String> rqtIds, String storeNum) throws DJException {
			
			logger.info("getOriginalJson", "Enter getOriginalJson storeNumber : " + storeNum + " -- rqtIds : " + rqtIds);
						
			List<String> rqtIdList = new ArrayList<String>();

			Map<String,Object> paramMap = new HashMap<String,Object>();
			
			String sql = PropertyUtils.getProperty("select.original.json.for.rqtids");
			
			paramMap.put(RQT_ID.name(), rqtIds);
			paramMap.put(STORE_NUMBER.name(), storeNum);
			
			logger.info("getOriginalJson", "getOriginalJson Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
			
			Map<String,String> map = (Map<String,String>) queryForList(storeNum, sql, paramMap);
			
			String sql = PropertyUtils.getProperty("select.original.json.for.rqts");
			
			logger.info("getOriginalJson", "Exit getOriginalJson storeNumber : " + storeNum + " -- rqtIds : " + rqtIds);
			
			return null;
		}*/

	
		
/**********************for Post void *************/
/**********8888888888*****************************/


	public ItemMeta getDivItem(String upc, String storeNo, String itemIdType) throws DJException{
			ItemMeta meta = new ItemMeta();
			try{
			logger.info("Entering MPUWebServiceDAOImpl.getDivItem	item:",upc);
			ResponseEntity<ItemMasterResponse> response = null;
			
			String itemServiceurl = PropertyUtils.getProperty("item_master_url");
			logger.info("getStockLocator","itemServiceurl getDivItem :" + itemServiceurl);
			String finalUrl = itemServiceurl + "/" + upc + "/"+ storeNo + "?itemIdType=" + itemIdType;
			logger.info("getStockLocator","Final URL from getDivItem :" + finalUrl);
			
			response = (ResponseEntity<ItemMasterResponse>)DJServiceLocator.get(finalUrl,ItemMasterResponse.class);
			
			if(response.getStatusCode() == HttpStatus.OK){
				ItemMasterResponse itemDetails = response.getBody();
				if (null != itemDetails){
					List<ItemMeta> itemList = itemDetails.getItemDetailsList();
					if (itemList != null & !itemList.isEmpty()){
						 meta = itemList.get(0);
						return meta;
					}
				}
			}
			else{
				ItemMasterResponse itemDetails = response.getBody();
				itemDetails.getWsResponeMeta().getErrorCode();
				itemDetails.getWsResponeMeta().getErrorMessage();
				throw new DJException(itemDetails.getWsResponeMeta().getErrorCode(), itemDetails.getWsResponeMeta().getErrorMessage());
				//logger.error("getStockLocator","Could not get stock location from service.Exception during service call");
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return meta;
		}
	/**
	 * This method returns the rqd_id list of items which where never binned/staged
	 * @param itemListToBeUpdated
	 * @return
	 * @author nkhan6
	 */
	public List<String> getOpenItemsFromDb(List<ItemDTO> itemListToBeUpdated){
		List<String>rqdList = new ArrayList<String>();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		List<String> rqdIds = new ArrayList<String>();
		String storeNumber = null;
		if(null!=itemListToBeUpdated){
			storeNumber = itemListToBeUpdated.get(0).getStoreNumber();
			for(ItemDTO item : itemListToBeUpdated){
				rqdIds.add(item.getRqdId());
			}
		
			paramMap.put("rqdIds", rqdIds);
			String sql = PropertyUtils.getProperty("select.binned.rqdlist");
			
			try {
				List<String> binnedRqdList = (List<String>)queryForList(storeNumber, sql, paramMap, String.class);
				for(String rqdid :rqdIds){
					if(null != binnedRqdList && !binnedRqdList.contains(rqdid)){
						rqdList.add(rqdid);
					}
				}
			} catch (DJException e) {
				logger.error("Inside getOpenItemsFromDb = ", e);
			}
		}
		return rqdList;
	}

	
	public Integer updateSecuredDBFlag(Map<String, Boolean> securedFlagMap, String storeNum) throws DJException {
		logger.info("Entering PickUpServiceDAOImpl.updateSecuredDBFlag	securedFlagMap:",securedFlagMap);
		
		String sql = PropertyUtils.getProperty("update_unsecured_flag");
		
		List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
		
		for (Map.Entry<String, Boolean> map : securedFlagMap.entrySet()) {
			
			HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put(RQT_ID.name(), map.getKey());			
			if(map.getValue()) 
				paramMap.put(UNSECURED_FLAG.name(), MpuWebConstants.N); 
			else
				paramMap.put(UNSECURED_FLAG.name(), MpuWebConstants.Y);
			
			paramMap.put(STORE_NUMBER.name(), storeNum);
			
			paramList.add(paramMap);
		}
		
		Map<String,Object> paramArray[] = new HashMap[paramList.size()];
		
		logger.info("Exiting PickUpServiceDAOImpl.updateSecuredDBFlag	securedFlagMap:",securedFlagMap);
		
		return batchUpdate(DJUtilities.leftPadding(storeNum, 5), sql, paramList.toArray(paramArray)).length;
	}

	// Do not delete
	/*@Override
	public List<Map<String, Object>> checkForPaymentList(List<String> rqtIds, String storeNum) throws DJException {
		
		logger.info("Entering PickUpServiceDAOImpl.checkForPaymentList	rqtIds:",rqtIds + "‰storeNum" + storeNum);
		
		String sql = PropertyUtils.getProperty("select_payment_list_for_rqtid");
		
		HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
			
		paramMap.put(RQT_ID.name(), rqtIds);
		paramMap.put(STORE_NUMBER.name(), storeNum);
		
		List<Map<String, Object>> map =(List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(storeNum, 5), sql, paramMap);
		
		logger.info("Exiting PickUpServiceDAOImpl.checkForPaymentList	rqtIds:",rqtIds + "storeNum" + storeNum);
		return map;
	}	*/
	
	
	public int deleteAllPaymentInfo(List<String> rqtIdList,String storeNum) throws DJException{
		logger.info("Entering PickUpServiceDAOImpl.checkForPaymentList	rqtIds:",rqtIdList + "storeNum" + storeNum);
		
		String sql = PropertyUtils.getProperty("delete_payment_list_for_rqtid");
		HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("rqtIdList", rqtIdList);
		paramMap.put("storeNumber", storeNum);
		return update(DJUtilities.leftPadding(storeNum, 5), sql,paramMap);
		
		
		
	}

	public void assignUser(String storeNumber, int transId, String associateId,
			String rqtId) throws DJException {
		// TODO Auto-generated method stub
		
	}

	public int insertIntoMPUAssociateReport(MPUAssociateReportDTO associateReport, String CurrTime)throws DJException {
		logger.info("Entering PickUpServiceDAOImpl.insertIntoMPUAssociateReport	associateReport:",associateReport);
		
		String sql = PropertyUtils.getProperty("insert_into_mpu_associate_report");
		
		HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MPU_ASSOCIATE_REPORT_CREATED_TS.name(), CurrTime);
		paramMap.put(OPERATION_TYPE.name(), associateReport.getOperationType());
		paramMap.put(OPERATION_VALUE.name(), associateReport.getOperationValue());
		paramMap.put(INPUT_TYPE.name(), associateReport.getInputType());
		paramMap.put(ORDER_TYPE.name(), associateReport.getOrderType());
		paramMap.put(STORE_NUMBER.name(), associateReport.getStoreNum());
		paramMap.put(ASSOCIATE_ID.name(), associateReport.getAssociateId());
		paramMap.put(RQD_ID.name(), associateReport.getRqdId());
		paramMap.put(WORK_ITEM_STATUS.name(), associateReport.getItemStatus());
		
		logger.info("Exiting PickUpServiceDAOImpl.insertIntoMPUAssociateReport	associateReport:",associateReport);
		
		return namedParameterJdbcTemplateWFD.update(sql,paramMap);
	}

	public int updateDivOperation(List<String> rqdIdList, String storeNum) throws DJException {
		logger.info("Entering PickUpServiceDAOImpl.updateDivOperation	rqdIdList:",rqdIdList);
		
		String sql = PropertyUtils.getProperty("update_mpu_associate_report");
		
		List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
		
		for (String rqdId : rqdIdList) {
			
			HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put(RQD_ID.name(), rqdId);						
			paramMap.put(STORE_NUMBER.name(), storeNum);
			paramMap.put(WORK_ITEM_STATUS.name(), MpuWebConstants.PICKED_UP);
			
			paramList.add(paramMap);
		}
		
		logger.info("Exiting PickUpServiceDAOImpl.updateDivOperation","");
		
		Map<String,Object> paramArray[] = new HashMap[paramList.size()];
		
		return namedParameterJdbcTemplateWFD.batchUpdate(sql, paramList.toArray(paramArray)).length;
		
	}

	public List<Map<String, Object>> checkIfRequestAlreadyCompleted(String transId, String storeNo) throws DJException {
		
		logger.info("Entering PickUpServiceDAOImpl.checkIfRequestAlreadyCompleted	transId:",transId);
		
		String sql = PropertyUtils.getProperty("select_from_mpu_trans");
		//sql = sql + PropertyUtils.getProperty("check_if_request_already_completed");
		
		HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put(TRANS_ID.name(), transId);
		paramMap.put(STORE_NUMBER.name(), storeNo);
		paramMap.put(PICKED_UP.name(), MpuWebConstants.PICKED_UP);
		paramMap.put(COMPLETED.name(), MpuWebConstants.COMPLETED);
		paramMap.put(RETURNED.name(), MpuWebConstants.RETURNED);
		
		logger.debug("checkIfRequestAlreadyCompleted", "checkIfRequestAlreadyCompleted Sql : " + DJUtilities.createSQLCommand(sql, paramMap));
		
		List<Map<String, Object>> map =(List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(storeNo, 5), sql, paramMap);
		logger.info("Exiting PickUpServiceDAOImpl.checkIfRequestAlreadyCompleted","map : " + map);
			
		if(map.size()==0) {
			//return Boolean.FALSE;
			return null;
		} else {
			//return Boolean.TRUE;
			return map;
		}
		
	}
	
	public List<ItemDTO> getAllItemsForPickUpFromSalescheckList(String customerId, List<String> salescheckList, String storeNumber) throws DJException {
		logger.info("Entering PickUpServiceDAOImpl.getAllItemsForPickUpFromSalescheckList	salescheckList:",salescheckList + " storeNumber:" + storeNumber);
		
		Map<String,Object> paramMap=new HashMap<String,Object>();

		String sql = PropertyUtils.getProperty("select.from.details.order18");
		
		paramMap.put(SALESCHECK.name(), salescheckList);
		paramMap.put(STORE_NUMBER.name(), storeNumber);
		paramMap.put(CUSTOMER_ID.name(), customerId);
		
		logger.debug("getAllItemsForPickUpFromSalescheckList","getAllItemsForPickUpFromSalescheckList	SQL:" + DJUtilities.createSQLCommand(sql, paramMap));
		List<ItemDTO> pickUpOrdersDTO = (List<ItemDTO>) query(DJUtilities.leftPadding(storeNumber, 5), sql, paramMap, new PickUpOrdersMapper());		
		logger.info("Exiting PickUpServiceDAOImpl.getAllItemsForPickUpFromSalescheckList ","pickUpOrdersDTO : " + pickUpOrdersDTO);
		return pickUpOrdersDTO;
	}
	
	public List<PackageDTO> getPackageList(String storeNumber, String requestId) throws DJException {
		logger.debug("Entering" ,"MPUWebServiceDAOImpl.getPackageList");
		logger.debug("Entering MPUWebServiceDAOImpl.getPackageList	storeNumber:",storeNumber +": requestId: "+requestId);
		String sql=PropertyUtils.getProperty("select.from.package");
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(STORE_NUMBER.name(), storeNumber);
		paramMap.put(RQT_ID.name(), requestId);
		logger.debug("Existing" ,"MPUWebServiceDAOImpl.getPackageList");
		return (List<PackageDTO>) query(storeNumber,sql, paramMap,new PackageDTOMapper());
	}

	public List<Map<String, Object>> getPackageInfoForRqtList(List<String> rqtIdList,String storeNum) throws DJException {
		logger.info("Entering PickUpServiceDAOImpl.getPackageInfoForRqtList	rqtIdList:",rqtIdList + " storeNum:" + storeNum);
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		
		String sql=PropertyUtils.getProperty("select.list.from.package");
		
		paramMap.put(RQT_ID.name(), rqtIdList);
		paramMap.put(STORE_NUMBER.name(), storeNum);
		
		logger.debug("getAllItemsForPickUpFromSalescheckList","getPackageInfoForRqtList	SQL:" + DJUtilities.createSQLCommand(sql, paramMap));
		List<Map<String, Object>> map =(List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(storeNum, 5), sql, paramMap);		
		logger.info("Exiting PickUpServiceDAOImpl.getPackageInfoForRqtList ","map : " + map);
		
		return map;
	}

	public int insertPackageInfo(List<PackageDTO> packageInfoList,String storeNum) throws DJException {
		
		logger.info("Entering","Entering insertPackageInfo packageInfoList : " + packageInfoList + "	-- storeNum : " + storeNum);
		
		String sql = PropertyUtils.getProperty("insert.into.package");

		List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
		
		
		for(PackageDTO packageInfo: packageInfoList)
		{
			HashMap<String, ? super Object> param = new HashMap<String, Object>();
					
			param.put(RQT_ID.name(), packageInfo.getRqtId());
			param.put(STORE_NUMBER.name(), packageInfo.getStoreNumber());
			param.put(FROM_LOCATION.name(), "FLR");			
			param.put(CREATED_BY.name(), packageInfo.getCreatedBy());
			
			/*if(packageInfo.getPackageNumber()==null||"".equals((packageInfo.getPackageNumber()))){
				String salescheck=packageInfo.getSalescheck();
				if (salescheck!=null&&salescheck.length()>=12){
				packageNumber=salescheck.substring(8,12)+org.apache.commons.lang.StringUtils.leftPad(new Integer(count).toString(),2,"0");
				}
			}*/
			param.put(PKG_NBR.name(), packageInfo.getPackageNumber());
			param.put(TO_LOCATION.name(), packageInfo.getToLocation());
			logger.info("==param==", param);
			
			if( packageInfo.getPackageNumber()!=null){
			paramList.add(param);
			}
		}
		
		Map<String,Object> paramArray[] = new HashMap[paramList.size()];
		
		logger.info("Existing" ,"PickUpWebServiceDAOImpl.insertPackageInfo");
				
		return batchUpdate(DJUtilities.leftPadding(storeNum, 5), sql, paramList.toArray(paramArray)).length;
	}

	public List<PackageDTO> getPackageInfoFromDB(List<String> salescheckPackageList, String storeNum) throws DJException {
		logger.info("Entering PickUpServiceDAOImpl.getPackageInfoFromDB	salescheckPackageList:",salescheckPackageList);
		
		String sql = PropertyUtils.getProperty("fetch_package_info_from_mcp_workflow");
		
		List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
			
		HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put(SALESCHECK.name(), salescheckPackageList);						
		paramMap.put(STORE_NUMBER.name(), storeNum);
		paramMap.put(CLOSED.name(), MpuWebConstants.CLOSED);
		
		Map<String,Object> paramArray[] = new HashMap[paramList.size()];
		List<PackageDTO> packageInfoList = (List<PackageDTO>) namedParameterJdbcTemplateWFD.query(sql, paramMap, new PackageInfoMapper());
		
		logger.info("Entering PickUpServiceDAOImpl.getPackageInfoFromDB	packageInfoList:",packageInfoList);
		return packageInfoList;

	}
	
	public String getAssociateId(){
		String associateId=(String)MDC.get("associateId");
		logger.info("associateId from MDC", associateId);
		if(associateId==null||"".equals(associateId)){
			associateId="000075";
		}
		return associateId;

	}
	//Exchange related APIs
/*public ArrayList<Order> getOrdersFromEI5NPOS(PickUpDTO pickUpDTO, String numType, String identifierValue, String reqType) {
		
		logger.info("getAllOrdersForPickUpNPOS","Entering getAllOrdersForPickUpNPOS pickUpDTO : " + pickUpDTO + " -- numType : " + numType + " -- identifierValue : " + identifierValue+ " -- reqType : " + reqType);
		ArrayList<Order> orderList = new ArrayList<Order>();
		OrderAdaptorRequest request = new OrderAdaptorRequest();
		String storeFormat = pickUpDTO.getStoreFormat();
		String serverURL = MPUWebServiceUtil.getDNSForStore(pickUpDTO.getStoreNumber(),storeFormat);
		Integer status = 2;
		if("RETURN".equalsIgnoreCase(reqType)) {
			
			serverURL = DJUtilities.concatString(serverURL, "/fetchReturnOrdersfromNPOS");
			status = 1;
			
		} else {
			status = 2;
			serverURL = DJUtilities.concatString(serverURL, "/fetchOrdersfromNPOS");
		}
		
		if(numType.equalsIgnoreCase("PHONE")) {
			
			PhoneNum phoneNum = new PhoneNum(identifierValue);
			phoneNum.setStatusCode(status);
			request.setRequestType("PHONENUM");
			request.setPhoneNum((PhoneNum)phoneNum);
			
		} else if(numType.equalsIgnoreCase("SALESCHECK")) {
			
			SalesCheck saleCheck = new SalesCheck(identifierValue);
			saleCheck.setStatusCode(status);
			request.setRequestType("SALESCHECKNO");
			request.setSalesCheck((SalesCheck)saleCheck);
			
		} else if(numType.equalsIgnoreCase("ACCOUNT_NUMBER")) {
			
			CreditCard creditCard = new CreditCard(identifierValue);
			creditCard.setStatusCode(status);
			request.setRequestType("CRRDITCARD");
			request.setCreditCard((CreditCard)creditCard);
			
		} else if(numType.equalsIgnoreCase("CUSTOMERID")) {
			
			CustomerID customerID = new CustomerID(pickUpDTO.getAddressId(),identifierValue,pickUpDTO.getIdStsCd());
			customerID.setStatusCode(status);
			request.setRequestType("CUSTOMERID");
			request.setCustomerID((CustomerID)customerID);
			
		} else if(numType.equalsIgnoreCase("MATCHKEY")) {
			
			MatchKey matchKey = new MatchKey();

			matchKey.setLastName(pickUpDTO.getCustomerName());
			matchKey.setStAddress1(pickUpDTO.getAddress1());
			matchKey.setZipCode(pickUpDTO.getZipcode());
			matchKey.setStatusCode(status);
			request.setRequestType("MATCHKEY");
			request.setMatchKey(matchKey);
		}
		
		logger.info("getAllOrdersForPickUpNPOS serverURL",serverURL);
		
		HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
		logger.info("getAllOrdersForPickUpNPOS requestEntity",requestEntity);
		ResponseEntity<OrderAdaptorResponse> response = restTemplate.postForEntity(serverURL, requestEntity, OrderAdaptorResponse.class);
		logger.info("getAllOrdersForPickUpNPOS response",response);
		if(!StringUtils.isEmpty(response) && !StringUtils.isEmpty(response.getBody().getStatus())) {
			
			if(Status.STATUS_OK == response.getBody().getStatus().getCode()) {
				
				orderList = (ArrayList<Order>) response.getBody().getOrders();
				
				logger.info("getAllOrdersForPickUpNPOS", "response status : "+Status.STATUS_OK);
			}
			else{
				logger.info("getAllOrdersForPickUpNPOS", "response status : "+Status.NOTFOUND);
			}
		}
		else{
			logger.info("getAllOrdersForPickUpNPOS", "response from fetchOrdersfromNPOS service is null");
		}
		
		logger.debug("getAllOrdersForPickUpNPOS", "Exit getAllOrdersForPickUpNPOS orderList : " + orderList);
		String response = null;
		JAXBContext jaxbContext;
		OrderAdaptorResponse orderAdaptorResponse = null;
		try {
			jaxbContext = JAXBContext.newInstance(OrderAdaptorResponse.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(response);
			orderAdaptorResponse = (OrderAdaptorResponse) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderList = (ArrayList<Order>)orderAdaptorResponse.getOrders();
		
		return  orderList;
	}*/

public boolean checkForRI5Order(String returnAuthCode ,String storeNo) throws DJException{
	//select count(*) from request_queue_trans trans where trans.return_auth_code = :RETURN_AUTH_CODE and 
	//trans.request_type = :REQUEST_TYPE and lpad(trans.store_number, 5, 0) = lpad(:store_number, 5, 0)
	boolean RI5Flag = false;

	final String queryString = PropertyUtils.getProperty("select.order.ri5.salescheck");
	HashMap<String,Object> parameterMap = new HashMap<String,Object>();
	parameterMap.put(RETURN_AUTH_CODE.name(), returnAuthCode);
	parameterMap.put(REQUEST_TYPE.name(),"RETURNIN5");
	parameterMap.put(STORE_NUMBER.name().toLowerCase(),storeNo);


	//List<OrderDTO> orders = (List<OrderDTO>) query(DJUtilities.leftPadding(storeNo, 5), queryString, parameterMap, new OrderDTOMapper());
	int noOfRows = queryForInt(DJUtilities.leftPadding(storeNo, 5), queryString, parameterMap);
	if(noOfRows > 0){
		RI5Flag = true;
	}

	return RI5Flag;
}

public int getReturnIn5QueueTrans(String returnAuthCode, String storeNumber) throws DJException{
	logger.info("Entering PickUpServiceDAOImpl.getReturnIn5QueueTrans	rqtIds:","Return Authorization Code :" + returnAuthCode);
	int result=0;
	String sql = PropertyUtils.getProperty("select.parentrqtid.queuetrans");
	HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
	
	paramMap.put("request_type", "RETURNIN5");
	paramMap.put("return_auth_code", returnAuthCode);
	paramMap.put("store_number", storeNumber);
	try{
	result= queryForInt(DJUtilities.leftPadding(storeNumber, 5), sql, paramMap);
	}
	catch(Exception e){
		logger.error("no RI5 info", e);
	}
	return result;
}

	/**
	 * This method returns Customer information based on EmailID
	 * @param String emailID
	 * @param String storeNo
	 * @param String storeFormat
	 * @return Customer
	 * @author nkumar1
	 */
	public ArrayList<Customer> fetchCustomersfromNPOSByEmailID(String emailID ,String storeNo, String storeFormat) {
		logger.info("Entering PickUpServiceDAOImpl.fetchCustomersfromNPOSByEmailID	emailID:","" + emailID);
		ResponseEntity<OrderAdaptorResponse> response = null;
		ArrayList<Customer> customerList=new ArrayList<Customer>();
		try{
			String serverURL = MPUWebServiceUtil.getDNSForStore(storeNo,storeFormat);
			serverURL = DJUtilities.concatString(serverURL, "/getCustomerBasedOnEmail?emailID="+emailID);
			//serverURL = "http://10.44.0.11:8080/mcp_order_adaptor/getCustomerBasedOnEmail?emailID=navneet.kumar@searshc.com";
			//serverURL = "http://10.109.188.11:8080/mcp_order_adaptor/getCustomerBasedOnEmail?emailID=navneet.kumar@searshc.com";
			response=restTemplate.getForEntity(serverURL, OrderAdaptorResponse.class);
			
			if(response!=null && response.getBody()!=null && response.getBody().getStatus()!=null){
				if(Status.STATUS_OK == response.getBody().getStatus().getCode()){
					customerList= (ArrayList<Customer>) response.getBody().getCustomers();
					logger.info("PickUpServiceDAOImpl.fetchCustomersfromNPOSByEmailID", "response status : "+Status.STATUS_OK);
				}
				else{
					logger.info("PickUpServiceDAOImpl.fetchCustomersfromNPOSByEmailID", "response status : "+Status.NOTFOUND);
				}
			}
			else{
				logger.info("PickUpServiceDAOImpl.fetchCustomersfromNPOSByEmailID", "response from fetchOrdersfromNPOS service is null");
			}
			
		} catch (Exception e) {
			logger.info("exception occred in fetchOrdersfromNPOSByEmailID emailID :","" + emailID);
		}
		return  customerList;
	}
	
	public String fetchNotificationIdByRqtId(String rqt_id,String storeNumber) throws DJException {
		logger.info("Inside PickUpServiceDAOImpl.fetchNotificationIdByRqtId for storeNumber: "+storeNumber," rqt_id: "+rqt_id);			
		CustomerDTO	 customerDTO = null;
		String sql = PropertyUtils.getProperty("select.from.details.order3");
		HashMap<String,Object> parameterMap = new HashMap<String,Object>();		
		parameterMap.put(RQT_ID.name(), rqt_id);
		parameterMap.put(STORE_NUMBER.name(), storeNumber);
		logger.info("checkVehicleInfoPresentInDB SQL: ", sql);
		List<CustomerDTO> customerDTOList =  query(DJUtilities.leftPadding(storeNumber, 5), sql, parameterMap, new IdentifierDTOMapper());
		if(CollectionUtils.isNotEmpty(customerDTOList)){
			customerDTO = customerDTOList.get(0);
		}		
		if(customerDTO != null){
			logger.info("Exit PickUpServiceDAOImpl.fetchNotificationIdByRqtId for storeNumber: "+storeNumber," rqt_id: "+rqt_id+" notificationId: "+customerDTO.getShopinNotificationId());
			return customerDTO.getShopinNotificationId();
		}else{
			logger.info("Exit PickUpServiceDAOImpl.fetchNotificationIdByRqtId for storeNumber: "+storeNumber," rqt_id: "+rqt_id+" notificationId: "+null);
			return null;	
		}		
	}

	
	public int pushNotificationToShopInDAO(String xmlStringForPushNotify) {
		
		logger.info("Inside pushNotificationToShopInDAO :" , xmlStringForPushNotify);
		int retryCount = 0;
		int responseStatusCode = 0;
		ResponseEntity<String> response = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String postStr = null;
		postStr = PropertyUtils.getProperty("MPU_DELIVER_STATUS_API_URL");
		while (retryCount <= 3) {
			try {
				HttpEntity request = new HttpEntity(xmlStringForPushNotify,
						headers);
				response = restTemplate.postForEntity(postStr, request,
						String.class);
				responseStatusCode = response.getStatusCode().value();
				if (responseStatusCode == 200) {
					logger.info("Response String: " + response.toString(), "");
					break;
				} else {
					retryCount++;
					logger.info("Error in push notification service:"
							+ responseStatusCode, "");
				}
			} catch (Exception e) {
				logger.error(
						"Exception pushNotificationToShopIn for count: ",
						retryCount);
				retryCount++;
			}
		}
		return responseStatusCode;
	}
	
	public List<Map<String, Object>> getMPUTransData(String storeNum, Integer transId) throws DJException{
		
		logger.info("Entering PickUpServiceDAOImpl.checkIfRequestAlreadyCompleted	transId:",transId);
		
		String sql = PropertyUtils.getProperty("select_from_mpu_trans");
		
		HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put(TRANS_ID.name(), transId);
		paramMap.put(STORE_NUMBER.name(), storeNum);
		List<Map<String, Object>> map =(List<Map<String, Object>>) queryForList(DJUtilities.leftPadding(storeNum, 5), sql, paramMap);
		
		logger.info("Exiting PickUpServiceDAOImpl.checkIfRequestAlreadyCompleted	transId:",transId);
		return map;
		
	}
	
	
	public int updateUPC(Map<String,String> rqdIdUpcMap,String storeNum) throws DJException{
		logger.info("updateUPC", "Enter updateUPC storeNumber : " + storeNum + " -- Map : " + rqdIdUpcMap);
		
		String sql = PropertyUtils.getProperty("update_upc");

		List<HashMap<String, ? super Object>> paramList = new  ArrayList<HashMap<String, ? super Object>>();
		
		for (String rqdId : rqdIdUpcMap.keySet()) {
			
			HashMap<String,Object> parameterMap = new HashMap<String,Object>();
			
			parameterMap.put(RQD_ID.name(),rqdId);
			parameterMap.put(UPC.name(), rqdIdUpcMap.get(rqdId));
			parameterMap.put(STORE_NUMBER.name(), storeNum);
			
			paramList.add(parameterMap);
			
		}
		
		Map<String,Object> paramArray[] = new HashMap[paramList.size()];
		
		logger.info("updateOriginalJson", "Exit updateOriginalJson storeNumber : " + storeNum);
		
		return batchUpdate(DJUtilities.leftPadding(storeNum, 5), sql, paramList.toArray(paramArray)).length;
		
	}
	public String getRetAuthCode(String storeNum,String rqtId) throws DJException{
		logger.info("getRetAuthCode()", "Entering getRetAuthCode() : " + "storeNum: "+storeNum+ "rqtId: "+rqtId);
		String sql = PropertyUtils.getProperty("select_ret_auth_code");
		HashMap<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put(RQT_ID.name(), rqtId);
		
		String retAuthCode=(String)query(storeNum, sql, paramMap, new ResultSetExtractor<Object>() {
			public Object extractData(ResultSet resultSet) {
			 try{if(resultSet!=null){
				 if(resultSet.next()){
					 return resultSet.getString(1);
				 }
			 }
			 }catch(Exception exception){
				 logger.error("getRetAuthCode()", exception);
			 }
			 return null;
		}
	});
		
		return retAuthCode;
	}

	public void updateQueueTrans(String store, String itemStatus, String rqtId) throws DJException {
		

	
	String sql = PropertyUtils.getProperty("update_post_void_request");
	HashMap<String,Object> parameterMap = new HashMap<String,Object>();	
	parameterMap.put(REQUEST_STATUS.name(), itemStatus);
	parameterMap.put(STORE_NUMBER.name(), store);
	parameterMap.put(RQT_ID.name(), rqtId);
	
	logger.info("==updateQueueTrans===parameterMap===", parameterMap);
	update(store, sql, parameterMap);
	}
}