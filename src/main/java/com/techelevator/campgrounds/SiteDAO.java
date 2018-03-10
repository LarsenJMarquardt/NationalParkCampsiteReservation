package com.techelevator.campgrounds;

import java.util.List;

public interface SiteDAO {
	
	public List<Site> getSitesByCampgroundIdAndDates(long campgroundId, String arrival, String departure);
}
