# Encryption-Decryption

Programs for Encryption-Decryption data using command-line arguments as input data

When starting the program, the necessary algorithm should be specified by an argument (-alg). 
The first algorithm named shift, the second one named unicode. If there is no -alg you default it to shift.

In case of shift algorithm encode only English letters (from 'a' to 'z' as the first circle and from 'A' to 'Z' as 
the second circle i.e. after 'z' comes 'a' and after 'Z" comes 'A'). Unicode algorithm shift letters in unicade table
 
-alg - for choose the algorithm(shift by default, unicode as second)

-key - define the number of step for encryption right, decryption left

-mode - for take the mode(encryption, decryption)

-data - string of enc-dec

-in - read string from file
If both -data and -in are selected the -data has higher priority

-out define file for output data(defauld: System.out.println)

Example 1
Reading from file and output to file
java Main -mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode

Example 2 
Input data:
java Main -mode enc -key 5 -data "Welcome to home!" -alg unicode
Output:
\jqhtrj%yt%mtrj&

Example 3
Input:
java Main -key 5 -alg unicode -data "\jqhtrj%yt%mtrj&" -mode dec
Output: 
Welcome to home!

Example 4
Input: 
java Main -key 5 -alg shift -data "Welcome to home!" -mode enc
Output:
Bjqhtrj yt mtrj!

Example 5
Input:
java Main -key 5 -alg shift -data "Bjqhtrj yt mtrj!" -mode dec
Output: 
Welcome to home!
