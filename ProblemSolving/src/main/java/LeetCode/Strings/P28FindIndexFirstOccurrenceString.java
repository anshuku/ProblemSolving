package LeetCode.Strings;

/*
 * P28. Find the Index of the First Occurrence in a String - Easy
 * 
 * Given two strings needle and haystack, return the index of the first occurrence of needle in haystack, 
 * or -1 if needle is not part of haystack.
 * 
 * Implement strStr()
 * 
 * Approach - KMP Algorithm | Iteration, substrings
 */
public class P28FindIndexFirstOccurrenceString {

	public static void main(String[] args) {
		String haystack = "leetcode";
		String needle = "code";
		int index = strStr(haystack, needle);
		System.out.println("The needle is present at index " + index);

		int idx = strStrKmp(haystack, needle);
		System.out.println("The needle is present at index via KPM Algorithm " + idx);

	}

	private static int strStrKmp(String haystack, String needle) {
		int n = haystack.length();
		int m = needle.length();
		if (n < m) {
			return -1;
		}
		int lps[] = createLpsArr(needle);
		int i = 0, j = 0;
		while (i < n) {
			if (haystack.charAt(i) == needle.charAt(j)) {
				i++;
				j++;
			}
			if (j == m) {
				return i - j;
			} else if (i < n && haystack.charAt(i) != needle.charAt(j)) {
				if (j > 0) {
					j = lps[j - 1];
				} else {
					i++;
				}
			}
		}

		return -1;
	}

	private static int[] createLpsArr(String needle) {
		int m = needle.length();
		int len = 0;
		int lps[] = new int[m];
		lps[0] = 0;
		int i = 1;
		while (i < m) {
			if (needle.charAt(i) == needle.charAt(len)) {
				len++;
				lps[i++] = len;
			} else {
				if (len > 0) {
					len = lps[len - 1];
				} else {
					lps[i] = 0;
					i++;
				}
			}
		}
		return lps;
	}

	public static int strStr(String haystack, String needle) {
		int n = haystack.length();
		int m = needle.length();
		if (n < m) {
			return -1;
		}
		for (int i = 0; i <= n - m; i++) {
			if (haystack.substring(i, i + m).equals(needle)) {
				return i;
			}
		}
		return -1;
	}

}
