package org.tcs.billing.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.tcs.billing.model.BillingMetaVO;
import org.tcs.billing.model.EmpDetailsVO;
import org.tcs.billing.model.generateOB10VO;
import org.tcs.billing.model.MonthlyInvoiceVO;
import org.tcs.billing.model.OB10ReportVO;
import org.tcs.billing.model.ResponseDTO;
import org.tcs.billing.model.SOWMetaVO;
import org.tcs.billing.model.ShippingMetaVO;
import org.tcs.billing.model.WONDetailsVO;
import org.tcs.billing.service.BillingService;

import com.google.gson.JsonObject;

@Controller
public class BillingController {
	
	@Autowired
	BillingService billingService;
	
	private static transient Log4JLogger logger = new Log4JLogger();
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homePage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("employee", new EmpDetailsVO());
			model.setViewName("home");
		}
		catch(Exception exception){
		}
		return model;
	}

	@RequestMapping(value = "/v1/createEmployee", method = RequestMethod.POST, consumes = "application/json")
	public ModelAndView createEmployee(HttpServletRequest request, HttpServletResponse response, @RequestBody EmpDetailsVO employeeVO) {
		ModelAndView model = new ModelAndView();
		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering BillingController.createEmployee - startTime: " + startTime);
			String message;
			if(billingService.createEmployee(employeeVO)>0){
				message = "Employee Creation Successful!";
			}else{
				message = "Employee Not created. Either it already exists or there is some other issues. Please contact the admin.";
			}
			model.addObject("message",message);
			model.addObject("employee", new EmpDetailsVO());
			model.setViewName("home");
			
//			RedirectView redirectView = new RedirectView();
//		    redirectView.setUrl("home");
//		    redirectView.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
//		    redirectView.setHttp10Compatible(Boolean.TRUE);
//		    model.setView(redirectView);
		    
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("createEmployee - End createEmployee took time:"+(endTime-startTime));
		} catch (Exception e) {
			logger.error("createEmployee",e);
		}
		 logger.debug("Exiting BillingController.createEmployee");
		return model;
	}

	
	@RequestMapping(value = "/v1/won", method = RequestMethod.GET)
	public ModelAndView wonHome(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("wonDetails", new WONDetailsVO());
			model.setViewName("won");
		}
		catch(Exception exception){
		}
		return model;
	}
	
	@RequestMapping(value = "/v1/createWON", method = RequestMethod.POST, consumes = "application/json")
	public ModelAndView createWON(@RequestBody WONDetailsVO wonDetails) {
		ModelAndView model = new ModelAndView();
		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering BillingController.createWON - startTime: " + startTime);
			String message;
			if(billingService.createWON(wonDetails)>0){
				message = "WON Creation Successful!";
			}else{
				message = "WON Not created. Please contact the admin.";
			}
			model.addObject("message",message);
			model.addObject("wonDetails", new WONDetailsVO());
			model.setViewName("won");
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("createWON - End createWON took time:"+(endTime-startTime));
		} catch (Exception e) {
			logger.error("createWON",e);
		}
		 logger.debug("Exiting BillingController.createWON");
		return model;
	}
	
	@RequestMapping(value = "/v1/sow", method = RequestMethod.GET)
	public ModelAndView sowHome(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("sowDetails", new SOWMetaVO());
			model.setViewName("sow");
		}
		catch(Exception exception){
		}
		return model;
	}
	
	@RequestMapping(value = "/v1/createSOW", method = RequestMethod.POST, consumes = "application/json")
	public ModelAndView createSOW(@RequestBody SOWMetaVO sowMeta) {
		ModelAndView model = new ModelAndView();
		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering BillingController.createSOW - startTime: " + startTime);
			String message;
			if(billingService.createSOW(sowMeta)>0){
				message = "SOW Creation Successful!";
			}else{
				message = "SOW Not created. Please contact the admin.";
			}
			model.addObject("message",message);
			model.addObject("sowDetails", new SOWMetaVO());
			model.setViewName("sow");
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("createSOW - End createSOW took time:"+(endTime-startTime));
		} catch (Exception e) {
			logger.error("createSOW",e);
		}
		 logger.debug("Exiting BillingController.createSOW");
		return model;
	}
	
	@RequestMapping(value = "/v1/shipping", method = RequestMethod.GET)
	public ModelAndView shipping(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("shippingDetails", new ShippingMetaVO());
			model.setViewName("shipping");
		}
		catch(Exception exception){
		}
		return model;
	}
	
	@RequestMapping(value = "/v1/createShipping", method = RequestMethod.POST, consumes = "application/json")
	public ModelAndView createShippingMeta(@RequestBody ShippingMetaVO shippingmeta) {
		ModelAndView model = new ModelAndView();
		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering BillingController.createShippingMeta - startTime: " + startTime);
			String message;
			if(billingService.createShippingMeta(shippingmeta)>0){
				message = "Shippng Information Creation Successful!";
			}else{
				message = "Shippng Information Not created. Please contact the admin.";
			}
			model.addObject("message",message);
			model.addObject("shippingDetails", new ShippingMetaVO());
			model.setViewName("shipping");
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("createShippingMeta - End createShippingMeta took time:"+(endTime-startTime));
		} catch (Exception e) {
			logger.error("createShippingMeta",e);
		}
		 logger.debug("Exiting BillingController.createShippingMeta");
		return model;
	}
	
	@RequestMapping(value = "/v1/billing", method = RequestMethod.GET)
	public ModelAndView billing(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("billingDetails", new BillingMetaVO());
			model.setViewName("billing");
		}
		catch(Exception exception){
		}
		return model;
	}
	
	@RequestMapping(value = "/v1/createBilling", method = RequestMethod.POST, consumes = "application/json")
	public ModelAndView createBillingMeta(@RequestBody BillingMetaVO billingmeta) {
		ModelAndView model = new ModelAndView();
		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering BillingController.createBillingMeta - startTime: " + startTime);
			String message;
			if(billingService.createBillingMeta(billingmeta)>0){
				message = "Billing Information Creation Successful!";
			}else{
				message = "Billing Information Not created. Please contact the admin.";
			}
			model.addObject("message",message);
			model.addObject("billingDetails", new BillingMetaVO());
			model.setViewName("billing");
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("createBillingMeta - End createBillingMeta took time:"+(endTime-startTime));
		} catch (Exception e) {
			logger.error("createBillingMeta",e);
		}
		 logger.debug("Exiting BillingController.createBillingMeta");
		return model;
	}
	
	@RequestMapping(value = "/v1/billingMeta/{billingId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDTO getBillingMeta(@PathVariable("billingId") int billingId){
		logger.debug("Entering BillingController.getBillingMeta:");
		logger.debug("Entering BillingController.getBillingMeta	billingId:" +billingId);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(billingService.getBillingMeta(billingId));
			//respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (Exception e) {
			logger.error("getBillingMeta",e);
		}
		logger.debug("exiting WebServicesController.getStoreDetails");
		return responseDTO;
		
	}
	
	@RequestMapping(value = "/v1/shippingMeta/{zipCode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDTO getShippingMetaList(@PathVariable("zipCode") String zipCode){
		logger.debug("Entering BillingController.getBillingMeta:");
		logger.debug("Entering BillingController.getBillingMeta	zipCode:" +zipCode);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(billingService.getShippingMetaList(zipCode));
			//respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (Exception e) {
			logger.error("getBillingMeta",e);
		}
		logger.debug("exiting WebServicesController.getStoreDetails");
		return responseDTO;
		
	}
	
	

	
	@RequestMapping(value = "/v1/getWON/{woNumber}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDTO getWonDetails(@PathVariable("woNumber") String woNumber){
		logger.debug("Entering BillingController.getWonDetails:");
		logger.debug("Entering BillingController.getWonDetails	woNumber:" +woNumber);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(billingService.getWonDetails(woNumber));
			//respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (Exception e) {
			logger.error("getBillingMeta",e);
		}
		logger.debug("exiting WebServicesController.getStoreDetails");
		return responseDTO;
		
	}
	

	
	@RequestMapping(value = "/v1/getSOW/{sowNumber}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDTO getSOWDetails(@PathVariable("sowNumber") String sowNumber){
		logger.debug("Entering BillingController.getSOWDetails:");
		logger.debug("Entering BillingController.getSOWDetails	sowNumber:" +sowNumber);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(billingService.getSOWDetails(sowNumber));
			//respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (Exception e) {
			logger.error("getSOWDetails",e);
		}
		logger.debug("exiting WebServicesController.getSOWDetails");
		return responseDTO;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/getEmployee/{empNumber}/{won}", produces = "application/json")
	public @ResponseBody ResponseDTO getEmployee(@PathVariable("empNumber") String empNumber,@PathVariable("won") String won){
		logger.debug("Entering BillingController.getEmployee:");
		logger.debug("Entering BillingController.getEmployee	woNumber:" +won);
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			responseDTO.setResponseBody(billingService.getEmployee(empNumber,won));
			//respEntity = MPUWebServiceUtil.getResponseEntity(responseDTO, true);
		} catch (Exception e) {
			logger.error("getSOWDetails",e);
		}
		logger.debug("exiting WebServicesController.getEmployee");
		return responseDTO;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/saveMonthlyInvoice", produces = "application/json")
	public @ResponseBody ResponseDTO saveMonthlyInvoice(@RequestBody List<MonthlyInvoiceVO> listOfInvoiceVO) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering BillingController.saveMonthlyInvoice ");
			logger.debug("Entering BillingController.saveMonthlyInvoice - startTime: " + startTime);
			billingService.saveMonthlyInvoice(listOfInvoiceVO);
			
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("saveMonthlyInvoice - End saveMonthlyInvoice took time:"+(endTime-startTime));
		} catch (Exception e) {
			logger.error("saveMonthlyInvoice",e);
		}
		 logger.debug("exiting - BillingController.saveMonthlyInvoice");
		return responseDTO;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/verifyMonthlyInvoice", produces = "application/json")
	public @ResponseBody ResponseDTO verifyMonthlyInvoice(@RequestBody List<MonthlyInvoiceVO> listOfInvoiceVO) {
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering BillingController.verifyMonthlyInvoice ");
			logger.debug("Entering BillingController.verifyMonthlyInvoice - startTime: " + startTime);
			billingService.verifyMonthlyInvoice(listOfInvoiceVO);
			
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("verifyMonthlyInvoice - End verifyMonthlyInvoice took time:"+(endTime-startTime));
		} catch (Exception e) {
			logger.error("verifyMonthlyInvoice",e);
		}
		 logger.debug("exiting - BillingController.verifyMonthlyInvoice");
		return responseDTO;
	}
    
	@RequestMapping(method = RequestMethod.POST, value = "/v1/generateOB10", produces = "application/json")
	public @ResponseBody ResponseDTO generateOB10(HttpServletResponse response, @RequestBody List<generateOB10VO> selectedInvoices) {
		
		List<OB10ReportVO> reportData = new ArrayList<OB10ReportVO>();
		ResponseEntity<ResponseDTO> respEntity = null;
		ResponseDTO responseDTO = new ResponseDTO();
		response.setContentType("text/csv");
		// creates mock data
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"","OB10.csv");
        response.setHeader(headerKey, headerValue);

		try {
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
			
			long startTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("Entering BillingController.generateOB10 ");
			logger.debug("Entering BillingController.generateOB10 - startTime: " + startTime);
			reportData = billingService.generateOB10(selectedInvoices);
			responseDTO.setResponseBody(reportData);
			
	        String[] header = { "ICNoteIndicator","InvoiceNumber","InvoiceDate","PoNumber",
	        		"CustomerOB10Number","BilltoCustomerNumber","DeliveryNoteNumber",
	        		"BilltoCustomerName","BillToCustomerAddress","BilltoCity","BilltoState","BilltoZipCode",
	        		"ShiptoName","ShiptoAddress","ShiptoCity","ShiptoState","ShiptoZipCode",
	        		"InvoiceDetails","SOWStartDate","SOWEndDate","POLineNumber","BillofLanding",
	        		"MaterialNumber","WorkorderNumber","WONStartDate","WONEndDate","ProductDescription",
	        		"UnitofMeasure","UnitPrice","Quantity","TotalLineAmount","fromdate","todate",
	        		"InvoiceLineDetail","TotalAmount","Comments","ContactName","ContactEmail","ContactPhone"};
	 
	        csvWriter.writeHeader(header);
	        csvWriter.flush();
	        for (OB10ReportVO data : reportData) {
	            csvWriter.write(data, header);
	            csvWriter.flush();
	        }
			
			long endTime=Calendar.getInstance().getTimeInMillis();
			logger.debug("generateOB10 - End generateOB10 took time:"+(endTime-startTime));
			
			csvWriter.close();
		} catch (Exception e) {
			logger.error("generateOB10",e);
		}
		 logger.debug("exiting - BillingController.generateOB10");
		return responseDTO;
	}
}
