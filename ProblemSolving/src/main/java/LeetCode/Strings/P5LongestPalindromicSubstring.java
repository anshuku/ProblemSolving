package LeetCode.Strings;

//Given a string s, return the longest palindromic substring in s.
public class P5LongestPalindromicSubstring {

	public static void main(String[] args) {
		String s = "cbbd";
		String longestPalindrome = longestPalindrome(s);

		System.out.println("The longest palindromic substring is " + longestPalindrome);
	}

	public static String longestPalindrome(String s) {
		if (s.length() <= 1) {
			return s;
		}

		int st = 0, end = 0, n = s.length();
		int maxLen = 1;
		String str = "";
		// odd
		for (int i = 0; i < n - 1; i++) {
			int l = i, r = i;
			while (l >= 0 && r < n) {
				if (s.charAt(l) == s.charAt(r)) {
					l--;
					r++;
				} else {
					break;
				}
			}

			int len = r - l - 1;
			if (maxLen < len) {
				maxLen = len;
				st = l + 1;
				end = r - 1;
			}
		}

		// even
		for (int i = 0; i < n - 1; i++) {
			int l = i, r = i + 1;
			while (l >= 0 && r < n) {
				if (s.charAt(l) == s.charAt(r)) {
					l--;
					r++;
				} else {
					break;
				}
			}
			int len = r - l - 1;
			if (maxLen < len) {
				maxLen = len;
				st = l + 1;
				end = r - 1;
			}
		}
		return s.substring(st, end + 1);

	}
}
