package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P3982. Sum of Integers with Maximum Digit Range - Easy
 * 
 * You are given an integer array nums.
 * 
 * The digit range of an integer is defined as the difference between its largest digit and smallest digit.
 * 
 * For example, the digit range of 5724 is 7 - 2 = 5.
 * 
 * Return the sum of all integers in nums whose digit range is equal 
 * to the maximum digit range among all integers in the array.
 * 
 * Approach - Range array, Map
 */
public class P3982SumOfIntegersWithMaximumDigitRange {

	public static void main(String[] args) {
		int[] nums = { 5724, 111, 350 };
//		int[] nums = { 90, 900 };

		int maxDigitRangeArray = maxDigitRangeArray(nums);
		System.out.println(
				"Array: The sum of all integers in nums whose digit range = max digit range: " + maxDigitRangeArray);

		int maxDigitRangeMap = maxDigitRangeMap(nums);
		System.out.println(
				"Map: The sum of all integers in nums whose digit range = max digit range: " + maxDigitRangeMap);
	}

	private static int maxDigitRangeArray(int[] nums) {
		int n = nums.length;

		int maxDiff = Integer.MIN_VALUE;

		int[] range = new int[n];

		for (int i = 0; i < n; i++) {
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;

			int num = nums[i];

			while (num > 0) {
				int val = num % 10;
				min = Math.min(min, val);
				max = Math.max(max, val);
				num /= 10;
			}
			int diff = max - min;

			maxDiff = Math.max(maxDiff, diff);
			range[i] = max - min;
		}

		int sum = 0;

		for (int i = 0; i < n; i++) {
			if (maxDiff == range[i]) {
				sum += nums[i];
			}
		}
		return sum;
	}

	public static int maxDigitRangeMap(int[] nums) {
		int n = nums.length;

		int maxDiff = Integer.MIN_VALUE;
		Map<Integer, List<Integer>> map = new HashMap<>();

		for (int i = 0; i < n; i++) {
			char[] charNum = String.valueOf(nums[i]).toCharArray();

			Arrays.sort(charNum);

			int c = charNum.length;

			int diff = charNum[c - 1] - charNum[0];

			maxDiff = Math.max(maxDiff, diff);

			map.computeIfAbsent(diff, k -> new ArrayList<>()).add(nums[i]);
		}

		int sum = 0;

		for (int num : map.get(maxDiff)) {
			sum += num;
		}

		return sum;
	}

}
