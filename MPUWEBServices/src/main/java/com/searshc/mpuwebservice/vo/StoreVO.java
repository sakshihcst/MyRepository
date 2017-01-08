package com.searshc.mpuwebservice.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class StoreVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String address;
	String city;
	String county;
	String district;
	String openDate;
	String orgUnitId;
	String phNbr;
	String region;
	String state;
	String timeZone;
	String unitId;
	String unitName;
	String unitSubType;
	String unitType;
	String zip;
	
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getCounty() {
		return county;
	}
	public String getDistrict() {
		return district;
	}
	public String getOpenDate() {
		return openDate;
	}
	public String getOrgUnitId() {
		return orgUnitId;
	}
	public String getPhNbr() {
		return phNbr;
	}
	public String getRegion() {
		return region;
	}
	public String getState() {
		return state;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public String getUnitId() {
		return unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public String getUnitSubType() {
		return unitSubType;
	}
	public String getUnitType() {
		return unitType;
	}
	public String getZip() {
		return zip;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public void setOrgUnitId(String orgUnitId) {
		this.orgUnitId = orgUnitId;
	}
	public void setPhNbr(String phNbr) {
		this.phNbr = phNbr;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public void setUnitSubType(String unitSubType) {
		this.unitSubType = unitSubType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
