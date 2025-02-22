package LeetCode.DynamicProgramming;

/*
 * P64. Minimum Path Sum - Medium
 * 
 * Given a m x n grid filled with non-negative numbers, find a path from top 
 * left to bottom right, which minimizes the sum of all numbers along its path.
 * 
 * Note: You can only move either down or right at any point in time.
 * 
 * Approach - DP
 */
public class P64MinimumPathSum {

	public static void main(String[] args) {

		int[][] grid = { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } };
//		int[][] grid = { { 1, 2, 3 }, { 4, 5, 6 } };

		int minSum2DDP = minPathSum2DDP(grid);
		System.out.println("2D DP: The min path sum is " + minSum2DDP);

		int minSumUpLeft = minPathSumUpLeft(grid);
		System.out.println("Up Left: The min path sum is " + minSumUpLeft);

		int minSumRecursive = minPathSumRecursive(grid);
		System.out.println("Recursion: The min path sum is " + minSumRecursive);
	}

	// Time complexity - O(m*n) for iterating through 2D grid.
	// Space complexity - O(m*n) for dp array.
	private static int minPathSum2DDP(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;
		int[][] dp = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i != 0 && j != 0) {
					dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
				} else if (i != 0 && j == 0) {
					dp[i][j] = grid[i][j] + dp[i - 1][j];
				} else if (i == 0 && j != 0) {
					dp[i][j] = grid[i][j] + dp[i][j - 1];
				} else {
					dp[i][j] = grid[i][j];
				}
			}
		}
		return dp[m - 1][n - 1];
	}

	public static int minPathSumUpLeft(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;
		int[][] dp = new int[m][n]; // can also do in place from grid
		dp[0][0] = grid[0][0];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				// for 1st row and 1st column continue is important
				if (i == 0 && j == 0) {
					continue;
				}
				int up = Integer.MAX_VALUE;
				if (i > 0) {
					up = dp[i - 1][j];
				}
				int left = Integer.MAX_VALUE;
				if (j > 0) {
					left = dp[i][j - 1];
				}
				dp[i][j] = grid[i][j] + Math.min(up, left);
			}
		}
		return dp[m - 1][n - 1];
	}

	private static int minPathSumRecursive(int[][] grid) {
		return recursive(grid, 0, 0);
	}

	private static int recursive(int[][] grid, int i, int j) {
		if (i == grid.length || j == grid[0].length) {
			return Integer.MAX_VALUE;
		}
		if (i == grid.length - 1 && j == grid[0].length - 1) {
			return grid[i][j];
		}
		return grid[i][j] + Math.min(recursive(grid, i + 1, j), recursive(grid, i, j + 1));
	}
}
