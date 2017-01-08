package org.tcs.billing.service.impl;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tcs.billing.dao.BillingDAO;
import org.tcs.billing.model.BillingMetaVO;
import org.tcs.billing.model.EmpDetailsVO;
import org.tcs.billing.model.generateOB10VO;
import org.tcs.billing.model.MonthlyInvoiceVO;
import org.tcs.billing.model.OB10ReportVO;
import org.tcs.billing.model.SOWMetaVO;
import org.tcs.billing.model.ShippingMetaVO;
import org.tcs.billing.model.WONDetailsVO;
import org.tcs.billing.service.BillingService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service("billingService")
public class BillingServiceImpl implements BillingService{
	
	@Autowired
	BillingDAO billingDAO;
	
	private Map<Object, Object> billingMeta = new HashMap<Object,Object>();
	private List<Map<String,Object>> shippingMetaList = new ArrayList<Map<String,Object>>();
	private Map<Object, Object> wonDetails = new HashMap<Object,Object>();
	private Map<Object, Object> sowDetails = new HashMap<Object,Object>();
	private Map<Object, Object> employee = new HashMap<Object,Object>();

	@Override
	public Map<Object, Object> getBillingMeta(int customerOB10) {
		
		billingMeta = billingDAO.getBillingMeta(customerOB10);
		
		return billingMeta;
	}

	@Override
	public int createBillingMeta(BillingMetaVO billingmeta) {
		// TODO Auto-generated method stub
		return billingDAO.createBillingMeta(billingmeta);
	}

	@Override
	public int createShippingMeta(ShippingMetaVO shippingmeta) {
		// TODO Auto-generated method stub
		return billingDAO.createShippingMeta(shippingmeta);
		
	}

	@Override
	public List<Map<String,Object>> getShippingMetaList(String zipCode) {
		// TODO Auto-generated method stub
		shippingMetaList = billingDAO.getShippingMetaList(zipCode);
		return shippingMetaList;
	}

	@Override
	public int createWON(WONDetailsVO wonDetails) {
		// TODO Auto-generated method stub
		return billingDAO.createWON(wonDetails);
	}

	@Override
	public Map<Object, Object> getWonDetails(String woNumber) {
		// TODO Auto-generated method stub
		wonDetails = billingDAO.getWonDetails(woNumber);
		return wonDetails;
	}

	@Override
	public int createSOW(SOWMetaVO sowMeta) {
		// TODO Auto-generated method stub
		return billingDAO.createSOW(sowMeta);
	}

	@Override
	public Map<Object, Object> getSOWDetails(String sowNumber) {
		// TODO Auto-generated method stub
		sowDetails = billingDAO.getSowDetails(sowNumber);
		return sowDetails;
	}

	@Override
	public int createEmployee(EmpDetailsVO employeeVO) {
		// TODO Auto-generated method stub
		if(this.getEmployee(employeeVO.getEmpNumber(), employeeVO.getWon()).isEmpty()){
			return billingDAO.createEmployee(employeeVO);
		}
		return 0;
	}

	@Override
	public Map<Object, Object> getEmployee(String empNumber,String won) {
		// TODO Auto-generated method stub
		employee = billingDAO.getEmployee(empNumber,won);
		return employee;
	}

	@Override
	public int saveMonthlyInvoice(List<MonthlyInvoiceVO> listOfInvoiceVO) {
		// TODO Auto-generated method stub
		return billingDAO.saveMonthlyInvoice(listOfInvoiceVO);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<OB10ReportVO> generateOB10(List<generateOB10VO> selectedInvoices) {
		
		List<OB10ReportVO> ob10ReportDataList = new ArrayList<OB10ReportVO>();
		OB10ReportVO ob10Record = new OB10ReportVO();
		List<EmpDetailsVO> employeeDataTemp = new ArrayList<EmpDetailsVO>();
		List<EmpDetailsVO> employeeData = new ArrayList<EmpDetailsVO>();
		EmpDetailsVO empData = new EmpDetailsVO();
		List<MonthlyInvoiceVO> monthlyInvoiceList = new ArrayList<MonthlyInvoiceVO>();
		BillingMetaVO billingData = new BillingMetaVO();
		ShippingMetaVO shippingData = new ShippingMetaVO();
		final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
		WONDetailsVO wonDetail = new WONDetailsVO();
		SOWMetaVO sowDetail = new SOWMetaVO();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
//		Gson gson = new Gson();
//		Type type = new TypeToken<List<generateOB10VO>>(){}.getType();
//		List<generateOB10VO> selectedInvoicesList= gson.fromJson(selectedInvoices.toString(), type);
		
		for (generateOB10VO selectedInvoice : selectedInvoices) {
			
			wonDetail = mapper.convertValue(billingDAO.getWonDetails(selectedInvoice.getWorkOrderNumber()),WONDetailsVO.class);
			sowDetail = mapper.convertValue(billingDAO.getSowDetails(wonDetail.getSowNumber()),SOWMetaVO.class);
			billingData = mapper.convertValue(billingDAO.getBillingMeta(selectedInvoice.getBillingId()), BillingMetaVO.class);
			shippingData = mapper.convertValue(billingDAO.getShippingMetaById(selectedInvoice.getShippingId()), ShippingMetaVO.class);
			
			try {
				monthlyInvoiceList = billingDAO.getMonthlyInvoiceList(wonDetail.getWon(),formatter.parse(selectedInvoice.getInvoiceDate()).getMonth()+1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//employeeList = billingDAO.getEmployeesDataForWon(selectedInvoice.getWorkOrderNumber());
			
			for(MonthlyInvoiceVO invoiceData : monthlyInvoiceList){
				employeeDataTemp = billingDAO.getEmployeesDataForMonthlyReport(selectedInvoice.getWorkOrderNumber(),invoiceData.getEmpId());
				if(employeeDataTemp.size() > 0){
					employeeData.add(employeeDataTemp.get(0));
					
					ob10Record = new OB10ReportVO();
					
					ob10Record.setDeliveryNoteNumber(billingData.getDeliveryNoteNumber());
					ob10Record.setBillToCity(billingData.getCustomerCity());
					ob10Record.setBillToCustomerAddress(billingData.getCustomerAddress());
					ob10Record.setBillToCustomerName(billingData.getCustomerName());
					ob10Record.setBillToCustomerNumber(billingData.getBillToCustomerNumber());
					ob10Record.setBillToState(billingData.getCustomerState());
					ob10Record.setBillToZipCode(billingData.getCustomerZipCode());
					ob10Record.setComments("");
					ob10Record.setCustomerOB10Number(billingData.getCustomerOB10());
					ob10Record.setIcNoteIndicator(selectedInvoice.getIcNoteIndicator());
					ob10Record.setInvoiceDate(selectedInvoice.getInvoiceDate());
					ob10Record.setInvoiceDetails(sowDetail.getSowName());
					ob10Record.setInvoiceLineDetail("");
					ob10Record.setInvoiceNumber(invoiceData.getInvoiceNumber());
					ob10Record.setMaterialNumber("");
					ob10Record.setPoLineNumber("");
					ob10Record.setPoNumber(sowDetail.getPurchaseOrderNumber());
					ob10Record.setProductDescription(employeeDataTemp.get(0).getEmpName());
					ob10Record.setShipToName(shippingData.getShipToName());
					ob10Record.setShipToAddress(shippingData.getShipToAddress());
					ob10Record.setShipToCity(shippingData.getShipToCity());
					ob10Record.setShipToState(shippingData.getShipToState());
					ob10Record.setShipToZipCode(shippingData.getShipToZip());
					ob10Record.setSowEndDate(sowDetail.getSowEndDate());
					ob10Record.setSowStartDate(sowDetail.getSowStartDate());
					ob10Record.setToDate(invoiceData.getInvoiceStartDate());
					ob10Record.setFromDate(invoiceData.getInvoiceEndDate());
					ob10Record.setUnitOfMeasure("Hours");
					ob10Record.setUnitPrice(employeeDataTemp.get(0).getUnitPrice());
					ob10Record.setWonEndDate(wonDetail.getWonEndDate());
					ob10Record.setWonStartDate(wonDetail.getWonStartDate());
					ob10Record.setWorkOrderNumber(wonDetail.getWon());
					ob10Record.setQuantity((invoiceData.getWorkingDays()-invoiceData.getLeaveDays())*8);
					ob10Record.setTotalLineAmount(invoiceData.getTotalLineAmount());
					ob10Record.setTotalAmount(invoiceData.getTotalAmount());
					ob10Record.setContactEmail(invoiceData.getContactEmail());
					ob10Record.setContactName(invoiceData.getContactName());
					ob10Record.setContactPhone(invoiceData.getContactPhone());
					
					ob10ReportDataList.add(ob10Record);
					
				}
			}
					
		}
		
		return ob10ReportDataList;
	}

	@Override
	public int verifyMonthlyInvoice(List<MonthlyInvoiceVO> listOfInvoiceVO) {
		// TODO Auto-generated method stub
		long totalAmount = 0;
		long totalLineAmount = 0;
		long unitPrice = 0;
		String contactName = "";
		String contactEmail = "";
		String contactPhone = "";
		Map<Object,Object> wonOwner = new HashMap<Object,Object>();
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<MonthlyInvoiceVO>>(){}.getType();
		List<MonthlyInvoiceVO> invoiceList = gson.fromJson(listOfInvoiceVO.toString(), type);
		
		
		for(MonthlyInvoiceVO invoiceData : invoiceList){
			unitPrice = Long.parseLong((String)this.getEmployee(invoiceData.getEmpId(), invoiceData.getInvoiceWon()).get("unit_price"));
			totalLineAmount = (invoiceData.getWorkingDays() - invoiceData.getLeaveDays())*8*unitPrice;
			
			wonOwner = billingDAO.getEmployee((String)this.getWonDetails(invoiceData.getInvoiceWon()).get("wonOwnerId"));
			contactName = (String) wonOwner.get("emp_name");
			contactEmail = (String) wonOwner.get("email");
			contactPhone = (String) wonOwner.get("phone");
			
			invoiceData.setContactEmail(contactEmail);
			invoiceData.setContactName(contactName);
			invoiceData.setContactPhone(contactPhone);
			invoiceData.setTotalLineAmount(totalLineAmount);
			
			totalAmount = totalAmount + totalLineAmount;
		}
		
		for(MonthlyInvoiceVO invoiceData : invoiceList){
			invoiceData.setTotalAmount(totalAmount);
			invoiceData.setVerificationStatus("true");
		}
		
		
		return billingDAO.verifyMonthlyInvoice(invoiceList);
	}

}









