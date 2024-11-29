package LeetCode.Arrays.SlidingWindow;

/*
 * P1493. Longest Subarray of 1's After Deleting One Element - Medium
 * 
 * Given a binary array nums, you should delete one element from it.
 * Return the size of the longest non-empty subarray containing only 1's 
 * in the resulting array. Return 0 if there is no such subarray.
 * 
 * Approach - Sliding Window
 */
public class P1493LongestSubarray1AfterDeletingOne {

	public static void main(String[] args) {

		int[] nums = { 0, 1, 1, 1, 0, 1, 1, 0, 1 };

//		int[] nums = { 1, 1, 1 };

		int longestOnes = longestSubarray(nums);

		System.out.println("SW : The longest subarray of 1's after deleting one - " + longestOnes);

		int longestOnesWhile = longestSubarrayWhile(nums);

		System.out.println("SW While : The longest subarray of 1's after deleting one - " + longestOnesWhile);

	}

	// Time Complexity - O(n)
	// Space Complexity - O(1)
	public static int longestSubarray(int[] nums) {
		int left = 0, right = 0;
		int zeros = 0;
		while (right < nums.length) {
			if (nums[right++] == 0) {
				zeros++;
			}
			if (zeros > 1) {
				if (nums[left++] == 0) {
					zeros--;
				}
			}
		}
		return right - left - 1;
	}

	private static int longestSubarrayWhile(int[] nums) {
		int zeros = 0;
		int left = 0, right = 0;
		int longestOnes = 0;
		while(right < nums.length) {
			if(nums[right++] == 0) {
				zeros++;
			}
			while(zeros > 1) {
				if(nums[left++] == 0) {
					zeros--;
				}
			}
			longestOnes = Math.max(longestOnes, right - left - 1);
		}
		return longestOnes;
	}

}
