package com.searshc.mpuwebservice.dao;

import java.util.List;
import java.util.Map;

import com.sears.dj.common.exception.DJException;
import com.sears.mpu.backoffice.domain.IMAProduct;
import com.searshc.mpuwebservice.bean.IdentifierDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.SOAItemDTO;
import com.searshc.mpuwebservice.entity.McpOrigSCPurchasedSCMappingEntity;

public interface KioskSOADao{
	
	
	public List<OrderDTO> getOrder(String storeNumber,String orderId) throws DJException;
	
	public List<OrderDTO> getWorkDetailBySalesCheck(String storeNumber,String salesCheck) throws DJException;
	
	public List<ItemDTO> getItem(String storeNumber,String rqt_Id , String rqd_Id) throws DJException;
	
	public List<ItemDTO> getItem(String storeNumber, String rqd_Id) throws DJException ;
	
	public List<Map<String, Object>> getKioskEntityList(String storeNumber) throws DJException;
	
	public String getSalesCheckNumber(String storeNumber,String rqtId , String rqdId) throws DJException;
	
	public List<SOAItemDTO> getAccessoriesInfo(String storeNumber,List<SOAItemDTO> selectedItemsList) throws DJException ;
	
	public ItemDTO getItemDetail(String storeNumber,int itemId) throws DJException;
	
	public SOAItemDTO getItemInfo(SOAItemDTO input) throws DJException ;
	
	public IMAProduct getAccessoryInfo(String identifier, String identifierType) throws DJException;	
	
	public IdentifierDTO getIdentifier(String storeNumber,final String rqt_Id,final String identifierDescription) throws DJException;
	
	public void insertMappingEntity(McpOrigSCPurchasedSCMappingEntity mappingEntity, String storeNumber) throws DJException;
	
}
