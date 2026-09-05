package LeetCode.MonotonicStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/*
 * P316. Remove Duplicate Letters - Medium
 * 
 * Given a string s, remove duplicate letters so that every letter appears once and only once. 
 * You must make sure your result is the smallest in lexicographical order among all possible results.
 * 
 * Approach - Greedy: Monotonic stack, String, Array, Count
 * 
 * Lexicographically smaller:
 * A String a is lexicographically smaller than String b, if the 1st position where the Strings differs,
 * String a has a letter that appears earlier in alphabet than corresponding letter in string b.
 * If the first min(a.length, b.length) characters do not differ, then the shorter string is lexicographically 
 * smaller one.
 */
public class P316RemoveDuplicateLetters {

	public static void main(String[] args) {
//		String s = "bcabc";
		String s = "cbacdcbc";

		String smallestUniqueMStack = removeDuplicateLettersMStack(s);
		System.out.println(
				"Monotonic Stack: The smallest unique string after removing duplicates is: " + smallestUniqueMStack);

		String smallestUniqueSmallestLeft = removeDuplicateLettersSmallestLeft(s);
		System.out.println("Smallest Left: The smallest unique string after removing duplicates is: "
				+ smallestUniqueSmallestLeft);
	}

	// Greedy: Solving letter by letter
	// String are compared from the 1st character to the last one. Which string is
	// greater depends on the comparision between the first unequal corresponding
	// character in the 2 strings. Hence, any string beginning with 'a' will always
	// be less than any string beginning with 'b', regardless of the ends of boths
	// strings. The optimal solution will have the smallest character as early as
	// possible.
	// As we iterate over the string, if character at index i is greater than
	// character at i + 1 and another occurrence of character at i exists later in
	// the string, deleting character i will always lead to the optimal osultion.
	// Also, characters that come later in the string i don't matter in this as i is
	// in a more significant spot. Even if character i+1 isn't the best yet, we can
	// always replace it for a smaller character down the line if possible. Here we
	// try to remove characters as early as possible, and picking the best letter at
	// each step leads to the best solution, hence this a greed approach.
	// We use a stack to store the solution as we iterate over the string. We will
	// delete characters off the stack whenever it's possible and this makes the
	// string smaller. Each iteration we add the current character to the solution
	// if it hasn't already been used (by checking the seen set). We try to remove
	// as many characters as possible off the top of the stack, and then add the
	// current character.
	// The conditions for deletions are:
	// > The stack must not be empty.
	// > The character at the top must be greater than current character.
	// > The character at the top can be removed as it occurs later in the string.
	// At each stage in the iteration, we greedily keep what's on the stack as small
	// as possible.
	// Time complexity - O(n), although there is a nested loop, the time is still
	// O(n). This is because the inner while loop is bounded by the total number of
	// elements added to the stack (each time it pops, an element goes). This means
	// that the total amount of time spent in the inner loop is bounded by O(n),
	// giving total O(n) time.
	// Space compelxity - O(1), it looks O(n) but it's not true, as we use seen and
	// hashmap which contains constant O(26) characters at most or it's bounded by
	// the number of unqiue characters. Also, an element can be added to the stack,
	// if the element has not been seen, so stack only contains unique elements.
	private static String removeDuplicateLettersMStack(String s) {
		char[] sArr = s.toCharArray();
		int n = sArr.length;

		// Helps to find what is there in the solution stack in O(1) time.
		Set<Character> seen = new HashSet<>();

		Stack<Character> stack = new Stack<>();

		// HashMap helps to know whether there is any more instance of char s[i] left in
		// the string s.
		Map<Character, Integer> lastOccurrence = new HashMap<>();
		for (int i = 0; i < n; i++) {
			lastOccurrence.put(sArr[i], i);
		}

		for (int i = 0; i < n; i++) {
			// We can only try to add c, if it's not already in the solution
			// This is to maintain only 1 of each character.
			if (!seen.contains(sArr[i])) {
				// If the last letter in our solution:
				// 1. exists, 2. is > c so removing it will make the string smaller, 3. It's not
				// the last occurrence.
				// We'll remove it from the solution to keep the solution optimal.
				while (!stack.isEmpty() && stack.peek() > sArr[i] && lastOccurrence.get(stack.peek()) > i) {
					seen.remove(stack.pop());
				}

				stack.push(sArr[i]);
				seen.add(sArr[i]);
			}
		}

		StringBuilder sb = new StringBuilder();
//		while (!stack.isEmpty()) {
//			sb.append(stack.pop());
//		}
//
//		return sb.reverse().toString();

		for (char c : stack) {
			sb.append(c);
		}

		return sb.toString();
	}

	// Greedy: Solving letter by letter
	// The optimal solution will smallest character as early as possible.
	// The leftmost letter in the solution will be the smallest letter such that the
	// suffix from that letter contains every other. This is because we know that
	// the solution must have copy of every letter, and we know that the solution
	// will have the lexicographically smallest leftmost character possible.
	// If there are multiple smallest letter, then we pick the leftmost one simply
	// because it gives more options, We can always eliminate letter later on, so
	// the optional solution will always remain in our search space.
	// Since we try to remove characters as early as possible, and picking the best
	// letter at each step leads to the best solution, "greedy" should be used.
	// Here, in each iteration, we determine leftmost letter in our solution which
	// is the smallest character such that its suffix contains at least 1 copy of
	// every character in the string. We find the rest of our answer by recursively
	// calling the function on the suffix we generate by removing the leftmost
	// letter. This letter is removed in all the places in this suffix.
	// Time complexity - O(n or n^2), each recursive call takes O(n). The number of
	// recursive calls is bounded by a constant (26 letters in alphabet), so we have
	// O(n)*c = O(n).
	// Space complexity - O(n), each time we slice the string we're creating a new
	// one (strings are immutable). The number of slices are bounded by a constant,
	// so we've O(n)*C = O(n)
	public static String removeDuplicateLettersSmallestLeft(String s) {
		char[] sArr = s.toCharArray();
		int n = sArr.length;

		if (n == 0) {
			return "";
		}

		// counter is used to find count of each unique character
		int[] count = new int[26];
		for (char c : sArr) {
			count[c - 'a']++;
		}

		// The index of the leftmost letter in our solution
		int pos = 0;
		// We end the iteration once the suffix doesn't have each uniqe character which
		// is tracked via count array.
		for (int i = 0; i < n; i++) {
			// pos will be the index of the smallest character we encounter, at the end of
			// the iteration.
			if (sArr[pos] > sArr[i]) {
				pos = i;
			}
			if (--count[sArr[i] - 'a'] == 0) {
				break;
			}
		}

		// The answer is the leftmost character + the recursive call on the remainder of
		// the string
		// We remove the further occurrences of sArr[pos] to ensure there are no
		// duplicates.
		return sArr[pos] + removeDuplicateLettersSmallestLeft(s.substring(pos + 1).replaceAll("" + sArr[pos], ""));
	}

}
