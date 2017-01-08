package com.searshc.targetinteraction;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class StockLocatorDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8304913046690148249L;
	private String locationId;
	private int qty;
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
}
