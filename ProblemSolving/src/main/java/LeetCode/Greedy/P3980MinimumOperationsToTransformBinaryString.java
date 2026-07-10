package LeetCode.Greedy;

/*
 * P3980. Minimum Operations to Transform Binary String - Medium
 * 
 * You are given two binary strings s1 and s2 of the same length n.
 * 
 * You can perform the following operations on s1 any number of times, in any order:
 * > Choose an index i such that s1[i] == '0', and change it to '1'.
 * > Choose an index i such that 0 <= i < n - 1, and both 
 * s1[i] and s1[i + 1] are '1'. Change both characters to '0'.
 * 
 * Return the minimum number of operations required to make s1 equal to s2. If it is impossible, return -1.
 * 
 * Approach - Greedy
 */
public class P3980MinimumOperationsToTransformBinaryString {

	public static void main(String[] args) {
//		String s1 = "11";
//		String s2 = "00";

//		String s1 = "10";
//		String s2 = "01";

		String s1 = "01";
		String s2 = "10";

		int minOperations = minOperations(s1, s2);
		System.out.println("The minimum number of operations required to make s1 equals s2: " + minOperations);
	}

	// Greedy
	// 0 -> 1 (1)
	// 11 -> 00 (1)
	// 1 -> 0 (Not possible) -1
	// 10 -> 00 | 11 -> 00 (2)
	// 10 -> 11 (1)
	// 10 -> 01 | 11 -> 00 -> 01(3)
	// 00 -> 11 (2)
	// 01 -> 00 | 11 -> 00 (2)
	// 01 -> 10 | 11 -> 00 -> 10(3)
	// 01 -> 11 (1)
	// We use Greedy approach from left to right. At each index i:
	// > If the character s1[i] matches s2[i], no action required.
	// > If s1[i] = 0 and s2[i] = 1: we flip s1[i] to 1, takes 1 operation.
	// > If s1[i] = 1 amd s2[i] = 0: We need to change s[i] to 0 using adjacent
	// characters.
	// > If i < n - 1
	// > - If s1[i+1] == 1, then convert both indices to 0. Here we add 1 operation
	// to total changes and mark s[i+1] = 0.
	// > - Else If s1[i+1] == 0, we convert s1[i+1] to 1 then convert both s1[i] and
	// s1[i+1] to 0. We add 2 operations.
	// > If i == n - 1, there are 2 cases. For both, we need 2 operations
	// 1. We change s1[n-2] to 0 if it's 1 by flipping both s1[n-2] and s1[n-1] then
	// again changing it back to 1.
	// 2. We change s1[n-2] to 1 if it's 0 then flipping both s1[n-2] and s1[n-1].
	public static int minOperations(String s1, String s2) {
		char[] s1Arr = s1.toCharArray();
		char[] s2Arr = s2.toCharArray();

		int n = s1Arr.length;

		if (n == 1) {
			if (s1Arr[0] == '1' && s2Arr[0] == '0') {
				return -1;
			}
		}

		int ans = 0;

		for (int i = 0; i < n; i++) {
			if (s1Arr[i] == s2Arr[i]) {
				continue;
			} else if (s1Arr[i] == '0') {
				ans += 1;
			} else if (i < n - 1) {
				if (s1Arr[i + 1] == '1') {// 1|0, 11 | 0_ -> 00 -> 0_
					ans += 1;
					s1Arr[i + 1] = '0';
				} else {// 1|0, 10|0_ -> 11|0_ -> 00 | 0_
					ans += 2;
				}
			} else { // 1|0 -> _1|_0 -> 11|10 or 01|00
				ans += 2;
			}
		}
		return ans;
	}
}
