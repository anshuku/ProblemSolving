package LeetCode.Graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/*
 * P1306. Jump Game III - Medium
 * 
 * Given an array of non-negative integers arr, you are initially positioned 
 * at start index of the array. When you are at index i, you can jump to i + arr[i] 
 * or i - arr[i], check if you can reach any index with value 0.
 * 
 * Notice that you can not jump outside of the array at any time.
 * 
 * Approach - BFS, DFS
 */
public class P1306JumpGameIII {

	public static void main(String[] args) {
		int[] arr = { 4, 2, 3, 0, 3, 1, 2 };
		int start = 5;
		int[] arr2 = arr.clone();

//		int[] arr = { 4, 2, 3, 0, 3, 1, 2 };
//		int start = 0;

//		int[] arr = { 3, 0, 2, 1, 2 };
//		int start = 2;

		boolean canReachDfs = canReachDfs(arr, start);
		System.out.println("DFS: One can reach the index with value 0: " + canReachDfs);

		arr = arr2;
		boolean canReachBfsSelf = canReachBfsSelf(arr, start);
		System.out.println("BFS Self: One can reach the index with value 0: " + canReachBfsSelf);

		boolean canReachBfsArr = canReachBfsArray(arr, start);
		System.out.println("BFS Array: One can reach the index with value 0: " + canReachBfsArr);

		boolean canReachBfsSet = canReachBfsSet(arr, start);
		System.out.println("BFS Set: One can reach the index with value 0: " + canReachBfsSet);
	}

	// DFS
	// It's similar to BFS but differs in the order of searching. We make the value
	// negative to mark as visited.
	// Time complexity - O(n), we will visit every index once.
	// Space complexity - O(n), as it needs at most O(n) stack space for recursion.
	private static boolean canReachDfs(int[] arr, int start) {
		int n = arr.length;

		if (start >= 0 && start < n && arr[start] >= 0) {
			if (arr[start] == 0) {
				return true;
			}
			arr[start] = -arr[start];

			return canReachDfs(arr, start + arr[start]) || canReachDfs(arr, start - arr[start]);
		}
		return false;
	}

	// BFS - Brute force
	// We iterate over all the possible routes and check if there is one which
	// reaches 0. If we've already checked 1 index, we don't need to check it again.
	// We can mark it as visited by making it negative.
	// Time complexity - O(n), since we'll visit every index at most once.
	// Space complexity - O(n), It neads a queue to store next index. The queue can
	// keep at most 2 levels of nodes. Since we got 2 children for each node, the
	// traversal of this solution is a binary tree. The maximum number of nodes
	// within a single level for a binary tree would be N/2, so the max length of q
	// is O(n/2 + n/2) = O(n).
	private static boolean canReachBfsSelf(int[] arr, int start) {
		int n = arr.length;

		Queue<Integer> queue = new LinkedList<>();
		queue.offer(start);

		while (!queue.isEmpty()) {
			int index = queue.poll();

			if (arr[index] == 0) {
				return true;
			}

			if (arr[index] > 0) {
				// Check available next steps
				if (index + arr[index] < n) {
					queue.offer(index + arr[index]);
				}

				if (index - arr[index] >= 0) {
					queue.offer(index - arr[index]);
				}

				// mark as visited
				arr[index] = -arr[index];
			}
		}
		return false;
	}

	public static boolean canReachBfsArray(int[] arr, int start) {
		int n = arr.length;

		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(start);

		boolean[] visited = new boolean[n];
		visited[start] = true;

		while (!queue.isEmpty()) {
			int index = queue.poll();

			if (arr[index] == 0) {
				return true;
			}

			int[] jumps = { index + arr[index], index - arr[index] };

			for (int jump : jumps) {

				if (jump >= 0 && jump < n && !visited[jump]) {
					queue.offer(jump);
					visited[jump] = true;
				}
			}

		}
		return false;
	}

	private static boolean canReachBfsSet(int[] arr, int start) {
		int n = arr.length;

		Queue<Integer> queue = new LinkedList<>();
		queue.add(start);

		Set<Integer> visited = new HashSet<>();
		visited.add(start);

		while (!queue.isEmpty()) {
			int index = queue.poll();
			if (arr[index] == 0) {
				return true;
			}

			int[] jumps = new int[] { index + arr[index], index - arr[index] };

			for (int jump : jumps) {
				if (jump >= 0 && jump < n && !visited.contains(jump)) {
					queue.add(jump);
					visited.add(jump);
				}
			}
		}
		return false;
	}

}
