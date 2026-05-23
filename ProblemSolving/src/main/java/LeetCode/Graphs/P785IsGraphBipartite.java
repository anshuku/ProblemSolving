package LeetCode.Graphs;

import java.util.Arrays;
import java.util.Stack;

/*
 * P785. Is Graph Bipartite? - Medium
 * 
 * There is an undirected graph with n nodes, where each node is numbered between 
 * 0 and n - 1. You are given a 2D array graph, where graph[u] is an array of nodes 
 * that node u is adjacent to. More formally, for each v in graph[u], there is 
 * an undirected edge between node u and node v. The graph has the following properties:
 * 
 * > There are no self-edges (graph[u] does not contain u).
 * > There are no parallel edges (graph[u] does not contain duplicate values).
 * > If v is in graph[u], then u is in graph[v] (the graph is undirected).
 * > The graph may not be connected, meaning there may be two 
 * nodes u and v such that there is no path between them.
 * 
 * A graph is bipartite if the nodes can be partitioned into two independent sets 
 * A and B such that every edge in the graph connects a node in set A and a node in set B.
 * 
 * Return true if and only if it is bipartite.
 * 
 * graph.length == n
 * 
 * Approach - Graph colouring: DFS, Stack
 */
public class P785IsGraphBipartite {

	public static void main(String[] args) {
		int[][] graph = { { 1, 2, 3 }, { 0, 2 }, { 0, 1, 3 }, { 0, 2 } };
//		int[][] graph = { { 1, 3 }, { 0, 2 }, { 1, 3 }, { 0, 2 } };

		boolean isBipartiteDfs = isBipartiteDfs(graph);
		System.out.println("DFS: The given undirected graph is bipartite: " + isBipartiteDfs);

		boolean isBipartiteStack = isBipartiteStack(graph);
		System.out.println("Stack: The given undirected graph is bipartite: " + isBipartiteStack);
	}

	// DFS Recursive: Graph colouring
	// A graph is bipartite if we can split its nodes into 2 groups such that no two
	// adjacent nodes belong to the same group. This is equivalent to being able to
	// colour the graph using only 2 colours so that every edge connects differently
	// coloured nodes. The key idea is: We try to colour the graph via dfs and If at
	// any point a neighbor already has the same colour -> not bipartite. Algo:
	// We use DFS colouring, we maintain a colours array, where -1 = unvisited, 0
	// and 1 = 2 colours. For each unvisited node: start a DFS and assign a colour
	// 0. Assign opposite colour to neighbors. If conflict: During DFS: If a
	// neighbor is uncoloured -> colour it opposite and continue. If a neihgbor
	// already has the same colour -> return false. To handle disconnected graph, we
	// loop over all nodes as graph can have multiple components.
	// Time complexity - O(V+E), every node and edge is visited once during DFS.
	// Space complexity - O(V), for recursion stack(in worst case) and colours
	// array.
	private static boolean isBipartiteDfs(int[][] graph) {
		int n = graph.length;
		int[] colours = new int[n];
		Arrays.fill(colours, -1);

		for (int i = 0; i < n; i++) {
			if (colours[i] == -1 && !dfs(graph, colours, i, 0)) {
				return false;
			}
		}

		return true;
	}

	private static boolean dfs(int[][] graph, int[] colours, int node, int colour) {
		colours[node] = colour;

		for (int neighbor : graph[node]) {
			if (colours[neighbor] == -1) {
				if (!dfs(graph, colours, neighbor, colour ^ 1)) {
					return false;
				}
			} else if (colours[neighbor] == colours[node]) {
				return false;
			}
		}
		return true;
	}

	// Stack: Graph colouring
	// We colour the node blue if it's part of first set, else red. We should be
	// able to greedily colour the graph if and only if it's bipartite: one node
	// being blue implies all it's neighbors are red, all those neighbors are blue,
	// and so on. Algo: We'll use an array to lookup the colour of each node. The
	// colours could be 0, 1, or uncoloured(-1 or null for map). Since the graph may
	// not be connected, we must search each node. For each uncoloured node, we'll
	// start the colouring process by doing a dfs on that node. Every neighbor gets
	// coloured the opposite colour from the current node. If we find a neighbor
	// coloured the same colour as the current node, the our colouring was
	// impossible and the graph is not bipartite. For DFS, we can use a stack. For
	// each uncoloured neighbor in graph[node], we'll colour it and add it to our
	// stack, which helps to visit the nodes next. We loop through each node to
	// colour every node.
	// Time complexity - O(n + e), where n = nodes and e = edges. We explore each
	// node once when we colour it from uncoloured, exploring each of it's edges
	// Space complexity - O(n), for colours array and stack space.
	public static boolean isBipartiteStack(int[][] graph) {
		int n = graph.length;
		int[] colour = new int[n];
		Arrays.fill(colour, -1);

		for (int i = 0; i < n; i++) {
			if (colour[i] == -1) {
				Stack<Integer> stack = new Stack<>();
				stack.push(i);

				colour[i] = 0;

				while (!stack.isEmpty()) {
					int node = stack.pop();

					for (int neighbor : graph[node]) {
						if (colour[neighbor] == -1) {
							colour[neighbor] = colour[node] ^ 1; // flips the current colour
							stack.push(neighbor);
						} else if (colour[neighbor] == colour[node]) {
							return false;
						}
					}
				}
			}

		}
		return true;
	}
}
