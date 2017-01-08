package com.searshc.targetinteraction;

import java.io.Serializable;
import com.searshc.targetinteraction.ItemOrderConfirmDTO;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrderConfirmRequestDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 955585774014360247L;
	private String paymentType;
	private java.util.List<ItemOrderConfirmDTO> items;
	private String maxMemberOfferCount;	
	private String channel;
	private double orderTotal;
	private String maxOfferCount;
	private String orderId;
	private String userId;
	private String email;
	private String client;
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public java.util.List<ItemOrderConfirmDTO> getItems() {
		return items;
	}
	public void setItems(java.util.List<ItemOrderConfirmDTO> items) {
		this.items = items;
	}
	public String getMaxMemberOfferCount() {
		return maxMemberOfferCount;
	}
	public void setMaxMemberOfferCount(String maxMemberOfferCount) {
		this.maxMemberOfferCount = maxMemberOfferCount;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getMaxOfferCount() {
		return maxOfferCount;
	}
	public void setMaxOfferCount(String maxOfferCount) {
		this.maxOfferCount = maxOfferCount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	@Override
	public String toString() {
		return "OrderConfirmRequestDTO [paymentType=" + paymentType
				+ ", items=" + items + ", maxMemberOfferCount="
				+ maxMemberOfferCount + ", channel=" + channel
				+ ", orderTotal=" + orderTotal + ", maxOfferCount="
				+ maxOfferCount + ", orderId=" + orderId + ", userId=" + userId
				+ ", email=" + email + ", client=" + client + "]";
	}
	
	
}
