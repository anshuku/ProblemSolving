package LeetCode.Arrays;

import java.util.Arrays;

/*
 * 3974. Maximum Total Sum of K Selected Elements - Medium
 * 
 * You are given an integer array nums and two integers k and mul.
 * 
 * Select exactly k elements from nums. Process these elements one by one in any order you choose.
 * 
 * For each selected element, independently choose one of the following:
 * > Add the element's value to the total sum, or
 * > Multiply the element by the current value of mul and add the result to the total sum.
 * 
 * After processing each selected element, mul decreases by 1, regardless of 
 * which option was chosen. The current value of mul may become 0 or negative.
 * 
 * Return an integer denoting the maximum possible total sum.
 * 
 * Approach - Iteration, int Overflow
 */
public class P3974MaximumTotalSumKSelectedElements {

	public static void main(String[] args) {
//		int[] nums = { 6, 1, 2, 9 };
//		int k = 3;
//		int mul = 2;

		int[] nums = { 3, 7, 5, 2 };
		int k = 2;
		int mul = 4;

//		int[] nums = { 4, 4 };
//		int k = 1;
//		int mul = 1;

		long maxSum = maxSum(nums, k, mul);
		System.out.println("The max sum after processing: " + maxSum);
	}

	public static long maxSum(int[] nums, int k, int mul) {
		int n = nums.length;

		Arrays.sort(nums);

		long totalSum = 0;

		for (int i = n - 1; i >= n - k; i--) {
			long val = Math.max(nums[i], (long) nums[i] * mul--);

			totalSum += val;
		}

		return totalSum;
	}

}
