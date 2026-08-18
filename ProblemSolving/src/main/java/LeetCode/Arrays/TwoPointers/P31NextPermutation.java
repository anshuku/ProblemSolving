package LeetCode.Arrays.TwoPointers;

import java.util.Arrays;

/*
 * P31. Next Permutation - Medium
 * 
 * A permutation of an array of integers is an arrangement of its members into a sequence or linear order.
 * 
 * * For example, for arr = [1,2,3], the following are all the permutations of arr: 
 * [1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1], [3,1,2], [3,2,1].
 * 
 * The next permutation of an array of integers is the next lexicographically greater permutation 
 * of its integer. More formally, if all the permutations of the array are sorted in one container 
 * according to their lexicographical order, then the next permutation of that array is the 
 * permutation that follows it in the sorted container. If such arrangement is not possible, 
 * the array must be rearranged as the lowest possible order (i.e., sorted in ascending order).
 * 
 * * For example, the next permutation of arr = [1,2,3] is [1,3,2].
 * * Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
 * * While the next permutation of arr = [3,2,1] is [1,2,3] because 
 * [3,2,1] does not have a lexicographical larger rearrangement.
 * 
 * Given an array of integers nums, find the next permutation of nums.
 * 
 * The replacement must be in place and use only constant extra memory.
 * 
 * Approach - D.E. Knuth Algorithm for next Permutation
 * This is also called Knuth's Algorithm L or Narayana Pandita's algorithm. 
 */
public class P31NextPermutation {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 3 };

//		int[] nums = { 1, 2, 3, 2, 1 };

//		int[] nums = { 2, 3, 1, 3, 3 };

//		int[] nums = { 3, 2, 1 };

		int[] nums = { 1, 5, 8, 4, 7, 6, 5, 3, 1 };

		int[] nums2 = Arrays.copyOf(nums, nums.length);

		nextPermutationLinearSearch(nums);
		System.out.println("Linear Search: The next permutation is: " + Arrays.toString(nums));

		nextPermutationBinarySearch(nums2);
		System.out.println("Binary Search: The next permutation is: " + Arrays.toString(nums2));
	}

	// D.E. Knuth Algorithm for next Permutation: Single pass approach
	// Via Brute force, we find out every possible permutation formed by the
	// elements of the given array and find out the permutation which is just larger
	// than the given one. But it'd take long time (Time = O(n!), space = O(n) and
	// the implementation is complex.
	// Algorithm:
	// First, we observe that for any given sequence is descending order, no next
	// larger permutation is possible. Example: [9,5,4,3,1].
	// We need to find the 1st pair of 2 successive numbers a[i] and a[i-1], from
	// the right, which satisfy a[i] > a[i-1]. Now, no rearrangements to the right
	// of a[i-1] can create a larger permutation since that subarray consists of
	// numbers in descending order. Thus, we need to rearrange the numbers to the
	// right of a[i-1] including itself.
	// What kind of rearrangement will produce the next larger number?
	// We want to create the permutation just larger than the current one.
	// Therefore, we need to replace the number a[i-1] with the number which is just
	// larger than itself among the numbers lying to its right section, say a[j].
	// We swap the numbers a[i-1] and a[j]. We now have the correct number at index
	// i - 1. But still the current permutation isn't the permutation that we're
	// looking for. We need the smallest permutation that can be formed by using the
	// numbers only to the right of a[i-1]. Therefore, we need to place those
	// numbers in ascending order to get their smallest permutation.
	// Now, while scanning the numbers from the right, we simply kept decrementing
	// the index until we found the pair a[i] and a[i-1] where, a[i] > a[i-1]. Thus,
	// all numbers to the right of a[i-1] were already sorted in descending order.
	// Also, swapping a[i-1] and a[j] didn't change that order. Therefore, we simple
	// need to reverse the numbers following a[i-1] to get the next smallest
	// lexicographic permutation.
	// Time complexity - O(n), where n is the size of nums array. The 1st while loop
	// runs at most n iterations, decrementing the variable i as it searches for the
	// 1st decreasing element from the right. In worst case, we need to check all
	// elements in O(n) time. The 2nd while loop also runs at most n times,
	// decrementing the variable j as it searches for the smallest element larger
	// than nums[i] taking O(n) time. The reverse function is called on a portion of
	// the array, from index i + 1 to the end. In worst case, it can cover the
	// entire array, taking O(n) time. In swap function, we only exchange 2 elements
	// in constant time, O(1).
	// Space complexity - O(1) as no extra space is used. The function operates
	// in-place on the nums array, as no extra space is used for storing additional
	// data apart from constant space variables.
	public static void nextPermutationLinearSearch(int[] nums) {
		int n = nums.length;

		int i = n - 2;

		// We find the pivot
		while (i >= 0 && nums[i] >= nums[i + 1]) {
			i--;
		}

		// If no such index exists, the array is already sorted in descending order
		// If a valid pivot is found
		if (i >= 0) {

			// Successor index for replacement
			int j = n - 1;

			// Find the successor which is the 1st element larger than pivot element in
			// rightward direction of i.
			while (nums[j] <= nums[i]) {
				j--;
			}

			// Exchange pivot element or nums[i] and successor or nums[j].
			swap(nums, i, j);
		}

		// Reverse all elements to the right of pivot index i (i+1) which minimizes the
		// remaining suffix, giving the smallest possible increment. Reverse the suffix.
		reverse(nums, i + 1);
	}

	private static void reverse(int[] nums, int start) {
		int end = nums.length - 1;

		while (start < end) {
			swap(nums, start, end);
			start++;
			end--;
		}
	}

	private static void swap(int[] nums, int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}

	private static void nextPermutationBinarySearch(int[] nums) {
		int n = nums.length;

		int i = n - 2;

		while (i >= 0 && nums[i + 1] <= nums[i]) {
			i--;
		}

		if (i >= 0) {
			// Lower bound binary search in decreasing nums
			int start = i + 1;
			int end = n - 1;

			int key = nums[i];

			while (start <= end) {
				int mid = start + (end - start) / 2;

				if (nums[mid] <= key) {
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			}

			swap(nums, i, end);
		}

		reverse(nums, i + 1);
	}

}
