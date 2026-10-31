package LeetCode.BitManipulation;

import java.util.Arrays;

/*
 * P2433. Find The Original Array of Prefix Xor - Medium
 * 
 * You are given an integer array pref of size n. Find and return the array arr of size n that satisfies:
 * pref[i] = arr[0] ^ arr[1] ^ ... ^ arr[i].
 * 
 * Note that ^ denotes the bitwise-xor operation.
 * 
 * It can be proven that the answer is unique.
 * 
 * Constraints:
 * > 1 <= pref.length <= 10^5
 * > 0 <= pref[i] <= 10^6
 * 
 * Approach - XOR Properties: Mathematical
 */
public class P2433FindTheOriginalArrayOfPrefixXor {

	public static void main(String[] args) {
		int[] pref = { 5, 2, 0, 3, 1 };
//		int[] pref = { 13 };

		int[] pref2 = Arrays.copyOf(pref, pref.length);

		int[] arrReverse = findArrayReverse(pref);
		System.out.println("Reverse: The array satisfying the XOR criteria is: " + Arrays.toString(arrReverse));

		pref = pref2;
		int[] arrForward = findArrayForward(pref);
		System.out.println("Forward: The array satisfying the XOR criteria is: " + Arrays.toString(arrForward));
	}

	// XOR Properties: Space optimized
	// To find the value at an index i in the array arr we need the values at
	// indices i and i - 1 in the array pref. In the previous approach, since we
	// were iterating from left to right, we needed a separate array to store the
	// values. This is because when we calculate the value of index i, we can't put
	// it directly into the array pref to override pref[i], because we need to use
	// the original value of pref[i] again when we compute the value of arr[i + 1]
	// later.
	// Therefore, instead of iterating from left to right, we will iterate from
	// right to left and store the answers directly in the array pref itself. This
	// way, when we store the value for index i in the pref using pref[i] ^ pref[i -
	// 1] we don't need the value of pref[i] again. Because the next value to be
	// calculated is at index i - 1 which will be calculated as pref[i - 1] ^ pref[i
	// - 2]. In this way, we can use the original input array pref itself to store
	// the answer.
	// Time complexity - O(n).
	// Space complexity - O(1).
	private static int[] findArrayReverse(int[] pref) {
		int n = pref.length;

		for (int i = n - 1; i > 0; i--) { // i =/= 0 as i - 1 < 0 in below equation.
			pref[i] = pref[i] ^ pref[i - 1];
		}
		return pref;
	}

	// XOR Properties: Mathematical Induction
	// pref[i] = arr[0] ^ arr[1] ^ ... ^ arr[i].
	// pref[i]^pref[i + 1] = arr[0] ^ arr[1] ^ ... ^ arr[i] ^ arr[0] ^ arr[1] ^ ...
	// ^ arr[i] ^ arr[i + 1]
	// Since a ^ a = 0, we get terms cancelled
	// pref[i]^pref[i + 1] = arr[i+1]
	// This uses XOR Associative and Commutative properties.
	// Also, arr[0] = pref[0]
	// Time complexity - O(n).
	// Space complexity - O(1).
	public static int[] findArrayForward(int[] pref) {
		int n = pref.length;

		int[] arr = new int[n];
		arr[0] = pref[0];

		for (int i = 1; i < n; i++) {
			arr[i] = pref[i] ^ pref[i - 1];
		}

		return arr;
	}

}
