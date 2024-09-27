package GeeksForGeeks.CircularArrays;

import java.util.Arrays;

/*
 * Given a circular array containing of positive integers value. The task is to find the maximum sum 
 * of a subsequence with the constraint that no 2 numbers in the sequence should be adjacent in the array.
 * 
 * In this problem the arr elements are arranged in circular fashion 
 * so the 1st and the last element are also adjacent. So we can not take first and last element together 
 * so we can take either of the element1 or last element.
 * So, first we can take element 1 and exclude last element and calculate the maximum sum subsequence 
 * where no two selected elements are adjacent and then also we can calculate another answer 
 * by excluding 1st element and including last element. And we will take maximum of two.
 */
public class MaximumSumCircularArrayNoTwoAdjacent {

	public static void main(String[] args) {

		int[] circularArr = { 1, 2, 3, 1 };
//		int[] circularArr = { 1, 2, 3, 4, 5, 1 };
//		int[] circularArr = { 3, 2, 7, 10 };
//		int[] circularArr = { 5, 3, 4, 11, 2 };
//		int[] circularArr = { 1 };
//		int[] circularArr = {};

		int maxSumRecursive = maxSumNo2AdjCircularRecursive(circularArr);

		System.out.println("The max sum with recursion is " + maxSumRecursive);

		int maxSumMemoized = maxSumNo2AdjCircularMemoized(circularArr);

		System.out.println("The max sum with memoization is " + maxSumMemoized);

		int maxSumDpArrForLoops = maxSumNo2AdjCircularDpArrLoops(circularArr);

		System.out.println("The max sum with DP array and 2 for loops is " + maxSumDpArrForLoops);

		int maxSumDp2DArr = maxSumNo2AdjCircularDp2DArr(circularArr);

		System.out.println("The max sum with 2D DP array is " + maxSumDp2DArr);

		int maxSumDp1DArr = maxSumNo2AdjCircularDp1DArr(circularArr);

		System.out.println("The max sum with 1D DP array is " + maxSumDp1DArr);

		int maxSumDpVar = maxSumNo2AdjCircularDpVars(circularArr);

		System.out.println("The max sum with DP 2 variables is " + maxSumDpVar);
	}

	// Time Complexity: O(N)
	// Space Complexity: O(1)
	private static int maxSumNo2AdjCircularDpVars(int[] circularArr) {
		int N = circularArr.length;
		if (N == 0) {
			return 0;
		}
		if (N == 1) {
			return circularArr[0];
		}
		int with1stWithoutN = getMaxSumDp2Var(circularArr, 0, N - 1);
		int without1stWithN = getMaxSumDp2Var(circularArr, 1, N);
		return Math.max(with1stWithoutN, without1stWithN);
	}

	private static int getMaxSumDp2Var(int[] circularArr, int l, int n) {
		int excl = 0;
		int incl = circularArr[l];
		for (int i = l + 1; i < n; i++) {
			int temp = excl;
			excl = Math.max(excl, incl);
			incl = temp + circularArr[i];
		}
		return Math.max(excl, incl);
	}

	// Time Complexity: O(N)
	// Space Complexity: O(N)
	private static int maxSumNo2AdjCircularDp1DArr(int[] circularArr) {
		int N = circularArr.length;
		if (N == 0) {
			return 0;
		}
		if (N == 1) {
			return circularArr[0];
		}
		int[] dp = new int[N + 1];
		int with1stWithoutN = getMaxSum1DDpArray(circularArr, 0, N - 1, dp);
		dp = new int[N + 1]; // Maybe not mandatory
		int without1stWithN = getMaxSum1DDpArray(circularArr, 1, N, dp);
		return Math.max(with1stWithoutN, without1stWithN);
	}

	private static int getMaxSum1DDpArray(int[] circularArr, int l, int n, int[] dp) {

		dp[l] = 0;
		dp[l + 1] = circularArr[l];
		for (int i = l + 1; i < n; i++) {
			dp[i + 1] = Math.max(dp[i - 1] + circularArr[i], dp[i]);
		}
		return dp[n];
	}

	// Treat the circular arrays as 2 arrays:
	// 1 array from 0th to (n-2)nd index and another from 1st to (n-1)st index
	// solve them separately and find the max of two
	// Time Complexity: O(N)
	// Space Complexity: O(N)
	private static int maxSumNo2AdjCircularDp2DArr(int[] circularArr) {
		int N = circularArr.length;
		if (N == 0) {
			return 0;
		}
		if (N == 1) {
			return circularArr[0];
		}
		int[][] dp = new int[N][2];
		int with1stWithoutN = getMaxSum2DDpArray(circularArr, 0, N - 1, dp);
		dp = new int[N][2]; // Maybe not mandatory
		int without1stWithN = getMaxSum2DDpArray(circularArr, 1, N, dp);
		return Math.max(with1stWithoutN, without1stWithN);
	}

	private static int getMaxSum2DDpArray(int[] circularArr, int l, int n, int[][] dp) {

		dp[l][0] = 0;
		dp[l][1] = circularArr[l];
		for (int i = l + 1; i < n; i++) {
			dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
			dp[i][1] = dp[i - 1][0] + circularArr[i];
		}
		return Math.max(dp[n - 1][0], dp[n - 1][1]);
	}

	// Time Complexity: O(N^2)
	// Space Complexity: O(N)
	private static int maxSumNo2AdjCircularDpArrLoops(int[] circularArr) {

		int N = circularArr.length;

		if (N == 1) {
			return circularArr[0];
		}

		return Math.max(getMaxSum1(circularArr, N), getMaxSum2(circularArr, N));
	}

	// Calculates sum from 0 to (n-2)
	private static int getMaxSum1(int[] circularArr, int n) {

		int[] dp = new int[n];

		int max = 0;

		for (int i = 0; i < n - 1; i++) {
			dp[i] = circularArr[i];

			if (max < circularArr[i]) {
				max = circularArr[i];
			}
		}

		// Iterate from 2nd to (n-2) position
		for (int i = 2; i < n - 1; i++) {

			// Traverse all pairs in bottom up approach
			for (int j = 0; j < i - 1; j++) {

				if (dp[i] < dp[j] + circularArr[i]) {
					dp[i] = dp[j] + circularArr[i];
					if (max < dp[i]) {
						max = dp[i];
					}
				}

			}
		}
		return max;
	}

	private static int getMaxVal(int[] circularArr, int[] dp, int l, int n) {
		int max = 0;
		for (int i = l; i < n; i++) {
			dp[i] = circularArr[i];
			if (max < circularArr[i]) {
				max = circularArr[i];
			}
		}
		return max;
	}

	// Calculates sum from 1 to (n-1)
	private static int getMaxSum2(int[] circularArr, int n) {

		int[] dp = new int[n];

		int max = 0;

		for (int i = 1; i < n; i++) {
			dp[i] = circularArr[i];

			if (max < circularArr[i]) {
				max = circularArr[i];
			}
		}

		// Iterate from 3rd to (n-1) position
		for (int i = 3; i < n; i++) {
			// Traverse all pairs in bottom up approach
			for (int j = 1; j < i - 1; j++) {

				if (dp[i] < dp[j] + circularArr[i]) {
					dp[i] = dp[j] + circularArr[i];
					if (max < dp[i]) {
						max = dp[i];
					}
				}
			}
		}
		return max;
	}

	// Time Complexity: O(N) or O(2^N)
	// Space Complexity: O(N)
	private static int maxSumNo2AdjCircularMemoized(int[] circularArr) {
		int n = circularArr.length;
		if (n == 1) {
			return circularArr[0];
		}
		int[] dp = new int[n];
		Arrays.fill(dp, -1);
		int with1stWithoutN = maxSumMemoized(circularArr, 0, n - 2, dp);
		Arrays.fill(dp, -1); // Mandatory
		int without1stWithN = maxSumMemoized(circularArr, 1, n - 1, dp);
		return Math.max(with1stWithoutN, without1stWithN);
	}

	private static int maxSumMemoized(int[] circularArr, int i, int n, int[] dp) {
		if (n < i) {
			return 0;
		}
		if (dp[n] != -1) {
			return dp[n];
		}
		return dp[n] = Math.max(circularArr[n] + maxSumMemoized(circularArr, i, n - 2, dp),
				maxSumMemoized(circularArr, i, n - 1, dp));
	}

	// Time Complexity: O(2^N)
	// Space Complexity: O(N)
	private static int maxSumNo2AdjCircularRecursive(int[] circularArr) {
		int n = circularArr.length;
		if (n == 1) {
			return circularArr[0];
		}
		int with1stWithoutN = maxSumRecursive(circularArr, 0, n - 2);
		int without1stWithN = maxSumRecursive(circularArr, 1, n - 1);
		return Math.max(with1stWithoutN, without1stWithN);
	}

	private static int maxSumRecursive(int[] circularArr, int i, int n) {
		if (n < i) {
			return 0;
		}

//		int pick = circularArr[n] + maxSumRecursive(circularArr, i, n - 2);
//		int notPick = maxSumRecursive(circularArr, i, n - 1);
//		return Math.max(pick, notPick);

		return Math.max(circularArr[n] + maxSumRecursive(circularArr, i, n - 2),
				maxSumRecursive(circularArr, i, n - 1));
	}

}
