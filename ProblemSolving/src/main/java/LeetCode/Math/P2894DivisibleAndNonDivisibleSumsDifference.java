package LeetCode.Math;

/*
 * P2894. Divisible and Non-divisible Sums Difference - Easy
 * 
 * You are given positive integers n and m.
 * 
 * Define two integers as follows:
 * > num1: The sum of all integers in the range [1, n] (both inclusive) that are not divisible by m.
 * > num2: The sum of all integers in the range [1, n] (both inclusive) that are divisible by m.
 * 
 * Return the integer num1 - num2.
 * 
 * Constraints:
 * * 1 <= n, m <= 1000
 * 
 * Approach - Math, Iteration
 */
public class P2894DivisibleAndNonDivisibleSumsDifference {

	public static void main(String[] args) {
		int n = 10, m = 3;
//		int n = 5, m = 6;

		int sumDifferenceMath = differenceOfSumsMath(n, m);
		System.out.println("Math: The Divisible and Non-divisible Sums Difference is: " + sumDifferenceMath);

		int sumDifferenceIteration = differenceOfSumsIteration(n, m);
		System.out.println("Iteration: The Divisible and Non-divisible Sums Difference is: " + sumDifferenceIteration);
	}

	// Math
	// Sum of 1,2,3,...n
	// Sum of numbers till n which are divisible by m
	// m + 2m + 3m + ... km = m*(1 + 2 + 3 + ... + k) = m*k*(k+1)/2
	// not divisible - divisible = ? ... 1
	// not divisible + divisible = total
	// not divisible = total - divisible ... 2
	// Using 2 in 1, total - divisible - divisible
	// num1 - num2 = total - 2*divisible
	// n*(n+1)/2 - m*k*(k+1)/2
	// km is last number which is <= n and divisible by m.
	// Here, k is floor(n/m) or the count of multiples of m upto n.
	// Time complexity - O(1)
	// Space complexity - O(1)
	private static int differenceOfSumsMath(int n, int m) {
		int k = n / m;

		return n * (n + 1) / 2 - m * k * (k + 1);
	}

	// Iteration
	// Let n be the given integer.
	// Time complexity - O(n). We traverse all numbers from 1 to n.
	// Space complexity - O(1). Only one integer variable ans was used.
	public static int differenceOfSumsIteration(int n, int m) {
		int difference = 0;
		for (int i = 1; i <= n; i++) {
			if (i % m != 0) {
				difference += i;
			} else {
				difference -= i;
			}
		}
		return difference;
	}

}
