package GeeksForGeeks.Arrays;

import java.util.Arrays;

/*
 * Given an array arr[] of positive numbers, The task is to find the maximum sum of a subsequence 
 * such that no 2 numbers in the sequence should be adjacent in the array.
 */
public class MaximumSumArrayNoTwoAdjacent {

	public static void main(String[] args) {

//		int[] arr = { 5, 5, 10, 100, 10, 5 };
		int[] arr = { 3, 2, 7, 10 };
//		int[] arr = { 5, 3, 4, 11, 2 };
//		int[] arr = { 5 };
//		int[] arr = {};

		int maxSumNoTwoAdjacentRecursive = getMaxSumNoTwoAdjacentRecursive(arr);
		System.out.println("The max sum via recursion bottom up is " + maxSumNoTwoAdjacentRecursive);

		int maxSumNoTwoAdjacentRecursiveTD = getMaxSumNoTwoAdjacentRecursiveTD(arr);
		System.out.println("The max sum via recursion top down is " + maxSumNoTwoAdjacentRecursiveTD);

		int maxSumNoTwoAdjMemoized = getMaxSumNoTwoAdjacentMemoized(arr);
		System.out.println("The max sum via memiozation is " + maxSumNoTwoAdjMemoized);

		int maxSumNoTwoAdjDp2DArr = getMaxSumNoTwoAdjacentDp2DArr(arr);
		System.out.println("The max sum via 2-D DP array is " + maxSumNoTwoAdjDp2DArr);

		int maxSumNoTwoAdjDp1DArr = getMaxSumNoTwoAdjacentDp1DArr(arr);
		System.out.println("The max sum via 1-D DP array is " + maxSumNoTwoAdjDp1DArr);

		int maxSumNoTwoAdjDp2Var = getMaxSumNoTwoAdjacentDp2Var(arr);
		System.out.println("The max sum via 2 states DP is " + maxSumNoTwoAdjDp2Var);
	}

	// The value of current states for ith element
	// depends on previous 2 states of (i-1)th element
	// So instead of 2D array, one can use 2 variables to store i-1 state
	// excl stores the value of max subsequence sum till i-1 excluding arr[i-1]
	// incl stores the value of max subsequence sum till i-1 including it.
	//
	// Time complexity: O(N), where N is the number of elements.
	// Space complexity: O(1), for constant space
	private static int getMaxSumNoTwoAdjacentDp2Var(int[] arr) {
		int N = arr.length;
		if (N == 0) {
			return 0;
		}
		int excl = 0; // dp[i-1][0]
		int incl = arr[0]; // dp[i-1][1]
		for (int i = 1; i < N; i++) {
			int val = arr[i] + excl;
			excl = Math.max(incl, excl);
			incl = val;
		}
		return Math.max(excl, incl);
	}

	private static int getMaxSumNoTwoAdjacentDp1DArr(int[] arr) {
		int N = arr.length;
		if (N == 0) {
			return 0;
		}
		int[] dp = new int[N + 1];
		dp[0] = 0;
		dp[1] = arr[0];// dp and arr are one index apart
		for (int i = 1; i < N; i++) {
			dp[i + 1] = Math.max(arr[i] + dp[i - 1], dp[i]);
		}
		return dp[N];
	}

	// Every element has 2 options:
	// a) if the element is picked then it's neighbours cannot be
	// b) the neighbours can or cannot be picked
	// Max sum till ith index has 2 possibilities: sum includes/excludes arr[i].
	// If arr[i] is included,
	// max sum depends on max subsequence sum till (i-1) element excluding arr[i-1]
	// Else, max sum is max subsequence sum till (i-1) with or without arr[i-1]
	//
	// 2-D array dp[N][2],
	// where dp[i][0] stores maximum subsequence sum till i without arr[i].
	// And dp[i][1] stores maximum subsequence sum with arr[i] included.
	// Time complexity: O(N), where N is the number of elements.
	// Space complexity: O(N), where N is size of dp array.
	private static int getMaxSumNoTwoAdjacentDp2DArr(int[] arr) {

		int N = arr.length;
		if (N == 0) {
			return 0;
		}
		int[][] dp = new int[N][2];
		dp[0][0] = 0;
		dp[0][1] = arr[0];
		for (int i = 1; i < N; i++) {
			dp[i][0] = Math.max(dp[i - 1][1], dp[i - 1][0]);
			dp[i][1] = dp[i - 1][0] + arr[i];
		}
		return Math.max(dp[N - 1][0], dp[N - 1][1]);
	}

	// DP Memoization - Top Down
	// Cache the result since there is overlapping subproblems
	// Time complexity: O(N), where N is the number of elements.
	// Space complexity: O(N), where N is stack space for recursive calls + dp array
	private static int getMaxSumNoTwoAdjacentMemoized(int[] arr) {

		int dp[] = new int[arr.length];
		Arrays.fill(dp, -1);
		return memoizedSum(arr, 0, arr.length, dp);
	}

	private static int memoizedSum(int[] arr, int i, int N, int[] dp) {
		if (i >= N) {
			return 0;
		} else if (dp[i] != -1) {
			return dp[i];
		}
		return dp[i] = Math.max(arr[i] + memoizedSum(arr, i + 2, N, dp), memoizedSum(arr, i + 1, N, dp));
	}

	// Recursion - Bottom Up
	// Each element has 2 choices, either it can be part of the subsequence
	// with the highest sum or it cannot be part of the subsequence.
	// To solve, build all the subsequence of array and find the subsequence
	// with maximum sum such that no two adjacent elements are present in it.
	//
	// 2 Options: a) Choose it and b) Not choose it
	// a) one can select ith element and all values till (i-2)th
	// b) one can select (i-1)th and all the values until that.
	// Time complexity: O(2^N), where N is the number of elements.
	// At each step one can select or not select.
	// Space complexity: O(N), where N is stack space for recursive calls
	private static int getMaxSumNoTwoAdjacentRecursive(int[] arr) {

		return recursiveSum(arr, 0, arr.length);
	}

	private static int recursiveSum(int[] arr, int i, int N) {

		if (i >= N) {
			return 0;
		}
		return Math.max(arr[i] + recursiveSum(arr, i + 2, N), recursiveSum(arr, i + 1, N));
	}

	// Recursion - Top Down
	private static int getMaxSumNoTwoAdjacentRecursiveTD(int[] arr) {

		return recursiveSumTD(arr, arr.length - 1);
	}

	private static int recursiveSumTD(int[] arr, int i) {

		if (i < 0) {
			return 0;
		}
		return Math.max(arr[i] + recursiveSumTD(arr, i - 2), recursiveSumTD(arr, i - 1));
	}

}
