package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P2466. Count Ways To Build Good Strings - Medium
 * 
 * Given the integers zero, one, low, and high, we can construct a string by 
 * starting with an empty string, and then at each step perform either of the following:
 * 
 * Append the character '0' zero times.
 * Append the character '1' one times.
 * 
 * This can be performed any number of times.
 * 
 * A good string is a string constructed by the above 
 * process having a length between low and high (inclusive).
 * 
 * Return the number of different good strings that can be constructed satisfying 
 * these properties. Since the answer can be large, return it modulo 109 + 7.
 * 
 * Approach - DP
 */
public class P2466CountWaysBuildGoodStrings {

	static final int mod = 1000000007;

	public static void main(String[] args) {
//		int low = 3, high = 3, zero = 1, one = 1;

//		int low = 2, high = 3, zero = 1, one = 2;

		int low = 200, high = 200, zero = 10, one = 1;

//		int low = 1, high = 100000, zero = 1, one = 1;

		int countTabulation = countGoodStringsTabulation(low, high, zero, one);
		System.out.println("Tabulation: The number of good strings are: " + countTabulation);

		int countMemoized = countGoodStringsMemoized(low, high, zero, one);
		System.out.println("Memoization: The number of good strings are: " + countMemoized);

		int countMemoized2 = countGoodStringsMemoized2(low, high, zero, one);
		System.out.println("Memoization2: The number of good strings are: " + countMemoized2);
	}

	// DP Iterative - Bottom Up
	// dp[i] array of length high + 1 to store number of good strings with length i.
	// Base case, dp[0] = 1 as empty string is only good string with length 0.
	// To find dp[i] We find the relation between each problem dp[i] with smaller
	// subproblems. Every good string ends with zeros of 0s or one of 1s like 0 or
	// 11. For dp[5], string = ????0 or ???11. If a good string of length 5 ends
	// with 0, it means every good string of length 4 can be turned into a good
	// string of length 5 by appending 0. So, dp[5] can be incremented by dp[4].
	// In general, dp[end] += dp[end - zero]. Also we need to check end >= zero.
	// Similarly, if the string ends with 11, it means that every good string of
	// length 3 can be turned into a good string of length 5 by appending 11. So,
	// dp[5] can be incremented by dp[3]. dp[5] = d[4] + dp[3]. With the base case
	// and recurrence relations, one can fill dp array to get number of good strings
	// of each length in the range [low ~ high]. Add the number of strings in the
	// range [low ~ high] to get the number valid good strings.
	// Time complexity - O(high) for itearting till length = high
	// Space complexity - O(high) to fill the dp array.
	private static int countGoodStringsTabulation(int low, int high, int zero, int one) {
		int[] dp = new int[high + 1];
		dp[0] = 1;
		for (int i = 1; i <= high; i++) {
			if (i >= zero) {
				dp[i] += dp[i - zero];
			}
			if (i >= one) {
				dp[i] += dp[i - one];
			}
			dp[i] %= mod;
		}
		int count = 0;
		for (int i = low; i <= high; i++) {
			count += dp[i];
			count %= mod;
		}
		return count;
	}

	// DP Memoization - Top down
	// dfs(end) denotes the number of good strings of length end. The problem is
	// broken into subproblems in recursive calls dfs(end - zero) and dfs(end -
	// one). The recursion call continues until it reaches a point where the
	// subproblem can be solved without further recursion i.e. dfs(0) = 1.
	// To avoid recalculation we use dp memo and store -1 for every value to denote
	// that the cell is not visited.
	// Time complexity - O(high) to fill dp array recursively.
	// Space complexity - O(high), to build the dp table and also during recursion
	// steps, there are at most high self calls in the stack.
	public static int countGoodStringsMemoized(int low, int high, int zero, int one) {
		int[] dp = new int[high + 1];
		Arrays.fill(dp, -1);
		return recursive(low, high, zero, one, 0, dp);
	}

	public static int recursive(int low, int high, int zero, int one, int len, int[] dp) {
		if (len > high) {
			return 0;
		}
		if (dp[len] != -1) {
			return dp[len];
		}
		int val = 0;
		if (len >= low) {
			val = 1;
		}
		val += recursive(low, high, zero, one, len + zero, dp);
		val += recursive(low, high, zero, one, len + one, dp);
		return dp[len] = val % mod;
	}

	// DP Memoization - Top down
	// dp[0] = 1, as an empty string is a good string.
	private static int countGoodStringsMemoized2(int low, int high, int zero, int one) {
		int[] dp = new int[high + 1];
		Arrays.fill(dp, -1);
		dp[0] = 1;
		int result = 0;
		for (int i = low; i <= high; i++) {
			result += recursive(zero, one, dp, i);
			result %= mod;
		}
		return result;
	}

	private static int recursive(int zero, int one, int[] dp, int len) {
		if (dp[len] != -1) {
			return dp[len];
		}
		int count = 0;
		if (len >= zero) {
			count += recursive(zero, one, dp, len - zero);
		}
		if (len >= one) {
			count += recursive(zero, one, dp, len - one);
		}
		return dp[len] = count % mod;
	}
}
