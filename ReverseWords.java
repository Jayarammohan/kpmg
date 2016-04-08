/**
 * 
 * @author Jaya
 * This program reverses a String's words
 * For instance, "This is a test" becomes "test a is This"
 *
 */

public class ReverseWords {
	public static void main(String[] args) throws Exception {

		String inputString = new String();
		if(args.length < 1) {
			System.out.println("Usage java ReverseWords input");
			System.exit(0);
		}
		else {
			inputString = args[0].trim().replaceAll(" +", " ");	//reduce many whiteSpaces to just 1 & also getRid of leading&trailing white spaces
		}

		System.out.println("the input=" + inputString);


		ReverseWords rw = new ReverseWords();
		System.out.println(rw.reverseWords(inputString));
	}

	public String reverseWords(String input) {
		String[] tokens = input.split("\\W+");
		StringBuffer result = new StringBuffer();
		for(int i=tokens.length-1; i >=0; i--) {
			result.append(tokens[i]);
			if(i > 0)
				result.append(" ");
		}	

		System.out.println("The reversed one="+ result.toString());
		return result.toString();

	}
}
