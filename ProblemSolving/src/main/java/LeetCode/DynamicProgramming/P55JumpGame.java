package LeetCode.DynamicProgramming;

/*
 * P55. Jump Game - Medium
 * 
 * You are given an integer array nums. You are initially positioned at the array's first 
 * index, and each element in the array represents your maximum jump length at that position.
 * 
 * Return true if you can reach the last index, or false otherwise.
 * 
 * Approach - DP, Greedy
 */
public class P55JumpGame {

	public static void main(String[] args) {
		int[] nums = { 2, 3, 1, 1, 4 };
//		int[] nums = { 3, 2, 1, 0, 4 };
//		int[] nums = { 1 };

		boolean canJumpGreedy = canJumpGreedy(nums);
		System.out.println("Greedy: One can reach the last index: " + canJumpGreedy);

		boolean canJumpTabulation = canJumpTabulation(nums);
		System.out.println("Tabulation: One can reach the last index: " + canJumpTabulation);

		boolean canJumpTabulation2 = canJumpTabulation2(nums);
		System.out.println("Tabulation2: One can reach the last index: " + canJumpTabulation2);

		boolean canJumpMemoized = canJumpMemoized(nums);
		System.out.println("Memoized: One can reach the last index: " + canJumpMemoized);

		boolean canJumpMemoized2 = canJumpMemoized2(nums);
		System.out.println("Memoized2: One can reach the last index: " + canJumpMemoized2);

		boolean canJumpRecursion = canJumpRecursion(nums);
		System.out.println("Recursion: One can reach the last index: " + canJumpRecursion);
	}

	// Greedy
	// From bottom up solution one can observe that from a given position, when we
	// try to see if we can jump to a GOOD position, we only ever use one - the
	// first one which is the left-most. If we keep track of this left-most GOOD
	// position as a separate variable, we can avoid searching for it in the array
	// and avoid the array as well. Iterating right-to-left, for each position we
	// check if there is a potential jump that reaches a GOOD index(currPosition +
	// nums[currPosition] >= leftMostGoodIndex). If we can reach a GOOD index, then
	// our position is itself GOOD. Also, this new GOOD position will be the new
	// leftmost GOOD index. Iteration continues until the beginning of the array.
	// If 1st position is a GOOD index then we can reach the last index.
	// Time complexity - O(n), as we do single pass of the nums array, hence n steps
	// where n is the length of array nums.
	// Space complexity - O(1), as we're not using any extra memory.
	private static boolean canJumpGreedy(int[] nums) {
		int n = nums.length;
		int lastGood = n - 1;
		for (int i = n - 1; i >= 0; i--) {
			if (i + nums[i] >= lastGood) {
				lastGood = i;
			}
		}

		return lastGood == 0;
	}

	private static boolean canJumpTabulation2(int[] nums) {
		int n = nums.length;
		boolean[] dp = new boolean[n];
		dp[n - 1] = true;

		for (int i = n - 2; i >= 0; i--) {
			int maxJump = Math.min(i + nums[i], n - 1);
			for (int j = i + 1; j <= maxJump; j++) {
				if (dp[j]) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[0];
	}

	// DP: Bottom-Up
	// Top-Down to bottom-up conversion is done by eliminating recursion. In
	// practice, it has better performance as we no longer have a method stack
	// overhead and we get caching benefit as well. It allows possibilities for
	// future optimization. The recursion is usually eliminated by trying to reverse
	// the order of steps from the top-down approach. The observation to make here
	// is that we only ever jump to the right once. It means if we start from the
	// right of the array, every time we'll query a position to our right, that
	// position has already be determined as being GOOD/BAD. It means we don't need
	// to recurse anymore, as we will always hit the memo table.
	// Time complexity - O(n^2), for every element in the array, say i, we're
	// looking at the next nums[i](<=n) elements to its right aiming to find a GOOD
	// index.
	// Space complexity - O(n), from memo table.
	private static boolean canJumpTabulation(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		dp[n - 1] = 1;

		for (int i = n - 2; i >= 0; i--) {
			int maxJump = Math.min(i + nums[i], n - 1);
			for (int j = i + 1; j <= maxJump; j++) {
				if (dp[j] == 1) {
					dp[i] = 1;
					break;
				}
			}
		}
		return dp[0] == 1;
	}

	// DP Memoized - Top Down
	// Top-down DP is kindof optimized backtracking. It relies on the observation
	// that once we determine that a certain index is good/bad the result will not
	// change. It means that we can store the result and not need to recompute it
	// every time. For each position in the array, we store if it's good or bad. We
	// call the array memo and let its values be either of GOOD=2, BAD=1, and
	// UNKNOWN=0. Steps: Initialize all elements of the memo table as UNKNOWN=0,
	// except for last one which is GOOD=2 as it can reach itself. Modify the
	// backtracking algorithm such that the recursive step first checks if the index
	// is known(1/2). a) If it's known then return the memo[index] = True/False. b)
	// Otherwise perform the backtracking steps as before. Once we determine the
	// value of the current index, we store it in the memo table.
	// Time complexity - O(n^2), for every element in the array, say i, we're
	// looking at the next nums[i](<=n) elements to its right aiming to find a GOOD
	// index.
	// Space complexity - O(2n) = O(n), first n originates from recursion and 2nd
	// from memo.
	private static boolean canJumpMemoized(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		dp[n - 1] = 2;
		return recursive(nums, 0, dp);
	}

	private static boolean recursive(int[] nums, int index, int[] dp) {
		if (dp[index] != 0) {
			return dp[index] == 2;
		}
		int maxJump = Math.min(index + nums[index], nums.length - 1);

		for (int i = index + 1; i <= maxJump; i++) {
			if (recursive(nums, i, dp)) {
				dp[i] = 2;
				return true;
			}
		}
		dp[index] = 1;
		return false;
	}

	private static boolean canJumpMemoized2(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		dp[n - 1] = 2;
		return recursive2(nums, 0, dp);
	}

	private static boolean recursive2(int[] nums, int index, int[] dp) {
		if (dp[index] != 0) {
			return dp[index] == 2;
		}
		int maxJump = Math.min(index + nums[index], nums.length - 1);

		for (int i = maxJump; i > index; i--) {
			if (recursive2(nums, i, dp)) {
				dp[i] = 2;
				return true;
			}
		}
		dp[index] = 1;
		return false;
	}

	// Recursion - Brute Force
	// We call a position in array as a good index if starting from that position,
	// one can reach the last index. Otherwise, that index is called as bad index.
	// We check whether index 0 is a good index. The recursive solution is
	// inefficient as we try every single jump that takes one from the 1st postion
	// to the last. We start from 1st position and jump to every index that is
	// reachable. We repeat it until last index is reached. If stuck, backtrack.
	// Optimization: We check for nextPosition from right to left. The theoretical
	// worst case is same, but for some examples, the code will be faster. It means
	// we always try to make the biggest jump to reach the end sooner.
	// Time complexity - O(2^n), there are 2^n ways of jumping from 1st position to
	// the last. We get this recursively. Let T(x) be the number of possible ways of
	// jumping from position x to position n. T(n) = 1 trivially.
	// T(x) = Σ i=x+1 to n T(i), because from position x we can jump to all
	// following positions i and then from there, there are T(i) ways of continuing.
	// It's an upper bound. T(x) = Σ i=x+1 to n T(i) = T(x+1) + Σ i=x+2 to n T(i)
	// = T(x+1) + T(x+1) = 2*T(x+1). By induction, assume T(x) = 2^(n-x-1) and prove
	// T(x-1) = 2^(n - (x-1) - 1. T(x-1) = 2*T(x) = 2*2^(n-x-1) = 2^(n - x - 1 + 1)
	// = 2^(n - (x-1) - 1. Here as we start from position 1, T(1) = 2^(n-2).
	// Space complexity - O(n), the recursion requires additional memory for the
	// stack frames.
	public static boolean canJumpRecursion(int[] nums) {
		return recursive(nums, 0);
	}

	private static boolean recursive(int[] nums, int index) {
		if (index == nums.length - 1) {
			return true;
		}
		int maxIndex = Math.min(index + nums[index], nums.length - 1);
		for (int i = index + 1; i <= maxIndex; i++) {
			if (recursive(nums, i)) {
				return true;
			}
		}
		return false;
	}

}
