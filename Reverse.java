 /**
 * 
 * @author Jaya
 * This program reverses a Character array's  words
 * For instance, "This is a test" becomes "test a is This"
 * This, it effects by implementing 2 passes
 * In the first pass, the character array simply gets reversed
 * In the second pass, each of the word in the character array gets reversed again restoring the original word
 */
  public class Reverse {
	
 	
	public static void main(String args[]) {

		Reverse re = new Reverse();
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
 		
 		//The first pass
 		//where the character array literally gets reversed
 		//Eg., This is a test becomes tset a si sihT
 		char[] reversedCharArray = re.reverseChars(charArray);
 		
 		//Now, the second pass where earch word in the character array gets reversed individually
 		char[] result = re.secondPassReverse(reversedCharArray);
 		System.out.println("The reversed string=" + new String(result));
	}
	
	/**
	 * Second pass of reversal where each reversed word is again reversed restoring the original word 
	 */
	char[] secondPassReverse(char[] source) {
		
		System.out.println("string after first pass reversal=" + new String(source));
		int length = source.length;
		//in this for loop, we locate next space (which is a word end marker) and then, reverse each word
		for(int i =0; i < length; i++) {
			int nextSpaceIndex = i;
			while((nextSpaceIndex < length)  && (source[nextSpaceIndex] != ' ')) {  	//traverse through the char array to determine the next space
				nextSpaceIndex ++;	
			}
 			source = reverseChars(source,i,nextSpaceIndex); 	// reverse this particular word which is bounded by i and nextSpaceIndex
 			i = nextSpaceIndex;
 		}
		return source;
	}
	
	/**
	 * This function does the reversal of the given character array starting from 'from' and upto 'upto'
	 * For eg., 
	 * if the char array = tset a si sihT, from =0 and upto=4, then it returns test a si sihT
	 * if the char array = test a si sihT, from =5 and upto=6, then it returns test a si sihT
	 * if the char array = test a si sihT, from =7 and upto=9, then it returns test a is sihT
	 * if the char array = test a is sihT, from =10 & upto=14, then it returns test a is This

	 * This enables us to reverse a single word only from the character array comprising of many words
	 * @param original
	 * @return
	 */
	char[] reverseChars(char[] original, int from,int upto) {
		
		int length = (upto-from);
		for(int i=0; i < length/2 ; i++) {
 			original = swap(original, from+i, from+length-i-1);
		}	
		return original;
	}
		
	
	/**
	 * This function literaly reverses the input character array called original
	 * It does this by calling the overloaded reverseChars function by passing 0 and length as bounds
	 * @param original
	 * @return
	 */
	char[] reverseChars(char[] original) {
		return reverseChars(original,0,original.length);
	}

	/**
	 * swap two characters given the indices
	 * @param list
	 * @param i
	 * @param j
	 * @return
	 */
	public char[] swap(char[] list, int i, int j) {
		char temp = list[i];
		list[i] = list[j];
		list[j] = temp;	
			
		return list;
	}
}	