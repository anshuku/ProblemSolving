package LeetCode.Stack;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/*
 * P394. Decode String - Medium
 * 
 * Given an encoded string, return its decoded string.
 * The encoding rule is: k[encoded_string], where the encoded_string inside the 
 * square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
 * 
 * You may assume that the input string is always valid; there are no extra white spaces, 
 * square brackets are well-formed, etc. Furthermore, you may assume that the original data 
 * does not contain any digits and that digits are only for those repeat numbers, k. 
 * For example, there will not be input like 3a or 2[4].
 * 
 * The test cases are generated so that the length of the output will never exceed 105.
 * 
 * Approach - Stack, Deque
 */
public class P394DecodeString {

	public static void main(String[] args) {

//		String s = "3[a]2[bc]";

		String s = "3[a2[c]]";

//		String s = "2[abc]3[cd]ef";

//		String s = "100[leetcode]";

		String decodedStackSB = decodeStringStackSB(s);
		System.out.println("Stack and String Builder: The decoded string is - " + decodedStackSB);

		String decodedDequeSB = decodeStringDeque(s);
		System.out.println("Deque and String Builder: The decoded string is - " + decodedDequeSB);

		String decodedStack = decodeStringStack(s);
		System.out.println("Stack: The decoded string is - " + decodedStack);

	}

	private static String decodeStringStackSB(String s) {
		Stack<StringBuilder> stack = new Stack<>();
		Stack<Integer> numStack = new Stack<>();
		StringBuilder currStr = new StringBuilder();
		int number = 0;
		for (char c : s.toCharArray()) {
			if (Character.isDigit(c)) {
				number = number * 10 + c - '0';
			} else if (Character.isLetter(c)) {
				currStr.append(c);
			} else if (c == '[') {
				stack.push(currStr);
				currStr = new StringBuilder();
				numStack.push(number);
				number = 0;
			} else {// c = '['
				StringBuilder sb = stack.pop();
				int num = numStack.pop();
				while (num != 0) {
					sb.append(currStr);
					num--;
				}
				currStr = sb;
			}
		}
		return currStr.toString();
	}

	private static String decodeStringDeque(String s) {
		Deque<StringBuilder> sbDeque = new LinkedList<>();
		Deque<Integer> numDeque = new LinkedList<>();
		StringBuilder currStr = new StringBuilder();
		int num = 0;
		for (char c : s.toCharArray()) {
			if (c >= '0' && c <= '9') {
				num = num * 10 + c - '0';
			} else if (c >= 'a' && c <= 'z') {
				currStr.append(c);
			} else if (c == '[') {
				sbDeque.addLast(currStr);
				currStr = new StringBuilder();
				numDeque.addLast(num);
				num = 0;
			} else { // c = '['
				StringBuilder sb = sbDeque.pollLast();
				int count = numDeque.pollLast();
				while (count != 0) {
					sb.append(currStr);
					count--;
				}
				currStr = sb;
			}
		}
		return currStr.toString();
	}

	public static String decodeStringStack(String s) {
		Stack<Character> stack = new Stack<>();
		StringBuilder result = new StringBuilder();
		char[] charArr = s.toCharArray();

		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i] != ']') {
				stack.push(charArr[i]);
			} else {
				StringBuilder sb = new StringBuilder();
				while (stack.peek() != '[') {
					sb.append(stack.pop());
				}
				String str = sb.reverse().toString();

				stack.pop();// Removes [

				sb = new StringBuilder();
				while (!stack.isEmpty() && stack.peek() - '0' >= 0 && stack.peek() - '0' <= 9) {
					sb.append(stack.pop());
				}
				int num = Integer.parseInt(sb.reverse().toString());

				while (num > 0) {
					for (char c : str.toCharArray()) {
						stack.push(c);
					}
					num--;
				}
			}
		}
		for (char c : stack) {
			result.append(c);
		}
		return result.toString();

	}

}
