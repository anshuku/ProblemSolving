package LeetCode.Arrays.TwoPointers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class P1679MaxNumberofKSumPairs {

	public static void main(String[] args) {

		int[] nums = { 2, 5, 4, 4, 1, 3, 4, 4, 1, 4, 4, 1, 2, 1, 2, 2, 3, 2, 4, 2 };// 1 - 4, 2 - 6
		int k = 3;

		int operations2Ptr = maxOperations2Ptr(nums, k);

		System.out.println("The max operations to get k via 2 pointers is " + operations2Ptr);

		int operationsMap = maxOperationsMap(nums, k);

		System.out.println("The max operations to get k via map is " + operationsMap);

		int operationsKArr = maxOperationsKArr(nums, k);

		System.out.println("The max operations to get k via k array is " + operationsKArr);

	}

	private static int maxOperations2Ptr(int[] nums, int k) {
		int ops = 0;
		Arrays.sort(nums);
		int n = nums.length;
		int left = 0, right = n - 1;
		while (left < right) {
			if (nums[left] + nums[right] > k) {
				right--;
			} else if (nums[left] + nums[right] < k) {
				left++;
			} else {
				ops++;
				right--;
				left++;
			}
		}
		return ops;
	}

	public static int maxOperationsMap(int[] nums, int k) {
		int ops = 0;
		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < nums.length; i++) {
			if (map.getOrDefault(k - nums[i], 0) > 0) {
				ops++;
				map.put(k - nums[i], map.get(k - nums[i]) - 1);
			} else {
				map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
			}
		}
		return ops;

	}

	private static int maxOperationsKArr(int[] nums, int k) {
		int[] count = new int[k];
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] < k) {
				count[nums[i]]++;
			}
		}
		int ans = 0;
		int i, j;
		for (i = 1, j = k - 1; i < j; i++, j--) {
			ans += Math.min(count[i], count[j]);
		}
		if (i == j) {
			ans = ans + count[i] / 2;
		}
		return ans;
	}

}
