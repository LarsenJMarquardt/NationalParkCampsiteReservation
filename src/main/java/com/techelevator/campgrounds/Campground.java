package com.techelevator.campgrounds;

import java.math.BigDecimal;

public class Campground {
		
		private long campgroundId;
		private long campgroundParkId;
		private String campgroundName;
		private String openFrom;
		private String openTo;
		private BigDecimal dailyFee;
		
		public long getCampgroundId() {
			return campgroundId;
		}
		public void setCampgroundId(long campgroundId) {
			this.campgroundId = campgroundId;
		}
		public long getCampgroundParkId() {
			return campgroundParkId;
		}
		public void setCampgroundParkId(long campgroundParkId) {
			this.campgroundParkId = campgroundParkId;
		}
		public String getCampgroundName() {
			return campgroundName;
		}
		public void setCampgroundName(String campgroundName) {
			this.campgroundName = campgroundName;
		}
		public String getOpenFrom() {
			return openFrom;
		}
		public void setOpenFrom(String openFrom) {
			String month = openFrom;
			String monthString = null;
			switch (month) {
			case "01": monthString = "January";
			break;
			case "02": monthString = "February";
			break;
			case "03": monthString = "March";
			break;
			case "04": monthString = "April";
			break;
			case "05": monthString = "May";
			break;
			case "06": monthString = "June";
			break;
			case "07": monthString = "July";
			break;
			case "08": monthString = "August";
			break;
			case "09": monthString = "September";
			break;
			case "10": monthString = "October";
			break;
			case "11": monthString = "November";
			break;
			case "12": monthString = "December";
			break;
			}
			this.openFrom = monthString;
		}
		public String getOpenTo() {
			return openTo;
		}
		public void setOpenTo(String openTo) {
			String month = openTo;
			String monthString = null;
			switch (month) {
			case "01": monthString = "January";
			break;
			case "02": monthString = "February";
			break;
			case "03": monthString = "March";
			break;
			case "04": monthString = "April";
			break;
			case "05": monthString = "May";
			break;
			case "06": monthString = "June";
			break;
			case "07": monthString = "July";
			break;
			case "08": monthString = "August";
			break;
			case "09": monthString = "September";
			break;
			case "10": monthString = "October";
			break;
			case "11": monthString = "November";
			break;
			case "12": monthString = "December";
			break;
			}
			this.openTo = monthString;
		}
		public BigDecimal getDailyFee() {
			return dailyFee;
		}
		public void setDailyFee(BigDecimal dailyFee) {
			this.dailyFee = dailyFee;
		}
}
