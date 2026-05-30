package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
 * P990. Satisfiability of Equality Equations - Medium
 * 
 * You are given an array of strings equations that represent relationships between 
 * variables where each string equations[i] is of length 4 and takes one of two 
 * different forms: "xi==yi" or "xi!=yi". Here, xi and yi are lowercase letters 
 * (not necessarily different) that represent one-letter variable names.
 * 
 * Return true if it is possible to assign integers to variable names 
 * so as to satisfy all the given equations, or false otherwise.
 * 
 * Approach - DisjointSetUnion, DFS: Graph Colour
 */
public class P990SatisfiabilityOfEqualityEquations {

	static class UnionFind {
		int[] parent;

		UnionFind() {
			parent = new int[26];

			for (int i = 0; i < 26; i++) {
				parent[i] = i;
			}
		}

		public int find(int i) {
			if (parent[i] != i) {
				parent[i] = find(parent[i]);
			}
			return parent[i];
		}

		public void union(int i, int j) {
			int p1 = find(i);
			int p2 = find(j);

			parent[p1] = p2;

		}
	}

	public static void main(String[] args) {
		String[] equations = { "a==b", "b!=a" };
//		String[] equations = { "b==a", "a==b" };

		boolean isPossibleUnionFind = equationsPossibleUnionFind(equations);
		System.out.println("Union Find: The equations are possible to be satisfied? " + isPossibleUnionFind);

		boolean isPossibleDFS = equationsPossibleDFS(equations);
		System.out.println("DFS: The equations are possible to be satisfied? " + isPossibleDFS);
	}

	// UnionFind
	// reflective: a==a, must hold for all variable a.
	// symmetric: If a==b holds, then we've b==a.
	// transitive: If a==b and b==c, then we've a==c.
	// If we can build an undirected graph based on the == relationship among all
	// variables by adding an edge between a and b if we've a==b in equations, then
	// all variables, which are represented as vertices in the graph, that can reach
	// from each other must've a relationship of ==. In other words, all variables
	// in a common connected components must share the same value. Our task is to
	// figure out all connected components in the graph. After tjat. we can easily
	// enumerate all equations in the form of x!=y to check if there is any
	// contradiction showing that x and y are indeed in the same connected
	// components in that graph. If no such contradiction, all the given equations
	// can be satisfied; otherwise, no possible values can be assigned to them.
	// In Union find, we maintain a union-find data structure to manage all known
	// connected components so far.
	// Time complexity - O(nlog|̇̇Σ|), the time complexity of performing m-length
	// sequence of find or union operations on n vertices with path compression is
	// O(mlogn). However, for this problem, |Σ| is only 26. Thus overall it's
	// O(nlog26) = O(n) as we invoke find or union methods to upper O(n) times.
	// Space complexity - O(̇̇|Σ|), only a parent array of alphabet size is
	// introduced as extra space here. It's actually O(1) as |Σ| = 26.
	private static boolean equationsPossibleUnionFind(String[] equations) {
		UnionFind uf = new UnionFind();

		for (String equation : equations) {
			char[] eqn = equation.toCharArray();

			if (eqn[1] == '=') {
				int a = eqn[0] - 'a';
				int b = eqn[3] - 'a';

				uf.union(a, b);
			}
		}

		for (String equation : equations) {
			char[] eqn = equation.toCharArray();

			if (eqn[1] == '!') {
				int a = eqn[0] - 'a';
				int b = eqn[3] - 'a';

				if (uf.find(a) == uf.find(b)) {
					return false;
				}
			}
		}
		return true;
	}

	// DFS
	// We obtain the connected components via dfs and color all visited variables in
	// each round of traversal starting from a given variable.
	// Time complexity - O(max(n,|̇̇Σ|)), where n = equations length and Σ or sigma
	// is the alphabet set of variables and |Σ| refers to its size. Here Σ is
	// limited to the space of only 26 lowercase lettters. we regard |Σ| as 26 and
	// final time complexity as O(n).
	// Space complexity - O(max(n,|̇̇Σ|)), though the variables are restricted to Σ,
	// we may still include the duplicated vertices when building the adjacency list
	// graph. As |Σ| is fixed and small, so the time complexity is also O(n).
	// However, we can reduce it to O(|Σ|) or O(1), if we apply a hash set instead
	// of a list as graph[i] to guarnatee all vertices in graph[i] are unique.
	public static boolean equationsPossibleDFS(String[] equations) {
		List<Integer>[] graph = new ArrayList[26];
		for (String equation : equations) {
			char[] eqn = equation.toCharArray();

			if (eqn[1] == '=') {
				int a = eqn[0] - 'a';
				int b = eqn[3] - 'a';

				if (graph[a] == null) {
					graph[a] = new ArrayList<>();
				}
				graph[a].add(b);
				if (graph[b] == null) {
					graph[b] = new ArrayList<>();
				}
				graph[b].add(a);
			}
		}

		int[] colours = new int[26];
		Arrays.fill(colours, -1);

		int colour = 0;

		for (int i = 0; i < 26; i++) {
			if (colours[i] == -1) {
				dfs(i, colour, colours, graph);
				colour++;

			}
		}

		for (String equation : equations) {
			char[] eqn = equation.toCharArray();

			if (eqn[1] == '!') {
				int a = eqn[0] - 'a';
				int b = eqn[3] - 'a';

				if (colours[a] == colours[b]) {
					return false;
				}
			}
		}
		return true;
	}

	private static void dfs(int node, int colour, int[] colours, List<Integer>[] graph) {
		if (colours[node] != -1) {
			return;
		}
		colours[node] = colour;

		if (graph[node] == null) {
			return;
		}

		for (int neighbor : graph[node]) {
			dfs(neighbor, colour, colours, graph);
		}
	}

	private static void dfsAlt(int node, int colour, int[] colours, List<Integer>[] graph) {
		colours[node] = colour;

		if (graph[node] == null) {
			return;
		}

		for (int neighbor : graph[node]) {
			if (colours[neighbor] == -1) {
				dfsAlt(neighbor, colour, colours, graph);
			}
		}
	}
}
