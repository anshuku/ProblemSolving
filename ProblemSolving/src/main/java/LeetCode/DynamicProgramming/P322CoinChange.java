package LeetCode.DynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * P322. Coin Change - Medium
 * 
 * You are given an integer array coins representing coins of different 
 * denominations and an integer amount representing a total amount of money.
 * 
 * Return the fewest number of coins that you need to make up that amount. If 
 * that amount of money cannot be made up by any combination of the coins, return -1.
 * 
 * You may assume that you have an infinite number of each kind of coin.
 * 
 * Approach - DP + Sorting, BFS + Greedy + Sorting
 */
public class P322CoinChange {

	static class Pair {
		int amount;
		int pair;

		Pair(int amount, int pair) {
			this.amount = amount;
			this.pair = pair;
		}
	}

	public static void main(String[] args) {
//		int[] coins = { 1, 2, 5 };
//		int amount = 11;

		int[] coins = { 5, 2, 1 };
		int amount = 6;

//		int[] coins = { 2 };
//		int amount = 3;

//		int[] coins = { 186, 419, 83, 408 };
//		int amount = 6249;

		int minCoinsArr = coinChangeArr(coins, amount);
		System.out.println("Array: The minimum number of coin combinations: " + minCoinsArr);

		int minCoinsRecursive = coinChangeRecursive(coins, amount);
		System.out.println("Recursive: The minimum number of coin combinations: " + minCoinsRecursive);

		int minCoinsBfs = coinChangeBfs(coins, amount);
		System.out.println("BFS: The minimum number of coin combinations: " + minCoinsBfs);

		int minCoinsBTBF = coinChangeBTBF(coins, amount);
		System.out.println("Backtracking: The minimum number of coin combinations: " + minCoinsBTBF);

		int minCoinsGreedy = coinChangeGreedy(coins, amount);
		System.out.println("Greedy: The minimum number of coin combinations: " + minCoinsGreedy);
	}

	// DP Tabulation - Bottom up
	// For iterative solution, we think in a bottom up manner. For calculating F(i),
	// we need to compute all minimum counts for amounts upto i-1. On each iteration
	// i of the algorithm F(i) = min j = 0 to n-1 F(i-ci) + 1
	// Assigning amount + 1(sentinel large number) to val instead of MAX helps to
	// avoid integer overflow.
	// Time complexity - O(S*n) where S is the amount, n is denomination count.
	// On each step the algorithm finds the next F(i) in at most n iterations,
	// where 1 <= i <= S. Total iterations are S*n.
	// Space complexity - O(S) for memo table, where S is amount to change.
	public static int coinChangeArr(int[] coins, int amount) {
		Arrays.sort(coins);
		int[] dp = new int[amount + 1];
		for (int i = 1; i <= amount; i++) {
			// Acts like infinity.
			int val = amount + 1;
			for (int coin : coins) {
				// No need to continue if coin is too large.
				if (i < coin) {
					break;
				}
				val = Math.min(val, dp[i - coin]);
			}
			dp[i] = val + 1;
		}
		return dp[amount] > amount ? -1 : dp[amount];
	}

	// DP Memoized - Top down
	// This uses backtracking and cuts the partial solutions in the recursive tree,
	// which doesn't leads to viable solutions which happens when coin > amount.
	// The problem has optimal substructure property. The optimal solution can be
	// constructed from optimal solutions of its subproblems. Let's assume
	// we know F(S) where some change val1, val2,... for S which is optimal and the
	// last coin's denomination is C. Due to optimal subtructure below is true:
	// F(S) = F(S - C) + 1, but we don't know which is denomination of last coin C.
	// So compute F(S - ci) for each coin c0, c1,.. cn-1 and chose min among them.
	// F(S) = min i = 0->n-1 F(S - ci) + 1, subject to S - ci >= 0,
	// F(S) = 0, when S = 0; F(S) = -1(ATQ), when n = 0
	// Caching the solutions to the subproblems in a table since there are lots of
	// subproblems which are calculated multiple times.
	// Time complexity - O(S*n) where S is the amount, n is denomination count.
	// In worst case, the recursive tree has height of S, and algo solves only S
	// subproblems because it caches precalculated solutions in a table. Each
	// subproblem is computed with n iterations, one by one for coin denomination.
	// Space complexity - O(S) for memo table, where S is amount to change.
	private static int coinChangeRecursive(int[] coins, int amount) {
		int[] dp = new int[amount + 1];
//		Arrays.sort(coins);
		return recursive(coins, amount, dp);
	}

	private static int recursive(int[] coins, int amount, int[] dp) {
		if (amount < 0) {
			return -1;
		}
		if (amount == 0) {
			return 0;
		}
		if (dp[amount] != 0) {
			return dp[amount];
		}
		int minCoins = Integer.MAX_VALUE;
		for (int coin : coins) {
//			if (amount - coin < 0) {
//				break;
//			}
			int res = recursive(coins, amount - coin, dp);
			if (res >= 0) {
				minCoins = Math.min(minCoins, res + 1);
			}
		}
		return dp[amount] = minCoins == Integer.MAX_VALUE ? -1 : minCoins;
	}

	// BFS + Greedy
	private static int coinChangeBfs(int[] coins, int amount) {
		if (amount == 0) {
			return 0;
		}
		Set<Integer> nodes = new HashSet<>();
		nodes.add(amount);
		Set<Integer> visited = new HashSet<>();
		visited.add(amount);
		int level = 0;
		Arrays.sort(coins);
		while (!nodes.isEmpty()) {
			Set<Integer> newNodes = new HashSet<>();
			level++;
			for (int remainder : nodes) {
				for (int coin : coins) {
					if (remainder == coin) {
						return level;
					} else if (remainder < coin) {
						break;
					} else {
						int value = remainder - coin;
						if (!visited.contains(value)) {
							newNodes.add(value);
							visited.add(value);
						}
					}
				}
			}
			nodes = newNodes;
		}
		return -1;
	}

	// Brute Force - Backtracking
	// It's based on how many of this coin should I take(0, S/c(max possible)) and
	// then recurses to next coin type for the remainder. We enumerate all
	// non-negative combinations of coin counts, coin type by coin type.
	// By moving from idxCoin to idxCoin + 1, one never permute the same set of
	// coins in different order - so there is no double count.
	// The minimum of all valid combinations is the optimal answer.
	// It can be modelled as min of Sum xi, i = 0 -> n-1; subject to
	// S = Sum xi * ci, i = 0 -> n-1 where S = amount, ci = coin denomination.
	// and xi = number of coins of denomination ci used for S.
	// Hence, xi = [0, S/ci]; A trivial solution is to enumerate all subsets of
	// coin frequencies [x0, x1, x2,... xn-1] that satisfies the constraint above,
	// compute their sum and return the minimum among them.
	// Backtracking Algorithm: The algo generates all combination of coin
	// frequencies [x0,x1,... xn-1] in the range [0, S/ci] which satisfies the
	// constraints. It makes a sum of the combinations and return their minimum or
	// -1 in case there is no acceptable solution.
	// Time complexity - O(S^n) where S is amount and n is number of coins. In worst
	// case, complexity is exponential in the number of coins n. The reason is that
	// every coin denomination ci could have at most S/ci values. Total combinations
	// is: S/c1 * S/c2 * S/c3 *... *S/cn = S^n/c1*c2*c3..c^n
	// T(n) = Product of[amount/coin(i)) + 1] for i = 0 to k-1
	// Space complexity - O(n), In worst case, the maximum depth of recursion is n.
	private static int coinChangeBTBF(int[] coins, int amount) {
		// start at the 0th(1st) coin with the full amount.
		return coinChange(0, coins, amount);
	}

	private static int coinChange(int idxCoin, int[] coins, int amount) {
		// If amount is exactly formed, I need 0 more coins.
		if (amount == 0) {
			return 0;
		}
		// If I still have coin denomination left and positive amount.
		if (idxCoin < coins.length && amount > 0) {
			// the most copies of current coin I can use without exceeding the amount.
			int maxVal = amount / coins[idxCoin];
			// minCost tracks the best(fewest coins) found along this branch.
			int minCoins = Integer.MAX_VALUE;
			// Try every count(x) of the current coin from 0(not included) till maxVal
			for (int x = 0; x <= maxVal; x++) {
				// Recurse to the next coin with the reduced amount(if possible)
				if (amount >= x * coins[idxCoin]) {
					int res = coinChange(idxCoin + 1, coins, amount - x * coins[idxCoin]);
					// If remainder coin + amount is solvable, total coins used is res + x
					// Update minCost.
					if (res != -1) {
						minCoins = Math.min(minCoins, res + x);
					}
				}

			}
			// If nothing worked at this level, return -1(unsolvable with remaining coin
			// types).
			return minCoins == Integer.MAX_VALUE ? -1 : minCoins;
		}
		// If one ran out of coin types while amount > 0, return -1.
		return -1;
	}

	static Set<Integer> coinSet;
	static Map<Pair, Boolean> memo;

	// Greedy enumeration
	// Starting from count = 1,2,3 stop when minimum count is found.
	// Unlike Perfect squares appraoch, there are certain issues:
	// 1. isNumDivisble(amount, count) repeated;y computes the same subproblems
	// which takes exponential time so memoization is essential.
	// 2. count 1 -> amount is brute force BFS without pruning.
	// The function may find the solution, but slowly for big amounts.
	// Hence, it's worse than dp for large inputs amount > 10^4.
	// Here we explore coint count first, like BFS layered by depth.
	private static int coinChangeGreedy(int[] coins, int amount) {
		coinSet = new HashSet<>();
		memo = new HashMap<>();
		for (int coin : coins) {
			coinSet.add(coin);
		}
		int count = 1;
		for (; count <= amount; count++) {
			if (isNumDivisble(amount, count)) {
				return count;
			}
		}
		return 0;
	}

	private static boolean isNumDivisble(int amount, int count) {
		// can't form negative
		if (amount < 0) {
			return false;
		}
		// must exactly match
		if (count == 0) {
			return amount == 0;
		}
		if (count == 1) {
			return coinSet.contains(amount);
		}
		Pair key = new Pair(amount, count);
		if (memo.containsKey(key)) {
			return memo.get(key);
		}
		for (int coin : coinSet) {
			if (isNumDivisble(amount - coin, count - 1)) {
				memo.put(key, true);
				return true;
			}
		}
		memo.put(key, false);
		return false;
	}

}
