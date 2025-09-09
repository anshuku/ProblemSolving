package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P377. Combination Sum IV - Medium
 * 
 * Given an array of distinct integers nums and a target integer target, 
 * return the number of possible combinations that add up to target.
 * 
 * The test cases are generated so that the answer can fit in a 32-bit integer.
 * 
 * Approach - DP, Backtracking
 * 
 * dp[target] or keeping start constant -> permuatations(order matters)
 * dp[target][i] with recursive call on i -> combinations(order doesn't matter)
 */
public class P377CombinationSumIV {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 3 };
//		int target = 4;

		int[] nums = { 2, 1, 3 };
		int target = 35;

//		int[] nums = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210,
//				220, 230, 240, 250, 260, 270, 280, 290, 300, 310, 320, 330, 340, 350, 360, 370, 380, 390, 400, 410, 420,
//				430, 440, 450, 460, 470, 480, 490, 500, 510, 520, 530, 540, 550, 560, 570, 580, 590, 600, 610, 620, 630,
//				640, 650, 660, 670, 680, 690, 700, 710, 720, 730, 740, 750, 760, 770, 780, 790, 800, 810, 820, 830, 840,
//				850, 860, 870, 880, 890, 900, 910, 920, 930, 940, 950, 960, 970, 980, 990, 111 };
//		int target = 999;

		int combinationsTabulation = combinationSum4Tabulation(nums, target);
		System.out.println("Tabulation: The number of combinations which add upto target: " + combinationsTabulation);

		int combinationsMemoized = combinationSum4Memoized(nums, target);
		System.out.println("Memoization: The number of combinations which add upto target: " + combinationsMemoized);

		int combinationsMemoized2 = combinationSum4Memoized2(nums, target);
		System.out.println("Memoization2: The number of combinations which add upto target: " + combinationsMemoized2);
	}

	// DP Tabulation - Bottom up
	// Formula: combs(target) = Sum i = 0->n combs(target - nums[i]) if
	// target>=nums[i], combs(target) gives the number of combinations that sums
	// upto target. We can define an array named dp[i] where each element
	// corresponds to the result of combs(i), number of combinations that sum to i.
	// Earlier to obtain combs(i), we started with target and recursively reduce it
	// to the base case, target=0. We start from dp[0] and reach target, dp[target].
	// We mark dp[0] = 1, it indicates there is one combination that sum upto 0
	// which happens when we select no number. This is used to facilitate the
	// calculation later. We then move from low index to high index and deduct
	// values one by one. For target = 4 and nums = [1,2,3]. With dp[0] we can get
	// dp[1], this gets broken down into a single case where where we place the
	// number 1 as last number in the combination and the remainder sum becomes
	// 0(1-1 = 0). So, dp[1] = dp[0] = 1. This happens if nums has 1. dp[0] = 1
	// means there exists one combination if the target value can be reduced down to
	// 0̰. For dp[2], 2 cases. We can either place 1 as last value and it becomes
	// dp[1], or place 2 as last value, then it becomes dp[0]. To sum up
	// dp[2] = dp[0] + dp[1]. At each step, calculation of current value depends on
	// previously calculated intermediate values.
	// Time complexity - O(T*N), we've a nested loop, with number of iterations as T
	// and N respectively.
	// Space complexity - O(T) for storing dp array.
	private static int combinationSum4Tabulation(int[] nums, int target) {
		int[] dp = new int[target + 1];
		dp[0] = 1;
		for (int i = 1; i <= target; i++) {
			for (int num : nums) {
				if (i >= num) {
					dp[i] += dp[i - num];
				}
			}
		}
		return dp[target];
	}

	// DP Memoization - Top Down
	// The reference of combination here is permutation where the order does matter.
	// Since the order is important, we build the solution recursively and in
	// backward manner. We find all subsets of combinations that end with each of
	// the candidate numbers. Backtracking algorithm is used to build all possible
	// combinations but here we need DP as we need the total number of combinations.
	// Recurrence: combs(target) = Union[combs(target - nums[i])], target >= nums[i]
	// combs(target) gives the combinations that sums up to target value. This gives
	// all the valid combinations. To obtain number of combinations, reformulate it.
	// Recurrence: combs(target) = sum[combs(target - nums[i])], target >= nums[i]
	// where i = 0->n, combs(target) gives the number of combinations that sums up
	// to target value. Total number of combinations is the sum of all subsets of
	// combinations where each subset of combinations ends with a specific number.
	// In top down strategy, we start from original input and then we recursively
	// reduce the input to a smaller scale until we reach the levels that doesn't
	// breaks further. We only care about the remaining target which is state here.
	// Here since the order matters, I don't need an extra dimension to track an
	// index or starting point. Memoizing by just target is enough since the set of
	// available numbers is always the same. [1,2] and [2,1] are picked separately
	// as we can pick any number from nums array, if target is 3, picked 1 and we
	// pass 2 as target for 2nd call and we can pick 2 as valid solution since we
	// will skip 1 and reach 2 due to target >= num logic. When 2nd call finishes it
	// comes to first call and we can pick 2 and can pass remaining target as 1 in
	// 2nd recursive call, there we can pick 1. Hence, order matters.
	// Time complexity - O(T*N), due to memoization, for each invocation of
	// combs(target) will be evaluated only once for each unique input. In worst
	// case we have T unique values when num is 1. For each invocation of
	// combs(target), in worst case it takes O(N) time for non recursive part.
	// Space complexity - O(T), due to recursive function, algorithm incurs
	// additional memory consumption in the function call stack. In worst case the
	// function can pile upto T times so O(T) for recursive function. Memo takes
	// O(T) as well for storing intermediate results in worst case.
	private static int combinationSum4Memoized(int[] nums, int target) {
		int[] dp = new int[target + 1];
		Arrays.fill(dp, -1);
		return dfs(nums, target, dp);
	}

	private static int dfs(int[] nums, int target, int[] dp) {
		if (target == 0) {
			return 1;
		}
		if (dp[target] != -1) {
			return dp[target];
		}
		int combinations = 0;
		for (int num : nums) {
			if (target >= num)
				combinations += dfs(nums, target - num, dp);
		}
		return dp[target] = combinations;
	}

	// DP Memoization - Top down
	// Here we pass start which remains same and is redundant - we're using full
	// nums array anyways. The dp just needs target as state and start can be
	// avoided. With start, the dp table is storing the same value multiple times
	// (for different start), but always consistent, since it recomputes with full
	// set of numbers. dp[target][start] only depends on target and degenerates into
	// same recurrence as solution without start.
	// If we pass i instead of start, when we recurse, the next call only considers
	// nums[i, ..end] not all of nums. This enforces an order, once we pick a number
	// at index i, one can only pick that number or ones after it in future choices.
	public static int combinationSum4Memoized2(int[] nums, int target) {
		Integer[][] dp = new Integer[target + 1][nums.length];
//		Arrays.sort(nums);
		return dfs2(nums, target, 0, dp);
	}

	private static int dfs2(int[] nums, int target, int start, Integer[][] dp) {
		if (target == 0) {
			return 1;
		}
		if (dp[target][start] != null) {
			return dp[target][start];
		}
		int combinations = 0;
		for (int i = start; i < nums.length; i++) {
			if (target >= nums[i]) {
				combinations += dfs2(nums, target - nums[i], start, dp);
			}
//			else {
//				break;
//			}
		}
		return dp[target][start] = combinations;
	}
}
