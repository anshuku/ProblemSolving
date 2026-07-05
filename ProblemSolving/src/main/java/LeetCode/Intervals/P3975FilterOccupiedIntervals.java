package LeetCode.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * P3975. Filter Occupied Intervals - Medium
 * 
 * You are given a 2D integer array occupiedIntervals, where occupiedIntervals[i] = [starti, endi] 
 * represents a time interval during which you are occupied. Each interval 
 * starts at starti and ends at endi, inclusive. These intervals may overlap.
 * 
 * You are also given two integers freeStart and freeEnd, which 
 * define a free time interval from freeStart to freeEnd, inclusive.
 * 
 * Your task is to merge all occupied intervals that overlap or touch, then remove 
 * all integer points in the free interval from the merged occupied intervals.
 * 
 * Two intervals touch if the second interval starts immediately after the first 
 * one ends. For example, [1, 1] and [2, 2] touch and should be merged into [1, 2].
 * 
 * Return the remaining occupied intervals in sorted order. The returned intervals 
 * must be non-overlapping and must contain the minimum number of intervals possible. 
 * If there are no remaining occupied points, return an empty list.
 * 
 * Approach - Intervals
 */
public class P3975FilterOccupiedIntervals {

	public static void main(String[] args) {
//		int[][] occupiedIntervals = { { 2, 6 }, { 4, 8 }, { 10, 10 }, { 10, 12 }, { 14, 16 } };
//		int freeStart = 7;
//		int freeEnd = 11;

//		int[][] occupiedIntervals = { { 1, 5 }, { 2, 3 } };
//		int freeStart = 3;
//		int freeEnd = 8;

//		int[][] occupiedIntervals = { { 1, 3 } };
//		int freeStart = 2;
//		int freeEnd = 2;

//		int[][] occupiedIntervals = { { 1, 1 }, { 2, 2 } };
//		int freeStart = 100;
//		int freeEnd = 100;

		int[][] occupiedIntervals = { { 31, 40 }, { 75, 92 }, { 44, 46 }, { 50, 51 }, { 43, 47 } };
		int freeStart = 45;
		int freeEnd = 52;

		List<List<Integer>> filteredIntervalsShort = filterOccupiedIntervalsShort(occupiedIntervals, freeStart,
				freeEnd);
		System.out.println("Short: The filtered intervals are: " + filteredIntervalsShort);

		List<List<Integer>> filteredIntervalsLong = filterOccupiedIntervalsLong(occupiedIntervals, freeStart, freeEnd);
		System.out.println("Long: The filtered intervals are: " + filteredIntervalsLong);
	}

	// Merging + Deleting intervals
	// The intervals in the occupiedIntervals may overlap or even be adjacent.
	// Before removing the occupied portion [freeStart, freeEnd], we first merge all
	// overlapping intervals so that each occupied segment is represented exactly
	// once.
	// After merging: If an interval lies completely outside the free range, we keep
	// it unchanged. If an interval overlaps the free range, we remove the
	// overlapping portion and keep only the remaining part(s).
	// An interval can contribute: nothing(completely inside the free range), one
	// interval(partial overlap), or two intervals (if the free range splits it into
	// 2 parts, although with a continuous interval this implementation produces
	// left and / or right remainders depending on overlap).
	// Approach: 1. We sort the intervals based on starting point which allows
	// overlapping intervals to appear consecutively. 2. Merge overlapping
	// intervals by traversing sorted intervals. If the current interval does not
	// overlap the previous merged interval (previousEnd + 1 < currentStart), start
	// a new merged interval. Otherwise, extend the previous interval's ending point
	// based on max of previous interval's end point and current interval's end
	// point. Here, +1 merges adjacent intervals. 3. Remove the occupied/free
	// range. For every merged interval: Case 1: Complete outside [freeStart,
	// freeEnd], we keep it. The intervals may lie completely before the free
	// interval or completely after free interval. Case 2: Overlaps the left side,
	// or if(s < freeStart), we keep (s, freeStart - 1). Case 3: Overlaps the right
	// side, or if(e > freeEnd), we keep (freeEnd + 1, e). Case 4: Completely inside
	// free range, nothing remains. No interval is added. Case 5: Completely
	// occupies the free interval, we add 2 intervals (s, freeStart - 1) and
	// (freeEnd + 1, e). Sorting ensures intervals are porcessed in increasing order
	// of their start time. Merging combines every overlapping or adjacent interval
	// into 1 maximal interval. Therefore, every occupied point belongs to exactly 1
	// interval. For each merged interval: If it does not intersect [freeStart,
	// freeEnd], the interval remains unchanged and is directly included. If it
	// intersects the free range, the algo removes the overlapping portion. Any
	// remaining left portion [start, freeStart-1] and/or right portion [freeEnd+1,
	// end] are exactly the occupied points outside the free range. Since, every
	// merged interval is processed independently and every occupied point belongs
	// to exactly one merged interval, every occupied point outside the free range
	// appears exactly once in the output.
	// Time complexity - O(nlogn), for sorting it takes O(nlogn), for merging O(n),
	// for filtering intervals another O(n).
	// Space complexity - O(n), for the merged intervals.
	private static List<List<Integer>> filterOccupiedIntervalsShort(int[][] occupiedIntervals, int freeStart,
			int freeEnd) {
		Arrays.sort(occupiedIntervals, (a, b) -> Integer.compare(a[0], b[0]));

//		for (int[] interval : occupiedIntervals) {
//			System.out.println(Arrays.toString(interval));
//		}

		List<int[]> mergedIntervals = new ArrayList<>();

//		for (int[] interval : occupiedIntervals) {
//			if (mergedIntervals.size() > 0 && interval[0] <= mergedIntervals.getLast()[1] + 1) {
//				mergedIntervals.getLast()[1] = Math.max(mergedIntervals.getLast()[1], interval[1]);
//			} else {
//				mergedIntervals.add(interval);
//			}
//		}

		for (int[] interval : occupiedIntervals) {
			if (mergedIntervals.size() == 0 || interval[0] > mergedIntervals.getLast()[1] + 1) {
				mergedIntervals.add(interval);
			} else {
				mergedIntervals.getLast()[1] = Math.max(mergedIntervals.getLast()[1], interval[1]);
			}
		}

//		for (int[] interval : mergedIntervals) {
//			System.out.println(Arrays.toString(interval));
//		}
//
//		System.out.println("here");

		List<List<Integer>> merged = new ArrayList<>();

		for (int[] interval : mergedIntervals) {
			int a = interval[0];
			int b = interval[1];

			if (b < freeStart || a > freeEnd) {
				merged.add(Arrays.asList(a, b));
			} else {
				if (a < freeStart) {
					merged.add(Arrays.asList(a, freeStart - 1));
				}
				if (b > freeEnd) {
					merged.add(Arrays.asList(freeEnd + 1, b));
				}
			}
		}

		return merged;
	}

	public static List<List<Integer>> filterOccupiedIntervalsLong(int[][] occupiedIntervals, int freeStart,
			int freeEnd) {
		List<int[]> mergedIntervals = new ArrayList<>();

		Arrays.sort(occupiedIntervals, (a, b) -> a[0] - b[0]);

		for (int[] interval : occupiedIntervals) {
			if (mergedIntervals.size() > 0 && interval[0] <= mergedIntervals.getLast()[1] + 1) {
				mergedIntervals.getLast()[1] = Math.max(interval[1], mergedIntervals.getLast()[1]);
			} else {
				mergedIntervals.add(interval);
			}
		}

		List<List<Integer>> merged = new ArrayList<>();

		for (int[] interval : mergedIntervals) {
			int a = interval[0];
			int b = interval[1];

			if (b < freeStart || a > freeEnd) {
				List<Integer> list = new ArrayList<>();
				list.add(a);
				list.add(b);
				merged.add(list);
			} else if (a >= freeStart && b <= freeEnd) {
				continue;
			} else if (a < freeStart && b <= freeEnd) {
				List<Integer> list = new ArrayList<>();
				list.add(a);
				list.add(freeStart - 1);
				merged.add(list);
			} else if (a >= freeStart && b > freeEnd) {
				List<Integer> list = new ArrayList<>();
				list.add(freeEnd + 1);
				list.add(b);
				merged.add(list);
			} else if (a < freeStart && b > freeEnd) {
				List<Integer> list1 = new ArrayList<>();
				list1.add(freeEnd + 1);
				list1.add(b);
				merged.add(list1);

				List<Integer> list2 = new ArrayList<>();
				list2.add(a);
				list2.add(freeStart - 1);
				merged.add(list2);
			}
		}
		return merged;
	}

}
