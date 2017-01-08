package com.searshc.mpuwebservice.mapper;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.DELIVERED_QUANTITY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ITEM_IDENTIFIER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.ASSOCIATE_ID;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ItemDTO;

public class ItemQuantityAndLdapIdMapper  implements RowMapper<ItemDTO>{
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(ItemQuantityAndLdapIdMapper.class);
	
	public ItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemDTO itemdto = new ItemDTO();
		
		itemdto.setItemQuantityActual(String.valueOf(rs.getInt(DELIVERED_QUANTITY.name())));
		itemdto.setAssociateID(rs.getString(ASSOCIATE_ID.name()));
		itemdto.setItemIdentifiers(String.valueOf(rs.getString(ITEM_IDENTIFIER.name())));
		
		/*more can be added*/
		
		return itemdto;
	}

}
