package LeetCode.Arrays.SlidingWindow;

public class P1004MaxConsecutiveOnesIII {

	public static void main(String[] args) {
		int[] nums = { 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1 };
		int k = 2;

//		int[] nums = { 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1 };
//		int k = 3;

		int longestOnesLessVar = longestOnesLessVar(nums, k);

		System.out.println("Sliding Window Less Var: The longest ones " + longestOnesLessVar);

		int longestOnes = longestOnes(nums, k);

		System.out.println("Sliding Window: The longest ones " + longestOnes);

		int longestOnesUSW = longestOnesUSW(nums, k);

		System.out.println("Unoptimized Sliding Window: The longest ones " + longestOnesUSW);

	}

	private static int longestOnesLessVar(int[] nums, int k) {
		int i = 0, j = 0;
		while (j < nums.length) {
			if (nums[j++] == 0) {
				k--;
			}
			if (k < 0) {
				if (nums[i++] == 0) {
					k++;
				}
			}
		}
		return j - i;
	}

	// Time Complexity - O(n)
	// Space Complexity - O(1)
	public static int longestOnes(int[] nums, int k) {
		int right = 0, left = 0;
		int zeros = 0;
		while (right < nums.length) {
			if (nums[right] == 0) {
				zeros++;
			}
			right++;
			if (zeros > k) {
				if (nums[left] == 0) {
					zeros--;
				}
				left++;
			}
		}
		return right - left;
	}

	// Time Complexity - O(2*n), since we have 1 more while loop inside while
	// Space Complexity - O(1)
	private static int longestOnesUSW(int[] nums, int k) {
		int right = 0, left = 0;
		int zeros = 0;
		int maxOnes = 0;
		while (right < nums.length) {
			if (nums[right] == 0) {
				zeros++;
			}
			right++;
			while (zeros > k) {
				if (nums[left] == 0) {
					zeros--;
				}
				left++;
			}
			maxOnes = Math.max(maxOnes, right - left);
		}

		return maxOnes;
	}

}
