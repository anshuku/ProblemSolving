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
		char[][] maze1 = maze.clone();
		int[] entrance = { 1, 2 };

//		char[][] maze = { { '+', '+', '+' }, { '.', '.', '.' }, { '+', '+', '+' } };
//		char[][] maze1 = maze.clone();
//		int[] entrance = { 1, 0 };

//		char[][] maze = { { '.', '+' } };
//		char[][] maze1 = maze.clone();
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

	private static int nearestExitFor(char[][] maze, int[] entrance) {
		int steps = 0;
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(entrance);
		int m = maze.length;
		int n = maze[0].length;

		boolean[][] visited = new boolean[m][n];
		visited[entrance[0]][entrance[1]] = true;

		while (!queue.isEmpty()) {
			int size = queue.size();
			steps++;
			for (int i = 0; i < size; i++) {
				int[] cell = queue.poll();
				for (int[] dir : direction) {
					int xDir = cell[0] + dir[0];
					int yDir = cell[1] + dir[1];
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
