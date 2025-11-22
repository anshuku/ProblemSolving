package CSES;

import java.util.Scanner;

/*
 * You are given a DNA sequence: a string consisting of characters A, C, G, and T. 
 * Your task is to find the longest repetition in the sequence. This 
 * is a maximum-length substring containing only one type of character.
 * 
 * Input
 * The only input line contains a string of n characters.
 * 
 * Output
 * Print one integer: the length of the longest repetition.
 * 
 * Constraints
 * 1 <= n <= 10^6
 * 
 * Example
 * Input: ATTCGGGA
 * Output: 3
 * 
 * Approach - Scanner, Window, 2 vars
 */
public class P3Repetitions {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();

		sc.close();
		maxLengthSequence(str);

		maxLengthSequencewindow(str);
	}

	private static void maxLengthSequence(String str) {
		char[] charArr = str.toCharArray();
		int curr = 1;
		int res = 1;
		for (int i = 1; i < charArr.length; i++) {
			if (charArr[i] != charArr[i - 1]) {
				curr = 0;
			}
			curr++;
			res = Math.max(res, curr);
		}
		System.out.println(res);
	}

	private static void maxLengthSequencewindow(String str) {
		int maxLen = 1;
		char[] charArr = str.toCharArray();
		int start = 0;
		int end = 1;
		for (end = 1; end < charArr.length; end++) {
			while (end < charArr.length && charArr[end] == charArr[start]) {
				end++;
			}
			maxLen = Math.max(maxLen, end - start);
			start = end;
		}
		maxLen = Math.max(maxLen, end - start);
		System.out.println(maxLen);
	}

}
