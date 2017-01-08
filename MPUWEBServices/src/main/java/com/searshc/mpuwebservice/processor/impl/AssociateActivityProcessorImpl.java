package com.searshc.mpuwebservice.processor.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sears.dej.interfaces.vo.UserVO;
import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.WebActitivtyDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.processor.AssociateActivityProcessor;

@Component
public class AssociateActivityProcessorImpl implements AssociateActivityProcessor {
	
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	
	@Autowired
	AssociateActivityServiceDAO activityServiceDAO;
	
	@Autowired
	private AssociateActivityServiceDAO associateActivityServiceDAOImpl;


	
	/**Insert record into the web_response_associate_activity_report table
	 * 
	 * @param rqtId
	 * @param storeNum
	 * @throws DJException
	 */
	@Async
	public void createAssociateActivity(String orderStatus,String rqtId,String storeNum) throws DJException{
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date currentTime=Calendar.getInstance().getTime();
		List<WebActitivtyDTO> webActitivtyDTOs=new ArrayList<WebActitivtyDTO>(); 
		List<ItemDTO> itemList = mpuWebServiceDAOImpl.getItemListForOrder(storeNum,rqtId);
		if(!CollectionUtils.isEmpty(itemList)){
			for(ItemDTO itemDTO : itemList){
				if(itemDTO.getRequestType().equalsIgnoreCase(MpuWebConstants.BINWEB) ){
					WebActitivtyDTO webActitivtyDTO = new WebActitivtyDTO();
					webActitivtyDTO.setAssociateId(getAssociateId(itemDTO.getAssignedUser()));
					webActitivtyDTO.setCreatedDate(date.format(currentTime));
					webActitivtyDTO.setItemIdentifier(itemDTO.getDivNum()+itemDTO.getItem()+itemDTO.getSku());
					webActitivtyDTO.setItemStatus(itemDTO.getItemStatus());
					webActitivtyDTO.setSalescheck(itemDTO.getSalescheck());
					webActitivtyDTO.setStoreNumber(itemDTO.getStoreNumber());
					webActitivtyDTO.setWorkStatus(orderStatus);
					webActitivtyDTOs.add(webActitivtyDTO);
				}
			}
		}
		if(!CollectionUtils.isEmpty(webActitivtyDTOs)){
			activityServiceDAO.insertWebActivityRecords(webActitivtyDTOs);
		}
	}
	
	private String getAssociateId(String userId) throws DJException{
		if(StringUtils.hasText(userId)){
			UserVO associateInfo = associateActivityServiceDAOImpl.getAssociateInfo(userId);	
			if(associateInfo!=null ){
					if(StringUtils.hasText(associateInfo.getSearsSalesID())){
						return associateInfo.getSearsSalesID();
					}else{
						return "";
					}
				}
		}
			return "";
	}
	
}
