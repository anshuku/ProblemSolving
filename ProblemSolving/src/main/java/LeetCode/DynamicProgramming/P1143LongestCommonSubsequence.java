package LeetCode.DynamicProgramming;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/*
 * P1143. Longest Common Subsequence - Medium
 * 
 * Given two strings text1 and text2, return the length of their longest 
 * common subsequence. If there is no common subsequence, return 0.
 * 
 * A subsequence of a string is a new string generated from the original string with some 
 * characters (can be none) deleted without changing the relative order of the remaining characters.
 * 
 * For example, "ace" is a subsequence of "abcde". A common subsequence of 
 * two strings is a subsequence that is common to both strings.
 * 
 * Approach - Multidimensional DP
 */
public class P1143LongestCommonSubsequence {

	public static void main(String[] args) {

		String text1 = "abcde";
		String text2 = "ace";

//		String text1 = "abc";
//		String text2 = "def";

//		String text1 = "";
//		String text2 = "";

		int longestCharArrCallable = longestCommonSubsequenceCharCallable(text1, text2);
		System.out.println("Array Callable: The longest common subsequence length is: " + longestCharArrCallable);

		int longestCharArr = longestCommonSubsequenceCharArr(text1, text2);
		System.out.println("Char Array: The longest common subsequence length is: " + longestCharArr);

		int longestString = longestCommonSubsequenceString(text1, text2);
		System.out.println("String: The longest common subsequence length is: " + longestString);
	}

	private static int longestCommonSubsequenceCharCallable(String text1, String text2) {
		Callable<Integer> callable = () -> (longestCommonSubsequenceCharArr(text1, text2));
		FutureTask<Integer> task = new FutureTask<Integer>(callable);
		new Thread(task).start();
		int val = 0;
		try {
			val = task.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

	private static int longestCommonSubsequenceCharArr(String text1, String text2) {
		char[] arr1 = text1.toCharArray();
		char[] arr2 = text2.toCharArray();
		int m = arr1.length;
		int n = arr2.length;

		int[][] dp = new int[m + 1][n + 1];

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (arr1[i - 1] == arr2[j - 1]) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		return dp[m][n];
	}

	public static int longestCommonSubsequenceString(String text1, String text2) {
		int m = text1.length();
		int n = text2.length();

		int[][] dp = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1] + 1;
				} else {
					dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
				}
			}
		}
		return dp[m][n];
	}

}
