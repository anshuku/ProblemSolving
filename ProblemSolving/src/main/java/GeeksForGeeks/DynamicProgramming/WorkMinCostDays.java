package GeeksForGeeks.DynamicProgramming;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Given a sorted array arr[] consisting of N positive integers such that arr[i] represent the days 
 * in which a worker will work and an array cost[] of size 3 representing the salary paid to the 
 * workers for 1 day, 7 days and 30 days respectively, the task is to find the minimum cost required 
 * to have a worker for all the given days in arr[].
 * 
 * Input: arr[] = [2, 4, 6, 7, 8, 10, 17], cost[] = [3, 8, 20] Output: 14
 * 
 */
public class WorkMinCostDays {

	public static void main(String[] args) {
		int[] days = { 2, 4, 6, 7, 8, 10, 17 };
		int[] costs = { 3, 8, 20 };

//		int[] days = { 1, 2, 3, 4, 6, 7, 8, 9, 11, 15, 20, 29 };
//		int[] costs = { 3, 8, 20 };

//		int[] days = { 1, 4, 6, 7 };
//		int[] costs = { 2, 7, 15 };

		int costTabulation = minCostDaysTabulation(days, costs);

		System.out.println("The min cost for work via tabulation is " + costTabulation);

		int costRecursion = minCostDaysRecursion(days, costs);

		System.out.println("The min cost for work via recursion is " + costRecursion);

		int costMemoization = minCostDaysMemoization(days, costs);

		System.out.println("The min cost for work via memoization is " + costMemoization);

		int costQueues = minCostDaysQueues(days, costs);

		System.out.println("The min cost for work via 2 queues is " + costQueues);

	}

	// stores day, cost K-V pairs
	static class Pair<K, V> {

		private K key;
		private V value;

		Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}
	}

	private static int minCostDaysQueues(int[] days, int[] costs) {
		Queue<Pair<Integer, Integer>> qWeek = new LinkedList<>();
		Queue<Pair<Integer, Integer>> qMonth = new LinkedList<>();
		int ans = 0;
		for (int day : days) {
			while (!qWeek.isEmpty() && qWeek.peek().getKey() + 7 <= day) {
				qWeek.poll();
			}
			while (!qMonth.isEmpty() && qMonth.peek().getKey() + 30 <= day) {
				qMonth.poll();
			}
			qWeek.add(new Pair<>(day, ans + costs[1]));
			qMonth.add(new Pair<>(day, ans + costs[2]));

			ans = Math.min(ans + costs[0], Math.min(qWeek.peek().getValue(), qMonth.peek().getValue()));
		}
		return ans;
	}

	private static int minCostDaysMemoization(int[] days, int[] costs) {
		int N = days.length;
		int maxValidity = days[N - 1] + 30;
		int dp[][] = new int[N][maxValidity];
		for (int[] arr : dp) {
			Arrays.fill(arr, -1);
		}
		return minCostMemoization(days, costs, 0, 0, N, dp);
	}

	private static int minCostMemoization(int[] days, int[] costs, int i, int validity, int N, int[][] dp) {
		if (i >= N) {
			return 0;
		}
		if (dp[i][validity] != -1) {
			return dp[i][validity];
		} else if (days[i] <= validity) {
			return dp[i][validity] = minCostMemoization(days, costs, i + 1, validity, N, dp);
		} else {
			int c1 = costs[0] + minCostMemoization(days, costs, i + 1, days[i], N, dp);
			int c2 = costs[1] + minCostMemoization(days, costs, i + 1, days[i] + 6, N, dp);
			int c3 = costs[2] + minCostMemoization(days, costs, i + 1, days[i] + 29, N, dp);
			return dp[i][validity] = Math.min(c1, Math.min(c2, c3));
		}
	}

	private static int minCostDaysRecursion(int[] days, int[] costs) {

		return minCostRecursion(days, costs, 0, 0, days.length);
	}

	private static int minCostRecursion(int[] days, int[] costs, int i, int validity, int N) {
		if (i >= N) {
			return 0;
		}
		if (days[i] <= validity) {
			return minCostRecursion(days, costs, i + 1, validity, N);
		} else {
			int c1 = costs[0] + minCostRecursion(days, costs, i + 1, days[i], N);
			int c2 = costs[1] + minCostRecursion(days, costs, i + 1, days[i] + 6, N);
			int c3 = costs[2] + minCostRecursion(days, costs, i + 1, days[i] + 29, N);
			return Math.min(c1, Math.min(c2, c3));
		}
	}

	private static int minCostDaysTabulation(int[] days, int[] costs) {

		int N = days.length;
		int lastDay = days[N - 1];

		int[] dp = new int[lastDay + 1];
		int ptr = N - 2;

		dp[lastDay] = Math.min(costs[0], Math.min(costs[1], costs[2]));

		for (int i = lastDay - 1; i > 0; i--) {
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

}
