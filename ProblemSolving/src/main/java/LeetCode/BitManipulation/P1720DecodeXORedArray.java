package LeetCode.BitManipulation;

import java.util.Arrays;

/*
 * P1720. Decode XORed Array - Easy
 * 
 * There is a hidden integer array arr that consists of n non-negative integers.
 * 
 * It was encoded into another integer array encoded of length n - 1, such that 
 * encoded[i] = arr[i] XOR arr[i + 1]. For example, if arr = [1,0,2,1], then encoded = [1,2,3].
 * 
 * You are given the encoded array. You are also given an 
 * integer first, that is the first element of arr, i.e. arr[0].
 * 
 * Return the original array arr. It can be proved that the answer exists and is unique.
 * 
 * Approach - Bit Manipulation
 */
public class P1720DecodeXORedArray {

	public static void main(String[] args) {
//		int[] encoded = { 1, 2, 3 };
//		int first = 1;

		int[] encoded = { 6, 2, 7, 3 };
		int first = 4;

		int[] originalArray = decode(encoded, first);
		System.out.println("The original array before encoding is: " + Arrays.toString(originalArray));
	}

	//   1 2 3 |   1 10 11
	// 1 0 2 1 | 1 0 10  1
	//   6 2 7 3 |     110 10 111  11
	// 4 2 0 7 4 | 100  10  0 111 100  
	public static int[] decode(int[] encoded, int first) {
		int n = encoded.length;
		int[] result = new int[n + 1];
		result[0] = first;
		for (int i = 0; i < n; i++) {
			result[i + 1] = result[i] ^ encoded[i];
		}
		return result;
	}

}
