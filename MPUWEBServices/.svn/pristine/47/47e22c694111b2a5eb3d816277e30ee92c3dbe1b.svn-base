package com.searshc.mpuwebservice.processor.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.mpu.backoffice.domain.IMAProduct;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.ProductInfoRequest;
import com.sears.mpu.backoffice.domain.ProductInfoResponse;
import com.searshc.mpuwebservice.bean.CustomerDTO;
import com.searshc.mpuwebservice.bean.IdentifierDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.PickUpDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.SOAItemDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.KioskSOADao;
import com.searshc.mpuwebservice.dao.PickUpServiceDAO;
import com.searshc.mpuwebservice.domain.PrintReceiptInfo;
import com.searshc.mpuwebservice.domain.TransactionDetailsResponse;
import com.searshc.mpuwebservice.domain.response.CheckoutResponse;
import com.searshc.mpuwebservice.domain.response.CheckoutResponse.StatusData;
import com.searshc.mpuwebservice.domain.response.Location;
import com.searshc.mpuwebservice.domain.response.StoreLocationResponse;
import com.searshc.mpuwebservice.entity.McpOrigSCPurchasedSCMappingEntity;
import com.searshc.mpuwebservice.processor.KioskSOAService;
import com.searshc.mpuwebservice.processor.MPUWebServiceProcessor;
import com.searshc.mpuwebservice.processor.PickUpServiceProcessor;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebutil.util.ConversionUtils;

/**
 * @author pgoyal
 * create date : 17-Oct-2014
 * This class acts as a processor for processing SOA.
 *
 */

@Service("kioskSOAServiceImpl")
public class KioskSOAServiceImpl implements KioskSOAService{

	private static transient DJLogger logger = DJLoggerFactory.getLogger(KioskSOAServiceImpl.class);
	@Autowired
	private PickUpServiceDAO pickUpServiceDAO;

	@Autowired
	private PickUpServiceProcessor pickUpServiceProcessor;

	@Autowired
	private KioskSOADao kioskSOADaoImpl;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private MPUWebServiceProcessor mpuWebServiceProcessor;
	
	@Autowired
	private SOAThirdPartyAPIHelper soaThirdPartyAPIHelper;
	
	/****************************************************DISPLAY ACCESSORY API *****************************************************/

	/*
		 * This method gives the single SOA item based on the selected items by the customer.
		 * @param SOAItemDTO
		 * @return SOAItemDTO
		 */
		public SOAItemDTO displayThankYouWithAccessories(SOAItemDTO soaItem)  throws DJException{

			logger.info("displayThankYouWithAccessories","Entering KioskSOAServiceImpl.displayThankYouWithAccessories");

			String selectedItems   = (String)soaItem.getItemsList();
			String storeNumber     = (String)soaItem.getStoreNumber();
			String storeFormat     = (String)soaItem.getStoreFormat();
			String kioskName       = (String)soaItem.getKioskName();
			
			logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","Selected Item List is :" + selectedItems+" "+storeNumber+" "+storeFormat+" "+kioskName);
		//	logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","Store Number is :" + storeNumber);
		//	logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","StoreFormat is : " + storeFormat);
		//	logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","KioskName is :" + kioskName);

			if(isSOAEnabledOnStore(storeNumber, kioskName)){

//				logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","SOA is available for Store Number : " + storeNumber);

				List<String> selectedItemList = new ArrayList<String>(Arrays.asList(selectedItems.split(",")));
				List<SOAItemDTO> selectedItemsList =	new ArrayList<SOAItemDTO>();

				boolean checkResult = false;
				String div_item_sku = null;

				// Step 1: Fetch item details from input and set list of bean
				for (Iterator<String> itemIter = selectedItemList.iterator(); itemIter.hasNext();) {

					String temp = itemIter.next();
					List<String> individualSelectedItem = new ArrayList<String>(Arrays.asList(temp.split(";")));
	//				logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","Checking if the selected order is present in our Database.");
					checkResult = getSalescheckNumber(storeNumber, individualSelectedItem.get(0), individualSelectedItem.get(1));

					logger.info("","");
					if (checkResult) {
		//				logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","order is present for rqt_id : " + individualSelectedItem.get(0));

						SOAItemDTO tempSellingAccessoriesItem = new SOAItemDTO();
						tempSellingAccessoriesItem.setWorkId(individualSelectedItem.get(0));
						tempSellingAccessoriesItem.setItemId(individualSelectedItem.get(1));
						tempSellingAccessoriesItem.setMainOrderSalesCheck(individualSelectedItem.get(2));
						tempSellingAccessoriesItem.setReqQuantity(individualSelectedItem.get(3));
						if(individualSelectedItem.size() == 12){
							//{{item.rqtId}};{{item.rqdId}};{{item.salescheck}};{{item.qty}};{{item.div}};{{item.itemNo}};
							//{{item.sku}};{{item.SequenceNo}};{{item.customerName}};{{item.customerId}};{{item.orderDate}};{{item.itemPrice}}
							tempSellingAccessoriesItem.setSequenceNo(individualSelectedItem.get(7));
							tempSellingAccessoriesItem.setCustomerName(individualSelectedItem.get(8));
							tempSellingAccessoriesItem.setCustomerID(individualSelectedItem.get(9));
							tempSellingAccessoriesItem.setOrderDate(individualSelectedItem.get(10));
							tempSellingAccessoriesItem.setPrice(individualSelectedItem.get(11));
						}


						selectedItemsList.add(tempSellingAccessoriesItem);
					}else{
						logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","order is not present for rqt_id : " + individualSelectedItem.get(0));
					}

				}
			//	logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","Selected Main Item List : " + selectedItemsList);
				if (null != selectedItemsList && selectedItemsList.size() > 0) {

					//fetching single accessory item with the maximum marginal rate and less than 50 $
					soaItem = getAccessoriesInfo(selectedItemsList, storeNumber, storeFormat);
					
					//whether SOA item is available
					if(null != soaItem ){

				//		logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","Selected SOA Item : " + soaItem);

						soaItem.setItemsList(selectedItems);
						soaItem.setStoreNumber(storeNumber);
						soaItem.setStoreFormat(storeFormat);
						soaItem.setKioskName(kioskName);

						//Fetch Accessory information
						String accessoryDiv        = soaItem.getRecommItemDiv();
						String accessoryItemNumber = soaItem.getRecommItemNum();
						String accessorySku        = soaItem.getRecommItemSku();

						// For Formatting for Div
						if (null != accessoryDiv) {
							switch (accessoryDiv.length()) {
							case 1:	accessoryDiv = "00" + accessoryDiv;	break;
							case 2:	accessoryDiv = "0" + accessoryDiv;	break;
							case 3:	break;
							}
						}

						// For Formatting for sku
						if (null != accessorySku ) {
							switch (accessorySku.length()) {
							case 1:	accessorySku = "00" + accessorySku;	break;
							case 2:	accessorySku = "0" + accessorySku;	break;
							case 3:	break;
							}
						}

						if (null != accessoryDiv && null != accessorySku
								&& null != accessoryItemNumber ) {
							div_item_sku = accessoryDiv + "/" + accessoryItemNumber	+ "/" + accessorySku;

					//		logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","Fetching Accessory Information");
							IMAProduct accessoryItem = getAccessoryInfo(div_item_sku, "divitemsku/");
				//			logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","IMAProduct Information : " + accessoryItem);
							if(null != accessoryItem){

								String imageURL = accessoryItem.getImageUrl();
								String rating = accessoryItem .getRating();
								String recommPartNumber = accessoryDiv	+ accessoryItemNumber + accessorySku;

								logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","ImageURL : "+imageURL+"rating : "+rating+"recommPartNumber : "+recommPartNumber);
					//			logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","rating : "+rating);
					//			logger.info("KioskSOAServiceImpl.displayThankYouWithAccessories","recommPartNumber : "+recommPartNumber);

								soaItem.setAccessoryImageUrl(imageURL);
								soaItem.setRecommRating(rating);
								soaItem.setRecommPartNumber(recommPartNumber);
							}

						}

						//Fetch Main Item information
						if (null != soaItem.getItemId() && soaItem.getItemId().length() > 0) {

							ItemDTO itemDTO = getItemDetail(storeNumber,Integer.parseInt(soaItem.getItemId()));

							soaItem.setItemImageUrl(itemDTO.getItemImage());

							//This call sets the item description and price
							/*soaItem = getItemInfo(soaItem);*/
							//logger.info("displayThankYouWithAccessories","soaItem after setting price and description of Main item : " + soaItem);

							// set salescheck,workid ,created date store number and zip
							// code
							List<OrderDTO> listOrderDTO = getOrder(itemDTO.getStoreNumber(),itemDTO.getRqtId());
							OrderDTO orderDTO = listOrderDTO.get(0);
							soaItem.setTransactionDate(orderDTO.getCreateTimestamp());
							//Puneet : set the store zip code here
							//logger.info("displayThankYouWithAccessories","Fetching Store details");
							Map<String,Object> storeInfo = mpuWebServiceProcessor.getstoreDetails(orderDTO.getStoreNumber());
							//logger.info("displayThankYouWithAccessories","Fetched store details : " + storeInfo);
							String zip="";
						     if(storeInfo!=null){
						    	 zip=(String) storeInfo.get("zip");
						     }
						     //logger.info("displayThankYouWithAccessories","zip code : "+ zip);
							soaItem.setStoreZipCode(zip);
						}

						soaItem.setValid(Boolean.TRUE);
					}else{
						//logger.info("displayThankYouWithAccessories","There is no proposed SOA item(s).");
						soaItem = new SOAItemDTO();
						//soaItem.setMessage("There is no proposed SOA item(s).");
						soaItem.setValid(Boolean.FALSE);
					}
				}else{
					//logger.info("displayThankYouWithAccessories","Selected Items are not considered for SOA");
					//soaItem.setMessage("Selected Items are not eligible for SOA.");
					soaItem.setValid(Boolean.FALSE);
				}
			}
			else{
				//logger.info("displayThankYouWithAccessories","SOA is not eligible for store with Store Number : " + storeNumber);
				//soaItem.setMessage("SOA is not eligible for store with Store Number : " + storeNumber);
				soaItem.setValid(Boolean.FALSE);
			}

		logger.info("displayThankYouWithAccessories","Exiting KioskSOAServiceImpl.displayThankYouWithAccessories");
		return soaItem;
	}

		/*
		 * This method implements business rules for determining SOA Item.
		 * @param List<SOAItemDTO>
		 * @param String
		 * @param String
		 * @return SOAItemDTO
		 */
		public SOAItemDTO getAccessoriesInfo(List<SOAItemDTO> selectedItemsList,String storeNumber , String storeFormat)  throws DJException{

			logger.info("getAccessoriesInfo","Entering KioskSOAServiceImpl.getAccessoriesInfo");

			List<SOAItemDTO> sellingAccessoriesItemList = null;
			List<SOAItemDTO> sellingAccessoriesWithStoreLocationList = new ArrayList<SOAItemDTO>();
			List<SOAItemDTO> sellingAccessoriesWithPluList= new ArrayList<SOAItemDTO>();
			// Step 1: fetch Accessory List from database
			//logger.info("getAccessoriesInfo","the provided item list is : "+ selectedItemsList.toString());
			//list of recommended items
			sellingAccessoriesItemList = kioskSOADaoImpl.getAccessoriesInfo(storeNumber,selectedItemsList);
			if(!CollectionUtils.isEmpty(sellingAccessoriesItemList)){
				logger.error("the list from DB is not null"+sellingAccessoriesItemList.size(), "");
			}else{
				logger.error("the list from DB is null", "");
			}

			//if there is no proposed SOA
			if(null == sellingAccessoriesItemList || sellingAccessoriesItemList.isEmpty()){
				logger.error("KioskSOAServiceImpl.getAccessoriesInfo","For the base items, There is no proposed SOA item in our database.");
				throw new DJException("");
			}else{
				logger.info("KioskSOAServiceImpl.getAccessoriesInfo","Selected SOA Item List :" + sellingAccessoriesItemList);
			}

			//logger.info("getAccessoriesInfo","<<StoreLocationResponse>><<Rule1>><<Begins>>fetching storelocation for the items");
			//looping through recommended items
			for(SOAItemDTO accessoriesItemDescription: sellingAccessoriesItemList ){

				accessoriesItemDescription.setStoreNumber(storeNumber);
				accessoriesItemDescription.setStoreFormat(storeFormat);
				//Fetching store Location
				logger.error("the parameters are as :-"+ accessoriesItemDescription.getRecommItemDiv()+accessoriesItemDescription.getRecommItemNum()+accessoriesItemDescription.getRecommItemSku()
						+storeNumber+storeFormat,"");

/*				logger.info("getAccessoriesInfo","<<StoreLocationResponse>>Div :"+accessoriesItemDescription.getRecommItemDiv());
				logger.info("getAccessoriesInfo","<<StoreLocationResponse>>Item :"+accessoriesItemDescription.getRecommItemNum());
				logger.info("getAccessoriesInfo","<<StoreLocationResponse>>Sku :"+accessoriesItemDescription.getRecommItemSku());
				logger.info("getAccessoriesInfo","<<StoreLocationResponse>>StoreNumber :" + storeNumber);
				logger.info("getAccessoriesInfo","<<StoreLocationResponse>>StoreFormat :"+storeFormat);*/

				//In case of Exception while getting locationResponse, next accessory is considered
				StoreLocationResponse locationResponse =  soaThirdPartyAPIHelper.getStorelocation(accessoriesItemDescription.getRecommItemDiv(),accessoriesItemDescription.getRecommItemNum(),accessoriesItemDescription.getRecommItemSku()
						, storeNumber, storeFormat );
				logger.error("KioskSOAServiceImpl.getAccessoriesInfo <<StoreLocationResponse>>fetched locationResponse :"+locationResponse,"");
				if(null != locationResponse){
					if(null != locationResponse.getLocationList() && locationResponse.getLocationList().getLocation().size()>0){
						for(Location location : locationResponse.getLocationList().getLocation()){
							//Adding Acc item to list if store location is ACC
							if(location.getAreaCode().equalsIgnoreCase("ACC") && Integer.parseInt(location.getBackroomQty())>0){

								logger.error("getAccessoriesInfo <<StoreLocationResponse>><<Rule1>>Considering this Accessory :"+accessoriesItemDescription,"");
								sellingAccessoriesWithStoreLocationList.add(accessoriesItemDescription);
								//break;
							}
						}
					}
				}
			}

			if(null == sellingAccessoriesWithStoreLocationList || sellingAccessoriesWithStoreLocationList.isEmpty()){
				logger.error("getAccessoriesInfo","<<StoreLocationResponse>><<Rule1>><<Failure>>Accessory List is empty");
				throw new DJException("");
			}

			//logger.info("getAccessoriesInfo","<<StoreLocationResponse>><<>Rule1>><<Success>> : " + sellingAccessoriesWithStoreLocationList);

		//	logger.info("getAccessoriesInfo","<<getPriceLookUpForAccessory>><<Rule2>><<Begins>>");
			//Fetching Price from PLU
			for(SOAItemDTO accessoriesItemDesc: sellingAccessoriesWithStoreLocationList ){
				//getting the recommended price and recommended margin rate
				soaThirdPartyAPIHelper.getPriceLookUpForAccessory(accessoriesItemDesc);
				if(Double.parseDouble(accessoriesItemDesc.getRecommPrice())<=50.0){
					//logger.info("getAccessoriesInfo","the recommended price for the accessories is as : "+accessoriesItemDesc.getRecommPrice());
					sellingAccessoriesWithPluList.add(accessoriesItemDesc);
				}
			}

			if(null == sellingAccessoriesWithPluList || sellingAccessoriesWithPluList.isEmpty()){
				logger.error("getAccessoriesInfo","<<getPriceLookUpForAccessory>><<Rule2>><<Failure>>");
				throw new DJException("");
			}

			//logger.info("getAccessoriesInfo","<<getPriceLookUpForAccessory>><<Rule2>><<Success>> : " + sellingAccessoriesWithPluList);
			if(!(CollectionUtils.isEmpty(sellingAccessoriesWithPluList))){
				Collections.sort(sellingAccessoriesWithPluList);
			}


		//	logger.info("getAccessoriesInfo","<<getPriceLookUpForAccessory>><<Rule1>><<Rule2>><<Success>> : " + sellingAccessoriesWithPluList.get(0));
			logger.info("getAccessoriesInfo","Exiting KioskSOAServiceImpl.getAccessoriesInfo");
			logger.error("getAccessoriesInfo,Exiting KioskSOAServiceImpl.getAccessoriesInfo"+sellingAccessoriesWithPluList.size(),"");
			return sellingAccessoriesWithPluList.get(0);
		}

	

		/*
			 * Retrieves Accessory Information.
			 * @param String , String
			 * @return SOAItemDTO
			 */
			public IMAProduct getAccessoryInfo(String identifier, String identifierType)  throws DJException{

			//	logger.info("getAccessoryInfo","Entering KioskSOAServiceImpl.getAccessoryInfo");

				logger.info("getAccessoryInfo","Start :: getAccessoryInfo :: identifier : " + identifier+" identifierType: "+identifierType);
				String productInfoserviceUrl = "https://web.searshc.com/Lookup/productinfo/";
				String target = productInfoserviceUrl + identifierType + identifier;
				//logger.info("getAccessoryInfo","Calling service and passing the following: " + target);
				// construct Request
				ProductInfoRequest aRequest = new ProductInfoRequest();
				// We use this to validate data against call
				aRequest.setIdentifier(identifier);
				aRequest.setIdentifierType(identifierType);
				// declare a response entity wrapping the type you want back from the rest call
				logger.info("KioskSOAServiceImpl.getAccessoryInfo","<<RestTemplate>><<Begins>>URL GET : " + target);
				ProductInfoResponse productInfoResponse = restTemplate.getForObject(target, ProductInfoResponse.class);
				logger.info("KioskSOAServiceImpl.getAccessoryInfo","<<RestTemplate>><<Success>> : " + productInfoResponse);

			//	logger.info("getAccessoryInfo","Exiting KioskSOAServiceImpl.getAccessoryInfo");

				return productInfoResponse.getProductInfo();
	}


		


	/****************************************************CHECKOUT API **************************************************************/


	/*
		 * This method gets the amount information of the selected SOA item like total Amount,
		 * sales tax total, pre tax total and subtotal to be displayed to the customer.
		 * @param SOAItemDTO
		 * @return SOAItemDTO
		 */
		public SOAItemDTO expressCheckoutAccessory(SOAItemDTO soaItem)  throws DJException{

			logger.info("expressCheckoutAccessory","Entering into KioskSOAServiceImpl.expressCheckoutAccessory");
		//	logger.info("KioskSOAServiceImpl.expressCheckoutAccessory","soaItem : "+soaItem);
			CheckoutResponse response = null;

			String transactiondate = soaItem.getTransactionDate();
			String partnum         = soaItem.getRecommPartNumber();
			String storenumber     = soaItem.getStoreNumber();
			String mainOrdersaleschecknum   = soaItem.getMainOrderSalesCheck();
			String zipcode         = soaItem.getStoreZipCode();
			String price           = soaItem.getRecommPrice();
			String invokeSecured   = soaItem.getInvokeSecured();
			String itemId          = soaItem.getItemId();

			if( org.apache.commons.lang.StringUtils.isNotBlank(transactiondate)){
				transactiondate = ((transactiondate.substring(0, transactiondate.lastIndexOf(".")).replace(" ", "%20")).replace(":", "%3a"));
			}
			partnum = partnum+"P";
			storenumber= org.apache.commons.lang.StringUtils.leftPad(storenumber, 7, '0');
			// Step 1: Invoke ExpressCheckoutAPI

			//logger.info("expressCheckoutAccessory","<<getExpressCheckOutResponse>>Begins");
			response = getExpressCheckOutResponse(soaItem,
					mainOrdersaleschecknum, transactiondate, storenumber, zipcode,
					partnum, "1", price);
			//logger.info("expressCheckoutAccessory","<<getExpressCheckOutResponse>>Completes");

			if (response != null && response.getStatusData()!=null && response.getStatusData().getResponseCode().equalsIgnoreCase("0")) {
				logger.info("expressCheckoutAccessory","The API provides valid response");
				if((getStatusForSYWRCustomer(soaItem,itemId,"SYW_NUMBER"))==true){
					if(response.getOrderSummary()!=null && response.getOrderSummary().getOrderSummaryTotals()!=null &&
							 org.apache.commons.lang.StringUtils.isNotEmpty(response.getOrderSummary().getOrderSummaryTotals().getSywrAmount())){
						soaItem.setSywrPoints(response.getOrderSummary().getOrderSummaryTotals().getSywrAmount());
					}
				}

				soaItem.setValid(Boolean.TRUE);

				// OrderId
				if (response.getOrderId()!=null && !response.getOrderId().trim().equals(""))
					soaItem.setOrderId("" + response.getOrderId());
				else {
					soaItem.setOrderId("");
				}

				// Total
				if (response.getOrderSummary() != null
						&& response.getOrderSummary().getOrderSummaryTotals() != null && response.getOrderSummary()
						.getOrderSummaryTotals().getTotal()!=null && !response.getOrderSummary()
						.getOrderSummaryTotals().getTotal().trim().equals("")) {

					soaItem.setRecommTotalAmount(""
							+ response.getOrderSummary()
							.getOrderSummaryTotals().getTotal());

				} else {
					soaItem.setRecommTotalAmount("");
				}

				// AddressId
				if (response.getAddresses() != null
						&& response.getAddresses().getAddress() != null
						&& response.getAddresses().getAddress().size() > 0 && response.getAddresses().getAddress().get(0)
						.getAddressId()!=null && !response.getAddresses().getAddress().get(0)
						.getAddressId().trim().equals("")) {

					soaItem.setBillingAddressId(""
							+ response.getAddresses().getAddress().get(0)
							.getAddressId());
				} else {
					soaItem.setBillingAddressId("");
				}

				// SessionKey
				if (response.getServiceHeaders() != null && response.getServiceHeaders()
						.getClientSessionKey()!=null && !response.getServiceHeaders()
						.getClientSessionKey().trim().equals("")) {
					soaItem.setSessionKey(response.getServiceHeaders()
							.getClientSessionKey());
				} else {
					soaItem.setSessionKey("");
				}

				//setting price of recommended accessory
				/*Double double1=new Double(price);
				soaItem.setRecommPrice(String.format("%.2f", double1));
				*/

				if (response.getOrderSummary() != null) {

					if (response.getOrderSummary().getOrderSummaryTotals() != null) {

						// Sub Total
						if(response.getOrderSummary()
								.getOrderSummaryTotals()
								.getMerchandiseSubtotal()!=null && !response.getOrderSummary()
								.getOrderSummaryTotals()
								.getMerchandiseSubtotal().trim().equals(""))
						{
							soaItem.setRecommSubTotal(""
									+ response.getOrderSummary()
									.getOrderSummaryTotals()
									.getMerchandiseSubtotal());
						}else {
							soaItem.setRecommSubTotal("");
						}

						// Pre-Tax Total
						if(response.getOrderSummary()
								.getOrderSummaryTotals()
								.getPreTaxTotal()!=null && !response.getOrderSummary()
								.getOrderSummaryTotals()
								.getPreTaxTotal().trim().equals("")){
							soaItem.setRecommPreTaxTotal(""
									+ response.getOrderSummary()
									.getOrderSummaryTotals()
									.getPreTaxTotal());
						}else {
							soaItem.setRecommPreTaxTotal("");
						}

						// Sales Tax
						if(response.getOrderSummary()
								.getOrderSummaryTotals()
								.getEstimatedSalesTax()!=null && !response.getOrderSummary()
								.getOrderSummaryTotals()
								.getEstimatedSalesTax().trim().equals("")){
							soaItem.setRecommSalesTaxTotal(""
									+ response.getOrderSummary()
									.getOrderSummaryTotals()
									.getEstimatedSalesTax());
						}else {
							soaItem.setRecommSalesTaxTotal("");
						}

						// Total
						if(response.getOrderSummary()
								.getOrderSummaryTotals()
								.getTotal()!=null && !response.getOrderSummary()
								.getOrderSummaryTotals()
								.getTotal().trim().equals("")){


							soaItem.setRecommTotalAmount(""
									+ response.getOrderSummary()
									.getOrderSummaryTotals().getTotal());
						}else {
							soaItem.setRecommTotalAmount("");
						}

					}// end of OrderSummaryTotals
					else {
						soaItem.setRecommSubTotal("");
						soaItem.setRecommPreTaxTotal("");
						soaItem.setRecommSalesTaxTotal("");
						soaItem.setRecommTotalAmount("");
					}

				}// end of OrderSummary
				else {
					//soaItem.setRecommPrice("");
					soaItem.setRecommSubTotal("");
					soaItem.setRecommPreTaxTotal("");
					soaItem.setRecommSalesTaxTotal("");
					soaItem.setRecommTotalAmount("");
				}

				logger.info("expressCheckoutAccessory","Recommended Sub Total : " + soaItem.getRecommSubTotal()+"Recommended Pre Tax Total : " + soaItem.getRecommPreTaxTotal()
						+"Recommended Sales Tax Total : " + soaItem.getRecommSalesTaxTotal()+"Recommended Total Amount : " + soaItem.getRecommTotalAmount());
/*				logger.info("expressCheckoutAccessory","Recommended Pre Tax Total : " + soaItem.getRecommPreTaxTotal());
				logger.info("expressCheckoutAccessory","Recommended Sales Tax Total : " + soaItem.getRecommSalesTaxTotal());
				logger.info("expressCheckoutAccessory","Recommended Total Amount : " + soaItem.getRecommTotalAmount());
*/
				//soaItem.setItemId(itemId);

			} // end of response
			else{
				//logger.info("expressCheckoutAccessory","The response of checkout API status is  ERROR");
				logger.error("expressCheckoutAccessory","Accessory purchase system is currently not available.");
				soaItem.setMessage("Accessory purchase system is currently not available.You can purchase the accessory in the store at a register.");
				soaItem.setInvokeSecured(invokeSecured);
				soaItem.setValid(Boolean.FALSE);
				//throw new DJException("Accessory purchase system is currently not available.You can purchase the accessory in the store at a register. ");
			}

	//	logger.info("expressCheckoutAccessory","Exiting KioskSOAServiceImpl.expressCheckoutAccessory");
		logger.info("KioskSOAServiceImpl.expressCheckoutAccessory",soaItem);
		return soaItem;
		}



	/*
	 * This method fetches details for amount of SOA Item.
	 * @param String,String,String,String,String,String,String
	 * @return CheckoutResponse
	 */
	public CheckoutResponse getExpressCheckOutResponse(SOAItemDTO soaItem, String salesCheckNum,String transactionDate,String storeNum,
			String storeZipcode,String partNumbers,String quantities,String prices) {

		CheckoutResponse checkoutResponse = null;
		logger.info("getExpressCheckOutResponse","Base Order SalesCheckNumber : " + salesCheckNum+"TransactionDate : " + transactionDate+"StoreNumber : " + storeNum
				+"StoreZipCode : " + storeZipcode+"PartNumbers : " + partNumbers+"Quantities<<It is set as 1>> : " + quantities+"Price : " + prices);

		String storeFormat = (String )soaItem.getStoreFormat();

		StringBuffer url = null;
		try{
			 checkoutResponse = new CheckoutResponse();

			String shopsearsAPIURL = MPUWebServiceUtil.getdnsForSOA("ShopsearsAPIURL");

			url=new  StringBuffer(shopsearsAPIURL + "/mpuservice/ExpressCheckout?");
			url.append("salesCheckNum="+salesCheckNum+"&transactionDate="+transactionDate+"&partNumbers="+partNumbers+"&quantities="+quantities+
					"&prices="+prices+"&storeNum="+	storeNum+"&storeZipcode="+storeZipcode);
			logger.error("the url for 1st API is ===="+url.toString(),"");
			URI urlLink=new URI(url.toString());

		//	String automationFlag = MPUWebServiceUtil.getdnsForSOA("automation.flag");
			
			/*if(null != storeNum && !"".equalsIgnoreCase(storeNum) && storeNum.contains("1333") && 
					null != automationFlag && automationFlag.equalsIgnoreCase("Y")){
				//This redirection to npos simulator is for Automation
				checkoutResponse = soaThirdPartyAPIHelper.thirdPartyCheckOutCall(storeNum,storeFormat);
			}else{
				logger.info("getExpressCheckOutResponse","<<RestTemplate>><<Begins>> URL GET: " + url.toString());
				
			}*/
			
			//logger.info("getExpressCheckOutResponse","<<RestTemplate>><<Success>>");
			
			checkoutResponse=restTemplate.getForObject(urlLink,CheckoutResponse.class);
			
			/*String response = null;

			if(StringUtils.hasText(response)){
				JAXBContext jaxbContext = JAXBContext.newInstance(CheckoutResponse.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(response);
				checkoutResponse = (CheckoutResponse) unmarshaller.unmarshal(reader);
			}else{
				checkoutResponse=new CheckoutResponse();
				StatusData data=new StatusData();
				data.setResponseCode("ERROR");
				checkoutResponse.setStatusData(data);
			}*/
			logger.info("getExpressCheckOutResponse","Exiting ");
			return checkoutResponse;
		}catch(Exception ex){
			logger.error("getExpressCheckOutResponse","Exception Block : "+ex.getMessage());
			soaItem.setMessage("Accessory purchase system is currently not available. You can purchase the accessory in the store at a register.");
			//throw new DJException("Accessory purchase system is currently not available.");
		}
		return checkoutResponse;
	}




	/****************************************************BILL PAY API **************************************************************/

	/*
		 * This method creates the session for transaction in the NPOS.
		 * @param SOAItemDTO
		 * @param String
		 * @param String
		 * @return SOAItemDTO
		 */
		public SOAItemDTO expressPayBillAccessory(SOAItemDTO soaItem,String searchMethod,String searchValue)  throws DJException{

			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","Entering KioskSOAServiceImpl.expressPayBillAccessory");
	//		logger.info("KioskSOAServiceImpl.expressPayBillAccessory",soaItem);
			CheckoutResponse response = null;
			//SOAItemDTO checkoutOutputresponse = soaItem;

			String orderId			= (String)soaItem.getOrderId();
			String totalAmount 		= (String)soaItem.getRecommTotalAmount();
			String billingAddressId = (String)soaItem.getBillingAddressId();
			String storeNum			= (String)soaItem.getStoreNumber();
			String sessionKey		= (String)soaItem.getSessionKey();
			String cardData			= (String)soaItem.getCardData();
			String invokeSecured	= (String)soaItem.getInvokeSecured();
			String itemId			= (String)soaItem.getItemId();
			String kioskName		= (String)soaItem.getKioskName();
			String storeFormat 		= (String)soaItem.getStoreFormat();

			
			cardData=";"+cardData+"?";
			soaItem.setInvokeSecured(invokeSecured);
			
			logger.error("KioskSOAServiceImpl.expressPayBillAccessory OrderId : " + orderId+"TotalAmount : " + totalAmount+"BillingAddressId : " + billingAddressId
					+"StoreNumber : " + storeNum+"SessionKey : " + sessionKey+"CardData : " + cardData+"InvokeSecured : " + invokeSecured+"ItemId : " + itemId+"KioskName : " + kioskName,"");

			//logger.info("KioskSOAServiceImpl.expressPayBillAccessory","Calling expressPayBill");
			response = getexpressPayBill(orderId,
					totalAmount, billingAddressId, storeNum, sessionKey,
					cardData,itemId,storeFormat);
			//logger.info("KioskSOAServiceImpl.expressPayBillAccessory","ExpressPayBill calling complete.");



			if(null != response && response.getStatusData().getResponseCode().equalsIgnoreCase("ERROR")){
				logger.error("expressPayBillAccessory The response status of BillPay API throws error","");
				soaItem.setValid(Boolean.FALSE);
				soaItem.setMessage("Accessory purchase system is currently not available. Your credit card will not be charged.");

			}else{
				logger.info("expressPayBillAccessory The billpay API has valid response","");
				soaItem.setMessage("Accessory purchase system is currently not available. Your credit card will not be charged.");

				//setting order Id
				soaItem.setOrderId(org.apache.commons.lang.StringUtils.isNotBlank(response.getOrderId()) ? response.getOrderId() : ""  );
				if(response.getOrderSummary()!=null ){
					soaItem.setOrderItemId(org.apache.commons.lang.StringUtils.isNotBlank(response.getOrderSummary().getOrderSummaryItem().getOrderItemId()) ?
							response.getOrderSummary().getOrderSummaryItem().getOrderItemId() : ""  );
					soaItem.setRecommTotalAmount(org.apache.commons.lang.StringUtils.isNotBlank(response.getOrderSummary().getOrderSummaryTotals().getTotal()) ?
							response.getOrderSummary().getOrderSummaryTotals().getTotal() : "");
				}else{
					soaItem.setOrderItemId("0");
					soaItem.setRecommTotalAmount("0");
				}



				if(response.getPickUpSection()!=null){
					soaItem.setRecommPartNumber(org.apache.commons.lang.StringUtils.isNotBlank(response.getPickUpSection().getPickUpItems().getPickUpItem().getPickUpItemDetail().getPartNumber()) ?
							response.getPickUpSection().getPickUpItems().getPickUpItem().getPickUpItemDetail().getPartNumber() : "");
				}else{
					soaItem.setRecommPartNumber("");
				}
				soaItem.setSessionKey(org.apache.commons.lang.StringUtils.isNotBlank(response.getServiceHeaders().getClientSessionKey()) ? response.getServiceHeaders().getClientSessionKey() : "");
				soaItem.setBillingAddressId(billingAddressId);
				if(null != response.getPaymentDetails())
					soaItem.setCc_brand(response.getPaymentDetails().getCardType());
				/*if(storeNum!=null && !storeNum.equals("")){
					storeNum = storeNum.substring(2, storeNum.length());
				}*/
				String deviceId = storeNum + kioskName;
				soaItem.setPrinterId(soaThirdPartyAPIHelper.getPropertyFromAdaptor(storeNum, "kioskPrinter"));
		//		checkoutOutputresponse.setDeviceid(updateMCPWorkService.getPropertyFromAdaptor(storeNum, "mpu.paging.registerNumber"));
				soaItem.setDeviceid(deviceId);
				if(response.getPaymentDetails()!=null){
					soaItem.setEmailAddress(org.apache.commons.lang.StringUtils.isNotBlank(response.getPaymentDetails().getAddress().getEmail()) ? response.getPaymentDetails().getAddress().getEmail() : "");
					soaItem.setState(org.apache.commons.lang.StringUtils.isNotBlank(response.getPaymentDetails().getAddress().getState()) ? response.getPaymentDetails().getAddress().getState() :"");
				}else{
					soaItem.setEmailAddress("");
					soaItem.setState("");
				}
				if(response.getSYWRDetails()!=null){
					soaItem.setSywrMemberId(response.getSYWRDetails().getSywrMemberId());
				}else{
					soaItem.setSywrMemberId("");
				}

				soaItem.setInvokeSecured(invokeSecured);
				soaItem.setValid(Boolean.TRUE);
				// Step 2 : Set and return output
				//need to change to false
				//This itemId is of the main item for which soa is proposed
				if( ! (getStatusForSYWRCustomer(soaItem,itemId,"SYW_NUMBER"))){
					/*If the customer is a non-SYWR member then
					  show third screen */
					soaItem.setSywrMemberId("");
					//logger.info("expressPayBillAccessory","going for expressPlaceOrderAccessory API directly ");
				//logger.info("KioskSOAServiceImpl.expressPayBillAccessory","Customer is not SYWR Customer");
					soaItem.setEmailReceiptFlag("false");
					soaItem = expressPlaceOrderAccessory( soaItem,searchMethod,searchValue);
				}
			}
			//logger.info("KioskSOAServiceImpl.expressPayBillAccessory",soaItem);

			//set from billpay API

			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","OrderId : " + soaItem.getOrderId()+"OrderItemId : " + soaItem.getOrderItemId()+
					"RecommTotalAmount : " + soaItem.getRecommTotalAmount()+"RecommPartNumber : " + soaItem.getRecommPartNumber()+"SessionKey : " + soaItem.getSessionKey()
					+"BillingAddressId : " + soaItem.getBillingAddressId()+"CC_brand : " + soaItem.getCc_brand()+"PrinterId : " + soaItem.getPrinterId()+
					"DeviceId : " + soaItem.getDeviceid()+"EmailAddress : " + soaItem.getEmailAddress()+"State : " + soaItem.getState()+"SywrMemberId : " + soaItem.getSywrMemberId());
			/*logger.info("KioskSOAServiceImpl.expressPayBillAccessory","OrderItemId : " + soaItem.getOrderItemId());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","RecommTotalAmount : " + soaItem.getRecommTotalAmount());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","RecommPartNumber : " + soaItem.getRecommPartNumber());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","SessionKey : " + soaItem.getSessionKey());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","BillingAddressId : " + soaItem.getBillingAddressId());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","CC_brand : " + soaItem.getCc_brand());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","PrinterId : " + soaItem.getPrinterId());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","DeviceId : " + soaItem.getDeviceid());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","EmailAddress : " + soaItem.getEmailAddress());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","State : " + soaItem.getState());
			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","SywrMemberId : " + soaItem.getSywrMemberId());*/

			//set from order API

			logger.info("KioskSOAServiceImpl.expressPayBillAccessory","Exiting KioskSOAServiceImpl.expressPayBillAccessory");
			return soaItem;

	}

	/*
		 * This method creates the session in the ShopinSears API
		 * @param String,String,String,String,String,String,String
		 * @return CheckoutResponse
		 */
		public CheckoutResponse getexpressPayBill(String orderId,String totalAmount,String billingAddressId,String storeNum,
			String sessionKey,String cardData,String itemId , String storeFormat)  throws DJException{

			logger.error("getexpressPayBill Entering KioskSOAServiceImpl.getexpressPayBill","");
			//String updatedStore = null;

			/*if(StringUtils.hasText(storeNum) && storeNum.length()>5){
				updatedStore = storeNum.substring(2);
			}*/
			CheckoutResponse checkoutResponse = null;
			try{
				
				MultiValueMap<String,String> parameters = new LinkedMultiValueMap<String, String>();
				if(StringUtils.hasText(itemId)){
					ItemDTO itemEntity= kioskSOADaoImpl.getItem(storeNum,itemId).get(0);
					if(itemEntity!=null){
						try{
							IdentifierDTO description= kioskSOADaoImpl.getIdentifier(storeNum,itemEntity.getRqtId(),"SYW_NUMBER");
							if(description!=null && StringUtils.hasText(description.getValue())){
								logger.info("KioskSOAServiceImpl.getexpressPayBill","SYWR Number is : " + description.getValue());
								parameters.add("sywrLoyaltyNum",description.getValue());
							}
						}catch(Exception e ){
							logger.info("KioskSOAServiceImpl.getexpressPayBill","The customer is not a SYWR Customer");
						}
					}
				}
				
				parameters.add("orderId",orderId);
				parameters.add("totalAmount",totalAmount);
				parameters.add("billingAddressId",billingAddressId);
				parameters.add("storeNum",org.apache.commons.lang.StringUtils.leftPad(storeNum, 7, '0'));
				parameters.add("sessionKey",sessionKey);
				parameters.add("cardData",cardData);


				String shopsearsAPIURL = MPUWebServiceUtil.getdnsForSOA("ShopsearsAPIURL");
				String restTemplateURL = shopsearsAPIURL + "/mpuservice/ExpressPaybill";
				//logger.info("KioskSOAServiceImpl.getexpressPayBill","ShopsearsAPIRURL : "+ shopsearsAPIURL);
				URI request=new URI(restTemplateURL);
				String automationFlag = MPUWebServiceUtil.getdnsForSOA("automation.flag");
				logger.info("KioskSOAServiceImpl.getexpressPayBill","<<RestTemplate>><<Begins>>URL POST : "+ restTemplateURL);
				String response= "";
				if(null != storeNum && !"".equalsIgnoreCase(storeNum) && storeNum.contains("1333")
						&& null != automationFlag && automationFlag.equalsIgnoreCase("Y")){
					//This redirection to npos simulator is for Automation
					return soaThirdPartyAPIHelper.thirdPartyBillPayCall(storeNum,storeFormat);
				}else{
					response=restTemplate.postForObject(request, parameters, String.class);
				}
			//	logger.info("KioskSOAServiceImpl.getexpressPayBill","<<RestTemplate>><<Success>>");
				logger.error("the response of 2nd api is ====", response);
				if(StringUtils.hasText(response)){
					JAXBContext jaxbContext = JAXBContext.newInstance(CheckoutResponse.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(response);
					checkoutResponse = (CheckoutResponse) unmarshaller.unmarshal(reader);

				}else{
					checkoutResponse=new CheckoutResponse();
					StatusData data=new StatusData();
					data.setResponseCode("ERROR");
					checkoutResponse.setStatusData(data);
				}
				logger.info("getexpressPayBill","Exiting KioskSOAServiceImpl.getexpressPayBill");
				return checkoutResponse;

			}catch(Exception ex){
				checkoutResponse=new CheckoutResponse();
				StatusData data=new StatusData();
				data.setResponseCode("ERROR");
				checkoutResponse.setStatusData(data);
				logger.error("getexpressPayBill","in the catch block of getexpressPayBill"+ex.getMessage());
				//throw new DJException("Accessory purchase system is currently not available. Your credit card will not be charged." );
			}
			return checkoutResponse;
		}

 	/**************************************************** PLACE ORDER API **********************************************************/

 	/*
		 * This method places the order for SOA Item in the NPOS.
		 * Order placed means that the balance is deducted from customer card and order placed in NPOS
		 * Printing functionality is also handled here.
		 * @param SOAItemDTO
		 * @param String
		 * @param String
		 * @return SOAItemDTO
		 */
		public SOAItemDTO expressPlaceOrderAccessory( SOAItemDTO soaItem,String searchMethod,String searchValue)  throws DJException{

			logger.info("expressPlaceOrderAccessory","Entering KioskSOAServiceImpl.expressPlaceOrderAccessory");

			TransactionDetailsResponse detailsResponse=new TransactionDetailsResponse();
			SOAItemDTO output = soaItem;

			String orderID				= (String)soaItem.getOrderId();
			String orderItemId			= (String)soaItem.getOrderItemId();
			String totalAmount			= (String)soaItem.getRecommTotalAmount();
			String sessionKey			= (String)soaItem.getSessionKey();
			String billingAddressId		= (String)soaItem.getBillingAddressId();
			String sellingStoreNumber	= (String)soaItem.getStoreNumber();
			String cc_brand				= (String)soaItem.getCc_brand();
			String deviceId				= (String)soaItem.getDeviceid();
			String emailAddress			= (String)soaItem.getEmailAddress();
			String printerId			= (String)soaItem.getPrinterId();
			String emailReceiptFlag		= (String)soaItem.getEmailReceiptFlag();
			String state				= (String)soaItem.getState();
			String partNumbers			= (String)soaItem.getRecommPartNumber();
			String invokeSecured		= (String)soaItem.getInvokeSecured();
			String itemId				= (String)soaItem.getItemId();
			String storeFormat 			= (String)soaItem.getStoreFormat();

			partNumbers = partNumbers+"P";

			String automationFlag = MPUWebServiceUtil.getdnsForSOA("automation.flag");

		//	logger.info("KioskSOAServiceImpl.expressPlaceOrderAccessory","Calling getPrintReceiptResponse");
			output = getPrintReceiptResponse(output,
					orderID, orderItemId, totalAmount, sessionKey,
					billingAddressId, sellingStoreNumber, cc_brand, deviceId,
					emailAddress, printerId, emailReceiptFlag, state,
					partNumbers,itemId, storeFormat);
			
			
			if(output!=null && StringUtils.hasText(output.getSalescheckNumber()))
			{
				RequestDTO requestDTO = null;
				requestDTO = createItemAccMapping( output,searchMethod,searchValue);
				//Rqt_id , Rqd_id and salescheck number are fetched from order in our database
				//This is done so that we can initiate pickup of the SOA item
				output.setRqtId( requestDTO.getOrder().getRqtId().trim());
				output.setRqdId(requestDTO.getItemList().get(0).getRqdId().trim());
			}


			/*if(detailsResponse!=null && detailsResponse.getStatusData().getResponseCode().equalsIgnoreCase("0")){
				logger.info("expressPlaceOrderAccessory","The response of Place order API is valid");
				if(org.apache.commons.lang.StringUtils.isEmpty(detailsResponse.getTransactionDetails().getTransactionDetail().get(0).getMerchandiseSalesCheckNumber())){
					//soaItem.setMessage("Accessory purchase system is currently not available. Your credit card has been charged, but the full amount will be credited back to your account shortly.");
					DJException dj = new DJException();
					dj.setMessage("Order is not placed in NPOS");
					//dj.setDeveloperMessage("Order is not placed in NPOS");
					throw dj;
				}else{
					output.setSalescheckNumber(detailsResponse.getTransactionDetails().getTransactionDetail().get(0).getMerchandiseSalesCheckNumber());
					RequestDTO requestDTO = null;
					if(! sellingStoreNumber.contains("1333") && null != automationFlag && automationFlag.equalsIgnoreCase("Y")){
						requestDTO = createItemAccMapping( output,searchMethod,searchValue);
						//Rqt_id , Rqd_id and salescheck number are fetched from order in our database
						//This is done so that we can initiate pickup of the SOA item
						output.setRqtId( requestDTO.getOrder().getRqtId().trim());
						output.setRqdId(requestDTO.getItemList().get(0).getRqdId().trim());
						output.setSalescheckNumber(output.getSalescheckNumber());
					}
					output.setThirdApiFlag("true");
					output.setInvokeSecured(invokeSecured);
				}

			}else if(detailsResponse!=null && org.apache.commons.lang.StringUtils.isNotBlank(detailsResponse.getStatusData().getRespMessage()) && detailsResponse.getStatusData().getRespMessage().contains("Printing Failure")){
				logger.info("expressPlaceOrderAccessory","The response of third API is Printing failure");
				if(org.apache.commons.lang.StringUtils.isEmpty(detailsResponse.getTransactionDetails().getTransactionDetail().get(0).getMerchandiseSalesCheckNumber())){

					//Order is not placed in NPOS and printing is also failed.

					throw new DJException("The response of third API is Printing failure");
				}else{

					//Order is placed but printing is failed
					output.setSalescheckNumber(detailsResponse.getTransactionDetails().getTransactionDetail().get(0).getMerchandiseSalesCheckNumber());
					RequestDTO requestDTO = createItemAccMapping( output,searchMethod,searchValue);

					String itemString = request.getOrder().getRqtId() + ";" + request.getItemList().get(0).getRqdId() + ";" + output.getSalescheckNumber() + ";1";
					itemString = itemString.replaceAll("null", "");
					output.setItemString(itemString);


					output.setRqtId(requestDTO.getOrder().getRqtId().trim());
					output.setRqdId(requestDTO.getItemList().get(0).getRqdId().trim());
					output.setSalescheckNumber(output.getSalescheckNumber());
					output.setReqQuantity("1");
					output.setThirdApiFlag("true");
					output.setInvokeSecured(invokeSecured);


					logger.info("KioskSOAServiceImpl.expressPlaceOrderAccessory","Rqt_id  : " + output.getRqtId()+"Rqd_id  : " + output.getRqdId()+"SOA Salescheck number  : " + output.getSalescheckNumber());
				//	logger.info("KioskSOAServiceImpl.expressPlaceOrderAccessory","Rqd_id  : " + output.getRqdId());
				//	logger.info("KioskSOAServiceImpl.expressPlaceOrderAccessory","SOA Salescheck number  : " + output.getSalescheckNumber());
				}

			}else{
				logger.info("expressPlaceOrderAccessory","The response of place order API is invalid");
				output.setThirdApiFlag("false");
				output.setInvokeSecured(invokeSecured);
			}*/

			logger.info("expressPlaceOrderAccessory","Exiting KioskSOAServiceImpl.expressPlaceOrderAccessory");
			return output;
		}


		/**
			 * This method do the following things
			 * 1) Fetches the SOA order from DB. ( This order is to be pushed from NPOS )
			 * 2) if the order is not pushed from NPOS in our DB, it waits for 5 seconds and fetches the SOA order again.
			 * 3) if the order is not found in our DB, it fetches the order from NPOS and insert it into our database
			 * This method also creates entry into purchase table
			 * It adds the SOA order in the cache , so that later on SOA order's pick up can be initiated.
			 * @param SOAItemDTO
			 * @param String
			 * @param String
			 * @return RequestDTO
			 */

			@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
			 public RequestDTO createItemAccMapping(SOAItemDTO itemDescription,String searchMethod,String searchValue ) throws DJException{

				logger.info("createItemAccMapping","Entering KioskSOAServiceImpl.createItemAccMapping");
				RequestDTO requestDTO = new RequestDTO();
				 McpOrigSCPurchasedSCMappingEntity mappingEntity=new McpOrigSCPurchasedSCMappingEntity();
				 String salesCheckNumber=org.apache.commons.lang.StringUtils.leftPad(itemDescription.getSalescheckNumber(), 12, '0');
				 ItemDTO itemdto=kioskSOADaoImpl.getItem(itemDescription.getStoreNumber(),itemDescription.getItemId()).get(0);
				 if(itemdto!=null){
					 //mappingEntity.setMcpOrigWorkId(itemdto.getRqtId());
					 mappingEntity.setMcpOrigItemId(itemdto.getRqdId());
					 //itemDescription.setWorkItemSequenceNumber(String.valueOf(mcpWorkItemEntity.getWorkItemSequenceNumber()));
					 itemDescription.setWorkId(String.valueOf(itemdto.getRqtId()));
					 itemDescription=kioskSOADaoImpl.getItemInfo(itemDescription);
					 if(StringUtils.hasText(itemDescription.getDescription()) && itemDescription.getDescription().length()>50){
						 mappingEntity.setMcpBaseItemDesc((itemDescription.getDescription()).substring(0,50));
					 }else{
						 mappingEntity.setMcpBaseItemDesc(itemDescription.getDescription());
				 }

				 }

				 mappingEntity.setAccessoryItemPrice(new BigDecimal(itemDescription.getRecommTotalAmount()));
				 mappingEntity.setAccessorySalescheckNo(salesCheckNumber);
				 mappingEntity.setBaseItemPrice(new BigDecimal(itemDescription.getPrice()));

				 if(null != itemDescription.getRecommPartNumber() && !itemDescription.getRecommPartNumber().isEmpty()){
					 mappingEntity.setMcpAccessoryItemId(itemDescription.getRecommPartNumber().substring(0,itemDescription.getRecommPartNumber().length()-1));
				 }


				 //fetching the SOA order from the database
				 List<OrderDTO> listOrderDTO = kioskSOADaoImpl.getWorkDetailBySalesCheck(itemDescription.getStoreNumber(),salesCheckNumber);
				 OrderDTO orderDTO = null;
				 if(null != listOrderDTO && listOrderDTO.size() != 0){
					 orderDTO = listOrderDTO.get(0);
					 String fields = MpuWebConstants.ITEM;
					 List<String> status = Arrays.asList(MpuWebConstants.OPEN,MpuWebConstants.EXPIRED,MpuWebConstants.COMPLETED);
					 requestDTO = mpuWebServiceProcessor.getRequestData(orderDTO.getRqtId(), itemDescription.getStoreNumber(), fields, null, status);
				 }
				 if(orderDTO != null){
					 //This is set to insert into mapping table
					 mappingEntity.setMcpAccessoryWorkId(Integer.parseInt(orderDTO.getRqtId()));
				 }else{
						 try {
							 //If the SOA order is not found, wait for 5 seconds for the NPOS to push the order to our DB
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							DJException djException = MPUWebServiceUtil.getDJExceptionFromException(e);
							throw djException;
						}
						 logger.info("createItemAccMapping","the time  after 5 second delay =="+new Timestamp(System.currentTimeMillis()));
						//Again fetching the SOA order from the database
						 listOrderDTO = kioskSOADaoImpl.getWorkDetailBySalesCheck(itemDescription.getStoreNumber(),salesCheckNumber);

						 if(null != listOrderDTO && listOrderDTO.size() != 0){
							 orderDTO = listOrderDTO.get(0);
							 String fields = MpuWebConstants.ITEM;
							 List<String> status = Arrays.asList(MpuWebConstants.OPEN,MpuWebConstants.EXPIRED,MpuWebConstants.COMPLETED);
							 requestDTO = mpuWebServiceProcessor.getRequestData(orderDTO.getRqtId(), itemDescription.getStoreNumber(), fields, null, status);
						 }
						 if(orderDTO!=null){
							 //order is pushed in our database from NPOS
							 mappingEntity.setMcpAccessoryWorkId(Integer.parseInt(orderDTO.getRqtId()));
						 }else{
							 //Order is not pushed by NPOS
							 //Hence, fetch the order From NPOS
							 PickUpDTO pickUpDTO = new PickUpDTO();
							 pickUpDTO.setStoreNumber(itemDescription.getStoreNumber());
							 pickUpDTO.setStoreFormat(itemDescription.getStoreFormat());
							 List<Order> orderListNPOS = pickUpServiceDAO.getOrdersFromNPOS(pickUpDTO, "SALESCHECK",	salesCheckNumber, MpuWebConstants.PICKUP);

							 //push in our database
							 if(null != orderListNPOS && orderListNPOS.size() != 0 ){
								 Order order = orderListNPOS.get(0);
								 RequestDTO soaOrder;
								try {
									soaOrder = ConversionUtils.convertNPOSOrderToRequestDTO(order);
									requestDTO = mpuWebServiceProcessor.createRequest(soaOrder.getOrder(), soaOrder.getCustomer(), soaOrder.getItemList(),
										 soaOrder.getPaymentList(), soaOrder.getTask());
								} catch (Exception e) {
									e.printStackTrace();
								}
							 }



							 //get rqt_id , rqd_id
						 }
				 }
				 mappingEntity.setAccessoryPurchaseDate(new Timestamp(System.currentTimeMillis()));
				// logger.info("createItemAccMapping","the time  before insert into mapping table =="+new Timestamp(System.currentTimeMillis()));
				 kioskSOADaoImpl.insertMappingEntity(mappingEntity, itemDescription.getStoreNumber());

				// logger.info("createItemAccMapping","the time  after insert into mapping table =="+new Timestamp(System.currentTimeMillis()));
			//	 logger.info("createItemAccMapping","Exiting KioskSOAServiceImpl.createItemAccMapping");

				 //settting the Assigned time in the request
				 SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				 SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				 Date date = new Date();

				 try {

						requestDTO.getOrder().setTimeAssigned(new Date().toString());
				 }catch(Exception e){
					 logger.error("KioskSOAServiceImpl.createItemAccMapping", "The SOA order's assigned time cannot be set");
				 }



				 if(requestDTO!=null){

					 List<RequestDTO> requestDTOListTemp = pickUpServiceProcessor.getAllRequestsFromEhCache(requestDTO.getOrder().getStoreNumber(),MpuWebConstants.PICK_UP_REQUEST,itemDescription.getKioskName(),"KIOSK",searchValue, searchMethod);
					 if(null != requestDTOListTemp){
						 CustomerDTO customer = requestDTOListTemp.get(0).getCustomer();
						 requestDTO.setCustomer(customer);
						 requestDTOListTemp.add(requestDTO);
						 try {
							pickUpServiceProcessor.addRequestToEhCache(requestDTOListTemp, requestDTO.getOrder().getStoreNumber(), itemDescription.getKioskName(), MpuWebConstants.PICK_UP_REQUEST, "KIOSK", searchValue, searchMethod);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }

				 }

				 return requestDTO;
			 }

	/*
		 * This method places order in NPOS,creates entry in purchase table, and add SOA order to cache.
		 * @param SOAItemDTO, String,String,String,String,String,String,String,String,String,String,String,String,String,String,String
		 * @return TransactionDetailsResponse
		 */
		public SOAItemDTO getPrintReceiptResponse(SOAItemDTO soaItem, String orderID,String orderItemId,String totalAmount,String sessionKey ,
				String billingAddressId,String sellingStoreNumber, 	String cc_brand,String deviceId ,String emailAddress,String printerId ,
				String emailReceiptFlag,String state,String partNumbers,String itemId, String storeFormat)  throws DJException{

			logger.error("the orderID=="+orderID+"orderItemId=="+orderItemId+"totalAmount=="+totalAmount+"sessionKey=="+sessionKey+"billingAddressId"+billingAddressId+
					"sellingStoreNumber"+sellingStoreNumber+"cc_brand"+cc_brand+"deviceId"+deviceId+"emailAddress"+emailAddress+"printerId"+printerId+
					"emailReceiptFlag"+emailReceiptFlag+"state"+state+"partNumbers"+partNumbers+"itemId"+itemId+"storeFormat"+storeFormat, "");

			//TransactionDetailsResponse transactionDetailsResponse=null;
			PrintReceiptInfo printReceiptInfo=new PrintReceiptInfo();
			boolean sywrFlag=false;
			StringBuffer buffer=new StringBuffer(MPUWebServiceUtil.getdnsForSOA("ShopsearsAPIURL")+"/mpuservice/ExpressPlaceOrder?");
			try{
				if(null != orderID){
					orderID = orderID.trim();
				}
				if(null != cc_brand){
					cc_brand = cc_brand.trim();
				}
				
				if(StringUtils.hasText(emailReceiptFlag) && emailReceiptFlag.equalsIgnoreCase("true")){
					emailReceiptFlag = "Y";
				}else{
					emailReceiptFlag = "N";
				}

					if(StringUtils.hasText(itemId)){
						ItemDTO itemEntity=kioskSOADaoImpl.getItem(sellingStoreNumber,itemId).get(0);
						if(itemEntity!=null){
							IdentifierDTO description= kioskSOADaoImpl.getIdentifier(sellingStoreNumber,itemEntity.getRqtId(),"SYW_NUMBER");
							if(description!=null && StringUtils.hasText(description.getValue())){
								buffer.append("orderId="+orderID+"&orderItemId="+orderItemId+"&totalAmount="+totalAmount+"&sessionKey="+sessionKey+"&billingAddressId="+billingAddressId
										+"&sellingStoreNumber="+org.apache.commons.lang.StringUtils.leftPad(sellingStoreNumber, 7,'0')+"&cc_brand="+cc_brand+"&deviceId="+deviceId+"&printerId="+printerId+"&emailReceiptFlag="+emailReceiptFlag+"&state="+state+"&partNumbers="+partNumbers+"&sywrLoyaltyNum="+description.getValue());
								sywrFlag=true;
							}
						}

						if(!sywrFlag){
							buffer.append("orderId="+orderID+"&orderItemId="+orderItemId+"&totalAmount="+totalAmount+"&sessionKey="+sessionKey+"&billingAddressId="+billingAddressId
									+"&sellingStoreNumber="+org.apache.commons.lang.StringUtils.leftPad(sellingStoreNumber, 7,'0')+"&cc_brand="+cc_brand+"&deviceId="+deviceId+"&printerId="+printerId+"&emailReceiptFlag="+emailReceiptFlag+"&state="+state+"&partNumbers="+partNumbers);
						}

					}

				URI uri=new URI(buffer.toString());
				String automationFlag = MPUWebServiceUtil.getdnsForSOA("automation.flag");
				logger.error("getPrintReceiptResponse <<RestTemplate>><<Begins>>URL GET : "+buffer.toString(),"");
				String printReceiptInfoResponse = "";
				/*if(null != sellingStoreNumber && !"".equalsIgnoreCase(sellingStoreNumber) && sellingStoreNumber.contains("1333") 
						&& null != automationFlag && automationFlag.equalsIgnoreCase("Y")){
					//This redirection to npos simulator is for Automation
					printReceiptInfo = soaThirdPartyAPIHelper.thirdPartyPlaceOrderCall(sellingStoreNumber,storeFormat);
					printReceiptInfoResponse = "PrintReceiptInfo";
				}else{*/
					printReceiptInfoResponse=restTemplate.getForObject(uri,String.class);
				//}
				//logger.info("getPrintReceiptResponse","<<RestTemplate>><<Success>>");

				logger.error("getPrintReceiptResponse The response of printReceiptInfo is  "+printReceiptInfoResponse,"");
				if(StringUtils.hasText(printReceiptInfoResponse)){
				//	logger.info("getPrintReceiptResponse","The response of printReceiptInfo contains CheckoutResponse");
					if(org.apache.commons.lang.StringUtils.containsIgnoreCase(printReceiptInfoResponse, "CheckoutResponse")){
						//Order is not placed in the NPOS
						soaItem.setMessage("Accessory purchase system is currently not available. Your credit card has been charged, but the full amount will be credited back to your account shortly.");
						soaItem.setValid(false);
/*						transactionDetailsResponse=new TransactionDetailsResponse();
						com.searshc.mpuwebservice.domain.TransactionDetailsResponse.StatusData statusData=
								new com.searshc.mpuwebservice.domain.TransactionDetailsResponse.StatusData();
						statusData.setResponseCode("ERROR");
						transactionDetailsResponse.setStatusData(statusData);*/
					}
					else if(org.apache.commons.lang.StringUtils.containsIgnoreCase(printReceiptInfoResponse, "PrintReceiptInfo")){
						//Order is placed successfully in NPOS
					//	logger.info("getPrintReceiptResponse","Order is successfully placed in NPOS");
						JAXBContext jaxbContext = JAXBContext.newInstance(PrintReceiptInfo.class);
						Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
						StringReader reader = new StringReader(printReceiptInfoResponse);
						printReceiptInfo = (PrintReceiptInfo) unmarshaller.unmarshal(reader);
						
/*						if(!sellingStoreNumber.contains("1333") && null != automationFlag && automationFlag.equalsIgnoreCase("Y")){

						}
*/						if(printReceiptInfo!=null && (printReceiptInfo.getStatusData().getResponseCode().equalsIgnoreCase("0") ||
								printReceiptInfo.getStatusData().getResponseCode().equalsIgnoreCase("601"))){
							//printing is done
							logger.error("getPrintReceiptResponse Printing is successful.","");
							soaItem.setMessage("We're retrieving your item(s) right now.  Watch the overhead monitor to track your status - an associate will be out shortly with your order, along with the accessory you just purchased");
							soaItem.setValid(true);
							//logger.info("getPrintReceiptResponse","calling getOrderInfo API");
							soaItem=soaThirdPartyAPIHelper.getOrderInfo(soaItem,orderID, sellingStoreNumber, storeFormat);
							//logger.info("getPrintReceiptResponse","getOrderInfo API calling complete.");
						}
						else if(printReceiptInfo!=null && printReceiptInfo.getStatusData().getResponseCode().equalsIgnoreCase("Error")){
							//There is some printing issue
							logger.error("getPrintReceiptResponse The printing failure occurred in printreceipt info","");
							//logger.info("getPrintReceiptResponse","calling getOrderInfo API");
							soaItem.setMessage("We're retrieving your item(s) right now. Watch the overhead monitor to track your status - an associate will be out shortly with your order and your [receipt/pick-up check]");
							soaItem.setValid(true);
							soaItem=soaThirdPartyAPIHelper.getOrderInfo(soaItem,orderID, sellingStoreNumber, storeFormat);
							//transactionDetailsResponse.getStatusData().setResponseCode("print_issue");
							//transactionDetailsResponse.getStatusData().setRespMessage(printReceiptInfo.getStatusData().getResponseDescription());
						}else{
							logger.error("getPrintReceiptResponse The response of printReceiptInfo contains invalid status response code","");
							//transactionDetailsResponse=new TransactionDetailsResponse();
							//com.searshc.mpuwebservice.domain.TransactionDetailsResponse.StatusData statusData=
							//		new com.searshc.mpuwebservice.domain.TransactionDetailsResponse.StatusData();
							//statusData.setResponseCode("ERROR");
							//transactionDetailsResponse.setStatusData(statusData);
							//soaItem.setMessage("We're retrieving your item(s) right now. Watch the overhead monitor to track your status - an associate will be out shortly with your order and your [receipt/pick-up check]");
							soaItem.setMessage("Accessory purchase system is currently not available. Your credit card has been charged, but the full amount will be credited back to your account shortly.");
							soaItem.setValid(false);
						}
					}}
			}catch(Exception ex){
				logger.error("in the ctach block of getPrintReceiptResponse", "");
				//logger.info("getPrintReceiptResponse","in the catch block of getPrintReceiptResponse ");
				/*transactionDetailsResponse=new TransactionDetailsResponse();
				com.searshc.mpuwebservice.domain.TransactionDetailsResponse.StatusData statusData=
						new com.searshc.mpuwebservice.domain.TransactionDetailsResponse.StatusData();

				statusData.setResponseCode("ERROR");

				transactionDetailsResponse.setStatusData(statusData);*/
				soaItem.setMessage("Accessory purchase system is currently not available. Your credit card has been charged, but the full amount will be credited back to your account shortly.");
				soaItem.setValid(false);
			}
			logger.info("getPrintReceiptResponse","Exiting KioskSOAServiceImpl.getPrintReceiptResponse");
			return soaItem;
	}

	/**************************************************** UTILTIY METHODS **********************************************************/

	/*
	 * This method determines whether a store is SOA Enabled.
	 * @param String
	 * @param String
	 * @return List<OrderDTO>
	 */
	public List<OrderDTO> getOrder(String storeNumber,String orderId) throws DJException{

		logger.info("getOrder","Entering KioskSOAServiceImpl.getOrder");

		List<OrderDTO> listOrderDTO = null;

		listOrderDTO = kioskSOADaoImpl.getOrder(storeNumber,orderId);

		logger.info("getOrder","Exiting KioskSOAServiceImpl.getOrder");

		return listOrderDTO;
	}

	/*
	 * This method determines whether a store is SOA Enabled.
	 * @param String
	 * @param int
	 * @return ItemDTO
	 */

	public ItemDTO getItemDetail(String storeNumber,int itemId)  throws DJException {

		logger.info("getItemDetail","Entering KioskSOAServiceImpl.getItemDetail");

		ItemDTO itemDTO = null;

		itemDTO = kioskSOADaoImpl.getItemDetail(storeNumber,itemId);

		logger.info("getItemDetail","Exiting KioskSOAServiceImpl.getItemDetail");

		return itemDTO;
	}

	/*
	 * This method determines whether a store is SOA Enabled.
	 * @param String
	 * @param String
	 * @return boolean
	 */
	public boolean isSOAEnabledOnStore(String storeNumber, String kioskName) throws DJException{

		logger.info("isSOAEnabledOnStore","Entering KioskSOAServiceImpl.isSOAEnabledOnStore");
		boolean sellofAccFlag = false;
		List<Map<String,Object>> kioskEntityList  = kioskSOADaoImpl.getKioskEntityList(storeNumber);
		if(!CollectionUtils.isEmpty(kioskEntityList)){
			for(Map<String,Object> kioskEntity : kioskEntityList){
				if("1".equalsIgnoreCase(kioskEntity.get("sell_of_acc_flag").toString()) && null!=kioskName
						&& kioskName.equalsIgnoreCase(kioskEntity.get("kiosk_name").toString())){
					sellofAccFlag =  true;
				}
			}
		}
		//logger.info("isSOAEnabledOnStore","Store "+ storeNumber + " has SOA enabled.");
		logger.info("isSOAEnabledOnStore","Exiting KioskSOAServiceImpl.isSOAEnabledOnStore");
		return sellofAccFlag;
	}

	/*
	 * This method determines whether a order is present in MPU database based on rqt_id.
	 * @param String
	 * @param String
	 * @param String
	 * @return boolean
	 */
	public boolean getSalescheckNumber(String storeNumber,String rqt_id, String rqd_id)  throws DJException{

		logger.info("getSalescheckNumber","Entering KioskSOAServiceImpl.getSalescheckNumber");

		String salesCheckNumber = kioskSOADaoImpl.getSalesCheckNumber(storeNumber,rqt_id,
				rqd_id);
		if(null != salesCheckNumber && !salesCheckNumber.equalsIgnoreCase("") ){
			logger.info("getSalescheckNumber","Exiting KioskSOAServiceImpl.getSalescheckNumber");
			return true;
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


			

		/*
		 * This method determines on the basis of rqd_id whether the customer is a SYWR member.
		 * @param SOAItemDTO, String , String
		 * @return boolean
		 */
		public boolean getStatusForSYWRCustomer(SOAItemDTO soaItemDTO , String rqdId,String description) {
			try{
				ItemDTO itemEntity= kioskSOADaoImpl.getItem(soaItemDTO.getStoreNumber(),rqdId).get(0);
				logger.info("getStatusForSYWRCustomer","Entering KioskSOAServiceImpl.getStatusForSYWRCustomer");
				if(itemEntity!=null){
					IdentifierDTO identifierDTO=kioskSOADaoImpl.getIdentifier(soaItemDTO.getStoreNumber(),itemEntity.getRqtId(), description);
					if(identifierDTO!=null){
						if(StringUtils.hasText(identifierDTO.getValue())){
							soaItemDTO.setSywrMemberId(identifierDTO.getValue());
							logger.info("getStatusForSYWRCustomer","Exiting KioskSOAServiceImpl.getStatusForSYWRCustomer");
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
		 * Retrieves updated item details.
		 * @param SOAItemDTO
		 * @return SOAItemDTO
		 */
		public SOAItemDTO getItemInfo(	SOAItemDTO input)  throws DJException{

			logger.info("getItemInfo","Entering KioskSOAServiceImpl.getItemInfo");

			SOAItemDTO sellingAccessoriesItem = null;

			sellingAccessoriesItem = kioskSOADaoImpl.getItemInfo(input);

			logger.info("getItemInfo","Exiting KioskSOAServiceImpl.getItemInfo");

			return sellingAccessoriesItem;
	}

	/**************************************************** END OF FILE **********************************************************/


}
