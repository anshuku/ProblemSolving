package LeetCode.BinarySearch;

/*
 * P81. Search in Rotated Sorted Array II - Medium
 * 
 * There is an integer array nums sorted in non-decreasing 
 * order (not necessarily with distinct values).
 * 
 * Before being passed to your function, nums is rotated at an unknown 
 * pivot index k (0 <= k < nums.length) such that the resulting array is 
 * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] 
 * (0-indexed). For example, [0,1,2,4,4,4,5,6,6,7] might be rotated 
 * at pivot index 5 and become [4,5,6,6,7,0,1,2,4,4].
 * 
 * Given the array nums after the rotation and an integer target, 
 * return true if target is in nums, or false if it is not in nums.
 * 
 * You must decrease the overall operation steps as much as possible.
 * 
 * Approach - Binary Search
 */
public class P81SearchRotatedSortedArrayII {

	public static void main(String[] args) {
//		int[] nums = { 2, 5, 6, 0, 0, 1, 2 };
//		int target = 0;
//		int target = 3;

		int[] nums = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 };
		int target = 2;

		boolean isPresentOnePassOpt = searchOnePassOpt(nums, target);
		System.out.println("BS One Pass Opt: The given element is present: " + isPresentOnePassOpt);

		boolean isPresentOnePass = searchOnePass(nums, target);
		System.out.println("BS One Pass: The given element is present: " + isPresentOnePass);

		boolean isPresentShift = searchShift(nums, target);
		System.out.println("BS Shift: The given element is present: " + isPresentShift);
	}

	// Binary Search - One pass
	// After rotating a sorted array, we get two sorted arrays appended to each
	// other. There is first sorted array(F) and 2nd sorted array(S). All the
	// elements of the 2nd array is <= first element start of first array.
	// We can tell which of the two arrays(F or S), the target lies by just
	// comparing with the first element of the array. We're looking target in nums:
	// Case 1. target > nums[start], target lies in 1st array F.
	// Case 2. target < nums[start], target lies in second array S.
	// Case 3. target == nums[start], target lies in 1st array F but can be in S.
	// If nums[start] <= target/nums[mid] we assume it lies in 1st array.
	// While using binary search, we can reduce the search space by using index of
	// mid and target in F and S.
	// Case 1: arr[mid] lies in F, target lies in S: Since S starts after F ends, we
	// know that element lies in (mid, End], start = mid + 1
	// Case 2: arr[mid] lies in S, target lies in F: Since S starts after F ends, we
	// know that element lies in [start, mid), end = mid - 1
	// Case 3: Both arr[mid] and target lie in F: since both of them are in same
	// sorted array, we compare arr[mid] and target to reduce the search space.
	// Case 4: Both arr[mid] and target lie in S: since both of them are in same
	// sorted array, we compare arr[mid] and target to reduce the search space.
	// There is a catch for all the above cases: if arr[mid] == arr[start], then
	// arr[mid] can belong to both F and S and we can't find relative position of
	// target. In this case, we have to move to next search space iteratively.
	// There are certain search spaces that allow binary search while others don't.
	// Time complexity - O(N) in worst case and O(logN) in best case, where N is
	// length of input array. Worst case: It happens when all the elements in the
	// array are same and we search for a different element. At each step, we will
	// only able to reduce the search space by 1 since arr[mid] == arr[start] and
	// it's not possible to decide the relative position of target from nums[mid].
	// Best case: when all elements are distinct. We can divide search space into
	// half just like normal binary search.
	// Space complexity - O(1)
	// Follow up: This problem is similar to Search in Rotated Sorted Array, but
	// nums may contain duplicates. Would this affect the runtime complexity? How
	// and why? By having duplicate elements in the array, we often miss the
	// opportunity to apply binary search in certain search spaces.
	private static boolean searchOnePassOpt(int[] nums, int target) {
		int n = nums.length;
		int start = 0;
		int end = n - 1;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] == target) {
				return true;
			}
			if (nums[start] != nums[mid]) {
				// which array does pivot belong to
				boolean isMidInFirst = isInFirst(nums, start, nums[mid]);
				// which array does target belong to
				boolean isTargetInFirst = isInFirst(nums, start, target);
				// If pivot and target exist in different sorted arrays
				// XOR is true when both operands are ditinct.
				if (isMidInFirst ^ isTargetInFirst) {
					if (isMidInFirst) {// pivot in the first, target in the second
						start = mid + 1;
					} else {// target in the first, pivot in the second
						end = mid - 1;
					}
				} else {// If pivot and target exist in same sorted array(both F or S)
					if (nums[mid] < target) {
						start = mid + 1;
					} else {
						end = mid - 1;
					}
				}
			} else { // We can't reduce search space in current binary search space
				start++;
				continue;
			}
		}
		return false;
	}

	private static boolean isInFirst(int[] nums, int start, int target) {
		return nums[start] <= target;
	}

	private static boolean searchOnePass(int[] nums, int target) {
		int n = nums.length;
		int start = 0;
		int end = nums.length;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] == target) {
				return true;
			} else if (nums[mid] != nums[start]) {
				boolean isMidFirst = isInFirst(nums, start, nums[mid]);
				boolean isTargetFirst = isInFirst(nums, start, target);
				if (isMidFirst && !isTargetFirst) {
					start = mid + 1;
				} else if (!isMidFirst && isTargetFirst) {
					end = mid - 1;
				} else {
					if (nums[mid] < target) {
						start = mid + 1;
					} else {
						end = mid - 1;
					}
				}
			} else {
				start++;
				for (int i = start; i <= end; i++) {
					if (nums[i] == target) {
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

	// X Binary Search Shift - doesn't work for certain cases
	public static boolean searchShift(int[] nums, int target) {
		int n = nums.length;
		int start = 0;
		int end = n - 1;
		while (start < end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] > nums[end]) {
				start = mid + 1;
			} else if (nums[mid] < nums[end]) {
				end = mid;
			} else {
				end--;
			}
		}
		return binarySearchShift(nums, start, target);
	}

	private static boolean binarySearchShift(int[] nums, int pivot, int target) {
		int n = nums.length;
		int start = pivot;
		int end = n - 1 + pivot;

		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid % n] == target) {
				return true;
			} else if (nums[mid % n] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return false;
	}

}
