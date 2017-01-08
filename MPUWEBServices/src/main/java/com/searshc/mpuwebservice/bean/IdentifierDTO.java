package com.searshc.mpuwebservice.bean;

import java.io.Serializable;

public class IdentifierDTO implements Serializable{
/**
	 * 
	 */
private static final long serialVersionUID = -6203392569525801069L;
private Integer rqtId;
private String value;
private String type;
private String storeNumber;
public Integer getRqtId() {
	return rqtId;
}
public void setRqtId(Integer rqtId) {
	this.rqtId = rqtId;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getStoreNumber() {
	return storeNumber;
}
public void setStoreNumber(String storeNumber) {
	this.storeNumber = storeNumber;
}
@Override
public String toString() {
	return "IdentifierDTO [rqtId=" + rqtId + ", value=" + value + ", type="
			+ type + ", storeNumber=" + storeNumber + "]";
}

}
