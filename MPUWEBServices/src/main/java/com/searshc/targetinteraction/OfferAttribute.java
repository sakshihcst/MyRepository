package com.searshc.targetinteraction;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OfferAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean clipped;
	private String clippedDate;
	public boolean isClipped() {
		return clipped;
	}
	public void setClipped(boolean clipped) {
		this.clipped = clipped;
	}
	public String getClippedDate() {
		return clippedDate;
	}
	public void setClippedDate(String clippedDate) {
		this.clippedDate = clippedDate;
	}
	
	@Override
	public String toString() {
		return "OfferAttribute [clipped=" + clipped + ", clippedDate="
				+ clippedDate + "]";
	}
}
