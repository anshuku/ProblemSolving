package LeetCode.Graphs;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P200. Number of Islands - Medium
 * 
 * Given an m x n 2D binary grid grid which represents a map of 
 * '1's (land) and '0's (water), return the number of islands.
 * 
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally 
 * or vertically. You may assume all four edges of the grid are all surrounded by water.
 * 
 * Approach - BFS, DFS, Disjoint Set
 * 
 * This is similar to Leetcode 547 Number of Provinces: P547NumberOfProvinces
 */
public class P200NumberOfIslands {

	public static final int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	static class DisjointSet {

		int[] parent;
		int[] rank;
		int count;

		DisjointSet(char[][] grid) {
			int m = grid.length;
			int n = grid[0].length;

			parent = new int[m * n];
			rank = new int[m * n];

			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					int index = i * n + j;
					if (grid[i][j] == '1') {
						parent[index] = index;
						count++;// Each land is initially an island
					} else {
						parent[index] = -1;
					}
				}
			}
		}

		// Path compression - Gives the root of this group.
		// parent[i] becomes the root after path compression.
		// We return parent[i]/root only and not i. As even after recursion resaches the
		// root, i is not updated and returns its original input value. Java will think
		// they're in different sets, even though they are connected.
		public int find(int i) {
			if (parent[i] != i) {
				parent[i] = find(parent[i]); // Path compression and ensures parent[i] becomes root.
			}
			return parent[i]; // Return parent[i]/root of set and not i.
		}

		// Union with rank for adjacent land cells reducing count.
		public void union(int i, int j) {
			int p1 = find(i);
			int p2 = find(j);

			if (p1 != p2) {
				if (rank[p1] > rank[p2]) {
					parent[p2] = p1;
				} else if (rank[p1] < rank[p2]) {
					parent[p1] = p2;
				} else {
					parent[p2] = p1;
					rank[p1]++;
				}
				count--; // merging reduces island count
			}
		}
	}

	public static void main(String[] args) {
//		char[][] grid = { { '1', '1', '1', '1', '0' }, { '1', '1', '0', '1', '0' }, { '1', '1', '0', '0', '0' },
//				{ '0', '0', '0', '0', '0' } };
		char[][] grid = { { '1', '1', '0', '0', '0' }, { '1', '1', '0', '0', '0' }, { '0', '0', '1', '0', '0' },
				{ '0', '0', '0', '1', '1' } };

		int numIslandsDfs = numIslandsDfs(grid);
		System.out.println("DFS: The number of islands is: " + numIslandsDfs);

		int numIslandsBfs = numIslandsBfs(grid);
		System.out.println("BFS: The number of islands is: " + numIslandsBfs);

		int numIslandsDisjointSet = numIslandsDisjointSet(grid);
		System.out.println("Disjoint Set: The number of islands is: " + numIslandsDisjointSet);

	}

	// DFS
	// WE treat the 2d grid map as an unidrected graph and there is an edge between
	// 2 horizontally and vertically adjacent nodes of value '1'.
	// Time complexity - O(m*n)
	// Space complexity - O(m*n) as dfs can go m*n deep when entire grid as '1' in
	// worst case.
	private static int numIslandsDfs(char[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		int count = 0;
		boolean[][] visited = new boolean[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == '1' && !visited[i][j]) {
					count++;
					dfs(grid, i, j, visited);
				}
			}
		}
		return count;
	}

	private static void dfs(char[][] grid, int i, int j, boolean[][] visited) {
		int m = grid.length;
		int n = grid[0].length;

		visited[i][j] = true;

		for (int[] dir : direction) {
			int x = i + dir[0];
			int y = j + dir[1];

			if (isValid(x, y, m, n) && !visited[x][y] && grid[x][y] == '1') {
				dfs(grid, x, y, visited);
			}
		}

	}

	// BFS
	// Time complexity - O(m*n)
	// Space complexity - O(min(m,n)), in worst case where grid is filled with lands
	// the size of queue can grow upto min(m,n).
	public static int numIslandsBfs(char[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		Queue<int[]> queue = new LinkedList<>();
		boolean[][] visited = new boolean[m][n];

		int count = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == '1' && !visited[i][j]) {
					count++;
					queue.offer(new int[] { i, j });
					visited[i][j] = true;

					while (!queue.isEmpty()) {
						int[] cell = queue.poll();

						for (int[] dir : direction) {
							int x = cell[0] + dir[0];
							int y = cell[1] + dir[1];

							if (isValid(x, y, m, n) && !visited[x][y] && grid[x][y] == '1') {
								queue.offer(new int[] { x, y });
								visited[x][y] = true;
							}
						}
					}

				}
			}
		}

		return count;
	}

	// Union Find (aka Disjoint Set)
	// We traverse the 2d grid and union adjacent lands horizontally or vertically.
	// At the end, return the number of connected components maintained in the
	// UnionFind data structure.
	// We can optimize it further if we check only right and down for adjacent land
	// cells as it avoid duplicate unions.
	// Time complexity - O(m*n*alpha(n), here union operation takes essentially
	// constant time alpha(n) when UnionFind is implemented with both path
	// compression and union by rank.
	// Space complexity - O(m*n) for UnionFind data structure.
	private static int numIslandsDisjointSet(char[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		DisjointSet union = new DisjointSet(grid);

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == '1') {

					// Can optimize further if we check only right and down for adjacent land cells.
					for (int[] dir : direction) {
						int x = i + dir[0];
						int y = j + dir[1];

						if (isValid(x, y, m, n) && grid[x][y] == '1') {
							int id1 = i * n + j;
							int id2 = x * n + y;
							union.union(id1, id2);
						}
					}
				}
			}
		}
		// return the number of connected components maintained in the UnionFind data
		// structure.
		return union.count;
	}

	private static boolean isValid(int x, int y, int m, int n) {
		return x >= 0 && x < m && y >= 0 && y < n;
	}

}
