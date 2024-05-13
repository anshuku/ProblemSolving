package LeetCode.Arrays;

public class P4MedianTwoSortedArrays {

	public static void main(String[] args) {
		int[] nums1 = { 1, 2 };
		int[] nums2 = { 3, 4 };

//		int[] nums1 = { 1, 3 };
//		int[] nums2 = { 2 };

		P4MedianTwoSortedArrays pma = new P4MedianTwoSortedArrays();
		double median = pma.findMedianSortedArrays(nums1, nums2);

		System.out.println("The median of two sorted array is " + median);
	}

	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
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

}
