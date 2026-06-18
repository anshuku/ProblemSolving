package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * P210. Course Schedule II - Medium
 * 
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. 
 * You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates 
 * that you must take course bi first if you want to take course ai.
 * 
 * > For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * 
 * Return the ordering of courses you should take to finish all courses. If there are many valid 
 * answers, return any of them. If it is impossible to finish all courses, return an empty array.
 * 
 * Approach - Kahn's algorithm: Topological sorting, DFS
 */
public class P210CourseScheduleII {

	public static void main(String[] args) {
//		int numCourses = 2;
//		int[][] prerequisites = { { 1, 0 } }; // [0,1]

		int numCourses = 4;
		int[][] prerequisites = { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } }; // [0,1,2,3] / [0,2,1,3]

//		int numCourses = 1;
//		int[][] prerequisites = {}; // [0]

//		int numCourses = 2;
//		int[][] prerequisites = { { 1, 0 }, { 0, 1 } }; // []

		int[] orderDFS = findOrderDFS(numCourses, prerequisites);
		System.out.println("DFS: The ordering of courses to take to finish all courses: " + Arrays.toString(orderDFS));

		int[] orderKahn = findOrderKahn(numCourses, prerequisites);
		System.out
				.println("Kahn: The ordering of courses to take to finish all courses: " + Arrays.toString(orderKahn));

		int[] orderDFSList = findOrderDFSList(numCourses, prerequisites);
		System.out.println(
				"DFS List: The ordering of courses to take to finish all courses: " + Arrays.toString(orderDFSList));

	}

	// Shared index is needed
	static int index;

	// DFS
	// It's a common problem and it's a natural fit for graph based algos and we can
	// easily model the elements in the problem statement as a graph. The graphical
	// representation of the prolem and it's components are: G(V,E) represents a
	// directed, unweighted graph. Each course would represent a vertex in the
	// graph. The edges are modeled after the prerequisite relationship between
	// courses. So we're given, that a pair such as [a,b] in the question means the
	// course b is a prerequisite for the course a. This is a directed edge b -> a
	// in the graph. The graph is a cyclic graph because there is a possibility of a
	// cycle in the graph. If the graph would be acyclic, then an ordering of
	// subjects as required in the question would always be possible. Since it's
	// mentioned that such and ordering may not always be possible, that means we've
	// a cyclic graph.
	// The presence of a cycle in the graph means that a proper ordering of
	// prerequisites is not possible at all. Intuitively, it's not possible to have
	// e.g. two subjects S1 and S2 prerequisites of each other. Similar ideology
	// applies to a larger cycle in the graph. Such an ordering of subjest is
	// referred to as a Topological Sorted Order and it's is a common algorithmic
	// problem in the graph domain.
	// If we're a node(say A) in our graph during the DFS traversal. The way DFS
	// would work is that we would consider all possible paths stemming from A
	// before finishing up the recursion for A and moving onto other nodes. All the
	// nodes in the paths stemming from the node A would have A as an ancestor. The
	// way this fits for this problem is, all the courses in the paths stemming from
	// the course A would have A as a prerequisite.
	// Now we know how to get all the courses that have a particular course as a
	// prerequisite. If a valid ordering of courses is possible, the course A would
	// come before all the other set of courses that have it as a prerequisite.
	// An important thing to note about the input for the problem is that a pair
	// such as [a, b] represents that the course b needs to be taken in order to do
	// the course a. This implies an edge of the form b -> a.
	// An important thing to note about toplogically sorted order is that there
	// won't be just one ordering of nodes(courses). There can different orderings.
	// Time complexity - O(V + E)
	// Space complexity - O(V + E)
	private static int[] findOrderDFS(int numCourses, int[][] prerequisites) {
		List<Integer>[] adjList = new ArrayList[numCourses];

		for (int i = 0; i < numCourses; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] prerequisite : prerequisites) {
			adjList[prerequisite[1]].add(prerequisite[0]);
		}

		boolean[] visited = new boolean[numCourses];
		boolean[] inStack = new boolean[numCourses];

		int[] order = new int[numCourses];

		index = numCourses - 1;

		for (int i = 0; i < numCourses; i++) {
			if (!visited[i]) {
				if (dfs(i, visited, inStack, order, adjList)) {
					return new int[] {};
				}
			}
		}

		return order;
	}

	// dfs return boolean for cycle detection
	private static boolean dfs(int node, boolean[] visited, boolean[] inStack, int[] order, List<Integer>[] adjList) {
		if (inStack[node]) {
			return true;
		}
		if (visited[node]) {
			return false;
		}

		visited[node] = true;
		inStack[node] = true;

		for (int neighbor : adjList[node]) {
			if (dfs(neighbor, visited, inStack, order, adjList)) {
				return true;
			}
		}
		inStack[node] = false;
		// Below is equivalent to pushing it onto a stack and reverting it at the end.
		// This is how DFS topological sort is normally implemented.
		order[index--] = node;
		return false;
	}

	// Kahn's Algo: Using node indegree
	// The approach is much easier to think as it's clear from the following fact
	// about topological ordering. The first node in the topological ordering will
	// be the node that doesn't have any incoming edges. Essentially, any node that
	// has an in-degree of 0 can start the topologically sorted order. If there are
	// multiple such nodes, their relative order doesn't matter and they can appear
	// in any order. This algo is based on this idea. We first process all the
	// nodes/courses with 0 in-degree implying no prerequisite courses required. If
	// we remove all these courses from the graph, along with their outgoing edges,
	// we can find out the courses/nodes that should be processed next. These would
	// be the nodes with 0 in-degree. This is continuously done until all the
	// courses have been accounted for. To keep track of nodes with 0 indegree we
	// can use a stack as well. The stack will give a different ordering than the
	// queue due to difference in access patterns between the 2 data-structures.
	// Time complexity - O(V + E)
	// Space complexity - O(V + E)
	public static int[] findOrderKahn(int numCourses, int[][] prerequisites) {
		int[] order = new int[numCourses];

		List<Integer>[] adjList = new ArrayList[numCourses];

		for (int i = 0; i < numCourses; i++) {
			adjList[i] = new ArrayList<>();
		}

		int[] indegree = new int[numCourses];

		for (int[] prerequisite : prerequisites) {
			indegree[prerequisite[0]]++;
			adjList[prerequisite[1]].add(prerequisite[0]);
		}

		Queue<Integer> queue = new LinkedList<>();

		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}

		int len = 0;
		while (!queue.isEmpty()) {
			int node = queue.poll();
			order[len++] = node;

			for (int neighbor : adjList[node]) {
				indegree[neighbor]--;

				if (indegree[neighbor] == 0) {
					queue.offer(neighbor);
				}
			}
		}

		if (len == numCourses) {
			return order;
		}
		return new int[] {};
	}

	private static int[] findOrderDFSList(int numCourses, int[][] prerequisites) {
		List<Integer>[] adjList = new ArrayList[numCourses];

		for (int i = 0; i < numCourses; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] prerequisite : prerequisites) {
			adjList[prerequisite[1]].add(prerequisite[0]);
		}

		boolean[] visited = new boolean[numCourses];
		boolean[] inStack = new boolean[numCourses];

		List<Integer> courses = new ArrayList<>();

		for (int i = 0; i < numCourses; i++) {
			if (!visited[i]) {
				if (dfs(i, visited, inStack, courses, adjList)) {
					return new int[] {};
				}
			}
		}

		// Reverse the list and not sort it.
		Collections.reverse(courses);

		int[] courseOrder = courses.stream().mapToInt(Integer::intValue).toArray();
		return courseOrder;
	}

	private static boolean dfs(int node, boolean[] visited, boolean[] inStack, List<Integer> courses,
			List<Integer>[] adjList) {
		if (inStack[node]) {
			return true;
		}

		if (visited[node]) {
			return false;
		}

		visited[node] = true;
		inStack[node] = true;

		for (int neighbor : adjList[node]) {
			if (dfs(neighbor, visited, inStack, courses, adjList)) {
				return true;
			}
		}

		inStack[node] = false;
		// courses.add(0, node) is O(n) per operation which leads to O(v^2) overall
		// instead of O(v+e).
		courses.add(node);
		return false;
	}

}
