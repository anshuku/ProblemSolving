package GeeksForGeeks.Revision;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReviseLargestSumContiguousSubarrayUnique {

	public static void main(String[] args) {

		int arr[] = { 1, 2, 3, 3, 4, 5, 2, 1 };

//		int arr[] = { 1, 2, 3, 1, 5 };

//		int arr[] = { -2, -3, 4, -1, -2, 1, 5, -3 };

//		int maxSum = largestSumContiguousSubarray(arr);

		int[] bounds = largestSumContiguousSubarrayIndex(arr);

		System.out.println("The bounds array with max sum is " + Arrays.toString(bounds));

		int[] nums = Arrays.copyOfRange(arr, bounds[0], bounds[1] + 1);

		int maxSum = bounds[2];

		System.out.println("The current max array is " + Arrays.toString(nums));

		System.out.println("The current max sum is " + maxSum);

		int maxSumDp = largestSumContiguousSubarrayDP(arr);

		System.out.println("The current max sum with dp is " + maxSumDp);

		int maxSumUnique = largestSumContiguousSubarrayUnique(arr);
		System.out.println("The max sum for unqiue elements in subarray is " + maxSumUnique);

		int maxSumUnique2Ptr = largestSumContiguousSubarrayUnique2Ptr(arr);
		System.out.println("The max sum for unqiue elements in subarray by two pointer is " + maxSumUnique2Ptr);

		int maxSumPrefixSumArr = largestSumContiguousSubarrayPrefixSumArr(arr);
		System.out
				.println("The max sum for unqiue elements in subarray by prefix sum and arr is " + maxSumPrefixSumArr);

		int maxSumPrefixSumMap = largestSumContiguousSubarrayPrefixSumMap(arr);
		System.out
				.println("The max sum for unqiue elements in subarray by prefix sum and map is " + maxSumPrefixSumArr);

	}

	private static int largestSumContiguousSubarrayPrefixSumMap(int[] arr) {
		int n = arr.length;
		int[] prefixSum = new int[n + 1];

		for (int i = 1; i < n; i++) {
			prefixSum[i] = prefixSum[i - 1] + arr[i - 1];
		}
		Map<Integer, Integer> map = new HashMap<>();
		int maxSum = Integer.MIN_VALUE;

		int j = 0;
		for (int i = 0; i < n; i++) {
			int num = arr[i];
			if (map.containsKey(num)) {
				j = Math.max(j, map.get(num));
			}

			maxSum = Math.max(maxSum, prefixSum[i] + num - prefixSum[j]);
			map.put(num, i + 1);
		}
		return 0;
	}

	private static int largestSumContiguousSubarrayPrefixSumArr(int[] arr) {
		int n = arr.length;
		int[] lastSeen = new int[1000];
		int maxSum = Integer.MIN_VALUE;

		int[] prefixSum = new int[n + 1];
		for (int i = 1; i < n; i++) {
			prefixSum[i] = prefixSum[i - 1] + arr[i - 1];
		}
		int j = 0;
		for (int i = 0; i < n; i++) {
			int num = arr[i];
			if (lastSeen[num] > 0) {
				j = Math.max(j, lastSeen[num]);
			}
			maxSum = Math.max(maxSum, prefixSum[i] + num - prefixSum[j]);
			lastSeen[num] = i + 1;
		}

		return maxSum;
	}

	private static int largestSumContiguousSubarrayUnique2Ptr(int[] arr) {

		int n = arr.length;
		int i = 0, j = 1;
		int sum = arr[0];
		int maxSumUnique = sum;

		Set<Integer> set = new HashSet<>();
		set.add(arr[0]);

		while (i < n - 1 && j < n) {
			if (set.add(arr[j])) {
				sum += arr[j];
				maxSumUnique = Math.max(maxSumUnique, sum);
				j++;
			} else {
				sum -= arr[i];
				set.remove(arr[i++]);
			}
		}
		return maxSumUnique;
	}

	private static int largestSumContiguousSubarrayUnique(int[] arr) {
		int n = arr.length;
		int maxSumUnique = arr[0];
		for (int i = 0; i < n; i++) {
			Map<Integer, Integer> map = new HashMap<>();
			int sum = 0;
			for (int j = i; j < n; j++) {
				sum += arr[j];
				map.put(arr[j], map.getOrDefault(arr[j], 0) + 1);

				if (map.size() != j - i + 1) {
					break;
				}
				maxSumUnique = Math.max(sum, maxSumUnique);
			}
		}
		return maxSumUnique;
	}

	private static int largestSumContiguousSubarrayDP(int[] arr) {
		int n = arr.length;
		int dp[] = new int[n];
		int result = arr[0];

		dp[0] = arr[0];

		for (int i = 1; i < arr.length; i++) {
			dp[i] = Math.max(arr[i], dp[i - 1] + arr[i]);

			result = Math.max(result, dp[i]);
		}
		return result;
	}

	// Time Complexity: O(n)
	// Space Complexity: O(1)
	private static int[] largestSumContiguousSubarrayIndex(int[] arr) {
		int maxSoFar = Integer.MIN_VALUE;
		int currentMax = 0;
		int start = 0, end = 0, s = 0;

		for (int i = 0; i < arr.length; i++) {
			currentMax += arr[i];
			if (maxSoFar < currentMax) {
				maxSoFar = currentMax;
				start = s;
				end = i;
			}
			if (currentMax < 0) {
				currentMax = 0;
				s = i + 1;
			}
		}
		return new int[] { start, end, maxSoFar };
	}

	private static int largestSumContiguousSubarray(int arr[]) {
		int maxSoFar = Integer.MIN_VALUE;
		int currentMax = 0;
		for (int i = 0; i < arr.length; i++) {
			currentMax += arr[i];
			if (maxSoFar < currentMax) {
				maxSoFar = currentMax;
			}
			if (currentMax < 0) {
				currentMax = 0;
			}
		}
		return maxSoFar;
	}

}
