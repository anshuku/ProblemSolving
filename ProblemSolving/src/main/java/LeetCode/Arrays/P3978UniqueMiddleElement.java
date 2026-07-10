package LeetCode.Arrays;

/*
 * P3978. Unique Middle Element - Easy
 * 
 * You are given an integer array nums of odd length n.
 * 
 * Return true if the middle element of nums appears exactly once in the array. Otherwise return false.
 * 
 * Approach - Arrays
 */
public class P3978UniqueMiddleElement {

	public static void main(String[] args) {
		int[] nums = { 1, 2, 3 };

//		int[] nums = { 1, 2, 2 };

		boolean isUnique = isMiddleElementUnique(nums);
		System.out.println("The middle element appears exactly once: " + isUnique);
	}

	public static boolean isMiddleElementUnique(int[] nums) {
		int n = nums.length;
		int mid = nums[n / 2];

		int count = 0;
		for (int num : nums) {
			if (num == mid) {
				count++;
			}
		}
		return count == 1;
	}

}
