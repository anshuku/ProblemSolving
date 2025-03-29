package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/*
 * P139. Word Break - Medium
 * 
 * Given a string s and a dictionary of unique strings wordDict, return true if s can 
 * be segmented into a space-separated sequence of one or more dictionary words.
 * 
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 * All the strings of wordDict are unique.
 * 
 * Approach - BFS, Trie, DP, Memo, Tabulation
 */
public class P139WordBreak {

	public static void main(String[] args) {
		String s = "leetcode";
		List<String> wordDict = new ArrayList<>();
		wordDict.add("leet");
		wordDict.add("code");
		wordDict.add("lee");
		wordDict.add("tcode");

//		String s = "catsandog";
//		List<String> wordDict = new ArrayList<>();
//		wordDict.add("cats");
//		wordDict.add("dog");
//		wordDict.add("sand");
//		wordDict.add("and");
//		wordDict.add("cat");

		boolean isSegmentedDPTrie = wordBreakTrie(s, wordDict);
		System.out.println("Trie: The string can be segemented into a sequence in dict: " + isSegmentedDPTrie);

		boolean isSegmentedDPMemo = wordBreakMemoized(s, wordDict);
		System.out.println("Memoized: The string can be segemented into a sequence in dict: " + isSegmentedDPMemo);

		boolean isSegmentedDPTable = wordBreakTabulation(s, wordDict);
		System.out.println("Tabulation: The string can be segemented into a sequence in dict: " + isSegmentedDPTable);

		boolean isSegmentedDP = wordBreakDP(s, wordDict);
		System.out.println("DP: The string can be segemented into a sequence in dict: " + isSegmentedDP);

		boolean isSegmentedBFS = wordBreakBFS(s, wordDict);
		System.out.println("BFS: The string can be segemented into a sequence in dict: " + isSegmentedBFS);

	}

	static class TrieNode {
		TrieNode[] trie;
		boolean isWord;

		TrieNode() {
			trie = new TrieNode[26];
		}

		void insert(String word) {
			TrieNode node = this;
			for (char c : word.toCharArray()) {
				if (node.trie[c - 'a'] == null) {
					node.trie[c - 'a'] = new TrieNode();
				}
				node = node.trie[c - 'a'];
			}
			node.isWord = true;
		}
	}

	// Trie Optimization
	// For each state earlier it used to take O(m*k) time - 1000*20
	// With a trie it'll take O(n) time - 300.
	// Create a Trie with characters of all the words in wordDict.
	// Starting from i till end of s. Check if i denotes 1st letter
	// or last letter was end of a word. Otherwise move to i+1.
	// If the condition is true. Check wheter the s has a substring
	// Which equals the word via iterating the trie from j = i till n - 1.
	// If there is a child node with isWord true mark dp[j] = true
	// If there is no child in trie at s[j] then break. Repeat
	// Time complexity - O(n^2 + m*k). O(n) for iterating over s
	// O(n*n) for iterating the trie at each state. O(m*k) for trie creation.
	// Space complexity - O(n + m*k) , dp take O(n) and trie O(m*k) nodes.
	private static boolean wordBreakTrie(String s, List<String> wordDict) {
		TrieNode root = new TrieNode();
		for (String word : wordDict) {
			root.insert(word);
		}
		int n = s.length();
		char[] charArr = s.toCharArray();
		boolean[] dp = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (i == 0 || dp[i - 1]) {
				TrieNode curr = root;
				for (int j = i; j < n; j++) {
					char c = charArr[j];
					if (curr.trie[c - 'a'] == null) {
						break;
					}
					curr = curr.trie[c - 'a'];
					if (curr.isWord) {
						dp[j] = true;
					}
				}
			}
		}
		return dp[n - 1];
	}

	// DP - Top Down memoization
	// Recursive function returns true if string s can be formed from wordDict
	// Recurrence relation: For recursive(i) true there are 2 conditions:
	// a) For a particular word in wordDict: it must end in index i
	// Substring of s from i-len(word)+1 == word
	// b) If first case is true. For remaining s, with end index i - len
	// The recursive call must return true
	// dp(i) = any(s[i-word.len+1, i+1].equals(word) && dp(i-word.len))
	// memo[i] = 0 for false and 1 for true.
	// Time complexity - O(n*m*k), for recursive function there are n states
	// Due to memoization each state is calculated once. For each state
	// We iterate through m words and get substring of k average length
	// Space complexity - O(n) for memoization and recursive call stack.
	private static boolean wordBreakMemoized(String s, List<String> wordDict) {
		int n = s.length();
		int[] memo = new int[n];
		Arrays.fill(memo, -1);
		return recursive(s, wordDict, memo, n - 1);
	}

	private static boolean recursive(String s, List<String> wordDict, int[] memo, int i) {
		if (i < 0) {
			return true;
		}
		if (memo[i] != -1) {
			return memo[i] == 1;
		}
		for (String word : wordDict) {
			int len = word.length();
			if (i - len + 1 < 0) {
				continue;
			}
			String str = s.substring(i - len + 1, i + 1);
			if (str.equals(word) && recursive(s, wordDict, memo, i - len)) {
				memo[i] = 1;
				return true;
			}
		}
		memo[i] = 0;
		return false;
	}

	// DP - Bottom Up Tabulation
	// We start matching words later in string traversal via start = i - len + 1
	// In this approach we start from index 0. We iterate over wordDict
	// and pin point exact substrings which equals the word present in wordDict.
	// We can break the wordDict once a word is found as it has unique words.
	// 1st we check the first word ending at index i and then later indices.
	// Time Complexity - O(n*m*k) where n is length of string, m is size of wordDict
	// and k is average length of strings in wordDict.
	// We calculate n states and each state cost O(m*k)
	// Space Complexity - O(n) for dp array of length n.
	private static boolean wordBreakTabulation(String s, List<String> wordDict) {
		int n = s.length();
		boolean[] dp = new boolean[n];
		for (int i = 0; i < n; i++) {
			for (String word : wordDict) {
				int len = word.length();
				// to handle bounds
				if (i - len + 1 < 0) {
					continue;
				}
				if (i == len - 1 || dp[i - len]) {
					// the substring goes backward to i - len + 1 and uptil i + 1
					// If there is a match in wordDict, it's unique
					if (s.substring(i - len + 1, i + 1).equals(word)) {
						dp[i] = true;
						// no need to check further words of same length
						// since wordDict has unique words
						break;
					}
				}
			}
		}
		return dp[n - 1];
	}

	// DP - Form s upto an index - i which represents length.
	// Use set for faster lookup time created from wordDict list.
	// Iterate from i = 1 to n and Create all the substrings
	// starting from j = 0 till i-1. If there is a substring in the set
	// and the string prior to current substring can be formed. dp[i] = true
	// j represents the starting index of word and also the last length.
	// dp[j] marks the end of last word in dp but start of new word in s.
	// Time complexity - O(n^3 + m*k) where O(m*k) for creating set.
	// The nested loop which takes O(n^2) time and the substring O(n) so O(n^3)
	// Space complexity - O(n + m*k), O(n) for dp and O(m*k) for set.
	private static boolean wordBreakDP(String s, List<String> wordDict) {
		int n = s.length();
		Set<String> words = new HashSet<>(wordDict);
		boolean[] dp = new boolean[n + 1];
		dp[0] = true;
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < i; j++) {
				if (dp[j] && words.contains(s.substring(j, i))) {
					dp[i] = true;
					// no need to further check as length = i is included
					break;
				}
			}
		}
		return dp[n];
	}

	// BFS - Queue
	// Take every indices as node(queue element) and then find all
	// substrings starting from 0 and ending at later indices
	// If the substring is present between start and end
	// Add end to queue one by one. Mark visited for end index true.
	// Poll the queue's 1st index and now this becomes 1st index
	// Repeat insertion operation. If the polled index is n
	// The substrings are proper words and true can be returned
	// Also add the end index only once with help of visited array.
	// Convert wordDict list into a set for constant lookup of substrings
	// visited array should be of length n+1 as 0 to n elements are tracked.
	// If end < n, it always marks the starting index for later traversal.
	// Time complexity - O(n^3 + m*k) n - length of s, m - length of wordDict
	// k - average length of word in wordDict. BFS: There are n nodes
	// For each node we check next n - 1 nodes. Substring takes O(n) time.
	// BFS takes O(n^3) time. Set formation takes O(m*k) time
	// Space complexity - O(n + m*k) n for queue and m*k for set.
	public static boolean wordBreakBFS(String s, List<String> wordDict) {
		int n = s.length();
		boolean[] visited = new boolean[n + 1];
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(0);
		Set<String> words = new HashSet<String>(wordDict);
		while (!queue.isEmpty()) {
//			if(queue.contains(n)) {
//				return true;
//			}
			int start = queue.poll();
			if (start == n) {
				return true;
			}
			for (int end = start + 1; end <= n; end++) {
				if (visited[end]) {
					continue;
				}
				if (words.contains(s.substring(start, end))) {
					visited[end] = true;
					queue.add(end);
				}
			}
		}
		return false;
	}

}
