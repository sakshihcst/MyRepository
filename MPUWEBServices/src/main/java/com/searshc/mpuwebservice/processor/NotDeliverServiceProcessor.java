package com.searshc.mpuwebservice.processor;

import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.NotDeliverReportDTO;

public interface NotDeliverServiceProcessor {

	public List<NotDeliverReportDTO> fetchRecordsForNotDeliverReport(String dateFrom,String dateTo,String storeNo) throws DJException;
	
	public int insertNotDeliverReasonCodeToDejService(String notDeliverReason,String rqdId, String storeNumber, String requestedQty, String deliverQty) throws DJException;
}
