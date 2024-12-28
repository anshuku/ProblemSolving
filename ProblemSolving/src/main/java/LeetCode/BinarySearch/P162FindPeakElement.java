package LeetCode.BinarySearch;

/*
 * P162. Find Peak Element - Medium
 * 
 * A peak element is an element that is strictly greater than its neighbors.
 * 
 * Given a 0-indexed integer array nums, find a peak element, and return its index. 
 * If the array contains multiple peaks, return the index to any of the peaks.
 * 
 * You may imagine that nums[-1] = nums[n] = -∞. In other words, an element is always 
 * considered to be strictly greater than a neighbor that is outside the array.
 * 
 * You must write an algorithm that runs in O(log n) time.
 * 
 * Approach - Searching, Binary Search
 */
public class P162FindPeakElement {

	public static void main(String[] args) {

		int[] nums = { 1, 2, 3, 1 };

//		int[] nums = { 1 };

//		int[] nums = { 1, 0 };

//		int[] nums = { 0, 1 };

//		int[] nums = { 2, 3, 4 };

		int peakBinarySearch = findPeakElementBS(nums);
		System.out.println("Binary Search: The peak element is: " + peakBinarySearch);

		int peak = findPeakElement(nums);
		System.out.println("The peak element is: " + peak);
	}

	private static int findPeakElementBS(int[] nums) {
		int n = nums.length;
		if (n == 1) {
			return 0;
		}
		if (nums[0] > nums[1]) {
			return 0;
		}
		if (nums[n - 1] > nums[n - 2]) {
			return n - 1;
		}
		int left = 1;
		int right = n - 2;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (nums[mid - 1] < nums[mid] && nums[mid] > nums[mid + 1]) {
				return mid;
			} else if (nums[mid - 1] < nums[mid]) { // 1 2 3 -> search towards right
				left = mid + 1;
			} else {
				right = mid - 1;// 3 2 1-> search towards left
			}
		}
		return 0;
	}

	public static int findPeakElement(int[] nums) {

		int n = nums.length;

		int peak = n - 1;
		for (int i = 0; i < n - 1; i++) {
			if (nums[i] > nums[i + 1]) {
				return i;
			}
		}
		return peak;

	}

}
