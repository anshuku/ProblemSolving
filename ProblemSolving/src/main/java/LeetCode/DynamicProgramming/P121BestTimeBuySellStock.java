package LeetCode.DynamicProgramming;

/*
 * P121. Best Time to Buy and Sell Stock - Easy
 * 
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * 
 * You want to maximize your profit by choosing a single day to buy one 
 * stock and choosing a different day in the future to sell that stock.
 * 
 * Return the maximum profit you can achieve from this transaction.
 * If you cannot achieve any profit, return 0.
 * 
 * Approach - DP
 */
public class P121BestTimeBuySellStock {

	public static void main(String[] args) {
		int[] prices = { 7, 1, 5, 3, 6, 4 };
//		int[] prices = { 1, 2, 4, 2, 5, 7, 2, 4, 9, 0, 9 };

		int profit = maxProfit(prices);

		System.out.println("The max profit to sell the stock is: " + profit);
	}

	public static int maxProfit(int[] prices) {
		int n = prices.length;
		int maxProfit = 0;
		int minPrice = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			minPrice = Math.min(minPrice, prices[i]);
			maxProfit = Math.max(maxProfit, prices[i] - minPrice);
		}
		return maxProfit;
	}

}
