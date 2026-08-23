package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P3989. Maximum Consistent Columns in a Grid - Hard
 * 
 * You are given a 2D integer array grid of size m x n, and an integer limit.
 * 
 * You may remove zero or more columns from the grid, but at least one column 
 * must remain. The relative order of the remaining columns must be preserved.
 * 
 * A grid is called consistent if for every row i, and for every pair of adjacent remaining 
 * columns a and b with a < b, the following holds: |grid[i][b] - grid[i][a]| <= limit.
 * 
 * Return the maximum number of columns that can remain such that the resulting grid is consistent.
 * 
 * Approach - DP: LIS
 */
public class P3989MaximumConsistentColumnsInGrid {

	public static void main(String[] args) {
		int[][] grid = { { -2, 0, 3 } };
		int limit = 2;

//		int[][] grid = { { 1, -1, 1 }, { 2, 2, 2 } };
//		int limit = 1;

//		int[][] grid = { { -5, 5 } };
//		int limit = 9;

		int maxConsistentColumnsLIS = maxConsistentColumns(grid, limit);
		System.out.println("The max number of columns which remain consistent are: " + maxConsistentColumnsLIS);
	}

	// Longest Increasing Subsequence
	// We iterate through each pair of columns j and k and instead of deciding which
	// columns to remove, we can think of building the longest increasing
	// subsequence of columns from left to right. A column can be appended to a
	// previously built sequence only if every row satisfies the difference
	// constraint between the last kept column and current column.
	// We check each column k < jth column for all the rows (i) and see if
	// there is a LIS which satisfies the criteria as per question which is
	// abs(grid[i][j] - grid[i][k]) should be <= limit, if it's there for all the
	// rows, we add this kth column to LIS and update the current LIS at j with LIS
	// at k + 1 (as we added a new column). If at any ith row, the condition isn't
	// true we stop scanning further rows and break and don't include this kth
	// column to LIS. The array dp stores the maximum length of valid columns that
	// can end at column j where the column number is the state. Initally, every
	// column alone forms a valid grid so we denote dp[j] = 1.
	// We return the max value in dp.
	// Time complexity - O(n^2*m)
	// Space complexity - O(n) for dp array
	public static int maxConsistentColumns(int[][] grid, int limit) {
		int m = grid.length;
		int n = grid[0].length;

		int[] dp = new int[n];

		Arrays.fill(dp, 1);

		int maxColumns = 1;

		// Iterate through columns from left to right.
		for (int j = 1; j < n; j++) {
			// For every columns, check all previous columns
			for (int k = 0; k < j; k++) {
				boolean ok = true;
				// For every pair of columns, verify the condition across all rows.
				for (int i = 0; i < m; i++) {
					if (Math.abs(grid[i][j] - grid[i][k]) > limit) {
						ok = false;
						break;
					}
				}
				// If every row satisfies the limit, extend the previous sequence.
				// Else, ignore that transition.
				if (ok) {
					dp[j] = Math.max(dp[j], dp[k] + 1);
				}
			}
			maxColumns = Math.max(maxColumns, dp[j]);
		}
		return maxColumns;
	}

}
