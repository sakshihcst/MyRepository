package com.sss.lbwebservices.dao;

import com.sss.lbwebservices.bean.Employee;

public interface EmployeeDAO {
	public Employee login(Employee emp);
	public Employee register(Employee emp);
}
