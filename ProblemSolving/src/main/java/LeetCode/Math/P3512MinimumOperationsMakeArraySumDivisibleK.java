package LeetCode.Math;

/*
 * P3512. Minimum Operations to Make Array Sum Divisible by K - Easy
 * 
 * You are given an integer array nums and an integer k. You 
 * can perform the following operation any number of times:
 * > Select an index i and replace nums[i] with nums[i] - 1.
 * 
 * Return the minimum number of operations required to make the sum of the array divisible by k.
 * 
 * Approach - Math
 */
public class P3512MinimumOperationsMakeArraySumDivisibleK {

	public static void main(String[] args) {
		int[] nums = { 3, 9, 7 };
		int k = 4;

//		int[] nums = { 4,1,3 };
//		int k = 4;

//		int[] nums = { 1, 1, 1, 1, 1 };
//		int k = 3;

		int minOps = minOperations(nums, k);
		System.out.println("The minimum number of operations required is: " + minOps);
	}

	public static int minOperations(int[] nums, int k) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		return sum % k;
	}
}
