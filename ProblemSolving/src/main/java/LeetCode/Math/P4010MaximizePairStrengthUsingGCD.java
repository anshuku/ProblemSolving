package LeetCode.Math;

/*
 * P4010. Maximize Pair Strength Using GCD - Easy
 * 
 * You are given an integer array nums.
 * 
 * Choose exactly one pair of distinct indices i and j. The strength of 
 * the pair is defined as (nums[i] * nums[j]) / gcd(nums[i], nums[j])^2.
 * 
 * Return the maximum strength over all possible pairs.
 * 
 * Approach - Math: GCD - Subtraction-Recursion, Modulo-Recursion, Modulo-While
 * 
 * GCD and HCF are same.
 * GCD is calculated via Euclidean Algorithm
 * 
 * Formula for Space complexity: Input Space + Output space + Auxiliary Space
 * Clarify if Space complexity involves Input Space!
 */
public class P4010MaximizePairStrengthUsingGCD {

	public static void main(String[] args) {
		int[] nums = { 2, 3, 5 }; // 15
//		int[] nums = { 4, 6, 8 }; // 12
//		int[] nums = { 3, 3 }; // 1

		long maxStrength = maxPairStrength(nums);
		System.out.println("The max strength over all pairs is: " + maxStrength);
	}

	// GCD calculation: Euclidean Algorithm
	public static long maxPairStrength(int[] nums) {
		int n = nums.length;

		long maxStrength = Integer.MIN_VALUE;

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int a = nums[i]; // Can take long a and b, but slower than using 1L * a * b
				int b = nums[j];

				long gcd = getGCDModuloWhile(a, b);

//				long gcd = getGCDModuloRecursion(a, b);

//				long gcd = getGCDSubtractionRecursion(a, b);

				long strength = 1L * a * b / (gcd * gcd);

				maxStrength = Math.max(maxStrength, strength);
			}
		}
		return maxStrength;
	}

	// Time complexity - O(n*n*logn)
	// Space complexity - O(1)
	private static long getGCDModuloWhile(int a, int b) {
		while (b != 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

	// Time complexity - O(n*n*log(max(nums))), computing GCD using the Euclidean
	// Algorithm takes O(log(max(nums)))
	// Space complexity - O(log(max(nums[i])))
	// Formula is: Input Space + Output space + Auxiliary Space
	private static long getGCDModuloRecursion(int a, int b) {
		if (b == 0) {
			return a;
		}

		// Not needed
//		if (a < b) {
//			return getGCDModuloRecursion(b, a);
//		}

		return getGCDModuloRecursion(b, a % b);
	}

	// TLE
	private static long getGCDSubtractionRecursion(int a, int b) {
		if (a == b) {
			return a;
		}

		// Not needed
//		if (a < b) {
//			return getGCDSubtractionRecursion(b, a);
//		}

		return getGCDSubtractionRecursion(b, a - b);
	}

}
