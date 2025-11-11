package LeetCode.DynamicProgramming;

/*
 * P91. Decode Ways - Medium
 * 
 * You have intercepted a secret message encoded as a string of numbers. The message is decoded via the following mapping:
 * "1" -> 'A'
 * "2" -> 'B'
 * ...
 * "25" -> 'Y'
 * "26" -> 'Z'
 * 
 * However, while decoding the message, you realize that there are many different ways you 
 * can decode the message because some codes are contained in other codes ("2" and "5" vs "25").
 * 
 * For example, "11106" can be decoded into:
 * "AAJF" with the grouping (1, 1, 10, 6)
 * "KJF" with the grouping (11, 10, 6)
 * The grouping (1, 11, 06) is invalid because "06" is not a valid code (only "6" is valid).
 * Note: there may be strings that are impossible to decode.
 * 
 * Given a string s containing only digits, return the number of ways to decode it. 
 * If the entire string cannot be decoded in any valid way, return 0.
 * 
 * The test cases are generated so that the answer fits in a 32-bit integer.
 * 
 * Approach - DP
 */
public class P91DecodeWays {

	public static void main(String[] args) {
//		String s = "12";
//		String s = "226";
//		String s = "06";
//		String s = "10";
		String s = "2101";

		int numDecodes2Vars = numDecodings2Vars(s);
		System.out.println("2 Vars: The number of decoding for the given string is: " + numDecodes2Vars);

		int numDecodes2VarsRev = nmDecodings2VarsRev(s);
		System.out.println("2 Vars Rev: The number of decoding for the given string is: " + numDecodes2VarsRev);

		int numDecodesTabulation = numDecodingsTabulation(s);
		System.out.println("Tabulation: The number of decoding for the given string is: " + numDecodesTabulation);

		int numDecodesTabulationRev = numDecodingsTabulationRev(s);
		System.out.println(
				"Tabulation Reverse: The number of decoding for the given string is: " + numDecodesTabulationRev);

		int numDecodesMemoized = numDecodingsMemoized(s);
		System.out.println("Memoized: The number of decoding for the given string is: " + numDecodesMemoized);

		int numDecodesMemoized2 = numDecodingsMemoized2(s);
		System.out.println("Memoized2: The number of decoding for the given string is: " + numDecodesMemoized2);
	}

	// DP Iterative 2 Variables - Top Down
	// As we iterate through the string s character by character we look back only 2
	// steps. For calculating dp[i] we need to know dp[i-1] and dp[i-2] only. Thus
	// we can easily cut down our O(N) space requirements to O(1) by using 2
	// variables to store the last 2 results.
	// Time complexity - O(n) for iterating though s.
	// Space complexity - O(1) for 2 variables
	private static int numDecodings2Vars(String s) {
		if (s.charAt(0) == '0') {
			return 0;
		}
		int n = s.length();
		int oneBack = 1;
		int twoBack = 1;
		for (int i = 2; i <= n; i++) {
			int curr = 0;
			if (s.charAt(i - 1) != '0') {
				curr += oneBack;
			}
			int num = Integer.parseInt(s.substring(i - 2, i));
			if (num >= 10 && num <= 26) {
				curr += twoBack;
			}
			twoBack = oneBack;
			oneBack = curr;
		}
		return oneBack;
	}

	private static int nmDecodings2VarsRev(String s) {
		if (s.charAt(0) == '0') {
			return 0;
		}
		int n = s.length();
		int oneNext = 1;
		int twoNext = 1;
		for (int i = n - 1; i >= 0; i--) {
			if (s.charAt(i) == '0') {
				twoNext = oneNext;
				oneNext = 0;
				continue;
			}
			int curr = oneNext;
			if (i + 1 < n) {
				int num = Integer.parseInt(s.substring(i, i + 2));
				if (num <= 26) {
					curr += twoNext;
				}
			}
			twoNext = oneNext;
			oneNext = curr;
		}
		return oneNext;
	}

	// DP Iterative Tabulation - Bottom up
	// dp[i] is used to store the number of decode ways for substring of s from
	// index 0 to i-1. Initialize the starting 2 indices with 1. As we iterate the
	// dp array from left to right these 2 values which signifies the number of ways
	// of decoding is passed to the next index or not depending on whether the
	// decode is possible. dp[i] is dependent upon either i-1 or i-2 as both single
	// and 2 digit decodes are possible.
	// Here the index i of dp is the ith character of string s, that is character at
	// index i-1 of s.
	// Time complexity - O(n) as we iterate the length of dp array of length n
	// Space complexity - O(n) for dp array.
	private static int numDecodingsTabulation(String s) {
		int n = s.length();
		if (s.charAt(0) == '0') {
			return 0;
		}
		int[] dp = new int[n + 1];
		dp[0] = dp[1] = 1;
		for (int i = 2; i <= n; i++) {
			if (s.charAt(i - 1) != '0') {
				dp[i] = dp[i - 1];
				// below commented code doesn't works in forward traversal as we've dp[1] = 1
				// and there can be code as 10 and 20 so 2 digit code can be used.
//				dp[i] = 0;
//				continue;
			}
			int num = Integer.parseInt(s.substring(i - 2, i));
			// only num <= 26 check is not enough we must check num >= 10 as well
			// this is because numbers like 01 will be passed to the <= 26 check.
			if (num >= 10 && num <= 26) {
				dp[i] += dp[i - 2];
			}
		}
		return dp[n];
	}

	// DP Iterative Tabulation - Bottom up
	// dp[i] denotes the number of decodings from ith character onwards till end.
	private static int numDecodingsTabulationRev(String s) {
		int n = s.length();
		int[] dp = new int[n + 1];
		dp[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			if (s.charAt(i) == '0') {
				dp[i] = 0;
				continue;
			}
			dp[i] += dp[i + 1];
			if (i + 1 < n) {
				int num = Integer.parseInt(s.substring(i, i + 2));
				if (num <= 26) {
					dp[i] += dp[i + 2];
				}
			}
		}
		return dp[0];
	}

	// DP Recursive Memoization - Top Down
	// The subproblem is to find the number of ways to decode the substring.
	// The key idea is - Why there would be many ways to decode a string. The reason
	// is at any given point we either decode using two digits or single digit. This
	// choice leads can lead to different combinations of decoding a string.
	// At any given time for a string we enter a recursion after successfully
	// decoding two digits to a single character or a single digit to a character
	// which leads to multiple paths to decode entire string. If given path leads to
	// end of string this means we could successfully decode the string. If at any
	// point we encounter digits(0, 27, 99) which cannot be decoded, we backtrack.
	// There are paths while decoding which deals with similar subproblems. So we
	// can use a memoization for storing result of overlapping subproblem for reuse.
	// Every time we enter recursion it's for a substring of original string. For
	// any recursion if the first character is 0 then terminate that path by
	// returning 0. This path won't contribute to number of ways. We get result from
	// memo if it exists. Otherwise the number of ways for the given string is
	// determined by making a resursive call to the function with index + 1 for next
	// substring and index + 2 after checking for valid 2-digit decode. The result
	// is stored in memo for key as current index.
	// Time complexity - O(n), memoization helps in pruning the recursion tree and
	// hence decoding for an index only once.
	// Space complexity - O(n) as the memo array takes space equal to length of
	// string and havee an entry for each index value. The recursion stack would
	// also be equal to length of the string.
	public static int numDecodingsMemoized(String s) {
		int[] dp = new int[s.length()];
		return recursive(s, dp, 0);
	}

	private static int recursive(String s, int[] dp, int start) {
		if (start == s.length()) {
			return 1;
		}
		if (s.charAt(start) == '0') {
			return 0;
		}
		if (dp[start] != 0) {
			return dp[start];
		}
		int len = recursive(s, dp, start + 1);
		if (start + 1 < s.length()) {
			int num = Integer.parseInt(s.substring(start, start + 2));
			if (num >= 10 && num <= 26) {
				len += recursive(s, dp, start + 2);
			}
		}
		return dp[start] = len;
	}

	public static int numDecodingsMemoized2(String s) {
		int[] dp = new int[s.length()];
		return recursive(s.toCharArray(), dp, 0);
	}

	private static int recursive(char[] charArr, int[] dp, int start) {
		if (start == charArr.length) {
			return 1;
		}
		if (charArr[start] == '0') {
			return 0;
		}
		if (dp[start] != 0) {
			return dp[start];
		}
		int len = recursive(charArr, dp, start + 1);
		if (start + 1 < charArr.length) {
			StringBuilder sb = new StringBuilder();
			sb.append(charArr[start]);
			sb.append(charArr[start + 1]);
			int num = Integer.valueOf(sb.toString());
//			int num = Integer.valueOf("" + charArr[start] + charArr[start + 1]);
			if (num <= 26) {
				len += recursive(charArr, dp, start + 2);
			}
		}
		return dp[start] = len;
	}

}
