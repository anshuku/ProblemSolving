package LeetCode.Graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

//import org.apache.commons.lang3.tuple.Pair;

/*
 * P864. Shortest Path to Get All Keys - Hard
 * 
 * You are given an m x n grid grid where:
 * > '.' is an empty cell.
 * > '#' is a wall.
 * > '@' is the starting point.
 * > Lowercase letters represent keys.
 * > Uppercase letters represent locks.
 * 
 * You start at the starting point and one move consists of walking one space in one 
 * of the four cardinal directions. You cannot walk outside the grid, or walk into a wall.
 * 
 * If you walk over a key, you can pick it up and you cannot 
 * walk over a lock unless you have its corresponding key.
 * 
 * For some 1 <= k <= 6, there is exactly one lowercase and one uppercase letter of the 
 * first k letters of the English alphabet in the grid. This means that there is exactly 
 * one key for each lock, and one lock for each key; and also that the letters used to 
 * represent the keys and locks were chosen in the same order as the English alphabet.
 * 
 * Return the lowest number of moves to acquire all keys. If it is impossible, return -1.
 * 
 * Approach - BFS with data(key) holding state.
 */
public class P864ShortestPathGetAllKeys {

	// Custom class is faster than org.apache.commons.lang3.tuple.Pair
	static class Pair {
		int x, y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof Pair)) {
				return false;
			}
			Pair p = (Pair) o;
			return x == p.x && y == p.y;
		}

		public int hashCode() {
			return Objects.hash(x, y);
		}

	}

	private static final int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	public static void main(String[] args) {
//		String[] grid = { "@.a..", "###.#", "b.A.B" };
//		String[] grid = { "@..aA", "..B#.", "....b" };
//		String[] grid = { "@Aa" };
//		String[] grid = { "@...a", ".###A", "b.BCc" };
		String[] grid = { "@.A", "a#b", "BcC" };
//		String[] grid = { "@abcdeABCDEFf" };

		int shortestPathArray = shortestPathAllKeysArray(grid);
		System.out.println("Array: The shortest path has length: " + shortestPathArray);

		int shortestPathMapSetPair = shortestPathAllKeysMapSetPair(grid);
		System.out.println("Map Set Pairs: The shortest path has length: " + shortestPathMapSetPair);
	}

	// BFS with key holding state: visited 3d Array
	// Here, unlike traditional BFS, we can revisit same cell with different key
	// sets, which must be treated as a different state. BFS must differentiate
	// between different key-holding states. Within the same state, we cannot
	// revisit a cell. However, 2 searchings between different key-holding states
	// are entirely independent. Example - We start with no keys, and the current
	// states are denoted as(have key a, have key b) = (0,0). If we pick a key - a,
	// and our state changes to (1,0). Then, the following BFS is based on new
	// state, implying that the cells visited in the previous state(0,0) can be
	// visited again. In summary, we will perform BFS on multiple identical grids,
	// each representing a distinct key-holding state. When we pick up a new key, we
	// will switch to the grid corresponding to the new state and perform BFS on
	// that grid. We not only include (row, col, distance) for BFS, but also need to
	// include the key-holding states as an additional parameter, so the states in
	// the queue are in the format of (row, col, distance, key-holding state).
	// Here, once a key is picked in 1 path, it may not be available for all paths.
	// We need a 3d seen array, visited[x][y][keyMask], as 2D visited is
	// insufficient. We need a bitmask for key, discard mutating a global state.
	// Instead of mutable and iterable objects like lists or sets to collect keys,
	// bit mask can be used to represent all the collected keys, as it saves time
	// and space. Without state per key combination, BFS wrongly assumes paths are
	// equivalent. The problem is not, shortest path in grid, it's shortest path in
	// (x, y, keyMask) state space. It follows, if same location, different
	// inventory = different universe.
	// Time complexity - O(m*n*2^k), where m*n is the size of grid and k be the
	// number of keys. The BFS algo visits each cell in the grid once for each
	// key-holding state. Therefore, the worst-case time complexity is proportional
	// to the number of cells and the number of key-holding states. There are 2^k
	// possible key-holding states and we need to consider each one separately.
	// Space complexity - O(m*n*2^k), to keep track of the visited cells and their
	// key-holding states, we need to store them in visited of size m*n*2^k.
	public static int shortestPathAllKeysArray(String[] grid) {
		int m = grid.length;
		int n = grid[0].length();

		char[][] maze = new char[m][n];

		// To find start or where @ is.
		int startX = -1;
		int startY = -1;

		// Finds number of keys (a - f)
		int keys = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				maze[i][j] = grid[i].charAt(j);
				if (maze[i][j] == '@') {
					startX = i;
					startY = j;
					// Logic later treats start like a normal empty cell, avoids special casing '@'.
					maze[i][j] = '.';
				}
				// Count keys
				if (maze[i][j] >= 'a' && maze[i][j] <= 'f') {
					keys++;
				}
			}
		}

		// Goal mask
		// 2^0 + 2^1 + .. + 2^(n-1) = 2^n - 1
		// If keys = 3, 1 << 3 = 1000, final mask = 0111
		// It means we've all keys which can be collected.
		int finalMask = (1 << keys) - 1;

		Queue<int[]> queue = new LinkedList<>();
		// [x, y, moves, keyMask]
		// moves -> steps taken, keyMask -> which keys we have.
		queue.offer(new int[] { startX, startY, 0, 0 });

		// We use 3D: same cell + different keys = different state.
		// (2,3,001) != (2,3,101)
		boolean[][][] visited = new boolean[m][n][1 << 6];
		// At start, no keys -> mask = 0
		visited[startX][startY][0] = true;

		while (!queue.isEmpty()) {
			int[] cell = queue.poll();

			int i = cell[0];
			int j = cell[1];
			int moves = cell[2];
			int currentMask = cell[3];

			for (int[] dir : direction) {
				int x = i + dir[0];
				int y = j + dir[1];
				// newMask = updated key state
				int newMask = currentMask;

				// Skip out of bounds and walls.
				if (!isValid(x, y, m, n) || maze[x][y] == '#') {
					continue;
				}

				char c = maze[x][y];

				// If it's a key
				if (c >= 'a' && c <= 'f') {
					// If c = 'c'
					// c - 'a' = 2, 1 << 2 = 100, add key using bitmask OR.
					// We walk to the cell and pick it up.
					newMask |= 1 << (c - 'a');
				}

				// If it's a lock
				if (c >= 'A' && c <= 'F') {
					// We check if we've a key for the lock
					// Lock = 'C', (c - 'A') = 2, check: newMask & 100
					// If it's 0 -> we don't have a key -> skip
					// If it's non zero -> can open
					if ((newMask & (1 << (c - 'A'))) == 0) {
						// 0 -> we don't have a key -> skip
						continue;
					}
				}

				// If we collect all keys, return distance + 1.
				// BFS ensures, first time reaching this = shortest path
				if (newMask == finalMask) {
					return moves + 1;
				}

				// Otherwise, just add this state to visited and queue.
				// Avoid revisiting same state
				if (!visited[x][y][newMask]) {
					// If not visited, push to queue and mark visited.
					queue.offer(new int[] { x, y, moves + 1, newMask });
					visited[x][y][newMask] = true;
				}
			}
		}
		return -1;
	}

	// BFS with key holding state: visited Map of keyMask and Set<Pair<Coordinates>>
	// One can use Pair from library or a class Pair with proper equals()/hashCode()
	// In case of int[], both adding the array values and checking presence of int[]
	// has issues. In Java, arrays int[] use reference equality, not value equality.
	// So new int[]{1,2} != new int[]{1,2}. This means contains() will always return
	// false. This leads to same state being revisited. BFS explodes -> TLE or wrong
	// traversal. Pair<> fixes it, as it implements value - based equality. This
	// makes contains() to work properly. We can revisit a key cell, if I already
	// have the key, as we may visit that cell with a different path. This path
	// might lead to new areas. Picking key again is useless but stepping on that
	// cell is allowed. Better to use boolean[][][] visited = new boolean[m][n][64].
	// This is faster, no hashing bugs, no equality issues. The real state is:
	// (x,y,keyMask). Here with Map<keyMask, Set<(x,y)>>, it works only if equality
	// works perfectly. Also, Map + Set<Pair> is correct but practically slow as
	// there is a Hashing overhead. Every operation: contains(new Pair(x,y)) does:
	// > Object Creation > Hash Computation > equals comparision. Also, there is
	// Memory fragmentation: Many small HashSets which leads to poor cache locality.
	// In Worst-case explosion for ["@abcdeABCDEFf"], Linear grid, but 6 keys -> 64
	// key states. Each cell revisited with many masks. So operations become:
	// O(m*n*2^k*hash_cost). boolean[][][] array is better as we have O(1) lookup,
	// no hashing, no object creation, contiguous memory -> CPU cache friendly.
	private static int shortestPathAllKeysMapSetPair(String[] grid) {
		int m = grid.length;
		int n = grid[0].length();

		Set<Character> keySet = new HashSet<>();
		Set<Character> lockSet = new HashSet<>();

		char[][] maze = new char[m][n];

		int startX = -1;
		int startY = -1;

		int allKeys = 0;

		for (int i = 0; i < m; i++) {
			char[] row = grid[i].toCharArray();
			for (int j = 0; j < n; j++) {
				maze[i][j] = row[j];
				if (maze[i][j] == '@') {
					startX = i;
					startY = j;
//					maze[i][j] = '.'; // Not needed
				} else if (maze[i][j] >= 'a' && maze[i][j] <= 'f') {
					keySet.add(maze[i][j]);
					allKeys |= 1 << (maze[i][j] - 'a');
				} else if (maze[i][j] >= 'A' && maze[i][j] <= 'F') {
					lockSet.add(maze[i][j]);
				}
			}
		}

		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] { startX, startY, 0, 0 });

		// Map<Integer, Set<int[]>> can't be used as it does reference check instead of
		// value. visited.contains(keyMask) is only for BFS with key state equals
		// keyMask. visited[key-holding states] represents all the visited cells with
		// this key-holding state.
		Map<Integer, Set<Pair>> visited = new HashMap<>();
		visited.computeIfAbsent(0, k -> new HashSet<>()).add(new Pair(startX, startY));

		while (!queue.isEmpty()) {
			int[] cell = queue.poll();
			int i = cell[0];
			int j = cell[1];
			int moves = cell[2];
			int mask = cell[3];

			for (int[] dir : direction) {
				int x = i + dir[0];
				int y = j + dir[1];

				if (!isValid(x, y, m, n) || maze[x][y] == '#') {
					continue;
				}

				char c = maze[x][y];

				// If it's a key
				if (keySet.contains(c)) {
					// We can walk to this cell and pick it up.
					int newMask = mask | (1 << (c - 'a'));

					// If we collect all keys, return distance + 1.
					if (newMask == allKeys) {
						return moves + 1;
					}

					// Otherwise, just add this state to visited and queue.
					if (!visited.computeIfAbsent(newMask, k -> new HashSet<>()).contains(new Pair(x, y))) {
						queue.offer(new int[] { x, y, moves + 1, newMask });
						visited.computeIfAbsent(newMask, k -> new HashSet<>()).add(new Pair(x, y));
					}
					// If we got a lock and we don't have the key, continue.
				} else if (lockSet.contains(c) && ((mask & (1 << (c - 'A'))) == 0)) {
					continue;
					// We can walk to this cell as we haven't visited it with same key state.
				} else if (!visited.computeIfAbsent(mask, k -> new HashSet<>()).contains(new Pair(x, y))) {
					queue.offer(new int[] { x, y, moves + 1, mask });
					visited.computeIfAbsent(mask, k -> new HashSet<>()).add(new Pair(x, y));
				}
			}
		}
		return -1;

	}

	private static boolean isValid(int x, int y, int m, int n) {
		return x >= 0 && x < m && y >= 0 && y < n;
	}

}
