package com.searshc.mpuwebservice.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PrintServiceResponse {
	
	private String status;
	
	public String getPrintServiceStatus() {
		return status;
	}

	public void setPrintServiceStatus(String status) {
		this.status = status;
	}
	
	private WSResponseMeta wsResponseMeta;

	
	public WSResponseMeta getWsResponeMeta() {
		return wsResponseMeta;
	}

	public void setWsResponeMeta(WSResponseMeta wsResponeMeta) {
		this.wsResponseMeta = wsResponeMeta;
	}
	

}
