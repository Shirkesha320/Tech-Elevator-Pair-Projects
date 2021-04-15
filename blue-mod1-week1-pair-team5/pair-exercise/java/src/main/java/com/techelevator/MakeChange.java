package com.techelevator;

import java.util.Scanner;

/*
 Write a command line program which prompts the user for the total bill, and the amount tendered. It should then
 display the change required.

 $ java MakeChange
 Please enter the amount of the bill: 23.65
 Please enter the amount tendered: 100.00
 The change required is 76.35
 */
public class MakeChange {

	public static void main(String[] args) {
		
		// 1) Get amount of bill and amount tendered from user.
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Please enter the amount of the bill: ");
		String billString = scanner.nextLine();
		
		System.out.print("Please enter the amount tendered: ");
		String tenderedString = scanner.nextLine();
		
		// 2) Convert inputs to doubles, then calculate the change required.

		double billNum = Double.parseDouble(billString);
		double tenderedNum = Double.parseDouble(tenderedString);
		 		
		double change = tenderedNum - billNum;
		
		// 3) Print out change required.
		
		System.out.println("The change required is: " + change);
		 
	}

}
