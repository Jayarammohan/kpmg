 /**
 * 
 * @author Jaya
 * This program reverses a Character array's  words
 * For instance, "This is a test" becomes "test a is This"
 * This, it effects by implementing 2 passes
 * In the first pass, the character array simply gets reversed
 * In the second pass, each of the word in the character array gets reversed again restoring the original word
 * The updated second version of the source code shows how much progress I have made in java 8. 
 */
 import java.util.stream.*;

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

        String reversed =  new StringBuilder(inputString).reverse().toString();
  		System.out.println(reversed.toString());
         
        String[] fields = reversed.split(" ");
        StringBuilder sb = new StringBuilder();
        IntStream.range(0,fields.length).forEach(x->sb.append(new StringBuilder(fields[x]).reverse().toString() + " "));
        System.out.println(sb);


	}
}
