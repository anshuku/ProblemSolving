package LeetCode.DynamicProgramming;

/*
 * P132. Palindrome Partitioning II
 * 
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * 
 * Return the minimum cuts needed for a palindrome partitioning of s.
 * 
 * Approach - DP, Backtracking(TLE)
 */
public class P132PalindromePartitioningII {

	public static void main(String[] args) {
//		String s = "aab";
//		String s = "a";
//		String s = "abb";
//		String s = "aba";
		String s = "ababababababababababababcbabababababababababababa";

		int cutsDP = minCutDP(s);
		System.out.println("DP: The min cuts needed for palindrome partitioning - " + cutsDP);

		int cutsBT = minCutBT(s);
		System.out.println("Backtracking: The min cuts needed for palindrome partitioning - " + cutsBT);
	}

	private static int minCutDP(String s) {

		return 0;
	}

	// Min should be used globally in case of recursion
	static int min;

	// TLE for larger strings (length > 16)
	// No need to use list to store palindromes.
	public static int minCutBT(String s) {
		min = s.length() - 1;
		recursive(s, 0, -1);
		return min;
	}

	private static void recursive(String s, int start, int currMin) {
		if (start == s.length() && currMin < min) {
			min = currMin;
			return;
		}
		for (int i = start; i < s.length(); i++) {
			String str = s.substring(start, i + 1);
			if (isPalindrome(str)) {
				recursive(s, i + 1, currMin + 1);
			}
		}

	}

	private static boolean isPalindrome(String s) {
		int n = s.length();
		for (int i = 0; i < n / 2; i++) {
			if (s.charAt(i) != s.charAt(n - i - 1)) {
				return false;
			}
		}
		return true;
	}

}
