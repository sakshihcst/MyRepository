package com.searshc.mpuwebservice.processor.impl;

import com.sears.dj.common.exception.DJException;

public interface MpuWebDlvryStatusService {

	
	public void sendMpuInStorePurchaseNotification(String sywrId,String store,String salescheck) throws DJException;
}
