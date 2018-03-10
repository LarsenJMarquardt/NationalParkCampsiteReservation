package com.techelevator;

import java.util.List;

import javax.sql.DataSource;

import com.techelevator.campgrounds.*;
import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.view.DisplayMenu;

public class CampgroundCLI {
	
	private static final Object BOOKING_MENU_OPTION_RESERVATION = "Search for date availability";;
	private JDBCParkDAO jdbcParkDAO = null;
	private JDBCCampgroundDAO jdbcCampgroundDAO = null;
	private JDBCSiteDAO jdbcSiteDAO = null;
	private JDBCReservationDAO jdbcReservationDAO = null;
	
	private DisplayMenu menu = new DisplayMenu();

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	

	public CampgroundCLI(DataSource datasource) {
		jdbcParkDAO = new JDBCParkDAO(datasource);
		jdbcCampgroundDAO = new JDBCCampgroundDAO(datasource);
		jdbcSiteDAO = new JDBCSiteDAO(datasource);
		jdbcReservationDAO = new JDBCReservationDAO(datasource);
	}
	
	public JDBCParkDAO getJdbcParkDAO() {
		return jdbcParkDAO;
	}


	public void run() {
		while (true) {

			//menu.displayParks(jdbcParkDAO);
			System.out.println("************************************************************************");
			System.out.println("**     WELCOME TO THE NATIONAL PARKS CAMPSITE RESERVATION SERVICE     **");
			System.out.println("**                                                                    **");
			System.out.println("************************************************************************");
			System.out.println("");
			
			Park a = menu.displayParks(jdbcParkDAO);

			menu.viewParkInfo(a);

			menu.viewCampgroundInfo(jdbcCampgroundDAO, a);


			while (true) {

				Object choice = menu.displayBookingMenu();
				
				menu.viewCampgroundInfo(jdbcCampgroundDAO, a);

				if (choice.equals(BOOKING_MENU_OPTION_RESERVATION)) {

					int[] siteNumbers = menu.checkAvailability(jdbcSiteDAO, jdbcCampgroundDAO);
					
					if (siteNumbers == null) {
						break;
					}
					
					while (true) {

						int siteNum = menu.makeReservation(jdbcReservationDAO, jdbcSiteDAO, siteNumbers);
					
						if (siteNum == 0) {
							
							break;
						}
					
					}
				}
				break;
			}
		}
	}
}
