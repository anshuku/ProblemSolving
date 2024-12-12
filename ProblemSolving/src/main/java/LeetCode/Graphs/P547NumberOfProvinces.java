package LeetCode.Graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/*
 * P547. Number of Provinces - Medium
 * 
 * There are n cities. Some of them are connected, while some are not. If city a is 
 * connected directly with city b, and city b is connected directly with city c, 
 * then city a is connected indirectly with city c.
 * 
 * A province is a group of directly or indirectly connected cities 
 * and no other cities outside of the group.
 * 
 * You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the 
 * ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.
 * 
 * Return the total number of provinces.
 * 
 * Approach - DFS, BFS, Disjoint sets
 * 
 * The connectivity is transitive, which means that if city a is directly connected with city b
 * and city b is directly connected with city c, then city a is indirectly connected with city c.
 *  
 * The number of required provinces is the number of connected components formed in such a graph.
 * To check the number of connected components in a graph, we can use a 
 * graph traversal algorithm like depth first search (DFS).
 * 
 * A disjoint set data structure is also called merge-find set or union-find data structure.
 * 
 * - Finding number of connected components can solve this.
 */
public class P547NumberOfProvinces {

	static class UnionFind {

		int[] parent;
		int[] rank;

		public UnionFind(int size) {
			parent = new int[size];
			rank = new int[size];
			for (int i = 0; i < size; i++) {
				parent[i] = i;
			}
		}

		public int find(int val) {
			if (parent[val] != val) {
				parent[val] = find(parent[val]);
			}
			return parent[val];
		}

		public void union(int x, int y) {
			int p1 = find(x);
			int p2 = find(y);
			if (p1 == p2) {
				return;
			} else if (rank[p1] < rank[p2]) {
				parent[p1] = p2;
			} else if (rank[p1] > rank[p2]) {
				parent[p2] = p1;
			} else {
				parent[p2] = p1;
				rank[p1]++;
			}
		}
	}

	public static void main(String[] args) {
//		int[][] isConnected = { { 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 1 } };

//		int[][] isConnected = { { 1, 0, 0, 1 }, { 0, 1, 1, 0 }, { 0, 1, 1, 1 }, { 1, 0, 1, 1 } };

		int[][] isConnected = { { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 } };

		int provincesUnionFind = findCircleNumDS(isConnected);

		System.out.println("Disjoint Set: The number of provinces are - " + provincesUnionFind);

		int provincesBfs = findCircleNumBfs(isConnected);

		System.out.println("BFS Queue: The number of provinces are - " + provincesBfs);

		int provincesDfs = findCircleNumDfs(isConnected);

		System.out.println("DFS Recursive: The number of provinces are - " + provincesDfs);

		int provincesStack = findCircleNumStack(isConnected);

		System.out.println("Stack: The number of provinces are - " + provincesStack);
	}

	// Preferable when the cities and connections are added dynamically
	// Time complexity - O(n^2), is needed for iterating over isConnected
	//
	// For T operations of Union Find(find with path compression and union by rank),
	// it takes constant O(1) time. The amortized time complexity is O(alpha(T))
	// where alpha(T) is inverse Ackermann function which grows slowly
	// It doesn't exceed 4 for all reasonable T(T < 10^600). Considered O(1)
	//
	// For initializing union find - O(n) time for parent and rank array
	// We iterate through each edge and use the find operation which takes
	// O(1) time to find the component of nodes connected by each edge.
	// For n nodes max n^2 edges(each node is connected to other) - O(n^2) time
	// If nodes from different components are connected by an edge,
	// union is performed for nodes which takes O(1) time. For n edges O(n) time
	// Space complexity - O(n), for parent and rank array of size n.
	private static int findCircleNumDS(int[][] isConnected) {
		int n = isConnected.length;
		UnionFind unf = new UnionFind(n);
		int provinces = n;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (isConnected[i][j] == 1 && unf.find(i) != unf.find(j)) {
					provinces--;
					unf.union(i, j);
				}
			}
		}
		return provinces;
	}

	// Time complexity - O(n^2), visited array takes O(n) time to initialize
	// Each queue operation takes O(1) time, O(n) time since each node is added once
	// ALl edges are iterated over once which take O(n) time for each visited node
	// Total time is O(n^2)
	// Space complexity - O(n), visit array takes O(n) space
	// The queue can store at most n elements in worst case
	private static int findCircleNumBfs(int[][] isConnected) {
		int provinces = 0;
		int n = isConnected.length;
		boolean[] visited = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				provinces++;
				bfs(isConnected, visited, i);
			}
		}
		return provinces;
	}

	private static void bfs(int[][] isConnected, boolean[] visited, int city) {
		visited[city] = true;
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(city);

		while (!queue.isEmpty()) {
			city = queue.poll();
			for (int i = 0; i < isConnected.length; i++) {
				if (!visited[i] && isConnected[city][i] == 1) {
					queue.offer(i);
					visited[i] = true;
				}
			}
		}
	}

	// Time complexity - O(n^2), visited array takes O(n) time to initialize
	// The dfs function visits each node once which takes O(n) time.
	// From each node, we iterate over all the possible edges which takes O(n) time.
	// So total O(n^2) time for all the nodes and iterate over its edges.
	// Space complexity - O(n), visit array takes O(n) space
	// The recursion call stack for dfs is not more than n elements
	private static int findCircleNumDfs(int[][] isConnected) {
		int n = isConnected.length;
		int provinces = 0;
		boolean[] visited = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				provinces++;
				dfs(isConnected, visited, i);
			}
		}
		return provinces;
	}

	private static void dfs(int[][] isConnected, boolean[] visited, int city) {
		visited[city] = true;
		for (int i = 0; i < visited.length; i++) {
			if (!visited[i] && isConnected[city][i] == 1) {
				dfs(isConnected, visited, i);
			}
		}
	}

	// Gives wrong result for higher isConnected grid
	public static int findCircleNumStack(int[][] isConnected) {
		Stack<Integer> stack = new Stack<>();
		int n = isConnected.length;
		boolean[] visited = new boolean[n];
		visited[0] = true;
		int[] parent = new int[n];
		for (int i = 0; i < n; i++) {
			if (isConnected[0][i] == 1) {
				parent[i] = 0;
				if (i != 0) {
					stack.push(i);
				}
				isConnected[0][i] = 0;
			} else {
				parent[i] = i;
			}
		}
		while (!stack.isEmpty()) {
			int city = stack.pop();
			if (!visited[city]) {
				visited[city] = true;
				for (int i = 0; i < n; i++) {
					if (isConnected[city][i] == 1) {
						parent[i] = city;
						if (i != city) {
							stack.push(i);
						}
						isConnected[city][i] = 0;
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			collapsingFind(parent, i);
		}
		Set<Integer> set = new HashSet<>();
		for (int num : parent) {
			set.add(num);
		}
		return set.size();
	}

	private static int collapsingFind(int[] parent, int i) {
		if (parent[i] == i) {
			return i;
		}
		int result = collapsingFind(parent, parent[i]);
		parent[i] = result;
		return result;
	}

}
