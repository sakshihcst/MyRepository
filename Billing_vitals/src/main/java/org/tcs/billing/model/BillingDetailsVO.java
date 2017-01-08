package org.tcs.billing.model;

public class BillingDetailsVO {
	 
    private long id;
    private String icNoteIndicator;
    private String invoiceNumber;
    private String invoiceDate;
    private String poNumber;
    private String poLineNumber;
    private String billOfLanding;
    private String materialNumber;
    private String quantity;
    private String invoiceDetails;
    private String comments;
    
     
    public BillingDetailsVO(){
        id=0;
    }


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getIcNoteIndicator() {
		return icNoteIndicator;
	}


	public void setIcNoteIndicator(String icNoteIndicator) {
		this.icNoteIndicator = icNoteIndicator;
	}


	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public String getInvoiceDate() {
		return invoiceDate;
	}


	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	public String getPoNumber() {
		return poNumber;
	}


	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}


	public String getPoLineNumber() {
		return poLineNumber;
	}


	public void setPoLineNumber(String poLineNumber) {
		this.poLineNumber = poLineNumber;
	}


	public String getBillOfLanding() {
		return billOfLanding;
	}


	public void setBillOfLanding(String billOfLanding) {
		this.billOfLanding = billOfLanding;
	}


	public String getMaterialNumber() {
		return materialNumber;
	}


	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}


	public String getQuantity() {
		return quantity;
	}


	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}


	public String getInvoiceDetails() {
		return invoiceDetails;
	}


	public void setInvoiceDetails(String invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
    
    
}