package com.searshc.targetinteraction;
import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ItemHazmatDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1264100752360718853L;
	
	private String usDotProperShippingName;
	private String dataChangeFlag;
	private String hazardStorageIndicator;
	private double retailGtin;
	private String usDotShipType;
	public String getUsDotProperShippingName() {
		return usDotProperShippingName;
	}
	public void setUsDotProperShippingName(String usDotProperShippingName) {
		this.usDotProperShippingName = usDotProperShippingName;
	}
	public String getDataChangeFlag() {
		return dataChangeFlag;
	}
	public void setDataChangeFlag(String dataChangeFlag) {
		this.dataChangeFlag = dataChangeFlag;
	}
	public String getHazardStorageIndicator() {
		return hazardStorageIndicator;
	}
	public void setHazardStorageIndicator(String hazardStorageIndicator) {
		this.hazardStorageIndicator = hazardStorageIndicator;
	}
	public double getRetailGtin() {
		return retailGtin;
	}
	public void setRetailGtin(double retailGtin) {
		this.retailGtin = retailGtin;
	}
	public String getUsDotShipType() {
		return usDotShipType;
	}
	public void setUsDotShipType(String usDotShipType) {
		this.usDotShipType = usDotShipType;
	}
	
	
}
