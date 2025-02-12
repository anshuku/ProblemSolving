package LeetCode.Tries.P745PrefixAndSuffixSearch;

import java.util.HashMap;
import java.util.Map;

/*
 * Approach - Insert Suffix { Prefix pairs in the trie
 * 
 * Here all combinations of suffix { word is inserted in the trie.
 * { is used since it comes after all lower case letters in ASCII.
 * This ensures every prefix search is correctly linked to correct suffix
 * Time complexity - O(n*L^2): Preprocessing where n is number of words and L is max length
 * Querying: o(Q*L) since the trie is traversed once and Q is number of queries.
 * Space complexity - O(n*L^2) for size of Trie.
 */
public class P745PrefixAndSuffixSearchSuffixTrieMap {

	static class WordFilter {

		class TrieNode {
			Map<Character, TrieNode> trie;
			int weight;

			TrieNode() {
				trie = new HashMap<>();
				weight = -1;
			}
		}

		TrieNode trie;

		public WordFilter(String[] words) {
			trie = new TrieNode();
			for (int wt = 0; wt < words.length; wt++) {
				String word = words[wt];
				for (int i = 0; i < word.length(); i++) {
					TrieNode node = trie;
					node.weight = wt;
					// Since outer for loop uses word.length() so
					// use another variable suffixWord to store the suffix { word
					String suffixWord = word.substring(i) + "{" + word; // { separator
					for (char c : suffixWord.toCharArray()) {
						node.trie.putIfAbsent(c, new TrieNode());
						node = node.trie.get(c);
						node.weight = wt; // store the weight
					}
				}
			}
		}

		public int f(String pref, String suff) {
			TrieNode node = trie;
			String searchWord = suff + "{" + pref;
			for (char c : searchWord.toCharArray()) {
				if (!node.trie.containsKey(c)) {
					return -1;
				}
				node = node.trie.get(c);
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
