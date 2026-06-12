package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * P721. Accounts Merge - Medium
 * 
 * Given a list of accounts where each element accounts[i] is a list of 
 * strings, where the first element accounts[i][0] is a name, and the 
 * rest of the elements are emails representing emails of the account.
 * 
 * Now, we would like to merge these accounts. Two accounts definitely belong 
 * to the same person if there is some common email to both accounts. Note that 
 * even if two accounts have the same name, they may belong to different people 
 * as people could have the same name. A person can have any number of accounts 
 * initially, but all of their accounts definitely have the same name.
 * 
 * After merging the accounts, return the accounts in the following format: 
 * the first element of each account is the name, and the rest of the elements 
 * are emails in sorted order. The accounts themselves can be returned in any order.
 * 
 * Approach - DFS, UnionFind
 * 
 * Documentaion pending
 */
public class P721AccountsMerge {

	static class DisjointSetUnion {

		int[] parent;
		int[] size;

		DisjointSetUnion(int n) {
			parent = new int[n];
			size = new int[n];

			for (int i = 0; i < n; i++) {
				// Initially each group(accounts indices) is its own representative.
				parent[i] = i;
				// size of all groups is initially 1.
				size[i] = 1;
			}
		}

		// To find the representative of group x.
		public int find(int i) {
			if (parent[i] != i) {
				parent[i] = find(parent[i]); // Path compression
			}
			return parent[i];
		}

		// Unite the group that contains "a" with group that contains "b".
		public void unionBySize(int i, int j) {
			int p1 = find(i);
			int p2 = find(j);

			// Union by size: We point the representative of the smaller group to the
			// representative of larger group.
			if (p1 != p2) {
				if (size[p1] >= size[p2]) {
					parent[p2] = p1;
					size[p1] += size[p2];
				} else {
					parent[p1] = p2;
					size[p2] += size[p1];
				}
			}
		}

	}

	public static void main(String[] args) {
		List<List<String>> accounts = Arrays.asList(
				Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"),
				Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"), Arrays.asList("Mary", "mary@mail.com"),
				Arrays.asList("John", "johnnybravo@mail.com"));

//		List<List<String>> accounts = Arrays.asList(Arrays.asList("Gabe", "Gabe0@m.co", "Gabe3@m.co", "Gabe1@m.co"),
//				Arrays.asList("Kevin", "Kevin3@m.co", "Kevin5@m.co", "Kevin0@m.co"),
//				Arrays.asList("Ethan", "Ethan5@m.co", "Ethan4@m.co", "Ethan0@m.co"),
//				Arrays.asList("Hanzo", "Hanzo3@m.co", "Hanzo1@m.co", "Hanzo0@m.co"),
//				Arrays.asList("Fern", "Fern5@m.co", "Fern1@m.co", "Fern0@m.co"));

		List<List<String>> mergedAccountsUnionFind = accountsMergeUnionFind(accounts);
		System.out.println("Union Find: The merged accounts are: " + mergedAccountsUnionFind);

		List<List<String>> mergedAccountsDFS = accountsMergeDFS(accounts);
		System.out.println("DFS: The merged accounts are: " + mergedAccountsDFS);
	}

	// Disjoint Set Union
	// For each person, we want to identify all of the emails that belong to that
	// person. Therefore, every time we find 2 accounts with an email in common,
	// we'll merge the 2 accounts into one. Whenever, we must work with a set of
	// elements (emails) that are connected (belong to tha same user), we should
	// always consider visualizing our input as a graph. Here, converting the input
	// into a graph will facilitiate the process of "merging" 2 accounts. Emails can
	// be represented as nodes, and an edge between nodes will sigify that they
	// belong to the same person. Since, all of the emails in an account belong to
	// the same person, we can connect all of the emails with edges. Thus, each
	// account can be represented by a connected component. Now, if 2 accounts have
	// an email in common, the we can add an edge between the 2 connected
	// components, effectively merging them into 1 connected component.
	private static List<List<String>> accountsMergeUnionFind(List<List<String>> accounts) {
		int n = accounts.size();

		DisjointSetUnion dsu = new DisjointSetUnion(n);

		// Maps email to their component index
		Map<String, Integer> emailGroup = new HashMap<>();
		for (int i = 0; i < n; i++) {
			int size = accounts.get(i).size();

			for (int j = 1; j < size; j++) {
				String email = accounts.get(i).get(j);

				// If it's the 1st time for the email, then assign the component group as the
				// account index.
				if (!emailGroup.containsKey(email)) {
					emailGroup.put(email, i);
				} else {
					// If we've seen this email before, then union this group with the previous
					// group of the email.
					dsu.unionBySize(i, emailGroup.get(email));
				}
			}
		}

		// Store emails corresponding to the component's representative.
		Map<Integer, List<String>> components = new HashMap<>();

		for (String email : emailGroup.keySet()) {
			int group = emailGroup.get(email);

			int groupIndex = dsu.find(group);

			components.computeIfAbsent(groupIndex, k -> new ArrayList<>()).add(email);
		}

		List<List<String>> mergedAccounts = new ArrayList<>();

		// Sort the components one by one and add the account name
		for (int group : components.keySet()) {
			List<String> component = components.get(group);
			Collections.sort(component);

			String name = accounts.get(group).get(0);
			component.add(0, name);
			mergedAccounts.add(component);
		}

		return mergedAccounts;
	}

	// DFS
	// We treat each email as a node in a graph. If 2 emails belong to the same
	// account, we connect them with an edge. Then, every connected component in the
	// graph represents one merged account.
	// ["John", "a@gmail.com", "b@gmail.com", "c@gmail.com"]
	// ["John", "c@gmail.com", "d@gmail.com"]
	// Graph:
	// a -- b
	// |
	// c -- d
	// All emails are connected, so they belong to the same merged account.
	// In adjacency list representation, each email maps to its neighboring emails.
	// The most important idea: Emails belonging to the same person form a connected
	// component in the graph. Reverse edges are needed because: Connected
	// components in DFS require undirected traversal.
	// Time complexity - O(nlogn), where n = total emails, e = total edges. Building
	// graph takes O(E) time, dfs traversal takes O(n + e) time. Sorting emails: for
	// component with k emails O(k log k).
	// Space complexity - O(n + e)
	public static List<List<String>> accountsMergeDFS(List<List<String>> accounts) {
		Map<String, List<String>> graph = new HashMap<>();

		for (List<String> account : accounts) {
			String firstEmail = account.get(1);

			// We build adjacency list by adding edge between first email and all other
			// emails in the account.
			// We start from index 2 as index 1 = 1st email which is already stored in dfs
			// method and remaining emails begin from 2.
			for (int i = 2; i < account.size(); i++) {
				String nextEmail = account.get(i);
				// a -> b and a -> c
				graph.computeIfAbsent(firstEmail, k -> new ArrayList<>()).add(nextEmail);
				// b -> a and c -> a
				// Graph: a -> [b, c] | b -> [a] | c -> [a].
				// Reverse edges are stored as the graph must be undirected.
				// If 2 emails belong to same account: a, b. Then, a can reach b AND b can reach
				// a. Without reverse edges: a -> b. DFS starting from b cannot reach a.
				// With reverse edges, Graph: a <-> b <-> c. Now DFS from any node reaches all
				// others. So, reverse edges are necessary.
				graph.computeIfAbsent(nextEmail, k -> new ArrayList<>()).add(firstEmail);
			}
		}

		// Prevents revisiting emails, infinite recursion and duplicate processing.
		Set<String> visited = new HashSet<>();

		List<List<String>> mergedAccounts = new ArrayList<>();

		// Traverse over all the accounts to store the components.
		for (List<String> account : accounts) {
			String name = account.get(0);
			String firstEmail = account.get(1);

			// Start DFS only once per component.
			// If firstEmail is visited, then it's part of different component. Hence, we
			// perform DFS only if first email is not visited yet.
			if (!visited.contains(firstEmail)) {
				List<String> mergedAccount = new ArrayList<>();
				// We add account name in the 0th index.
				mergedAccount.add(name);

				dfs(mergedAccount, firstEmail, visited, graph);

				// .subList(1, ...), index 0 = name, only emails should be sorted.
				Collections.sort(mergedAccount.subList(1, mergedAccount.size()));
				mergedAccounts.add(mergedAccount);

//				List<String> mergedAccount = new ArrayList<>();
//
//				dfs(graph, mergedAccount, visited, firstEmail);
//
//				Collections.sort(mergedAccount);
//
//				mergedAccount.add(0, name);
//				mergedAccounts.add(mergedAccount);
			}

		}
		return mergedAccounts;
	}

	// Collects all connected emails
	private static void dfs(List<String> mergedAccount, String email, Set<String> visited,
			Map<String, List<String>> graph) {
		// Avoid revisiting
		visited.add(email);
		// Add email to current component
		// Add the email vector that contains the current component's emails.
		mergedAccount.add(email);

		// Checks if node has neighbors, as some email may have no edges.
		// If no neighbors are present, we return DFS.
		if (!graph.containsKey(email)) {
			return;
		}

		// Traverse adjacent emails
		for (String neighbor : graph.get(email)) {
			if (!visited.contains(neighbor)) {
				// Explores all unvisited connected emails
				dfs(mergedAccount, neighbor, visited, graph);
			}
		}

	}

}
