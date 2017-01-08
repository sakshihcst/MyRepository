package org.tcs.billing.model;

public class BillingMetaVO {
	 
    private long id;
    private String customerOB10;
    private String billToCustomerNumber;
    private String deliveryNoteNumber;
    private String customerName;
    private String customerAddress;
    private String customerCity;
    private String customerState;
    private String customerZipCode;
    private String activeStatus;
    
     
    public BillingMetaVO(){
        id=0;
    }
    
    public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getCustomerOB10() {
		return customerOB10;
	}



	public void setCustomerOB10(String customerOB10) {
		this.customerOB10 = customerOB10;
	}



	public String getBillToCustomerNumber() {
		return billToCustomerNumber;
	}



	public void setBillToCustomerNumber(String billToCustomerNumber) {
		this.billToCustomerNumber = billToCustomerNumber;
	}



	public String getDeliveryNoteNumber() {
		return deliveryNoteNumber;
	}



	public void setDeliveryNoteNumber(String deliveryNoteNumber) {
		this.deliveryNoteNumber = deliveryNoteNumber;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getCustomerAddress() {
		return customerAddress;
	}



	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}



	public String getCustomerCity() {
		return customerCity;
	}



	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}



	public String getCustomerState() {
		return customerState;
	}



	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}



	public String getCustomerZipCode() {
		return customerZipCode;
	}



	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}



	public String getActiveStatus() {
		return activeStatus;
	}



	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
 
    @Override
    public String toString() {
        return "User [customerOB10=" + customerOB10 + ", customerName=" + customerName + ", customerAddress=" + customerAddress
                + ", customerCity=" + customerCity + ", customerState=" + customerState + "]";
    }
     
 
     
}