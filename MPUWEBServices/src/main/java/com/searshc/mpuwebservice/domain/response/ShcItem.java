package com.searshc.mpuwebservice.domain.response;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({
"formattedUPC",
"formattedKSN",
"searsDivision",
"searsItem",
"searsSKU",
"sku991Plus4"
})
public class ShcItem {

	
	@JsonProperty("formattedKSN")
	private String formattedKSN;
	@JsonProperty("formattedUPC")
	private String formattedUPC;
	@JsonProperty("searsDivision")
	private String searsDivision;
	@JsonProperty("searsItem")
	private String searsItem;
	@JsonProperty("searsSKU")
	private String searsSKU;
	@JsonProperty("sku991Plus4")
	private String sku991Plus4;
	
	/**
	 * @return formattedKSN
	 */
	@JsonProperty("formattedKSN")
	public String getFormattedKSN() {
		return formattedKSN;
	}
	
	/**
	 * @param formattedKSN
	 */
	@JsonProperty("formattedKSN")
	public void setFormattedKSN(String formattedKSN) {
		this.formattedKSN = formattedKSN;
	}
	
	/**
	 * @return formattedUPC
	 */
	@JsonProperty("formattedUPC")
	public String getFormattedUPC() {
		return formattedUPC;
	}
	
	/**
	 * @param formattedUPC
	 */
	@JsonProperty("formattedUPC")
	public void setFormattedUPC(String formattedUPC) {
		this.formattedUPC = formattedUPC;
	}
	
	/**
	 * @return searsDivision
	 */
	@JsonProperty("searsDivision")
	public String getSearsDivision() {
		return searsDivision;
	}
	
	/**
	 * @param searsDivision
	 */
	@JsonProperty("searsDivision")
	public void setSearsDivision(String searsDivision) {
		this.searsDivision = searsDivision;
	}
	
	/**
	 * @return searsItem
	 */
	@JsonProperty("searsItem")
	public String getSearsItem() {
		return searsItem;
	}
	
	/**
	 * @param searsItem
	 */
	@JsonProperty("searsItem")
	public void setSearsItem(String searsItem) {
		this.searsItem = searsItem;
	}
	
	/**
	 * @return searsSKU
	 */
	@JsonProperty("searsSKU")
	public String getSearsSKU() {
		return searsSKU;
	}
	
	/**
	 * @param searsSKU
	 */
	@JsonProperty("searsSKU")
	public void setSearsSKU(String searsSKU) {
		this.searsSKU = searsSKU;
	}
	
	/**
	 * @return sku991Plus4
	 */ 
	@JsonProperty("sku991Plus4")
	public String getSku991Plus4() {
		return sku991Plus4;
	}
	
	/**
	 * @param sku991Plus4
	 */
	@JsonProperty("sku991Plus4")
	public void setSku991Plus4(String sku991Plus4) {
		this.sku991Plus4 = sku991Plus4;
	}

	
}
