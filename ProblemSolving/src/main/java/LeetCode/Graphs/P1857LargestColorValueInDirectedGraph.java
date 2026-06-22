package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * P1857. Largest Color Value in a Directed Graph - Hard
 * 
 * There is a directed graph of n colored nodes and m edges. The nodes are numbered from 0 to n - 1.
 * 
 * You are given a string colors where colors[i] is a lowercase English letter representing 
 * the color of the ith node in this graph (0-indexed). You are also given a 2D array edges 
 * where edges[j] = [aj, bj] indicates that there is a directed edge from node aj to node bj.
 * 
 * A valid path in the graph is a sequence of nodes x1 -> x2 -> x3 -> ... -> xk such that 
 * there is a directed edge from xi to x(i+1) for every 1 <= i < k. The color value of the path 
 * is the number of nodes that are colored the most frequently occurring color along that path.
 * 
 * Return the largest color value of any valid path in the given graph, or -1 if the graph contains a cycle.
 * 
 * Approach - Topological Sort: Kahn's Algo, DFS
 */
public class P1857LargestColorValueInDirectedGraph {

	public static void main(String[] args) {
//		String colors = "abaca";
//		int[][] edges = { { 0, 1 }, { 0, 2 }, { 2, 3 }, { 3, 4 } };

//		String colors = "a";
//		int[][] edges = { { 0, 0 } };

//		String colors = "nnllnzznn";
//		int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 2, 4 }, { 3, 5 }, { 4, 6 }, { 3, 6 }, { 5, 6 }, { 6, 7 },
//				{ 7, 8 } };

		String colors = "iivvvvv";
		int[][] edges = { { 0, 1 }, { 1, 2 }, { 1, 3 }, { 2, 3 }, { 3, 4 }, { 2, 4 }, { 3, 5 }, { 1, 5 }, { 4, 5 },
				{ 5, 6 } };

		int largestColourValueKahn = largestPathValueKahn(colors, edges);
		System.out.println("Kahn: The largest color value of any valid path is: " + largestColourValueKahn);

		int largestColourValueDFSBoolean = largestPathValueDFSBoolean(colors, edges);
		System.out
				.println("DFS Boolean: The largest color value of any valid path is: " + largestColourValueDFSBoolean);

		int largestColourValueDFSInt = largestPathValueDFSInt(colors, edges);
		System.out.println("DFS Int: The largest color value of any valid path is: " + largestColourValueDFSInt);
	}

	// Toplogical Sort: Kahn's Algo - BFS + DP
	// To find the max frequency of a color in a directed graph, we must iterate
	// over all of its paths in an optimal manner. If we know the max frequency of
	// all the colors for paths ending at u, we can use it to calculate the
	// frequency of all colors for paths that use the outgoing edges from u. If
	// there is an edge from u -> v, the path ending at v will have the same color
	// frequencies as the path ending at u, except that the color of v will be
	// incremented by 1. Now, if we do this for all the nodes that have an incoming
	// edge to v and take the max frequency of each across these edges, we will have
	// the max frequency of all the colors for paths ending at v. After covering all
	// the edges going into v, we can use the max frequency of all the colors stored
	// in v for edges going out of v. For edge u -> v, we must first obtain the max
	// frequency of all the colours for paths ending until u, and only then can we
	// form the answers for paths ending until v. This leaves us to consider using
	// topological sort to solve the problem. We also need to detect if a cycle is
	// in the graph and return -1 is so. The advantage of using Kahn's algorithm is
	// that it also aids in the detection of graph cycles.
	// To solve the problem, we use a 2D array count with n rows and 26 columns
	// where n is number of graph nodes. We have an array of size 26 for each node
	// to store the max frequency of each colour across the paths that end at the
	// node. count[x] keeps track of the maximum frequencies of the colors for paths
	// that end at node x. The colour a = column 0, b = column 1, ... We use Kahn's
	// algo to perform toplogical sort. A popped-out node means all its incoming
	// edges have been processed, and it can now be a neighbor edge, we use:
	// count[neighbor][i] = max(count[neighbor][i], count[node][i]) (we use max here
	// instead of directly setting the value as there could be multiple ways to
	// reach the neighbor) for all colors i. We also need to count the colour of
	// node. So, when node is popped(or pushed) out of the queue, we increase the
	// frequency of the colour of node by '1'. We update answer variable every time
	// when node is popped out of the queue by taking node's colour into account.
	// answer = max(answer, count[node][colors[node] - 'a']). We can update the
	// answer each time we update the frequencies of the colours using the outgoing
	// edges of nodes, but only the node colour is sufficient. This is because on
	// any path with the highest frequency of a particular colour, we can shorten it
	// by starting from the first node with that colour and keeping the same count
	// of the colour on this path. As a result, simply using the node itself
	// suffices for the answer computations.
	// We use a queue to start BFS moving from the leaf nodes(indegree = 0) to the
	// parent nodes.
	// Time complexity - O(n + 26*n + m + m*26), where n = number of nodes and m =
	// number of edges. Initializing adjList takes O(m) time as we go through all
	// the edges. The indegree array take O(n) time and the count array takes
	// O(26*n) time. Each queue operation takes O(1) time, and a single node will be
	// pushed once, leading to O(n) operations for n nodes. We iterate over the
	// neighbor of each node that is popped out of the queue iterating over all the
	// edges once. Since there are m edges at most and while iterating over each
	// edge we try to update the frequencies of all the 26 colors, it'd take O(26*m)
	// time.
	// Space complexity - O(n + m + n*26), the adjList takes O(m) space. The count
	// array takes O(26*n) space. The queue can have no more than n elements in the
	// worst-case scenario. It'd take up to O(n) space in that case.
	public static int largestPathValueKahn(String colors, int[][] edges) {
		char[] colorArr = colors.toCharArray();
		int n = colorArr.length;

		int[] indegree = new int[n];
		List<Integer>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			indegree[edge[1]]++;
			adjList[edge[0]].add(edge[1]);
		}

		Queue<Integer> queue = new LinkedList<>();

		for (int i = 0; i < n; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}

		int[][] count = new int[n][26];

		int answer = 1, nodeVisited = 0;

		while (!queue.isEmpty()) {
			int node = queue.poll();

			// We increment count[node][colorArr[node] - 'a'] by 1 to include the current
			// node's colour.
			answer = Math.max(answer, ++count[node][colorArr[node] - 'a']);

			nodeVisited++;

			if (adjList[node] == null) {
				continue;
			}

			for (int neighbor : adjList[node]) {
				// We update the frequency of colors for the neighbor to include the paths that
				// use node -> neighbor edge.
				for (int i = 0; i < 26; i++) {
					count[neighbor][i] = Math.max(count[neighbor][i], count[node][i]);
				}

				indegree[neighbor]--;

				if (indegree[neighbor] == 0) {
					queue.offer(neighbor);
				}
			}
		}

		if (nodeVisited != n) {
			return -1;
		}

		return answer;
	}

	static int answer;

	// Toplogical sort: DFS + Memoization (boolean)
	// In the Kahn's Algo approach, we obtained the max frequencies of the colours
	// for the paths ending at u first and then moved to v for an edge u -> v. We
	// can also go in the opposite direction. We can use the max frequencies of
	// colours across all paths that begin with v to for the max frequencies of
	// colours for paths that begin with u. We can update the frequency of colours
	// similar to the previous approach.
	// Using DFS, we can also detect a cycle. To detect a cycle, we must keep track
	// of the vertices that are currently in the function's recursion stack for DFS
	// traversal. If a vertex is reached that is already in the recursion stack then
	// there is a cycle in the graph.
	// To solve the problem, we use a 2D-array count with n rows and 26 columns
	// where count[x] keeps track of the maximum frequencies of all the colors among
	// all the paths that begin at node x. Note, the definition of count is opposite
	// hre. We use boolean array inStack of size n which keeps track of nodes that
	// are currently in the ongoing DFS stack. This helps to detect cycle in the
	// graph.
	// Time complexity - O(n + 26*n + m + m*26), where n = number of nodes and m =
	// number of edges. Initializing adjList takes O(m) time as we go through all
	// the edges. The count array takes O(26*n) time.
	// The dfs function visits each node once, which takes O(n) time in total.
	// Since, there are m edges at most and while iterating over each edge we try to
	// update the frequencies of all the 26 colors, it would take O(26*m) time.
	// Space complexity - O(2*n + m + n + n*26), the adjList takes O(m) space. The
	// count array takes O(26*n) space. The recursion call stack used by dfs can
	// have no more than n elements in the worst-case scenario. It'd take up to O(n)
	// space in that case.
	private static int largestPathValueDFSBoolean(String colors, int[][] edges) {
		char[] colorsArr = colors.toCharArray();

		int n = colorsArr.length;

		List<Integer>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			adjList[edge[0]].add(edge[1]);
		}

		boolean[] inStack = new boolean[n];
		boolean[] visited = new boolean[n];

		int[][] count = new int[n][26];

		answer = 0;
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				if (dfs(i, inStack, visited, adjList, count, colorsArr)) {
					return -1;
				}
			}
		}
		return answer;
	}

	private static boolean dfs(int node, boolean[] inStack, boolean[] visited, List<Integer>[] adjList, int[][] count,
			char[] colorsArr) {
		// If the node is already in stack, there is a cycle.
		if (inStack[node]) {
			return true;
		}

		if (visited[node]) {
			return false;
		}

		// Mark the current node as visited and part of current recursion stack.
		visited[node] = true;
		inStack[node] = true;

		if (adjList[node] != null) {
			for (int neighbor : adjList[node]) {
				if (dfs(neighbor, inStack, visited, adjList, count, colorsArr)) {
					return true;
				}

				for (int i = 0; i < 26; i++) {
					count[node][i] = Math.max(count[node][i], count[neighbor][i]);
				}
			}
		}

		// After all the outgoing edges of the node are processed, we count the color on
		// the node itself.
		answer = Math.max(answer, ++count[node][colorsArr[node] - 'a']);
		// Remove the node from the stack.
		inStack[node] = false;
		return false;
	}

	// Toplogical sort: DFS + Memoization (int)
	private static int largestPathValueDFSInt(String colors, int[][] edges) {
		char[] colorsArr = colors.toCharArray();

		int n = colorsArr.length;

		List<Integer>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			adjList[edge[0]].add(edge[1]);
		}

		boolean[] visited = new boolean[n];
		boolean[] inStack = new boolean[n];

		int[][] count = new int[n][26];

		int answer = 0;

		for (int i = 0; i < n; i++) {
			answer = Math.max(answer, dfs(i, inStack, visited, count, colorsArr, adjList));
		}
		return answer == Integer.MAX_VALUE ? -1 : answer;
	}

	// dfs method returns the max frequncy of the color of node that we can get
	// across all the paths starting from node. We return Infinity if there is a
	// cycle, we we detect by checking inStack.
	private static int dfs(int node, boolean[] inStack, boolean[] visited, int[][] count, char[] colorsArr,
			List<Integer>[] adjList) {
		// If the node is already in stack, there is a cycle.
		if (inStack[node]) {
			return Integer.MAX_VALUE;
		}
		// If the node is already visited, we return the frequency of node's color,
		// i.e., count[node][colors[node]-'a'].
		if (visited[node]) {
			return count[node][colorsArr[node] - 'a'];
		}

		// Mark the current node as visited and part of current recursion stack.
		visited[node] = true;
		inStack[node] = true;

		if (adjList[node] != null) {
			// We iterate over all the outgoing edges of node and for each neighbor, we
			// recursively call the dfs method.
			for (int neighbor : adjList[node]) {
				// If we get a cycle from neighbor, we return Infinity.
				if (dfs(neighbor, inStack, visited, count, colorsArr, adjList) == Integer.MAX_VALUE) {
					return Integer.MAX_VALUE;
				}

				// We update the frequencies of all colors stored for node by including the
				// paths that use the node -> neighbor edge.
				for (int i = 0; i < 26; i++) {
					count[node][i] = Math.max(count[node][i], count[neighbor][i]);
				}
			}
		}

		// After all the outgoing edges of the node are processed, we count the color on
		// the node itself. It's done by incrementing the frequency of node's color by 1
		// to count the node itself. In other words, all descendants reachable through
		// outgoing edges for the node have been processed. Now include the current
		// node's own color in the DP state.
		count[node][colorsArr[node] - 'a']++;
		// Remove the node from the stack.
		inStack[node] = false;
		return count[node][colorsArr[node] - 'a'];
	}
}
