package LeetCode.BitManipulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * P136. Single Number - Easy
 * 
 * Given a non-empty array of integers nums, every element appears 
 * twice except for one. Find that single one.
 * 
 * You must implement a solution with a linear runtime 
 * complexity and use only constant extra space.
 * 
 * Approach - Bit manipulation - XOR chaining
 * 
 * a^0 = a
 * b^b = 0
 * 
 * b^a^b = (b^b)^a = 0^a = a
 */
public class P136SingleNumber {

	public static void main(String[] args) {

//		int[] nums = { 2, 2, 1 };
		int[] nums = { 4, 1, 2, 1, 2 };

		int numXOR = singleNumberXOR(nums);
		System.out.println("XOR: The single number is: " + numXOR);

		int numMath = singleNumberMath(nums);
		System.out.println("Math: The single number is: " + numMath);

		int numMap = singleNumberMap(nums);
		System.out.println("Map: The single number is: " + numMap);

		int numList = singleNumberList(nums);
		System.out.println("List: The single number is: " + numList);
	}

	// XOR chaining
	// 8 - 1000, 7 - 111
	// 8 7 8
	// 1000 ^ 111 = 1111
	// 1111 ^ 1000 = 111 = 7
	// For 8 8 7
	// 1000 ^ 1000 = 0
	// 0 ^ 111 = 111 = 7
	// The XOR chaining gives the number which appears once.
	// When the numbers appears twice anywhere then
	// XOR removes it as 0. 0 ^ number appearing once gives the result.
	public static int singleNumberXOR(int[] nums) {
		int num = nums[0];
		int n = nums.length;

		for (int i = 1; i < n; i++) {
			num = nums[i] ^ num;
		}
		return num;
	}

	// Math
	// Time complexity - O(n)
	// Space complexity - O(n)
	private static int singleNumberMath(int[] nums) {
		long sumOfSet = 0, sumOfNums = 0;
		Set<Integer> set = new HashSet<>();
		for (int num : nums) {
			if (!set.contains(num)) {
				set.add(num);
				sumOfSet += num;
			}
			sumOfNums += num;
		}
		int result = (int) (2 * sumOfSet - sumOfNums);
		return result;
	}

	// Hash Table
	// Time complexity - O(n)
	// Space complexity - O(n)
	private static int singleNumberMap(int[] nums) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : nums) {
			map.put(num, map.getOrDefault(num, 0) + 1);
		}
		for (int num : nums) {
			if (map.get(num) == 1) {
				return num;
			}
		}
		return 0;
	}

	// List Operation
	// Time complexity - O(n^2), contains takes O(n) time and and for loop has O(n)
	// time. This are multiplied to give O(n^2).
	// Space complexity - O(n)
	private static int singleNumberList(int[] nums) {
		List<Integer> list = new ArrayList<>();
		for (int num : nums) {
			if (!list.contains(num)) {
				list.add(num);
			} else {
				list.remove(num);
			}
		}
		return list.get(0);
	}
}
