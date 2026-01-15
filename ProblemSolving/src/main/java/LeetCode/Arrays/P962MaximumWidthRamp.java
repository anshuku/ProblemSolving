package LeetCode.Arrays;

import java.util.Arrays;
import java.util.Stack;

/*
 * P962. Maximum Width Ramp - Medium
 * A ramp in an integer array nums is a pair (i, j) for which i < j and nums[i] <= nums[j]. 
 * The width of such a ramp is j - i.
 * Given an integer array nums, return the maximum width of a ramp in nums. 
 * If there is no ramp in nums, return 0.
 * 
 * Indices Sorting, 2 pointers, monotonic stack
 */
public class P962MaximumWidthRamp {

	public static void main(String[] args) {

//		int[] nums = { 6, 0, 8, 2, 1, 5 };

		int[] nums = { 9, 8, 1, 0, 1, 9, 4, 0, 4, 1 };

		int maxWidthRampStack = maxWidthRampStack(nums);

		System.out.println("The max width of ramp via Monotonic Stack is " + maxWidthRampStack);

		int maxWidthRamp2Ptr = maxWidthRamp2Ptr(nums);

		System.out.println("The max width of ramp via 2 Pointers is " + maxWidthRamp2Ptr);

		int maxWidthRampSort = maxWidthRampSort(nums); // [3, 7, 2, 4, 9, 6, 8, 1, 0, 5]

		System.out.println("The max width of ramp via indices sorting is " + maxWidthRampSort);

		int maxWidthRampBF = maxWidthRampBF(nums);

		System.out.println("The max width of ramp via brute force is " + maxWidthRampBF);

	}

	// Stack stores indices only if the value at current index at nums is
	// less than the value at top index of stack.
	// The stack contains indices which are decreasing from top to bottom.
	// While iterating from last index of nums, whenever a value at that
	// index is encountered which is greater than the value at stacks top element
	// Pop all elements one by one from stack which can form valid pairs
	// For each index popped, calculate the max ramp width alongwith.
	// Time complexity - O(n)
	// Space complexity - O(n), where n is for the stack space.
	private static int maxWidthRampStack(int[] nums) {
		int n = nums.length;
		// Stack to store the indices which are decreasing in value
		Stack<Integer> stack = new Stack<>();
		stack.push(0);
		for (int i = 1; i < n; i++) {
			if (nums[i] < nums[stack.peek()]) {
				stack.push(i);
			}
		}
		int width = 0;
		for (int i = n - 1; i >= 0; i--) {
			while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
				width = Math.max(width, i - stack.pop());
			}
		}

		return width;
	}

	// If the max value from current index towards right is known
	// then the ramp width can be obtained by taking difference.
	// Time complexity - O(n)
	// Space complexity - O(n), where n is for maxRight array.
	private static int maxWidthRamp2Ptr(int[] nums) {

		int n = nums.length;
		int maxRight[] = new int[n];
		maxRight[n - 1] = nums[n - 1];
		for (int i = n - 2; i >= 0; i--) {
			maxRight[i] = Math.max(nums[i], maxRight[i + 1]);
		}
		int left = 0, right = 0;
		int width = 0;
		while (right < n) {
//			if (nums[left] <= maxRight[right]) {
//				width = Math.max(width, right - left);
//			} else {
//				left++;
//			}
//			right++;

			while (left < right && nums[left] > maxRight[right]) {
				left++;
			}
			width = Math.max(width, right - left);
			right++;
		}
		return width;
	}

	// The indices of the nums array is sorted based on values.
	// The values at indices being processed >= values at previous indices.
	// Max difference between min index(starts with n, updated) and current index
	// Time complexity - O(nlogn)
	// Space complexity - O(n + S), where n is for indices array.
	// Additional space S is for Sorting algorithm and language dependent.
	// In Java, Arrays.sort uses a variant of MergeSort and has around O(logn)
	// space.
	private static int maxWidthRampSort(int[] nums) {
		int n = nums.length;
		Integer[] indices = new Integer[n];

		for (int i = 0; i < n; i++) {
			indices[i] = i;
		}

		// Custom comparator to ensure stability while sorting
		Arrays.sort(indices, (a, b) -> nums[a] != nums[b] ? nums[a] - nums[b] : a - b);

		int minIndex = n;
		int width = 0;

		for (int i : indices) {
			width = Math.max(width, i - minIndex);
			minIndex = Math.min(minIndex, i);
		}

		return width;
	}

	// Time complexity - O(n^2)
	// Space complexity - O(1)
	public static int maxWidthRampBF(int[] nums) {
		int width = 0;
		int n = nums.length;
		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				if (nums[i] <= nums[j]) {
					width = Math.max(width, j - i);
				}
			}
		}
		return width;
	}

}
