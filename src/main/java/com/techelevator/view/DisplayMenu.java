package com.techelevator.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.campgrounds.*;
import org.springframework.jdbc.core.JdbcTemplate;

public class DisplayMenu {

	private static final Object BOOKING_MENU_OPTION_RESERVATION = "Search for date availability";
	private static final Object BOOKING_MENU_OPTION_RETURN = "Return to previous screen";
	private static List<Object> BOOKING_MENU_OPTIONS = new ArrayList<>();

	public DisplayMenu() {
		BOOKING_MENU_OPTIONS.add(BOOKING_MENU_OPTION_RESERVATION);
		BOOKING_MENU_OPTIONS.add(BOOKING_MENU_OPTION_RETURN);
	}
	
	private Scanner in = new Scanner(System.in);
	private JdbcTemplate jdbcTemplate;
	private Menu menu = new Menu(System.in, System.out);
	private Park parkResult;
	private Campground campGroundResult = null;
	private BigDecimal cost = new BigDecimal(0);
	private String arrivalDate = "";
	private String departureDate = "";
	private int campgroundNum = 0;
	
	public int[] checkAvailability(JDBCSiteDAO jdbcSiteDAO, JDBCCampgroundDAO jdbcCampgroundDAO) {
		
		System.out.println("");
		System.out.println("");
		System.out.print("Which campground (enter 0 to cancel)? __ ");
		campgroundNum = in.nextInt();
		
		if (campgroundNum == 0) {
			System.out.println("Thank you for visiting the National Park Campsite Revervation Service!");
			System.out.println("----------------------------------------------------------------------");
			System.out.println("");
			return null;
			
		}
		System.out.println("");		
		System.out.print("Enter date of arrival in the following format: yyyy-mm-dd) >>> ");
		arrivalDate = in.next();
		
		System.out.println("");
		System.out.print("Enter date of departure in the following format: yyyy-mm-dd) >>> ");
		departureDate = in.next();
		
		campGroundResult = jdbcCampgroundDAO.getCampgroundByCamgroundId(campgroundNum);
		cost = campGroundResult.getDailyFee().multiply(calculateLengthOfStay(arrivalDate, departureDate));
		
		List<Site> sites = jdbcSiteDAO.getSitesByCampgroundIdAndDates(campgroundNum, arrivalDate, departureDate);
		int[] siteNumbersToChooseFrom = new int[5];
		
		System.out.println("");	
		System.out.println("");	
		System.out.println("Results Matching Your Search Criteria");
		System.out.println("");	
		System.out.printf("%-10s %-10s %-10s %-15s %-10s %-10s%n", "Site No.",  "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost");
		for (int i = 0; i < sites.size(); i++) {
			siteNumbersToChooseFrom[i] = sites.get(i).getSiteNumber();
			System.out.printf("%-10s %-10s %-10s %-15s %-10s %-10s%n", sites.get(i).getSiteNumber(), sites.get(i).getMaxOccupancy(), 
					sites.get(i).isAccessible(), sites.get(i).getMaxRVLength(), sites.get(i).isUtilities(), "$" + cost );
		
		}
		return siteNumbersToChooseFrom;
	}
	
	public int makeReservation(JDBCReservationDAO jdbcReservationDAO, JDBCSiteDAO jdbcSiteDAO, int[] siteNumbers) {

		System.out.println("");	
		System.out.print("Which site should be reserved (enter 0 to cancel)? __ ");
		int siteNum = in.nextInt();
		for(int a : siteNumbers) {
			if (a == siteNum) {
				long siteId = jdbcSiteDAO.getSiteIdByCampgroundIdAndSiteNumber(siteNum, campgroundNum);

				System.out.println("");
				System.out.print("Under what name should the reservation be made? __ ");
				String name = in.next();

				jdbcReservationDAO.addReservation(siteNum, name, arrivalDate, departureDate);

				Reservation reservation = jdbcReservationDAO.getReservation(siteNum, name, arrivalDate, departureDate);
				long reservationId = reservation.getReservationId();
				System.out.println("");
				System.out.println("----------------------------------------------------------------------");
				System.out.println("----------------------------------------------------------------------");
				System.out.println("Your reservation has been made and the confirmation number is " + reservationId + "!");
				System.out.println("");
				System.out.println("Thank you for choosing the National Park Campsite Reservation Service!");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				siteNum = 0;
				return siteNum;
			}
			else if (siteNum == 0) {
				System.out.println("Thank you for visiting the National Park Campsite Revervation Service!");
				System.out.println("----------------------------------------------------------------------");
				System.out.println("");
				return siteNum;
			}
			else {
				System.out.println("You did not choose a valid campsite number. Try again!");
				break;
			} 
		}
			
		return siteNum;
    }

//	public void confirmingReservationId(Reservation reservation){
//	    long reservationId = reservation.getReservationId();
//
//        System.out.println("The reservation has been made and the confirmation Id is " + reservationId);
//    }

	
	public BigDecimal calculateLengthOfStay(String arrival, String departure) {
		LocalDate arrivalDate = LocalDate.parse(arrival);
		LocalDate departureDate = LocalDate.parse(departure);
		
		BigDecimal daysBetween = new BigDecimal(ChronoUnit.DAYS.between(arrivalDate, departureDate));
		
		return daysBetween;
	}
	

	public Object displayBookingMenu() {
		Object choice = menu.getChoiceFromOptions(BOOKING_MENU_OPTIONS);
		
		return choice;
	}
	
	@SuppressWarnings("unchecked")
	public Park displayParks(JDBCParkDAO jdbcParkDAO) {
		
		System.out.println("Select a Park for Further Details");
		System.out.println("");
		
		List<Park> parks = jdbcParkDAO.getAllParks();
		
		
		Park parkResult = (Park) menu.getChoiceFromOptions((List)parks);


		//viewParkInfo(parkResult);
	//	viewCampgroundInfo(jdbcCampgroundDAO, parkResult);


		
		//viewParkInfo(parkResult);

		return parkResult;
		}   
			
	public void viewParkInfo(Park parkResult) {
		System.out.println("");
		System.out.println(parkResult.getParkName() + " National Park");
		System.out.println("");
		System.out.printf("%-20s %-20s%n", "Location:", parkResult.getParkLocation());
		System.out.printf("%-20s %-20s%n", "Established:", parkResult.getEstablished());
		System.out.printf("%-20s %-20s%n", "Area:", parkResult.getParkArea() + " sq km");
		System.out.printf("%-20s %-20s%n", "Annual Visitors:", parkResult.getVisitorNum());
		System.out.println("");
		
		StringBuilder sb = new StringBuilder(parkResult.getParkDescription());
		int i = 0;
		while (i + 100 < sb.length() && (i = sb.lastIndexOf(" ", i + 100)) != -1) {
			sb.replace(i, i+1, "\n");
		}
		System.out.println(sb.toString());
		System.out.println("");
	}
	
			
	public void viewCampgroundInfo(JDBCCampgroundDAO jdbcCampgroundDAO, Park parkResult) {
		
		System.out.println("");
		System.out.println(parkResult.getParkName() + " National Park Campgrounds");
		System.out.println("");
		System.out.printf("%-5s %-20s %-10s %-10s %-10s%n", " ", "Name", "Open", "Close", "Daily Fee");
		
		List<Campground> campgrounds = jdbcCampgroundDAO.getCampgroundByParkId(parkResult.getParkId());
		
		for (int i = 0; i < campgrounds.size(); i++) {
			
			System.out.printf("%-5s %-20s %-10s %-10s %-10s%n", "# " + (i+1), campgrounds.get(i).getCampgroundName(), campgrounds.get(i).getOpenFrom(),
					campgrounds.get(i).getOpenTo(), "$" + campgrounds.get(i).getDailyFee());
		
		}
		
	}


}
