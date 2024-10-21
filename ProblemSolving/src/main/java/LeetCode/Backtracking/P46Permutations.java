package LeetCode.Backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
 * P.46 Permutations - Medium
 * Given an array nums of distinct integers, return all the possible permutations. 
 * You can return the answer in any order.
 */
public class P46Permutations {

	public static void main(String[] args) {

		// 65(2^6) 128(2^7) | 16(2^4) 30(2^5)
		int[] nums = { 1, 2, 3 };

		List<List<Integer>> list = permute(nums);
		System.out.println("Permute: Permutations are " + list);
		System.out.println("Permute: counter " + counter + " swap counter " + swapCounter);

		List<List<Integer>> ans = backtrack(nums);
		System.out.println("Backtrack: Permutations are " + ans);
		System.out.println("Backtrack: counter " + counter);

		counter = 0;
		recursion(nums, new ArrayList<Integer>(), new boolean[nums.length]);
		System.out.println("Recursion: Permutations are " + globalResult);
		System.out.println("Recursion: counter " + counter);

		permuteList(nums);
		System.out.println("Permute List: Permutations are " + result);
		System.out.println("Permute List: counter " + counter + " swap counter " + swapCounter);

	}

	private static List<List<Integer>> permute(int[] nums) {
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
				swap(nums, index, i);
				permute(nums, result, index + 1);
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

	private static List<List<Integer>> backtrack(int[] nums) {
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

	static List<List<Integer>> result = new ArrayList<>();
	static int counter = 0;
	static int swapCounter = 0;

	// Time complexity - O(n!)
	// Space complexity - O(n!)
	public static List<List<Integer>> permuteList(int[] nums) {
		counter = 0;
		swapCounter = 0;
		List<Integer> list = new ArrayList<Integer>();
		Arrays.stream(nums).forEach(a -> list.add(a));
		permuteList(list, 0);
		return result;
	}

	private static void permuteList(List<Integer> nums, int l) {
		counter++;
		if (l == nums.size()) {
			result.add(nums);
		} else {
			for (int i = l; i < nums.size(); i++) {
				nums = swap(nums, l, i);
				permuteList(nums, l + 1);
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
