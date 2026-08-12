package LeetCode.Math;

/*
 * P258. Add Digits - Easy
 * 
 * Given an integer num, repeatedly add all its digits until the result has only one digit, and return it.
 * 
 * Approach - Math: digital sum
 */
public class P258AddDigits {

	public static void main(String[] args) {
//		int num = 38;
//		int num = 27;
		int num = 882;

		int digitalRootMathsShort = addDigitsMathsShort(num);
		System.out.println("Maths Short: The digital root for the number is: " + digitalRootMathsShort);

		int digitalRootMaths = addDigitsMaths(num);
		System.out.println("Maths: The digital root for the number is: " + digitalRootMaths);

		int digitalRootIterative = addDigitsIterative(num);
		System.out.println("Iterative: The digital root for the number is: " + digitalRootIterative);

		int digitalRootRecursive = addDigitsRecursive(num);
		System.out.println("Recursive: The digital root for the number is: " + digitalRootRecursive);

		int digitalRootBruteForce = addDigitsBruteForce(num);
		System.out.println("Brute Force: The digital root for the number is: " + digitalRootBruteForce);
	}

	// Maths
	// There is known formula to compute digital root in a decimal numeral system:
	// dr 10 (n) = 0, if n = 0
	// dr 10 (n) = 9, if n = 9k
	// dr 10 (n) = n mod 9, if n =/= 9k
	// Test of divisibility by 9: A number is divisible by 9, if and only if the sum
	// of its digits is divisible by 9.
	// The input number num can be presented in a standard way, where dk is digit:
	// num = d0*10^0 + d1*10^1 + d2*10^2 + ... + dk*10^k
	// One could expand each power of 10, using the following:
	// 10 = 9*1 + 1, 100 = 99 + 1 = 9*11 + 1, 1000 = 999 + 1 = 9*111 + 1
	// 10^k = 100..0 = 99..9 + 1 = 9*11..1 + 1, where .. = k times
	// num = d0 + d1*(9*1+1) + d2*(9*11+1) + ... + dk*(9*11..1 + 1)
	// num = (d0 + d1 + d2 + ... + dk) + 9*(d1 + 11d2 + ... + 11..1dk)
	// Taking mod 9 both sides
	// num mod 9 = (d0 + d1 + d2 + ... + dk) mod 9
	// To consider 3 cases: the sum of digits is 0, the sum of digits is divisible
	// by 9, and the sum of digits is not divisible by 9:
	// This derives the known formula given at the top
	// The last 2 cases could be merged into 1:
	// dr 10 (n) = 0, if n = 0
	// dr 10 (n) = 1 + (n - 1) mod 9, if n =/= 0
	// Time complexity - O(1)
	// Space complexity - O(1)
	private static int addDigitsMathsShort(int num) {
		return num == 0 ? 0 : 1 + (num - 1) % 9;
	}

	private static int addDigitsMaths(int num) {
		if (num == 0) {
			return 0;
		}
		if (num % 9 == 0) {
			return 9;
		}
		return num % 9;
	}

	// Iteration
	// Time complexity - O(logn)
	private static int addDigitsIterative(int num) {
		int sum = 0;

		while (num > 0) {
			sum += num % 10;

			num /= 10;

			if (num == 0 && sum > 9) {
				num = sum;
				sum = 0;
			}
		}
		return sum;
	}

	public static int addDigitsRecursive(int num) {
		int sum = 0;

		while (num > 0) {
			sum += num % 10;
			num /= 10;
		}

		if (sum < 10) {
			return sum;
		}

		return addDigitsRecursive(sum);
	}

	private static int addDigitsBruteForce(int num) {
		while (num > 10) {
			int sum = 0;

			char[] nums = String.valueOf(num).toCharArray();

			for (int i = 0; i < nums.length; i++) {
				sum += nums[i] - '0';
			}

			num = sum;
		}
		return num;
	}

}
