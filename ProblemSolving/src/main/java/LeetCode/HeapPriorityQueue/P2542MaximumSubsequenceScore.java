package LeetCode.HeapPriorityQueue;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * P2542. Maximum Subsequence Score - Medium
 * 
 * You are given two 0-indexed integer arrays nums1 and nums2 of equal length n and a 
 * positive integer k. You must choose a subsequence of indices from nums1 of length k.
 * 
 * For chosen indices i0, i1, ..., ik - 1, your score is defined as:
 * 
 * - The sum of the selected elements from nums1 multiplied with the 
 *   minimum of the selected elements from nums2.
 * - It can defined simply as: (nums1[i0] + nums1[i1] +...+ nums1[ik - 1]) * 
 *   min(nums2[i0] , nums2[i1], ... ,nums2[ik - 1]). 
 * 
 * Return the maximum possible score.
 * 
 * A subsequence of indices of an array is a set that can be derived from the set 
 * {0, 1, ..., n-1} by deleting some or no elements.
 * 
 * Approach - Priority Queue and Heap array
 */
public class P2542MaximumSubsequenceScore {

	public static void main(String[] args) {

//		int[] nums1 = { 1, 3, 3, 2 };
//		int[] nums2 = { 2, 1, 3, 4 };
//		int k = 3;

		int[] nums1 = { 4, 2, 3, 1, 1 };
		int[] nums2 = { 7, 5, 10, 9, 6 };
		int k = 1;

		long maxScoreArr = maxScoreArr(nums1, nums2, k);
		System.out.println("Heap Array: The maximum subsequence score is - " + maxScoreArr);

		long maxScorePQ = maxScorePriorityQueue(nums1, nums2, k);
		System.out.println("Priority Queue: The maximum subsequence score is - " + maxScorePQ);
	}

	// Time complexity - O(nlogn + klogk), time n*logn for sorting combined array
	// time k*logk for sorting heap array
	// Space complexity - O(n)
	private static long maxScoreArr(int[] nums1, int[] nums2, int k) {
		int n = nums1.length;
		int[][] combined = new int[n][2];
		for (int i = 0; i < n; i++) {
			combined[i] = new int[] { nums1[i], nums2[i] };
		}
		// Created a combined array and sorted the array based on nums2
		Arrays.sort(combined, (a, b) -> b[1] - a[1]);
		// Heap will store the min value of nums1 at it's root
		int[] heap = new int[k];
		int sum = 0;
		for (int i = 0; i < k; i++) {
			heap[i] = combined[i][0];
			sum += combined[i][0];
		}
		for (int i = k / 2 - 1; i >= 0; i--) {
			heapify(heap, i, k);
		}
		int product = 0;
		product = Math.max(product, sum * combined[k - 1][1]);
		for (int i = k; i < n; i++) {
			sum -= heap[0];
			heap[0] = combined[i][0];
			heapify(heap, 0, k);
			sum += combined[i][0];
			product = Math.max(product, sum * combined[i][1]);
		}
		return product;
	}

	private static void heapify(int[] heap, int i, int k) {
		int min = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		if (left < k && heap[left] < heap[min]) {
			min = left;
		}
		if (right < k && heap[right] < heap[min]) {
			min = right;
		}
		if (i != min) {
			int temp = heap[i];
			heap[i] = heap[min];
			heap[min] = temp;
			heapify(heap, min, k);
		}
	}

	public static long maxScorePriorityQueue(int[] nums1, int[] nums2, int k) {
		int n = nums1.length;
		int[][] combined = new int[n][2];
		for (int i = 0; i < n; i++) {
			combined[i][0] = nums1[i];
			combined[i][1] = nums2[i];
		}
		// sorting the array based on values of 2nd array in descending order
		Arrays.sort(combined, (a, b) -> b[1] - a[1]);
		for (int[] a : combined) {
			System.out.println(Arrays.toString(a));
		}

		// stores values of 1st array and pops the least element to deduct from sum.
		// min heap to store top k elements
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(k, (a, b) -> a - b);

		int product = 0;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum += combined[i][0];
			pq.offer(combined[i][0]);
			// Come before equality check otherwise no further product updates
			if (pq.size() > k) {
				int num = pq.poll();
				sum -= num;
			}
			if (pq.size() == k) {
				// Minimum from the window of k elements is taken in combined[i][1]
				product = Math.max(product, sum * combined[i][1]);
			}
		}

		return product;
	}

}
