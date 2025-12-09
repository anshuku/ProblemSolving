package LeetCode.BinarySearch.P981TimeBasedKeyValueStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

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
 * Approach - Pairs, Binary Search
 */
public class P981TimeBasedKeyValueStorePairsList {

	Map<String, List<Pair<Integer, String>>> map;

	P981TimeBasedKeyValueStorePairsList() {
		map = new HashMap<>();
	}

	// Time complexity - O(m*L) since we do M set calls and for hashing strings of
	// average length L we take L time.
	// space complexity - O(M*L)
	private void set(String key, String value, int timestamp) {
		if (map.containsKey(key)) {
			List<Pair<Integer, String>> pairs = map.get(key);
			pairs.add(Pair.of(timestamp, value));
		} else {
			List<Pair<Integer, String>> pair = new ArrayList<>();
			pair.add(Pair.of(timestamp, value));
			map.put(key, pair);
		}
	}

	// Time complexity - P(N*L*logM), since we perform binary search on key's bucket
	// which has M elements of average size L and hashing takes L time. There are N
	// calls.
	// space complexity - O(1)
	private String get(String key, int timestamp) {
		if (!map.containsKey(key)) {
			return "";
		}
		List<Pair<Integer, String>> pairs = map.get(key);
		int start = 0;
		int end = pairs.size() - 1;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (pairs.get(mid).getKey() <= timestamp) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		if (start == 0) {
			return "";
		}
		return pairs.get(start - 1).getValue();
	}

	public static void main(String[] args) {
		P981TimeBasedKeyValueStorePairsList map = new P981TimeBasedKeyValueStorePairsList();
		map.set("foo", "bar", 1);
		System.out.println(map.get("foo", 1));
		System.out.println(map.get("foo", 3));
		map.set("foo", "bar2", 4);
		System.out.println(map.get("foo", 4));
		System.out.println(map.get("foo", 5));

	}

}
