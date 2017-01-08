package com.searshc.mpuwebservice.processor;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.ProcessReturnIn5Request;
import com.searshc.mpuwebservice.bean.ProcessReturnIn5Response;
import com.searshc.mpuwebservice.bean.MpuPickUpReportResposne;
import com.searshc.mpuwebservice.bean.ShopInReportDTO;
import com.searshc.mpuwebservice.bean.ShopinRequestDTO;

public interface ShopInServiceProcessor {

	public MpuPickUpReportResposne insertRecordForShopInReport(
			ShopinRequestDTO shopinRequestDTO) throws DJException;

	public MpuPickUpReportResposne updateShopInReport(String storeNo, String salesCheckNumber,
			String pickedUpQty, String status, String customerName,
			String associateName, String pickupEndTime) throws DJException;

	public List<ShopInReportDTO> fetchRecordsForShopinReport(String dateFrom,
			String dateTo, String storeNo, String region, String district)
			throws DJException;

	public ProcessReturnIn5Response getResponseForRI5Shopin(
			ProcessReturnIn5Request processReturnIn5Request) throws DJException;

	public void pushNotificationToShopIn(String rqtId, String salesCheck,
			String orderStatus, String searsSalesId, String associateName,
			String timeTakenForPickup,String storeNumber)throws DJException;
}
