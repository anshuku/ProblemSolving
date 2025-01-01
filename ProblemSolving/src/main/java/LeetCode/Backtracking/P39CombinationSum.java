package LeetCode.Backtracking;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * P39. Combination Sum
 * 
 * Given an array of distinct integers candidates and a target integer target, 
 * return a list of all unique combinations of candidates where the chosen numbers 
 * sum to target. You may return the combinations in any order.
 * 
 * The same number may be chosen from candidates an unlimited number of times. 
 * Two combinations are unique if the frequency of at least one of the chosen numbers is different.
 * 
 * The test cases are generated such that the number of unique combinations that 
 * sum up to target is less than 150 combinations for the given input.
 * 
 * All elements of candidates are distinct.
 * 
 * Approach - Backtracking
 * 
 * Here we need to generate combinations with the given target sum
 * Backtracking is used to incrementally generate all the combinations.
 * The conditions are used to eliminate combinations(backtrack) which are not needed.
 * 
 * The solution is applicable for non negative input.
 */
public class P39CombinationSum {

	public static void main(String[] args) {

//		int[] candidates = { 2, 3, 6, 7 };
//		int target = 7;

		int[] candidates = { 8, 7, 4, 3 };
		int target = 11;

		List<List<Integer>> combinationsAbstract = combinationSumAbstract(candidates, target);
		System.out.printf("Abstract List: The combinations for elements having sum %d is - " + combinationsAbstract,
				target);

		List<List<Integer>> combinationsNoBreak = combinationSumNoBreak(candidates, target);
		System.out.printf("\nNo break: The combinations for elements having sum %d is - " + combinationsNoBreak,
				target);

		List<List<Integer>> combinationsSort = combinationSumSort(candidates, target);
		System.out.printf("\nSorting and Break: The combinations for elements having sum %d is - " + combinationsSort,
				target);

	}

	private static List<List<Integer>> combinationSumAbstract(int[] candidates, int target) {
		return new AbstractList<List<Integer>>() {

			List<List<Integer>> result;

			public int size() {
				if (result == null) {
					init();
				}
				return result.size();
			}

			public List<Integer> get(int index) {
				return result.get(index);
			}

			public void init() {
				result = new ArrayList<List<Integer>>();
				recursive(result, new ArrayList<>(), candidates, 0, target);
			}
		};
	}

	// Time Complexity - O(2^n) worst case, all subsets of candidates are explored.
	// Space Complexity - O(t/d) where t is target and d is smallest candidate.
	// For recursion stack and temporary list
	private static List<List<Integer>> combinationSumNoBreak(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<>();
		recursive(result, new ArrayList<>(), candidates, 0, target);
		return result;
	}

	private static void recursive(List<List<Integer>> result, List<Integer> list, int[] candidates, int start,
			int target) {
		if (target == 0) {
			result.add(new ArrayList<Integer>(list));
		} else {
			for (int i = start; i < candidates.length; i++) {
				// Can't use break since the candidates array might not be sorted
				if (candidates[i] <= target) {
					list.add(candidates[i]);
					// Passing index i as start since the candidate can be reused
					recursive(result, list, candidates, i, target - candidates[i]);
					list.remove(list.size() - 1); // Java 21: list.removeLast();
				}
			}
		}
	}

	public static List<List<Integer>> combinationSumSort(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		Arrays.sort(candidates);
		recursiveSort(result, new ArrayList<Integer>(), candidates, 0, target);
		return result;
	}

	private static void recursiveSort(List<List<Integer>> result, ArrayList<Integer> list, int[] candidates, int start,
			int target) {
		if (target == 0) {
			result.add(new ArrayList<Integer>(list));
			return;
		}
		for (int i = start; i < candidates.length; i++) {
			if (candidates[i] > target) {
				break;
			}
			list.add(candidates[i]);
			// Passing index i as start since the candidate can be reused
			recursiveSort(result, list, candidates, i, target - candidates[i]);
			list.remove(list.size() - 1);
		}

	}

}
