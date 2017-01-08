package org.tcs.billing.service;

import java.util.List;
import java.util.Map;

import org.tcs.billing.model.BillingMetaVO;
import org.tcs.billing.model.EmpDetailsVO;
import org.tcs.billing.model.MonthlyInvoiceVO;
import org.tcs.billing.model.OB10ReportVO;
import org.tcs.billing.model.SOWMetaVO;
import org.tcs.billing.model.ShippingMetaVO;
import org.tcs.billing.model.WONDetailsVO;
import org.tcs.billing.model.generateOB10VO;

public interface BillingService {
	
	public Map<Object,Object> getBillingMeta(int customerOB10);
	
	int createBillingMeta(BillingMetaVO billingmeta);

	public int createShippingMeta(ShippingMetaVO shippingmeta);

	public List<Map<String,Object>> getShippingMetaList(String zipCode);

	public int createWON(WONDetailsVO wonDetails);

	public Map<Object,Object> getWonDetails(String woNumber);

	public int createSOW(SOWMetaVO sowMeta);

	public Map<Object,Object> getSOWDetails(String sowNumber);

	public int createEmployee(EmpDetailsVO employeeVO);

	public  Map<Object,Object> getEmployee(String empNumber, String won);

	public int saveMonthlyInvoice(List<MonthlyInvoiceVO> listOfInvoiceVO);

	public List<OB10ReportVO> generateOB10(List<generateOB10VO> selectedInvoices);

	public int verifyMonthlyInvoice(List<MonthlyInvoiceVO> listOfInvoiceVO);

}
