package LeetCode.Graphs;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P1020. Number of Enclaves - Medium
 * 
 * You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.
 * 
 * A move consists of walking from one land cell to another adjacent 
 * (4-directionally) land cell or walking off the boundary of the grid.
 * 
 * Return the number of land cells in grid for which we cannot 
 * walk off the boundary of the grid in any number of moves.
 * 
 * Approach - DFS, BFS: Multisource
 * 
 * Documentation pending
 */
public class P1020NumberOfEnclaves {

	private static final int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	public static void main(String[] args) {
		int[][] grid = { { 0, 0, 0, 0 }, { 1, 0, 1, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } };
//		int[][] grid = { { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } };
//		int[][] grid = { { 0, 1, 1, 0, 0 } };
//		int[][] grid = { { 0, 0, 0, 1, 1, 1, 0, 1, 0, 0 }, { 1, 1, 0, 0, 0, 1, 0, 1, 1, 1 },
//				{ 0, 0, 0, 1, 1, 1, 0, 1, 0, 0 }, { 0, 1, 1, 0, 0, 0, 1, 0, 1, 0 }, { 0, 1, 1, 1, 1, 1, 0, 0, 1, 0 },
//				{ 0, 0, 1, 0, 1, 1, 1, 1, 0, 1 }, { 0, 1, 1, 0, 0, 0, 1, 1, 1, 1 }, { 0, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
//				{ 1, 0, 1, 0, 1, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0, 0, 1 } };
//		int[][] grid = { { 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1 }, { 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0 },
//				{ 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0 }, { 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1 },
//				{ 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0 }, { 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1 },
//				{ 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0 }, { 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0 },
//				{ 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0 }, { 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1 } };

		int numEnclavesBFS = numEnclavesBFS(grid);
		System.out.println("BFS: The number of enclaves is: " + numEnclavesBFS);

		int numEnclavesDFS = numEnclavesDFS(grid);
		System.out.println("DFS: The number of enclaves is: " + numEnclavesDFS);

		int numEnclavesMultiBFS = numEnclavesMultiSourceBFS(grid);
		System.out.println("Multi Source BFS: The number of enclaves is: " + numEnclavesMultiBFS);

	}

	// BFS
	// Time complexity - O(m*n), where m and n are the number or rows and columns in
	// the given grid. Initializing visited array takes O(m*n) time. Iterating over
	// the boundary and finding unvisited land cells to perform BFS traversal takes
	// O(2*(m + n)) time. Each queue operation in the BFS algo, takes O(1) time and
	// a single node can be pushed at most once in the queue. We also pop the node
	// and iterate 4 times to iterate over all the neighbors of each node that is
	// popped out of the queue which takes O(4*m*n) time. Counting the number of
	// unvisited land cells also takes O(m*n) time.
	// Space complexity - O(m*n), the visited array takes O(m*n) space. The
	// BFS queue used by bfs method can have no more than O(m*n) elements in
	// worst-case when each node is added to it.
	private static int numEnclavesBFS(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		boolean[][] visited = new boolean[m][n];

		for (int i = 0; i < m; i++) {
			// First column
			if (grid[i][0] == 1 && !visited[i][0]) {
				bfs(i, 0, m, n, grid, visited);
			}
			// Last column
			if (grid[i][n - 1] == 1 && !visited[i][n - 1]) {
				bfs(i, n - 1, m, n, grid, visited);
			}
		}

		for (int i = 0; i < n; i++) {
			// First row
			if (grid[0][i] == 1 && !visited[0][i]) {
				bfs(0, i, m, n, grid, visited);
			}
			// Last row
			if (grid[m - 1][i] == 1 && !visited[m - 1][i]) {
				bfs(m - 1, i, m, n, grid, visited);
			}
		}

		int lands = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1 && !visited[i][j]) {
					lands++;
				}
			}
		}
		return lands;
	}

	private static void bfs(int i, int j, int m, int n, int[][] grid, boolean[][] visited) {
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] { i, j });

		visited[i][j] = true;

		while (!queue.isEmpty()) {
			int[] cell = queue.poll();

			for (int[] dir : direction) {
				int x = cell[0] + dir[0];
				int y = cell[1] + dir[1];

				if (isValid(x, y, m, n) && grid[x][y] == 1 && !visited[x][y]) {
					queue.offer(new int[] { x, y });
					visited[x][y] = true;
				}
			}
		}
	}

	// DFS
	// Time complexity - O(m*n), where m and n are the number or rows and columns in
	// the given grid. Initializing visited array takes O(m*n) time. Iterating over
	// the boundary and finding unvisited land cells to perform DFS traversal takes
	// O(2*(m + n)) time. DFS function visits each node at most once for O(m*n)
	// nodes, at worst we will perform O(4*m*n) operations as we iterate over the
	// neighbors. Counting the number of unvisited land cells also takes O(m*n)
	// time.
	// Space complexity - O(m*n), the visited array takes O(m*n) space. The
	// recursion stack used by dfs method can have no more than O(m*n) elements in
	// worst-case when each node is added to it.
	public static int numEnclavesDFS(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		boolean[][] visited = new boolean[m][n];

		for (int i = 0; i < m; i++) {
			// First column
			if (grid[i][0] == 1) { // can use if (grid[i][0] == 1 && !visited[i][0]) but redundant.
				dfs(i, 0, m, n, grid, visited);
			}

			// Last column
			if (grid[i][n - 1] == 1) {
				dfs(i, n - 1, m, n, grid, visited);
			}
		}

		for (int i = 0; i < n; i++) {
			// First row
			if (grid[0][i] == 1) {
				dfs(0, i, m, n, grid, visited);
			}

			// Last row
			if (grid[m - 1][i] == 1) {
				dfs(m - 1, i, m, n, grid, visited);
			}
		}
		int lands = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1 && !visited[i][j]) {
					lands++;
				}
			}
		}

		return lands;
	}

	private static void dfs(int i, int j, int m, int n, int[][] grid, boolean[][] visited) {
		if (!isValid(i, j, m, n) || visited[i][j]) {
			return;
		}
		visited[i][j] = true; // grid[i][j] = 0 can remove use of visited

		for (int[] dir : direction) {
			int x = i + dir[0];
			int y = j + dir[1];

			if (isValid(x, y, m, n) && grid[x][y] == 1 && !visited[x][y]) {
				dfs(x, y, m, n, grid, visited);
			}
		}

	}

	private static int numEnclavesMultiSourceBFS(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		boolean[][] visited = new boolean[m][n];
		Queue<int[]> queue = new LinkedList<>();

		for (int i = 0; i < m; i++) {
			if (grid[i][0] == 1 && !visited[i][0]) {
				queue.offer(new int[] { i, 0 });
				visited[i][0] = true;
			}
			if (grid[i][n - 1] == 1 && !visited[i][n - 1]) {
				queue.offer(new int[] { i, n - 1 });
				visited[i][n - 1] = true;
			}
		}
		for (int i = 0; i < n; i++) {
			if (grid[0][i] == 1 && !visited[0][i]) {
				queue.offer(new int[] { 0, i });
				visited[0][i] = true;
			}
			if (grid[m - 1][i] == 1 && !visited[m - 1][i]) {
				queue.offer(new int[] { m - 1, i });
				visited[m - 1][i] = true;
			}
		}
		multiSourceBfs(m, n, grid, queue, visited);

		int lands = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1 && !visited[i][j]) {
					lands++;
				}
			}
		}
		return lands;
	}

	private static void multiSourceBfs(int m, int n, int[][] grid, Queue<int[]> queue, boolean[][] visited) {
		while (!queue.isEmpty()) {
			int[] cell = queue.poll();

			for (int[] dir : direction) {
				int x = cell[0] + dir[0];
				int y = cell[1] + dir[1];

				if (isValid(x, y, m, n) && grid[x][y] == 1 && !visited[x][y]) {
					queue.offer(new int[] { x, y });
					visited[x][y] = true;
				}
			}
		}
	}

	private static boolean isValid(int x, int y, int m, int n) {
		return x >= 0 && x < m && y >= 0 && y < n;
	}

}
