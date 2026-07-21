package LeetCode.Arrays;

import java.util.Arrays;

/*
 * P3992. Rearrange String to Avoid Character Pair - Easy
 * 
 * You are given a string s and two distinct lowercase English letters x and y.
 * 
 * Rearrange the characters of s to construct a new string t such that:
 * > t is a permutation of s.
 * > Every occurrence of y appears before every occurrence of x in t.
 * 
 * Return any valid string t.
 * 
 * Approach - Iteration with variable, Sorting
 */
public class P3992RearrangeStringToAvoidCharacterPair {

	public static void main(String[] args) {
//		String s = "aabc";
//		char x = 'a';
//		char y = 'c';

//		String s = "dcab";
//		char x = 'd';
//		char y = 'b';

//		String s = "axe";
//		char x = 'o';
//		char y = 'x';

		String s = "zmrcf";
		char x = 'f';
		char y = 'm';

//		String s = "rradp";
//		char x = 'r';
//		char y = 'd';

		String rearrangedStrMoveEndStart = rearrangeStringMoveEndStart(s, x, y);
		System.out.println("Move to End and Start: The rearranged valid string s is: " + rearrangedStrMoveEndStart);

		String rearrangedStrSort = rearrangeStringSort(s, x, y);
		System.out.println("Sort: The rearranged valid string s is: " + rearrangedStrSort);
	}

	public static String rearrangeStringMoveEndStart(String s, char x, char y) {
		char[] sArr = s.toCharArray();
		int n = sArr.length;

		// x goes to end
		int j = 0;
		for (int i = 0; i < n; i++) {
			if (sArr[i] != x) {
				char temp = sArr[i];
				sArr[i] = sArr[j];
				sArr[j] = temp;
				j++;
			}
		}

		// y comes to front
		j = n - 1;
		for (int i = n - 1; i >= 0; i--) {
			if (sArr[i] != y) {
				char temp = sArr[i];
				sArr[i] = sArr[j];
				sArr[j] = temp;
				j--;
			}
		}

		return new String(sArr);
	}

	private static String rearrangeStringSort(String s, char x, char y) {
		char[] sArr = s.toCharArray();

		Arrays.sort(sArr);

		if (x > y) {
			return new String(sArr);
		}

		int n = sArr.length;

		for (int i = 0; i < n / 2; i++) {
			char temp = sArr[i];
			sArr[i] = sArr[n - i - 1];
			sArr[n - i - 1] = temp;
		}

		return new String(sArr);
	}
}
