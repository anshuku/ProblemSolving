package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * P1964. Find the Longest Valid Obstacle Course at Each Position - Hard
 * 
 * You want to build some obstacle courses. You are given a 0-indexed integer array 
 * obstacles of length n, where obstacles[i] describes the height of the ith obstacle.
 * 
 * For every index i between 0 and n - 1 (inclusive), find the 
 * length of the longest obstacle course in obstacles such that:
 * 
 * - You choose any number of obstacles between 0 and i inclusive.
 * - You must include the ith obstacle in the course.
 * - You must put the chosen obstacles in the same order as they appear in obstacles.
 * - Every obstacle (except the first) is taller than or the same height as the obstacle immediately before it.
 * 
 * Return an array ans of length n, where ans[i] is the length of 
 * the longest obstacle course for index i as described above.
 * 
 * Approach - DP
 */
public class P1964FindLongestValidObstacleCourseEachPosition {

	public static void main(String[] args) {
		int[] obstacles = { 1, 2, 3, 2 };

//		int[] obstacles = { 2, 2, 1 };

//		int[] obstacles = { 3, 1, 5, 6, 4, 2 };

		int[] longestCourse = longestObstacleCourseAtEachPosition(obstacles);
		System.out.println("The longest obstacle course at each position: " + Arrays.toString(longestCourse));
	}

	// Greedy + Binary Search
	// The longest course at index i is determined by two factors:
	// a) Current obstacle[i] b) The longest course before index i whose last
	// obstacle is <= obstacle[i]. Combining these two gives longest[i].
	// Store all the previous obstacle courses we've met before index i.
	// To store longest length at i, select the obstacle course, where the
	// obstacle height <= obstacles[i] and append current 1 to get longer length.
	// Greedily chose the longest one gives the longest course at i.
	// For 1 4 6 2 3, 1 -> 4 -> 6 and 1 -> 2 -> 3, we can't append 5 to 1->4->6
	// So it's always better to record the obstacle with lowest height.
	// We don't care about the exact course but only the height of last obstacle.
	// Use an array lis, to record the height of the shortest ending obstacle for
	// courses of each length: lis[i] is height of shortest ending obstacle for
	// for course of length i+1. lis[4]=7, lowest end of course with length 4 is 7.
	// At each i, binary search over lis to find insertion position of obstacles[i].
	// Here, it's not strictly increasing binary search where we find and replace
	// the first index where obstacles[i] <= list.get(mid). It's a non decreasing
	// obstacle course meaning each element can be equal to greater than the
	// previous. Here, we need to find the last position where obstacles[i] >=
	// list.get(mid) and place the current obtacle after that. Upper bound for <=.
	// Time complexity - O(n*logn) to update lis with binary search in worst case.
	// Space complexity - O(n) for storing lis.
	public static int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
		int n = obstacles.length;
		int[] longest = new int[n];
		longest[0] = 1;
		// List stores the smallest height of obstacle for length i+1.
		List<Integer> list = new ArrayList<>();
		list.add(obstacles[0]);
		for (int i = 1; i < n; i++) {
			int start = 0;
			int end = list.size() - 1;
			while (start <= end) {
				int mid = start + (end - start) / 2;
				// non decreasing requires <
				if (obstacles[i] < list.get(mid)) {
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			}
			if (start == list.size()) {
				list.add(obstacles[i]);
			} else {
				list.set(start, obstacles[i]);
			}
			longest[i] = start + 1;
		}
		return longest;
	}
}
