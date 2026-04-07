package LeetCode.BitManipulation;

/*
 * P268. Missing Number - Easy
 * 
 * Given an array nums containing n distinct numbers in the range [0, n], 
 * return the only number in the range that is missing from the array.
 * 
 * Approach - Bit Manipulation: XOR | Gauss Formula
 */
public class P268MissingNumber {

	public static void main(String[] args) {
		int[] nums = { 3, 0, 1 };
//		int[] nums = { 0, 1 };
//		int[] nums = { 9, 6, 4, 2, 3, 5, 7, 0, 1 };

		int missingNumberGauss = missingNumberGauss(nums);
		System.out.println("Gauss: The missing number in the range is: " + missingNumberGauss);

		int missingNumberXOR = missingNumberXOR(nums);
		System.out.println("XOR: The missing number in the range is: " + missingNumberXOR);
	}

	// Gauss' formula
	// Young Gauss deduced a closed-form expression for the sum
	// ∑i=0 to n i = n*(n+1)/2
	public static int missingNumberGauss(int[] nums) {
		int n = nums.length;
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		return (n * (n + 1) / 2) - sum;
	}

	// Bit Manipulation
	// We use the fact that XOR is its own inverse to find the missing element in
	// linear time. Algo:
	// nums contains n numbers except 1 number on the range [0,1,...n], we know
	// that n definitely replaces the missing number in nums index. If we initalize
	// and integer to n(no index = n present), and XOR it with every index and
	// value, we will be left with the missing number.
	// Time complexity - O(n)
	// Space complexity - O(1).
	private static int missingNumberXOR(int[] nums) {
		int n = nums.length;
		for (int i = 0; i < nums.length; i++) {
			n ^= i ^ nums[i];
		}
		return n;
	}

}
