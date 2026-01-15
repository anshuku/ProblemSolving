package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * Time complexity: O(n^2*logn)
 * Space complexity: O(n^2)
 */
public class P2812FindSafestPathGrid {

	// Directions to move neighboring cells: right, left, up, down(row - i, col - j)
	int[][] dir = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };

	public static void main(String[] args) {

		List<List<Integer>> grid = new ArrayList<>();

//		grid.add(Arrays.asList(0, 0, 1));
//		grid.add(Arrays.asList(0, 0, 0));
//		grid.add(Arrays.asList(0, 0, 0));

//		grid.add(Arrays.asList(0, 0, 0));
//		grid.add(Arrays.asList(0, 0, 0));
//		grid.add(Arrays.asList(0, 0, 1));

		grid.add(Arrays.asList(0, 0, 0, 1));
		grid.add(Arrays.asList(0, 0, 0, 0));
		grid.add(Arrays.asList(0, 0, 0, 0));
		grid.add(Arrays.asList(1, 0, 0, 0));

		P2812FindSafestPathGrid pfs = new P2812FindSafestPathGrid();
		int safenessFactor = pfs.maximumSafenessFactorBinarySearch(grid);

//		int safenessFactor = pfs.maximumSafenessFactorMaxHeap(grid);

//		int safenessFactor = pfs.maximumSafenessFactorBSArrays(grid);

		System.out.println("The max safeness factor is " + safenessFactor);
	}

	// Optimized Time complexity
	int[][] visit;
	private int maximumSafenessFactorBSArrays(List<List<Integer>> grid) {
		int n = grid.size();
		if (n < 2 || grid.get(0).get(0) == 1 || grid.get(n - 1).get(n - 1) == 1) {
			return 0;
		}
		int[][] levels = new int[n][];
		visit = new int[n][];
		for (int i = 0; i < n; i++) {
			levels[i] = new int[n];
			visit[i] = new int[n];
			Arrays.fill(levels[i], n + n);
		}

		for (int[] arr : levels) {
			System.out.println("The levels matrix is " + Arrays.toString(arr));
		}

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				if (grid.get(r).get(c) == 1) {
					calculate(grid, levels, r, c);
				}
			}
		}
		int level = Math.min(levels[0][0], levels[n - 1][n - 1]);
		if (hasPath(levels, level, 0, 0)) {
			return level;
		}
		
		for (int[] arr : levels) {
			System.out.println("The levels changed is " + Arrays.toString(arr));
		}

		int minLevel = 1;
		int maxLevel = level - 1;
		int result = 0;

		while (minLevel <= maxLevel) {
			level = (minLevel + maxLevel) / 2;
			if (hasPath(levels, level, 0, 0)) {
				result = level;
				minLevel = level + 1;
			} else {
				maxLevel = level - 1;
			}
		}
		return result;
	}

	private void calculate(List<List<Integer>> grid, int[][] levels, int r, int c) {
		levels[r][c] = 0;
		var row = grid.get(r);
		int maxJ = fill(row, levels[r], 1, c + 1, levels.length);
		int minJ = fill(row, levels[r], 1, c - 1, -1);

		int i = r + 1;
		int dMaxJ = maxJ;
		int dMinJ = minJ;
		while (i < levels.length && grid.get(i).get(c) != 1) {
			row = grid.get(i);
			dMaxJ = fill(row, levels[i], i - r, c, dMaxJ);
			dMinJ = fill(row, levels[i], i - r + 1, c - 1, dMinJ);
			i++;
		}

		i = r - 1;
		int upMaxJ = maxJ;
		int upMinJ = minJ;
		while (i > -1 && grid.get(i).get(c) != 1) {
			row = grid.get(i);
			upMaxJ = fill(row, levels[i], r - i, c, upMaxJ);
			upMinJ = fill(row, levels[i], r - i + 1, c - 1, upMinJ);
			i--;
		}

	}

	private int fill(List<Integer> row, int[] levels, int level, int from, int to) {
		int change = from > to ? -1 : 1;
		int i = from;
		while (i != to && row.get(i) != 1 && levels[i] > level) {
			levels[i] = level;
			level += 1;
			i += change;
		}
		System.out.println("The levels array is " + Arrays.toString(levels));
		return i;
	}

	private boolean hasPath(int[][] levels, int level, int r, int c) {
		if (levels[r][c] < level) {
			return false;
		}
		int n = levels.length;

		if (r == n - 1 && c == n - 1) {
			return true;
		}
		visit[r][c] = level;

		return (c < n - 1 && level != visit[r][c + 1] && hasPath(levels, level, r, c + 1))
				|| (r < n - 1 && level != visit[r + 1][c] && hasPath(levels, level, r + 1, c))
				|| (c > 0 && level != visit[r][c - 1] && hasPath(levels, level, r, c - 1))
				|| (r > 0 && level != visit[r - 1][c] && hasPath(levels, level, r - 1, c));
	}

	// Faster than binary search
	private int maximumSafenessFactorMaxHeap(List<List<Integer>> grid) {
		int n = grid.size();
		if (grid.get(0).get(0) == 1 || grid.get(n - 1).get(n - 1) == 1) {
			return 0;
		}
		Queue<int[]> multiSourceQueue = new LinkedList<>();
		int[][] mat = new int[n][n];
		// Grid is converted to a 2-D array
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid.get(i).get(j) == 1) {
					multiSourceQueue.add(new int[] { i, j });
					// thieves are marked 0
					mat[i][j] = 0;
				} else {
					// non thief cells are marked -1
					mat[i][j] = -1;
				}
			}
		}

		for (int[] arr : mat) {
			System.out.println("The matrix is " + Arrays.toString(arr));
		}

		// Updating safeness factor via BFS
		while (!multiSourceQueue.isEmpty()) {
			int size = multiSourceQueue.size();
			while (size-- > 0) {
				int[] curr = multiSourceQueue.poll();
				for (int d[] : dir) {
					int di = curr[0] + d[0];
					int dj = curr[1] + d[1];
					int val = mat[curr[0]][curr[1]];
					// Check is neighboring cell is valid and unvisited
					if (isValid(di, dj, mat) && mat[di][dj] == -1) {
						// update safeness factor and push to queue.
						mat[di][dj] = val + 1;
						// add the non thief cells in queue to be iterated after 1st pass.
						multiSourceQueue.add(new int[] { di, dj });
					}
				}
			}
		}

		for (int[] arr : mat) {
			System.out.println("The new matrix is " + Arrays.toString(arr));
		}

		// Max heap for safeness factor
		// Priority queue - max heap, to prioritize cells with higher safeness factor
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[2] - a[2]);
		pq.add(new int[] { 0, 0, mat[0][0] });// x-coordinate, y-coordinate, max_safeness_till_now
		mat[0][0] = -1;// source cell is marked as visited

		// BFS to find the path with max safeness factor
		while (!pq.isEmpty()) {
//			pq.forEach(a -> System.out.print(Arrays.toString(a)));
//			System.out.println();
			int[] curr = pq.poll();
//			System.out.println("polled " + Arrays.toString(curr));
			if (curr[0] == n - 1 && curr[1] == n - 1) {
				return curr[2];
			}
			for (int[] d : dir) {
				int di = curr[0] + d[0];
				int dj = curr[1] + d[1];

				if (isValid(di, dj, mat) && mat[di][dj] != -1) {
					// Update safeness factor of path and mark the cell as visited
					pq.add(new int[] { di, dj, Math.min(curr[2], mat[di][dj]) });
					mat[di][dj] = -1;
				}
			}
		}
		return -1;
	}

	public int maximumSafenessFactorBinarySearch(List<List<Integer>> grid) {

		int n = grid.size();
		if (grid.get(0).get(0) == 1 || grid.get(n - 1).get(n - 1) == 1) {
			return 0;
		}
		Queue<int[]> multiSourceQueue = new LinkedList<>();
		int[][] mat = new int[n][n];

		// Populate a matrix from list
		// Thieves are marked with 0 and rest as -1
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid.get(i).get(j) == 1) {
					// push thief coordinates to the queue
					multiSourceQueue.add(new int[] { i, j });
					// mark thief cell with 0
					mat[i][j] = 0;
				} else {
					// mark empty cell with -1
					mat[i][j] = -1;
				}
			}
		}
		for (int[] arr : mat) {
			System.out.println("The matrix is " + Arrays.toString(arr));
		}
		// Mark the highest safeness factor
		// Calculate safeness factor for each cell using BFS
		while (!multiSourceQueue.isEmpty()) {
			int size = multiSourceQueue.size();
			while (size-- > 0) {
				int[] curr = multiSourceQueue.poll();
				// Check neighboring cells
				for (int[] d : dir) {
					int di = curr[0] + d[0];
					int dj = curr[1] + d[1];
					int val = mat[curr[0]][curr[1]];// curr[i] has coordinates and mat[][] has values
					if (isValid(di, dj, mat) && mat[di][dj] == -1) {
						mat[di][dj] = val + 1;
						multiSourceQueue.add(new int[] { di, dj });
					}
				}
			}
		}
		for (int[] arr : mat) {
			System.out.println("The new matrix is " + Arrays.toString(arr));
		}

		int res = -1;
		int start = 0;
		int end = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				end = Math.max(end, mat[i][j]);
			}
		}

		// Binary search for maximum safeness factor
		while (start <= end) {
			int mid = (start + end) / 2;
			if (isValidSafeness(mat, mid)) {
				// store valid safeness and search for larger ones
				res = mid;
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}

		return res;
	}

	private boolean isValid(int i, int j, int[][] mat) {
		int n = mat.length;
		return i >= 0 && j >= 0 && i < n && j < n;
	}

	// Check if path exists with given minimum safeness value
	private boolean isValidSafeness(int[][] mat, int minSafeness) {
		int n = mat.length;

		// Check if source and destination cells satisfy minimum safeness
		if (mat[0][0] < minSafeness || mat[n - 1][n - 1] < minSafeness) {
			return false;
		}

		boolean[][] visited = new boolean[n][n];
		Queue<int[]> traversalQueue = new LinkedList<>();
		traversalQueue.add(new int[] { 0, 0 });
		visited[0][0] = true;

		// BFS to find a valid path
		while (!traversalQueue.isEmpty()) {
			int[] curr = traversalQueue.poll();
			if (curr[0] == n - 1 && curr[1] == n - 1) {
				return true;
			}

			// check neighboring cells
			for (int[] d : dir) {

//				traversalQueue.forEach(a -> System.out.print(Arrays.toString(a)));
//				System.out.println();

				int di = curr[0] + d[0];
				int dj = curr[1] + d[1];

				// Neighboring cell is valid, unvisited and satisfies minimum safeness
				if (isValid(di, dj, mat) && !visited[di][dj] && mat[di][dj] >= minSafeness) {
					traversalQueue.add(new int[] { di, dj });
					visited[di][dj] = true;
				}
			}
		}
		return false;// No valid path found
	}

}
