package LeetCode.Backtracking;

import java.util.ArrayList;
import java.util.List;

/*
 * P131. Palindrome Partitioning - Medium
 * 
 * Given a string s, partition s such that every substring of the partition is a palindrome. 
 * Return all possible palindrome partitioning of s.
 */
public class P131PalindromePartitioning {

	public static void main(String[] args) {

//		String s = "aab";
//		String s = "a";
//		String s = "abb";
		String s = "aba";

		List<List<String>> partitions = partition(s);

		System.out.println("The palindrome partitons are - " + partitions);

	}

	public static List<List<String>> partition(String s) {

		List<List<String>> result = new ArrayList<>();
		recursive(result, new ArrayList<String>(), s, 0);
		return result;
	}

	private static void recursive(List<List<String>> result, List<String> list, String s, int start) {
		if (start == s.length()) {
			result.add(new ArrayList<>(list));
			return;
		}
		for (int i = start; i < s.length(); i++) {
			String str = s.substring(start, i + 1);
			if (isPalindrome(str)) {
				list.add(str);
				recursive(result, list, s, i + 1);
				list.remove(list.size() - 1);
			}
		}
	}

	private static boolean isPalindrome(String str) {
		int n = str.length();
		for (int i = 0; i < n / 2; i++) {
			if (str.charAt(i) != str.charAt(n - i - 1)) {
				return false;
			}
		}
		return true;
	}

}
