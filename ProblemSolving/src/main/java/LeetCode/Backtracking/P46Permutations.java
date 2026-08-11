package LeetCode.Backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* 
 * P46. Permutations - Medium
 * 
 * Given an array nums of distinct integers, return all the possible permutations. 
 * You can return the answer in any order.
 * 
 * Approach - Backtracking
 */
public class P46Permutations {
	static int counter = 0;
	static int swapCounter = 0;

	public static void main(String[] args) {

		// 65(2^6) 128(2^7) | 16(2^4) 30(2^5)
		int[] nums = { 1, 2, 3 };

		List<List<Integer>> listArray = permuteArray(nums);
		System.out.println("Permute Array: Permutations are " + listArray);
//		System.out.println("Permute: counter " + counter + " swap counter " + swapCounter);

		List<List<Integer>> ans = permuteBacktrack(nums);
		System.out.println("Backtrack: Permutations are " + ans);
//		System.out.println("Backtrack: counter " + counter);

		counter = 0;
		recursion(nums, new ArrayList<Integer>(), new boolean[nums.length]);
		System.out.println("Recursion: Permutations are " + globalResult);
//		System.out.println("Recursion: counter " + counter);

		List<List<Integer>> list = permuteList(nums);
		System.out.println("Permute List: Permutations are " + list);

		permuteListAlt(nums);
		System.out.println("Permute List Alt: Permutations are " + result);
//		System.out.println("Permute List: counter " + counter + " swap counter " + swapCounter);

	}

	// Backtracking
	// Backtracking is an algorithm for finding all solutions by exploring all
	// potential candidates. If the solution candidate turns to be not a solution
	// (or at least not the last one), backtracking algorithm discards it by making
	// some changes on the previous step, i.e. backtracks and then try again.
	// Here is a backtrack function which takes the index of the 1st integer to
	// consider as an argument backtrack(first).
	// * If the 1st index to consider has index n that means the current permutation
	// is done. So we add it to result.
	// * Iterate over the integers from index first to index n-1.
	// -> Place i-th integer first in permutation, i.e. swap(nums[first], nums[i])
	// -> Proceed to create all permutations which starts from i-th integer:
	// backtrack(first + 1).
	// -> Now backtrack, i.e. swap(nums[first], nums[i]) back.
	// Time complexity - O(∑ k = 1 -> N P(N, k)) where P(N, k) = N!/(N-k)! =
	// N*(N-1)*...(N-k+1) is so-called k permutations of n or partial permutation.
	// Here, first+1 = k for the expression simplicity. The formula is: for each k
	// (each first) one performs N*(N-1)*..(N-k+1) operations, and k is going
	// through the range of values from 1 to N (and first from 0 to N-1).
	// Let's do a rough estimation of the result:
	// N! <= ∑ k = 1 -> N N!/(N-k)! = ∑ k = 1 -> N P(N, k) <= N*N!, i.e. the
	// algorithm performs better than O(N*N!) and a bit slower than O(N!).
	// Space complexity - O(N!) since one has to keep N! solutions
	private static List<List<Integer>> permuteArray(int[] nums) {
		counter = 0;
		swapCounter = 0;
		List<List<Integer>> ans = new ArrayList<>();
		permute(nums, ans, 0);
		return ans;
	}

	private static void permute(int[] nums, List<List<Integer>> result, int index) {
		counter++;
		if (nums.length == index) {
			List<Integer> list = new ArrayList<>();
			for (int num : nums) {
				list.add(num);
			}
			result.add(list);
			return;
		} else {
			for (int i = index; i < nums.length; i++) {
				// Place i-th integer first in the current permutation.
				swap(nums, index, i);
				// Use next integers to complete the permutations
				permute(nums, result, index + 1);
				// Backtrack
				swap(nums, index, i);
			}
		}
	}

	private static void swap(int[] nums, int s, int e) {
		swapCounter++;
		if (s == e) {
			return;
		}
		int temp = nums[s];
		nums[s] = nums[e];
		nums[e] = temp;
	}

	private static List<List<Integer>> permuteBacktrack(int[] nums) {
		counter = 0;
		List<List<Integer>> ans = new ArrayList<>();
		return backtrack(nums, new ArrayList<Integer>(), ans);
	}

	private static List<List<Integer>> backtrack(int[] nums, List<Integer> list, List<List<Integer>> ans) {
		counter++;
		if (list.size() == nums.length) {
			ans.add(new ArrayList<>(list));
			return ans;
		} else {
			for (int i = 0; i < nums.length; i++) {
				if (!list.contains(nums[i])) {
					list.add(nums[i]);
					backtrack(nums, list, ans);
					list.remove(list.size() - 1);
				}
			}
		}
		return ans;
	}
	// 1st for loop i = 0 (1) (-1) -> add i = 1 (2) (-2) | add i = 2 (3) (-3)

	// 2nd for loop i = 1 (2) (-2) -> add i = 2 (3) (-3) |
	// add i = 0 (1) (-1) | add i = 2 (3) (-3) | add i = 0 (1) (-1) | add i = 1 (2)
	// (-2)

	// 3rd for loop i = 2 (3) added 123 (-3) | 3rd i = 1 (2) added 132 (-2) |
	// add i = 2 (3) add 213 (-3) | add i = 0 (1) add 231 (-1) | add i = 1 (2) add
	// 312 (-2)
	// add i = 0 (1) add 321 (-1)

	static List<List<Integer>> globalResult = new ArrayList<>();

	private static void recursion(int[] nums, List<Integer> list, boolean[] visited) {
		counter++;
		if (list.size() == nums.length) {
			globalResult.add(new ArrayList<>(list));
			return;
		} else {
			for (int i = 0; i < nums.length; i++) {
				if (!visited[i]) {
					list.add(nums[i]);
					visited[i] = true;
					recursion(nums, list, visited);
					visited[i] = false;
					list.remove(list.size() - 1);
				}
			}
		}
	}

	private static List<List<Integer>> permuteList(int[] nums) {
		counter = 0;
		swapCounter = 0;
		List<Integer> list = new ArrayList<>();

		for (int num : nums) {
			list.add(num);
		}
		List<List<Integer>> result = new ArrayList<>();
		permuteList(0, list, result);
		return result;
	}

	private static void permuteList(int index, List<Integer> nums, List<List<Integer>> result) {
		if (index == nums.size()) {
			result.add(new ArrayList<Integer>(nums));
		}

		for (int i = index; i < nums.size(); i++) {
			Collections.swap(nums, index, i);
			permuteList(index + 1, nums, result);
			Collections.swap(nums, index, i);
		}
	}

	static List<List<Integer>> result = new ArrayList<>();

	// Time complexity - O(n!)
	// Space complexity - O(n!)
	public static List<List<Integer>> permuteListAlt(int[] nums) {
		counter = 0;
		swapCounter = 0;
		List<Integer> list = new ArrayList<Integer>();
		Arrays.stream(nums).forEach(a -> list.add(a));
		permuteListAlt(list, 0);
		return result;
	}

	private static void permuteListAlt(List<Integer> nums, int l) {
		counter++;
		if (l == nums.size()) {
			result.add(nums);
		} else {
			for (int i = l; i < nums.size(); i++) {
				nums = swap(nums, l, i);
				permuteListAlt(nums, l + 1);
				nums = swap(nums, l, i);
			}
		}
	}

	public static List<Integer> swap(List<Integer> nums, int s, int e) {
		swapCounter++;
		if (s == e) {
			return nums;
		}
		List<Integer> s1 = nums.subList(0, s);
		List<Integer> s2 = nums.subList(s + 1, e);
		List<Integer> s3 = nums.subList(e + 1, nums.size());

		List<Integer> list = new ArrayList<>();
		list.addAll(s1);
		list.add(nums.get(e));
		list.addAll(s2);
		list.add(nums.get(s));
		list.addAll(s3);

		return list;
	}

}
