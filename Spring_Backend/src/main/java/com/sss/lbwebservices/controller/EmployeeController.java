package com.sss.lbwebservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sss.lbwebservices.bean.Employee;
import com.sss.lbwebservices.constant.URIConstants;
import com.sss.lbwebservices.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value=URIConstants.LOGIN, method=RequestMethod.POST,consumes="application/json")
	public @ResponseBody Employee login(@RequestBody Employee employee){
		Employee emp = employeeService.login(employee);
		return emp;
	}

	@RequestMapping(value=URIConstants.REGISTER, method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Employee register(@RequestBody Employee employee){
		return employeeService.register(employee);
	}
}
