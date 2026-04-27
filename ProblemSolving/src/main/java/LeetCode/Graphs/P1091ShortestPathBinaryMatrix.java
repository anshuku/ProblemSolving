package LeetCode.Graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/*
 * P1091. Shortest Path in Binary Matrix - Medium
 * 
 * Given an n x n binary matrix grid, return the length of the shortest 
 * clear path in the matrix. If there is no clear path, return -1.
 * 
 * A clear path in a binary matrix is a path from the top-left cell 
 * (i.e., (0, 0)) to the bottom-right cell (i.e., (n - 1, n - 1)) such that:
 * > All the visited cells of the path are 0.
 * > All the adjacent cells of the path are 8-directionally connected 
 * (i.e., they are different and they share an edge or a corner).
 * 
 * The length of a clear path is the number of visited cells of this path.
 * 
 * Approach - BFS, A-Star
 */
public class P1091ShortestPathBinaryMatrix {

	static final int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { 1, -1 }, { -1, 1 },
			{ -1, -1 } };

	public static void main(String[] args) {
//		int[][] grid = { { 0, 1 }, { 1, 0 } };
		int[][] grid = { { 0, 0, 0 }, { 1, 1, 0 }, { 1, 1, 0 } };
//		int[][] grid = { { 0 } };
//		int[][] grid = { { 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0 }, { 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 1 },
//				{ 0, 0, 0, 1, 0 } };

		int n = grid.length;
		int[][] grid2 = new int[n][n];
		int i = 0;
		for (int[] arr : grid) {
			System.arraycopy(arr, 0, grid2[i++], 0, n);
		}

		int shortestPathOverwrite = shortestPathBinaryMatrixOverwrite(grid);
		System.out.println("Overwrite: The shortest path length in the binary matrix: " + shortestPathOverwrite);

		grid = grid2;
		grid2 = new int[n][n];
		i = 0;
		for (int[] arr : grid) {
			System.arraycopy(arr, 0, grid2[i++], 0, n);
		}
		int shortestPathVisited = shortestPathBinaryMatrixVisited(grid);
		System.out.println("Visited: The shortest path length in the binary matrix: " + shortestPathVisited);

		grid = grid2;
		grid2 = new int[n][n];
		i = 0;
		for (int[] arr : grid) {
			System.arraycopy(arr, 0, grid2[i++], 0, n);
		}
		int shortestPathSet = shortestPathBinaryMatrixSet(grid);
		System.out.println("Set: The shortest path length in the binary matrix: " + shortestPathSet);

		grid = grid2;
		int shortestPathAStar = shortestPathBinaryMatrixAStar(grid);
		System.out.println("A Star: The shortest path length in the binary matrix: " + shortestPathAStar);
	}

	private static int shortestPathBinaryMatrixSet(int[][] grid) {
		int n = grid.length;

		if (grid[0][0] != 0 && grid[n - 1][n - 1] != 0) {
			return -1;
		}

		Set<int[]> set = new HashSet<>();
		set.add(new int[] { 0, 0 });

		grid[0][0] = 1;
		int distance = 1;
		while (!set.isEmpty()) {
			Set<int[]> nextLevelSet = new HashSet<>();

			for (int[] cell : set) {

				for (int[] dir : direction) {
					int xDir = cell[0] + dir[0];
					int yDir = cell[1] + dir[1];

					if (isValid(xDir, yDir, n) && grid[xDir][yDir] == 0) {
						if (xDir == n - 1 && yDir == n - 1) {
							return distance + 1;
						}
						grid[xDir][yDir] = 1;
						nextLevelSet.add(new int[] { xDir, yDir });
					}
				}
			}
			set = nextLevelSet;
			distance++;
		}
		return -1;
	}

	private static int shortestPathBinaryMatrixOverwrite(int[][] grid) {
		int n = grid.length;

		if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) {
			return -1;
		}

		if (n == 1) {
			return 0;
		}

		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] { 0, 0 });

		grid[0][0] = 1;

		while (!queue.isEmpty()) {
			int[] cell = queue.poll();
			int distance = grid[cell[0]][cell[1]];

			for (int[] dir : direction) {
				int xDir = cell[0] + dir[0];
				int yDir = cell[1] + dir[1];

				if (isValid(xDir, yDir, n) && grid[xDir][yDir] == 0) {
					if (xDir == n - 1 && yDir == n - 1) {
						return distance + 1;
					}
					queue.offer(new int[] { xDir, yDir });
					grid[xDir][yDir] = distance + 1;
				}
			}
		}

		return -1;
	}

	public static int shortestPathBinaryMatrixVisited(int[][] grid) {
		int n = grid.length;

		if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) {
			return -1;
		}

		if (n == 1) {
			return 0;
		}

		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] { 0, 0 }); // can insert distance here as well instead of using level

		boolean[][] visited = new boolean[n][n];
		visited[0][0] = true;

		int level = 1;
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				int[] cell = queue.poll();

				// Not needed as we have a n == 1 check
//				if (cell[0] == n - 1 && cell[1] == n - 1) {
//					return level;
//				}

				for (int[] dir : direction) {
					int xDir = cell[0] + dir[0];
					int yDir = cell[1] + dir[1];

					if (isValid(xDir, yDir, n) && !visited[xDir][yDir] && grid[xDir][yDir] == 0) {
						if (xDir == n - 1 && yDir == n - 1) {
							return level + 1;
						}
						queue.offer(new int[] { xDir, yDir });
						visited[xDir][yDir] = true;
					}
				}
			}
			level++;
		}
		return 0;
	}

	private static boolean isValid(int x, int y, int n) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

	// A-Star Approach
	// Time complexity - O(nlogn), where n is number of cells. Adding and removing
	// items from a priority queue is O(logn), as opposed to O(1) in BFS. Given that
	// we add/remove upto O(N) items, time taken is O(nlogn).
	// Space complexity - O(n)
	// To optimze the time further to O(N) - It can be found that there will be at
	// most 3 unique estimates on the priority queue at 1 time, so we can maintain 3
	// lists instead of a priority queue. Adding or removing from lists is O(1).
	private static int shortestPathBinaryMatrixAStar(int[][] grid) {
		int n = grid.length;

		if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) {
			return -1;
		}
		if (n == 1) {
			return 1;
		}

		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[3] - b[3]);

		pq.offer(new int[] { 0, 0, 1, heuristic(0, 0, n) });

		boolean[][] visited = new boolean[n][n];

		while (!pq.isEmpty()) {
			int[] cell = pq.poll();
			int x = cell[0];
			int y = cell[1];

			int distance = cell[2];
			if (x == n - 1 && y == n - 1) {
				return distance;
			}

			if (visited[x][y]) {
				continue;
			}

			// We don't add neighbor to visited. As it'd assume that it's the best way but
			// it isn't necessarily true.
			visited[x][y] = true;

			for (int[] dir : direction) {
				int dx = x + dir[0];
				int dy = y + dir[1];

				if (isValid(dx, dy, n) && !visited[dx][dy] && grid[dx][dy] == 0) {
					pq.offer(new int[] { dx, dy, distance + 1, distance + heuristic(dx, dy, n) + 1 });
				}
			}
		}

		return -1;
	}

	// Best-case estimate to the end is simply max of rows and columns left to
	// traverse.
	private static int heuristic(int x, int y, int n) {
		return Math.max(Math.abs(n - 1 - x), Math.abs(n - 1 - y));
	}
}
