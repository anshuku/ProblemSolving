package LeetCode.MonotonicStack;

import java.util.Stack;

/*
 * P2104. Sum of Subarray Ranges - Medium
 * 
 * You are given an integer array nums. The range of a subarray of nums is 
 * the difference between the largest and smallest element in the subarray.
 * 
 * Return the sum of all subarray ranges of nums.
 * 
 * A subarray is a contiguous non-empty sequence of elements within an array.
 * 
 * Constraints:
 * > 1 <= nums.length <= 1000
 * > -10^9 <= nums[i] <= 10^9
 * 
 * Approach - Monotonic Stack, DP
 */
public class P2104SumOfSubarrayRanges {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 3 };
//		int[] nums = { 1, 3, 3 };
		int[] nums = { 4, -2, -3, 4, 1 };

		long sumRangeMStackDP = subArrayRangesMStackDP(nums);
		System.out.println("Monotonic Stack DP: The sum of all the subarray range is: " + sumRangeMStackDP);

		long sumRangeMStack = subArrayRangesMStack(nums);
		System.out.println("Monotonic Stack: The sum of all the subarray range is: " + sumRangeMStack);

		long sumRange2Loops = subArrayRanges2Loops(nums);
		System.out.println("2 Loops: The sum of all the subarray range is: " + sumRange2Loops);

	}

	private static long subArrayRangesMStackDP(int[] nums) {
		int n = nums.length;

		long[] dp = new long[n];
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < n; i++) {
			while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
				stack.pop();
			}

			if (stack.isEmpty()) {
				dp[i] = 1L * (i + 1) * nums[i]; // 1L is needed as 1000*10^9 > int capacity
			} else {
				int prev = stack.peek();
				dp[i] = dp[prev] + 1L * (i - prev) * nums[i]; // 1L is needed as 1000*10^9 > int capacity
			}
			stack.push(i);
		}

		long minSum = 0;
		for (long count : dp) {
			minSum += count;
		}

		dp = new long[n];
		stack.clear();

		for (int i = 0; i < n; i++) {
			while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
				stack.pop();
			}

			if (stack.isEmpty()) {
				dp[i] = 1L * (i + 1) * nums[i];
			} else {
				int prev = stack.peek();
				dp[i] = dp[prev] + 1L * (i - prev) * nums[i];
			}
			stack.push(i);
		}

		long maxSum = 0;
		for (long count : dp) {
			maxSum += count;
		}

		return maxSum - minSum;
	}

	// Monotonic stack
	// We find the minimum and maximum value range for a each element and multiply
	// it with the element's value for each element. We subtract minSum from maxSum
	// to get rangeSum. We use stack containing indices of element to find the range
	// where an element is max/min. We get this range when we pop and element off
	// the stack as the right and left indices are clear at that time. The left
	// index is the element below the popped element (left) and is the new top,
	// while the right index is the element's index which causes the element at the
	// top to be popped. Also, note that the range is not calculated when we add the
	// index of current element to the stack, but when we pop the element at the top
	// of the stack because only then are the left and right indices are clear.
	// Edge cases:
	// > If the stack is empty after we pop stackTop, we use left index as -1, it
	// means all the numbers on stackTop's left are within range [left, tops' index]
	// > In order to pop the remaining elements from the stack after the iteration
	// over nums stops, we set the right boundaries of all the remaining elements
	// (no smaller element on right) as n. This means that all the numbers on
	// nums[i]'s right are within the range [i, right]. For this we iterate from i =
	// 0 -> n (inclusive) to pop all the remaining elements from the stack.
	// Now, the algo also handles identical values that are close or adjacent, and
	// prevents double counting of any subarray. This is due to the fact that if
	// there are indentical elements say A then the subarrays using the 1st A will
	// never cross the second A, thus we don't double count any subarray.
	// We find maxSum similarly, as we reverse the comparision condition while
	// popping elements.
	// Time complexity - O(n)
	// Space complexity - O(n)
	public static long subArrayRangesMStack(int[] nums) {
		int n = nums.length;

		Stack<Integer> stack = new Stack<>();
		long minSum = 0;

		for (int i = 0; i <= n; i++) {
			while (!stack.isEmpty() && (i == n || nums[stack.peek()] >= nums[i])) {
				int mid = stack.pop();
				int right = i;
				int left = stack.isEmpty() ? -1 : stack.peek();

				long count = (right - mid) * (mid - left);
				minSum += count * nums[mid];
			}
			stack.push(i);
		}

		stack.clear();

		long maxSum = 0;

		for (int i = 0; i <= n; i++) {
			while (!stack.isEmpty() && (i == n || nums[stack.peek()] <= nums[i])) {
				int mid = stack.pop();
				int right = i;
				int left = stack.isEmpty() ? -1 : stack.peek();

				long count = (right - mid) * (mid - left);
				maxSum += count * nums[mid];
			}
			stack.push(i);
		}

		return maxSum - minSum;
	}

	// Two loops - fast due to cache line prefetch
	// We use i and j to mark the left and right indices of subarrays.
	// For a fixed left index, 2 adjacent arrays only differ by 1 element. If the
	// previous array is [i, j] and new array is [i, j+1], we can get the minVal,
	// maxVal of the new subarray by updating minVal, maxVal of previous array using
	// nums[j+1]. maxVal = max(maxVal, nums[j+1]), minVal = min(minVal, nums[j+1]).
	// Hence, the average time for finding range of 1 subarray is reduced to O(1).
	// Time complexity - O(n^2), we've 2 nested iterations over nums.
	// Space complexity - O(1)
	private static long subArrayRanges2Loops(int[] nums) {
		int n = nums.length;
		long sumRange = 0;

		for (int i = 0; i < n; i++) {
			int maxValue = nums[i];
			int minValue = nums[i];
			for (int j = i; j < n; j++) {
				maxValue = Math.max(maxValue, nums[j]);
				minValue = Math.min(minValue, nums[j]);
				sumRange += (maxValue - minValue);
			}
		}

		return sumRange;
	}
}
