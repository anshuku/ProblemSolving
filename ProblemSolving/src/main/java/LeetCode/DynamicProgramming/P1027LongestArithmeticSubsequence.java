package LeetCode.DynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * P1027. Longest Arithmetic Subsequence - Medium
 * 
 * Given an array nums of integers, return the length of the longest arithmetic subsequence in nums.
 * 
 * Note that:
 * A subsequence is an array that can be derived from another array by deleting 
 * some or no elements without changing the order of the remaining elements.
 * 
 * A sequence seq is arithmetic if seq[i + 1] - seq[i] are all the same value (for 0 <= i < seq.length - 1).
 * 
 * Approach - DP, Map
 */
public class P1027LongestArithmeticSubsequence {

	public static void main(String[] args) {
		int[] nums = { 3, 6, 9, 12 };

//		int[] nums = { 9, 4, 7, 2, 10 };

//		int[] nums = { 20, 1, 15, 3, 10, 5, 8 };

		int longestArithSeqDPArr = longestArithSeqLengthDPArr(nums);
		System.out.println("DP array: The longest arithmetic subsequence: " + longestArithSeqDPArr);

		int longestArithSeqDPMap = longestArithSeqLengthDPMap(nums);
		System.out.println("DP map: The longest arithmetic subsequence: " + longestArithSeqDPMap);

		int longestArithSeqBF = longestArithSeqLengthBF(nums);
		System.out.println("Brute Force: The longest arithmetic subsequence: " + longestArithSeqBF);
	}

	// DP with 2D Array where dp[i][diff] = length
	// dp[i][diff] represents the length of the longest AP subsequence that ends at
	// i with common difference diff.
	private static int longestArithSeqLengthDPArr(int[] nums) {
		int n = nums.length;
		int[][] dp = new int[n][1001];
		for (int[] arr : dp) {
			Arrays.fill(arr, 1);
		}
		int maxLength = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				int diff = nums[i] - nums[j] + 500;
				int len = dp[j][diff];
				dp[i][diff] = len + 1;
				maxLength = Math.max(maxLength, len + 1);
			}
		}
		return maxLength;
	}

	// DP with HashMap Array where dp[i] = Map<Difference, Length>
	// For a pair (i,j) where i > j we consider them as last 2 numbers of a seq.
	// We determine the sequence which ends at j and has the same difference as diff
	// = nums[i] - nums[j]. To determine if they are last two elements use map where
	// the diff as key is mapped to length as value for each index of the array.
	// The Map has all the differences mapped with length at a particular index j.
	// We can have diff, length - (1,5), (2, 1), (3, 5), (4,2) at dp[7]. It means
	// The array can store all the subsequences ending at dp[j] with different
	// combinations of diff mapped to length. One needs diff = nums[i] - nums[j]
	// to get the length of the sequence ending at a particular j to extend it with
	// nums at index i. dp[right][diff] = dp[left][diff] + 1, where left < right.
	// The maxiumum value stored in dp is the answer.
	// Time complexity - O(n^2) for nested loop
	// Space complexity - O(n^2) for storing the dp array.
	private static int longestArithSeqLengthDPMap(int[] nums) {
		int n = nums.length;
		Map<Integer, Integer>[] dp = new HashMap[n];
		int maxLength = 0;
		for (int i = 0; i < n; i++) {
			dp[i] = new HashMap<>();
			for (int j = 0; j < i; j++) {
				int diff = nums[i] - nums[j];
				int len = dp[j].getOrDefault(diff, 1);
				dp[i].put(diff, len + 1);
				maxLength = Math.max(maxLength, len + 1);
			}
		}
		return maxLength;
	}

	// Brute Force Approach
	// We check all the triplets and len to simulate the Arithmetic subsequence.
	// For every pair(i, j) fixed and obtained via nested operation over nums such
	// that (j>i) we check the difference diff: nums[j] - nums[i].
	// Loop through k > j, to extend the subsequence if diff matches.
	// Update the maxLength after the loop with k finishes
	// Time complexity - O(n^3) for 3 nested loops.
	// Space complexity - O(1)
	public static int longestArithSeqLengthBF(int[] nums) {
		int n = nums.length;
		int maxLength = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int diff = nums[j] - nums[i];
				int len = 2;
				int last = nums[j];
				for (int k = j + 1; k < n; k++) {
					if (nums[k] - last == diff) {
						len++;
						last = nums[k];
					}
				}
				maxLength = Math.max(maxLength, len);
			}
		}
		return maxLength;
	}

}
