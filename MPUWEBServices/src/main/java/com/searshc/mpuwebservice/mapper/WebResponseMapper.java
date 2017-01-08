package com.searshc.mpuwebservice.mapper;


import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PERIOD_CODE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.BINNED_COUNT;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.NR_COUNT;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.OS_COUNT;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.WebResponseDTO;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;

public class WebResponseMapper implements RowMapper<WebResponseDTO> {
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(WebResponseMapper.class);
		
	public WebResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		WebResponseDTO webResponseDTO = new WebResponseDTO();
		webResponseDTO.setStoreNumber(MPUWebServiceUtil.getFormattedStoreNo(rs.getString(STORE_NUMBER.name())));
		webResponseDTO.setAssociateId(MPUWebServiceUtil.getFormattedAssociateId(rs.getString(ASSOCIATE_ID.name())));
		webResponseDTO.setPeriod(rs.getString(PERIOD_CODE.name()));
		webResponseDTO.setBinnedCount(rs.getInt(BINNED_COUNT.name()));
		webResponseDTO.setOutOfStockCount(rs.getInt(OS_COUNT.name()));
		webResponseDTO.setNoResponseCount(rs.getInt(NR_COUNT.name()));
		return webResponseDTO;
		
	}


}
