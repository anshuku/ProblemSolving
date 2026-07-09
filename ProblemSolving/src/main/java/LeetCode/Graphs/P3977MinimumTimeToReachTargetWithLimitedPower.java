package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * P3977. Minimum Time to Reach Target With Limited Power - Hard
 * 
 * You are given a directed weighted graph with n nodes labeled from 0 to n - 1.
 * 
 * The graph is represented by a 2D integer array edges, where edges[i] = [ui, vi, ti] 
 * indicates a directed edge from node ui to node vi that takes ti seconds to traverse.
 * 
 * You are also given an integer power representing the initial available power, and 
 * an integer array cost of length n, where cost[u] represents the power required 
 * to forward the signal from node u through any one of its outgoing edges.
 * 
 * You are given two integers source and target.
 * 
 * The signal starts at source at time 0 with power units of power and follows these rules:
 * 
 * > The signal may traverse a directed edge from node u only if the remaining power is at least cost[u].
 * 
 * > No power is consumed when the signal arrives at a node, 
 * unless it later leaves that node by traversing another edge.
 * 
 * > When the signal is forwarded from node u, the remaining power is decreased by cost[u] units.
 * 
 * > Traversing an edge edges[i] = [ui, vi, ti] increases the total time by ti seconds.
 * 
 * Return an integer array answer of size 2, where:
 * > answer[0] is the minimum time required for the signal to reach node target.
 * > answer[1] is the maximum remaining power among all paths that achieve answer[0].
 * 
 * If the signal cannot reach target, return [-1, -1].
 * 
 * Constraints:
 * 1 <= n <= 1000
 * 0 <= edges.length <= 1000
 * edges[i] = [ui, vi, ti]
 * 0 <= ui, vi <= n - 1
 * 1 <= ti <= 10^9
 * 1 <= power <= 1000
 * cost.length == n
 * 1 <= cost[i] <= 2000
 * 0 <= source, target <= n - 1
 * 
 * Approach - Dijkstra's Algorithm with a state
 */
public class P3977MinimumTimeToReachTargetWithLimitedPower {

	public static void main(String[] args) {
//		int n = 5;
//		int[][] edges = { { 0, 1, 1 }, { 1, 4, 1 }, { 0, 2, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
//		int power = 4;
//		int[] cost = { 2, 3, 1, 1, 1 };
//		int source = 0;
//		int target = 4;

//		int n = 3;
//		int[][] edges = { { 0, 1, 2 }, { 1, 2, 2 }, { 2, 0, 2 } };
//		int power = 3;
//		int[] cost = { 1, 1, 1 };
//		int source = 1;
//		int target = 1;

//		int n = 4;
//		int[][] edges = { { 0, 1, 3 }, { 2, 3, 4 } };
//		int power = 3;
//		int[] cost = { 1, 1, 1, 1 };
//		int source = 0;
//		int target = 3;

		int n = 4;
		int[][] edges = { { 0, 1, 1 }, { 1, 3, 2 }, { 0, 2, 1 }, { 2, 3, 2 } };
		int power = 3;
		int[] cost = { 1, 2, 1, 1 };
		int source = 0;
		int target = 3;

		long[] minTimeMaxPowerCompTimeOpt = minTimeMaxPowerCompTimeOpt(n, edges, power, cost, source, target);
		System.out.println("Comparator Time Optimized: The min time and max remaining power for node to reach target: "
				+ Arrays.toString(minTimeMaxPowerCompTimeOpt));

		long[] minTimeMaxPowerCompTimePower = minTimeMaxPowerCompTimePower(n, edges, power, cost, source, target);
		System.out.println("Comparator Time Power: The min time and max remaining power for node to reach target: "
				+ Arrays.toString(minTimeMaxPowerCompTimePower));

		long[] minTimeMaxPowerCompPower = minTimeMaxPowerCompPower(n, edges, power, cost, source, target);
		System.out.println("Comparator Power: The min time and max remaining power for node to reach target: "
				+ Arrays.toString(minTimeMaxPowerCompPower));

		long[] minTimeMaxPowerCompTime = minTimeMaxPowerCompTime(n, edges, power, cost, source, target);
		System.out.println("Comparator Time: The min time and max remaining power for node to reach target: "
				+ Arrays.toString(minTimeMaxPowerCompTime));
	}

	// Dijkstra with a state
	// Goal: find the minimum time for a signal to travel from source to target,
	// then - among every path that achieves that minimum time - keep the maximum
	// remaning power. If target is unreachable, return {-1, -1}.
	// Intuition: We want the minimum total time to push a signal from source to
	// target across a directed graph whose edges have non-negative travel times.
	// This means we need to use Dijkstra's algorithm.
	// There are 2 extra condition: > Power is spent to leave a node: Each time the
	// signal is forwarded out of a node u, it pays cost[u] power, and it's only
	// allowed to leave when the remaining power is at least cost[u]. Only departing
	// from a node costs power while arriving is free. > There is a tie-breaker.
	// Among all of the fastest paths, we must report the one that finishes with the
	// most leftover power.
	// Hence, knowing only which node we are standing on is not enough. Two
	// different ways of arriving at the same node can leave us with different
	// amounts of power. A path that reaches u with lots of power can keep
	// forwarding through expensive nodes, while a path that reaches u nearly
	// drained may get stuck. So, the position in the search must remember two
	// facts: 1. The node we're currently on. 2. How much power we still have at
	// that node. So the state is (node, remaning_power).
	// Approach: We run Dijkstra, but on an expanded state space. Instead of
	// time[node] we keep time[node][power] = the min time needed to reach node u
	// while holding exactly p units of remaining power. Everything else is now
	// normal Dijkstra, with 3 problem-specific rules:
	// > Order by time: The PriorityQueue, always returns the state with the
	// smallest arrival time first. Because all edge times are non-negative, the 1st
	// time we settle a state it's optimal.
	// > Pay on departure: To forward out of u, we need remaining >= cost[u]. The
	// power after leaving is remaining - cost[u], and this value sis same for every
	// outgoing edge of u, because the cost depends only on the node we leave, not
	// on which edge we take.
	// > 2 objectives in 1 sweep: The primary objective (min time) is handled by
	// Dijkstra's ordering. For the secondary objective (max power), we lock in the
	// best target time the moment we first pop target(node, keep collecting the max
	// power across every target state that ties that time, and stop as soon as we
	// pop a state whose time already exceeds the best.
	// Walkthrough:
	// Step 1: Build the adjacency list - We've directed graph. Each edge [u,v,t] is
	// an edge from u to v taking t seconds. For every node u we store its outgoing
	// (neighbor, time) pairs so we can quickly scan all edges leaving a node.
	// Step 2: Set up the time table - Create a 2D table time[node][p] filled with
	// infinity (meaning "not reached yet"). The 1st dimension is node (0... n-1);
	// the second is the remaining power (power..0). Power never increases.
	// Step 3: Seed the starting state - The signal begins at source at time 0
	// holding the full power. So, time[source][power] = 0, and we push (time = 0,
	// node = source, power = power) intp a min-heap ordered by time (1. We can also
	// add a comparator with power in decreasing order in case of time match 2. We
	// can also add the node in a max-heap ordered by remaining power).
	// Step 4: Pop the earliest-time state - Repeatedly remove the state with the
	// smallest time. Standard Dijkstra hygiene: If the popped time does not equal
	// time[u][p], this entry is stale (a better time for the same (node, power) was
	// found after it was pushed), so we skip it.
	// Step 5: Early exit and recording the target - > If we have already locked in
	// the best target time and the state we just popped has a larger time, we can
	// stop entirely: every remaining state in the heap has a time at least this
	// large, so none can beat or tie the best. We break.
	// > If the current node is target, we record the answer. The 1st arrival fixes
	// the minimum time best; for every target state that ties best we update
	// bestPower with the larger remaining power. We then continue - there is no
	// reason to forward the signal beyond the target.
	// Because the heap returns states in non-decreasing time order, every target
	// arrival that ties best is popped before any state with a strcitly larger
	// time, so the max power tie-break is always complete before we break out.
	// Step 6: Check power before forwarding. If the remaining power is less than
	// cost[u], the signal cannot leave this node at all, so this state is a dead
	// end - skip it. Otherwise the power after departure is remaining - cost[u].
	// Step 7: Relax the outgoing edges - For each neighbor v reached by an edge of
	// time t, the arrival time is newTime = currTime + t, and the remaining power
	// is nextPower. If this is faster than the best know time[v][nextPower], we
	// record it and push the new state (newTime, v, nextPower).
	// Step 8: Return the answer - After the loop, bestT is the min time to reach
	// target and bestP is the max remaining power among those fastest paths. If
	// target was never reached, we get (bestT, bestP)=(Inf, -Inf), giving (-1,-1).
	// Correctness: All edge times are non-negative, which is Dijkstra's
	// requirement. The only twist here is that the same node behaves differently
	// depending on how much power remains, so we treat (node, power) as a separate
	// vertex in an expanded graph. Dijkstra on this expanded graph settles each
	// (node, power) at its min time. Ordering the search by time guarantees the 1st
	// arrival at target is the fastest; collecting the max power across all
	// equally-fast target arrivals satisfies the secondary objective.
	// Time complexity - O(E*P*(log(n*P)), where n = number of nodes, E = number of
	// edges, and P = the initial power. There are upto n*(P+1) states, each
	// outgoing edge maybe relaxed once per power level. Here time is E*logV.
	// V = number of vertices, here it's n*P. E = edges, here it's E*P.
	// Space complexity - O(n*P + E), where n*p is for time 2d array and E is for
	// adjacency list.
	private static long[] minTimeMaxPowerCompTimeOpt(int n, int[][] edges, int power, int[] cost, int source,
			int target) {
		List<int[]>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			adjList[edge[0]].add(new int[] { edge[1], edge[2] });
		}

		PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
		pq.offer(new long[] { 0, source, power });

		long[][] time = new long[n][power + 1];
		for (long[] arr : time) {
			Arrays.fill(arr, Long.MAX_VALUE);
		}
		time[source][power] = 0;

		long bestT = Long.MAX_VALUE, bestP = Long.MIN_VALUE;

		while (!pq.isEmpty()) {
			long[] node = pq.poll();

			int u = (int) node[1];
			long currTime = node[0];
			int currPower = (int) node[2];

			// Not mandatory an can be ignored
			if (currTime != time[u][currPower]) {
				continue;
			}

			// We check it before the check for if (u == target). This is done to ensure we
			// don't update max power for higher time. As the just next node after best time
			// can have lower time and incorrect update of best power.
			if (currTime > bestT) {
				break;
			}

			if (u == target) {
				if (bestT == Long.MAX_VALUE) {
					bestT = currTime;
				}
				bestP = Math.max(bestP, currPower);
				continue;
			}

			int remainingPower = currPower - cost[u];

			if (remainingPower < 0) {
				continue;
			}

			for (int[] neighbor : adjList[u]) {
				int v = neighbor[0];
				int t = neighbor[1];

				if (time[v][remainingPower] > currTime + t) {
					time[v][remainingPower] = currTime + t;

					pq.offer(new long[] { time[v][remainingPower], v, remainingPower });
				}
			}
		}

		if (bestT == Long.MAX_VALUE) {
			return new long[] { -1, -1 };
		}
		return new long[] { bestT, bestP };
	}

	private static long[] minTimeMaxPowerCompTimePower(int n, int[][] edges, int power, int[] cost, int source,
			int target) {
		List<int[]>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			adjList[edge[0]].add(new int[] { edge[1], edge[2] });
		}

		// We take the smallest time taken first for processing and then max remaining
		// power for processing.
		PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> {
			if (a[2] != b[2]) {
				return Long.compare(a[2], b[2]);
			}
			return Long.compare(b[1], a[1]);
		});
		pq.offer(new long[] { source, power, 0 });

		long[][] time = new long[n][power + 1];

		for (long[] arr : time) {
			Arrays.fill(arr, Long.MAX_VALUE);
		}

		time[source][power] = 0;

		while (!pq.isEmpty()) {
			long[] node = pq.poll();

			int u = (int) node[0];
			int currPower = (int) node[1];
			long currTime = node[2];

			if (u == target) {
				return new long[] { currTime, currPower };
			}

			// can place this above if (u == target) check but checking it later is faster.
			if (currTime > time[u][currPower]) { // can be currTime != time[u][currPower]
				continue;
			}

			int remainingPower = currPower - cost[u];

			if (remainingPower < 0) {
				continue;
			}

			for (int[] neighbor : adjList[u]) {
				int v = neighbor[0];
				int t = neighbor[1];

				if (time[v][remainingPower] > currTime + t) {
					time[v][remainingPower] = currTime + t;

					pq.offer(new long[] { v, remainingPower, time[v][remainingPower] });
				}
			}

		}

		return new long[] { -1, -1 };
	}

	public static long[] minTimeMaxPowerCompPower(int n, int[][] edges, int power, int[] cost, int source, int target) {
		List<int[]>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			adjList[edge[0]].add(new int[] { edge[1], edge[2] });
		}

		// We take the most remaining power first for processing
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(b[1], a[1]));
		pq.offer(new int[] { source, power });

		long[][] time = new long[n][power + 1];

		for (long[] arr : time) {
			Arrays.fill(arr, Long.MAX_VALUE);
		}
		time[source][power] = 0;

		while (!pq.isEmpty()) {
			int[] node = pq.poll();

			int u = node[0];
			int currPower = node[1];

			int remainingPower = currPower - cost[u];

			if (remainingPower < 0) {
				continue;
			}

			for (int[] neighbor : adjList[u]) {
				int v = neighbor[0];
				int t = neighbor[1];

				if (time[v][remainingPower] > time[u][currPower] + t) {
					time[v][remainingPower] = time[u][currPower] + t;

					pq.offer(new int[] { v, remainingPower });
				}
			}

		}

		long minTime = Long.MAX_VALUE;
		long maxPower = Long.MIN_VALUE;

		for (int i = 0; i <= power; i++) {
			minTime = Math.min(minTime, time[target][i]);
		}

		if (minTime == Long.MAX_VALUE) {
			return new long[] { -1, -1 };
		}

		for (int i = power; i >= 0; i--) {
			if (time[target][i] == minTime) {
				maxPower = i;
				break;
			}
		}

		return new long[] { minTime, maxPower };
	}

	private static long[] minTimeMaxPowerCompTime(int n, int[][] edges, int power, int[] cost, int source, int target) {
		List<int[]>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			adjList[edge[0]].add(new int[] { edge[1], edge[2] });
		}

		PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
		pq.offer(new long[] { 0, source, power });

		long[][] time = new long[n][power + 1];

		for (long[] arr : time) {
			Arrays.fill(arr, Long.MAX_VALUE);
		}
		time[source][power] = 0;

		while (!pq.isEmpty()) {
			long[] node = pq.poll();

			long currTime = node[0];
			int u = (int) node[1];
			int currPower = (int) node[2];

			if (time[u][currPower] != currTime) {
				continue;
			}

			int remainingPower = currPower - cost[u];
			if (remainingPower < 0) {
				continue;
			}

			for (int[] neighbor : adjList[u]) {
				int v = neighbor[0];
				int t = neighbor[1];

				if (time[v][remainingPower] > currTime + t) {
					time[v][remainingPower] = currTime + t;

					pq.offer(new long[] { time[v][remainingPower], v, remainingPower });
				}
			}
		}

		long minTime = Long.MAX_VALUE;
		long maxPower = Long.MIN_VALUE;

		for (int i = 0; i <= power; i++) {
			minTime = Math.min(minTime, time[target][i]);
		}
		if (minTime == Long.MAX_VALUE) {
			return new long[] { -1, -1 };
		}

		for (int i = power; i >= 0; i--) {
			if (time[target][i] == minTime) {
				maxPower = i;
				break;
			}
		}
		return new long[] { minTime, maxPower };
	}
}
