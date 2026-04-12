package LeetCode.BitManipulation;

import java.util.ArrayList;
import java.util.List;

/*
 * P78. Subsets - Medium
 * 
 * Given an integer array nums of unique elements, return all possible subsets (the power set).
 * 
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 * 
 * Approach - Backtracking, Bit Manipulation: Bitmasking
 * 
 * Subsets' solution space is quite large:  2^n since each element could be absent or present.
 * Similary for permutations: n! and combinations: C (k n) = n!/[(n-k)!*k!] are solution space.
 * As they've exponential solution space, it's tricky to ensure that the generated solutions 
 * are complete and non-redundant. For this clear and easy-to-reason strategy are:
 * Iterative, Recursion/Backtracking, Lexicographic generation where we map binary bitmasks
 * and the corresponding permutations/combinations/subsets. Last one is good for interviews
 * as we simplify the problem to binary numbers generation and it's easy to implement and 
 * verify that no solution is missing. It also generates lexicographic sorted output for
 * sorted inputs.
 */
public class P78Subsets {

	public static void main(String[] args) {
		int[] nums = { 1, 2, 3 };

		List<List<Integer>> subsetsLexicographicBS = subsetsLexicographicBinarySorted(nums);
		System.out
				.println("Lexicographic Binary Sorted: The subsets of the given array are: " + subsetsLexicographicBS);

		List<List<Integer>> subsetsLexicographicBSAlt = subsetsLexicographicBinarySortedAlt(nums);
		System.out.println(
				"Lexicographic Binary Sorted Alt: The subsets of the given array are: " + subsetsLexicographicBSAlt);

		List<List<Integer>> subsetsBT = subsetsBacktracking(nums);
		System.out.println("Backtracking: The subsets of the given array are: " + subsetsBT);

		List<List<Integer>> subsetsBTAlt = subsetsBacktrackingAlternate(nums);
		System.out.println("Backtracking Alternate: The subsets of the given array are: " + subsetsBTAlt);

		List<List<Integer>> subsetsCascading = subsetsCascading(nums);
		System.out.println("Cascading: The subsets of the given array are: " + subsetsCascading);
	}

	// Lexicographic(Binary Sorted) subsets
	// The idea came form Donald E. Knuth where we map each subset to a bitmask of
	// length n, where 1 on the ith position in bitmask means the presence of
	// nums[i] in the subset, and 0 means its absence. For instance, the bitmask
	// 0..00 (all zeroes) means an empty subset and the bitmask 1..11 (all ones)
	// means entire input array nums. So, we just need to generate n bitmasks from
	// 0..00 to 1..11. There is a problem of zero left padding, because one has to
	// generate bitmasks of fixed length, i.e. 001 and not just 1. We use a standard
	// bit manipulation trick. We find nthBit = 1 << n and the iterate [0 - nthBit):
	// Integer.toBinaryString(i | nthBit).substring(1)
	// or keep it simple and shift iteration limits from [2^n - 2^n+1).
	// Integer.toBinaryString(i).substring(1). Algo:
	// Generate all possible binary bitmasks of length n. Map a subset to each
	// bitmask: 1 on the ith position in bitmask means presence of nums[i] in the
	// subset, and 0 means its absence. Return output list.
	// Time complexity - O(n*2^n), to generate all subsets and then copy them into
	// output list.
	// Space complexity - O(n), to store the bitset of length n.
	private static List<List<Integer>> subsetsLexicographicBinarySorted(int[] nums) {
		int n = nums.length;
		int nthBit = 1 << n;
		List<List<Integer>> result = new ArrayList<>();

		for (int i = 0; i < nthBit; i++) { // equivalent to (int) Math.pow(2, n)
			// Generate bitmask, from 0..00 to 1..11
			char[] bitmask = Integer.toBinaryString(i | nthBit).substring(1).toCharArray();

			// append subset corresponding to that bitmask
			List<Integer> list = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				if (bitmask[j] == '1') {
					list.add(nums[j]);
				}
			}
			result.add(list);
		}
		return result;
	}

	private static List<List<Integer>> subsetsLexicographicBinarySortedAlt(int[] nums) {
		int n = nums.length;
		List<List<Integer>> result = new ArrayList<>();

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
		return result;
	}

	// Backtracking
	// Power set is all possible combinations of all possible lengths, from 0 to n.
	// Given the definition, the problem can also be interpreted as finding the
	// power set from a sequence. We loop over the length of combination, rather
	// than the candidate numbers, and generate all combinations for a given length
	// with help of backtracking technique. Algo:
	// We define a backtrack function named backtrack(first, curr) which takes the
	// index of the 1st element to add and a current combination as arguments.
	// We add the current combination(done?) to the final output. We then iterate
	// over the indices i from first to the length of entire sequence n. Add integer
	// nums[i] into the current combination curr. Proceed to add more integers into
	// the combination: backtrack(i + 1, curr). Backtrack by removing nums[i] from
	// curr.
	// Time complexity - O(n*2^n) to generate all subsets and then copy them into
	// the result.
	// Space complexity - O(n), we use O(n) space to maintain curr, and are
	// modifying curr in-place with backtracking. We do not count space that is only
	// used for the purpose of returning output so result list is ignored.
	private static List<List<Integer>> subsetsBacktracking(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		backtracking(nums, 0, new ArrayList<>(), result);
		return result;
	}

	private static void backtracking(int[] nums, int index, List<Integer> list, List<List<Integer>> result) {
		// Add current subset to the result
		result.add(new ArrayList(list));

		// Generate subsets starting from the current index.
		for (int i = index; i < nums.length; i++) {
			list.add(nums[i]);
			backtracking(nums, i + 1, list, result);
			list.removeLast();
		}
	}

	public static List<List<Integer>> subsetsBacktrackingAlternate(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		backtrackingAlternate(nums, 0, new ArrayList<>(), result);
		return result;
	}

	private static void backtrackingAlternate(int[] nums, int index, List<Integer> list, List<List<Integer>> result) {
		if (index == nums.length) {
			result.add(new ArrayList<>(list));
			return;
		}
		list.add(nums[index]);
		backtrackingAlternate(nums, index + 1, list, result);
		list.removeLast();

		backtrackingAlternate(nums, index + 1, list, result);
	}

	// Cascading
	// We start from an empty subset in the output list. At each step, we take a new
	// integer and generate new substes from the existing ones.
	// Time complexity - O(n*2^n), to generate all subsets and then copy them into
	// output list.
	// Space complexity - O(n*2^n), it's the number of solutions for subsets *
	// number n of elements to keep for each subset(average n/2?).
	// For a given number, it could be present or absent(binary choice) in a subset
	// solution. As a result, for N numbers, we'd have total 2^n choices(solutions).
	private static List<List<Integer>> subsetsCascading(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		result.add(new ArrayList<>());

		for (int num : nums) {
			List<List<Integer>> newSubsets = new ArrayList<>();
			for (List<Integer> list : result) {
				List<Integer> newList = new ArrayList<>(list);
				newList.add(num);
				newSubsets.add(newList);
			}
			for (List<Integer> list : newSubsets) {
				result.add(list);
			}
		}
		return result;
	}
}
