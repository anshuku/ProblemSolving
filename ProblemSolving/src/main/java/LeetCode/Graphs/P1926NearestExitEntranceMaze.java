package LeetCode.Graphs;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P1926. Nearest Exit from Entrance in Maze - Medium
 * 
 * You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls 
 * (represented as '+'). You are also given the entrance of the maze, where entrance = 
 * [entrancerow, entrancecol] denotes the row and column of the cell you are initially standing at.
 * 
 * In one step, you can move one cell up, down, left, or right. You cannot step into a cell 
 * with a wall, and you cannot step outside the maze. Your goal is to find the nearest exit 
 * from the entrance. An exit is defined as an empty cell that is at the border of the maze. 
 * The entrance does not count as an exit.
 * 
 * Return the number of steps in the shortest path from the entrance 
 * to the nearest exit, or -1 if no such path exists.
 * 
 * Approach - BFS with queue, array with m*n*2 size
 */
public class P1926NearestExitEntranceMaze {

	static int[][] direction = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

	public static void main(String[] args) {
		char[][] maze = { { '+', '+', '.', '+' }, { '.', '.', '.', '+' }, { '+', '+', '+', '.' } };
		char[][] maze1 = new char[maze.length][];
		int i = 0;
		for (char[] arr : maze) {
			maze1[i++] = arr.clone();
		}
		int[] entrance = { 1, 2 };

//		char[][] maze = { { '+', '+', '+' }, { '.', '.', '.' }, { '+', '+', '+' } };
//		char[][] maze1 = new char[maze.length][];
//		int i = 0;
//		for (char[] arr : maze) {
//			maze1[i++] = arr.clone();
//		}
//		int[] entrance = { 1, 0 };

//		char[][] maze = { { '.', '+' } };
//		char[][] maze1 = new char[maze.length][];
//		int i = 0;
//		for (char[] arr : maze) {
//			maze1[i++] = arr.clone();
//		}
//		int[] entrance = { 0, 0 };

		int stepsWhileSelfArr = nearestExitWhileSelfArr(maze, entrance);

		System.out.println("While Self Arr: The nearest exist from entrance is maze has steps: " + stepsWhileSelfArr);

		maze = maze1;
		int stepsWhileSelf = nearestExitWhileSelf(maze, entrance);

		System.out.println("While Self: The nearest exist from entrance is maze has steps: " + stepsWhileSelf);

		maze = maze1;
		int stepsArrSelf = nearestExitArrSelf(maze, entrance);

		System.out.println("Array Self: The nearest exist from entrance is maze has steps: " + stepsArrSelf);

		maze = maze1;
		int stepsArr = nearestExitArr(maze, entrance);

		System.out.println("Array: The nearest exist from entrance is maze has steps: " + stepsArr);

		int stepsFor = nearestExitFor(maze, entrance);

		System.out.println("For: The nearest exist from entrance is maze has steps: " + stepsFor);
	}

	private static int nearestExitWhileSelfArr(char[][] maze, int[] entrance) {
		int steps = 0;
		int m = maze.length;
		int n = maze[0].length;

		int[] queue = new int[m * n * 2];

		int front = 0, rear = 0;
		queue[rear++] = entrance[0];
		queue[rear++] = entrance[1];

		while (front < rear) {
			int size = (rear - front) / 2;
			steps++;// size is important to keep track of steps
			while (size-- > 0) {
				int row = queue[front++];
				int col = queue[front++];
				if (row > 0 && maze[row - 1][col] == '.') {
					if (row - 1 == 0 || col == 0 || col == n - 1) {
						return steps;
					}
					queue[rear++] = row - 1;
					queue[rear++] = col;
					maze[row - 1][col] = '+';
				}
				if (row < m - 1 && maze[row + 1][col] == '.') {
					if (row + 1 == m - 1 || col == 0 || col == n - 1) {
						return steps;
					}
					queue[rear++] = row;
					queue[rear++] = col + 1;
					maze[row][col + 1] = '+';
				}
				if (col > 0 && maze[row][col - 1] == '.') {
					if (row == 0 || row == m - 1 || col - 1 == 0) {
						return steps;
					}
					queue[rear++] = row;
					queue[rear++] = col - 1;
					maze[row][col - 1] = '+';
				}
				if (col < n - 1 && maze[row][col + 1] == '.') {
					if (row == 0 || row == m - 1 || col + 1 == n - 1) {
						return steps;
					}
					queue[rear++] = row;
					queue[rear++] = col + 1;
					maze[row][col + 1] = '+';
				}
			}
		}
		return -1;
	}

	private static int nearestExitWhileSelf(char[][] maze, int[] entrance) {
		int steps = 0;
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(entrance);
		int m = maze.length;
		int n = maze[0].length;

		// Important, otherwise few test case will fail.
		maze[entrance[0]][entrance[1]] = '+';

		while (!queue.isEmpty()) {
			int size = queue.size();
			steps++;
			while (size-- > 0) {
				int[] cell = queue.poll();
				for (int[] dir : direction) {
					int xDir = cell[0] + dir[0];
					int yDir = cell[1] + dir[1];
					if (isValidCell(xDir, yDir, m, n) && maze[xDir][yDir] == '.') {
						if (xDir == 0 || xDir == m - 1 || yDir == 0 || yDir == n - 1) {
							return steps;
						}
						// Important, otherwise test cases will fail.
						maze[xDir][yDir] = '+';
						queue.offer(new int[] { xDir, yDir });
					}
				}
			}
		}
		return -1;
	}

	private static int nearestExitArrSelf(char[][] maze, int[] entrance) {
		int steps = 0;
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] { entrance[0], entrance[1], steps });

		// Important, otherwise few test case will fail.
		maze[entrance[0]][entrance[1]] = '+';

		int m = maze.length;
		int n = maze[0].length;
		while (!queue.isEmpty()) {
			int[] cell = queue.poll();
			for (int[] dir : direction) {
				int xDir = cell[0] + dir[0];
				int yDir = cell[1] + dir[1];
				if (isValidCell(xDir, yDir, m, n) && maze[xDir][yDir] == '.') {
					if (xDir == 0 || xDir == m - 1 || yDir == 0 || yDir == n - 1) {
						return cell[2] + 1;
					}
					maze[xDir][yDir] = '+';
					queue.offer(new int[] { xDir, yDir, cell[2] + 1 });
				}
			}
		}
		return -1;
	}

	public static int nearestExitArr(char[][] maze, int[] entrance) {
		int steps = 0;
		Queue<int[]> queue = new LinkedList<int[]>();
		queue.offer(new int[] { entrance[0], entrance[1], steps });

		int m = maze.length;
		int n = maze[0].length;
		boolean[][] visited = new boolean[m][n];
		visited[entrance[0]][entrance[1]] = true;

		while (!queue.isEmpty()) {
			int[] cell = queue.poll();

			for (int[] dir : direction) {
				int xDir = cell[0] + dir[0];
				int yDir = cell[1] + dir[1];

				if (isValidCell(xDir, yDir, m, n) && !visited[xDir][yDir] && maze[xDir][yDir] == '.') {
					if (xDir == 0 || xDir == m - 1 || yDir == 0 || yDir == n - 1) {
						return cell[2] + 1;
					}
					visited[xDir][yDir] = true;
					queue.offer(new int[] { xDir, yDir, cell[2] + 1 });
				}
			}
		}
		return -1;
	}

	private static boolean isValidCell(int x, int y, int m, int n) {
		return x >= 0 && x < m && y >= 0 && y < n;
	}

	// BFS
	// For finding the shortest path in a matrix, BFS can be used. Here DFS will not
	// guarantee to find the shortest path, as it'll explore the matrix as much as
	// possible before moving on to another branch. In BFS, we explore cells by the
	// order of their distance from the start position, so whenever we reach an exit
	// cell, we're guaranteed that it's the closest exist. We will first visit the
	// cell with a distance of 0, then move on to all he cells with distance 1, 2...
	// We use a queue to store all the cells to be visited as it involves First In,
	// First Out(FIFO) order. It allows one to explore all the cells with distance d
	// which is stored previously, before move to cells with larger distance d+1.
	// To prevent revisiting same cells, we mark the unvisited neighbor cell as
	// visited before adding it to queue. We skip these visited cells during further
	// searches. So, each empty cell will be added to the queue at most once. Since
	// the input has different characters for empty cells(.) and walls(+), we can
	// mark the cells to be visited as +.
	// Algo:
	// Initialize an empty queue to store all the nodes to be visited. Add entrance
	// and its distance 0 to queue and mark entrance as visited. While we don't
	// reach the exit and queue still has cells, pop the 1st cell. If the distance
	// from entrance is curr. We check its neighboring cells in all 4 directions, if
	// it has an unvisited neighbor cell: > If this neighbor cell is an exit, return
	// its distance from the start position, curr + 1, as the nearest distance.
	// Otherwise, we mark it as visited, and add it to queue along with its distance
	// curr + 1. At end if there is no exit, return -1.
	// Time complexity - O(m*n), where m, n are size of maze. For each visited cell,
	// we add it queue and pop it once, which takes contant time as queue operation
	// is O(1). For each cell in queue, we marking it as visited, and check if it
	// has any unvisited neighbor in all 4 directions in constant time. In worst
	// case, we may have to visit O(m*n), before iteration stops.
	// Space complexity - O(max(m*n)), we can mark input matrix in-place as visited
	// which requires constant space. For storing cells in queue, in worst-case
	// there may be O(m+n) cells stored in queue. Overall it's O(m+n) + O(max(m,n)).
	private static int nearestExitFor(char[][] maze, int[] entrance) {
		int steps = 0;
		Queue<int[]> queue = new LinkedList<>();
		// Start BFS from the entrance, and use a queue to store all the cells to be
		// visited.
		queue.offer(entrance);
		int m = maze.length;
		int n = maze[0].length;

		boolean[][] visited = new boolean[m][n];
		// Mark the entrance as visited as it's not an exit.
		visited[entrance[0]][entrance[1]] = true;

		while (!queue.isEmpty()) {
			int size = queue.size();
			steps++;
			// For current cell, check its 4 neighbor cells.
			for (int i = 0; i < size; i++) {
				int[] cell = queue.poll();
				for (int[] dir : direction) {
					int xDir = cell[0] + dir[0];
					int yDir = cell[1] + dir[1];
					// If there exists anunvisited empty neighbor
					if (isValidCell(xDir, yDir, m, n) && !visited[xDir][yDir] && maze[xDir][yDir] == '.') {
						if (xDir == 0 || xDir == m - 1 || yDir == 0 || yDir == n - 1) {
							return steps;
						}
						visited[xDir][yDir] = true;
						queue.offer(new int[] { xDir, yDir });
					}
				}
			}
		}
		return -1;
	}

}
