package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P62. Unique Paths - Medium
 * 
 * There is a robot on an m x n grid. The robot is initially located at the top-left 
 * corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner 
 * (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
 * 
 * Given the two integers m and n, return the number of possible 
 * unique paths that the robot can take to reach the bottom-right corner.
 * 
 * The test cases are generated so that the answer will be less than or equal to 2 * 109.
 * 
 * Approach - DP
 */
public class P62UniquePaths {

	public static void main(String[] args) {
		int m = 3, n = 7;
//		int m = 3, n = 2;
//		int m = 19, n = 13;

		int pathsLoops = uniquePathsLoops(m, n);
		System.out.println("Loops: The number of unique paths are: " + pathsLoops);

		int pathsDP = uniquePathsDP(m, n);
		System.out.println("DP: The number of unique paths are: " + pathsDP);

		int pathsMemoized = uniquePathsMemoized(m, n);
		System.out.println("Memoized: The number of unique paths are: " + pathsMemoized);

		int pathsRecursive = uniquePathsRecursive(m, n);
		System.out.println("Recursive: The number of unique paths are: " + pathsRecursive);

	}

	private static int uniquePathsLoops(int m, int n) {
		int[] arr = new int[n]; // j's max length
		Arrays.fill(arr, 1);
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				arr[j] += arr[j - 1];
			}
		}
		return arr[n - 1];
	}

	// Bottom Up Approach
	private static int uniquePathsDP(int m, int n) {
		int[][] dp = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 && j == 0) {
					dp[0][0] = 1;
					continue;
				}
				int up = 0;
				if (i >= 1) {
					up = dp[i - 1][j];
				}
				int left = 0;
				if (j >= 1) {
					left = dp[i][j - 1];
				}
				dp[i][j] = up + left;
			}
		}
		return dp[m - 1][n - 1];
	}

	// Top Down Approach
	private static int uniquePathsMemoized(int m, int n) {
		int[][] dp = new int[m][n];

		// dp[0][0] has answer
		return recursiveMemoized(0, 0, m, n, dp);
	}

	private static int recursiveMemoized(int i, int j, int m, int n, int[][] dp) {
		if (i == m || j == n) {
			return 0;
		}
		if (i == m - 1 && j == n - 1) {
			return 1;
		}
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		int count = 0;
		count += recursiveMemoized(i + 1, j, m, n, dp);
		count += recursiveMemoized(i, j + 1, m, n, dp);
		dp[i][j] = count;
		return count;
	}

	// Top Down Approach
	public static int uniquePathsRecursive(int m, int n) {

		return recursive(0, 0, m, n);
	}

	private static int recursive(int i, int j, int m, int n) {
		if (i == m || j == n) {
			return 0;
		}
		int count = 0;
		if (i == m - 1 && j == n - 1) {
			count++;
		}
		count += recursive(i + 1, j, m, n);
		count += recursive(i, j + 1, m, n);
		return count;
	}

}
