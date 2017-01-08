package com.searshc.targetinteraction;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ItemOrderConfirmDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7154894525841005952L;
	private String category1;
	private String category2;
	private String fmStoreId;
	private String category3;
	private String price;
	private int quantity;
	private String fmChannel;
	private String partNumber;
	
	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	public String getFmStoreId() {
		return fmStoreId;
	}
	public void setFmStoreId(String fmStoreId) {
		this.fmStoreId = fmStoreId;
	}
	public String getCategory3() {
		return category3;
	}
	public void setCategory3(String category3) {
		this.category3 = category3;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getFmChannel() {
		return fmChannel;
	}
	public void setFmChannel(String fmChannel) {
		this.fmChannel = fmChannel;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	@Override
	public String toString() {
		return "ItemOrderConfirmDTO [category1=" + category1 + ", category2="
				+ category2 + ", fmStoreId=" + fmStoreId + ", category3="
				+ category3 + ", price=" + price + ", quantity=" + quantity
				+ ", fmChannel=" + fmChannel + ", partNumber=" + partNumber
				+ "]";
	}
	
}
