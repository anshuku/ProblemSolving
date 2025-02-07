package LeetCode.Tries.P211DesignAddSearchWordsDataStructure;

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
public class P211DesignAddSearchWordsDataStructureArrayTrie {

	static class WordDictionary {

		WordDictionary[] trie;
		boolean isEndOfWord;

		WordDictionary() {
			trie = new WordDictionary[26];
		}

		public void addWord(String word) {
			WordDictionary trie = this;
			for (char c : word.toCharArray()) {
				if (trie.trie[c - 'a'] == null) {
					trie.trie[c - 'a'] = new WordDictionary();
				}
				trie = trie.trie[c - 'a'];
			}
			trie.isEndOfWord = true;
		}

		// Time complexity - Best case O(N) when no . is present
		// worst case - 26N when 1 . is present and 26^2^N or 676N for 2 . 
		public boolean search(String word) {
			return dfs(this, word.toCharArray(), 0);
		}

		private boolean dfs(WordDictionary node, char[] word, int index) {
			if (node == null) {
				return false;
			}
			if (word.length == index) {
				return node.isEndOfWord;
			}
			char c = word[index];
			if (c == '.') {
				for (WordDictionary child : node.trie) {
					if (child != null && dfs(child, word, index + 1)) {
						return true;
					}
				}
				return false;
			} else {
				return dfs(node.trie[c - 'a'], word, index + 1);
			}
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
