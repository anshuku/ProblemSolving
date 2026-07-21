package LeetCode.Arrays;

import java.util.Arrays;

/*
 * P283. Move Zeroes - Easy
 * 
 * Given an integer array nums, move all 0's to the end of it 
 * while maintaining the relative order of the non-zero elements.
 * 
 * Note that you must do this in-place without making a copy of the array.
 * 
 * Approach - Iteration with variable
 */
public class P283MoveZeroes {

	public static void main(String[] args) {
		int[] nums = { 0, 1, 0, 3, 12 };
		int[] nums1 = nums.clone();
		int[] nums2 = nums.clone();

		int[] resultSwapTemp = moveZeroesSwapTemp(nums);
		System.out.println("Swap Temp: The array with all 0s moved to end with maintained relative order is: "
				+ Arrays.toString(resultSwapTemp));

		int[] resultSwap = moveZeroesSwap(nums1);
		System.out.println("Swap: The array with all 0s moved to end with maintained relative order is: "
				+ Arrays.toString(resultSwap));

		int[] resultSwapCopy = moveZeroesSwapCopy(nums2);
		System.out.println("Swap Copy: The array with all 0s moved to end with maintained relative order is: "
				+ Arrays.toString(resultSwapCopy));
	}

	public static int[] moveZeroesSwapTemp(int[] nums) {
		int n = nums.length;
		for (int i = 0, j = 0; i < n; i++) {
			if (nums[i] != 0) {
				int temp = nums[j];
				nums[j] = nums[i];
				nums[i] = temp;
				j++;
			}
		}
		return nums;
	}

	public static int[] moveZeroesSwap(int[] nums) {
		int n = nums.length;
		int j = 0;
		for (int i = 0; i < n; i++) {
			if (nums[i] != 0) {
				nums[j++] = nums[i];
			}
		}
		for (; j < nums.length; j++) {
			nums[j] = 0;
		}
		return nums;
	}

	public static int[] moveZeroesSwapCopy(int[] nums) {
		int n = nums.length;
		int arr[] = new int[n];
		int j = 0;
		for (int i = 0; i < n; i++) {
			if (nums[i] != 0) {
				arr[j++] = nums[i];
			}
		}
		for (int i = 0; i < n; i++) {
			nums[i] = arr[i];
		}
		return nums;
	}

}
