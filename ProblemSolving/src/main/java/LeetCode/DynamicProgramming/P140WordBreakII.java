package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * P140. Word Break II - Hard
 * 
 * Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence 
 * where each word is a valid dictionary word. Return all such possible sentences in any order.
 * 
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 * 
 * Approach - Backtracking, 
 * 
 * sb.setLength(int num) - resets the length of StringBuilder to given length = num
 * 
 * For input "aaaaaa", with wordDict = ["a", "aa", "aaa", "aaaa", "aaaaa", "aaaaa"].
 * Every partition is a valid sentence and there are 2^n-1 partitions.
 * Algo must generate all partitions and it cannot perform any better.
 * Cost of iterating over cached results will also be exponential, as every
 * result is cached, resulting in same runtime as regular backtracking.
 * Similary for space complexity, it'll be O(n*2^n) every partition is stored in memory.
 * 
 * Worst case complexty is O(n*2^n) or all algos. Given an array of length n
 * there are n+1 ways/intervals to partition it into 2 parts. Each interval has
 * 2 choices: to split or not to split. In worst case all possiblities need to
 * be checked. This is similar to palindrome partitioning.
 */
public class P140WordBreakII {

	public static void main(String[] args) {
		String s = "catsanddog";
		List<String> wordDict = new ArrayList<>();
		wordDict.add("cats");
		wordDict.add("dog");
		wordDict.add("sand");
		wordDict.add("and");
		wordDict.add("cat");

		List<String> sentencesBT = wordBreakBacktrack(s, wordDict);
		System.out.println("Backtracking: The sentences in wordDict are: " + sentencesBT);

		List<String> sentencesTrie = wordBreakTrie(s, wordDict);
		System.out.println("Trie: The sentences in wordDict are: " + sentencesTrie);

		List<String> sentencesTabulation = wordBreakTabulation(s, wordDict);
		System.out.println("Tabulation: The sentences in wordDict are: " + sentencesTabulation);

		List<String> sentencesMemoized = wordBreakMemoized(s, wordDict);
		System.out.println("Memoization: The sentences in wordDict are: " + sentencesMemoized);
	}

	// Backtracking
	// Breaks the string into words in dictionary.
	// Recursively form words from string and add them to sentence if in dictionary.
	// If the current prefix doesn't lead to a valid solution, backtrack by
	// removing the last added word and try next possible word(all possibilities).
	// At each index, we consider all possible end indices from current index.
	// Use a set for constant time lookups.
	// Time complexity - O(n*2^n), we iterate the length of s
	// For each substring in worst case of each characer as word,
	// we add and remove so 2*2*...n = 2^n or there are 2^n leaf nodes
	// For each lead node O(n) work is performed in creating substring.
	// Space complexity - O(2^n), the recursion stack can grow upto depth n
	// where each call consumes additional space for storing current state.
	// Each n positions in string can or cannot be a split point.
	// There are total 2^n possible combinations of splits which needs storage.
	public static List<String> wordBreakBacktrack(String s, List<String> wordDict) {
		Set<String> words = new HashSet<>(wordDict);
		List<String> result = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		recursive(s, sb, result, words, 0);
		return result;
	}

	private static void recursive(String s, StringBuilder sb, List<String> result, Set<String> words, int start) {
		if (start == s.length()) {
			result.add(sb.toString().trim());
			return;
		}
		for (int end = start + 1; end <= s.length(); end++) {
			String word = s.substring(start, end);
			if (words.contains(word)) {
				int len = sb.length();
				sb.append(word).append(" ");
				recursive(s, sb, result, words, end);
				sb.setLength(len);
			}
		}
	}

	static class TrieNode {
		TrieNode[] trie;
		boolean isWord;

		TrieNode() {
			trie = new TrieNode[26];
		}

		public void insert(String word) {
			TrieNode root = this;
			for (char c : word.toCharArray()) {
				if (root.trie[c - 'a'] == null) {
					root.trie[c - 'a'] = new TrieNode();
				}
				root = root.trie[c - 'a'];
			}
			root.isWord = true;
		}
	}

	// Trie Optimization - efficient for word breakup.
	// Trie enhances word lookup process with prefix matching.
	// It helps to quickly find whether a substring can form a valid word
	// without performing linear search or set lookups. Reduces search space
	// While traversing in reverse order and starting from start index,
	// the valid sentences which can be formed from the start index are found.
	// We check if the current substring forms a valid word using trie.
	// When a valid word is found, we append it to the list of valid sentences
	// for current starting index. If the current valid word is not the last word
	// in s, it is appended to next valid sentences in end + 1 index in dp.
	// The valid sentences for each start index are stored in dp for reuse.
	// Time complexity - O(n*2^n), with trie one still need to explore all ways
	// to break string into words. In worst case there are 2^n combinations.
	// O(n) work is performed for each partition.
	// Space complexity - O(n*2^n), Trie can have maximum of 2^n nodes in
	// worst case, where each character in s represents a separate word.
	// Tabulation map can store 2^n words of size n.
	private static List<String> wordBreakTrie(String s, List<String> wordDict) {
		TrieNode root = new TrieNode();
		for (String word : wordDict) {
			root.insert(word);
		}
		int n = s.length();
		Map<Integer, List<String>> dp = new HashMap<>();
		char[] charArr = s.toCharArray();
		for (int start = n - 1; start >= 0; start--) {
			// List to store valid sentences from start index
			List<String> list = new ArrayList<>();
			TrieNode node = root;
			for (int end = start; end < n; end++) {
				char c = charArr[end];
				if (node.trie[c - 'a'] == null) {
					break;
				}
				node = node.trie[c - 'a'];
				// Check if a valid word is found.
				if (node.isWord) {
					String currentWord = s.substring(start, end + 1);
					// If it's a last word, add it as a valid sentence.
					if (end == n - 1) {
						list.add(currentWord);
					} else {
						// If it's not the last word, append it to each
						// sentence formed by remaining substring.
						List<String> endList = dp.get(end + 1);
						for (String endSentence : endList) {
							list.add(currentWord + " " + endSentence);
						}
					}
				}
			}
			// store the valid sentences formed at start index
			dp.put(start, list);
		}
		// return the valid sentences formed from entire substring.
		return dp.getOrDefault(0, new ArrayList<>());
	}

	// Dynamic Programming - Tabulation
	// It builds a table or map of valid sentences for each starting index.
	// It is better than backracking and memoization since it avoids the
	// overhead of recursive calls and stack usage.
	// Bottom up - At each index, we construct all possible sentences that can be
	// formed starting from current index by checking if substrings forms valid word
	// in wordDict. If valid word is found it's combined with next valid word
	// present just at a later index from the remaining string.
	// Iterating from end to start ensures subproblems are solved before its need.
	// Time complexity - O(n*2^n), We might need to explore all possible substrings
	// which is 2^n in worst case, leading to exponential time complexity.
	// O(n) work is performed for each substring
	// Space complexity - O(n*2^n), the dp map needs to store 2^n strings of size n
	// in worst case, resulting in exponential space complexity.
	private static List<String> wordBreakTabulation(String s, List<String> wordDict) {
		int n = s.length();
		Map<Integer, List<String>> dp = new HashMap<>();
		Set<String> words = new HashSet<>(wordDict);
		for (int start = n - 1; start >= 0; start--) {
			List<String> list = new ArrayList<>();
			for (int end = start + 1; end <= n; end++) {
				String word = s.substring(start, end);
				if (words.contains(word)) {
					// If it's last word, add it as a valid sentence.
					if (end == n) {
						list.add(word);
					} else {
						// If it's not the last word, append it to each sentence formed
						// by the remaining substring.
						List<String> curr = dp.get(end);
						for (String nextWord : curr) {
							list.add(word + " " + nextWord);
						}
					}
				}
			}
			dp.put(start, list);
		}
		return dp.getOrDefault(0, new ArrayList<>());
	}

	// Dynamic Programming - Memoization Breaks string into words
	// We store the results of backtracking via memoization for later use.
	// Memoization helps to reduce computations by ensuring each substring
	// is processed only once in average cases.
	// If the remaining string is empty, it means all characters have been
	// processed.
	// An empty string represents a valid sentence so return an array with "".
	// Time complexity - O(n*2^n), while memoization avoids redundant computaions.
	// It doesn't change the number of subproblems to be solved.
	// In worst case, there are stilll 2^n possible substrings.
	// For each subproblem O(n) work is performed.
	// Space complexity - O(n*2^n), recursion stack can have depth n
	// where each call consumes additiobal space for storing current state
	// The map needs to store the results of upto 2^n substrings of length = n
	private static List<String> wordBreakMemoized(String s, List<String> wordDict) {
		Set<String> words = new HashSet<String>(wordDict);
		Map<String, List<String>> memo = new HashMap<>();
		return recursive(s, words, memo);
	}

	// DFS function to find all possible word break combinations.
	private static List<String> recursive(String remaining, Set<String> words, Map<String, List<String>> memo) {
		// when the string is empty return a list of an empty string.
		if (remaining.isEmpty()) {
			return Collections.singletonList("");
		}
		// check if remaining string is already memoized.
		if (memo.containsKey(remaining)) {
			return memo.get(remaining);
		}
		List<String> result = new ArrayList<String>();
		for (int i = 1; i <= remaining.length(); i++) {
			String word = remaining.substring(0, i);
			// If the current substring is a valid word
			if (words.contains(word)) {
				List<String> curr = recursive(remaining.substring(i), words, memo);
				for (String nextWord : curr) {
					// Append current word with next word(if exists) with space in between.
					result.add(word + (nextWord.isEmpty() ? "" : " ") + nextWord);
				}
			}
		}
		// Memoize the results for the current substrings
		memo.put(remaining, result);
		return result;
	}

}
