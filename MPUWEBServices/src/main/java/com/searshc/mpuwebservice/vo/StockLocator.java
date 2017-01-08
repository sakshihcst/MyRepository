package com.searshc.mpuwebservice.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class StockLocator implements Comparable<StockLocator> {
	
	private String locationId;
	
	private int qty;
		
	public String getLocationId() {
		return locationId;
	}

	public int getQty() {
		return qty;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public int compareTo(StockLocator compareStockLocator) {
		 
		int compareQuantity = ((StockLocator) compareStockLocator).getQty(); 
 
		//ascending order
		//return this.qty - compareQuantity;
 
		//descending order
		return compareQuantity - this.qty;
 
	}
}
