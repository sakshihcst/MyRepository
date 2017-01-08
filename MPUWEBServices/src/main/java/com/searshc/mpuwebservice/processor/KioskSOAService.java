package com.searshc.mpuwebservice.processor;

import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.sears.mpu.backoffice.domain.IMAProduct;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.bean.SOAItemDTO;
import com.searshc.mpuwebservice.domain.TransactionDetailsResponse;
import com.searshc.mpuwebservice.domain.response.CheckoutResponse;
import com.searshc.mpuwebservice.domain.response.StoreLocationResponse;

/**
 * @author pgoyal
 * create date : 17-Oct-2014
 * Interface class.
 *
 */

public interface KioskSOAService{

	public List<OrderDTO> getOrder(String storeNumber,String orderId) throws DJException;

	public ItemDTO getItemDetail(String storeNumber,int itemId) throws DJException;

	public boolean isSOAEnabledOnStore(String storeNumber, String kioskName) throws DJException;

	public boolean getSalescheckNumber(String storeNumber,String rqt_id, String rqd_id) throws DJException;

	public SOAItemDTO getAccessoriesInfo(List<SOAItemDTO> selectedItemsList, String storeNumber, String storeFormat) throws DJException;

	//public void getPriceLookUpForAccessory(SOAItemDTO accessoriesItemDescription) throws DJException;

	public boolean getStatusForSYWRCustomer(SOAItemDTO soaItemDTO , String rqdId,String description) throws DJException;

	//For Accessory
	public IMAProduct getAccessoryInfo(String identifier, String identifierType) throws DJException;

	public SOAItemDTO getItemInfo(SOAItemDTO input) throws DJException;

	//for checkout response
	public CheckoutResponse getExpressCheckOutResponse(SOAItemDTO soaItem, String salesCheckNum,String transactionDate,String storeNum,
			String storeZipcode,String partNumbers,String quantities,String prices) throws DJException;

	public CheckoutResponse getexpressPayBill(String orderId,String totalAmount,String billingAddressId,String storeNum,String sessionKey,
			String cardData,String itemId ,String storeFormat) throws DJException;

	/*public String getPropertyFromAdaptor(String storeNumber, String propertyName) throws DJException;*/

	public SOAItemDTO getPrintReceiptResponse(SOAItemDTO soaItem, String orderID,String orderItemId,String totalAmount,String sessionKey ,String billingAddressId,String sellingStoreNumber,
			String cc_brand,String deviceId ,String emailAddress,String printerId ,String emailReceiptFlag,String state,String partNumbers,String itemId, String storeFormat) throws DJException;

	/*public TransactionDetailsResponse getOrderInfo(String orderId) throws DJException;*/

	public RequestDTO createItemAccMapping(SOAItemDTO itemDescription,String searchMethod,String searchValue ) throws DJException;

	public SOAItemDTO displayThankYouWithAccessories(SOAItemDTO soaItem) throws DJException;

	public SOAItemDTO expressCheckoutAccessory(SOAItemDTO soaItem) throws DJException;

	public SOAItemDTO expressPayBillAccessory( SOAItemDTO soaItem,String searchMethod,String searchValue) throws DJException;

	public SOAItemDTO expressPlaceOrderAccessory( SOAItemDTO soaItem,String searchMethod,String searchValue) throws DJException;
}
