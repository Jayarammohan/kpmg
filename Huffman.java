/**
 * Huffman coding
 * Counts the occurrences of characters in the given string
 * add them in the binary tree and then
 * traverses that binary tree and prints the items
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;
 
public class Huffman {

	static class Node {
		Node left;
		Node right;
		int value;

		public Node(int value) {
			this.value = value;
		}
	}

	/**
	 * Read the input string and build a Map of the counts of the various characters present
	 * @param inputString
	 * @return
	 */
	public Map<Character,Integer> populateCountsMap(String inputString) {
		char[] characters = inputString.toCharArray();
		Map<Character,Integer> countsMap = new HashMap<>();
		Integer count=0;
		
		for(int i=0; i < characters.length; i++) {
			count = countsMap.get(characters[i]);
			count = (count == null) ? 1 : ++count;
			countsMap.put(characters[i], count);
		}	
		return countsMap;
	}
	
	public static void main(String args[]) {

		String inputString = new String(); 
		if(args.length < 1) {
			System.out.println("Usage java Huffman inputstring");
			System.exit(0);
		}
		else {
			inputString = args[0];
		}
		
		Huffman hm = new Huffman();
		Map<Character,Integer> countsMap = hm.populateCountsMap(inputString);
 
		//build a frequency table containing the frequency and the character within the same integer with frequency being in the MSB and the character in the LSB. We need to use Integer rather than String because sorting based on freq. required
		List<Integer>  frequency = new ArrayList<>();
		int freq = 0;
		for (Character key : countsMap.keySet()) {
 			int intEquivalent = (int)key.charValue();
			freq = countsMap.get(key);
			frequency.add(freq * 10000000 + intEquivalent);
		}


 		Node root = new Node(12);

		for(;;) {
			if (frequency.size() < 2 )
				break;							//finished traversing the tree
			Collections.sort(frequency, (a, b) -> a.compareTo(b));	//sort the frequency list in ascending order
			
			//get the least frequency two items and add the frequency up into sum as per Huffman algorithm
			Integer first = frequency.get(0);
			Integer frequency1 = first/10000000;
			Integer character = first - frequency1 * 10000000;
 			Integer second = frequency.get(1);
			Integer frequency2 = second/10000000;
			Integer sum = frequency1 + frequency2;


			//add them into the binary tree and print the new tree
			insert(root, first.intValue());
			insert(root, second.intValue());
 			printInOrder(root);	    

 			//remove these least frequency two items and add the sum frequency item into the list as per Huffman algorithm
			frequency.remove(0);
			frequency.remove(0);

			int 	intEquivalent = (int)(new Character('*')).charValue();		//use * to indicate that it is the summation of two frequencies
			freq = sum;
			frequency.add(freq * 10000000 + intEquivalent);

		}
		
		//add the last entry into the binary tree
		Integer first = frequency.get(0);   
		//add that into the binary tree and print the new tree
		insert(root, first.intValue());
		System.out.println("");
		System.out.println("The binary tree is printed ");
		System.out.println("-------------------------- ");
 		printInOrder(root);	    


	}

	/**
	 * Insert the given node into the tree
	 * @param node
	 * @param value
	 */
	public static void insert(Node node, int value) {
		if (value < node.value) {
			if (node.left != null) {
				insert(node.left, value);
			} else {
				System.out.println("  Inserted " + value + " to left of "
						+ node.value);
				node.left = new Node(value);
			}
		} else if (value > node.value) {
			if (node.right != null) {
				insert(node.right, value);
			} else {
				System.out.println("  Inserted " + value + " to right of "
						+ node.value);
				node.right = new Node(value);
			}
		}
	}

	/**
	 * Print the tree's contents
	 * @param node
	 */
	public static void printInOrder(Node node) {
		if (node != null) {
			printInOrder(node.left);
 
 			Integer frequency = node.value/10000000;
			Integer character = node.value - frequency * 10000000;
			char b = (char) character.intValue();
			
			System.out.println("  Traversed " + frequency + " " + b);
  
			printInOrder(node.right);
		}
	}
}