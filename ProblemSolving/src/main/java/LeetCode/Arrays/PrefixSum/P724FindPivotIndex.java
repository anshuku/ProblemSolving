package LeetCode.Arrays.PrefixSum;

import java.util.Arrays;

/*
 * P724. Find Pivot Index - Easy
 * 
 * Given an array of integers nums, calculate the pivot index of this array.
 * The pivot index is the index where the sum of all the numbers strictly to the left 
 * of the index is equal to the sum of all the numbers strictly to the index's right.
 * If the index is on the left edge of the array, then the left sum is 0 because 
 * there are no elements to the left. This also applies to the right edge of the array.
 * Return the leftmost pivot index. If no such index exists, return -1.
 * 
 * Approach - Prefix sum
 */
public class P724FindPivotIndex {

	public static void main(String[] args) {
		int[] nums = { 1, 7, 3, 6, 5, 6 };

//		int[] nums = { 2, 1, -1 };

		int pivotIndex = pivotIndex(nums);

		System.out.println("The pivot index is " + pivotIndex);

		int pivotIndexArr = pivotIndexArr(nums);

		System.out.println("Array: The pivot index is " + pivotIndexArr);

	}

	// Time Complexity - O(n)
	// Space Complexity - O(1)
	private static int pivotIndex(int[] nums) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		int lsum = 0;
		for (int i = 0; i < nums.length; i++) {
			if (lsum == sum - lsum - nums[i]) {
				return i;
			}
			lsum += nums[i];
		}
		return -1;
	}

	// Time Complexity - O(n)
	// Space Complexity - O(n)
	public static int pivotIndexArr(int[] nums) {
		int pivot = -1;
		int n = nums.length;

		int[] leftArr = new int[n];
		leftArr[0] = nums[0];
		for (int i = 1; i < n; i++) {
			leftArr[i] = leftArr[i - 1] + nums[i];
		}
//		System.out.println(Arrays.toString(leftArr));

		int[] rightArr = new int[n];
		rightArr[n - 1] = nums[n - 1];
		for (int i = n - 2; i >= 0; i--) {
			rightArr[i] = rightArr[i + 1] + nums[i];
		}
//		System.out.println(Arrays.toString(rightArr));

		for (int i = 0; i < n; i++) {
			if (leftArr[i] == rightArr[i]) {
				return i;
			}
		}

		return pivot;
	}

}
