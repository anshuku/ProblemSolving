package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P714. Best Time to Buy and Sell Stock with Transaction Fee
 * 
 * You are given an array prices where prices[i] is the price of a given stock 
 * on the ith day, and an integer fee representing a transaction fee.
 * 
 * Find the maximum profit you can achieve. You may complete as many transactions 
 * as you like, but you need to pay the transaction fee for each transaction.
 * 
 * Note:
 * You may not engage in multiple transactions simultaneously 
 * (i.e., you must sell the stock before you buy again).
 * The transaction fee is only charged once for each stock purchase and sale.
 * 
 * Approach - DP
 */
public class P714BestTimeBuySellStockTransactionFee {

	public static void main(String[] args) {

//		int[] prices = { 1, 3, 2, 8, 4, 9 };
//		int fee = 2;

//		int[] prices = { 1, 3, 7, 5, 10, 3 };
//		int fee = 3;

//		int[] prices = { 1, 3, 5, 7 };
//		int fee = 2;

		int[] prices = { 1, 4, 7, 10 };
		int fee = 2;

//		int[] prices = { 1, 4, 3, 6 };
//		int fee = 2;

		int maxProfit1Var = maxProfit1Var(prices, fee);
		System.out.println("Buy price: The max profit while selling stock with transaction fee: " + maxProfit1Var);

		int maxProfit2StatesBuy = maxProfit2StatesBuy(prices, fee);
		System.out.println(
				"2 States Buy: The max profit while selling stock with transaction fee: " + maxProfit2StatesBuy);

		int maxProfit2StatesSell = maxProfit2StatesSell(prices, fee);
		System.out.println(
				"2 States Sell: The max profit while selling stock with transaction fee: " + maxProfit2StatesSell);

		int maxProfit2States = maxProfit2States(prices, fee);
		System.out.println("2 States: The max profit while selling stock with transaction fee: " + maxProfit2States);

		int maxProfit1DDP = maxProfit1DDP(prices, fee);
		System.out.println("1D DP: The max profit while selling the stock with transaction fee: " + maxProfit1DDP);

		int maxProfit2DDP = maxProfit2DDP(prices, fee);
		System.out.println("2D DP: The max profit while selling the stock with transaction fee: " + maxProfit2DDP);
	}

	// Greedy
	// price > (buyPrice + fee), price - fee > buyPrice (current).
	private static int maxProfit1Var(int[] prices, int fee) {
		int buyPrice = Integer.MAX_VALUE;
		int profit = 0;
		for (int price : prices) {
			if (buyPrice > price) {
				buyPrice = price;
			} else if (price > (buyPrice + fee)) {
				profit += price - (buyPrice + fee);
				// It means buying the stock again at the adjusted price.
				// To find subsequent profits, it needs to be reflected that a fee was paid.
				// Any future price increase must consider previous fee paid
				buyPrice = price - fee; // effective cost after deducting fee
			}
		}
		return profit;
	}

	// Buying or selling either one of them can involve fee
	private static int maxProfit2StatesBuy(int[] prices, int fee) {
		int sold = 0, held = Integer.MIN_VALUE;
		for (int price : prices) {
			sold = Math.max(sold, held + price);
			held = Math.max(held, sold - price - fee); // buying involves fee
		}
		return sold;
	}

	// Here since there is a possibility of cash value overflowing max int
	// Use long to store the cash and sold
	// This happens since held is initialized with -INFs
	private static int maxProfit2StatesSell(int[] prices, int fee) {
		long sold = 0, held = Integer.MIN_VALUE;
		for (int price : prices) {
			sold = Math.max(sold, held + price - fee); // selling involves fee
			held = Math.max(held, sold - price);
		}
		return (int) sold;
	}

	// Here no overflow since hold is initialized to -prices[0]
	private static int maxProfit2States(int[] prices, int fee) {
		int n = prices.length;
		int cash = 0; // sell // fee applied to ensure profit >= 0
		int hold = -prices[0]; // buy, no fee since we may not be able to sell
		for (int i = 1; i < n; i++) {
			int lastCash = cash; // not needed 1 5 8 -> profit is 7 - fee and not 7 - 2*fee
			// selling and buying at same day is not needed.
			// hold can have today's non holding profit since it depends
			// on best possible non holding profit upto that day.
			cash = Math.max(cash, hold + prices[i] - fee); // selling involves fee
			hold = Math.max(hold, lastCash - prices[i]);
		}
		return (int) cash;
	}

	private static int maxProfit1DDP(int[] prices, int fee) {
		int n = prices.length;
		int[] cash = new int[n];
		int[] hold = new int[n];
		hold[0] = -prices[0];
		for (int i = 1; i < n; i++) {
			cash[i] = Math.max(cash[i - 1], hold[i - 1] + prices[i] - fee);
			hold[i] = Math.max(hold[i - 1], cash[i - 1] - prices[i]);
		}
		return cash[n - 1];
	}

	public static int maxProfit2DDP(int[] prices, int fee) {
		int maxProfit = 0;
		int n = prices.length;
		int k = n - 1;
		int[] txPrice = new int[k];
		int[] txProfit = new int[k];

		Arrays.fill(txPrice, prices[0]);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				int profit = 0;
				if (j != 0) {
					profit = txProfit[j - 1];
				}
				txPrice[j] = Math.min(txPrice[j], prices[i] - profit);
				txProfit[j] = Math.max(txProfit[j], prices[i] - txPrice[j] - fee);
			}
		}
		for (int profit : txProfit) {
			maxProfit = Math.max(maxProfit, profit);
		}
		return maxProfit;
	}
}
