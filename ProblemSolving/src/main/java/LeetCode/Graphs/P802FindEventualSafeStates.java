package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * P802. Find Eventual Safe States - Medium
 * 
 * There is a directed graph of n nodes with each node labeled from 
 * 0 to n - 1. The graph is represented by a 0-indexed 2D integer 
 * array graph where graph[i] is an integer array of nodes adjacent 
 * to node i, meaning there is an edge from node i to each node in graph[i].
 * 
 * A node is a terminal node if there are no outgoing edges. 
 * A node is a safe node if every possible path starting from 
 * that node leads to a terminal node (or another safe node).
 * 
 * Return an array containing all the safe nodes of the 
 * graph. The answer should be sorted in ascending order.
 * 
 * Approach - Kahn's Algorithm, DFS
 * 
 * A topological sort or topological ordering of a directed graph is a linear 
 * ordering of its vertices such that for every directed edge u->v, from vertex
 * u to vertex v, u comes before v in the ordering.
 * 
 * In a directed acyclic graph, we can use Kahn's algorithm to get the topological ordering.
 * Kahn's algo works by keeping track of the number of incoming edges into each node(indegree).
 * It works by repeatedly visiting the nodes with an indegree of 0 and deleting all the edges
 * associated with it leading to a decrement of indegree for the nodes whose incoming edges are
 * deleted. This process continues until no elements with 0 indegree can be found.
 * 
 * The advantage of using Kahn's technique is that it also aids in the discovery of graph cycles.
 */
public class P802FindEventualSafeStates {

	public static void main(String[] args) {
		int[][] graph = { { 1, 2 }, { 2, 3 }, { 5 }, { 0 }, { 5 }, {}, {} };
//		int[][] graph = { { 1, 2, 3, 4 }, { 1, 2 }, { 3, 4 }, { 0, 4 }, {} };

		List<Integer> safeNodesdfs = eventualSafeNodesDfs(graph);
		System.out.println("DFS: The safe nodes are: " + safeNodesdfs);

		List<Integer> safeNodesKahn = eventualSafeNodesKahn(graph);
		System.out.println("Kahn: The safe nodes are: " + safeNodesKahn);
	}

	// DFS
	// We use a recursive function to explore nodes as far as possible along each
	// branch. Upon reaching the end, we backtrack to previous node and continue
	// exploring the next branches. Once we encounter an unvisited node, we will
	// take one of neighbor nodes(if exists) as next on this branch. Recursively
	// call the function to take the next node as starting node and solve the
	// subproblem. A node remains in the dfs recursion stack until all of the nodes
	// its subtree(branches) have not been explored. When we've visited all of the
	// nodes in its subtree, the node is removed from the DFS recursive stack. To
	// find the unsafe nodes, we must 1st recognize a cycle. If we find a cycle,
	// we'll mark all of the nodes in the cycle as unsafe and then go back and mark
	// all of the nodes that led to this cycle as unsafe. To find cycle, we must
	// find the back edge connecting a node to one of its ancestors while traversing
	// nodes in the DFS manner. To find whether or not a node's negihbor is an
	// ancestor: If the neighborinh node has not yet been visited, it can't be an
	// ancestor(it's child). If neighboring node is visited, it may or may not be an
	// ancestor. If the neighboring node is an ancestor or there is a back edge, it
	// means that we visited this ancestor node 1st in DFS traversal, then visited
	// and explored some other nodes, and eventually visited a node that connects
	// back to the ancestor node. As we're still exploring the ancestor node's
	// subtree while iterating over this path, so this node must be in current DFS
	// recursive stack. However, if the neighboring node is visited but not in
	// recursion stack, it means we've previously explored that node in a different
	// branch, and it doesn't form a cycle in the current branch. So, to detect the
	// cyle we keep track of visited nodes(normal DFS) and also nodes in the
	// function's recursion call stack for DFS. The nodes in the stack stores the
	// current path. There is a cycle in the graph if a node is reached that is
	// already in the recursion stack. We use boolean array inStack of length n to
	// track whcih nodes are in call stack to check if a node exists in O(1). This
	// inStack array emulates call stack of computer to execute recursion. We mark
	// an unvisited node in inStack when we make a recursive call to it and unmark
	// it when we return from that call. After identifying the cycle, we check for
	// unsafe nodes. When we get a cycle, all of the nodes in the recursion stack
	// either form or lead to a cycle. If we start a DFS traversal from node 1 in a
	// graph 1->2->3->4->2, nodes 2,3,4 forms cycle. Here node 1 was also in stack,
	// so all the nodes in the recursion stack are unsafe as the form or lead to a
	// loop. inStack array is used to detect cycles as well as to store the unsafe
	// nodes. We don't unmark any of unsafe ndoes from inStack to keep track of
	// them. When any node has an outgoing edge to any of the unsafe nodes, we can
	// immediately return DFS call for node without unmarking it from inStack. This
	// is because if any neighbor of node is marked inStack, it signifies that
	// either neighbor and node are part of a cycle or neighbor is a previously
	// detected unsafe node. In both the cases, node is unsafe and we return the DFS
	// call without unmarking node from inStack. We only unmark a node from inStack,
	// if we've explored all of its branches and no branch leads to an unsafe node.
	// Algorithm: We use the input graph as adjacency list adj. We use boolean
	// arrays visit and inStack. The visit array keeps track of visited nodes and
	// inStack keeps track of nodes that ar currently in ongoing DFS - helps
	// detecing cycle and unsafe nodes. FOr each node we begin a DFS traversal and
	// it returns a boolean indicating whether node is unsafe. We perform following:
	// If node is already present in inStack, either we jyst got a cycle or a
	// previoulsy detected unsafe node. We return true as the node is unsafe.
	// If node is already visited(but not in inStack), we return false as we already
	// vistied this node and didn;t find it as unsage node and it's safe. We mark
	// node as visited and also in inStack. We iterate over the outgoing edges of
	// node and for each neighbor, we recursively call dfs. If we get a cycle from
	// neighbor(or if it's previouslt detected unsafe node), return true without
	// unmarking node in inStack. After processing all the outgoing edges of node,
	// we mark inStack[node] = false to mark node as safe. We return false. At end
	// of dfs for all nodes we create answer safeNodes and add all the odes with
	// inStack[node] = false.
	// Time complexity - O(m+n), where n = number of nodes and m = number of edges
	// in the graph. Array visited and inStack takes O(n). The dfs function handles
	// each node once - O(n) time. From each node, we iterate all the outgoing edges
	// which further takes O(m) time to iterate all the edges as there are total m
	// edges. Iterating over all the ndoes to push safe nodes into safeNodes - O(n).
	// Space complexity - O(n), the visit and inStack arrays takes O(n). The
	// recursion call stack used by dfs can have no more than n elements in worst
	// case.
	private static List<Integer> eventualSafeNodesDfs(int[][] graph) {
		int n = graph.length;
		boolean[] visited = new boolean[n];
		boolean[] inStack = new boolean[n];

		for (int i = 0; i < n; i++) {
			dfs(i, graph, visited, inStack);
		}

		List<Integer> safeNodes = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (!inStack[i]) {
				safeNodes.add(i);
			}
		}
		return safeNodes;
	}

	private static boolean dfs(int node, int[][] adjList, boolean[] visited, boolean[] inStack) {
		if (inStack[node]) {
			return true;
		}
		if (visited[node]) {
			return false;
		}
		visited[node] = true;
		inStack[node] = true;
		for (int neighbor : adjList[node]) {
			if (dfs(neighbor, adjList, visited, inStack)) {
				return true;
			}
		}
		inStack[node] = false;
		return false;
	}

	// Topological sort using Kahn's Algorithm
	// We consider when a node is safe/unsafe. If we begin at any node and proceed
	// along any path from that node, we will eventually reach either a terminal
	// node or enter a cycle and continue to loop in it(break it with boolean flag).
	// If there is no path from the node that enters a cycle, we will always reach a
	// terminal node. This node is safe and added to answer. Thus, we need to find
	// the nodes that do not have any paths that lead to a cycle. A node is safe if
	// all of its outgoing edges are to nodes that are also safe(none lead to
	// cycle). Since, the terminal nodes are safe, so nodes that solely have
	// outgoing edges to terminal nodes are also safe. Then, we may check the nodes
	// that have just outgoing nodes to safe nodes again and keep updating until no
	// new safe node is discovered. For this, we traverse from terminal nodes to
	// nodes that only have outgoing edges to terminal nodes by reversing the edges
	// of the graph to create a new graph with reversed edges. After visiting all
	// the terminal nodes, we use new graph to go to nodes that have edges to
	// terminal nodes in original graph by using reverse edges. In new graph, a node
	// is a safe node if all of its incoming edges come from previously identified
	// safe nodes in the graph. If we erase the edges outgoing from the safe node
	// and discover a node with no incoming edges, it's a new safe node. This hints
	// towards Kahn's method, which does topological sort by removing edges.
	// The advantage of using Kahn's technique is that helps to find cycles. The
	// method does not visit any node in a cycle. So, nodes with outgoing edges from
	// nodes in the cycle(in reversed graph) will never be visited and won't be
	// marked safe. Nodes with outgoing edges from these unsafe nodes will never be
	// visited as well, and so on. Every node in the original network that has a
	// path to the cycle will never be visited by Kahn's algorithm. So, if there is
	// a cycle, the indegree of nodes in the cycle cannot be set to 0 due to cyclic
	// dependency. We're unable to visit the cycle's nodes and also unable to visit
	// any node with an incoming edge from any node in the cycle. Also, the nodes
	// with incoming edges from these nodes won't be visited.
	// Algo: Create n = graph's length to get number of nodes. Create indegree array
	// of length n, where indegree[x] stores the number of edges entering node x.
	// This is for reversed edges. We create adjacency list in which adj[x] contains
	// all nodes with incoming reverse edges from node x(neighbors of x). For a node
	// i which originally has outgoing edges to nodes in graph[i], we push i into
	// adj[node] to add a reverse edge from node to i. We use queue of integers to
	// start a BFS moving from leaf to parent nodes where leaf nodes are nodes with
	// indegree = 0. Use boolean array: safe of size = n to track the safe nodes in
	// the graph. While the queue is not empty: Dequeue the 1st node from queue and
	// mark the node as safe. For each neighbor(nodes that have an incoming edge
	// from node in adj) of node, we decrement indegreepneighbor] by 1 to delete the
	// node -> neighbor edge. If indegree[neighbor[ == 0, it means that neighbor is
	// leaf node so we push it to queue. At end of this while loop we use safeNodes
	// to add all the nodes which are marked safe.
	// Time complexity - O(m+n), where n = number of nodes and m = number of edges
	// in the graph. we initialize the adj list and indegree takes O(m) time as we
	// go through all the edges. Safe array takes O(n) time. Each queue operation
	// takes O(1) time and for n nodes it's O(n). We iterate over the neighbors of
	// each node that is popped out of the queue iterating over all the edges once.
	// We iterate over neighbors of each node that is popped out of queue to iterate
	// all the edges once. For total edges it's O(m). Iterating over all the nodes
	// and pushing only safe nodes into safeNodes also takes O(n) time.
	// Space complexity - O(m + n), the adj takes O(m) space. The indegree, queue(in
	// worst case) and safe array takes O(n) space.
	public static List<Integer> eventualSafeNodesKahn(int[][] graph) {
		int n = graph.length;
		List<Integer> safeNodes = new ArrayList<>();
		int[] indegree = new int[n];
		List<List<Integer>> adjList = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			adjList.add(new ArrayList<>());
		}
		for (int i = 0; i < n; i++) {
			for (int node : graph[i]) {
				indegree[i]++;
				adjList.get(node).add(i);
			}
		}
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			if (indegree[i] == 0) {
				queue.add(i);
			}
		}
		boolean[] safe = new boolean[n];
		while (!queue.isEmpty()) {
			int node = queue.poll();
			safe[node] = true;
			for (int neighbor : adjList.get(node)) {
				indegree[neighbor]--;
				if (indegree[neighbor] == 0) {
					queue.add(neighbor);
				}
			}
		}
		for (int i = 0; i < n; i++) {
			if (safe[i]) { // if (indegree[i] == 0) also works
				safeNodes.add(i);
			}
		}
		return safeNodes;
	}

}
