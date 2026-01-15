package GeeksForGeeks.Arrays;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LargestSumContiguousSubarrayUnique {

	public static void main(String[] args) {

		int[] arr = { 1, 2, 3, 3, 4, 5, 2, 1 };

//		int arr[] = {1, 2, 3, 1, 5};

//		int arr[] = { -2, -3, 4, -1, -2, 1, 5, -3 };

		int maxSumPrefixSumMap = maxSumUniqueSubarrayPrefixSumMap(arr);

		System.out.println("Largest sum contiguous subarray by prefix sum by map is: " + maxSumPrefixSumMap);

		int maxSumPrefixSumArr = maxSumUniqueSubarrayPrefixSumArray(arr);

		System.out.println("Largest sum contiguous subarray by prefix sum by arr is: " + maxSumPrefixSumArr);

		int maxSum2Ptr = maxSumUniqueSubarray2Pointers(arr);

		System.out.println("Largest sum contiguous subarray by 2 pointers is: " + maxSum2Ptr);

		int maxSumBf = maxSumUniqueSubarrayBF(arr);

		System.out.println("Largest sum contiguous subarray by brute force is: " + maxSumBf);

	}

	// for positive integers
	// Time complexity: O(n)
	// Space complexity: O(n)
	private static int maxSumUniqueSubarrayPrefixSumMap(int[] arr) {
		int maxSum = arr[0];

		int n = arr.length;

		int[] prefixSum = new int[n + 1];
		for (int i = 1; i < n; i++) {
			prefixSum[i] = prefixSum[i - 1] + arr[i - 1];
		}

		Map<Integer, Integer> map = new HashMap<>();
		int j = 0;
		for (int i = 0; i < n; i++) {
			int num = arr[i];
			if (map.containsKey(num)) {// map.getOrDefault(num, 0) > 0 also works
				j = Math.max(j, map.get(num));
			}

			maxSum = Math.max(maxSum, prefixSum[i] + num - prefixSum[j]);
			map.put(num, i + 1);
		}
		return maxSum;
	}

	// Only for positive integers
	// Time complexity: O(n)
	// Space complexity: O(max(n, max_element))
	private static int maxSumUniqueSubarrayPrefixSumArray(int[] arr) {

		int maxSum = Integer.MIN_VALUE;
		int n = arr.length;
		int[] prefixSum = new int[n + 1];

		for (int i = 1; i < n; i++) {
			prefixSum[i] = prefixSum[i - 1] + arr[i - 1];
		}

		// Create an array/hashmap containing the index of last occurrence of an element
		int[] lastSeen = new int[1000];
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

	// Only for positive integers
	// Time complexity: O(n)
	// Space complexity: O(n)
	private static int maxSumUniqueSubarray2Pointers(int[] arr) {
		int i = 0, j = 1;// Initialize 2 pointers

		int sum = arr[0];
		int maxSum = sum;

		// Stores unique elements
		Set<Integer> set = new HashSet<>();
		set.add(arr[0]);// Insert the first element

		int n = arr.length;

		while (i < n - 1 && j < n) {

			// Update sum and increment j
			if (!set.contains(arr[j])) {// set.add(arr[j] and j++ also works
				sum += arr[j];
				maxSum = Math.max(sum, maxSum);
				set.add(arr[j++]);
			} // Update sum and increment i
			else {
				sum -= arr[i];
				// remove arr[i] element from the start of the set
				set.remove(arr[i++]);
			}
		}
		return maxSum;
	}

	// Brute force: Generates all subarrays and checks for unique elements
	// Time Complexity: O(n^2)
	// Space Complexity: O(n)
	private static int maxSumUniqueSubarrayBF(int[] arr) {
		int n = arr.length;
		int result = 0;
		for (int i = 0; i < n; i++) {
			int sum = 0;
			// Keeps track of duplicate elements in subarray
			Map<Integer, Integer> map = new HashMap<>();
			for (int j = i; j < n; j++) {
				map.put(arr[j], map.getOrDefault(arr[j], 0) + 1);

				sum += arr[j];
				// Check if there is any duplicate
				if (map.size() == j - i + 1) {
					result = Math.max(sum, result);
				} else {
					break;
				}
			}
		}
		return result;
	}

}
