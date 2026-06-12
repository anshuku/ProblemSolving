package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * P1061. Lexicographically Smallest Equivalent String - Medium
 * 
 * You are given two strings of the same length s1 and s2 and a string baseStr.
 * 
 * We say s1[i] and s2[i] are equivalent characters.
 * > For example, if s1 = "abc" and s2 = "cde", then we have 'a' == 'c', 'b' == 'd', and 'c' == 'e'.
 * 
 * Equivalent characters follow the usual rules of any equivalence relation:
 * > Reflexivity: 'a' == 'a'.
 * > Symmetry: 'a' == 'b' implies 'b' == 'a'.
 * > Transitivity: 'a' == 'b' and 'b' == 'c' implies 'a' == 'c'.
 * 
 * For example, given the equivalency information from s1 = "abc" and 
 * s2 = "cde", "acd" and "aab" are equivalent strings of baseStr = "eed", 
 * and "aab" is the lexicographically smallest equivalent string of baseStr.
 * 
 * Return the lexicographically smallest equivalent string of 
 * baseStr by using the equivalency information from s1 and s2.
 * 
 * Approach - DFS: graph colouring, DisjointSetUnion
 */
public class P1061LexicographicallySmallestEquivalentString {

	static class UnionFind {

		// Used for finding representative
		int[] parent;

		UnionFind() {
			parent = new int[26];

			// Make each character as representative of itself.
			for (int i = 0; i < 26; i++) {
				parent[i] = i;
			}
		}

		// Returns the root representative of the component.
		public int find(int i) {
			if (parent[i] != i) {
				parent[i] = find(parent[i]);
			}
			return parent[i];
		}

		// Perform union if x and y aren't in the same component.
		public void union(int i, int j) {
			int p1 = find(i);
			int p2 = find(j);

			if (p1 != p2) {
				// We make the smaller character as the representative.
				if (p1 < p2) {
					parent[p2] = p1;
				} else {
					parent[p1] = p2;
				}
			}
		}
	}

	public static void main(String[] args) {
//		String s1 = "parker", s2 = "morris", baseStr = "parser";

//		String s1 = "hello", s2 = "world", baseStr = "hold";

		String s1 = "leetcode", s2 = "programs", baseStr = "sourcecode";

//		String s1 = "abc", s2 = "bcd", baseStr = "abcd";

		String smallestStringUnionFind = smallestEquivalentStringUnionFind(s1, s2, baseStr);
		System.out.println("UnionFind: The smallest equivalent string for baseStr is: " + smallestStringUnionFind);

		String smallestStringDFS = smallestEquivalentStringDFS(s1, s2, baseStr);
		System.out.println("DFS: The smallest equivalent string for baseStr is: " + smallestStringDFS);

		String smallestStringDFSAlt = smallestEquivalentStringDFSAlt(s1, s2, baseStr);
		System.out.println("DFS Alt: The smallest equivalent string for baseStr is: " + smallestStringDFSAlt);
	}

	// Disjoint set union
	// It's a graph problem where characters can be represented as the nodes and the
	// equivalence relation between these characters as an edge betweeen the nodes.
	// We can have some connected components. Each character in a component can be
	// converted to any other character in the component. Since, we need to find the
	// lexicographically smallest string, we'll find the smallest character in the
	// component and convert every other character instance in the baseStr to this
	// character. This way, we ensure that each character in the string baseStr gets
	// converted to the smallest possible character, and hence we get the smallest
	// string as a whole. We find the connected components in a graph is using a
	// Disjoin Set Union(DSU) data structure. Instead of using an adjacency matrix,
	// we'll perform a union operation for all the edges. In the end, the nodes that
	// can be converted to each other will be in the same component. We also need to
	// find thte smallest character in each component. For this, we change how we
	// perfrom union. While merging 2 components, we'll always make the smaller
	// character representative of the whole merged group. Although generally, we
	// perform union by size to have almost constant time (Inverse Ackermann ⍺(n),
	// which is practicaly O(1)) operations, but owning to the smaller
	// constraints(26 lowercase English characters), we can still have constant time
	// complexity. For larger constraints, we can still follow the union by size and
	// have a different method to fetch the components by finding representative of
	// each character. However, for this problem, we'll not use union by size as
	// it's not going to improve the time complexity by much.
	// Time complexity - O((N + M)*log|Σ|), here n is length of string s1 and s2, M
	// is the length of string baseStr, and |Σ|, is the number of unique characters
	// in s1 or s2, which is 26. We perform the union operation for all the N
	// characters in the string s1 and s2. Since we didn't use union by size and
	// only have the path compression, the time complexity for the union operation
	// would be equal to O(log|Σ|). Also, we iterate over the characters in baseStr
	// and call the find() operation which costs O(M*log|Σ|) in total.
	// Space complexity - O(|Σ|), the only space needed is the list of size |Σ|
	// representative to store the representatives, and hence the total space
	// complexity is constant.
	// For both the solutions, since the strings s1 and s2 can oly have lowercase
	// English letters (constant). The time complexity of both the solution is O(N +
	// M), and space complexity as constant.
	private static String smallestEquivalentStringUnionFind(String s1, String s2, String baseStr) {
		int n = s1.length();

		char[] s1Arr = s1.toCharArray();
		char[] s2Arr = s2.toCharArray();

		UnionFind uf = new UnionFind();

		// Perform union merge for all the edges.
		for (int i = 0; i < n; i++) {
			int a = s1Arr[i] - 'a';
			int b = s2Arr[i] - 'a';

			uf.union(a, b);
		}

		StringBuilder sb = new StringBuilder();

		// Create the answer string with all the mappings
		for (char c : baseStr.toCharArray()) {
			int ch = uf.find(c - 'a');

			sb.append((char) (ch + 'a'));
		}

		return sb.toString();
	}

	static int minChar;

	// DFS - minima tracking globally
	// The problem is to build the graph and find the smallest character in each
	// connected component. In the end, we replace the characters in baseStr with
	// the corresponding smallest character in its component. We can use DFS to find
	// the connected components.
	// Time complexity - O(N + M + |Σ|^2), here n is length of string s1 and s2, M
	// is the length of string baseStr, and |Σ|, is the number of unique characters
	// in s1 or s2, which is 26. We first iterate over string s1 and s2, which costs
	// us O(N). The DFS, in the worst case, will perform |Σ|^2, number of operations
	// as we iterate over all the 26 characters and can perform DFS for all of them,
	// leading to another 26 operations for each character. In the end, we iterate
	// over the string baseStr to create the ans string; this costs O(M).
	// Space complexity - O(|Σ|^2), The adjacency matrix size is |Σ|*|Σ| = 26*26 =
	// 676. The size of mappingChar and visited is also at |Σ|. The recursion call
	// stack space used by DFS will also be at max |Σ|, which is 1 recursive call
	// for each character.
	public static String smallestEquivalentStringDFS(String s1, String s2, String baseStr) {
		int n = s1.length();

		// Adjacency matrix to store edges.
		int[][] adjMatrix = new int[26][26];

		char[] s1Arr = s1.toCharArray();
		char[] s2Arr = s2.toCharArray();

		for (int i = 0; i < n; i++) {
			int a = s1Arr[i] - 'a';
			int b = s2Arr[i] - 'a';

			adjMatrix[a][b] = 1;
			adjMatrix[b][a] = 1;
		}

		// Array to store the final character mappings.
		int[] mapping = new int[26];

		for (int i = 0; i < 26; i++) {
			mapping[i] = i;
		}

		// Array to keep visited nodes during DFS.
		int[] visited = new int[26];

		for (int i = 0; i < 26; i++) {
			if (visited[i] == 0) {
				// Store the characters in the current component.
				List<Integer> component = new ArrayList<>();

				// Variable to store the minimum character in the component.
				minChar = 27;
				dfs(i, visited, component, adjMatrix);

				// Map the characters in the component to the minimum character.
				for (int node : component) {
					mapping[node] = minChar;
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		// Create the answer string
		for (char c : baseStr.toCharArray()) {
			char ch = (char) (mapping[c - 'a'] + 'a');
			sb.append(ch);
		}
		return sb.toString();
	}

	private static void dfs(int node, int[] visited, List<Integer> component, int[][] adjMatrix) {
		// Mark the character as visited
		visited[node] = 1;

		// Add it to the list
		component.add(node);

		// Update the minimum character in the component.
		minChar = Math.min(minChar, node);

		for (int i = 0; i < 26; i++) {
			// Perform DFS if the edge exists and the neighbor isn't visited yet.
			if (adjMatrix[node][i] == 1 && visited[i] == 0) {
				dfs(i, visited, component, adjMatrix);
			}
		}
	}

	// DFS - Graph colouring
	// We find connected components using colours, then we need to compute the
	// smallest character in each component and then replace every character in
	// baseStr with that smallest character.
	// Example: s1 = "abc", s2 = "bcd". The equivalence relations: a <-> b, b <-> c,
	// c <-> d. The graph looks like: a -- b -- c -- d. All 4 characters belong to
	// the same connected component. Step 1. DFS assigns colours, suppose DFS starts
	// from 'a' and uses colour 0. After DFS: a b c d e f g h... 0 0 0 0 1 2 3...
	// The colours array would be: colours[0] = 0; //a | colours[1] = 0; //b |
	// colours[2] = 0; //c | colours[3] = 0; //d | colours[4] = 1; //e | colours[5]
	// = 2; //f. Interpretation: colour 0 => {a,b,c,d} | colour 1 => {e} | colour 2
	// => {f}... Step2: Create smallest array: Suppose colour = 23. Initially:
	// smallest = [∞, ∞, ∞, ∞, ∞, ...]. Step 3: Find smallest letter in each
	// component: smallest[colours[i]] = Math.min(smallest[colours[i]], i); Let's
	// see the 1st few iterations. i = 0('a'), colours[0] = 0, smallest[0] = min(∞,
	// 0) = 0. i = 1 ('b'). colours[1] = 0. smallest[0] = min(0, 1) = 0, no change.
	// i = 2 ('c'), smallest[0] = min(0, 2) = 0, no change. i = 3 ('d'),
	// smallest[0] = min(0, 3) = 0, no change. i = 4 ('e'), colours[4] = 1,
	// smallest[1] = min(∞, 4) = 4. After while loop: smallest[0] = 0 // a,
	// smallest[1] = 4 // e, smallest[2] = 5 // f, smallest[3] = 6 // g. Meaning:
	// Component 0 -> smallest letter is 'a'. Component 1 -> smallest letter is 'e'.
	// Component 2 -> smallest letter is 'f'. Step 4: Build answer, Suppose, baseStr
	// = "dcba". For 'd': c - 'a' = 3, component = colours[3] = 0, smallest[0] = 0
	// Append (char) ('a' + 0) which is: 'a'. For 'c': c - 'a' = 3, component =
	// colours[3] = 0, smallest[0] = 0, append 'a'. For 'b', append 'a'. For 'a',
	// append 'a'. Result "aaaa". Intuition: colours[letter], answers, which group /
	// component does this letter belong to? and smallest[component] as answering
	// what is the smallest letter in that group? So the lookup chain is: letter ->
	// component = colours[letter] | component -> smallest letter =
	// smallest[component]. Example: 'd' -> component 0 -> smallest[0] = 'a'.
	// Therefore, 'd' -> 'a'.
	private static String smallestEquivalentStringDFSAlt(String s1, String s2, String baseStr) {
		int n = s1.length();

		char[] s1Arr = s1.toCharArray();
		char[] s2Arr = s2.toCharArray();

		List<Integer>[] graph = new ArrayList[26];

		for (int i = 0; i < n; i++) {
			int a = s1Arr[i] - 'a';
			int b = s2Arr[i] - 'a';

			if (graph[a] == null) {
				graph[a] = new ArrayList<>();
			}
			graph[a].add(b);

			if (graph[b] == null) {
				graph[b] = new ArrayList<>();
			}
			graph[b].add(a);
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

		// We find the smallest character in each connected component.
		// The smallest size can be equal to number of distinct colours.
		int[] smallest = new int[colour];
		Arrays.fill(smallest, Integer.MAX_VALUE);

		// Check all alphabets
		for (int i = 0; i < 26; i++) {
			smallest[colours[i]] = Math.min(smallest[colours[i]], i);
		}

		StringBuilder sb = new StringBuilder();

		for (char c : baseStr.toCharArray()) {
			int component = colours[c - 'a'];
			char smallestChar = (char) (smallest[component] + 'a');
			sb.append(smallestChar);
		}

		return sb.toString();
	}

	// Assigns colours
	private static void dfs(int node, int colour, int[] colours, List<Integer>[] graph) {
		colours[node] = colour;

		if (graph[node] == null) {
			return;
		}

		for (int neighbor : graph[node]) {
			if (colours[neighbor] == -1) {
				dfs(neighbor, colour, colours, graph);
			}
		}
	}
}
