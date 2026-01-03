package LeetCode.BitManipulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * P137. Single Number II - Medium
 * 
 * Given an integer array nums where every element appears three times except 
 * for one, which appears exactly once. Find the single element and return it.
 * 
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 * 
 * Approach - Bit manipulation - NOT, AND and XOR chaining, couting + modulo
 */
public class P137SingleNumberII {

	public static void main(String[] args) {

		int[] nums = { 2, 2, 3, 2 };
//		int[] nums = { 0, 1, 0, 1, 0, 1, 99 };

		int numBitwise = singleNumberBitwise(nums);
		System.out.println("Bitwise NOT AND XOR: The single number is: " + numBitwise);

		int numBitModulo = singleNumberBitModulo(nums);
		System.out.println("Bit counting Modulo: The single number is: " + numBitModulo);

		int numMath = singleNumberMath(nums);
		System.out.println("Math: The single number is: " + numMath);

		int numMap = singleNumberMap(nums);
		System.out.println("Map: The single number is: " + numMap);
	}

	// Bitwise: NOT, AND and XOR - Finite state machine
	// ~x = bitwise NOT, ~2 = -3; ~3 = -4
	// x & y = bitwise AND
	// x ^ y = bitwise XOR
	// XOR operator can be used to detect the bit which appears odd number of times:
	// 1, 3, 5, etc. XOR of 0 and a bit results in that bit, 0 ^ x = x
	// XOR of 2 equal bits(including 0) results in 0, x ^ x = 0
	// One can see the bit in a XOR bitmask only if it appears odd numer of times.
	// 8 - 1000, 7 - 111 => 8 7 8 => 1000 ^ 111 = 1111 => 1111 ^ 1000 = 111 = 7
	// For 8 8 7 => 1000 ^ 1000 = 0 => 0 ^ 111 = 111 = 7
	// With XOR, one can detect the bit which appears once and the bit which appears
	// 3 times. To distinguish between these 2, we use AND and NOT operators.
	// To separate number that appears once from a number that appears 3 times, we
	// use 2 bitmasks instead of one: seenOnce and seenTwice. Use ideas below:
	// > We change seenOnce only if seenTwice is unchanged.
	// > We change seenTwice only if seenOnce is unchanged.
	// For x = 2, we carry the operations
	// 1) seenOnce = 00000010, seenTwice = 00000000 // add only in seenOnce
	// 2) seenOnce = 00000000, seenTwice = 00000010 // remove from 1, add in 2
	// 3) seenOnce = 00000000, seenTwice = 00000000 // remove from 2
	// The seenOnce keeps numbers appearing once and seenTwice keeps numbers
	// appearing twice in a bitwise manner. If a number in a bit appears thrice we
	// removed the number from both the variables.
	// The two bitmasks cycles bits through states 0 -> 1 -> 2 -> 0.
	// The unique number appears only once, so its bits remain in ones.
	// Logical derivation for FSM: We build a base-3 counter per bit. For each bit
	// position, we want: 0 -> 1 -> 2 -> back to 0. We need to store number mod 3.
	// 2 bits are enough to represent 0,1,2.
	// count % 3 | twos | ones
	// 0 			0		0
	// 1 			0		1
	// 2 			1		0
	// This table is the gist of the choice of operators.
	// (twos, ones) is the state of the counter for that bit.
	// XOR helps to toggle bits 0 ^ 1 = 1, 1 ^ 1 = 0
	// ones ^ x tries to toggle seen once
	// twos ^ x tries to toggle seen twice
	// Here x is only this bit of the incoming number(0 or 1).
	// XOR alone can't work as we must prevent illegal states:
	// ones = 1 and twos = 1 is invalid, so we must mask.
	// Prevent illegal overlap: A bit cannot be in both ones and twos.
	// When something enters ones, it must be removed from twos.
	// When something enters twos, it must be removes from ones.
	// So we use masking, which enforces 00 -> 01 -> 10 -> 00. Simulation:
	// current | x | ones^x | twos^x | Masked Result(twos, ones)
	// 00 		1	0^1 = 1	 0^1 = 1	1&~1=0 | 1&~0=1 => 01 //1 see this bit 1st time.
	// 10		1	1^1 = 0	 0^1 = 1	1&~0=1 | 0&~0=0 => 10 //2 saw this bit 2nd time
	// 01		1	0^1 = 1	 1^1 = 0  	0&~0=0 | 1&~1=0 => 01 //3 saw this bit 3rd time, reset
	// 00		1	cycle starts again
	// For row1: current=00, x = 1, we see this bit 1st time.
	// Step 1 - XOR: ones ^ x = 0 ^ 1 = 1, twos ^ x = 0 ^ 1 = 1 (we mask as no 11)
	// Step 2 - Mask: we must not allow a bit to exist in both ones and twos.
	// ones = 1 & ~ tows = 1 & ~0 = 1, twos = 1 & ~ tows = 1 & ~0 = 0
	// For row 3, we saw the bit 3rd time and we must reset to 0.
	// the XOR tries to move bit to ones again, but that would represent count = 3, 
	// which we don't want. So we mask the key. So as per FSM rule, when both toggled, 
	// drop both. Each bit independently keeps a base-3 counter. Triplicate numbers disappear.
	// Final state -> 01(count = 1). This leads to modulo-3 cycling. 
	// Only the number that appears once leaves its bits in state 01.
	// Everything else resets to 00. So ones is the unique number.
	// Time complexity - O(n)
	// Space complexity - O(1)
	public static int singleNumberBitwise(int[] nums) {
		int seenOnce = 0, seenTwice = 0;
		for (int num : nums) {
			seenOnce = ~seenTwice & (seenOnce ^ num);
			seenTwice = ~seenOnce & (seenTwice ^ num);
		}
		return seenOnce;
	}

	// Bit counting + Modulo solution
	// Each number is 32 bits. For each bit i = 0 to 31, We count how many times
	// each bit appears at each position or we count how many numbers have that bit
	// set. Triplicated numbers contribute counts divisible by 3. The unique numbers
	// contributes +1 at its bit positions and also another 3k by other numbers.
	// count of bit at i % 3 = bit at i of answer. If count % 3 == 1, set that bit
	// in answer.
	// Time complexity - O(n)
	// Space complexity - O(1)
	private static int singleNumberBitModulo(int[] nums) {
		int result = 0;
		// 0 to 31 as index starts from 0, total 32 bits
		for (int i = 0; i < 32; i++) {
			int count = 0;
			for (int num : nums) {
				count += (num >> i) & 1;
			}
			int bit = count % 3;
			// Id bit == 1, we set the bit in result
			result |= bit * (1 << i);
		}
		return result;
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
		int result = (int) (3 * sumOfSet - sumOfNums);
		return result / 2;
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
}
