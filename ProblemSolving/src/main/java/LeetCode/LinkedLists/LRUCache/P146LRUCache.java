package LeetCode.LinkedLists.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * Implement the LRUCache class:
 * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * void put(int key, int value) Update the value of the key if the key exists. 
 * Otherwise, add the key-value pair to the cache. If the number of keys exceeds the 
 * capacity from this operation, evict the least recently used key.
 * The functions get and put must each run in O(1) average time complexity.
 * 
 * LOD: Medium
 * 
 * LinkedHashMap - overriding internal function
 */
public class P146LRUCache {

	private Map<Integer, Integer> map;

	public P146LRUCache(int capacity) {

		// A LinkedHashMap with access order attribute is well suited for LRU Cache.
		// The access order = true (false for insertion order)
		// helps to iterate the map in the order of its most recent access.
		// The entries are listed from least recent to most recently accessed.
		// The least recently accessed entry are placed in head and most recent in tail.
		map = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {

			// This is invoked after inserting a new entry by put or putAll(1) method.
			// For the map which represents a cache, it reduces memory consumption.
			@Override
			protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
				return size() > capacity;
			}
		};
	}

	public int get(int key) {
		return map.getOrDefault(key, -1);
	}

	public void put(int key, int value) {
		map.put(key, value);
	}

	public static void main(String[] args) {
		P146LRUCache lru = new P146LRUCache(3);

		lru.put(1, 1);
		lru.put(2, 2);
		lru.put(3, 3);

		int val = lru.get(2);

		lru.put(3, 10);

		lru.put(4, 4);
		lru.put(5, 5);
		lru.put(6, 6);

	}

}
