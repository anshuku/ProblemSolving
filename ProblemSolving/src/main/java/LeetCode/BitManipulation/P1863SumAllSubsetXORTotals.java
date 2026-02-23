package LeetCode.BitManipulation;

import java.util.ArrayList;
import java.util.List;

/*
 * P1863. Sum of All Subset XOR Totals - Easy
 * 
 * The XOR total of an array is defined as the bitwise 
 * XOR of all its elements, or 0 if the array is empty.
 * 
 * > For example, the XOR total of the array [2,5,6] is 2 XOR 5 XOR 6 = 1.
 * 
 * Given an array nums, return the sum of all XOR totals for every subset of nums. 
 * 
 * Note: Subsets with the same elements should be counted multiple times.
 * 
 * An array a is a subset of an array b if a can be obtained 
 * from b by deleting some (possibly zero) elements of b.
 * 
 * Approach - Bit Manipulation, DFS, Backtracking
 */
public class P1863SumAllSubsetXORTotals {

	public static void main(String[] args) {
//		int[] nums = { 1, 3 };
//		int[] nums = { 5, 1, 6 };
		int[] nums = { 3, 4, 5, 6, 7, 8 };

		int subsetXORSumBM = subsetXORSumBitManipulation(nums);
		System.out.println("Bit Manipulation: The sum of all subset XOR total is: " + subsetXORSumBM);

		int subsetXORSumDfs = subsetXORSumDfs(nums);
		System.out.println("DFS: The sum of all subset XOR total is: " + subsetXORSumDfs);

		int subsetXORSumDfsAlt = subsetXORSumDfsAlt(nums);
		System.out.println("DFS Alt: The sum of all subset XOR total is: " + subsetXORSumDfsAlt);

		int subsetXORSumList = subsetXORSumList(nums);
		System.out.println("List: The sum of all subset XOR total is: " + subsetXORSumList);

		int subsetXORSumBT = subsetXORSumBacktracking(nums);
		System.out.println("Backtracking: The sum of all subset XOR total is: " + subsetXORSumBT);

	}

	// Bit Manipulation
	// XOR is a bitwise operation and working backward helps to develop bit
	// manipulation apporaches. In the result, below bits are set.
	// Input: nums = [1,3] (n=2) output = 6 = 110
	// Input: nums = [5,1,6] (n=3) output = 28 = 11100
	// Input: nums = [3,4,5,6,7,8] (n=6) output = 480 = 111100000
	// Observing patterns we find the least significant(rightmost) n-1 bits in the
	// binary representation are 0. Testing more, Input:nums=[1] (n=1), output =1=1
	// 1-1 = 0, so the least significant n-1(0) bits in the binary are still 0. All
	// test cases follows this pattern. So we find the bits that need to be set,
	// then shift them by n-1 to get result. The most significant bits in the output
	// may or may not be 1. Input: nums = [5,20] (n=2) output = 42 = 101010.
	// By observation, we check that every bit that is set in any of the elements is
	// set in the output. The OR operator is true for a bit position if that bit
	// position is set for any of the elements in the input. So we calculate a
	// running OR pf each of the elements in nums and save it in result. Tnen we
	// append n-1 zeroes to the right of the binary representation by shifting the
	// result by n-1 towards left.
	// How this works?
	// We find the number of times each bit is set in all of the subset XOR totals,
	// and use it to set the appropriate bits in the result. We use the properties:
	// The XOR of 2 equal numbers is 0. The XOR total of empty set is 0. For more
	// than 2 operands, the XOR operation is true when an odd number of them are
	// true. For a bit position to be set in the subset XOR total, it must be set in
	// an odd number of elements in the subset.
	// > For a given element, how many subsets will include it? When nums contains N
	// elements, the total number of subsets is 2^n(including empty). A particular
	// element is included in half of these so 2^(n-1).
	// > For a given bit position x, how many subset XOR totals have xth bit set?
	// If the xth bit is not set in any of the elements. none of the subset XOR
	// total have the xth bit set. If the xth bit is set in exactly 1 of the
	// elements, it'll be set in half of the XOR totals because half of the subsets
	// contain that element. If the xth bit is set in more than 1 of the elements,
	// it'll be set in half of the subset XOR totals. Consider nums has 2 elements
	// with xth bit set. The xth bit is not set in the XOR total of the empty
	// subset. For the 2 subsets with 1 element, the xth bts is set in both XOR
	// totals. Also, it'll not be set in XOR total of the subset with both elements.
	// So, the xth bit will be set in 2/4 or half of the subset XOR totals. We call
	// this set of subsets A. If we add an element with the xth bit set to nums, all
	// of the A subsets will still be included. Apart from this there will be new
	// subsets which has 1 of A's subsets and the new element. For each of these new
	// subsets if the xth bit of the XOR total was 0 in corresponding subset in A,
	// it'll be 1 in new subset and vice versa. It means the xth bit will be set for
	// half of the new subsets. Since, the xth bit was also set for half of the A
	// subsets, the xth bit will be set for half of the total subsets. Adding
	// another element that has the xth bits set to a subset creates a new subset
	// for each of the original subsets. The xth bit will be flipped in XOR total
	// for each each new subsets, so the xth bit will be set in half the subsets.
	// It means for each bit that is set in any of the numbers in nums, the bit will
	// be set in half of the subsets. So, we take OR of all of the elements to
	// capture evety bit that is set in any of the elements and store in result. If
	// a bit is set in any element at least once, it's value will be added to sum
	// exactly 2^(n-1) times. Input: nums = [1,3] (n=2) output = 6 = 11-.
	// 2^(n-1) = 2^(2-1) = 2. the 1st bit is set in 2 of the subsets: 1*2 = 2
	// The second bit is set in 2 of the subsets: 2*2 = 4, 2+4 = 6.
	// We multiply the result containing the set bit positions by the number of
	// subsets each bit is set in, 2^(n-1), using shift operation: result << (n-1).
	// Time complexity - O(n), we travese through nums to find running OR.
	// Space complexity - O(1).
	private static int subsetXORSumBitManipulation(int[] nums) {
		int sum = 0;
		// We capture the bit that is set in any of the elements
		for (int num : nums) {
			sum |= num;
		}
		// Multiply it by the number of subset XOR totals that'll have each bit set.
		return sum << (nums.length - 1); // or sum * (1 << (nums.length - 1))
	}

	// Optimized backtracking
	// We calculate the running XOR totals and sum them while generating each
	// subset. We calculate the running XOR total for current subset by passing the
	// XOR of the running XOR and current element's index in nums as a parameter to
	// helper function dfs. For the current subset, we save the XOR total by adding
	// the element to subset in variable withElement and XOR total by not adding the
	// element in the variable withoutElement. Each of these variables represents
	// the XOR total of a different subset, so we return their sum to compute the
	// running total for these 2 subsets. We use recursive function dfs for this to
	// calculate the sum of the subset XOR totals. Base case: When index equals the
	// size of nums, the current subset is complete, return currentXOR.
	// Time complexity - O(2^n), where n = nums size. We traverse through each of
	// the 2^n subsets to calculate the result.
	// Space complexity - O(n), the recursion depth can reach n beacuse we calculate
	// the XOR totals for each of the N indices in nums. So, the recursive call
	// stack may require O(n) space.
	private static int subsetXORSumDfs(int[] nums) {
		return dfsInt(nums, 0, 0);
	}

	private static int dfsInt(int[] nums, int index, int xorSum) {
		if (index == nums.length) {
			return xorSum;
		}
		int withNum = dfsInt(nums, index + 1, xorSum ^ nums[index]);
		int withoutNum = dfsInt(nums, index + 1, xorSum);
		return withNum + withoutNum;
	}

	// Time complexity - O(2^n), where n = nums size. We traverse through each of
	// the 2^n subsets to calculate the result.
	// Space complexity - O(n), the recursion depth can reach n beacuse we calculate
	// the XOR totals for each of the N indices in nums. So, the recursive call
	// stack may require O(n) space.
	public static int subsetXORSumDfsAlt(int[] nums) {
		dfs(nums, 0, 0);
		return totalSum;
	}

	static int totalSum = 0;

	private static void dfs(int[] nums, int index, int xorSum) {
		if (nums.length == index) {
			totalSum += xorSum;
			return;
		}
		dfs(nums, index + 1, xorSum ^ nums[index]);
		dfs(nums, index + 1, xorSum);
	}

	private static int subsetXORSumList(int[] nums) {
		List<Integer> sumList = new ArrayList<>();
		sumList.add(0);
		for (int num : nums) {
			int size = sumList.size();
			for (int i = 0; i < size; i++) {
				sumList.add(sumList.get(i) ^ num);
			}
		}
		int totalSum = 0;
		for (int sum : sumList) {
			totalSum += sum;
		}
		return totalSum;
	}

	// Backtracking: generate all subsets
	// We generate all the subsets, calculate XOR total of each subset, return the
	// sum of subset XOR totals. We generate all subsets using backtracking in list
	// of list. Function generateSubsets reursively generates all subsets for nums.
	// For each element in nums, we can call generateSubsets and include it in the
	// subset or not include it. For 1st element, we can build subset in two ways:
	// 1. Include the elements in subset and continue choosing other elements: Add
	// the element to subset, call generateSubsets with next index, then remove the
	// element from the subset to explore other subsets. 2. Not include the element
	// in subset and continue choosing other elements: call generateSubsets with
	// next index. Base case: When we pass last index of nums where there are no
	// more elements to add to the subset. We add the subset to the list of subsets
	// and return. We use 2 for loops to calculate sum of subset XOR totals. Outer
	// loop for iterating through subsets and inner loop for finding XOR sum of each
	// subset.
	// Time complexity - O(n*2^n), each element can be included or excluded from any
	// subset, so there are 2^n subsets which takes O(2^n) time. We iterate through
	// 2^n subsets to find xor total sum. The average size of each subset is approx
	// n/2, so it takes O(n/2*2^n). So overall O(2^n + n/2*2^n).
	// Space complexity - O(n*2^n), the subsets list will contains 2^n subsets with
	// an average size of n/2, so O(n/2*2^n) space. The recursion depth can reach
	// size N because we genearate subsets with and without each element in nums or
	// O(n) space. So overall O(2^n + n/2*2^n).
	private static int subsetXORSumBacktracking(int[] nums) {
		List<List<Integer>> subsets = new ArrayList<>();

		// Generate all subsets
		generateSubsets(nums, 0, new ArrayList<>(), subsets);

		// Compute the XOR total for each subset and add to the result.
		int xorTotalSum = 0;
		for (List<Integer> subset : subsets) {
			int xorSubset = 0;
			for (int num : subset) {
				xorSubset ^= num;
			}
			xorTotalSum += xorSubset;
		}
		return xorTotalSum;
	}

	private static void generateSubsets(int[] nums, int index, List<Integer> subset, List<List<Integer>> subsets) {
		// Base case: index reached end of nums
		// WE add current subset to subsets.
		if (index == nums.length) {
			subsets.add(new ArrayList<>(subset));
			return;
		}

		// Generate susbets including nums[i]
		subset.add(nums[index]);
		generateSubsets(nums, index + 1, subset, subsets);
		subset.removeLast();

		// Generate subsets without nums[i]
		generateSubsets(nums, index + 1, subset, subsets);
	}
}
