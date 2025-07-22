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
 * 
 * state machine is mathematical model of computation.
 */
public class P309BestTimeBuySellStockCooldown {

	public static void main(String[] args) {

		int[] prices = { 1, 2, 3, 0, 2 };

//		int[] prices = { 8, 6, 4, 3, 3, 2, 3, 5, 8, 3, 8, 2, 6 };

		int maxProfit3VarsSkip = maxProfit3VarsSkip(prices);
		System.out.println("3 Vars skip: The max profit with cooldown: " + maxProfit3VarsSkip);

		int maxProfit3VarsStart = maxProfit3VarsStart(prices);
		System.out.println("3 Vars start: The max profit with cooldown: " + maxProfit3VarsStart);

		int maxProfit3Vars = maxProfit3Vars(prices);
		System.out.println("3 Vars: The max profit with cooldown: " + maxProfit3Vars);

		int maxProfit1DDP = maxProfit1DDP(prices);
		System.out.println("1D DP: The max profit with cooldown: " + maxProfit1DDP);

		int maxProfit1DDPReverse = maxProfit1DDPReverse(prices);
		System.out.println("1D DP Reverse: The max profit with cooldown: " + maxProfit1DDPReverse);

		int maxProfitBF = maxProfitBF(prices);
		System.out.println("Brute Force: The max profit with cooldown: " + maxProfitBF);

	}

	// Presold is profit obtained for selling stocks on (i-2)th day
	// This can be used to solve cooldown = 1,2,..n day problems.
	private static int maxProfit3VarsSkip(int[] prices) {
		int n = prices.length;
		int held = Integer.MIN_VALUE;
		int sold = 0;
		int preSold = 0;
		for (int i = 0; i < n; i++) {
			int lastSold = sold;
			sold = Math.max(sold, held + prices[i]);
			held = Math.max(held, preSold - prices[i]);
			preSold = lastSold;
		}
		return sold;
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

	// State machine - models the behaviors and states of game agent.
	// There are 3 states - Held, Sold and Reset(starting point)
	// Held - Agent holds a stock which he bought at some point before.
	// Sold - Agent just sold the stock right before and is not holding any stock.
	// Reset - starting point(no stock buy/sell). Transient state before held and
	// sold. Due to cooldown rule, after sold state, agent can't immediately acquire
	// any stock after sell so force reset. Rest button for cycles of buy and sell.
	// There are 3 actions - Buy, Sell and Rest for transitioning between states.
	// sell - Agent sells the stock at current moment and transitions to sold state.
	// buy - Agent buys the stock at current moment and transitions to held state.
	// rest - no buy or sell transaction. At held/reset state agent performs no
	// transaction and it remains in held/reset state.
	// One can either rest(state remains held) or buy(state -> sold) at Held state
	// One can only enter to reset state after Sold state
	// One can either rest or buy(state -> sold) at Reset state
	// The previous state of sold can be only be held state since a stock is needed.
	// The previous state of held can be held(no transaction) or reset state to buy.
	// The previous state of reset can be reset(no transaction) or sold state(just).
	// The max profits from all the transactions is max(sold[n-1], reset[n-1])
	// At last price point one either sell the stock or does no transaction.
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

	// MP[i] is the maximal profit we can obtain while starting from index i to n.
	//
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
			// We either sell the stock at future or do nothing.
			MP[i] = Math.max(maxProfit, MP[i + 1]);
		}
		return MP[0];
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
