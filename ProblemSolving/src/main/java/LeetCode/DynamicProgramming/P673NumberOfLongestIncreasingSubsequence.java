package LeetCode.DynamicProgramming;

/*
 * P673. Number of Longest Increasing Subsequence - Medium
 * 
 * Given an integer array nums, return the number of longest increasing subsequences.
 * 
 * Notice that the sequence has to be strictly increasing.
 * 
 * Approach - DP
 */
public class P673NumberOfLongestIncreasingSubsequence {

	public static void main(String[] args) {

		int[] nums = { 1, 3, 5, 4, 7 };
//		int[] nums = { 2, 2, 2, 2, 2 };
//		int[] nums = { 1, 2, 4, 3, 5, 4, 7, 2 };
//		int[] nums = { 1 };

		int numLISDPBU = findNumberOfLISBU(nums);
		System.out.println("Bottom Up: The number of longest increaing subsequence: " + numLISDPBU);

		int numLISDPTD = findNumberOfLISTD(nums);
		System.out.println("Top Down: The number of longest increaing subsequence: " + numLISDPTD);
	}

	// DP - Bottom up
	// For this we need 2 arrays - length and count
	// length[i] denotes length and count[i] denotes the number of LIS at index i.
	// Initialize the arrays - length and count with 1. Every subsequence with
	// single element is increasing. Update arrays if longer subsequence is at i.
	// For each index j such that j < i and nums[j] < nums[i], we can extend any
	// increasing subsequence at index j with element at index i. Length[i] is the
	// maximum length of an increasing subsequence ending at index i seen so far.
	// If length[j]+1 > length[i], we've found a longer subsequence, update
	// length[i] = length[j] + 1 and count[i] = 0 as we discard older LIS.
	// If length[j]+1 == length[i], we can extend every LIS ending at index j
	// with nums[i] to create new LIS. We add count[j] to count[i] to include
	// all subsequences that include both indices j and i.
	// Find maxLength which is the maximum length[i] or LIS for entire array nums
	// Then, Sum up the count[i] values where length[i] == maxLength
	// Time complexity - O(n^2) for nested for loops.
	// Space complexity - O(n) for length and count arrays.
	public static int findNumberOfLISBU(int[] nums) {
		int n = nums.length;

		int[] length = new int[n];
		int[] count = new int[n];

		for (int i = 0; i < n; i++) {
			length[i] = 1;
			count[i] = 1;
		}

		int maxLength = 1;
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (nums[i] > nums[j]) {
					if (length[i] < length[j] + 1) {
						length[i] = length[j] + 1;
						count[i] = 0;
					}
					if (length[i] == length[j] + 1) {
						count[i] += count[j];
					}
				}
			}
			maxLength = Math.max(maxLength, length[i]);
		}
		int result = 0;
		for (int i = 0; i < n; i++) {
			if (length[i] == maxLength) {
				result += count[i];
			}
		}
		return result;
	}

	// Time complexity - O(n^2) for nested for loops.
	// Space complexity - O(n) for length and count arrays.
	private static int findNumberOfLISTD(int[] nums) {
		int n = nums.length;
		int[] length = new int[n];
		int[] count = new int[n];

		int maxLength = 0;
		for (int i = n - 1; i >= 0; i--) {
			calculateDP(i, nums, length, count);
			maxLength = Math.max(maxLength, length[i]);
		}

		int result = 0;

		for (int i = 0; i < n; i++) {
			if (length[i] == maxLength) {
				result += count[i];
			}
		}
		return result;
	}

	private static void calculateDP(int i, int[] nums, int[] length, int[] count) {
		if (length[i] != 0) {
			return;
		}

		length[i] = 1;
		count[i] = 1;

		for (int j = 0; j < i; j++) {
			if (nums[i] > nums[j]) {
				calculateDP(j, nums, length, count);
				if (length[i] < length[j] + 1) {
					length[i] = length[j] + 1;
					count[i] = 0;
				}
				if (length[i] == length[j] + 1) {
					count[i] += count[j];
				}
			}
		}

	}
}
