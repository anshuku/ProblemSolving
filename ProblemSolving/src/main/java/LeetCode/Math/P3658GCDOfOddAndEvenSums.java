package LeetCode.Math;

/*
 * P3658. GCD of Odd and Even Sums - Easy
 * 
 * You are given an integer n. Your task is to compute the GCD (greatest common divisor) of two values:
 * 
 * > sumOdd: the sum of the smallest n positive odd numbers.
 * > sumEven: the sum of the smallest n positive even numbers.
 * 
 * Return the GCD of sumOdd and sumEven.
 * 
 * Approach - Math
 */
public class P3658GCDOfOddAndEvenSums {

	public static void main(String[] args) {
		int n = 4;

		int gcdOfOddEvenSums = gcdOfOddEvenSums(n);
		System.out.println("The GCD of sum of 1st positive odd and even numbers is: " + gcdOfOddEvenSums);
	}

	// The sum of 1st n odd numbers = 1 + 3 + 5.. 2n-1 = n/2*(2+(n-1)*2) = n^2.
	// The sum of 1st n even numbers = 2 + 4.. 2n = n/2*(2*2+(n-1)*2) = n*(n+1).
	// GCD of (n^2, n*(n+1)) = GCD of n*(n, n+1), GCD of (n,n+1) = 1, so n.
	public static int gcdOfOddEvenSums(int n) {
		return n;
	}
}
