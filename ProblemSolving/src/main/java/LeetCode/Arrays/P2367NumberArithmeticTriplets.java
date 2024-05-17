package LeetCode.Arrays;

import java.util.HashSet;
import java.util.Set;

public class P2367NumberArithmeticTriplets {

	public static void main(String[] args) {

		int[] nums = { 0, 1, 4, 6, 7, 10 };
		int diff = 3;
//		int[] nums = { 4, 5, 6, 7, 8, 9 };
//		int diff = 2;

		P2367NumberArithmeticTriplets pna = new P2367NumberArithmeticTriplets();
//		int count = pna.arithmeticTripletsBF(nums, diff);

//		int count = pna.arithmeticTripletsSetTwo(nums, diff);

//		int count = pna.arithmeticTripletsSetOne(nums, diff);

		int count = pna.arithmeticTripletsPointers(nums, diff);

		System.out.println("The number of arithmetic triplets is " + count);
	}

	// Brute force method
	public int arithmeticTripletsBF(int[] nums, int diff) {
		int n = nums.length;
		int count = 0;

		for (int i = 0; i < n - 2; i++) {
			for (int j = i + 1; j < n - 1; j++) {
				if (nums[j] - nums[i] == diff) {
					for (int k = j + 1; k < n; k++) {
						if (nums[k] - nums[j] == diff) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	// Set with 2 for loops
	private int arithmeticTripletsSetTwo(int[] nums, int diff) {
		Set<Integer> set = new HashSet<>();
		for (int num : nums) {
			set.add(num);
		}
		int count = 0;
		for (int num : nums) {
			if (set.contains(num - diff) && set.contains(num + diff)) {
				count++;
			}
		}
		return count;
	}

	// Set with 1 for loop
	private int arithmeticTripletsSetOne(int[] nums, int diff) {
		Set<Integer> set = new HashSet<>();
		int count = 0;
		for (int num : nums) {
			set.add(num);
			if (set.contains(num - diff) && set.contains(num - 2 * diff)) {
				count++;
			}
		}
		return count;
	}

	// Most optimized
	private int arithmeticTripletsPointers(int[] nums, int diff) {
		int ptr1 = 0, ptr2 = 1, ptr3 = 2;
		int count = 0;
		int n = nums.length;
		while (ptr3 < n) {
			int low = nums[ptr2] - diff;
			int high = nums[ptr2] + diff;
			while (ptr1 < ptr2 && nums[ptr1] < low) {
				ptr1++;
			}
			while (ptr3 < n && nums[ptr3] < high) {
				ptr3++;
			}
			if (ptr3 < n && nums[ptr1] == low && nums[ptr3] == high) {
				count++;
			}
			ptr2++;
		}
		return count;
	}

}
