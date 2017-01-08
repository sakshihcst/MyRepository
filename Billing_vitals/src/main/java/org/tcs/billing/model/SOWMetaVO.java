package org.tcs.billing.model;

public class SOWMetaVO {
	 
    private long id;
    private String sowNumber;
    private String sowName;
    private String sowStartDate;
    private String sowEndDate;
    private String purchaseOrderNumber;
    private String activeStatus;
      
    public SOWMetaVO(){
        id=0;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSowNumber() {
		return sowNumber;
	}

	public void setSowNumber(String sowNumber) {
		this.sowNumber = sowNumber;
	}

	public String getSowName() {
		return sowName;
	}

	public void setSowName(String sowName) {
		this.sowName = sowName;
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

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
       
}