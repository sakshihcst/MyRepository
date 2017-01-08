package org.tcs.billing.dao;

import java.util.List;
import java.util.Map;
import org.tcs.billing.model.*;

public interface BillingDAO {
	
	Map<Object, Object> getBillingMeta(int customerOB10);
	
	int createBillingMeta(BillingMetaVO billingmeta);
	
	int createShippingMeta(ShippingMetaVO shippingmeta);

	List<Map<String, Object>> getShippingMetaList(String zipCode);

	int createWON(WONDetailsVO wonDetails);

	Map<Object, Object> getWonDetails(String woNumber);

	int createSOW(SOWMetaVO sowMeta);

	Map<Object, Object> getSowDetails(String sowNumber);

	int createEmployee(EmpDetailsVO employeeVO);

	Map<Object, Object> getEmployee(String empNumber,String won);

	Map<Object, Object> getEmployee(String empNumber);
	
	int saveMonthlyInvoice(List<MonthlyInvoiceVO> listOfInvoiceVO);

	List<EmpDetailsVO> getEmployeesDataForWon(String workOrderNumber);

	List<MonthlyInvoiceVO> getMonthlyInvoiceList(String string, int i);

	List<EmpDetailsVO> getEmployeesDataForMonthlyReport(String workOrderNumber, String empId);

	Map<Object, Object> getShippingMetaById(int shippingId);

	int verifyMonthlyInvoice(List<MonthlyInvoiceVO> listOfInvoiceVO);

}
