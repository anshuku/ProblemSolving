package LeetCode.BitManipulation;

/*
 * P1318. Minimum Flips to Make a OR b Equal to c
 * 
 * Given 3 positives numbers a, b and c. Return the minimum flips required 
 * in some bits of a and b to make ( a OR b == c ). (bitwise OR operation).
 * 
 * Flip operation consists of change any single bit 1 to 0 
 * or change the bit 0 to 1 in their binary representation.
 * 
 * Approach - Bit manipulation: &, & | ^ Integer.bitCount(num)
 */
public class P1318MinimumFlipsMakeAOrBEqualC {

	public static void main(String[] args) {
//		int a = 2, b = 6, c = 5;
//		int a = 4, b = 2, c = 7;
		int a = 8, b = 3, c = 5;

		int minFlipsOrXorAnd = minFlipsOrXorAnd(a, b, c);
		System.out.println("OR XOR AND: The minimum number of flips needed: " + minFlipsOrXorAnd);

		int minFlipsRShift = minFlipsRShift(a, b, c);
		System.out.println("Right Shift: The minimum number of flips needed: " + minFlipsRShift);

		int minFlipsMask = minFlipsMask(a, b, c);
		System.out.println("Mask: The minimum number of flips needed: " + minFlipsMask);
	}

	// a  	  		    - 000100
	// b   	   			- 110110
	// a|b     			- 110110
	// c	   			- 100001
	// a|b ^ c will return all the bits which are different
	// a|b ^ c 			- 010111 - all the bits needs 1 flip
	// Now there is a case where c&1 is 0 and a&1 and b&1 is 1.
	// For this we need additional flip
	// To find that bit use &
	// a 	  			- 000100
	// b   	  			- 110110
	// a&b	 			- 000100
	// a|b ^ c 			- 010111
	// a&b & (a|b ^ c) 	- 000100 - all bits need an additioanl flip
	// Time complexity - O(1), Integer.bitCount(num) uses
	// precomputed lookup table of bit count for every possible 16-bit integer
	// For 32 bit integers, it sums the bit counts for 2 16-bit halves.
	// Space complexity - O(1)
	private static int minFlipsOrXorAnd(int a, int b, int c) {
		return Integer.bitCount((a & b) & ((a | b) ^ c)) + Integer.bitCount((a | b) ^ c);
	}

	// Time complexity - O(log(max(a,b,c)))
	// Space complexity - O(1)
	public static int minFlipsRShift(int a, int b, int c) {
		int flips = 0;
		while (a != 0 || b != 0 || c != 0) {
			if ((c & 1) == 1) {
				if ((a & 1) == 0 && (b & 1) == 0) {
					flips++;
				}
			} else {
				flips += (a & 1) + (b & 1);
			}
			a >>= 1;
			b >>= 1;
			c >>= 1;
		}
		return flips;
	}

	public static int minFlipsMask(int a, int b, int c) {
		int flips = 0;
		int mask = 1;
		for (int i = 0; i < 32; i++) {
			if ((c & mask) != 0) {
				if ((a & mask) == 0 && (b & mask) == 0) {
					flips++;
				}
			} else {
				if ((a & mask) != 0) {
					flips++;
				}
				if ((b & mask) != 0) {
					flips++;
				}
			}
			mask = mask << 1;
		}
		return flips;
	}

}
