package com.techelevator.campgrounds;

public class Site {
	
	private long siteId;
	private long campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private String accessible;
	private int maxRVLength;
	private String utilities; 
	
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public String isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		String printYesNo = null;
		if (accessible == true) {
			printYesNo = "Yes";
		}
		else {
			printYesNo = "No";
		}
		this.accessible = printYesNo;
	}
	public int getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(int maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public String isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		String printYesNo = null;
		if (utilities == true) {
			printYesNo = "Yes";
		}
		else {
			printYesNo = "No";
		}
		this.utilities = printYesNo;
	}
}
