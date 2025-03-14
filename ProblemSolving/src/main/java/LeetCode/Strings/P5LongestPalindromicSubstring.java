package LeetCode.Strings;

/*
 * P5. Longest Palindromic Substring - Medium
 * 
 * Given a string s, return the longest palindromic substring in s.
 * 
 * Approach - DP, Expand from centers
 * 
 * Using char[] is always faster than String directly
 */
public class P5LongestPalindromicSubstring {

	public static void main(String[] args) {
		String s = "cbbd";
//	    String s = "babad";

		String longestPalindromeBF = longestPalindromeBruteForce(s);
		String longestPalindromeDP = longestPalindromeDP(s);
		String longestPalindromeEC = longestPalindromeExpandFromCenters(s);
		String longestPalindromeECNew = longestPalindromeExpandFromCentersNew(s);

		System.out.println("The longest palindromic substring from brute force is " + longestPalindromeBF);
		System.out.println("The longest palindromic substring from dynamic programming is " + longestPalindromeDP);
		System.out.println("The longest palindromic substring from expanding centers is " + longestPalindromeEC);
		System.out.println("The longest palindromic substring from expanding centers new is " + longestPalindromeECNew);
	}

	
	/* Approach: Expand at centers
	* The substrings are found using pointer(s) at center and moving outwards using the idea that palindrome mirrors around center.
	* When focused on bounds of a substring - i,j there were O(n^2) bounds but there are only O(n) centers.
	* For each index i, I can have odd length palindromes by starting at pointer (i, i). For even lengths pointers (i, i+1).
	* There are n starting points for odd-length and (n-1) starting points for even-length palindromes. Total 2n-1 = O(n).
	* Use a helper function expand(i, j) that starts two pointers left = i and right = j where (i, j) is center.
	* When i == j then its odd-length palindrome and if i!=j it's even length palindrome. 
	* Expand from center to far away and return length. 
	* Odd: Center (i, i) returns odd length. Bounds: dist = floor(length/2) -> (i-dist, i+dist).
	* Even: Center (i, i+1) returns even length. Bounds: dist = (length/2 - 1) for length=2 -> (i-dist, i+1+dist).
	* 
	* Take ans{0, 0} to track the substring indices.
	* Iterate for all centers from 0 to n-1 and find palindromes of odd length and even length at each center(i,i) and (i,i+1) respectively.
	* The common expand function returns the length of longest palindrome by checking bounds(0, n) and also chars equality at left and right.
	* The returned length is right - left - 1 and not right - left + 1. Here, 2 is subtracted because s[left] != s[right] when loop ends.
	*  - Odd: If the length is greater than ans[1] - ans[0] + 1, bounding dist = oddLen/2
	*   > The bounds for palindrome is (i-dist, i+dist)
	*  - Even: If the length is greater than a[1] - ans[0] + 1. bounding dist = evenLen/2 - 1
	*   > The bounds for palindrome is (i-dist, i+1+dist)
	*
	* Time complexity - O(n^2), there are 2n-1 or O(n) centers and for each center, expand function of cost O(n) is called.
	*  - The TC is same as DP, but practical runtime of algo is faster because most centers will not produce long palindromes.
	*  - Most expand function cost less than n iterations.
	*  - Worst case when every character in string is same.
	* Space complexity - O(1) since no extra space is needed other than constants.
	*/
	private static String longestPalindromeExpandFromCenters(String s) {
		int[] arr = { 0, 0 };
        char[] charArr = s.toCharArray();
        int n = s.length();
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            int oddLen = expandCenters(charArr, i, i);
            if (maxLen < oddLen) {
                arr[0] = i - oddLen / 2;
                arr[1] = i + oddLen / 2;
                maxLen = oddLen;
            }
            int evenLen = expandCenters(charArr, i, i + 1);
            if (maxLen < evenLen) {
                arr[0] = i - evenLen / 2 + 1;
                arr[1] = i + evenLen / 2;
                maxLen = evenLen;
            }
        }
        return s.substring(arr[0], arr[1] + 1);
        
//		int n = s.length();
//		int ans[] = new int[] { 0, 0 };
//
//		for (int i = 0; i < n; i++) {
//
//			int oddLen = expand(i, i, s);
//			if (oddLen > ans[1] - ans[0] + 1) {
//				int dist = oddLen / 2;
//				ans[0] = i - dist;
//				ans[1] = i + dist;
//			}
//
//			int evenLen = expand(i, i + 1, s);
//			if (evenLen > ans[1] - ans[0] + 1) {
//				int dist = evenLen / 2 - 1;
//				ans[0] = i - dist;
//				ans[1] = i + 1 + dist;
//			}
//		}
//		return s.substring(ans[0], ans[1] + 1);
	}

	private static int expandCenters(char[] arr, int i, int j) {
		while (i >= 0 && j < arr.length && arr[i] == arr[j]) {
			i--;
			j++;
		}
		return j - i - 1;
	}

	private static int expand(int i, int j, String s) {

		int left = i;
		int right = j;

		// the length (right - left + 1) includes +2 for s.charAt(i) != s.charAt(j) as
		// well so remove 2.
		while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
			left--;
			right++;
		}

		return right - left - 1;
	}

	
	/* Approach: Dynamic Programming
	* 
	* The substrings are found using two for loops in such a way that smallest substrings are checked first and tracked.
	* It's based on the condition that if s[i]==s[j] and s(i+1, j-1) is palindrome then s(i, j) inclusive is palindrome
	* The boolean dp[][] is is initialized with dp[i][i] = true for odd length and dp[i][i+1] = (s[i]==s[i+1]) for even length
	* Iterate over all (i, j) pairs, starting from pairs having difference of 2(length 3), 3,..., n-1(length = n)
	*  - For a given difference i has bounds from 0 to n-difference and j = i+diff
	*   > If s[i] == s[j] and dp[i+1][j-1] is true then s(i, j) inclusive is palindrome.
	* 
	* Since, it's bottom up approach, starting from shortest to longest, every time a new palindrome is found, it's longest.
	* This can be used to track answer on fly. Take ans{0, 0} to track the substring indices.
	*
	* Time complexity - O(n^2)
	* Space complexity - O(n^2) for dp[][].
	*/
	public static String longestPalindromeDP(String s) {

		int n = s.length();
		boolean[][] dp = new boolean[n][n];
		int arr[] = new int[] { 0, 0 };

		for (int i = 0; i < n; i++) {
			dp[i][i] = true;
		}
		for (int i = 0; i < n - 1; i++) {
			if (s.charAt(i) == s.charAt(i + 1)) {
				dp[i][i + 1] = true;
				arr[0] = i;
				arr[1] = i + 1;
			}
		}
		for (int diff = 2; diff < n; diff++) {// difference 1 is for odd
			for (int i = 0; i < n - diff; i++) {
				int j = i + diff;
				if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
					dp[i][j] = true;
					arr[0] = i;
					arr[1] = j;
				}
			}
		}

		return s.substring(arr[0], arr[1] + 1);
	}
	
	
	/* Approach: Check all substrings
	* 
	* The substrings are found using two for loops in such a way that biggest substrings are checked first and returned.
	* The first for loop variable(i) tracks the length of substring starting from highest length (n = s.length()) to 1
	* The second for loop(j) tracks the starting point of substring starting from 0 until (total length - current value of i)
	* The substring value is calculated with help of j until j+i. 
	* We check starting from 1 substring of length n, then 2 substring of length n-1, then 3 of len n-2, and so on till n substrings of length 1.
	* For checking whether the value is palindrome use two pointers, and check from extreme ends until middle of the substring.
	* 
	* Time complexity - O(n^3)(worst case) substring check -> 1+2+3+...+n-1 -> f(n^2) and palindrome check f(n) -> O(n^3)
	* Space complexity - O(1) We don't count the answer as part of the space complexity.
	*/
	public static String longestPalindromeBruteForce(String s) {
		int n = s.length();
		// length is the length of the substring
		for (int length = n; length >= 1; length--) {
			// start is the starting point of the substring
			for (int start = 0; start <= n - length; start++) {
				if (isValidSubstring(start, start + length, s)) {
					return s.substring(start, start + length);
				}
			}
		}
		return "";
	}

	private static boolean isValidSubstring(int start, int end, String s) {
		int a = start;
		int b = end - 1;
		while (a < b) {
			if (s.charAt(a) != s.charAt(b)) {
				return false;
			}
			a++;
			b--;
		}
		return true;
	}

	// Approach: Expand from centers
	public static String longestPalindromeExpandFromCentersNew(String s) {
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
