package com.techelevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class WordCount {

public static void main(String[] args) {

    aliceInWonderLand("alices_adventures_in_wonderland.txt"); }
	
	
	public static void aliceInWonderLand(String path) {
				 

	   int wordCount = 0;
	   int sentenceCount = 0;
	   File storyLine = new File("alices_adventures_in_wonderland.txt");
			
		

	  try ( Scanner scanner = new Scanner (storyLine)) { 
				
	  while (scanner.hasNextLine()) {
		String lineFromFile = scanner.nextLine(); 
			if (!lineFromFile.isEmpty()){
					
		String [] words = lineFromFile.trim().split("\\s+");
			 for (String word : words) {
					
					
							
					
			if (word.contains(".") || word.contains("!") || word.contains("?")) {
				sentenceCount += 1;
		}
	    }	
				wordCount+= words.length;
	   }
			System.out.println(wordCount);
			System.out.println(sentenceCount);
	 }	
	  }
				catch (FileNotFoundException e) {
				
			}
		}	

}		
			
		
	
	

