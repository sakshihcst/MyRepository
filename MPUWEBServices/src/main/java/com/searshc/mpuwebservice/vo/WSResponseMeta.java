package com.searshc.mpuwebservice.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.searshc.mpuwebservice.vo.ContactDetails;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WSResponseMeta {

	private ContactDetails contactDetails;

	private String errorCode;

	private String errorMessage;

	private int recordCount;

	private String responseStatus;

	private long responseTimeMillis;

	private String serviceId;

	private String serviceVersion;

	public ContactDetails getContactDetails() {
		return contactDetails;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public long getResponseTimeMillis() {
		return responseTimeMillis;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setContactDetails(ContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public void setResponseTimeMillis(long responseTime) {
		this.responseTimeMillis = responseTime;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

}
