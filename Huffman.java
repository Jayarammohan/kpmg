/**

 * Huffman coding

 * Counts the occurrences of characters in the given string
 * add them in the binary tree and then
 * traverses that binary tree and prints the items
 * Assumptions : The characters that are catered for is only ASCII character set and not unicode characters
 */
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Huffman {

	static class Node {
		Node left;
		Node right;
		String value;

		public Node(String value) {
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
		//we need to do this because we want to keep the character and their frequency together, but we also need to sort based on the frequencies
		//hence, we keep them together in the same Integer in different bytes, ie., in different significant bytes		
 
		PriorityQueue<Integer> frequency = new PriorityQueue<>();
		int freq = 0;
		for (Character key : countsMap.keySet()) {
			int intEquivalent = (int)key.charValue();
			freq = countsMap.get(key);
			frequency.add(freq * 10000000 + intEquivalent);
		}


		/**
		 * Implementation of the Huffman algorithm
		 * That is, go through the sorted list, add topmost two items and get the result
		 * remove the 2 items and add their result back into the list
		 * now resort it again, and then
		 * repeat this process till the list is reduced to a mere size 1
		 */
		List<String> entries = new ArrayList<>();
		int freqSum =0;
		for(;;) {
			if (frequency.size() < 2 )
				break;							//finished traversing the tree
			 
			//get the least frequency two items and add the frequency up into sum as per Huffman algorithm
			Integer first = frequency.poll();
			Integer frequency1 = first/10000000;
			Integer character = first - frequency1 * 10000000;
			Integer second = frequency.poll();
			Integer frequency2 = second/10000000;
			Integer sum = frequency1 + frequency2;


			//add them into the binary tree 
			entries.add(convertToString(first.intValue()));
			entries.add(convertToString(second.intValue()));

			//add the sum node as per Huffman algorithm
			int 	moreThanASCII = 256;									//to indicate this is is sum node
			freqSum = sum;
			frequency.add(freqSum * 10000000 + moreThanASCII);

		}

		//add the last entry into the binary tree
		Integer first = frequency.poll();
		//add that into the binary tree and print the entries
		entries.add(convertToString(first.intValue()));
		System.out.println("");
		System.out.println("The Entries are ");
		System.out.println("--------------- ");

		//build a stack of these entries because when we pop them back, we can create the binary tree with root as the highest sum and
		//our characters being in the leaf nodes.

		Stack<String> stk = new Stack<>();
		stk = buildStack(entries);

		/**
		 * Now build the Huffman tree out of the above built stack
		 * The logic:
		 * Add the root as a nonLeafNonAttachedNodes list initially because it is yet to be attached to its children
		 *
		 * While there are entries left in the stack
		 *   pop 2 items from stack and attach them to the current topmost NonLeafNonAttachedNodes, one of them left node and another right node
		 *   Now, delete this sum node from the NonLeafNonAttachedNodes list because it has just been attached to the 2 children
		 *   if a node is a sum node, (Denoted by Sum being present in the value), then add it to a NonLeafNonAttachedNodes list because the summation
		 *   node does not end, only the leaf node terminates
		 * end while  
		 **/
		Node root = new Node("");
		List<Node> nonLeafNonAttachedNodes = new ArrayList<>();
		if(!stk.empty()) {
			String next = (String)stk.pop();
			root.value = next;					//the root node
		}
		nonLeafNonAttachedNodes.add(root);		//since the root node is not a leaf node

		while(!stk.empty()) {
			String left = (String)stk.pop();
			String right = (String)stk.pop();
			Node leftNode = new Node(left);
			Node rightNode = new Node(right);
			Node sumNode = nonLeafNonAttachedNodes.get(0);
			nonLeafNonAttachedNodes.remove(0);

			sumNode.left = leftNode;
			System.out.println("attached as left node of " + sumNode.value + " is " + leftNode.value);
			if(left.indexOf("Sum") >= 0) {
				nonLeafNonAttachedNodes.add(leftNode);
			}

			sumNode.right = rightNode;
			System.out.println("attached as right node of " + sumNode.value + " is " + rightNode.value);
			if(right.indexOf("Sum") >= 0) {
				nonLeafNonAttachedNodes.add(rightNode);
			}
		}
		printPaths(root); 
	}

	/**
	 * build a stack out of the input entries
	 * @param entries
	 * @return
	 */
	static Stack<String> buildStack(List<String>entries) {

		Stack<String> stk = new Stack<>();
		for(String entry : entries) {
			System.out.println(entry);
			stk.push(entry);
		}	
		return stk;
	}

	/**
	 * Given a value for a node, construct the string equivalent indicating whether it is sum or leaf
	 * @param value
	 * @return
	 */
	static String convertToString(Integer value) {

		Integer frequency = value/10000000;
		Integer intEquivalent = (value - frequency * 10000000);
		String stringValue = new String();
		if(intEquivalent <= 255) {							//if it is a valid character and not our made up sum character of 256
			char ch = (char)intEquivalent.intValue();
			Character character = new Character(ch);
			stringValue = character.toString(); 
		}	
		if(intEquivalent == 256) {
			return frequency + " " + "*"         + " Sum";
		}
		else
			return frequency + " " + stringValue + " Leaf";

	}

	/**
	 * Print the tree's contents
	 * @param node
	 */
	public static void printInOrder(Node node) {
		if (node != null) {
			printInOrder(node.left);

			System.out.println("  Traversed " + node.value);

			printInOrder(node.right);
		}
	}

	/**
	 * prints the path by calling printPathsRecur
	 * @param node
	 */
	public static void printPaths(Node node) {
		String path[] = new String[1000];
		String bits[] = new String[1000];
		System.out.println("");
		System.out.println("We are allocating bits for the characters based on Huffman Coding algorithm");
		System.out.println("---------------------------------------------------------------------------");
		printPathsRecur(node, path, bits,0,"0");
	}

	/**
	 * Print the path recursively
	 * @param node
	 * @param path
	 * @param bits
	 * @param pathLen
	 * @param traverseDirection
	 */
	public static void printPathsRecur(Node node, String path[], String bits[],int pathLen,String traverseDirection) {
		if (node == null) {
			return;
		}

		/* append this node to the path array */
		path[pathLen] = node.value;
		bits[pathLen] = traverseDirection;
		pathLen++;

		/* it's a leaf, so print the path that led to here  */
		if (node.left == null && node.right == null) {
			printArray(path, bits,pathLen);
		} else {

			/* otherwise try both subtrees */

			printPathsRecur(node.left, path, bits,pathLen,"0");
			printPathsRecur(node.right, path, bits, pathLen,"1");
		}
	}

	/**
	 * Once we have arrived at a leaf node, print the traversal, the value, the frequency and the allocation of bits for that as per
	 * Huffman coding algorithm
	 * @param data
	 * @param bits
	 * @param len
	 */
	public static  void printArray(String data[], String bits[], int len) {
		int i;
		String leaf = data[len-1];
		String[] fields = leaf.split(" ");
		String frequency = fields[0];
		String value = fields[1];

		System.out.print("Allocation of bits for " + value + " which occurs with a freqency of " + frequency + " is ");
		for (i = 1; i < len; i++) {
			System.out.print(bits[i] );
		}
		System.out.println(" ");
		System.out.println("This is arrived at by traversing through the tree in the following manner ");
		for (i = 0; i < len; i++) {
			System.out.print(data[i] );
			System.out.print(", ");
		}

		System.out.println("");
	}

}