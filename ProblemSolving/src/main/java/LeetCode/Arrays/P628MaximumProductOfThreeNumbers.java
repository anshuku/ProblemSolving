package LeetCode.Arrays;

import java.util.Arrays;

/*
 * P628. Maximum Product of Three Numbers - Easy
 * 
 * Given an integer array nums, find three numbers whose product is maximum and return the maximum product.
 * 
 * Constraints:
 * * 3 <= nums.length <= 10^4
 * * -1000 <= nums[i] <= 1000
 * 
 * Approach - Sorting, Comparing 5 variables
 */
public class P628MaximumProductOfThreeNumbers {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 3, 4 };
//		int[] nums = { -1, -2, -3, -4 };
//		int[] nums = { -2, -1, 0, 3 };
		int[] nums = { -2, -1, 0, 2, 3 };

		int maxProduct3Nums5Vars = maximumProduct5Vars(nums);
		System.out.println("5 Variables: the maximum product for the three numbers is: " + maxProduct3Nums5Vars);

		int maxProduct3NumsSort = maximumProductSort(nums);
		System.out.println("Sort: the maximum product for the three numbers is: " + maxProduct3NumsSort);
	}

	// Comparing Variables
	// We don't need to sort the nums array to find the max product. Instead, we can
	// only find the required 2 smallest values (min1 and min2) and the 3 largest
	// values (max1, max2, max3) by iterating over the nums array only once.
	// We need to compare the product of the product of max 3 numbers or product of
	// the smallest 2 numbers with the max number.
	// Time complexity - O(n).
	// Space complexity - O(1).
	private static int maximumProduct5Vars(int[] nums) {
		int min1, min2;
		min1 = min2 = Integer.MAX_VALUE;

		int max1, max2, max3;
		max1 = max2 = max3 = Integer.MIN_VALUE;

		for (int num : nums) {
			if (num < min1) { // nums is < min1, min2
				min2 = min1;
				min1 = num;
			} else if (num < min2) { // num is between min1 and min2
				min2 = num;
			}

			if (num > max1) { // nums is > max1, max2, max3
				max3 = max2;
				max2 = max1;
				max1 = num;
			} else if (num > max2) { // num is between max1 and max2
				max3 = max2;
				max2 = num;
			} else if (num > max3) { // num is between max2 and max3
				max3 = num;
			}
		}

		int startTwoProduct = min1 * min2 * max1;
		int endProduct = max1 * max2 * max3;

		return Math.max(startTwoProduct, endProduct);
	}

	// Sorting
	// We sort the given array in ascending order and find out the product of the
	// last 3 numbers. But, we can note that this product will be max only if all
	// the numbers are +ve. Since, there can be -ve elements as per constraints.
	// It could be possible that 2 negative numbers lying at the left extreme end
	// could contribute to a larger if the 3rd number in the triplet is the largest
	// number in the nums array.
	// We need to compare the product of the product of max 3 numbers or product of
	// the smallest 2 numbers with the max number.
	// Time complexity - O(nlogn) for sorting.
	// Space complexity - O(logn) for sorting.
	public static int maximumProductSort(int[] nums) {
		Arrays.sort(nums);

		int n = nums.length;

//		int startProduct = nums[0] * nums[1] * nums[2]; // not needed

		int startTwoProduct = nums[0] * nums[1] * nums[n - 1];
		int endProduct = nums[n - 1] * nums[n - 2] * nums[n - 3];

//		return Math.max(startTwoProduct, Math.max(startProduct, endProduct));
		return Math.max(startTwoProduct, endProduct);
	}

}
