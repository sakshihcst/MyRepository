package com.searshc.mpuwebservice.dao;

import java.util.HashMap;
import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.NotDeliverReportDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;

public interface NotDeliverServiceDAO {

	public List<NotDeliverReportDTO> getNotDeliverReportRecords(String dateFrom, String dateTo, String storeNo) throws DJException;
	
	public OrderDTO getOrderDetailsByRqdId(String storeNumber,String rqd_Id) throws DJException;
	
	/**
	 * @description This method is used to get ItemDTO to update DEJ Database for Not Delivered Report
	 * @param storeNumber String
	 * @param rqd_Id String
	 * @return ItemDTO
	 * @throws DJException
	 */
	public ItemDTO getItemDetailsByRqdId(String storeNumber,String rqd_Id) throws DJException;
	
	public int insertNotDeliverReasonCodeToDejService(HashMap<String, String> map) throws DJException;
	
	
}
