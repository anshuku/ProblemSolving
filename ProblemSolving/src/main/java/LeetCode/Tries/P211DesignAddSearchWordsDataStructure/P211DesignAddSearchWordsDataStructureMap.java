package LeetCode.Tries.P211DesignAddSearchWordsDataStructure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public class P211DesignAddSearchWordsDataStructureMap {

	static class WordDictionary {

		Map<Integer, Set<String>> map;

		public WordDictionary() {
			map = new HashMap<Integer, Set<String>>();
		}

		// Map stores all the words mapped to the length as key
		public void addWord(String word) {
			int n = word.length();
			if (!map.containsKey(n)) {
				map.put(n, new HashSet<>());
			}
			map.get(n).add(word);
		}

		// Not suitable for:
		// - finding all keys with common prefix.
		// - enumerating a string dataset in a lexicographical order.
		// - scaling for large dataset, once the map's table increases in size
		// there will be hash collisions and search time complexity increases to
		// O(n^2*m) where n is number of inserted keys
		// Time complexity - o(m*n) where m is number of words of length n
		// Space complexity - O(m) where m is number of words
		public boolean search(String word) {
			int n = word.length();
			if (map.containsKey(n)) {
				for (String storedWord : map.get(n)) {
					int i = 0;
					while (i < n && (word.charAt(i) == storedWord.charAt(i) || word.charAt(i) == '.')) {
						i++;
					}
					if (i == n) {
						return true;
					}
				}
			}
			return false;
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
