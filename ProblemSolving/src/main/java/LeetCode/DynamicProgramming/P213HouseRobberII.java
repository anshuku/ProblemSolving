package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P213. House Robber II - Medium
 * 
 * You are a professional robber planning to rob houses along a street. Each house 
 * has a certain amount of money stashed. All houses at this place are arranged in 
 * a circle. That means the first house is the neighbor of the last one. Meanwhile, 
 * adjacent houses have a security system connected, and it will automatically contact 
 * the police if two adjacent houses were broken into on the same night.
 * 
 * Given an integer array nums representing the amount of money of each house, 
 * return the maximum amount of money you can rob tonight without alerting the police.
 * 
 * Approach - DP
 */
public class P213HouseRobberII {

	public static void main(String[] args) {
//		int[] nums = { 2 };
//		int[] nums = { 2, 3, 2 };
//		int[] nums = { 1, 2, 3, 1 };
//		int[] nums = { 1, 2, 3 };
		int[] nums = { 200, 3, 140, 20, 10 };

		int robbedSum2Vars = rob2Vars(nums);
		System.out.println("2 Vars: The maximum amount of money robbed by robber: " + robbedSum2Vars);

		int robbedSum1DDP = rob1DDP(nums);
		System.out.println("1D DP: The maximum amount of money robbed by robber: " + robbedSum1DDP);

		int robbedSum2DDP = rob2DDP(nums);
		System.out.println("2D DP: The maximum amount of money robbed by robber: " + robbedSum2DDP);

		int robbedSum = robRecursive(nums);
		System.out.println("The maximum amount of money robbed by robber: " + robbedSum);
	}

	private static int rob2Vars(int[] nums) {
		int n = nums.length;
		int maxRobWithN = rob2Vars(nums, 0, n - 1);
		int maxRobWithoutN = rob2Vars(nums, 1, n);
		return Math.max(maxRobWithN, maxRobWithoutN);
	}

	private static int rob2Vars(int[] nums, int l, int n) {
		int excl = 0;
		int incl = nums[l];
		for (int i = l + 1; i < n; i++) {
			int newExcl = Math.max(excl, incl);
			incl = excl + nums[i];
			excl = newExcl;
		}
		return Math.max(excl, incl);
	}

	private static int rob1DDP(int[] nums) {
		int n = nums.length;
		if (n == 1) {
			return nums[0];
		}
		int[] dp = new int[n + 1];
		int maxRobWithN = rob1DDP(nums, 0, n - 1, dp);
		int maxRobWithoutN = rob1DDP(nums, 1, n, dp);
		return Math.max(maxRobWithN, maxRobWithoutN);
	}

	private static int rob1DDP(int[] nums, int l, int n, int[] dp) {
		dp[l] = 0;
		dp[l + 1] = nums[l];
		// i <= n to find till dp[n]
		for (int i = l + 2; i <= n; i++) {
			dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
		}
		return dp[n];
	}

	private static int rob2DDP(int[] nums) {
		int n = nums.length;
		if (n == 1) {
			return nums[0];
		}
		int[][] dp = new int[n][2];
		int maxRobWithN = rob2DDP(nums, 0, n - 1, dp);
//		dp = new int[n][2];
		int maxRobWithoutN = rob2DDP(nums, 1, n, dp);
		return Math.max(maxRobWithN, maxRobWithoutN);
	}

	private static int rob2DDP(int[] nums, int l, int n, int[][] dp) {
		dp[l][0] = 0;
		dp[l][1] = nums[l];
		for (int i = l + 1; i < n; i++) {
			// not take = max(last not taken, last taken) either of but max
			dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
			// take = last not taken + current
			dp[i][1] = dp[i - 1][0] + nums[i];
		}
		return Math.max(dp[n - 1][0], dp[n - 1][1]);
	}

	public static int robRecursive(int[] nums) {
		int n = nums.length;
		if (n == 1) {
			return nums[0];
		}
		int[] dp = new int[n];
		Arrays.fill(dp, -1);
		int maxRobWithoutN = recursive(nums, 0, n - 1, dp);
		Arrays.fill(dp, -1);
		int maxRobWithN = recursive(nums, 1, n, dp);
		return Math.max(maxRobWithoutN, maxRobWithN);
	}

	private static int recursive(int[] nums, int i, int n, int[] dp) {
		if (i >= n) {
			return 0;
		}
		if (dp[i] != -1) {
			return dp[i];
		}
		return dp[i] = Math.max(recursive(nums, i + 1, n, dp), recursive(nums, i + 2, n, dp) + nums[i]);
	}

}
