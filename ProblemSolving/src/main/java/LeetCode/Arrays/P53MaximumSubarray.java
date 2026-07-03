package LeetCode.Arrays;

/*
 * P53. Maximum Subarray - Medium
 * 
 * Given an integer array nums, find the subarray with the largest sum, and return its sum.
 * 
 * Approach - Greedy, DP
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1) and O(n)
 */
public class P53MaximumSubarray {

	public static void main(String[] args) {
		int[] arr = { -2, -3, 4, -1, -2, 1, 5, -3 };

//		int[] arr = { 1 };

//		int[] arr = { 5, 4, -1, 7, 8 };

		int maxSum = largestSumContiguousSubarrayKadane(arr);

		System.out.println("Kadane: Largest sum for contiguous subarray is:" + maxSum);

		int[] subArr = arrLargestSumContiguousSubarrayKadane(arr);

		System.out.println("Kadane Array: Largest sum contiguous subarray is:");
		for (int i = subArr[0]; i <= subArr[1]; i++) {
			System.out.print(arr[i] + " ");
		}

		int maxSumDp = largestSumContiguousSubarrayKadaneDP(arr);
		System.out.println();
		System.out.println("DP: Largest sum for contiguous subarray is:" + maxSumDp);
	}

	private static int largestSumContiguousSubarrayKadane(int[] arr) {
		int maxSumEndingHere = 0;
		int maxSumSoFar = Integer.MIN_VALUE;
		int n = arr.length;

		for (int i = 0; i < n; i++) {
			maxSumEndingHere += arr[i];
			if (maxSumSoFar < maxSumEndingHere) {
				maxSumSoFar = maxSumEndingHere;
			}
			if (maxSumEndingHere < 0) {
				maxSumEndingHere = 0;
			}
		}
		return maxSumSoFar;
	}

	private static int[] arrLargestSumContiguousSubarrayKadane(int[] arr) {
		int maxSumEndingHere = 0;
		int maxSumSoFar = Integer.MIN_VALUE;
		int n = arr.length;
		int s = 0, start = 0, end = 0;
		for (int i = 0; i < n; i++) {
			maxSumEndingHere += arr[i];
			if (maxSumSoFar < maxSumEndingHere) {
				maxSumSoFar = maxSumEndingHere;
				end = i;
				start = s;
			}
			if (maxSumEndingHere < 0) {
				maxSumEndingHere = 0;
				s = i + 1;
			}
		}
		System.out.println("Array: Largest sum for contiguous subarray is " + maxSumSoFar);
		return new int[] { start, end };
	}

	// Not space optimized
	private static int largestSumContiguousSubarrayKadaneDP(int[] arr) {
		int n = arr.length;
		// Intermediate stores results
		int dp[] = new int[n];
		dp[0] = arr[0];
		// Answer is initialized with first element of intermediate array
		int ans = dp[0];

		for (int i = 1; i < n; i++) {
			// intermediate value is max of current element and sum of current element and
			// previous result
			dp[i] = Math.max(arr[i], arr[i] + dp[i - 1]);
			// update answer with maximum encountered so far
			ans = Math.max(ans, dp[i]);
		}

		return ans;
	}

}
