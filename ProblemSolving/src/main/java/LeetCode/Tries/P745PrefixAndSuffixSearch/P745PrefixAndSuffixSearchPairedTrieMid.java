package LeetCode.Tries.P745PrefixAndSuffixSearch;

import java.util.HashMap;
import java.util.Map;

/*
 * Approach - Insert Suffix, Prefix pairs in the trie
 * 
 * Here all combinations of suffix # word is inserted so that
 * This ensures every prefix search is correctly linked to correct suffix
 * Time complexity - O(n*L^2): Preprocessing where n is number of words and L is max length
 * Querying: o(Q*L): Querying since the trie is traversed once
 * Space complexity - O(n*L^2) for size of Trie
 */
public class P745PrefixAndSuffixSearchPairedTrieMid {

	static class WordFilter {

		class TrieNode {
			Map<Integer, TrieNode> trie;
			int weight;

			TrieNode() {
				trie = new HashMap<>();
				weight = -1;
			}
		}

		TrieNode trie;

		public WordFilter(String[] words) {
			trie = new TrieNode();
			int wt = 0;
			for (String word : words) {
				int L = word.length();
				// Insert all suffix#prefix combinations
				for (int i = 0; i <= L; i++) { // i = 0 ensures entire suffix is included
					// i = L ensures no suffix is included
					TrieNode curr = trie;
					curr.weight = wt; // store word index at root

					// insert suffix first
					for (int j = i; j < L; j++) {
						int code = (word.charAt(j) - '`') * 27;
						curr.trie.putIfAbsent(code, new TrieNode());
						curr = curr.trie.get(code);
						curr.weight = wt; // store max index at each node
					}

					// Insert separator #
					int sepCode = 27 * 27; // Unique separator to avoid collisions
					curr.trie.putIfAbsent(sepCode, new TrieNode());
					curr = curr.trie.get(sepCode);
					curr.weight = wt;

					// insert full prefix
					for (char c : word.toCharArray()) {
						int code = (c - '`');
						curr.trie.putIfAbsent(code, new TrieNode());
						curr = curr.trie.get(code);
						curr.weight = wt;
					}
				}
				wt++;
			}
		}

		public int f(String pref, String suff) {
			TrieNode node = trie;
			// first find suffix
			for (char c : suff.toCharArray()) {
				int code = (c - '`') * 27;
				if (node.trie.get(code) == null) { // or !node.trie.containsKey(code)
					return -1;
				}
				node = node.trie.get(code);
			}

			// then fix #
			int sepCode = 27 * 27;
			if (node.trie.get(sepCode) == null) {
				return -1;
			}
			node = node.trie.get(sepCode);

			// then find prefix at list. The preprocessing ensure when
			// prefix is searched it's already linked to suffix
			for (char c : pref.toCharArray()) {
				int code = (c - '`');
				if (!node.trie.containsKey(code)) { // node.trie.get(code) == null
					return -1;
				}
				node = node.trie.get(code);
			}
			return node.weight;

		}
	}

	public static void main(String[] args) {
		String[] words = { "apple", "appe", "ace" };
		WordFilter wordFilter = new WordFilter(words);

		int index = wordFilter.f("a", "e");

		System.out.println("The index of word with prefix a and e is " + index);
	}
}
