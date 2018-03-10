package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import com.techelevator.campgrounds.Park;

public class Menu {
	
	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}
	
	public Object getChoiceFromOptions(List<Object> options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(List<Object> options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if(selectedOption > 0 && selectedOption <= options.size()) {
				choice = options.get(selectedOption -1);
			}
		} catch(NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if(choice == null) {
			out.println("\n*** Sorry, "+userInput+" is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(List<Object> options) {
		out.println();
		
		for(int i = 0; i < options.size(); i++) {
			int optionNum = i+1;
			
			out.println(optionNum+") " + options.get(i).toString());
//			if(options.get(0).equals(Park)) {
//
//            }
		}
		out.print("\nChoose from options above >>> ");
		out.flush();
	}

}
