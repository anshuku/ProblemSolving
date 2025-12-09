package LeetCode.BinarySearch.P981TimeBasedKeyValueStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * P981. Time Based Key-Value Store - Medium
 * 
 * Design a time-based key-value data structure that can store multiple values for the 
 * same key at different time stamps and retrieve the key's value at a certain timestamp.
 * 
 * Implement the TimeMap class:
 * 
 * TimeMap() Initializes the object of the data structure.
 * 
 * void set(String key, String value, int timestamp) Stores the key 
 * key with the value value at the given time timestamp.
 * 
 * String get(String key, int timestamp) Returns a value such that set was called previously, 
 * with timestamp_prev <= timestamp. If there are multiple such values, it returns the 
 * value associated with the largest timestamp_prev. If there are no values, it returns "".
 * 
 * Approach - Binary Search, LinkedHashMap
 */
public class P981TimeBasedKeyValueStoreBinarySearch {

	Map<String, LinkedHashMap<Integer, String>> map;

	P981TimeBasedKeyValueStoreBinarySearch() {
		map = new HashMap<>();
	}

	public void set(String key, String value, int timestamp) {
//		if (map.containsKey(key)) {
//			LinkedHashMap<Integer, String> entries = map.get(key);
//			entries.put(timestamp, value);
//		} else {
//			LinkedHashMap<Integer, String> entry = new LinkedHashMap<>();
//			entry.put(timestamp, value);
//			map.put(key, entry);
//		}

		// Faster
		map.computeIfAbsent(key, k -> new LinkedHashMap<>()).put(timestamp, value);

	}

	public String get(String key, int timestamp) {
		if (!map.containsKey(key)) {
			return "";
		}
		LinkedHashMap<Integer, String> entries = map.get(key);
		List<Integer> list = new ArrayList<>(entries.keySet());
		int n = list.size();
		int start = 0;
		int end = n - 1;

		// We find upper bound as per constraints given
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (list.get(mid) <= timestamp) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		if (start == 0) {
			return "";
		} else {
			return entries.get(list.get(start - 1));
		}
	}

	public static void main(String[] args) {

		P981TimeBasedKeyValueStoreBinarySearch map = new P981TimeBasedKeyValueStoreBinarySearch();
		map.set("foo", "bar", 1);
		System.out.println(map.get("foo", 1));
		System.out.println(map.get("foo", 3));
		map.set("foo", "bar2", 4);
		System.out.println(map.get("foo", 4));
		System.out.println(map.get("foo", 5));

	}

}
