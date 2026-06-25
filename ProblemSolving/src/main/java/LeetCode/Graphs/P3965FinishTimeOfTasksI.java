package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * P3965. Finish Time of Tasks I - Medium
 * 
 * You are given an integer n representing the number of tasks in a project, 
 * numbered from 0 to n - 1. These tasks are connected as a tree rooted at task 0. 
 * This is represented by a 2D integer array edges of length n - 1, where 
 * edges[i] = [ui, vi] indicates that task ui is the parent of task vi.
 * 
 * You are also given an array baseTime of length n, where baseTime[i] represents the time to complete task i.
 * 
 * The finish time of each task is calculated as follows:
 * > Leaf task: The finish time is baseTime[i].
 * > Non-leaf task:
 * > - Let earliest be the minimum finish time among its children, 
 * and latest be the maximum finish time among its children.
 * > - Let ownDuration be (latest - earliest) + baseTime[i].
 * > - The finish time of task i is latest + ownDuration.
 * 
 * Return the finish time of the root task 0.
 * 
 * Approach - DFS, Topological Sort: Kahn's Algo
 */
public class P3965FinishTimeOfTasksI {

	public static void main(String[] args) {
		int n = 3;
		int[][] edges = { { 0, 1 }, { 1, 2 } };
		int[] baseTime = { 9, 5, 3 }; // 17

//		int n = 3;
//		int[][] edges = { { 0, 1 }, { 0, 2 } };
//		int[] baseTime = { 4, 7, 6 }; // 12

		long finishTimeDFS = finishTimeDFS(n, edges, baseTime);
		System.out.println("DFS: The finish time of the root task is: " + finishTimeDFS);

		long finishTimeKahn = finishTimeKahn(n, edges, baseTime);
		System.out.println("Kahn: The finish time of the root task is: " + finishTimeKahn);
	}

	// DFS: Tree bottom-up DP.
	// Since every node's answer depends only on its children's answers, making it a
	// classic bottom-up tree DP problem. We ues recursion to compute the finish
	// time of each node. The base case is when the node is leaf, we return
	// baseTime[node]. The recursive case is when we compute the finish time of all
	// children. We get the latest and earlies and then find finishTime as per the
	// conditions.
	// The finish time of a node depends only on: minimum child finish time, maximum
	// child finish time. So we never need info from deeper descendants directly.
	// Once all children's finish times are known, the parent's finish time can be
	// computed immediately.
	// For this problem. the input forms a tree rooted at node 0. Every node(except
	// the root) has exactly one parent. There are no cycles. There is exactly one
	// path from the root to any node. Because of this structure:
	// dfs(node) is invoked exactly once for every node. No node's result is
	// recomputed. There are no overlapping subproblems. So, the DP/memoization
	// array does not provide any benefit as the same node is never reached from
	// multiple paths. DP/Memoization helps when the graph is a DAG rather than a
	// tree. So for every node we: Visit the node once. Process each node once.
	// Time complexity - O(n), as each node is visited exactly once.
	// Space complexity - O(n), for adjacency list and recursion stack(DFS).
	private static long finishTimeDFS(int n, int[][] edges, int[] baseTime) {
		List<Integer>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			adjList[edge[0]].add(edge[1]);
		}

		return dfs(0, adjList, baseTime);
	}

	private static long dfs(int node, List<Integer>[] adjList, int[] baseTime) {
		if (adjList[node].size() == 0) {
			return baseTime[node];
		}

		long finishTime = 0;
		long min = Long.MAX_VALUE;
		long max = 0;

		for (int child : adjList[node]) {
			long time = dfs(child, adjList, baseTime);

			min = Math.min(min, time);
			max = Math.max(max, time);
		}

		long ownDuration = (max - min) + baseTime[node];
		finishTime = max + ownDuration;
		return finishTime;
	}

	// Kahn's Algo: Toplogical sorting + Critical path
	// If any parent has multiple child nodes, they can run parallely. The key point
	// here is that the parent task can start only if the child tasks are completed.
	// Here Critical Path Method(CPM) can be used where parallel dependencies are
	// present and we want to find min completion time. We can use Topological sort
	// + max-min tracking.
	// We use a reverse graph, as we are using a bottom-up approach. We start from
	// leaf nodes and we move to parent so we reverse the edges.
	// We track min child(earliest) and max child(latest) as we can execute child
	// tasks parallely. The base case is the time taken for leaf nodes as they don't
	// have any dependency.
	// Time complexity - O(V + E)
	// Space complexity - O(V + E)
	public static long finishTimeKahn(int n, int[][] edges, int[] baseTime) {
		int[] indegree = new int[n]; // Used to track the count of parents, outdegree is better
		// Outdegre measures how many children are present.

		List<Integer>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			// The parent's dependency count
			indegree[edge[0]]++;
			// We create edge from child to parent
			adjList[edge[1]].add(edge[0]);
		}

		long[] finish = new long[n];
		Queue<Integer> queue = new LinkedList<>();

		// Identify the leaf nodes.
		for (int i = 0; i < n; i++) {
			if (indegree[i] == 0) {
				finish[i] = baseTime[i]; // Tasks are independent.
				queue.offer(i);
			}
		}

		long[] earliest = new long[n]; // min finishing time
		Arrays.fill(earliest, Long.MAX_VALUE);

		long[] latest = new long[n]; // max finishing time
		Arrays.fill(latest, Long.MIN_VALUE); // no need as min is 0.

		while (!queue.isEmpty()) {
			int child = queue.poll();

			for (int parent : adjList[child]) {
				indegree[parent]--;

				earliest[parent] = Math.min(earliest[parent], finish[child]);
				latest[parent] = Math.max(latest[parent], finish[child]);

				// We're moving from child to parent. Parent will be processed only once the
				// childs are completely processed.
				if (indegree[parent] == 0) {
					long ownDuration = (latest[parent] - earliest[parent]) + baseTime[parent];
					finish[parent] = latest[parent] + ownDuration;

					queue.offer(parent);
				}
			}
		}

		return finish[0];
	}

}
