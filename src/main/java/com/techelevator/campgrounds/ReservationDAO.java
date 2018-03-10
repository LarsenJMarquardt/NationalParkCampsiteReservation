package com.techelevator.campgrounds;

import java.util.List;

public interface ReservationDAO {

	public List<Reservation> getAllReservationsForThisSite(long siteId);
	
	public long calculateLengthOfStay(String arrival, String departure);
	
}
