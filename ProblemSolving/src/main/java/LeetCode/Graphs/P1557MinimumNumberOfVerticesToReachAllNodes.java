package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * P1557. Minimum Number of Vertices to Reach All Nodes - Medium
 * 
 * Given a directed acyclic graph, with n vertices numbered from 0 to n-1, and an array 
 * edges where edges[i] = [fromi, toi] represents a directed edge from node from(i) to node to(i).
 * 
 * Find the smallest set of vertices from which all nodes in the 
 * graph are reachable. It's guaranteed that a unique solution exists.
 * 
 * Notice that you can return the vertices in any order.
 * 
 * Approach - Indegree
 */
public class P1557MinimumNumberOfVerticesToReachAllNodes {

	public static void main(String[] args) {
//		int n = 6;
//		List<List<Integer>> edges = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(0, 2), Arrays.asList(2, 5),
//				Arrays.asList(3, 4), Arrays.asList(4, 2));
		int n = 5;
		List<List<Integer>> edges = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(2, 1), Arrays.asList(3, 1),
				Arrays.asList(1, 4), Arrays.asList(2, 4));

		List<Integer> reachableVertices = findSmallestSetOfVertices(n, edges);
		System.out.println("The smallest set of vertices to reach all nodes: " + reachableVertices);
	}

	// Indegree
	// We find all the vertices that don't have any incoming edge.
	// Time complexity - O(n+e), where n = nodes, e = edges, we iterate over the
	// edges to find indegree. We then iterate over the vertices to find the
	// vertices which doesn't have any incoming edge.
	// Space complexity - O(n) for indegree array
	public static List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
		List<Integer> result = new ArrayList<>();

		boolean[] indegree = new boolean[n];

		for (List<Integer> edge : edges) {
			indegree[edge.get(1)] = true;
		}

		for (int i = 0; i < n; i++) {
			// Store all the nodes that doesn't have any incoming edge.
			if (!indegree[i]) {
				result.add(i);
			}
		}

		return result;
	}
}
