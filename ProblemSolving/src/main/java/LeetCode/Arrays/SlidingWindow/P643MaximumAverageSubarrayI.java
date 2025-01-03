package LeetCode.Arrays.SlidingWindow;

/*
 * P 643 - Maximum Average Subarray I - easy
 * 
 * You are given an integer array nums consisting of n elements, and an integer k.
 * Find a contiguous subarray whose length is equal to k that has the maximum average value 
 * and return this value. Any answer with a calculation error less than 10-5 will be accepted.
 * 
 * Approach - Rolling sum
 */
public class P643MaximumAverageSubarrayI {

	public static void main(String[] args) {
		int[] nums = { 1, 12, -5, -6, 50, 3 };
		int k = 4;

		double maxAverageRolling = findMaxAverageRolling(nums, k);

		System.out.println("Rolling Sum: The max average for k element is " + maxAverageRolling);

		double maxAverageBF = findMaxAverageBF(nums, k);

		System.out.println("Brute Force: The max average for k element is " + maxAverageBF);
	}

	private static double findMaxAverageRolling(int[] nums, int k) {
		double maxTillNow = 0;
		double maxAverage = Integer.MIN_VALUE;
		for (int i = 0; i < k; i++) {
			maxTillNow += nums[i];
		}
		maxAverage = maxTillNow;
		for (int i = k; i < nums.length; i++) {
			maxTillNow += nums[i];
			maxTillNow -= nums[i - k];
			maxAverage = Math.max(maxAverage, maxTillNow);
		}
		return maxAverage / k;
	}

	public static double findMaxAverageBF(int[] nums, int k) {

		double maxForWindow = 0;
		double maxAverage = Integer.MIN_VALUE;

		for (int i = 0; i < nums.length - k + 1; i++) {
			for (int j = i; j < i + k; j++) {
				maxForWindow += nums[j];
			}
			maxAverage = Math.max(maxAverage, maxForWindow / k);
			maxForWindow = 0;
		}

		return maxAverage;
	}

}
