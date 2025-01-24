package LeetCode.BitManipulation;

import java.util.Arrays;

/*
 * PP338. Counting Bits
 * 
 * Given an integer n, return an array ans of length n + 1 such that for each i (0 <= i <= n), 
 * ans[i] is the number of 1's in the binary representation of i.
 * 
 * Approach - 
 * 
 * Most significant bits or MSBs are leftmost
 * Least significant bits or LSB are rightmost
 * 
 * MSB ... LSB
 * 
 * Hamming weight/ popcount/ population count is the sum of non zero bits in a string.
 * 
 * Set bits are bits in a binary representation of a number having value 1.
 * 
 * n & (n-1) always flips the least significant 1 bit and rest remains same.
 * In binary, the least significant 1 bit is always 0 bit in n-1.
 */
public class P338CountingBits {

	public static void main(String[] args) {
		int n = 15;

		int[] bitsLastSet = countBitsLastSet(n);
		System.out.println("Last set bit: The count bits are: " + Arrays.toString(bitsLastSet));

		int[] bitsLSBDP = countBitsLSBDP(n);
		System.out.println("LSB DP: The count bits are: " + Arrays.toString(bitsLSBDP));

		int[] bitsMSBDP = countBitsMSBDP(n);
		System.out.println("MSB DP: The count bits are: " + Arrays.toString(bitsMSBDP));

		int[] bitsPopCount = countBitsPopCount(n);
		System.out.println("PopCount: The count bits are: " + Arrays.toString(bitsPopCount));

		int[] bitsBF = countBitsBF(n);
		System.out.println("Brute Force: The count bits are: " + Arrays.toString(bitsBF));
	}

	// DP and Last Set bit(righmost bit)
	// P(x) = P(x & (x-1)) + 1
	// Time complexity - O(n), fixed operations, bits independent.
	// Space complexity - O(1)
	private static int[] countBitsLastSet(int n) {
		int[] bits = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			bits[i] = bits[i & (i - 1)] + 1;
		}
		return bits;
	}

	// DP and Least significant bit
	// x = (1001011101)2 = (605)10 and we've previous values from 0 to x-1.
	// x' = (100101110)2 = (302)10 = x/2
	// The there is a difference of only 1 bit = LSB in x and x/2.
	// P(x) = P(x/2) + (x mod 2)
	// i & 1 operator is to know whether a number is even or odd
	// & with 1 only takes care of last digit(&1)
	// bits[i/2] will tell last time how many 1 were present
	// Time complexity - O(n), for constant operations, bits independent
	// Space complexity - O(1)
	private static int[] countBitsLSBDP(int n) {
		int[] bits = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			// i>>2 since the constraint is that, i is positive.
			bits[i] = bits[i >> 1] + (i & 1); // i >> 1 can be i / 2 | i&1 can be i%2
		}
		return bits;
	}

	// DP and Most significant bit
	// We use previous count result to generate new result.
	// x = (1001011101)2 = (605)10 and we've previous values from 0 to x-1.
	// x' = (1011101)2 = (93)10
	// x = x' + 1; // 605 - 512 = 93
	// 0 = (0)2 | 1 = (1)2 | 2 = (10)2 | 3 = (11)2
	// 2, 3 is obtained from 0,1
	// 4,7 is obtained from 0,3
	// P(x+b) = P(x) + 1 where b = 2^m where 2^m > x
	// Time complexity - O(n), for constant operations, bits independent
	// Space complexity - O(1)
	private static int[] countBitsMSBDP(int n) {
		int[] bits = new int[n + 1];
		int x = 0;
		int b = 1;
		// [0, b) is calculated
		while (b <= n) {
			// Calculate [b, 2b) or [b, n) from [0, b)
			// x < b ensures the range [b, 2b) and
			// x + b <= n ensures the capacity of answer
			while (x < b && x + b <= n) {
				bits[x + b] = bits[x] + 1;
				x++;
			}
			b = 2 * b;
			x = 0;
		}
		return bits;
	}

	// Time complexity - O(nlogn/2)
	// For each integer n, in worst case we need to perform log(n) operation
	// The numer of bits in n is log(n) + 1 and all bits can be 1.
	// On average each bit will be set n/2 times.
	// On average we will perform log(x)/2 operations
	// The number of bits on average is
	// Space complexity - O(1)
	private static int[] countBitsPopCount(int n) {
		int[] bits = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			bits[i] = popCount(i);
		}
		return bits;
	}

	// n&(n-1) ensures, least significant 1-bit is flipped and rest remains same.
	// Once n reaches 0, there are no more 1-bit or set bit remaining.
	// 111 110 | 110 101 | 100 11 | 00
	private static int popCount(int num) {
		int count;
		for (count = 0; num != 0; count++) {
			num = num & num - 1; // finding the least significant non-zero bit.
		}
		return count;
	}

	public static int[] countBitsBF(int n) {
		int[] bits = new int[n + 1];

		for (int i = 1; i <= n; i++) {
			int val = i;
			StringBuilder num = new StringBuilder();
			int count = 0;
			while (val > 0) {
				num.append(val % 2);
				if (val % 2 == 1) {
					count++;
				}
				val /= 2;
			}
//			bits[i] = Integer.valueOf(num.reverse().toString());
			bits[i] = count;

		}
		return bits;

	}

}
