package com.techelevator.campgrounds;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO{

private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getCampgroundByParkId(long parkId) {
		ArrayList<Campground> campgrounds = new ArrayList<>();
		String sqlListCampgrounds = "SELECT * FROM campground WHERE park_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlListCampgrounds, parkId);
		while(results.next()) {
			Campground theCampground = mapRowToCampground(results);
			campgrounds.add(theCampground);
		}
		return campgrounds;
	}
	
	@Override
	public Campground getCampgroundByCamgroundId(long campgroundId) {
		Campground campground = null;
		String sqlCampgroundSearch = "SELECT * FROM campground WHERE campground_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampgroundSearch, campgroundId);
		while(results.next()) {
			campground = mapRowToCampground(results);
		}
		return campground;	
	}

	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground = new Campground();
		theCampground.setCampgroundId(results.getLong("campground_id"));
		theCampground.setCampgroundParkId(results.getLong("park_id"));
		theCampground.setCampgroundName(results.getString("name"));
		theCampground.setOpenFrom(results.getString("open_from_mm"));
		theCampground.setOpenTo(results.getString("open_to_mm"));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		return theCampground;
	}

	

	

}
