package org.tcs.billing.model;

public class OB10ReportVO {
	 
    private long id;
    private String icNoteIndicator;
    private String invoiceNumber;
    private String invoiceDate;
    private String poNumber;
    private String customerOB10Number;
    private String billToCustomerNumber;
    private String deliveryNoteNumber;
    private String billToCustomerName;
    private String billToCustomerAddress;
    private String billToCity;
    private String billToState;
    private String billToZipCode;
    private String shipToName;
    public String getShipToName() {
		return shipToName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	private String shipToAddress;
    private String shipToCity;
    private String shipToState;
    private String shipToZipCode;
    private String invoiceDetails;
    private String sowStartDate;
    private String sowEndDate;
    private String poLineNumber;
    private String billOfLanding;
    private String materialNumber;
    private String workOrderNumber;
    private String wonStartDate;
    private String wonEndDate;
    private String productDescription;
    private String unitOfMeasure;
    private long unitPrice;
    private long quantity;
    private long totalLineAmount;
    private String fromDate;
    private String toDate;
    private String invoiceLineDetail;
    private long totalAmount;
    private String comments;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
     
    public OB10ReportVO(){
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

	public String getCustomerOB10Number() {
		return customerOB10Number;
	}

	public void setCustomerOB10Number(String customerOB10Number) {
		this.customerOB10Number = customerOB10Number;
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

	public String getBillToCustomerName() {
		return billToCustomerName;
	}

	public void setBillToCustomerName(String billToCustomerName) {
		this.billToCustomerName = billToCustomerName;
	}

	public String getBillToCustomerAddress() {
		return billToCustomerAddress;
	}

	public void setBillToCustomerAddress(String billToCustomerAddress) {
		this.billToCustomerAddress = billToCustomerAddress;
	}

	public String getBillToCity() {
		return billToCity;
	}

	public void setBillToCity(String billToCity) {
		this.billToCity = billToCity;
	}

	public String getBillToState() {
		return billToState;
	}

	public void setBillToState(String billToState) {
		this.billToState = billToState;
	}

	public String getBillToZipCode() {
		return billToZipCode;
	}

	public void setBillToZipCode(String billToZipCode) {
		this.billToZipCode = billToZipCode;
	}

	public String getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToState() {
		return shipToState;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	public String getShipToZipCode() {
		return shipToZipCode;
	}

	public void setShipToZipCode(String shipToZipCode) {
		this.shipToZipCode = shipToZipCode;
	}

	public String getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(String invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public String getSowStartDate() {
		return sowStartDate;
	}

	public void setSowStartDate(String sowStartDate) {
		this.sowStartDate = sowStartDate;
	}

	public String getSowEndDate() {
		return sowEndDate;
	}

	public void setSowEndDate(String sowEndDate) {
		this.sowEndDate = sowEndDate;
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

	public String getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public String getWonStartDate() {
		return wonStartDate;
	}

	public void setWonStartDate(String wonStartDate) {
		this.wonStartDate = wonStartDate;
	}

	public String getWonEndDate() {
		return wonEndDate;
	}

	public void setWonEndDate(String wonEndDate) {
		this.wonEndDate = wonEndDate;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(long unitPrice) {
		this.unitPrice = unitPrice;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getTotalLineAmount() {
		return totalLineAmount;
	}

	public void setTotalLineAmount(long totalLineAmount) {
		this.totalLineAmount = totalLineAmount;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getInvoiceLineDetail() {
		return invoiceLineDetail;
	}

	public void setInvoiceLineDetail(String invoiceLineDetail) {
		this.invoiceLineDetail = invoiceLineDetail;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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
    
}