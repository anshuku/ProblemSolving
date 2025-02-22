package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P740. Delete and Earn - Medium
 * 
 * You are given an integer array nums. You want to maximize the number of 
 * points you get by performing the following operation any number of times:
 * 
 * Pick any nums[i] and delete it to earn nums[i] points. Afterwards, you must 
 * delete every element equal to nums[i] - 1 and every element equal to nums[i] + 1.
 * Return the maximum number of points you can earn by applying the above operation some number of times.
 * 
 * Approach - DP: tabulation, memoization
 * 
 * log(n) to the base 2 in program is given by logn / log2
 * since log(n) takes base as 10.
 */
public class P740DeleteAndEarn {

	public static void main(String[] args) {
//		int[] nums = { 3, 4, 2 };
//		int[] nums = { 2, 2, 3, 3, 3, 4 };
		int[] nums = { 3, 1 };

		int maxPoints2VarsOrItr = deleteAndEarn2VarsOrIterate(nums);
		System.out.println("2 Vars or Iterate: The max points earned is: " + maxPoints2VarsOrItr);

		int maxPointsIterate = deleteAndEarnIterate(nums);
		System.out.println("Iterate: The max points earned is: " + maxPointsIterate);

		int maxPoints2Vars = deleteAndEarn2Vars(nums);
		System.out.println("2 Vars: The max points earned is: " + maxPoints2Vars);

		int maxPointsTabulation = deleteAndEarnTabulation(nums);
		System.out.println("Tabulation: The max points earned is: " + maxPointsTabulation);

		int maxPoints = deleteAndEarnMemoized(nums);
		System.out.println("Memoization: The max points earned is: " + maxPoints);
	}

	// Best of Iterate over elements or Tabulation with 2 variables
	// Tabulation with 2 variables takes O(max) time for max points.
	// Iterating over elements takes O(n+nlogn) time for max points.
	// if max < n + nlogn take the tabulation approach.
	// Othewise take the iterating over elements approach.
	// Time complexity - O(n + min(k, n+nlogn)), O(n) for points, O(nlogn) for
	// sorting keys or O(k) for iterating over elements. O(n) for finding max points
	// Space complexity - O(n) for storing points or points and list.
	// n in worst case when all elements are unique.
	private static int deleteAndEarn2VarsOrIterate(int[] nums) {
		Map<Integer, Integer> points = new HashMap<>();
		int max = 0;
		for (int num : nums) {
			points.put(num, points.getOrDefault(num, 0) + num);
			max = Math.max(max, num);
		}
		int maxPoints = 0;
		int var1 = 0;
		int n = points.size();
		if (max < n + n * Math.log(n) / Math.log(2)) {
			int var2 = points.getOrDefault(1, 0);
			for (int i = 2; i <= max; i++) {
				maxPoints = Math.max(var2, var1 + points.getOrDefault(i, 0));
				var1 = var2;
				var2 = maxPoints;
			}
		} else {
			List<Integer> list = new ArrayList<>(points.keySet());
			Collections.sort(list);
			int var2 = points.get(list.get(0));
			maxPoints = points.get(list.get(0));
			for (int i = 1; i < n; i++) {
				if (list.get(i) - list.get(i - 1) == 1) {
					maxPoints = Math.max(var2, var1 + points.get(list.get(i)));
				} else {
					maxPoints += points.get(list.get(i));
				}
				var1 = var2;
				var2 = maxPoints;
			}
		}
		return maxPoints;
	}

	// Iterate over elements
	// In case when there is a lot of gap between elements
	// It'll take a lot of unnecessary time to check points
	// Get the points map, then sort the keys in ascending order
	// Use 2 variables to store last and 2nd last points
	// In case the difference between current and last key is 1
	// Either take the last element or current points and 2nd last element
	// Otherwise take the points
	// Time complexity - O(nlogn), O(n) for points, O(nlogn) for sorting keys
	// O(n) for finding max points
	// Space complexity - O(n) for storing list.
	// n in worst case when all elements are unique.
	private static int deleteAndEarnIterate(int[] nums) {
		Map<Integer, Integer> points = new HashMap<>();
		for (int num : nums) {
			points.put(num, points.getOrDefault(num, 0) + num);
		}
		// map.keySet() returns a Set. Get a list for index based traversal.
		List<Integer> list = new ArrayList<>(points.keySet());
		Collections.sort(list); // Sort in ascending order
		int n = list.size();
		int val1 = 0;
		int val2 = points.get(list.get(0));
		int maxPoints = points.get(list.get(0));
		for (int i = 1; i < n; i++) {
			if (list.get(i) - list.get(i - 1) == 1) {
				maxPoints = Math.max(val2, val1 + points.get(list.get(i)));
			} else {
				maxPoints += points.get(list.get(i));
			}
			val1 = val2;
			val2 = maxPoints;
		}
		return maxPoints;
	}

	// Bottom up - Tabulation with 2 variables
	// Time complexity - O(n + k) where n is number or points and k = max points.
	// n for points map and k for finding max gain.
	// Space complexity - O(n), for points, in worst case when elements are unqiue.
	private static int deleteAndEarn2Vars(int[] nums) {
		int[] points = new int[10001];
		int max = 0;
		for (int num : nums) {
			points[num] += num;
			max = Math.max(max, num);
		}
		int val1 = 0;
		int val2 = points[1];
		int maxPoints = points[1];
		for (int i = 2; i <= max; i++) {
			maxPoints = Math.max(val2, val1 + points[i]);
			val1 = val2;
			val2 = maxPoints;
		}
		return maxPoints;
	}

	// Bottom up - Tabulation
	// Time complexity - O(n + k) where n is number or points and k = max points.
	// n for points map and k for finding max gain.
	// Space complexity - O(n + k), for points map in worst case where all elements
	// are unique O(n), k for dp array.
	private static int deleteAndEarnTabulation(int[] nums) {
		Map<Integer, Integer> points = new HashMap<>();
		int max = 0;
		for (int num : nums) {
			points.put(num, points.getOrDefault(num, 0) + num);
			max = Math.max(max, num);
		}
		int[] dp = new int[max + 1];
		dp[1] = points.getOrDefault(1, 0);
		for (int i = 2; i <= max; i++) {
			dp[i] = Math.max(dp[i - 1], dp[i - 2] + points.getOrDefault(i, 0));
		}
		return dp[max];
	}

	// Top down - memoization
	// The problem needs maximum and decision making which leads to future choices
	// This is DP pradigm. It needs memory, recurrence relation(RR), base cases
	// Approach - Map the points to number of it's occurence * point
	// To find max point we either take the current point or leave it
	// We go top down from max of all points.
	// Either the current point is chosen alongwith maxPoints obtained from num - 2
	// or the maxPoints obtained from n-1 leaving current point.
	// RR: max of maxPoints(num-1) and maxPoints(n-2) + points.get(num)
	// Base case: For Point 0 we return 0. when it's 1 return points.get(1) else 0
	// Cache the result for each number for memoization
	// Time complexity - O(n + k) where n is number or points and k = max points.
	// Because of cache k unique subproblems need to be solved in O(k*1) time.
	// Space complexity - O(n + 2*k), n for points, k each for cache and recursion.
	public static int deleteAndEarnMemoized(int[] nums) {
		Map<Integer, Integer> points = new HashMap<>();
		int max = 0;
		for (int num : nums) {
			points.put(num, points.getOrDefault(num, 0) + num);
			max = Math.max(max, num);
		}
		Map<Integer, Integer> cache = new HashMap<>();
		return maxPoints(max, points, cache);
	}

	private static int maxPoints(int num, Map<Integer, Integer> points, Map<Integer, Integer> cache) {
		if (num == 0) {
			return 0;
		}
		if (num == 1) {
			return points.getOrDefault(1, 0);
		}
		if (cache.containsKey(num)) {
			return cache.get(num);
		}
		int val = Math.max(maxPoints(num - 1, points, cache),
				maxPoints(num - 2, points, cache) + points.getOrDefault(num, 0));
		cache.put(num, val);
		return val;
	}

}
