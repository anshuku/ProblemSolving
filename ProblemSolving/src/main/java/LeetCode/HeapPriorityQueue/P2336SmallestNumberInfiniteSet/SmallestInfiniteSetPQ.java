package LeetCode.HeapPriorityQueue.P2336SmallestNumberInfiniteSet;

import java.util.Arrays;

/*
 * P2336. Smallest Number in Infinite Set - Medium
 * 
 * You have a set which contains all positive integers [1, 2, 3, 4, 5, ...].
 * Implement the SmallestInfiniteSet class:
 * 
 * - SmallestInfiniteSet() Initializes the SmallestInfiniteSet object to contain all positive integers.
 * 
 * - int popSmallest() Removes and returns the smallest integer contained in the infinite set.
 * 
 * - void addBack(int num) Adds a positive integer num back into the infinite set, 
 *   if it is not already in the infinite set.
 * 
 * Approach - Min Heap
 */
public class SmallestInfiniteSetPQ {

	int[] heap;
	int n;

	public SmallestInfiniteSetPQ() {
		n = 1000;
		heap = new int[n];

		for (int i = 0; i < n; i++) {
			heap[i] = i + 1;
		}
		for (int i = n / 2 - 1; i >= 0; i--) {
			heapify(heap, i, n);
		}
	}

	private void heapify(int[] nums, int i, int n) {
		int min = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		if (left < n && nums[left] < nums[min]) {
			min = left;
		}
		if (right < n && nums[right] < nums[min]) {
			min = right;
		}
		if (min != i) {
			int temp = nums[i];
			nums[i] = nums[min];
			nums[min] = temp;

			heapify(nums, min, n);
		}

	}

	public int popSmallest() {
		int temp = heap[0];
		heap[0] = heap[n - 1];
		heap[n - 1] = temp;
		heapify(heap, 0, n - 1);
		n--;
		return temp;
	}

	public void addBack(int num) {
		for (int i = 0; i < n; i++) {
			if (heap[i] == num) {
				return;
			}
		}
		heap[n] = num;
		n++;
		for (int i = n / 2 - 1; i >= 0; i--) {
			heapify(heap, i, n);
		}
	}

	public static void main(String[] args) {
		SmallestInfiniteSetPQ sni = new SmallestInfiniteSetPQ();
		System.out.println(Arrays.toString(sni.heap));
		sni.addBack(2);
		int smallest1 = sni.popSmallest();
		int smallest2 = sni.popSmallest();
		int smallest3 = sni.popSmallest();
		sni.addBack(1);
		int smallest4 = sni.popSmallest();
		int smallest5 = sni.popSmallest();
		int smallest6 = sni.popSmallest();

		System.out.println("The smallest values are: " + smallest1 + " " + smallest2 + " " + smallest3 + " " + smallest4
				+ " " + smallest5 + " " + smallest6);

	}

}
