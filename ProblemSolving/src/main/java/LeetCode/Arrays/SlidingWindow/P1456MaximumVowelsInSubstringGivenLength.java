package LeetCode.Arrays.SlidingWindow;

import java.util.HashSet;
import java.util.Set;

/* P1456 - Maximum Number of Vowels in a Substring of Given Length -  Medium
 * 
 * Given a string s and an integer k, return the maximum number of vowel letters in any substring of s with length k.
 * Vowel letters in English are 'a', 'e', 'i', 'o', and 'u'.
 * 
 * Approach - Set, Char Array - Sliding Window
 */
public class P1456MaximumVowelsInSubstringGivenLength {

	public static void main(String[] args) {
		String s = "abciiidef";
		int k = 3;

//		String s = "aeiou";
//		int k = 2;

		int maxVowelsArr = maxVowelsArr(s, k);

		System.out.println("Array: The max number of vowels in length k is " + maxVowelsArr);

		int maxVowelsSet = maxVowelsSet(s, k);

		System.out.println("Set: The max number of vowels in length k is " + maxVowelsSet);
	}

	private static int maxVowelsArr(String s, int k) {
		int[] vowels = new int[26];
		vowels['a' - 'a'] = 1;
		vowels['e' - 'a'] = 1;
		vowels['i' - 'a'] = 1;
		vowels['o' - 'a'] = 1;
		vowels['u' - 'a'] = 1;

		int maxVowels = 0;

		char[] charArr = s.toCharArray();

		for (int i = 0; i < k; i++) {
			maxVowels += vowels[charArr[i] - 'a'];
		}
		int currVowels = maxVowels;

		if (currVowels == k) {
			return k;
		}

		for (int i = k; i < charArr.length; i++) {
			currVowels += vowels[charArr[i] - 'a'];
			currVowels -= vowels[charArr[i - k] - 'a'];

			if (currVowels == k) {
				return k;
			}

			maxVowels = Math.max(maxVowels, currVowels);
		}

		return maxVowels;
	}

	public static int maxVowelsSet(String s, int k) {
		int currVowels = 0;
		Set<Character> set = new HashSet<>();
		set.add('a');
		set.add('e');
		set.add('i');
		set.add('o');
		set.add('u');

		for (int i = 0; i < k; i++) {
			if (set.contains(s.charAt(i))) {
				currVowels++;
			}
		}

		if (currVowels == k) {
			return k;
		}
		int maxVowels = currVowels;

		for (int i = k; i < s.length(); i++) {
			if (set.contains(s.charAt(i))) {
				currVowels++;
			}
			if (set.contains(s.charAt(i - k))) {
				currVowels--;
			}
			if (currVowels == k) {
				return k;
			}
			maxVowels = Math.max(maxVowels, currVowels);
		}

		return maxVowels;
	}

}
