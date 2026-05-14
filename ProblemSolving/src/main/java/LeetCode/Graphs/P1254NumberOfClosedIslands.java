package LeetCode.Graphs;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P1254. Number of Closed Islands - Medium
 * 
 * Given a 2D grid consists of 0s (land) and 1s (water).  An island 
 * is a maximal 4-directionally connected group of 0s and a closed island 
 * is an island totally (all left, top, right, bottom) surrounded by 1s.
 * 
 * Return the number of closed islands.
 * 
 * Approach - BFS, DFS
 */
public class P1254NumberOfClosedIslands {

	private static final int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	public static void main(String[] args) {
		int[][] grid = { { 1, 1, 1, 1, 1, 1, 1, 0 }, { 1, 0, 0, 0, 0, 1, 1, 0 }, { 1, 0, 1, 0, 1, 1, 1, 0 },
				{ 1, 0, 0, 0, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 0 } };
//		int[][] grid = { { 0, 0, 1, 0, 0 }, { 0, 1, 0, 1, 0 }, { 0, 1, 1, 1, 0 } };

		int closedIslandCountDFS = closedIslandDFS(grid);
		System.out.println("DFS: The number of closed islands are: " + closedIslandCountDFS);

		int closedIslandCountBFS = closedIslandBFS(grid);
		System.out.println("BFS: The number of closed islands are: " + closedIslandCountBFS);
	}

	// DFS
	private static int closedIslandDFS(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		boolean[][] visited = new boolean[m][n];

		int closedIslands = 0;

//        for (int i = 1; i < m - 1; i++) {
//            for (int j = 1; j < n - 1; j++) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 0 && !visited[i][j] && dfs(i, j, m, n, grid, visited)) {
					closedIslands++;
				}
			}
		}
		return closedIslands;
	}

	private static boolean dfs(int i, int j, int m, int n, int[][] grid, boolean[][] visited) {
		if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
			return false;
		}

		visited[i][j] = true;
		boolean isClosed = true;

		for (int[] dir : direction) {
			int x = i + dir[0];
			int y = j + dir[1];

			if (isValid(x, y, m, n) && grid[x][y] == 0 && !visited[x][y]) {
				if (!dfs(x, y, m, n, grid, visited)) {
					isClosed = false;
				}
			}
		}
		return isClosed;
	}

	// BFS
	// We travel the grid one neighbor node at a time until we can't, all the nodes
	// that are visited in this traversal form an island. While traversing the
	// island, if any node in the graph is at the grid's bounday. The island does
	// not form a closed island. A closed island is formed if there is no node in
	// the grid's boundary. We can use BFS traversal. If we came across a node on
	// the boundaty, we will complete the BFS traversal to cover the entire island
	// so that we can mark all the nodes of the island and not visit any of its
	// nodes again.
	// Time complexity - O(m*n)
	// Space complexity - O(m*n)
	public static int closedIslandBFS(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		if (m < 3 || n < 3) {
			return 0;
		}

		boolean[][] visited = new boolean[m][n];
		int count = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 0 && !visited[i][j] && bfs(i, j, m, n, grid, visited)) {
					count++;
				}
			}
		}

		return count;
	}

	private static boolean bfs(int i, int j, int m, int n, int[][] grid, boolean[][] visited) {
		Queue<int[]> queue = new LinkedList<>();

		queue.offer(new int[] { i, j });
		visited[i][j] = true;

		boolean isValidIsland = true;
		while (!queue.isEmpty()) {
			int[] cell = queue.poll();
			// Best to place here as we may start the iteration from i = 0 as well
			if (cell[0] == 0 || cell[0] == m - 1 || cell[1] == 0 || cell[1] == n - 1) {
				isValidIsland = false;
			}

			for (int[] dir : direction) {
				int x = cell[0] + dir[0];
				int y = cell[1] + dir[1];

				if (isValid(x, y, m, n) && grid[x][y] == 0 && !visited[x][y]) {
					queue.offer(new int[] { x, y });
					visited[x][y] = true;
				}
			}
		}
		return isValidIsland;
	}

	public static boolean isValid(int x, int y, int m, int n) {
		return x >= 0 && x < m && y >= 0 && y < n;
	}

}
