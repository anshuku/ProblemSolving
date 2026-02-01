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
 * Approach - DFS | BFS
 * 
 * In this directed tree graph, if there is an edge from child to parent node, one can't 
 * traverse the tree further. For this we treat the edges of tree as undirected 
 * We store negative of edge for traversal.
 * 
 * Creating an instance of List<int[]>[] can't be done because of java's generic type erasure.
 * Generics and Arrays don't work well together, since generics are invariant
 * while arrays are covariant in nature.
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
		System.out.println("DFS Array: The number of paths reordered: " + reorderCountArr);

		int reorderCountArrayArr = minReorderArrayArr(n, connections);
		System.out.println("DFS Array of List Array: The number of paths reordered: " + reorderCountArrayArr);

		int reorderCountList = minReorderList(n, connections);
		System.out.println("DFS List: The number of paths reordered: " + reorderCountList);

		P1466ReorderRoutesMakePathsLeadCityZero.count = 0;

		int reorderCountQueueArr = minReorderQueueArr(n, connections);
		System.out.println("BFS Arr: The number of paths reordered: " + reorderCountQueueArr);

		P1466ReorderRoutesMakePathsLeadCityZero.count = 0;

		int reorderCountQueue = minReorderQueue(n, connections);
		System.out.println("BFS: The number of paths reordered: " + reorderCountQueue);

		P1466ReorderRoutesMakePathsLeadCityZero.count = 0;

		int reorderCountMapArr = minReorderMapArr(n, connections);
		System.out.println("DFS Map Arr: The number of paths reordered: " + reorderCountMapArr);

		P1466ReorderRoutesMakePathsLeadCityZero.count = 0;

		int reorderCountMap = minReorderMap(n, connections);
		System.out.println("DFS Map: The number of paths reordered: " + reorderCountMap);

	}

	private static int minReorderArr(int n, int[][] connections) {
		// Can't create an instance of List<Integer>[] due to java's generic type
		// erasure.
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
		// Can't create an instance of List<int[]>[] due to java's generic type erasure.
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

	private static int minReorderQueueArr(int n, int[][] connections) {
		Map<Integer, List<int[]>> adj = new HashMap<>();
		for (int[] c : connections) {
			adj.computeIfAbsent(c[0], k -> new ArrayList<>()).add(new int[] { c[1], 1 });
			adj.computeIfAbsent(c[1], k -> new ArrayList<>()).add(new int[] { c[0], 0 });
		}
		return bfs2(adj, 0, n);
	}

	private static int bfs2(Map<Integer, List<int[]>> adj, int start, int n) {
		int count = 0;
		boolean[] visited = new boolean[n];
		Queue<Integer> queue = new LinkedList<>();
		queue.add(start);
		visited[start] = true;
		while (!queue.isEmpty()) {
			int parent = queue.poll();
			for (int[] child : adj.get(parent)) {
				int childId = child[0];
				int sign = child[1];
				if (!visited[childId]) {
					count += sign;
					queue.offer(childId);
					visited[childId] = true;
				}
			}
		}
		return count;
	}

	// BFS
	// We can use BFS as we only need to find the number of edges that are directed
	// from the parent node to the child node in a rooted tree. Algo: Initialize
	// count as number of edges that must be flipped. Create an adjacency list adj
	// that contains a list of pairs of integers such that adj[node] contains all
	// the neighbors of node in the form of (neighbor, sign). Start BFS traversal:
	// We use bfs function for traversal with node = 0, n, adj as the params. Create
	// a visited array of length n(we can't use parent != child here) to keep track
	// of nodes that are visited. Initialize queue q of integers and push the
	// root node 0 into it and mark visited[root] = true. While the queue is not
	// empty: We dequeue the 1st element(root) node from the queue and iterate over
	// all its neighbors using adj[node]. For each (neighbor, sign) in adj[node], we
	// check if neighbor has been visited already. If neighbor has not yet been
	// visited, we mark it visited, we mark it visited, perform count += sign, and
	// push neighbor into the queue.
	// Time complexity - O(n), where n = number of nodes, the adjacency list and
	// visited array takes O(n) time. Each queue operation in the bfs algo takes
	// O(1) time, and a single node can only be pushed once so O(n) for n nodes.
	// We iterate over all the neighbors of the popped node, for an undirected edge,
	// a given edge could be iterated at most twice(by nodes at both ends) so O(e)
	// operations for all the nodes. Since, the resulting graph is a tree, there are
	// n-1 undirected edges. So, O(n+e) = O(n).
	// Space complexity - the adjacency list and visited array takes O(n) space.
	// The BFS queue in worst case takes O(n) space, when each node is added once.
	private static int minReorderQueue(int n, int[][] connections) {
		Map<Integer, List<List<Integer>>> adjList = new HashMap<>();
//		Map<Integer, int[][]> adjArr = new HashMap<>();
//		int index = 0;
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

	// DFS
	// We bring everyone to node 0: a graph is a tree rooted at node 0. To move from
	// any node to the root, all edges must be directed from a child to its parent.
	// If there is an edge from parent node to it's child node, no node in the
	// subtree of the child can reach the root node. The edge must be flipped as
	// there is only 1 way to travel between cities. So, we need to count the number
	// of edges in a tree rooted at node 0 that are directed from parent to child.
	// We traverse the entire tree via DFS. Once we get an unvisited node, we'll
	// take 1 of its neighbor nodes(if exists) as the next node on this branch.
	// Recursively call the function to take the next node as the starting node and
	// solve the subproblem. Here, the edges are directed. To count the number of
	// edges that are directed from a parent to its child node, we must traverse the
	// entire tree. If there is an edge from a child -> parent, we'll be unable to
	// reach the child from parent. To traverse the entire tree, we must traverse
	// from node 0 to all of the nodes in any case which is possible if the
	// edges are undirected. For this, we add an opposite edge from node b to node a
	// (artifical edge) for every given edge in connections from node a to node b
	// (original edge). If we use 'artifical' edge to move from the parent node to
	// the child node, it mean original edge is directed from child to parent node
	// so we don't flip the node. If we use original edge to move, it means we need
	// to flip the edge. Whenever we find such edge, we increment counter by 1.
	// To differentiate original and artificial edge we can use booleans, specific
	// numbers etc. We associate an extra value with each edge: 1 for original and 0
	// for artificial. We use count variable to count number of edges to be flipped.
	// We start DFS from root 0 and go way down the tree(parent to child). If we get
	// an original edge during the traversal, we increase the count by 1 and keep it
	// same for artifical edge. We combine these 2 operations and perform count +=
	// sign. Algo: Initialize count as number of edges that must be flipped. Create
	// an adjacency list adj that contains a list of pairs of integers such that
	// adj[node] contains all the neighbors of node in the form of (neighbor, sign).
	// Start a DFS: We use a dfs to perform the traversal. For each call, pass node,
	// parent, adj as the params. We start with node 0 and parent as -1. Iterate
	// over all the children of the node(nodes that share an edge) using adj[node].
	// For every (child, sign) in adj[node], if cild = parent, we'll not visit it
	// again. If child =/= parent, we perform count+= sign and recursively call dfs
	// with node = child and parent = node.
	// Time complexity - O(n), n = number of nodes, the adjacency list is
	// initialized in O(n) time. The dfs function visits each node once - O(n).
	// For undirected edges, each edge can only be visited twice(nodes at end) -
	// O(e). Since, the resulting graph is a tree, there are n-1 undirected edges.
	// So, O(n+e) = O(n).
	// Space complexity - the adjacency list is built takes O(n) space.
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
//		if (!adjList.containsKey(node)) {
//			return;
//		}
		for (List<Integer> list : adjList.get(node)) {
			int neighbour = list.get(0);
			int sign = list.get(1);
			if (parent != neighbour) {
				count += sign;
				dfs(adjList, neighbour, node);
			}
		}
	}

	private static int minReorderMapArr(int n, int[][] connections) {
		Map<Integer, List<int[]>> adj = new HashMap<>();
		for (int[] c : connections) {
			adj.computeIfAbsent(c[0], k -> new ArrayList<>()).add(new int[] { c[1], 1 });
			adj.computeIfAbsent(c[1], k -> new ArrayList<>()).add(new int[] { c[0], 0 });
		}
		return dfsIntArr(adj, 0, -1);
	}

	private static int dfsIntArr(Map<Integer, List<int[]>> adj, int city, int parent) {
//		if (!adj.containsKey(city)) {
//			return 0;
//		}
		int count = 0;
		for (int[] neighbor : adj.get(city)) {
			if (neighbor[0] != parent) {
				count += neighbor[1];
				count += dfsIntArr(adj, neighbor[0], city);
			}
		}
		return count;
	}

}
