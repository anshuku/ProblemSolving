package LeetCode.Math;

/*
 * Medium Implement pow(x, n), which calculates x raised to the power n (i.e., xn).
 * 
 * LOD: Medium
 * 
 * x^n = (x^2)^n/2 if even
 * x^n = x*(x^2)^(n -1)/2 if odd
 */
public class P50Powxn {

	public static void main(String[] args) {
		double x = 2;
		int n = Integer.MIN_VALUE;
		Math.pow(x, n);
		double val = myPow(x, n);

		System.out.println("Bits: The value of x to the power n " + val);

		double valRecursive = myPowRecursive(x, n);

		System.out.println("Recursive: The value of x to the power n " + valRecursive);

		double valBF = myPowBF(x, n);

		System.out.println("Brute force: The value of x to the power n " + valBF);
	}

	// Binary exponentiation can be used to do logn calculations
	// Divide the power using binary representation of exponents.
	// We keep multiplying with pow with x if n is odd
	// and x with itself until n reaches 0.
	// Unsigned right shift >>> is used instead of >> since for n = -2147483648
	// for n = -n, it'll become n = 2147483648 which is again -2^31
	// After right shift 11000000000000000000000000000000 or -1073741824
	// At end of while loop it'll become -1 and the while loop will never end/TLE.
	// To avoid this >>> is used which pushed 0 at left.
	// Time complexity - O(log(n))
	// Space complexity - O(1)
	public static double myPow(double x, int n) {
		if (n == 1 || x == 1) {
			return x;
		}
		if (n < 0) {
			x = 1 / x;
			n = -n;
			if (n == Integer.MIN_VALUE) {
				n = Integer.MAX_VALUE;
				return x * myPow(x, n);
			}
		}
		double pow = 1;
		while (n != 0) {
			if ((n & 1) != 0) {
				pow *= x;
			}
			x *= x;
			n >>>= 1;
		}
		return pow;
	}
	
	// Time complexity - O(log(n))
	// Space complexity - O(1)
	private static double myPowRecursive(double x, int n) {
		if (x == 1 || n == 1) {
			return x;
		}
		double value = powRecursive(x, n);
		if (n < 0) {
			return 1 / value;
		} else {
			return value;
		}
	}

	private static double powRecursive(double x, int n) {
		if (n == 0) {
			return 1;
		}
		int power = n / 2;
		double val = powRecursive(x, power);
		if (n % 2 == 0) {
			return val * val;
		} else {
			return x * val * val;
		}
	}

	// Time complexity - O(n)
	// Space complexity - O(1)
	public static double myPowBF(double x, int n) {
		if (n == 1 || x == 1) {
			return x;
		}
		double val = 1f;
		if (n < 0) {
			x = 1 / x;
			n = -n;
		}
		for (int i = 0; i < n; i++) {
			val *= x;
		}
		return val;
	}

}
