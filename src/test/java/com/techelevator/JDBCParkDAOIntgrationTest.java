package com.techelevator;

import static org.junit.Assert.assertEquals;

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
import com.techelevator.campgrounds.Park;

public class JDBCParkDAOIntgrationTest {
	
	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO jdbcParkDAO;
	
	
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
		jdbcParkDAO = new JDBCParkDAO(dataSource);
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
	public void read_all_parks() {
		List<Park> results = jdbcParkDAO.getAllParks();
		
		assertEquals(3, results.size());
//		assertParksAreEqual(parka, parkb);
	}

	private void assertParksAreEqual(Park expected, Park actual){
		assertEquals(expected.getParkId(), actual.getParkId());
		assertEquals(expected.getParkName(), actual.getParkName());
		assertEquals(expected.getParkArea(), actual.getParkArea());
		assertEquals(expected.getEstablished(), actual.getEstablished());
		assertEquals(expected.getParkLocation(), actual.getParkLocation());
		assertEquals(expected.getVisitorNum(), actual.getVisitorNum());
		assertEquals(expected.getParkDescription(), actual.getParkDescription());

//		assertEquals("Acadia", results.get(0).getParkName());

	}


}
