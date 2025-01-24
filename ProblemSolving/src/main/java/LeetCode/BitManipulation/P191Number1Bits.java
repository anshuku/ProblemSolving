package LeetCode.BitManipulation;

/* 
 * P191. Number of 1 Bits
 * 
 * Given a positive integer n, write a function that returns the number of set bits
 * in its binary representation (also known as the Hamming weight).
 * 
 * Approach - Bit manipulation, & operator
 */
public class P191Number1Bits {

	public static void main(String[] args) {

//		int n = 11;
//		int n = 128;
//		int n = 2147483645;
		int n = 2147483647;

		int weightBitNear = hammingWeightBitNear(n);
		System.out.println("Bit nearby: The hamming weight is: " + weightBitNear);

		int weightAnd = hammingWeightAnd(n);
		System.out.println("And: The hamming weight is: " + weightAnd);

		int weightDivision = hammingWeightDivide(n);
		System.out.println("Division: The hamming weight is: " + weightDivision);

	}

	// The least signficant 1-bit of n is always 0 in n-1
	// The & opeation turns to Least significant 1 bit to 0
	// While the remaining bits remains same.
	// Time complexity - O(1), runtime depends on number of 1 bits
	// In worst case it's 32.
	// Space complexity - O(1)
	private static int hammingWeightBitNear(int n) {
		int count = 0;
		while (n != 0) {
			n = n & (n - 1);
			count++;
		}
		return count;
	}

	// Loop and flip
	// Time complexity - O(1)
	// Space complexity - O(1)
	private static int hammingWeightAnd(int n) {
		int mask = 1;
		int count = 0;
		// Loop
		for (int i = 0; i < 32; i++) {
			// Flip
			if ((n & mask) != 0) { // the & operation can give 1, 2, 4...
				count++;
			}
			mask = mask << 1;
		}
		return count;
	}

	public static int hammingWeightDivide(int n) {
		int count = 0;
		while (n > 0) {
			if (n % 2 == 1) {
				count++;
			}
			n = n / 2;
		}
		return count;
	}

}
