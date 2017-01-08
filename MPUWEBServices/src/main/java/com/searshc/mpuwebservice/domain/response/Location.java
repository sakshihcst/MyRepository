package com.searshc.mpuwebservice.domain.response;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;


@JsonPropertyOrder({
"areaCode",
"areaNumber",
"sectionID",
"rowID",
"locationBarcode",
"backroomQty",
"areaBarcode"
})
public class Location {

	@JsonProperty("areaBarcode")
	private String areaBarcode;
	@JsonProperty("areaCode")
	private String areaCode;
	@JsonProperty("areaNumber")
	private String areaNumber;
	@JsonProperty("backroomQty")
	private String backroomQty;
	@JsonProperty("locationBarcode")
	private String locationBarcode;
	@JsonProperty("rowID")
	private String rowID;
	@JsonProperty("sectionID")
	private String sectionID;
	
	
	/**
	 * @return areaBarcode
	 */
	@JsonProperty("areaBarcode")
	public String getAreaBarcode() {
		return areaBarcode;
	}
	
	
	/**
	 * @param areaBarcode
	 */
	@JsonProperty("areaBarcode")
	public void setAreaBarcode(String areaBarcode) {
		this.areaBarcode = areaBarcode;
	}
	
	
	/**
	 * @return areaCode
	 */
	@JsonProperty("areaCode")
	public String getAreaCode() {
		return areaCode;
	}
	
	
	/**
	 * @param areaCode
	 */
	@JsonProperty("areaCode")
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
	/**
	 * @return areaNumber
	 */
	@JsonProperty("areaNumber")
	public String getAreaNumber() {
		return areaNumber;
	}
	
	/**
	 * @param areaNumber
	 */
	@JsonProperty("areaNumber")
	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}
	
	/**
	 * @return backroomQty
	 */
	@JsonProperty("backroomQty")
	public String getBackroomQty() {
		return backroomQty;
	}
	
	/**
	 * @param backroomQty
	 */
	@JsonProperty("backroomQty")
	public void setBackroomQty(String backroomQty) {
		this.backroomQty = backroomQty;
	}
	
	/**
	 * @return locationBarcode
	 */
	@JsonProperty("locationBarcode")
	public String getLocationBarcode() {
		return locationBarcode;
	}
	
	/**
	 * @param locationBarcode
	 */
	@JsonProperty("locationBarcode")
	public void setLocationBarcode(String locationBarcode) {
		this.locationBarcode = locationBarcode;
	}
	
	/**
	 * @return rowID
	 */
	@JsonProperty("rowID")
	public String getRowID() {
		return rowID;
	}
	
	/**
	 * @param rowID
	 */
	@JsonProperty("rowID")
	public void setRowID(String rowID) {
		this.rowID = rowID;
	}
	
	/**
	 * @return sectionID
	 */
	@JsonProperty("sectionID")
	public String getSectionID() {
		return sectionID;
	}
	
	/**
	 * @param sectionID
	 */
	@JsonProperty("sectionID")
	public void setSectionID(String sectionID) {
		this.sectionID = sectionID;
	}
	
}
