package LeetCode.Stack;

import java.util.Stack;

/*
 * P2390. Removing Stars From a String - Medium
 * 
 * You are given a string s, which contains stars *.
 * In one operation, you can:
 * - Choose a star in s.
 * - Remove the closest non-star character to its left, as well as remove the star itself.
 * Return the string after all stars have been removed.
 * Note:
 * The input will be generated such that the operation is always possible.
 * It can be shown that the resulting string will always be unique.
 * 
 * Approach - Stack
 */
public class P2390RemovingStarsFromString {

	public static void main(String[] args) {

		String s = "leet**cod*e";

//		String s = "erase*****";

		String starsRemovedCharArr = removeStarsCharArr(s);
		System.out.println("Char Array: The string after removing stars - " + starsRemovedCharArr);

		String starsRemovedSBReverse = removeStarsSBReverse(s);
		System.out.println("String Builder Reverse: The string after removing stars - " + starsRemovedSBReverse);

		String starsRemovedSB = removeStarsSB(s);
		System.out.println("String Builder: The string after removing stars - " + starsRemovedSB);

		String starsRemovedStack = removeStarsStack(s);
		System.out.println("Stack: The string after removing stars - " + starsRemovedStack);

	}

	private static String removeStarsCharArr(String s) {
		char[] charArr = s.toCharArray();
		int pointer = 0;
		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i] == '*') {
				pointer--;
			} else {
				charArr[pointer++] = charArr[i];
			}
		}
		return new String(charArr, 0, pointer);
	}

	private static String removeStarsSBReverse(String s) {
		StringBuilder sb = new StringBuilder();
		char[] charArr = s.toCharArray();
		int stars = 0;
		for (int i = charArr.length - 1; i >= 0; i--) {
			if (charArr[i] == '*') {
				stars++;
			} else if (stars > 0) {
				stars--;
			} else {
				sb.append(charArr[i]);
			}
		}
		return sb.reverse().toString();
	}

	private static String removeStarsSB(String s) {
		StringBuilder sb = new StringBuilder();
		char[] charArr = s.toCharArray();
		for (char c : charArr) {
			if (c == '*') {
				sb.deleteCharAt(sb.length() - 1);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String removeStarsStack(String s) {
		Stack<Character> stack = new Stack<>();
		char[] charArr = s.toCharArray();
		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i] == '*') {
				stack.pop();
			} else {
				stack.push(charArr[i]);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (char c : stack) {
			sb.append(c);
		}
		return sb.toString();
	}

}
