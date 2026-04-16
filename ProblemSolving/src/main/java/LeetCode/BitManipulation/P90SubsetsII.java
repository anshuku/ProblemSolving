package LeetCode.BitManipulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * P90. Subsets II - Medium
 * 
 * Given an integer array nums that may contain duplicates, return all possible subsets (the power set).
 * 
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 * 
 * Approach - Backtracking, Bit Manipulation: Bitmasking
 */
public class P90SubsetsII {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 2 };
//		int[] nums = {};
//		int[] nums = { 0 };
		int[] nums = { 4, 4, 4, 1, 4 };

		List<List<Integer>> subsetsBitmask = subsetsWithDupBitmask(nums);
		System.out.println("Bitmask: The possible subsets are: " + subsetsBitmask);

		List<List<Integer>> subsetsBitmaskString = subsetsWithDupBitmaskString(nums);
		System.out.println("Bitmask String: The possible subsets are: " + subsetsBitmaskString);

		List<List<Integer>> subsetsBitmaskStringAlt = subsetsWithDupBitmaskStringAlt(nums);
		System.out.println("Bitmask String Alt: The possible subsets are: " + subsetsBitmaskStringAlt);

		List<List<Integer>> subsetsBacktracking = subsetsWithDupBacktracking(nums);
		System.out.println("Backtracking: The possible subsets are: " + subsetsBacktracking);

		List<List<Integer>> subsetsBacktrackingAlt = subsetsWithDupBacktrackingAlt(nums);
		System.out.println("Backtracking Alternate: The possible subsets are: " + subsetsBacktrackingAlt);

		List<List<Integer>> subsetsBacktrackingListAlt = subsetsWithDupBacktrackingListAlt(nums);
		System.out.println("Backtracking List Alternate: The possible subsets are: " + subsetsBacktrackingListAlt);

		List<List<Integer>> subsetsCascading = subsetsWithDupCascading(nums);
		System.out.println("Cascading: The possible subsets are: " + subsetsCascading);

		List<List<Integer>> subsetsCascadingAlt = subsetsWithDupCascadingAlt(nums);
		System.out.println("Cascading Alt: The possible subsets are: " + subsetsCascadingAlt);
	}

	// Bitmasking
	// Time complexity - O(n*2^n), sorting the nums array required n*logn time. The
	// outer for loop runs 2^n times. The inner loop runs n tmes. The average case
	// time complexity of set operations is O(1). Overall, O(nlogn + 2^n*n).
	// Space complexity - O(logn), we need to sort the nums which in
	// Arrays.sort() is implemented as variant of quicksort algo which takes O(logn)
	// space. We need O(1) space for auxiliary variables.
	private static List<List<Integer>> subsetsWithDupBitmask(int[] nums) {
		// Sort the nums to gelp identify duplicates.
		Arrays.sort(nums);
		Set<List<Integer>> result = new HashSet<>();

		int n = nums.length;
		int nthBit = 1 << n;

		for (int i = 0; i < nthBit; i++) {
			// Append the subset corresponding to the bitmask.
			List<Integer> list = new ArrayList<>();

			for (int j = 0; j < n; j++) {
				// Generate the bitmask
				int mask = 1 << j;

				if ((mask & i) != 0) {
					list.add(nums[j]);
				}
			}

			result.add(list);
		}

		return new ArrayList<>(result);
	}

	// Bitmasking
	// The maximum number of subsets exists when nums contains unique numbers only.
	// In a subset, each number could bee present/absent. For n numbers, the power
	// set will consist of <= 2^n susbets - so time taken is also O(2^n). THe max
	// value of n can be 10. So no more than 2^10 = 1024 subsets will be generated.
	// Since this number is small, we can represent all the subsets via
	// bitmasking. We map each subset to a bitmask of length n, where 1 on the ith
	// position in bitmask means the presence of nums[i] in the subset, and 0 means
	// its absence. The bitmask 0..00 (all zeroes) means an empty subset and the
	// bitmask 1..11 (all ones) means entire input array nums is present in set. The
	// bitmask values from 0 to 2^n - 1 represent all possible subsets. We iterate
	// over binary representation of each number from 0 to 2^n-1 and depending on
	// the position of set bits(bit value = 1) we can include numbers in current
	// subset. An unsigned integer can respesent at most 32 positions. Here array
	// size is <= 10 which is < 32. So we can use bitmasking to respresent all
	// subsets. Here some of the generated subsets will be duplicates. We need to
	// use an additional set seen, to find duplicate subsets. To use the seen, we
	// must 1st sort the given array. Otherwise we won't be able to distinguish
	// between duplicates. Example nums = [2,1,2], then we get subsets = [], [2],
	// [1], [2], [2,1], [2,2], [1,2], [2,1,2]. Here, subset [1,2] is duplicate of
	// [2,1]. To find these duplicates prior sorting of the array is needed. This
	// ensures all the included subsets are unique.
	// Time complexity - O(n*2^n), sorting the nums array required n*logn time. The
	// outer for loop runs 2^n times. The inner loop runs n tmes. The average case
	// time complexity of set operations is O(1). Overall, O(nlogn + 2^n*n).
	// Space complexity - O(n + logn), we need to sort the nums which in
	// Arrays.sort() is implemented as variant of quicksort algo which takes O(logn)
	// space. We need O(n) space for bitmask.
	private static List<List<Integer>> subsetsWithDupBitmaskString(int[] nums) {
		Arrays.sort(nums);
		Set<List<Integer>> result = new HashSet<>();

		int n = nums.length;

		int nthBit = 1 << n;

		for (int i = 0; i < nthBit; i++) {
			char[] bitmask = Integer.toBinaryString(i | nthBit).substring(1).toCharArray();

			List<Integer> list = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				if (bitmask[j] == '1') {
					list.add(nums[j]);
				}
			}

			result.add(list);
		}

		return new ArrayList<>(result);
	}

	private static List<List<Integer>> subsetsWithDupBitmaskStringAlt(int[] nums) {
		Arrays.sort(nums);
		int n = nums.length;

		Set<List<Integer>> result = new HashSet<>();

		for (int i = (int) Math.pow(2, n); i < (int) Math.pow(2, n + 1); i++) {
			char[] bitmask = Integer.toBinaryString(i).substring(1).toCharArray();

			List<Integer> list = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				if (bitmask[j] == '1') {
					list.add(nums[j]);
				}
			}

			result.add(list);
		}

		return new ArrayList<>(result);
	}

	private static List<List<Integer>> subsetsWithDupBacktracking(int[] nums) {
		Arrays.sort(nums);
		Set<List<Integer>> result = new HashSet<>();
		backtracking(nums, 0, new ArrayList<>(), result);
		return new ArrayList<>(result);
	}

	private static void backtracking(int[] nums, int index, List<Integer> list, Set<List<Integer>> result) {
		result.add(new ArrayList<>(list));

		for (int i = index; i < nums.length; i++) {
			list.add(nums[i]);
			backtracking(nums, i + 1, list, result);
			list.removeLast();
		}
	}

	private static List<List<Integer>> subsetsWithDupBacktrackingAlt(int[] nums) {
		Arrays.sort(nums);
		Set<List<Integer>> result = new HashSet<>();
		backtrackingAlt(nums, 0, new ArrayList<Integer>(), result);
		return new ArrayList<>(result);
	}

	private static void backtrackingAlt(int[] nums, int index, List<Integer> list, Set<List<Integer>> result) {
		if (index == nums.length) {
			result.add(new ArrayList<>(list));
			return; // return, else the nums[index] in next line gives
					// ArrayIndexOutOfBoundsException
		}
		list.add(nums[index]);
		backtrackingAlt(nums, index + 1, list, result);
		list.removeLast();

		backtrackingAlt(nums, index + 1, list, result);
	}

	private static List<List<Integer>> subsetsWithDupBacktrackingListAlt(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> result = new ArrayList<>();
		backtrackingListAlt(nums, 0, new ArrayList<>(), result);
		return result;
	}

	private static void backtrackingListAlt(int[] nums, int index, List<Integer> list, List<List<Integer>> result) {
		result.add(new ArrayList<>(list));

		for (int i = index; i < nums.length; i++) {
			// If the current subset is a duplicate, ignore it.
			if (i != index && nums[i] == nums[i - 1]) {
				continue;
			}

			// It means we haven't seen the current element before, add it to list.
			list.add(nums[i]);
			backtrackingListAlt(nums, i + 1, list, result);
			list.removeLast();
		}

	}

	// Cascading
	public static List<List<Integer>> subsetsWithDupCascading(int[] nums) {
		Arrays.sort(nums);
		Set<List<Integer>> result = new HashSet<>();
		result.add(new ArrayList<>());

		for (int num : nums) {
			Set<List<Integer>> curr = new HashSet<>();

			for (List<Integer> list : result) {
				List<Integer> newList = new ArrayList<>(list);
				newList.add(num);
				curr.add(newList);
			}
			for (List<Integer> list : curr) {
				result.add(list);
			}
		}

		return new ArrayList<>(result);
	}

	private static List<List<Integer>> subsetsWithDupCascadingAlt(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> result = new ArrayList<>();
		result.add(new ArrayList<>());

		int subsetSize = 0;

		int n = nums.length;
		for (int i = 0; i < n; i++) {
			int start = (i > 0 && nums[i] == nums[i - 1]) ? subsetSize : 0;
			subsetSize = result.size();

			for (int j = start; j < subsetSize; j++) {
				List<Integer> newList = new ArrayList<>(result.get(j));
				newList.add(nums[i]);
				result.add(newList);
			}
		}

		return new ArrayList<>(result);
	}
}
