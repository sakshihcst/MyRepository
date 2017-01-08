package org.tcs.billing.model;

public class generateOB10VO {
	 
    private String workOrderNumber;
    private int billingId;
    private int shippingId;
    private String icNoteIndicator;
    private String invoiceDate;
    
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public int getBillingId() {
		return billingId;
	}
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}
	public int getShippingId() {
		return shippingId;
	}
	public void setShippingId(int shippingId) {
		this.shippingId = shippingId;
	}
	public String getIcNoteIndicator() {
		return icNoteIndicator;
	}
	public void setIcNoteIndicator(String icNoteIndicator) {
		this.icNoteIndicator = icNoteIndicator;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
    
}