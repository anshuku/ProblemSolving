package LeetCode.DynamicProgramming;

/*
 * P712. Minimum ASCII Delete Sum for Two Strings - Medium
 * 
 * Given two strings s1 and s2, return the lowest ASCII sum of deleted characters to make two strings equal.
 * 
 * Approach - DP
 */
public class P712MinimumASCIIDeleteSumForTwoStrings {

	public static void main(String[] args) {
//		String s1 = "sea";
//		String s2 = "eat";

		String s1 = "delete";
		String s2 = "leet";

		int minSumTabulation = minimumDeleteSumTabulation(s1, s2);
		System.out.println("Tabulation: The minimum ASCII delete sum is: " + minSumTabulation);

		int minSumMemoized = minimumDeleteSumMemoized(s1, s2);
		System.out.println("Memoized: The minimum ASCII delete sum is: " + minSumMemoized);
	}

	// DP - Tabulation
	// When the characters are same we can ignore both
	// When the characters are different we need to delete at least one
	// Time complexity - O(M*N) where m and n are lengths of s1 and s2.
	// Space complexity - O(M*N) where m and n are lengths of s1 and s2.
	public static int minimumDeleteSumTabulation(String s1, String s2) {
		int m = s1.length();
		int n = s2.length();

		char[] word1 = s1.toCharArray();
		char[] word2 = s2.toCharArray();

		int[][] dp = new int[m + 1][n + 1];

		for (int i = m - 1; i >= 0; i--) {
			dp[i][n] = dp[i + 1][n] + word1[i];
		}
		for (int j = n - 1; j >= 0; j--) {
			dp[m][j] = dp[m][j + 1] + s2.codePointAt(j);
		}

		for (int i = m - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				if (word1[i] == word2[j]) {
					dp[i][j] = dp[i + 1][j + 1];
				} else {
					dp[i][j] = Math.min(dp[i + 1][j] + word1[i], dp[i][j + 1] + s2.codePointAt(j));
				}
			}
		}
		return dp[0][0];
	}

	private static int minimumDeleteSumMemoized(String s1, String s2) {
		int m = s1.length();
		int n = s2.length();

		char[] word1 = s1.toCharArray();
		char[] word2 = s2.toCharArray();

		int[][] dp = new int[m + 1][n + 1];
		return recursive(word1, word2, 0, 0, dp);
	}

	private static int recursive(char[] word1, char[] word2, int i, int j, int[][] dp) {
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		if (i == word1.length && j == word2.length) {
			return 0;
		}
		if (i == word1.length) {
			return dp[i][j] = recursive(word1, word2, i, j + 1, dp) + word2[j];
		}
		if (j == word2.length) {
			return dp[i][j] = recursive(word1, word2, i + 1, j, dp) + word1[i];
		}
		if (word1[i] == word2[j]) {
			dp[i][j] = recursive(word1, word2, i + 1, j + 1, dp);
		} else {
			dp[i][j] = Math.min(recursive(word1, word2, i + 1, j, dp) + word1[i],
					recursive(word1, word2, i, j + 1, dp) + word2[j]);
		}
		return dp[i][j];
	}
}
