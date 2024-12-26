package LeetCode.HeapPriorityQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * P2462. Total Cost to Hire K Workers - Medium
 * 
 * You are given a 0-indexed integer array costs where costs[i] is the cost of hiring the ith worker.
 * 
 * You are also given two integers k and candidates. We want to 
 * hire exactly k workers according to the following rules:
 * 
 * - You will run k sessions and hire exactly one worker in each session.
 * 
 * - In each hiring session, choose the worker with the lowest cost from either the first 
 * - candidates workers or the last candidates workers. Break the tie by the smallest index.
 *   > For example, if costs = [3,2,7,7,1,2] and candidates = 2, then in the first hiring 
 *     session, we will choose the 4th worker because they have the lowest cost [3,2,7,7,1,2].
 *   
 *   > In the second hiring session, we will choose 1st worker because they have the same 
 *     lowest cost as 4th worker but they have the smallest index [3,2,7,7,2]. Please note 
 *     that the indexing may be changed in the process.
 *   
 * - If there are fewer than candidates workers remaining, choose the worker with the 
 *   lowest cost among them. Break the tie by the smallest index.
 * 
 * - A worker can only be chosen once.
 * Return the total cost to hire exactly k workers.
 * 
 * Approach - Priority Queues / Min Heaps
 */
public class P2462TotalCostHireKWorkers {

	public static void main(String[] args) {
//		int[] costs = { 17, 12, 10, 2, 7, 2, 11, 20, 8 };
//		int k = 3, candidates = 4;

//		int[] costs = { 18, 64, 12, 21, 21, 78, 36, 58, 88, 58, 99, 26, 92, 91, 53, 10, 24, 25, 20, 92, 73, 63, 51, 65,
//				87, 6, 17, 32, 14, 42, 46, 65, 43, 9, 75 };
//		int k = 13, candidates = 23;'

//		int[] costs = { 31, 25, 72, 79, 74, 65, 84, 91, 18, 59, 27, 9, 81, 33, 17, 58 };
//		int k = 11, candidates = 2;

		int[] costs = { 50, 80, 34, 9, 86, 20, 67, 94, 65, 82, 40, 79, 74, 92, 84, 37, 19, 16, 85, 20, 79, 25, 89, 55,
				67, 84, 3, 79, 38, 16, 44, 2, 54, 58 };
		int k = 7, candidates = 12;

		long costHeap = totalCostHeap(costs, k, candidates);
		System.out.printf("Heap Array: The total cost to hire %d workers is - %d\n", k, costHeap);

		long costPQ = totalCostPQ(costs, k, candidates);
		System.out.printf("Priority Queue: The total cost to hire %d workers is - %d\n", k, costPQ);
	}

	private static long totalCostHeap(int[] costs, int k, int candidates) {
		int[] min1 = new int[candidates];
		int[] min2 = new int[candidates];
		long cost = 0;
		int n = costs.length;
		if (2 * candidates + k > n) {
			Arrays.sort(costs);
			while (k-- > 0) {
				cost += costs[k];
			}
			return cost;
		}
		int left = 0;
		int right = n - 1;
		int leftSize = 0;
		int rightSize = 0;
		while (left <= right && leftSize < candidates) {
			min1[leftSize++] = costs[left++];
		}
		for (int i = leftSize / 2 - 1; i >= 0; i--) {
			heapify(min1, i, leftSize);
		}
		while (left <= right && rightSize < candidates) {
			min2[rightSize++] = costs[right--];
		}
		for (int i = rightSize / 2 - 1; i >= 0; i--) {
			heapify(min2, i, rightSize);
		}
		while (k-- > 0) {
			if (leftSize != 0 & rightSize != 0) {
				if (min1[0] <= min2[0]) {
					cost += min1[0];
					pollHeap(min1, leftSize);
					leftSize--;
				} else {
					cost += min2[0];
					pollHeap(min2, rightSize);
					rightSize--;
				}
			} else {
				if (leftSize != 0) {
					cost += min1[0];
					pollHeap(min1, leftSize);
					leftSize--;
				} else {
					cost += min2[0];
					pollHeap(min2, rightSize);
					rightSize--;
				}
			}
			while (left <= right && leftSize < candidates) {
				min1[leftSize++] = costs[left++];
				heapifyWithSize(min1, leftSize);
			}
			while (left <= right && rightSize < candidates) {
				min2[rightSize++] = costs[right--];
				heapifyWithSize(min2, rightSize);
			}
		}
		return cost;
	}

	private static void pollHeap(int[] min, int size) {
		int temp = min[0];
		min[0] = min[size - 1];
		min[size - 1] = temp;
		heapify(min, 0, size - 1);
	}

	private static void heapifyWithSize(int[] min, int size) {
		if (min[0] > min[size - 1]) {
			int temp = min[0];
			min[0] = min[size - 1];
			min[size - 1] = temp;
			heapify(min, 0, size);
		}
	}

	// Min Heap
	private static void heapify(int[] heap, int i, int n) {
		int min = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;

		if (left < n && heap[left] < heap[min]) {
			min = left;
		}

		if (right < n && heap[right] < heap[min]) {
			min = right;
		}

		if (i != min) {
			int temp = heap[min];
			heap[min] = heap[i];
			heap[i] = temp;
			heapify(heap, min, n);
		}

	}

	public static long totalCostPQ(int[] costs, int k, int candidates) {
		// Default Priority Queues are min heaps for integers
		PriorityQueue<Integer> queue1 = new PriorityQueue<Integer>();
		PriorityQueue<Integer> queue2 = new PriorityQueue<Integer>();
		long cost = 0l;
		int left = 0;
		int right = costs.length - 1;
		while (k-- > 0) {
			// Populate min heaps spearately other wise in case of k < candidates
			// the repetion of last index where left == right gives wrong results
			// Left min heap can have more elements till the size candidates as wells
			while (left <= right && queue1.size() < candidates) {
				queue1.offer(costs[left++]);
			}
			while (left <= right && queue2.size() < candidates) {
				queue2.offer(costs[right--]);
			}
			if (queue1.size() > 0 && queue2.size() > 0) {
				if (queue1.peek() <= queue2.peek()) {
					cost += queue1.poll();
				} else {
					cost += queue2.poll();
				}
			} else {
				if (queue1.size() > 0) {
					cost += queue1.poll();
				} else {
					cost += queue2.poll();
				}
			}
		}
		return cost;
	}

}
