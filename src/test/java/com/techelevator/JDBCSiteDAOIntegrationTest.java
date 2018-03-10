package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campgrounds.JDBCParkDAO;
import com.techelevator.campgrounds.JDBCSiteDAO;
import com.techelevator.campgrounds.Park;
import com.techelevator.campgrounds.Site;

public class JDBCSiteDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO jdbcSiteDAO;
	
	
	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections 
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}
	
	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		jdbcSiteDAO = new JDBCSiteDAO(dataSource);
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	/* This method provides access to the DataSource for subclasses so that 
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Test
	public void check_if_sites_returned_list_is_correct_size_and_first_site_is_listed_twice() {

		long campgroundId = 1;
		String arrival = "2018-02-25";
		String departure = "2018-02-28";
		
		List<Site> results = jdbcSiteDAO.getSitesByCampgroundIdAndDates(campgroundId, arrival, departure);
		
		assertNotNull(results);
		assertEquals(5, results.size());
		assertEquals(1, results.get(0).getSiteNumber());
		assertEquals(1, results.get(1).getSiteNumber());
		
	}
	
	@Test
	public void check_if_sites_returned_list_is_correct_size_and_first_site_is_not_listed_twice() {

		long campgroundId = 1;
		String arrival = "2018-02-25";
		String departure = "2018-02-28";
		
		List<Site> results = jdbcSiteDAO.getSitesByCampgroundIdAndDates(campgroundId, arrival, departure);
		
		assertNotNull(results);
		assertEquals(5, results.size());
		assertEquals(1, results.get(0).getSiteNumber());
		
	}
	
	@Test
	public void check_if_site_id_by_campground_id_and_site_number_returns_correct_id() {
		
		int siteNum = 10;
		long campgroundId = 2;
		long result = 0;
		
		assertEquals(286, jdbcSiteDAO.getSiteIdByCampgroundIdAndSiteNumber(siteNum, campgroundId));
		
	}
	
}
