package com.searshc.mpuwebservice.domain.response;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * @author 586428
 *
 */
/**
 * @author 586428
 *
 */
/**
 * @author 586428
 *
 */
/**
 * @author 586428
 *
 */
/**
 * @author 586428
 *
 */
@JsonPropertyOrder({
"status",
"respCode",
"respDesc"
})
public class StockLocatorResponseHeader {

	@JsonProperty("respCode")
	private String respCode;
	@JsonProperty("respDesc")
	private String respDesc;
	@JsonProperty("status")
	private String status;
	
	/**
	 * @return respCode
	 */
	@JsonProperty("respCode")
	public String getRespCode() {
		return respCode;
	}
	/**
	 * @param respCode
	 */
	@JsonProperty("respCode")
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	
	/**
	 * @return respDesc
	 */
	@JsonProperty("respDesc")
	public String getRespDesc() {
		return respDesc;
	}
	
	
	/**
	 * @param respDesc
	 */
	@JsonProperty("respDesc")
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	
	/**
	 * @return status
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}
	
	
	/**
	 * @param status
	 */
	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	
}
