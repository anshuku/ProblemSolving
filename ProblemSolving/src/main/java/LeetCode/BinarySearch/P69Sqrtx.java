package LeetCode.BinarySearch;

/*
 * P69. Sqrt(x) - Easy
 * 
 * Given a non-negative integer x, return the square root of x rounded down to 
 * the nearest integer. The returned integer should be non-negative as well.
 * 
 * You must not use any built-in exponent function or operator.
 * 
 * For example, do not use pow(x, 0.5) in c++ or x ** 0.5 in python.
 * 
 * Approach - Binary Search, Recursion + Bit shifts, Newton's method
 */
public class P69Sqrtx {

	public static void main(String[] args) {
//		int x = 0;
//		int x = 4;
//		int x = 5;
//		int x = 8;
//		int x = 16;
//		int x = 2147395599;
//		int x = 2147395600;
		int x = 2147483647;

		int squareRootNewton1 = mySqrtNewton1(x);
		System.out.println("Newton1: The square root of " + squareRootNewton1);

		int squareRootNewton2 = mySqrtNewton2(x);
		System.out.println("Newton2: The square root of " + squareRootNewton2);

		int squareRootBS = mySqrtBinarySearch(x);
		System.out.println("Binary Search: The square root of " + squareRootBS);

		int squareRootMath = mySqrtMath(x);
		System.out.println("Math: The square root of " + squareRootMath);

		int squareRootRBS = mySqrtRecursionBitShifts(x);
		System.out.println("Recursion Bit Shifts: The square root of " + squareRootRBS);

	}

	// Newton's method
	// x(k+1) = (xk + num/xk)/2 converges to rtx if x0 = x
	private static int mySqrtNewton1(int x) {
		if (x < 2) {
			return x;
		}
		long y = x / 2;
		while (y * y > x) {
			y = (y + x / y) / 2;
		}
		return (int) y;
	}

	// We define that error should be < 1
	// Time complexity - O(logN) guess sequence converges quadratically
	// Space complexity - O(1)
	private static int mySqrtNewton2(int x) {
		if (x < 2) {
			return x;
		}
		double x0 = x;
		double x1 = (x0 + x / x0) / 2;
		while (Math.abs(x0 - x1) >= 1) {
			x0 = x1;
			x1 = (x0 + x / x0) / 2;
		}
		return (int) x1;
	}

	// Binary Search
	// Here we iterate over sorted set of integers.
	public static int mySqrtBinarySearch(int x) {
		long start = 1;
		long end = x / 2 + 1;
		while (start <= end) {
			long mid = start + (end - start) / 2;
			if (mid * mid == x) {
				return (int) mid;
			} else if (mid * mid < x) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return (int) end;
	}

	// Pocket calculator algorithm
	// Pocket calculators computes well exponential functions and natural logarithms
	// by having log tables hardcoded or by other means. We reduce square root
	// calculations to these 2 algos. rt(x) = e ^ (1/2)*logx
	// We use non elementary functions.
	// Time complexity - O(1)
	// Space complexity - O(1)
	private static int mySqrtMath(int x) {
		if (x < 2) {
			return x;
		}
		int start = (int) Math.pow(Math.E, 0.5 * Math.log(x));
		int end = start + 1;
		return (long) end * end > x ? start : end;
	}

	// Recursion + Bit shifts
	// For recursion base cases are rtx = x for x < 2. We decrease x recursively at
	// each step to go down the base case. As rt(x) = 2 * rt(x/4). So rtx can be
	// computed recursively as mySqrt(x) = 2*mySqrt(x/4). One can stop here, but
	// with left and right shifts, which are fast manipulations with bits:
	// x << y or x*2^y and x >> y or x/2^y. One can write the recursion as
	// mySqrt(x) = mySqrt(x >> 2) << 1 to fasten up the computations.
	// Time complexity - O(logN)
	// tn = at(n/b) + theta(n^d) = tn/2 + n^0 hence case 2.
	// n ^ logb a + log d+1 N = O(logN)
	// Space complexity - O(logN) for recursion stack
	private static int mySqrtRecursionBitShifts(int x) {
		if (x < 2) {
			return x;
		}
		int left = mySqrtRecursionBitShifts(x >> 2) << 1;
		int right = left + 1;
		return (long) right * right > x ? left : right;
	}
}
