package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * P188. Best Time to Buy and Sell Stock IV - Hard
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
 * Approach - Dynamic Programming, Merging or Deleting intervals
 */
public class P188BestTimeBuySellStockIV {

	public static void main(String[] args) {

//		int[] prices = { 3, 3, 5, 0, 0, 3, 1, 4 };
//		int k = 3;

//		int[] prices = { 3, 2, 6, 5, 0, 3 };
//		int k = 2;

//		int[] prices = { 1, 2, 3, 4, 5 };
//		int k = 2;

//		int[] prices = { 3, 9, 1, 9, 2, 9 };
//		int k = 2;

//		int[] prices = { 1, 3, 4, 2, 5, 7, 2, 4, 9, 0 };
//		int k = 4;

//		int[] prices = { 1, 2 };
//		int k = 1;

//		int[] prices = { 6, 1, 6, 4, 3, 0, 2 };
//		int k = 1;

//		int[] prices = { 8, 1, 7, 2, 9, 1, 8 };
//		int k = 2;

		int[] prices = { 8, 6, 4, 3, 3, 2, 3, 5, 8, 3, 8, 2, 6 };
		int k = 2;

		int maxProfit = maxProfit(k, prices);
		System.out.println("The max profit for selling the stock at most k times: " + maxProfit);

		int maxProfitOnePass = maxProfitOnePass(k, prices);
		System.out.println("One Pass: The max profit for selling the stock at most k times: " + maxProfitOnePass);

		int maxProfit2DDP = maxProfit2DDP(k, prices);
		System.out.println("2D DP: The max profit for selling the stock at most k times: " + maxProfit2DDP);

		int maxProfit3DDPOpt = maxProfit3DDPOpt(k, prices);
		System.out
				.println("3D DP Optimized: The max profit for selling the stock at most k times: " + maxProfit3DDPOpt);

		int maxProfit3DDP = maxProfit3DDP(k, prices);
		System.out.println("3D DP: The max profit for selling the stock at most k times: " + maxProfit3DDP);

		int maxProfitMerging = maxProfitMerge(k, prices);
		System.out.println("Merging: The max profit for selling the stock at most k times: " + maxProfitMerging);

	}

	private static int maxProfit(int k, int[] prices) {
		int n = prices.length;
		if (n == 0 || k == 0) {
			return 0;
		}
		if (k / 2 >= n) {
			int maxProfit = 0;
			for (int i = 1; i < n; i++) {
				if (prices[i] > prices[i - 1]) {
					maxProfit += prices[i] - prices[i - 1];
				}
			}
			return maxProfit;
		}

		List<int[]> intervals = new ArrayList<>();
		int start = 0, end = 0;
		for (int i = 1; i < n; i++) {
			if (prices[i] >= prices[i - 1]) {
				end = i;
			} else {
				if (end > start) {
					intervals.add(new int[] { start, end, prices[end] - prices[start] });
				}
				start = i;
			}
		}
		if (end > start) {
			intervals.add(new int[] { start, end, prices[end] - prices[start] });
		}
		while (intervals.size() > k) {
			int minLoss = Integer.MAX_VALUE;
			int minLossIndex = 0;
			int minProfit = intervals.get(0)[2];
			int minProfitIndex = 0;
			for (int i = 0; i < intervals.size() - 1; i++) {
				int[] interval1 = intervals.get(i);
				int[] interval2 = intervals.get(i + 1);
				int loss = interval1[2] + interval2[2] - (prices[interval2[1]] - prices[interval1[0]]);
				if (minLoss > loss) {
					minLoss = loss;
					minLossIndex = i;
				}
				int profit = interval2[2];
				if (minProfit > profit) {
					minProfit = profit;
					minProfitIndex = i + 1;
				}
			}
			if (minLoss >= minProfit) {
				intervals.remove(minProfitIndex);
			} else {
				int[] interval = intervals.get(minLossIndex);
				interval[1] = intervals.get(minLossIndex + 1)[1];
				interval[2] = prices[interval[1]] - prices[interval[0]];
				intervals.remove(minLossIndex + 1);
			}
		}
		int maxProfit = 0;
		for (int[] interval : intervals) {
			maxProfit += interval[2];
		}
		return maxProfit;
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

	// Transaction centric and implicitly handles state transitions
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
//		dp[1][1] = -prices[0]; If taken then we can start from i = 1

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
		// Transaction consists of 2 actions in pair - buy and sell.
		// Only buy changes the number of transactions -> +1 for buy, 0 for sell
		// size for transaction number is k+1 as the trader can do any transactions
		// form 0 to k so the size is k+1.
		int[][][] dp = new int[n][k + 1][2];

		// For day 0, transaction_number > 1, = 0 and stock hold = 1 needs to be removed
		// One can also remove the dp[i][0][1] with -INF but not need as j starts from 1
		// Since at any day one can't hold a stock without any transaction.
		for (int i = 0; i <= k; i++) {
			dp[0][i][1] = Integer.MIN_VALUE;
//			dp[0][i][1] = -prices[0]; // not a constant still works, but slower
		}
		// Starting point where no transactions and no stocks dp[0][0][0] = 0
		// If there is a buy reducing profit, transaction number = 1 and stock hold = 1
		// necessary as prices[0] is not taken into account later.
		dp[0][1][1] = -prices[0];

		// 4 States
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= k; j++) {
				// Stock is not held or stock is sold for profit. Stock hold is 0 EOD
				dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
				// Stock is held or stock is bought with prices[i]. Stock hold is 1 EOD
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

	// Merging - This method involves finding all the profit intervals for prices
	// and treat them as potential transactions.
	// If total number of these intervals exceeds k, then remove total number
	// of transactions to exactly k by:
	// a) Removing the least profitable transaction, or
	// b) Merging two adjacent intervals which results in least loss.
	// Time complexity - O(n*(n-k)) for merging and interval scanning
	// Space complexity - O(n)
	private static int maxProfitMerge(int k, int[] prices) {
		int n = prices.length;

		if (k == 0 || n == 0) {
			return 0;
		}
		int maxProfit = 0;

		if (k >= n / 2) {
			for (int i = 1; i < n; i++) {
				if (prices[i] > prices[i - 1]) {
					maxProfit += prices[i] - prices[i - 1];
				}
			}
			return maxProfit;
		}
		ArrayList<int[]> intervals = new ArrayList<>();
//		PriorityQueue<int[]> intervals = new PriorityQueue<>((a, b) -> a[2] - b[2]);
//		int start = 0;
//		for (int i = 1; i < n; i++) {
//			if (prices[i] < prices[i - 1]) {
//				if (start < i - 1) {
//					intervals.add(new int[] { start, i - 1 });
//				}
//				start = i;
//			}
//		}
//		if (start < n - 1) {
//			intervals.add(new int[] { start, n - 1 });
//		}
		int start = 0, end = 0;
		for (int i = 1; i < n; i++) {
			if (prices[i] >= prices[i - 1]) {
				end = i;
			} else {
				if (start < end) {
					intervals.add(new int[] { start, end, prices[end] - prices[start] });
				}
				start = i;
			}
		}
		if (start < end) { // end = n - 1
			intervals.add(new int[] { start, end, prices[end] - prices[start] });
		}
		for (int[] arr : intervals) {
			System.out.println(Arrays.toString(arr));
		}
		while (intervals.size() > k) {
			// Below two are for b) and tracks the merged adjacent intervals with min loss
			int mergeLoss = Integer.MAX_VALUE;
			int mergeLossIndex = 0;
			// Below two are for a) and tracks the min profit interval(directly found)
			int minProfit = intervals.get(0)[2];
			int minProfitIndex = 0;
			for (int i = 0; i < intervals.size() - 1; i++) {
				int[] first = intervals.get(i);
				int[] second = intervals.get(i + 1);

				int loss = first[2] + second[2] - (prices[second[1]] - prices[first[0]]);

				// Both the minLossIndex and minProfitIndex are independently found
				// But used in a combined way to delete/merge adjacent interval(s).
				if (loss < mergeLoss) {
					mergeLoss = loss;
					mergeLossIndex = i;
				}
				if (minProfit > second[2]) {
					minProfit = second[2];
					minProfitIndex = i + 1;
				}
			}
			if (mergeLoss >= minProfit) {
				intervals.remove(minProfitIndex);
			} else {
				int[] interval = intervals.get(mergeLossIndex);
				interval[1] = intervals.get(mergeLossIndex + 1)[1];
				interval[2] = prices[interval[1]] - prices[interval[0]];
//				intervals.set(minLossIndex, interval); // happens implicitly
				intervals.remove(mergeLossIndex + 1);
			}
		}
		for (int[] profits : intervals) {
			maxProfit += profits[2];
		}

		return maxProfit;
	}

}
