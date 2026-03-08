package LeetCode.BitManipulation;

/*
 * P1684. Count the Number of Consistent Strings - Easy
 * 
 * You are given a string allowed consisting of distinct characters and an array of strings 
 * words. A string is consistent if all characters in the string appear in the string allowed.
 * 
 * Return the number of consistent strings in the array words.
 * 
 * Approach - Bit Manipulation, Boolean Array
 */
public class P1684CountNumberConsistentStrings {

	public static void main(String[] args) {
		String allowed = "ab";
		String[] words = { "ad", "bd", "aaab", "baa", "badab" };
//		String allowed = "abc";
//		String[] words = { "a","b","c","ab","ac","bc","abc" };
//		String allowed = "cad";
//		String[] words = { "cc", "acd", "b", "ba", "bac", "bad", "ac", "d" };

		int countConsistentArr = countConsistentStringsArr(allowed, words);
		System.out.println("Array: The count of consistent strings are: " + countConsistentArr);

		int countConsistentBM = countConsistentStringsBM(allowed, words);
		System.out.println("Bit Manipulation: The count of consistent strings are: " + countConsistentBM);

	}

	// Boolean Array
	// Time complexity - O(m + n*k), where m and n are length of allowed and words.
	// The algo iterates over each character in allowed to mark it as true in O(m)
	// time. We then iterate through each character of each word in the words array.
	// If k is the length of longest word, the overall time complexity of this
	// portion is O(n*k). Overall it's O(m) + O(n*k).
	// Space complexity - O(1), isAllowed array has constant length of 26.
	public static int countConsistentStringsArr(String allowed, String[] words) {
		int count = 0;
		boolean[] isAllowed = new boolean[26];
		for (char c : allowed.toCharArray()) {
			isAllowed[c - 'a'] = true;
		}

		for (String word : words) {
			boolean include = true;
			for (char c : word.toCharArray()) {
				if (!isAllowed[c - 'a']) {
					include = false;
					break;
				}
			}

			if (include) {
				count++;
			}
		}
		return count;
	}

	// Bit Manipulation
	// In a binary number, each bit can be 0 or 1. A boolean variable can be either
	// true or false. So, each bit in binary number can represet a boolean value. We
	// can use this to show whether a character is in allowed via bitmask. This
	// works like the boolean array, where each index stands for a character from a
	// to z. Since, there are only 26 characters, a 32-bit integer will be enough
	// for our bitmask. We need to perform 2 main operations with this bitmask:
	// 1. Set a bit: Each bit will show whether a character from a to z is present
	// (1) or not(0). 0th bit represents a, 1st - b, so on. To mark a character
	// we'll set the corresponding bit to 1. To set a bit, we use Bitwise OR with 1
	// shifted left by that bit's position. Example, to set 2nd bit in binary number
	// 1000010, we use Bitwise OR with 1 shofter left by 2. For marking character c
	// mask = mask | (1 << (c - 'a'))
	// 2. Checking a bit: To see if a character is in allowed, we check the
	// corresponding bit in the bitmask. We isolate the bit by right-shifting the
	// bitmask by the bit's position and then using Bitwise AND with 1. If the
	// character is in allowed. Example, to check if the 2nd bit in 1000110 is setm
	// we right-shift by 2 and use Bitwise AND with 1 which gives 1, so the bit is
	// set. To check character c: bit = (mask >> (c - 'a')) & 1.
	// Time complexity - O(m + n*k)
	// Space complexity - O(1)
	private static int countConsistentStringsBM(String allowed, String[] words) {
		int count = 0;
		// allowedBits represents the bitmask of allowed characters.
		int allowedBits = 0;

		// set the corresponding bit for each chaacter in allowed
		for (char c : allowed.toCharArray()) {
			allowedBits |= (1 << (c - 'a'));
		}
		for (String word : words) {
			boolean include = true;
			for (char c : word.toCharArray()) {
				if (((allowedBits >> (c - 'a')) & 1) == 0) {
					include = false;
					break;
				}
			}

			if (include) {
				count++;
			}
		}
		return count;
	}

}
