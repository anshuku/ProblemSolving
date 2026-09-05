package LeetCode.MonotonicStack;

import java.util.Stack;

/*
 * P402. Remove K Digits - Medium
 * 
 * Given string num representing a non-negative integer num, and an integer k, 
 * return the smallest possible integer after removing k digits from num.
 * 
 * Constraints:
 * > 1 <= k <= num.length <= 10^5
 * > num consists of only digits.
 * > num does not have any leading zeros except for the zero itself.
 *
 * Approach - Monotonic Stack
 */
public class P402RemoveKDigits {

	public static void main(String[] args) {
		String num = "1432219";
		int k = 3;

//		String num = "10200";
//		int k = 1;

//		String num = "10";
//		int k = 2;

//		String num = "10";
//		int k = 1;

		String smallestIntegerK = removeKdigits(num, k);
		System.out.println("The smallest integer after removing k digits from num is: " + smallestIntegerK);
	}

	// Greedy with Monotonic stack
	// One may enumerate all the possible cominations and find the minimal number
	// among them, i.e. brute-force. But, with small reflection, one could rule this
	// out. The major caveat is that the algo would have exponential time
	// complexity, as one need to enumerate the combinations of selecting k numbers
	// out a list of n, i.e., nCk. Another issue is to compare the values of two
	// digit strings. One could convert the digit string to a numerical value but
	// the max value which can be stored in an unsigned 32 bit integer is ~2*1e9 or
	// uptil 10 digits. But as per constraints num.length <= 10^5. One may not
	// convert the digit string to its numeric value, but simply compare the
	// sequence of digits one by one from left to right. This can work, but the
	// problem seems that there should be a deterministic way to construct the
	// solution, without need of exhausting all possible solutions.
	// Greedy: Given 2 sequences of digit of the same length, it's the leftmost
	// distinct digits that determines the superior of the two numbers, e.g. for A =
	// 1axxx, B = 1bxxx, if the digits a > b, then A > B. With this insight, we know
	// for our problem is that we should iterate from the left to right, when
	// removing the digits. The more a digit is to the left-hand side, the more
	// weight it carries. To determine the criteria for eliminating the digits, to
	// obtain the minimum value. We can take example of sequence of digits, 425, if
	// we're asked to remove only 1 digit. From left to right we've 4, 2 and 5. We
	// compare each digit with its left neightbour. We find that we must remove 4 to
	// get the lowest number (25). We can deduce that for removing a digit, we
	// follow a rule: Give a sequence of digits |D1D2D3...Dn|, if the digits D2 is
	// less than its left neighbor(s) D1, then we should remove the left neighbor
	// (D1) in order to obtain the minimum result.
	// Algorithm:
	// The above rule is the only key needed to solve the problem. It clearly
	// defines the condition to remove a digit without any doubt. By removing digits
	// 1 by 1, we're approaching the optimal solution step by step. Hence this
	// follows Greedy algo. Once we remove a digit from the sequence, the remaining
	// digits forms a new problem where we can continue to apply the rule.
	// Corner Case: There are certain cases where the condition to apply the rule
	// doesn't hold for any of the digits. This is case of monotonically increasing
	// sequence, where each digit is bigger than its previous digit. In this case,
	// we remove the pending large digits, again greedily.
	// Implementation:
	// We use a stack to hold the digits that we would keep at the end. Iterating
	// the sequence of digits from left to right, the main loop can be broken down
	// as follows:
	// 1) For each digit, if the digit is less than the top of the stack, i.e. the
	// left neighbor of the digit, then we pop the stack, i.e. removing the left
	// neighbor. At the end, we push the digit to the stack.
	// 2) We keep on repeating step 1 until any of the conditions doesn't hold
	// anymore, e.g. the stack is empty (no more digits left). or in another case,
	// have already removed k digits, so we are done.
	// Out of the the main loop, we need to handle some corner cases for completion:
	// Case 1) We we remove m digits (m < k) and come out of the main loop. This is
	// the extreme case of monotonic increasing sequence in the loop (m==0 as well).
	// In this case, we remove the addition k - m digits from the tail of seqeunce.
	// Case 2) We removed the required k digits, but there are some trailin zeros
	// leeft. We format the final number via stripping off leading zeros.
	// Case 3) We might end up removing all numbers from the sequence. In this case,
	// we need to return 0, instead of "".
	// Time compelxity - O(n), Although there are nested loops, inner loop is
	// bounded to run at most k times globally. Together with the outer loop, we've
	// exact (n + k) number of operations. Since 0 < k <= N, the time taken by main
	// loop is bounded within 2N. For logic outside main loop, the time taken is
	// O(n).
	// Space compelxity - O(n), as the stack might keep all the input digits in
	// the worst case.
	public static String removeKdigits(String num, int k) {
		char[] nums = num.toCharArray();
		int n = nums.length;

		if (k == n) {
			return "0";
		}

		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < n; i++) {
			while (!stack.isEmpty() && k > 0 && stack.peek() > nums[i]) {
				stack.pop();
				k--;
			}
			stack.push(nums[i]);
		}

		// Remove remaining digits from the tail.
		while (k-- > 0) {
			stack.pop();
		}

		StringBuilder temp = new StringBuilder();

		boolean leadingZero = true;

		// Build the final string, while removing the leading zeros
		for (char c : stack) {
			if (leadingZero && c == '0') {
				continue;
			}
			leadingZero = false;
			temp.append(c);
		}

		String result = temp.toString();
		return result == "" ? "0" : result;
	}

}
