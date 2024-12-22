package LeetCode.HeapPriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/*
 * P215. Kth Largest Element in an Array - Medium
 * 
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 * 
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 * 
 * Can you solve it without sorting?
 * 
 * Approach - Heap Sort, Priority queue
 */
public class P215KthLargestElementArray {

	public static void main(String[] args) {
		int[] nums = { 3, 2, 1, 5, 6, 4 };
		int k = 2;

//		int[] nums = { 3, 2, 3, 1, 2, 4, 5, 5, 6 };
//		int k = 4;

		int kthLargestFrequency = findKthLargestFrequency(nums, k);
		System.out.println("Frequency Array: Kth Largest element " + kthLargestFrequency);

		int kthLargestQuickSelect = findKthLargestQuickSelect(nums, k);
		System.out.println("Quick Select: Kth Largest element " + kthLargestQuickSelect);

		int kthLargestPriorityQ = findKthLargestPQ(nums, k);
		System.out.println("Priority Queue: Kth Largest element " + kthLargestPriorityQ);

		int kthLargestHeap = findKthLargestHeap(nums, k);
		System.out.println("Heap: Kth Largest element " + kthLargestHeap);
	}

	private static int findKthLargestFrequency(int[] nums, int k) {
		int[] frequency = new int[20001];
		int max = -1;
		for (int num : nums) {
			frequency[num + 10000]++;
			if (max < num + 10000) {
				max = num + 10000;
			}
		}
		for (int i = max; i >= 0; i--) {
			if (frequency[i] > 0) {
				k -= frequency[i];
				if (k <= 0) {
					return i - 10000;
				}
			}
		}
		return -1;
	}

	private static int findKthLargestQuickSelect(int[] nums, int k) {
		List<Integer> list = new ArrayList<Integer>();

		for (int num : nums) {
			list.add(num);
		}
		return getPivot(list, k);
	}

	// QuickSelect
	// sorting happens in descending order
	private static int getPivot(List<Integer> list, int k) {
		int pivotIndex = new Random().nextInt(list.size());

		int pivot = list.get(pivotIndex);
		List<Integer> left = new ArrayList<Integer>();
		List<Integer> right = new ArrayList<Integer>();
		List<Integer> mid = new ArrayList<Integer>(); // not necessarily there is 1 mid element

		for (int num : list) {
			if (num > pivot) {// descending order
				left.add(num);
			} else if (num < pivot) {
				right.add(num);
			} else {
				mid.add(num);
			}
		}
		if (k <= left.size()) {
			return getPivot(left, k);
		}
		if (left.size() + mid.size() < k) {
			return getPivot(right, k - left.size() - mid.size());
		}
		return pivot;
	}

	private static int findKthLargestPQ(int[] nums, int k) {
		// Max Heap with max element at top.
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>((a, b) -> b - a);// (a, b) else error
		for (int num : nums) {
			queue.offer(num);
		}
		while (k-- > 1) { // example k = 2 [6, 5, 4]
			queue.poll();
		}
		return queue.peek();
	}

	public static int findKthLargestHeap(int[] nums, int k) {
		int N = nums.length;
		for (int i = N / 2 - 1; i >= 0; i--) {
			heapify(nums, i, N);
		}
//		for (int i = N - 1; i > 0; i--) {
//			int temp = nums[0];
//			nums[0] = nums[i];
//			nums[i] = temp;
//			heapify(nums, 0, i);
//		}
//		return nums[N - k];

		for (int i = N - 1; k-- > 1; i--) {// i > 0 && - not needed for valid inputs
			int temp = nums[0];
			nums[0] = nums[i];
			nums[i] = temp;
			heapify(nums, 0, i);
		}
		return nums[0];
	}

	static void heapify(int[] arr, int i, int N) {
		int largest = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;

		if (left < N && arr[largest] < arr[left]) {
			largest = left;
		}
		if (right < N && arr[largest] < arr[right]) {
			largest = right;
		}
		if (i != largest) {
			int temp = arr[largest];
			arr[largest] = arr[i];
			arr[i] = temp;
			heapify(arr, largest, N);
		}
	}

}
