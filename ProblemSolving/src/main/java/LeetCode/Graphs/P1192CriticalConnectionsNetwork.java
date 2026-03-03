package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

/*
 * P1192. Critical Connections in a Network - Hard
 * 
 * There are n servers numbered from 0 to n - 1 connected by undirected 
 * server-to-server connections forming a network where connections[i] = [ai, bi] 
 * represents a connection between servers ai and bi. Any server can 
 * reach other servers directly or indirectly through the network.
 * 
 * A critical connection is a connection that, if removed, 
 * will make some servers unable to reach some other server.
 * 
 * Return all critical connections in the network in any order.
 * 
 * Approach - Tarjan's Algorithm: DFS
 */
public class P1192CriticalConnectionsNetwork {

	public static void main(String[] args) {
		int n = 4;
		List<List<Integer>> connections = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(1, 2), Arrays.asList(2, 0),
				Arrays.asList(1, 3));
//		int n = 2;
//		List<List<Integer>> connections = Arrays.asList(Arrays.asList(0, 1));

		List<List<Integer>> criticalConnectionsOpt = criticalConnectionsOpt(n, connections);
		System.out.println("Optimized: The critical connections in server connections are: " + criticalConnectionsOpt);

		List<List<Integer>> criticalConnections = criticalConnections(n, connections);
		System.out.println("The critical connections in server connections are: " + criticalConnections);

	}

	static Map<Integer, List<Integer>> graph;
	static Map<Pair<Integer, Integer>, Boolean> conn;
	static Map<Integer, Integer> rank; // Stores discovery time

	// Tarjan's Algorithm: Bridge finding - DFS for cycle detection
	// Here we're asked to find number of bridges in the undirected graph. Bridge
	// are edges, if removed can make the graph disconnected. In graph, a cut is a
	// partition of the vertices of a graph into 2 disjoint subsets. Any cut
	// determines a cut-set, the set of edges that've 1 endpoint in each subset of
	// the partition. We're not finding cuts in the graph, but bridges only.
	// Tarjan's algo/DFS can be used to find bridges in an undirected/directed
	// graph. It's a standard way of finding the articulation points and bridges in
	// a graph. The edges belonging to a cycle couldn't be a critical connection. As
	// removing any 1 of the edges doesn't change the connectivity between the nodes
	// in a cycle. An edge is a criticial connection, if and only if it's not in a
	// cycle. Edges that are not a part of the cycle form a single route from 1 part
	// of the graph to the other. If there were multiple ways, then our edge would
	// be a part of the cycle as this is an undirected graph. Thus, edges not
	// belonging to any cycle end up being a cricitical connection for the graph.
	// Here, we find all the cycles in the graph and discard all the edges belonging
	// to such cycles, and we'll be left with edges which are critical connections.
	// The problem is not simply about finding a cycle in an undirected graph where
	// DFS can be used. Here, we've an undirected graph, where a visited map/array
	// of nodes can be used. During DFS, if a node is already visited, it implies a
	// cycle. The algorithm is bit more involved for directed graphs due to presence
	// of different kinds of edges. We use rank of node which is similar to
	// discovery times in Tarjan's algorithm. This is a graph with no designated
	// concept of a root like trees, we can consider any node to be the root. We
	// need a node to start the traversal which becomes the root node. The rank of
	// root node is always 0. For unvisited nodes, we'll keep rank as null/None
	// (except -1). Rank helps to detect cyles, as it works like using visited
	// array. At each step of traversal, we maintain the rank of nodes we've come
	// across until now on the current path. If at any point, we come across a
	// neighbor that has a rank lower than the current node' rank, we know that the
	// neighbor must've already been visited and this implies presence of a cycle.
	// Rank importance: We detect a cycle simply by checking if a rank has already
	// been assigned to a neighbor or not(except for the parent, handled
	// separately). So when we detect a cycle, we discard the current edge since
	// that edge cannot be a critical connection. To discard remaining edges in
	// cycle, the mere presence of cycle in a subgraph, doesn't mean an ancestral
	// edge/node is also in cycle. We need minimum rank that a cycle includes. The
	// traversal function should return this info so that the previous callers can
	// use it to identify if an edge has to be discarded or not. We know that only
	// current level knows of cycle presence. To make upper levels(of recursion)
	// make aware of this cycle, and also to help them discard necessary edges, we
	// return the minimum rank that our traversal finds. During a step of recursion
	// from node u to its neighbor v, if DFS returns <= rank of u, then u knows its
	// neighbor v is a part of of cycle spanning back to u or some other node higher
	// up in the recursion tree i.e. an ancestor node. Thus, we can safely discard
	// edge u-v as it's part of a cycle.
	// Algo:
	// The dfs function takes node=0 and discoveryRank=0(for the node). We build the
	// graph using adjacency list(map). Since we discard edges, to do it efficiently
	// in O(1) we use connections map. We use a map to keep track of the rank of the
	// nodes. Inside dfs: a) We check if node already has a rank, if it's there we
	// return that. b) Else we assign the rank of this node i.e. rank[node] to the
	// discoveryRank. c) We iterate over all neighbors of node and for each we make
	// a recursive call to get the recursiveRank as the return value and do 2 things
	// using this value. i) If recursiveRank ≤ discoveryRank, this implies that the
	// edge is a part of a cycle and can be discarded. b) We record minimum rank
	// till now for all neighbors and name it minRank and we return this. We don't
	// make recursive call to parent node by checking if rank of the neighboring
	// node = discoveryRank - 1. At end of dfs we convert the remaining edges from
	// connection to a list.
	// Time complexity - O(V + E), V = vertices and E = edges of graph. We process
	// each node once via rank map which acts as a visited array. Since the graph is
	// connected, so E >= V and hence, time complexity is dominated by edges O(E).
	// Space complexity - O(E), The space complexity is due to conn, ranka and graph
	// map. E + V + (V + E) = O(E).
	public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
		graph = new HashMap<>();
		conn = new HashMap<>();
		rank = new HashMap<>();

		for (int i = 0; i < n; i++) {
			graph.put(i, new ArrayList<>());
		}

		for (int i = 0; i < connections.size(); i++) {
			int u = connections.get(i).get(0);
			int v = connections.get(i).get(1);

			graph.get(u).add(v);
			graph.get(v).add(u);

			// Store edges in sorted order to avoid duplicate direction issue.
			conn.put(Pair.of(Math.min(u, v), Math.max(u, v)), true);
		}

		dfs(0, 0);

		List<List<Integer>> criticalConnections = new ArrayList<>();
		// Remaining edges in conn are critical connections.
		for (Pair<Integer, Integer> pair : conn.keySet()) {
			criticalConnections.add(new ArrayList<>(Arrays.asList(pair.getKey(), pair.getValue())));
		}

		return criticalConnections;
	}

	// Return minimum reachable rank(low-link value)
	private static int dfs(int node, int discoveryRank) {
		// If node is already visited
		if (rank.containsKey(node)) {
			return rank.get(node);
		}

		rank.put(node, discoveryRank);

		int minRank = discoveryRank;
		for (int neighbor : graph.get(node)) {
			// Skip the parent node
			if (rank.containsKey(neighbor) && rank.get(neighbor) == discoveryRank - 1) {
				continue;
			}
			int recursiveRank = dfs(neighbor, discoveryRank + 1);

			// Remove edge from connections when it's not critical / part of cycle.
			if (recursiveRank <= discoveryRank) { // means there's a back edge and not a bridge, so remove it.
				conn.remove(Pair.of(Math.min(node, neighbor), Math.max(node, neighbor)));
			}

			minRank = Math.min(minRank, recursiveRank);
		}

		return minRank;
	}

	static List<Integer>[] adjList;
	static int[] rankArr;
	static Set<Pair<Integer, Integer>> connectionsSet;

	private static List<List<Integer>> criticalConnectionsOpt(int n, List<List<Integer>> connections) {
		adjList = new ArrayList[n];
		rankArr = new int[n];
		connectionsSet = new HashSet<>();

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int i = 0; i < connections.size(); i++) {
			int u = connections.get(i).get(0);
			int v = connections.get(i).get(1);

			adjList[u].add(v);
			adjList[v].add(u);

			connectionsSet.add(Pair.of(Math.min(u, v), Math.max(u, v)));
		}

		dfsOpt(0, 0);

		List<List<Integer>> criticalConnections = new ArrayList<>();

		for (Pair<Integer, Integer> pair : connectionsSet) {
			criticalConnections.add(new ArrayList<>(Arrays.asList(pair.getKey(), pair.getValue())));
		}

		return criticalConnections;
	}

	private static int dfsOpt(int node, int discoveryRank) {
		if (rankArr[node] != 0) {
			return rankArr[node];
		}

		rankArr[node] = discoveryRank;

		int minRank = discoveryRank;
		for (int neighbor : adjList[node]) {
			if (rankArr[neighbor] != 0 && rankArr[neighbor] == discoveryRank - 1) {
				continue;
			}
			int recursiveRank = dfsOpt(neighbor, discoveryRank + 1);

			if (recursiveRank <= discoveryRank) {
				connectionsSet.remove(Pair.of(Math.min(node, neighbor), Math.max(node, neighbor)));
			}

			minRank = Math.min(minRank, recursiveRank);
		}

		return minRank;

	}

}
