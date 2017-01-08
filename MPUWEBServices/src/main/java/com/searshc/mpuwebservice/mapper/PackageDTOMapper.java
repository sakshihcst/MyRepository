package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSIGNED_USER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATE_TIMESTAMP;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.FROM_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PACKAGE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TO_LOCATION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.PACKAGE_ID;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sears.dj.common.mapper.DJRowMapper;
import com.searshc.mpuwebservice.bean.PackageDTO;

public class PackageDTOMapper implements DJRowMapper<PackageDTO> {
	public PackageDTO mapRow(ResultSet rs, int rowNum)
			throws SQLException{
		PackageDTO packageDTO = new PackageDTO();
		packageDTO.setAssignedUser(rs.getString(ASSIGNED_USER.name()));
		packageDTO.setCreatedBy(rs.getString(CREATED_BY.name()));
		packageDTO.setCreateTimestamp(rs.getString(CREATE_TIMESTAMP.name()));
		packageDTO.setFromLocation(rs.getString(FROM_LOCATION.name()));
		packageDTO.setPackageNumber(rs.getString(PACKAGE_NUMBER.name()));
		packageDTO.setRqtId(rs.getString(RQT_ID.name()));
		packageDTO.setStoreNumber(rs.getString(STORE_NUMBER.name()));
		packageDTO.setToLocation(rs.getString(TO_LOCATION.name()));
		packageDTO.setPackageId(rs.getInt(PACKAGE_ID.name()));
		packageDTO.setPackageCount(rowNum+1);
		return packageDTO;
	}
}
