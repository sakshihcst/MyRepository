package com.searshc.targetinteraction;

import java.io.Serializable;

public class TIRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6576496646406971511L;
	private String divItemSku;		
	private String storeNumber;
	private String itemIdType;
	private String sywrNumber;
	private String paymentType;
	private String quantity;
	private String orderId;
	private String storeFormat;
	private String orderTotal;
	private String emailId;
	private String fmChannel;

	public String getDivItemSku() {
		return divItemSku;
	}
	public void setDivItemSku(String divItemSku) {
		this.divItemSku = divItemSku;
	}
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	public String getItemIdType() {
		return itemIdType;
	}
	public void setItemIdType(String itemIdType) {
		this.itemIdType = itemIdType;
	}
	public String getSywrNumber() {
		return sywrNumber;
	}
	public void setSywrNumber(String sywrNumber) {
		this.sywrNumber = sywrNumber;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStoreFormat() {
		return storeFormat;
	}
	public void setStoreFormat(String storeFormat) {
		this.storeFormat = storeFormat;
	}
	public String getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(String orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFmChannel() {
		return fmChannel;
	}
	public void setFmChannel(String fmChannel) {
		this.fmChannel = fmChannel;
	}
}
