package LeetCode.Strings;

import java.util.Arrays;

/*
 * P3517. Smallest Palindromic Rearrangement I - Medium
 * 
 * You are given a palindromic string s.
 * 
 * Return the lexicographically smallest palindromic permutation of s.
 * 
 * Approach - Sort Half, Counting sort
 */
public class P3517SmallestPalindromicRearrangementI {

	public static void main(String[] args) {
//		String s = "z";
//		String s = "babab";
//		String s = "daccad";
//		String s = "eye";
		String s = "yey";

		String lexicographicallySmallestCountingSortSB = smallestPalindromeCountingSortSB(s);
		System.out
				.println("Counting Sort StringBuilder: The lexicographically smallest palindromic permutation of s is: "
						+ lexicographicallySmallestCountingSortSB);

		String lexicographicallySmallestCountingSortArr = smallestPalindromeCountingSortArr(s);
		System.out.println("Counting Sort Arr: The lexicographically smallest palindromic permutation of s is: "
				+ lexicographicallySmallestCountingSortArr);

		String lexicographicallySmallestSortHalf = smallestPalindromeSortHalf(s);
		System.out.println("Sort Half: The lexicographically smallest palindromic permutation of s is: "
				+ lexicographicallySmallestSortHalf);
	}

	// Counting sort
	// Since the string contains only lowercase English letters, there are only 26
	// possible characters. Instead of using a comparision-based sorting algorithm,
	// we can use counting sort.
	// We first count the frequency of each character in the left half of the
	// string. Then, we scan the frequency array in lexicographical order, placing
	// each character into both left and right halves simultaneously. This directly
	// constructs the lexicographically smallest palindrome without performing an
	// explicit sort.
	// Time complexity - O(n), counting the frequencies and reconstructing the
	// palindrome is done in O(n).
	// Space complexity - O(1), the auxiliary frequency array has a fixed size of
	// 26, independent of the input size.
	private static String smallestPalindromeCountingSortSB(String s) {
		char[] sArr = s.toCharArray();
		int n = sArr.length;
		int partition = n / 2;

		int[] count = new int[26];

		for (int i = 0; i < partition; i++) {
			count[sArr[i] - 'a']++;
		}

		StringBuilder left = new StringBuilder();

		for (int i = 0; i < 26; i++) {
			if (count[i] > 0) {
				left.append(String.valueOf((char) (i + 'a')).repeat(count[i]));
			}
		}

		String mid = n % 2 == 0 ? "" : String.valueOf(sArr[partition]);

		StringBuilder right = new StringBuilder(left).reverse();

		return left.append(mid).append(right).toString();
	}

	// Counting Sort
	private static String smallestPalindromeCountingSortArr(String s) {
		char[] sArr = s.toCharArray();
		int n = sArr.length;

		int[] count = new int[26];

		for (int i = 0; i < n / 2; i++) {
			count[sArr[i] - 'a']++;
		}

		int l = 0;
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < count[i]; j++) {
				sArr[l++] = (char) (i + 'a');
			}
		}

		for (int i = 0; i < n / 2; i++) {
			sArr[n - i - 1] = sArr[i];
		}

		return new String(sArr);
	}

	// Sort the Half
	// A palindrome is symmetric and the original string is already a palindrome,
	// the multiset(set may contain duplicate elements, order may not be important)
	// of characters on each side of the center must remain the same after any valid
	// rearrangement. Therefore, once the arrangement on the left half is
	// determined, the arrangement on right half is uniquely determined. Likewise,
	// when s has odd length, the center character cannot be moved. To obtain the
	// lexicographically smallest palindrome, we sort the left half in ascending
	// order and then mirror it to construct the right half.
	// Time complexity - O(nlogn), the sorting operation dominates the overall
	// complexity.
	// Space complexity - O(n) or O(logn), depending on the language implementation,
	// if the string is modified in place, only the O(logn)
	public static String smallestPalindromeSortHalf(String s) {
		char[] sArr = s.toCharArray();
		int n = sArr.length;

		// 3, 1 | 4, 2
		int partition = n / 2;

		// Arrays.sort(char[] a, int fromIndex, int toIndex) Sorts the specified range
		// of the array into ascending order. The range to be sorted extends from the
		// index fromIndex, inclusive, to the index toIndex, exclusive. If fromIndex
		// == toIndex, the range to be sorted is empty.
		Arrays.sort(sArr, 0, partition);

		for (int i = 0; i < partition; i++) {
			sArr[n - 1 - i] = sArr[i];
		}

		return new String(sArr);
	}

}
