package LeetCode.BinarySearch;

/*
 * P33. Search in Rotated Sorted Array - Medium
 * 
 * There is an integer array nums sorted in ascending order (with distinct values).
 * 
 * Prior to being passed to your function, nums is possibly left rotated at an 
 * unknown index k (1 <= k < nums.length) such that the resulting array is 
 * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). 
 * For example, [0,1,2,4,5,6,7] might be left rotated by 3 indices and become [4,5,6,7,0,1,2].
 * 
 * Given the array nums after the possible rotation and an integer target, 
 * return the index of target if it is in nums, or -1 if it is not in nums.
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * 
 * All values of nums are unique.
 * 
 * Approach - Binary Search
 */
public class P33SearchRotatedSortedArray {

	public static void main(String[] args) {
		int[] nums = { 4, 5, 6, 7, 0, 1, 2 };
		int target = 0;
//		int target = 3;

//		int[] nums = { 1 };
//		int target = 0;

		int indexShiftOnePass = searchShiftOnePass(nums, target);
		System.out.println("One Pass: The index for the given target is: " + indexShiftOnePass);

		int indexShiftBS = searchShiftBS(nums, target);
		System.out.println("Shift BS: The index for the given target is: " + indexShiftBS);

		int indexLeftRightBS = searchLeftRightBS(nums, target);
		System.out.println("Left Right BS: The index for the given target is: " + indexLeftRightBS);

	}

	// Binary Search - One Pass
	// We use additional checks in binary search to narrow down the scope of the
	// search. We find mid as the pivot element's index. There are 3 cases:
	// If the nums[mid] == target, then return mid as it's the required index.
	// a) Pivot element is larger than the first element in the array, i.e. the
	// subarray from the first element to the pivot is non-rotated.
	// Here also 2 cases: If the target is located in the non-rotated subarray:
	// Go right or left based whether target is present in this part.
	// b) Pivot element is smaller than the first element of the array, i.e. the
	// rotation index is somehwere between 0 and mid. It means the subarray from the
	// pivot element to the last one is non-rotated. Again 2 cases:
	// If the target is located in non-rotated subarray. Go right or left based on
	// whether target is present in this part.
	private static int searchShiftOnePass(int[] nums, int target) {
		int n = nums.length;
		int start = 0;
		int end = n - 1;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] == target) {
				return mid;
				// Subarray on mid's left is sorted
			} else if (nums[mid] >= nums[start]) {
				if (target >= nums[start] && target < nums[mid]) {
					end = mid - 1;
				} else {
					start = mid + 1;
				}
				// Subarray on mid's right is sorted
			} else {
				if (target > nums[mid] && target < nums[end]) {
					start = mid + 1;
				} else {
					end = mid - 1;
				}
			}
		}
		return -1;
	}

	// Binary Search - Shift after pivot element
	private static int searchShiftBS(int[] nums, int target) {
		int n = nums.length;
		int start = 0;
		int end = n - 1;
		// Find the index of the pivot element(smallest element)
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] > nums[n - 1]) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return binarySearchShift(nums, start, target);
	}

	// Shift elements in a circular manner, with pivot element at index 0.
	// Then perform regular binary search.
	private static int binarySearchShift(int[] nums, int pivot, int target) {
		int n = nums.length;
		int start = pivot;
		int end = n - 1 + pivot;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid % n] == target) {
				return mid % n;
			} else if (nums[mid % n] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return -1;
	}

	// Binary Search - Search both left and right side of pivot element
	// 4, 5, 6, 7, 0, 1, 2
	// 4, 5, 6, 0, 1, 2, 3
	// 5, 6, 0, 1, 2, 3, 4
	public static int searchLeftRightBS(int[] nums, int target) {
		int n = nums.length;
		int start = 0;
		int end = n - 1;

		// Find the index of the smallest element(pivot index)
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] > nums[n - 1]) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}

		// Binary search over elements on the pivot element's left
		int answer = binarySearch(nums, 0, start - 1, target);
		if (answer != -1) {
			return answer;
		}

		// Binary search over elements on the pivot element's right
		return binarySearch(nums, start, n - 1, target);
	}

	private static int binarySearch(int[] nums, int start, int end, int target) {
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] == target) {
				return mid;
			} else if (nums[mid] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return -1;
	}

}
