package LeetCode.Arrays;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/*
 * P3984. Divisible Game - Medium
 * 
 * You are given an integer array nums of length n.
 * 
 * Alice and Bob are playing a game. Alice chooses:
 * 
 * > An integer k such that k > 1.
 * > Two integers l and r such that 0 <= l <= r < n.
 * 
 * Initially, both Alice's and Bob's scores are 0.
 * 
 * For each index i in the range [l, r] (inclusive):
 * 
 * > If nums[i] is divisible by k, Alice's score increases by nums[i].
 * > Otherwise, Bob's score increases by nums[i].
 * 
 * The score difference is Alice's score minus Bob's score.
 * 
 * Alice wants to maximize the score difference. If there are multiple values of 
 * k that achieve the maximum score difference, she chooses the smallest such k.
 * 
 * Return the product of the maximum score difference and the chosen 
 * value of k. Since the result can be large, return it modulo 10^9 + 7.
 * 
 * Approach - Sieve Factorization + Kadanes's Algorithm
 */
public class P3984DivisibleGame {

	final static int mod = 1_000_000_007;

	public static void main(String[] args) {
//		int[] nums = { 1, 4, 6, 8 }; // 36

//		int[] nums = { 2, 1, 2 }; // 6

//		int[] nums = { 1 }; // 1000000005

		int[] nums = { 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983, 999983,
				999983 }; // 993289245

		int maxScoreDiffKProductHashSet = divisibleGameHashSet(nums);
		System.out
				.println("HashSet: The product of max score difference and chose k is: " + maxScoreDiffKProductHashSet);

		int maxScoreDiffKProductTreeSet = divisibleGameTreeSet(nums);
		System.out
				.println("TreeSet: The product of max score difference and chose k is: " + maxScoreDiffKProductTreeSet);
	}

	// Sieve of Prime Factors + Kadane
	// Math: Each number can be written in some form of its prime factors, example
	// 100 = 2^2*5^2.
	// Brute Force approach: We select each number from 2 to max number in nums
	// (<=10^6) and check each subarray (10^3 * 10^3 = 10^6) in O(n^2) time to
	// get the maxDiff (Alice - Bob) * smallest K for maxDiff. This will cost
	// 10^6*10^6 >>> acceptable 10^8.
	// Optimization: We divide the task in 2 phases:
	// 1. We have to optimally find all possible values of k
	// 2. Instead of checking each subarray, we should use Kadane's Algorithm, to
	// find maxDiff.
	// To find all possible k, we will find all prime factors of each number in nums
	// and put it in ordered set because we want the smallest k for maximum diff(can
	// use TreeSet for easy tie breaks). We will only update k if we get maxDiff
	// greater than current. In case of HashSet if the maxDiff is same then we
	// compare current k with bestK, if bestK > k update the bestK = k. In case of a
	// sorted set like TreeSet, the ties favor the smallest k. Set avoid duplicates.
	// We avoid checking every k: If k divides nothing in nums, every element
	// becomes -ve, so the best diff that k is < 0. If k divides at least 1 element,
	// picking just that element alone gives diff > 0. This means we only need to
	// test k values that are divisors (>1) of some element in nums - found by trail
	// division up to sqrt(num) for each element.
	// Now for a particular k, the problem boils down to finding the max subarray
	// sum. This is because we'll add a num from nums if it's divisibly by k else
	// subtract k. This naturally gives us the diff between alice and bob score. We
	// also add 2 in case we don't get any prime factors (all 1) so that all number
	// can go to Bob. We use Kadane's algo to maintain max subarray sum (maxDiff)
	// over any subset.
	// Modulo care: bestDiff can be -ve (e.g. if Alice's best option is to lose less
	// badly than the alternative), and Java's % preserves the sign of the dividend.
	// Hence, bestDiff % mod might stay -ve. Add MOD and re-mod to normalize into
	// [0, mod - 1] before multiplying by bestK % mod.
	// Time complexity - O(n*sqrt(m) + d*n), where n = length of nums, m = max
	// number in nums, d = number of distinct divisor candidates collected (bounded
	// by total divisor counts across all elements), each requiring an O(n) Kadane's
	// Pass. The divisor enumeration per element costs O(sqrt(num)).
	// Space comeplexity - O(d) for set containing all the divisors.
	public static int divisibleGameHashSet(int[] nums) {
		Set<Integer> set = new HashSet<>();
		set.add(2);

		// Get all the prime factors
		// Sieve
		for (int num : nums) {
			int temp = num;
			for (int d = 2; d * d <= num; d++) {
				if (num % d == 0) {
					set.add(d);

					set.add(num / d); // avoids temp but adds more time due to Hashing.

//					while (temp % d == 0) {
//						temp /= d;
//					}
				}
			}
			if (num > 1) {
				set.add(temp);
			}
//			if (temp > 1) {
//				set.add(temp);
//			}
		}

		long bestK = Integer.MAX_VALUE;
		long maxScore = Integer.MIN_VALUE;

		for (int k : set) {
			long currSum = 0;
			long maxSoFar = Integer.MIN_VALUE;

			for (int num : nums) {
				int val = num % k == 0 ? num : -num;

				currSum += val;

				if (maxSoFar < currSum) {
					maxSoFar = currSum;
				}

				if (currSum < 0) {
					currSum = 0;
				}
			}

			if (maxScore < maxSoFar || (maxScore == maxSoFar && bestK > k)) {
				maxScore = maxSoFar;
				bestK = k;
			}
		}

		long product = ((maxScore * bestK) % mod + mod) % mod;

		return (int) product;
	}

	// Time complexity - O(n*sqrt(m)*logd + d*n), where n = length of nums, m = max
	// number in nums, d = number of divisors. here, n*sqrt(m)*logd is for finding
	// the divisors and inserting them into TreeSet.
	private static int divisibleGameTreeSet(int[] nums) {
		Set<Integer> set = new TreeSet<>();
		set.add(2); // Handles cases where all nums are 1 or no permissible divisor.

		for (int num : nums) {
			int temp = num;
			for (int d = 2; d * d <= num; d++) {
				if (num % d == 0) {
					set.add(num);

					// set.add(num / d); avoids using temp but adds more time due to Hashing.

					while (temp % d == 0) {
						temp /= d;
					}
				}
			}
			if (temp > 1) {
				set.add(temp);
			}
		}

		long bestK = 0;
		long maxScore = Integer.MIN_VALUE;

		for (int k : set) {
			long maxSoFar = Integer.MIN_VALUE;
			long currSum = 0;

			for (int num : nums) {
				int val = num % k == 0 ? num : -num;

				currSum += val;
				if (maxSoFar < currSum) {
					maxSoFar = currSum;
				}

				if (currSum < 0) {
					currSum = 0;
				}
			}

			if (maxScore < maxSoFar) {
				maxScore = maxSoFar;
				bestK = k;
			}
		}

		long product = ((maxScore * bestK) % mod + mod) % mod;

		return (int) product;
	}

}
