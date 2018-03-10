package com.techelevator.campgrounds;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO{
 
private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getSitesByCampgroundIdAndDates(long campgroundId, String arrival, String departure) {
		ArrayList<Site> sites = new ArrayList<>();
		String sqlListSites = "SELECT distinct site.site_id, site.campground_id, site.site_number, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities FROM site "
				+ "JOIN campground ON site.campground_id = campground.campground_id "
				+ "WHERE site.campground_id = ? AND site.site_id NOT IN "
				+ "(SELECT site.site_id FROM site "
				+ "JOIN reservation ON reservation.site_id = site.site_id "
				+ "WHERE ((to_date(?, 'YYYY/MM/DD')) <= reservation.to_date "
				+ "AND (to_date(?, 'YYYY/MM/DD')) >= reservation.from_date)) "
				+ "ORDER BY site.site_number LIMIT 5";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlListSites, campgroundId, arrival, departure);
		while(results.next()) {
			Site theSite = mapRowToSite(results);
			sites.add(theSite);
		}
		return sites;
	}
	
	public long getSiteIdByCampgroundIdAndSiteNumber(int siteNum, long campgroundId) {
		Site theSite = null;
		String sqlSiteIdSearch = "SELECT * FROM site WHERE site_number = ? AND campground_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSiteIdSearch, siteNum, campgroundId);
		while(results.next()) {
			theSite = mapRowToSite(results);
		}
		return theSite.getSiteId();
	}

	private Site mapRowToSite(SqlRowSet results) {
		Site theSite = new Site();
		theSite.setSiteId(results.getLong("site_id"));
		theSite.setSiteNumber(results.getInt("site_number"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setMaxOccupancy(results.getInt("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMaxRVLength(results.getInt("max_rv_length"));
		theSite.setUtilities(results.getBoolean("utilities"));
		return theSite;
	}
	
}
