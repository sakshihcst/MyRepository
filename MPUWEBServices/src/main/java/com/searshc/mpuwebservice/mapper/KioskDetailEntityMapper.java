package com.searshc.mpuwebservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.mapper.DJRowMapper;
import com.searshc.mpuwebservice.constant.SOAConstants;
import com.searshc.mpuwebservice.entity.KioskDetailEntity;

/**
 * This is the mapper class used to map the Item list object with the item list type of data returned from database
 * @author TCS
 * @version 1.0
 */

public class KioskDetailEntityMapper implements DJRowMapper<KioskDetailEntity> {

	
	/**
	 * maps the ResultSet row with the item list object
	 * @return Item Detail
	 * @param resultset, row number
	 * @author TCS
	 * @throws DJException
	 */
	
	public KioskDetailEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
		KioskDetailEntity kioskDetailEntity = new KioskDetailEntity();
		kioskDetailEntity.setHoldGoFlag(rs.getInt(SOAConstants.HOLD_GO_FLAG));
		kioskDetailEntity.setKiosk(rs.getString(SOAConstants.KIOSK_NAME));
		kioskDetailEntity.setLockerEnabled(rs.getString(SOAConstants.LOCKER_ENABLED).charAt(0));
		kioskDetailEntity.setModEnabled(rs.getString(SOAConstants.MOD_ENABLED).charAt(0));
		kioskDetailEntity.setSellOfAccFlag(rs.getInt(SOAConstants.SELL_OF_ACC_FLAG));
		kioskDetailEntity.setStoreNo(rs.getString(SOAConstants.STORE_NUMBER));
		kioskDetailEntity.setSboPrinterFlag(rs.getString(SOAConstants.SBO_PRINTER_FLAG).charAt(0));
		return kioskDetailEntity;
	}
	
	
}
