package LeetCode.MonotonicStack;

import java.util.Stack;

/*
 * P769. Max Chunks To Make Sorted - Medium
 * 
 * You are given an integer array arr of length n that represents a permutation of the integers in the range [0, n - 1].
 * 
 * We split arr into some number of chunks (i.e., partitions), and individually sort 
 * each chunk. After concatenating them, the result should equal the sorted array.
 * 
 * Return the largest number of chunks we can make to sort the array.
 * 
 * Constraints:
 * > n == arr.length
 * > 1 <= n <= 10
 * > 0 <= arr[i] < n
 * > All the elements of arr are unique.
 * 
 * Approach - Suffix-Prefix Array, Prefix Sum, Monotonic Stack
 */
public class P769MaxChunksToMakeSorted {

	public static void main(String[] args) {
//		int[] arr = { 4, 3, 2, 1, 0 };
		int[] arr = { 1, 0, 2, 3, 4 };
//		int[] arr = { 1, 2, 0, 3 };
//		int[] arr = { 2, 0, 1 };

		int maxChunksMaxElement = maxChunksToSortedMaxElement(arr);
		System.out.println("Max Element: The largest number of chunks to sort the array " + maxChunksMaxElement);

		int maxChunksMStack = maxChunksToSortedMStack(arr);
		System.out.println("Monotonic Stack: The largest number of chunks to sort the array " + maxChunksMStack);

		int maxChunksMStackAlt = maxChunksToSortedMStackAlt(arr);
		System.out.println("Monotonic Stack Alt: The largest number of chunks to sort the array " + maxChunksMStackAlt);

		int maxChunksPrefixSum = maxChunksToSortedPrefixSum(arr);
		System.out.println("Prefix Sum: The largest number of chunks to sort the array " + maxChunksPrefixSum);

		int maxChunksPrefixMaxSuffixMin = maxChunksToSortedPrefixMaxSuffixMin(arr);
		System.out.println(
				"Prefix Max Suffix Min: The largest number of chunks to sort the array " + maxChunksPrefixMaxSuffixMin);
	}

	// Maximum Element
	// This is similar to Prefix Sum approch, we use a condition to determine when a
	// segment can be considered a valid chunk. While iterating the array, we keep
	// track of the max element we've found up to the current index.
	// Consider the case where the current index i = max element found so far,
	// maxElement. It means that all elements before index i are < maxElement.
	// Since, this array is the permutation of integer in the range [0, n - 1], it
	// also guarantees that all integers from 0 to maxElement must appear in the
	// array before index i. Therefore, whenever, the curent index matches the max
	// value so far (i == maxElement), we increment the count of chunks.
	// Time complexity - O(n)
	// Space complexity - O(1)
	private static int maxChunksToSortedMaxElement(int[] arr) {
		int n = arr.length;

		int max = 0;
		int chunks = 0;

		// Iterate over the array
		for (int i = 0; i < n; i++) {
			// Update the maxElement
			max = Math.max(max, arr[i]);

			if (max == i) {
				// All values in range [0, i] belong to the prefix arr[0:i]; a new chunk can be
				// formed.
				chunks++;
			}
		}

		return chunks;
	}

	// Monotonically increasing stack
	// The main idea of this approach is that if a number in the array is < any
	// number in the previous chunks, this number cannot create a new chunk, as we
	// cannot swap elements from different chunks to fix their relative order. We
	// will iterate over the array and maintain a stack to represent the max values
	// of the chunks created so far. As we loop over the array, we decide whether
	// the current element (arr[i]) can start a new chunk or should merge with an
	// existing chunks. There are 2 cases:
	// 1. arr[i] > stack.top(): it means we can start a new chunk as it's > all
	// previous chunks. We push arr[i] into the stack to represent a new chunk.
	// 2. arr[i] < stack.top(): it means we cannot form a new chunk. Instead, it
	// must merge with 1 or more existing chunks. We remove all chunks whose max
	// value is > current element. Then, push the max value of the merged chunks to
	// maintain the stack sorted.
	// Now, at each point, the elements of the stack represent the max elements of
	// the chunks created so far. Therefore, at the end of the iteration, the size
	// of the stack = the max number of chunks that can be formed.
	// Time complexity - O(n), While iterating the array, we either push or pop
	// elements from the stack (constant-time). The number of times the while loop
	// runs in a single iteration corresponds to the size of the current chunk being
	// merged. The total number of pop operations across all iteratons is therefore
	// equal to the sum of the sizes of the chunks which is the total number of
	// elements in the array.
	// Space complexity - O(n), in the worst case, the stack contains n elements.
	private static int maxChunksToSortedMStack(int[] arr) {
		int n = arr.length;

		// Monotonic stack to store the max elements of each chunk.
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < n; i++) {
			// Case 1: Current element is larger, start a new chunk.
			if (stack.isEmpty() || stack.peek() < arr[i]) {
				stack.push(arr[i]);
			} else {
				// Case 2: Merge chunks
				int max = stack.peek();
				while (!stack.isEmpty() && stack.peek() > arr[i]) {
					stack.pop();
				}
				stack.push(max);
			}
		}
		return stack.size();
	}

	private static int maxChunksToSortedMStackAlt(int[] arr) {
		int n = arr.length;

		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < n; i++) {
			int max = Integer.MIN_VALUE;
			while (!stack.isEmpty() && stack.peek() > arr[i]) {
				max = Math.max(max, stack.pop());
			}

			if (max != Integer.MIN_VALUE) {
				stack.push(max);
			} else {
				stack.push(arr[i]);
			}
		}
		return stack.size();
	}

	// Prefix Sum
	// An important observation is that a segment of the array can form a new chunk,
	// if when sorted, it matches the correspoding segment of the fully sorted
	// sorted array. Since, the numbers in array belong to the range [0, n - 1], we
	// can simplify the problem by using the property of sums. For any index i, it's
	// sufficient to check whether the sum of elements in arr upto that index = sum
	// of elements in the corresponding prefix of the sorted array.
	// This ensures that the elements in the corresponding segments match (possibly
	// in a different order). When this condition is satisfied, we can form a new
	// chunk - either from the beginning of the array or the end of the previous
	// chunk.
	// Time complexity - O(n), we iterate over the array arr once.
	// Space complexity - O(1)
	private static int maxChunksToSortedPrefixSum(int[] arr) {
		int n = arr.length;

		int prefixSum = 0;
		int sortedSum = 0;

		int chunks = 0;

		for (int i = 0; i < n; i++) {
			// Update prefix sum of arr.
			prefixSum += arr[i];
			// Update prefix sum of the sorted array.
			sortedSum += i;

			// If the 2 sums are equal, the 2 prefixes contain the same elements, a chunk
			// can be formed.
			if (prefixSum == sortedSum) {
				chunks++;
			}
		}
		return chunks;
	}

	// Prefix and Suffix array
	// A key observation is that a split is valid if and only if each segement
	// contains numbers strictly greater than those in the previous segment. In
	// other words, the minimum value of each segment must be > the max value of
	// previous segment.
	// One can further notice that for each number in the array, we've 2 options: we
	// can either include it in the same chunk as the previous number or create a
	// new chunk for it. One must consider the limitation that a new chunk at index
	// i can only be created if all the numbers in the current and previous chunks
	// (the "prefix" of the array) are smaller than all the numbers in the following
	// chunks (the "suffix"). Hence, max(prefix[0:i]) < min(suffix[i:n]).
	// We aim to find the largest possible number of chunks, we will choose the 2nd
	// option (create a new chunk) whenever the above condition is satisfied. Hence,
	// the problem is to count how many indices in the array satisify this
	// condition.
	// While iterating the array, if i == 0 (we create a chunk for the 1st element)
	// or for i == 1 onwards we check the condition suffixMin[i] > prefixMax[i-1].
	// Time complexity - O(n), for calculating prefixMax, suffixMin and chunks.
	// Space complexity - O(n), for prefixMax, suffixMin arrays.
	public static int maxChunksToSortedPrefixMaxSuffixMin(int[] arr) {
		int n = arr.length;

		int[] prefixMax = arr.clone();
		int[] suffixMin = arr.clone();

		// Fill the prefixMax array
		for (int i = 1; i < n; i++) {
			prefixMax[i] = Math.max(prefixMax[i], prefixMax[i - 1]);
//			suffixMin[n - 1 - i] = Math.min(suffixMin[n - 1 - i], suffixMin[n - i]);
		}

		// Fill the suffixMin array in reverse order
		for (int i = n - 2; i >= 0; i--) {
			suffixMin[i] = Math.min(suffixMin[i], suffixMin[i + 1]);
		}

		int chunks = 0;

		for (int i = 0; i < n; i++) {
			// A new chunk can be created
			if (i == 0 || suffixMin[i] > prefixMax[i - 1]) {
				chunks++;
			}
		}

		return chunks;
	}
}
