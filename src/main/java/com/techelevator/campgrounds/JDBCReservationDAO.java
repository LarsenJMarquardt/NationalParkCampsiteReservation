package com.techelevator.campgrounds;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO{

private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> getAllReservationsForThisSite(long siteId) {
		ArrayList<Reservation> reservations = new ArrayList<>();
//		String sqlListReservations = "SELECT ...";
//
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlListReservation, siteId);
//		while(results.next()) {
//			Reservation theReservation = mapRowToReservation(results);
//			reservations.add(theReservation);
//		}
		return reservations;
	}

	public Reservation getReservation(long siteId, String name, String fromDate, String toDate) {
		LocalDate arrivalDate = LocalDate.parse(fromDate);
		LocalDate departureDate = LocalDate.parse(toDate);
		Reservation reservation = null;
		String sqlGetReservation = "SELECT * FROM reservation WHERE site_id = ? AND from_date = ? AND to_date = ? AND name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservation, siteId, arrivalDate, departureDate, name);
		while(results.next()) {
			reservation = mapRowToReservation(results);
		}
		return reservation;
	}
	
	@Override
	public long calculateLengthOfStay(String arrival, String departure) {
		LocalDate arrivalDate = LocalDate.parse(arrival);
		LocalDate departureDate = LocalDate.parse(departure);
		
		long daysBetween = ChronoUnit.DAYS.between(arrivalDate, departureDate);
		
		return daysBetween;
	}
	
	public void addReservation(int siteNum, String name, String arrival, String departure) {
	LocalDate today = LocalDate.now();
	LocalDate arrivalDate = LocalDate.parse(arrival);
	LocalDate departureDate = LocalDate.parse(departure);
	
	String sqlAddReservationToTable = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) VALUES (?, ?, ?, ?, ?)";
	jdbcTemplate.update(sqlAddReservationToTable, siteNum, name, arrivalDate, departureDate, today);
	}

	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation theReservation = new Reservation();
		theReservation.setReservationId(results.getLong("reservation_id"));
		theReservation.setSiteId(results.getLong("site_id"));
		theReservation.setName(results.getString("name"));
		theReservation.setFromDate(results.getDate("from_date"));
		theReservation.setToDate(results.getDate("to_date"));
		theReservation.setCreateDate(results.getDate("create_date"));
		return theReservation;
	}

	
}
