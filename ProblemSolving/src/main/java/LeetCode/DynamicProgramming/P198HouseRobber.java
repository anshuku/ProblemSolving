package LeetCode.DynamicProgramming;

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

		int maxRob1DDP = rob1DDP(nums);
		System.out.println("1D DP: The max robbed amount from houses is: " + maxRob1DDP);

		int maxRob2Vars = rob2Vars(nums);
		System.out.println("2 vars DP: The max robbed amount from houses is: " + maxRob2Vars);
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

	public static int rob1DDP(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n + 1];

		dp[0] = 0;
		dp[1] = nums[0];

		for (int i = 1; i < n; i++) {
			dp[i + 1] = Math.max(dp[i], dp[i - 1] + nums[i]);
		}
		return dp[n];
	}

}
