package com.searshc.mpuwebservice.vo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StoreFinderResponsev2 {

	private List<StoreInfoVo> storeDetailsList;

	private WSResponseMeta wsResponeMeta;

	public List<StoreInfoVo> getStoreDetailsList() {
		if (storeDetailsList == null) {
			storeDetailsList = new ArrayList<StoreInfoVo>();
		}
		return storeDetailsList;
	}

	public WSResponseMeta getWsResponeMeta() {
		return wsResponeMeta;
	}

	public void setWsResponeMeta(WSResponseMeta wsResponeMeta) {
		this.wsResponeMeta = wsResponeMeta;
	}

}
