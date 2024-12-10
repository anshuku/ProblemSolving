package LeetCode.Graphs;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/*
 * P841. Keys and Rooms - Medium
 * 
 * There are n rooms labeled from 0 to n - 1 and all the rooms are locked 
 * except for room 0. Your goal is to visit all the rooms. However, you cannot 
 * enter a locked room without having its key.
 * 
 * When you visit a room, you may find a set of distinct keys in it. Each key has a 
 * number on it, denoting which room it unlocks, and you can take all of them 
 * with you to unlock the other rooms.
 * 
 * Given an array rooms where rooms[i] is the set of keys that you can obtain if 
 * you visited room i, return true if you can visit all the rooms, or false otherwise.
 * 
 * Approach - DFS - Stack/Recursion
 */
public class P841KeysAndRooms {

	public static void main(String[] args) {
		List<List<Integer>> rooms = Arrays.asList(Arrays.asList(1), Arrays.asList(2), Arrays.asList(3),
				Arrays.asList());

//		List<List<Integer>> rooms = Arrays.asList(Arrays.asList(1, 3), Arrays.asList(3, 0, 1), Arrays.asList(2),
//				Arrays.asList(0));

		boolean canVisitRecursion = canVisitAllRoomsRecursion(rooms);
		System.out.println("Recursion: All rooms can be visited - " + canVisitRecursion);

		boolean canVisitStack = canVisitAllRoomsStack(rooms);
		System.out.println("Stack: All rooms can be visited - " + canVisitStack);
	}

	private static boolean canVisitAllRoomsRecursion(List<List<Integer>> rooms) {
		boolean[] visited = new boolean[rooms.size()];
		canVisitRecursive(rooms, visited, 0);
		for (boolean val : visited) {
			if (!val) {
				return false;
			}
		}
		return true;
	}

	private static void canVisitRecursive(List<List<Integer>> rooms, boolean[] visited, int key) {
		if (!visited[key]) {
			visited[key] = true;
			for (int room : rooms.get(key)) {
				if (!visited[room]) {
					canVisitRecursive(rooms, visited, room);
				}
			}
		}
	}

	// Time Complexity - O(n+e), where n is number of rooms, e - keys
	// Space Complexity - O(n), where n is visited and stack space need for n rooms
	public static boolean canVisitAllRoomsStack(List<List<Integer>> rooms) {
		Stack<Integer> stack = new Stack<>();
		for (int num : rooms.get(0)) {
			stack.push(num);
		}
		boolean[] visited = new boolean[rooms.size()];
		visited[0] = true;
		while (!stack.isEmpty()) {
			int room = stack.pop();
			if (!visited[room]) {
				visited[room] = true;
				for (int num : rooms.get(room)) {
					stack.push(num);
				}
			}
		}
		for (boolean val : visited) {
			if (!val) {
				return false;
			}
		}
		return true;
	}

}
