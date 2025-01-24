package LeetCode.BitManipulation;

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

		int num = singleNumber(nums);
		System.out.println("The single number is: " + num);

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
	public static int singleNumber(int[] nums) {
		int num = nums[0];
		int n = nums.length;

		for (int i = 1; i < n; i++) {
			num = nums[i] ^ num;
		}
		return num;
	}

}
