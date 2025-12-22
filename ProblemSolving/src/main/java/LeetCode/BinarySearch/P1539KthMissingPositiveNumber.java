package LeetCode.BinarySearch;

/*
 * P1539. Kth Missing Positive Number - Easy
 * 
 * Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
 * 
 * Return the kth positive integer that is missing from this array.
 * 
 * Approach - Binary search
 */
public class P1539KthMissingPositiveNumber {

	public static void main(String[] args) {
		int[] arr = { 2, 3, 4, 7, 11 };
		int k = 5;
//		int k = 15;

//		int[] arr = { 1, 2, 3, 4 };
//		int k = 2;

		int kthPositiveBS = findKthPositiveBS(arr, k);
		System.out.println("Binary Search: The kth postive integer missing from array " + kthPositiveBS);

		int kthPositiveNum = findKthPositiveNum(arr, k);
		System.out.println("Check num: The kth postive integer missing from array " + kthPositiveNum);

		int kthPositiveBetween = findKthPositiveBetween(arr, k);
		System.out.println("Between: The kth postive integer missing from array " + kthPositiveBetween);
	}

	// Binary search - sorted input
	// To check how many positive integers are missing before the given array
	// element. We compare the input array with an array with no missing numbers.
	// The number of missing integers = difference between corresponding numbers.
	// The number of missing positive integers before idx = arr[idx] - idx - 1.
	// Using binary search, we get middle of array as pivot index. If the number of
	// +ve integers which are missing before arr[pivot] < k, we search on right.
	// Otherwise we search on left side. At end start = end + 1 and the kth missing
	// number is in-between arr[right] and arr[left]. The number of integers missing
	// before arr[right] is arr[right] - right - 1. The number to return is
	// arr[end] + k - (arr[end] - end - 1) = k + start
	// Time complexity - O(logN)
	// Space complexity - O(1)
	private static int findKthPositiveBS(int[] arr, int k) {
		if (k < arr[0]) {
			return k;
		}
		int start = 0;
		int end = arr.length - 1;

		while (start <= end) {
			int mid = start + (end - start) / 2;
			int currMissing = arr[mid] - mid - 1;
			// If the number of positive integers missing before arr[pivot] < k
			// we search on the right
			if (currMissing < k) {
				start = mid + 1;
			} else { // otherwise we go left
				end = mid - 1;
			}
		}
		return start + k;
	}

	public static int findKthPositiveNum(int[] arr, int k) {
		int n = arr.length;
		if (arr[0] > k) {
			return k;
		}
		k -= arr[0] - 1;
		int num = arr[0];
		for (int i = 0; i < n; i++) {
			while (arr[i] != num) {
				k--;
				if (k == 0) {
					return num;
				}
				num++;
			}
			num++;
		}
		return num + k - 1;
	}

	// Brute force
	// We find how many positive numbers are missing between 2 array elements:
	// arr[i+1] - arr[i] - 1, We iterate over the array and compute the number of
	// missing numbers in-between the elements. There are 3 cases:
	// If the kth missing number is < 1st element of array, return k.
	// Decrease k by the number of positive integers which are missing before array
	// starts: k -= arr[0] - 1. Iterate over the array elements:
	// At each step, compute the number of missing positive integers in between
	// (i+1)th and ith elements: currMissing = arr[i+1] - arr[i] - 1.
	// If k <= currMissing then number to return is in-between arr[i+1] and arr[i].
	// we return arr[i] + k. Otherwise decrease k by currMissing and proceed.
	// We're at end of loop, because the element to return is > last element. Return
	// arr[n-1] + k.
	// Time complexity - O(n), in worst case we need to parse all array elements
	// Space complexity - O(1)
	private static int findKthPositiveBetween(int[] arr, int k) {
		int n = arr.length - 1;
		// If the kth missing number is < arr[0]
		if (k < arr[0]) {
			return k;
		}
		k -= arr[0] - 1;
		// Search kth missing between the array numbers
		for (int i = 0; i < n; i++) {
			// missing count between arr[i] and arr[i+1]
			int between = arr[i + 1] - arr[i] - 1;
			// If the kth missing is between arr[i] and arr[i+1], return it.
			if (k <= between) {
				return arr[i] + k;
			}
			// decrement k by the count between current and next element.
			k -= between;
		}
		// missing number > arr[n-1]
		return arr[n] + k;
	}

}
