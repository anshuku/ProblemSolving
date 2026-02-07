package LeetCode.Strings;

import java.util.ArrayList;
import java.util.List;

/*
 * P2942. Find Words Containing Character - Easy
 * 
 * You are given a 0-indexed array of strings words and a character x.
 * 
 * Return an array of indices representing the words that contain the character x.
 * 
 * Note that the returned array may be in any order.
 * 
 * Approach - Strings
 */
public class P2942FindWordsContainingCharacter {

	public static void main(String[] args) {
		String[] words = { "leet", "code" };
		char x = 'e';

//		String[] words = { "abc", "bcd", "aaaa", "cbc" };
//		char x = 'a';

//		String[] words = { "abc", "bcd", "aaaa", "cbc" };
//		char x = 'z';

		List<Integer> containsChar2Loops = findWordsContaining2Loops(words, x);
		System.out.println("2 Loops: The words that contain the character x are: " + containsChar2Loops);

		List<Integer> containsCharString = findWordsContainingString(words, x);
		System.out.println("String: The words that contain the character x are: " + containsCharString);
	}

	// Time complexity - O(n*m), where n is length of words and m = average length
	// of each word in words.
	// Space complexity - O(1), as space required for result is not considered.
	private static List<Integer> findWordsContainingString(String[] words, char x) {
		List<Integer> result = new ArrayList<Integer>();
		int n = words.length;
		for (int i = 0; i < n; i++) {
			if (words[i].indexOf(x) != -1) {
				result.add(i);
			}
		}
		return result;
	}

	public static List<Integer> findWordsContaining2Loops(String[] words, char x) {
		List<Integer> result = new ArrayList<Integer>();
		int n = words.length;
		for (int i = 0; i < n; i++) {
			for (char c : words[i].toCharArray()) {
				if (c == x) {
					result.add(i);
					break;
				}
			}
		}
		return result;
	}

}
