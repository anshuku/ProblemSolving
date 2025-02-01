package LeetCode.Tries;

/*
 * P208. Implement Trie (Prefix Tree)
 * 
 * A trie (pronounced as "try") or prefix tree is a tree data structure used 
 * to efficiently store and retrieve keys in a dataset of strings. There are 
 * various applications of this data structure, such as autocomplete and spellchecker.
 * 
 * Implement the Trie class:
 * - Trie() Initializes the trie object.
 * - void insert(String word) Inserts the string word into the trie.
 * - boolean search(String word) Returns true if the string word is 
 * in the trie (i.e., was inserted before), and false otherwise.
 * - boolean startsWith(String prefix) Returns true if there is a previously 
 * inserted string word that has the prefix prefix, and false otherwise.
 * 
 * Approach - Moving pointer, Recursion, Array element's traversal
 */
public class P208ImplementTriePrefixTree {

	Node trie;

	P208ImplementTriePrefixTree() {
		trie = new Node();
	}

	class Node {
		boolean checkEnd;
		Node[] node;

		Node() {
			node = new Node[26];
		}

		public void insert(String word, int index) {
			if (index == word.length()) {
				return;
			}
			int i = word.charAt(index) - 'a';
			if (node[i] == null) {
				node[i] = new Node();
			}
			if (index == word.length() - 1) {
				node[i].checkEnd = true;
			}
			// node[i] will be passed in next method call with freshly created values.
			// Otherwise same node will be used and pointer to the index won't work.
			node[i].insert(word, index + 1);
		}

		public boolean search(String word, int index) {
			if (index == word.length()) {
				return false;
			}
			int i = word.charAt(index) - 'a';
			if (node[i] == null) {
				return false;
			}
			if (index == word.length() - 1) {
				return node[i].checkEnd;
			}
			return node[i].search(word, index + 1);
		}

		public boolean startsWith(String prefix, int index) {
			if (index == prefix.length()) {
				return true;
			}
			int i = prefix.charAt(index) - 'a';
			if (node[i] == null) {
				return false;
			}
			return node[i].startsWith(prefix, index + 1);
		}

	}

	public void insert(String word) {
		trie.insert(word, 0);
	}

	public boolean search(String word) {
		return trie.search(word, 0);
	}

	public boolean startsWith(String prefix) {
		return trie.startsWith(prefix, 0);
	}

	public static void main(String[] args) {

		P208ImplementTriePrefixTree node = new P208ImplementTriePrefixTree();

		String word1 = "and";
		String word2 = "ant";

		node.insert(word1);
		node.insert(word2);

		boolean isPresent1 = node.search("and");
		System.out.println("The word and is present: " + isPresent1);
		boolean isPresent2 = node.search("an");
		System.out.println("The word an is present: " + isPresent2);
		boolean isPresent3 = node.search("andrew");
		System.out.println("The word andrew is present: " + isPresent3);
		boolean isPresent4 = node.search("ant");
		System.out.println("The word ant is present: " + isPresent4);
		boolean isPresent5 = node.search("atn");
		System.out.println("The word atn is present: " + isPresent5);

		boolean startsWith1 = node.startsWith("and");
		System.out.println("The word and is a prefix present in trie: " + startsWith1);
		boolean startsWith2 = node.startsWith("an");
		System.out.println("The word an is a prefix present in trie: " + startsWith2);
		boolean startsWith3 = node.startsWith("a");
		System.out.println("The word a is a prefix present in trie: " + startsWith3);
		boolean startsWith4 = node.startsWith("");
		System.out.println("The empty word is a prefix present in trie: " + startsWith4);
		boolean startsWith5 = node.startsWith("andrew");
		System.out.println("The word andrew is a prefix present in trie: " + startsWith5);
		boolean startsWith6 = node.startsWith("atn");
		System.out.println("The word atn is a prefix present in trie: " + startsWith6);

	}

}
