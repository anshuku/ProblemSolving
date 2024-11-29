package LeetCode.Arrays;

/*
 * P 485. Max Consecutive Ones - Easy
 * 
 * Given a binary array nums, return the maximum number of consecutive 1's in the array.
 * 
 * Approach - 2 variables
 */
public class P485MaxConsecutiveOnes {

	public static void main(String[] args) {
//		int[] nums = { 1, 1, 0, 1, 1, 1 };
		int[] nums = { 1, 0, 1, 1, 0, 1 };

		int maxOnes = findMaxConsecutiveOnes(nums);
		System.out.println("The max consecutive one is " + maxOnes);

	}

	public static int findMaxConsecutiveOnes(int[] nums) {
		int maxOnes = 0;
		int currOnes = 0;

		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == 1) {
				currOnes++;
			} else {
				maxOnes = Math.max(maxOnes, currOnes);
				currOnes = 0;
			}
		}
		return Math.max(maxOnes, currOnes);
	}

}
