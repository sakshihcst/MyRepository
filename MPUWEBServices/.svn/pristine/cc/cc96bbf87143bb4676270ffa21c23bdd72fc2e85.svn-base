package com.searshc.targetinteraction;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrderConfirmResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4770197519123215229L;
	private int count;
	private List<Offer> offers;
    private String referenceUuid;
    private String message;
    private String offerGroup;
    private boolean success;
		
    public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Offer> getOffers() {
		return offers;
	}
	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
	public String getReferenceUuid() {
		return referenceUuid;
	}
	public void setReferenceUuid(String referenceUuid) {
		this.referenceUuid = referenceUuid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getOfferGroup() {
		return offerGroup;
	}
	public void setOfferGroup(String offerGroup) {
		this.offerGroup = offerGroup;
	}
	@Override
	public String toString() {
		return "OrderConfirmResponseDTO [count=" + count + ", offers=" + offers
				+ ", referenceUuid=" + referenceUuid + ", message=" + message
				+ ", offerGroup=" + offerGroup + ", success=" + success + "]";
	}
	
	
}
