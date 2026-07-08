package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P45. Jump Game II - Medium
 * 
 * You are given a 0-indexed array of integers nums of length n. You are initially positioned at index 0.
 * 
 * Each element nums[i] represents the maximum length of a forward jump from index i. 
 * In other words, if you are at index i, you can jump to any index (i + j) where:
 * 0 <= j <= nums[i] and
 * i + j < n
 * 
 * Return the minimum number of jumps to reach index n - 1. The 
 * test cases are generated such that you can reach index n - 1.
 * 
 * Approach - DP, Greedy.
 */
public class P45JumpGameII {

	public static void main(String[] args) {
		int[] nums = { 2, 3, 1, 1, 4 };
//		int[] nums = { 2, 3, 0, 1, 4 };

		int jumpGreedy = jumpGreedy(nums);
		System.out.println("Greedy: The minimum number of jumps to reach index n - 1: " + jumpGreedy);

		int jumpTabulation = jumpTabulation(nums);
		System.out.println("Tabulation: The minimum number of jumps to reach index n - 1: " + jumpTabulation);

		int jumpMemoized = jumpMemoized(nums);
		System.out.println("Memoized: The minimum number of jumps to reach index n - 1: " + jumpMemoized);
	}

	// Greedy
	// For an index i, if it has value of 3 and the value of destinations are 3, 5
	// and 2. All the indices covered by these ranges are reachale indices after
	// this jump. For 3 it's the cell with 3 and the next 3 cells. If we move on to
	// the jump i+1, the choices of starting indices for jump i+1 is exactly the
	// reachable indices of the jump i?. We can draw the starting range of the jump
	// i+1 and so on. With the increase in the number of jumps, the forward distance
	// also increases, the 1st jump that brings us to the last cell is the jump we
	// should choose. We're guaranteed to reach the last index, the starting range
	// of each jump is always larger than its previous jump(otherwise, we'd be stuck
	// at a jump forever). Given the length of nums as n, in the worst case, we
	// may have n jumps and O(n^2) total updates, which is the new and overlapping
	// region. This leads to O(n^2) time complexity. To reduce the time complexity,
	// we don't need to consider all of the updates. Take the 1st jump at index 0
	// for example, suppose the starting indices of jump at 0 are in range [0,2].
	// When we check starting indices of the next jump, we don't consider the range
	// [0,2]. We want to reach the ending position by using the least number of
	// jumps, so there is no reason in reaching an index using more jumps.
	// Therefore, we take a greedy approach that tries to reach each index using the
	// least number of jumps and ignore the unpdates which leads to more jumps. For
	// the expample, even if we can move to [0,2] in jump 1, we would not consider
	// doing so since we already covered that range with jump 0, So, the valid range
	// of reachable index for jump 1 is [3,4], instead of [0,4]. Instead of
	// considering entire range, we only focus on the newly-added range. In short,
	// if we can reach an index using j jumps, we will never consider reaching it
	// using more than j jumps. So, the overallping regions are discarded in new
	// jumps. We need 2 auxiliary marks to help delimit ranges and to avoid repeated
	// visits to the same range: end is the furthest starting index of the current
	// jump. far is the furthest reachable index of the current jump.
	// Once we've finished iterating over the range of the current jump(we reach
	// index end), the next step is to continue iterating over the reachable indices
	// that are > end, which has range [end + 1, far]. We skip the overlapped range
	// using the greedy approach. In the range of jump i, we update far for jump
	// i+1, similary for the range of jump i + 1, we update far for jump i+2.
	// To summarize: the current jump ends when we reach index end. Between the
	// current index and end, we find the farthest reachable index far. At the end
	// of the current jump, we increment our answer and set end = far for next jump.
	// Algo: Initialize currEnd and currFar as 0 and number of jumps as answer = 0.
	// Iterate over nums, for each index i, the farthest index we can reach from i
	// is i + nums[i]. We update currFar = max(currFar, i + nums[i]). If i =
	// currEnd, it means we've finished the current jump, and should move on to the
	// next jump. Increment answer, and set currEnd = currFar as the furthest we can
	// reach with the next jump. Repeat iterating over nums.
	// Time complexity - O(n), we iterate over nums and stop at the 2nd last
	// element. In each step of the iteration, we make some calculations that take
	// constant time. Therefore, the overall time is O(n).
	// Space complexity - O(1).
	private static int jumpGreedy(int[] nums) {
		int n = nums.length;
		int answer = 0;

		// Starting range of the 1st jump is [0,0].
		int currEnd = 0, currFar = 0;

		for (int i = 0; i < n - 1; i++) {
			// Update the farthest reachable index of this jump.
			currFar = Math.max(currFar, i + nums[i]);

			// If we finish the starting range of this jump, move on to the starting range
			// of the next jump.
			if (i == currEnd) {
				answer++;
				currEnd = currFar;
			}
		}
		return answer;
	}

	private static int jumpTabulation(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		Arrays.fill(dp, 10000);
		dp[n - 1] = 0;

		for (int i = n - 2; i >= 0; i--) {
			int maxJump = Math.min(i + nums[i], n - 1);
			for (int j = i + 1; j <= maxJump; j++) { // same as for (int j = maxJump; j > index; j--)
				dp[i] = Math.min(dp[i], dp[j] + 1);
			}
		}
		return dp[0];
	}

	public static int jumpMemoized(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		Arrays.fill(dp, 10000);

		return dfs(nums, 0, dp);
	}

	private static int dfs(int[] nums, int index, int[] dp) {
		if (index == nums.length - 1) {
			return 0;
		}
		if (dp[index] != 10000) {
			return dp[index];
		}

		int maxJump = Math.min(index + nums[index], nums.length - 1);
		for (int i = maxJump; i > index; i--) { // same as for (int i = index + 1; i <= maxJump; i++)
			int jumps = dfs(nums, i, dp) + 1;
			dp[index] = Math.min(dp[index], jumps);
		}
		return dp[index];
	}

}
