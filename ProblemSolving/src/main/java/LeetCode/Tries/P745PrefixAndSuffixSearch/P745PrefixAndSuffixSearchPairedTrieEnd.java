package LeetCode.Tries.P745PrefixAndSuffixSearch;

import java.util.HashMap;
import java.util.Map;

/*
 * Approach - Insert Prefix, Suffix pairs in the trie
 * 
 * Here all combinations of prefix and suffix is inserted so that
 * This ensures every prefix search is correctly linked to correct suffix
 * Time complexity - O(n*L^2): Preprocessing where n is number of words and L is max length
 * Querying: o(Q*L): Querying since the trie is traversed once
 * Space complexity - O(n*L^2) for size of Trie
 */
public class P745PrefixAndSuffixSearchPairedTrieEnd {

	static class TrieNode {
		Map<Integer, TrieNode> trie;
		int weight;

		public TrieNode() {
			trie = new HashMap<>();
			weight = 0;
		}
	}

	static class WordFilter {

		TrieNode trie;

		public WordFilter(String[] words) {
			trie = new TrieNode();
			int wt = 0;
			for (String word : words) {
				TrieNode curr = trie;
				int L = word.length();
				curr.weight = wt;
				char[] charArr = word.toCharArray();

				// This modifiex where we begin prefix and suffix insertion
				// i=0, prefix apple and suffix apple are inserted
				// i=1, prefix pple and suffix appl are inserted
				// i=2, prefix ple and suffix app are inserted
				for (int i = 0; i < L; i++) {
					TrieNode node = curr;

					// Insert prefixes
					// j = i to insert partial prefixes
					// if prefix ple is searched then it's matched with apple
					for (int j = i; j < L; j++) {
						int code = (charArr[j] - '`') * 27;
						node.trie.putIfAbsent(code, new TrieNode());
						node = node.trie.get(code);
						node.weight = wt;
					}

					node = curr;

					// Insert suffixes
					for (int k = L - i - 1; k >= 0; k--) {
						int code = (charArr[k] - '`');
						node.trie.putIfAbsent(code, new TrieNode());
						node = node.trie.get(code);
						node.weight = wt;
					}

					// Store both prefix and suffix together
					// To handle characters not present in suffix and prefix
					int code = (charArr[i] - '`') * 27 + (charArr[L - i - 1] - '`');
					curr.trie.putIfAbsent(code, new TrieNode());
					curr = curr.trie.get(code);
					curr.weight = wt;
				}
				wt++;
			}
		}

		public int f(String pref, String suff) {
			TrieNode node = trie;
			int i = 0;
			int j = suff.length() - 1;
			while (i < pref.length() || j >= 0) {
				char c1 = i < pref.length() ? pref.charAt(i) : '`';
				char c2 = j >= 0 ? suff.charAt(j) : '`';
				int code = (c1 - '`') * 27 + (c2 - '`');
				node = node.trie.get(code);
				if (node == null) {
					return -1;
				}
				i++;
				j--;
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
