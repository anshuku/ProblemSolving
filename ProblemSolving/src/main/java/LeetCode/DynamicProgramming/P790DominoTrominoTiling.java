package LeetCode.DynamicProgramming;

/* 
 * P790. Domino and Tromino Tiling - Medium
 * 
 * You have two types of tiles: a 2 x 1 domino shape and a tromino shape. You may rotate these shapes.
 * ## #   | ##
 *    #   | #
 * Domino | Tromino
 * 
 * Given an integer n, return the number of ways to tile an 2 x n board. 
 * Since the answer may be very large, return it modulo 109 + 7.
 * 
 * In a tiling, every square must be covered by a tile. Two tilings are 
 * different if and only if there are two 4-directionally adjacent cells on the 
 * board such that exactly one of the tilings has both squares occupied by a tile.
 * 
 * Approach - 1D Dynamic Programming
 */
public class P790DominoTrominoTiling {

//	public static long mod = 1000000007l;
	public static long mod = (long) 1e9 + 7;

	public static void main(String[] args) {

//		int n = 1;
//		int n = 2;
//		int n = 3;
//		int n = 4;
//		int n = 5;
//		int n = 20;
		int n = 30;

		int ways3Vars = numTilings3Vars(n);
		System.out.println("3Vars DP: The number of ways for tiling: " + ways3Vars);

		int ways3VarsFP = numTilings3VarsFP(n);
		System.out.println("3Vars DP FP: The number of ways for tiling: " + ways3VarsFP);

		int ways1D = numTilings1D(n);
		System.out.println("1D DP: The number of ways for tiling: " + ways1D);

		int waysMatrixBinaryExp = numTilingsMatrixBinaryExponentiation(n);
		System.out.println("Matrix Binary exponentiation: The number of ways for tiling: " + waysMatrixBinaryExp);

		int waysMatrixBinaryExpFP = numTilingsMatrixBinaryExponentiationFP(n);
		System.out.println("Matrix Binary exponentiation FP: The number of ways for tiling: " + waysMatrixBinaryExpFP);

		int ways1DAlt = numTilings1DAlt(n);
		System.out.println("1D DP Alt: The number of ways for tiling: " + ways1DAlt);

		int ways2D = numTilings2D(n);
		System.out.println("2D DP: The number of ways for tiling: " + ways2D);

		int waysMemoized = numTilingsMemoized(n);
		System.out.println("Memoized: The number of ways for tiling: " + waysMemoized);

		int waysMemoizedRev = numTilingsMemoizedRev(n);
		System.out.println("Memoized Reverse: The number of ways for tiling: " + waysMemoizedRev);

		int waysMatrixExpFP = numTilingsMatrixExponentiationFP(n);
		System.out.println("Matrix exponentiation FP: The number of ways for tiling: " + waysMatrixExpFP);

		int waysRecursive = numTilingsRecursive(n);
		System.out.println("Recursive: The number of ways for tiling: " + waysRecursive);

	}

	private static int numTilings3Vars(int n) {
		if (n < 3) {
			return n;
		}
		long curr = 5;
		long previous = 2;
		long beforePrevious = 1;
		for (int i = 4; i <= n; i++) {
			long temp = curr;
			curr = (2 * curr + beforePrevious) % mod;
			beforePrevious = previous;
			previous = temp;
		}
		return (int) curr;
	}

	// DP - 2 Variables
	// We only need f(n-1), f(n-2) and p(n-1) to store the values. This can be done
	// with the help of 3 variables.
	// Time complexity - O(n)
	// Space complexity - O(1)
	private static int numTilings3VarsFP(int n) {
		if (n < 3) {
			return n;
		}
		long fCurr = 2;
		long fPrev = 1;
		long pCurr = 1;

		for (int i = 3; i <= n; i++) {
			long temp = fCurr;
			fCurr = (fCurr + fPrev + 2 * pCurr) % mod;
			pCurr = (pCurr + fPrev) % mod;
			fPrev = temp;
		}
		return (int) fCurr;
	}

	// DP - Bottom Up Approach
	// Recurrence relations for 1D DP, derived empirically
	// 1 config of 2 horizontal domino, 1 config of 1 vertical domino
	// 2 configs of 2 trominos
	// dp[n] = dp[n-1] + dp[n-2] + 2*(0 -> n-3)∑dp[i]
	// Expand 2*(0 -> n-3)∑dp[i]
	// dp[n] = dp[n-1] + dp[n-2] + 2*dp[n-3] + 2*(0 -> n-4)∑dp[i]
	// dp[n] = dp[n-1] + dp[n-3] + (dp[n-2] + dp[n-3] + 2*(0 -> n-4)∑dp[i])
	// dp[n-1] = dp[n-2] + dp[n-3] + 2*(0 -> n-4)∑dp[i]
	// dp[n] = dp[n-1] + dp[n-3] + dp[n-1]
	// dp[n] = 2*dp[n-1] + dp[n-3]
	// Another recurrence relation:
	// f[i] = f[i-1] + f[i-2] + 2*p[i-1] ...1
	// p[i] = p[i-1] + f[i-2] ...2
	// f[i-1] = f[i-2] + f[i-3] + 2*p[i-2]...3
	// p[i] - p[i-1] = f[i-2] ...4
	// p[i-1] - p[i-2] = f[i-3] ...5
	// 1 - 3
	// f[i] - f[i-1] = f[i-1] - f[i-3] + 2(p[i-1] - p[i-2])
	// f[i] = 2*f[i-1] - f[i-3] + 2*f[i-3]
	// f[i] = 2*f[i-1] + f[i-3]
	private static int numTilings1D(int n) {
		long[] dp = new long[n + 1];

		if (n < 3) {
			return n;
		}
		dp[0] = 1;
		dp[1] = 1;
		dp[2] = 2;
		dp[3] = 5;

		for (int i = 4; i <= n; i++) {
			dp[i] = (2 * dp[i - 1] + dp[i - 3]) % mod;
		}
		return (int) dp[n];
	}

	// DP Tabulation - Bottom Up
	// To remove additonal call stack space we can use iterative approach.
	// Time complexity - O(n), to fill dp arrays
	// Space complexity - O(n), for 2 dp arrays
	private static int numTilings1DAlt(int n) {
		long[] dpF = new long[n + 1];
		long[] dpP = new long[n + 1];
		dpF[1] = 1;
		dpF[2] = 2;
		dpP[2] = 1; // to partially cover a board of width 2, there's only 1 way using L-shaped
					// Tromino, leave upper right corner uncovered.
		for (int i = 3; i <= n; i++) {
			// 1D, 2D, 1T
			dpF[i] = (dpF[i - 1] + dpF[i - 2] + 2 * dpP[i - 1]) % mod;
			// 1D, 1T
			dpP[i] = (dpP[i - 1] + dpF[i - 2]) % mod;
		}
		return (int) dpF[n];
	}

	// Matrix Binary Exponentiation
	// Binary/fast exponentiation helps to find A^k quickly.
	// This optimization is used to find Pow(x,n)
	// M^n = M^(n/2)*M^(n/2) = (M^(n/4)*M^(n/4))*(M^(n/4)*M^(n/4))
	// We can obtain M^n by recursively dividing the power by 2 and multiplying the
	// results until we reach M^1 or M. This is smooth for n = even
	// For odd n, n-1 is even. Perform this operation on n-1 to get Pow(x, n-1) then
	// multiply it with the base M. This creates a binary tree which is a recursive
	// data structure. Using recursion reduces time to height of tree or O(logn)
	// 13 = 1101
	// result = A
	// A^2
	// 110 / 6
	// result = A
	// A^4
	// 11 / 3
	// result = A^5 = A*A^4
	// A^8
	// 1
	// result = A^13 = A^8*A^4*A
	// A^16
	// Time complexity - O(logn), we did matrix multiplication of 3*3 square matrix
	// logn times. We do 1 calculation per level of recursion tree.
	// Space complexity - O(1) or O(logn) as we use stack space for recursion.
	private static int numTilingsMatrixBinaryExponentiationFP(int n) {
		long[][] matrix = { { 1, 1, 2 }, { 1, 0, 0 }, { 0, 1, 1 } };

		matrix = getProductMatrixBinaryExponentiation(matrix, n - 2);

		long[][] last = { { 2 }, { 1 }, { 1 } };
		matrix = getMatrixProduct(matrix, last, 3, 3, 3, 1);
		return (int) matrix[0][0];
	}

	private static long[][] getProductMatrixBinaryExponentiation(long[][] matrix, int times) {
		// Identity matrix is used since A*I = A, I is 1 of matrix multiplication
		// We start with result = I, because if times = 0 -> A^0 = I
		long[][] result = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
		// We repeatedly perform binary exponentiation
		// Breaking k into binary digits 13 -> 1101
		// Use squares of A
		while (times > 0) {
			// If last bit is 1
			if ((times & 1) == 1) {
				// We multiply the current result with current A
				result = getMatrixProduct(matrix, result, 3, 3, 3, 3);
			}
			// A*A so A -> A^2 -> A^4 -> A^8 -> A^16
			matrix = getMatrixProduct(matrix, matrix, 3, 3, 3, 3);
			// rightshift times by 1 or drop last bit or times = times/2
			// 1101 -> 110 -> 11 -> 1 -> 0
			times >>= 1;
		}
		return result;
	}

	// Matrix exponentiation
	// Recurrence relation:
	// dp[i] = 2*dp[i-1] + 0*dp[i-2] + 1*dp[i-3]
	// dp[i-1] = 1*dp[i-1] + 0*dp[i-2] + 0*dp[i-3]
	// dp[i-2] = 0*dp[i-1] + 1*dp[i-2] + 0*dp[i-3]
	// State vector S(n) = T*S(n-1)
	// S(n) = [f(n) f(n-1) f(n-2)]
	// Base vector S(3) = [f(3) f(2) f(1)] = [5 2 1]
	// f( k ) = [2 0 1] * f(k-1)
	// f(k-1) = [1 0 0] * f(k-2)
	// f(k-2) = [0 1 0] * f(k-3)
	// S(n) = T^(n-3)*S(3)
	// [2 0 1] * 5 = 11
	// [1 0 0] * 2 = 5
	// [0 1 0] * 1 = 2
	// f(4) = 11 so it's correct
	// T is the canonical and minimal form and needs to be multiplied n-3 times.
	private static int numTilingsMatrixBinaryExponentiation(int n) {
		long[][] matrix = { { 2, 0, 1 }, { 1, 0, 0 }, { 0, 1, 0 } };
		matrix = getProductMatrixBinaryExponentiation(matrix, n - 3); // multiply n-3 times

		long[][] last = { { 5 }, { 2 }, { 1 } }; // S(3)
		matrix = getMatrixProduct(matrix, last, 3, 3, 3, 1);
		return (int) matrix[0][0];
	}

	// 0 - 1
	// 1 - 1
	// 2 - 2
	// 3 - 5
	// 4 - 11
	// 5 - 24
	// 6 - 53

	// 1 2 5 11 24 53
	// 1 3 6 13 29
	// 2 3 7 16
	// 1 4 9
	// 1 1
	// 0
	// DP Tabulation - Bottom Up Approach
	// dp[n][0] means total number of tilings till column n with no empty space at
	// nth index.
	public static int numTilings2D(int n) {
		if (n < 3) {
			return n;
		}
		// dp[][3] holds 3 states, 0 when the last column is entirely filled
		// 1 when last column's top is only filled
		// 2 when last column bottom is only filled
		// 1 and 2 are overflows from current column i
		// 1 and 2 fills the top and bottom on (i+1)th column
		long[][] dp = new long[n + 1][3];

		// For 1 Vertical domino
		dp[0][0] = 1;
		// For 2 horizontal dominos as 2 vertical is not taken as previous value for
		// 1 vertical will be reused.
		dp[1][0] = 1;
		// 1 Tromino with top filled
		dp[1][1] = 1;
		// 1 Tromino with bottom filled
		dp[1][2] = 1;

		// There are 8 cases in total for n >=2
		for (int i = 2; i <= n; i++) {
			// in this case 1 vertical d, 2 horizontal d, 1 tromino, 1 tromino
			dp[i][0] = (dp[i - 1][0] + dp[i - 2][0] + dp[i - 2][1] + dp[i - 2][2]) % mod;

			// 1 horizontal d at top, post tromino, 1 tromino
			dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % mod;

			// 1 horizontal d at bottom, post tromino, 1 tromino
			dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % mod;
		}
		return (int) dp[n][0];
	}

	// DP Memoized - Top Down Approach: dp table filled from n -> 0
	// Here we take use of the current column and space as state.
	// We take space for last index. Empty space at last index is given by 1.
	// Operations happens from i=0 to n-1. dp[n][0] means at last column n, with no
	// space at n-1.
	// Time complexity - O(n) with cache, it's 3*n for 3 states and n columns
	// Space complexity - O(n) needed for recursion stack space and dp array.
	private static int numTilingsMemoized(int n) {
		long[][] dp = new long[n + 1][2];
		// prepopulation not needed
//		for(int i = 0; i <= n; i++) {
//			dp[i][0] = -1;
//			dp[i][1] = -1;
//		}
		// may prepopulate the dp array with -1

		// 1 for space and 0 for no space
		// dp[0][0] has answer
		recursiveMemoized(n, 0, 0, dp);
		return (int) dp[0][0];
	}

	private static long recursiveMemoized(int n, int i, int space, long[][] dp) {
		if (i > n) {
			return 0;
		}
		if (i == n) {
			if (space == 1) {
				return 0;
			}
			return 1;
		}
		if (dp[i][space] != 0) {
			return dp[i][space];
		}
		long count = 0;
		if (space == 0) {
			count += recursiveMemoized(n, i + 1, 0, dp);
			count += recursiveMemoized(n, i + 2, 0, dp);
			count += 2 * recursiveMemoized(n, i + 2, 1, dp);
		} else {
			count += recursiveMemoized(n, i + 1, 1, dp);
			count += recursiveMemoized(n, i + 1, 0, dp);
		}
		dp[i][space] = count % mod;
		return count % mod;
	}

	// DP Memoization - Top down
	// Here we take use of the current column and space as state.
	// The previous full and partial configurations can be used for future states.
	// Fully covered board has all space covered with domino and tromino.
	// Partially covered board is one where the upper right corner is uncovered.
	// This is true where lower right corner is empty as well. But we don't need to
	// keep track of which corner is empty due to symmetry.
	// f(k): number of ways to fully cover a board of width k.
	// p(k): number of ways to partially cover a board of width k.
	// f(k) = f(k-1) + f(k-2) + 2*p(k-1)
	// p(k) = f(k-2) + p(k-1) and not 2*RHS as p(k) is unique.
	// The transition functions depends on previous states means we can use DP.
	// The 2 transition functions are used as recurrence relations.
	// Time complexity - O(n), from top to bottom there will be n non-memoized
	// recursive calls to f and p, where each call requires constant time O(2n).
	// There will be 2N memoized calls to f and N memoized calls to p, where each
	// memoized calls also required constant time. Thus, O(3N) time is required for
	// the memoized calls. Thus O(2n+3n) = O(n).
	// Space complexity - each recursion call takes O(n) space, also each hashmap
	// takes O(n) space. Hence, overall O(n) space.
	private static int numTilingsMemoizedRev(int n) {
		long[] dpFull = new long[n + 1];
		dpFull[0] = 1;
		dpFull[1] = 1;
		dpFull[2] = 2;
		long[] dpPartial = new long[n + 1];
		dpPartial[2] = 1;

		recursive(n, n, dpFull, dpPartial);
		return (int) dpFull[n];
	}

	private static void recursive(int n, int i, long[] dpFull, long[] dpPartial) {
		dpFull[i] = (recursiveFull(n, i - 1, dpFull, dpPartial) + recursiveFull(n, i - 2, dpFull, dpPartial)
				+ 2 * recursivePartial(n, i - 1, dpFull, dpPartial)) % mod;
		dpPartial[i] = (recursivePartial(n, i - 1, dpFull, dpPartial) + recursiveFull(n, i - 2, dpFull, dpPartial))
				% mod;
	}

	private static long recursiveFull(int n, int i, long[] dpFull, long[] dpPartial) {
		if (dpFull[i] != 0) {
			return dpFull[i];
		}
		long val = recursiveFull(n, i - 1, dpFull, dpPartial) + recursiveFull(n, i - 2, dpFull, dpPartial)
				+ 2 * recursivePartial(n, i - 1, dpFull, dpPartial);
		return dpFull[i] = val % mod;
	}

	private static long recursivePartial(int n, int i, long[] dpFull, long[] dpPartial) {
		if (i < 2) {
			return 0;
		}
		if (dpPartial[i] != 0) {
			return dpPartial[i];
		}
		long val = recursivePartial(n, i - 1, dpFull, dpPartial) + recursiveFull(n, i - 2, dpFull, dpPartial);
		return dpPartial[i] = val % mod;
	}

	// Matrix exponentiation
	// Used when the recurrence relation for the DPs are defined
	// We use 3 matrices in an equations where the values in LHS are the base case
	// functions for i, 3rd matrix has base case values for functions with k - 2.
	// The 2nd matrix are the coefficient value for functions,
	// f(k): number of ways to fully cover a board of width k.
	// p(k): number of ways to partially cover a board of width k.
	// Recurrence relations:
	// f(k) = f(k-1) + f(k-2) + 2*p(k-1)
	// p(k) = f(k-2) + p(k-1) and not 2*RHS as p(k) is unique.
	// The below transition functions can be used in matrix multiplication:
	// f(k) = 1*f(k-1) + 1*f(k-2) + 2*p(k-1) Transition functions
	// f(k-1) = 1*f(k-1) + 0*f(k-2) + 0*p(k-1)
	// p(k) = 0*f(k-1) + 1*f(k-2) + 1*p(k-1) Transition functions
	// Here we reach f(k) till base case f(2) in k-2 matrix exponentiation.
	// f( k ) = [ 1 1 2] * f(k-1)
	// f(k-1) = [ 1 0 0] * f(k-2)
	// p( k ) = [ 0 1 1] * p(k-1)
	// Also,
	// f(k-1) = [ 1 1 2] * f(k-2)
	// f(k-2) = [ 1 0 0] * f(k-3)
	// p(k-1) = [ 0 1 1] * p(k-2)
	// 1st matrix is state vector and last matrix is base vector
	// Here the 2nd transition matrix is multiplied k-2 times to reach base case.
	// f( k ) = [ 1 1 2] * f( 2 ) / 2
	// f(k-1) = [ 1 0 0] * f( 1 ) / 1
	// p( k ) = [ 0 1 1] * p( 2 ) / 1
	// The transition functions are used to create matrix representation.
	// The decomposition of transition functions helps to find f(k).
	// This exponentiation technique can be used when we've m*m square matrix.
	// As, square matrix doesn't change its dimension post multiplication.
	// Here the dimension of third matrix or base vector should be m*1.
	// Matrix multiplication technique:
	// for M1[row1][col1] * M2[row2][col2] gives final matrix M2[row1][col2]
	// Matrix multiplication is possible if col1 = row2
	// r1c1 * r2c2 gives r1c2 as final matrix dimensions. We use c1/r2 for
	// multiplication purpose.
	// Time complexity - O(n), we did matrix multiplication of 3*3 square matrix n-2
	// times and 1 more matrix multiplication.
	// Space complexity - O(1) as we used constant 3*3 space for matrices.
	private static int numTilingsMatrixExponentiationFP(int n) {
		long[][] matrix1 = { { 1, 1, 2 }, { 1, 0, 0 }, { 0, 1, 1 } };
		long[][] matrix2 = { { 1, 1, 2 }, { 1, 0, 0 }, { 0, 1, 1 } };

		for (int i = 1; i < n - 2; i++) {
			matrix1 = getMatrixProduct(matrix1, matrix2, 3, 3, 3, 3);
		}

		long[][] last = { { 2 }, { 1 }, { 1 } };
		long[][] matrix3 = getMatrixProduct(matrix1, last, 3, 3, 3, 1);
		return (int) matrix3[0][0];

//		long[] last = { 2, 1, 1 };
//		long ans = (matrix1[0][0] * last[0] + matrix1[0][1] * last[1] + matrix1[0][2] * last[2]) % mod;
//		return (int) ans;

	}

	private static long[][] getMatrixProduct(long[][] matrix1, long[][] matrix2, int r1, int c1, int r2, int c2) {
		long[][] ans = new long[r1][c2];
		for (int i = 0; i < r1; i++) {
			for (int j = 0; j < c2; j++) {
				long currSum = 0;
				for (int k = 0; k < r2; k++) {
					currSum = (currSum + matrix1[i][k] * matrix2[k][j]) % mod;
				}
				ans[i][j] = currSum;
			}
		}
		return ans;
	}

	// DP Recursive - Top Down Approach
	// Here, we take space for previous index from current which is given by 1.
	// Operations happens from i=0 to n-1. dp[n][0] means at last column n, with no
	// space at n-1.
	// Time complexity - O(3^n) for n columns. max 3 recursive calls are branched
	// and there are n such states.
	// Space complexity - O(n) for recursion stack space
	private static int numTilingsRecursive(int n) {
		return recursive(0, false, n);
	}

	private static int recursive(int i, boolean space, int n) {
		if (i > n) {
			return 0;
		}
		if (i == n) {
			if (space) {
				return 0;
			}
			return 1;
		}
		long count = 0;
		if (!space) {
			// 1 vertical domino
			count += recursive(i + 1, false, n);
			// 2 horizontal dominos as 2 vertical is not taken as previous recursion for 1
			// vertical will be reused which will lead to double counting.
			count += recursive(i + 2, false, n);
			// 2 tromino configs
			// For each partially covered tiling there is a horizontally symmetrical tiling.
			count += 2 * recursive(i + 2, true, n);
		} else {
			// horizontal domino
			count += recursive(i + 1, true, n);
			// 1 tromino config which can fill the gap
			count += recursive(i + 1, false, n);
		}
		return (int) (count % mod);
	}

}
