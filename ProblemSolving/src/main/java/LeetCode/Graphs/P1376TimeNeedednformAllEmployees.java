package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * P1376. Time Needed to Inform All Employees - Medium
 * 
 * A company has n employees with a unique ID for each employee from 
 * 0 to n - 1. The head of the company is the one with headID.
 * 
 * Each employee has one direct manager given in the manager array where manager[i] 
 * is the direct manager of the i-th employee, manager[headID] = -1. Also, it 
 * is guaranteed that the subordination relationships have a tree structure.
 * 
 * The head of the company wants to inform all the company employees of an urgent 
 * piece of news. He will inform his direct subordinates, and they will inform 
 * their subordinates, and so on until all employees know about the urgent news.
 * 
 * The i-th employee needs informTime[i] minutes to inform 
 * all of his direct subordinates (i.e., After informTime[i] minutes, 
 * all his direct subordinates can start spreading the news).
 * 
 * Return the number of minutes needed to inform all the employees about the urgent news.
 * 
 * Approach - DFS, BFS
 * 
 * In BFS and DFS we don't need to keep track of the nodes traversed when the structure being 
 * traversed over is a directed tree without any cycles. Hence, a node is never visited twice.
 */
public class P1376TimeNeedednformAllEmployees {

	public static void main(String[] args) {
//		int n = 1;
//		int headID = 0;
//		int[] manager = { -1 };
//		int[] informTime = { 0 };

//		int n = 6;
//		int headID = 2;
//		int[] manager = { 2, 2, -1, 2, 2, 2 };
//		int[] informTime = { 0, 0, 1, 0, 0, 0 };

		int n = 15;
		int headID = 0;
		int[] manager = { -1, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] informTime = { 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 };

		int numMinutesDfs = numOfMinutesDfs(n, headID, manager, informTime);
		System.out.println("DFS: The number of minutes to inform all employees: " + numMinutesDfs);

		int numMinutesBfs = numOfMinutesBfs(n, headID, manager, informTime);
		System.out.println("BFS: The number of minutes to inform all employees: " + numMinutesBfs);
	}

	static int maxTime = 0;

	// DFS
	// We need to find the maximum of all the inform time when each employee will
	// know the information. We iterate over all the employees with keeping the time
	// they were made aware of the information. Before using DFS, We prepare the
	// tree in a way we can iterate quickly. The info starts from the headID at
	// time=0, the it traverses to its subordiantes at time informTime[headID], and
	// then their subordinates pass it on to their subordinates, and so on. For this
	// parent-to-child relationship is needed instead of child-to-parent
	// relationship. For this we create an edge from manager[i] to i. We start the
	// DFS from root node, i.e. headID with time as 0, then we iterate over its
	// subordinates with time as time + informTime[parent]. Before we iterate over
	// the subordinates, we update maxTime, whch is the maximum time seen so far.
	// We return maxTime at end, which is the time for employee that got the info at
	// last. We don't have to keep track of nodes traversed via visited as it's a
	// directed tree without any cycles. Hence, a node is never visited twice.
	// Algo: Create an adjacency list adj: the ith index stores the adjacent nodes
	// of the employee id i or manager id is connected with it's direct
	// subordinates. Iterate over employees and for each i, store an edge manager[i]
	// -> i. Start DFS with node headID and time as 0 for each node as curr:
	// a) Update the max time we've seen. maxTime by comparing with time
	// b) Iterate over adjacent nodes of curr, and for each adjacent node, perform
	// DFS with time = time + informTime[curr]. At end return maxTime(global var).
	// Time complexity - O(n), we iterate employees to create the adjacency list. We
	// perform DFS where we iterate over each node once to find when thet get the
	// info from headID.
	// Space complexity - O(n), the size of adjacency list is N, and there will be
	// only N-1 edges in the tree. There is some stack space required for DFS and
	// the max active stack calls = number of nodes in skewed tree: O(n).
	private static int numOfMinutesDfs(int n, int headID, int[] manager, int[] informTime) {
		Map<Integer, List<Integer>> adj = new HashMap<>();
		// Each index stores the ids of subordinate employees.
		for (int i = 0; i < n; i++) {
			adj.computeIfAbsent(manager[i], k -> new ArrayList<>()).add(i);
		}
		dfs(adj, headID, 0, informTime);
		return maxTime;
	}

	private static void dfs(Map<Integer, List<Integer>> adj, int managerId, int time, int[] informTime) {
		// Maximum time for an employee to get the news.
		maxTime = Math.max(maxTime, time);

		if (!adj.containsKey(managerId)) {
			return;
		}

		for (int employeeId : adj.get(managerId)) {
			// Visit the subordinate employee who gets the news after informTime[curr] time
			dfs(adj, employeeId, time + informTime[managerId], informTime);
		}
	}

	// BFS
	// We iterate over nodes while keeping the time it takes to get info using BFS.
	// We start from the headID with time = 0, and for each child node, we put all
	// subordinates in the queue with time as informTime[parent]. Then, similar to
	// DFS, we'll update maximum time we've seen so far, maxTime each time we
	// extract a node from the queue. Also, we don't need to keep track of visited
	// nodes as there aren't any cycles. Alog: Create an adjacency list adj: the ith
	// index stores the adjacent nodes of the employee id i or manager id is
	// connected with it's direct subordinates. Iterate over employees and for each
	// i, store an edge manager[i] -> i. Initialize a queue q of pairs of integers.
	// The 1st integer is the ID of an employee, and 2nd integer is the time at
	// which this employee gets the information. Insert 1st queue entry as (headID,
	// 0). Keep extracting pairs from the queue until it's empty. For each pair
	// update maxTime 1st and then push each subordinate of current employee with
	// extra time required to inform its subordinates via informTime array.
	// Time complexity - O(n), we iterate employees to create the adjacency list. We
	// perform BFS where we iterate over each node once to find when they get info
	// from headID.
	// Space complexity - O(n), the size of adjacency list is N, and there will be
	// only N-1 edges in the tree. The size of the queue at most will be O(N).
	public static int numOfMinutesBfs(int n, int headID, int[] manager, int[] informTime) {
		Map<Integer, List<Integer>> adj = new HashMap<>();
		for (int i = 0; i < n; i++) {
			adj.computeIfAbsent(manager[i], k -> new ArrayList<>()).add(i);
		}
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] { headID, 0 });

		int maxTime = 0;
		while (!queue.isEmpty()) {
			int[] managerArr = queue.poll();
			int managerId = managerArr[0];
			int timeSoFar = managerArr[1];
			maxTime = Math.max(maxTime, timeSoFar);
			if (!adj.containsKey(managerId)) {
				continue;
			}
			for (int employeeId : adj.get(managerId)) {
				queue.offer(new int[] { employeeId, timeSoFar + informTime[managerId] });
			}
		}
		return maxTime;
	}

}
