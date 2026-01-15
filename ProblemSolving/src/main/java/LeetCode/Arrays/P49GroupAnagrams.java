package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P49. Group Anagrams - Medium
 * 
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 * 
 * Approach - Maps, Sorting, Hashing
 * 
 * int[] cannot be used as a HashMap key for content-based equality.
 * As arrays in Java do not override equals() and hashCode(), the map compares references and not values.
 * So, 2 different int[26] with same counts are treated as different keys, breaking grouping
 * This compares memory address and not content.
 */
public class P49GroupAnagrams {

	public static void main(String[] args) {
		String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };

		List<List<String>> groupsMapChar = groupAnagramsMapChar(strs);
		System.out.println("Map Char: The grouped anagrams are: " + groupsMapChar);

		List<List<String>> groupsMapSort = groupAnagramsMapSort(strs);
		System.out.println("Map Sort: The grouped anagrams are: " + groupsMapSort);

		List<List<String>> groupsMapSB = groupAnagramsMapSB(strs);
		System.out.println("Map String Builder: The grouped anagrams are: " + groupsMapSB);
	}

	private static List<List<String>> groupAnagramsMapChar(String[] strs) {
		Map<String, List<String>> map = new HashMap<>();
		for (String str : strs) {
			char[] arr = new char[26];
			for (char c : str.toCharArray()) {
				arr[c - 'a']++;
			}
			String key = new String(arr);
			map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
		}
		return new ArrayList<>(map.values());
	}

	// Categorize by sorted string.
	// 2 strings are anagrams if and only if their sorted strings are equal.
	// Algo: Maintain a map{String -> List}, where eack key K is a sorted string,
	// and each value is a list of strings which are anagrams of K.
	// Time complexity - O(n*klogk) where n is length of strs and k is max length of
	// string in strs. We iterate each string in strs and then we sort each string
	// in O(klogk) time.
	// Space complexity - O(n*k), for map.
	public static List<List<String>> groupAnagramsMapSort(String[] strs) {
		Map<String, List<String>> map = new HashMap<>();
		for (String str : strs) {
			char[] arr = str.toCharArray();
			Arrays.sort(arr);
			String key = new String(arr);
			map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
		}
		return new ArrayList<>(map.values());
	}

	// Categorize by count
	// 2 strings are anagrams if and only if their character counts are same.
	// We transform each string s in strs to character count, count, having 26
	// non-negative integers representing number of each alphabet. We use it as key
	// for hashmap. The count is then hashed by delimiting by '#'. abbccc =
	// #1#2#3#0#0...#0 where there are 26 entries total.
	// Time complexity - O(n*k), we count every string for each character.
	// Space complexity - O(n*k), for map.
	public static List<List<String>> groupAnagramsMapSB(String[] strs) {
		Map<String, List<String>> map = new HashMap<>();
		for (String str : strs) {
			int[] arr = new int[26];
			for (char c : str.toCharArray()) {
				arr[c - 'a']++;
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 26; i++) {
				sb.append('#');
				sb.append(arr[i]);
			}
			String key = sb.toString();
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<>());
			}
			map.get(key).add(str);
		}
		return new ArrayList<>(map.values());
	}

}
