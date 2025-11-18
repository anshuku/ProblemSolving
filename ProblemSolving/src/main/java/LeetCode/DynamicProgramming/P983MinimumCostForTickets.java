package LeetCode.DynamicProgramming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * P983. Minimum Cost For Tickets - Medium
 * 
 * You have planned some train traveling one year in advance. The days of the year in which 
 * you will travel are given as an integer array days. Each day is an integer from 1 to 365.
 * 
 * Train tickets are sold in three different ways:
 * 
 * > a 1-day pass is sold for costs[0] dollars,
 * > a 7-day pass is sold for costs[1] dollars, and
 * > a 30-day pass is sold for costs[2] dollars.
 * 
 * The passes allow that many days of consecutive travel.
 * 
 * For example, if we get a 7-day pass on day 2, then we can travel for 7 days: 2, 3, 4, 5, 6, 7, and 8.
 * 
 * Return the minimum number of dollars you need to travel every day in the given list of days.
 * 
 * Approach - DP
 */
public class P983MinimumCostForTickets {

	public static void main(String[] args) {
		int[] days = { 1, 4, 6, 7, 8, 20 };
		int[] costs = { 2, 7, 15 };

//		int[] days = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 31 };
//		int[] costs = { 2, 7, 15 };

		int minCostMemoizedWindow = mincostTicketsMemoizedWindow(days, costs);
		System.out.println("Window Variant: The minimum cost for tickets " + minCostMemoizedWindow);

		int minCost1DDP = mincostTickets1DDP(days, costs);
		System.out.println("1D DP: The minimum cost for tickets " + minCost1DDP);

		int minCost1DDPRev = mincostTickets1DDPRev(days, costs);
		System.out.println("1D DP Rev: The minimum cost for tickets " + minCost1DDPRev);

		int minCostMemoized2D = mincostTicketsMemoized2D(days, costs);
		System.out.println("Memoized 2D: The minimum cost for tickets " + minCostMemoized2D);

		int minCostMemoized1D = mincostTicketsMemoized1D(days, costs);
		System.out.println("Memoized 1D: The minimum cost for tickets " + minCostMemoized1D);
	}

	// DP Memoization - Window Variant
	// We only need to buy a travel pass on a day we intend to travel.
	// dp[i] is the cost to travel from day - day[i] to the end of the plan.
	// If j1 is the largest index such that days[j1] < days[i] + 1,
	// j7 is the largest index such that days[j7] < days[i] + 7,
	// j30 is the largest index such that days[j30] < days[i] + 30, then
	// dp[i] = min(dp[j1] + costs[0], dp[j7] + costs[1], dp[j30] + costs[2])
	// Time complexity - O(n) where n is the unique number of days in travel plan
	// Space complexity - O(n) for dp array.
	private static int mincostTicketsMemoizedWindow(int[] days, int[] costs) {
		int n = days.length;
		int[] dp = new int[n];
		return recursive(days, costs, 0, dp);
	}

	private static int recursive(int[] days, int[] costs, int i, int[] dp) {
		if (i >= days.length) {
			return 0;
		}
		if (dp[i] != 0) {
			return dp[i];
		}
		int j = i;
		int ans = Integer.MAX_VALUE;
		for (int k = 0; k < 3; k++) {
			while (j < days.length && days[j] < days[i] + durations[k]) {
				j++;
			}
			int cost = recursive(days, costs, j, dp);
			ans = Math.min(ans, costs[k] + cost);
		}
		return dp[i] = ans;
	}

	private static int mincostTickets1DDP(int[] days, int[] costs) {
		int n = days.length;
		int lastDay = days[n - 1];
		int[] dp = new int[lastDay + 1];
		int i = 0;
		for (int day = 1; day <= lastDay; day++) {
			// If we don't need to travel on this day, the cost won't change.
			if (day < days[i]) {
				dp[day] = dp[day - 1];
			} else {
				// Buy a pass on this day and move on to the next travel day.
				i++;
				int c1 = costs[0] + dp[day - 1];
				int c2 = costs[1];
				if (day - 7 > 0) {
					c2 += dp[day - 7];
				}
				int c3 = costs[2];
				if (day - 30 > 0) {
					c3 += dp[day - 30];
				}
				// Store the cost with the minimum of the 3 options.
				dp[day] = Math.min(c1, Math.min(c2, c3));
			}
		}
		return dp[lastDay];
	}

	public static int mincostTickets1DDPRev(int[] days, int[] costs) {
		int n = days.length;
		int lastDay = days[n - 1];
		int[] dp = new int[lastDay + 1];
		dp[lastDay] = Math.min(costs[0], Math.min(costs[1], costs[2]));
		int ptr = n - 2;

		for (int i = lastDay - 1; i >= 0; i--) {
			if (ptr >= 0 && i == days[ptr]) {
				int c1 = costs[0] + dp[i + 1];
				int c2 = costs[1];
				if (i + 7 <= lastDay) {
					c2 += dp[i + 7];
				}
				int c3 = costs[2];
				if (i + 30 <= lastDay) {
					c3 += dp[i + 30];
				}
				dp[i] = Math.min(c1, Math.min(c2, c3));
				ptr--;
			} else {
				dp[i] = dp[i + 1];
			}
		}
		return dp[1];
	}

	static int[] durations = { 1, 7, 30 };

	private static int mincostTicketsMemoized2D(int[] days, int[] costs) {
		int n = days.length;
		int maxValidity = days[n - 1] + 30;
		int[][] dp = new int[n][maxValidity];
		for (int[] arr : dp) {
			Arrays.fill(arr, -1);
		}
		return recursive(days, costs, 0, 0, dp);
	}

	private static int recursive(int[] days, int[] costs, int day, int validity, int[][] dp) {
		if (day >= days.length) {
			return 0;
		} else if (dp[day][validity] != -1) { // Can use if instead of else if
			return dp[day][validity];
		} else if (days[day] <= validity) { // Can use if instead of else if
			return dp[day][validity] = recursive(days, costs, day + 1, validity, dp);
		} else {
			int c1 = costs[0] + recursive(days, costs, day + 1, days[day], dp);
			int c2 = costs[1] + recursive(days, costs, day + 1, days[day] + 6, dp);
			int c3 = costs[2] + recursive(days, costs, day + 1, days[day] + 29, dp);

			return dp[day][validity] = Math.min(c1, Math.min(c2, c3));
		}
	}

	// DP Memoization 1D - Top down
	// For each day, if we don't travel today it's better to wait and buy the pass.
	// If one travel today, then one must buy 1-day, 7-day or 30-day pass.
	// These choices can be expressed as recursion and we can use dynamic
	// programming. dp[i] is the cost of fulfilling the travel plan from day i to
	// end of the plan. If I've to travel today, the cost is dp[i] =
	// min(costs[0] + dp[i+1], min(costs[1] + min(dp[i+7], costs[2] + min(dp[i+30]))
	// Time complexity - O(W) where W is last day of travel which can be 365.
	// Space complexity - O(W) for dp array, isValidDay set and recursion stack with
	// max length = lastDay.
	private static int mincostTicketsMemoized1D(int[] days, int[] costs) {
		int n = days.length;
		int lastDay = days[n - 1];
		int[] dp = new int[lastDay + 1];
		Arrays.fill(dp, -1);
		Set<Integer> isValidDay = new HashSet<>();
		// Mark the days on which we want to travel.
		for (int day : days) {
			isValidDay.add(day);
		}
		return recursive(days, costs, 1, dp, isValidDay);
	}

	private static int recursive(int[] days, int[] costs, int day, int[] dp, Set<Integer> isValidDay) {
		if (day > days[days.length - 1]) {
			return 0;
		}
		// If we don't travel on this day, move on to the next day.
		if (!isValidDay.contains(day)) {
			return dp[day] = recursive(days, costs, day + 1, dp, isValidDay);
		}
		if (dp[day] != -1) {
			return dp[day];
		}
		int c1 = costs[0] + recursive(days, costs, day + 1, dp, isValidDay);
		int c2 = costs[1] + recursive(days, costs, day + 7, dp, isValidDay);
		int c3 = costs[2] + recursive(days, costs, day + 30, dp, isValidDay);
		// Store the cost with the minimum of the 3 options.
		return dp[day] = Math.min(c1, Math.min(c2, c3));
	}

}
