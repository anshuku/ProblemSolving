package LeetCode.BinarySearch;

/*
 * P367. Valid Perfect Square - Easy
 * 
 * Given a positive integer num, return true if num is a perfect square or false otherwise.
 * 
 * A perfect square is an integer that is the square of an integer. 
 * In other words, it is the product of some integer with itself.
 * 
 * You must not use any built-in library function, such as sqrt.
 * 
 * Approach - Binary search, Newton's method
 */
public class P367ValidPerfectSquare {

	public static void main(String[] args) {
//		int num = 1;
//		int num = 14;
//		int num = 16;
		int num = 808201;

		boolean isSquareNewton = isPerfectSquareNewton(num);
		System.out.println("Newton's method: The number is perfect square: " + isSquareNewton);

		boolean isSquareBS = isPerfectSquareBS(num);
		System.out.println("Binary Search: The number is perfect square: " + isSquareBS);
	}

	// Newton's method
	// We need to find root of f(x) = x^2 - num = 0, the idea of Newton's algo is to
	// start from a seed(initial guess and large) and then compute a root as a
	// sequence of improved guesses. If we guessed xk(large), to compute next guess
	// x(k+1)(smaller), we approximate f(xk) but its tangent line
	// x(k+1) = xk - f(xk)/f'(xk). We use f(xk) = xk^2 - num and f'(xk) = 2xk.
	// x(k + 1) = 1/2(xk + num / xk). Choose a seed, since functions f(x) = x^2 -
	// num is monotonous, the smaller seed is better, so take num/2.
	// Do next guess if x*x > num and it's (x + num / x) / 2.
	// Time complexity - O(logN) guess sequence converges quadratically
	// Space complexity - O(1)
	private static boolean isPerfectSquareNewton(int num) {
		if (num == 1) {
			return true;
		}
		long x = num / 2;
		while (x * x > num) {
			x = (x + num / x) / 2;
		}
		return x * x == num;
	}

	// Binary search
	// The perfect square can be between 1 to num/2 + 1
	// For num > 2, the square root a is always > num 2 and < 1:
	// 1 < x < num/2. We can use binary serach to find the number.
	// Time complexity - O(logN), we compute time complexity with master theorem.
	// T(N) = a*T(N/b) + theta(N^d), this equation represents dividing the problem
	// into a subproblems of size N/b in theta(N^d) time. Here at a step there is
	// only one subproblem a = 1, size is half the initial problem b = 2 and this
	// happens in constant time d= 0. So log b (a) = d so case 2
	// O(n ^ [log b (a)] * log (d + 1) N) = O(logN)
	// Space complexity - O(1)
	public static boolean isPerfectSquareBS(int num) {
		long start = 1;
		long end = num / 2 + 1;

		while (start <= end) {
			long mid = start + (end - start) / 2;
			if (mid * mid == num) {
				return true;
			} else if (mid * mid < num) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return false;
	}

}
