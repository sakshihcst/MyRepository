package com.searshc.mpuwebservice.dao.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sears.dj.common.dao.DJCommonDAO;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.sears.dj.common.util.DJUtilities;
import com.searshc.mpuwebservice.bean.OrderDTO;
import com.searshc.mpuwebservice.bean.SOAItemDTO;
import com.searshc.mpuwebservice.mapper.OrderDTOMapper;



@Repository
public class SellingAccessoriesCommonDAO extends DJMPUCommonDAO {
	
	private static Logger logger = Logger
			.getLogger(SellingAccessoriesCommonDAO.class);
	
	public List<OrderDTO> getPayLoadList(String storeNumber,final String rqt_Id)
			throws DJException {
		
		logger.info("getPayLoadList  started");
		
		List<OrderDTO> listOrderDTO = null;
		
		final String queryString = "SELECT * FROM request_queue_trans trans " +
				" where trans.rqt_id in ("+rqt_Id+")";
		
		listOrderDTO = query(DJUtilities.leftPadding(storeNumber, 5),queryString, new OrderDTOMapper());
		
		logger.info("getPayLoadList  ended");
		return (List<OrderDTO>) listOrderDTO;
	}
	
	public SOAItemDTO getPayLoadData(
			SOAItemDTO input) throws DJException {
		// Logging start of method
		logger.info("getPayLoadData  started");

		JSONObject json = null;

		JSONArray itemsArr = null;

		JSONObject item = null;

		String payLoad = null;

		OrderDTO orderDTO = null;

		OrderDTO tempOrderDTO = null;

		SOAItemDTO sellingAccessoriesItem = null;

		// String itemPrice = null;

		// Code changes for performance
		String rqt_Id = null;


		// To Handle json null array objects
		boolean isValidJSON = true;
		boolean isValidItemsArray = true;


		sellingAccessoriesItem = input;
		//fetching the order ID 
		rqt_Id = input.getWorkId();

		// Step #1: Fetch Payload for rqt_Id
		if (!rqt_Id.equals("")) {

			String orderId = "'" + rqt_Id + "'";
			tempOrderDTO = (OrderDTO) getPayLoadList(input.getStoreNumber(),orderId).get(0);
		}
		if (null != tempOrderDTO  && !tempOrderDTO.equals("")) {

			orderDTO = tempOrderDTO;
			payLoad = orderDTO.getOriginalJson();
			logger.info("SOA Payload: "+payLoad);
			if (null != payLoad  && !payLoad.equals("")) {

				try {
					// Step #2.2: Create JSON of payload
					json = (JSONObject) JSONSerializer.toJSON(payLoad);
					isValidJSON = true;
				} catch (Exception e) {
					logger.error("JSON parsing error : " + e.getMessage());
					isValidJSON = false;
				}

				if (isValidJSON) {

					try {
						itemsArr = json.getJSONArray("items");
						isValidItemsArray = true;
					} catch (Exception e) {
						logger.error("JSON parsing error for items array : "
								+ e.getMessage());
						isValidItemsArray = false;
					}

				}

				if (isValidJSON && isValidItemsArray) {
					for (int itemIndex = 0; itemIndex < itemsArr.size(); itemIndex++) {
						item = itemsArr.getJSONObject(itemIndex);
						logger.info("item data for SOA: "+item.toString());
						if (null == item.getString("itemDescr") || item.getString("itemDescr").equalsIgnoreCase("null")) {
							sellingAccessoriesItem.setDescription("");
						} else{
							sellingAccessoriesItem.setDescription(item.getString("itemDescr"));
						}

						// Step #2.4: Add Item Price

						if (null == item.getString("itemPrice") || item.getString("itemPrice").equalsIgnoreCase("null")) {
							sellingAccessoriesItem.setPrice("");
						} else{
							sellingAccessoriesItem.setPrice(item.getString("itemPrice"));
						}
					}
				} // end if valid json and item array
			}// end if payload is null
		}
		logger.info("getPayLoadData ended");
		return sellingAccessoriesItem;
	}
}
