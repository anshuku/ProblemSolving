package LeetCode.Arrays;

/*
 * 4. Median of Two Sorted Arrays - Hard
 * 
 * Given two sorted arrays nums1 and nums2 of size m and n 
 * respectively, return the median of the two sorted arrays.
 * 
 * The overall run time complexity should be O(log (m+n)).
 * 
 * Approach - Iteration, Binary Search
 */
public class P4MedianTwoSortedArrays {

	public static void main(String[] args) {
//		int[] nums1 = { 1, 2 };
//		int[] nums2 = { 3, 4 };

//		int[] nums1 = { 1, 3 };
//		int[] nums2 = { 2 };

		int[] nums1 = { 1, 3, 8 };
		int[] nums2 = { 7, 9, 10, 11 };

		double medianLinear = findMedianSortedArraysLinearMerge(nums1, nums2);
		System.out.println("Linear Merge: The median of two sorted array is " + medianLinear);

		double medianBinary = findMedianSortedArraysBinarySearch(nums1, nums2);
		System.out.println("Binary search: The median of two sorted array is " + medianBinary);
	}

	public static double findMedianSortedArraysLinearMerge(int[] nums1, int[] nums2) {
		int a = nums1.length;
		int b = nums2.length;

		int[] merged = new int[a + b];
		int i = 0, j = 0, k = 0;

		while (i < a && j < b) {
			if (nums1[i] < nums2[j]) {
				merged[k++] = nums1[i++];
			} else {
				merged[k++] = nums2[j++];
			}
		}
		while (i < a) {
			merged[k++] = nums1[i++];
		}
		while (j < b) {
			merged[k++] = nums2[j++];
		}

		if (k % 2 == 1) {
			return merged[k / 2];
		} else {
			a = merged[k / 2];
			b = merged[(k - 1) / 2];
			return (double) (a + b) / 2;
		}

	}

	// Binary search - Without merge
	// We always binary search the smaller array which guarantees log(min(n,m))
	// Median: If we merged the arrays, the median splits them like Left half |
	// Right half. We need to cut nums1 and nums2 into 2 parts such that, total
	// elements in left = (a + b + 1)/2. We add +1 to handle odd/even length.
	// Partition logic: We choose a cut in A and B:
	// partitionA = (left + right)/2, partitionB = (a+b+1)/2 - partitionA. We choose
	// a cut in A, such that left side count = partitionA + partitionB.
	// Define borders: We define the largest on left and smallest on right:
	// maxLeftA = biggest element left of cut in A.
	// minRightA = smallest element right of cut in A.
	// maxLeftB = biggest element left of cut in B.
	// minRightB = smallest element right of cut in B.
	// MIN and MAX handles empty partitions.
	// Check if partition is valid: maxLeftA <= minRightB AND maxLeftB <= minRightA
	// This guarantees: All left elements <= all right elements. So median is inside
	// the border elements.
	// We compute median: If total length is even
	// median = (max(left side) + min(right side)) / 2. If odd length:
	// median = max(left side). Othewise we adjust binary search
	// maxLeftA > minRightB, A's cut too far right, right = partitionA - 1
	// else A's cut too far left, left = paritionA + 1.
	// Example: A = [1,3,8] and B = [7,9,10,11]. Total = 7, left must have 4
	// elements
	// Try partitionA = 2, A: [1,3 | 8] and B: [7, 9 | 10, 11]
	// left side = [1,3,7,9], right side = [8,10,11]. Median = max(left) = 9
	// Time complexity - O(log(min(n, m))
	private static double findMedianSortedArraysBinarySearch(int[] nums1, int[] nums2) {
		// We always binary search the smaller array. This guarantees log(min(n,m))
		if (nums1.length > nums2.length) {
			return findMedianSortedArraysBinarySearch(nums2, nums1);
		}
		int a = nums1.length;
		int b = nums2.length;
		int left = 0;
		int right = a;
		while (left <= right) {
			// We cut nums1 and nums2 into 2 parts such that: total elements in left =
			// (a+b+1)/2
			// We choose a cut in A, such that left side count = partitionA + partitionB
			int partitionA = (left + right) / 2;
			int partitionB = (a + b + 1) / 2 - partitionA;

			int maxLeftA = partitionA == 0 ? Integer.MIN_VALUE : nums1[partitionA - 1];
			int minRightA = partitionA == a ? Integer.MAX_VALUE : nums1[partitionA];
			int maxLeftB = partitionB == 0 ? Integer.MIN_VALUE : nums2[partitionB - 1];
			int minRightB = partitionB == b ? Integer.MAX_VALUE : nums2[partitionB];

			if (maxLeftA <= minRightB && maxLeftB <= minRightA) {
				if ((a + b) % 2 == 0) {
					return (Math.max(maxLeftA, maxLeftB) + Math.min(minRightA, minRightB)) / 2.0;
				} else {
					return Math.max(maxLeftA, maxLeftB);
				}
			} else if (maxLeftA > minRightB) {
				right = partitionA - 1;
			} else {
				left = partitionA + 1;
			}
		}
		return 0;
	}

}
