package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * P300. Longest Increasing Subsequence - Medium
 * 
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 * 
 * Approach - DP, Binary Search
 */
public class P300LongestIncreasingSubsequence {

	public static void main(String[] args) {
//		int[] nums = { 10, 9, 2, 5, 3, 7, 101, 18 };
		int[] nums = { 0, 1, 0, 3, 2, 3 };
//		int[] nums = { 7, 7, 7, 7, 7, 7, 7 };

		int longestIncreasingSubseqBuildBS = lengthOfLISBuildBS(nums);
		System.out.println(
				"Build BS: The lengths of longest increasing subsequence is " + longestIncreasingSubseqBuildBS);

		int longestIncreasingSubseqBuild = lengthOfLISBuild(nums);
		System.out.println("Build: The lengths of longest increasing subsequence is " + longestIncreasingSubseqBuild);

		int longestIncreasingSubseqDP = lengthOfLIS(nums);
		System.out.println("The lengths of longest increasing subsequence is " + longestIncreasingSubseqDP);

		int longestIncreasingSubseqDP2 = longestIncreasingSubseqDP2(nums);
		System.out.println("Build: The lengths of longest increasing subsequence is " + longestIncreasingSubseqDP2);
	}

	// Intelligently building a subsequence with Binary search
	// In case when the current num to be checked is not greater than list's
	// largest. We can perform Binary search(list is sorted) to find the list's
	// index which has an element greater than or equal to num.
	// Time complexity - O(n*logn)
	// Space complexity - O(n)
	private static int lengthOfLISBuildBS(int[] nums) {
		int n = nums.length;
		List<Integer> list = new ArrayList<>();
		list.add(nums[0]);
		for (int i = 1; i < n; i++) {
			if (nums[i] > list.get(list.size() - 1)) {
				list.add(nums[i]);
			} else {
//				int index = Collections.binarySearch(list, nums[i]);
//				if (index < 0) {
//					index = -(index + 1);
//				}
//				list.set(index, nums[i]);
				int start = 0;
				int end = list.size() - 1;
				while (start <= end) {
					int mid = start + (end - start) / 2;
					if (nums[i] <= list.get(mid)) {
						end = mid - 1;
					} else {
						start = mid + 1;
					}
				}
				list.set(start, nums[i]);
			}
		}
		return list.size();
	}

	// Intelligently building a subsequence
	// Take the numbers and add it to the list one by one
	// Take the 1st element in the list and for subsequent elements check if
	// a) the next element is largest, then add it
	// b) the next element is not largest, then replace the current element with the
	// existing element in the list where it's not greater than the existing one.
	// Or replace the first existing element greater than or equal to num with num.
	// example 8 and then nums[i] = 1 take 1 and remove 8, since there may be
	// numbers included later which are larger than 1 but smaller than 8.
	// The length of the list is the answer and the list doesn't always generate a
	// valid subsequence. For input 3,4,5,1, list 1,4,5 which isn't a subsequence.
	// Time complexity - O(n^2), 1st half - 1,2,3,4,...,98,99
	// 2nd half - 98,98,...98 - O((n/2)^2)
	// Space complexity - O(n), when the input array is strictly increasing
	private static int lengthOfLISBuild(int[] nums) {
		int n = nums.length;
		List<Integer> list = new ArrayList<Integer>();
		list.add(nums[0]);
		for (int i = 1; i < n; i++) {
			if (nums[i] > list.get(list.size() - 1)) {
				list.add(nums[i]);
			} else {
				int j = 0;
				while (nums[i] > list.get(j)) {
					j++;
				}
				list.set(j, nums[i]);
			}
		}
		return list.size();
	}

	// Dynamic Programming - max and min, using previous decisions
	// dp[i] represents the length of the longest increasing subsequence
	// that ends at ith element. State is one dimensional represented by index i.
	// Recurrence relation - dp[i], if nums[i] is larger than any of nums[i-1...]
	// take the subsequence ending at that j and append the nums at that j (j<i).
	// Need to maximize dp[i] so take max value of all the indices before i.
	// dp[i] = max(dp[j]+1) if nums[j] < nums[i] and i > j.
	// Base case: Initialize dp with 1, all elements form increasing subsequences.
	// Time complexity - O(n^2), 2 for loops 1+2+3...n-1
	// Space complexity - O(n) for dp array.
	public static int lengthOfLIS(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		int result = 1;
		dp[0] = 1;
		for (int i = 1; i < n; i++) {
			dp[i] = 1; // initialize dp with 1
			for (int j = 0; j < i; j++) {
				if (nums[i] > nums[j]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			result = Math.max(result, dp[i]);
		}
		return result;
	}

	private static int longestIncreasingSubseqDP2(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		int result = 0;
		for (int i = 0; i < n; i++) {
			dp[i] = 1;
		}
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (nums[i] > nums[j]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			result = Math.max(result, dp[i]);
		}
		return result;
	}

}
