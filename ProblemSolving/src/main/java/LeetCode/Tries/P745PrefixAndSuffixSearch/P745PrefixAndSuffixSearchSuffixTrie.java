package LeetCode.Tries.P745PrefixAndSuffixSearch;

/*
 * Approach - Insert Suffix { Prefix pairs in the trie
 * 
 * Here all combinations of suffix { word is inserted in the trie.
 * { is used since it comes after all lower case letters in ASCII.
 * This ensures every prefix search is correctly linked to correct suffix.
 * Time complexity - O(n*L^2): Preprocessing where n is number of words and L is max length
 * Querying: o(Q*L) since the trie is traversed once and Q is number of queries.
 * Space complexity - O(n*L^2) for size of Trie.
 */
public class P745PrefixAndSuffixSearchSuffixTrie {

	static class TrieNode {
		TrieNode[] trie;
		int weight;

		TrieNode() {
			trie = new TrieNode[27];
			weight = 0;
		}
	}

	static class WordFilter {
		TrieNode trie;

		public WordFilter(String[] words) {
			trie = new TrieNode();
			for (int wt = 0; wt < words.length; wt++) {
				String word = words[wt] + "{";
				for (int i = 0; i < word.length(); i++) {
					TrieNode node = trie;
					node.weight = wt;
					for (int j = i; j < 2 * word.length() - 1; j++) {
						int k = word.charAt(j % word.length()) - 'a';
						if (node.trie[k] == null) {
							node.trie[k] = new TrieNode();
						}
						node = node.trie[k];
						node.weight = wt;
					}
				}
			}
		}

		public int f(String pref, String suff) {
			TrieNode node = trie;
			String word = suff + "{" + pref;
			for (char c : word.toCharArray()) {
				if (node.trie[c - 'a'] == null) {
					return -1;
				}
				node = node.trie[c - 'a'];
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
