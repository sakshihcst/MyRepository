package com.searshc.mpuwebservice.processor.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sears.dej.interfaces.vo.UserVO;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.util.DJUtilities;
import com.sears.mpu.backoffice.domain.Order;
import com.searshc.mpuwebservice.bean.COMSearch;
import com.searshc.mpuwebservice.bean.ExceptionReportResults;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.bean.ReportResults;
import com.searshc.mpuwebservice.bean.ReportResultsDet;
import com.searshc.mpuwebservice.bean.RequestDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO;
import com.searshc.mpuwebservice.dao.COMServiceDAO;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.processor.COMServiceProcessor;
import com.searshc.mpuwebutil.util.ConversionUtils;

/**
 * 
 * @author ssingh6
 *
 */
@Service("comServiceProcessorImpl")
public class COMServiceProcessorImpl implements COMServiceProcessor{
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(COMServiceProcessorImpl.class);
	
	@Autowired
	@Qualifier("comServiceDAOImpl")
	private COMServiceDAO comServiceDAOImpl;
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	@Autowired
	private AssociateActivityServiceDAO associateActivityServiceDAOImpl;

	public List<ReportResults> getReport(COMSearch comSearch) throws DJException {
		// TODO Auto-generated method stub
		List<ReportResults> resultPick = new ArrayList<ReportResults>();
		if(null != comSearch.getDayDate() && !("").equalsIgnoreCase(comSearch.getDayDate()))
			comSearch.setDayDate(dateConversion(comSearch.getDayDate()));
		if(null != comSearch.getToDate() && !("").equalsIgnoreCase(comSearch.getToDate()))
			comSearch.setToDate(dateConversion(comSearch.getToDate()));
		// getting results for pickup and return
		resultPick = comServiceDAOImpl.getReports(comSearch);
		List<ReportResults> resultHelp = comServiceDAOImpl.getHelpRepairReports(comSearch);
		// getting results for help/repair , cant be done in previous query 
		if(null == resultPick){
			 resultPick = new ArrayList<ReportResults>();
		}
		if(null != resultHelp && ! resultHelp.isEmpty()){
			resultPick.addAll(resultHelp);
		}
		
		for(ReportResults res: resultPick){
			String customerDetails = res.getFullName();
			if(null != customerDetails && !("").equalsIgnoreCase(customerDetails)){
			String[] fnameLnamePhone = customerDetails.split(" ");
			if(fnameLnamePhone.length>=1){
				if(fnameLnamePhone.length>=2){
					res.setFirstName(fnameLnamePhone[0]);
					res.setLastName(fnameLnamePhone[1]);
					continue;
				}
				res.setLastName(fnameLnamePhone[0]);
			}
		}
		}
		if(null != resultPick && !resultPick.isEmpty()){
		Collections.sort(resultPick, new Comparator<ReportResults>() {
/*
 *   String Time1 = a.ora, Time2 = b.ora;

    int cmp = Time1.compareTo(Time2);
    if (cmp == 0) {
       // avoid expensive operations.
       Long VolTot1 = Long.parseLong(a.volume_totale);
       Long VolTot2 = Long.parseLong(b.volume_totale);
       cmp = VolTot1.compareTo(VolTot2);
    }
    return cmp;
 */
			@Override
			public int compare(ReportResults o1, ReportResults o2) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					 String Time1 = o1.getEndTime(), Time2 = o2.getEndTime();
					 if(o1 == null || o2 == null ||null == o1.getEndTime() || null==o2.getEndTime())
					 {
					    return 0;
					 } 
					 Date d1 = df.parse(o1.getEndTime());
					 Date d2 = df.parse(o2.getEndTime());
					 return d1.compareTo(d2);
					/*if(o1 == null || o2 == null)
					{
					    return 0;
					} 
					if(null != o1.getEndTime() && null != o2.getEndTime() && !"".equalsIgnoreCase(o1.getEndTime()) && !"".equalsIgnoreCase(o2.getEndTime())) {
						if(df.parse(o1.getEndTime()).before(df.parse(o2.getEndTime()))) {
							return 1;
						} else if (df.parse(o1.getEndTime()).after(df.parse(o2.getEndTime()))) {
							return -1;
						} else {
							return 0;
						}
					}else
					{
						return 0;
					}*/
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return 0;
			}
	    });
		}
		return resultPick;
	}
	
	public List<ReportResultsDet> getReportDetails(COMSearch comSearch) throws DJException {
		List<ReportResultsDet> result = comServiceDAOImpl.getReportsDetails(comSearch);
		List<ReportResultsDet> resultDet = new ArrayList<ReportResultsDet>();
				for(ReportResultsDet res: result){
					// set selling assoc id based on associate id
					UserVO user = associateActivityServiceDAOImpl.getAssociateInfo(res.getAssignedUser());
					if(null != user.getSearsSalesID() && !("").equalsIgnoreCase(user.getSearsSalesID())){
						res.setSellingAssoc(user.getSearsSalesID());
					}
					else{
						res.setSellingAssoc("75");
					}
					// separate rows for picked up items and not delivered items for that salescheck or request
					
					// in case of post void ::
					
					if(null != res.getDeliveredQty()){
					if(Integer.parseInt(res.getDeliveredQty()) > 0){
						ReportResultsDet reportResultsDet = copytoReportResult(res);
						reportResultsDet = getItemPrice(reportResultsDet);
						reportResultsDet.setQty(res.getDeliveredQty());
						reportResultsDet.setRequestStatus(MpuWebConstants.COMPLETED);
						resultDet.add(reportResultsDet);
					}
					}
					if(null!= res.getNotDeliveredQty()){
					if(Integer.parseInt(res.getNotDeliveredQty()) > 0){
						ReportResultsDet reportResultsDet = copytoReportResult(res);
						reportResultsDet = getItemPrice(reportResultsDet);
						reportResultsDet.setQty(reportResultsDet.getNotDeliveredQty());
						reportResultsDet.setRequestStatus("Not Shipped");
						if(null != reportResultsDet.getRequestType() && "POSTVOID".equalsIgnoreCase(reportResultsDet.getRequestType())){
							reportResultsDet.setRequestStatus(MpuWebConstants.COMPLETED);	
						}
						resultDet.add(reportResultsDet);
					}
					}
				}
		return resultDet;
	}
	
	private ReportResultsDet copytoReportResult(ReportResultsDet res){
		
		ReportResultsDet reportResultsDet = new ReportResultsDet(); 
		reportResultsDet.setSalesCheckNumber(res.getSalesCheckNumber());
		reportResultsDet.setUpdateTimeStamp(res.getUpdateTimeStamp());
		reportResultsDet.setTime(res.getTime());
		reportResultsDet.setRqtId(res.getRqtId());
		reportResultsDet.setTimeStamp(res.getTimeStamp());
		reportResultsDet.setCustomerDetails(res.getCustomerDetails());
		reportResultsDet.setRequestType(res.getRequestType());
		reportResultsDet.setRequestStatus(res.getRequestStatus());
		reportResultsDet.setAssignedUser(res.getAssignedUser());
		reportResultsDet.setDivNumber(res.getDivNumber());
		reportResultsDet.setItem(res.getItem());
		reportResultsDet.setSku(res.getSku());
		reportResultsDet.setPlus4(res.getPlus4());
		reportResultsDet.setUpc(res.getUpc());
		reportResultsDet.setDescription(res.getDescription());
		reportResultsDet.setPrice(res.getPrice());
		reportResultsDet.setJournalDate(res.getJournalDate());
		reportResultsDet.setRelatedSalescheck(res.getRelatedSalescheck());
		reportResultsDet.setTransId(res.getTransId());
		reportResultsDet.setTransDetailId(res.getTransDetailId());
		reportResultsDet.setOriginalJson(res.getOriginalJson());
		reportResultsDet.setAssociateId(res.getAssociateId());
		reportResultsDet.setAssociateName(res.getAssociateId());
		reportResultsDet.setNotDeliveredQty(res.getNotDeliveredQty());
		reportResultsDet.setQty(res.getQty());
		reportResultsDet.setStoreNumber(res.getStoreNumber());
		reportResultsDet.setStockQty(res.getStockQty());
		reportResultsDet.setDeliveredQty(res.getDeliveredQty());
		reportResultsDet.setFirstName(res.getFirstName());
		reportResultsDet.setLastName(res.getLastName());
		reportResultsDet.setPhoneNumber(res.getPhoneNumber());
		reportResultsDet.setItemSeq(res.getItemSeq());
		reportResultsDet.setSellingAssoc(res.getSellingAssoc());
		reportResultsDet.setTimeStart(res.getTimeStart());
		return reportResultsDet;
	}
	
	private ReportResultsDet getItemPrice(ReportResultsDet res)  throws DJException {
		try{
		    Order order = (Order)DJUtilities.createStringToObject(res.getOriginalJson(), new TypeReference<Order>(){});
		    RequestDTO requestDTO = ConversionUtils.convertNPOSOrderToRequestDTO(order);
			for(ItemDTO item : requestDTO.getItemList()){
			
				if(item.getDivNum().equalsIgnoreCase(res.getDivNumber()) && item.getItem().equalsIgnoreCase(res.getItem()) && item.getItemSeq().equalsIgnoreCase(res.getItemSeq())){
					res.setPrice(item.getItemPrice());
				}
			}
		}
		catch (Exception e) {
				e.printStackTrace();
			}	
		return res;
	}
	
	public List<ExceptionReportResults> getExceptionReportData(COMSearch comSearch) throws DJException{
		// TODO Auto-generated method stub
		List<ExceptionReportResults> resultmpu = new ArrayList<ExceptionReportResults>();
		try{
			  
		comSearch.setDayDate(dateConversion(comSearch.getDayDate()));
		 resultmpu = comServiceDAOImpl.getExceptionReportData(comSearch);
		List<ExceptionReportResults> resultqueue = comServiceDAOImpl.getExceptionReportQueueData(comSearch);
		if(null == resultmpu){
			resultmpu = new ArrayList<ExceptionReportResults>();
		}
		if(null != resultqueue && ! resultqueue.isEmpty()){
			resultmpu.addAll(resultqueue);
		}
		
		for(ExceptionReportResults res : resultmpu){
			if((res.getRequestType().equalsIgnoreCase("STAGE") || res.getRequestType().equalsIgnoreCase("BINSTAGE") || res.getRequestType().equalsIgnoreCase("LAYAWAY"))
					&& (res.getStatus().equalsIgnoreCase("OPEN") || res.getStatus().equalsIgnoreCase("STAGED") || res.getStatus().equalsIgnoreCase("BIN_PENDING") 
							|| res.getStatus().equalsIgnoreCase("BINNED") || res.getStatus().equalsIgnoreCase("PACKAGE_PENDING"))){
				res.setRequestType("Same Day HFM");
				res.setStatus("Incomplete");
				res.setQtyShipped("0");
				res.setLastName(res.getCustomerName());
				if(null != res.getQtyRemaining() && !("").equalsIgnoreCase(res.getQtyRemaining()) && !("0").equalsIgnoreCase(res.getQtyRemaining()))
					res.setQuantity(res.getQtyRemaining());
						
			}
			else if(res.getRequestType().equalsIgnoreCase("PICKUP")){
				res.setRequestType("PICK UP");
				res.setStatus("Open");
			}
			else if(res.getStatus().equalsIgnoreCase("RSTK_PEND_CNL") || res.getStatus().equalsIgnoreCase("CANCELLED")){
				res.setQtyShipped("0");
				res.setRequestType("Cancel");
				res.setStatus("Open");
				res.setLastName(res.getCustomerName());
			}
			else if(res.getStatus().equalsIgnoreCase("RSTK_PEND_VOID") || res.getStatus().equalsIgnoreCase("VOIDED") ){
				res.setQtyShipped("0");
				res.setRequestType("Void");
				res.setStatus("Open");
				res.setLastName(res.getCustomerName());
			}
			else if(res.getStatus().equalsIgnoreCase("CLOSED")){
				res.setQtyShipped("0");
				res.setRequestType("Out of Stock");
				res.setStatus("Open");
				res.setLastName(res.getCustomerName());
			}
			else if(res.getRequestType().equalsIgnoreCase("RETURN")){
				res.setQtyShipped(res.getQuantity());
				res.setRequestType("RETURN");
				res.setStatus("Open");
			}
			String customerDetails = res.getCustomerName();
			
			if(null == res.getLastName()){
			if(null!=customerDetails){
			String[] fnameLnamePhone = customerDetails.split(" ");
			if(fnameLnamePhone.length>=1){
				if(fnameLnamePhone.length>=2){
					res.setLastName(fnameLnamePhone[1]);
				}
				res.setFirstName(fnameLnamePhone[0]);
			}
			
		}
		}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return resultmpu;
	}
	
	public String dateConversion(String date) {
		SimpleDateFormat fromFormat = new SimpleDateFormat("MMM dd yyyy");
		SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dayDateString = "";
		try {
			Date dayDate = fromFormat.parse(date);
			dayDateString = toFormat.format(dayDate);
		} catch (ParseException e) {

			logger.error(e.getMessage(), e);
		}

		return dayDateString;
	}
	
	public Integer insertExceptionUpdate(String dateString,COMSearch comSearch) throws DJException {
		Integer result = comServiceDAOImpl.insertExceptionUpdate(comSearch);
		return result;
	}
	
	
	//exception
	
	public List<String> getKioskList(String store) throws DJException{
		return comServiceDAOImpl.getKioskList(store);
	}

	public boolean updateRequestForCOM(HashMap<String, String> reqInfo,String user,String store)
			throws DJException {
		String rqtId = reqInfo.get("rqtId").toString();
		String rqdId = reqInfo.get("rqdId").toString();
		String[] rqtIds = rqtId.split(",");
		String[] rqdIds = rqdId.split(",");
		int rows = 0;
		rows = comServiceDAOImpl.updateItemDetailForCOM(rqdIds,user,store);
		if(rows > 0){
			rows += comServiceDAOImpl.updateOrderDetailForCOM(rqtIds,store);	
		}
		if(rows > 0){
			return true;
		}else{
			return false;
		}
		
	}
	
	public ReportResultsDet getSellingAssoc(String assignedUser) throws DJException{
		ReportResultsDet res = new ReportResultsDet();
		UserVO user = associateActivityServiceDAOImpl.getAssociateInfo(res.getAssignedUser());
		if(null != user.getSearsSalesID() && !("").equalsIgnoreCase(user.getSearsSalesID())){
			res.setSellingAssoc(user.getSearsSalesID());
		}
		else{
			res.setSellingAssoc("75");
		}
	return res;	
	}
}
