package LeetCode.Arrays;

import java.util.HashSet;
import java.util.Set;

public class P217ContainsDuplicate {

	public static void main(String[] args) {
		int[] nums = { 1, 1, 1, 3, 3, 4, 3, 2, 4, 2 };

		boolean containsDuplicate = containsDuplicate(nums);

		System.out.println("The nums array contains duplicate - " + containsDuplicate);

		boolean containsDuplicateOpt = containsDuplicateOpt(nums);

		System.out.println("The nums array contains duplicate optimized - " + containsDuplicateOpt);
	}

	private static boolean containsDuplicateOpt(int[] nums) {
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] == nums[i - 1]) {
				return true;
			} else if (nums[i] < nums[i - 1]) {
				int temp = nums[i - 1];
				for (int j = i - 2; j >= 0; j--) {
					if (nums[i] == nums[j]) {
						return true;
					}
				}
				nums[i - 1] = nums[i];
				nums[i] = temp;
			}
		}
		return false;
	}

	public static boolean containsDuplicate(int[] nums) {
		Set<Integer> set = new HashSet<>();
		for (int num : nums) {
			if (!set.add(num)) {
				return true;
			}
		}
		return false;
	}

}
