package com.searshc.mpuwebservice.domain.response;


import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({
"stockLocatorResponseHeader",
"storeNum",
"storeFormat",
"shcItem",
"locationList"
})
public class StoreLocationResponse {

	@JsonProperty("stockLocatorResponseHeader")
	private  StockLocatorResponseHeader stockLocatorResponseHeader;
	
	@JsonProperty("storeFormat")
	private String storeFormat;
	
	@JsonProperty("storeNum")
	private String storeNum;
	
	@JsonProperty("shcItem")
	private ShcItem shcItem;
	
	@JsonProperty("locationList")
	private Locationlist locationList= new Locationlist();

	
	/**
	 * @return stockLocatorResponseHeader
	 */
	@JsonProperty("stockLocatorResponseHeader")
	public StockLocatorResponseHeader getStockLocatorResponseHeader() {
		return stockLocatorResponseHeader;
	}

	
	/**
	 * @param stockLocatorResponseHeader
	 */
	@JsonProperty("stockLocatorResponseHeader")
	public void setStockLocatorResponseHeader(
			StockLocatorResponseHeader stockLocatorResponseHeader) {
		this.stockLocatorResponseHeader = stockLocatorResponseHeader;
	}

	
	
	/**
	 * @return storeFormat
	 */
	@JsonProperty("storeFormat")
	public String getStoreFormat() {
		return storeFormat;
	}

	
	/**
	 * @param storeFormat
	 */
	@JsonProperty("storeFormat")
	public void setStoreFormat(String storeFormat) {
		this.storeFormat = storeFormat;
	}

	
	/**
	 * @return storeNum
	 */
	@JsonProperty("storeNum")
	public String getStoreNum() {
		return storeNum;
	}

	/**
	 * @param storeNum
	 */
	@JsonProperty("storeNum")
	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}

	/**
	 * @return shcItem
	 */
	@JsonProperty("shcItem")
	public ShcItem getShcItem() {
		return shcItem;
	}

	/**
	 * @param shcItem
	 */
	@JsonProperty("shcItem")
	public void setShcItem(ShcItem shcItem) {
		this.shcItem = shcItem;
	}

	/**
	 * @return locationList
	 */
	@JsonProperty("locationList")
	public Locationlist getLocationList() {
		return locationList;
	}

	/**
	 * @param locationList
	 */
	@JsonProperty("locationList")
	public void setLocationList(Locationlist locationList) {
		this.locationList = locationList;
	}

	
	
}
