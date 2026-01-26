package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * P1129. Shortest Path with Alternating Colors - Medium
 * 
 * You are given an integer n, the number of nodes in a directed graph 
 * where the nodes are labeled from 0 to n - 1. Each edge is red or 
 * blue in this graph, and there could be self-edges and parallel edges.
 * 
 * You are given two arrays redEdges and blueEdges where:
 * > redEdges[i] = [ai, bi] indicates that there is a directed red edge from node ai to node bi in the graph, and
 * > blueEdges[j] = [uj, vj] indicates that there is a directed blue edge from node uj to node vj in the graph.
 * 
 * Return an array answer of length n, where each answer[x] is the 
 * length of the shortest path from node 0 to node x such that the edge 
 * colors alternate along the path, or -1 if such a path does not exist.
 * 
 * Approach - BFS
 */
public class P1129ShortestPathAlternatingColors {

	public static void main(String[] args) {
		int n = 3;
		int[][] redEdges = { { 0, 1 }, { 1, 2 } };
		int[][] blueEdges = {};

//		int n = 3;
//		int[][] redEdges = { { 0, 1 } };
//		int[][] blueEdges = { { 2, 1 } };

//		int n = 3;
//		int[][] redEdges = { { 0, 1 } };
//		int[][] blueEdges = { { 1, 2 } };

		int[] shortestPaths = shortestAlternatingPaths(n, redEdges, blueEdges);
		System.out.println("The shortest path with alternating paths are: " + Arrays.toString(shortestPaths));
	}

	// BFS
	// A breadth-first search algo can be used to find the shortest path in an
	// unweighted graph. For BFS, the 1st time a node is reached during the
	// traversal, it was reached in minimum possible steps from the source. The path
	// used in BFS traversal always has the least number of edges. The BFS algorithm
	// does a level-wise iteration which finds all paths that 1 edge away from
	// source node, followed by all paths that are 2 edges away from the source
	// node, and so on. This allows BFS to find the shortest path from source to
	// other nodes. Here BFS algo can be used with some modifications. The problem
	// has a constraint that restricts the traversal to using only alternate color
	// edges along the path. So, during BFS, we must keep track of the color with
	// which we visited the current node to the neighboring nodes with alternate
	// colour edges. Unlike normal BFS, here a node can be visited at most twice
	// (visited[n][2]). It can be visited once from red edge and once from blue
	// edge. We get the minimum number of steps required to visit a node the 1st
	// time we visit it. We may also need to return to the same node using a
	// different colour edge than the 1 used on the 1st visit. We then process to
	// visit other neighbors which may not be covered during 1st visit due to colour
	// constraints. We need to keep track of how many steps it takes to get to each
	// node. So, the BFS queue is an integer triplet: 1st value = node index, the
	// 2nd value = number of steps used and 3rd value = colour of edge through which
	// the current node was reached. We use the queue for BFS traversal from current
	// node to neighboring nodes while following the colour constraints.
	// Algorithm: Create adjacency list adj that contains a list of pairs of
	// integers, adj[node] contains neighbors of node (neighbor, color) where colour
	// is edge colour that connects node and neighbor. Colour for red: 0, blue: 1.
	// Create a distance array with all value as -1. Create a visited[node][colour]
	// array, which indicates a node has been visited using an edge of colour. We
	// set visit[0][0], visit[0][1] as true as we don't need to visit node 0 again.
	// Create a queue of triplets which stores: a) current node's index, b) steps
	// taken to visit the node, and c) colour of previous edge used. The node 0 has
	// 0 steps and no specific color of visit so use -1 as colour. Now, while the
	// queue is not empty: We remove the 1st element out if the queue to get [node,
	// steps, prevColour]. Loop through all (neighbor, colour) pairs in adj[node].
	// If a neighbor has not yet visited with a colour edge and colour !=
	// prevColour, we visit neighbor with the colour edge by pushing [neighbor,
	// steps+1, colour[ in the queue. If this neighbor's 1st visit or
	// distance[neighbor] == -1, we set answer[neighbor] = steps + 1.
	// Time complexity - O(n+e), the complexity is similar to BFS(iterate each node
	// at most twice). Each queue operation in BFS algo takes O(1) time, and a
	// single node can be pushed onto queue at most twice so O(n). We iterate over
	// all the neighbors of each node that is popped out of hte queue. For an
	// undirected edge, a given edge could be iterated at most twice, resulting in
	// O(e) operations total for all the nodes.
	// Space complexity - O(n+e), building adj takes O(e) time. The BFS queue takes
	// O(n) time as each vertex is added at most twice in form of triplet of
	// integers. The visit array takes O(n) space.
	public static int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
		int[] distance = new int[n];
		Arrays.fill(distance, -1);
		Map<Integer, List<List<Integer>>> adj = new HashMap<>();
		for (int[] redEdge : redEdges) {
			adj.computeIfAbsent(redEdge[0], k -> new ArrayList<>()).add(Arrays.asList(redEdge[1], 0));
		}
		for (int[] blueEdge : blueEdges) {
			adj.computeIfAbsent(blueEdge[0], k -> new ArrayList<>()).add(Arrays.asList(blueEdge[1], 1));
		}

		Queue<int[]> queue = new LinkedList<>();
		// Start with node 0, with number of steps as 0 and undefined colour -1.
		queue.add(new int[] { 0, 0, -1 });

		// Check for a node from a particular colour edge has been visited
		// Red has colour 0 and blue has colour 1.
		boolean[][] visited = new boolean[n][2];
		visited[0][0] = true;
		visited[0][1] = true;
		distance[0] = 0;

		while (!queue.isEmpty()) {
			int[] node = queue.poll();
			int index = node[0];
			int steps = node[1];
			int prevColour = node[2];

			if (!adj.containsKey(index)) {
				continue;
			}

			for (List<Integer> currNode : adj.get(index)) {
				int currNodeIndex = currNode.get(0);
				int colour = currNode.get(1);

				if (!visited[currNodeIndex][colour] && colour != prevColour) {
					queue.add(new int[] { currNodeIndex, steps + 1, colour });
					visited[currNodeIndex][colour] = true;
					if (distance[currNodeIndex] == -1) {
						distance[currNodeIndex] = steps + 1;
					}
				}
			}
		}
		return distance;
	}

}
