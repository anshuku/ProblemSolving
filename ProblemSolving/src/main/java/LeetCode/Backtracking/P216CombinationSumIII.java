package LeetCode.Backtracking;

import java.util.ArrayList;
import java.util.List;

/*
 * P216. Combination Sum III - Medium
 * 
 * Find all valid combinations of k numbers that sum up to n such that the following conditions are true:
 * 
 * - Only numbers 1 through 9 are used.
 * - Each number is used at most once.
 * 
 * Return a list of all possible valid combinations. The list must not contain the same 
 * combination twice, and the combinations may be returned in any order.
 * 
 * Approach - Backtracking
 */
public class P216CombinationSumIII {

	public static void main(String[] args) {

//		int k = 3, n = 7;
		int k = 3, n = 9;

		List<List<Integer>> list = combinationSum3(k, n);

		System.out.printf("The combination sum for %d elements having sum %d is " + list, k, n);

	}

	public static List<List<Integer>> combinationSum3(int k, int n) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();

		recursive(result, new ArrayList<>(), 1, k, n);
		return result;
	}

	private static void recursive(List<List<Integer>> result, List<Integer> list, int start, int k, int n) {
		if (k == list.size() && n == 0) {
			result.add(new ArrayList<Integer>(list));
			return;
		}
		for (int i = start; i <= 9; i++) {
			if (list.size() >= k || i > n) {
				break;
			}
			list.add(i);
			recursive(result, list, i + 1, k, n - i);
			list.remove(list.size() - 1);
		}
	}

}
