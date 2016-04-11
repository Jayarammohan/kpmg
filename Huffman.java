/**

 * Huffman coding


 * Counts the occurrences of characters in the given string
 * add them in the binary tree and then
 * traverses that binary tree and prints the items
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Huffman {

	static class Node {
		Node left;
		Node right;
		Element value;
		boolean sumNode;
		
		public Node(Element value) {
			this.value = value;
		}
	}

	static class Element {
		String value;
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		Integer  count;

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

		public Element(String value, Integer count) {
			this.value = value;
			this.count = count;
		}
		public String toString() {
			return value + ":" + count;
		}
	}

	
	static class PQsort implements Comparator<Element> {
		 
		public int compare(Element one, Element two) {
 				return (one.getCount() - two.getCount());
 		}
	}
	
	/**
	 * Read the input string and build a Map of the counts of the various characters present
	 * @param inputString
	 * @return
	 */
	public Map<String,Integer> populateCountsMap(String inputString) {
		char[] characters = inputString.toCharArray();
		Map<String,Integer> countsMap = new HashMap<>();
		Integer count=0;

		for(int i=0; i < characters.length; i++) {
			count = countsMap.get(Character.toString(characters[i]));
			count = (count == null) ? 1 : ++count;
			countsMap.put(Character.toString(characters[i]), count);
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
		Map<String,Integer> countsMap = hm.populateCountsMap(inputString);

		//build a frequency table containing the frequency and the character within the same integer with frequency being in the MSB and the character in the LSB. We need to use Integer rather than String because sorting based on freq. required
		//we need to do this because we want to keep the character and their frequency together, but we also need to sort based on the frequencies
		//hence, we keep them together in the same Integer in different bytes, ie., in different significant bytes		
 
		PQsort pqs = new PQsort();
		PriorityQueue<Element> frequency = new PriorityQueue<>(pqs);
		
		int freq = 0;
		for (String key : countsMap.keySet()) {
			System.out.println(key +" " + countsMap.get(key));
			Element element = new Element(key, countsMap.get(key));
			System.out.println("adding element " +element.toString());
 			frequency.add(element);
		}


		/**
		 * Implementation of the Huffman algorithm
		 * That is, go through the sorted list, add topmost two items and get the result
		 * remove the 2 items and add their result back into the list
		 * now resort it again, and then
		 * repeat this process till the list is reduced to a mere size 1
		 */
		List<Element> entries = new ArrayList<>();
		int freqSum =0;
		for(;;) {
			if (frequency.size() < 2 )
				break;							//finished traversing the tree
			 
			//get the least frequency two items and add the frequency up into sum as per Huffman algorithm
			Element first = frequency.poll();
 			Element second = frequency.poll();
			Integer sum = first.getCount() + second.getCount();

			//add them into the binary tree 
			entries.add(first);
			entries.add(second);

			//add the sum node as per Huffman algorithm
 			freqSum = sum;
			frequency.add(new Element("sum",freqSum));

		}

		//add the last entry into the binary tree
		Element first = frequency.poll();
		//add that into the binary tree and print the entries
		entries.add(first);
		System.out.println("");
		System.out.println("The Entries are ");
		System.out.println("--------------- ");

		//build a stack of these entries because when we pop them back, we can create the binary tree with root as the highest sum and
		//our characters being in the leaf nodes.

		Stack<Element> stk = new Stack<>();
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
		Node root = new Node(new Element("",0));
		List<Node> nonLeafNonAttachedNodes = new ArrayList<>();
		if(!stk.empty()) {
			Element next = (Element)stk.pop();
			root.value = next;					//the root node
		}
		nonLeafNonAttachedNodes.add(root);		//since the root node is not a leaf node

		while(!stk.empty()) {
			Element left = (Element)stk.pop();
			Element right = (Element)stk.pop();
			Node leftNode = new Node(left);
			Node rightNode = new Node(right);
			Node sumNode = nonLeafNonAttachedNodes.get(0);
			nonLeafNonAttachedNodes.remove(0);

			sumNode.left = leftNode;
			System.out.println("attached as left node of " + sumNode.value + " is " + leftNode.value);
			if("sum".equals(left.getValue())) {
				nonLeafNonAttachedNodes.add(leftNode);
			}

			sumNode.right = rightNode;
			System.out.println("attached as right node of " + sumNode.value + " is " + rightNode.value);
			if("sum".equals(right.getValue())) {
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
	static Stack<Element> buildStack(List<Element> entries) {

		Stack<Element> stk = new Stack<>();
		for(Element entry : entries) {
			System.out.println(entry.toString());
			stk.push(entry);
		}	
		return stk;
	}

	/**
	 * Given a value for a node, construct the string equivalent indicating whether it is sum or leaf
	 * @param value
	 * @return
	 */
 
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
		Element path[] = new Element[1000];
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
	public static void printPathsRecur(Node node, Element path[], String bits[],int pathLen,String traverseDirection) {
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
	public static  void printArray(Element data[], String bits[], int len) {
		int i;
		Element leaf = data[len-1];
		Integer frequency = leaf.getCount();
		String value = leaf.getValue();

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