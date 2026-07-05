package LeetCode.DynamicProgramming;

/*
 * P3976. Maximum Subarray Sum After Multiplier - Medium
 * 
 * You are given an integer array nums and a positive integer k.
 * 
 * You must choose exactly one subarray of nums and perform exactly one of the following operations:
 * 
 * > Multiply each number in the chosen subarray by k.
 * > Divide each number in the chosen subarray by k.
 * > - When dividing a positive number by k, use the floor value of the division result.
 * > - When dividing a negative number by k, use the ceiling value of the division result.
 * 
 * Return the maximum possible sum of a non-empty subarray in the resulting array.
 * 
 * Note that the subarray chosen for the operation and the subarray chosen for the sum may be different.
 * 
 * Approach - DP: 3-state machine
 */
public class P3976MaximumSubarraySumAfterMultiplier {

	public static void main(String[] args) {
//		int[] nums = { 1, -2, 3, 4, -5 };
//		int k = 2; // 14

//		int[] nums = { -5, -4, -3 };
//		int k = 2; // -1

		int[] nums = { 96117, 80613, 72852, -70020, -66572 };
		int k = 9520; // 2376020640

		long maxSumVars = maxSubarraySumVars(nums, k);
		System.out.println("Variables: The max subarray sum post exactly one of the given operations: " + maxSumVars);

		long maxSumArray = maxSubarraySumArray(nums, k);
		System.out.println("Array: The max subarray sum post exactly one of the given operations: " + maxSumArray);
	}

	private static long maxSubarraySumVars(int[] nums, int k) {
		return Math.max(getSumVars(nums, k, 1), getSumVars(nums, k, 0));
	}

	private static long getSumVars(int[] nums, int k, int op) {
		int n = nums.length;

		long dp1 = nums[0];
		long dp2 = getVal(nums[0], k, op);
		long dp3 = Long.MIN_VALUE / 4;

		long ans = max(dp1, dp2, dp3);

		for (int i = 1; i < n; i++) {
			long val = getVal(nums[i], k, op);

			long dp1Old = dp1;
			long dp2Old = dp2;

			dp1 = Math.max(nums[i], dp1 + nums[i]);
			dp2 = max(val, dp1Old + val, dp2 + val);
			dp3 = max(dp2, dp2Old + nums[i], dp3 + nums[i]);

			ans = Math.max(ans, max(dp1, dp2, dp3));
		}

		return ans;
	}

	// DP
	// At the last line of problem statement it's mentioned that the subarray chosen
	// for the operation and the subarray chosen for the sum may be different. So
	// the subarray we transform and the subarray we sum are chosen independently -
	// they may overlap, be disjoint, or be unrelated. If there are no operation at
	// all, "max sum of a non-empty subarray" would be the textbook Kadane's
	// algorithm.
	// Key observation: The transformed block is a single contiguous range, any
	// sum-window we finally pick, scanned left -> right, passes through at most 3
	// phases: [original ...][transformed ...][original ...]. That is, a window
	// reads some original values, then at most one contiguous run of transformed
	// values, then original values again. Any of the three parts may be empty. We
	// extend Kadane with a 3-state machine that remembers which phase the current
	// window is in.
	// Approach:
	// Step 1: The division rule(truncate toward 0). ATQ, we floor positive results
	// and ceils negative results. Both cases are exactly truncation toward 0(drop
	// the fractional part). For x >= 0 -> x/k(floors) and for x < 0 -> -(|x|/k)
	// (ceils).
	// Step 2: Handle one operation type at a time. The single operation is applied
	// uniformly to the whole chosen block - one cannot multiply part of it and
	// divide another part. So we solve the whole problem twice: 1. Assuming the
	// transformed block(if the window uses it) is multiplied, and 2. Assuming it's
	// divided. We take the larger answer. Inside one run, the per-element transform
	// op(x) is fixed.
	// Step 3: 3 running states(extended Kadane), for every index i(the end of the
	// sum-window) we keep the best window sum for each phase:
	// > dp1[i] = best window ending at i that has used no transformed element yet
	// (plain Kadane on original values).
	// > dp2[i] = best window ending at i that is currently inside the transformed
	// block (element i is transformed).
	// > dp3[i] = best window ending at i whose transformed block has already
	// finished (element i is original again, but the window passed through
	// transformed values earlier).
	// Step 4: Bases cases. Let op(x) apply the current operation to a value.
	// dp1[0] = v[0] - a first original element.
	// dp2[0] = op(v[0]) - the transfored black starts and ends at index 0.
	// dp3[0] = -Infinity - Impossible th have already finished a transformed block
	// at index 0, so we forbid it.
	// Step 5: Transitions. Let curr = nums[i] for original value and val =
	// op(nums[i]) for transformed value, where op() is either * or / depending on
	// the current run.
	// Transition for dp1, dp1[i] = max(curr, dp1[i - 1] + curr)
	// This is standard Kadane transition. > curr starts a new subarray at index i.
	// > dp1[i-1] + curr extends the previous subarray that has not yet entered the
	// transformed block. Since, dp1 represents the phase before any transformation,
	// these are the only 2 possibilities.
	// Transition for dp2, dp2[i] = max(val, dp1[i - 1] + val, dp2[i - 1] + val))
	// Here, current element must be transformed, there are exactly 3 ways to reach:
	// 1. Start both the subarray and the transformed block here: val, the subarray
	// consists of only the current transformed element.
	// 2. Start the transformed block here: dp1[i-1] + val, until index i-1, every
	// element in the subarray was original. At index i, we decide to begin the
	// transformed segment. This is the only transition from the "original" phase
	// (dp1) into the "transformed" phase (dp2).
	// 3. Continue the transformed block: dp2[i-1] + val, the previous element was
	// already transformed, so we simply extend the current transformed segment by 1
	// more element. Since, the transformed block must be contiguous, these are the
	// only possible transitions into dp2.
	// Transition for dp3, dp3[i] = max(dp2[i], dp2[i-1] + curr, dp3[i-1] + curr)
	// Here, the current element is not transformed, meaning the transformed block
	// has already ended. Again, there are exactly 3 possibilities.
	// 1. Leave the transformed block just before the current element dp2[i-1]+curr.
	// At index i - 1, we were still inside the transformed block. By taking the
	// current element as its original value, we finish the transformed segment and
	// start the final original suffix. This is the transition from dp2 to dp3.
	// 2. Continue the original suffix, dp3[i-1] + curr. The transformed block had
	// already ended earlier, so we simply append another original element while
	// staying in dp3.
	// 3. End the transformed block exactly at index i, dp2[i]. It acts as a start
	// for dp3 and is a subtle but important transition. Suppose the transformed
	// block ends at the current index itself. Then, the subarray has no original
	// suffix after the transformed block. Such subarrays are perfectly valid and
	// should also be considered. Without this transition, dp3 would always require
	// at least one original element after the transformed segment, causing us to
	// miss optimal answers where the subarray ends immediately after the
	// transformed block. Including dp2[i] allows the trailing original segment to
	// be empty while still treating the transformed block as finished.
	// Step 6: Collect the answer. The best window for one operation is the max of
	// dp1[i], dp2[i], dp3[i] over all i. The final answer is the larger of the 2
	// operation runs: ans = max(f(flag = 0), f(flag = 1))
	// Step 7: The mandatory operation subtlety. The operation is required, yet dp1
	// describes a window that ignores the transformation. It's still legal and
	// safe. For any value x and any k >= 1, the better of x*k and div(x, k) is
	// always >= x: > If x >= 0: x*k >= x (k >= 1). > If x < 0: div(x, k) >= x
	// (dividing pulls a negative number -> 0). So, we can always satisfy the forced
	// operation (for example, apply the locally-better operation to a single
	// element) without ever lowering the achievable sum. Therefore allowing an
	// effectively-untouched window (dp1) never reports something unattainable, even
	// in the extreme case n = 1.
	// This approach is Kadane invariant as we start fresh or extend previous best.
	// Time complexity - O(n), where n = nums's length. We do O(n) operation * 2
	// operations of multiply/divide.
	// Space complexity - O(n), for 3 dp arrays.
	public static long maxSubarraySumArray(int[] nums, int k) {
		return Math.max(getSum(nums, k, 1), getSum(nums, k, 0));
	}

	private static long getSum(int[] nums, int k, int op) {
		int n = nums.length;

		long[] dp1 = new long[n];
		long[] dp2 = new long[n];
		long[] dp3 = new long[n];

		dp1[0] = nums[0];
		dp2[0] = getVal(nums[0], k, op);
		dp3[0] = Long.MIN_VALUE / 4;

		long ans = Math.max(dp1[0], Math.max(dp2[0], dp3[0]));

		for (int i = 1; i < n; i++) {
			long val = getVal(nums[i], k, op);

			dp1[i] = Math.max(nums[i], dp1[i - 1] + nums[i]);
			dp2[i] = max(val, dp1[i - 1] + val, dp2[i - 1] + val);
			dp3[i] = max(dp2[i], dp3[i - 1] + nums[i], dp2[i - 1] + nums[i]);

			ans = Math.max(ans, max(dp1[i], dp2[i], dp3[i]));

//			dp1[i] = Math.max(nums[i], dp1[i - 1] + nums[i]);
//			dp2[i] = Math.max(val, Math.max(dp1[i - 1] + val, dp2[i - 1] + val));
//			dp3[i] = Math.max(dp2[i], Math.max(dp3[i - 1] + nums[i], dp2[i - 1] + nums[i]));
//
//			ans = Math.max(ans, Math.max(dp1[i], Math.max(dp2[i], dp3[i])));
		}

		return ans;
	}

	public static long max(long a, long b, long c) {
		return Math.max(a, Math.max(b, c));
	}

	private static long getVal(int num, int k, int op) {
		if (op == 0) {
			return num / k;
		}
		return (long) num * k;
	}

}
