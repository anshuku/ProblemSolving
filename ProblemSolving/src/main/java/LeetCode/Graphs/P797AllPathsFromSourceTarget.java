package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P797. All Paths From Source to Target - Medium
 * 
 * Given a directed acyclic graph (DAG) of n nodes labeled from 0 to n - 1, 
 * find all possible paths from node 0 to node n - 1 and return them in any order.
 * 
 * The graph is given as follows: graph[i] is a list of all nodes you can visit 
 * from node i (i.e., there is a directed edge from node i to node graph[i][j]).
 * 
 * graph[i][j] != i, no self loops. All elements of graph[i] are unique.
 * 
 * Approach - 
 */
public class P797AllPathsFromSourceTarget {

	public static void main(String[] args) {
//		int[][] graph = { { 1, 2 }, { 3 }, { 3 }, {} };
		int[][] graph = { { 4, 3, 1 }, { 3, 2, 4 }, { 3 }, { 4 }, {} };

		List<List<Integer>> allPathsDfsBT = allPathsSourceTargetDfsBT(graph);
		System.out.println("DFS BT: All the paths from 0 to n-1 are: " + allPathsDfsBT);

		List<List<Integer>> allPathsDP = allPathsSourceTargetDP(graph);
		System.out.println("DP: All the paths from 0 to n-1 are: " + allPathsDP);
	}

	// DFS + Backtracking
	// Here, we've path exploration in graph data structre, so apply backtracking
	// algo. We explore graph 1 step at a time as an agent in a game. At any given
	// node, we try out each neighbor node recursively until we reach the target or
	// there is no more nodes. While trying we mark the choice before moving on and
	// later reverse the choice(backtrack) and start another exploration. Algo:
	// This involves DFS + backtracking, which backtracking algo often assumes.
	// We'll use recursion for DFS as it implements it in intuitive and concise way.
	// We Implement a recursive function backtrack(currNode, path) which explores
	// the nodes with current node and path traversed as params. In recursive
	// function, we 1st define its base case, which is when we encounter the target
	// node or currNode == target. In the body of recursive function, we enumerate
	// through all the neighbor nodes of current node. For each iteration, we 1st
	// mark the choice by appending the neighbor node in the path. Then we
	// recursively invoke the backtrack function to explore deeper. At the end of
	// the iteration, we should reverse the choice by popping the neighbor node from
	// the path, so that we can start from next neighbor node. To kick off the
	// backtrack function we can add initial node(index 0) to path.
	// Complexity Analysis: Let n be number of nodes in the graph. To estimate max
	// paths to travel from node 0 to node n-1 for a graph with n nodes. For graph
	// with 2 nodes: only 1 path. If we add 1 more node then it becomes 2: one from
	// previous path, other 1 is bridged by newly-added node. If we continue to add
	// nodes to graph, every time we add a new node, number of paths would double.
	// With new node, new paths will be created by preceding all previous paths with
	// the newly added nodes. So for a graph with n nodes, at max, there could be
	// Sum(i=0 to N-1)2^i = 2^(N-1) - 1 number oh paths between start and end nodes.
	// Time complexity: O(n*2^n), there are at most 2^(N-1) - 1 possible paths in
	// the graph. For each path, there could be at most N-2 intermediate nodes, i.e.
	// it takes O(N) time to build a path. So a loose upper bound is O(2^N*N), where
	// we consider it takes O(N) efforts to build each path. To find lower bound of
	// the algo, 1 can imagine an input: the edge set of graph is {(i,j) | 0 <=i <
	// j< N}, there exists an edge from node i -> j, if i < j. Here, we can
	// arbitrarily build a set of nodes from 1 to N-1 and construct valid path by
	// adding the starting point 0 and the end point N-1. For each path of k
	// intermediate nodes, we've taken O(k) time to build and deep copy the path to
	// the result set. Thus total time is at least Sum (k = 0 to N-1)k*(N-2 k) =
	// 2^(N-3)*(N-2), whiich is O(2^N*N).
	// Space complexity: O(n*2^n), as we applied recursion which taes O(n) time for
	// functional call stack due to N consecutive calls. The recursive call also
	// keps state of the current path, which again takes O(N) space. Apart from
	// this, we at most could have 2^(N-1) - 1 paths as the results and each path
	// can contain upto N nodes, so we need extra O(n*2^n) space to store.
	public static List<List<Integer>> allPathsSourceTargetDfsBT(int[][] graph) {
		List<List<Integer>> result = new ArrayList<>();
		int n = graph.length;
		List<List<Integer>> adjList = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			adjList.add(new ArrayList<>());
			for (int node : graph[i]) {
				adjList.get(i).add(node);
			}
		}
		List<Integer> route = new ArrayList<>();
		route.add(0);
//		dfsAdjList(adjList, route, result, 0);
		dfsGraph(graph, route, result, 0);
		return result;
	}

	private static void dfsAdjList(List<List<Integer>> adjList, List<Integer> route, List<List<Integer>> result,
			int node) {
		if (node == adjList.size() - 1) {
			result.add(new ArrayList<>(route));
			return;
		}
		for (int nextNode : adjList.get(node)) {
			route.add(nextNode);
			dfsAdjList(adjList, route, result, nextNode);
			route.remove(route.getLast());
		}
	}

	private static void dfsGraph(int[][] graph, List<Integer> route, List<List<Integer>> result, int node) {
		if (node == graph.length - 1) {
			result.add(new ArrayList<>(route));
			return;
		}
		for (int nextNode : graph[node]) {
			route.add(nextNode);
			dfsGraph(graph, route, result, nextNode);
			route.removeLast(); // route.remove(route.getLast());
		}
	}

	// Dynamic programming - Memoization
	// DP involves divide-and-conquer just like the backtracking approach which
	// breaks the problem down to smaller steps. Here DP is less optimal than
	// Backtracking. We use Top-Down approach where we use laissez-faire strategy
	// assuming that the target function would work out on its own. Given a node
	// currNode, the target function allPathsToTarget(currNode), this returns all
	// the paths from current node to target node. The target function is calculated
	// by iterating through the neighbor nodes of current node via recursive
	// function: For all nextNode element of neighbors(currNode),
	// allPathsToTarget(currNode) = {currNode + allPathsToTarget(nextNode)}
	// The paths from current node to target node consist of all the paths starting
	// from each neighbor of the current node. Algo: We could implement a DP
	// algorithm by 1st defining a target function allPathsToTarget(node). The
	// target function is recursive, whose base case is when the given node is the
	// target node. Otherwise, we iterate through the neighbor nodes, and invoke
	// the target function function with each neighbor node, i.e.,
	// allPathsToTarget(neighbor). With the returned results from the target
	// function, we preprend the current node to to the downstream paths, to build
	// the final paths. We invoke this target function with the desired starting
	// node or node 0. To resue intermediate results, we cache the results returned
	// from the target function as we can get teh node multiple times if there is an
	// overlapping between paths. Once we get the paths from a given npde to the
	// target node, we cache it for reuse.
	// Time complexity - O(n*2^n), N is the number of nodes in the graph. There can
	// be at most 2^(n-1) - 1 number of paths. We start from the last step when we
	// prepend the start node to each of the paths returned from the target
	// function. Since we've to copy each path in order to create new paths, it
	// would take up to N steps for each final path. So, for the last step it could
	// take us O(n*2^(n-1) time. Right before the last step, when the max length of
	// the path is N-1, we should have 2^(n-2) number of paths at this moment.
	// From these 2 steps, loose upper-bound of the time complexity is O(sum i=1 to
	// N 2^(i-1)*i) = O(n*2^n). Similar to backtracking, we can also get a lower
	// bound of the time complexity as O(n*2^n). So, the time complexity = O(n*2^n).
	// DP approach is slower than backtracking as we copy the intermediate paths
	// over and over.
	// Space complexity - O(n*2^n), we at most could have 2^(N-1) - 1 paths as the
	// results and each path can contain upto N nodes, so we need extra O(n*2^n)
	// space to store. For recursion we need additional memory for function call
	// stack. The stack can grow upto N consecutive calls but we need to memorize
	// the intermediate results for allPathsToTarget(node). So, sum(i = 1 to N)i*2^i
	// = O(n*2^n).
	private static List<List<Integer>> allPathsSourceTargetDP(int[][] graph) {
		Map<Integer, List<List<Integer>>> memo = new HashMap<>();

		return appPathsToTarget(graph, memo, 0);
	}

	private static List<List<Integer>> appPathsToTarget(int[][] graph, Map<Integer, List<List<Integer>>> memo,
			int node) {
		if (memo.containsKey(node)) {
			return memo.get(node);
		}
		List<List<Integer>> result = new ArrayList<>();
		if (node == graph.length - 1) {
			List<Integer> route = new ArrayList<>();
			route.add(node);
			result.add(route);
		} else { // can remove else as well
			for (int nextNode : graph[node]) {
				for (List<Integer> nodes : appPathsToTarget(graph, memo, nextNode)) {
					List<Integer> route = new ArrayList<Integer>();
					route.add(node);
					for (int val : nodes) {
						route.add(val);
					}
					result.add(route);
				}
			}
		}
		memo.put(node, result);
		return result;
	}

}
