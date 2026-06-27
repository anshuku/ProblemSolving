package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P3966. Count Good Integers in a Range - Hard
 * 
 * You are given three integers l, r and k.
 * 
 * A number is considered good if the absolute difference between every pair of adjacent digits is at most k.
 * 
 * Return the number of good integers in the range [l, r] (inclusive).
 * 
 * The absolute difference between values x and y is defined as abs(x - y).
 * 
 * Approach - DP, Array
 */
public class P3966CountGoodIntegersInRange {

	public static void main(String[] args) {
//		long l = 10, r = 15;
//		int k = 1; // 3

//		long l = 201, r = 204;
//		int k = 2; // 2

//		long l = 7857, r = 31907629;
//		int k = 3; // 1157095

		long l = 5256, r = 8436894;
		int k = 5; // 2741252

		long goodIntegersDP = goodIntegersDP(l, r, k);
		System.out.println("DP: The number of good integers in the range are: " + goodIntegersDP);

		long goodIntegersBruteForce = goodIntegersBruteForce(l, r, k);
		System.out.println("Brute Force: The number of good integers in the range are: " + goodIntegersBruteForce);
	}

	// DP: Digit
	// The value of l and r can go upto 10^15, so checking every number one by one
	// will take a lot of time. We must use Digit DP technique. Instead of counting
	// good number in [l, r] directly, we count: good(0...r) - good(0...l-1). We
	// subtract total good numbers till r from total good numbers till l - 1(not
	// needed). This gives the count of good numbers inside the required range.
	// The use a 4d dp state variable and a dfs function, dfs(pos, prev, tight,
	// started), where: pos = current digit position/index in the string, prev =
	// previous/last chosen digit: we use -1 if no digit has been chosen yet, tight
	// = whether we're still matching the upper bound: if it's true/1 the current
	// digit cannot exceed the digit in the bound. Otherwise we can freely choose
	// between 0 to 9, started = whether we've started building the number: tracks
	// whether the number has started, this helps us ignore leading zeroes.
	// dfs(x) = number of good integers in [0, x], both included.
	// Observation: A number is good if abs(adjacent digits) <= k.
	// Example: For k = 2, num = 1234, good. Similarly 135 is good but 159 is not.
	// While building the number digit by digit, we only need to know:
	// > the previous digit and > whether we've started the number(to ignore leading
	// zeros). Nothing else matters, this makes Digit DP perfect.
	// Suppose we're processing 52791 at position 1 (digit = 2).
	// Our dp state is (position, previousDigit, started, tight) where
	// - position: which digit are we filling or index,
	// - previousDigit or prev: the previous chosen digit. Need it to check
	// abs(prev - current) <= k.
	// - started: to ignore leading zeroes. Example 00057, before selecting 5,
	// started = false or 0. After selecting 5, started = true, without this
	// variable, we would incorrectly compare 0 and 5.
	// - tight: Whether we've already become smaller than the limit. Suppose limit
	// is 52791. If we've already chose 41... then remaining digits can be anything
	// 0...9 as 41abc < 52791. This is what tight represents.
	// Transition: At every position we try all possible digits. Suppose we're at,
	// position = i, Maximum digit we may place is limit = tight ? digit[pos] : 9;
	// Then for loop is from digit = 0 to limit, we try every possibility,
	// Case 1 - Still in leading zeros: (prev == -1 || started = false) && current
	// digit == 0, we continue without starting the number. Example 0000, no
	// previous digit exists. Simply continue.
	// Case 2 - First non-zero digit: We can always start the number, started = 1.
	// Example 0004, now previous = 4 and started = true/1. No adjacency check yet.
	// Case 3 - Number already started, we can choose the next digit only if:
	// abs(prev - digit) <= k, which is exactly the condition given in problem. If
	// it's true, we continue, else we skip.
	// Base case - When we process all the figits, if (pos == len), we return
	// started, (if valid number formed we return 1, else 0.
	// Memoization: There are many states which repeat. Example: position = 4,
	// previous = 6, started = true, tight = false can be reached from many
	// different prefixes. So we memoize. Only memoize when tight = false otherwise
	// the upper limit changes.
	// Time complexity - O(20*11*2*2*10), D = number of digits(at most 19 for a
	// long). D*11*2*2 = 19*44 = 836. Each state tries at most 10 digits. So the
	// time complexity is: O(D*11*2*2*10) = O(D * 10). Since the number of digit
	// values and flags are constants, this simplifies to O(D).
	// Space complexity - O(20*11*2*2) or O(D*11*2*2) = O(D), where D is the number
	// of digits in the upper bound.
	private static long goodIntegersDP(long l, long r, int k) {
		return solve(r, k) - solve(l - 1, k);
	}

	private static long solve(long num, int k) {
		if (num < 0) {
			return 0;
		}

		String s = String.valueOf(num); // Helps to work with digits.
		int n = s.length();

		// 20 or 16 which is max length of string and can be iterated from 0 to maxLen
		// 12 for 0 to 9 and -1, total 11 / 12. -1 = no previous digit.
		// started = 0/1, tight = 0/1.
		long[][][][] dp = new long[n][12][2][2];

		for (long[][][] a : dp) {
			for (long[][] b : a) {
				for (long[] c : b) {
					Arrays.fill(c, -1);
				}
			}
		}

		// position = 0, no previous digit = -1, tight = true/1, not started = 0.
		return dfs(0, -1, 1, 0, s, k, dp);
		// no previous digit = 10
//		return dfs2(0, 10, 1, 0, s, k, dp);
	}

	private static long dfs(int i, int prev, int tight, int started, String s, int k, long[][][][] dp) {
		// We've formed a valid number in started/1 way
		if (i == s.length()) {
			return started; // started = 1
		}

		// Already solved, return it.
		if (dp[i][prev + 1][tight][started] != -1) {
			return dp[i][prev + 1][tight][started];
		}

		// Target = 52791, if tight(1), for position 0, max digit = 5 otherwise 0-9.
		int limit = tight == 1 ? s.charAt(i) - '0' : 9;

		long ans = 0;

		// Standard digit DP where we try very figit.
		for (int d = 0; d <= limit; d++) {

			// We compute new tight
			// Equivlaent to tight == 1 && d == (num.charAt(i) - '0') as limit = current
			// digit when tight. If we choose exactly maximum, still tight, otherwise
			// becomes false/0.
			int newTight = tight == 1 && d == limit ? 1 : 0;

			// Leading 0, example - 00057, still haven't started. Previous digit remains 10
			// (dummy value).
			if (prev == -1 && d == 0) { // prev == -1 or started == 0

				ans += dfs(i + 1, -1, newTight, 0, s, k, dp);

				// First real digit, 0006, now prev = 6 and started = true/1.
				// Normal digit, Math.abs(prev - digit) <= k, If prev = 5, digit = 7, k = 2,
				// |5-7| = 2, allowed.
			} else if (prev == -1 || Math.abs(prev - d) <= k) { // prev == -1 or started == 0

				ans += dfs(i + 1, d, newTight, 1, s, k, dp);

			}
		}
		return dp[i][prev + 1][tight][started] = ans;
	}

	private static long dfs2(int i, int prev, int tight, int started, String s, int k, long[][][][] dp) {
		if (i == s.length()) {
			return started;
		}

		if (dp[i][prev][tight][started] != -1) {
			return dp[i][prev][tight][started];
		}

		long ans = 0;
		int limit = tight == 1 ? s.charAt(i) - '0' : 9;

		for (int d = 0; d <= limit; d++) {
			int newTight = d == limit && tight == 1 ? 1 : 0;

			if (started == 0 && d == 0) {
				ans += dfs2(i + 1, 10, newTight, 0, s, k, dp);
			} else if (started == 0 || Math.abs(prev - d) <= k) {
				ans += dfs2(i + 1, d, newTight, 1, s, k, dp);
			}
		}
		// We can save only non-tight states. A tight state depends on the exact prefix
		// of the bound (for example, whether the bound is 52791 or 52795), so caching
		// it is less reusable. Caching only non-tight states is a common optimization
		// because once tight is false, the remaining search space is identical
		// regardless of the original bound. One can cache tight states too, if the DP
		// table is recreated for each count() call (as it's here) but caching only
		// non-tight states is sufficient and slightly reduces memory usage.
//		if (tight == 0) {
//			dp[i][prev][started][tight] = ans;
//		}
//		return ans;
		return dp[i][prev][tight][started] = ans;
	}

	public static long goodIntegersBruteForce(long l, long r, int k) {
		long count = 0;

		for (long i = l; i <= r; i++) {
			int size = String.valueOf(i).length();

			int[] num = new int[size];
			int index = 0;

			long number = i;

			while (number > 0) {
				num[index++] = (int) (number % 10);
				number /= 10;
			}

			boolean goodNum = true;
			for (int j = 0; j < size - 1; j++) {
				if (Math.abs(num[j] - num[j + 1]) > k) {
					goodNum = false;
					break;
				}
			}

			if (goodNum) {
				count++;
			}
		}

		return count;
	}
}
