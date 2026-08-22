package LeetCode.Math;

import java.util.Arrays;

/*
 * P3987. Minimum Total Cost to Process All Elements - Medium
 * 
 * You are given an integer array nums and an integer k.
 * 
 * Initially, you have k units of resources.
 * 
 * You must process the elements of nums from left to right. 
 * To process the ith element, you need nums[i] resources.
 * 
 * If your available resources are less than nums[i], you may perform 
 * an operation that increases your available resources by k. The value of k 
 * is fixed and does not change throughout the process. The first such 
 * operation incurs a cost of 1, the second incurs a cost of 2, and so on.
 * 
 * After processing the ith element, your available resources decrease by nums[i].
 * 
 * Return an integer denoting the minimum total cost required to process all 
 * elements. Since the answer may be very large, return it modulo 10^9 + 7.
 * 
 * Constraints:
 * > 1 <= nums.length <= 10^5
 * > 1 <= nums[i] <= 10^9
 * > 1 <= k <= 10^9
 * 
 * Approach - Math, Modularity
 */
public class P3987MinimumTotalCostToProcessAllElements {

	static final int mod = 1_000_000_007;
	static final int inv2 = 500_000_004; // modular inverse of 2 mod (1e9+7)

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 3, 4 };
//		int k = 4;

//		int[] nums = { 1, 1, 7, 14 };
//		int k = 4;

//		int[] nums = { 1000000000, 1000000000, 1000000000, 1000000000, 1000000000 };
//		int k = 1;

		int[] nums = { 1000000000 };
		int k = 2;

//		int[] nums = { 242950953, 744666528, 941655623, 369742753 };
//		int k = 335106514;

		int[] nums2 = Arrays.copyOf(nums, nums.length);

		int[] nums3 = Arrays.copyOf(nums, nums.length);

		int[] nums4 = Arrays.copyOf(nums, nums.length);

		int[] nums5 = Arrays.copyOf(nums, nums.length);

		int minimumCostSumLongOpt = minimumCostSumLongOpt(nums, k);
		System.out.println("Sum Long Optimized: The minimum cost to process all elements: " + minimumCostSumLongOpt);

		int minimumCostSumLong = minimumCostSumLong(nums2, k);
		System.out.println("Sum Long: The minimum cost to process all elements: " + minimumCostSumLong);

		int minimumCostOptLong = minimumCostOptLong(nums3, k);
		System.out.println("Optimized Long: The minimum cost to process all elements: " + minimumCostOptLong);

		int minimumCost = minimumCost(nums4, k);
		System.out.println("The minimum cost to process all elements: " + minimumCost);

		int minimumCostAlt = minimumCostAlt(nums5, k);
		System.out.println("Alternate: The minimum cost to process all elements: " + minimumCostAlt);
	}

	private static int minimumCostSumLongOpt(int[] nums, int k) {
		long sum = 0;

		for (int num : nums) {
			sum += num;
		}

//		long ops = (sum + k - 1) / k % mod;
//		long total = ops * (ops - 1) / 2 % mod;

		sum -= k; // if this is ignored then long total = ops * (ops - 1) / 2 % mod;

		long ops = (sum + k - 1) / k % mod;

		long total = ops * (ops + 1) / 2 % mod;

		return (int) total;
	}

	private static int minimumCostSumLong(int[] nums, int k) {
		long sum = 0;

		for (int num : nums) {
			sum += num;
		}

		sum -= k;

		long ops = (sum + k - 1) / k % mod;

		long start = 1;
		long end = ops % mod;

		long count = ops % mod;

		long total = count * ((start + end) % mod) % mod;

		total = total * inv2 % mod;

		return (int) total;
	}

	// Long usage
	// * Walk through nums tracking real leftover resources and a real running count
	// opsSoFar of top-up operations already paid for.
	// * Whenever nums[i] > resources, compute the deficit and the minimum number of
	// top-ups ops = ceil(deficit/k) needed to cover it - this is a real integer, no
	// mod needed yet (since it's bounded by 10^9 - we don't mod).
	// * The ops operations are consecutively numbered opsSoFar + 1, ..., opsSoFar +
	// ops, so their cost is the arithmetic series sum ops * (first + last) / 2.
	// Since this sum eventually needs to be modded (it can get very large over 10^5
	// elements), do the "divide by 2" via multiplying by the modular inverse of 2,
	// not integer division after modding.
	// * Update resources and opSoFar with exact (unmodded) long arithmetic, since
	// they represent real physical quantities used in later comparisions, not
	// modular-answer values.
	// Only cost - which the problem actually asks to return mod 10^9 + 7 - should
	// ever be reduced via mod 1e9 + 7.
	// The general lesson: keep a hard boundary between "real quantities used for
	// real comparisions/arithmetic" (resources, ops) and "the final answer
	// accumulator" (cost). Only mod the latter, and whenever one need to divide
	// inside modular arithmetic, use a modular inverse instead of /.
	private static int minimumCostOptLong(int[] nums, int k) {
		int n = nums.length;

		long resources = k; // true resource count, never modded

		long cost = 0; // we need to put this in modular space

		long opsSoFar = 0; // true count of operations performed so far, never modded

		for (int i = 0; i < n; i++) {
			if (resources >= nums[i]) {
				resources -= nums[i];
				continue;
			}

			long deficit = nums[i] - resources;

			long ops = (deficit + k - 1) / k; // ceil(deficit / k), exact long math

			// This batch's operations are numbered opsSoFar + 1 ... opsSoFar + ops
			long start = (opsSoFar + 1) % mod;
			long end = (opsSoFar + ops) % mod;
			long count = ops % mod;

			long sum = count * ((start + end) % mod) % mod;
			sum = sum * inv2 % mod; // "/2" done via modular inverse.

			cost = (cost + sum) % mod;

			resources = resources + ops * k - nums[i]; // exact, guaranteed to land in [0, k-1]

			opsSoFar += ops;
		}
		return (int) cost;
	}

	// Bug 1: dividing by 2 after taking % mod
	// curr = ((curr * val) % mod + mod) % mod;
	// Here, curr * val is mathematically always even as it's val consecutive
	// integer's arithmetiic-series sum. Dividing by 2 is safe before modding. But
	// once we do % mod, that "evenness" can be destroyed (for the product > mod).
	// Now mod = 1000000007 is odd, so reducing an even number mod an odd number can
	// easily produce an odd result. Then curr/2 silently truncates and throws away
	// a remainder, corrupting the answer. For test case nums = [1000000000], k = 2
	// gives output 375000010 instead of 875000014 as the true value before mode is
	// 249999999500000000 (even), but after % mod it becomes 750000021 (odd).
	// Integer-dividing that by 2 gives 375000010 - which is the wrong output.
	// Fix: In modular arithmetic "divide by 2" means "multiply by the modular
	// inverse of 2", not integer-divide:
	// long INV2 = 500000004L; // modular inverse of 2 mod 1e9+7, since 2*500000004L
	// ≡ 1 (mod 1e9+7) similary any even number multiplied by 500000004L (modular
	// inverse) then taken mod with 1e9+7 gives half of that even number without
	// truncating the remainder. Hence sum = (curr*INV2) % mod; gives the AP sum.
	// Bug 2: modding k itself
	// k (available resources) is not a value in modular-answer-space - it's a real
	// quantity used later for a real comparision (nums[i] <= k). The code computes:
	// long product = ((val * initial) % mod + mod) % mod; // truncated to mod range
	// val * initial can be upto ~10^9 * 10^9 = 10^18, far bigger than mod = 10^9.
	// Reducing it via mod 10^9 + 7 before using it to compute the true leftover
	// resource count silently corrupts k for every later element whenever val *
	// initial exceeds mod. It's a ticking bug for larger inputs.
	// We need to realize that k (lefover resources) is always bounded by initial -
	// 1 (by minimality of val) so it should just be tracked with plain long
	// arithmetic - never modded.
	public static int minimumCost(int[] nums, int k) {
		int n = nums.length;

		int initial = k;

		long cost = 0;

		long units = 0;

		for (int i = 0; i < n; i++) {
			if (nums[i] <= k) {
				k -= nums[i];
				continue;
			}

			int temp = nums[i];

			nums[i] -= k;

			long val = nums[i] / initial;

			if (nums[i] % initial > 0) {
				val = ((val + 1) % mod + mod) % mod;
			}

			units = ((units + 1) % mod + mod) % mod;

			long curr = ((2l * units % mod + mod) % mod) + val - 1;
			curr = (curr + mod) % mod;
			curr = ((curr * val) % mod + mod) % mod;

			long sum = curr * inv2 % mod;
			cost = ((cost + sum) % mod + mod) % mod;

			long product = ((val * initial) % mod + mod) % mod;

			k = ((k + (int) product) % mod + mod) % mod;

			k = ((k - temp) % mod + mod) % mod;

			units = ((units + val - 1) % mod + mod) % mod;
		}

		return (int) cost;
	}

	private static int minimumCostAlt(int[] nums, int k) {
		int n = nums.length;

		long kRun = k;
		long cost = 0;
		long units = 0;

		for (int i = 0; i < n; i++) {
			if (kRun >= nums[i]) {
				kRun -= nums[i];
				continue;
			}

			long temp = nums[i];

			nums[i] -= kRun;

			long val = nums[i] / k;

			if (nums[i] % k > 0) {
				val++;
			}

			units++;

			long curr = ((2l * units) % mod + mod) % mod;
			curr = ((curr + val - 1) % mod + mod) % mod;
//			curr = ((curr * val) % mod + mod) % mod;

			curr = (((curr * val) / 2) % mod + mod) % mod;

//			long sum = curr * inv2 % mod;
//			cost = ((cost + sum) % mod + mod) % mod;

			cost = ((cost + curr) % mod + mod) % mod;

			units = ((units + val - 1) % mod + mod) % mod;

			long product = ((k * val) % mod + mod) % mod;

			kRun = ((kRun + product) % mod + mod) % mod;

			kRun = ((kRun - temp) % mod + mod) % mod;
		}
		return (int) (cost + mod) % mod;
	}
}
