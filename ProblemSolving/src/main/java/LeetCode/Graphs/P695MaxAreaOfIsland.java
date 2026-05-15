package LeetCode.Graphs;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P695. Max Area of Island - Medium
 * 
 * You are given an m x n binary matrix grid. An island is a group of 1's 
 * (representing land) connected 4-directionally (horizontal or vertical.) 
 * You may assume all four edges of the grid are surrounded by water.
 * 
 * The area of an island is the number of cells with a value 1 in the island.
 * 
 * Return the maximum area of an island in grid. If there is no island, return 0.
 * 
 * Approach - DFS, BFS
 */
public class P695MaxAreaOfIsland {

	final static int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	public static void main(String[] args) {
		int[][] grid = { { 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
				{ 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0 },
				{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 } };
//		int[][] grid = { { 0, 0, 0, 0, 0, 0, 0, 0 } };

		int maxAreaDFS = maxAreaOfIslandDFS(grid);
		System.out.println("DFS: The max area of the island is: " + maxAreaDFS);

		int maxAreaBFS = maxAreaOfIslandBFS(grid);
		System.out.println("BFS: The max area of the island is: " + maxAreaBFS);
	}

	// DFS
	// We want to know the area of each connected shape in the grid, then take max
	// of these. If we're on a land square, we explore every square connected to it
	// 4-directionally and recursively squares connected to those squares, and so
	// on. The total number of squares exploted will be the area of that connected
	// shape.
	// Time complexity - O(m*n)
	// Space complexity - O(m*n)
	private static int maxAreaOfIslandDFS(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		boolean[][] visited = new boolean[m][n];

		int maxArea = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1 && !visited[i][j]) {
					int area = dfs(i, j, m, n, grid, visited);
					maxArea = Math.max(maxArea, area);
				}
			}
		}
		return maxArea;
	}

	// DFS returns the size of that connected component including the current node.
	private static int dfs(int i, int j, int m, int n, int[][] grid, boolean[][] visited) {
		int count = 1; // counts the current cell.
		visited[i][j] = true;

		for (int[] dir : direction) {
			int x = i + dir[0];
			int y = j + dir[1];

			if (isValid(x, y, m, n) && grid[x][y] == 1 && !visited[x][y]) {
				// Every recursive dfs() call returns the full area of that neighboring part so
				// we only need to add the returned value.
				count += dfs(x, y, m, n, grid, visited);
			}
		}
		return count;
	}

	// BFS
	public static int maxAreaOfIslandBFS(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		boolean[][] visited = new boolean[m][n];

		int maxArea = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1 && !visited[i][j]) {
					int area = bfs(i, j, m, n, grid, visited);

					maxArea = Math.max(maxArea, area);
				}
			}
		}
		return maxArea;
	}

	private static int bfs(int i, int j, int m, int n, int[][] grid, boolean[][] visited) {
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] { i, j });

		visited[i][j] = true;

		int count = 1;

		while (!queue.isEmpty()) {
			int[] cell = queue.poll();

			for (int[] dir : direction) {
				int x = cell[0] + dir[0];
				int y = cell[1] + dir[1];

				if (isValid(x, y, m, n) && grid[x][y] == 1 && !visited[x][y]) {
					queue.offer(new int[] { x, y });
					visited[x][y] = true;
					count++;
				}
			}
		}
		return count;
	}

	private static boolean isValid(int x, int y, int m, int n) {
		return x >= 0 && x < m && y >= 0 && y < n;
	}

}
