package LeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P57InsertInterval {

	public static void main(String[] args) {

		int[][] intervals = { { 1, 3 }, { 6, 9 } };
		int[] newInterval = { 2, 5 };
		int[][] mergedInterval = insertBinarySearch(intervals, newInterval);

		for (int i = 0; i < mergedInterval.length; i++) {
			for (int j = 0; j < mergedInterval[0].length; j++) {
				System.out.print(mergedInterval[i][j] + " ");
			}
			System.out.println();
		}

	}

	public static int[][] insertBinarySearch(int[][] intervals, int[] newInterval) {
		int n = intervals.length;
		int left = 0, right = n - 1;
		int target = newInterval[0];

		while (left <= right) {
			int mid = (left + right) / 2;
			if (intervals[mid][0] < target) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		List<int[]> list = new ArrayList<>();
		int i = 0;
		for (; i < left; i++) {
			list.add(intervals[i]);
		}
		list.add(newInterval);
		for (i = left; i < n; i++) {
			list.add(intervals[i]);
		}

		List<int[]> merged = new ArrayList<>();
		for (int[] interval : list) {
			if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
				merged.add(interval);
			} else {
				merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]);
			}
		}
		return merged.toArray(new int[merged.size()][2]);
	}

	public static int[][] insert(int[][] intervals, int[] newInterval) {
		int i = 0, n = intervals.length;
		List<int[]> result = new ArrayList<>();

		while (i < n && intervals[i][1] < newInterval[0]) {
			result.add(intervals[i]);
			i++;
		}
		while (i < n && intervals[i][0] <= newInterval[1]) {
			newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
			newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
			i++;
		}
		result.add(newInterval);
		while (i < n) {
			result.add(intervals[i]);
			i++;
		}

		return result.toArray(new int[result.size()][2]);

	}

	// check where the 1st val of newInterval is present in intervals[] and create a
	// new array
	public static int[][] insertBruteForce(int[][] intervals, int[] newInterval) {

		if (intervals.length == 0) {
			int[][] newInt = new int[1][2];
			newInt[0][0] = newInterval[0];
			newInt[0][1] = newInterval[1];
			return newInt;
		}
		int[][] mergedInterval = new int[intervals.length][2];

		if (newInterval[1] < intervals[0][0]) {
			mergedInterval = new int[intervals.length + 1][2];
			mergedInterval[0][0] = newInterval[0];
			mergedInterval[0][1] = newInterval[1];
			for (int i = 0; i < intervals.length; i++) {
				mergedInterval[i + 1][0] = intervals[i][0];
				mergedInterval[i + 1][1] = intervals[i][1];
			}
			return mergedInterval;
		}
		if (newInterval[0] > intervals[intervals.length - 1][1]) {
			mergedInterval = new int[intervals.length + 1][2];
			for (int i = 0; i < intervals.length; i++) {
				mergedInterval[i][0] = intervals[i][0];
				mergedInterval[i][1] = intervals[i][1];
			}
			mergedInterval[intervals.length][0] = newInterval[0];
			mergedInterval[intervals.length][1] = newInterval[1];
			return mergedInterval;
		}

		int i = 0;
		int k = 0;
		for (; i < intervals.length; i++) {
			if (i == 0 && (newInterval[0] < intervals[i][0] && newInterval[1] >= intervals[i][0]
					&& newInterval[1] <= intervals[i][1])) {
				mergedInterval[k][0] = newInterval[0];
				mergedInterval[k++][1] = intervals[i][1];
			} else if (i == 0 && newInterval[0] < intervals[i][0] && newInterval[1] > intervals[i][1]) {
				mergedInterval[k][0] = newInterval[0];
				mergedInterval[k][1] = newInterval[1];
				i++;
				break;
			} else if (i == (intervals.length - 1) && (newInterval[0] >= intervals[i][0]
					&& newInterval[0] <= intervals[i][1] && newInterval[1] > intervals[i][1])) {
				mergedInterval[k][0] = intervals[i][0];
				mergedInterval[k++][1] = newInterval[1];
			} else if (newInterval[0] >= intervals[i][0] && newInterval[0] <= intervals[i][1]
					&& newInterval[1] > intervals[i][0] && newInterval[1] <= intervals[i][1]) {
				mergedInterval[k][0] = intervals[i][0];
				mergedInterval[k++][1] = intervals[i][1];
			} else if (newInterval[0] >= intervals[i][0] && newInterval[0] <= intervals[i][1]
					&& newInterval[1] > intervals[i][1]) {
				mergedInterval[k][0] = intervals[i][0];
				mergedInterval[k][1] = newInterval[1];
				i++;
				break;
			} else if (i > 0 && newInterval[0] > intervals[i - 1][0] && newInterval[0] < intervals[i][0]) {
				mergedInterval[k][0] = newInterval[0];
				mergedInterval[k++][1] = Math.max(newInterval[1], intervals[i][1]);
				i++;
				break;
			} else {
				mergedInterval[k][0] = intervals[i][0];
				mergedInterval[k++][1] = intervals[i][1];
			}
		}
		for (; i < intervals.length; i++) {
			if (mergedInterval[k][1] >= intervals[i][0] && mergedInterval[k][1] <= intervals[i][1]) {
				mergedInterval[k][1] = Math.max(mergedInterval[k][1], intervals[i][1]);
				k++;
				i++;
				break;
			} else if (mergedInterval[k][1] < intervals[i][0]) {
				k++;
				break;
			}
		}
		for (; i < intervals.length; i++) {
			mergedInterval[k][0] = intervals[i][0];
			mergedInterval[k++][1] = intervals[i][1];
		}
		if (k == 0) {
			k = 1;
		}

		return Arrays.copyOf(mergedInterval, k);
	}

}
