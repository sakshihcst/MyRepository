package com.searshc.mpuwebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ResponseDTO;
import com.searshc.mpuwebservice.bean.SOAItemDTO;
import com.searshc.mpuwebservice.constant.SOAConstants;
import com.searshc.mpuwebservice.constant.URLConstants;
import com.searshc.mpuwebservice.processor.KioskSOAService;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;

/**
 * @author pgoyal
 * create date : 17-Oct-2014
 * This class acts as a controller interface for calling webservice methods
 * related to SOA.
 *
 * SOA stands for Selling of Accessories. When customer comes at kiosk and places the order
 * for pickup items. Bases on those pickup items a single accessory is proposed to the customer
 * for buying. This accessory is selected based on certain business rules.
 * The SOA code also handles the bill payment and placing of order both in NPOS and in our database.
 * If the customer selects the SOA item (eg cable is SOA item for TV ). then SOA item's pickup will
 * also be initiated.
 * In case of system failure or some exception, customer is asked to purchase the item from the
 * register.
 */

@Controller
@RequestMapping("/KioskSOAMonitor")
public class KioskSOAController {

	@Autowired
	private KioskSOAService kioskSOAServiceImpl;

	private static transient DJLogger logger = DJLoggerFactory.getLogger(KioskSOAController.class);

	SOAItemDTO sellingItem = null;
	/**
	 * This method is the entry point for getting the proposed SOA item.
	 * This method gives a unique SOA item as per the business rules.
	 * @param soaItem
	 * @return ResponseEntity<ResponseDTO>
	 */
	@RequestMapping(method = RequestMethod.POST, value = SOAConstants.DISPLAYACCESSORY,produces = URLConstants.APPLICATION_JSON)
	public @ResponseBody
	ResponseEntity<ResponseDTO> displayThankYouWithAccessories(@RequestBody SOAItemDTO soaItem) {

		logger.info("KioskSOAController.displayThankYouWithAccessories","Entering method");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			logger.info("displayThankYouWithAccessories","Begins calling processor");
			sellingItem = kioskSOAServiceImpl.displayThankYouWithAccessories(soaItem);
			//sellingItem = getSOADTO();
			responseDTO.setResponseBody(sellingItem);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			logger.info("displayThankYouWithAccessories","Success");
		}catch (Exception e) {
			logger.error("KioskSOAController.displayThankYouWithAccessories","Failure");
			logger.error("KioskSOAController.displayThankYouWithAccessories",e);
			SOAItemDTO exceptionSOA = new SOAItemDTO();
			exceptionSOA.setValid(Boolean.FALSE);
			if (e instanceof DJException) {
				DJException djEx = (DJException) e;
				//exceptionSOA.setMessage( null == djEx.getMessage() ? djEx.getDevloperMessage() : djEx.getMessage());
				String exceptionMessage = ( null == djEx.getMessage() ? djEx.getDevloperMessage() : djEx.getMessage());
				logger.error("KioskSOAController.displayThankYouWithAccessories","Exception Message : "+ exceptionMessage);
			} else {
				logger.error("KioskSOAController.displayThankYouWithAccessories",e);
				//exceptionSOA.setMessage(e.toString());
			}
			responseDTO.setResponseBody(exceptionSOA);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);

		}
		 logger.info("KioskSOAController.displayThankYouWithAccessories" ,"Exiting");
		return respEntity;
	}

	/**
	 * This method acts as a controller method for fetching the SOA item price details.
	 * @param soaItem
	 * @return ResponseEntity<ResponseDTO>
	 */
	@RequestMapping(method = RequestMethod.POST, value = SOAConstants.CHECKOUTACCESSORY,produces = URLConstants.APPLICATION_JSON)
	public @ResponseBody
	ResponseEntity<ResponseDTO> expressCheckoutAccessory(@RequestBody SOAItemDTO soaItem) {
		logger.info("KioskSOAController.expressCheckoutAccessory","Entering method");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			logger.info("expressCheckoutAccessory","Begins");
			SOAItemDTO sellingItem = kioskSOAServiceImpl.expressCheckoutAccessory(soaItem);
			//SOAItemDTO sellingItem = getSOADTO();
			responseDTO.setResponseBody(sellingItem);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			logger.info("expressCheckoutAccessory","Success");
		}catch (Exception e) {
			logger.error("KioskSOAController.expressCheckoutAccessory","Failure");
			logger.error("KioskSOAController.expressCheckoutAccessory",e);
			SOAItemDTO exceptionSOA = new SOAItemDTO();
			exceptionSOA.setValid(Boolean.FALSE);
			exceptionSOA.setMessage(soaItem.getMessage());

			/*if (e instanceof DJException) {
				DJException djEx = (DJException) e;
				exceptionSOA.setMessage( null == djEx.getMessage() ? djEx.getDevloperMessage() : djEx.getMessage());
			} else {
				exceptionSOA.setMessage(e.toString());
			}*/
			responseDTO.setResponseBody(exceptionSOA);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			/*responseDTO.setResponseBody(sellingItem);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);*/
		}
		 logger.info("KioskSOAController.expressCheckoutAccessory" ,"Exiting");
		return respEntity;
	}

	/**
	 * This method acts as a controller method when the customer swipes the payment card
	 * @param searchMethod , searchValue
	 * @param soaItem
	 * @return ResponseEntity<ResponseDTO>
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getMonitor/expressPayBillAccessory/{searchMethod}/{searchValue}",produces = URLConstants.APPLICATION_JSON)
	public @ResponseBody
	ResponseEntity<ResponseDTO> expressPayBillAccessory(@PathVariable("searchMethod") String searchMethod,@PathVariable("searchValue") String searchValue,
												@RequestBody SOAItemDTO soaItem) {
		logger.info("KioskSOAController.expressPayBillAccessory","Entering method");

		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			logger.info("expressPayBillAccessory","Begins");
			SOAItemDTO sellingItem = kioskSOAServiceImpl.expressPayBillAccessory( soaItem,searchMethod,searchValue);
			//SOAItemDTO sellingItem = getSOADTO();
			responseDTO.setResponseBody(sellingItem);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			logger.info("expressPayBillAccessory","Success");
		}catch (Exception e) {
			logger.error("KioskSOAController.expressPayBillAccessory","Failure");
			logger.error("KioskSOAController.expressPayBillAccessory",e);
			SOAItemDTO exceptionSOA = new SOAItemDTO();
			exceptionSOA.setValid(Boolean.FALSE);
			exceptionSOA.setMessage(soaItem.getMessage());
			//exceptionSOA.setMessage("Accessory purchase system is currently not available. Your card will not be charged.");

			/*if (e instanceof DJException) {
				DJException djEx = (DJException) e;
				exceptionSOA.setMessage( null == djEx.getMessage() ? djEx.getDevloperMessage() : djEx.getMessage());
			} else {
				exceptionSOA.setMessage(e.toString());
			}*/
			responseDTO.setResponseBody(exceptionSOA);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			/*responseDTO.setResponseBody(sellingItem);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);*/
		}
		 logger.info("exiting" ,"KioskSOAController.expressPayBillAccessory");
		return respEntity;
	}

	/**
	 * This method acts as a controller method which deducts the payment, places the order in NPOS,
	 * ensures that the order is present in MPU database if it is placed in NPOS
	 * @param searchMethod , searchValue
	 * @param soaItem
	 * @return ResponseEntity<ResponseDTO>
	 */
	@RequestMapping(method = RequestMethod.POST, value = SOAConstants.PLACEORDERACCESSORY+"/{searchMethod}/{searchValue}",produces = URLConstants.APPLICATION_JSON)
	public @ResponseBody
	ResponseEntity<ResponseDTO> expressPlaceOrderAccessory(@PathVariable("searchMethod") String searchMethod,@PathVariable("searchValue") String searchvalue,
			@RequestBody SOAItemDTO soaItem) {
		logger.info("KioskSOAController.expressPlaceOrderAccessory","Entering");
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			logger.info("expressPlaceOrderAccessory","Begins");
			SOAItemDTO sellingItem = kioskSOAServiceImpl.expressPlaceOrderAccessory( soaItem,searchMethod,searchvalue);
			//sellingItem = getSOADTO();
			responseDTO.setResponseBody(sellingItem);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			logger.info("expressPlaceOrderAccessory","Success");
		}catch (Exception e) {
			logger.error("KioskSOAController.expressPlaceOrderAccessory","Failure");
			logger.error("KioskSOAController.expressPlaceOrderAccessory",e);
			SOAItemDTO exceptionSOA = new SOAItemDTO();
			exceptionSOA.setValid(Boolean.FALSE);
			exceptionSOA.setMessage(soaItem.getMessage());
			/*if (e instanceof DJException) {
				DJException djEx = (DJException) e;
				exceptionSOA.setMessage( null == djEx.getMessage() ? djEx.getDevloperMessage() : djEx.getMessage());
			} else {
				exceptionSOA.setMessage(e.toString());
			}*/
			responseDTO.setResponseBody(exceptionSOA);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
			/*responseDTO.setResponseBody(sellingItem);
			respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);*/
		}
		 logger.info("KioskSOAController.expressPlaceOrderAccessory" ,"Exiting");
		return respEntity;
	}

	/*public SOAItemDTO getSOADTO(){

		SOAItemDTO soaItem = new SOAItemDTO();

		soaItem.setItemId("7634");
		soaItem.setWorkId("4898");
		soaItem.setReqQuantity("1");
		soaItem.setKioskName("MPU1");
		soaItem.setDescription("");
		soaItem.setPrice("49.98");
		soaItem.setMainOrderSalesCheck("093002429473");

		soaItem.setRecommItemDiv("9");
		soaItem.setRecommItemNum("41626");
		soaItem.setRecommItemSku("000");
		soaItem.setRecommItemMarginRate("47.309136420525654");
		soaItem.setItemImageUrl("http://c.shld.net/rpx/i/s/i/spin/image/spin_prod_207583601");
		soaItem.setAccessoryImageUrl("http://c.shld.net/rpx/i/s/i/spin/image/spin_prod_207583601");
		soaItem.setRecommDescription("Briggs & Stratton  Enviro-Flo Plus Gas Can, 2+, 1 can");
		soaItem.setRecommPrice("7.99");
		soaItem.setRecommRating("");
		soaItem.setWorkItemSequenceNumber("");

		// For Navigation to correct flow
		soaItem.setInvokeSecured("true");
		soaItem.setItemsList("4898;7634;093002429473;1,4898;7634;093002429473;1,4898;7634;093002429473;1");
		soaItem.setSecuredOrder(null);
		soaItem.setSalescheckNumber(null);
		soaItem.setTransactionDate("2014-08-23 17:34:17.0");
		soaItem.setStoreNumber("2990");
		soaItem.setStoreFormat("SearsRetail");
		soaItem.setRecommPartNumber("00941626000");
		soaItem.setStoreZipCode("61112");

		// For Output of CheckOut API
		soaItem.setRecommTotalAmount("49.88");
		soaItem.setBillingAddressId("22546234");
		soaItem.setSessionKey("API_0000v2YBRnRGZPDYoc_ex2rTr2T:175selcnv");
		soaItem.setCardData(null);
		soaItem.setOrderId("4685680");

		//For Display on Checkout Screen
		soaItem.setRecommSubTotal("10.99");
		soaItem.setRecommPreTaxTotal("10.99");
		soaItem.setRecommSalesTaxTotal("0.89");

		//Flag to decide navigation
		//private boolean valid;
		soaItem.setValid(true);
		soaItem.setMessage("ABCD");

		//For paybillAPI
		soaItem.setCc_brand(null);
		soaItem.setDeviceid("01818MPU1");
		soaItem.setEmailAddress("PuneetkumarGoyal@gmail.com");
		soaItem.setPrinterId("SRVQ09-31-5039");
		soaItem.setState(null);

		//For third API response
		soaItem.setThirdApiFlag("false");
		soaItem.setSywrPoints("null");
		soaItem.setSywrMemberId("7081390000309593");
		soaItem.setOrderInfoFlag(null);


		//Attribute for orderitem id in 3rd API
		soaItem.setOrderItemId("0");

		soaItem.setEmailReceiptFlag(null);
		soaItem.setReceiptFlag(null);

		soaItem.setItemString(""); //This is added so that pick up of the soa item can be initiated from kiosk

		//Main item Details
		soaItem.setOrderDate("");
		soaItem.setCustomerID("");
		soaItem.setCustomerName("");
		soaItem.setSequenceNo("");


		//SOA Item detail
		soaItem.setRqtId("5001");
		soaItem.setRqdId("7656");
		soaItem.setDiv("009");
		soaItem.setItem("65012");
		soaItem.setSku("000");

		return soaItem;

	}*/



}
