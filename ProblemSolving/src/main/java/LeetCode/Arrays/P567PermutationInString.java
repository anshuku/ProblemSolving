package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* P.567 Permutation in String - Medium
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 * In other words, return true if one of s1's permutations is the substring of s2.
 */
public class P567PermutationInString {

	public static void main(String[] args) {
//		String s1 = "abc"; //abc, bac, cba, acb
		// abc acb bac bca cab cba
//		String s2 = "aeidbfadbadoacoacbo";
//		String s1 = "bac";
//		String s2 = "bbbca";
		String s1 = "ab";
		String s2 = "eidbaooo";

		boolean isPermutation = checkInclusion(s1, s2);
		System.out.println("Sliding Window Optimized: s1's permutations is substring of s2 - " + isPermutation);

		boolean isPermutationSlidingWindowArrOpt = checkInclusionSlidingWindowArrOpt(s1, s2);
		System.out.println("Sliding window arr optimized: s1's permutations is substring of s2 - "
				+ isPermutationSlidingWindowArrOpt);

		boolean isPermutationSlidingWindowArr = checkInclusionSlidingWindowArr(s1, s2);
		System.out
				.println("Sliding window arr: s1's permutations is substring of s2 - " + isPermutationSlidingWindowArr);

		boolean isPermutationSlidingWindowMap = checkInclusionSlidingWindowMap(s1, s2);
		System.out
				.println("Sliding window map: s1's permutations is substring of s2 - " + isPermutationSlidingWindowMap);

		boolean isPermutationArr = checkInclusionArr(s1, s2);
		System.out.println("Frequency Array: s1's permutations is substring of s2 - " + isPermutationArr);

		boolean isPermutationMap = checkInclusionMap(s1, s2);
		System.out.println("Map: s1's permutations is substring of s2 - " + isPermutationMap);

		boolean isPermutationS1LenSorted = checkInclusionS1LenSorted(s1, s2);
		System.out.println("S1 Len sorted: s1's permutations is substring of s2 - " + isPermutationS1LenSorted);

//		boolean isPermutationS1S2Sorted = checkInclusionS1S2Sorted(s1, s2);
//		System.out.println("Both sorted: s1's permutations is substring of s2 - " + isPermutationS1S2Sorted);

		boolean isPermutationBruteForce = checkInclusionBruteForce(s1, s2);
		System.out.println("Brute Force: s1's permutations is substring of s2 - " + isPermutationBruteForce);
		System.out.println("counter " + counter);
	}

	public static boolean checkInclusion(String s1, String s2) {
		int[] charArr = new int[26];
		for (int i = 0; i < s1.length(); i++) {
			charArr[s1.charAt(i) - 'a']++;
		}
		int left = 0, right = 0;
		int n = s2.length();
		int rem = s1.length();
		while (right < n) {
			if (charArr[s2.charAt(right++) - 'a']-- > 0) {
				rem--;
			}
			if (rem == 0) {
				return true;
			}
			if ((right - left == s1.length()) && charArr[s2.charAt(left++) - 'a']++ >= 0) {
				rem++;
			}
		}
		return false;
	}

	// Instead of comparing all the characters of s1Arr with s2Arr after every
	// iteration, we keep the count of matching characters and update it.
	// Time Complexity - O(m + 26*(n-m)) = O(n)
	// Space Complexity - O(26 + 26) = O(1), space required is constant,
	// independent of input size.
	private static boolean checkInclusionSlidingWindowArrOpt(String s1, String s2) {
		if (s1.length() > s2.length()) {
			return false;
		}
		int m = s1.length();
		int[] s1Arr = new int[26];
		int[] s2Arr = new int[26];
		for (int i = 0; i < m; i++) {
			s1Arr[s1.charAt(i) - 'a']++;
			s2Arr[s2.charAt(i) - 'a']++;
		}
		int count = 0;
		for (int i = 0; i < 26; i++) {
			if (s1Arr[i] == s2Arr[i]) {
				count++;
			}
		}
		int n = s2.length();
		for (int i = 0; i < n - m; i++) {
			if (count == 26) {
				return true;
			}
			int l = s2.charAt(i) - 'a';
			int r = s2.charAt(i + m) - 'a';
			s2Arr[r]++;
			if (s2Arr[r] == s1Arr[r]) {
				count++;
			} else if (s2Arr[r] == s1Arr[r] + 1) {
				count--;
			}
			s2Arr[l]--;
			if (s2Arr[l] == s1Arr[l]) {
				count++;
			} else if (s2Arr[l] == s1Arr[l] - 1) {
				count--;
			}
		}
		return count == 26;
	}

	// Create a frequency array for a string of length s1 present in s2
	// Slide the window of length that of s1 and update the array
	// Add the new character frequency and remove the last character's frequency.
	// Time Complexity - O(m + 26*(n-m)) = O(n)
	// Space Complexity - O(26 + 26) = O(1), space required is constant,
	// independent of input size.
	private static boolean checkInclusionSlidingWindowArr(String s1, String s2) {
		if (s1.length() > s2.length()) {
			return false;
		}
		int m = s1.length();
		int[] s1Arr = new int[26];
		int[] s2Arr = new int[26];
		for (int i = 0; i < m; i++) {
			s1Arr[s1.charAt(i) - 'a']++;
			s2Arr[s2.charAt(i) - 'a']++;
		}
		int n = s2.length();
		for (int i = 0; i < n - m; i++) {
			if (matches(s1Arr, s2Arr)) {
				return true;
			}
			s2Arr[s2.charAt(i + m) - 'a']++;
			s2Arr[s2.charAt(i) - 'a']--;
		}
		return matches(s1Arr, s2Arr);
	}

	private static boolean checkInclusionSlidingWindowMap(String s1, String s2) {
		if (s1.length() > s2.length()) {
			return false;
		}
		Map<Character, Integer> s1Map = new HashMap<>();
		Map<Character, Integer> s2Map = new HashMap<>();
		int m = s1.length();
		for (int i = 0; i < m; i++) {
			s1Map.put(s1.charAt(i), s1Map.getOrDefault(s1.charAt(i), 0) + 1);
			s2Map.put(s2.charAt(i), s2Map.getOrDefault(s2.charAt(i), 0) + 1);
		}
		int n = s2.length();
		for (int i = 0; i < n - m; i++) {
			if (matches(s1Map, s2Map)) {
				return true;
			}
			s2Map.put(s2.charAt(i + m), s2Map.getOrDefault(s2.charAt(i + m), 0) + 1);
			s2Map.put(s2.charAt(i), s2Map.get(s2.charAt(i)) - 1);
		}
		return matches(s1Map, s2Map);
	}

	// matches functions takes O(26) time
	// Time complexity - O(m + (26 + m)*(n - m)) or O(m*(n-m))
	// Space complexity - O(26*(n-m+1)) or O(n-m)
	private static boolean checkInclusionArr(String s1, String s2) {
		if (s1.length() > s2.length()) {
			return false;
		}
		int[] s1Arr = new int[26];
		int m = s1.length();
		for (int i = 0; i < m; i++) {
			s1Arr[s1.charAt(i) - 'a']++;
		}
		int n = s2.length();
		for (int i = 0; i <= n - m; i++) {
			int[] s2Arr = new int[26];
			for (int j = 0; j < m; j++) {
				s2Arr[s2.charAt(i + j) - 'a']++;
			}
			if (matches(s1Arr, s2Arr)) {
				return true;
			}
		}
		return false;
	}

	private static boolean matches(int[] s1Arr, int[] s2Arr) {
		for (int i = 0; i < 26; i++) {
			if (s1Arr[i] != s2Arr[i]) {
				return false;
			}
		}
		return true;
	}

	// Create a map for s1 and s2 having a string of s1's length
	// The map stores the frequency of each character for the 2 strings
	// Post frequency map creation compare the two maps to check permutation
	// Slower than sort based logic
	// Time complexity - O(m + (26 + m)*(n - m)) or O(m*(n-m))
	// Space complexity - O(26*(n-m+1)) or O(n-m)
	private static boolean checkInclusionMap(String s1, String s2) {
		if (s1.length() > s2.length()) {
			return false;
		}
		Map<Character, Integer> s1Map = new HashMap<>();
		int m = s1.length();
		for (int i = 0; i < m; i++) {
			s1Map.put(s1.charAt(i), s1Map.getOrDefault(s1.charAt(i), 0) + 1);
		}
		int n = s2.length();
		for (int i = 0; i <= n - m; i++) {
			Map<Character, Integer> s2Map = new HashMap<>();
			for (int j = 0; j < m; j++) {
				s2Map.put(s2.charAt(i + j), s2Map.getOrDefault(s2.charAt(i + j), 0) + 1);
			}
			if (matches(s1Map, s2Map)) {
				return true;
			}
		}
		return false;
	}

	private static boolean matches(Map<Character, Integer> s1Map, Map<Character, Integer> s2Map) {
		for (char c : s1Map.keySet()) {
			if (s1Map.get(c) - s2Map.getOrDefault(c, -1) != 0) {
				return false;
			}
		}
		return true;
	}

	// Sort s1 and compare every window of s1's length in s2 by sorting that window.
	// Time complexity - (n-m)*mlogm for n-m+1 times iteration and mlogm for sorting
	// Space complexity - O(m + logm), In java Arrays.sort() is implemented via
	// a variant quick sort which uses log m space for sorting.
	private static boolean checkInclusionS1LenSorted(String s1, String s2) {
		if (s1.length() > s2.length()) {
			return false;
		}
		s1 = sort(s1);
		int n = s2.length();
		int m = s1.length();

		for (int i = 0; i <= n - m; i++) {
			if (s1.equals(sort(s2.substring(i, i + m)))) {
				return true;
			}
		}
		return false;
	}

	private static String sort(String s) {
		char[] charArr = s.toCharArray();
		Arrays.sort(charArr);
		return new String(charArr);
	}

	// Doesn't work for abbc and bac, etc.
	private static boolean checkInclusionS1S2Sorted(String s1, String s2) {
		if (s1.length() > s2.length()) {
			return false;
		}
		s1 = sort(s1);
		s2 = sort(s2);
		int m = s1.length();
		int n = s2.length();
		for (int i = 0; i <= n - m; i++) {
			if (s1.equals(s2.substring(i, i + m))) {
				return true;
			}
		}
		return false;
	}

	static boolean flag = false;

	// Time Complexity - O(n!), for each index first we are choosing n characters
	// then n-1 characters, n-2, ...,1
	// Permute method generates all possible permutations of string s1.
	// At level 1, for 1st position we've n choices
	// At level 2, for 2nd position we've n-1 choices,..
	// Total there are n! recursive calls
	// Space Complexity - O(n^2), depth of recursion tree is length of s1 or n
	// and we are passing s1
	private static boolean checkInclusionBruteForce(String s1, String s2) {
		permute(s1, s2, 0);
		return flag;
	}

	static int counter = 0;

	private static void permute(String s1, String s2, int l) {
		counter++;
		if (s1.length() == l) {
			if (s2.indexOf(s1) >= 0) {
				flag = true;
			}
		} else {
			for (int i = l; i < s1.length(); i++) {
				s1 = swap(s1, l, i);
				permute(s1, s2, l + 1);
				s1 = swap(s1, l, i);
			}
		}
	}

	private static String swap(String s, int l, int h) {
		if (l == h) {
			return s;
		}
		String s1 = s.substring(0, l);
		String s2 = s.substring(l + 1, h);
		String s3 = s.substring(h + 1);
		return s1 + s.charAt(h) + s2 + s.charAt(l) + s3;
	}

	// Will Not work
	private static boolean checkInclusionBF(String s1, String s2) {
		if (s1.length() > s2.length()) {
			return false;
		}
		Set<String> set = permute(s1);
		int n = s2.length();
		int m = s1.length();
		for (int i = 0; i <= n - m; i++) {
			if (set.contains(s2.substring(i, i + m))) {
				return true;
			}
		}
		return false;
	}

	private static Set<String> permute(String s1) {
		Set<String> set = new HashSet<>();
		set.add(s1);
		int n = s1.length();
		for (int i = 0; i < n - 1; i++) {
			char c = s1.charAt(i);
			for (int j = i + 1; j < n; j++) {
				char[] charArr = s1.toCharArray();
				charArr[i] = charArr[j];
				charArr[j] = c;
				set.add(new String(charArr));
			}
		}
		return set;
	}
}
