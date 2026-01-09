package LeetCode.BinarySearch;

import java.util.Arrays;

/*
 * P1498. Number of Subsequences That Satisfy the Given Sum Condition - Medium
 * 
 * You are given an array of integers nums and an integer target.
 * 
 * Return the number of non-empty subsequences of nums such that the 
 * sum of the minimum and maximum element on it is less or equal to 
 * target. Since the answer may be too large, return it modulo 10^9 + 7.
 * 
 * Approach - Binary Search, 2 pointers
 */
public class P1498NumberSubsequencesSatisfyGivenSumCondition {

	static int mod = 1_000_000_007;

	public static void main(String[] args) {
//		int[] nums = { 3, 5, 6, 7 };
//		int target = 9;

//		int[] nums = { 3, 3, 6, 8 };
//		int target = 10;

//		int[] nums = { 2, 3, 3, 4, 6, 7 };
//		int target = 12;

		int[] nums = { 14, 4, 6, 6, 20, 8, 5, 6, 8, 12, 6, 10, 14, 9, 17, 16, 9, 7, 14, 11, 14, 15, 13, 11, 10, 18, 13,
				17, 17, 14, 17, 7, 9, 5, 10, 13, 8, 5, 18, 20, 7, 5, 5, 15, 19, 14 };
		int target = 22;

		int numSubsequences2P = numSubseq2Pointers(nums, target);
		System.out.println("2 Pointers: The number of required subsequences are- " + numSubsequences2P);

		int numSubsequencesBS = numSubseqBinarySearch(nums, target);
		System.out.println("Binary Search: The number of required subsequences are- " + numSubsequencesBS);

	}

	// 2 Pointers
	// In binary search, we traverse left from 0 to n - 1, we traverse left from 0
	// to n-1, so the value of target - nums[left] decreases, so insertion position
	// of target - nums[left] in array also decreases. Therefore, we don't need
	// binary search, but only need to set the pointer right for largest element <=
	// target - nums[left]. When we traverse left, the pointer right is
	// monotonically moving to the left and time complexity post-sort is only O(n)
	// instead of O(n*logn) in binary search(but overall time remains due to sort).
	// Precompute the value of 2 to the power of each value.
	// Time complexity - O(n*logn), we sort nums in O(n*logn) time and get power
	// array in O(n) time. We iterate nums with left, and each index is visited by
	// it once and visited by right at most once which takes O(n) time. This takes
	// O(logn) on average for array of size n.
	// Space complexity - O(n), for power array and sorting.
	private static int numSubseq2Pointers(int[] nums, int target) {
		int n = nums.length;
		int[] power = new int[n];
		power[0] = 1;
		for (int i = 1; i < n; i++) {
			power[i] = (2 * power[i - 1]) % mod;
		}
		Arrays.sort(nums);
		int result = 0;
		int left = 0;
		int right = n - 1;
		while (left <= right) {
			if (nums[left] + nums[right] <= target) {
				result += power[right - left];
				result %= mod;
				left++;
			} else {
				right--;
			}
		}
		return result;
	}

	// Binary Search
	// Brute force will generate all subsequences and will try to find the valid
	// subsequences. This will take large n*2^n time to generate and then scan the
	// non-empty subsequences. We only need to consider the min and max elements of
	// a subsequence to check validity, the elements in between is unaffecting. We
	// traverse all possible (min, max) combinations and check if they're valid. If
	// there are k subsequences with min and max, we only check min + max <= target.
	// The number of such subsequences k depends on number of elements between min
	// and max. Therefore, we sort nums first, so that the number of values between
	// min and max can be represented by their index difference. We take pointers in
	// min and max and then travese each possible min value. For a particular min =
	// nums[left], we need to ensure that the subsequences with this number as min
	// value are valid, which means the max element of these subsequences can't be >
	// target - nums[left]. In other words, we need to find the largest element not
	// greater than target - nums[left]. We can freely pick elements within the
	// range [left + 1, right] to make valid subsequences. We can use binary search
	// as array is sorted to get target - nums[left]. We look for rightmost element
	// that is <= target - nums[left] and binary search finds the index of the
	// smallest element that is > target-nums[left]. So, once we find k, right=k-1.
	// For each number in range [left + 1, right], there are 2 options, take or not.
	// There are total of 2^(right - left) valid subsequences with nums[left] as
	// min element. We then move to the next min by moving left pointer 1 step to
	// right and again perform binary search to find new insertion position of
	// target - nums[left]. When we get left > right, we don't need to consider
	// these subsequences as they've been already calculated.
	// To get number of subsequnces we initialize an array power and precompute the
	// value of 2 to the power of each value from 0,1,.. in O(n) time.
	// Time complexity - O(n*logn), we sort nums in O(n*logn) time and get power
	// array in O(n) time. We iterate nums, and for each index left, we binary
	// search to locate the required rightmost element. This takes O(logn) on
	// average for array of size n.
	// Space complexity - O(n), for power array and sorting.
	public static int numSubseqBinarySearch(int[] nums, int target) {
		int n = nums.length;
		int[] power = new int[n];
		power[0] = 1;
		// Precompute the value of 2 to the power of each value.
		for (int i = 1; i < n; i++) {
			power[i] = (2 * power[i - 1]) % mod;
		}

		Arrays.sort(nums);
		int numSubsequences = 0;
		for (int i = 0; i < n; i++) {
			int start = i;
			int end = n - 1;
			while (start <= end) {
				int mid = start + (end - start) / 2;
				long val = nums[i] + nums[mid];
				if (val <= target) {
					start = mid + 1;
				} else {
					end = mid - 1;
				}
			}
			if (start != i) {
				numSubsequences = (numSubsequences + power[start - i - 1]) % mod;
			}
		}
		return numSubsequences;
	}
}
