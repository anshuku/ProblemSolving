package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P115. Distinct Subsequences - Hard
 * 
 * Given two strings s and t, return the number of distinct subsequences of s which equals t.
 * 
 * The test cases are generated so that the answer fits on a 32-bit signed integer.
 * 
 * Approach - DP
 * 
 * Recursive approach is preferred when there are choices to make.
 * It is also preferred when the problem can be broken down into subproblems.
 * The solution of subproblems can be used to solve the overall problem.
 * In this problem, the substring is the subproblem 
 * i represents that we already processed [0,1,... i-1] characters of s.
 * j represents that we already processed [0,1,... j-1] characters of t.
 * 
 * Recursion needs variables to represent states of recursion - i and j.
 * Given i and j the function represents the number of distinct subsequences
 * in s[i...M] which equals t[j...N].
 * 
 * Recursion -> choice and subproblem -> substring
 * Recursion -> states -> indices i and j
 * states -> time and space complexity
 * 
 * An empty string is a subsequence for an empty string.
 */
public class P115DistinctSubsequences {

	public static void main(String[] args) {
		String s = "rabbbit";
		String t = "rabbit";

//		String s = "babgbag";
//		String t = "bag";

		int distinctSubsequencesMemo = numDistinctMemoized(s, t);
		System.out.println("Memoized: The distinct subsequences of s which equals t: " + distinctSubsequencesMemo);

		int distinctSubsequencesTable1D1Var = numDistinctTabulation1D1Var(s, t);
		System.out.println("Tabulation 1D 1Var: The distinct subsequences of s which equals t: "
				+ distinctSubsequencesTable1D1Var);

		int distinctSubsequencesTable1D1Arr = numDistinctTabulation1D1Arr(s, t);
		System.out.println("Tabulation 1D 1Arr: The distinct subsequences of s which equals t: "
				+ distinctSubsequencesTable1D1Arr);

		int distinctSubsequencesTable = numDistinctTabulation(s, t);
		System.out.println("Tabulation: The distinct subsequences of s which equals t: " + distinctSubsequencesTable);

	}

	// DP - Memoization: Match 1 character at a time.
	// Here we need the right ordering for t as character position doesn't matter.
	// In Recursion we maintain 2 indices(i,j) for traversing s and t
	// If there is a match then: a) Move both i and j by 1 and explore further.
	// b) There might be same character present at ith index later for more t.
	// So move i by 1 and keep j same. As there might be other t in s.
	// If there is no match then: explore more characters in s by moving i by 1.
	// Base case: 3 cases when we break the recursion and do backtrack
	// when either of i and j reaches M and N respectively.
	// When i == M func(M,j), and j != N then return 0 since there is no match
	// When j == N func(i, N) in any case return 1, since there is a match for all i
	// This is because an empty substring is a subsequence for all strings.
	// When M-i < N-j or remaining s is less than remaining t, return 0.
	// There are scenarios of recalculation for certain (i,j) leading to 2^n time
	// Using a memo dp[i][j] and storing the values avoids recalculation.
	// Time complexity - O(m*n) where O(1) is time for single call and there are
	// m*n combinations of substrings so O(m*n) calls.
	// Space complexity - O(m*n) since there are m*n combinations of states i and j
	// Space utilized by recursion stack is O(m-n) in case there is no match in t.
	public static int numDistinctMemoized(String s, String t) {
		char[] sArr = s.toCharArray();
		char[] tArr = t.toCharArray();
		int m = sArr.length;
		int n = tArr.length;
		int[][] dp = new int[m + 1][n + 1];
		for (int[] arr : dp) {
			Arrays.fill(arr, -1);
		}
		return recursive1(sArr, tArr, 0, 0, dp);
//		return recursive2(sArr, tArr, 0, 0, dp);
	}

	private static int recursive1(char[] sArr, char[] tArr, int i, int j, int[][] dp) {
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		if (i == sArr.length || j == tArr.length || sArr.length - i < tArr.length - j) {
			return dp[i][j] = j == tArr.length ? 1 : 0;
		}
		if (sArr[i] == tArr[j]) {
			return dp[i][j] = recursive1(sArr, tArr, i + 1, j + 1, dp) + recursive1(sArr, tArr, i + 1, j, dp);
		} else {
			return dp[i][j] = recursive1(sArr, tArr, i + 1, j, dp);
		}
	}

	private static int recursive2(char[] sArr, char[] tArr, int i, int j, int[][] dp) {
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		if (i == sArr.length || j == tArr.length || sArr.length - i < tArr.length - j) {
			return dp[i][j] = j == tArr.length ? 1 : 0;
		}
		dp[i][j] = recursive2(sArr, tArr, i + 1, j, dp);
		if (sArr[i] == tArr[j]) {
			dp[i][j] += recursive2(sArr, tArr, i + 1, j + 1, dp);
		}
		return dp[i][j];
	}

	// DP - Tabulation: Iterative 1D
	// Here we only need values for next row(i+1) to calculate values for current.
	// 2 values of dp[i+1][j] and dp[i+1][j+1] is sufficient.
	// Time complexity - O(m*n)
	// Space complexity - O(n)
	private static int numDistinctTabulation1D1Var(String s, String t) {
		char[] sArr = s.toCharArray();
		char[] tArr = t.toCharArray();

		int m = sArr.length;
		int n = tArr.length;

		int[] dp = new int[n + 1];
		int prev = 1;

		for (int i = m - 1; i >= 0; i--) {
			prev = 1;
			for (int j = n - 1; j >= 0; j--) {
				int old = dp[j]; // when j becomes j-1 this acts as dp[i+1][j]
				dp[j] = dp[j];
				if (sArr[i] == tArr[j]) {
					dp[j] += prev;
				}
				prev = old;
			}
		}
		return dp[0];
	}

	// DP - Tabulation: Iterative 1D
	// Here we only need values for next row(i+1) to calculate values for current.
	// The next row values of dp[i+1] is sufficient.
	// Time complexity - O(m*n)
	// Space complexity - O(n)
	private static int numDistinctTabulation1D1Arr(String s, String t) {
		char[] sArr = s.toCharArray();
		char[] tArr = t.toCharArray();

		int m = sArr.length;
		int n = tArr.length;

		int[] dp = new int[n + 1];
		dp[n] = 1;

		for (int i = m - 1; i >= 0; i--) {
			int[] curr = new int[n + 1];
			curr[n] = 1;
			for (int j = n - 1; j >= 0; j--) {
				if (sArr[i] == tArr[j]) {
					curr[j] = dp[j + 1] + dp[j];
				} else {
					curr[j] = dp[j];
				}
			}
			dp = curr;
		}
		return dp[0];
	}

	// DP - Tabulation: Iterative
	// Create a dp[][] of size (m+1)*(n+1), the extra row and column
	// represents the empty substrings for s(rows) and t(columns).
	// recurse(i,j) represents the number of distinct subsequences in s[i...M]
	// that equals t[j...N]. Here we first find recurse(i,j) before finding
	// recurse(i,j-1), recurse(i-1,j), recurse(i-1,j-1).
	// Base case: Initialize the last column dp[i][N] as 1 since every substring
	// of s has an empty subsequence. In the last column we have empty t so it's 1
	// In last row for s, we've empty s but non empty t so, dp[M][j..N-1] = 0.
	// dp[M][N] = 1 since an empty string is a subsequence for an empty string.
	// Time complexity - O(m*n)
	// Space complexity - O(m*n)
	private static int numDistinctTabulation(String s, String t) {
		char[] sArr = s.toCharArray();
		char[] tArr = t.toCharArray();
		int m = sArr.length;
		int n = tArr.length;
		int[][] dp = new int[m + 1][n + 1];

		for (int i = 0; i <= m; i++) {
			dp[i][n] = 1;
		}

		for (int i = m - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				dp[i][j] = dp[i + 1][j];
				if (sArr[i] == tArr[j]) {
					dp[i][j] += dp[i + 1][j + 1];
				}
			}
		}
		return dp[0][0];
	}

}
