package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * P839. Similar String Groups - Hard
 * 
 * Two strings, X and Y, are considered similar if either they are identical or we can make 
 * them equivalent by swapping at most two letters (in distinct positions) within the string X.
 * 
 * For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and 
 * "rats" and "arts" are similar, but "star" is not similar to "tars", "rats", or "arts".
 * 
 * Together, these form two connected groups by similarity: {"tars", "rats", "arts"}
 * and {"star"}.  Notice that "tars" and "arts" are in the same group even though 
 * they are not similar Formally, each group is such that a word is in the group
 * if and only if it is similar to at least one other word in the group.
 *   
 * We are given a list strs of strings where every string in strs is 
 * an anagram of every other string in strs. How many groups are there?
 * 
 * Approach - UnionFind, DFS, BFS
 */
public class P839SimilarStringGroups {

	static class UnionFind {
		int[] parent;
		int[] rank;

		UnionFind(int n) {
			parent = new int[n];
			rank = new int[n];

			for (int i = 0; i < n; i++) {
				parent[i] = i;
			}
		}

		public int find(int i) {
			if (i != parent[i]) {
				parent[i] = find(parent[i]);
			}
			return parent[i];
		}

		public void union(int i, int j) {
			int p1 = find(i);
			int p2 = find(j);

			if (p1 != p2) {
				if (rank[p1] > rank[p2]) {
					parent[p2] = p1;
				} else if (rank[p1] < rank[p2]) {
					parent[p1] = p2;
				} else {
					parent[p2] = p1;
					rank[p1]++;
				}
			}
		}
	}

	public static void main(String[] args) {
		String[] strs = { "tars", "rats", "arts", "star" };

//		String[] strs = { "omv", "ovm" };

		int numSimilarUnionFind = numSimilarGroupsUnionFind(strs);
		System.out.println("Union Find: The number of similar groups are: " + numSimilarUnionFind);

		int numSimilarDFSList = numSimilarGroupsDFSList(strs);
		System.out.println("DFS List: The number of similar groups are: " + numSimilarDFSList);

		int numSimilarDFSMap = numSimilarGroupsDFSMap(strs);
		System.out.println("DFS Map: The number of similar groups are: " + numSimilarDFSMap);

		int numSimilarBFSList = numSimilarGroupsBFSList(strs);
		System.out.println("BFS List: The number of similar groups are: " + numSimilarBFSList);

	}

	// Union Find
	// 2 words A and B belong to same group if they are similar or equal, or if
	// there are some words in the group such as X1, X2, ... XN, such that A is
	// similar to X1, X1 is similar to X2, ... Xn is similar to B. It means that if
	// we can create a path from A to B using words from the group, then A and B are
	// also members of that group. This prompts one to think about the problem in
	// terms of graphs. Each word in strs can be viewed as a node. We can add an
	// undirected edge between each pair of similar words. If there is a path in
	// this graph from words A to B, then A and B belong to the same group. Because
	// the graph is undirected, A and B belong to the same group if and only if they
	// belong to the same component of the graph. The number of required groups is
	// the number of connected components formed in such a graph.
	// To solve this graph connectivity problem, we can use union-find data
	// structure. We count the number of connected components formed in the graph by
	// indices of words acting as nodes and an edge between indices of every 2
	// similar words. We initialize all nodes as separate components in the union
	// find data structure. We declare and initialize a variable called count = n to
	// count the number of connected components in the graph. We iterate over all
	// the edges, decrementing count by 1 for each edge whenver 2 different
	// components are merged into a single one using that edge. We iterate through
	// all of the pairs that can be formed by selecting any 2 words from strs, and
	// for each pair of similar words at index i and j, we use the find operation to
	// determine which components both of them belong to. If they belong to
	// different components, i.e., find(node1) !=find(node2), we perform a union
	// operation on both nodes, combining the 2 different connected components into
	// a single connected component. We also reduce count by 1. We skip if i and j
	// are in same component. We return count at end.
	// Time complexity - O(n^2*m), Here n is the size of strs and m is the length of
	// each word in strs. We need O(n^2) time to iterate over all the pairs of words
	// that can be formed using strs. We further need O(m) time to check whether the
	// chosen two words are similar or not, resulting in O(n^2*m) operations to
	// check all the pairs.
	// For T operations, the amortized time complexity of the union-find algorithm
	// (using path compression with union by rank) is O(alpha(T)). Here, alpha(T),
	// is the inverse Ackermann function that grows so slowly that it doesn't exceed
	// 4 for all reasonable T(approx T < 10 ^ 600). Because the function grows so
	// slowly, we consider it to be O(1).
	// Initializing UnionFind takes O(n) time because we are initializing the parent
	// and rank arrays of size n each.
	// We iterate through every edge and use the find operation to find the
	// component of nodes connected by each edge. It takes O(1) per operation and
	// takes O(e) time for all the e edges. We can have a maximum of O(n^2) edges in
	// between n nodes, so it would take O(n^2) time. If nodes from different
	// components are connected by an edge, we also perform union of the nodes,
	// which takes O(1) time per operation. In the worst-case scenario, it may be
	// called O(n) times to connect all the components to form a connected graph
	// with only 1 component.
	// Space complexity - O(n), for using parent and rank array.
	public static int numSimilarGroupsUnionFind(String[] strs) {
		int n = strs.length;

		UnionFind uf = new UnionFind(n);
		int count = n; // can go inside UnionFind class as well, but little slower

		// Form a graph from the given strings array.
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (isSimilar(strs[i].toCharArray(), strs[j].toCharArray()) && uf.find(i) != uf.find(j)) {
					count--;
					uf.union(i, j);
				}
			}
		}
		return count;
	}

	// DFS: adjacency list
	// We make a graph with help of an adjacency list, with an integer as index, and
	// a list of integers as the value to map the index of the word to a list of
	// indices of words that are similar to it. We're using the indices as the nodes
	// instead of strings themselves as it's faster. For a pair of similar words A
	// and B with indices i and j in strs, we add j to the adj[i] list and i to the
	// adj[j] list. This adds an undirected edge between nodes i and j. We iterate
	// over all the pairs of words that can be formed in strs, see if they're
	// similar, and then add an edge between them. This forms the graph. To find the
	// number of connected components, we use DFS. We use a dfs recursive method
	// which takes node, adj, visit as parameters. The parameter node is the index
	// of the word from which we start our path. visit is used to keep track of
	// visited indices, adj is adjacency list. In dfs, we mark the node as visited.
	// We then iterate over all the neighbors of node and recursively visit them to
	// cover all the nodes in the current connected component. To figure out how
	// many connected components are there in the graph, we first mark all nodes as
	// unvisited. We create a variable called count = 0 which counts the number of
	// connected components. We iterate through all the nodes from 0 to n - 1,
	// checking whether each node has been visited or not. If node is not visited,
	// we begin a DFS traversal from node and increment count by 1(means a new
	// connected component). The DFS traversal would visit all of the nodes in the
	// component to which the node belongs. We return count.
	// Time complexity - O(n^2*m), where n = size of strs and m = length of each
	// word in strs. We iterate over all pairs of words that can be formed using
	// strs, we need O(n^2) time. We also need O(m) time to determine whether the
	// chosen 2 words are similar or not, which results in O(n^2*m) operations to
	// check all the pairs. The dfs function visits each node once, which takes O(n)
	// time because there are n nodes in total. We can have upto O(n^2) edges
	// between n nodes(assume every word is similar to every other word). As we've
	// undirected edges, each edge can only be iterated twice(by nodes at the end),
	// resulting in O(n^2) operations total in the worst-case scenario while
	// visiting all nodes.
	// Space complexity - O(n^2), as there can be a maximum of O(n^2) edges,
	// building the adjacency list takes O(n^2) space. The visit array takes O(n)
	// space. The recusion call stack used by dfs can have no more than n elements
	// in the worst-case scenario. It would take up O(n) space in that case.
	private static int numSimilarGroupsDFSList(String[] strs) {
		int n = strs.length;

		int count = 0;

		List<Integer>[] adjList = new ArrayList[n]; // faster than adjMap

		// Form a graph from the given strings array.
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (isSimilar(strs[i].toCharArray(), strs[j].toCharArray())) {
					if (adjList[i] == null) {
						adjList[i] = new ArrayList<>();
					}
					adjList[i].add(j);

					if (adjList[j] == null) {
						adjList[j] = new ArrayList<>();
					}
					adjList[j].add(i);
				}
			}
		}

		boolean[] visited = new boolean[n];

		// Count the number of connected components.
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				count++;
				dfsList(i, visited, adjList);
			}
		}

		return count;
	}

	private static void dfsList(int node, boolean[] visited, List<Integer>[] adjList) {
		visited[node] = true;

		if (adjList[node] == null) {
			return;
		}

		for (int neighbor : adjList[node]) {
			if (!visited[neighbor]) {
				dfsList(neighbor, visited, adjList);
			}
		}
	}

	// DFS: adjacency map
	private static int numSimilarGroupsDFSMap(String[] strs) {
		int n = strs.length;

		Map<Integer, List<Integer>> adjMap = new HashMap<>();
		int count = 0;

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (isSimilar(strs[i].toCharArray(), strs[j].toCharArray())) {
					adjMap.computeIfAbsent(i, k -> new ArrayList<>()).add(j);
					adjMap.computeIfAbsent(j, k -> new ArrayList<>()).add(i);
				}
			}
		}

		boolean[] visited = new boolean[n];

		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				count++;
				dfsMap(i, visited, adjMap);
			}
		}

		return count;
	}

	private static void dfsMap(int node, boolean[] visited, Map<Integer, List<Integer>> adjMap) {
		visited[node] = true;

		if (!adjMap.containsKey(node)) {
			return;
		}

		for (int neighbor : adjMap.get(node)) {
			if (!visited[neighbor]) {
				dfsMap(neighbor, visited, adjMap);
			}
		}

	}

	private static boolean isSimilar(char[] s1, char[] s2) {
		int n = s1.length;
		int diff = 0;

		for (int i = 0; i < n; i++) {
			if (s1[i] != s2[i]) {
				diff++;
			}
		}
		return diff == 0 || diff == 2;
	}

	// BFS: Queue
	// To find the number of connected components in the graph, we can us BFS. The
	// algo is used to traverse or search the graph. It'll travel in a level-wise
	// manner, i.e., all the nodes at the present level(say l) are exploted before
	// moving on to the nodes at the next level(l + 1), where a level's number is
	// the distance from a starting node.
	// Time complexity - O(n^2*m), where n = size of strs and m = length of each
	// word in strs. We iterate over all pairs of words that can be formed using
	// strs, we need O(n^2) time. We also need O(m) time to determine whether the
	// chosen 2 words are similar or not, which results in O(n^2*m) operations to
	// check all the pairs. Each queue operation in the BFS algo takes O(1) time,
	// and a single node can only be pushed once, leading to O(n) operations for n
	// nodes. We can have upto O(n^2) edges between n nodes(assume every word is
	// similar to every other word). As we've undirected edges, each edge can only
	// be iterated twice(by nodes at the end), resulting in O(n^2) operations total
	// in the worst-case scenario while visiting all nodes.
	// Space complexity - O(n^2), as there can be a maximum of O(n^2) edges,
	// building the adjacency list takes O(n^2) space in worst case. The visit array
	// takes O(n) space. The BFS queue takes O(n) time as each node is added at most
	// once.
	private static int numSimilarGroupsBFSList(String[] strs) {
		int n = strs.length;

		List<Integer>[] adjList = new ArrayList[n];

		// Form a graph from the given strings array.
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (isSimilar(strs[i].toCharArray(), strs[j].toCharArray())) {
					if (adjList[i] == null) {
						adjList[i] = new ArrayList<>();
					}
					adjList[i].add(j);
					if (adjList[j] == null) {
						adjList[j] = new ArrayList<>();
					}
					adjList[j].add(i);
				}
			}
		}

		int count = 0;
		boolean[] visited = new boolean[n];

		// Count the number of connected components.
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				count++;
				bfsList(i, visited, adjList);
			}
		}

		return count;
	}

	private static void bfsList(int node, boolean[] visited, List<Integer>[] adjList) {
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(node);

		visited[node] = true;

		while (!queue.isEmpty()) {
			node = queue.poll();

			if (adjList[node] == null) {
				return;
			}

			for (int neighbor : adjList[node]) {
				if (!visited[neighbor]) {
					queue.offer(neighbor);
					visited[neighbor] = true;
				}
			}
		}

	}
}
