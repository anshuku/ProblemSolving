package LeetCode.DynamicProgramming;

/*
 * P123. Best Time to Buy and Sell Stock III
 * 
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * 
 * Find the maximum profit you can achieve. You may complete at most two transactions.
 * 
 * Note: You may not engage in multiple transactions simultaneously 
 * (i.e., you must sell the stock before you buy again).
 * 
 * Approach - DP
 * You may not engage in multiple transactions simultaneously - no overlap
 * 
 */
public class P123BestTimeBuySellStockIII {

	public static void main(String[] args) {

//		int[] prices = { 3, 3, 5, 0, 0, 3, 1, 4 };

//		int[] prices = { 1, 2, 3, 4, 5 };

		int[] prices = { 3, 9, 1, 9, 2, 9 };

		int maxProfitOnePass = maxProfitOnePass(prices);

		System.out.println("One Pass: The max profit for selling the stock at most twice: " + maxProfitOnePass);

		int maxProfitBDP = maxProfitBidirectional(prices);

		System.out.println("Bidirectional: The max profit for selling the stock at most twice: " + maxProfitBDP);

	}

	// One Pass Simulation
	// 1st transaction Profit is obtained just like Best Time to Buy and Sell Stock
	// 2nd transaction price is obtained by using 1st transaction's profit
	// The 2nd price is the cost of reinvestment, compensated by 1st price.
	// Time Complexity - O(n)
	// Space Complexity - O(1)
	private static int maxProfitOnePass(int[] prices) {
		int t1Price = prices[0];
		int t1Profit = 0;
		int t2Price = prices[0];
		int t2Profit = 0;
		int n = prices.length;
		for (int i = 0; i < n; i++) {
			t1Price = Math.min(t1Price, prices[i]);
			t1Profit = Math.max(t1Profit, prices[i] - t1Price);
			t2Price = Math.min(t2Price, prices[i] - t1Profit);
			t2Profit = Math.max(t2Profit, prices[i] - t2Price);
		}
		return t2Profit;
	}

	// Bidirectional dynamic programming - Divide and conquer
	// The input array is divided into two and 2 max profits are obtained.
	// The sum of the two proifts in [0,1..i] and [i+1,i+2,..n-1] is result
	// Time Complexity - O(n)
	// Space Complexity - O(n)
	public static int maxProfitBidirectional(int[] prices) {
		int maxProfit = 0;
		int n = prices.length;
		int minLeft = prices[0];
		int maxRight = prices[n - 1];
		int[] left = new int[n];
		int[] right = new int[n + 1];// Extra length for single transaction at end

		for (int i = 1; i < n; i++) {
			minLeft = Math.min(minLeft, prices[i]);
			left[i] = Math.max(left[i - 1], prices[i] - minLeft);

			maxRight = Math.max(maxRight, prices[n - i - 1]);
			right[n - i - 1] = Math.max(right[n - i], maxRight - prices[n - i - 1]);
		}
		for (int i = 0; i < n; i++) {
			maxProfit = Math.max(maxProfit, left[i] + right[i + 1]);
		}
		return maxProfit;
	}

}
