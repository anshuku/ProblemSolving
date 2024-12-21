package LeetCode.Graphs;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P994. Rotting Oranges
 * 
 * You are given an m x n grid where each cell can have one of three values:
 * 
 * 0 representing an empty cell,
 * 1 representing a fresh orange, or
 * 2 representing a rotten orange.
 * 
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
 * 
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange. 
 * If this is impossible, return -1.
 * 
 * Approach - Multisource BFS
 */
public class P994RottingOranges {

	public final static int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	public static void main(String[] args) {

		int[][] grid = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };

//		int[][] grid = { { 2, 1, 1 }, { 1, 1, 0 }, { 1, 0, 1 } };

//		int[][] grid = { { 2, 1 } };

//		int[][] grid = { { 2 } };

//		int[][] grid = { { 2, 2 } };

//		int[][] grid = { { 2, 0 } };
		
//		int[][] grid = { { 0 } };

//		int[][] grid = { { 0, 1 } };

//		int[][] grid = { { 0, 0 } };

		int minutes = orangesRotting(grid);

		System.out.println("The time taken for all oranges to rot: " + minutes);

	}

	public static int orangesRotting(int[][] grid) {

		int minutes = -1;

		Queue<int[]> multiSourceQueue = new LinkedList<int[]>();
		int m = grid.length;
		int n = grid[0].length;

		int orangeCount = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 2) {
					multiSourceQueue.offer(new int[] { i, j });
				} else if (grid[i][j] == 1) {
					orangeCount++;
				}
			}
		}
		if (orangeCount == 0) {
			return 0;
		}

		while (!multiSourceQueue.isEmpty()) {
			int size = multiSourceQueue.size();
			minutes++;
			while (size-- > 0) {
				int[] orange = multiSourceQueue.poll();
				for (int[] dir : direction) {
					int xDir = orange[0] + dir[0];
					int yDir = orange[1] + dir[1];
					if (isValid(xDir, yDir, m, n) && grid[xDir][yDir] == 1) {
						grid[xDir][yDir] = 2;
						multiSourceQueue.offer(new int[] { xDir, yDir });
						orangeCount--;
					}
				}
			}
		}
		if (orangeCount > 0) {
			return -1;
		}
		return minutes;
	}

	private static boolean isValid(int x, int y, int m, int n) {
		return x >= 0 && x < m && y >= 0 && y < n;
	}

}
