package LeetCode.HashMapSet;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * P2215. Find the Difference of Two Arrays - Easy
 * 
 * Given two 0-indexed integer arrays nums1 and nums2, return a list answer of size 2 where:
 * answer[0] is a list of all distinct integers in nums1 which are not present in nums2.
 * answer[1] is a list of all distinct integers in nums2 which are not present in nums1.
 * Note that the integers in the lists may be returned in any order.
 * 
 * Approach - Set, Array
 * 
 * AbstractList needs get() and size() method implementation.
 */
public class P2215FindDifferenceTwoArrays {

	public static void main(String[] args) {
		int[] nums1 = { 1, 2, 3 };
		int[] nums2 = { 2, 4, 6 };

		List<List<Integer>> differenceAbsList = findDifferenceAbsList(nums1, nums2);
		System.out.println("Abstract List: The difference of two array is " + differenceAbsList);

		List<List<Integer>> differenceArr = findDifferenceArr(nums1, nums2);
		System.out.println("Array: The difference of two array is " + differenceArr);

		List<List<Integer>> differenceSet = findDifferenceSet(nums1, nums2);
		System.out.println("Set: The difference of two array is " + differenceSet);

		List<List<Integer>> differenceSetList = findDifferenceSetList(nums1, nums2);
		System.out.println("Set and List: The difference of two array is " + differenceSetList);
	}

	private static List<List<Integer>> findDifferenceAbsList(int[] nums1, int[] nums2) {

		return new AbstractList<List<Integer>>() {

			List<Integer> distinct1;
			List<Integer> distinct2;

			@Override
			public int size() {
				return 2;
			}

			@Override
			public List<Integer> get(int index) {
				if (distinct1 == null || distinct2 == null) {
					process();
				}
				return index == 0 ? distinct1 : distinct2;
			}

			public void process() {
				distinct1 = new ArrayList<>();
				distinct2 = new ArrayList<>();

				int[] map = new int[2001];

				for (int num : nums1) {
					if (map[num + 1000] == 0) {
						map[num + 1000]++;
					}
				}

				for (int num : nums2) {
					if (map[num + 1000] == 0) {
						distinct2.add(num);
						map[num + 1000] += 2;
					} else {
						map[num + 1000]++;
					}
				}

				for (int num : nums1) {
					if (map[num + 1000] == 1) {
						distinct1.add(num);
						map[num + 1000]++;
					}
				}
			}

		};
	}

	private static List<List<Integer>> findDifferenceArr(int[] nums1, int[] nums2) {
		int[] map = new int[2001];
		for (int num : nums1) {
			if (map[num + 1000] == 0) {
				map[num + 1000]++;
			}
		}
		List<Integer> list2 = new ArrayList<>();
		for (int num : nums2) {
			if (map[num + 1000] == 0) {
				list2.add(num);
				map[num + 1000] = 2;
			} else {
				map[num + 1000]++;
			}
		}
		List<Integer> list1 = new ArrayList<>();
		for (int num : nums1) {
			if (map[num + 1000] == 1) {
				list1.add(num);
				map[num + 1000]++;
			}
		}
		List<List<Integer>> result = new ArrayList<>();
		result.add(list1);
		result.add(list2);
		return result;
	}

	private static List<List<Integer>> findDifferenceSet(int[] nums1, int[] nums2) {
		Set<Integer> set1 = new HashSet<>();
		for (int num : nums1) {
			set1.add(num);
		}
		Set<Integer> set2 = new HashSet<>();
		for (int num : nums2) {
			set2.add(num);
		}
		List<Integer> list1 = new ArrayList<>();
		for (int num : set1) {
			if (!set2.contains(num)) {
				list1.add(num);
			}
		}
		List<Integer> list2 = new ArrayList<>();
		for (int num : set2) {
			if (!set1.contains(num)) {
				list2.add(num);
			}
		}
		List<List<Integer>> result = new ArrayList<>();
		result.add(list1);
		result.add(list2);
		return result;
	}

	public static List<List<Integer>> findDifferenceSetList(int[] nums1, int[] nums2) {
		Set<Integer> set1 = new HashSet<>();
		for (int num : nums1) {
			set1.add(num);
		}
		Set<Integer> set2 = new HashSet<>();
		for (int num : nums2) {
			set2.add(num);
		}

		Set<Integer> setR1 = new HashSet<>();
		for (int i = 0; i < nums1.length; i++) {
			if (!set2.contains(nums1[i])) {
				setR1.add(nums1[i]);
			}
		}

		Set<Integer> setR2 = new HashSet<>();
		for (int i = 0; i < nums2.length; i++) {
			if (!set1.contains(nums2[i])) {
				setR2.add(nums2[i]);
			}
		}

		List<List<Integer>> result = new ArrayList<>();
		List<Integer> list1 = new ArrayList<>(setR1);
		List<Integer> list2 = new ArrayList<>(setR2);
		result.add(list1);
		result.add(list2);
		return result;
	}

}
