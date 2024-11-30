package LeetCode.HashMapSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * P1207. Unique Number of Occurrences - Easy
 * 
 * Given an array of integers arr, return true if the number of occurrences 
 * of each value in the array is unique or false otherwise.
 * 
 * Approach - Set and Map / Frequency and boolean array
 */
public class P1207UniqueNumberOccurrences {

	public static void main(String[] args) {

		int[] arr = { 1, 2, 2, 1, 1, 3 };

//		int[] arr = { -3, 0, 1, -3, 1, 1, 1, -3, 10, 0 };

		boolean uniqueArr = uniqueOccurrencesArr(arr);

		System.out.println("Array: The array has unique occurrences - " + uniqueArr);

		boolean uniqueSetMap = uniqueOccurrencesSetMap(arr);

		System.out.println("Set and Map: The array has unique occurrences - " + uniqueSetMap);

	}

	private static boolean uniqueOccurrencesArr(int[] arr) {
		int[] map = new int[2001];
		for (int num : arr) {
			map[num + 1000]++;
		}
		boolean[] seen = new boolean[1001];
		for (int num : map) {
			if (num != 0 && seen[num]) {
				return false;
			}
			seen[num] = true;
		}
		return true;
	}

	public static boolean uniqueOccurrencesSetMap(int[] arr) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : arr) {
			map.put(num, map.getOrDefault(num, 0) + 1);
		}
		Set<Integer> set = new HashSet<>();
		for (int num : map.values()) {
			if (!set.add(num)) {
				return false;
			}
		}
		return true;
	}

}
