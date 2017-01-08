package org.tcs.billing.dao.impl;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.tcs.billing.dao.BillingDAO;
import org.tcs.billing.mapper.BillingMetaExtractor;
import org.tcs.billing.mapper.EmployeeDetailsExtractor;
import org.tcs.billing.mapper.SOWMetaExtractor;
import org.tcs.billing.mapper.ShippingMetaExtractor;
import org.tcs.billing.mapper.WONMetaExtractor;
import org.tcs.billing.model.BillingMetaVO;
import org.tcs.billing.model.EmpDetailsVO;
import org.tcs.billing.model.MonthlyInvoiceVO;
import org.tcs.billing.model.SOWMetaVO;
import org.tcs.billing.model.ShippingMetaVO;
import org.tcs.billing.model.WONDetailsVO;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@Repository("billingDao")
public class BillingDAOImpl implements BillingDAO {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplates;
	@Autowired
	private JdbcTemplate jdbcTemplates;
	
	private Map<Object,Object> billingMeta = new HashMap<Object,Object>();
	private List<Map<String,Object>> shippingMetaList = new ArrayList<Map<String,Object>>();
	private Map<Object,Object> wonMeta = new HashMap<Object,Object>();
	
	private static transient Log4JLogger logger = new Log4JLogger();
	
	@SuppressWarnings("unchecked")
	public  Map<Object,Object> getBillingMeta(int billingID){
		
		billingMeta = new HashMap<Object,Object>();
		
		logger.debug("Entering BillingDAOImpl.BillingDAOImpl	billingID:" + billingID);
		if(billingMeta.isEmpty()){
		String sql ="SELECT id,customerOB10,billToCustomerNumber,deliveryNoteNumber,customerName,customerAddress,customerCity,customerState,customerZipCode,activeStatus"
				+ " FROM BillingVitals.billing_meta where id="+billingID;
		billingMeta=(Map<Object,Object>)namedParameterJdbcTemplates.query(sql,new BillingMetaExtractor());
	    }
		logger.debug("exiting" + "BillingDAOImpl.getActionStatusMeta actionMeta"+ billingMeta.toString());
		logger.debug("exiting" + " BillingDAOImpl.getActionStatusMeta");
		return billingMeta;
	}
	
	
	public int createBillingMeta(BillingMetaVO billingMeta){
			logger.debug("Entering BillingDAOImpl.createBillingMeta" +billingMeta.toString());
			logger.debug("Entering BillingDAOImpl.createBillingMeta	order:" + billingMeta);
			
			Map<String, ? super Object> params = new HashMap<String, Object>();
			params.put("customerOB10", billingMeta.getCustomerOB10());
			params.put("billToCustomerNumber", billingMeta.getBillToCustomerNumber());
			params.put("deliveryNoteNumber", billingMeta.getBillToCustomerNumber());
			params.put("customerName", billingMeta.getCustomerName());
			params.put("customerAddress", billingMeta.getCustomerAddress());
			params.put("customerCity", billingMeta.getCustomerCity());
			params.put("customerState", billingMeta.getCustomerState());
			params.put("customerZipCode", billingMeta.getCustomerZipCode());
			params.put("activeStatus", billingMeta.getActiveStatus());
			
			String sql="INSERT INTO billing_meta(customerOB10,billToCustomerNumber,deliveryNoteNumber,customerName,customerAddress,customerCity,customerState,customerZipCode,activeStatus) "
					+ "VALUES (:customerOB10,:billToCustomerNumber,:deliveryNoteNumber,:customerName,:customerAddress,:customerCity,:customerState,:customerZipCode,:activeStatus)";
			
			return namedParameterJdbcTemplates.update(sql, params);
	}


	@Override
	public int createShippingMeta(ShippingMetaVO shippingmeta) {
		logger.debug("Entering BillingDAOImpl.createShippingMeta" +shippingmeta.toString());
		logger.debug("Entering BillingDAOImpl.createShippingMeta:" + shippingmeta);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		params.put("shipToName", shippingmeta.getShipToName());
		params.put("shipToAddress", shippingmeta.getShipToAddress());
		params.put("shipToCity", shippingmeta.getShipToCity());
		params.put("shipToState", shippingmeta.getShipToState());
		params.put("shipToZip", shippingmeta.getShipToZip());
		params.put("activeStatus", shippingmeta.getActiveStatus());
		
		String sql="INSERT INTO shipping_meta(shipToName,shipToAddress,shipToCity,shipToState,shipToZip,activeStatus) "
				+ "VALUES (:shipToName,:shipToAddress,:shipToCity,:shipToState,:shipToZip,:activeStatus)";
		
		return namedParameterJdbcTemplates.update(sql, params);

	}


	@Override
	public List<Map<String, Object>> getShippingMetaList(String zipCode) {
		
		shippingMetaList = new ArrayList<Map<String,Object>>();
		
		logger.debug("Entering BillingDAOImpl.getShippingMetaList	zipCode:" + zipCode);
		Map<String, ? super Object> params = new HashMap<String, Object>();
		
		if(shippingMetaList.isEmpty()){
			
		String sql ="SELECT * FROM shipping_meta where shipToZip="+zipCode;
		
		params.put("zipCode",zipCode);
		
		shippingMetaList= jdbcTemplates.queryForList(sql);
		
	    }
		logger.debug("exiting" + "BillingDAOImpl.getActionStatusMeta actionMeta"+ billingMeta.toString());
		logger.debug("exiting" + " BillingDAOImpl.getActionStatusMeta");
		return shippingMetaList;
	}


	@Override
	public int createWON(WONDetailsVO wonDetails) {
		logger.debug("Entering BillingDAOImpl.createWON" +wonDetails.toString());
		logger.debug("Entering BillingDAOImpl.createWON:" + wonDetails);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		params.put("won", wonDetails.getWon());
		params.put("wonStartDate", wonDetails.getWonStartDate());
		params.put("wonEndDate", wonDetails.getWonEndDate());
		params.put("wonOwnerId", wonDetails.getWonOwnerId());
		params.put("wonLocation", wonDetails.getWonLocation());
		params.put("activeStatus", wonDetails.getActiveStatus());
		params.put("sowNumber",wonDetails.getSowNumber());
		
		String sql="INSERT INTO won_meta(workOrderNumber,won_start_dt,won_end_dt,won_owner_id,won_location,activeStatus,sowNumber) "
				+ "VALUES (:won,:wonStartDate,:wonEndDate,:wonOwnerId,:wonLocation,:activeStatus,:sowNumber)";
		
		return namedParameterJdbcTemplates.update(sql, params);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getWonDetails(String woNumber) {
		wonMeta =  new HashMap<Object,Object>();
		
		logger.debug("Entering BillingDAOImpl.getWonDetails	zipCode:" + woNumber);
		
		if(wonMeta.isEmpty()){
			
		String sql ="SELECT id,workOrderNumber,won_start_dt,won_end_dt,won_owner_id,won_location,activeStatus,sowNumber FROM won_meta where workOrderNumber="+woNumber;
		
		wonMeta = (Map<Object,Object>)jdbcTemplates.query(sql, new WONMetaExtractor());
		
	    }
		logger.debug("exiting" + "BillingDAOImpl.getWonDetails wonMeta: "+ wonMeta.toString());
		logger.debug("exiting" + " BillingDAOImpl.getWonDetails");
		return wonMeta;
	}


	@Override
	public int createSOW(SOWMetaVO sowMeta) {
		logger.debug("Entering BillingDAOImpl.createSOW" +sowMeta.toString());
		logger.debug("Entering BillingDAOImpl.createSOW:" + sowMeta);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		params.put("sowName", sowMeta.getSowName());
		params.put("sow_start_dt", sowMeta.getSowStartDate());
		params.put("sow_end_dt", sowMeta.getSowEndDate());
		params.put("activeStatus", sowMeta.getActiveStatus());
		params.put("sowNumber", sowMeta.getSowNumber());
		params.put("purchaseOrderNumber", sowMeta.getPurchaseOrderNumber());
		
		String sql="INSERT INTO sow_meta(sowName,sow_start_dt,sow_end_dt,activeStatus,sowNumber,purchaseOrderNumber) "
				+ "VALUES (:sowName,:sow_start_dt,:sow_end_dt,:activeStatus,:sowNumber,:purchaseOrderNumber)";
		
		return namedParameterJdbcTemplates.update(sql, params);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getSowDetails(String sowNumber) {
		
		HashMap<Object, Object> sowMeta = new HashMap<Object,Object>();
		
		logger.debug("Entering BillingDAOImpl.getSowDetails	zipCode:" + sowNumber);
		
		if(sowMeta.isEmpty()){
			
		String sql ="SELECT id,sowName,sow_start_dt,sow_end_dt,activeStatus,sowNumber,purchaseOrderNumber FROM sow_meta where sowNumber="+sowNumber;
		
		sowMeta = (HashMap<Object,Object>)jdbcTemplates.query(sql, new SOWMetaExtractor());
		
	    }
		logger.debug("exiting" + "BillingDAOImpl.getSowDetails wonMeta :"+ sowMeta.toString());
		logger.debug("exiting" + " BillingDAOImpl.getSowDetails");
		return sowMeta;
	}


	@Override
	public int createEmployee(EmpDetailsVO employeeVO) {
		logger.debug("Entering BillingDAOImpl.createEmployee" +employeeVO.toString());
		logger.debug("Entering BillingDAOImpl.createEmployee:" + employeeVO);
		
		Map<String, ? super Object> params = new HashMap<String, Object>();
		params.put("emp_id", employeeVO.getEmpNumber());
		params.put("emp_name", employeeVO.getEmpName());
		params.put("billing_start_dt", employeeVO.getBillingStartDate());
		params.put("billing_end_dt", employeeVO.getBillingEndDate());
		params.put("won_number", employeeVO.getWon());
		params.put("email", employeeVO.getEmail());
		params.put("phone", employeeVO.getPhone());
		params.put("unit_price", employeeVO.getUnitPrice());
		params.put("activeStatus", employeeVO.getActiveStatus());
		
		String sql="INSERT INTO emp_details(emp_id,emp_name,billing_start_dt,billing_end_dt,won_number,email,phone,unit_price,activeStatus) "
				+ "VALUES (:emp_id,:emp_name,:billing_start_dt,:billing_end_dt,:won_number,:email,:phone,:unit_price,:activeStatus)";
		
		return namedParameterJdbcTemplates.update(sql, params);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getEmployee(String empNumber,String won) {
		
		HashMap<Object, Object> employee = new HashMap<Object,Object>();
		
		logger.debug("Entering BillingDAOImpl.getEmployee	empNumber:" + empNumber);
		
		if(employee.isEmpty()){
			
		String sql ="SELECT id,emp_id,emp_name,billing_start_dt,billing_end_dt,won_number,email,phone,unit_price,activeStatus FROM emp_details where "
				+ "emp_id="+empNumber+" and won_number="+won;
		
		employee = (HashMap<Object,Object>)jdbcTemplates.query(sql, new EmployeeDetailsExtractor());
		
	    }
		logger.debug("exiting" + "BillingDAOImpl.getEmployee employee :"+ employee.toString());
		logger.debug("exiting" + " BillingDAOImpl.getEmployee");
		return employee;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getEmployee(String empNumber) {
		HashMap<Object, Object> employee = new HashMap<Object,Object>();
		
		logger.debug("Entering BillingDAOImpl.getEmployee	empNumber:" + empNumber);
		
		if(employee.isEmpty()){
			
		String sql ="SELECT id,emp_id,emp_name,billing_start_dt,billing_end_dt,won_number,email,phone,unit_price,activeStatus FROM emp_details where "
				+ "emp_id="+empNumber + " ORDER BY won_number DESC LIMIT 1;";
		
		employee = (HashMap<Object,Object>)jdbcTemplates.query(sql, new EmployeeDetailsExtractor());
		
	    }
		logger.debug("exiting" + "BillingDAOImpl.getEmployee employee :"+ employee.toString());
		logger.debug("exiting" + " BillingDAOImpl.getEmployee");
		return employee;
	}

	@Override
	public int saveMonthlyInvoice(List<MonthlyInvoiceVO> listOfInvoiceVO) {
		
		String sql="INSERT INTO monthly_invoice_trans(invoice_month,invoice_won,invoice_number,emp_id,invoice_start_dt,invoice_end_dt,working_days,"
				+ "leave_days,sow_number,verification_status,totalLineAmount,totalAmount,contactName,contactEmail,contactPhone) "
				+ "VALUES (:invoiceMonth,:invoiceWon,:invoiceNumber,:empId,:invoiceStartDate,:invoiceEndDate,:workingDays,:leaveDays,:sowNumber,"
				+ ":verificationStatus,:totalLineAmount,:totalAmount,:contactName,:contactEmail,:contactPhone)";
		
		List<SqlParameterSource> parameters = new ArrayList<SqlParameterSource>();
		try{
			Gson gson = new Gson();
			Type type = new TypeToken<List<MonthlyInvoiceVO>>(){}.getType();
			List<MonthlyInvoiceVO> invoiceList = gson.fromJson(listOfInvoiceVO.toString(), type);	
			for (MonthlyInvoiceVO invoiceVo : invoiceList) {
				parameters.add(new BeanPropertySqlParameterSource(invoiceVo)); 
			}
		}
		catch(JsonSyntaxException jsone){
			for (MonthlyInvoiceVO invoiceVo : listOfInvoiceVO) {
				parameters.add(new BeanPropertySqlParameterSource(invoiceVo)); 
			}
		}
		namedParameterJdbcTemplates.batchUpdate(sql,parameters.toArray(new SqlParameterSource[0]));
		
		return 0;
	}
	
	@Override
	public int verifyMonthlyInvoice(List<MonthlyInvoiceVO> listOfInvoiceVO) {
		
		String sql="UPDATE monthly_invoice_trans "
				+ "SET verification_status=:verificationStatus,totalLineAmount=:totalLineAmount,totalAmount=:totalAmount,contactName=:contactName,"
				+ "contactEmail=:contactEmail,contactPhone=:contactPhone where "
				+ "invoice_won=:invoiceWon and invoice_month=:invoiceMonth and emp_id=:empId";
		
		List<SqlParameterSource> parameters = new ArrayList<SqlParameterSource>();
		try{
			Gson gson = new Gson();
			Type type = new TypeToken<List<MonthlyInvoiceVO>>(){}.getType();
			List<MonthlyInvoiceVO> invoiceList = gson.fromJson(listOfInvoiceVO.toString(), type);	
			for (MonthlyInvoiceVO invoiceVo : invoiceList) {
				parameters.add(new BeanPropertySqlParameterSource(invoiceVo)); 
			}
		}
		catch(JsonSyntaxException jsone){
			for (MonthlyInvoiceVO invoiceVo : listOfInvoiceVO) {
				parameters.add(new BeanPropertySqlParameterSource(invoiceVo)); 
			}
		}
		namedParameterJdbcTemplates.batchUpdate(sql,parameters.toArray(new SqlParameterSource[0]));
		
		return 0;
	}

	@Override
	public List<EmpDetailsVO> getEmployeesDataForWon(String workOrderNumber) {
		
		List<EmpDetailsVO> employeeList = null;
		logger.debug("Entering BillingDAOImpl.getEmployeesDataForWon	workOrderNumber:" + workOrderNumber);
		
		String sql ="SELECT id,emp_id,emp_name,billing_start_dt,billing_end_dt,won_number,email,phone,unit_price,activeStatus FROM emp_details where won_number="+workOrderNumber;
		
		employeeList = jdbcTemplates.queryForList(sql, EmpDetailsVO.class);
		
		logger.debug("exiting" + "BillingDAOImpl.getEmployeesDataForWon employee :"+ employeeList.toString());
		logger.debug("exiting" + " BillingDAOImpl.getEmployeesDataForWon");
		return employeeList;
	}


	@Override
	public List<MonthlyInvoiceVO> getMonthlyInvoiceList(String workOrderNumber, int month) {
		List<MonthlyInvoiceVO> invoiceList = null;
		logger.debug("Entering BillingDAOImpl.getEmployeesDataForWon	workOrderNumber:" + workOrderNumber);
		
		String sql ="SELECT id,invoice_month,invoice_won,invoice_number,emp_id,invoice_start_dt,invoice_end_dt,working_days,leave_days,sow_number,verification_status"
				+ ",totalLineAmount,totalAmount,contactName,contactEmail,contactPhone"
				+ " FROM monthly_invoice_trans where invoice_won="+workOrderNumber +" and invoice_month="+month;
		
		invoiceList = jdbcTemplates.query(sql, new RowMapper<MonthlyInvoiceVO>() {
		    			public MonthlyInvoiceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		    				MonthlyInvoiceVO invoice = new MonthlyInvoiceVO();
					        invoice.setEmpId(rs.getString("emp_id"));
					        invoice.setId(rs.getInt("id"));
					        invoice.setInvoiceEndDate(rs.getString("invoice_end_dt"));
					        invoice.setInvoiceMonth(rs.getString("invoice_month"));
					        invoice.setInvoiceNumber(rs.getString("invoice_number"));
					        invoice.setInvoiceStartDate(rs.getString("invoice_start_dt"));
					        invoice.setInvoiceWon(rs.getString("invoice_won"));
					        invoice.setLeaveDays(rs.getInt("leave_days"));
					        invoice.setSowNumber(rs.getString("sow_number"));
					        invoice.setVerificationStatus(rs.getString("verification_status"));
					        invoice.setWorkingDays(rs.getInt("working_days"));
					        invoice.setTotalLineAmount(rs.getInt("totalLineAmount"));
					        invoice.setTotalAmount(rs.getInt("totalAmount"));
					        invoice.setContactEmail(rs.getString("contactEmail"));
					        invoice.setContactName(rs.getString("contactName"));
					        invoice.setContactPhone(rs.getString("contactPhone"));
					        return invoice;
		    			}

					});
		
		logger.debug("exiting" + "BillingDAOImpl.getEmployeesDataForWon employee :"+ invoiceList.toString());
		logger.debug("exiting" + " BillingDAOImpl.getEmployeesDataForWon");
		return invoiceList;
	}
	
	@Override
	public List<EmpDetailsVO> getEmployeesDataForMonthlyReport(String workOrderNumber, String empId) {
		
		List<EmpDetailsVO> employeeList = null;
		
		logger.debug("Entering BillingDAOImpl.getEmployeesDataForMonthlyReport	workOrderNumber:" + workOrderNumber);
		
		String sql ="SELECT id,emp_id,emp_name,billing_start_dt,billing_end_dt,won_number,email,phone,unit_price,activeStatus FROM emp_details "
				+ "where won_number="+workOrderNumber +" and emp_id="+empId;
		
		employeeList = jdbcTemplates.query(sql, new RowMapper<EmpDetailsVO>() {
			public EmpDetailsVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmpDetailsVO empData = new EmpDetailsVO();
				empData.setActiveStatus(rs.getString("activeStatus"));
				empData.setBillingEndDate(rs.getString("billing_end_dt"));
				empData.setBillingStartDate(rs.getString("billing_start_dt"));
				empData.setEmail(rs.getString("email"));
				empData.setEmpName(rs.getString("emp_name"));
				empData.setEmpNumber(rs.getString("emp_id"));
				empData.setId(rs.getInt("id"));
				empData.setPhone(rs.getString("phone"));
				empData.setUnitPrice(rs.getInt("unit_price"));
				empData.setWon(rs.getString("won_number"));
		        return empData;
			}
		});
		
		logger.debug("exiting" + "BillingDAOImpl.getEmployeesDataForWon employee :"+ employeeList.toString());
		logger.debug("exiting" + " BillingDAOImpl.getEmployeesDataForWon");
		return employeeList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getShippingMetaById(int shippingId) {
		
		HashMap<Object,Object> shippingMetaData = new HashMap<Object,Object>();
		
		logger.debug("Entering BillingDAOImpl.getShippingMetaById	shippingId:" + shippingId);
		Map<String, ? super Object> params = new HashMap<String, Object>();
			
		String sql ="SELECT id,shipToName,shipToAddress,shipToCity,shipToState,shipToZip,activeStatus FROM shipping_meta where id="+shippingId;
		
		shippingMetaData= (HashMap<Object,Object>)jdbcTemplates.query(sql,new ShippingMetaExtractor());

		logger.debug("exiting" + "BillingDAOImpl.getShippingMetaById actionMeta"+ shippingMetaData.toString());
		logger.debug("exiting" + " BillingDAOImpl.getShippingMetaById");
		return shippingMetaData;
		//return null;
	}




}
