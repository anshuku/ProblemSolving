package LeetCode.BinarySearch;

import java.util.Arrays;

/*
 * P34. Find First and Last Position of Element in Sorted Array - Medium
 * 
 * Given an array of integers nums sorted in non-decreasing order, 
 * find the starting and ending position of a given target value.
 * 
 * If target is not found in the array, return [-1, -1].
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * 
 * Approach - Binary Search lower and upper bound
 */
public class P34FindFirstLastPositionElementSortedArray {

	public static void main(String[] args) {
		int[] nums = { 5, 7, 7, 8, 8, 10 };
		int target = 8;
//		int target = 6;

//		int[] nums = {};
//		int target = 0;

//		int[] nums = {1};
//		int target = 1;

		int[] range = searchRange(nums, target);
		System.out.println("The start and ending position of the given target value: " + Arrays.toString(range));

		int[] range2 = searchRange2(nums, target);
		System.out.println("2: The start and ending position of the given target value: " + Arrays.toString(range2));
	}

	public static int[] searchRange(int[] nums, int target) {
		int n = nums.length;
		int start = 0;
		int end = n - 1;
		int lowerBound = 0;
		int upperBound = 0;

		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] <= target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		if (start != 0 && nums[start - 1] == target) {
			upperBound = start - 1;
		} else {
			return new int[] { -1, -1 };
		}

		start = 0;
		end = nums.length - 1;

		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		lowerBound = start;
		// below is not needed as we already have a condition in upper bound
		// reverse is also true when lower bound is found first and then upper bound
		// we don't need below(similar for upper bound) condition
//		if (start != n && nums[start] == target) {
//			lowerBound = start;
//		} else {
//			return new int[] { -1, -1 };
//		}

		return new int[] { lowerBound, upperBound };
	}

	private static int[] searchRange2(int[] nums, int target) {
		int lowerBound = getBounds(nums, target, true);
		if (lowerBound == -1) {
			return new int[] { -1, -1 };
		}
		int upperBound = getBounds(nums, target, false);
		return new int[] { lowerBound, upperBound };
	}

	private static int getBounds(int[] nums, int target, boolean isFirst) {
		int start = 0;
		int end = nums.length - 1;

		while (start <= end) {
			int mid = start + (end - start) / 2;

			if (nums[mid] == target) {
				if (isFirst) {
					if (mid == start || nums[mid - 1] != target) {
						return mid;
					} else {
						end = mid - 1;
					}
				} else {
					if (mid == end || nums[mid + 1] != target) {
						return mid;
					} else {
						start = mid + 1;
					}
				}
			} else if (nums[mid] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return -1;
	}
}
