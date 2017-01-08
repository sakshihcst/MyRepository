package org.tcs.billing.model;

public class MonthlyInvoiceVO {
	 
    private long id;
    private String invoiceMonth;
    private String invoiceWon;
    private String invoiceNumber;
    private String empId;
    private String invoiceStartDate;
    private String invoiceEndDate;
    private int workingDays;
    private int leaveDays;
    private String sowNumber;
    private String verificationStatus;
    private long totalLineAmount;
    private long totalAmount;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    
     
    public MonthlyInvoiceVO(){
    	invoiceMonth = "";
        invoiceWon = "";
        invoiceNumber = "";
        empId = "";
        invoiceStartDate = "";
        invoiceEndDate = "";
        workingDays=0;
        leaveDays=0;
        sowNumber="";
        verificationStatus="false";
        totalLineAmount = 0;
        totalAmount = 0;
        contactName = "";
        contactEmail = "";
        contactPhone = "";
    }


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getInvoiceMonth() {
		return invoiceMonth;
	}


	public void setInvoiceMonth(String invoiceMonth) {
		this.invoiceMonth = invoiceMonth;
	}


	public String getInvoiceWon() {
		return invoiceWon;
	}


	public void setInvoiceWon(String invoiceWon) {
		this.invoiceWon = invoiceWon;
	}


	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public String getInvoiceStartDate() {
		return invoiceStartDate;
	}


	public void setInvoiceStartDate(String invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}


	public String getInvoiceEndDate() {
		return invoiceEndDate;
	}


	public void setInvoiceEndDate(String invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}


	public int getWorkingDays() {
		return workingDays;
	}


	public void setWorkingDays(int workingDays) {
		this.workingDays = workingDays;
	}


	public int getLeaveDays() {
		return leaveDays;
	}


	public void setLeaveDays(int leaveDays) {
		this.leaveDays = leaveDays;
	}


	public String getSowNumber() {
		return sowNumber;
	}


	public void setSowNumber(String sowNumber) {
		this.sowNumber = sowNumber;
	}


	public String getVerificationStatus() {
		return verificationStatus;
	}


	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}


	public long getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getContactEmail() {
		return contactEmail;
	}


	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	public String getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	public long getTotalLineAmount() {
		return totalLineAmount;
	}


	public void setTotalLineAmount(long totalLineAmount) {
		this.totalLineAmount = totalLineAmount;
	}

}