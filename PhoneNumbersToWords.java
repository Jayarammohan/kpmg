/**
 * Author : Jaya



 * This program takes any 10-digit phone number and produces a list of words matching first letters of the phone number (2 – ABC, 3 – DEF, .. 9 – WXYZ). 
 * The input file is a word list file from the internet.
 * The input file is local to the computer that is running the application
 * The input file should be deposited in the same directory as the program   and it should be called wordsEn.txt
 */
 

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

 

public class PhoneNumbersToWords {
	
	public final static String fileName = "wordsEn.txt";
	List<String> list = new ArrayList<>();
 
 
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getFileName() {
		return fileName;
	}

 	
	/**
	 * read file into stream,, try with resources
	 * @param fileName
	 */
	private void populateWords(String fileName) {
 
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			list = stream.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Error opening input word list file " + fileName);
			System.out.println("The exception=" + e.getMessage());
			System.exit(0);
		}
 	}
	
	public void translateKeypadNumericToAlpha(Map<Integer,Character> digitAlphaMapping) {
		digitAlphaMapping.put(2,'a');
		digitAlphaMapping.put(3,'d');
		digitAlphaMapping.put(4,'g');
		digitAlphaMapping.put(5,'j');
		digitAlphaMapping.put(6,'m');
		digitAlphaMapping.put(7,'p');
		digitAlphaMapping.put(8,'t');
		digitAlphaMapping.put(9,'w');		
	}

	public List<Character> getTheIndividualDigits(String phoneNumber,Map<Integer,Character> digitAlphaMapping) {

		char[] digits = phoneNumber.toCharArray();							//get the individual characters of the phoneNumber String 

		List<Character> digitsList = new ArrayList<>();	
		for(int i=0; i < digits.length; i++) {
			digits[i] = digitAlphaMapping.get((int)digits[i] - '0');
			digitsList.add(digits[i]);
		}
		
		return digitsList;
	}	

	public List<Character> getTheLettersNotPresent(List<Character> digitsList) {
		
		List<Character> alphaNotPresent = new ArrayList<>();
			
 		for(char j = 'a'; j <= 'z'; j++)  {
			if(! digitsList.contains(j)) {
				alphaNotPresent.add(new Character((char)j));
			}
 		}	
 	
 		return alphaNotPresent;
		
	}
	
	public List<String> excludeWordsContainingNotPresentLetters(List<Character> alphaNotPresent, List<String> list) {
		for(int notPresent = 0; notPresent < alphaNotPresent.size(); notPresent++) {
			int temp = notPresent;
			list = list.stream().filter(t-> t.indexOf(alphaNotPresent.get(temp)) < 0 ).collect(Collectors.toList());
		}
		return list;
	}	
	
	/*
	 * To determine if a String contains all numeric
	 */
	public boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}

	/**
	 * Given a phone number, the DTMF phone keypad is referred and the digit is translated into the first character equivalent, eg., 2= A, 3 =D and so on
	 * all the alphabets except the required alphabet set of A-Z are made into a list called alphaNotPresent
	 * and then any word in the word list dictionary containing any of these alphabets are excluded and the rest are printed.
	 * @param args
	 */
	public static void main(String args[]) {

		String phoneNumber = new String();
		if(args.length < 1) {
			System.out.println("Usage java PhoneNumbersToWords thephoneNumber");
			System.exit(0);
		}
		else {
			phoneNumber = args[0];
		}

		 
		PhoneNumbersToWords words = new PhoneNumbersToWords();
		if(! words.isNumeric(phoneNumber)) {
			System.out.println("The phone number should contain only digits, 0-9");
			System.exit(0);
		}
		
		words.populateWords(words.getFileName());
 
   		//0 and 1 do not have alpha equivalents in the DTMF keypad, hence we can ignore them
		phoneNumber = phoneNumber.replace("0", "");
		phoneNumber = phoneNumber.replace("1", "");

		Map<Integer,Character> digitAlphaMapping = new HashMap<>();
		
		words.translateKeypadNumericToAlpha(digitAlphaMapping);
		List<Character> digitsList = words.getTheIndividualDigits(phoneNumber,digitAlphaMapping);
 		List<Character> alphaNotPresent = words.getTheLettersNotPresent(digitsList); 			
 
		words.list = words.excludeWordsContainingNotPresentLetters(alphaNotPresent,words.getList());
        
		System.out.println("The words are ");
		System.out.println("--------------");
		words.getList().forEach(System.out::println);
		
	}
}

