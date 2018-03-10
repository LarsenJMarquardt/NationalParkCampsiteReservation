package com.techelevator.campgrounds;

import java.util.Date;

public class Park {
	
	private long parkId;
	private String parkName;
	private String parkLocation;
	private Date established;
	private int parkArea;
	private int visitorNum;
	private String parkDescription;
	
	public long getParkId() {
		return parkId;
	}
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getParkLocation() {
		return parkLocation;
	}
	public void setParkLocation(String parkLocation) {
		this.parkLocation = parkLocation;
	}
	public Date getEstablished() {
		return established;
	}
	public void setEstablished(Date established) {
		this.established = established;
	}
	public int getParkArea() {
		return parkArea;
	}
	public void setParkArea(int parkArea) {
		this.parkArea = parkArea;
	}
	public int getVisitorNum() {
		return visitorNum;
	}
	public void setVisitorNum(int visitorNum) {
		this.visitorNum = visitorNum;
	}
	public String getParkDescription() {
		return parkDescription;
	}
	public void setParkDescription(String parkDescription) {
		this.parkDescription = parkDescription;
	}
	
	public String toString() {
		
		return getParkName();
	}
	

}
