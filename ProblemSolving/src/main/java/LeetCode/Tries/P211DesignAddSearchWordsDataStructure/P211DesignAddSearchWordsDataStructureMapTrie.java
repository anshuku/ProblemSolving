package LeetCode.Tries.P211DesignAddSearchWordsDataStructure;

import java.util.HashMap;
import java.util.Map;

/*
 * P211. Design Add and Search Words Data Structure - Medium
 * 
 * Design a data structure that supports adding new words and 
 * finding if a string matches any previously added string.
 * 
 * Implement the WordDictionary class:
 * - WordDictionary() Initializes the object.
 * - void addWord(word) Adds word to the data structure, it can be matched later.
 * - bool search(word) Returns true if there is any string in the data structure that matches 
 * word or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 * 
 * Approach - Tries, DFS; HashMap
 */
public class P211DesignAddSearchWordsDataStructureMapTrie {

	static class WordDictionary {

		TrieNode trie;

		public WordDictionary() {
			trie = new TrieNode();
		}

		class TrieNode {
			Map<Character, TrieNode> trie;
			boolean isEndOfWord;

			TrieNode() {
				trie = new HashMap<>();
			}
		}

		// Time complexity - O(m) where m is word's length
		// Space complexity - O(m) where m is new node's created.
		public void addWord(String word) {
			TrieNode curr = trie;
			for (char c : word.toCharArray()) {
				if (!curr.trie.containsKey(c)) {
					curr.trie.put(c, new TrieNode());
				}
				curr = curr.trie.get(c);
			}
			curr.isEndOfWord = true;
		}

		public boolean search(String word) {
			return searchWord(word, trie);
		}

		// In absence of '.' it's as simple as addWord
		// With '.' we've to explore all possible node.
		// Time complexity - we defined words without '.' - O(N*M)
		// where N is number of keys and M is key length
		// for words with '.' it's N*26^m*(M-m) where m is the number of '.'
		// Space complexity - O(1) for well defined words and
		// O(M) for the undefined words to recursion stack.
		private boolean searchWord(String word, TrieNode node) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (node.trie.containsKey(c)) {
					// If character is found go down next level in the trie node
					node = node.trie.get(c);
				} else {
					// If the character is '.' explore all possible node at this level
					if (c == '.') {
//						for (char ch : node.trie.keySet()) {
//							TrieNode child = node.trie.get(ch);
//							if (searchWord(word.substring(i + 1), child)) {
//								return true;
//							}
//						}
						for (TrieNode child : node.trie.values()) {
							if (searchWord(word.substring(i + 1), child)) {
								return true;
							}
						}
					}
					// If the character is not '.' or no nodes leads to answer
					return false;
				}
			}
			return node.isEndOfWord;
		}
	}

	public static void main(String[] args) {

		WordDictionary wordDictionary = new WordDictionary();

		wordDictionary.addWord("bad");
		wordDictionary.addWord("dad");
		wordDictionary.addWord("sit");
		wordDictionary.addWord("matter");

		boolean isPresent1 = wordDictionary.search("pad");
		System.out.println("The word bad is present:" + isPresent1);

		boolean isPresent2 = wordDictionary.search(".ad");
		System.out.println("The word .ad is present:" + isPresent2);

		boolean isPresent3 = wordDictionary.search("b..");
		System.out.println("The word b.. is present:" + isPresent3);

		boolean isPresent4 = wordDictionary.search("s..");
		System.out.println("The word s.. is present:" + isPresent4);

		boolean isPresent5 = wordDictionary.search(".i.");
		System.out.println("The word .i. is present:" + isPresent5);

		boolean isPresent6 = wordDictionary.search("..tter");
		System.out.println("The word ..tter is present:" + isPresent6);
	}
}
