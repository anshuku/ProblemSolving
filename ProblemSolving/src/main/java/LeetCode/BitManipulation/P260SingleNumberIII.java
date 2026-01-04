package LeetCode.BitManipulation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * P260. Single Number III - Medium
 * 
 * Given an integer array nums, in which exactly two elements appear only once
 * and all the other elements appear exactly twice. Find the two elements
 * that appear only once. You can return the answer in any order.
 * 
 * You must write an algorithm that runs in linear 
 * runtime complexity and uses only constant extra space.
 * 
 * Approach - Bit Manipulation
 * 
 * For problems where we need to identify an array element(s), which appears exactly given
 * number of times then build first an array bitmask using XOR operator. Ex - In-place Swap,
 * Single Number I,II,III.
 */
public class P260SingleNumberIII {

	public static void main(String[] args) {
		int[] nums = { 1, 2, 1, 3, 2, 5 };
//		int[] nums = { -1, 0 };
//		int[] nums = { 0, -1 };

		int[] singleNumbersBitwise = singleNumberBitwise(nums);
		System.out.println("Bitwise: The single numbers are - " + Arrays.toString(singleNumbersBitwise));

		int[] singleNumbersMap = singleNumberMap(nums);
		System.out.println("Map: The single numbers are - " + Arrays.toString(singleNumbersMap));
	}

	// Bit Manipulation - 2 Bitmasks
	// > If one builds an array bitmask with the help of XOR operator, following
	// bitmask ^= x strategy, it'll keep only those bits which appear odd times.
	// > x & (-x) keeps the rightmost 1-bit and sets all other bits to 0 - Power(2)
	// x = 7,0 0 0 0 0 1 1 1 | -x = ~x + 1,1 1 1 1 1 0 0 1, x & (-x),0 0 0 0 0 0 0 1
	// x = 6,0 0 0 0 0 1 1 0 | -x = ~x + 1,1 1 1 1 1 0 1 0, x & (-x),0 0 0 0 0 0 1 0
	// Array XOR bitmask: bitmask ^= x, it'll not keep elements appearing twice
	// a^a=0. Now for 2 numbers appearing once, it'll keep only the bits difference
	// between those: x and y. x = 1, y = 2, bitmask^x^y^a^a (xor) = 0 0 0 0 0 0 1 1
	// 1 can't get x and y directly from this bitmask but use it as a marker to
	// separate x and y. Now, bitmask & (-bitmask) is used to isolate the righmost
	// 1-bit, which is different between x and y. Let's say this is 1-bit for x, and
	// 0-bit for y. diff = bitmask & (-bitmask) = 0 0 0 0 0 0 0 1. We use diff to
	// find x. We use XOR as before, but for the new bitmask xBitmask, which will
	// contain only the numbers which have 1-bit in the poistion of diff. This
	// bitmask will contain only number x = xBitmask as:
	// > y has 0-bit in the position of diff > All other valid numbers appear twice.
	// To get y we simply do y = bitmask ^ x.
	// Time complexity - O(n), for iterating over nums array twice.
	// Space complexity - O(1)
	private static int[] singleNumberBitwise(int[] nums) {
		if (nums.length == 2) {
			return nums;
		}
		// Bit difference between 2 numbers x and y (both appear only once)
		int bitmask = 0;
		for (int num : nums) {
			bitmask ^= num;
		}
		// Rightmost 1-bit difference between x and y
		int diff = bitmask & (-bitmask);

		int x = 0;
		// bitmask which will contain only x
		for (int num : nums) {
			if ((num & diff) != 0) {
				x ^= num;
			}
		}
		// y = x ^ bitmask
		return new int[] { x, bitmask ^ x };
	}

	// Map
	// We build a hashmap with key as every number along with value as its
	// frequency. Return the elements with the frequency equal to 1.
	// Time complexity - O(n)
	// Space complexity - O(n), for hashmap.
	public static int[] singleNumberMap(int[] nums) {
		if (nums.length == 2) {
			return nums;
		}
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : nums) {
			map.put(num, map.getOrDefault(num, 0) + 1);
		}
		int[] result = new int[2];
		int i = 0;
		for (int num : nums) {
			if (map.get(num) == 1) {
				result[i++] = num;
			}
		}
		return result;
	}

}
