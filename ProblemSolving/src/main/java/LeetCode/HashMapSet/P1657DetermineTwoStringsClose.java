package LeetCode.HashMapSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P1657. Determine if Two Strings Are Close - Medium
 * 
 * Two strings are considered close if you can attain one from the other using the following operations:
 * Operation 1: Swap any two existing characters.
 * For example, abcde -> aecdb
 * Operation 2: Transform every occurrence of one existing character into another 
 * existing character, and do the same with the other character.
 * For example, aacabb -> bbcbaa (all a's turn into b's, and all b's turn into a's)
 * You can use the operations on either string as many times as necessary.
 * Given two strings, word1 and word2, return true if word1 and word2 are close, and false otherwise.
 * 
 * Approach - HashMap and HashSet / Frequency Array
 * 
 * map.values() returns a Collection<V> type
 * Collections.sort() is not applicable for Collection<V> type
 * List can have a Collection as a constructor argument.
 * 
 * Map.Entry<K, V> from map.entrySet() can be compared by .equals() method
 * 
 * (words1[i] == 0 && words2[i] > 0) || (words1[i] > 0 && words2[i] == 0) is XOR
 * words1[i] == 0 ^ words2[i] == 0
 * 
 * for a3b4c2d2e5f5 =/= a5b2c3d3e4f4
 * Instead of using 2 list and then sorting and comparing them, use one int[]
 */
public class P1657DetermineTwoStringsClose {

	public static void main(String[] args) {

//		String word1 = "abc", word2 = "bca";

//		String word1 = "cabbba", word2 = "abbccc";

		// a3b4c2d2e5f5 =/= a5b2c3d3e4f4
		// Instead of using 2 list and then sorting and comparing them, use one int[]
		String word1 = "aaabbbbccddeeeeefffff", word2 = "aaaaabbcccdddeeeeffff";

		boolean isCloseArr = closeStringsArr(word1, word2);

		System.out.println("Array: The two strings are close - " + isCloseArr);

		boolean isCloseMap = closeStringsMap(word1, word2);

		System.out.println("Map: The two strings are close - " + isCloseMap);
	}

	private static boolean closeStringsArr(String word1, String word2) {
		if (word1.equals(word2)) {
			return true;
		}
		if (word1.length() != word2.length()) {
			return false;
		}
		int[] words1 = new int[26];
		char[] wordArr1 = word1.toCharArray();
		for (char c : wordArr1) {
			words1[c - 'a']++;
		}

		int[] words2 = new int[26];
		char[] wordArr2 = word2.toCharArray();
		for (char c : wordArr2) {
			words2[c - 'a']++;
		}
		int[] occurrence = new int[wordArr1.length];

//		List<Integer> list1 = new ArrayList<>();
//		List<Integer> list2 = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
			// (words1[i] == 0 && words2[i] > 0) || (words1[i] > 0 && words2[i] == 0) is XOR
			if (words1[i] == 0 ^ words2[i] == 0) {
				return false;
			}

			occurrence[words1[i]]++;
			occurrence[words2[i]]--;

//			if (words1[i] > 0) {
//				list1.add(words1[i]);
//			}
//			if (words2[i] > 0) {
//				list2.add(words2[i]);
//			}
		}

		for (int i : occurrence) {
			if (i != 0) {
				return false;
			}
		}

//		Collections.sort(list1);
//		Collections.sort(list2);
//		
//		if (!list1.equals(list2)) {
//			return false;
//		}
		return true;
	}

	public static boolean closeStringsMap(String word1, String word2) {
		Map<Character, Integer> map1 = new HashMap<>();
		char[] wordArr1 = word1.toCharArray();
		for (char c : wordArr1) {
			map1.put(c, map1.getOrDefault(c, 0) + 1);
		}

		Map<Character, Integer> map2 = new HashMap<>();
		char[] wordArr2 = word2.toCharArray();
		for (char c : wordArr2) {
			map2.put(c, map2.getOrDefault(c, 0) + 1);
		}

		List<Integer> list1 = new ArrayList<>(map1.values());
		Collections.sort(list1);

		List<Integer> list2 = new ArrayList<>(map2.values());
		Collections.sort(list2);

		if (!list1.equals(list2)) {
			return false;
		}

		for (char c : map1.keySet()) {
			if (!map2.keySet().contains(c)) {
				return false;
			}
		}
		for (char c : map2.keySet()) {
			if (!map1.keySet().contains(c)) {
				return false;
			}
		}

		return true;
	}

}
