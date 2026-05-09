package LeetCode.HeapPriorityQueue.P295FindMedianFromDataStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
 */
public class P295FindMedianFromDataStreamListSort {

	static class MedianFinder {

		List<Integer> list;

		public MedianFinder() {
			list = new ArrayList<>();
		}

		public void addNum(int num) {
			list.add(num);
		}

		public double findMedian() {
			int n = list.size();
			if (n % 2 == 1) {
				return list.get(n / 2);
			}
			return (double) (list.get((n / 2) - 1) + list.get(n / 2)) / 2;
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
