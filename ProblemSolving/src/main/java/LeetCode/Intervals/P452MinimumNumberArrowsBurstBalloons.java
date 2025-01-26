package LeetCode.Intervals;

import java.util.Arrays;

/*
 * P452. Minimum Number of Arrows to Burst Balloons - Medium
 * 
 * There are some spherical balloons taped onto a flat wall that represents the XY-plane. 
 * The balloons are represented as a 2D integer array points where points[i] = [xstart, xend] 
 * denotes a balloon whose horizontal diameter stretches between xstart and xend. 
 * You do not know the exact y-coordinates of the balloons.
 * 
 * Arrows can be shot up directly vertically (in the positive y-direction) from different 
 * points along the x-axis. A balloon with xstart and xend is burst by an arrow shot at 
 * x if xstart <= x <= xend. There is no limit to the number of arrows that can be shot. 
 * A shot arrow keeps traveling up infinitely, bursting any balloons in its path.
 * 
 * Given the array points, return the minimum number of 
 * arrows that must be shot to burst all balloons.
 * 
 * Approach - Sorting, Greedy
 * 
 * Greed - It looks like Find minimum number of .. to do ..
 * Find maximum number of .. to fit conditions
 * Greedy algo involves picking the locally optimal move
 * to find the globally optimal solution.
 */
public class P452MinimumNumberArrowsBurstBalloons {

	public static void main(String[] args) {

//		int[][] points = { { 10, 16 }, { 2, 8 }, { 1, 6 }, { 7, 12 } };

//		int[][] points = { { 1, 2 }, { 3, 4 }, { 5, 6 }, { 7, 8 } };

		int[][] points = { { -2147483646, -2147483645 }, { 2147483646, 2147483647 } };

		int minShots = findMinArrowShots(points);

		System.out.println("Minimum arrows to burst all balloons: " + minShots);
	}

	// Greedy - It looks like Find minimum number of .. to do ..
	// Find maximum number of .. to fit conditions
	// Sort the points array by end points.
	// Start with 1st end point and count = 1 to burst it.
	// For i = 1 till n-1 onwards, there are 2 possibilities
	// 1. The start coordinate is smaller than current end coordinate at prev:
	// These balloons can be burst together with current one.
	// 2. The start coordinate is larger than current end coordinate at prev:
	// Increase the count by 1 and change the prev pointer to i.
	// Time complexity - O(nlogn) for sorting the points array
	// Space complexity - O(logn) which uses a variant of Quick sort in java
	public static int findMinArrowShots(int[][] points) {
		int n = points.length;
		// comparator with a[1] - b[1] can't prevent int overflow
		Arrays.sort(points, (a, b) -> a[1] - b[1]); // give 2 for extreme ends
		// Use Integer.compare(a, b) which internally uses >, < and =
		// The method returns -ve if a[1] < b[1]
		// 0 if a[1] == b[1] and +ve if a[1] > b[1]
		Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
		int prev = 0;
		int count = 1;
		for (int i = 1; i < n; i++) {
			if (points[prev][1] < points[i][0]) {
				prev = i;
				count++;
			}
		}
		return count;
	}

}
