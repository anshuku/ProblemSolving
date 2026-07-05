package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * P3970. Shortest Path With At Most K Consecutive Identical Characters - Medium
 * 
 * You are given an integer n representing the number of nodes in a directed weighted graph, 
 * numbered from 0 to n - 1. This is represented by a 2D integer array edges, where 
 * edges[i] = [ui, vi, wi] represents a directed edge from node ui to node vi with weight wi.
 * 
 * You are also given a string labels of length n, where labels[i] 
 * is the character assigned to node i, and an integer k.
 * 
 * Return the minimum total edge weight of a path from node 0 to node n - 1 
 * such that the concatenation of the labels of the nodes along the path contains 
 * at most k consecutive identical characters. If no valid path exists, return -1.
 * 
 * Approach - Dijkstra's Algorithm with a state
 */
public class P3970ShortestPathWithAtMostKConsecutiveIdenticalCharacters {

	public static void main(String[] args) {
//		int n = 3;
//		int[][] edges = { { 0, 1, 1 }, { 1, 2, 1 }, { 0, 2, 3 } };
//		String labels = "aab";
//		int k = 1;

//		int n = 3;
//		int[][] edges = { { 0, 1, 1 }, { 1, 2, 1 }, { 0, 2, 3 } };
//		String labels = "aab";
//		int k = 2;

//		int n = 3;
//		int[][] edges = { { 0, 1, 1 }, { 1, 2, 1 } };
//		String labels = "aaa";
//		int k = 2;

		int n = 3;
		int[][] edges = { { 0, 1, 1 }, { 1, 2, 1 }, { 0, 2, 100 } };
		String labels = "aaa";
		int k = 2;

		int minEdgeWeight = shortestPath(n, edges, labels, k);
		System.out.println(
				"Min edge weight of path 0->(n-1) with <= k consecutive identical characters: " + minEdgeWeight);
	}

	// Dijkstra's Algorithm
	// We need the min total edge weight of a path from 0 to n - 1. It's a shortest
	// path problem on a directed graph with non-negative weights, which can be
	// solved via Dijkstra's Algo. There is 1 restriction: The labels of the nodes
	// along the path must not contain more than k consecutive identical characters.
	// So, knowing only which node we're standing on is not enough. Two different
	// ways of reaching the same node can leave us in different situations, because
	// one might end in long run of identical labels and the other might not.
	// Reaching node u with a current run of 1 lets us continue more freely than
	// reachig u with a run of k(already maxed out). So we must remember 2 things
	// about our position: 1. The node we're on. 2. How many identical labels in a
	// row we've ending at this node. State is (node, current_consecutive_count).
	// Approach - We run Dijkstra on an expanded state. Instead of distance[node],
	// we use distance[node][count] where distance[node][count] = minimum cost to
	// reach node u such that the current run of identical labels ending at u has
	// length = count. The value of count ranges from [1,k].
	// Now, we use Dijkstra normally, we always expand the cheapest state first,
	// then relax outgoing edges.
	// Algo: We build the adjacency list, the graph is directed, so edge [u,v,w]
	// means an edge from u to v with weight w. We store, for every node u, it's
	// outgoing neighbors so we can quickly find all edges leaving a node.
	// We then create 2D distance table distance[node][count], filled with infinity
	// (a very large number meaning "not reached yet"). node goes 0..n-1, and count
	// goes 1..k.
	// Starting state: We start at node 0. Standing on a single node already gives a
	// length 1(the label of node 0 itself), so distance[0][1] = 0. We create a min
	// heap via priority queue and push (distance = 0, node = 0, count = 1) where we
	// order by distance(uses weight).
	// Pop the cheapest state: Repeatedly take out the state with smallest distance.
	// A Dijkstra trick: if popped distance is stale(not equal to the best known
	// dist[u][curr]), skip it. This happens when the same state was pushed multiple
	// times with different distances; we only process the best one.
	// Try each neighbor and update the run length: For each neighbor v of u with
	// weight w, compute the new consecutive count: If labels[v] == labels[u], the
	// run continues: newCount = count + 1. If labels[v] != labels[u], the run
	// resets: newCount = 1. If newCount > k, this move would create more than k
	// identical characters in a row, so skip it.
	// Relax the edge: Usual Dijkstra relaxation, just with the extra count
	// dimension: If going through u gives a cheaper way to reach v ending in a run
	// of length newCount, record it and push the new state to the min heap.
	// Read the answer, we can finish at node n-1 ending with any run length from 1
	// to k, so the answer is the smallest of distance[n-1][1]... distance[n-1][k].
	// If all are still infinity, node n - 1 is ureachable, so return -1.
	// This is correct as all edge weights are >= 0, exactly the condition for
	// Dijkstra. But, the twist is that the node behaves differently depending on
	// the current run length, so we treat (node, count) as separate vertices in an
	// expanded graph. Dijkstra on this expanded graph gives the min valid cost.
	// (node, count) is a valid state than (node, character) as it uniquely
	// determines current repeated character = lables[node] (fixed), so we do not
	// need to store the character separately.
	// We identified the minimum state needed for DP/graph search. One's instinct
	// may point to store the character. But we only need the count of character.
	// 1. We ask: "What information affects the future?". Suppose we're at node u.
	// The only restriction is: If the next node has the same label as the current
	// streak's character, increment the streak; otherwise reset it to 1. So to
	// determine the next state, one needs: 1. which node one is in. 2. How long the
	// current streak is. 3. What character the streak consists of. Initially this
	// suggests the state (node, streakCharacter, streakLength).
	// 2. Is the streak character independent? Now ask can I ever be at the same
	// node with two different streak characters? Suppose we're at node u with
	// label[u] = 'c'. Could the streak character be 'a'? No. As every path ends
	// with node u. The last character of the path is therefore label[u]. The streak
	// is defined by the suffix of identical characters ending at the current node.
	// Therefore the streak character must equal label[u] as it's determined by
	// current node.
	// 3. Observation, if the state is (node, count), then current streak character
	// = labels[node] is already known. In other words, (node, count, character)
	// contains redundant info. Whenever one piece of info can be computed from the
	// others, we shouldn't store it. This is common DP optimizations.
	// Now to recognize this in contest: We write down the transition before
	// deciding the DP state. Here it's labels[v] == ? then streak++ else
	// streak = 1. We ask what's "?" It's the character of the current streak. Then
	// we ask can I determine it from my current node? Immediately, current node = u
	// so labels[u] is exactly that character.
	// For contests, when we're about to add another dimension to the state, ask can
	// this dimension be derived from the existing state? If it's fixed no need to
	// store it. Example parent's color in graph, substree size in tree, cellvalue
	// for fixed grid, as they're fixed so no need to store it.
	// Rule: Never store something that is a deterministic function of the rest of
	// the state.
	// Here, if we store the character, state is distance[node][26][k+1], or (node,
	// currentCharacter, count). Transition, if labels[v] == currentCharacter,
	// newCount = count + 1 else currentCharacter = labels[v] and newCount = 1.
	// Here, currentCharacter will always equal labels[node]. So for every reachable
	// state, currentCharacter == labels[node], The other 25 character entries for
	// that node are unreachable. No need to store 26x more states, while only 1/26
	// of them can ever be valid.
	// Mindset: When solving DP or shortest-path problems with state, repeatedly ask
	// these 3 questions:
	// 1. What information influences future transitions?
	// 2. Is any of that informaton already implied by my current position/state?
	// 3. If I remove one state variable, can I still compute every transition
	// correctly?
	// In this problem, > The streak length influences future transitions, so it
	// must be stored. > The streak character also influences future transitions but
	// it's always equal to labels[currentNode], so it's implied by the node and
	// doesn't need to be stored.
	// Time complexity - O((e*k + n*k)log(n*k) n = nodes, e = edges. There are n*k
	// possible states, and each edge can be relaxed for different run lengths.
	// Here k <= 50 and n,m <= 50000, w <= 10000. Paths may contain many edges,
	// still the shortest path cannot exceed Integer.MAX_VALUE, as 50000 edges *
	// 10000 weight (max) = 5*10^8 < 2*10^9. Hence int is sufficent and overflow
	// won't occur on any valid shortest path.
	// Space compelxity - O(n*k+E)
	public static int shortestPath(int n, int[][] edges, String labels, int k) {
		List<int[]>[] adjList = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			adjList[edge[0]].add(new int[] { edge[1], edge[2] });
		}

		char[] labelsArr = labels.toCharArray();

		long[][] distance = new long[n][k + 1];

		for (long[] arr : distance) {
			Arrays.fill(arr, Long.MAX_VALUE); // Can use Integer.MAX_VALUE as well.
		}

		PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));

		pq.offer(new long[] { 0, 0, 1 });

		distance[0][1] = 0;

		while (!pq.isEmpty()) {
			long[] node = pq.poll();
			long d = node[0];
			int u = (int) node[1];
			int count = (int) node[2];

			if (u == n - 1) {
				return (int) d;
			}

			if (d != distance[u][count]) {
				continue;
			}

			char color = labelsArr[u];

			for (int[] neighbor : adjList[u]) {
				int v = neighbor[0];
				int w = neighbor[1];

				char newColor = labelsArr[v];

				int newCount = color == newColor ? count + 1 : 1;

				if (newCount > k) {
					continue;
				}
				if (distance[v][newCount] > d + w) {
					distance[v][newCount] = d + w;

					pq.offer(new long[] { distance[v][newCount], v, newCount });
				}
			}
		}

		long minWeight = Integer.MAX_VALUE;

		for (int i = 1; i <= k; i++) {
			minWeight = Math.min(minWeight, distance[n - 1][i]);
		}

		return minWeight == Integer.MAX_VALUE ? -1 : (int) minWeight;
	}

}
