package LeetCode.Stack;

import java.util.Stack;

/*
 * P84. Largest Rectangle in Histogram - Hard
 * 
 * Given an array of integers heights representing the histogram's bar height where 
 * the width of each bar is 1, return the area of the largest rectangle in the histogram.
 * 
 * Approach - Stack, Divide and Conquer: DP
 * 
 * Documentation pending
 */
public class P84LargestRectangleInHistogram {

	public static void main(String[] args) {
		int[] heights = { 2, 1, 5, 6, 2, 3 };
//		int[] heights = { 2, 4 };

		int largestAreaStack = largestRectangleAreaStack(heights);
		System.out.println("Stack: The largest rectangle area is: " + largestAreaStack);

		int largestAreaDAndC = largestRectangleAreaDivideAndConquer(heights);
		System.out.println("Divide and Conquer: The largest rectangle area is: " + largestAreaDAndC);

		int largestAreaBFOpt = largestRectangleAreaBruteForceOptimized(heights);
		System.out.println("Brute Force Optimized: The largest rectangle area is: " + largestAreaBFOpt);

		int largestAreaBF = largestRectangleAreaBruteForce(heights);
		System.out.println("Brute Force: The largest rectangle area is: " + largestAreaBF);
	}

	// Monotonic Stack
	// Time complexity - O(n), as n elements are pushed and popped.
	// Space complexity - O(n), for stack
	private static int largestRectangleAreaStack(int[] heights) {
		int n = heights.length;

		Stack<Integer> stack = new Stack<>();
		stack.push(-1); // marks the end.

		int maxArea = 0;
		for (int i = 0; i < n; i++) {
			while (stack.peek() != -1 && heights[i] <= heights[stack.peek()]) {
				int height = heights[stack.pop()];
				maxArea = Math.max(maxArea, height * (i - stack.peek() - 1));
			}
			stack.push(i);
		}
		while (stack.peek() != -1) {
			int height = heights[stack.pop()];
			maxArea = Math.max(maxArea, height * (n - stack.peek() - 1));
		}
		return maxArea;
	}

	// Using dp causes MLE - Memory Limit Exceeded
	static int[][] dp;

	// Divide and Conquer - without DP TLE
	// Can be improved with a Segment Tree.
	// Time complexity - Average Case: O(n*logn), Worst Case: O(n^2), if the numbers
	// are sorted, we don't gain the advantage of divide and conquer. The time
	// complexity can be reduce to O(nlogn) if we use Segment tree which takes logn
	// for a totoal of n times.
	// Space complexity - O(n), recursion with worst case depth n(same for segment
	// tree).
	private static int largestRectangleAreaDivideAndConquer(int[] heights) {
		int n = heights.length;
		dp = new int[n][n];
		return calculateArea(heights, 0, n - 1);
	}

	private static int calculateArea(int[] heights, int start, int end) {
		if (start > end) {
			return 0;
		}

		if (dp[start][end] != 0) {
			return dp[start][end];
		}

		int minIndex = start;

		for (int i = start; i <= end; i++) {
			if (heights[i] < heights[minIndex]) {
				minIndex = i;
			}
		}

		return dp[start][end] = Math.max(heights[minIndex] * (end - start + 1),
				Math.max(calculateArea(heights, start, minIndex - 1), calculateArea(heights, minIndex + 1, end)));
	}

	// Brute Force: Optimized - TLE
	private static int largestRectangleAreaBruteForceOptimized(int[] heights) {
		int n = heights.length;
		int maxArea = 0;

		for (int i = 0; i < n; i++) {
			int minHeight = Integer.MAX_VALUE;
			for (int j = i; j < n; j++) {
				minHeight = Math.min(minHeight, heights[j]);
				maxArea = Math.max(maxArea, minHeight * (j - i + 1));
			}
		}
		return maxArea;
	}

	// Brute Force - TLE
	public static int largestRectangleAreaBruteForce(int[] heights) {
		int n = heights.length;
		int maxArea = 0;

		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				int minHeight = Integer.MAX_VALUE;
				for (int k = i; k <= j; k++) {
					minHeight = Math.min(minHeight, heights[k]);
				}
				maxArea = Math.max(maxArea, minHeight * (j - i + 1));
			}
		}
		return maxArea;
	}
}
