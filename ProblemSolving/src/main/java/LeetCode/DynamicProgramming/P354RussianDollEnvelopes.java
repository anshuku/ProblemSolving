package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
 * P354. Russian Doll Envelopes - Hard
 * 
 * You are given a 2D array of integers envelopes where envelopes[i] = [wi, hi] 
 * represents the width and the height of an envelope.
 * 
 * One envelope can fit into another if and only if both the width and height 
 * of one envelope are greater than the other envelope's width and height.
 * 
 * Return the maximum number of envelopes you can Russian doll (i.e., put one inside the other).
 * 
 * Note: You cannot rotate an envelope.
 * 
 * Approach - DP, Sorting, Binary Search
 */
public class P354RussianDollEnvelopes {

	public static void main(String[] args) {
		int[][] envelopes = { { 5, 4 }, { 6, 4 }, { 6, 7 }, { 2, 3 } };

//		int[][] envelopes = { { 1, 1 }, { 1, 1 }, { 1, 1 } };

//		int[][] envelopes = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 }, { 5, 6 }, { 5, 5 }, { 6, 7 }, { 7, 8 } };

		int envelopesCountList = maxEnvelopesList(envelopes);
		System.out.println("List: The max envelopes which can fit inside one another " + envelopesCountList);

		int envelopesCountArr = maxEnvelopesArr(envelopes);
		System.out.println("Array: The max envelopes which can fit inside one another " + envelopesCountArr);
	}

	// DP with list - selecting envelopes intelligently: Sort + LIS
	// LIS algorithm with dimensional constraints can be applied
	// but to apply LIS, we need to rearrange the data(allowed here).
	// Sort first based on width in increasing order and then on height in
	// decreasing order if the values are equal. For {1,3},{1,4},{1,5},{2,3}
	// Sorted in width, applying LIS in height {3,4,5,3} gives 3 envelopes.
	// But the answer is 1. Sorting height in descending order for same width
	// gives correct result: {5,4,3,3}. This ensures two envelopes which are equal
	// in first dimension can't be put into each other.
	// Time complexity - O(n*logn), both sorting the array and finding the LIS
	// via binary search can be done in n*logn time.
	// Space complexity - O(n) for storing the LIS list.
	private static int maxEnvelopesList(int[][] envelopes) {
		Arrays.sort(envelopes, (a, b) -> a[0] - b[0] != 0 ? a[0] - b[0] : b[1] - a[1]);
		List<Integer> list = new ArrayList<>();
		list.add(envelopes[0][1]);
		int n = envelopes.length;
		for (int i = 1; i < n; i++) {
			if (envelopes[i][1] > list.get(list.size() - 1)) {
				list.add(envelopes[i][1]);
			} else {
				int start = 0;
				int end = list.size() - 1;
				while (start <= end) {
					int mid = start + (end - start) / 2;
					if (envelopes[i][1] <= list.get(mid)) {
						end = mid - 1;
					} else {
						start = mid + 1;
					}
				}
				list.set(start, envelopes[i][1]);
			}
		}
		return list.size();
	}

	// DP with Array - TLE
	// LIS algorithm with dimensional constraints
	// Sort first based on width in increasing order and then on height in
	// decreasing order if the values are equal. For {1,3},{1,4},{1,5},{2,3}
	// Sorted in width, applying LIS in height {3,4,5,3} gives 3 envelopes.
	// But the answer is 1. Sorting height in descending order for same width
	// gives correct result: {5,4,3,3}. This ensures two envelopes which are equal
	// in first dimension can't be put into each other.
	// Time complexity - O(n^2), n*logn for sorting and n^2 for filling dp array.
	// Space complexity - O(n) for storing dp array.
	public static int maxEnvelopesArr(int[][] envelopes) {
		Arrays.sort(envelopes,
				Comparator.comparingInt((int[] a) -> a[0]).thenComparing((a, b) -> Integer.compare(b[1], a[1])));
//		Arrays.sort(envelopes,
//				Comparator.<int[]>comparingInt(a -> a[0]).thenComparing((a, b) -> Integer.compare(b[1], a[1])));
		int n = envelopes.length;
		int maxCount = 0;
		int[] dp = new int[n];
		for (int i = 0; i < n; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (envelopes[i][0] > envelopes[j][0] && envelopes[i][1] > envelopes[j][1]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			maxCount = Math.max(maxCount, dp[i]);
		}
		return maxCount;
	}
}
