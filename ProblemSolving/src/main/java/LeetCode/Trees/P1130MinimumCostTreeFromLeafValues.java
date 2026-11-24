package LeetCode.Trees;

import java.util.Stack;

/*
 * P1130. Minimum Cost Tree From Leaf Values - Medium
 * 
 * Given an array arr of positive integers, consider all binary trees such that:
 * > Each node has either 0 or 2 children;
 * > The values of arr correspond to the values of each leaf in an in-order traversal of the tree.
 * > The value of each non-leaf node is equal to the product of the 
 * largest leaf value in its left and right subtree, respectively.
 * 
 * Among all possible binary trees considered, return the smallest possible sum of the 
 * values of each non-leaf node. It is guaranteed this sum fits into a 32-bit integer.
 * 
 * A node is a leaf if and only if it has zero children.
 * 
 * Constraints:
 * > 2 <= arr.length <= 40
 * > 1 <= arr[i] <= 15
 * > It is guaranteed that the answer fits into a 32-bit signed integer (i.e., it is less than 2^31).
 * 
 * Approach - Stack, DP
 */
public class P1130MinimumCostTreeFromLeafValues {

	public static void main(String[] args) {
		int[] arr = { 6, 2, 4 };
//		int[] arr = { 4, 11 };

		int minSumNonLeavesStack = mctFromLeafValuesStack(arr);
		System.out.println("Stack: The smallest possible sum of values of non-leaf nodes is: " + minSumNonLeavesStack);

		int minSumNonLeavesTabulation = mctFromLeafValuesTabulation(arr);
		System.out.println(
				"Tabulation: The smallest possible sum of values of non-leaf nodes is: " + minSumNonLeavesTabulation);

		int minSumNonLeavesTabulationGap = mctFromLeafValuesTabulationGap(arr);
		System.out.println("Tabulation Gap: The smallest possible sum of values of non-leaf nodes is: "
				+ minSumNonLeavesTabulationGap);

		int minSumNonLeavesMemoized = mctFromLeafValuesMemoized(arr);
		System.out.println(
				"Memoized: The smallest possible sum of values of non-leaf nodes is: " + minSumNonLeavesMemoized);

	}

	// Monotonic stack - monotonically decreasing: Greedy
	// The stack adds an element in a monotonically decreasing format. We compare
	// the stack's peek with the current array element, if the current array value
	// is greater, we pop the stack's top and multiply it with the smaller value
	// among it's neighbor. The neighbor's are the updated stack's peek (> value)
	// and current element (again > as per constraints). We take the smaller of
	// these 2 values as we want to save the element with max value for later.
	// Every non-leaf node merges 2 adjacent leaves and keeps only the larger one
	// for future merges. The smaller one is "used up" and removed. The cost of
	// removing a leaf x is x * (smaller of its two neighbors). We want to remove
	// small values early, while they sit next to other small values, so they never
	// get multiplied by something large. We keep removing the smallest leaf, paying
	// leaf * min(left neighbor, right neighbor).
	// The stack keeps the array in decreasing order, which makes it easy to spot a
	// leaf that is smaller smaller than both of its neighbors.
	// We populate the stack with sentinel MAX_VALUE. It ensures stack.peek() always
	// has something to look at, even after pops. It acts as "infinitely large" left
	// neighbor for the 1st element. It's never removed.
	// The arr[i] is the right neighbor of whatever is on top of the stack. If the
	// top of the stack is <= arr[i], the decreasing order is broken. That means the
	// top element is <= its right neighbor arr[i], so it's a candidate to be
	// removed (stack.pop()). Take out the element being removed. Call it mid
	// because it sits between 2 neighbors:
	// left neighbor: the new stack.peek() after popping
	// right neighbor: arr[i]
	// The stack is decreasing, so the left neighbor is > mid. And arr[i] > mid
	// (constraints). So mid is the smallest of the 3, which is exactly the
	// condition for removing it. We add the product to the minSum. We merge the mid
	// with its smaller neighbor, creating a non-leaf node with value mid *
	// min(left, right). The larger neighbor survives. The larger neighbor survives.
	// Nothing needs to be pushed back, since the surviving neighbor already
	// represents the max of the merged group. Once the top is > than arr[i], the
	// order is decreasing again, so push arr[i].
	// After the loop, the stack is strictly decreasing from bottom to top: [MAX,
	// big, ..., small]. The leftover elements have no right neighbor larger than
	// them, so merge from top (the smallest) downward. Each pop says popped * the
	// element below it, since the left neighbor is the only neighbor left.
	// size > 2 stops the loop with [MAX, largest] remaining. The largest element is
	// the final survivor and the sentinel is not a real leaf, so neither should be
	// merged.
	// We can write while (stack.peek() <= arr[i]) { as with equal values [4, 4],
	// either one can be removed. Using <= pops the left one, paus 4*min(left, 4),
	// and keps the stack strictly decreasing. This avoids duplicates on the stack
	// keep the final loop simple.
	// Time complexity - O(n), each element is pushed once and popped at most once,
	// so time is O(n). This beats O(n^3) DP, but relies on greedy insight, which DP
	// doesn't need.
	// Space complexity - O(n) for max stack size.
	private static int mctFromLeafValuesStack(int[] arr) {
		int n = arr.length;
		Stack<Integer> stack = new Stack<>();
		// We populate with sentinel MAX_VALUE. It ensures stack.peek() always has
		// something to look at, even after pops.
		stack.push(Integer.MAX_VALUE);

		// Running total of all non-leaf node values.
		int minSum = 0;

		for (int i = 0; i < n; i++) {
			// arr[i] is the right neighbor of whatever is on top of the stack.
			while (stack.peek() <= arr[i]) {
				int mid = stack.pop();
				minSum += mid * Math.min(stack.peek(), arr[i]);
			}
			stack.push(arr[i]);
		}

		while (stack.size() > 2) {
			minSum += stack.pop() * stack.peek();
		}

		return minSum;
	}

	// DP: Tabulation - Bottom Up
	// We need compute dp[i][j] with help of a split tracked via k, dp[i][j] depends
	// on dp[i][k] and dp[k+1][j], where k+1 > i, so we need to iterate via
	// tabulation such that we get these values.
	// We need to make the dependencies available by iterating i from bottom to top
	// or n-1 -> 0, and keep the base case at 0 by iterating j from i+1 onwards.
	// dp[i][k] is in the same row at a smaller column, which is already finished as
	// j counts up.
	// dp[k+1][j] is in a row below i, which is already finished because i counts
	// down.
	// Starting j at i+1 keeps dp[i][i] = 0.
	private static int mctFromLeafValuesTabulation(int[] arr) {
		int n = arr.length;

		// max[i][j] is the largest value in arr[i..j]
		int[][] max = getMaxRange(arr);

		int[][] dp = new int[n][n];

		// We make the dependencies of dp[i][j] available by iterating from bottom to
		// top or n-1 -> 0. We keep the base case at 0.
		for (int i = n - 1; i >= 0; i--) {
			for (int j = i + 1; j < n; j++) { // starts at i+1, so dp[i][i] stays 0.
				dp[i][j] = Integer.MAX_VALUE;

				for (int k = i; k < j; k++) {
					int lm = max[i][k];
					int rm = max[k + 1][j];

					dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + lm * rm);
				}
			}
		}
		return dp[0][n - 1];
	}

	// DP: Tabulation - Gap
	// We use nested loops to fill the dp table by gap size g.
	// Base case: dp[i][i] stays 0 as it's single leaf, so no cost.
	// Filling by gap g guarantees that dp[i][k] and dp[k+1][j] are already computed
	// before dp[i][j] needs them.
	// dp[i][j] = MAX_VAL is only the start value for the min, it's never added to
	// anything so there's no overflow.
	// lm*rm is the root's value.
	// Usage of gap loop: dp[i][j] depends on dp[i][k] and dp[k+1][j]. dp[i][j]
	// depends only on shorter intervals ([i..k] and [k+1..j] are both shorter than
	// [i..j]). The gap loop computes all intervals of length 1, then 2, then 3, and
	// so on. Every dependency is ready when one needs it. This is the usage of
	// gaps.
	// g = j - i is the gap, g = 0 is the diagonal (single leaves), which stays 0
	// automatically, so we start at g = 1. The pair (i,j) slides along the diagonal
	// at distance g.
	// We start with dp[i][j] = MAX_VAL, as it's the right starting value of the
	// min. This is safe because j > i always, so the k loop runs at least once and
	// overwrites it.
	// Here, we precompute once and lookup later.
	private static int mctFromLeafValuesTabulationGap(int[] arr) {
		int n = arr.length;

		// max[i][j] is the largest value in arr[i..j]
		int[][] max = getMaxRange(arr);

		int[][] dp = new int[n][n];

		for (int g = 1; g < n; g++) {
			for (int i = 0, j = g; i < n && j < n; i++, j++) {
				dp[i][j] = Integer.MAX_VALUE; // This will be overwritten below as j > i.

				// Same logic as recursive version. k is the split, dp[i][k] and dp[k+1][j] are
				// left and right, and lm*rm is the root.
				// Here max[][] is precomputed, so no inner loops are needed for the max scans.
				// This makes the work per call O(n) instead of O(n) + the scans.
				for (int k = i; k < j; k++) {
					int lm = max[i][k];
					int rm = max[k + 1][j];
					dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + lm * rm);
				}
			}
		}
		return dp[0][n - 1];
	}

	// To get the max of range [i..j], we fix the starting index i, then extend the
	// range 1 element at a time to the right. maxVal holds the max of everything
	// seen so far, which is the max of arr[i..j].
	// maxVal is reset to Integer.MIN_VALUE for each new i. This is because a new i
	// starts a new range, so the running max must not carry over from the previous
	// start. The key insight is that the max of arr[i..j] is just max(max of
	// arr[i..j-1], arr[j]). The running max reuses the previous answer instead of
	// rescanning the whole range which costs O(1).
	// This precompute removes the repeated scanning and makes the inner loop
	// simpler.
	private static int[][] getMaxRange(int[] arr) {
		int n = arr.length;
		int[][] max = new int[n][n];

		for (int i = 0; i < n; i++) {
			int maxVal = Integer.MIN_VALUE;

			for (int j = i; j < n; j++) {
				maxVal = Math.max(maxVal, arr[j]);

				max[i][j] = maxVal;
			}
		}
		return max;
	}

	// DP: Memoization
	// We're given an in-order traversal of the leaves. This means any subtree
	// always covers a contiguous slice of the array. Hence, the solution (sum of
	// all non-leaf nodes) only needs (l, r) to describe the subproblem.
	// Each non-leaf node's value = max leaf in its left subtree * max leaf in its
	// right subtree. One need to minimize the sum of all non-leaf values. We have:
	// recursiveMemo(l, r) = the minimum sum of non-leaf node values for a tree
	// built from the leaves arr[l..r]
	// We assume that a split happened at index i. The slice arr[l..r] has a root
	// node with a left child and right child. The left and right correctly gets the
	// cheapest cost to build a tree from respective sides. This bottoms out at
	// l==r. These are independent as left subtree does't affect the right one. The
	// only thing connecting them is the root, handled next.
	// Memoization helps here, as different splits uses the same slices repeatedly.
	// Time complexity - O(n^3), without dp, each slice is recomputed from scratch
	// (exponential). With dp, each of the O(n^2) slices is solved once, and each
	// does O(n) work in its loop (plus the max scans), so time is O(n^3).
	public static int mctFromLeafValuesMemoized(int[] arr) {
		int n = arr.length;
		// dp[l][r] caches the answer for the slice arr[l..r] for whole array: 0 to n-1.
		int[][] dp = new int[n][n];

		return recursiveMemo(0, n - 1, arr, dp);
	}

	private static int recursiveMemo(int l, int r, int[] arr, int[][] dp) {
		// Base case: One leaf = tree with a single node and no non-leaf nodes, so the
		// cost is 0.
		if (l == r) {
			return 0;
		}
		// As per constraints, 1 <= arr[i] <= 15
		// Memo check: any slice with 2+ leaves has a cost > 0 (values >= 1), so 0
		// safely means "not computed yet" and >= 1 means we can use cached value.
		if (dp[l][r] > 0) {
			return dp[l][r];
		}

		int ans = Integer.MAX_VALUE;

		// Assuming split happened at index i.
		// The slice arr[l..r] has a root node with a left child and right child. We
		// don't know where the split between them is so we try every split point i.
		// For right we use recursiveMemo(i+1,r) so we must use i < r in for loop.
		for (int i = l; i < r; i++) {
			// left subtree gets arr[l..i]
			// i starts at i so the left side always as >= 1 leaf.
			// left = cheapest cost to build a tree from arr[l..i]. It covers only the
			// internal nodes inside the left subtree.
			int left = recursiveMemo(l, i, arr, dp);
			// right subtree gets arr[i+1..r]
			// i stops at r - 1 so the right side always has >= 1 leaf due to i = r-1 + 1.
			// right = cheapest cost to build a tree from arr[i+1..r]. It covers only the
			// internal nodes inside the right subtree.
			int right = recursiveMemo(i + 1, r, arr, dp);

			// We find the largest leaf on each side. These are needed for the root's value.
			int maxLeft = 0;
			int maxRight = 0;

			for (int j = l; j <= i; j++) {
				maxLeft = Math.max(maxLeft, arr[j]);
			}

			for (int j = i + 1; j <= r; j++) {
				maxRight = Math.max(maxRight, arr[j]);
			}
			// left and right are cost of the left and right subtree, and
			// maxLeft * maxRight is the root of this slice itself which is not counted yet.
			// It's also the cost of joining 2 subtrees.
			// ans = quantity we want to minimize or sum of the values of all non-leaf nodes
			// in the given range [l,r] or whole tree.
			ans = Math.min(ans, left + right + maxLeft * maxRight);
		}

		// Cache the value, so the slice isn't recomputed.
		return dp[l][r] = ans;
	}

}
