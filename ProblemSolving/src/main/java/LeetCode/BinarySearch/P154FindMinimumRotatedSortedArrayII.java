package LeetCode.BinarySearch;

/*
 * P154. Find Minimum in Rotated Sorted Array II - Hard
 * 
 * Suppose an array of length n sorted in ascending order is rotated between
 * 1 and n times. For example, the array nums = [0,1,4,4,5,6,7] might become:
 * 
 * [4,5,6,7,0,1,4] if it was rotated 4 times.
 * [0,1,4,4,5,6,7] if it was rotated 7 times.
 * 
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time
 * results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 * 
 * Given the sorted rotated array nums that may contain duplicates, return the minimum element of this array.
 * 
 * You must decrease the overall operation steps as much as possible.
 * 
 * Approach - Binary Search modified
 */
public class P154FindMinimumRotatedSortedArrayII {

	public static void main(String[] args) {
		int[] nums = { 4, 5, 6, 7, 0, 1, 4 };
//		int[] nums = { 0, 1, 4, 4, 5, 6, 7 };
//		int[] nums = { 2, 2, 2, 0, 0, 1, 1 };
//		int[] nums = { 1, 3, 5 };
//		int[] nums = { 3, 1, 3 };

		int minElement = findMin(nums);
		System.out.println("End: The minimum element in the sorted rotated array: " + minElement);

		int minElementTrackMin = findMinTrackMin(nums);
		System.out.println("Track Min: The minimum element in the sorted rotated array: " + minElementTrackMin);
	}

	// Binary search
	// We compare pivot element with the element at upper bound pointer nums[end].
	// Now we define cases on how to update the 2 pointers.
	// Here duplicates may destroy ordering guarantees. There are 3 cases:
	// 1. nums[pivot] < nums[end]: The pivot element is in the same side as upper
	// bound element. So the desired min element resides in left half so we move the
	// upper bound to pivot index, end = pivot as pivot may contain the min element.
	// 2. nums[pivot] > nums[end]: The pivot element is in different side of upper
	// bound element. So, the desired element should be in right side of pivot
	// element. We update lower bound next to pivot or low = pivot + 1.
	// 3. nums[pivot] == nums[end]: In this case, we're not sure which side of the
	// pivot, the desired minimum element should reside. To further reduce the
	// search scope, a safe measure is to reduce upper bound by one i.e. end=end-1,
	// rather than moving to the pivot point. It prevents algo stagnation and
	// maintains correctness as it'd not skip the desired element.
	// nums[mid] == nums[n-1] or nums[mid] == nums[end], one can't tell which side
	// the minimum lies on. Shrinking the wrong side can skip the minimum.
	// Unlike Problem: Find Minimum in Rotated Sorted Array, where all numbers were
	// unique, nums[n-1] is not a safe reference with duplicates and can belong to
	// any of the side of the nums array. One may eliminate the wrong segment.
	// Also we can't have end = mid - 1 in case nums[mid] < nums[end], as mid can be
	// the minumum. But, end = mid - 1 excludes the mid which leads to lose of
	// answer. In case nums[mid] < nums[end], we have end = mid as it can be the
	// min. When values are equal we discard safely by putting end--, this leads to
	// worst case O(n) when all elements are equal.
	// Time complexity - O(logn) as it's a binary search algo. In worst case when
	// all elements are identical, the algo would iterate each element due to end =
	// end - 1 and time complexity becomes O(n).
	// Space complexity - O(1)
	// This problem is similar to Find Minimum in Rotated Sorted Array, but nums may
	// contain duplicates. Would this affect the runtime complexity? How and why?
	// The Problem: Find Minimum in Rotated Sorted Array can also be solved by this
	// algo, it's a specific case of this problem#154 where the array does not
	// contain any duplicates. This duplicates presence renders the time complexity
	// to be linear, rather than O(logN). Also high=pivot-1 doesn't works directly.
	// for equals case, low-- doesn't works for all cases.
	// This is due to the fact that the update of the pointers should be consistent
	// with the conditions of the loop.
	public static int findMin(int[] nums) {
		int n = nums.length;
		int start = 0;
		int end = n - 1;

		while (start <= end) { // start < end also works
			int mid = start + (end - start) / 2;
			if (nums[mid] > nums[end]) { // min is strictly right, no need to keep mid
				start = mid + 1;
			} else if (nums[mid] < nums[end]) {
				end = mid; // min can be in mid, keep mid
			} else { // nums[mid] == nums[end]
				end--; // shrink safely
			}
		}
		return nums[start];
	}

	// Ambiguity due to duplicates. One must keep mid when unsure or explicitly
	// track ans. Here, ans tracks the minimum. Even if we discard the wrong half
	// due to duplicates, we saved the min. It also maintains correctness.
	private static int findMinTrackMin(int[] nums) {
		int n = nums.length;
		int start = 0;
		int end = n - 1;
		int ans = Integer.MAX_VALUE;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			ans = Math.min(ans, nums[mid]);
			if (nums[mid] > nums[end]) {
				start = mid + 1;
			} else if (nums[mid] < nums[end]) {
				end = mid - 1;
			} else {
				end--;
			}
		}
		return ans;
	}
}
