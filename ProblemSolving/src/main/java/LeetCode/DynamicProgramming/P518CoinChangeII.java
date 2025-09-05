package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P518. Coin Change II - Medium
 * 
 * You are given an integer array coins representing coins of different 
 * denominations and an integer amount representing a total amount of money.
 * 
 * Return the number of combinations that make up that amount. If that amount 
 * of money cannot be made up by any combination of the coins, return 0.
 * 
 * You may assume that you have an infinite number of each kind of coin.
 * 
 * The answer is guaranteed to fit into a signed 32-bit integer.
 * 
 * Approach - DP
 * 
 * Integer[][] dp is faster than int[][] dp in case there 
 * are lots of 0s stored as answer to subproblems.
 */
public class P518CoinChangeII {

	public static void main(String[] args) {
//		int[] coins = { 1, 2, 5 };
//		int amount = 5;

		int[] coins = { 3, 5, 7, 8, 9, 10, 11 };
		int amount = 500;

		int combinationsTabulation = changeTabulation(amount, coins);
		System.out.println("Tabulation: The number of coin combinations: " + combinationsTabulation);

		int combinationsTabulation2D = changeMemoized2D(amount, coins);
		System.out.println("Tabulation 2D: The number of coin combinations: " + combinationsTabulation2D);

		int combinationsMemoized = changeMemoized(amount, coins);
		System.out.println("DFS Memoized BT: The number of coin combinations: " + combinationsMemoized);

		int combinationsMemoized2 = changeMemoized2(amount, coins);
		System.out.println("DFS Memoized BT 2: The number of coin combinations: " + combinationsMemoized2);

		int combinationsRecursiveSorted = changeBacktrackingSorted(amount, coins);
		System.out.println("DFS Recursive BT Sorted: The number of coin combinations: " + combinationsRecursiveSorted);

		combinations = 0;

		int combinationsRecursive = changeBacktracking(amount, coins);
		System.out.println("DFS Recursive BT: The number of coin combinations: " + combinationsRecursive);
	}

	// DP - Tabulation : Bottom Up
	// Template: > Define a base case for which the answer is obvious.
	// > Develop a strategy to compute more complex case from a simple one.
	// > Link the answer to base cases with this strategy.
	// Base cases: No coins or amount = 0
	// a) amount of money is 0: there is only 1 combination, take 0 coins.
	// b) No coins or coins = 0: 0 for amount > 0 and 1 for amount = 0
	// In other words, for 0 coins, there is 1 combination for amount = 0.
	// Here we compute the combinations for all amounts of money, 1 by 1 for each
	// coin. It's obvious that all amounts < coin value are not impacted by the
	// current coin value. Hence, for amounts less than coin we simply reuse the old
	// dp values. dp[0,1, coin - 1] remains as it is.
	// The number of combinations for a particular amount with a current coin =
	// The number of current combinations for that particular amount +
	// The number of current combinations at an amount = amount - coin value
	// Strategy: Add coins one by one, starting from base case: no coins
	// For each added coin, compute recursively, the number of combinations for each
	// amount of money from thay coin value to amount.
	// DP[amount] = DP[amount] + DP[amount - coin].
	// Time complexity - O(amount*n) for two loops.
	// Space complexity - O(amount), since we only keep a 1D dp array.
	private static int changeTabulation(int amount, int[] coins) {
		int[] dp = new int[amount + 1];
		dp[0] = 1;
		for (int coin : coins) {
			for (int i = coin; i <= amount; i++) {
				dp[i] += dp[i - coin];
			}
		}
		return dp[amount];
	}

	// DP - Tabulation 2D
	// dp[i][j], i is the index of coin we're considering (coins[i] to coins[n-1])
	// and j is amount already considered or remaining amount we want to make.
	// dp[i][j] is number of ways to make amount using coins all coins from first
	// coin at index 0 till last(n-1).
	// we set dp[i][0] as 1 instead of just dp[0][0] as 1 because for any set of
	// coins, starting from any index, there's exactly 1 way to make amount 0, take
	// no coins.
	private static int changeMemoized2D(int amount, int[] coins) {
		int n = coins.length;
		// dp has size (n + 1)*(amount + 1)
		// n+1 acts as padding when we calculate dp[i][j] = dp[i + 1][j]
		// amount + 1 is row length as we want to return dp[0][amount]
		int[][] dp = new int[n + 1][amount + 1];
		// Making amount
		// for amount = 0, we can have 1 combination when coin = 0
		// for coin = 1, 2,..., n-1, that combination = 1 is reused.
		// till n-1 or nth coin. index = n or (n+1)th coin means no coin left.
		// In recursive approach it means we return 1 when amount = 0
		// It also means we return 0 when coins index = n
		for (int i = 0; i < n; i++) { // j <= n, then j = 0 can be in inner loop
			dp[i][0] = 1;
		}

		// reverse iterating coin values as we need to use (i+1)th value
		for (int i = n - 1; i >= 0; i--) {
			// iterating amount from i = 1 to amount as amount = 0 is already taken care.
			for (int j = 1; j <= amount; j++) {
				// use current coins at least once
				// stay at same i as we have unlimited coins
				if (j >= coins[i]) {
					dp[i][j] = dp[i + 1][j] + dp[i][j - coins[i]];
				} else { // skip current coin and use coins after this one.
					dp[i][j] = dp[i + 1][j];
				}
			}
		}
		// we want to return max combinations of coins needed for the given whole amount
		// dp[i][j] is number of ways to make amount j using coins i onwards till n-1.
		// In recursive approach we pass 0th coin and max amount.
		return dp[0][amount];
	}

	private static int changeMemoized(int amount, int[] coins) {
		// Stores number of ways to make amount(remaining) from coin start onwards.
		// can use int[][] dp as well, but we need to populate all cells with -1.
		Integer[][] dp = new Integer[amount + 1][coins.length];
//		Arrays.sort(coins);
		return dfsMemoized(amount, coins, 0, dp);
	}

	private static int dfsMemoized(int amount, int[] coins, int start, Integer[][] dp) {
		// Found one valid combination
		if (amount == 0) {
			return 1;
		}
		// no coins left
		if (start == coins.length) {
			return 0;
		}
		if (dp[amount][start] != null) {
			return dp[amount][start];
		}
		if (amount < coins[start]) {
			return dp[amount][start] = dfsMemoized(amount, coins, start + 1, dp);
		}
		return dp[amount][start] = dfsMemoized(amount - coins[start], coins, start, dp)
				+ dfsMemoized(amount, coins, start + 1, dp);
	}

	// DFS Memoization - Backtracking
	// We cache the results in a 2D DP table
	// State = (amount, start), amount: how much is left
	// start: the index of the coin we're allowed to use(and beyond).
	// Avoids recomputing state, so it runs faster than plain recursion.
	// Time complexity - O(amount * n), we cache results for each (amount, start)
	// pair. amount ranges from 0, ... amount. start ranges from 0, ... n(coins).
	// Total states = amount * n and each state is solved once and does O(1) work
	// where there are two recursive calls so O(amount*n).
	// Space complexity - O(amount * n), Memo table takes O(amount * n) and
	// Recursion stack takes O(amount) time.
	private static int changeMemoized2(int amount, int[] coins) {
		// Stores number of ways to make amount(remaining) from coin start onwards.
		// can use int[][] dp as well, but we need to populate all cells with -1.
		Integer[][] dp = new Integer[amount + 1][coins.length];
//		Arrays.sort(coins);
		return dfsMemoized2(amount, coins, 0, dp);
	}

	private static int dfsMemoized2(int amount, int[] coins, int start, Integer[][] dp) {
		// Found one valid combination
		if (amount == 0) {
			return 1;
		}
		// no coins left
		if (start == coins.length) {
			return 0;
		}
		if (dp[amount][start] != null) {
			return dp[amount][start];
		}
		int combinations = 0;
		// 1. Take current coin if it fits and
		// stay at current coin since coins can be reused.
		if (amount >= coins[start]) {
			combinations += dfsMemoized(amount - coins[start], coins, start, dp);
		}
		// 2. Move to the next coin by skipping current coin
		// we always go for option 2 even if we don't take option 1.
		combinations += dfsMemoized(amount, coins, start + 1, dp);

//		for (int i = start; i < coins.length; i++) {
//			if (amount < coins[i]) {
//				break;
//			}
//			combinations += dfsMemoized(amount - coins[i], coins, i, dp);
//		}

//		for (int i = start; i < coins.length; i++) {
//			if (amount >= coins[i]) {
//				combinations += dfsMemoized(amount - coins[i], coins, i, dp);
//			}
//		}
		return dp[amount][start] = combinations;
	}

	// DFS Recursion - Backtracking with sorting
	private static int changeBacktrackingSorted(int amount, int[] coins) {
		Arrays.sort(coins);
		dfsSorted(amount, coins, 0);
		return combinations;
	}

	private static void dfsSorted(int amount, int[] coins, int start) {
		if (amount == 0) {
			combinations++;
			return;
		}
		for (int i = start; i < coins.length; i++) {
			// early break if the current coin denomination is more than amount.
			// Uses sorting
			if (amount < coins[i]) {
				break;
			}
			// if the current amount is more than current coin denomiation
			// rescurse further with reduced amount at same denomination index.
			dfs(amount - coins[i], coins, i);
		}
	}

	static int combinations;

	// DFS Recursion - Backtracking
	// We count unique combinations and order doesn't matter. When we pick a coin we
	// shouldn't go back to smaller coins. The start index helps recursion to only
	// consider current and later coins and prevent double counting.
	// Time complexity - O(2^amount), we have two choices - 1. To take current
	// coin(stay at same index) 2. Skip current coin(move to next index). This
	// creates a binary recursion tree. Worst case branching factor = 2(take/skip)
	// Height of recursion is around the amount(each coin can reduce amount by 1)
	// Exponential: 2^amount in worst case.
	// Space complexity - O(amount), recursion stack depth in worst case.
	public static int changeBacktracking(int amount, int[] coins) {
		// start with 1st coin.
		dfs(amount, coins, 0);
		return combinations;
	}

	private static void dfs(int amount, int[] coins, int start) {
		// Found a valid combination
		// when we exhaust the amount with coins increase combination count.
		if (amount == 0) {
			combinations++;
			return;
		}
		for (int i = start; i < coins.length; i++) {
			// if the current amount is more than current coin denomiation
			// rescurse further with reduced amount at same denomination index.
			if (amount >= coins[i]) {
				dfs(amount - coins[i], coins, i);
			}

		}
	}

}
