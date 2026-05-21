package LeetCode.Graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * P1615. Maximal Network Rank - Medium
 * 
 * There is an infrastructure of n cities with some number of roads connecting these cities. 
 * Each roads[i] = [ai, bi] indicates that there is a bidirectional road between cities ai and bi.
 * 
 * The network rank of two different cities is defined as the total number of directly connected 
 * roads to either city. If a road is directly connected to both cities, it is only counted once.
 * 
 * The maximal network rank of the infrastructure is the 
 * maximum network rank of all pairs of different cities.
 * 
 * Given the integer n and the array roads, return the 
 * maximal network rank of the entire infrastructure.
 * 
 * Approach - Graph: indegree
 */
public class P1615MaximalNetworkRank {

	public static void main(String[] args) {
//		int n = 4;
//		int[][] roads = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 1, 3 } };

//		int n = 5;
//		int[][] roads = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 1, 3 }, { 2, 3 }, { 2, 4 } };

		int n = 8;
		int[][] roads = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 2, 4 }, { 5, 6 }, { 5, 7 } };

		int maxRankArray = maximalNetworkRankArray(n, roads);
		System.out.println("Array :The maximal network rank of the infra is: " + maxRankArray);

		int maxRankMap = maximalNetworkRankMap(n, roads);
		System.out.println("Map :The maximal network rank of the infra is: " + maxRankMap);
	}

	// Indegree
	// We calculate the degree of each city and the iterate over every possible pair
	// of cities to compute their combined network rank and update the max rank.
	// Time compelxity - O(n^2)
	// Space complexity - O(n) for indegree array and connections 2d
	// array.
	private static int maximalNetworkRankArray(int n, int[][] roads) {
		int[] indegree = new int[n];

		boolean[][] connections = new boolean[n][n];

		for (int[] road : roads) {
			indegree[road[0]]++;
			indegree[road[1]]++;

			connections[road[0]][road[1]] = true;
			connections[road[1]][road[0]] = true;
		}

		int maxArea = 0;

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int area = indegree[i] + indegree[j] - (connections[i][j] ? 1 : 0); // loop all pairs
				maxArea = Math.max(maxArea, area);
			}
		}
		return maxArea;
	}

	// Time complexity - O(n^2)
	// Space complexity - O(E)
	public static int maximalNetworkRankMap(int n, int[][] roads) {
		Map<Integer, Set<Integer>> graph = new HashMap<>();

		for (int[] road : roads) {
			graph.computeIfAbsent(road[0], k -> new HashSet<>()).add(road[1]);
			graph.computeIfAbsent(road[1], k -> new HashSet<>()).add(road[0]);
		}

		int maxRank = 0;

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int currentRank = graph.getOrDefault(i, new HashSet<>()).size()
						+ graph.getOrDefault(j, new HashSet<>()).size();

				if (graph.getOrDefault(i, new HashSet<>()).contains(j)) {
					currentRank--;
				}

				maxRank = Math.max(maxRank, currentRank);
			}
		}

		return maxRank;
	}

}
