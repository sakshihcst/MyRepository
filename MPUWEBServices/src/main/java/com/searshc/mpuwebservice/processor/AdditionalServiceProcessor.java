package com.searshc.mpuwebservice.processor;

import com.sears.dj.common.exception.DJException;

public interface AdditionalServiceProcessor {
	public void sendFinalResponseManual(String[] salescheckList,String dbNum) throws DJException;
}
