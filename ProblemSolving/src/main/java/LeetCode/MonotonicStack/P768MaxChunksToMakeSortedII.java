package LeetCode.MonotonicStack;

import java.util.Stack;

/*
 * P768. Max Chunks To Make Sorted II - Hard
 * 
 * You are given an integer array arr.
 * 
 * We split arr into some number of chunks (i.e., partitions), and individually sort 
 * each chunk. After concatenating them, the result should equal the sorted array.
 * 
 * Return the largest number of chunks we can make to sort the array.
 * 
 * Approach - Suffix-Prefix array, Monotonic Stack
 */
public class P768MaxChunksToMakeSortedII {

	public static void main(String[] args) {
//		int[] arr = { 5, 4, 3, 2, 1 };
		int[] arr = { 2, 1, 3, 4, 4 };

		int maxChunksPrefixMinSuffixMax = maxChunksToSortedPrefixMinSuffixMax(arr);
		System.out.println("Prefix Min Suffix Max: The largest number of chunks which can sort the array: "
				+ maxChunksPrefixMinSuffixMax);

		int maxChunksMStack = maxChunksToSortedMStack(arr);
		System.out
				.println("Monotonic Stack: The largest number of chunks which can sort the array: " + maxChunksMStack);
	}

	public static int maxChunksToSortedPrefixMinSuffixMax(int[] arr) {
		int n = arr.length;

		int[] prefixMax = arr.clone();
		int[] suffixMin = arr.clone();

		for (int i = 1; i < n; i++) {
			prefixMax[i] = Math.max(prefixMax[i], prefixMax[i]);
//			suffixMin[n - 1 - i] = Math.min(suffixMin[n - 1 - i], suffixMin[n - i]);
		}

		for (int i = n - 2; i >= 0; i--) {
			suffixMin[i] = Math.min(suffixMin[i], suffixMin[i + 1]);
		}

		int chunks = 0;

		for (int i = 0; i < n; i++) {
			if (i == 0 || suffixMin[i] >= prefixMax[i - 1]) {
				chunks++;
			}
		}

		return chunks;
	}

	private static int maxChunksToSortedMStack(int[] arr) {
		int n = arr.length;

		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < n; i++) {
			if (stack.isEmpty() || stack.peek() <= arr[i]) {
				stack.push(arr[i]);
			} else {
				int max = stack.peek();
				while (!stack.isEmpty() && stack.peek() > arr[i]) {
					stack.pop();
				}
				stack.push(max);
			}
		}
		return stack.size();
	}
}
