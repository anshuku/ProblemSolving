package LeetCode.BitManipulation;

import java.util.HashMap;
import java.util.Map;

/*
 * P190. Reverse Bits - Easy
 * 
 * Reverse bits of a given 32 bits signed integer.
 * 
 * Approach - Bit Manipulation
 */
public class P190ReverseBits {

	public static void main(String[] args) {
		int n = 43261596;

		int reversedBitByBit = reverseBitsBitByBit(n);
		System.out.println("Bit by bit: The reversed bits are: " + reversedBitByBit);

		int reversedByteByByteMemo = reverseBitsByteByByteMemo(n);
		System.out.println("Byte by Byte Memo: The reversed bits are: " + reversedByteByByteMemo);

		int reversedMaskAndShift = reverseBitsMaskAndShift(n);
		System.out.println("Mask and Shift: The reversed bits are: " + reversedMaskAndShift);

		int reversedBitsShiftRevN = reverseBitsShiftRevN(n);
		System.out.println("Bit Shift: The reversed bits are: " + reversedBitsShiftRevN);

		int reversedBitsShiftRevNDirect = reverseBitsShiftRevNDirect(n);
		System.out.println("Bit Shift Direct: The reversed bits are: " + reversedBitsShiftRevNDirect);

		int reversedTwoPower = reverseBitsTwoPower(n);
		System.out.println("Two Power: The reversed bits are: " + reversedTwoPower);

		int reversedTwoPower2 = reverseBitsTwoPower2(n);
		System.out.println("Two Power2: The reversed bits are: " + reversedTwoPower2);
	}

	// Bit by Bit - reverse the bits 1 by 1.
	// To get right-most bit of an integer n, either use modulo (n % 2) or the bit
	// AND operations (n & 1). To combine result of reversed bits (2^a, 2^b), one
	// could either use addition (2^a + 2^b) or the bit OR operation (2^a | 2^b).
	// Algo: The bit situated at index i, after reversion, its position should be 31
	// - i as index start from 0. We iterate through the bit string of the input
	// integer, from right to left(n = n >> 1). To get the right-most bit of an
	// integer, we apply the modulo/bit AND operation. For each bit we reverse it to
	// the correct position or (n&1) << power and we accululate this reversed bit to
	// the final result. When there is no more bits of 1 left(n == 0), we return.
	// Time complexity - O(1), as the number of iteration = 32 is fixed.
	// Space complexity - O(1)
	private static int reverseBitsBitByBit(int n) {
		int result = 0;
		int power = 31;
		while (n != 0) {
			result |= (n & 1) << power; // or result += (n % 2) << power
			power--;
			n >>= 1;
		}
		return result;
	}

	// Byte by Byte with memoization
	// It's more efficient to reverse the bits, per byte(unit of 8 bits). It's not
	// much efficient in case of 32 bit int but it is when it's long bit stream. We
	// can use memoization while using byte as unit of operation to avoid
	// recalculation. This helps reduce the function calls. We use the algo where we
	// "reverse the bits in a byte with 3 operations". Algo: We iterate over the
	// bytes of an integer. To retrieve the right-most byte in an integer, we apply
	// the bit AND operation(n & 0xff) with bit mask of 11111111. For each byte,
	// first we reverse the bits within the byte, via reverseByte(byte) function,
	// then we shift the reversed bits to their final postions. In reverseByte(byte)
	// function we use memoization to cache the result of the function for future.
	// One can use a smaller unit than byte like 4 bits. This would require a bit
	// more calculation in exchange of less space for cache. Memoizaion is a
	// trade-off between space and computation.
	// Time complexity - O(1), as input is fixed size.
	// Space complexity - O(1), as total number of items in cache is bounded at 2^8.
	private static int reverseBitsByteByByteMemo(int n) {
		int result = 0, power = 24;
		Map<Integer, Integer> cache = new HashMap<>();
		while (n != 0) { // or for (int i = 0; i < 4; i++) {
			result += reverseByte(n & 0xff, cache) << power;
			power -= 8;
			n >>= 8;
		}
		return result;
	}

	private static int reverseByte(int byteVal, Map<Integer, Integer> cache) {
		if (cache.containsKey(byteVal)) {
			return cache.get(byteVal);
		}
		int value = (int) ((byteVal * 0x0202020202L & 0x010884422010L) % 1023);
		cache.put(byteVal, value);
		return value;
	}

	// Mask and Shift
	// To reverse the entire 32 bits without using loop. We can use only the bit
	// operations. It's divide and conquer strategy where we divide the orignal
	// 32-bts into blocks with fewer bits via bit masking, then we reverse each
	// block via bit shifting. At end we merge the result of each block for result.
	// Algo: we break the original 32-bit into 2 blocks of 16 bits and switch them.
	// We then break the 16-bits block into 2 blocks of 8 bits. Again, we switch the
	// position of the 8-bits blocks. We continue break the blocks into smaller
	// blocks, until we reach the level with the block of 1 bit. At each of the
	// above steps, we merge the intermediate results into a single integer which
	// serves as input for next step. Here, we need to do unsigned right shift so
	// that we don't pass on the leftmost 1 bit.
	// Time complexity - O(1), as no loops are used
	// Space complexity - O(1), as no other variables are used.
	private static int reverseBitsMaskAndShift(int n) {
		n = (n >>> 16) | (n << 16);
		n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
		n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
		n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
		n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
		return n;
	}

	// Time complexity - O(1)
	// Space complexity - O(1)
	private static int reverseBitsShiftRevN(int n) {
		int result = 0;
		for (int i = 0; i < 32; i++) {
			result <<= 1;
			if ((n % 2) == 1) { // or (n & 1) == 1
				result++;
			}
			n >>= 1;
		}
		return result;
	}

	// Time complexity - O(1)
	// Space complexity - O(1)
	private static int reverseBitsShiftRevNDirect(int n) {
		int result = 0;
		for (int i = 0; i < 32; i++) {
			result <<= 1;
			result += n % 2; // or reversed += n & 1;
			n >>= 1;
		}
		return result;
	}

	// Time complexity - O(1)
	// Space complexity - O(1)
	public static int reverseBitsTwoPower(int n) {
		int reversed = 0;
		for (int i = 0; i < 32; i++) {
			int currentBit = 1 << i;
			if ((currentBit & n) != 0) {
				reversed |= 1 << (31 - i); // or reversed += 1 << (31 - i)
			}
		}
		return reversed;
	}

	// Time complexity - O(1)
	// Space complexity - O(1)
	private static int reverseBitsTwoPower2(int n) {
		int result = 0;
		for (int i = 0; i < 32; i++) {
			int last = n & 1;
			if (last == 1) {
				result |= 1 << (31 - i);
			}
			n >>= 1;
		}
		return result;
	}
}
