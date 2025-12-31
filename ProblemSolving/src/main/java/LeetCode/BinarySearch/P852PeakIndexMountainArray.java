package LeetCode.BinarySearch;

/*
 * P852. Peak Index in a Mountain Array - Medium
 * 
 * You are given an integer mountain array arr of length n where 
 * the values increase to a peak element and then decrease.
 * 
 * Return the index of the peak element.
 * 
 * Your task is to solve it in O(log(n)) time complexity.
 * 
 * Approach - Binary search
 */
public class P852PeakIndexMountainArray {

	public static void main(String[] args) {
		int[] arr = { 0, 1, 0 };
//		int[] arr = { 0, 2, 1, 0 };
//		int[] arr = { 0, 1, 2, 0 };
//		int[] arr = { 0, 10, 5, 2 };

		int peakIndexBinary = peakIndexInMountainArrayBinary(arr);
		System.out.println("Binary Search: The peak index in mountain array: " + peakIndexBinary);

		int peakIndexLinear = peakIndexInMountainArrayLinear(arr);
		System.out.println("Linear Search: The peak index in mountain array: " + peakIndexLinear);
	}

	// Binary search
	// The mountain array has peak index i, any element at index < i will obey
	// arr[index]<arr[index + 1] only. Any index> i will follow arr[index] >
	// arr[index + 1] only. Here we can use binary search for element i where all
	// values < i satisfy a certain condition and all values > i do not satisfy it.
	// We find mid from start and end and check if arr[mid] < arr[mid + 1], we move
	// to upper half by setting start = mid + 1, as peak is at i > mid. Otherwise if
	// arr[mid] >= arr[mid + 1], we move to lower half of the range with r = mid as
	// peak index is either mid or some i < mid. Continue search until start < end.
	// When l == r, l or r denotes peak index.
	// Time complexity - O(logN), due to binary search
	// Space complexity - O(1)
	private static int peakIndexInMountainArrayBinary(int[] arr) {
		int n = arr.length;
		if (n == 1) {
			return 0;
		}
		int start = 0;
		int end = n - 1;
		while (start < end) {
			int mid = start + (end - start) / 2;
			if (arr[mid] < arr[mid + 1]) {
				start = mid + 1;
			} else {
				end = mid;
			}
		}
//		while (start <= end) {
//			int mid = start + (end - start) / 2;
//			if (arr[mid] < arr[mid + 1]) {
//				start = mid + 1;
//			} else {
//				end = mid - 1;
//			}
//		}
		// start == end and it's last element in search space
		return start;
	}

	// Linear search
	// From peak element, all elements to left are sorted ascending and all elements
	// to right are sorted descending.
	public static int peakIndexInMountainArrayLinear(int[] arr) {
		int n = arr.length;
		if (n == 1) {
			return 0;
		}
		int i = 0;
		while (arr[i] < arr[i + 1]) {
			i++;
		}
		return i;
	}

}
