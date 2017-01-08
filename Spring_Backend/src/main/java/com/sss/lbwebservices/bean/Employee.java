package com.sss.lbwebservices.bean;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="employee")
public class Employee {
	
	@Id
	private String empId;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String emailId;
	private Date profileCreationDate;
	private Date lastloggedInDate;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Date getProfileCreationDate() {
		return profileCreationDate;
	}
	public void setProfileCreationDate(Date profileCreationDate) {
		this.profileCreationDate = profileCreationDate;
	}
	public Date getLastloggedInDate() {
		return lastloggedInDate;
	}
	public void setLastloggedInDate(Date lastloggedInDate) {
		this.lastloggedInDate = lastloggedInDate;
	}
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", profileCreationDate=" + profileCreationDate + ", lastloggedInDate=" + lastloggedInDate + "]";
	}
}
