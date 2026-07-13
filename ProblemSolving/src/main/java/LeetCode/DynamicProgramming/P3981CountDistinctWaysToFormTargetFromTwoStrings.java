package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P3981. Count Distinct Ways to Form Target from Two Strings - Hard
 * 
 * You are given three strings word1, word2, and target.
 * 
 * Your task is to count the number of ways to form target by choosing 
 * characters from word1 and word2 under the following conditions:
 * 
 * > For each character of target, choose one matching character from either word1 or word2.
 * > The chosen indices from word1 must be strictly increasing.
 * > The chosen indices from word2 must be strictly increasing.
 * > At least one character must be chosen from both word1 and word2.
 * 
 * Two ways are considered different if, for at least one position in target, 
 * the chosen character comes from a different string or a different index.
 * 
 * Return the number of ways. Since the answer may be very large, return it modulo 10^9 + 7.
 * 
 * Approach - DP: 3D - Tabulation, Memoization
 */
public class P3981CountDistinctWaysToFormTargetFromTwoStrings {

	final static int mod = 1_000_000_007;

	public static void main(String[] args) {
//		String word1 = "abc";
//		String word2 = "bac";
//		String target = "abc";

//		String word1 = "cd";
//		String word2 = "cd";
//		String target = "ccd";

		String word1 = "xy";
		String word2 = "xy";
		String target = "xyxy";

		int waysCountMemoization = interleaveCharactersMemoization(word1, word2, target);
		System.out.println(
				"Memoization: The number of distinct ways to form target from the 2 strings: " + waysCountMemoization);

		int waysCountTabulation = interleaveCharactersTabulation(word1, word2, target);
		System.out.println(
				"Tabulation: The number of distinct ways to form target from the 2 strings: " + waysCountTabulation);
	}

	// DP-3D: Tabulation
	// The memoization state is: dp[i][j][k] = number of ways to form target[k...]
	// when: > next usable index in word1 is i > next usable index in word2 is j.
	// Tabulation is simply the reverse order of these states. Since every
	// transition goes from (i,j,k) to (x+1,j,k+1) or (i,x+1,k+1), we compute:
	// > k from l down to 0 > i from m down to 0 > j from n down to 0.
	// The base case is exactly the memoization base case.
	// We iterate i from n to 0 and j from m to 0 because of 2 reasons:
	// 1. These are valid DP states. > i == n means there are no characters left in
	// word1. > j == m means there are no characters left in word2. > The
	// memoization also allows calls like dfs(n,j,k) and dfs(i,m,k).
	// 2. Those states naturally evaluate to 0 (except the base case). For example,
	// i == n means for(int y = i; y < n; y++) never runs. Likewise if j == m, the
	// corresponding for loop never runs. We get long ans = 0; dp[i][j][k] = (int)
	// ans; stores 0, which matches the memoization where we return 0 when both are
	// n and m. It also happens when only 1 string is exhausted but it cannot
	// contribute anything.
	// We iterate k from l-1 instead of l, as k == l is already the base case. Just
	// like in memoization, in tabulation we initialize dp[i][j][l] directly. There
	// is nothing left to compute for k == l. Then, every other state depends on
	// k+1: dp[i][j][k] -> dp[x+1][j][k+1] -> dp[i][x+1][k+1]. Therefore, when
	// computing layer k, we must have layer k+1 available. That is why we iterate:
	// k = l-1, l-2, ..., 0 and not k = l, l - 1, ... because the k = l layer has
	// already been initialized as the base case.
	// A useful rule when converting memoization to tabulation is:
	// > If a memoized state returns immediately(base case), initialize that DP
	// layer in tabulation.
	// > Start the loops from the states that depend on the base case, not from the
	// base case itself.
	// That's exactly what happens here: dp[][][l] is initialized first, and then we
	// compute dp[][][l-1], dp[][][l-2],..., dp[][][0].
	// Tabulation has same asymptotic complexity and computes identical states in
	// reverse dependency order.
	// Time complexity - O(n*m*l*(n + m), as every state sccans the remaining
	// suffixes.
	// Space complexity - O(n*m*l) for 3D array used for memoization.
	private static int interleaveCharactersTabulation(String word1, String word2, String target) {
		char[] w1 = word1.toCharArray();
		char[] w2 = word2.toCharArray();
		char[] t = target.toCharArray();

		int l = t.length;
		int m = w1.length;
		int n = w2.length;

		int[][][] dp = new int[m + 1][n + 1][l + 1];

		// Base case
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				dp[i][j][l] = 1;
			}
		}

		// We start k from l-1 as k = l is covered in base case.
		for (int k = l - 1; k >= 0; k--) {
			// i==m and j==n should be 0 later with help of ans at dp[i][j][k] = (int) ans;
			for (int i = m; i >= 0; i--) {
				for (int j = n; j >= 0; j--) {

					long ans = 0;
					for (int x = i; x < m; x++) {
						if (w1[x] == t[k]) {
							ans += dp[x + 1][j][k + 1];
							ans %= mod;
						}
					}

					for (int x = j; x < n; x++) {
						if (w2[x] == t[k]) {
							ans += dp[i][x + 1][k + 1];
							ans %= mod;
						}
					}

					dp[i][j][k] = (int) ans;
				}
			}
		}
		return dp[0][0][0];
	}

	// DP-3D: Memoization
	// Intuition: For every character of target, we have 2 choices:
	// > Pick it from word1. > Pick it from word2.
	// But it should follow the rules:
	// > Indices chosen from word1 and word2 must be increasing.
	// While building the target, we only need to remember:
	// > where we are in word1 > where we are in word2 > which character of target
	// we're trying to match. This leads to 3D DP (memoized recursion).
	// Approach: Let dfs(i, j, k) represent the ways to form: target[k...], meaning
	// we're forming target from k onwards till target's end, when we are index i of
	// word1 and index j of word2. i,j = next available index of word1 and word2.
	// Base case: If we've formed the entire target: k == target.size() (here it's
	// not target.size() - 1 as it means the last character was still processing),
	// the answer is valid only if we've used >= 1 character from both strings.
	// Since i and j move only after selecting a character, this simply becomes:
	// i > 0 && j > 0.
	// Transition: For the current target character: target[k], try every matching
	// character in word1 starting from i. If it matches: dfs(nextIndex + 1, j, k+1)
	// Similarly, try every matching character in word2 starting from j. If it
	// matches: dfs(i, nextIndex + 1, k + 1). The final answer is the sum of both
	// choices. Since, many states repeat, we memoize every (i, j, k).
	// Time complexity - O(n*m*l*(n + m), since each DP state scans the remaining
	// characters of both strings once.
	// Space complexity - O(n*m*l) for 3D array used for memoization.
	public static int interleaveCharactersMemoization(String word1, String word2, String target) {
		char[] w1 = word1.toCharArray();
		char[] w2 = word2.toCharArray();
		char[] t = target.toCharArray();

		int l = t.length;
		int m = w1.length;
		int n = w2.length;

		int[][][] dp = new int[m + 1][n + 1][l + 1];

		for (int[][] a : dp) {
			for (int[] b : a) {
				Arrays.fill(b, -1);
			}
		}

		return dfs(t, w1, w2, 0, 0, 0, dp);
	}

	private static int dfs(char[] t, char[] w1, char[] w2, int i, int j, int k, int[][][] dp) {
		if (k == t.length) {
			if (i > 0 && j > 0) {
				return 1;
			}
			return 0;
		}

		if (i == w1.length && j == w2.length) {
			return 0;
		}

		if (dp[i][j][k] != -1) {
			return dp[i][j][k];
		}

		int ans = 0;
		for (int x = i; x < w1.length; x++) {
			if (w1[x] == t[k]) {
				ans += dfs(t, w1, w2, x + 1, j, k + 1, dp);
				ans %= mod;
			}
		}
		for (int x = j; x < w2.length; x++) {
			if (w2[x] == t[k]) {
				ans += dfs(t, w1, w2, i, x + 1, k + 1, dp);
				ans %= mod;
			}
		}
		return dp[i][j][k] = ans;
	}

}
