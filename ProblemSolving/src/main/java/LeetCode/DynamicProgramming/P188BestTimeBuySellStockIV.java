package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P188. Best Time to Buy and Sell Stock IV
 * 
 * You are given an integer array prices where prices[i] is the 
 * price of a given stock on the ith day, and an integer k.
 * 
 * Find the maximum profit you can achieve. You may complete at most 
 * k transactions: i.e. you may buy at most k times and sell at most k times.
 * 
 * Note: You may not engage in multiple transactions simultaneously 
 * (i.e., you must sell the stock before you buy again).
 * 
 * Approach - Dynamic Programming
 */
public class P188BestTimeBuySellStockIV {

	public static void main(String[] args) {

//		int[] prices = { 3, 3, 5, 0, 0, 3, 1, 4 };
//		int k = 3;

//		int[] prices = { 3, 2, 6, 5, 0, 3 };
//		int k = 2;

//		int[] prices = { 1, 2, 3, 4, 5 };
//		int k = 3;

//		int[] prices = { 3, 9, 1, 9, 2, 9 };
//		int k = 2;

		int[] prices = { 1, 3, 4, 2, 5, 7, 2, 4, 9, 0 };
		int k = 4;

//		int[] prices = { 1, 2 };
//		int k = 1;

		int maxProfitOnePass = maxProfitOnePass(k, prices);
		System.out.println("One Pass: The max profit for selling the stock at most k times: " + maxProfitOnePass);

		int maxProfit2DDP = maxProfit2DDP(k, prices);
		System.out.println("2D DP: The max profit for selling the stock at most k times: " + maxProfit2DDP);

		int maxProfit3DDPOpt = maxProfit3DDPOpt(k, prices);
		System.out
				.println("3D DP Optimized: The max profit for selling the stock at most k times: " + maxProfit3DDPOpt);

		int maxProfit3DDP = maxProfit3DDP(k, prices);
		System.out.println("3D DP: The max profit for selling the stock at most k times: " + maxProfit3DDP);

	}

	// Time complexity - O(n*k), since we iterate over all days and transactions
	// Space complexity - O(k), for storing txnPrice and txnProfit
	public static int maxProfitOnePass(int k, int[] prices) {
		int n = prices.length;

		if (n == 0 || k == 0) {
			return 0;
		}

		// Greedy - Maximum profit for unlimited transactions
		// similar to Best Time to Buy and Sell Stock II
		if (k >= n / 2) {
			int maxProfit = 0;
			for (int i = 1; i < n; i++) {
				if (prices[i] > prices[i - 1]) {
					maxProfit += prices[i] - prices[i - 1];
				}
			}
			return maxProfit;
		}

		int[] txnPrice = new int[k];
		int[] txnProfit = new int[k];

		Arrays.fill(txnPrice, prices[0]);
		for (int price : prices) {
			for (int i = 0; i < k; i++) {
				int profit = 0;
				if (i != 0) {
					profit = txnProfit[i - 1];
				}
				txnPrice[i] = Math.min(txnPrice[i], price - profit);
				txnProfit[i] = Math.max(txnProfit[i], price - txnPrice[i]);
			}
		}
		return txnProfit[k - 1];
	}

	// Transaction cerntric and implicitly handles state transitions
	private static int maxProfit2DDP(int k, int[] prices) {
		int n = prices.length;
		if (k >= n / 2) {
			int maxProfit = 0;
			for (int i = 1; i < n; i++) {
				if (prices[i] > prices[i - 1]) {
					maxProfit += prices[i] - prices[i - 1];
				}
			}
			return maxProfit;
		}

		int[][] dp = new int[k + 1][2];

		for (int i = 0; i <= k; i++) {
			dp[i][1] = Integer.MIN_VALUE;
//			dp[i][1] = -prices[0]; // not a constant still works, but slower
		}
		// not necessary as prices[0] is also taken into account later
//		dp[1][1] = -prices[0]; 

		// int price : prices -> we're starting from index 0 for prices
		// prices[0] is also taken into account
		// This helps to avoid finding the max value from j = 0 to k later.
		for (int price : prices) {
			for (int i = 1; i <= k; i++) {
				dp[i][0] = Math.max(dp[i][0], dp[i][1] + price);
				dp[i][1] = Math.max(dp[i][1], dp[i - 1][0] - price); // prices[0] at start
			}
		}
		return dp[k][0];
	}

	// Time complexity - O(n*k), as we iterate over all days and transactions
	// Space complexity - O(n*k*2) for storing the dp array
	private static int maxProfit3DDP(int k, int[] prices) {
		int n = prices.length;
		int maxProfit = 0;

		if (n == 0 || k == 0) {
			return 0;
		}

		// Greedy
		// Unlimited transactions when k >= n/2
		if (k >= n / 2) {
			for (int i = 1; i < n; i++) {
				if (prices[i] > prices[i - 1]) {
					maxProfit += prices[i] - prices[i - 1];
				}
			}
			return maxProfit;
		}

		// dp[day_number][transaction_number][stock_holding] holds profit
		int[][][] dp = new int[n][k + 1][2];

		// For day = 0, transaction_number > 1 and stock hold = 1 needs to be removed
		for (int i = 0; i <= k; i++) {
			dp[0][i][1] = Integer.MIN_VALUE;
//			dp[0][i][1] = -prices[0]; // not a constant still works, but slower
		}
		// Starting point where no transactions and no stocks dp[0][0][0] = 0
		// If there is a buy reducing profit, transaction number = 1 and stock hold = 1
		// necessary as prices[0] is not taken into account later
		dp[0][1][1] = -prices[0];

		// 4 States
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= k; j++) {
				// Stock is not held or stock is sold for profit
				dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
				// Stock is held or stock is bought with prices[i]
				dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]); // prices[1] directly
				// dp[i - 1][j][1] or dp[0][1][1] should have -prices[0] else
				// dp[i - 1][j - 1][0] - prices[i] or dp[0][0][0] - prices[i] = 0 - prices[1]
				// dp[i - 1][j][1] or dp[0][1][1] is Integer.MIN_VALUE
				// will give dp[1][1][1] = - prices[1] even if -prices[0] > -prices[1]
			}
		}
		// 0 at end since stock holding status 1 means purchase and less profit
		// Maximum profit can occur at any transaction in dp[n-1][j][0] for j = 0 to k
		// Not all k transactions might be fully utilized
		// It may hapen that Maximum profit occurs at any transaction
		for (int i = 0; i <= k; i++) {
			maxProfit = Math.max(maxProfit, dp[n - 1][i][0]);
		}
		return maxProfit;
	}

	// Storing day(i) in innermost array makes use of cpu cache more efficiently
	// The consecutive memory accesses are along the innermost array.
	// This takes advantage of CPU cache locality and is useful in modern CPUs.
	private static int maxProfit3DDPOpt(int k, int[] prices) {
		int maxProfit = 0;
		int n = prices.length;

		if (k >= n / 2) {
			for (int i = 1; i < n; i++) {
				if (prices[i] > prices[i - 1]) {
					maxProfit += prices[i] - prices[i - 1];
				}
			}
			return maxProfit;
		}
		int[][][] dp = new int[2][k + 1][n];
		for (int i = 0; i <= k; i++) {
			dp[1][i][0] = Integer.MIN_VALUE;
//			dp[0][i][1] = -prices[0]; // not a constant still works, but slower
		}
		// necessary as prices[0] is not taken into account later
		dp[1][1][0] = -prices[0];
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= k; j++) {
				dp[0][j][i] = Math.max(dp[0][j][i - 1], dp[1][j][i - 1] + prices[i]);
				dp[1][j][i] = Math.max(dp[1][j][i - 1], dp[0][j - 1][i - 1] - prices[i]); // prices[1] directly
			}
		}
		for (int i = 0; i <= k; i++) {
			maxProfit = Math.max(maxProfit, dp[0][i][n - 1]);
		}
		return maxProfit;
	}

}
