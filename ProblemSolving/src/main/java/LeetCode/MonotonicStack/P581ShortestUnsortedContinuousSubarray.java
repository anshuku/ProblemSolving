package LeetCode.MonotonicStack;

import java.util.Arrays;
import java.util.Stack;

/*
 * P581. Shortest Unsorted Continuous Subarray - Medium
 * 
 * Given an integer array nums, you need to find one continuous subarray such that if you only sort 
 * this subarray in non-decreasing order, then the whole array will be sorted in non-decreasing order.
 * 
 * Return the shortest such subarray and output its length.
 * 
 * Approach - Monotonic stack, Min Max Track
 */
public class P581ShortestUnsortedContinuousSubarray {

	public static void main(String[] args) {
		int[] nums = { 2, 6, 4, 8, 10, 9, 15 };
//		int[] nums = { 1, 3, 2, 2, 2 };

		int shortestSubarrayMinMax = findUnsortedSubarrayMinMax(nums);
		System.out.println("Min Max: The shortest subarray length which makes whole array in non-decreasing order: "
				+ shortestSubarrayMinMax);

		int shortestSubarrayStack = findUnsortedSubarrayStack(nums);
		System.out.println("Stack: The shortest subarray length which makes whole array in non-decreasing order: "
				+ shortestSubarrayStack);

		int shortestSubarraySorting = findUnsortedSubarraySorting(nums);
		System.out.println("Sorting: The shortest subarray length which makes whole array in non-decreasing order: "
				+ shortestSubarraySorting);

		int shortestSubarraySelection = findUnsortedSubarraySelection(nums);
		System.out.println("Selection: The shortest subarray length which makes whole array in non-decreasing order: "
				+ shortestSubarraySelection);

		int shortestSubarrayBruteForce = findUnsortedSubarrayBruteForce(nums);
		System.out.println("Brute Force: The shortest subarray length which makes whole array in non-decreasing order: "
				+ shortestSubarrayBruteForce);
	}

	// Min Max tracking
	// The idea behind this is that the correct position of the minimum element in
	// the unsorted subarray helps to find the required left boundary. Similarly,
	// the correct position of the max element in the unsorted subarray helps to
	// find the required right boundary.
	// Firstly, we find where the sorted array foes wrong. We track this by check
	// rising slope starting from the beginning of the array. Whenever the slope
	// falls, we know the unsorted array has started. From there, we find the min
	// element till the end of the array given by min.
	// Similary, we scan nums in the reverse order and this time the slope should be
	// falling. And when slope starts rising, we look for the max element till we
	// reach the start of the array, given by max.
	// Then, we iterate the nums to find the correct position of min and max
	// elements via comparision with the elements. For min, we know the initial
	// position of nums maybe / is already sorted. We need to find the 1st element
	// which is > min. Similarly, for max we find the 1st element which is < max by
	// searching the nums backwards.
	// Time complexity - O(n), for O(n) loops are used.
	// Space complexity - O(1).
	private static int findUnsortedSubarrayMinMax(int[] nums) {
		int n = nums.length;
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		boolean flag = false;

		for (int i = 1; i < n; i++) {
			if (!flag && nums[i] < nums[i - 1]) {
				flag = true;
			}
			if (flag) {
				min = Math.min(min, nums[i]);
			}
		}

		if (min == Integer.MAX_VALUE) {
			return 0;
		}
		flag = false;
		for (int i = n - 2; i >= 0; i--) {
			if (!flag && nums[i] > nums[i + 1]) {
				flag = true;
			}
			if (flag) {
				max = Math.max(max, nums[i]);
			}
		}

		int l, r;
		for (l = 0; l < n; l++) {
			if (nums[l] > min) {
				break;
			}
		}

		for (r = n - 1; r >= 0; r--) {
			if (nums[r] < max) {
				break;
			}
		}
		return r - l + 1;
	}

	// Stack
	// The idea behind this approach is based on selective sorting. We need to
	// determine the correct position of minimum and maximum element in the unsorted
	// subarray to determine the boundaries of the required unsorted subarray.
	// We take help of a stack. We traverse over nums from the beginning. As we face
	// elements in the ascending order (a rising slope), we keep on pushing the
	// elements's indices over the stack. This is done as those elements are in
	// correct sorted order. As soon as we found a falling slope, i.e. an element
	// nums[i] which is smaller than the element on the top of the stack, we know
	// nums[i] isn't at its correct position. To determine the correct position of
	// nums[i] we keep on popping the elements from the top of the stack until we
	// reach the stage where the element (give by the index) on the top of stack <
	// nums[i]. If the pop stops when the index on stack's top if k. Now, nums[j]
	// has found its correct position. It needs to lie at an index k + 1. We follow
	// this for whole array and determine the value of minimum such k. This marks
	// the left boundary of the unsorted subarray.
	// Similarly, for the right boundary of the unsorted subarray, we traverse over
	// the nums array backwards. This time we keep on pushing the elements if we see
	// a falling slope. If we find a rising slope, we trace forwards (in stack) and
	// determine the larger element's correct position with help of popping the
	// stack's top until stack[top] > nums[i]. We do so for the complete array and
	// thus, determine the right boundary.
	// Time complexity - O(n), stack can push and pop at most n elements.
	// Space complexity - O(n), stack can push at most n elements.
	private static int findUnsortedSubarrayStack(int[] nums) {
		int n = nums.length;
		Stack<Integer> stack = new Stack<>();

		int min = n, max = 0;

		for (int i = 0; i < n; i++) {
			while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
				min = Math.min(min, stack.pop());
			}
			stack.push(i);
		}
		if (min == n) {
			return 0;
		}

		stack.clear();

		for (int i = n - 1; i >= 0; i--) {
			while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
				max = Math.max(max, stack.pop());
			}
			stack.push(i);
		}

		return max - min + 1;
	}

	// Sorting
	// We sort the copy of the given array nums, called sorted array. Then, if we
	// compare the elements of nums and sorted, we can determine the leftmost and
	// rightmost elements which mismatch. The subarray lying between them is, then
	// the required shortest unsorted subarray.
	// Time complexity - O(nlogn), we sort the array in O(nlogn) time and itering
	// over nums for comparision with sorted array takes O(n) time.
	// Space complexity - O(n), we make the copy of original array.
	public static int findUnsortedSubarraySorting(int[] nums) {
		int n = nums.length;
		int[] sorted = nums.clone();

		Arrays.sort(sorted);

		int min = n;
		int max = 0;

		for (int i = 0; i < n; i++) {
			if (nums[i] != sorted[i]) {
				min = Math.min(min, i);
				max = Math.max(max, i);
			}
		}

		if (min == n) {
			return 0;
		}
		return max - min + 1;
	}

	// Brute Force - Selection sort idea
	// In this approach, we use selection sort idea. We can traverse over the given
	// nums array choosing the elements nums[i]. For every such chosen element, we
	// try to determine its correct position in the sorted array. For this, we
	// compare nums[i] with every nums[j], such that i < j < n.
	// If nums[j] < nums[i], it means both nums[i] and nums[j] aren't at the correct
	// position for the sorted array. We need to swap them to bring them at their
	// correct positon. Here, instead of swapping we not the position of nums[i] (i)
	// and nums[j] (j). There 2 elements marks the boundary of the unsorted subarray
	// (for the time being). For all the nums[i] chosen, we determine the leftmost
	// nums[i] which isn't at its correct position. This is the left boundary of the
	// smallest unsorted subarray (l). Similarly, out of all the nums[j]'s
	// considered for all nums[i]'s we determine the rightmost nums[j] which isn't
	// at its correct position. This marks the right boundary of the smallest
	// unsorted subarray (r). We determine the length of the smallest unsorted
	// subarray as r - l + 1.
	// Time complexity - O(n^2), 2 nested loops are used.
	// Space complexity - O(1)
	private static int findUnsortedSubarraySelection(int[] nums) {
		int n = nums.length;

		int min = n, max = 0;

		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				if (nums[i] > nums[j]) {
					min = Math.min(min, i);
					max = Math.max(max, j);
				}
			}
		}

		if (min == n) {
			return 0;
		}

		return max - min + 1;
	}

	// Brute Force - for subarray in i -> j-1 (included)
	// We consider every possible subarray which can be formed from nums and for
	// every subarray nums[i:j] considered, we check whether it's the smallest
	// unsorted subarray or not. Thus, for every such subarray we find min and max
	// values in that subarray.
	// If the subarray nums[0:i-1] and nums [j: n-1] are correctly sorted, then only
	// nums[i:j] (j-1) could be the required subarray. Further, the elements in
	// nums[0: n-1] all need to be < min. Also, all the elements in nums[j: n-1]
	// need to be larger than max.
	// Further we also check if nums[0: i-1] and nums[j: n-1] is sorted correctly.
	// If all these conditions are met, the length of unsorted subarray is j - i.
	// We do this for all the subarray chosen, and determine the length of the
	// smallest unsorted subarray found.
	// Time complexity - O(n^3), for 3 nested loops
	// Space complexity - O(1)
	private static int findUnsortedSubarrayBruteForce(int[] nums) {
		int n = nums.length;
		int result = n;

		for (int i = 0; i < n; i++) {
			for (int j = i; j <= n; j++) {
				int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, prev = Integer.MIN_VALUE;

				for (int k = i; k < j; k++) {
					min = Math.min(min, nums[k]);
					max = Math.max(max, nums[k]);
				}

				if ((i > 0 && nums[i - 1] > min) || (j < n && nums[j] < max)) {
					continue;
				}

				int k = 0;
				while (k < i && prev <= nums[k]) {
					prev = nums[k];
					k++;
				}

				if (k != i) {
					continue;
				}

				k = j;

				while (k < n && prev <= nums[k]) {
					prev = nums[k];
					k++;
				}

				if (k == n) {
					result = Math.min(result, j - i);
				}
			}
		}
		return result;
	}
}
