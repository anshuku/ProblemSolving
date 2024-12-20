package LeetCode.Graphs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * P399. Evaluate Division - Medium
 * 
 * You are given an array of variable pairs equations and an array of real numbers values, 
 * where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. 
 * Each Ai or Bi is a string that represents a single variable.
 * 
 * You are also given some queries, where queries[j] = [Cj, Dj] represents 
 * the jth query where you must find the answer for Cj / Dj = ?.
 * 
 * Return the answers to all queries. If a single answer cannot be determined, return -1.0.
 * 
 * Note: The input is always valid. You may assume that evaluating the queries will not 
 * result in division by zero and that there is no contradiction.
 * 
 * Note: The variables that do not occur in the list of equations are undefined, so the 
 * answer cannot be determined for them.
 * 
 * Approach - DFS
 */
public class P399EvaluateDivision {

	static class UnionFind {
		private int[] parent;
		private double[] weight;

		public UnionFind(int size) {
			parent = new int[size];
			weight = new double[size];

			for (int i = 0; i < size; i++) {
				parent[i] = i;
				weight[i] = 1.0;
			}
		}

		public int find(int val) {
			if (val != parent[val]) {
				int oldParent = parent[val];
				parent[val] = find(parent[val]);
				weight[val] *= weight[oldParent];
			}
			return parent[val];
		}

		public void union(int i, int j, double val) {
			int p1 = find(i);
			int p2 = find(j);

			if (p1 != p2) {
				parent[p1] = p2;
				weight[p1] = val * weight[j] / weight[i];
			}
		}

		public double getVal(int i, int j) {
			if (find(i) != find(j)) {
				return -1.0;
			} else {
				return weight[i] / weight[j];
			}
		}
	}

	public static void main(String[] args) {

		List<List<String>> equations = Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("b", "c"));
		double[] values = { 2.0, 3.0 };
		List<List<String>> queries = Arrays.asList(Arrays.asList("a", "c"), Arrays.asList("b", "a"),
				Arrays.asList("a", "e"), Arrays.asList("a", "a"), Arrays.asList("x", "x"));

//		List<List<String>> equations = Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("b", "c"),
//				Arrays.asList("bc", "cd"));
//		double[] values = { 1.5, 2.5, 5.0 };
//		List<List<String>> queries = Arrays.asList(Arrays.asList("a", "c"), Arrays.asList("c", "b"),
//				Arrays.asList("bc", "cd"), Arrays.asList("cd", "bc"), Arrays.asList("bc", "ab"),
//				Arrays.asList("ab", "cd"));

		double[] divisionUFArr = calcEquationUnionFindArr(equations, values, queries);

		System.out.println("Union Find Array: The evaluated division - " + Arrays.toString(divisionUFArr));

		double[] divisionUFMap = calcEquationUnionFindMap(equations, values, queries);

		System.out.println("Union Find Map: The evaluated division - " + Arrays.toString(divisionUFMap));

		double[] divisionDfs = calcEquationDfs(equations, values, queries);

		System.out.println("DFS: The evaluated division - " + Arrays.toString(divisionDfs));

	}

	private static double[] calcEquationUnionFindArr(List<List<String>> equations, double[] values,
			List<List<String>> queries) {
		int n = equations.size();
		int varId = 0;
		Map<String, Integer> map = new HashMap<>();
		for (List<String> equation : equations) {
			String start = equation.get(0);
			String end = equation.get(1);
			if (!map.containsKey(start)) {
				map.put(start, varId++);
			}
			if (!map.containsKey(end)) {
				map.put(end, varId++);
			}
		}

		UnionFind uf = new UnionFind(varId);

		for (int i = 0; i < n; i++) {
			int x = map.get(equations.get(i).get(0));
			int y = map.get(equations.get(i).get(1));
			
			uf.union(x, y, values[i]);
		}

		n = queries.size();
		double[] result = new double[n];
		
		for(int i = 0; i < n; i++) {
			String start = queries.get(i).get(0);
			String end = queries.get(i).get(1);
			
			if(!map.containsKey(start) || !map.containsKey(end)) {
				result[i] = -1.0;
			} else {
				result[i] = uf.getVal(map.get(start), map.get(end));
			}
		}
		
		return result;
	}

	private static double[] calcEquationUnionFindMap(List<List<String>> equations, double[] values,
			List<List<String>> queries) {
		Map<String, String> parent = new HashMap<>();
		Map<String, Double> ratio = new HashMap<>();
		int n = equations.size();
		for (int i = 0; i < n; i++) {
			union(parent, ratio, equations.get(i), values[i]);
		}
		n = queries.size();
		double[] result = new double[n];
		for (int i = 0; i < n; i++) {
			String start = queries.get(i).get(0);
			String end = queries.get(i).get(1);
			if (!parent.containsKey(start) || !parent.containsKey(end)
					|| !find(parent, ratio, start).equals(find(parent, ratio, end))) {
				result[i] = -1.0;
			} else {
				result[i] = ratio.get(start) / ratio.get(end);
			}
		}
		return result;
	}

	private static void union(Map<String, String> parent, Map<String, Double> ratio, List<String> list, double val) {
		String start = list.get(0);
		String end = list.get(1);

		if (!parent.containsKey(start)) {
			parent.put(start, start);
			ratio.put(start, 1.0);
		}
		if (!parent.containsKey(end)) {
			parent.put(end, end);
			ratio.put(end, 1.0);
		}

		String p1 = find(parent, ratio, start);
		String p2 = find(parent, ratio, end);

		parent.put(p1, p2);
		ratio.put(p1, val * ratio.get(end) / ratio.get(start));
	}

	private static String find(Map<String, String> parent, Map<String, Double> ratio, String s) {
		if (s.equals(parent.get(s))) {
			return s;
		}
		String father = parent.get(s);
		String grandpa = find(parent, ratio, father);
		parent.put(s, grandpa);
		ratio.put(s, ratio.get(s) * ratio.get(father));
		return grandpa;
	}

	public static double[] calcEquationDfs(List<List<String>> equations, double[] values, List<List<String>> queries) {
		int n = queries.size();
		double[] result = new double[n];
		Map<String, Map<String, Double>> graph = getGraph(equations, values);
		for (int i = 0; i < n; i++) {
			result[i] = getWeight(queries.get(i).get(0), queries.get(i).get(1), new HashSet<String>(), graph);
		}
		return result;
	}

	private static double getWeight(String start, String end, Set<String> visited,
			Map<String, Map<String, Double>> graph) {
		if (!graph.containsKey(start) || !graph.containsKey(end)) {
			return -1.0;
		}
		if (graph.get(start).containsKey(end)) {
			return graph.get(start).get(end);
		}
		visited.add(start);
		for (Map.Entry<String, Double> neighbour : graph.get(start).entrySet()) {
			if (!visited.contains(neighbour.getKey())) {
				double val = getWeight(neighbour.getKey(), end, visited, graph);
				if (val != -1.0) {
					return val * neighbour.getValue();
				}
			}
		}
		return -1.0;
	}

	private static Map<String, Map<String, Double>> getGraph(List<List<String>> equations, double[] values) {
		Map<String, Map<String, Double>> graph = new HashMap<String, Map<String, Double>>();
		int i = 0;
		for (List<String> list : equations) {
			String u = list.get(0);
			String v = list.get(1);

			graph.putIfAbsent(u, new HashMap<>());
			graph.get(u).put(v, values[i]);

			graph.putIfAbsent(v, new HashMap<>());
			graph.get(v).put(u, 1 / values[i]);
			i++;
		}
		return graph;
	}

}
