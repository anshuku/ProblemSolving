package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P1312. Minimum Insertion Steps to Make a String Palindrome - Hard
 * 
 * Given a string s. In one step you can insert any character at any index of the string.
 * 
 * Return the minimum number of steps to make s palindrome.
 * 
 * A Palindrome String is one that reads the same backward as well as forward.
 * 
 * Approach - DP, String reverse
 */
public class P1312MinimumInsertionStepsMakeStringPalindrome {

	public static void main(String[] args) {
		String s = "zzazz";
//		String s = "mbadm";
//		String s = "leetcode";

		int minInsert1DDP = minInsertions1DDP(s);
		System.out.println("1D DP: The min insertions to make the string a palindrome: " + minInsert1DDP);

		int minInsert2DDP = minInsertions2DDP(s);
		System.out.println("2D DP: The min insertions to make the string a palindrome: " + minInsert2DDP);

		int minInsertRecursive = minInsertionsRecursive(s);
		System.out.println("Recursive: The min insertions to make the string a palindrome: " + minInsertRecursive);
	}

	private static int minInsertions1DDP(String s) {
		char[] sArr = s.toCharArray();
		char[] sRevArr = new StringBuilder(s).reverse().toString().toCharArray();
		int n = s.length();
		int[] dp = new int[n + 1];
		for (int i = 0; i < n; i++) {
			int[] curr = new int[n + 1];
			for (int j = 0; j < n; j++) {
				if (sArr[i] == sRevArr[j]) {
					curr[j + 1] = 1 + dp[j];
				} else {
					curr[j + 1] = Math.max(curr[j], dp[j + 1]);
				}
			}
			dp = curr;
		}
		return n - dp[n];
	}

	// DP Tabulation - Bottom Up
	// mbadm
	// mdabm - reverse
	// leetcode
	// edocteel - reverse
	// insert or not insert
	// Task is to insert minimum number of additional characters to s to make it a
	// palindrome. The answer is length of s - longest palindromic subsequence.
	// To get length of longest palindromic subsequence find lcs of string and it's
	// substring.
	// Time complexity - O(n^2) for dp array
	// Space complexity - O(n^2) for dp array, the recursion stack can go upto a
	// maximum size of O(n). In the recusion tree, there are maximum of two
	// branches that can be formed at each level. The recursion stack would only
	// have one call out of the two branches. The height of such a tree would be
	// O(n) because at each stage we're decrementing the length of strings by 1.
	public static int minInsertions2DDP(String s) {
		int n = s.length();
		char[] sArr = s.toCharArray();
		char[] sRevArr = new StringBuilder(s).reverse().toString().toCharArray();
		int[][] dp = new int[n + 1][n + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (sArr[i] == sRevArr[j]) {
					dp[i + 1][j + 1] = 1 + dp[i][j];
				} else {
					dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
				}
			}
		}
		return n - dp[n][n];
	}

	// DP Recursion - Top Down
	private static int minInsertionsRecursive(String s) {
		int n = s.length();
		char[] sArr = s.toCharArray();
		char[] sRevArr = new StringBuilder(s).reverse().toString().toCharArray();
		int[][] dp = new int[n][n];
		for (int[] arr : dp) {
			Arrays.fill(arr, -1);
		}
		return n - solve(sArr, sRevArr, n - 1, n - 1, dp);
	}

	private static int solve(char[] sArr, char[] sRevArr, int i, int j, int[][] dp) {
		// at i = 0 or j = 0 we can calculate values
		// so we take the condition i < 0 or j < 0 instead.
		if (i < 0 || j < 0) {
			return 0;
		}
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		if (sArr[i] == sRevArr[j]) {
			dp[i][j] = 1 + solve(sArr, sRevArr, i - 1, j - 1, dp);
		} else {
			dp[i][j] = Math.max(solve(sArr, sRevArr, i - 1, j, dp), solve(sArr, sRevArr, i, j - 1, dp));
		}
		return dp[i][j];
	}

}
