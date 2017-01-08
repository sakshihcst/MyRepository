package com.searshc.mpuwebservice.constant;

public class SOAConstants {
	
	public static final String KIOSKENTITY = "/getKioskDetailList";
	public static final String STORE_NO = "store_no";
	public static final String STORE_NUMBER = "store_number";
	public static final String KIOSK_NAME = "kiosk_name";
	public static final String MOD_ENABLED = "mod_enabled";
	public static final String SELL_OF_ACC_FLAG = "sell_of_acc_flag";
	public static final String SBO_PRINTER_FLAG = "sbo_printer_flag";
	public static final String HOLD_GO_FLAG = "hold_go_flag";
	public static final String LOCKER_ENABLED = "locker_enabled";

	//SOA constants
	public static final String GETSALESCHECKNUMBER = "/getSalesCheckNumber";
	
	public static final String url1 = "/getMonitor/displayThankYouWithAccessories/{selectedItems}/{invokeSecured}/{storeNumber}";
	public static final String url2 = "/getMonitor/displayThankYouWithAccessories/{selectedItems}/{invokeSecured}/{securedOrder}/{storeNumber}";
	public static final String checkouturl = "/getMonitor/expressCheckoutAccessory/{saleschecknum}/{transactiondate}/{storenumber}/{zipcode}/{partnum}/{price}/{itemId}/{accdesc}/{invokeSecured}/{baseItemPrice}";
	public static final String billpayurl = "/getMonitor/expressPayBillAccessory/{orderId}/{totalAmount}/{billingAddressId}/{storeNum}/{sessionKey}/{cardData}/{invokeSecured}/{itemId}/{kioskName}/{baseItemPrice}";
	public static final String placeorderurl = "/getMonitor/expressPlaceOrderAccessory/{orderID}/{orderItemId}/{totalAmount}/{sessionKey}/{billingAddressId}/{sellingStoreNumber}/{cc_brand}/{deviceId}/{emailAddress}/{printerId}/{emailReceiptFlag}/{state}/{partNumbers}/{invokeSecured}/{itemId}/{baseItemPrice}";

	public static final String DISPLAYACCESSORY = "/getMonitor/displayThankYouWithAccessories";
	public static final String CHECKOUTACCESSORY = "/getMonitor/expressCheckoutAccessory";
	public static final String BILLPAYACCESSORY = "/getMonitor/expressPayBillAccessory";
	public static final String PLACEORDERACCESSORY = "/getMonitor/expressPlaceOrderAccessory";

}
