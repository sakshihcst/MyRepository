package com.sss.lbwebservices.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.sss.lbwebservices.bean.Employee;
import com.sss.lbwebservices.dao.EmployeeDAO;

@Repository("employeeDAO")
public class EmployeeDAOImpl implements EmployeeDAO {

	@Autowired 
	MongoTemplate mongoTemplate;
	
	@Override
	public Employee login(Employee emp) {
		Employee employee = new Employee();
		if(emp.getFirstName().equalsIgnoreCase("Sakshi")){
			employee.setFirstName("Sakshi");
			employee.setLastName("Gupta");
		}
		return employee;
	}
	
	@Override
	public Employee register(Employee emp) {
		mongoTemplate.insert(emp);
		return emp;
	} 
	
	public void createEmployeeCollection() { 
		if (!mongoTemplate.collectionExists(Employee.class)) { 
			mongoTemplate.createCollection(Employee.class); 
		} 
	}

}
