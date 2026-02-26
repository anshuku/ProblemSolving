package LeetCode.BitManipulation;

/*
 * P2044. Count Number of Maximum Bitwise-OR Subsets - Medium
 * 
 * Given an integer array nums, find the maximum possible bitwise OR of a subset of 
 * nums and return the number of different non-empty subsets with the maximum bitwise OR.
 * 
 * An array a is a subset of an array b if a can be obtained from b 
 * by deleting some (possibly zero) elements of b. Two subsets are 
 * considered different if the indices of the elements chosen are different.
 * 
 * The bitwise OR of an array a is equal to a[0] OR a[1] OR ... OR a[a.length - 1] (0-indexed).
 * 
 * Approach - Bit Manipulation, DP, DFS
 */
public class P2044CountNumberMaximumBitwiseORSubsets {

	public static void main(String[] args) {
//		int[] nums = { 3, 1 };
//		int[] nums = { 2, 2, 2 };
		int[] nums = { 3, 2, 1, 5 };

		int countMaxSubsetDfsOpt = countMaxOrSubsetsDfsOpt(nums);
		System.out.println("DFS Optimized: The count of max or subsets are: " + countMaxSubsetDfsOpt);

		int countMaxSubsetDfsOpt2 = countMaxOrSubsetsDfsOpt2(nums);
		System.out.println("DFS Optimized2: The count of max or subsets are: " + countMaxSubsetDfsOpt2);

		P2044CountNumberMaximumBitwiseORSubsets.count = 0;
		int countMaxSubsetDfs = countMaxOrSubsetsDfs(nums);
		System.out.println("DFS: The count of max or subsets are: " + countMaxSubsetDfs);

		int countMaxSubsetDP = countMaxOrSubsetsDP(nums);
		System.out.println("DP: The count of max or subsets are: " + countMaxSubsetDP);

		int countMaxSubsetBMDP = countMaxOrSubsetsBitManipulationDP(nums);
		System.out.println("Bit Manipulation DP: The count of max or subsets are: " + countMaxSubsetBMDP);

		int countMaxSubsetBM = countMaxOrSubsetsBitManipulation(nums);
		System.out.println("Bit Manipulation: The count of max or subsets are: " + countMaxSubsetBM);
	}

	private static int countMaxOrSubsetsDfsOpt(int[] nums) {
		int maxOr = 0;
		for (int num : nums) {
			maxOr |= num;
		}
		return dfsOpt(nums, 0, 0, maxOr);
	}

	private static int dfsOpt(int[] nums, int index, int currOr, int maxOr) {
		if (currOr == maxOr) {
			return 1 << (nums.length - index);
		}
		if (index == nums.length) {
			return 0;
		}
		int with = dfsOpt(nums, index + 1, currOr | nums[index], maxOr);
		int without = dfsOpt(nums, index + 1, currOr, maxOr);
		return with + without;
	}

	private static int countMaxOrSubsetsDfsOpt2(int[] nums) {
		int maxOr = 0;
		for (int num : nums) {
			maxOr |= num;
		}
		dfsOpt2(nums, 0, 0, maxOr);
		return count;
	}

	private static void dfsOpt2(int[] nums, int index, int currOr, int maxOr) {
		if (currOr == maxOr) {
			count += 1 << (nums.length - index);
		}
		if (index == nums.length) {
			return;
		}

		dfs(nums, index + 1, currOr | nums[index], maxOr);
		dfs(nums, index + 1, currOr, maxOr);
	}

	public static int countMaxOrSubsetsDfs(int[] nums) {
		int maxOr = 0;
		for (int num : nums) {
			maxOr |= num;
		}
		dfs(nums, 0, 0, maxOr);
		return count;
	}

	static int count = 0;

	private static void dfs(int[] nums, int index, int currOr, int maxOr) {
		if (index == nums.length) {
			if (currOr == maxOr) {
				count += 1 << (nums.length - index);
			}
			return;
		}

		dfs(nums, index + 1, currOr | nums[index], maxOr);
		dfs(nums, index + 1, currOr, maxOr);
	}

	private static int countMaxOrSubsetsDP(int[] nums) {
		int maxOr = 0;
		for (int num : nums) {
			maxOr |= num;
		}
		int[][] dp = new int[nums.length][maxOr + 1]; // new int[maxOr + 1][nums.length] will take more time
		return dfsDp(nums, 0, 0, maxOr, dp);
	}

	private static int dfsDp(int[] nums, int index, int currOr, int maxOr, int[][] dp) {
//		if (index == nums.length) {
//			if (currOr == maxOr) {
//				return 1;
//			}
//			return 0;
//		}
		if (currOr == maxOr) {
			return 1 << (nums.length - index);
		}
		if (index == nums.length) {
			return 0;
		}
		if (dp[index][currOr] != 0) {
			return dp[index][currOr];
		}

		int with = dfsDp(nums, index + 1, currOr | nums[index], maxOr, dp);
		int without = dfsDp(nums, index + 1, currOr, maxOr, dp);
		return dp[index][currOr] = with + without;
	}

	// Bit Manipulation + Dynamic Programming
	// If we replace OR with addition, this resembles Knapsack problem of DP. We
	// create a dp array of size 2^17, where dp[i] = number of subsets with
	// cumulative OR value of i. The base case is dp[0] = 1, as the only subset with
	// an OR value of 0 is the empty subset. We track the maximum cumulative OR with
	// variable max, initially set to 0. The largest possible element as per
	// constraint is 10^5 which needs 17 bits. The max OR will set all 17 bits and
	// max OR is 2^17 - 1. To accomodate every possible OR result, we need an array
	// of size 2^17 (1 << 17). To fill dp, we iterate over nums, for each value in
	// nums, we OR it with all the possible subset OR values we've achieved till
	// now. This is all the values between 0 and max. We iterate i from max to 0
	// backward, and add the count of subsets in dp[i] to dp[i | num]. The backward
	// iteration prevents double counting. If we iterate forward,, we mght update a
	// value and then use the updated values in same iteration, which gives
	// incorrect count. At end max gives max OR value and dp[max] gives the number
	// of subsets which gives max OR.
	// Time complexity - O(n*max), where n is length of nums and max is max OR
	// value. The outer loop iterate trhough nums and inner loop iterates from max
	// to 0.
	// Space complexity - O(2^17), as dp array has a constant size of 2^17. Even
	// though size is constant is significant in size.
	private static int countMaxOrSubsetsBitManipulationDP(int[] nums) {
		int max = 0;

//		int tempMax = 0;
//		for (int num : nums) {
//			tempMax = Math.max(tempMax, num);
//		}

		int[] dp = new int[1 << 17]; // or tempMax << 1

		dp[0] = 1;
		for (int num : nums) {
			for (int i = max; i >= 0; i--) {
				dp[i | num] += dp[i];
			}
			max |= num;
		}
		return dp[max];
	}

	// Bit Manipulation
	// A susbet of the array nums can be represented by a boolean array or a
	// subsetMask, where each value indicates if the element in nums is included. If
	// the 3rd index is true, it means the 3rd element is part of the subset. Since
	// the max length is capped at 16, we can generate all subsets and track via
	// integer subsetMask (2^16 < 2^31) or use binary representation of an integer,
	// where a set ith bit indicates that ith element is included in subset.
	// nums:3 2 1 5 7(0 1 2 3 4), to include 3 2 7(maxOr), mask:1 0 0 1 1(4 3 2 1 0)
	// The indexing direction in the mask is reversed to represent how we count
	// positions. We iterate over all possible subsets(2^n) of nums with integers
	// from 0 to 2^n - 1, each representing a unique subset. For each subset, we
	// calculate the OR value on elements corresponding to set bits in the integer
	// by checking if the ith bit of current subset mask is set. If this OR value
	// matches the max OR value, we increase the counter. By the end, we return the
	// counter.
	// Tine complexity - O(n*2^n), where n is length of input array nums.
	// Calculating maxOr takes O(n) time. We iterate 2^n subsets and for each
	// subset, the inner loop iterates all n elements. So, the loops take O(n*2^n)
	// time. Overall it's O(n) + O(n*2^n) = O(n*2^n).
	// Space complexity - O(1)
	private static int countMaxOrSubsetsBitManipulation(int[] nums) {
		int maxOr = 0;
		for (int num : nums) {
			maxOr |= num;
		}
		int totalSubsets = 1 << nums.length;

		int countMaxOr = 0;

		// Iterate through all possible subsets
		for (int subsetMask = 0; subsetMask < totalSubsets; subsetMask++) {
			int currentOr = 0;

			// Calculate OR value for current subset.
			for (int i = 0; i < nums.length; i++) {
				if (((subsetMask >> i) & 1) == 1) {
					currentOr |= nums[i];
				}
			}
			if (currentOr == maxOr) {
				countMaxOr++;
			}
		}
		return countMaxOr;
	}

}
