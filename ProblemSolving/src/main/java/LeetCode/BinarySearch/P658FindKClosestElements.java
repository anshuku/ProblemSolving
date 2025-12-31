package LeetCode.BinarySearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * P658. Find K Closest Elements - Medium
 * 
 * Given a sorted integer array arr, two integers k and x, return the k closest 
 * integers to x in the array. The result should also be sorted in ascending order.
 * 
 * An integer a is closer to x than an integer b if:
 * > |a - x| < |b - x|, or
 * > |a - x| == |b - x| and a < b
 * 
 * Approch - Binary search, Sliding window, Custom Comparator
 * 
 * list.subList(start, end) takes O(1) time.
 */
public class P658FindKClosestElements {

	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4, 5 };
		int k = 4;
		int x = 3;

//		int[] arr = { 1, 1, 2, 3, 4, 5 };
//		int k = 4;
//		int x = -1;

		List<Integer> kClosestBinaryLower = findClosestElementsBinarySearchLowerBound(arr, k, x);
		System.out.println("Binary Search Lower Bound: The k closest elements to x are: " + kClosestBinaryLower);

		List<Integer> kClosestBinarySliding = findClosestElementsBinarySearchSlidingWindow(arr, k, x);
		System.out.println("Binary Search Sliding Window: The k closest elements to x are: " + kClosestBinarySliding);

		List<Integer> kClosestCustomComparator = findClosestElementsCustomComparator(arr, k, x);
		System.out.println("Custom Comparator: The k closest elements to x are: " + kClosestCustomComparator);

		List<Integer> kClosestCustomComparator2 = findClosestElementsCustomComparator2(arr, k, x);
		System.out.println("Custom Comparator2: The k closest elements to x are: " + kClosestCustomComparator2);

		List<Integer> kClosestCustomComparator3 = findClosestElementsCustomComparator3(arr, k, x);
		System.out.println("Custom Comparator3: The k closest elements to x are: " + kClosestCustomComparator3);
	}

	// Binary Search to find left bound: Find leftmost valid window
	// We can find the bounds of the sliding window - independent of k. We need to
	// find the leftmost index of a number that'll be the final answer. The biggest
	// index the left bound can be is arr.length - k(upper limit). If we go any
	// further to the right, one would run out of elements to include. Binary search
	// helps to find if an element exists or where it is in the sorted array. The
	// abstract binary search algo can help move start and end pointers closer to
	// left bound of the answer. We consider 2 indices at each binary search
	// operation, mid and mid+k. The relation between these indices is that only 1
	// of them can be in answer. We move left and right pointers based on:
	// If an element at arr[mid] is closer to x than arr[mid+k] then it means
	// arr[mid+k] and the elements in its right can't be in answer. We move right
	// pointer to avoid considering them. Similarly, if arr[mid+k] is closer to x
	// then move left pointer. The smallest element wins when there is a tie.
	// At end we've located the leftmost index for the final answer.
	// We're not searching value but searching the smallest start index i such that:
	// x−arr[i]≤arr[i+k]−x. This is a monotonic boolean function over i ∈ [0 , n-k].
	// while(start < end) is a classic lower bound/1st true binary search template.
	// When start == end invariant answer ∈ [start, end]. We get 1st valid index.
	// while(start <= end) breaks as the invariant, answer ∈ [start, end+1]
	// After the loop, answer is not at start. it's at start after crossing.
	// To use start <= end use end = n-k-1 to reduce search space to get mid <=
	// n-k-1 otherwise we get mid = n-k which gives out of bound with arr[mid+k].
	// mid ∈ [0 … n-k-1], mid+k ∈ [k … n-1] SAFE, start ends at correct window
	// Return subarray starting at this index that contains k elements.
	// Time complexity - O(log(n-k) + k), for doing binary search and populating
	// answer.
	// Space complexity - O(1)
	private static List<Integer> findClosestElementsBinarySearchLowerBound(int[] arr, int k, int x) {
		int n = arr.length;
		List<Integer> kClosest = new ArrayList<>();
		if (k == n) {
			for (int num : arr) {
				kClosest.add(num);
			}
			return kClosest;
		}
		int start = 0;
		// while(start < end) natrually preserves mid <= n-k-1 so mid
		int end = n - k;

		while (start < end) {
			int mid = start + (end - start) / 2;
			// If an element at arr[mid] is closer to x than arr[mid+k] then it means
			// arr[mid+k] and the elements in its right can't be in answer.
			// The smallest element wins when there is a tie.
			if (x - arr[mid] <= arr[mid + k] - x) {
				end = mid;
			} else {// if arr[mid+k] is closer to x then move left pointer.
				start = mid + 1;
			}
		}
		// To use start <= end we must use end = n - k - 1 else
		// when both start and end = n - k, we get arr[mid + k] = arr[n] which gives
		// ArrayIndexOutOfBoundsException. So to shrink search space use end = n-k-1
//		int end = n - k - 1;
//		while (start <= end) {
//			int mid = start + (end - start) / 2;
//			if (x - arr[mid] <= arr[mid + k] - x) {
//				end = mid - 1;
//			} else {
//				start = mid + 1;
//			}
//		}
		for (int i = end; i < start + k; i++) {
			kClosest.add(arr[i]);
		}

		return kClosest;
	}

	// Binary search: lower bound with Sliding window
	// Here the array is sorted so binary search can be used. When k << n we don't
	// need to sort. We find the closest number to x in array via binary search. The
	// 2nd closest number to x must be either left or right of 1st number. The 3rd
	// closest number to x must either be to the left of the 1st number or to the
	// right of 2nd number(vice versa). This pattern continues as input is sorted.
	// Using 2 pointers, we can maintain a sliding window that expands to contain k
	// closest elements to x. We use pointers obtained from lower bound binary
	// search and these are near to x. We expand window by moving left or right
	// pointers based on closeness to x.
	// Time complexity - O(logN + k + klogk), the initial binary search to find
	// pointers costs O(logN). The sliding window costs O(k)
	// Space complexity - O(1)
	private static List<Integer> findClosestElementsBinarySearchSlidingWindow(int[] arr, int k, int x) {
		int n = arr.length;
		List<Integer> kClosest = new ArrayList<>();
		if (k == n) {
			for (int num : arr) {
				kClosest.add(num);
			}
			return kClosest;
		}
		int start = 0;
		int end = n - 1;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (arr[mid] < x) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		int left = end;
		int right = start;

		while (kClosest.size() < k) {
			if (left < 0) {
				kClosest.add(arr[right++]);
			} else if (right >= n) {
				kClosest.add(arr[left--]);
			} else if (Math.abs(arr[left] - x) <= Math.abs(arr[right] - x)) {
				kClosest.add(arr[left--]);
			} else {
				kClosest.add(arr[right++]);
			}
		}
		Collections.sort(kClosest);
		return kClosest;
	}

	// Custom Comparator - List
	// We check every number in arr for its distance from x and sort the numbers by
	// this criteria. We take first k elements of the new sorted array as answer.
	// Algo: We create a new list/array that is arr sorted with a custom comparator.
	// The comparator should be abs(x-num) for each num in arr. Sorting the array in
	// ascending order order means that, 1st k elements = k closest elements to x.
	// At end we need to sort this array in asc order as per requirement.
	// Time complexity - O(NlogN + klogk), for n = array's length. To build
	// sortedArr, we need to sort every element in array by the criteria: x - num
	// which takes O(nlogn). This sorted array is sorted again in O(klogk) time.
	// Space complexity - O(N), we create list of size n. This can be reduces is
	// sorting is done in place.
	public static List<Integer> findClosestElementsCustomComparator(int[] arr, int k, int x) {
		int n = arr.length;
		List<Integer> kClosest = new ArrayList<>();
		if (k == n) {
			for (int num : arr) {
				kClosest.add(num);
			}
			return kClosest;
		}

		for (int num : arr) {
			kClosest.add(num);
		}

		Collections.sort(kClosest, (a, b) -> Math.abs(a - x) - Math.abs(b - x));

		// list.sublist(begin, end) O(1) is faster than populating list 1 by 1 from
		// array.
		kClosest = kClosest.subList(0, k);
		Collections.sort(kClosest);

		return kClosest;
	}

	// Custom Comparator - Array and List
	private static List<Integer> findClosestElementsCustomComparator2(int[] arr, int k, int x) {
		arr = Arrays.stream(arr).boxed().sorted((a, b) -> Math.abs(a - x) - Math.abs(b - x)).mapToInt(i -> i).toArray();

		List<Integer> kClosest = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			kClosest.add(arr[i]);
		}

		Collections.sort(kClosest);
		return kClosest;
	}

	private static List<Integer> findClosestElementsCustomComparator3(int[] arr, int k, int x) {
		arr = Arrays.stream(arr).boxed().sorted((a, b) -> Math.abs(a - x) - Math.abs(b - x)).mapToInt(i -> i).toArray();
		arr = Arrays.copyOf(arr, k);
		Arrays.sort(arr);
		List<Integer> kClosest = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			kClosest.add(arr[i]);
		}
		return kClosest;
	}
}
