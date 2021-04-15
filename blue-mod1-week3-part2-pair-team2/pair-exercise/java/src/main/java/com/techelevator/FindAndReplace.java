package com.techelevator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FindAndReplace {

	public static void main(String[] args) {
		
		findAndReplace ("destination.txt");
	}

	private static void findAndReplace(String replace) {
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("Enter the file you are looking for: ");
		
		String filePath = userInput.nextLine();
		
		//declaring source file
		File sourceFile = new File (filePath);
		
//		//source file
//		filePath = "alices_adventures_in_wonderland.txt";
		
//		try {
//			Files.copy(sourceFile.toPath(), destinationFile.toPath());
//		}
//			catch (Exception e) {
//				System.out.println("The file does not exist!!");
//			} 
		//Prompts user to enter the original word
		System.out.println("What is the word you are replacing?: ");
		
		String originalWord = userInput.nextLine();
		
		//Prompts user to enter the replacement word
		System.out.println("What is the word you are replacing WITH?: ");
		
		String replaceWord = userInput.nextLine();
		
		File destinationFile = new File("destination.txt");
		
		boolean append = destinationFile.exists() ? true : false;
		
		try (Scanner scanner = new Scanner(sourceFile))	{
			String lineWithReplacement = "";
			int lineNum = 1;
			while(scanner.hasNextLine()) {
				String lineFromFile = scanner.nextLine();
				lineNum ++;
				lineWithReplacement = lineFromFile;
				
				//replace originalWord w/ replaceWord
				if (lineFromFile.contains(originalWord)) {
				
					lineWithReplacement = lineFromFile.replace(originalWord, replaceWord);
				} 
				try (PrintWriter writer = new PrintWriter(new FileOutputStream(destinationFile.getAbsoluteFile(), append))) {
					writer.append(lineWithReplacement);
				} catch (IOException e) {
					System.out.println("Exception: " + e.getMessage());
				} 
			}
		}	catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
			
		}
	}}
				

				
		
	




//Write a program that can be used to replace all occurrences 
//of one word with another word.
//The program should prompt the user for the following values:


//alices_adventures_in_wonderland.txt

//The search word
//The WORD TO REPLACE the search word with
//The SOURCE FILE must be an EXISTING FILE.				
//If the user enters an INVALID SOURCE FILE, the program indicate this and exits.

//THE DESTINATION FILE. 
//The program creates a COPY of the SOURCE FILE with the requested replacements at this
//location. 

//If the file already exists, it should be overwritten. 

//If the user enters an invalid destination file, the
//program indicates this and exits.
