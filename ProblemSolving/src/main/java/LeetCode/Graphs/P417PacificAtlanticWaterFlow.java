package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * P417. Pacific Atlantic Water Flow - Medium
 * 
 * There is an m x n rectangular island that borders both the Pacific Ocean and 
 * Atlantic Ocean. The Pacific Ocean touches the island's left and top edges, 
 * and the Atlantic Ocean touches the island's right and bottom edges.
 * 
 * The island is partitioned into a grid of square cells. You are 
 * given an m x n integer matrix heights where heights[r][c] 
 * represents the height above sea level of the cell at coordinate (r, c).
 * 
 * The island receives a lot of rain, and the rain water can flow to 
 * neighboring cells directly north, south, east, and west if the neighboring 
 * cell's height is less than or equal to the current cell's height. 
 * Water can flow from any cell adjacent to an ocean into the ocean.
 * 
 * Return a 2D list of grid coordinates result where result[i] = [ri, ci] denotes 
 * that rain water can flow from cell (ri, ci) to both the Pacific and Atlantic oceans.
 * 
 * Approach - DFS: Boundaries, BFS: Multisource
 */
public class P417PacificAtlanticWaterFlow {

	private static final int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	public static void main(String[] args) {
//		int[][] heights = { { 1, 2, 2, 3, 5 }, { 3, 2, 3, 4, 4 }, { 2, 4, 5, 3, 1 }, { 6, 7, 1, 4, 5 },
//				{ 5, 1, 1, 2, 4 } };
//		int[][] heights = { { 1 } };
//		int[][] heights = { { 1, 1 }, { 1, 1 }, { 1, 1 } };
		int[][] heights = { { 10, 10, 10 }, { 10, 1, 10 }, { 10, 10, 10 } };

		List<List<Integer>> islandsDFSBoundaries = pacificAtlanticDFSBoundaries(heights);
		System.out.println(
				"DFS Boundaries: The islands connected with Atlantic and Pacific Ocean are: " + islandsDFSBoundaries);

		List<List<Integer>> islandsBFSMultiSource = pacificAtlanticBFSMultiSource(heights);
		System.out.println("BFS Multi Source: The islands connected with Atlantic and Pacific Ocean are: "
				+ islandsBFSMultiSource);

		List<List<Integer>> islandsDFS = pacificAtlanticDFS(heights);
		System.out.println("DFS: The islands connected with Atlantic and Pacific Ocean are: " + islandsDFS);

		List<List<Integer>> islandsBFS = pacificAtlanticBFS(heights);
		System.out.println("BFS: The islands connected with Atlantic and Pacific Ocean are: " + islandsBFS);

		List<List<Integer>> islandsDFSArr = pacificAtlanticDFSArr(heights);
		System.out.println("DFS Array: The islands connected with Atlantic and Pacific Ocean are: " + islandsDFSArr);

	}

	// DFS from boundaries
	// We start from boundaries for oceans, move to equal/higher heights and then
	// intersect reachable cells. When we start at the oceans' boundaries and work
	// our way to the cells, it becomes much faster. When we start from the ocean
	// and work backwards, we already know that every cell we visit must be
	// connected to the ocean. We start the traversal from every cell that is
	// immediately beside the Pacific Ocean, and figure out what cell can flow into
	// the Pacific(check for higher height instead of lower height). We do the same
	// thing with the Atlantic ocean. At the end, the cells that end up connected to
	// both oceans is the answer. We use recursion, dfs method will be called for
	// every reachable cell. Note: we could also work iteratively with DFS with help
	// of a stack.
	// Time complexity - O(m*n), where m = rows, n = columns, in worst case, where
	// every value is equal, we would visit every cell twice. This is because we
	// perform 2 traversals, and during each traversal, we visit each cell exactly
	// once via dfs function. There are m*n cells total, which gives O(2*m*n) =
	// O(m*n).
	// Space complexity - O(m*n), due to visited array and dfs function. The space
	// is occupied by dfs calls on the recursion stack.
	private static List<List<Integer>> pacificAtlanticDFSBoundaries(int[][] heights) {
		int m = heights.length;
		int n = heights[0].length;

		List<List<Integer>> result = new ArrayList<>();

		// Keeps track of cells already visited as well as contains every cell that can
		// flow into that ocean.
		boolean[][] visitedPacific = new boolean[m][n];
		boolean[][] visitedAtlantic = new boolean[m][n];

		for (int i = 0; i < m; i++) {
			if (!visitedPacific[i][0]) {
				dfsBoundaries(i, 0, m, n, heights, visitedPacific);
			}
			if (!visitedAtlantic[i][n - 1]) {
				dfsBoundaries(i, n - 1, m, n, heights, visitedAtlantic);
			}
		}
		for (int i = 0; i < n; i++) {
			if (!visitedPacific[0][i]) {
				dfsBoundaries(0, i, m, n, heights, visitedPacific);
			}
			if (!visitedAtlantic[m - 1][i]) {
				dfsBoundaries(m - 1, i, m, n, heights, visitedAtlantic);
			}
		}

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				// We want cells reachable from both the oceans which is the intersection.
				if (visitedPacific[i][j] && visitedAtlantic[i][j]) {
					List<Integer> list = new ArrayList<>();
					list.add(i);
					list.add(j);
					result.add(list);
				}
			}
		}

		return result;
	}

	private static void dfsBoundaries(int i, int j, int m, int n, int[][] heights, boolean[][] visited) {
		visited[i][j] = true;

		int height = heights[i][j];

		for (int[] dir : direction) {
			int x = i + dir[0];
			int y = j + dir[1];

			if (isValid(x, y, m, n) && !visited[x][y] && height <= heights[x][y]) {
				dfsBoundaries(x, y, m, n, heights, visited);
			}
		}

	}

	// BFS: Multi source
	// BFS makes more sense for this problem since water flows in the same manner.
	// Time complexity - O(m*n), where m = rows, n = columns, in worst case, where
	// every value is equal, we would visit every cell twice. This is because we
	// perform 2 traversals, and during each traversal, we visit each cell exactly
	// once. There are m*n cells total, which gives O(2*m*n) = O(m*n).
	// Space complexity - O(m*n), due to queues and visited arrays. Just like time
	// complexity, the amount of space we'll use scales linearly with the number of
	// cell.
	private static List<List<Integer>> pacificAtlanticBFSMultiSource(int[][] heights) {
		int m = heights.length;
		int n = heights[0].length;

		List<List<Integer>> result = new ArrayList<>();
		Queue<int[]> queuePacific = new LinkedList<>();
		boolean[][] visitedPacific = new boolean[m][n];

		Queue<int[]> queueAtlantic = new LinkedList<>();
		boolean[][] visitedAtlantic = new boolean[m][n];

		for (int i = 0; i < m; i++) {
			if (!visitedPacific[i][0]) {
				queuePacific.offer(new int[] { i, 0, heights[i][0] });
				visitedPacific[i][0] = true;
			}

			if (!visitedAtlantic[i][n - 1]) {
				queueAtlantic.offer(new int[] { i, n - 1, heights[i][n - 1] });
				visitedAtlantic[i][n - 1] = true;
			}
		}

		for (int i = 0; i < n; i++) {
			if (!visitedPacific[0][i]) {
				queuePacific.offer(new int[] { 0, i, heights[0][i] });
				visitedPacific[0][i] = true;
			}

			if (!visitedAtlantic[m - 1][i]) {
				queueAtlantic.offer(new int[] { m - 1, i, heights[m - 1][i] });
				visitedAtlantic[m - 1][i] = true;
			}
		}

		bfs(heights, m, n, queuePacific, visitedPacific);
		bfs(heights, m, n, queueAtlantic, visitedAtlantic);

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				// We want cells reachable from both the oceans.
				if (visitedPacific[i][j] && visitedAtlantic[i][j]) {
					List<Integer> list = new ArrayList<>();
					list.add(i);
					list.add(j);
					result.add(list);
				}
			}
		}

		return result;
	}

	private static void bfs(int[][] heights, int m, int n, Queue<int[]> queue, boolean[][] visited) {
		while (!queue.isEmpty()) {
			int[] cell = queue.poll();
			int height = cell[2];

			for (int[] dir : direction) {
				int x = cell[0] + dir[0];
				int y = cell[1] + dir[1];

				if (isValid(x, y, m, n) && height <= heights[x][y] && !visited[x][y]) {
					queue.offer(new int[] { x, y, heights[x][y] });
					visited[x][y] = true;
				}
			}
		}

	}

	static boolean isPacific;
	static boolean isAtlantic;

	// DFS: Global boolean variables(may support only a single thread)
	// This naive approach checks every cell and at each one, we start a traversal
	// that manages to reach both the oceans. This process is very slow as it
	// repeats a ton of computation. As we're looking for every path from cell to
	// oceans. Here when we start the traversal at a cell, whatever result we end up
	// with can be applied to only that cell.
	private static List<List<Integer>> pacificAtlanticDFS(int[][] heights) {
		int m = heights.length;
		int n = heights[0].length;

		List<List<Integer>> result = new ArrayList<>();

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				boolean[][] visited = new boolean[m][n];
				isPacific = false;
				isAtlantic = false;
				if (dfs(i, j, m, n, heights, visited)) {
					List<Integer> list = new ArrayList<>();
					list.add(i);
					list.add(j);
					result.add(list);
				}
			}
		}
		return result;
	}

	private static boolean dfs(int i, int j, int m, int n, int[][] heights, boolean[][] visited) {
		if (i == 0 || j == 0) {
			isPacific = true;
		}
		if (i == m - 1 || j == n - 1) {
			isAtlantic = true;
		}

		if (isPacific && isAtlantic) {
			return true;
		}

		visited[i][j] = true;

		int height = heights[i][j];

		for (int[] dir : direction) {
			int x = i + dir[0];
			int y = j + dir[1];

			if (isValid(x, y, m, n) && heights[x][y] <= height && !visited[x][y]) {
				dfs(x, y, m, n, heights, visited);
			}
		}
		return isPacific && isAtlantic;
	}

	// BFS
	public static List<List<Integer>> pacificAtlanticBFS(int[][] heights) {
		List<List<Integer>> result = new ArrayList<>();

		int m = heights.length;
		int n = heights[0].length;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (bfs(i, j, m, n, heights)) {
					List<Integer> list = new ArrayList<>();
					list.add(i);
					list.add(j);
					result.add(list);
				}
			}
		}

		return result;
	}

	private static boolean bfs(int i, int j, int m, int n, int[][] heights) {
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] { i, j, heights[i][j] });

		boolean[][] visited = new boolean[m][n];
		visited[i][j] = true;

		boolean isPacific = false;
		boolean isAtlantic = false;

		while (!queue.isEmpty()) {
			int[] cell = queue.poll();

			if (cell[0] == 0 || cell[1] == 0) {
				isPacific = true;
			}
			if (cell[0] == m - 1 || cell[1] == n - 1) {
				isAtlantic = true;
			}

			if (isPacific && isAtlantic) {
				return true;
			}

			int height = cell[2];

			for (int[] dir : direction) {
				int x = cell[0] + dir[0];
				int y = cell[1] + dir[1];

				if (isValid(x, y, m, n) && heights[x][y] <= height && !visited[x][y]) {
					queue.offer(new int[] { x, y, heights[x][y] });
					visited[x][y] = true;
				}
			}

		}
		return false;
	}

	// DFS: boolean array
	private static List<List<Integer>> pacificAtlanticDFSArr(int[][] heights) {
		int m = heights.length;
		int n = heights[0].length;

		List<List<Integer>> result = new ArrayList<>();

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				boolean[][] visited = new boolean[m][n];
				boolean[] isPacificAtlantic = dfsAlt(i, j, m, n, heights, visited);
				if (isPacificAtlantic[0] && isPacificAtlantic[1]) {
					List<Integer> list = new ArrayList<>();
					list.add(i);
					list.add(j);
					result.add(list);
				}
			}
		}

		return result;
	}

	// We capture the result in a boolean array and update the booleans isPacific
	// and isAtlantic
	private static boolean[] dfsAlt(int i, int j, int m, int n, int[][] heights, boolean[][] visited) {
		boolean isPacific = false;
		boolean isAtlantic = false;

		if (i == 0 || j == 0) {
			isPacific = true;
		}

		if (i == m - 1 || j == n - 1) {
			isAtlantic = true;
		}
		visited[i][j] = true;

		int height = heights[i][j];

		for (int[] dir : direction) {
			int x = i + dir[0];
			int y = j + dir[1];

			if (isValid(x, y, m, n) && heights[x][y] <= height && !visited[x][y]) {
				boolean[] isPacificAtlantic = dfsAlt(x, y, m, n, heights, visited);

				isPacific = isPacific || isPacificAtlantic[0];
				isAtlantic = isAtlantic || isPacificAtlantic[1];
			}
		}

		return new boolean[] { isPacific, isAtlantic };
	}

	private static boolean isValid(int x, int y, int m, int n) {
		return x >= 0 && x < m && y >= 0 && y < n;
	}

}
