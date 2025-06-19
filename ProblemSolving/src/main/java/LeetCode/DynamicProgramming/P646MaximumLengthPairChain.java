package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P646. Maximum Length of Pair Chain - Medium
 * 
 * You are given an array of n pairs pairs where pairs[i] = [lefti, righti] and lefti < righti.
 * 
 * A pair p2 = [c, d] follows a pair p1 = [a, b] if b < c. A chain of pairs can be formed in this fashion.
 * 
 * Return the length longest chain which can be formed.
 * 
 * You do not need to use up all the given intervals. You can select pairs in any order.
 * 
 * Approach - DP
 */
public class P646MaximumLengthPairChain {

	public static void main(String[] args) {
		int[][] pairs = { { 1, 2 }, { 2, 3 }, { 3, 4 } };
//		int[][] pairs = { { 1, 2 }, { 7, 8 }, { 4, 5 } };
//		int[][] pairs = { { 1, 8 }, { 2, 3 }, { 3, 4 } };

		int longestChainDP = findLongestChainDP(pairs);
		System.out.println("DP: The longest chain formed has length: " + longestChainDP);

		int longestChainGreedy = findLongestChainGreedy(pairs);
		System.out.println("Greedy: The longest chain formed has length: " + longestChainGreedy);
	}

	// Greedy
	// Greedily add to the chain. Sort the input pairs based on 2nd coordinate.
	// Choosing a pair with lower 2nd coordinate is better than larger 2nd
	// coordinate. This maximizes the room for future pairs to fit.
	// Here we choose the interval with smallest ending point that doesn't
	// overlaps with previous. Curr keeps track of end of last pair in chain.
	// Time complexity - O(n*logn) for sorting.
	// Space complexity - O(n) for sorting based on implementation.
	private static int findLongestChainGreedy(int[][] pairs) {
		Arrays.sort(pairs, (a, b) -> Integer.compare(a[1], b[1]));
		int curr = Integer.MIN_VALUE;
		int count = 0;
		for (int[] pair : pairs) {
			if (curr < pair[0]) {
				count++;
				curr = pair[1];
			}
		}
		return count;
	}

	// Dynamic Programming
	// Sort the input pairs based on first coordinate of array.
	// If a chain of length k ends at some pair[j], and j < i and
	// pairs[i][0] > pairs[j][1], the chain's length can become k+1.
	// dp[i] is length of longest chain formed at index i.
	// Time complexity - O(n^2) for nested for loops.
	// Space complexity - O(n) for dp array.
	public static int findLongestChainDP(int[][] pairs) {
		Arrays.sort(pairs, (a, b) -> Integer.compare(a[0], b[0]));
		int n = pairs.length;

		int[] dp = new int[n];
		int count = 0;
		for (int i = 0; i < n; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (pairs[i][0] > pairs[j][1]) {
					dp[i] = dp[j] + 1;
				}
			}
			count = Math.max(count, dp[i]);
		}
		return count;
	}

}
