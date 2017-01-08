package com.searshc.mpuwebservice.processor.impl;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sears.dej.interfaces.json.generated.stocklocatorresponse.LocationList;
import com.searshc.mpuwebservice.domain.PrintReceiptInfo;
import com.searshc.mpuwebservice.domain.TransactionDetailsResponse;
import com.searshc.mpuwebservice.domain.response.CheckoutResponse;
import com.searshc.mpuwebservice.domain.response.Location;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.ws.DJServiceLocator;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderAdaptorResponse;
import com.sears.mpu.backoffice.domain.OrderItem;
import com.searshc.mpuwebservice.bean.ExceptionReportResults;
import com.searshc.mpuwebservice.bean.IdentifierDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.bean.SOAItemDTO;
import com.searshc.mpuwebservice.dao.KioskSOADao;
import com.searshc.mpuwebservice.dao.impl.KioskSOADaoImpl;
import com.searshc.mpuwebservice.domain.response.StoreLocationResponse;
import com.searshc.mpuwebservice.util.HttpSecurityValidator;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;


@Service("soaThirdPartyAPIHelper")
public class SOAThirdPartyAPIHelperImpl implements SOAThirdPartyAPIHelper {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private KioskSOADao kioskSOADaoImpl;

	private static transient DJLogger logger = DJLoggerFactory.getLogger(SOAThirdPartyAPIHelperImpl.class);
	
	/****************************************************DISPLAY ACCESSORY API *****************************************************/
	/**
	 * The method provides the location response for the given input params.
	 * @param division
	 * @param itemNumber
	 * @param skunumber
	 * @param storeNumber
	 * @param storeFormat
	 * @return StoreLocationResponse
	 */
	public StoreLocationResponse getStorelocation(String division,String itemNumber,String skunumber,String storeNumber,String storeFormat)  throws DJException{
		logger.info("getStorelocation","Div-Item-Sku "+ division + "-" + itemNumber + "-" + skunumber);

		StoreLocationResponse locationResponse = null;

		StringBuffer buffer = null;
		try {
			String automationFlag = MPUWebServiceUtil.getdnsForSOA("automation.flag");
			/*Start : This code is added for Automation testing */	
			/*if(null != storeNumber && storeNumber.contains("1333") && null != automationFlag && 
					automationFlag.equalsIgnoreCase("Y")){
				locationResponse = new StoreLocationResponse();
				Location location = new Location();
				location.setAreaCode("ACC");
				location.setBackroomQty("1");
				List<Location> ll = new ArrayList<Location>();
				ll.add(location);
				locationResponse.getLocationList().setLocation(ll);		
						
				return locationResponse;
			}*/
			/*End : This code is added for Automation testing */

			buffer = new StringBuffer(MPUWebServiceUtil.getdnsForSOA("SOAstockLocation")+"/DEJServices/RestServices/message/getMPULocations?");

			//formatting div, item and sku
			division = formatNumber(division, 3);
			itemNumber = formatNumber(itemNumber, 5);
			skunumber = formatNumber(skunumber, 3);


			if(MPUWebServiceUtil.getdnsForSOA("GetDSNForQA").equalsIgnoreCase("Y")){

				//	logger.info("getStorelocation","MPUWebServiceUtil.getdnsForSOA is Y");
				buffer.append("storeNo="+"00957"+"&storeFormat="+storeFormat+"&divDept="+division+"&item="+itemNumber+"&sku="+skunumber);
			}else{
				//logger.info("getStorelocation","MPUWebServiceUtil.getdnsForSOA is N");
				buffer.append("storeNo="+storeNumber+"&storeFormat="+storeFormat+"&divDept="+division+"&item="+itemNumber+"&sku="+skunumber);
			}

			HttpSecurityValidator.trustSelfSignedSSL();
			URI urlLink=new URI(buffer.toString());


			logger.error("KioskSOAServiceImpl.getStorelocation <<RestTemplate>><<Begins>>URL GET : " + buffer.toString(),"");
			String response = restTemplate.getForObject(urlLink, String.class);
			logger.info("KioskSOAServiceImpl.getStorelocation <<RestTemplate>><<Success>> : " + response,"");

			JsonFactory factory = new JsonFactory();
			ObjectMapper mapper = new ObjectMapper(factory);
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			locationResponse = mapper.readValue(response, StoreLocationResponse.class);
			logger.error("getStorelocation Exiting KioskSOAServiceImpl.getStorelocation Successfully","");

		} catch (Exception e) {
			//	logger.info("getStorelocation","Exiting KioskSOAServiceImpl.getStorelocation with failure for url :" + buffer.toString());
			logger.error("getStorelocation","Exception in getting Product Information from getCheckoutInfo service. " + e.getMessage());
			//throw new DJException("");
		}
		return locationResponse;
	}

	/**The method is used to get the marginalPrice and cost of the
	 * recommended accessory from the PLU service.
	 * @param SOAItemDTO
	 * @return void
	 */
	public void getPriceLookUpForAccessory(SOAItemDTO accessoriesItemDescription)  throws DJException{

		logger.info("SOAThirdPartyAPIHelperImpl.getPriceLookUpForAccessory","Entering SOAThirdPartyAPIHelperImpl.getPriceLookUpForAccessory");

		try{
			String automationFlag = MPUWebServiceUtil.getdnsForSOA("automation.flag");
			/*Start : This code is added for Automation testing */	
			/*if(null != accessoriesItemDescription.getStoreNumber() && accessoriesItemDescription.getStoreNumber().contains("1333")
					&& null != automationFlag &&  automationFlag.equalsIgnoreCase("Y")){
				accessoriesItemDescription.setRecommItemMarginRate("40.00");
				accessoriesItemDescription.setRecommPrice("39.50");
				return;
			}*/
			/*End : This code is added for Automation testing */		

			String cleanXML = null;
			String cleanPLUXML = null;
			Pattern pattern = null;
			Matcher matcher = null;
			//String target = "http://"+detailUtil.getDNSForStore()+":8080/mcp_order_adaptor/pluLookupForItem";
			String target = MPUWebServiceUtil.getDNSForStore(accessoriesItemDescription.getStoreNumber(), accessoriesItemDescription.getStoreFormat());

			target=target+"/pluLookupForItem";
			//		String target = "http://"+detailUtil.getdnsForSOA("PriceLookUp")+"/mcp_order_adaptor/pluLookupForItem";
			URI urlLink=new URI(target);
			Order order=new Order();
			List<OrderItem> items = new ArrayList<OrderItem>();
			OrderItem orderItem = new OrderItem();
			/*As data is not set for recommended items in QA so hard coding division,item no ,sku , store number and store format
			 *Store number and store format are mandatory fields as prices will be different for different stores */
			orderItem.setItemDivision(accessoriesItemDescription.getRecommItemDiv());//9
			orderItem.setItemNo(accessoriesItemDescription.getRecommItemNum());//0100,6500,65020
			orderItem.setItemSku(accessoriesItemDescription.getRecommItemSku());
			items.add(orderItem);
			order.setItems(items);
			order.setStoreNo(accessoriesItemDescription.getStoreNumber());
			order.setStoreFormat(accessoriesItemDescription.getStoreFormat());
			/* Need to actually get store format in below mentioned format
		String storeFormat=getStoreFormat(storeNo);
		storeDetailUtil.setStoreFormat(storeFormat);
		storeDetailUtil.setStoreNumber(storeNo); */

			OrderAdaptorRequest aRequest = new OrderAdaptorRequest();
			aRequest.setRequestOrder(order);
			aRequest.setRequestType(OrderAdaptorRequest.RETRIEVE_ORDER);
			HttpEntity<OrderAdaptorRequest> request=new HttpEntity<OrderAdaptorRequest>(aRequest);

			logger.error("SOAThirdPartyAPIHelperImpl.getPriceLookUpForAccessory <<RestTemplate>><<Begins>>URL POST : " + target,"");
			ResponseEntity<String> response = restTemplate.exchange(urlLink, HttpMethod.POST, request,String.class);


			String pluResponse = response.getBody();
			logger.error("SOAThirdPartyAPIHelperImpl.getPriceLookUpForAccessory <<RestTemplate>><<Success>> : " + pluResponse,"");
			//if(!("null".equalsIgnoreCase(pluResponse) || pluResponse.equalsIgnoreCase(null))){
			if(StringUtils.hasText(pluResponse) ){
				/* Code to remove error "An invalid XML character (Unicode: 0x0) was found in the element content of the document"
		 which was coming due to null value in response which is not permissible in XML. */
				pattern = Pattern.compile("[\\000]*");
				matcher = pattern.matcher(pluResponse);
				if (matcher.find()) {
					cleanXML = matcher.replaceAll("");
				}
				// Nullifying matcher to reuse it in below code
				matcher=null;
				// Code to parse xml file
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				InputSource inputsource = new InputSource(new StringReader(cleanXML));
				Document doc = dBuilder.parse(inputsource);
				doc.getDocumentElement().normalize();

				NodeList propertyValueList = doc.getElementsByTagName("propertyValue");
				String childNodeList = propertyValueList.item(0).getFirstChild().getNodeValue();
				/* Data was coming in text format in form of string so converted it back to xml to get value from PLUItem --> regular price
		 Issue was coming while converting string data into xml to get node value so replaced below mentioned text and replaced
		 "xsi:nil=='true with space'"*/
				childNodeList = childNodeList.replaceAll("xsi:nil='true'","");

				//for cost
				String rtiNode="<"+childNodeList.substring(childNodeList.indexOf("RTIItem"), childNodeList.lastIndexOf("RTIItem")+7)+">";
				//logger.info("KioskSOAServiceImpl.getPriceLookUpForAccessory","rtiNode : "+ rtiNode);

				childNodeList="<"+childNodeList.substring(childNodeList.indexOf("PluItem"), childNodeList.lastIndexOf("PluItem")+7)+">";
				//	logger.info("KioskSOAServiceImpl.getPriceLookUpForAccessory","childNodeList : "+ childNodeList);

				/*Converted PLUItem to XML */
				String pluXML = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>"+childNodeList;
				matcher = pattern.matcher(pluXML);
				if (matcher.find()) {
					cleanPLUXML = matcher.replaceAll("");
				}
				InputSource inputsourcePLU = new InputSource(new StringReader(cleanPLUXML));
				Document docPLU = dBuilder.parse(inputsourcePLU);
				// Regular Price
				NodeList regularPriceNode = docPLU.getElementsByTagName("regPrice");
				String regPrice = regularPriceNode.item(0).getFirstChild().getNodeValue();
				// Current Price
				NodeList currentPriceNode = docPLU.getElementsByTagName("currentPrice");
				String curPrice = currentPriceNode.item(0).getFirstChild().getNodeValue();

				//	logger.info("KioskSOAServiceImpl.getPriceLookUpForAccessory","Regular Price"+ regPrice);
				//	logger.info("KioskSOAServiceImpl.getPriceLookUpForAccessory","Current Price"+ curPrice);

				/*
		if actual price is $159.99 then from xml will get 0015999 so below code is for giving it form 00159.99 */

				int length_reg = regPrice.length()-2;
				int length_cur = curPrice.length()-2;
				String regularPrice_final = regPrice.substring(0, length_reg).concat(".").concat(regPrice.substring(length_reg, regPrice.length()));
				String currentPrice_final = curPrice.substring(0, length_cur).concat(".").concat(curPrice.substring(length_cur, curPrice.length()));

				/*Converting price 00159.99 which is in string format to double to apply margin calculation formula which will be like 159.99*/
				double regP = Double.parseDouble(regularPrice_final);
				double curP = Double.parseDouble(currentPrice_final);


				/*Deriving cost from RTI  Section*/

				String rtiXML = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>"+rtiNode;
				matcher = pattern.matcher(rtiXML);
				String cleanRTIXML=null;
				if (matcher.find()) {
					cleanRTIXML = matcher.replaceAll("");
				}
				InputSource inputsourceRTI = new InputSource(new StringReader(cleanRTIXML));
				Document docRTI = dBuilder.parse(inputsourceRTI);
				NodeList costPriceNode = docRTI.getElementsByTagName("cost");
				String costPrice = costPriceNode.item(0).getFirstChild().getNodeValue();

				double cost = new Double(costPrice);
				/*If customer is non SYWR Member then price will regular price else if he is SYWR member then price will be member price.
				 * e.g if customer is SYWR member then price=curP  */

				/*
				 * to check whether the customer is SYWR or not
				 */
				double price=0.00;
				boolean flag=getStatusForSYWRCustomer(accessoriesItemDescription,accessoriesItemDescription.getItemId(),"SYW_NUMBER");
				if(flag){
					price=curP;
					accessoriesItemDescription.setRecommPrice(String.format("%.2f", curP));
				}else{
					price=regP;
					accessoriesItemDescription.setRecommPrice(String.format("%.2f", regP));
				}
				//logger.info("KioskSOAServiceImpl.getPriceLookUpForAccessory","Recommended Price of SOA Item is : "+ accessoriesItemDescription.getRecommPrice());
				double marginRate = (price - cost)*100/price;
				accessoriesItemDescription.setRecommItemMarginRate(marginRate+"");
				//logger.info("KioskSOAServiceImpl.getPriceLookUpForAccessory","Recommended Margin Rate of SOA Item is : "+ accessoriesItemDescription.getRecommItemMarginRate());
			}
		}
		catch (Throwable e)
		{
			logger.error("SOAThirdPartyAPIHelperImpl.getPriceLookUpForAccessory","Exception while fetching Price. Hence aborting SOA.");
			throw new DJException("");
		}
		logger.info("SOAThirdPartyAPIHelperImpl.getPriceLookUpForAccessory","Exiting KioskSOAServiceImpl.getAccessoriesInfo");
	}
	
	/***************************************************CHECKOUT API*****************************************************/
	public CheckoutResponse thirdPartyCheckOutCall( String storeNumber, String storeFormat) throws DJException{
		
		//calling the npos simualator
		storeNumber = storeNumber.replaceFirst("^0+(?!$)", "");
		String urlLink1 = MPUWebServiceUtil.getDNSForStore(storeNumber,storeFormat);
		String urlLink2 = urlLink1 + "/thirdPartyCheckOutCall";
		//return restTemplate.getForObject(urlLink2, CheckoutResponse.class);
		
		/*ResponseEntity<ResponseDTO> responseEntity=(ResponseEntity<ResponseDTO>)restTemplate.exchange(urlLink2, 
				HttpMethod.GET,null, ResponseDTO.class);*/
		
		ResponseEntity<ResponseDTO> responseEntity=(ResponseEntity<ResponseDTO>)restTemplate.exchange(urlLink2, 
				HttpMethod.GET,null, ResponseDTO.class);
		
		ObjectMapper theObjectMapper = new ObjectMapper();
		ResponseDTO responseDTO = (ResponseDTO)responseEntity.getBody();
		CheckoutResponse result = null;
		Object responseBody = responseDTO.getResponseBody();
		if (HttpStatus.OK != responseEntity.getStatusCode()) {
			DJException dJException = theObjectMapper.convertValue(responseBody, new TypeReference<DJException>(){});
			throw dJException;
		} else {
			result = theObjectMapper.convertValue(responseBody, new TypeReference<CheckoutResponse>(){});
			
		}
		return result;
	}
	
	/***************************************************BILLPAY API*****************************************************/
	public CheckoutResponse thirdPartyBillPayCall(String storeNumber, String storeFormat) throws DJException{
		
		storeNumber = storeNumber.replaceFirst("^0+(?!$)", "");
		String urlLink1 = MPUWebServiceUtil.getDNSForStore(storeNumber,storeFormat);
		String urlLink2 = urlLink1 + "/thirdPartyBillPayCall";
		
		ResponseEntity<ResponseDTO> responseEntity=(ResponseEntity<ResponseDTO>)restTemplate.exchange(urlLink2, 
				HttpMethod.GET,null, ResponseDTO.class);
		
		ObjectMapper theObjectMapper = new ObjectMapper();
		ResponseDTO responseDTO = (ResponseDTO)responseEntity.getBody();
		CheckoutResponse result = null;
		Object responseBody = responseDTO.getResponseBody();
		if (HttpStatus.OK != responseEntity.getStatusCode()) {
			DJException dJException = theObjectMapper.convertValue(responseBody, new TypeReference<DJException>(){});
			throw dJException;
		} else {
			result = theObjectMapper.convertValue(responseBody, new TypeReference<CheckoutResponse>(){});
			
		}
		return result;
	}
	
	/**
	 * get property from mpu_order_adaptor using rest template.
	 * @param storenumber
	 * @param propertyName
	 * @return String
	 */
	public String getPropertyFromAdaptor(String storeNumber, String propertyName)  throws DJException{

		logger.info("getPropertyFromAdaptor","Entering KioskSOAServiceImpl.getPropertyFromAdaptor");

		ResponseEntity<OrderAdaptorResponse> response = null;
		OrderAdaptorRequest request=new OrderAdaptorRequest();
		String propertyValue = null ;
		request.setPropertyName(propertyName);
		String automationFlag = MPUWebServiceUtil.getdnsForSOA("automation.flag");
		/*
		 * This is added for Automation*/
		if(! storeNumber.equalsIgnoreCase("") && storeNumber.contains("1333") && null != automationFlag && automationFlag.equalsIgnoreCase("Y")){
			return "423423";
		}

		String dnsName =MPUWebServiceUtil.getDNSForStore(storeNumber, "SearsRetail");
		String serverURL =   dnsName + "/getPropertyValue";
		//logger.info("getPropertyFromAdaptor","server URL to get "+propertyName+" value is "+serverURL);

		if(serverURL!=null){
			HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);

			logger.info("getPropertyFromAdaptor","<<RestTemplate>><<Begins>> URL POST : " + serverURL);
			response = restTemplate.postForEntity(serverURL, requestEntity, OrderAdaptorResponse.class);
		//	logger.info("getPropertyFromAdaptor","<<RestTemplate>><<Success>>");
		}
		propertyValue = response.getBody().getPropertyValue();

		logger.info("getPropertyFromAdaptor","Exiting KioskSOAServiceImpl.getPropertyFromAdaptor");

		return propertyValue;
}
	/***************************************************PLACEORDER API*****************************************************/
	public PrintReceiptInfo thirdPartyPlaceOrderCall(String storeNumber, String storeFormat) throws DJException{
		
		storeNumber = storeNumber.replaceFirst("^0+(?!$)", "");
		String urlLink1 = MPUWebServiceUtil.getDNSForStore(storeNumber,storeFormat);
		String urlLink2 = urlLink1 + "/thirdPartyPlaceOrderCall";
		
		ResponseEntity<ResponseDTO> responseEntity=(ResponseEntity<ResponseDTO>)restTemplate.exchange(urlLink2, 
				HttpMethod.GET,null, ResponseDTO.class);
		
		ObjectMapper theObjectMapper = new ObjectMapper();
		ResponseDTO responseDTO = (ResponseDTO)responseEntity.getBody();
		PrintReceiptInfo result = null;
		Object responseBody = responseDTO.getResponseBody();
		if (HttpStatus.OK != responseEntity.getStatusCode()) {
			DJException dJException = theObjectMapper.convertValue(responseBody, new TypeReference<DJException>(){});
			throw dJException;
		} else {
			result = theObjectMapper.convertValue(responseBody, new TypeReference<PrintReceiptInfo>(){});
			
		}
		return result;
	}
	
	/*
	 * This method fetches the order information from the ShopInSears
	 * @param String
	 * @return TransactionDetailsResponse
	 */
	public SOAItemDTO getOrderInfo(SOAItemDTO soaItem,String orderId, String storeNumber, String storeFormat) throws DJException{

		logger.info("getOrderInfo","Entering KioskSOAServiceImpl.getOrderInfo");

		TransactionDetailsResponse detailsResponse=null;
		String response=  "";
		try{
			StringBuffer buffer=new StringBuffer(MPUWebServiceUtil.getdnsForSOA("ShopsearsAPIURL")+"/mpuservice/OrderInfo?");
			buffer.append("orderId="+orderId);
			URI uri=new URI(buffer.toString());
			logger.error("the url for getOrderInfo==="+buffer.toString(), "");
		//	String automationFlag = MPUWebServiceUtil.getdnsFor==SOA("automation.flag");
			
			logger.info("getOrderInfo","<<RestTemplate>><<Begin>>URL GET : "+ buffer.toString());
			/*if(null != storeNumber && !"".equalsIgnoreCase(storeNumber) && storeNumber.contains("1333")
					&& null != automationFlag && automationFlag.equalsIgnoreCase ("Y")){
				
				//This code is written for Automation testing 
				storeNumber = storeNumber.replaceFirst("^0+(?!$)", "");
				String urlLink1 = MPUWebServiceUtil.getDNSForStore(storeNumber,storeFormat);
				String urlLink2 = urlLink1 + "/thirdPartyGetOrderCall";
				
				ResponseEntity<ResponseDTO> responseEntity=(ResponseEntity<ResponseDTO>)restTemplate.exchange(urlLink2, 
						HttpMethod.GET,null, ResponseDTO.class);
				
				ObjectMapper theObjectMapper = new ObjectMapper();
				ResponseDTO responseDTO = (ResponseDTO)responseEntity.getBody();
				TransactionDetailsResponse result = null;
				Object responseBody = responseDTO.getResponseBody();
				if (HttpStatus.OK != responseEntity.getStatusCode()) {
					DJException dJException = theObjectMapper.convertValue(responseBody, new TypeReference<DJException>(){});
					throw dJException;
				} else {
					result = theObjectMapper.convertValue(responseBody, new TypeReference<TransactionDetailsResponse>(){});
					
				}
				
				return result;
			}else{*/
				response = restTemplate.getForObject(uri,String.class);
		//	}
			logger.error("getOrderInfo <<RestTemplate>><<Success>>"+response,"");

			if(StringUtils.hasText(response)){
				JAXBContext jaxbContext = JAXBContext.newInstance(TransactionDetailsResponse.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(response);
				detailsResponse = (TransactionDetailsResponse) unmarshaller.unmarshal(reader);
				
				if(detailsResponse!=null && !StringUtils.isEmpty(detailsResponse.getTransactionDetails().getTransactionDetail().get(0).getMerchandiseSalesCheckNumber())){
					soaItem.setSalescheckNumber(detailsResponse.getTransactionDetails().getTransactionDetail().get(0).getMerchandiseSalesCheckNumber());
				}else{
					throw new Exception();
				}
			}
			logger.info("getOrderInfo","Response of OrderInfo API is "+detailsResponse);
		}catch(Exception  exception){
			logger.error("in the catch block of getOrderInfo", "");
		/*	logger.info("getOrderInfo","The exception in getOredrInfo is"+exception.getMessage());
			detailsResponse=new TransactionDetailsResponse();
			com.searshc.mpuwebservice.domain.TransactionDetailsResponse.StatusData statusData=
					new com.searshc.mpuwebservice.domain.TransactionDetailsResponse.StatusData();
			statusData.setResponseCode("ERROR");*/
			soaItem.setMessage("Accessory purchase system is currently not available. Your credit card has been charged, but the full amount will be credited back to your account shortly.");
			soaItem.setValid(false);
		}
		logger.info("getOrderInfo","Exiting KioskSOAServiceImpl.getOrderInfo");
		return soaItem;
}
	/****************************************************UTILITY METHODS*****************************************************/
	
	/*
	 * This method determines on the basis of rqd_id whether the customer is a SYWR member.
	 * @param SOAItemDTO, String , String
	 * @return boolean
	 */
	public boolean getStatusForSYWRCustomer(SOAItemDTO soaItemDTO , String rqdId,String description) {
		try{
			ItemDTO itemEntity= kioskSOADaoImpl.getItem(soaItemDTO.getStoreNumber(),rqdId).get(0);
			logger.info("getStatusForSYWRCustomer","Entering SOAThirdPartyAPIHelperImpl.getStatusForSYWRCustomer");
			if(itemEntity!=null){
				IdentifierDTO identifierDTO=kioskSOADaoImpl.getIdentifier(soaItemDTO.getStoreNumber(),itemEntity.getRqtId(), description);
				if(identifierDTO!=null){
					if(StringUtils.hasText(identifierDTO.getValue())){
						soaItemDTO.setSywrMemberId(identifierDTO.getValue());
						logger.info("getStatusForSYWRCustomer","Exiting SOAThirdPartyAPIHelperImpl.getStatusForSYWRCustomer");
						return true;
					}
				}
			}

		}catch(Exception exception){
			return false;
		}
		return false;
	}

	


	/**
	 * This method is used to format Div, Item and Sku
	 */
	private String formatNumber(String value , int expectedlength){

		int lenghtOfValue = value.length();
		if(lenghtOfValue < expectedlength){

			int noOfZerostoAppend = expectedlength - lenghtOfValue;

			for(int i = 0 ; i < noOfZerostoAppend ; i++){
				value = "0" + value;
			}
		}
		return value;

	}
	
	

}
