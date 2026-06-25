package LeetCode.Strings;

import java.util.Arrays;

/*
 * P3963. Create Grid With Exactly One Path - Easy
 * 
 * You are given two integers m and n, representing the number of rows and columns of a grid.
 * 
 * Construct any m x n grid consisting only of the characters '.' and '#', where:
 * 
 * > '.' represents a free cell.
 * > '#' represents an obstacle cell.
 * 
 * A valid path is a sequence of free cells that:
 * > Starts at the top-left cell (0, 0).
 * > Ends at the bottom-right cell (m - 1, n - 1).
 * > Moves only:
 * > - Right, from (i, j) to (i, j + 1), or
 * > - Down, from (i, j) to (i + 1, j).
 * 
 * Return any grid such that there is exactly one valid path from the top-left cell to the bottom-right cell.
 * 
 * Approach - String
 */
public class P3963CreateGridWithExactlyOnePath {

	public static void main(String[] args) {
		int m = 2, n = 3;

		String[] grid = createGrid(m, n);

		System.out.println("The grid with exactly one valid path is: " + Arrays.toString(grid));
	}

	// The simplest route to chose is to go right when possible, then go down till
	// one reaches the destination.
	public static String[] createGrid(int m, int n) {
		String[] grid = new String[m];

		char[][] gridArr = new char[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				gridArr[i][j] = '#';
			}
		}

		for (int i = 0; i < n; i++) {
			gridArr[0][i] = '.';
		}

		for (int i = 0; i < m; i++) {
			gridArr[i][n - 1] = '.';
		}

		for (int i = 0; i < m; i++) {
			grid[i] = new String(gridArr[i]);
		}

		return grid;
	}
}
