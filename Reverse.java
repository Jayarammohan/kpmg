/**
 * This program reverses a character array without allocating any data structures or without using any built in java functions

 * If the input is "The Blessing of the Lord",   the output = "Lord the of Blessing The"
 * To effect that, two main things are performed
 * First
 * =====
 * we formulate a schme of how to swap words, ie., which words are to be swapped
 * Example
 * -------
 * 
 * Words numbered
 * 
 * 1		2	3	4	5
 * 2		1	3	4	5
 * 2		3	1	4	5
 * 2		3	4	1	5
 * 2		3	4	5	1
 * 
 * 3		2	4	5	1
 * 3		4	2	5	1
 * 3		4	5	2	1
 * 
 * 4		3	5	2	1
 * 4		5	3	2	1
 * 
 * 5		4	3	2	1
 * 
 * 
 * In other words, the words are cascading through slot by slot to eventually arriving at their required destination by swapping two adjacent words.
 * --------------------------------------------------------------------------------------------------------------------------------------------------
 * 
 * 
 * Second
 * ======
 * we swap two different adjacent words, taking good care of the space in between them
 * Assumption: The words should be separated by a single space as the delimiter
 * If more than 1 white space is used in the input string, it is reduced to single white space
 * 
 * 
 * 
 * Test Results
 * =============
 * java Reverse  "1 12 123 1234 12345 123456 1234567 12345678 123456789 1234567890"
 * the input=1 12 123 1234 12345 123456 1234567 12345678 123456789 1234567890
 * Reversed=1234567890 123456789 12345678 1234567 123456 12345 1234 123 12 1
 */

public class Reverse {
	
 	
	public static void main(String args[]) {

		String inputString = new String();
		if(args.length < 1) {
			System.out.println("Usage java Shift input");
			System.exit(0);
		}
		else {
			inputString = args[0].trim().replaceAll(" +", " ");	//reduce many white spaces to a single one in addition to getting rid of leading and trailing white spaces
		}
		
		System.out.println("the input=" + inputString);

 		char[] charArray = inputString.toCharArray();			//from this point onwards, the reversal is effected with merely swapping characters of the input array, without any new usage nor using any java built in functions
		
		//determine the total number of words in this char array
		int totWords = 0;
		for(int i=0; i < charArray.length; i++)
			if(charArray[i] == ' ')
				totWords++;
		
		totWords++;

  		for(int i=0, count =0; count < totWords-1; count++) {
			for(int j=i+1; j  < totWords-count; j++) {
   				charArray = swapWord(charArray,j-1,j); 				
  			}
		}
 		System.out.println("Reversed=" + new String(charArray));
	}
	
	//determine the start of the nth word
	public static int determineStart(char[] input,int word) {
		int numberOfSpacesTraversed =0;
		int start =0;
		
		for(int i=0; i < input.length; i++) {
			if(numberOfSpacesTraversed == word) {		//we have located the start of word1
				start = i;
				return start;
			}
		
			if(input[i]  == ' ')
				numberOfSpacesTraversed++;
		}
		return input.length;
	}


	public static int determineEnd(char[] input, int from) {
		
		int end = 0;
		for(int i= from+1; i < input.length; i++) {
			if (input[i] == ' ') {
				end = i-1;
				break;
			}	
		}
		if(end == 0)
			end = input.length-1;

		return end;
	}
	
	public static char[] swapWord(char[] input, int word1, int word2) {
		int start1=0, end1=0, start2=0, end2=0;
   		
		//determine start1
		start1 = determineStart(input,word1);
		
		//determine end1
		end1 = determineEnd(input,start1);
		
		//determine start2
		start2 = determineStart(input,word2);
		
		//determine end2
		end2 = determineEnd(input,start2);
 
		//determine sum of all lengths till the swapped word (the second one)
		int lengthOfAllWordsUptoWord2 = 0;
		int lengthOfAllWordsUptoWord1 = 0;
		
 		int lengthOfSecondWord = end2 - start2 +1;
  		lengthOfAllWordsUptoWord2 = determineLengthOfAllWordsUpto (input,word2+1);
		
		lengthOfAllWordsUptoWord1 = determineLengthOfAllWordsUpto (input,word1);
  				
		int toShiftTo = start1;
		for(int i = start2; i <= end2; i++) {
			input = shiftCharsTo(input,i,toShiftTo);
			toShiftTo++;
		}
				
		//now bring the blank from last position to the position after the first word		
    		
		input = shiftCharsTo(input,lengthOfAllWordsUptoWord2-1,   (lengthOfAllWordsUptoWord1 +lengthOfSecondWord));
 		return input;

	}
	
	public static int determineLengthOfAllWordsUpto (char[] input, int word) {
		int lengthOfAllWordsUpto  = determineStart(input,word);
 		
		return lengthOfAllWordsUpto ;
	}
	
	/**
	 * To shift n number of characters from 1 start location to another by invoking swap function 
	 * @param input
	 * @param source
	 * @param destn
	 * @return
	 */
	public static char[] shiftCharsTo(char[] input, int source, int destn) {
	
		for(int i = source; i > destn ; i--) {
 			swap(input,i,i-1);
		}
		return input;	
	}
	
	/**
	 * swap two words given the indices
	 * @param list
	 * @param i
	 * @param j
	 * @return
	 */
	public static char[] swap(char[] list, int i, int j) {
		char temp = list[i];
		list[i] = list[j];
		list[j] = temp;	
		
		return list;
	}
}
