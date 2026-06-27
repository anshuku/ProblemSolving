package LeetCode.Arrays;

/*
 * P3969. Valid Subarrays With Matching Sum Digits I - Medium
 * 
 * You are given an integer array nums and an integer digit x.
 * 
 * A subarray nums[l..r] is considered valid if the sum of its elements satisfies both of the following conditions:
 * 
 * > The first digit of the sum is equal to x.
 * > The last digit of the sum is equal to x.
 * 
 * Return the number of valid subarrays.
 * 
 * Approach - Running Sum
 */
public class P3969ValidSubarraysWithMatchingSumDigitsI {

	public static void main(String[] args) {
		int[] nums = { 1, 100, 1 };
		int x = 1;

//		int[] nums = { 1 };
//		int x = 2;

//		int[] nums = { 1000000000, 1, 1000000000, 1, 1000000000, 1, 1000000000 };
//		int x = 3;

//		int[] nums = { 5, 61 };
//		int x = 6;

		int validCountBFOpt = countValidSubarraysBFOpt(nums, x);
		System.out.println("Brute Force Opt: The number of valid subarrays: " + validCountBFOpt);

		int validCountBruteForce = countValidSubarraysBruteForce(nums, x);
		System.out.println("Brute Force: The number of valid subarrays: " + validCountBruteForce);
	}

	// Running sum
	// Here, n <= 1500, so we can check every possible subarray, as there are total
	// O(n^2) subarrays. For each subarray we maintain its running sum and check
	// whether the first digit and last digit of the sum are both equal to x.
	// Running sum is stored as long to prevent integer overflow when the subarray
	// sum exceeds the range of an int.
	// Time complexity - O(n^2*log10(M)), where n is the total number of integers in
	// nums, M is the maximum possible subarray sum. There are O(n^2) subarrays, and
	// extracting the first digit takes O(log10(M)).
	// Space complexity - O(1)
	private static int countValidSubarraysBFOpt(int[] nums, int x) {
		int n = nums.length;
		int count = 0;

		for (int i = 0; i < n; i++) {
			long sum = 0;
			for (int j = i; j < n; j++) {
				sum += nums[j];

				int last = (int) (sum % 10);

				long first = sum;

				while (first > 9) {
					first /= 10;
				}

				if (x == first && x == last) {
					count++;
				}
			}
		}
		return count;
	}

	public static int countValidSubarraysBruteForce(int[] nums, int x) {
		int n = nums.length;
		int count = 0;

		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				long sum = 0;
				for (int k = i; k <= j; k++) {
					sum += nums[k];
				}

				int last = (int) (sum % 10);

				while (sum > 9) {
					sum /= 10;
				}

				int first = (int) sum;

				if (x == first && x == last) {
					count++;
				}
			}
		}
		return count;
	}
}
