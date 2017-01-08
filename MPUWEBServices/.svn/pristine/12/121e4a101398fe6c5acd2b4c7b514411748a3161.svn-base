package com.searshc.mpuwebservice.processor.impl;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.SOAItemDTO;
import com.searshc.mpuwebservice.domain.PrintReceiptInfo;
import com.searshc.mpuwebservice.domain.TransactionDetailsResponse;
import com.searshc.mpuwebservice.domain.response.CheckoutResponse;
import com.searshc.mpuwebservice.domain.response.StoreLocationResponse;

/**
 * This class is added for Automation testing of SOA 
 * We have used store 01333 for redirection
 * @author 829809
 *
 */

public interface SOAThirdPartyAPIHelper {

	public StoreLocationResponse getStorelocation(String division,String itemNumber,String skunumber,String storeNumber,String storeFormat) throws DJException;


	/**The method is used to get the marginalPrice and cost of the
	 * recommended accessory from the PLU service.
	 * @param SOAItemDTO
	 * @return void
	 */
	public void getPriceLookUpForAccessory(SOAItemDTO accessoriesItemDescription)  throws DJException;
	
	public CheckoutResponse thirdPartyCheckOutCall( String storeNumber, String storeFormat) throws DJException;
	
	public CheckoutResponse thirdPartyBillPayCall(String storeNumber, String storeFormat) throws DJException;
	
	public String getPropertyFromAdaptor(String storeNumber, String propertyName)  throws DJException;
	
	public PrintReceiptInfo thirdPartyPlaceOrderCall(String storeNumber, String storeFormat) throws DJException;
	
	public SOAItemDTO getOrderInfo(SOAItemDTO soaItemDTO, String orderId, String storeNumber, String storeFormat) throws DJException;

}
