package com.searshc.targetinteraction;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PogDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2080767081290092177L;
	
	
	private String spcPlanNbr;
	private int busFillQty;
	private String planStartDate;
	private String planEndDate;
	private int divNbr;
	private String description;
	private int planGroupNbr;
	private String seqNbr;
	public String getSpcPlanNbr() {
		return spcPlanNbr;
	}
	public void setSpcPlanNbr(String spcPlanNbr) {
		this.spcPlanNbr = spcPlanNbr;
	}
	public int getBusFillQty() {
		return busFillQty;
	}
	public void setBusFillQty(int busFillQty) {
		this.busFillQty = busFillQty;
	}
	public String getPlanStartDate() {
		return planStartDate;
	}
	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}
	public String getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}
	public int getDivNbr() {
		return divNbr;
	}
	public void setDivNbr(int divNbr) {
		this.divNbr = divNbr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPlanGroupNbr() {
		return planGroupNbr;
	}
	public void setPlanGroupNbr(int planGroupNbr) {
		this.planGroupNbr = planGroupNbr;
	}
	public String getSeqNbr() {
		return seqNbr;
	}
	public void setSeqNbr(String seqNbr) {
		this.seqNbr = seqNbr;
	}	

}
