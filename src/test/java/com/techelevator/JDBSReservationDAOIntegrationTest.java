package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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

import com.techelevator.campgrounds.JDBCReservationDAO;
import com.techelevator.campgrounds.JDBCSiteDAO;
import com.techelevator.campgrounds.Reservation;
import com.techelevator.campgrounds.Site;

public class JDBSReservationDAOIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;
	private JDBCReservationDAO jdbcReservationDAO;
	
	
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
		jdbcReservationDAO = new JDBCReservationDAO(dataSource);
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
	public void get_reservation_from_site_id_from_and_to_dates_and_name() {

		long site_id = 19;
		String name = "Schiller Family Reservation";
		String arrival = "2018-02-23";
		String departure = "2018-02-26";
		
		Reservation result = jdbcReservationDAO.getReservation(site_id, name, arrival, departure);
				
		assertNotNull(result);
		assertEquals(16, result.getReservationId());
		
	}
	
	@Test
	public void verify_length_of_stay_one_day() {
		
		String arrival = "2018-02-02";
		String departure = "2018-02-03";
		
		long result = jdbcReservationDAO.calculateLengthOfStay(arrival, departure);
		
		assertEquals((long)1, result);
	}
	
	@Test
	public void verify_length_of_stay_many_days() {
		
		String arrival = "2018-02-02";
		String departure = "2018-04-26";
		
		long result = jdbcReservationDAO.calculateLengthOfStay(arrival, departure);
		
		assertEquals((long)83, result);
	}
	
	@Test
	public void try_to_calculate_length_of_stay_with_invalid_dates() {
		
		String arrival = "2018-02-02";
		String departure = "03-02-2018";
		
		long result = jdbcReservationDAO.calculateLengthOfStay(arrival, departure);
		
		assertNotEquals((long)1, result);
	}
	
	//Test not finished
	@Test
	public void add_new_valid_reservation() {
		
		int siteNum = 0;
		String name = null;
		String arrival = null;
		String departure = null;
		
		jdbcReservationDAO.addReservation(siteNum, name, arrival, departure);
		
	}
	

}
