package LeetCode.DynamicProgramming;

/*
 * P516. Longest Palindromic Subsequence - Medium
 * 
 * Given a string s, find the longest palindromic subsequence's length in s.
 * 
 * A subsequence is a sequence that can be derived from another sequence by deleting 
 * some or no elements without changing the order of the remaining elements.
 * 
 * Approach - DP
 */
public class P516LongestPalindromicSubsequence {

	public static void main(String[] args) {
		String s = "bbbab";
//		String s = "bbabcb";

		int longestPDSubseqTabulation1D = longestPalindromeSubseqTabulation1D(s);
		System.out.println("Tabulation 1D: The longest palindromic subsequence is: " + longestPDSubseqTabulation1D);

		int longestPDSubseqTabulation = longestPalindromeSubseqTabulation(s);
		System.out.println("Tabulation: The longest palindromic subsequence is: " + longestPDSubseqTabulation);

		int longestPDSubseqMemoized = longestPalindromeSubseqMemoized(s);
		System.out.println("Memoization: The longest palindromic subsequence is: " + longestPDSubseqMemoized);
	}

	// Iterative Dynamic Programming - Space optimization
	// dp[j] stores the length of longest palindromic subsequence of substring from
	// index i to j in s. It is similar to what dp[i][j] stores.
	// Time complexity - O(n^2)
	// Space complexity - O(n)
	private static int longestPalindromeSubseqTabulation1D(String s) {
		int n = s.length();
		int[] dp = new int[n];
		char[] charArr = s.toCharArray();
		for (int i = n - 1; i >= 0; i--) {
			int[] curr = new int[n];
			curr[i] = 1;
			for (int j = i + 1; j < n; j++) {
				if (charArr[i] == charArr[j]) {
					curr[j] = dp[j - 1] + 2;
				} else {
					curr[j] = Math.max(dp[j], curr[j - 1]);
				}
			}
			dp = curr;
		}
		return dp[n - 1];
	}

	// Iterative dynamic programming - Bottom Up
	// A dp array is used, dp[i][j] has the longest palindromic subsequence of the
	// substring formed from index i to j in s. The dp array is filled in variety
	// of way - From smaller to larger strings: length 1, then 2,... n
	// Using 2 pointers i and j where i moves from n-1 to 0 and j from i+1 to n-1
	// At the end of each iteration of i, we get length of longest palindromic
	// subsequence in all the substring with start = i and end = n-1
	// For each i mark dp[i][i]=1, as it denotes 1 character. Then, Iterate from
	// j = i+1 to n-1. If 1st and last characters are same. dp[i][j]=2+dp[i+1][j-1]
	// If the characters do not match, take the max of longest palindromic
	// subsequence in both substrings formed after ignoring 1st and last characters.
	// Time complexity - O(n^2), for dp array initializing and filling take O(n^2)
	// Space complexity - O(n^2), for dp array.
	private static int longestPalindromeSubseqTabulation(String s) {
		int n = s.length();
		char[] charArr = s.toCharArray();
		int[][] dp = new int[n][n];
		// At end of each loop the dp[i] gives the length of max palindromic subsequence
		// with start = i
		for (int i = n - 1; i >= 0; i--) {
			dp[i][i] = 1;
			for (int j = i + 1; j < n; j++) {
				if (charArr[i] == charArr[j]) {
					dp[i][j] = dp[i + 1][j - 1] + 2;
				} else {
					dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
				}
			}
		}
		return dp[0][n - 1];
	}

	// Recursive dynamic programming - Top Down Recursion
	// Generate all possible subsequences of given string and find longest
	// palindromic string among strings. There are total of 2^n possible strings.
	// If 1st and last characters are same, both are included, add 2 and remove them
	// If they aren't the same, they cannot both occur in final palindrome.
	// Recurse the substring removing first character and same for last, taking max.
	// Use two pointer, start (0) and end (len-1) for creating substrings.
	// Here subproblems maybe solved multiple times, hence use memoization.
	// Time complexity - O(n^2), Initializing the dp array take O(n^2) time/
	// Since there are O(n^2) states to iterate over,
	// the recursive function is called O(n^2) time
	// Space complexity - O(n^2), O(n^2) for dp array. Recursion stack can grow
	// to a maximum size of O(n). When we try to form recursion tree, at max 2
	// branches can formed at a level. The recursion stack would only have 1 call
	// The tree height will be O(n) since at each call we decrease height by 1.
	// The recursion stack will have a maximum of O(n) elements.
	public static int longestPalindromeSubseqMemoized(String s) {
		int n = s.length();
		char[] charArr = s.toCharArray();
		int[][] dp = new int[n][n];
		return recursive(charArr, 0, n - 1, dp);
	}

	private static int recursive(char[] charArr, int start, int end, int[][] dp) {
		// Subproblem already solved
		if (dp[start][end] != 0) {
			return dp[start][end];
		}
		// Substring is empty so return 0
		if (start > end) {
			return 0;
		}
		// Substring has 1 character which is a palindrome so return 1
		if (start == end) {
			return 1;
		}
		// Both must be included in longest palindromic subsquence
		if (charArr[start] == charArr[end]) {
			dp[start][end] = 2 + recursive(charArr, start + 1, end - 1, dp);
		} else {
			// Recursively find longest palindromic subequence in both substrings
			// by ignoring 1st and last characters and take max of these.
			dp[start][end] = Math.max(recursive(charArr, start + 1, end, dp), recursive(charArr, start, end - 1, dp));
		}
		return dp[start][end];
	}

}
