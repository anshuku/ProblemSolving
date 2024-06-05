package LeetCode.Arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Alice plays the following game, loosely based on the card game "21".
 * Alice starts with 0 points and draws numbers while she has less than k points. During each draw, she gains an integer number of points randomly from the range [1, maxPts], where maxPts is an integer. Each draw is independent and the outcomes have equal probabilities.
 * Alice stops drawing numbers when she gets k or more points.
 * Return the probability that Alice has n or fewer points.
 * Answers within 10^-5 of the actual answer are considered accepted.
 * 
 * 
 */
public class P837New21Game {

	public static void main(String[] args) {
//		int n = 10, k = 1, maxPts = 10;//1
//		int n = 3, k = 2, maxPts = 3;// 0.88
//		int n = 3, k = 1, maxPts = 3;//0.88
//		int n = 6, k = 1, maxPts = 10;// 0.6
		int n = 21, k = 17, maxPts = 10;// 0.73278

		P837New21Game png = new P837New21Game();

		double probability = png.new21GameWindowArrOne(n, k, maxPts);

//		double probability = png.new21GameWindowArrSpace(n, k, maxPts);

//		double probability = png.new21GameWindowArrTwo(n, k, maxPts);

//		double probability = png.new21GameWindowMap(n, k, maxPts);

//		double[] cache = new double[k];
//		double probability = png.new21GameDfsArr(0, n, k, maxPts, cache);

//		double probability = png.new21GameDfsMap(0, n, k, maxPts);

//		double probability = png.new21GameBruteForce(n, k, maxPts);

		System.out.printf("The probability for drawing a sum less than n = %d is %f", n, probability);
	}

	private double new21GameWindowArrOne(int n, int k, int maxPts) {
		if (k == 0 || n >= k - 1 + maxPts) {
			return 1;
		}
		double windowSum = 1;
		double probability = 0;
		double[] dp = new double[n + 1];
		dp[0] = 1;
		// starting from 1 to n
		for (int i = 1; i <= n; i++) {
			dp[i] = windowSum / maxPts;
			if (i < k) {
				windowSum += dp[i];
			} else {
				probability += dp[i];// probability from k to n
			}
			if (i >= maxPts) {
				windowSum -= dp[i - maxPts];
			}
		}
		return probability;
	}

	// Its very important to use 1.0 instead of 1 for double values
	private double new21GameWindowArrSpace(int n, int k, int maxPts) {
		if (k == 0 || n >= k - 1 + maxPts) {
			return 1;
		}
		double sum = 0.0;
		double[] dp = new double[k];
		dp[0] = 0.0;
		// starting from i = 1 to k - 1
		for (int i = 1; i < k; i++) {
			if (i - maxPts > 0) {
				sum -= dp[i - maxPts - 1];
			}
			dp[i] += sum / maxPts;
			if (i <= maxPts) {
				dp[i] += 1.0 / maxPts;
			}
			sum += dp[i];
		}
		double ans = 0.0;
		int upperBound = Math.min(n, k - 1 + maxPts);
		// starting from i = k to upperBound
		for (int i = k; i <= upperBound; i++) {
			if (i - maxPts > 0) {
				sum -= dp[i - maxPts - 1];
			}
			ans += sum / maxPts;
			if (i <= maxPts) {
				ans += 1.0 / maxPts;
			}
		}
		return ans;
	}

	private double new21GameWindowArrTwo(int n, int k, int maxPts) {
		if (k == 0 || n >= k - 1 + maxPts) {
			return 1;
		}
		double windowSum = 0;
		for (int i = k; i <= k - 1 + maxPts; i++) {
			if (i <= n) {
				windowSum += 1;
			}
		}
		double[] dp = new double[n + 1];
		for (int i = k - 1; i >= 0; i--) {
			dp[i] = windowSum / maxPts;
			double remove = 0;
			if (i + maxPts <= n) {
//				remove = dp[i + maxPts] == 0 ? 1 : dp[i + maxPts];
				remove = dp[i + maxPts];
				if (remove == 0) {
					remove = 1;
				}
			}
			windowSum += dp[i] - remove;
		}
		return dp[0];
	}

	private double new21GameWindowMap(int n, int k, int maxPts) {
		if (k == 0 || n >= k - 1 + maxPts) {
			return 1.0;
		}

		double windowSum = 0;
		for (int i = k; i <= k - 1 + maxPts; i++) {
			if (i <= n) {
				windowSum += 1;
			}
		}
		Map<Integer, Double> dp = new HashMap<>();

		for (int i = k - 1; i >= 0; i--) {
			dp.put(i, windowSum / maxPts);
			double remove = 0;
			if (i + maxPts <= n) {
				remove = dp.getOrDefault(i + maxPts, 1.0);
			}
			windowSum += dp.get(i) - remove;
		}

		return dp.get(0);
	}

	private double new21GameDfsArr(int score, int n, int k, int maxPts, double[] cache) {
		if (score >= k) {
			if (score <= n) {
				return 1;
			} else {
				return 0;
			}
		}
		if (cache[score] != 0) {
			return cache[score];
		}

		double prob = 0;
		for (int i = 1; i <= maxPts; i++) {
			prob += new21GameDfsArr(score + i, n, k, maxPts, cache);
		}
		cache[score] = prob / maxPts;
		return cache[score];
	}

	Map<Integer, Double> cache = new HashMap<>();

	private double new21GameDfsMap(int score, int n, int k, int maxPts) {

		if (score >= k) {
			if (score <= n) {
				return 1;
			} else {
				return 0;
			}
		}
		if (cache.containsKey(score)) {
			return cache.get(score);
		}
		double prob = 0;
		for (int i = 1; i <= maxPts; i++) {
			prob += new21GameDfsMap(score + i, n, k, maxPts);
		}
		cache.put(score, prob / maxPts);
		return cache.get(score);
	}

	public double new21GameBruteForce(int n, int k, int maxPts) {
		if (k == 0 || n >= k - 1 + maxPts) {
			return 1.0;
		}
		int maxPoint = k - 1 + maxPts;

		// for cases where n > maxPoint + 1, it's already returning 1.0
		double[] dp = new double[maxPoint + 1];
		dp[0] = 1.0;
		for (int i = 1; i <= maxPoint; i++) {
			for (int pt = 1; pt <= maxPts; pt++) {
				// Note - for few iterations, i - pt must be carefully checked
				if (i - pt >= 0 && i - pt < k) {
					dp[i] += dp[i - pt] * (1.0 / maxPts);
				}
			}
		}
		System.out.println("The dp array is " + Arrays.toString(dp));
		double probability = 0.0;
		for (int i = k; i <= n; i++) {
			probability += dp[i];
		}
		return probability;
	}

}
