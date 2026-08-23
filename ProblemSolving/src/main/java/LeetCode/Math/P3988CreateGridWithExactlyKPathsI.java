package LeetCode.Math;

import java.util.Arrays;

/*
 * P3988. Create Grid With Exactly K Paths I - Medium
 * 
 * You are given three integers m, n, and k.
 * 
 * Construct any m x n grid consisting only of the characters '.' and '#', where:
 * > '.' represents a free cell.
 * > '#' represents an obstacle cell.
 * 
 * A valid path is a sequence of free cells that:
 * > Starts at the top-left cell (0, 0).
 * > Ends at the bottom-right cell (m - 1, n - 1).
 * > Moves only:
 * > * Right, from (i, j) to (i, j + 1), or
 * > * Down, from (i, j) to (i + 1, j).
 * 
 * Return any grid such that there are exactly k valid paths from the top-left 
 * cell to the bottom-right cell. If no such grid exists, return an empty array.
 * 
 * Constraints:
 * > 1 <= m, n <= 10
 * > 1 <= k <= 4
 * 
 * Approach - Math, Combinatorics, Pattern
 */
public class P3988CreateGridWithExactlyKPathsI {

	public static void main(String[] args) {
//		int m = 2, n = 3, k = 2;
//		int m = 3, n = 3, k = 4;
//		int m = 1, n = 4, k = 2;
		int m = 5, n = 4, k = 3;

		String[] gridRowCol = createGridRowCol(m, n, k);
		System.out.println("Row Col: The grid is: " + Arrays.toString(gridRowCol));

		String[] gridPattern = createGridPattern(m, n, k);
		System.out.println("Pattern: The grid is: " + Arrays.toString(gridPattern));
	}

	// Rows and Columns
	// The constraints are very small: 1 <= m, n <= 10 | 1 <= k <= 4. So we only
	// need to construct any 1 valid grid.
	// We create a grid with all cells blocked then open only 1 path. For this we
	// open the entire 1st row and open the entire last column, Then open 1 cell and
	// so on based on need (value of k). Since k <= 4, we only need to create at
	// most 3 extra paths. So, opening at most 3 cells is enough. If we still need
	// more paths, return an empty grid. Each newly opened cell creates 1 more valid
	// route.
	// if n > m (more wider) we open the cells in 2nd row from col = n - 2 from
	// right toward left.
	// else we open the cells in 2nd last column from row = 1 from top to bottom.
	// Special case:
	// 1) m = 3, n = 3, k = 3. The general construction cannot create
	// exactly 4 paths. We create a grid with top right and bottom left cell as
	// blocked and remaining as open.
	// 2) If the greed has only 1 row or 1 column, there is only 1 possible path. So
	// if k > 1, the grid can't be formed and we return an empty array.
	// Time complexity - O(m*n)
	// Space compexity - O(m*n)
	private static String[] createGridRowCol(int m, int n, int k) {
		if (m == 1 || n == 1) {
			if (k != 1) {
				return new String[0];
			}
		}
		long ways = getWays(m - 1, n + m - 2);

		if (ways < k) {
			return new String[] {};
		}

		if (m == 3 && n == 3 && k == 4) {
			return new String[] { "..#", "...", "#.." };
		}

		char[][] grid = new char[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = '#';
			}
		}

		// Open 1st row
		for (int j = 0; j < n; j++) {
			grid[0][j] = '.';
		}

		// Open last column
		for (int i = 1; i < m; i++) {
			grid[i][n - 1] = '.';
		}

		k--;

		if (n > m) {
			int c = n - 2;

			while (c >= 0 && k > 0) {
				grid[1][c] = '.'; // create 1 extra path
				c--;
				k--;
			}
		} else {
			int r = 1;

			while (r < m && k > 0) {
				grid[r][n - 2] = '.'; // create 1 extra path
				r++;
				k--;
			}
		}

		if (k > 0) {
			return new String[0];
		}

		String[] result = new String[m];

		for (int i = 0; i < m; i++) {
			result[i] = new String(grid[i]);
		}
		return result;
	}

	// Pattern
	// There are certain patterns while creating the grid with k paths:
	// * If m == 1 or n == 1, the only possible number of valid path = 1
	// * For m > 1 and n > 1, we try to construct a small grid pattern with exactly
	// k paths for each k from 1 to 4.
	// * For k = 2, we can use 2*2 open block. It has exactly 2 paths: right then
	// down, or down then right.
	// * For k = 3, use a 2*3 open block, or a 3*2 open block. An empty 2*3 or 3*2
	// grid has exactly 3 paths.
	// * For k = 4, use either a 2*4 or 4*2 open block, or a 3*3 block with the
	// top-right and bottom-left cells blocked.
	// After building the small block connect it's bottom-right cell to (m-1, n-1)
	// cell using a single corridor, and fill all other cells with obstacles. If the
	// required block cannot fit in either orientation, return ane empty array.
	// Time complexity - O(m*n)
	// Space compexity - O(m*n)
	public static String[] createGridPattern(int m, int n, int k) {
		if (m == 1 || n == 1) {
			if (k != 1) {
				return new String[] {};
			}
		}

		long ways = getWays(m - 1, m + n - 2);

		if (ways < k) {
			return new String[] {};
		}

		char[][] grid = new char[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = '#';
			}
		}

		if (k == 1) {
			for (int i = 0; i < n; i++) {
				grid[0][i] = '.';
			}
			for (int i = 1; i < m; i++) {
				grid[i][n - 1] = '.';
			}
		} else {
			grid[0][0] = grid[0][1] = grid[1][0] = grid[1][1] = '.';
		}

		if (k == 2) {
			for (int i = 2; i < n; i++) {
				grid[1][i] = '.';
			}
			for (int i = 2; i < m; i++) {
				grid[i][n - 1] = '.';
			}
		}

		if (k == 3) {
			if (m < 3) {
				grid[0][2] = grid[1][2] = '.';
				for (int i = 3; i < n; i++) {
					grid[1][i] = '.';
				}
				for (int i = 2; i < m; i++) {
					grid[i][n - 1] = '.';
				}
			} else {
				grid[2][0] = grid[2][1] = '.';
				for (int i = 2; i < n; i++) {
					grid[2][i] = '.';
				}
				for (int i = 3; i < m; i++) {
					grid[i][n - 1] = '.';
				}
			}
		}

		if (k == 4) {
			if (m < 3) {
				grid[0][2] = grid[1][2] = grid[0][3] = grid[1][3] = '.';
				for (int i = 4; i < n; i++) {
					grid[1][i] = '.';
				}
				for (int i = 2; i < m; i++) {
					grid[i][n - 1] = '.';
				}
			} else if (n < 3) {
				grid[2][0] = grid[2][1] = grid[3][0] = grid[3][1] = '.';
				for (int i = 2; i < n; i++) {
					grid[3][i] = '.';
				}
				for (int i = 4; i < m; i++) {
					grid[i][n - 1] = '.';
				}
			} else {
				grid[2][1] = grid[2][2] = grid[1][2] = '.';
				for (int i = 3; i < n; i++) {
					grid[2][i] = '.';
				}
				for (int i = 3; i < m; i++) {
					grid[i][n - 1] = '.';
				}
			}
		}

		String[] result = new String[m];

		for (int i = 0; i < m; i++) {
			result[i] = new String(grid[i]);
		}

		return result;
	}

	private static long getWays(int m, int n) {
		m = Math.min(m, n - m);
		long ways = 1;

		for (int i = 1; i <= m; i++) {
			ways = ways * (n - i + 1) / i;
		}
		return ways;
	}

}
