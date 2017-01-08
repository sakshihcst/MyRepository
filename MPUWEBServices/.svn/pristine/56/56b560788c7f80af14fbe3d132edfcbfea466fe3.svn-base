package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_IMAGE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ItemDTO;

public class ItemMapper  implements RowMapper<ItemDTO>{
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(ItemMapper.class);
	
	public ItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemDTO itemdto = new ItemDTO();
		
		itemdto.setRqdId(String.valueOf(rs.getInt(RQD_ID.name())));
		itemdto.setRqtId(String.valueOf(rs.getInt(RQT_ID.name())));
		itemdto.setItemStatus(rs.getString(ITEM_IMAGE.name()));
		
		/*more can be added*/
		
		return itemdto;
	}

}
