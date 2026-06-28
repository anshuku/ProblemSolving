package LeetCode.Arrays;

import java.util.HashMap;
import java.util.Map;

/*
 * P3737. Count Subarrays With Majority Element I - Medium
 * 
 * You are given an integer array nums and an integer target.
 * 
 * Return the number of subarrays of nums in which target is the majority element.
 * 
 * The majority element of a subarray is the element that 
 * appears strictly more than half of the times in that subarray.
 * 
 * Approach - int Counter, HashMap
 */
public class P3737CountSubarraysWithMajorityElementI {

	public static void main(String[] args) {
		int[] nums = { 1, 2, 2, 3 };
		int target = 2;

//		int[] nums = { 1, 1, 1, 1 };
//		int target = 1;

//		int[] nums = { 1, 2, 3 };
//		int target = 4;

		int countCounter = countMajoritySubarraysCounter(nums, target);
		System.out.println("Counter: Number of subarrays of nums in which target is majority element: " + countCounter);

		int countMap = countMajoritySubarraysMap(nums, target);
		System.out.println("Map: Number of subarrays of nums in which target is majority element: " + countMap);
	}

	// Enumeration
	// We enumerate all possible left endpoints i of subarrays and then extend the
	// right endpoint j one at a time. During this process, we maintain a counter
	// counter. If nums[j] == target, we increment counter by 1; otherwise, we
	// decrement it by 1. For a subarray, counter represents the difference between
	// the number of occurrences of target and the number of non-target elements.
	// So, when counter > 0, it means the number of target element is > number of
	// non-target elements, which means target appears more than half the length of
	// the subarray. Hence, target is the majority element of the subarray, and we
	// increment the answer by 1.
	// Time complexity - O(n^2), as we enumerate all subarrays using 2 nested loops.
	// Space complexity - O(1)
	private static int countMajoritySubarraysCounter(int[] nums, int target) {
		int n = nums.length;
		int count = 0;

		for (int i = 0; i < n; i++) {
			int counter = 0;
			for (int j = i; j < n; j++) {
				counter += (nums[j] == target ? 1 : -1);

				if (counter > 0) {
					count++;
				}
			}
		}
		return count;
	}

	public static int countMajoritySubarraysMap(int[] nums, int target) {
		int n = nums.length;
		int count = 0;

		for (int i = 0; i < n; i++) {
			Map<Integer, Integer> map = new HashMap<>();

			for (int j = i; j < n; j++) {
				map.put(nums[j], map.getOrDefault(nums[j], 0) + 1);

				if (map.containsKey(target) && map.get(target) * 2 > (j - i + 1)) {
					count++;
				}
			}
		}

		return count;
	}
}
