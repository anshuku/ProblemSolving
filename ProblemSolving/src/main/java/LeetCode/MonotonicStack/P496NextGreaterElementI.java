package LeetCode.MonotonicStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
 * P496. Next Greater Element I - Easy
 * 
 * The next greater element of some element x in an array is the 
 * first greater element that is to the right of x in the same array.
 * 
 * You are given two distinct 0-indexed integer arrays nums1 and nums2, where nums1 is a subset of nums2.
 * 
 * For each 0 <= i < nums1.length, find the index j such that nums1[i] == nums2[j] 
 * and determine the next greater element of nums2[j] in nums2. If there 
 * is no next greater element, then the answer for this query is -1.
 * 
 * Return an array ans of length nums1.length such that ans[i] is the next greater element as described above.
 * 
 * Approach - Monotonic Stack + Map, Array
 */
public class P496NextGreaterElementI {

	public static void main(String[] args) {
		int[] nums1 = { 4, 1, 2 };
		int[] nums2 = { 1, 3, 4, 2 };

//		int[] nums1 = { 2, 4 };
//		int[] nums2 = { 1, 2, 3, 4 };

		int[] nextGreaterElementMStack = nextGreaterElementMStack(nums1, nums2);
		System.out.println(
				"Monotonic Stack: The next greater element array: " + Arrays.toString(nextGreaterElementMStack));

		int[] nextGreaterElementMap = nextGreaterElementMap(nums1, nums2);
		System.out.println("Map: The next greater element array: " + Arrays.toString(nextGreaterElementMap));

		int[] nextGreaterElementArrayCF = nextGreaterElementArrayCF(nums1, nums2);
		System.out.println(
				"Cache friendly Array: The next greater element array: " + Arrays.toString(nextGreaterElementArrayCF));

		int[] nextGreaterElementArray = nextGreaterElementArray(nums1, nums2);
		System.out.println("Array: The next greater element array: " + Arrays.toString(nextGreaterElementArray));
	}

	// Stack + Map: Can handle duplicate values
	// We use Map and Stack to store the result for every possible number in nums2
	// in the form of (element, next_greater_element). Stack stores elements in
	// monotonically decreasing order due to *next greater* value requirement.
	// We scan nums2 and store each element in stack if the current element in nums2
	// is smaller than stack's top. If the current element is greater than stack's
	// top, we keep on popping each element from the stack and populate the map with
	// (stack.top(), nums2[i]) as all the stack's element are smaller than current
	// nums2 element. At the end if the stack is not empty. It means no greater
	// element is found towards the right in nums2 so we populate those with -1.
	// Time complexity - O(m), The entire nums2 array of size m is scanned only
	// once. Each of the stack's m elements are pushed and popped exactly once. The
	// nums1 array is scanned only once. The map is populated for each element in
	// nums2 once. Altogether it needs O(n+n+n+m) time, since nums1 is a subset of
	// nums2, so m <= n.
	// Space complexity - O(m), for stack containing at most n elements and map has
	// n key-value pairs.
	public static int[] nextGreaterElementMStack(int[] nums1, int[] nums2) {
		int n = nums1.length;
		int m = nums2.length;

		Stack<Integer> stack = new Stack<>();
		Map<Integer, Integer> map = new HashMap<>();

		// Find next greater element for nums2
		for (int i = 0; i < m; i++) {
			while (!stack.isEmpty() && stack.peek() < nums2[i]) {
				map.put(stack.pop(), nums2[i]);
			}
			stack.push(nums2[i]);
		}

		// Remaining elements have no greater element
		while (!stack.isEmpty()) {
			map.put(stack.pop(), -1);
		}

		int[] result = new int[n];

		for (int i = 0; i < n; i++) {
			result[i] = map.get(nums1[i]);
		}

		return result;
	}

	// Map: Best in terms of Time complexity
	// Time complexity - O(m*n), this will be faster than the previous one, since
	// here we won't need to scan nums2 array to find the position of nums1[i]
	// element.
	// Space complexity - O(m)
	private static int[] nextGreaterElementMap(int[] nums1, int[] nums2) {
		int n = nums1.length;
		int m = nums2.length;

		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < m; i++) {
			map.put(nums2[i], i);
		}

		int[] result = new int[n];

		for (int i = 0; i < n; i++) {
			int j = map.get(nums1[i]);

			j++;

			while (j < m && nums1[i] >= nums2[j]) {
				j++;
			}

			if (j == m) {
				result[i] = -1;
				continue;
			}

			result[i] = nums2[j];
		}
		return result;
	}

	// Time complexity - O(m*n)
	// Space complexity - O(1)
	private static int[] nextGreaterElementArrayCF(int[] nums1, int[] nums2) {
		int n = nums1.length;
		int m = nums2.length;

		int[] result = new int[n];

		for (int i = 0; i < n; i++) {
			int j = 0;
			while (j < m && nums1[i] != nums2[j]) {
				j++;
			}

			if (j == m - 1) {
				result[i] = -1;
				continue;
			}

			j++;

			while (j < m && nums1[i] >= nums2[j]) {
				j++;
			}

			if (j == m) {
				result[i] = -1;
				continue;
			}

			result[i] = nums2[j];
		}
		return result;
	}

	// Time complexity - O(m*n), as the whole of nums2 is scanned for all the
	// elements of nums1 in worst case.
	// Space complexity - O(1)
	private static int[] nextGreaterElementArray(int[] nums1, int[] nums2) {
		int n = nums1.length;
		int m = nums2.length;

		int[] result = new int[n];

		for (int i = 0; i < n; i++) {
			boolean found = false;
			int j;
			for (j = 0; j < m; j++) {
				if (found && nums1[i] < nums2[j]) {
					result[i] = nums2[j];
					break;
				}

				if (nums1[i] == nums2[j]) {
					found = true;
				}
			}

			if (j == m) {
				result[i] = -1;
			}
		}
		return result;
	}
}
