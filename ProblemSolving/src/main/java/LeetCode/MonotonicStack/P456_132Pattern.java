package LeetCode.MonotonicStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/*
 * P456. 132 Pattern - Medium
 * 
 * Given an array of n integers nums, a 132 pattern is a subsequence of three integers 
 * nums[i], nums[j] and nums[k] such that i < j < k and nums[i] < nums[k] < nums[j].
 * 
 * Return true if there is a 132 pattern in nums, otherwise, return false.
 * 
 * Approach - Monotonic stacks, Intervals
 */
public class P456_132Pattern {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 3, 4 };
//		int[] nums = { 3, 1, 4, 2 };
//		int[] nums = { -1, 3, 2, 0 };
//		int[] nums = { 1, 0, 1, -4, -3 };
		int[] nums = { 1, 2, 3, 4, -4, -3, -5, -1 };

		int[] nums2 = Arrays.copyOf(nums, nums.length);
		int[] nums3 = Arrays.copyOf(nums, nums.length);

		boolean is132PatternMStackArray = find132patternMStackArray(nums);
		System.out.println("Monotonic Stack Array: There is a 132 pattern in nums: " + is132PatternMStackArray);

		nums = nums2;

		boolean is132PatternMStack = find132patternMStack(nums);
		System.out.println("Monotonic Stack: There is a 132 pattern in nums: " + is132PatternMStack);

		boolean is132PatternBS = find132patternBinarySearch(nums);
		System.out.println("Binary Search: There is a 132 pattern in nums: " + is132PatternBS);

		nums = nums3;

		boolean is132PatternIntervals = find132patternIntervals(nums);
		System.out.println("Intervals: There is a 132 pattern in nums: " + is132PatternIntervals);

		boolean is132PatternBFOpt = find132patternBruteForceOpt(nums);
		System.out.println("Brute Force Optimized: There is a 132 pattern in nums: " + is132PatternBFOpt);

		boolean is132PatternBF = find132patternBruteForce(nums);
		System.out.println("Brute Force: There is a 132 pattern in nums: " + is132PatternBF);
	}

	// Using Array as a Stack
	// In last approach, in worst case, the required element is not found for the n
	// elements and Binary Search is done at every step increasing the time
	// complexity. We can follow the stack approach, i.e. we can remove the elements
	// by updating index k which aren't greater than nums[i] (min[j]). Thus, in case
	// no element is larger than min[j] the index k reaches the last element. Now,
	// at every step, only nums[j] will be added and removed from consideration in
	// the next step, improving time complexity in worst case. Rest remains same as
	// in Stack approach.
	// Time complexity - O(n), we fill the min array in O(n) time. AFter this we
	// traverse the nums array in backward direction to find the nums[k]. At most n
	// elements can be added or removed from nums array. Thus, the 2nd traversal
	// takes O(n) time.
	// Space complexity - O(n) for min array.
	private static boolean find132patternMStackArray(int[] nums) {
		int n = nums.length;

		if (n < 3) {
			return false;
		}

		int[] min = new int[n];
		min[0] = nums[0];

		for (int i = 1; i < n; i++) {
			min[i] = Math.min(min[i - 1], nums[i]);
		}

		int k = n;
		for (int j = n - 1; j >= 0; j--) {
			if (nums[j] > min[j]) {
				while (k < n && nums[k] <= min[j]) {
					k++;
				}

				if (k < n && nums[j] > nums[k]) {
					return true;
				}

				nums[--k] = nums[j];
			}
		}

		return false;
	}

	// Monotonic Stack: nums[k] Decreasing
	// In last approach we work out on nums[i] and nums[j] to find nums[k]. Here, we
	// work out on nums[i] and nums[k] via stack to find nums[j].
	// We find nums[i] as the best candidate for nums[j] by storing the min as we
	// iterate through nums as part of preprocessing. We then iterate backwards in
	// nums for nums[k] and check if nums[j] > min[j] (for nums[i]), if it's equal
	// (less is not possible) we try to find nums[k] stored in a stack which is
	// monotonically decreasing. We discard the equal case for nums[j] and min[j] as
	// during this iteration nums[j] may become nums[k] and stored in a stack. For
	// equal values nums[k] won't even satisfy nums[k] > min[j] as they will be
	// equal so we consider > case only.
	// Inside this condition. We continuously check if the stack which contains
	// nums[k] for emptiness and also if the top of stack is <= min[j], this is to
	// pop the irrevalant candidates of nums[k] as it won't satisfy the condition of
	// nums[k] > nums[i]. These popped elements are not suitable canidates for later
	// elements (0 <= k < j) as well, since min[p] >= min[q] for p < q. So while we
	// iterate backwards, the min will either remain same or increase, so we can
	// safely discard nums[k] off the stack as they are not potential nums[k] for
	// even the preceding elements, even after stack remains sorted. After
	// continuous popping inside while loop, we check if the stack is not empty and
	// if stack[top] < nums[j], if it's true we return true as we know stack[top] >
	// nums[i]. Otherwise we add this nums[j] as next suitable candidate for nums[k]
	// in the stack as current nums[j] > min[j]. As stack[top] >= nums[j], even
	// after pushing this element on the stack, the stack remains sorted. Also, no
	// other element in stack below top will satisfy this as all are > nums[min] and
	// thus >= nums[j] as well. The stack stores the potential nums[k] which may
	// satisfy 132 criteria in a descending order (minimum element on the top).
	// These elements stored in stack are sorted automatically. If no element is
	// found satisfying the 132 criteria till we reach the 1st element, we return
	// false.
	// Time complexity - O(n), we iterate nums of length n for finding min and then
	// we iterate backwards to find nums[k]. During this we populate stack or pop
	// the n elements once.
	// Space complexity - O(n), we use min array and stack can grow upto n elements.
	private static boolean find132patternMStack(int[] nums) {
		int n = nums.length;

		// Keeps track of nums[i]
		int[] min = new int[n];
		min[0] = nums[0];

		for (int i = 1; i < n; i++) {
			min[i] = Math.min(min[i - 1], nums[i]);
		}

		// Keeps track of nums[k]
		Stack<Integer> stack = new Stack<>();

		for (int j = n - 1; j >= 0; j--) {
			if (nums[j] > min[j]) {
				while (!stack.isEmpty() && stack.peek() <= min[j]) {
					stack.pop();
				}

				if (!stack.isEmpty() && stack.peek() < nums[j]) {
					return true;
				}

				stack.push(nums[j]);
			}
		}
		return false;
	}

	// Binary Search
	// In the last approach we used stack, but we can get rid of that. We calculate
	// min as it is but while iterating over nums in backward direction, we can see
	// that when we reach the index j, the stack can contain atmost n - j - 1
	// elements. Also this is the number of elements which lie beyond the jth index
	// in nums array. We know that these elements lying beyond the jth index in nums
	// won't be needed again in future. We can make use of this space in nums array
	// instead of a separate stack. The remaning steps are carried out in same
	// manner as stack approach. Since, we've an array for storing the potential
	// nums[k] values, we don't need to do the popping for a min[j] to find an
	// element > min[j] among potential values. Instead, we use Binary Search to
	// find an element, which is > min[j] in the required interval, if it exists. If
	// such an element is found, we can compare it with nums[j] to check if it
	// satisfies the 132 criteria. Else, we continue the process like the stack.
	// Time complexity - O(nlogn), filling min requires O(n) time. The 2nd traversal
	// is done over the whole nums array of length n. For every current nums[j] we
	// need to do Binary Search, which requires O(logn). In the worst case, the
	// Binary Search will be done for all the n elements, and the required element
	// is not found in any case, leading to O(nlogn)
	// Space complexity - O(n) for min array.
	private static boolean find132patternBinarySearch(int[] nums) {
		int n = nums.length;
		int[] min = new int[n];
		min[0] = nums[0];

		for (int i = 1; i < n; i++) {
			min[i] = Math.min(min[i - 1], nums[i]);
		}

		int k = n;
		for (int j = n - 1; j >= 0; j--) {
			if (nums[j] > min[j]) {
				k = Arrays.binarySearch(nums, k, n, min[j] + 1);
				if (k < 0) {
					k = -k - 1;
				}
				if (k < n && nums[k] < nums[j]) {
					return true;
				}

				nums[--k] = nums[j];
			}
		}
		return false;
	}

	// Intervals
	// In the last approach we tried to work only on nums[i], but there is a way to
	// work out on nums[j]. If we check the graph of nums = [2,3,5,6,4,1,2,2,4].
	// This graph consists of rising as well as falling slopes. Now to get the best
	// (nums[i], nums[j]) pair, we need to maximize the range nums[i], nums[j], at
	// any instant, while traversing nums array and this will be the endpoints of a
	// local rising slope only. Once we get this we use the current nums[i] as
	// nums[k] to find if it satisfies 132 criteria. To find the ends of such a
	// local rising slope, we traverse the nums array while keeping a track of the
	// minimum point found after the last peak (nums[s]) or it can be i = 0. When we
	// encounter a falling slope, say at index i, we know that nums[i - 1] was the
	// endpoint of the last rising slope found. We then scan over the k indices
	// (k>i) to find a 132 pattern. But instead of traversing over nums to find k
	// for every such rising slope, we can store the range (nums[s], nums[i-1])
	// which acts as (nums[i], nums[j]) in an intervals array. While traversing over
	// the nums array to check the rising / falling slopes, we keep adding the
	// endpoints of rising slope to this intervals array. At the same time, we can
	// also check if the current element falls in any of the ranges found so far
	// which satisfies the 132 criteria. If no such element is found till the end we
	// return false.
	// Time compelxity - O(n^2), we traverse over the nums array of size n, to find
	// the slopes. But for every element we also need to traverse the intervals to
	// check if any element falls in any range found so far. The array can contain
	// at most (n/2) pairs in worst case of an alterante increasing-decreasing
	// sequence. We keep on adding the rising slopes to intervals array, and check
	// the current nums element in each of the intervals leading to O(n^2)
	// time. Example - [5,6,4,7,3,8,2,9...]
	// Space complexity - O(n) for intervals list.
	private static boolean find132patternIntervals(int[] nums) {
		int n = nums.length;
		List<int[]> intervals = new ArrayList<>();

		int s = 0;

		for (int k = 1; k < n; k++) {
			// If we encounter a falling edge, then nums[i-1] is the peak
			if (nums[k] < nums[k - 1]) {
				// We ensure that the peak occurs after the rising edge's minimum
				if (s < k - 1) {
					// nums[min_point_post_last_peak ... (i-1)] is a valid rising peak.
					intervals.add(new int[] { nums[s], nums[k - 1] });
				}
				// The current element can be the minimum point for next rising peak.
				s = k;
			}

			for (int[] interval : intervals) {
				if (nums[k] > interval[0] && interval[1] > nums[k]) {
					return true;
				}
			}
		}

		return false;
	}

	// Better Brute Force
	// To improve the last approach, we can ignore nums[k] and we iterate for
	// nums[j] to find nums[i] (i < j) which is less than nums[j]. If we find the
	// pair (nums[i], nums[j]), we then find nums[k] for k > j by iterating over the
	// next elements after current j. To find nums[i] while choosing nums[j], we
	// keep track of the minimum element found so far (excluding nums[j]. This min
	// element always serves as the nums[i] for current nums[j]. Thus, we travese
	// farther than j to get num[k] which satisfies the 132 criteria.
	// Time complexity - O(n^2)
	// Space complexity - O(1)
	private static boolean find132patternBruteForceOpt(int[] nums) {
		int n = nums.length;

		int minI = Integer.MAX_VALUE;
//		int minI = nums[0];

		for (int j = 0; j < n; j++) { // for (int j = 1; j < n; j++) {
			minI = Math.min(minI, nums[j]);

			for (int k = j + 1; k < n; k++) {
				if (nums[k] < nums[j] && minI < nums[k]) {
					return true;
				}
			}
		}
		return false;
	}

	// Brute Force
	// The simplest solution is to consider every triplet (i, j, k) and check if the
	// corresponding numbers satisfy the 132 criteria.
	// Time complexity - O(n^3)
	// Space complexity - O(1)
	public static boolean find132patternBruteForce(int[] nums) {
		int n = nums.length;

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
//				if (nums[j] > nums[i]) {
				for (int k = j + 1; k < n; k++) {
					if (nums[k] < nums[j] && nums[i] < nums[k]) {
						return true;
					}
				}
//				}
			}
		}
		return false;
	}

}
