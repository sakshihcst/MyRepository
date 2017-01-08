package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.searshc.mpuwebservice.bean.PackageDTO;

public class PackageInfoMapper implements RowMapper<PackageDTO>{

	public PackageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PackageDTO packagedto = new PackageDTO();
		
		packagedto.setSalescheck(rs.getString("work_order_identifier"));
		packagedto.setPackageNumber(rs.getString("package_number"));
		packagedto.setCreatedBy(rs.getString("created_by"));
		packagedto.setStoreNumber(rs.getString("store_no"));
		packagedto.setToLocation(rs.getString("layaway_bin"));
		
		return packagedto;
	}
	
	

}
