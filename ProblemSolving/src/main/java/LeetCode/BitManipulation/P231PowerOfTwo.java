package LeetCode.BitManipulation;

/*
 * P231. Power of Two - Easy
 * 
 * Given an integer n, return true if it is a power of two. Otherwise, return false.
 * 
 * An integer n is a power of two, if there exists an integer x such that n == 2x.
 * 
 * Approach - Bit Manipulation
 */
public class P231PowerOfTwo {

	public static void main(String[] args) {
		int n = 1;
//		int n = 16;
//		int n = 3;
//		int n = -4;

		boolean isPowerTwoSetRMBit = isPowerOfTwoSetRMBit(n);
		System.out.println("Turn Right Most Bit: Is the number power of two: " + isPowerTwoSetRMBit);

		boolean isPowerTwoGetRMBit = isPowerOfTwoGetRMBit(n);
		System.out.println("Get Right Most Bit: Is the number power of two: " + isPowerTwoGetRMBit);

		boolean isPowerTwoWhileBM = isPowerOfTwoWhileBM(n);
		System.out.println("While Bit Manipulation: Is the number power of two: " + isPowerTwoWhileBM);

		boolean isPowerTwoWhile = isPowerOfTwoWhile(n);
		System.out.println("While: Is the number power of two: " + isPowerTwoWhile);
	}

	// Bitwise operators: Turn off the rightmost 1-bit.
	// x & (x - 1) is a way to set the rightmost 1-bit to 0. To subtract 1 means to
	// change the rightmost 1-bit to 0 and set all the lower bits to 1. With AND
	// operator: the rightmost 1-bit will be turned off because 1 & 0 == 0, and all
	// the lower bits as well 0 & 1 == 0. Now power of 2 has just one 1-bit. x & (x
	// - 1) sets this 1-bit to 0, and hence we need to check if result is 0 x & (x -
	// 1) == 0.
	// Time complexity - O(1)
	// Space complexity - O(1)
	private static boolean isPowerOfTwoSetRMBit(int n) {
		return n > 0 && (n & (n - 1)) == 0;
	}

	// Time complexity - O(logN)
	public static boolean isPowerOfTwoWhileBM(int n) {
		int ones = 0;
		while (n > 0) {
			if ((n & 1) == 1) {
				ones++;
			}
			n = n >> 1;
		}
		return ones == 1;
	}

	// Bitwise operators: Get the rightmost 1-bit.
	// The problem can be solved in O(1) time with the help of bitwise operators. If
	// we turn off(set to 0) the righmost 1-bit: x & (x - 1).
	// The power of 2 in binary representation is one 1-bit, followed by some or no
	// zeros. A number which is not a power of 2, has more than one 1-bit in its
	// binary representation. The number 0 has zero 1-bit.
	// x = ~x + 1. ~x means we revert the bits of x. x & (-x) is a way to keep the
	// rightmost 1-bit and to set all the other bits to 0. It works because of two's
	// complement, where -x is same as ~x + 1. We revert all bits of x and add 1.
	// Adding 1 to ~x in binary representation means to carry that 1-bit till the
	// rightmost 0-bit in -x and to set all the lower bits to zero. The rightmost
	// 0-bit in -x corresponds to the rightmost 1-bit in x. -x = ~x+1, this
	// operation reverts all bits of x except the rightmost 1-bit. Hence, x and -x
	// have just 1 bit in common - the rightmost 1-bit. It means that x & (-x) would
	// keep the rightmost 1-bit and set all the other bits to 0.
	// We do x & (-x) to keep the rightmost 1-bit and to set all the other bits to
	// 0. For the power of 2, it would result in x itself, since a power of 2
	// contains just 1-bit. For the power of 2, it'd result in x itself, since a
	// power of 2 contains just one 1-bit. Other numbers have more than 1-bit in
	// their binary representation and hence for them x & (-x) would not be equal to
	// x itself. Hence for power of 2, x & (-x) == x.
	// Time complexity - O(1)
	// Space complexity - O(1)
	private static boolean isPowerOfTwoGetRMBit(int n) {
		return n > 0 && (n & (n - 1)) == 0;
	}

	// Time complexity - O(logN)
	private static boolean isPowerOfTwoWhile(int n) {
		if (n == 0) {
			return false;
		}
		// We check every time the number is even.
		while (n % 2 == 0) {
			n /= 2; // We divide every time the number by 2.
		}
		return n == 1;
	}
}
