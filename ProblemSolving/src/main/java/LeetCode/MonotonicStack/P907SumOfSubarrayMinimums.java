package LeetCode.MonotonicStack;

import java.util.Stack;

/*
 * P907. Sum of Subarray Minimums - Medium
 * 
 * Given an array of integers arr, find the sum of min(b), where b ranges over every (contiguous) 
 * subarray of arr. Since the answer may be large, return the answer modulo 10^9 + 7.
 * 
 * Approach - Monotonic Stack, DP
 */
public class P907SumOfSubarrayMinimums {

	private static final int MOD = 1_000_000_007;

	public static void main(String[] args) {
//		int[] arr = { 3, 1, 2, 4 }; // 17
		int[] arr = { 11, 81, 94, 43, 3 }; // 444

		int minSumStackDP = sumSubarrayMinsStackDP(arr);
		System.out.println("Stack DP: The sum of minimum element of each subarray is: " + minSumStackDP);

		int minSumStack = sumSubarrayMinsStack(arr);
		System.out.println("Stack: The sum of minimum element of each subarray is: " + minSumStack);

		int minSumArray = sumSubarrayMinsArray(arr);
		System.out.println("Array: The sum of minimum element of each subarray is: " + minSumArray);
	}

	// Monotonically increasing stack + DP
	// In the previous approach, we found the contribution of each element towards
	// final summation by iterating through all the elements and finding the range
	// where the element is smallest. With Stack + DP, we try to find a way so that
	// elements appearing later can use the calculation done for previous elements.
	// We check for overlapping subproblems - done via building on smaller problems
	// and find a solution for bigger problems. In the current context, we use
	// smaller subarrays to find a solution for larger subarrays.
	// We use dp array, where dp[i] stores the sum of minimums of all subarrays,
	// which end at index i. We find relation between dp[i] and dp[j] where i > j.
	// We can see that the number of subarrays ending at i > number of subarrays
	// ending at j.
	// Example: arr = [8,6,3,5,4,9,2], let's consider all subarrays which end the
	// element 3. There are 3: [8,6,3], [6,3], [3]. 3 is the smallest element here.
	// So the sum contributed by all subarrays ending at 3 = 3+3+3 = 9. dp[2] = 9,
	// as 2 is the index of element 3.
	// For next element, 5 at index 3. There are 4 subarrays ending at 5. We get
	// them by concatenating 5 at the end of the subarrays ending at the previous
	// element. 1 subarrays consists of only 5. [8,6,3,5], [6,3,5], [3,5], [5].
	// The current element 5 is greater than previous element 3. So we can see that
	// in all the new subarrays made by concatenating 5, 5 is not going to be the
	// minimum element. 5 contributes to only 1 array - [5].
	// Subarray minimums' sum for dp[3] = dp[2] + 5 = 14
	// Hence, one can say, for 2 elements at index i and i + 1, if arr[i+1] >
	// arr[i], then dp[i+1] = dp[i] + arr[i+1]
	// Now, in case of a number less than last instead of greater (5>3).
	// We see at index 4 we've 4 < 5. [8,6,3,5,4], [6,3,5,4], [3,5,4], [5,4], [4].
	// Now, we walk left from 4 to find the 1st element smaller than 4. We find 3 at
	// index 2. We can see that in subarrays which start after 3, 4 is minimum.
	// There are 2 such subarrays - [5,4], [4]. The rest of them maintain 3 as min.
	// dp[4] = dp[2] + 2*arr[4] = 9+2*4 = 17. The 2 here in 2*arr[4] comes from 2
	// subarrays [5,4], [4], or the difference between indices 4 and 2.
	// We see a pattern here, take any element i in the array arr. We check left
	// from i and find first index j where the element is < arr[i]. We find i - j
	// subarrays with arr[i] as the minimum element. For the rest, dp[j] contains
	// the answer, we sum both to get dp[i], dp[] = dp[j] + (i-j)*arr[i].
	// For the elements which have no smaller element, we can assume j to be -1 and
	// dp[j] = 0. Or dp[i] = (i+1)*arr[i]. We use a monotonic stack to find previous
	// smaller index for each index. We also populate dp array parallely to find the
	// answer in 1 pass. At the end, we sum up all the elements in dp to get result.
	// Time complexity - O(n), we create a monotonic stack in O(n) time, as we build
	// the stack, we fill dp array in same time. In the end we take sum of all
	// elements of dp array in O(n) time.
	// Space complexity - O(n), we use 2 external data structures - dp and stack
	// which takes O(n) space in worst case. So, it requires O(2n) space.
	private static int sumSubarrayMinsStackDP(int[] arr) {
		int n = arr.length;

		// Stores the sum of minimum value present in the subarrays ending at index i.
		int[] dp = new int[n];

		// Monotonically increasing stack
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < n; i++) {
			// We pop the stack until the stack is empty as well as the top of the stack is
			// >= the current element.
			while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
				stack.pop();
			}

			// If the previousSmaller element doesn't exists then the current element
			// contributes with all subarrays ending at i.
			if (stack.isEmpty()) {
				dp[i] = (i + 1) * arr[i];
			} else {
				int previous = stack.peek();

				dp[i] = dp[previous] + (i - previous) * arr[i];
				dp[i] %= MOD;
			}
			// Push the current index.
			stack.push(i);
		}

		// Add all elements of the dp to get the answer.
		long minSum = 0;

		for (int count : dp) {
			minSum += count;
			minSum %= MOD;
		}

		return (int) minSum;
	}

	// Monotonically increasing stack
	// In the previous brute force approach, to get the range [i+1, j-1]. We can use
	// a monotonically increasing stack to determine the value of i and j. Monotonic
	// stacks are used to find the previous smaller element and the next smaller
	// element in linear time complexity.
	// Finding the previous smaller and next smaller elements with a monotonic stack
	// We use a monotonically increasing stack where for i > j, arr[i] >= arr[j]. As
	// we're interested in the next and the previous smaller elements. If we wanted
	// to know the next and previous greater elements, we can use a monotonically
	// decreasing stack. This monotonically decreasing stack is useful for finding
	// the sum of subarray maximums. But in this case, we need increasing one.
	// In monotonically increasing stack, we ensure that if the item at the top of
	// the stack is bigger than or equal to current item, we first pop it off before
	// pushing the current element on top. The process of building this
	// monotonically increasing stack is useful here. As a new item gets added to
	// the stack, older items are removed from the top if the old items are of same
	// size or bigger. One can say that the incoming element must be the next
	// smaller (or equal size) element of the item going off the stack. So everytime
	// an item is popped, we know about its next smaller item.
	// Now if the stack is not empty, the new stack top has the previous smaller
	// item. This is due to the fact that whenever new item is added to the stack,
	// we ensure that all the bigger items are removed first. Also, if the stack
	// becomes empty at the time of removal of an item, it indicates that the popped
	// item is the smallest one seen so far.
	// Once the process is complete, the stack contains a series of items in
	// increasing order. These items don't have any smaller items after themselves.
	// And their previous smaller items are stored right below them in the stack.
	// Edge Case - Duplicate Elements
	// We should make sure that we don't count the contribution by an element twice.
	// This is possible in the case [2,2,2]. While finding the boundary elements for
	// a range, we look for elements that are strictly less than < the current
	// element on the left. To decide the right boundary, we look for the elements
	// which are less than or equal to <= the current element.
	// Example - [3,1,5,2,6,2,8,2,1]. In this example, 2 appears 3 times at indices
	// 3, 5 and 7. When calculating the range for the second 2 (at index 5), we
	// calculate the next smaller element to be the 2 at index 7. The previous
	// smaller element is 1 at index 1 via strict comparision. For 3rd 2, at index
	// 7. Previous smaller item index is 1 and next smaller item index is 8.
	// This consideration of < in left and <= in right ensures that none of the
	// ranges are counted twice.
	// Algorithm:
	// We use a monotonically increasing stack which stores the indices of the array
	// elements. We iterate from index 0 to n (inclusive) where n is the length of
	// the array arr. We use n to indicate we've reached the end of the array and
	// everything left in the stack can then be removed. For each index i in array:
	// a) If the stack isn't empty, pop all the items from top until item at the top
	// of the stack, stackTop < arr[i] or stack becomes empty.
	// We must be careful about duplicate elements in the array. So when we consider
	// the next smaller items, we also allow equal elements. For previous smaller
	// items, we keep them strictly smaller (not equal =/=).
	// For each mid, popped from the stack, we get the range in which it's minimum.
	// The range is [previous strictly smaller item, next smaller item].
	// The next smaller item's index is i (while iterating). The previous smaller
	// item's index is the current top (post popping). If the stack becomes empty,
	// it's -1. Contribution of element = arr[mid]*(i - mid)*(mid - previousSmall).
	// We i=n, we would have pushed all the array elements into the stack. Some of
	// them might have been removed. The remaining items in the stack has no smaller
	// items after them (monotonically increasing) but there is smaller element just
	// before (except 1st). We consider the array's length = n as the next
	// smallerIndex for all of these. We find their contribution similarly.
	// b) Since all the bigger items have already been removed from the stack, we
	// can push index i to the stack.
	// Time complexity - O(n), while building a monotonic stack, each element is
	// pushed in once and popped out once. Every time an item is popped, we
	// calculate the contribution of that item. This is done for n times.
	// Space complexity - O(n), in worst case when the elements are in increasing
	// order, the stack will contain all the items.
	private static int sumSubarrayMinsStack(int[] arr) {
		int n = arr.length;

		long minSum = 0;

		// Monotonically increasing stack
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i <= n; i++) {
			// When i reaches the array length, it indicates that all the elements have been
			// processed, and the remaining elements (if present as it's checked earlier) in
			// the stack should be popped now where right = n.
			// Here >= in arr[stack.peek()] >= arr[i] as right can be same but left can't.
			// This ensures no contribution is counted twice. rightBoundary takes equal or
			// smaller <= elements while leftBoundary takes only strictly smaller elements
			// into account.
			while (!stack.isEmpty() && (i == n || arr[stack.peek()] >= arr[i])) {
				int mid = stack.pop();
				int left = stack.isEmpty() ? -1 : stack.peek();
				int right = i;

				// Count of subarrays where mid is the minimum element.
				long count = (mid - left) * (right - mid) % MOD;

				minSum += (arr[mid] * count) % MOD;
				minSum %= MOD;
			}
			stack.push(i);
		}

		return (int) minSum;
	}

	// Brute force - helps optimize later
	// We can find all subarrays starting from 0 to n - 1. We can do this with 2
	// nested loops where 1 loop is for the start of range and another one for the
	// end of range. While considering each subarray of a range, we find the minimum
	// of this range as well. Once we get the minimum of range, we add this to the
	// running total of all minimums. In the end, this running total is the answer.
	// In the brute force approach, most of the time is spent to generate the
	// subarrays. Here, we are focussing on the range first, and then we look for
	// the minimum element in the range which consumes time. We optimize this via
	// changing the approach. We focus on each element instead of focusing on the
	// range and then we figure out the range in which it's the minimum. By doing
	// so, we determine each element's contribution toward the summation of all
	// minimums. Now, assume we know the range in which each element is smallest.
	// Now we can determine the number of subarrays in this range that contain this
	// element. Since, this element is the smallest in the range, it'll also be the
	// smallest in all the subarrays. The count of the subarrays multiplied by the
	// element will give this element's contribution to minimum sum.
	// To get number of subarrays that contain a specific (minimum) element in a
	// given range. Ex. - array: [0,3,4,5,2,3,4,1,4], we need to find number of
	// subarrays where 2 is te smallest element. We can see 2 is smallest in the
	// range [1,6] given by the subarray [3,4,5,2,3,4]. So, all the subarrays of
	// this range that contain 2 will also have 2 as the smallest integer. Now, in
	// the given range, to find count of subarrays which contain 2. Each subarray is
	// a continuous series of elements that contains 2 from the given range. So to
	// count them, we can count every subarray that starts before 2 or at 2. We can
	// get the count by multiplying 2 numbers - the count of elements before (and
	// including) 2 and the count of elements after (and including) 2. So we've 4
	// ([3,4,5,2]) * 3 ([2,3,4]) = 12.
	// Now, we know the count of subarrays where each element is smallest, to get
	// the amount each element will contribute to final summation. We get it via
	// element * count of subarrays where it's the smallest. We can sum this amount
	// for every element to get the answer.
	// Now, the only remaining part is to get the range in which each element is the
	// smallest. For this, we find the nearest element on the left, which is less
	// than itself. Then, find the closest element on the right, which is less than
	// (<=) itself. If i and j are indices of these elements on the left and right,
	// then [i + 1, j - 1] indices create our range.
	public static int sumSubarrayMinsArray(int[] arr) {
		int n = arr.length;

		long minSum = 0;

		for (int i = 0; i < n; i++) {
			int left = i;

			while (left > 0 && arr[left - 1] >= arr[i]) {
				left--;
			}

			int right = i;

			while (right < n - 1 && arr[right + 1] > arr[i]) {
				right++;
			}

			int numLeft = i - left + 1; // + 1 as we took left-1 while comparision
			int numRight = right - i + 1; // + 1 as we took right+1 while comparision

			long count = numLeft * numRight % MOD;
			minSum += (count * arr[i]) % MOD;
			minSum %= MOD;
		}

		return (int) minSum;
	}

}
