package com.searshc.mpuwebservice.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.dao.DJCommonDAO;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.util.DJUtilities;
import com.sears.mpu.backoffice.domain.IMAProduct;
import com.sears.mpu.backoffice.domain.ProductInfoRequest;
import com.sears.mpu.backoffice.domain.ProductInfoResponse;
import com.searshc.mpuwebservice.bean.IdentifierDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.SOAItemDTO;
import com.searshc.mpuwebservice.dao.KioskSOADao;
import com.searshc.mpuwebservice.entity.McpOrigSCPurchasedSCMappingEntity;
import com.searshc.mpuwebservice.mapper.IdentifierMapper;
import com.searshc.mpuwebservice.mapper.OrderDTOMapper;
import com.searshc.mpuwebservice.mapper.SOAItemMapper;
import com.searshc.mpuwebservice.util.PropertyUtils;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;

/**
 * @author pgoyal
 * create date : 17-Oct-2014
 * DAO implementation class.
 *
 */

@Repository("kioskSOADaoImpl")
public class KioskSOADaoImpl extends DJMPUCommonDAO implements KioskSOADao{

	@Autowired
	SellingAccessoriesCommonDAO sellingAccessoriesCommonDAO;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateWFD;

	@Autowired
	RestTemplate restTemplate;


	//private static Logger logger = Logger.getLogger(KioskSOADaoImpl.class.getName());

	private static transient DJLogger logger = DJLoggerFactory.getLogger(KioskSOADaoImpl.class);

	public List<OrderDTO> getOrder(String storeNumber,String orderId) throws DJException{

		List<OrderDTO> listOrderDTO = null;

		String sql = PropertyUtils.getProperty("select.from.transtable");
		//String sql = "select * from request_queue_trans where rqt_id = :rqt_id ";
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("rqt_id", orderId);
		logger.info("KioskSOADaoImpl.getOrder","SQL : " + sql);
		logger.info("KioskSOADaoImpl.getOrder","rqt_id : " + orderId);
		listOrderDTO = query(DJUtilities.leftPadding(storeNumber, 5),sql,mapParam, new OrderDTOMapper());
		logger.info("KioskSOADaoImpl.getAccessoriesInfo","Exiting");
		return listOrderDTO;
	}

	public List<OrderDTO> getWorkDetailBySalesCheck(String storeNumber,String salesCheck) throws DJException{

		List<OrderDTO> listOrderDTO = null;

		String sql = PropertyUtils.getProperty("select.via.salescheck");
		//String sql = "select * from request_queue_trans where salescheck = :salescheck ";
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("salescheck", salesCheck);
		logger.info("KioskSOADaoImpl.getWorkDetailBySalesCheck","SalesCheck : " + salesCheck);
		logger.info("KioskSOADaoImpl.getWorkDetailBySalesCheck","SQL : "+ sql );
		listOrderDTO = query(DJUtilities.leftPadding(storeNumber, 5),sql,mapParam, new OrderDTOMapper());
		logger.info("KioskSOADaoImpl.getWorkDetailBySalesCheck","Exiting");
		return listOrderDTO;
	}

	public List<ItemDTO> getItem(String storeNumber,String rqt_Id , String rqd_Id) throws DJException{

		List<ItemDTO> listItemDTO = null;

		String sql = PropertyUtils.getProperty("selectitem.via.rqtrqdid");
		//String sql = "select * from request_queue_details where rqt_id = :rqt_Id and rqd_id = :rqd_Id";
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("rqt_Id", rqt_Id);
		mapParam.put("rqd_Id", rqd_Id);
		logger.info("KioskSOADaoImpl.getItem","rqt_id : "+ rqt_Id);
		logger.info("KioskSOADaoImpl.getItem","rqd_id :"+ rqd_Id);
		logger.info("KioskSOADaoImpl.getItem","SQL : "+sql);
		listItemDTO = query(DJUtilities.leftPadding(storeNumber, 5),sql,mapParam, new SOAItemMapper());
		logger.info("KioskSOADaoImpl.getItem","Exiting");
		return listItemDTO;
	}

	public List<ItemDTO> getItem( String storeNumber, String rqd_Id) throws DJException{
		List<ItemDTO> listItemDTO = null;
		String sql = PropertyUtils.getProperty("select.from.detailstable");
		//String sql = "select * from request_queue_details where rqd_id = :rqd_Id";
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("rqd_Id", rqd_Id);
		logger.info("KioskSOADaoImpl.getItem","rqd_Id : " + rqd_Id);
		logger.info("KioskSOADaoImpl.getItem","SQL : " + sql);
		listItemDTO = query(DJUtilities.leftPadding(storeNumber, 5),sql,mapParam, new SOAItemMapper());
		logger.info("KioskSOADaoImpl.getItem","Exiting");
		return listItemDTO;
	}

	public List<Map<String, Object>> getKioskEntityList(String storeNumber) throws DJException{

		String sql = PropertyUtils.getProperty("select.from.storekiosk");
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("storeNumber", storeNumber);

		//kioskDetailEntities = super.query(sql, mapParam, new KioskDetailEntityMapper());
		List<Map<String,Object>> kioskDetailList = null;

		logger.info("KioskSOADaoImpl.getKioskEntityList","storeNumber : " + storeNumber);
		logger.info("KioskSOADaoImpl.getKioskEntityList","SQL : "+ sql);
		kioskDetailList= namedParameterJdbcTemplateWFD.queryForList(sql, mapParam);
		logger.info("KioskSOADaoImpl.getKioskEntityList","Exiting");
			//return (List<KioskDetailDTO>) query(storeNo,sql, paramMap,new KioskDetailDTOMapper());
		return kioskDetailList;
	}

	public String getSalesCheckNumber(String storeNumber,String rqtId , String rqdId) throws DJException{

		String sql = PropertyUtils.getProperty("select.from.salescheck");

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("rqt_id", rqtId);
		logger.info("KioskSOADaoImpl.getSalesCheckNumber","rqt_id : " + rqtId);
		logger.info("KioskSOADaoImpl.getSalesCheckNumber","SQL : "+ sql);
		List<Map<String, Object>> listMap =  super.query(DJUtilities.leftPadding(storeNumber, 5),sql, mapParam, new ColumnMapRowMapper());
		logger.info("KioskSOADaoImpl.getSalesCheckNumber","Exiting");
		if(null != listMap && listMap.size() > 0){
			return listMap.get(0).get("salescheck").toString();
		}
		return null;
	}

	/* This method gets the list of selected items from the customer and returns the SOA item from our database.
	 * @param String, List<SOAItemDTO> ( base item list selected by the customer )
	 * @return List<SOAItemDTO> ( soa Item list from MPU database )
	 */
	public List<SOAItemDTO> getAccessoriesInfo(String storeNumber,List<SOAItemDTO> selectedItemsList) throws DJException{

		logger.info("KioskSOADaoImpl.getAccessoriesInfo","Entering");
		//logger.info("entering getAccessoriesInfo in SellingAccessoriesDAO");
		List<Integer> itemIdsList = new ArrayList<Integer>();
		List<SOAItemDTO> sellingAccessoriesItemList = null;
		String itemIds = "";
		int itemIdVar = 0;

		String query = PropertyUtils.getProperty("select.from.recommendeditems1");
		StringBuffer sql = new StringBuffer(query);

		// Step 1: fetch comma separated list of item numbers
		for (SOAItemDTO selectedItem : selectedItemsList) {

			// if (tempArrObj[tempArrObj.length - 1] instanceof Integer) {
			itemIdVar = (Integer.parseInt(selectedItem.getItemId()));

			// If List does not contain the work id then add to string
			if (!itemIdsList.contains(itemIdVar)) {
				if (itemIds.equals("")) {
					itemIds = "" + itemIdVar + "";
				} else {
					itemIds = itemIds + "," + itemIdVar + "";
				}

				// Add to list if it does not contain it
				itemIdsList.add(itemIdVar);
			}

			// }

		}

		sql.append(itemIds);
		sql.append(PropertyUtils.getProperty("select.from.recommendeditems2"));
		sql.append(itemIds);
		sql.append("))");

		logger.info("KioskSOADaoImpl.getAccessoriesInfo","query for getting soa Item :" + sql);
		logger.info("KioskSOADaoImpl.getAccessoriesInfo","List of base item's rqd_id :" + itemIdsList);
		if (null != itemIdsList  && !itemIdsList.isEmpty()) {

			// Step 2: Fetch data from DB
			Map<String, Object> mapParam = new HashMap<String, Object>();
			//List<Map<String, Object>> listMap =  super.query(sql.toString(), mapParam, new ColumnMapRowMapper());
			List<Map<String, Object>> listMap = (List<Map<String, Object>>)query(DJUtilities.leftPadding(storeNumber, 5),sql.toString(), mapParam,new ColumnMapRowMapper());

			logger.info("KioskSOADaoImpl.getAccessoriesInfo","List of soa item's  :" + listMap);
			//This list contains the recommended items
			sellingAccessoriesItemList = new ArrayList<SOAItemDTO>();

			// Step 3: Return list
			if (null != listMap  && listMap.size() > 0) {

				for (Map m : listMap) {

					if(((String)m.get("is_active")).equalsIgnoreCase("Y")){
						//This item is finally considered as proposed item
						SOAItemDTO temp = new SOAItemDTO();
						//temp.setItemId((String)m.get("rqd_id"));
						temp.setItemId(Long.toString((Long)m.get("rqd_id")));
						temp.setRecommItemDiv((String)m.get("recomm_item_div"));
						temp.setRecommItemNum((String)m.get("recomm_item_num"));
						temp.setRecommItemSku((String)m.get("recomm_item_sku"));
						temp.setRecommDescription((String)m.get("recomm_item_descr"));
						sellingAccessoriesItemList.add(temp);
					}

				}
			}

			// set value for recommended item(s)
			for (SOAItemDTO accessory : sellingAccessoriesItemList) {
				for (SOAItemDTO itemInput : selectedItemsList) {
					//main item rqd id == accessory item id
					if (itemInput.getItemId().equals(accessory.getItemId())) {
						// Match values here
						accessory.setWorkId(itemInput.getWorkId());
						accessory.setReqQuantity(itemInput.getReqQuantity());
						accessory.setMainOrderSalesCheck(itemInput.getMainOrderSalesCheck());
						accessory.setPrice(itemInput.getPrice());
					}
				}
			}
		}

		// Step 4 Set list of bean
		logger.info("getAccessoriesInfo","exiting getAccessoriesInfo in SellingAccessoriesDAO");
		return sellingAccessoriesItemList;
	}

	public ItemDTO getItemDetail(String storeNumber,int itemId) throws DJException {

		List<ItemDTO> listItemDTO = null;

		//hit the database
		String sql = PropertyUtils.getProperty("select.via.itemid");
		//String sql = "select * from request_queue_details where rqd_id  = :itemId";
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("itemId", itemId);
		logger.info("KioskSOADaoImpl.getItemDetail","itemId : "+ itemId);
		logger.info("KioskSOADaoImpl.getItemDetail","SQL : " + sql);
		listItemDTO = super.query(DJUtilities.leftPadding(storeNumber, 5),sql, mapParam, new SOAItemMapper());
		logger.info("KioskSOADaoImpl.getItemDetail","Exiting");
		return listItemDTO.get(0);
	}
	public SOAItemDTO getItemInfo(SOAItemDTO input) throws DJException {
		SOAItemDTO sellingAccessoriesItem = null;

		sellingAccessoriesItem = sellingAccessoriesCommonDAO
				.getPayLoadData(input);

		return sellingAccessoriesItem;
	}

	//For Accessory
	public IMAProduct getAccessoryInfo(String identifier, String identifierType) throws DJException {
		logger.info("getAccessoryInfo","Start :: getAccessoryInfo :: identifier : " + identifier+" identifierType: "+identifierType);
		String productInfoserviceUrl = "https://web.searshc.com/Lookup/productinfo/";
		String target = productInfoserviceUrl + identifierType + identifier;
		logger.info("getAccessoryInfo","Calling service and passing the following: " + target);
		// construct Request
		ProductInfoRequest aRequest = new ProductInfoRequest();
		// We use this to validate data against call
		aRequest.setIdentifier(identifier);
		aRequest.setIdentifierType(identifierType);

		// declare a response entity wrapping the type you want back from the rest call
		logger.info("KioskSOADaoImpl.getAccessoryInfo","<<RestTemplate>><<Begins>>URL GET : " + target);
		ProductInfoResponse productInfoResponse = restTemplate.getForObject(target, ProductInfoResponse.class);
		logger.info("KioskSOADaoImpl.getAccessoryInfo","<<RestTemplate>><<Success>>");
		logger.info("KioskSOADaoImpl.getAccessoryInfo","Exiting");
		return productInfoResponse.getProductInfo();

	}
	public IdentifierDTO getIdentifier(String storeNumber,final String rqt_Id,final String identifierDescription) throws DJException {

		List<IdentifierDTO> result = null;

		logger.info("KioskSOADaoImpl.getIdentifier","Entering");

		final String sql = PropertyUtils.getProperty("select.via.identifier");
		//final String sql = "select * from request_identifier where rqt_id = :rqt_id and type = " +
		//		"(select identifier_id from identifier_meta where identifier_type = :identifier_type)";

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("rqt_id", rqt_Id);
		mapParam.put("identifier_type", identifierDescription);

		logger.info("KioskSOADaoImpl.getIdentifier","rqt_id : " + rqt_Id);
		logger.info("KioskSOADaoImpl.getIdentifier","identifier_type : " + identifierDescription);
		logger.info("KioskSOADaoImpl.getIdentifier","SQL : "+ sql);

		try{
			result = super.query(DJUtilities.leftPadding(storeNumber, 5),sql, mapParam, new IdentifierMapper());
			return result.get(0);
		}catch(Exception e){

		}
		logger.info("KioskSOADaoImpl.getIdentifier","Exiting");
		return null;
	}

	public void insertMappingEntity(McpOrigSCPurchasedSCMappingEntity mappingEntity, String storeNumber) throws DJException{

		logger.info("KioskSOADaoImpl.insertMappingEntity","Entering");

		String sql = PropertyUtils.getProperty("insert.into.entitymapping");
		//insert into orig_purchased_item_mapping( orig_rqt_id, orig_item_id, accessory_rqt_id, accessory_item_id)
		//values (:orig_rqt_id,:orig_item_id,:accessory_rqt_id,:accessory_item_id);

		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("orig_rqt_id", String.valueOf(mappingEntity.getMcpOrigWorkId()));
		paramMap.put("orig_item_id", mappingEntity.getMcpOrigItemId());
		paramMap.put("accessory_rqt_id",String.valueOf( mappingEntity.getMcpAccessoryWorkId()));
		paramMap.put("accessory_item_id", mappingEntity.getMcpAccessoryItemId());

		logger.info("KioskSOADaoImpl.insertMappingEntity","orig_rqt_id : " + String.valueOf(mappingEntity.getMcpOrigWorkId()));
		logger.info("KioskSOADaoImpl.insertMappingEntity","orig_item_id : " + mappingEntity.getMcpOrigItemId());
		logger.info("KioskSOADaoImpl.insertMappingEntity","accessory_rqt_id : " + String.valueOf( mappingEntity.getMcpAccessoryWorkId()));
		logger.info("KioskSOADaoImpl.insertMappingEntity","accessory_item_id : " + mappingEntity.getMcpAccessoryItemId());
		logger.info("KioskSOADaoImpl.insertMappingEntity","SQL : " + sql);

		super.update(DJUtilities.leftPadding(storeNumber, 5),sql, paramMap);
		logger.info("KioskSOADaoImpl.insertMappingEntity","Exiting");


	}

}
