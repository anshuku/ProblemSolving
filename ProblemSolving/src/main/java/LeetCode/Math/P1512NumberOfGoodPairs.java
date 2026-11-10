package LeetCode.Math;

/*
 * P1512. Number of Good Pairs - Easy
 * 
 * Given an array of integers nums, return the number of good pairs.
 * A pair (i, j) is called good if nums[i] == nums[j] and i < j.
 * 
 * Constraints:
 * > 1 <= nums.length <= 100
 * > 1 <= nums[i] <= 100
 * 
 * Approach - Math: Combinations, Iteration
 */
public class P1512NumberOfGoodPairs {

	public static void main(String[] args) {
//		int[] nums = { 1, 2, 3, 1, 1, 3 };
//		int[] nums = { 1, 1, 1, 1 };
		int[] nums = { 3, 1, 10, 2, 4, 8, 3, 2, 9, 5, 4, 8, 4, 3, 1, 5, 5, 7, 2, 2, 8, 8, 10, 1, 7, 10, 5, 10, 2, 9, 8,
				7, 10, 3, 10, 10, 9, 8, 10, 7, 3, 10, 2, 9, 8, 3, 1, 2, 1, 6, 4, 9, 7, 5, 6, 7, 4, 5, 3, 1, 4, 2, 2, 1,
				10, 4, 2, 7, 3, 6, 5, 7, 3, 10 };
//		int[] nums = { 1, 2, 3, 100, 100, 3 };
//		int[] nums = { 1, 2, 3 };

		int numGoodFrequency1Pass = numIdenticalPairsFrequency1Pass(nums);
		System.out.println("Frequency 1 Pass: The number of good pairs are: " + numGoodFrequency1Pass);

		int numGoodFrequency2Pass = numIdenticalPairsFrequency2Pass(nums);
		System.out.println("Frequency 2 Pass: The number of good pairs are: " + numGoodFrequency2Pass);
	}

	private static int numIdenticalPairsFrequency1Pass(int[] nums) {
		int count = 0;

		int[] frequency = new int[101];

		for (int num : nums) {
			count += frequency[num]++;
		}
		return count;
	}

	public static int numIdenticalPairsFrequency2Pass(int[] nums) {
		int numGood = 0;

		int[] frequency = new int[101]; // Max number is 100

		for (int num : nums) {
			frequency[num]++;
		}

		for (int count : frequency) {
			numGood += count * (count - 1) / 2; // Ways to form pair is nc2.
		}

		return numGood;
	}
}
