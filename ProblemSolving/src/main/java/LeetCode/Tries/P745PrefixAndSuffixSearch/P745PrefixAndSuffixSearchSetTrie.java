package LeetCode.Tries.P745PrefixAndSuffixSearch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
 * Approach - Trie, Set Intersection
 * 
 * Two tries are used to seaparately find all words that match prefix and all words that match suffix.
 * Then, the highest weight is found in the intersection of these sets.
 * 
 * Time complexity - O(n*k + Q*(n+k)) where n is number of words and k is average/max length of word
 * and Q is number of queries. O(n*k) is insert complexity and O(Q*(n+K)) is search complexity.
 * Space complexity - O(n*k) for storing words and weight in trie.
 */
public class P745PrefixAndSuffixSearchSetTrie {

	static class WordFilter {

		TrieNode trieForward; // prefix tree
		TrieNode trieBackward; // suffix tree - stores word in reverse order

		class TrieNode {
			TrieNode[] trie;
			// Each node maintains a set of indices of words that pass through it.
			Set<Integer> weight;

			TrieNode() {
				trie = new TrieNode[26];
				weight = new HashSet<>();
			}
		}

		public WordFilter(String[] words) {
			trieForward = new TrieNode();
			trieBackward = new TrieNode();
			int wt = 0;
			for (String word : words) {
				TrieNode curr = trieForward;
				curr.weight.add(wt); // Store the index
				for (char c : word.toCharArray()) {
					if (curr.trie[c - 'a'] == null) {
						curr.trie[c - 'a'] = new TrieNode();
					}
					curr = curr.trie[c - 'a'];
					curr.weight.add(wt);
				}

				curr = trieBackward;
				curr.weight.add(wt);
				for (int i = word.length() - 1; i >= 0; i--) {
					char c = word.charAt(i);
					if (curr.trie[c - 'a'] == null) {
						curr.trie[c - 'a'] = new TrieNode();
					}
					curr = curr.trie[c - 'a'];
					curr.weight.add(wt);
				}
				wt++;
			}
		}

		Map<String, TrieNode> prefixMap = new HashMap<>();
		Map<String, TrieNode> suffixMap = new HashMap<>();

		public int f(String pref, String suff) {
			// Finds all words with given prefix
			// To get the prefix set
			TrieNode node = trieForward;
			Set<Integer> prefixSet;
			if (prefixMap.get(pref) != null) {
				prefixSet = prefixMap.get(suff).weight;
			} else {
				for (char c : pref.toCharArray()) {
					if (node.trie[c - 'a'] == null) {
						return -1;
					}
					node = node.trie[c - 'a'];
				}
				prefixMap.put(pref, node);
				prefixSet = node.weight;
			}

			// Finds all words with given suffix
			// To get the suffix set
			node = trieBackward;
			Set<Integer> suffixSet;
			if (suffixMap.get(suff) != null) {
				suffixSet = suffixMap.get(suff).weight;
			} else {
				for (int i = suff.length() - 1; i >= 0; i--) {
					char c = suff.charAt(i);
					if (node.trie[c - 'a'] == null) {
						return -1;
					}
					node = node.trie[c - 'a'];
				}
				suffixMap.put(suff, node);
				suffixSet = node.weight;
			}

			int maxWeight = -1;
			// Set intersection to find max index(weight) of word
			for (int wt : prefixSet) {
				if (suffixSet.contains(wt)) {
					maxWeight = Math.max(maxWeight, wt);
				}
			}
			return maxWeight;
		}

	}

	public static void main(String[] args) {
		String[] words = { "apple", "appe", "ace" };
		WordFilter wordFilter = new WordFilter(words);

		int index = wordFilter.f("a", "e");

		System.out.println("The index of word with prefix a and e is " + index);
	}
}
