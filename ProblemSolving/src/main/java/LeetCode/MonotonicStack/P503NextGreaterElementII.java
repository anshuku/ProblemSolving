package LeetCode.MonotonicStack;

import java.util.Arrays;
import java.util.Stack;

/*
 * P503. Next Greater Element II - Medium
 * 
 * Given a circular integer array nums (i.e., the next element of nums[nums.length - 1] 
 * is nums[0]), return the next greater number for every element in nums.
 * 
 * The next greater number of a number x is the first greater number to its 
 * traversing-order next in the array, which means you could search circularly to 
 * find its next greater number. If it doesn't exist, return -1 for this number.
 * 
 * Approach - Monotonic Stack, Circular Array Iteration
 */
public class P503NextGreaterElementII {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 1 };
		int[] nums = { 1, 2, 3, 4, 3 };

		int[] nextGreaterMStack = nextGreaterElementsMStack(nums);
		System.out.println("Monotonic Stack: The next greater element is: " + Arrays.toString(nextGreaterMStack));

		int[] nextGreaterArrayTwice = nextGreaterElementsArrayTwice(nums);
		System.out.println("Array Twice: The next greater element is: " + Arrays.toString(nextGreaterArrayTwice));

		int[] nextGreaterArrayMod = nextGreaterElementsArrayMod(nums);
		System.out.println("Array Modulus: The next greater element is: " + Arrays.toString(nextGreaterArrayMod));

		int[] nextGreaterArrayModAlt = nextGreaterElementsArrayModAlt(nums);
		System.out
				.println("Array Modulus Alt: The next greater element is: " + Arrays.toString(nextGreaterArrayModAlt));
	}

	// Monotonic stack: Monontonically decreasing
	// We store the indices of the elements from nums array (as the element value
	// can be duplicate) in a stack. The top of the stack referes to the index of
	// the Next Greater Element found so far. We start traversing the nums array
	// from right towards left. For nums[i], we pop all the elements stack[top] from
	// the stack such that nums[stack[top]] <= nums[i]. We continue popping till we
	// either encounter a stack[top] such that nums[stack[top]] > nums[i] or stack
	// becomes empty. Now, is the stack isn't empty, the current stack[top], can
	// only act as the Next Greater Element for nums[i] (considering only elements
	// lying to the right of nums[i]). If no element remains on the top of the
	// stack, it means no larger element than nums[i] exists to its right so we put
	// -1 in result for this i. We push the index of the element just encountered
	// (nums[i[), i.e. i over the top of the stack, so that nums[i] or stack[top]
	// now acts as the next Greater Element for elements lying to its left.
	// We go through 2 such passes over the complete nums array. This is to complete
	// a circular traversal over the nums array. The first pass could make some
	// wrong entries in the result array since it considers only the element lying
	// to the right of nums[i], without a circular traversal. But, these entries are
	// corrected in 2nd pass. In the 1st pass there can be a number of wrong entries
	// (marked as -1) in the result array, because only the elements lying to the
	// corresponding right (non-circular) are considered till now. But after 2nd
	// pass, the correct values are substituted.
	// Time complexity - O(n), only 2 traversals of nums array are done. Further, at
	// most 2n elements are pushed and popped from the stack
	// Space complexity - O(n), for the stack.
	private static int[] nextGreaterElementsMStack(int[] nums) {
		int n = nums.length;
		int[] greater = new int[n];

		Stack<Integer> stack = new Stack<>();

		for (int i = 2 * n - 1; i >= 0; i--) {
			while (!stack.isEmpty() && nums[stack.peek()] <= nums[i % n]) {
				stack.pop();
			}
			greater[i % n] = stack.isEmpty() ? -1 : nums[stack.peek()];
			stack.push(i % n);
		}
		return greater;
	}

	// Brute Force: Double Length Array
	// We use an array doubleNums which is formed by concatenating 2 copies of the
	// nums array one after the other. To find out the next greater element for
	// nums[i], we can simply scan all the elements doubleNums[j], such that i < k <
	// length(doubleNums). The first element found satisfying the given condition is
	// the result for nums[i]. If no such element is found we put -1 in result.
	// Time compelxity - O(n^2), the complete doubleNums array of size 2n is scanned
	// for all the elements of nums in the worst case.
	// Space complexity - O(n) for doubleNums array.
	private static int[] nextGreaterElementsArrayTwice(int[] nums) {
		int n = nums.length;
		int[] doubleNums = Arrays.copyOf(nums, 2 * n);

		for (int i = 0; i < n; i++) {
			doubleNums[i + n] = nums[i];
		}

		int[] greater = new int[n];

		for (int i = 0; i < n; i++) {
			greater[i] = -1;
			for (int j = i + 1; j < 2 * n; j++) {
				if (doubleNums[j] > nums[i]) {
					greater[i] = doubleNums[j];
					break;
				}
			}
		}
		return greater;
	}

	// Brute Force: Optimized - Circular traversal via modulus
	// We traverse circularly in the nums array with help of modulus operator. For
	// every element nums[i], we start searching in the nums array (length n) from
	// the index (i+1) and look at the next (circularly) n - 1 elements. For
	// nums[i], we do so by scanning over nums[j] from i + 1 and we look for the 1st
	// greater element. If no such element is found, we put -1 in result array.
	// Time complexity - O(n^2), the complete nums array is scanned for all the
	// elements of nums in the worst case.
	// Space complexity - O(1)
	private static int[] nextGreaterElementsArrayModAlt(int[] nums) {
		int n = nums.length;
		int[] greater = new int[n];

		for (int i = 0; i < n; i++) {
			greater[i] = -1;

			for (int j = 1; j < n; j++) {
				if (nums[(i + j) % n] > nums[i]) {
					greater[i] = nums[(i + j) % n];
					break;
				}
			}
		}
		return greater;
	}

	public static int[] nextGreaterElementsArrayMod(int[] nums) {
		int n = nums.length;
		int[] greater = new int[n];

		for (int i = 0; i < n; i++) {
			int j;
			for (j = 1; j < n; j++) {
				if (nums[(i + j) % n] > nums[i]) {
					greater[i] = nums[(i + j) % n];
					break;
				}
			}
			if (j == n) {
				greater[i] = -1;
			}
		}
		return greater;
	}

}
