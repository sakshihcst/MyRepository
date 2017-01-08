package org.tcs.billing.model;

public class ShippingMetaVO {
	 
    private long id;
    private String shipToName;
	private String shipToAddress;
    private String shipToCity;
    private String shipToState;
    private String shipToZip;
    private String activeStatus;
    
    public ShippingMetaVO(){
        id=0;
    }
    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	} 
	
    public String getShipToName() {
		return shipToName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
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

	public String getShipToZip() {
		return shipToZip;
	}

	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
         
}