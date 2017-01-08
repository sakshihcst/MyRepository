package com.searshc.targetinteraction;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SBOServiceResponse implements Serializable {		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1686675720789126555L;

	private List<ItemDetailsDTO> itemDetailsList;
	
	private WebServiceResponseMeta wsResponeMeta;
	
	public List<ItemDetailsDTO> getItemDetailsList() {
		return itemDetailsList;
	}
	public void setItemDetailsList(List<ItemDetailsDTO> itemDetailsList) {
		this.itemDetailsList = itemDetailsList;
	}
	public WebServiceResponseMeta getWsResponeMeta() {
		return wsResponeMeta;
	}
	public void setWsResponeMeta(WebServiceResponseMeta wsResponeMeta) {
		this.wsResponeMeta = wsResponeMeta;
	}

}
