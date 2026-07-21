package LeetCode.Strings;

/*
 * P3985. Palindromic Subarray Sum - Hard
 * 
 * You are given an integer array nums.
 * 
 * Return the maximum possible sum of a subarray of nums that is a palindrome.
 * 
 * Constraints:
 * > 1 <= nums.length <= 10^5
 * > 1 <= nums[i] <= 10​​​​​​​^9
 * 
 * Approach - Manacher + Prefix Sum, Prefix Sum + Binary Search + Hashing
 */
public class P3985PalindromicSubarraySum {

	public static void main(String[] args) {
		int[] nums = { 10, 10 };

		long maxPalindromeSumExpandCenters = getSumExpandCenters(nums);
		System.out.println(
				"Expand Centers: The maximum sum of subarray that is a palindrome: " + maxPalindromeSumExpandCenters);
	}

	// Expanding the Centers - TLE
	// As per constraints the time taken is >= 10^5*10^5 or 10^10 which is >>
	// allowed 10^8.
	public static long getSumExpandCenters(int[] nums) {
		int n = nums.length;

		long maxSum = 0;

		for (int i = 0; i < n; i++) {
			long maxOddSum = getPalindromeSum(i, i, nums);
			long maxEvenSum = getPalindromeSum(i, i + 1, nums);

			maxSum = Math.max(maxSum, Math.max(maxOddSum, maxEvenSum));
		}

		return maxSum;
	}

	private static long getPalindromeSum(int i, int j, int[] nums) {
		int n = nums.length;

		if (j < n && nums[i] != nums[j]) {
			return 0;
		}

		long sum = 0;

		while (i >= 0 && j < n && nums[i] == nums[j]) {
			i--;
			j++;
		}

		for (int a = i + 1; a <= j - 1; a++) {
			sum += nums[a];
		}

		return sum;
	}

}
