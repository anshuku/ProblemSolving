package LeetCode.Arrays;

/*
 * P3979. Maximum Valid Pair Sum - Medium
 * 
 * You are given an integer array nums of length n and an integer k.
 * 
 * A pair of indices (i, j) is called valid if:
 * > 0 <= i < j < n
 * > j - i >= k
 * 
 * Return the maximum value of nums[i] + nums[j] among all valid pairs.
 * 
 * Constraints:
 * > 2 <= n == nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 1 <= k <= n - 1
 * 
 * Approach - Running sum, Suffix array
 */
public class P3979MaximumValidPairSum {

	public static void main(String[] args) {
//		int[] nums = { 1, 3, 5, 2, 8 };
//		int k = 2;

//		int[] nums = { 5, 1, 9 };
//		int k = 1;

//		int[] nums = { 28, 95, 34, 25 };
//		int k = 2;

//		int[] nums = { 12, 92, 25 };
//		int k = 1;

		int[] nums = { 24, 2, 68, 86 };
		int k = 1;

		int maxValidPairSumOnePassLeft = maxValidPairSumOnePassLeft(nums, k);
		System.out.println("One Pass Left: The max valid pair sum is: " + maxValidPairSumOnePassLeft);

		int maxValidPairSumTwoPassRight = maxValidPairSumTwoPassRight(nums, k);
		System.out.println("Two Pass Right: The max valid pair sum is: " + maxValidPairSumTwoPassRight);
	}

	// Running maximum - One pass
	// For every valid j, we need, j - i >= k or i <= j - k and we need to maximize
	// nums[i] + nums[j]. As we iterate j from k to n - 1, keep track of the max
	// value among all eligible nums[i].
	// Algo:
	// Initialize maxLeft = 0/ -INF/nums[0], iterate j from k to n - 1. During this:
	// > Before processsing j, include the new eligible index j - k (i):
	// maxLeft = max(maxLeft + nums[j-k])
	// > Update the answer, ans = Math.max(ans, maxLeft + nums[j])
	// Correctness: When processing j, all indices 0...j-k for i satisfy j - i >= k.
	// maxLeft stores the max num[i] among these indices, so maxLeft + num[j] is the
	// best pair ending at j. Taking the max over all j gives the answer.
	// Time complexity - O(n)
	// Space complexity - O(1)
	public static int maxValidPairSumOnePassLeft(int[] nums, int k) {
		int n = nums.length;

		int leftMax = Integer.MIN_VALUE;
		int ans = Integer.MIN_VALUE;

		for (int j = k; j < n; j++) {
			leftMax = Math.max(leftMax, nums[j - k]);// max for i present in left.
			ans = Math.max(ans, nums[j] + leftMax); // nums[j] + max i in left.
		}
		return ans;
	}

	// Running max array - Two pass
	// A pair (i, j) is valid if: j - i >= k. This means once we fix the left index
	// i, the right index can be any position from i + k to n - 1. Instead of
	// checking every possible j, we only need the max value in this suffix.
	// So the idea is: > For every index, know the max value to its right. > Then
	// for every valid left index, combine it with the best possible right value.
	// Approach - We build the right suffix max array first: maxRight[i] = max value
	// from index i to n - 1. Now iterate through every possible left index. For
	// index i, the right index must satisfy: j >= i + k. The best possible choice
	// is simply: maxRight[i + k]. The ans for this i is nums[i] + maxRight[i + k].
	// We take the max over all valid i.
	// Time complexity - O(n), we first build suffix array and then compute the
	// answer.
	// Space complexity - O(n), for suffix max array.
	private static int maxValidPairSumTwoPassRight(int[] nums, int k) {
		int n = nums.length;

		int[] maxRight = new int[n + 1];
		int ans = 0;

		for (int i = n - 1; i >= 0; i--) {
			maxRight[i] = Math.max(maxRight[i + 1], nums[i]); // Max value from i to end
		}

		for (int i = 0; i < n - k; i++) {
			ans = Math.max(ans, nums[i] + maxRight[i + k]); // Best partner for index i
		}
		return ans;
	}
}
