package com.techelevator.campgrounds;

import java.util.List;

public interface ParkDAO {
	
	public List<Park> getAllParks();
	
	public String getFirstPark();

}
