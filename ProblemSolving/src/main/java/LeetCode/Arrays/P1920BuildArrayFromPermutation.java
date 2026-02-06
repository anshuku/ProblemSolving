package LeetCode.Arrays;

import java.util.Arrays;

/*
 * P1920. Build Array from Permutation - Easy
 * 
 * Given a zero-based permutation nums (0-indexed), build an array ans of the same 
 * length where ans[i] = nums[nums[i]] for each 0 <= i < nums.length and return it.
 * 
 * A zero-based permutation nums is an array of distinct integers from 0 to nums.length - 1 (inclusive).
 * 
 * Follow-up: Can you solve it without using an extra space (i.e., O(1) memory)?
 * 
 * Approach - Iteration, Formulation
 */
public class P1920BuildArrayFromPermutation {

	public static void main(String[] args) {
//		int[] nums = { 0, 2, 1, 5, 3, 4 };
		int[] nums = { 5, 0, 1, 2, 3, 4 };

		int[] zeroPermutationRequired = buildArrayRequired(nums);
		System.out.println("Required: The zero-based permutation nums of the array is: "
				+ Arrays.toString(zeroPermutationRequired));

		int[] zeroPermutationInPlace = buildArrayInPlace(nums);
		System.out.println("In Place: The zero-based permutation nums of the array is: "
				+ Arrays.toString(zeroPermutationInPlace));
	}

	// Build In Place - modify original array
	// We enable each element nums[i] in nums to store both the current
	// value(nums[i]) and the final value(nums[nums[i]). Since the range of values
	// of elements in nums is [0, 999], it means both current and final value of
	// each element in nums are within the interval [0, 999]. We use concept similar
	// to "1000-based system" to represent current and final value of each element.
	// We use the quotient when divided by 1000 as final value and the remainder to
	// represent the current value. We traverse the nums and find final value and
	// add 1000 times to the current element. We traverse the array again and divide
	// the value of each element by 1000, retaining the quotient which gives the
	// final value. Since remainder/modulus gives the current value, while
	// calculating the final value of nums[i] and modifying the element, we need to
	// calculate the value of nums[nums[i]] before the modification, and the element
	// at the index nums[i] may have been modified. So, we take the modulus of the
	// value at that index with 1000 get final value. Here, 1000 can be nums.length.
	// Time complexity - O(n), as we traversed and modified the nums array twice in
	// O(n).
	// Space complexity - O(1)
	private static int[] buildArrayInPlace(int[] nums) {
		int n = nums.length;
		// Build the final value on the 1st iteration
		for (int i = 0; i < n; i++) {
			nums[i] = nums[i] + n * (nums[nums[i]] % n);
		}
		// Modifiedn to final value on the 2nd iteration.
		for (int i = 0; i < n; i++) {
			nums[i] = nums[i] / n;
		}
		return nums;
	}

	// Time complexity - O(n)
	// Space complexity - O(1), as the output array is not counted in the space
	// complexity.
	public static int[] buildArrayRequired(int[] nums) {
		int n = nums.length;
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = nums[nums[i]];
		}
		return result;
	}
}
