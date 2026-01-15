package LeetCode.DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/*
 * 1218. Longest Arithmetic Subsequence of Given Difference - Medium
 * 
 * Given an integer array arr and an integer difference, return the length of the 
 * longest subsequence in arr which is an arithmetic sequence such that the difference 
 * between adjacent elements in the subsequence equals difference.
 * 
 * A subsequence is a sequence that can be derived from arr by deleting some 
 * or no elements without changing the order of the remaining elements.
 * 
 * Approach - 
 */
public class P1218LongestArithmeticSubsequenceGivenDifference {

	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4 };
		int difference = 1;

//		int[] arr = { 1, 3, 5, 7 };
//		int difference = 1;

//		int[] arr = { 1, 5, 7, 8, 5, 3, 4, 2, 1 };
//		int difference = -2;

		int longestSubseqMap = longestSubsequenceMap(arr, difference);
		System.out.println("Array: The longest arithmetic subsequence has length: " + longestSubseqMap);

		int longestSubseqArr = longestSubsequenceArr(arr, difference);
		System.out.println("Array: The longest arithmetic subsequence has length: " + longestSubseqArr);
	}

	// DP with Map
	// DP map, maps the current element arr[i] with the number of APs ending at i.
	// For each element arr[i] we check if map.containsKey(arr[i] - diff).
	// If it's true - If it's present it mean there exists an AP of length
	// map.get(arr[i] - diff), now we can extend this AP with current element
	// arr[i], add map with map.put(arr[i], map.get(arr[i]-diff) + 1)
	// If it's false - We can update map with map.put(arr[i], 1), as the element
	// in it's own is technically an arithmetic subsequence.
	// Time complexity - O(n) as we iterate through the array and hashmap lookup
	// and update takes constant time on an average.
	// Space complexity - O(n) for storing n elements of array in the map.
	private static int longestSubsequenceMap(int[] arr, int difference) {
		Map<Integer, Integer> map = new HashMap<>();
		int length = 0;
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			if (map.containsKey(arr[i] - difference)) {
				map.put(arr[i], map.get(arr[i] - difference) + 1);
				length = Math.max(length, map.get(arr[i]));
			} else {
				map.put(arr[i], 1);
			}
//			int beforeCount = map.get(arr[i] - difference) != null ? map.get(arr[i] - difference) : 0;
//			map.put(arr[i], beforeCount + 1);
//			length = Math.max(length, beforeCount + 1);
		}
		return length;
	}

	// DP in array
	// Here, we fill the array by being only concerned about the
	// Difference*1 and not subsequent differences which is 2*diff,
	// 3*diff and so on. Still we get the correct answer due to dp array.
	// Here the solution takes O(n^2) time which is large.
	// Time complexity - O(n^2)
	// Space complexity - O(n)
	public static int longestSubsequenceArr(int[] arr, int difference) {
		int n = arr.length;
		int[] dp = new int[n];
		int longest = 0;
		for (int i = 0; i < n; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (arr[i] - arr[j] == difference) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			longest = Math.max(longest, dp[i]);
		}
		return longest;
	}

}
