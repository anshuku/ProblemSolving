package LeetCode.BinarySearch.P981TimeBasedKeyValueStore;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
 * Approach - Binary Search, TreeMap
 */
public class P981TimeBasedKeyValueStoreTreeMap {

	Map<String, TreeMap<Integer, String>> map;

	P981TimeBasedKeyValueStoreTreeMap() {
		map = new HashMap<>();
	}

	// Time complexity - O(M*L*logM), If the key and value has average length L,
	// For M set calls, map takes logM time(height of tree) to store it and for
	// hashing it takes L time.
	public void set(String key, String value, int timestamp) {
//		if (map.containsKey(key)) {
//			TreeMap<Integer, String> entries = map.get(key);
//			entries.put(timestamp, value);
//		} else {
//			TreeMap<Integer, String> entry = new TreeMap<>();
//			entry.put(timestamp, value);
//			map.put(key, entry);
//		}
		// Faster
		map.computeIfAbsent(key, v -> new TreeMap<>()).put(timestamp, value);
	}

	// Time complexity - O(N*L*logM), we first find the key in map which takes
	// O(L*logM) as we have M set operations to build the tree. Then we use binary
	// search among m elements which may take logM time. So for N get calls,
	// O(N*(L*logM + logM))
	// Space complexity - O(1)
	public String get(String key, int timestamp) {
		if (!map.containsKey(key)) {
			return "";
		}
		TreeMap<Integer, String> entries = map.get(key);
		// Need to find the timestamp entry which is just <= given timestamp key.
		Integer floorKey = entries.floorKey(timestamp);
		if (floorKey == null) {
			return "";
		}
		return entries.get(floorKey);
	}

	public static void main(String[] args) {

		P981TimeBasedKeyValueStoreTreeMap map = new P981TimeBasedKeyValueStoreTreeMap();
		map.set("foo", "bar", 1);
		System.out.println(map.get("foo", 1));
		System.out.println(map.get("foo", 3));
		map.set("foo", "bar2", 4);
		System.out.println(map.get("foo", 4));
		System.out.println(map.get("foo", 5));

	}

}
