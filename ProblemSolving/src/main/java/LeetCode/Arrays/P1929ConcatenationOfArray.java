package LeetCode.Arrays;

import java.util.Arrays;

/*
 * P1929. Concatenation of Array - Easy
 * 
 * Given an integer array nums of length n, you want to create an array ans of length 
 * 2n where ans[i] == nums[i] and ans[i + n] == nums[i] for 0 <= i < n (0-indexed).
 * 
 * Specifically, ans is the concatenation of two nums arrays.
 * 
 * Return the array ans.
 * 
 * Approach - Arrays.copyOf, System.arraycopy
 * 
 * System.arraycopy is faster than normal iteration
 * System.arraycopy(source array, start, destination array, dest start, length to copy)
 * int[] arr = Arrays.copyOf(source, length)
 */
public class P1929ConcatenationOfArray {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 1 };
		Integer[] nums = { 1, 3, 2, 1 };

		Integer[] result = getConcatenation(nums);
		System.out.println("The concatenated array is " + Arrays.toString(result));
	}

	public static Integer[] getConcatenation(Integer[] nums) {
		Integer[] result = new Integer[nums.length * 2];
		System.arraycopy(nums, 0, result, 0, nums.length);
		System.arraycopy(nums, 0, result, nums.length, nums.length);
		return result;
	}

}
