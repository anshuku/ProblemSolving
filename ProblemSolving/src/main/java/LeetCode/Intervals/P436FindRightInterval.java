package LeetCode.Intervals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*
 * P436. Find Right Interval - Medium
 * 
 * You are given an array of intervals, where intervals[i] = [starti, endi] and each starti is unique.
 * 
 * The right interval for an interval i is an interval j such that 
 * startj >= endi and startj is minimized. Note that i may equal j.
 * 
 * Return an array of right interval indices for each interval i. If 
 * no right interval exists for interval i, then put -1 at index i.
 * 
 * Approach - Binary Search, TreeMap, Sorting
 */
public class P436FindRightInterval {

	public static void main(String[] args) {
//		int[][] intervals = { { 1, 2 } };
		int[][] intervals = { { 3, 4 }, { 2, 3 }, { 1, 2 } };
//		int[][] intervals = { { 1, 4 }, { 2, 3 }, { 3, 4 } };

		int[] resultBS = findRightIntervalBS(intervals);
		System.out.println("Binary Search: The right interval indices are: " + Arrays.toString(resultBS));

		intervals = new int[][] { { 3, 4 }, { 2, 3 }, { 1, 2 } };
		int[] resultTreeMap = findRightIntervalTreeMap(intervals);
		System.out.println("Tree Map: The right interval indices are: " + Arrays.toString(resultTreeMap));

		int[] result2Arrays = findRightInterval2Arrays(intervals);
		System.out.println("2 Arrays: The right interval indices are: " + Arrays.toString(result2Arrays));
	}

	// Binary Search
	// Here binary search can be used since we can sort the intervals based on start
	// index. Also we can use binary search to find the start index of the interval
	// which is just >= end index of current interval. If such interval is found we
	// obtain it's index from hashmap and store it in appropriate entry. If the
	// interval is not found we put -1 at the corresponding entry.
	// Time complexity - O(n*logn) for sorting the intervals and doing binary search
	// for n entries which takes O(logn) time.
	// Space complexity - O(n) for the map.
	public static int[] findRightIntervalBS(int[][] intervals) {
		int n = intervals.length;
		int[] result = new int[n];
		// Map is used to populate original array index
		Map<int[], Integer> map = new HashMap<>();
		int idx = 0;
		for (int[] interval : intervals) {
			map.put(interval, idx++);
		}

		// Sort the intervals based on start to use binary search
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

		// Iterate through the intervals and find the right interval indices
		// It's a binary search and also lower bound as we need to find min start(j)
		// and all starts are unique and in ascending order post sorting.
		for (int i = 0; i < n; i++) {
			// We can start from index i as start <= end
			int start = i;
			int end = n - 1;
			// Key is the end for the current interval which is compared with all the
			// interval's start value via binary search.
			int key = intervals[i][1];
			while (start <= end) {
				int mid = start + (end - start) / 2;
				// search insert position
				if (intervals[mid][0] == key) {
					start = mid;
					break;
				} else if (intervals[mid][0] < key) {
					start = mid + 1;
				} else {
					end = mid - 1;
				}
				// lower bound can also work as we get the required index in start
//				if (intervals[mid][0] < key) {
//					start = mid + 1;
//				} else {
//					end = mid - 1;
//				}
			}
			if (start == n) {
				result[map.get(intervals[i])] = -1;
			} else {
				result[map.get(intervals[i])] = map.get(intervals[start]);
			}

		}
		return result;
	}

	// TreeMap - without sorting separately and without binary search
	// TreeMap is a self balanced binary search tree or a Red Black tree.
	// it also remains sorted in order of its keys. We store the start point of the
	// interval as the key and the index of the interval as the value.
	// We iterate over intervals and get the end point of it. We use
	// TreeMap.ceilingEntry(end_point) to either get the entry where the start index
	// is >= end point of ith(current) interval or null if no such key is found.
	// If we obtain the value from the entry we store it in result else store -1.
	// Time complexity - O(n*logn), to insert an entry into tree map takes O(logn)
	// times and we have n such elements. TreeMap.ceilingEntry() takes O(logn) time
	// and n searches are done.
	// Space complexity - O(n) for using treemap.
	private static int[] findRightIntervalTreeMap(int[][] intervals) {
		TreeMap<Integer, Integer> map = new TreeMap<>();
		int idx = 0;
		for (int[] interval : intervals) {
			map.put(interval[0], idx++);
		}
		int n = intervals.length;
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			int end = intervals[i][1];
			Map.Entry<Integer, Integer> entry = map.ceilingEntry(end);
			if (entry != null) {
				result[i] = entry.getValue();
			} else {
				result[i] = -1;
			}
		}
		return result;
	}

	// 2 Arrays without binary search
	// We maintain 2 arrays intervals which is sorted based on start points and
	// endIntervals which is sorted based on the end points. We pick intervals one
	// by one from endIntervals and then we scan the intervals in intervals array
	// from left to right. If we get the index j from intervals array based on
	// start(j) >= end(i) we know for the remaining intervals in endIntervals will
	// have at least j as the correct index. For next (i+1)th interval from the
	// endIntervals array, we can start scanning from jth index onwards. This is
	// because end point for endIntervals[i+1] is bigger than endIntervals[i] and no
	// intervals from intervals[k] such that 0 < k < j satisfied the criteria
	// earlier, and hence this won't happen for endIntervals[i+1] as well. If we
	// reach the end of array j = intervals.length we put -1 at the right entry.
	// This will happen for remaining elements of endIntervals array as there end
	// points are even larger.
	// Time complexity - O(n*logn), for sorting we take O(n*logn) time and for
	// searching the appropriate intervals it takes O(n) as the intervals are
	// scanned only once.
	// Space complexity - O(n) for map and endIntervals.
	private static int[] findRightInterval2Arrays(int[][] intervals) {
		int n = intervals.length;
		int[] result = new int[n];
		Map<int[], Integer> map = new HashMap<>();
		int idx = 0;
		for (int[] interval : intervals) {
			map.put(interval, idx++);
		}
		int[][] endIntervals = Arrays.copyOf(intervals, n);
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		Arrays.sort(endIntervals, (a, b) -> a[1] - b[1]);

		int j = 0;
		for (int i = 0; i < n; i++) {
			int end = endIntervals[i][1];
			while (j < n && end > intervals[j][0]) {
				j++;
			}
			if (j < n) {
				result[map.get(endIntervals[i])] = map.get(intervals[j]);
			} else {
				result[map.get(endIntervals[i])] = -1;
			}
		}
		return result;
	}
}
