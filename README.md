There are 3 tasks

Problem descriptions:
Problem 1
Write a program that takes any 10-digit phone number and produces a list of words matching first letters of the phone number (2 – ABC, 3 – DEF, .. 9 – WZXYZ). Use any free word list file, e.g.
 
Problem 2
Implement http://en.wikipedia.org/wiki/Huffman_coding tree construction algorithm, which takes any string and prints the tree.

Problem 3.a
Write a java program with reverses the words in a String. Use whatever features you feel will solve this problem clearly and efficiently.  

Problem 3.b
Write the same program as above, but now the function will work over a single char array, swapping characters within the array to achieve the same word reversal. Your function CANNOT use any built in Java methods and CANNOT allocate any free store (no use of the new keyword). Your function can allocate as many primitive variables as your function needs to provide this functionality.


Problem 1 Solution
===================
* This program takes any 10-digit phone number and produces a list of words matching first letters of the phone number (2 – ABC, 3 – DEF, .. 9 – WXYZ). 
 * The input file is a word list file from the internet.
 * The input file is local to the computer that is running the application
 * The input file should be deposited in the same directory as the program   and it should be called wordsEn.txt


Test results
=============
>java PhoneNumbersToWords 0413122434

The words are 
--------------
a
ad
add
dad
dada
ga
gad
gag
gaga


Algorithm
=========
1. The input string is validated that it contains only numeric characters
2. The digits are mapped to their DTMF keypad counterparts as below
	1 and 0 from the input string are eliminated because they do not correspond to any letters in the DTMF keypad
	2	-	A
	3	-	D
	4	- G
	5	- J
	6	-	M
	7	-	P
	8	-	T
	9	-	W

3. The words in the input dictionary file is streamed into a List
4. Get the letters corresponding to the individual digits of the phone number. Example in 0413122434, it is a,d,g  (1 and 0 can be ignored)
5. From a-z, form a list of alphabets not comprising these above letters, 
	ie., b,c, e,f,  h..z   into a List
6. using filter and collector, collect a list of words from the input which do not comprise the alphabets that need to be excluded giving 	 the desired output	
7. Print the list of filtered words as output on the screen.





Problem 2
==========
Huffman coding tree construction algorithm

Test Results
============
I have given an input string with the below frequencies as below
----------------------------------------------------------------
Character       Frequency
---------       ---------
1                  5
2                  7
3                 10
4                 15
5                 20
6                 45


> java Huffman 123456123456123456123456123456234562345634563456345645645645645645656565656566666666666666666666666666

 The Entries are 
--------------- 
1:5
2:7
3:10
sum:12
4:15
5:20
sum:22
sum:35
6:45
sum:57
sum:102
attached as left node of sum:102 is sum:57
attached as right node of sum:102 is 6:45
attached as left node of sum:57 is sum:35
attached as right node of sum:57 is sum:22
attached as left node of sum:35 is 5:20
attached as right node of sum:35 is 4:15
attached as left node of sum:22 is sum:12
attached as right node of sum:22 is 3:10
attached as left node of sum:12 is 2:7
attached as right node of sum:12 is 1:5

We are allocating bits for the characters based on Huffman Coding algorithm
---------------------------------------------------------------------------
Allocation of bits for 5 which occurs with a freqency of 20 is 000 
This is arrived at by traversing through the tree in the following manner 
sum:102, sum:57, sum:35, 5:20, 
Allocation of bits for 4 which occurs with a freqency of 15 is 001 
This is arrived at by traversing through the tree in the following manner 
sum:102, sum:57, sum:35, 4:15, 
Allocation of bits for 2 which occurs with a freqency of 7 is 0100 
This is arrived at by traversing through the tree in the following manner 
sum:102, sum:57, sum:22, sum:12, 2:7, 
Allocation of bits for 1 which occurs with a freqency of 5 is 0101 
This is arrived at by traversing through the tree in the following manner 
sum:102, sum:57, sum:22, sum:12, 1:5, 
Allocation of bits for 3 which occurs with a freqency of 10 is 011 
This is arrived at by traversing through the tree in the following manner 
sum:102, sum:57, sum:22, 3:10, 
Allocation of bits for 6 which occurs with a freqency of 45 is 1 
This is arrived at by traversing through the tree in the following manner 
sum:102, 6:45, 



  Algorithm
  =========
  1.From the input string, build a frequency table containing the frequency and the character encapsulated within a class called Element
  These frequecy + character information is stored in a PriortyQueue data structure so that it is always sorted automatically with the provided comparator which sorts based on frequency
  
  2. Now, build the Priority Queue entries by the below algorithm
  		While the frequency list size >= 2
 	  		a. Take the least 2 items from the frequency table and insert into the entries table
   			b. Remove these two items from the frequency PriorityQueue and add the sum of these two entries with value set as 'sum' to indicate that it is the sum.  
  
  			     Eg., 5 and 7 are least two frequencies, so we create
  		                     12:sum
  		                    /   \
  		                   5:1	7:2

   		end while
      Now add the last entry into the entries table
  		
      By the above algorithm,
         our frequency table of
      1                  5
      2                  7
      3                 10
      4                 15
      5                 20
      6                 45

      is translated into Entries list of

      5 1 Leaf
      7 2 Leaf
      10 3 Leaf
      12 Sum
      15 4 Leaf
      20 5 Leaf
      22  Sum
      35  Sum
      45 6 Leaf
      57  Sum
      102 Sum

     3. Now, put the entries list into a stack, so that we can start traversing from 102 Sum by popping from the stack
     4. Now build the Huffman tree out of the above built stack
        The logic:
        Add the root as a nonLeafNonAttachedNodes list initially because it is yet to be attached to its children
     
        While there are entries left in the stack
          pop 2 items from stack and attach them to the current topmost NonLeafNonAttachedNodes, one of them left node and another right node
          delete this sum node from the NonLeafNonAttachedNodes list because it has just been attached to the 2 children
          if a node is a sum node, (Denoted by Sum being present in the value), then add it to a NonLeafNonAttachedNodes list because the summation node does not end, only the leaf node terminates
        end while  
         
        By this logic, our entries table gets converted into
                                         
                                         102sum
                                        /   \
                                      57sum  45:6 (Leaf)
                                      / \
                                     /   \
                                    /     \
                                   /       \
                                  /         \
                                35sum       22sum
                                /  \        /  \
                               /    \      /    \  
                            20:5   15:4   12sum 10:3 (Leaf)   
                            (leaf) (Leaf) /  \
                                         /    \
                                        7:2   5:1 (Leaf)
                                      (Leaf)

         5.Now, we go through this tree and to traverse to each leaf node, we make bit "1" if we traverse right and bit "0" if we traverse left
         So, to traverse to 1,   it is = 0101
             to traverse to 2,   it is = 0100
             to traverse to 3,   it is = 011
             to traverse to 4,   it is = 001
             to traverse to 5,   it is = 000
             to traverse to 6,   it is = 1     


3.a   This is fairly simple program to reverse a string

3.b   Reverse a character array without using "new" nor using any java built-in functions

Test Result
============
>java Reverse "1234567890 123456789 12345678 1234567 123456 12345 1234 123 12 1"
the input=1234567890 123456789 12345678 1234567 123456 12345 1234 123 12 1
string after first pass reversal=1 21 321 4321 54321 654321 7654321 87654321 987654321 0987654321
The reversed string=1 12 123 1234 12345 123456 1234567 12345678 123456789 1234567890  		


Algorithm
==========
The reversal is effected by 2 passes
First pass, the whole character array is reversed
Second pass, each word is individually reversed to restore the original (unreversed) word

Eg.,
This is a test becomes
tset a si sihT 		in the first pass
In the second pass, each word individually gets reversed by locating the blank character as the delimiter and this gives the desired output of
test a is This 