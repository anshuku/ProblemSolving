package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P3983. Subsequence After One Replacement - Medium
 * 
 * You are given two strings s and t consisting of lowercase English letters.
 * 
 * You may choose at most one index in s and replace the character at that index with any lowercase English letter.
 * 
 * Return true if it is possible to make s a subsequence of t; otherwise, return false.
 * 
 * Constraints:
 * > 1 <= s.length, t.length <= 10^5
 * > s and t consist only of lowercase English letters.
 * 
 * Approach - DP: 2 Pointers, Prefix and Suffix Arrays, LCS
 */
public class P3983SubsequenceAfterOneReplacement {

	public static void main(String[] args) {
//		String s = "cat";
//		String t = "chat";

		String s = "op";
		String t = "opoqm";

//		String s = "qo";
//		String t = "npnmq";

//		String s = "az";
//		String t = "bzy";

//		String s = "bfebdfeeaedaeafceefbdafcfdfbcaffefbdfbabcaedaccbfcfeddbbfccedbfbdabdfaeeecabeccff";
//		String t = "effecfaefcfcefcccbafddbdcfaeedfbbdcbbdecafaaacefbefafdeefecbdebfbfdfecedecddbbeefb";

		boolean canMakeSubsequenceOnePass2Ptrs = canMakeSubsequenceOnePass2Ptrs(s, t);
		System.out.println(
				"One Pass 2 Pointers: It is possible to make s a subsequence of t: " + canMakeSubsequenceOnePass2Ptrs);

		boolean canMakeSubsequenceOnePass2Arrays = canMakeSubsequenceOnePass2Arrays(s, t);
		System.out.println(
				"One Pass 2 Arrays: It is possible to make s a subsequence of t: " + canMakeSubsequenceOnePass2Arrays);

		boolean canMakeSubsequenceCharReplace = canMakeSubsequenceCharReplace(s, t);
		System.out
				.println("Char Replace: It is possible to make s a subsequence of t: " + canMakeSubsequenceCharReplace);

		boolean canMakeSubsequenceLCS = canMakeSubsequenceLCS(s, t);
		System.out.println("LCS: It is possible to make s a subsequence of t: " + canMakeSubsequenceLCS);
	}

	// DP: One Pass with 2 pointers.
	// Variable i tracks the match length of 0 or no replacement
	// Variable j tracks the match length of 1 replacement.
	// For each character c in t:
	// We extend i if c matches s[i].
	// We extend j if c matches s[j] or we replace s[i] to reach length i+1.
	// We return true if j reaches the full length of s.
	// Time complexity - O(n)
	// Space complexity - O(1)
	private static boolean canMakeSubsequenceOnePass2Ptrs(String s, String t) {
		char[] sArr = s.toCharArray();
		char[] tArr = t.toCharArray();

		int m = sArr.length;
		int n = tArr.length;

		if (m > n) {
			return false;
		}

		int i = 0, j = 0;

		for (int k = 0; k < n; k++) {
			char c = tArr[k];

			j = Math.max(j + (j < m && c == sArr[j] ? 1 : 0), i + 1);
			i += (i < m && c == sArr[i] ? 1 : 0);
		}
		return j >= m;
	}

	// One pass: 2 Arrays
	// If s is already a subsequence of t, the answer is true. Otherwise, we're
	// allowed to modify exactly 1 character (or choose not to modify). Instead of
	// trying all 26*n possible replacements, we only need to determine whether
	// there exists an index in s whose replacement can bridge the unmatched
	// portion. To achieve this efficiently, we precompute:
	// > left[i] = the earliest position in t where the prefix s[0..i] can be
	// matched as a subsequence.
	// > right[i] = the latest position in t where the suffix s[i..n-1] can be
	// matched as a subsequence.
	// Now suppose we replace s[i]:
	// > The prefix before i must already match
	// > The suffix after i must also match.
	// > The replacement character only needs to occupy any position between those 2
	// matched parts.
	// If there exists at least 1 unused position in t between the matched prefix
	// and suffix, then we can replace s[i] with that character and obtain a valid
	// subsequence.
	// Approach:
	// 1. Prefix matching: Scan both strings from left to right. For every character
	// of s, store the earliest matching index in t.
	// left[i] = earliest position of s[i] in t after matching s[0..i-1].
	// If every character is matched then, s is already a subsequence, return true.
	// 2. Suffix matching: Scan from right to left.
	// right[i] = latest position of s[i] before matching s[i+1...]
	// It records where every suffix can start
	// 3. Try replacing each position: Assume we replace s[i],
	// The prefix must end at: L = left[i - 1], (or -1 if i == 0).
	// The suffix must begin at: R = right[i+1], (or n if i == m - 1)
	// If L + 1 < R, then there exists at least 1 position of t between the matched
	// prefix and suffix or L < k < R. We can simply replace s[i] with the character
	// at that position, making the entire string a subsequence. If this consition
	// holds true for any index, return true. Otherwise, return false.
	// L is the index in t where the prefix s[0...i-1] end, and R is the index where
	// the suffix s[i+1..m-1] starts. The replaced character s[i] can become any
	// letter, so we only need 1 position in t between L and R to match it. L+1 < R
	// is equivalent to R - L > 1, meaning there is >= 1 index k with L < k < R.
	// Without such a position, the prefix and suffix would overlap or touch,
	// leaving no place to insert the replaced character while preserving the
	// subsequence order.
	// Time complexity - O(n + m), m = s.length(), n = t.length(). Each pointer
	// scans
	// t at most once. Prefix scan: O(n+m), Suffix scan: O(n+m), final iteration for
	// condition check: O(m).
	// Space complexity - O(m) for left and right arrays.
	private static boolean canMakeSubsequenceOnePass2Arrays(String s, String t) {
		char[] sArr = s.toCharArray();
		char[] tArr = t.toCharArray();

		int m = sArr.length;
		int n = tArr.length;

		if (m > n) {
			return false;
		}

		int[] left = new int[m];
		Arrays.fill(left, -1);

		int[] right = new int[m];
		Arrays.fill(right, -1);

		int l = 0;
		for (int i = 0; i < m; i++) {
			while (l < n && tArr[l] != sArr[i]) {
				l++;
			}
			if (l == n) {
				break;
			}
			left[i] = l++;
		}

		if (left[m - 1] != -1) {
			return true;
		}

		l = n - 1;
		for (int i = m - 1; i >= 0; i--) {
			while (l >= 0 && tArr[l] != sArr[i]) {
				l--;
			}
			if (l < 0) {
				break;
			}
			right[i] = l--;
		}

		for (int i = 0; i < m; i++) {
			if ((i == 0 || left[i - 1] != -1) && (i == m - 1 || right[i + 1] != -1)) {
				int L = i == 0 ? -1 : left[i - 1];
				int R = i == m - 1 ? n : right[i + 1];

				if (L + 1 < R) {
					return true;
				}
			}
		}

		return false;
	}

	// Char Replacement - TLE
	private static boolean canMakeSubsequenceCharReplace(String s, String t) {
		char[] sArr = s.toCharArray();
		char[] tArr = t.toCharArray();

		int m = sArr.length;
		int n = tArr.length;

		if (m > n) {
			return false;
		}

		// Below for loop can be ignored and we can directly use next nested for loops.
		int a = 0;
		for (int i = 0; i < n; i++) {
			if (sArr[a] == tArr[i]) {
				a++;
			}
			if (a == m) {
				return true;
			}
		}

		for (int i = 0; i < m; i++) {
			int k = 0;
			for (int j = 0; j < n; j++) {
				if (sArr[k] == tArr[j] || k == i) {
					k++;
				}

				if (k == m) {
					return true;
				}
			}
		}
		return false;
	}

	// Longest Common Subsequence - TLE
	public static boolean canMakeSubsequenceLCS(String s, String t) {
		char[] sArr = s.toCharArray();
		char[] tArr = t.toCharArray();

		int n = sArr.length;
		int m = tArr.length;

		if (n > m) {
			return false;
		}

		int[][] dp = new int[n + 1][m + 1];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (sArr[i] == tArr[j]) {
					dp[i + 1][j + 1] = dp[i][j] + 1;
				} else {
					dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
				}
			}
		}

		if (dp[n][m] == n) {
			return true;
		} else if (dp[n][m] < n - 2) {
			return false;
		}

		for (int k = 0; k < n; k++) {
			dp = new int[n + 1][m + 1];
			char old = sArr[k];
			sArr[k] = '_';

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (sArr[i] == tArr[j] || sArr[i] == '_') {
						dp[i + 1][j + 1] = dp[i][j] + 1;
					} else {
						dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
					}
				}
			}

			sArr[k] = old;

			if (dp[n][m] == n) {
				return true;
			}
		}
		return false;
	}

}
