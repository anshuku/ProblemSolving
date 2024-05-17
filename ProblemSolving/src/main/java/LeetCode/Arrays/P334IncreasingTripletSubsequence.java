package LeetCode.Arrays;

/*
 * Given an integer array nums, return true if there exists a triple of indices (i, j, k) 
 * such that i < j < k and nums[i] < nums[j] < nums[k]. If no such indices exists, return false.
 */
public class P334IncreasingTripletSubsequence {

	public static void main(String[] args) {

		int[] nums = { 1, 2, 3, 4, 5 };
//		int[] nums = { 5, 4, 3, 2, 1 };
//		int[] nums = { 2, 1, 5, 0, 4, 6 };
//		int[] nums = { 2, 4, -2, -3 };
//		int[] nums = { 1, 5, 0, 4, 1, 3 };
//		int[] nums = { 1, 1, -2, 6 };
//		int[] nums = { 20, 100, 10, 12, 5, 13 };

		P334IncreasingTripletSubsequence pts = new P334IncreasingTripletSubsequence();
		boolean subsequenceExists = pts.increasingTriplet(nums);
		System.out.println("The increasing triplet subsequence exists " + subsequenceExists);
	}

	private boolean increasingTriplet(int[] nums) {
		int a = Integer.MAX_VALUE, b = Integer.MAX_VALUE;
		for (int num : nums) {
			if (num <= a) {// = other wise use case where there is repeating number returns wrong result
				a = num;
			} else if (num <= b) {// = other wise it fails for use case: 1, 1, -2, 6
				b = num;
			} else {
				return true;
			}
		}
		return false;
	}

	// can only handle the case when left and right is 1 index apart and fails for
	// use case: 1, 5, 0, 4, 1, 3
	public boolean increasingTripletWrong(int[] nums) {
		if (nums.length < 3) {
			return false;
		}
		int n = nums.length;
		int left = 0, right = 1;
		while (right < n) {
			if (nums[left] < nums[right]) {
				int curr = right + 1;
				while (curr < n && nums[right] >= nums[curr]) {
					curr++;
				}
				if (curr < n && nums[right] < nums[curr]) {
					return true;
				}
			}
			left++;
			right++;
		}
		return false;
	}
}
