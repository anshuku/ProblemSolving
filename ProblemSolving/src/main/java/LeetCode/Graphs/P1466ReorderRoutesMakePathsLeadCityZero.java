package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * P1466. Reorder Routes to Make All Paths Lead to the City Zero - Medium
 * 
 * There are n cities numbered from 0 to n - 1 and n - 1 roads such that there is only one
 * way to travel between two different cities (this network form a tree). Last year, The ministry
 * of transport decided to orient the roads in one direction because they are too narrow.
 * 
 * Roads are represented by connections where connections[i] = [ai, bi] 
 * represents a road from city ai to city bi.
 * 
 * This year, there will be a big event in the capital (city 0), 
 * and many people want to travel to this city.
 * 
 * Your task consists of reorienting some roads such that each city can visit 
 * the city 0. Return the minimum number of edges changed.
 * 
 * It's guaranteed that each city can reach city 0 after reorder.
 * 
 * Approach - DFS; BFS
 * 
 * In this directed tree graph, if there is an edge from child to parent node, one can't 
 * traverse the tree further. For this we treat the edges of tree as undirected 
 * We store negative of edge for traversal.
 */
public class P1466ReorderRoutesMakePathsLeadCityZero {

	static int count = 0;

	public static void main(String[] args) {
		int n = 6;
		int[][] connections = { { 0, 1 }, { 1, 3 }, { 2, 3 }, { 4, 0 }, { 4, 5 } };

//		int n = 3;
//		int[][] connections = { { 1, 0 }, { 2, 0 }};

//		int n = 4;
//		int[][] connections = { { 1, 0 }, { 2, 0 }, { 1, 3 } };

		int reorderCountArr = minReorderArr(n, connections);
		System.out.println("DFS Array: The number of paths reordered - " + reorderCountArr);

		int reorderCountArrayArr = minReorderArrayArr(n, connections);
		System.out.println("DFS Array of List Array: The number of paths reordered - " + reorderCountArrayArr);

		int reorderCountList = minReorderList(n, connections);
		System.out.println("DFS List: The number of paths reordered - " + reorderCountList);

		P1466ReorderRoutesMakePathsLeadCityZero.count = 0;

		int reorderCountQueue = minReorderQueue(n, connections);
		System.out.println("BFS: The number of paths reordered - " + reorderCountQueue);

		P1466ReorderRoutesMakePathsLeadCityZero.count = 0;

		int reorderCountMap = minReorderMap(n, connections);
		System.out.println("DFS Map: The number of paths reordered - " + reorderCountMap);

	}

	private static int minReorderArr(int n, int[][] connections) {
		List<Integer>[] adjList = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}
		for (int[] c : connections) {
			adjList[c[0]].add(c[1]);
			adjList[c[1]].add(-c[0]);
		}

		return dfs(adjList, 0, -1);
	}

	private static int dfs(List<Integer>[] adjList, int city, int parent) {
		int count = 0;
		for (int to : adjList[city]) {
			if (parent != Math.abs(to)) {
				if (to > 0) {
					count++;
				}
				count += dfs(adjList, Math.abs(to), city);
			}
		}
		return count;
	}

	private static int minReorderArrayArr(int n, int[][] connections) {
		List<int[]>[] adjList = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<int[]>();
		}
		for (int c[] : connections) {
			adjList[c[0]].add(new int[] { c[1], 1 });
			adjList[c[1]].add(new int[] { c[0], 0 });
		}
		boolean[] visited = new boolean[n];
		dfs(adjList, visited, 0);
		return count;
	}

	private static void dfs(List<int[]>[] adjList, boolean[] visited, int cityFrom) {
		visited[cityFrom] = true;
		for (int[] c : adjList[cityFrom]) {
			int neighbour = c[0];
			int sign = c[1];
			if (!visited[neighbour]) {
				count += sign;
				dfs(adjList, visited, neighbour);
			}
		}
	}

	// Time complexity - O(n) for visiting n nodes in the tree(graph)
	// Space complexity - O(n) for creating adjacency list and visited array
	public static int minReorderList(int n, int[][] connections) {

		List<List<Integer>> adjList = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			adjList.add(new ArrayList<Integer>());
		}

		for (int[] c : connections) {
			adjList.get(c[0]).add(c[1]);
			adjList.get(c[1]).add(-c[0]);
		}
		boolean[] visited = new boolean[n];
		return dfs(adjList, visited, 0);
	}

	private static int dfs(List<List<Integer>> adjList, boolean[] visited, int cityFrom) {
		int reorderCount = 0;
		visited[cityFrom] = true;
		for (int to : adjList.get(cityFrom)) {
			if (!visited[Math.abs(to)]) {
				if (to > 0) { // No forward edge in DFS starting from 0, away from 0
					reorderCount++;
				}
				reorderCount += dfs(adjList, visited, Math.abs(to));
			}
		}

		return reorderCount;
	}

	private static int minReorderQueue(int n, int[][] connections) {
		Map<Integer, List<List<Integer>>> adjList = new HashMap<>();
		Map<Integer, int[][]> adjArr = new HashMap<>();
		int index = 0;
//		for (int[] c : connections) {
//			adjArr.computeIfAbsent(c[0], i -> new int[n][2])[index][0] = c[1];
//			adjArr.computeIfAbsent(c[0], i -> new int[n][2])[index][1] = 1;
//			adjArr.computeIfAbsent(c[1], i -> new int[n][2])[index][0] = c[0];
//			adjArr.computeIfAbsent(c[1], i -> new int[n][2])[index][1] = 0;
//			index++;
//		}
		for (int[] c : connections) {
			adjList.computeIfAbsent(c[0], i -> new ArrayList<List<Integer>>()).add(Arrays.asList(c[1], 1));
			adjList.computeIfAbsent(c[1], i -> new ArrayList<List<Integer>>()).add(Arrays.asList(c[0], 0));
		}
		bfs(adjList, 0, n);
		return count;
	}

	private static void bfs(Map<Integer, List<List<Integer>>> adjList, int node, int n) {
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(node);
		boolean[] visited = new boolean[n];
		visited[node] = true;
		while (!queue.isEmpty()) {
			int v = queue.poll();
			for (List<Integer> list : adjList.get(v)) {
				int neighbour = list.get(0);
				int sign = list.get(1);
				if (!visited[neighbour]) {
					count += sign;
					queue.offer(neighbour);
					visited[neighbour] = true;
				}
			}

		}
	}

	// Time complexity - O(n), the adjacency list is initialized in O(n) time
	// The dfs function visits each node once - O(n)
	// For undirected edges, each edge is visited twice(nodes at end) - O(e)
	// The resulting graph is a tree, there are n-1 undirected edges.
	// Space complexity - the adjacency list is built in O(n) time.
	// The recursion stack used by dfs function can have no more than n elements.
	private static int minReorderMap(int n, int[][] connections) {
		Map<Integer, List<List<Integer>>> adjList = new HashMap<>();
		for (int[] c : connections) {
			// Returns the value from the mapping function to use add function
			// original edge
			adjList.computeIfAbsent(c[0], i -> new ArrayList<List<Integer>>()).add(Arrays.asList(c[1], 1));
			// artificial edge for traversing the nodes
			adjList.computeIfAbsent(c[1], i -> new ArrayList<List<Integer>>()).add(Arrays.asList(c[0], 0));
		}
		dfs(adjList, 0, -1);
		return count;
	}

	private static void dfs(Map<Integer, List<List<Integer>>> adjList, int node, int parent) {
		// Below code is redundant
		if (!adjList.containsKey(node)) {
			return;
		}
		for (List<Integer> list : adjList.get(node)) {
			int neighbour = list.get(0);
			int sign = list.get(1);
			if (parent != neighbour) {
				count += sign;
				dfs(adjList, neighbour, node);
			}
		}
	}

}
