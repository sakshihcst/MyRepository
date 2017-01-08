package org.tcs.billing.model;

public class EmpDetailsVO {
	 
    private long id;
    private String empName;
    private String empNumber;
    private String billingStartDate;
    private String billingEndDate;
    private String won;
    private String email;
    private String phone;
    private long unitPrice;
    private String activeStatus;
    
     
    public EmpDetailsVO(){
        setId(0);
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpNumber() {
		return empNumber;
	}

	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}

	public String getBillingStartDate() {
		return billingStartDate;
	}

	public void setBillingStartDate(String billingStartDate) {
		this.billingStartDate = billingStartDate;
	}

	public String getBillingEndDate() {
		return billingEndDate;
	}

	public void setBillingEndDate(String billingEndDate) {
		this.billingEndDate = billingEndDate;
	}

	public String getWon() {
		return won;
	}

	public void setWon(String won) {
		this.won = won;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(long unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	} 
}