package org.tcs.billing.model;

public class WONDetailsVO {
	 
    private long id;
    private String won;
    private String wonStartDate;
    private String wonEndDate;
    private String wonOwnerId;
    private String wonLocation;
    private String activeStatus;   
    private String sowNumber;
   
	public WONDetailsVO(){
        id=0;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWon() {
		return won;
	}

	public void setWon(String won) {
		this.won = won;
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

	public String getWonOwnerId() {
		return wonOwnerId;
	}

	public void setWonOwnerId(String wonOwnerId) {
		this.wonOwnerId = wonOwnerId;
	}

	public String getWonLocation() {
		return wonLocation;
	}

	public void setWonLocation(String wonLocation) {
		this.wonLocation = wonLocation;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	
    public String getSowNumber() {
		return sowNumber;
	}

	public void setSowNumber(String sowNumber) {
		this.sowNumber = sowNumber;
	}

}