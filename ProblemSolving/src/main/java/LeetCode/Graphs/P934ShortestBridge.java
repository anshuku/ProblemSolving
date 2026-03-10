package LeetCode.Graphs;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P934. Shortest Bridge - Medium
 * 
 * You are given an n x n binary matrix grid where 1 represents land and 0 represents water.
 * 
 * An island is a 4-directionally connected group of 1's not connected 
 * to any other 1's. There are exactly two islands in grid.
 * 
 * You may change 0's to 1's to connect the two islands to form one island.
 * 
 * Return the smallest number of 0's you must flip to connect the two islands.
 * 
 * Approach - BFS, DFS
 */
public class P934ShortestBridge {

	final static int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	public static void main(String[] args) {
//		int[][] grid = { { 0, 1 }, { 1, 0 } };
//		int[][] grid = { { 0, 1, 0 }, { 0, 0, 0 }, { 0, 0, 1 } };
		int[][] grid = { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1 } };
		int[][] grid2 = new int[grid.length][];
		int i = 0;
		for (int[] arr : grid) {
			grid2[i++] = arr.clone();
		}

		int shortestBridgeDfsBfs = shortestBridgeDfsBfs(grid);
		System.out.println("DFS BFS: The smallest number of 0s to flip to connect 2 islands: " + shortestBridgeDfsBfs);

		grid = grid2;
		int shortestBridgeBfs = shortestBridgeBfs(grid);
		System.out.println("BFS: The smallest number of 0s to flip to connect 2 islands: " + shortestBridgeBfs);
	}

	// BFS + DFS
	// We find all the land cells on the 1st island(A) via DFS. We start with 1 cell
	// of island A and try to move to its 4 neighboring cells. If there is an
	// unvisited neighboring land cell, we move to that cell and change its value to
	// a number like 2 to avoid revisiting it again in the future and distinguish it
	// from the land cells of the other island. We repeat the same strategy for the
	// new cell. If we find that the current cell has no univisted neighbors, we
	// will backtrack to the previous cell and try the next neighboring cell from
	// there. Once we've found all cells in island A and set them to 2, in grid
	// we've: 0 for water cells, 2 for land cells of the 1st island(A), 1 for the
	// land cells of the 2nd island(B). We use BFS to find the shortest distance
	// from island A to island B: We start with all the cells in island A as the
	// source, and set distance to 0. Add all the cells of island A to a list
	// bfsQueue. While bfsQueue is not empty, we build an empty list newBfs as the
	// candidate cells for the next BFS round, then we iterate over every cell(x, y)
	// in bfsQueue. Check the 4 neighbors of (x,y) (up, down, left, and right). If a
	// valid neighbor has value of 0, we can mark it as visited by setting the value
	// as -1. then we can add this cell to list newBfs. If a neighbor cell has a
	// value of 1, it means that we've found a land cell of the 2nd island(B). As
	// we're traversing water cells in BFS approach, it means that the 1st cell of
	// island B we found has the shortest distance from island A among all cells on
	// island B. At the end of current round iteration, if we still haven't reached
	// island B, it means we should look for cells that've a longer distance from
	// island A. We increment distance by 1, set bfsQueue = newBfs, and again repeat
	// BFS steps by checking if bfsQueue is empty.
	// Approach: The distance of each cell from island A: We start with all cells in
	// island A, that've a distance of 0. In the 1st round, we visit all water cells
	// that've a distance of 1 from island A. In 2nd round, we visit all water cells
	// that've a distance of 2 from island A and so on. After 2/3 round of BFS
	// search, we find some land cells of island B being the neighbors of water
	// cells that've a distance of 3 from island A, we can stop the BFS search. The
	// shortest distance is 3, so we need at least 3 flips to connect them.
	// Note: We're directly modifying the input to distinguish cells which is not
	// good. One can do the same functionality by using set to store cells that have
	// already been visited.
	// Algo:
	// Iterate over the grid until we find a land cell(x,y). Start from grid[x][y]
	// and use DFS to find and set the values of all cells of the same island(A) to
	// 2. Create a list bfsQueue and add all cells on island A to it, starting with
	// distance = 0. While bfsQueue is not empty, we create another list newBfs to
	// collect the water cells we need to visit in the next round. Iterate over
	// cells in bfsQueue, for each cell (x,y) in 4 directions: If grid[x][y]=1, it
	// means we've reached the 2nd island, return distance. Otherwise, look for
	// unvistied water neighbors(cells are 0), mark it -1, and add it to newBfs.
	// Once the iteration ends, set bfsQueue = newBfs, increment distance by 1,
	// start the next round.
	// Time complexity - O(n^2), the general time complexity of DFS is O(V+E), where
	// V stands for the number of vertices and E for number of edges. The max number
	// of cells in 1st island is n^2, so iterating over cells takes O(n^2). E is a
	// constant here, as we're only allowed to traverse in upto 4 directions. The
	// general time complexity of BFS is O(V+E). The max number of water cells we
	// need to check before reaching the 2nd island is n^2, which takes O(n^2) time.
	// Space complexity - O(n^2), The general space complexity of DFS is O(V). The
	// max number of cells in the 1st island is n^2, thus the space used by recusive
	// stack during DFS is O(n^2). The general space complexity of BFS is O(V). The
	// max number of water cells we check iusing BFS vefore reaching the 2nd island
	// is n^2, thus the space used by the queue is O(n^2).
	public static int shortestBridgeDfsBfs(int[][] grid) {
		int n = grid.length;

		// We find any land cell and we treat it as a cell of island A.
		int x = 0, y = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1) {
					x = i;
					y = j;
					break;
				}
			}
		}

		// Add all land cells of island A to bfsQueue.
		Queue<int[]> bfsQueue = new LinkedList<>();
		dfs(grid, x, y, bfsQueue);

		int flips = 0;

		while (!bfsQueue.isEmpty()) {
			// newBfs list collects water cells, we may need to visit in next round.
			Queue<int[]> newBfs = new LinkedList<>();
			for (int[] pair : bfsQueue) {
				for (int[] dir : direction) {
					int xDir = pair[0] + dir[0];
					int yDir = pair[1] + dir[1];

					if (isValid(xDir, yDir, n)) {
						if (grid[xDir][yDir] == 1) {
							return flips;

						}
						if (grid[xDir][yDir] == 0) {
							grid[xDir][yDir] = -1;
							newBfs.offer(new int[] { xDir, yDir });
						}
					}
				}
			}
			// Once we finish 1 round without finding land cells of island B, we will start
			// the next round on all water cells that are 1 cell away from island A and
			// increment the distance by 1.
			bfsQueue = newBfs;
			flips++;
		}

		return flips;
	}

	// Recursively check the neighboring land cell of current cell grid[x][y] and
	// add all land cells of island A to bfsQueue.
	private static void dfs(int[][] grid, int x, int y, Queue<int[]> bfsQueue) {
		grid[x][y] = 2;
		bfsQueue.add(new int[] { x, y });

		for (int[] dir : direction) {
			int xDir = x + dir[0];
			int yDir = y + dir[1];

			if (isValid(xDir, yDir, grid.length) && grid[xDir][yDir] == 1) {
				dfs(grid, xDir, yDir, bfsQueue);
			}
		}
	}

	private static boolean isValid(int x, int y, int n) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

	// BFS
	// In this approach, same strategy is used as DFS+BFS, but we'll use BFS instead
	// of DFS to search for all cells of island A. We'll 1st traverse grid, take the
	// 1st land found(assume it's grid[firstX][firstY]) and treat it as a land cell
	// of island A. We BFS over all cells of island A and set their values to 2 to
	// distinguish it from island B.
	// Algo:
	// Iterate over the grid until we find a land cell(x,y). Create: A list bfsQueue
	// and add grid[x][y] on island A to it. An empty list newBfs/queue for the next
	// round's search. Post this, iterate over bfsQueue, for each cell grid[x][y],
	// if grid[x][y] = 1: set grid[x][y] = 2. Add (x,y) to queue for next round's
	// search. Add (x,y) of all cells in A to bfsQueue for finding distance later.
	// Set distance = 0. We start BFS on water cells. While bfsQueue is not empty,
	// we create an empty list newBfs to collect the cells we need to visit in the
	// next round. Iterate over cells in bfsQueue. For each cell (x,y) in 4
	// directions: If grid[x][y]=1, it means we've reached the 2nd island, return
	// distance. Otherwise, look for unvistied water neighbors(cells are 0), mark it
	// -1, and add it to newBfs. Once the iteration ends, set bfsQueue = newBfs,
	// increment distance by 1, start the next round.
	// Time complexity - O(n^2), where n is size of input grid. The max number of
	// water cells and land cells in island A we need to check are n^2.
	// Space complexity - O(n^2), the max number of land cells of island A that we
	// need to check with BFS is n^2, thus the space used by bfsQueue is O(n^2). The
	// max number of water cells that we need to check using BFS to reach B is n^2,
	// thus the space used by bfsQueue is O(n^2).
	private static int shortestBridgeBfs(int[][] grid) {
		int n = grid.length;

		// We find any land cell and we treat it as a cell of island A.
		int x = 0, y = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1) {
					x = i;
					y = j;
					break;
				}
			}
		}

		// Add all land cells of island A to bfsQueue.
		Queue<int[]> bfsQueue = new LinkedList<>();
		bfsQueue.add(new int[] { x, y });
		grid[x][y] = 2;

		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[] { x, y });

		// BFS for all land cells of island A and add them to bfsQueue.
		// We use queue for BFS on water cells.
		while (!queue.isEmpty()) {
			int[] pair = queue.poll();

			for (int[] dir : direction) {
				int xDir = pair[0] + dir[0];
				int yDir = pair[1] + dir[1];

				if (isValid(xDir, yDir, n) && grid[xDir][yDir] == 1) {
					grid[xDir][yDir] = 2;
					queue.offer(new int[] { xDir, yDir });
					bfsQueue.offer(new int[] { xDir, yDir });
				}
			}
		}

		int flips = 0;
		while (!bfsQueue.isEmpty()) {
			Queue<int[]> newBfs = new LinkedList<>();

			for (int[] pair : bfsQueue) {
				for (int[] dir : direction) {
					int xDir = pair[0] + dir[0];
					int yDir = pair[1] + dir[1];

					if (isValid(xDir, yDir, n)) {
						if (grid[xDir][yDir] == 1) {
							return flips;
						}
						if (grid[xDir][yDir] == 0) {
							grid[xDir][yDir] = -1;
							newBfs.offer(new int[] { xDir, yDir });
						}
					}
				}
			}
			// Once we finish 1 round without finding land cells of island B, we will start
			// the next round on all water cells that are 1 cell away from island A and
			// increment the distance by 1.
			bfsQueue = newBfs;
			flips++;
		}

		return flips;
	}

}
