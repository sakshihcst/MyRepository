package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ActivityUserEntity;
import com.searshc.mpuwebservice.bean.MpuReportDetailVO;

public class MpuReportDetailVOMapper implements RowMapper<MpuReportDetailVO> {
	
	public MpuReportDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MpuReportDetailVO mpuReportDetail = new MpuReportDetailVO();
		
		mpuReportDetail.setPickupId(rs.getInt("trans_id"));
		mpuReportDetail.setStoreNo(rs.getString("store_number"));
		mpuReportDetail.setPickedUpDate(rs.getString("end_time"));
		mpuReportDetail.setSalescheck(rs.getString("original_salescheck"));
		mpuReportDetail.setPickedupQuantity(rs.getString("delivered_qty"));
		mpuReportDetail.setCompleteTime(rs.getString("elapsed_time"));
		mpuReportDetail.setStatus(rs.getString("request_status"));
		mpuReportDetail.setCustomerName(rs.getString("customer_name"));
		mpuReportDetail.setAssociateName(rs.getString("associate_id"));
		mpuReportDetail.setStartDate(rs.getString("start_time"));
		mpuReportDetail.setEndDate(rs.getString("end_time"));

		return mpuReportDetail;
	}
}
