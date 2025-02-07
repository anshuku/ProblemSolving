package LeetCode.Tries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * P1268. Search Suggestions System - Medium
 * 
 * You are given an array of strings products and a string searchWord.
 * 
 * Design a system that suggests at most three product names from products 
 * after each character of searchWord is typed. Suggested products should 
 * have common prefix with searchWord. If there are more than three products 
 * with a common prefix return the three lexicographically minimums products.
 * 
 * Return a list of lists of the suggested products after each character of searchWord is typed.
 * 
 * Approach - Binary Search; Trie and DFS
 */
public class P1268SearchSuggestionsSystem {

	static class Trie {
		Trie[] trie;
		boolean checkEnd;

		Trie() {
			trie = new Trie[26];
		}

		public void insertion(String word) {
			Trie curr = this;
			for (char c : word.toCharArray()) {
				if (curr.trie[c - 'a'] == null) {
					curr.trie[c - 'a'] = new Trie();
				}
				curr = curr.trie[c - 'a'];
			}
			curr.checkEnd = true;
		}

		public Trie startsWith(String prefix) {
			Trie curr = this;
			for (char c : prefix.toCharArray()) {
				if (curr.trie[c - 'a'] == null) {
					return null;
				}
				curr = curr.trie[c - 'a'];
			}
			return curr;
		}
	}

	public static void main(String[] args) {
		String[] products = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
		String searchWord = "mouse";

		List<List<String>> suggestedBS = suggestedProductsBS(products, searchWord);
		System.out.println("Binary Search: The suggested products are: " + suggestedBS);

		List<List<String>> suggestedTrieDFS = suggestedProductsTrieDFS(products, searchWord);
		System.out.println("Trie DFS: The suggested products are: " + suggestedTrieDFS);
	}

	// Binary search
	// Find the first occurence of prefix in the products array.
	// If there is a word then find the next 2 occurrences.
	// Time complexity - O(nlogn) + O(mlogn) where n is products length and
	// m is searchWord's length. O(nlogn) is for sorting and
	// O(mlogn) is for binary search in products for each prefix
	// Space complexity - Depends on the algo used and O(n)? in java
	// O(m) for storing searchword.
	public static List<List<String>> suggestedProductsBS(String[] products, String searchWord) {
		Arrays.sort(products);
		List<List<String>> result = new ArrayList<>();
		int n = products.length;
		int start = 0;
		int end = n - 1;
		for (int i = 0; i < searchWord.length(); i++) {
			String prefix = searchWord.substring(0, i + 1);
			List<String> list = new ArrayList<>();
			while (start <= end) {
				int mid = start + (end - start) / 2;
				if (products[mid].compareTo(prefix) < 0) {
					start = mid + 1;
				} else {
					end = mid - 1;
				}
			}
			if (start < n && products[start].startsWith(prefix)) {
				list.add(products[start]);
				if (start + 1 < n && products[start + 1].startsWith(prefix)) {
					list.add(products[start + 1]);
				}
				if (start + 2 < n && products[start + 2].startsWith(prefix)) {
					list.add(products[start + 2]);
				}
			}
			end = n - 1;
			result.add(list);
		}
		return result;
	}

	// Trie and DFS
	// Involves searching prefix - trie ensures lexicographically sorted order.
	// A trie word is represented by it's preorder traversal
	// A preorder traversal of a trie always leads to sorted traversal.
	// Here the words traversal needs to be limited to 3.
	// Time complexity - O(M) where M is total number of elements in products.
	// For each prefix, it's representative node in trie is found in O(len(prefix))
	// DFS to find at most 3 elements is O(1)
	// Space complexity - O(26n) where n is the number of node in trie.
	private static List<List<String>> suggestedProductsTrieDFS(String[] products, String searchWord) {
		int n = products.length;
		List<List<String>> result = new ArrayList<>();
		Trie trie = new Trie();
		for (int i = 0; i < n; i++) {
			trie.insertion(products[i]);
		}
		StringBuilder prefix = new StringBuilder();
		for (char c : searchWord.toCharArray()) {
			prefix.append(c);
			Trie curr = trie.startsWith(prefix.toString());
			if (curr != null) {
				List<String> list = new ArrayList<>();
				dfs(list, prefix, curr);
				result.add(list);
			} else {
				// If there is no node for the given prefix then end further search
				// As all further searches will lead to empty lists.
				while (searchWord.length() > result.size()) {
					result.add(new ArrayList<>());
				}
				break;
			}

		}
		return result;
	}

	private static void dfs(List<String> list, StringBuilder prefix, Trie curr) {
		if (list.size() == 3) {
			return;
		}
		if (curr.checkEnd) {
			list.add(prefix.toString());
		}
		for (char c = 'a'; c <= 'z'; c++) {
			// Keeping child separate from curr to keep curr fixed for a level
			Trie child = curr.trie[c - 'a'];
			if (child != null) {
				prefix.append(c);
				dfs(list, prefix, child);
				// Backtrack to check other branches
				prefix.deleteCharAt(prefix.length() - 1);
			}
		}
	}

}
