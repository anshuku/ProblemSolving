package LeetCode.Tries.P745PrefixAndSuffixSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * P745. Prefix and Suffix Search - Hard
 * 
 * Design a special dictionary that searches the words in it by a prefix and a suffix.
 * 
 * Implement the WordFilter class:
 * 
 * - WordFilter(string[] words) Initializes the object with the words in the dictionary.
 * - f(string pref, string suff) Returns the index of the word in the dictionary, which 
 *   has the prefix pref and the suffix suff. If there is more than one valid index, return 
 *   the largest of them. If there is no such word in the dictionary, return -1.
 *   
 * Approach - Trie, List Intersection
 * 
 * Two tries are used to seaparately find all words that match prefix and all words that match suffix.
 * Then, the highest weight is found in the intersection of the indices lists.
 * 
 * Time complexity - O(n*k + Q*(n+k)) where n is number of words and k is average/max length of word
 * and Q is number of queries. O(n*k) is insert complexity and O(Q*(n+K)) is search complexity.
 * Space complexity - O(n*k) for storing words and weight in trie.
 */
public class P745PrefixAndSuffixSearchListTrie {

	static class WordFilter {

		class TrieNode {
			TrieNode[] trie;
			List<Integer> indices;

			TrieNode() {
				trie = new TrieNode[26];
				indices = new ArrayList<>();
			}

			void insert(String word, int index) {
				TrieNode node = this;
				for (char c : word.toCharArray()) {
					if (node.trie[c - 'a'] == null) {
						node.trie[c - 'a'] = new TrieNode();
					}
					node = node.trie[c - 'a'];
					node.indices.add(index);
				}
			}

			List<Integer> search(String word) {
				TrieNode node = this;
				for (char c : word.toCharArray()) {
					if (node.trie[c - 'a'] == null) {
						return Collections.emptyList();
					}
					node = node.trie[c - 'a'];
				}
				return node.indices;
			}

		}

		TrieNode prefixTrie;
		TrieNode suffixTrie;

		WordFilter(String[] words) {
			prefixTrie = new TrieNode();
			suffixTrie = new TrieNode();
			int index = 0;
			for (String word : words) {
				prefixTrie.insert(word, index);
				suffixTrie.insert(new StringBuilder(word).reverse().toString(), index);
				index++;
			}
		}

		int f(String pref, String suff) {
			List<Integer> prefixList = prefixTrie.search(pref);
			List<Integer> suffixList = suffixTrie.search(new StringBuilder(suff).reverse().toString());

			int i = prefixList.size() - 1;
			int j = suffixList.size() - 1;

			while (i >= 0 && j >= 0) {
				int prefixIndex = prefixList.get(i);
				int suffixIndex = suffixList.get(j);

				if (prefixIndex == suffixIndex) {
					return prefixIndex;
				} else if (prefixIndex > suffixIndex) {
					i--;
				} else {
					j--;
				}
			}

			return -1;
		}

	}

	public static void main(String[] args) {
		String[] words = { "apple", "appe", "ace" };
		WordFilter wordFilter = new WordFilter(words);

		int index = wordFilter.f("a", "e");

		System.out.println("The index of word with prefix a and e is " + index);
	}
}
