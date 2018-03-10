package com.techelevator.campgrounds;

import java.util.List;

public interface CampgroundDAO {

		public List<Campground> getCampgroundByParkId(long parkId);
		
		public Campground getCampgroundByCamgroundId(long campgroundId);
		
		
}
