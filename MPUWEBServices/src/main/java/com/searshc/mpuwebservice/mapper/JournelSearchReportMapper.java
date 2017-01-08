package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.ReportResults;
import com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants;

public class JournelSearchReportMapper implements RowMapper<ReportResults>
{


	public ReportResults mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
        ReportResults reportResult = new ReportResults();
        reportResult.setFullName(rs.getString(MPUWebServiceColumnConstants.CUSTOMER_NAME.name()));
        reportResult.setRqtId(rs.getString(MPUWebServiceColumnConstants.RQT_ID.name()));
        reportResult.setCouponInd(rs.getString(MPUWebServiceColumnConstants.COUPONINDICATOR.name()));
        reportResult.setJournalDate(rs.getString(MPUWebServiceColumnConstants.MXTIME.name()));
        reportResult.setSalesCheck(rs.getString(MPUWebServiceColumnConstants.SALESCHECK.name()));
        reportResult.setOriginalSalesCheck(rs.getString(MPUWebServiceColumnConstants.ORIGINAL_SALESCHECK.name()));
        reportResult.setVehicleInd(rs.getString(MPUWebServiceColumnConstants.PICKUP_SOURCE.name()));
        reportResult.setRequestStatus(MPUWebServiceColumnConstants.REQUEST_STATUS.name());
        reportResult.setRequestType(rs.getString(MPUWebServiceColumnConstants.TYPE.name()));
       reportResult.setPhone(rs.getString(MPUWebServiceColumnConstants.PHONENO.name()));
       reportResult.setAssociate_id(rs.getString("associate_id"));
       reportResult.setTime(rs.getString("time"));
       reportResult.setEndTime(rs.getString("end_time"));
       
        return reportResult;
    }


}