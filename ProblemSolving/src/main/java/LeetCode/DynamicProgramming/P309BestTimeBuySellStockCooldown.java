package LeetCode.DynamicProgramming;

/*
 * P309. Best Time to Buy and Sell Stock with Cooldown
 * 
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * 
 * Find the maximum profit you can achieve. You may complete as many transactions as you like 
 * (i.e., buy one and sell one share of the stock multiple times) with the following restrictions:
 * 
 * After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).
 * Note: You may not engage in multiple transactions simultaneously 
 * (i.e., you must sell the stock before you buy again).
 * 
 * Approach - DP
 */
public class P309BestTimeBuySellStockCooldown {

	public static void main(String[] args) {

		int[] prices = { 1, 2, 3, 0, 2 };

//		int[] prices = { 8, 6, 4, 3, 3, 2, 3, 5, 8, 3, 8, 2, 6 };

		int maxProfit1DDPReverse = maxProfit1DDPReverse(prices);
		System.out.println("1D DP Reverse: The max profit with cooldown: " + maxProfit1DDPReverse);

		int maxProfit3VarsStart = maxProfit3VarsStart(prices);
		System.out.println("3 Vars start: The max profit with cooldown: " + maxProfit3VarsStart);

		int maxProfit3Vars = maxProfit3Vars(prices);
		System.out.println("3 Vars: The max profit with cooldown: " + maxProfit3Vars);

		int maxProfit1DDP = maxProfit1DDP(prices);
		System.out.println("1D DP: The max profit with cooldown: " + maxProfit1DDP);

		int maxProfitBF = maxProfitBF(prices);
		System.out.println("Brute Force: The max profit with cooldown: " + maxProfitBF);

	}

	// Time complexity - O(n^2), outer loop n and inner loop 1 to n
	// Total 1+2..+n = n*(n+1)/2 or O(n^2).
	// Space complexity - O(n) for dp array.
	private static int maxProfit1DDPReverse(int[] prices) {
		int n = prices.length;
//		if (n < 2 || prices[n - 2] > prices[n - 1]) {
//			return 0;
//		}
		int[] MP = new int[n + 2];
		for (int i = n - 2; i >= 0; i--) {
			int maxProfit = 0;
			for (int j = i + 1; j < n; j++) {
//				if (prices[j] > prices[i]) {
//					maxProfit = Math.max(maxProfit, prices[j] - prices[i] + MP[j + 2]);
//				}
				maxProfit = Math.max(maxProfit, prices[j] - prices[i] + MP[j + 2]);
			}
			MP[i] = Math.max(maxProfit, MP[i + 1]);
		}
		return MP[0];
	}

	// reset can be the starting state since there are no transactions
	// reset is set to 0 and sold and held at initial points are -INF
	private static int maxProfit3VarsStart(int[] prices) {
		int held = Integer.MIN_VALUE;
		int sold = Integer.MIN_VALUE;
		int reset = 0;
		for (int price : prices) {
			int lastSold = sold;
			int lastHeld = held;
			held = Math.max(held, reset - price);
			sold = held + price; // held can be lastHeld here
			reset = Math.max(reset, lastSold);
		}
		return Math.max(sold, reset);
	}

	// Since we only need the last 3 states
	// so a sliding window of size 1 is enough
	private static int maxProfit3Vars(int[] prices) {
		int n = prices.length;
		int held = -prices[0];
		int sold = 0;
		int reset = 0;
		for (int i = 1; i < n; i++) {
			int lastSold = sold;
			held = Math.max(held, reset - prices[i]);
			sold = held + prices[i];
			reset = Math.max(reset, lastSold);
		}
		return Math.max(sold, reset);
	}

	// State machine
	// There are 3 states - Held, Sold and Reset(starting point)
	// Due to cooldown rule, agent can't held any stock after sell so force reset
	// There are 3 actions - Buy, Sell and Rest
	// Rest state - no buy or sell transaction
	// One can either rest(state remains held) or buy(state -> sold) at Hold state
	// One can only enter to reset state after Sold state
	// One can either rest or buy(state -> sold) at Reset state
	// The previous state of sold can be only be held state since a stock is needed
	// The previous state of held can be held(no transaction) or reset state to buy
	// The prvious state of reset can be rest(no transaction) or sold state(just)
	// One can also find the actions needed to find the max profit
	private static int maxProfit1DDP(int[] prices) {
		int n = prices.length;
		int[] held = new int[n];
		int[] sold = new int[n];
		int[] reset = new int[n];
		held[0] = -prices[0];
		for (int i = 1; i < n; i++) {
			held[i] = Math.max(held[i - 1], reset[i - 1] - prices[i]);
			sold[i] = held[i - 1] + prices[i];
			reset[i] = Math.max(reset[i - 1], sold[i - 1]);// no transaction
		}
		return Math.max(reset[n - 1], sold[n - 1]);
	}

	public static int maxProfitBF(int[] prices) {
		int n = prices.length;
		int minPrice = prices[0];
		int maxProfit = 0;

		for (int i = 1; i < n; i++) {
			if (minPrice > prices[i]) {
				minPrice = prices[i];
			} else if (prices[i] > minPrice) {
				int profit = prices[i] - minPrice;
				if (i < n - 2 && prices[i + 2] > prices[i + 1]) {
					if (profit < (prices[i + 2] - prices[i + 1])) {
						continue;
					} else {
						maxProfit += profit;
						minPrice = prices[i];
						i++;
					}
				} else {
					maxProfit += profit;
					minPrice = prices[i];
				}
			}
		}

		return maxProfit;

	}

}
