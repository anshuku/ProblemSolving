package LeetCode.HeapPriorityQueue.P295FindMedianFromDataStream;

import java.util.Collections;
import java.util.PriorityQueue;

/*
 * P295. Find Median from Data Stream - Hard
 * 
 * The median is the middle value in an ordered integer list. If the size of the list 
 * is even, there is no middle value, and the median is the mean of the two middle values.
 * 
 * > For example, for arr = [2,3,4], the median is 3.
 * > For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
 * 
 * Implement the MedianFinder class:
 * > MedianFinder() initializes the MedianFinder object.
 * > void addNum(int num) adds the integer num from the data stream to the data structure.
 * > double findMedian() returns the median of all elements so far. 
 * Answers within 10-5 of the actual answer will be accepted.
 * 
 * Approach - Priority Queue, Insertion sort: Binary Search
 * 
 * We can use bucket sort, reservoir sampling, segment tree, order statistic trees.
 * 
 * Documentation pending
 */
public class P295FindMedianFromDataStreamHeaps {

	static class MedianFinder {

		PriorityQueue<Integer> maxHeap;
		PriorityQueue<Integer> minHeap;

		public MedianFinder() {
			// (a, b) -> b - a can fail due to Integer Overflow.
			// It can fail if the values are near Integer.MAX_VALUE or Integer.MIN_VALUE.
			// Better to use Integer.compare(b, a)
			maxHeap = new PriorityQueue<>((a, b) -> b - a);
//			minHeap = new PriorityQueue<>((a, b) -> a - b);
//			maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));// slower than (a, b) -> b - a
//			minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a, b));
			minHeap = new PriorityQueue<>(); // Default behavior is ascending order
//			maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // slower than Integer.compare(b, a)
		}

		public void addNum(int num) {
			maxHeap.add(num);
			minHeap.add(maxHeap.poll());
			if (maxHeap.size() < minHeap.size()) {
				maxHeap.add(minHeap.poll());
			}
		}

		public double findMedian() {
			if (maxHeap.size() > minHeap.size()) {
				return maxHeap.peek();
			} else {
				return (double) (maxHeap.peek() + minHeap.peek()) / 2;
			}
		}
	}

	public static void main(String[] args) {
		MedianFinder md = new MedianFinder();

		md.addNum(1);
		md.addNum(2);

		System.out.println(md.findMedian());

		md.addNum(3);
		System.out.println(md.findMedian());
	}

}
