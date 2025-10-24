package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P474. Ones and Zeroes - Medium
 * 
 * You are given an array of binary strings strs and two integers m and n.
 * 
 * Return the size of the largest subset of strs such that there are at most m 0's and n 1's in the subset.
 * 
 * A set x is a subset of a set y if all elements of x are also elements of y.
 * 
 * Approach - DP
 */
public class P474OnesandZeroes {

	public static void main(String[] args) {
		String[] strs = { "10", "0001", "111001", "1", "0" };
		int m = 5;
		int n = 3;

//		String[] strs = { "10", "1", "0" };
//		int m = 1;
//		int n = 1;

//		String[] strs = { "0", "11", "1000", "01", "0", "101", "1", "1", "1", "0", "0", "0", "0", "1", "0", "0110101",
//				"0", "11", "01", "00", "01111", "0011", "1", "1000", "0", "11101", "1", "0", "10", "0111" };
//		int m = 9;
//		int n = 80;

		int largestSubset2DDP = findMaxForm2DDP(strs, m, n);
		System.out.println("2D DP: The size of largest subset with at most m 0s and n 1s " + largestSubset2DDP);

		int largestSubsetMemoized = findMaxFormMemoized(strs, m, n);
		System.out.println("Memoized: The size of largest subset with at most m 0s and n 1s " + largestSubsetMemoized);

		int largestSubsetBF = findMaxFormBF(strs, m, n);
		System.out.println("Brute force: The size of largest subset with at most m 0s and n 1s " + largestSubsetBF);
	}

	// DP - Tabulation: Bottom up
	// dp[i][j] denotes the maximum number of strings that can be included in the
	// subset given i 0's and j 1's are available. To fill the dp array:
	// We traverse the given list of strings, at some point, we pick any string sk
	// with x zeroes and y ones. Now, choosing to put this string in any of the
	// subset possible by using the previous strings traversed so far will impact
	// the element denoted by dp[i][j] for i, j satisfying x <= i <= m, y <= j <= n.
	// This is because for entries dp[i][j] with i < x or j < y, there won't be
	// sufficient number of 1's and 0's available to accomoade the current string in
	// any subset. For every string encountered, we need to appropriately update the
	// dp entries within the correct range. Further, while chossing the curernt
	// string to be part of subset, the updated result will depend on whether it's
	// profitable to include it. If included dp[i[j] is updated as
	// dp[i][j] = 1 + dp[i-zeroes(curr_string)][j-ones(curr_string)], where the
	// factor of +1 is due to new entry into the subset. But, its possible that the
	// current string is so long that it's not profitable to include in any subset.
	// dp[i][j] = max(dp[i][j], 1 + dp[i-zeroes(curr_string)][j-ones(curr_string)])
	// After traversing entire strings dp[m][n] gives the largest subset.
	// Time complexity - O(m*n*l), l is length of strs. There are 3 nested loops.
	// Space complexity - O(m*n) for dp array.
	private static int findMaxForm2DDP(String[] strs, int m, int n) {
		int[][] dp = new int[m + 1][n + 1];
		for (String str : strs) {
			int zeroes = 0, ones = 0;
			for (char c : str.toCharArray()) {
				if (c == '0') {
					zeroes++;
				} else {
					ones++;
				}
			}
			for (int i = m; i >= zeroes; i--) {
				for (int j = n; j >= ones; j--) {
					dp[i][j] = Math.max(dp[i][j], 1 + dp[i - zeroes][j - ones]);
				}
			}
		}
		return dp[m][n];
	}

	// DP - Memoization: Top down
	// Here, subsets are formed recursively. dfs(strs, i, zeroes, ones) gives the
	// size of the largest subsets with zeroes 0's and ones 1's considering the
	// strings lying after the ith index(including itself) in strs. 2 scenarios:
	// 1) Include string from the subset currently being considered and remove the
	// number of 0's and 1's in current string from total counts(m and n).
	// dfs(strs, i + 1, zeroes - 0scurr str, ones - 1scurr str) + 1. Here +1 to
	// increment the total number of strings considered so far and store in taken.
	// i + 1 as we can't consider the same string more than once.
	// 2) Not include the current string in the current subset. Here we don't update
	// the count of zeroes and ones. notTaken = dfs(strs, i + 1, zeroes, ones).
	// The larger value out of taken and notTaken is result for current function.
	// To remove redundant function calls with same i, zeroes and ones we use a 3-D
	// memo. Here we use 3-D dp since there are 3 variables to store the results of
	// dfs(strs, i, zeroes, ones). It stores the maximum number of subsets possible
	// considering the strings starting from ith index with zeroes 0s and ones 1s.
	// Time complexity - O(l*m*n), as dp of size l*m*n is filled.
	// Space complexity - O(l*m*n) for memo array.
	public static int findMaxFormMemoized(String[] strs, int m, int n) {
		int[][][] dp = new int[strs.length][m + 1][n + 1];
		for (int[][] arr1 : dp) {
			for (int[] arr2 : arr1) {
				Arrays.fill(arr2, -1);
			}
		}
		return dfs(strs, m, n, 0, dp);
	}

	private static int dfs(String[] strs, int m, int n, int start, int[][][] dp) {
		if (start == strs.length) {
			return 0;
		}
		if (dp[start][m][n] != -1) {
			return dp[start][m][n];
		}
		int ones = 0;
		int zeroes = 0;
		for (char c : strs[start].toCharArray()) {
			if (c == '0') {
				zeroes++;
			} else {
				ones++;
			}
		}
		// skip current string
		int notTake = dfs(strs, m, n, start + 1, dp);
		int take = 0;
		// pick current string(if enough capacity)
		if (m >= zeroes && n >= ones) {
			take = 1 + dfs(strs, m - zeroes, n - ones, start + 1, dp);
		}
		return dp[start][m][n] = Math.max(notTake, take);
	}

	// Brute force - Bitmask enumeration
	// Here we consider every subset of strs array and count the total number of
	// zeroes and ones in that subset. The subset with zeroes and ones less than m
	// and n are valid subsets. The maximum length subset among all valid subsets
	// will be the answer. There are 2^n subsets possible for list of length n, and
	// we're using 32 bit int for iterating every subset. It'll work till len < 32.
	// Time complexity - O(2^n*n*x) where n = strs length, x is average strs[i] len.
	// Space complexity - O(1) for constant space.
	private static int findMaxFormBF(String[] strs, int m, int n) {
		int maxLen = 0;
		// 1 << strs.length = 2^strs.length
		// Each integer i between 0 and 2^len - 1 is a bitmask representing a subset
		// for len = 3, we have i = 0 to 7 for i = 5 in binary we have 101 and we pick
		// strs[0] and strs[2] and not take strs[1].
		for (int i = 0; i < (1 << strs.length); i++) {
			// len counts the number of strings included in the subset.
			int zeroes = 0, ones = 0, len = 0;
			// iterates over each string to check whether it belongs to current subset.
			for (int j = 0; j < strs.length; j++) {
				// check if the jth bit of i is set, or strs[j] is included in the subset.
				// for i = 5 or 101 only j = 0 and j = 2, condition is true.
				if ((i & (1 << j)) != 0) {
					int[] count = getZeroesOnes(strs[j]);
					// adds string's contribution to subset totals.
					zeroes += count[0];
					ones += count[1];
					// Better brute force
					// It limits the number of subsets by breaking the loop when 0s > m || 1s > n
					// This reduces computation.
					if (zeroes > m || ones > n) {
						break;
					}
					// increments subset length
					len++;
				}
			}
			// After processing one subset, checks if it's valid
			if (zeroes <= m && ones <= n) {
				maxLen = Math.max(maxLen, len);
			}
		}
		return maxLen;
	}

	private static int[] getZeroesOnes(String str) {
		int[] count = new int[2];
		for (char c : str.toCharArray()) {
			if (c == '0') {
				count[0]++;
			} else {
				count[1]++;
			}
		}
		return count;
	}

}
