package com.searshc.mpuwebservice.vo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ItemMasterResponse {

	private List<ItemMeta> itemDetailsList;

	private WSResponseMeta wsResponseMeta;
	
	public List<ItemMeta> getItemDetailsList() {
		if(itemDetailsList==null){
			itemDetailsList = new ArrayList<ItemMeta>();
		}
		return itemDetailsList;
	}
	
	public WSResponseMeta getWsResponeMeta() {
		return wsResponseMeta;
	}

	public void setWsResponeMeta(WSResponseMeta wsResponeMeta) {
		this.wsResponseMeta = wsResponeMeta;
	}
	

}
