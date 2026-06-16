package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * P207. Course Schedule - Medium
 * 
 * There are a total of numCourses courses you have to take, labeled from 0 to 
 * numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] 
 * indicates that you must take course bi first if you want to take course ai.
 * 
 * > For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * 
 * Return true if you can finish all courses. Otherwise, return false.
 * 
 * Approach - Kahn's Topological sorting, DFS: inStack
 */
public class P207CourseSchedule {

	public static void main(String[] args) {
		int numCourses = 2;
		int[][] prerequisites = { { 1, 0 } };

//		int numCourses = 2;
//		int[][] prerequisites = {{1, 0}, {0, 1}};

//		int numCourses = 3;
//		int[][] prerequisites = {{1, 0}, {2, 1}};

		boolean canFinishDFS = canFinishDFS(numCourses, prerequisites);
		System.out.println("DFS: All the courses can be finised: " + canFinishDFS);

		boolean canFinishKahn = canFinishKahn(numCourses, prerequisites);
		System.out.println("Kahn: All the courses can be finised: " + canFinishKahn);

		boolean canFinishKahnAlt = canFinishKahnAlt(numCourses, prerequisites);
		System.out.println("Kahn Alt: All the courses can be finised: " + canFinishKahnAlt);
	}

	// DFS
	// We can also use a depth-first seach (DFS) traversal to detect a cycle in a
	// directed graph. A node remains in the DFS recursion stack until all of its
	// branches (all nodes in its subtree) have not been explored. When we've
	// examined all of a node's branches, i.e., visited all of the nodes in its
	// subtree, the node is removed from the DFS recursive stack.
	// If the graph has a cycle, we must have a back edge connecting a node to one
	// of its ancestors while traversing nodes in the DFS manner. To establish
	// whether or not a node's neighbor is an ancestor when navigating from one node
	// to another: If the neighboring node has not yet been visited, it cannot be an
	// ancestor (it is a child node). Otherwise, if a neighboring node is visited,
	// it may or may not be an ancestor. If the neighboring node is an ancestor,
	// i.e. there is a back edge, it means that we visited this ancestor node first
	// in the DFS traversal, then visited and explored some other nodes, and
	// eventually visited a node that connects back to the ancestor node. As we're
	// still exploring the ancestor node's subtree while iterating over this path,
	// hence this node must be in the current DFS recursive stack.
	// However, if a neighboring node is visited but not in the recursion stack, it
	// signifies we've previously explored that node in a different branch, and it
	// does not form a cycle in the current branch.
	// As a result, to detect the cycle we must keep track of the visited nodes
	// (like in a normal DFS) and also the nodes in the function's recursion call
	// stack for DFS traversal. The nodes in the stack store the current path that
	// we're on. There is a cycle in the graph if a node is reached that is already
	// in the recursion stack. We use a boolean array of length n to track which
	// nodes are in the call stack so we can check if a node exists in O(1).
	// The dfs function returns a boolean indicating whether there is a cycle in the
	// graph. If any dfs call returns true, we've cycle, in this case we return
	// false as the courses can't be finished.
	// Time compelxity - O(m + n), where n = number of courses and m = size of
	// prerequisites (edges). Initialzing adj takes O(m) time as we go through all
	// the edges. Initializng the visit and inStack arrays takes O(n) time each. The
	// dfs function handles each node once, which takes O(n) time in total. From
	// each node, we iterate over all the outgoing edges, which further takes O(m)
	// time to iterate over all the edges as there are a total of m edges.
	// Space complexity - O(m + n), the adj list takes O(m) space. The visit and
	// inStack arrays takes O(n) space each. The recursion call stack used by dfs
	// method can have no more than n elements in the worst case, it'd take up O(n)
	// space in that case.
	private static boolean canFinishDFS(int numCourses, int[][] prerequisites) {
		List<Integer>[] adjList = new ArrayList[numCourses];

		for (int i = 0; i < numCourses; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] prerequisite : prerequisites) {
			adjList[prerequisite[1]].add(prerequisite[0]);
		}

		boolean[] inStack = new boolean[numCourses];
		boolean[] visited = new boolean[numCourses];

		for (int i = 0; i < numCourses; i++) {
			if (!visited[i]) { // visited check is optional as we check it in dfs.
				if (dfs(i, visited, inStack, adjList)) {
					// If any dfs call returns true, we've cycle, in this case we return false.
					return false;
				}
			}
		}
		return true;
	}

	// The dfs function returns a boolean indicating whether there is a cycle in the
	// graph.
	private static boolean dfs(int node, boolean[] visited, boolean[] inStack, List<Integer>[] adjList) {
		// If the node is already in the stack, we've a cycle / back edge.
		if (inStack[node]) {
			return true;
		}
		// If the node is already visited, we return false as we already visited this
		// node and didn't find a cycle earlier.
		if (visited[node]) {
			return false;
		}

		// Mark the current node as visited and part of current recursion stack.
		inStack[node] = true;
		visited[node] = true;

		for (int neighbor : adjList[node]) {
			if (dfs(neighbor, visited, inStack, adjList)) {
				return true;
			}
		}

		// Remove the node from the stack.
		inStack[node] = false;
		// No cycle exists
		return false;
	}

	// Topological sorting: Kahn's algorithm
	// We can see that we have been given certain courses with some dependencies
	// (expressed as pairs) between them, which hints to frame the problem as graph.
	// If we regard each course as a node and draw an edge from bi to ai for any
	// prerequisite [ai, bi] (to indicate that course bi must be completed before
	// course ai), we get a directed graph. If there a cycle in this directed graph,
	// it suggests that we will not be able to finish all of the courses. Otherwise,
	// we can perform a toplogical sort of the graph to determine the order in which
	// all of the courses can be finished. The problem is reduced to finding whether
	// a cycle occurs in a graph. If there is a cycle, we must return false. If not,
	// we return true. We use Kahn's algo which aids in the detection of graph
	// cycles. We start with nodes having 0 indegree and delete outgoing edges from
	// them. We see that if there is a cycle, the indegree of nodes in the cycle
	// cannot be set to 0 due to cyclic dependency. We're unable to visit the
	// cycle's nodes. So, if the number of visited nodes is less than the total
	// number of nodes in the graph, we have a cycle. We use an indegree array,
	// where indegree[x] stores the number of edges entering node x. We create an
	// adjacency list adj in which adj[x] contains all the nodes with an incoming
	// edge from node x, i.e., neighbors of node x. We us a queue of integers to
	// start BFS algo moving from the leaf nodes to the parent nodes. We push all
	// the leaf nodes (indegree = 0) in the queue. For each neighbor (nodes that
	// have an incoming edge from node) of node, we decrement indegree[neighbor] by
	// 1 to delete the node -> neighbor edge.
	// Time complexity - O(m + n), where n = number of courses and m = size of
	// prerequisites/edges. Initializing the adj list takes O(m) time as we go
	// throguh all the edges. The indegree array take O(n) time. Each queue
	// operation takes O(1) time, and a single node will be pushed once, leading to
	// O(n) operations for n nodes. We iterate over the neighbors of each node that
	// is popped out of the queue iterating over all the edges once. Since, there
	// are total of m edges, it'd take O(m) time to iterate over the edges.
	// Space complexity - O(m + n), the adj list takes O(m) space. The indegree
	// array takes o(n) space. The queue can have no more than n elements in the
	// worst-case scenario. It'd take up O(n) space in that case.
	private static boolean canFinishKahn(int numCourses, int[][] prerequisites) {
		int[] indegree = new int[numCourses];

		List<Integer>[] adjList = new ArrayList[numCourses];

		for (int i = 0; i < numCourses; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] prerequisite : prerequisites) {
			indegree[prerequisite[0]]++;
			adjList[prerequisite[1]].add(prerequisite[0]);
		}

		Queue<Integer> queue = new LinkedList<>();

		// Push all the nodes with indegree 0 in the queue.
		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}

		int connected = 0;

		while (!queue.isEmpty()) {
			int node = queue.poll();
			connected++;

			for (int neighbor : adjList[node]) {
				// Delete the edge node -> neighbor
				indegree[neighbor]--;

				if (indegree[neighbor] == 0) {
					queue.offer(neighbor);
				}
			}
		}
		return connected == numCourses;
	}

	public static boolean canFinishKahnAlt(int numCourses, int[][] prerequisites) {
		int[] indegree = new int[numCourses];
		List<Integer>[] adjList = new ArrayList[numCourses];

		for (int i = 0; i < numCourses; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] prerequisite : prerequisites) {
			indegree[prerequisite[0]]++;

			adjList[prerequisite[1]].add(prerequisite[0]);
		}

		Queue<Integer> queue = new LinkedList<>();

		// Push all the nodes with indegree 0 in the queue.
		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()) {
			int node = queue.poll();

			for (int neighbor : adjList[node]) {
				// Delete the edge node -> neighbor
				indegree[neighbor]--;

				if (indegree[neighbor] == 0) {
					queue.offer(neighbor);
				}
			}
		}

		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] != 0) {
				return false;
			}
		}

		return true;
	}
}
