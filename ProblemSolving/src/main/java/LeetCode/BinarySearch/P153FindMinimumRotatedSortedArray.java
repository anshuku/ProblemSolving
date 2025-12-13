package LeetCode.BinarySearch;

/*
 * P153. Find Minimum in Rotated Sorted Array - Medium
 * 
 * Suppose an array of length n sorted in ascending order is rotated between 
 * 1 and n times. For example, the array nums = [0,1,2,4,5,6,7] might become:
 * 
 * [4,5,6,7,0,1,2] if it was rotated 4 times.
 * [0,1,2,4,5,6,7] if it was rotated 7 times.
 * 
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 
 * time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 * 
 * Given the sorted rotated array nums of unique elements, return the minimum element of this array.
 * 
 * You must write an algorithm that runs in O(log n) time.
 * 
 * All the integers of nums are unique.
 * 
 * Approach - Binary Search
 */
public class P153FindMinimumRotatedSortedArray {

	public static void main(String[] args) {
//		int[] nums = { 3, 4, 5, 1, 2 };
		int[] nums = { 4, 5, 6, 7, 0, 1, 2 };
//		int[] nums = { 11, 13, 15, 17 };
//		int[] nums = { 2, 1 };
//		int[] nums = { 1 };

		int minElementEnd = findMinEnd(nums);
		System.out.println("End: The minimum element in the sorted rotated array: " + minElementEnd);

		int minElementStart = findMinStart(nums);
		System.out.println("Start: The minimum element in the sorted rotated array: " + minElementStart);
	}

	// Binary search - End as reference
	// We use a modified version of binary search where the condition which decides
	// the search direction is different. If the last element is not greater than
	// first. It means the array is rotated. This also means there is a point in
	// array where there is a change called inflection point.
	// The array is rotated strictly increasing. There is exactly one pivot.
	// nums[n-1] acts a fixed reference that lies on the right sorted part.
	// The algo is perfectly monotonic, so binary search works. It relies on
	// uniqueness.
	// 4, 5, 6, 7, 0, 1, 2
	// 4, 5, 6, 0, 1, 2, 3
	// 5, 6, 0, 1, 2, 3, 4
	private static int findMinEnd(int[] nums) {
		int n = nums.length;
		if (nums[0] < nums[n - 1]) {
			return nums[0];
		}
		int start = 0;
		int end = n - 1;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] > nums[n - 1]) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return nums[start];
	}

	// Binary search - start as reference
	// In the modified version of binary search we look for this inflection point:
	// All the elements at the left of inflection point >= first element
	// All the elements at the right of inflection point < first element
	// We find mid element of the array. If the mid element > first element of the
	// array, we look for inflection point on the right of mid.
	// If the mid element < first element of the array, we look for inflection point
	// on the left of mid.
	// We stop our search when any of the below 2 condition is satisfied:
	// If nums[mid] > nums[mid + 1] then nums[mid+1] is the smallest element.
	// If nums[mid - 1] > nums[mid] then nums[mid] is the smallest element.
	public static int findMinStart(int[] nums) {
		int n = nums.length;
		int start = 0;
		int end = n - 1;

		if (nums[0] < nums[end] || n == 1) {
			return nums[0];
		}

		while (start <= end) {
			int mid = start + (end - start) / 2;
//			if (nums[mid] > nums[mid + 1]) {
//				return nums[mid + 1];
//			}
//			if (nums[mid] < nums[mid - 1]) {
//				return nums[mid];
//			}
//			if (nums[mid] > nums[0]) {
//				start = mid + 1;
//			} else {
//				end = mid - 1;
//			}
			if (nums[mid] >= nums[0]) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return nums[start];
	}

}
