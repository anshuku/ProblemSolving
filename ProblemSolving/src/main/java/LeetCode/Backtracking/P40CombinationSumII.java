package LeetCode.Backtracking;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * P40. Combination Sum II - Medium
 * 
 * Given a collection of candidate numbers (candidates) and a target number (target), 
 * find all unique combinations in candidates where the candidate numbers sum to target.
 * 
 * Each number in candidates may only be used once in the combination.
 * 
 * Note: The solution set must not contain duplicate combinations.
 * 
 * Pruning is the process of writing some additional conditions within the recursion code 
 * which helps to reduce the size of the recursion trees by removing redundant sections.
 * 
 * Example of pruning - Unsorted: 1 x 1 x 1 -> Total number of ways to pick 1s - 2^3
 * We've a choice to pick or not to pick 1.
 * Sorted: 1 1 1 x x -> Total ways to pick 1s - 4
 * Combinations 1, 1 1, 1 1 1, null
 * 
 * Approach - Backtracking
 */
public class P40CombinationSumII {

	public static void main(String[] args) {

		int[] candidates = { 10, 1, 2, 7, 6, 1, 5, 5, 6 };
		int target = 8;

//		int[] candidates = { 1, 1, 2, 2, 3, 2, 3, 6, 1, 3, 5 };
//		int target = 6;

		List<List<Integer>> combinationsAbstract = combinationSum2Abstract(candidates, target);
		System.out.printf("Abstract List: The unique combinations having sum %d is - " + combinationsAbstract, target);

		List<List<Integer>> combinations = combinationSum2(candidates, target);
		System.out.printf("\nThe unique combinations having sum %d is - " + combinations, target);

	}

	private static List<List<Integer>> combinationSum2Abstract(int[] candidates, int target) {

		return new AbstractList<List<Integer>>() {

			List<List<Integer>> result = null;

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
				if (result == null) {
					result = new ArrayList<>();
				}
				Arrays.sort(candidates);
				recursive(result, new ArrayList<>(), candidates, 0, target);
			}
		};
	}

	// Time complexity - O(2^n) where n is candidates length - elements are
	// either included / excluded. All the subsets of candidates are explored.
	// O(n*logn) for sorting
	// Space complexity - O(t/d) where t is target and d is min candidate
	// due to recursion stack and temporary list.
	public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<>();
		// Sort to skip duplicates
		Arrays.sort(candidates);
		recursive(result, new ArrayList<>(), candidates, 0, target);
		return result;
	}

	private static void recursive(List<List<Integer>> result, List<Integer> list, int[] candidates, int start,
			int target) {
		if (target == 0) {
			result.add(new ArrayList<Integer>(list));
			return;
		}
		for (int i = start; i < candidates.length; i++) {
			// Skip duplicates at same level of recursion
			// start determines level of recursion.
			if (i > start && candidates[i] == candidates[i - 1]) {
				continue;
			}
			// If current target is less than candidates, stop further
			// since the array is already sorted.
			if (candidates[i] > target) {
				break;
			}
			list.add(candidates[i]);
			// Passing index i+1 as start since the candidate can't be reused 
			recursive(result, list, candidates, i + 1, target - candidates[i]);
			list.remove(list.size() - 1);
		}

	}

}
