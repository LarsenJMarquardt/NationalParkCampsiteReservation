package com.techelevator.campgrounds;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		ArrayList<Park> parks = new ArrayList<>();
		String sqlListParks = "SELECT * FROM park ORDER BY name";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlListParks);
		
		while (results.next()) {
			Park thePark = mapRowToPark(results);
			parks.add(thePark);
		}
		
		return parks;
	}
	
	@Override
	public String getFirstPark() {
		String firstPark = "SELECT name FROM park WHERE park_id = 1";
		SqlRowSet result = jdbcTemplate.queryForRowSet(firstPark);
		return firstPark;
	}
	

	
	public Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();
		thePark.setParkId(results.getLong("park_id"));
		thePark.setParkName(results.getString("name"));
		thePark.setParkLocation(results.getString("location"));
		thePark.setEstablished(results.getDate("establish_date"));
		thePark.setParkArea(results.getInt("area"));
		thePark.setVisitorNum(results.getInt("visitors"));
		thePark.setParkDescription(results.getString("description"));
		
		return thePark;
	}

	

}
