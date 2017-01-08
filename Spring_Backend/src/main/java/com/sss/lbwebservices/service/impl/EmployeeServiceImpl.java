package com.sss.lbwebservices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sss.lbwebservices.bean.Employee;
import com.sss.lbwebservices.dao.EmployeeDAO;
import com.sss.lbwebservices.service.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeDAO employeeDAO;

	@Override
	public Employee login(Employee emp) {
		return employeeDAO.login(emp);
	}

	@Override
	public Employee register(Employee emp) {
		return employeeDAO.register(emp);
	}

}
