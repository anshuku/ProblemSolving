package LeetCode.DynamicProgramming;

/*
 * P122. Best Time to Buy and Sell Stock II
 * 
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
 * 
 * On each day, you may decide to buy and/or sell the stock. You can only hold at most one share 
 * of the stock at any time. However, you can buy it then immediately sell it on the same day.
 * 
 * Find and return the maximum profit you can achieve.
 * 
 * Approach - DP
 */
public class P122BestTimeBuySellStockII {

	public static void main(String[] args) {

//		int[] prices = { 7, 1, 5, 3, 6, 4 };
		int[] prices = { 1, 2, 4, 2, 5, 7, 2, 4, 9, 0, 9 };

		int profit = maxProfit(prices);

		System.out.println("The max profit to sell at most 1 stock is: " + profit);
	}

	public static int maxProfit(int[] prices) {
		int maxProfit = 0;
		int minPrice = Integer.MAX_VALUE;
		int n = prices.length;

		for (int i = 0; i < n; i++) {
			if (minPrice > prices[i]) {
				minPrice = prices[i];
			}
			if (prices[i] - minPrice > 0) {
				maxProfit += prices[i] - minPrice;
				minPrice = prices[i];
			}
		}
		return maxProfit;
	}

}
