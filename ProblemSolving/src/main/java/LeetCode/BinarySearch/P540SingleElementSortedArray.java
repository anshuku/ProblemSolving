package LeetCode.BinarySearch;

/*
 * P540. Single Element in a Sorted Array - Medium
 * 
 * You are given a sorted array consisting of only integers where every element 
 * appears exactly twice, except for one element which appears exactly once.
 * 
 * Return the single element that appears only once.
 * 
 * Your solution must run in O(log n) time and O(1) space.
 * 
 * Approach - Binary search
 * 
 * For these problems, it's good to draw a diagram and avoid off-by-one errors. Avoid
 * guess and check approach.
 * 
 * For solving array problems invariants like this can be solved with binary search as it's flexible.
 */
public class P540SingleElementSortedArray {

	public static void main(String[] args) {
		int[] nums = { 1, 1, 2, 3, 3, 4, 4, 8, 8 };
//		int[] nums = { 3, 3, 7, 7, 10, 11, 11 };
//		int[] nums = { 1, 1, 2 };

		int uniqueBinaryEven = singleNonDuplicateBinaryEven(nums);
		System.out.println("Binary Search Even: The number which appears exactly once " + uniqueBinaryEven);

		int uniqueBinary = singleNonDuplicateBinary(nums);
		System.out.println("Binary Search: The number which appears exactly once " + uniqueBinary);

		int uniqueLinear = singleNonDuplicateLinear(nums);
		System.out.println("Linear Search: The number which appears exactly once " + uniqueLinear);

		int uniqueXOR = singleNonDuplicateXOR(nums);
		System.out.println("XOR: The number which appears exactly once " + uniqueXOR);
	}

	// Binary search
	// We only binary search the even indices. The single element is 1st even index
	// not followed by its pair(used this property in linear search algo). After the
	// single element, the pattern becomes being odd index followed by their pair.
	// The single element(even index) and all elements after it are odd index
	// followed by their pair or if we check the next to next record it's even
	// indexes not followed by thier pair. With given even index one can check left
	// or right for single element. Algo: We set vars and loops so that we only
	// consider even indices. The last index of odd-len array is always even, so we
	// can set hi and lo as start and end of array. To ensure mid is also even, we
	// decrement it by 1 if it's odd. In cases where we decrement(even number of
	// even indices), we get lower middle, incrementing by 1 wouldn't work, it'd
	// lead of infinite loop as search space won't reduce in some cases.
	// - We ccheck whether mid index is same as one after it.
	// If it is we know that mid is not single element as single element is at an
	// even index after mid. start = mid + 2, +2 to point to an even index.
	// If it is now, then single element is either mid or before mid. end = mid.
	// Once start = end, we've 1 element = single element.
	// Time complexity - O(logN/2) as we're using binary search for half the
	// elements.
	// Space complexity - O(1)
	private static int singleNonDuplicateBinaryEven(int[] nums) {
		int n = nums.length;
		if (n == 1) {
			return nums[0];
		}
		int start = 0;
		int end = n - 1;
		while (start < end) {
			int mid = start + (end - start) / 2;
			mid = mid % 2 == 0 ? mid : mid - 1;
			if (nums[mid] == nums[mid + 1]) {
				start = mid + 2;
			} else {
				end = mid;
			}
		}
		return nums[start];
	}

	// Binary search
	// even odd
	// odd 7: 0 6 | 3 | 0 1 2 3 4 5 6 | 11 22 33 4
	// odd 7: 0 6 | 3 | 0 1 2 3 4 5 6 | 11 22 3 44
	// odd 7: 0 6 | 3 | 0 1 2 3 4 5 6 | 11 2 33 44
	// odd 7: 0 6 | 3 | 0 1 2 3 4 5 6 | 1 22 33 44
	// odd 9: 0 8 | 4 | 0 1 2 3 4 5 6 7 8 | 11 22 33 44 5
	// odd 9: 0 8 | 4 | 0 1 2 3 4 5 6 7 8 | 11 22 33 4 55
	// odd 9: 0 8 | 4 | 0 1 2 3 4 5 6 7 8 | 11 22 3 44 55
	// odd 9: 0 8 | 4 | 0 1 2 3 4 5 6 7 8 | 11 2 33 44 55
	// odd 9: 0 8 | 4 | 0 1 2 3 4 5 6 7 8 | 1 22 33 44 55
	// Here there are 3 cases for which 2 cases has 2 subcases each.
	// We use binary search to find the single element. We check the middle item to
	// determine if the solution is middle item, or to left or right. The starting
	// array always have odd number of items. nums=[1,1,4,4,5,5,6,6,8,9,9]. When we
	// remove a pair from the center. We're left with a left subarray and a right
	// subarray. The subarray containing the single must have odd length. The
	// subarray not having it must have even length. We find the side with odd
	// length. Algo: We set lo and hi to be the lowest and highest index, and then
	// halve the array until we find the single element or until there is 1 element.
	// If there is only 1 element then it's the result. On each iteration we find
	// mid and then save odd/evenness of sidesin isHalfEven(mid-start). We then
	// look at which half the middle element's partner is in - either last element
	// in left subarray or 1st element in right subarray. We can decide which side
	// is now odd lengthed(contains single element) and set lo and high accordingly.
	// We update lo and hi based on values of mid and isHalfEven.
	// Case 1: mid's partner in right and halves - even. Right side odd lengthed.
	// 1 1 4 4 5 5 6 8 8 - mid's i = 4(5), new start = mid + 2 to get above mid + 1.
	// Case 2: mid's partner in right and halves - odd. Left side odd lengthed.
	// 1 1 4 5 5 6 6 8 8 9 9 - mid's i = 5(6), new end = mid - 1 to get below mid.
	// Case 3: mid's partner in left and halves - even. Left side odd lengthed.
	// 1 1 4 5 5 6 6 8 8 - mid's i = 4(5), new end = mid - 2 to get below mid - 1.
	// Case 4: mid's partner in left and halves - odd. Right side odd lengthed.
	// 1 1 4 4 5 5 6 6 8 9 9 - mid's i = 5(5), new start = mid + 1 to get above mid.
	// Algo works even if the array isn't fully sorted. The pairs should always be
	// grouped together (ex - 10,10,4,4,7,11,11,12,12,2,2), order doesn't matter.
	// Binary search worked for this problem because subarray with the single number
	// is always odd lengthed, not because the array was fully sorted. This is
	// invariant, something that is always true(array having single element - odd
	// lengthed).
	// Time complexity - O(logN), due to binary search
	// Space complexity - O(1)
	private static int singleNonDuplicateBinary(int[] nums) {
		int n = nums.length;
		if (n == 1) {
			return nums[0];
		}
		int start = 0;
		int end = n - 1;
		while (start < end) {
			int mid = start + (end - start) / 2;
			boolean isHalfEven = (mid - start) % 2 == 0;
			if (nums[mid] == nums[mid + 1]) {
				if (isHalfEven) {
					start = mid + 2;
				} else {
					end = mid - 1;
				}
			} else if (nums[mid] == nums[mid - 1]) {
				if (isHalfEven) {
					end = mid - 2;
				} else {
					start = mid + 1;
				}
			} else {
				return nums[mid]; // as nums[mid] =/= nums[mid + 1] or nums[mid - 1]
			}
		}
		// At end we get last element, nums[start] = nums[end]
		return nums[start];
	}

	// Linear search
	// We skip 2 elements if nums[i] == nums[i + 1] before i = n-1.
	// We return nums[i] before loop finishes before i=n-1 else we return nums[n-1]
	// at end.
	private static int singleNonDuplicateLinear(int[] nums) {
		int n = nums.length;
		for (int i = 0; i < n - 1; i += 2) {
			if (nums[i] != nums[i + 1]) {
				return nums[i];
			}
		}
		return nums[n - 1];
	}

	// XOR
	// a^a = 0
	// a^b^b = a
	// a^b^b^c^c = a
	public static int singleNonDuplicateXOR(int[] nums) {
		int n = nums.length;
		int num = nums[0];
		for (int i = 1; i < n; i++) {
			num ^= nums[i];
		}
		return num;
	}

}
