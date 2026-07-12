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
	// One may think of solving it via DP, with the state (index, lastBit) or
	// (index, prev) but it won't solve the problem accurately in a simple manner
	// as the problem doesn't have the property of optimal substructure.
	// For the state dp[index][changed] it's insufficient. For example 11 -> 00,
	// affects 2 adjacent positions. So when one is at index, whether one can make
	// the 2nd operation depends on the value at index + 1, which may have been
	// modified earlier. This state = changed only remembers whether the previous
	// character was "changed", which loses too much information. Mutating s1 is
	// also incorrect for memoization with (index, changed). As 2 calls may reach
	// dfs(5, 0) with completely different contents of s1Arr. Example - 101110 and
	// 111010 would share the same DP state even though the future answers are
	// different. So memoization becomes invalid.
	// For the state dp[index][carry], where we include the current bit carried to
	// this position. This is still insufficient. Here, carry = whether position has
	// already been flipped by an operation started at i - 1. Since, the only
	// operation involving 2 positions is adjacent, information only needs to
	// propagate 1 step forward. Another equivalent state is dp[index][currentBit],
	// meaning the effective value of s1[i] after all operations from the left. That
	// gives only 2 states per index, i.e. O(n) DP.
	// The key observation is that the only operation that affects the future is 11
	// -> 00, because it changes the next position as well. This dp is unlike
	// interval DPs, as it has a nice left-to-right DP. DP state dfs(i, prev), where
	// prev = whether position i has already been cosumed by a 11 -> 00 operation
	// started at i - 1. So, the effective bit at i is current == prev ? 0: s1[i],
	// because if (i-1, i) was removed together, then position i has already become
	// 0. The DP returns the min cost from i onward. Only 2 states per index.
	// Transition: suppose curr == prev ? 0 : s1[i], target = s2[i]. Case 1: curr ==
	// target, do nothing. dfs(i + 1, false), Case 2: curr = 0, target = 1, only 1
	// possible operation 0 -> 1. Cost 1 + dfs(i + 1, false). Case 3: curr = 1,
	// target = 0, we must remove this 1, only possibility, 11 -> 00. So we need i +
	// 1 exists and effective bit at i+1 == 1. There are 2 possibilities: > original
	// next bit is 1. > original next bit is 0, but first convert it to 1. next
	// already 1. 11 -> 00. cost: 1 + dfs(i + 1, true) because index i+1 has already
	// become 0. next is 0, Need 01 -> 11 -> 00, total 2 + dfs(i+1, true). Base case
	// i == n, we return 0. If we try removing the last 1 at i == n - 1, there is no
	// partner -> impossible. Complexity, state = 2*n, transitions O(1), total O(n).
	// This DP is incorrect for s1 = 111, s2 = 000, Optimal 111 -> remove 1st pair
	// 001 + add 2nd last -> 011 -> remove last pair -> 000. cost = 3. The simple
	// state (i, prev) forces a left-to-right decision and cannot revisit earlier
	// choices like temporarily creating a 1 to pair with another 1. In other words,
	// the operation 0 -> 1 can be used strategically, not just to match the target
	// at the current position. That means the optimal decision depends on info
	// beyond the current index. Hence, this DP looks natural but, it's insufficent.
	// Conclusion 0> There is no simple O(n) memoization DP with only a
	// constant-sized state. The reason is that the operation 0->1 can create new
	// opportunities for future 11->00 operations, introducing dependencies that
	// extend beyond adjacent positions. So we use a greedy invariant rather than a
	// local DP. We may try to derive DP by expanding the state via profile /
	// state-maching DP. But this is also not possible even with a polynomial-state
	// DP over the index. The reason is: Suppose we're processing left to right. At
	// index i, if we have i -> 0. We have 2 choices: 1. Pair it with i+1, if i+1 is
	// already 1. 2. First convert i+1 to 1, then pair. So far, this looks local.
	// The problem is that the converting i+1 to 1 may itself need to be paired with
	// i+2 later instead of with i. Example 1111 -> 0000. There are multiple optimal
	// sequences. Another example: 10111 -> 00000. One may choose to 10111 -> 11111
	// -> 00111 -> 00001 -> 00011 -> 00000. or 10111 -> 10011... The best decision
	// at position 1 depends on operations several positions later.
	// Expanding the state doesn't helps: Normally we'd add extra info. For example:
	// dp[i][state] where state stores the next few bits. But the provlem is suppose
	// we're middle of processing after ...101011001..., the remaining suffix is not
	// determined only by the next few characters. It depends on: > which zeros were
	// intentionally created. > which ones were added. > which future pair removals
	// are still planned. The possibilities are exponentially many. Another
	// viewpoint: one can think of every operation as 00 01 10 11 being transformed
	// repeatedly. The operation 0 -> 1 creates resources. The operation 11 -> 00
	// consumes two resouces. A created 1 can be consumed immediately, or several
	// stes later interacting with other operations. That creates long-range
	// dependencies. The state which is sufficient is dp[position][current_string]
	// where current_string is the remaining suffix. State count: O(2^n) is clearly
	// infeasible.
	// Profile DP: Sometimes keeping the next 2 or 3 bits works. It doesn't here. As
	// the 1 one creates at index may survive arbitrarily far. Example: 100000001.
	// One may propagate ca created 1 through many operations before finally
	// eliminating it. So the "active information" is not bounded by a constant
	// number of positions.
	// Conclusion: This problem has a greedy / exchange property, not an optimal
	// substructure property with a compact state. We cannot have dp[i][carry],
	// dp[i][mask], dp[i][last] as it'll fail on some inputs because those states
	// cannot encod all future possibilities. The only correct DP formulations are
	// exponential (e.g., over the entire remaining string or bitmask). Hence we
	// need a greedy invariant.
	//
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
	// Time complexity - O(n)
	// Space complexity - O(1)
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
