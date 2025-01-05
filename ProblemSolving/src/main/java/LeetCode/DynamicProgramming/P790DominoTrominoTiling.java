package LeetCode.DynamicProgramming;

/* 
 * P790. Domino and Tromino Tiling - Medium
 * 
 * You have two types of tiles: a 2 x 1 domino shape and a tromino shape. You may rotate these shapes.
 * ## #   | ##
 *    #   | #
 * Domino | Tromino
 * 
 * Given an integer n, return the number of ways to tile an 2 x n board. 
 * Since the answer may be very large, return it modulo 109 + 7.
 * 
 * In a tiling, every square must be covered by a tile. Two tilings are 
 * different if and only if there are two 4-directionally adjacent cells on the 
 * board such that exactly one of the tilings has both squares occupied by a tile.
 * 
 * Approach - 1D Dynamic Programming
 */
public class P790DominoTrominoTiling {

//	public static long mod = 1000000007l;
	public static long mod = (long) 1e9 + 7;

	public static void main(String[] args) {

//		int n = 1;
//		int n = 2;
//		int n = 3;
//		int n = 4;
//		int n = 5;
		int n = 30;

		int ways1D = numTilings1D(n);
		System.out.println("1D DP: The number of ways for tiling: " + ways1D);

		int ways2D = numTilings2D(n);
		System.out.println("2D DP: The number of ways for tiling: " + ways2D);

		int waysMemoized = numTilingsMemoized(n);
		System.out.println("Memoized: The number of ways for tiling: " + waysMemoized);

		int waysRecursive = numTilingsRecursive(n);
		System.out.println("Recursive: The number of ways for tiling: " + waysRecursive);
	}

	// Recurrence relations for 1D DP, derived empirically
	// 1 config of 2 horizontal domino, 1 config of 1 vertical domino
	// 2 configs of 2 trominos
	// dp[n] = dp[n-1] + dp[n-2] + 2*(0 -> n-3)∑dp[i]
	// Expand 2*(0 -> n-3)∑dp[i]
	// dp[n] = dp[n-1] + dp[n-2] + 2*dp[n-3] + 2*(0 -> n-4)∑dp[i]
	// dp[n] = dp[n-1] + dp[n-3] + (dp[n-2] + dp[n-3] + 2*(0 -> n-4)∑dp[i])
	// dp[n-1] = dp[n-2] + dp[n-3] + 2*(0 -> n-4)∑dp[i]
	// dp[n] = dp[n-1] + dp[n-3] + dp[n-1]
	// dp[n] = 2*dp[n-1] + dp[n-3]
	private static int numTilings1D(int n) {
		long[] dp = new long[n + 1];

		if (n < 3) {
			return n;
		}
		dp[0] = 1;
		dp[1] = 1;
		dp[2] = 2;
		dp[3] = 5;

		for (int i = 4; i <= n; i++) {
			dp[i] = (2 * dp[i - 1] + dp[i - 3]) % mod;
		}
		return (int) dp[n];
	}

	// 0 - 1
	// 1 - 1
	// 2 - 2
	// 3 - 5
	// 4 - 11
	// 5 - 24
	// 6 - 53

	// 1 2 5 11 24 53
	// 1 3 6 13 29
	// 2 3 7 16
	// 1 4 9
	// 1 1
	// 0
	public static int numTilings2D(int n) {

		if (n < 3) {
			return n;
		}
		// dp[][3] holds 3 states, 0 when the last column is entirely filled
		// 1 when last column's top is only filled
		// 2 when last column bottom is only filled
		// 1 and 2 are overflows from current column i
		// 1 and 2 fills the top and bottom on (i+1)th column
		long[][] dp = new long[n + 1][3];

		dp[0][0] = 1;
		dp[1][0] = 1;
		dp[1][1] = 1;
		dp[1][2] = 1;

		// There are 8 cases in total for n >=3
		for (int i = 2; i <= n; i++) {
			// in this case 1 vertical d, 2 horizontal d, 1 tromino, 1 tromino
			dp[i][0] = (dp[i - 1][0] + dp[i - 2][0] + dp[i - 2][1] + dp[i - 2][2]) % mod;

			// 1 horizontal d at top, post tromino, 1 tromino
			dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % mod;

			// 1 horizontal d at bottom, post tromino, 1 tromino
			dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % mod;
		}
		return (int) dp[n][0];
	}

	// Time complexity - O(n) with cache, it's 3*n for 3 states and n columns
	// Space complexity - O(n) needed for recursion stack space and dp array.
	private static int numTilingsMemoized(int n) {
		long[][] dp = new long[n + 1][2];
		// prepopulation not needed
//		for(int i = 0; i <= n; i++) {
//			dp[i][0] = -1;
//			dp[i][1] = -1;
//		}
		// may prepopulate the dp array with -1

		// 1-> for space and 0 for no space
		return (int) recursiveMemoized(n, 1, 0, dp);
	}

	private static long recursiveMemoized(int n, int i, int space, long[][] dp) {
		if (i > n + 1) {
			return 0;
		}
		if (i == n + 1) {
			if (space == 1) {
				return 0;
			}
			return 1;
		}
		if (dp[i][space] != 0) {
			return dp[i][space];
		}
		long count = 0;
		if (space == 0) {
			count += recursiveMemoized(n, i + 1, 0, dp);
			count += recursiveMemoized(n, i + 2, 0, dp);
			count += 2 * recursiveMemoized(n, i + 2, 1, dp);
		} else {
			count += recursiveMemoized(n, i + 1, 1, dp);
			count += recursiveMemoized(n, i + 1, 0, dp);
		}
		dp[i][space] = count % mod;
		return count % mod;
	}

	// Time complexity - O(3^n) for n columns. max 3 recursive calls are branched
	// and there are n such states
	// Space complexity - O(n) for recursivon stack space
	private static int numTilingsRecursive(int n) {
		return recursive(1, false, n);
	}

	private static int recursive(int i, boolean space, int n) {
		if (i > n + 1) {
			return 0;
		}
		if (i == n + 1) {
			if (space) {
				return 0;
			}
			return 1;
		}
		long count = 0;
		if (!space) {
			// 1 vertical domino
			count += recursive(i + 1, false, n);
			// 2 horizontal dominos
			count += recursive(i + 2, false, n);
			// 2 tromino configs
			count += 2 * recursive(i + 2, true, n);
		} else {
			// horizontal domino
			count += recursive(i + 1, true, n);
			// 1 tromino config which can fill the gap
			count += recursive(i + 1, false, n);
		}
		return (int) (count % mod);
	}

}
