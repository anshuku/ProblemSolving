package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P198. House Robber - Medium
 * 
 * You are a professional robber planning to rob houses along a street. Each house 
 * has a certain amount of money stashed, the only constraint stopping you from robbing 
 * each of them is that adjacent houses have security systems connected and it will 
 * automatically contact the police if two adjacent houses were broken into on the same night.
 * 
 * Given an integer array nums representing the amount of money of each house, return 
 * the maximum amount of money you can rob tonight without alerting the police.
 * 
 * Approach - 1D DP
 */
public class P198HouseRobber {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 3, 1 };
//		int[] nums = { 2, 7, 9, 3, 1 };
		int[] nums = { 3, 1, 2, 4 };

		int maxRob2Vars = rob2Vars(nums);
		System.out.println("2 vars DP: The max robbed amount from houses is: " + maxRob2Vars);

		int maxRob1DDP = rob1DDPBU(nums);
		System.out.println("1D DP: The max robbed amount from houses is: " + maxRob1DDP);

		int maxRobMemoized = robMemoizedTD(nums);
		System.out.println("Memoization The max robbed amount from houses is: " + maxRobMemoized);

	}

	private static int rob2Vars(int[] nums) {
		int n = nums.length;
		int val1 = 0;
		int val2 = nums[0];

		for (int i = 1; i < n; i++) {
			int val = Math.max(val1 + nums[i], val2);
			val1 = val2;
			val2 = val;
		}
		return val2;
	}

	// Tabulation - Bottom up
	// The cache is the dp table
	public static int rob1DDPBU(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n + 1];

		dp[0] = 0;
		dp[1] = nums[0];

		for (int i = 1; i < n; i++) {
			dp[i + 1] = Math.max(dp[i], dp[i - 1] + nums[i]);
		}
		return dp[n];
	}

	// Recusion with memoization - Top down
	// Here we must try all the possible combinations of houses and then use the
	// combination which gives the maxiumum ammount. There is no greedy strategy.
	// We use Recusion since there are choices involved. Basic choice: Decide
	// whether to rob the current house or not. If they rob the current house they
	// must skip the next house. Otherwise, they can evaluate the same choice on the
	// next house i.e. to rob or not to rob. To solve the problem recursively we
	// need to ensure that it can be broken down into subproblems. The optimal
	// solution to the subproblem can be used to solve the main problem.
	// At each step, the robber has two options. If he chooses to rob the current
	// he must skip the next house in the list by moving two steps forward.
	// If he chooses not to rob the current house, he can simply move on to the
	// next house on the list. robFrom(i) = max(robFrom(i+1), robFrom(i+2)+nums[i])
	// For repeating sub-problems we can use memoization or caching to reduce the
	// overall solution complexity.
	// This approach is not suitable when the call stack grows too large. It may
	// also run into trouble because for each recursive call the compiler must
	// do additional work to maintain the call stack(funtion variables etc) which
	// results in unwanted overhead.
	// Time complexity - O(n) for dp array.
	// Space complexity - O(n) for dp array and recursion stack.
	private static int robMemoizedTD(int[] nums) {
		int[] dp = new int[nums.length];
		Arrays.fill(dp, -1);
		return recursive(nums, 0, dp);
	}

	private static int recursive(int[] nums, int i, int[] dp) {
		if (i >= nums.length) {
			return 0;
		}
		if (dp[i] != -1) {
			return dp[i];
		}
		return dp[i] = Math.max(recursive(nums, i + 1, dp), recursive(nums, i + 2, dp) + nums[i]);
	}
}
