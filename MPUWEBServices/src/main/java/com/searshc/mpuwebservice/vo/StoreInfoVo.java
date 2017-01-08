package com.searshc.mpuwebservice.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;



@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class StoreInfoVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StoreVO storeInfo;

	private StoreworkinhHours storeWorkingHrs;	
	
	public StoreVO getStoreInfo() {
		return storeInfo;
	}
	public StoreworkinhHours getStoreWorkingHrs() {
		return storeWorkingHrs;
	}
	public void setStoreInfo(StoreVO storeInfo) {
		this.storeInfo = storeInfo;
	}
	public void setStoreWorkingHrs(StoreworkinhHours storeWorkingHrs) {
		this.storeWorkingHrs = storeWorkingHrs;
	}
	
}
