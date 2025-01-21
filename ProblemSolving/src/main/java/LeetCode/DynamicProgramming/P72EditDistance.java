package LeetCode.DynamicProgramming;

/*
 * P72. Edit Distance
 * 
 * Given two strings word1 and word2, return the minimum number of 
 * operations required to convert word1 to word2.
 * 
 * You have the following three operations permitted on a word:
 * - Insert a character
 * - Delete a character
 * - Replace a character
 * 
 * Approach - DP
 * 
 * Transform operation - Insert, Update and Delete is used in Levenshetein Distance
 *  
 */
public class P72EditDistance {

	public static void main(String[] args) {
//		String word1 = "horse";
//		String word2 = "ros";

//		String word1 = "";
//		String word2 = "a";

//		String word1 = "a";
//		String word2 = "ab";

		String word1 = "dinitrophenylhydrazine";
		String word2 = "benzalphenylhydrazone";

		int m = word1.length();
		int n = word2.length();

		int minDistanceTabulation = minDistanceTabulation(word1, word2);
		System.out.println("Tabulation: Min number of operations needed: " + minDistanceTabulation);

		int minDistanceMemo = minDistanceMemoized(word1, word2);
		System.out.println("Memoized: Min number of operations needed: " + minDistanceMemo);

		int minDistanceRecursive = minDistanceRecursive(word1, word2, m, n);
		System.out.println("Recursive: Min number of operations needed: " + minDistanceRecursive);

	}

	// Bottom Up DP approach - Tabulation
	// Time complexity - O(m*n) for filling DP array
	// Space complexity - O(m*n) for DP array
	private static int minDistanceTabulation(String word1, String word2) {
		int m = word1.length();
		int n = word2.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			dp[i][0] = i;
		}
		for (int j = 1; j <= n; j++) {
			dp[0][j] = j;
		}
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
					// Replace i-1, j-1 | Insert j-1 | or Delete i-1
					dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
				} else {
					dp[i][j] = dp[i - 1][j - 1];
				}
			}
		}
		return dp[m][n];
	}

	// Top Down DP approach - Memoization
	// Time complexity - O(m*n) for filling DP array. Due to cache,
	// for every combination of word1 and word2, result is found once.
	// Space complexity - O(m*n) for DP array
	public static int minDistanceMemoized(String word1, String word2) {
		int m = word1.length();
		int n = word2.length();
		int[][] dp = new int[m + 1][n + 1];
		return minDistance(word1, word2, m, n, dp);
	}

	public static int minDistance(String word1, String word2, int i, int j, int[][] dp) {
		if (i == 0) {
			return dp[i][j] = j;
		}
		if (j == 0) {
			return dp[i][j] = i;
		}
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
			return dp[i][j] = minDistance(word1, word2, i - 1, j - 1, dp);
		} else {
			// Replace, insert, delete
			int minReplaceDistance = minDistance(word1, word2, i - 1, j - 1, dp);
			int minInsertDistance = minDistance(word1, word2, i, j - 1, dp);
			int minDeleteDistance = minDistance(word1, word2, i - 1, j, dp);

			return dp[i][j] = Math.min(minReplaceDistance, Math.min(minInsertDistance, minDeleteDistance)) + 1;
		}
	}

	// Time complexity - O(3^a) where a = Max(word1 length, word2 length)
	// Space complexity - O(a), recursion uses internal call stack
	// equal to depth of recursion tree. Recursion ends when word1 or word2 is empty
	// TLE for large strings
	public static int minDistanceRecursive(String word1, String word2, int i, int j) {
		if (i == 0) {
			return j;
		}
		if (j == 0) {
			return i;
		}
		if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
			return minDistanceRecursive(word1, word2, i - 1, j - 1);
		} else {
			// Replace, insert, delete
			int minReplaceDistance = minDistanceRecursive(word1, word2, i - 1, j - 1);
			int minInsertDistance = minDistanceRecursive(word1, word2, i, j - 1);
			int minDeleteDistance = minDistanceRecursive(word1, word2, i - 1, j);

			return Math.min(minReplaceDistance, Math.min(minInsertDistance, minDeleteDistance)) + 1;
		}
	}

}
