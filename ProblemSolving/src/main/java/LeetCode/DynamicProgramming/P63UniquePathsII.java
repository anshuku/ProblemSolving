package LeetCode.DynamicProgramming;

/*
 * P63. Unique Paths II - Medium
 * 
 * You are given an m x n integer array grid. There is a robot initially located at the 
 * top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner 
 * (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
 * 
 * An obstacle and space are marked as 1 or 0 respectively in grid. A path 
 * that the robot takes cannot include any square that is an obstacle.
 * 
 * Return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 * 
 * The testcases are generated so that the answer will be less than or equal to 2 * 10^9.
 * 
 * Approach - DP: Matrix
 */
public class P63UniquePathsII {

	public static void main(String[] args) {

		int[][] obstacleGrid = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } };

//		int[][] obstacleGrid = { { 0, 1 }, { 0, 0 } };

//		int[][] obstacleGrid = { { 1 } };

		int uniquePaths2DDP = uniquePathsWithObstacles2DDP(obstacleGrid);
		System.out.println("2D DP: The unique paths with obstacles are: " + uniquePaths2DDP);

		int uniquePathsMemoized = uniquePathsWithObstaclesMemoized(obstacleGrid);
		System.out.println("Memoized: The unique paths with obstacles are: " + uniquePathsMemoized);
	}

	private static int uniquePathsWithObstacles2DDP(int[][] obstacleGrid) {
		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;
		int[][] dp = new int[m][n];
		dp[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if ((i == 0 && j == 0) || obstacleGrid[i][j] == 1) {
					continue;
				}
				int up = 0;
				if (i > 0) {
					up = dp[i - 1][j];
				}
				int left = 0;
				if (j > 0) {
					left = dp[i][j - 1];
				}
				dp[i][j] = up + left;
			}
		}
		return dp[m - 1][n - 1];
	}

	public static int uniquePathsWithObstaclesMemoized(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;
		int[][] dp = new int[m][n];
		return recursive(0, 0, m, n, grid, dp);
	}

	private static int recursive(int i, int j, int m, int n, int[][] grid, int[][] dp) {
		if (i == m || j == n || grid[i][j] == 1) {
			return 0;
		}
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		int count = 0;
		if (i == m - 1 && j == n - 1) {
			count++;
		}
		count += recursive(i + 1, j, m, n, grid, dp);
		count += recursive(i, j + 1, m, n, grid, dp);
		dp[i][j] = count;
		return count;
	}
}
