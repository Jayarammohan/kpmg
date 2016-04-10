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
	4	- 	G
	5	- 	J
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

java Huffman 111112222222333333333344444444444444455555555555555555555666666666666666666666666666666666666666666666

The binary tree is printed 
-------------------------- 
 

  Traversed 5 1
  Traversed 7 2
  Traversed 10 3
  Traversed 12 *
  Traversed 15 4
  Traversed 20 5
  Traversed 22 *
  Traversed 35 *
  Traversed 45 6
  Traversed 57 *
  Traversed 102 *


  Algorithm
  =========
  1.From the input string, build a frequency table containing the frequency and the character within the same integer with frequency being in the MSB and the character in the LSB. We need to use Integer rather than String because sorting based on freq. required

  
  2. Now, build the binary tree by the below algorithm
  		While the frequency list size >= 2
  			a. sort the frequency table in ascending order
	  		a. Take the least 2 items from the frequency table and insert into the tree
   			b. Remove these two items from the frequency list and add the sum of these two entries suffixed with * to indicate that it is the sum. This sum frequency entry will participate in the sorting in the next iteration
  
  			     Eg., 5 and 7 are least two frequencies, so we create
  		                     12:*
  		                    /   \
  		                   5:1	7:2

   		end while

  		Now add the last entry into the binary tree
  		print the tree                   


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