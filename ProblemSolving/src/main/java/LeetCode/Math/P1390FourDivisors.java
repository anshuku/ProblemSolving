package LeetCode.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P1390. Four Divisors - Medium
 * 
 * Given an integer array nums, return the sum of divisors of the integers in that array 
 * that have exactly four divisors. If there is no such integer in the array, return 0.
 * 
 * Approach - Math
 */
public class P1390FourDivisors {

	public static void main(String[] args) {
//		int[] nums = { 21, 4, 7 };
//		int[] nums = { 21, 4, 7, 16 };
		int[] nums = { 21, 4, 7, 16, 32, 8, 27, 81 };

		int sumDivisorsEnumerationSqrt = sumFourDivisorsEnumerationSqrt(nums);
		System.out.println("Enumeration Sqrt: The sum of 4 divisors is: " + sumDivisorsEnumerationSqrt);

		int sumDivisorsEnumeration = sumFourDivisorsEnumeration(nums);
		System.out.println("Enumeration: The sum of 4 divisors is: " + sumDivisorsEnumeration);

		int sumDivisorsPreprocessingSErst = sumFourDivisorsPreprocessingSErst(nums);
		System.out.println("Preprocessing Sieve Erst: The sum of 4 divisors is: " + sumDivisorsPreprocessingSErst);

		int sumDivisorsEnumerationSEuler = sumFourDivisorsEnumerationSEuler(nums);
		System.out.println("Enumeration Sieve Euler: Thep sum of 4 divisors is: " + sumDivisorsEnumerationSEuler);

	}

	// Enumeration
	// We sequentially check if the numbers in num has 4 divisors and add all the
	// divisors to result. For any x, we can find the number of its factors using a
	// method similar to prime number checking. If an integer x has a factor y, then
	// it must also have a factor x/y, and at least one of y and x/y is <= sqrt(x).
	// So, we only enumerate y within [1, rt(x)] and obtain the other factors of x
	// through x/y, resulting in time complexity of O(rt(x)).
	// Time complexity - O(N*rtC), where C is the range of elements in nums <= 10^5.
	// Space complexity - O(1)
	private static int sumFourDivisorsEnumeration(int[] nums) {
		int sum = 0;
		for (int num : nums) {
			int divisors = 0;
			int add = 0;
			for (int i = 1; i * i <= num; i++) {
				if (num % i == 0) {
					divisors++;
					add += i;
					// We check if i and num/i are equal
					// If not, consider num/i as a new factor.
					if (i * i != num) {
						divisors++;
						add += num / i;
					}
				}
			}
			if (divisors == 4) {
				sum += add;
			}
		}
		return sum;
	}

	public static int sumFourDivisorsEnumerationSqrt(int[] nums) {
		int sum = 0;
		for (int num : nums) {
			int sqrt = (int) Math.sqrt(num);
			if (sqrt * sqrt == num) {
				continue;
			}
			int divisors = 0;
			int add = 0;
			for (int i = 1; i <= sqrt; i++) {
				if (num % i == 0) {
					divisors++;
					add += i;
					add += num / i;
				}
			}
			if (divisors == 2) {
				sum += add;
			}
		}
		return sum;
	}

	// Preprocessing -
	// Accoring to Fundamental theorem of Arithmetic/Unique Factorization theorem,
	// if an integer x can decomposed as: x = p1^a1 * p2^a2 * ... pk^ak where pi are
	// distinct prime numbers, the numbers of divisors of x is:
	// factor count(x) = TT i=1 to k (ai + 1). If factor count(x) = 4, then 2 cases:
	// > An integer x has only 1 prime factor with exponent of 3. In this case
	// factor count = (3+1) =4.
	// > An integer x has 2 prime factors with exponent = 1. In this case, factor
	// count(x) = (1+1)*(1+1) = 4.
	// For the 1st case, we need to find all primes <= C^1/3. For 2nd case, we need
	// to find all primes <= C, then multiply them pairwise and remove the results
	// that exceed C. To find all primes <= C. We use Sieve of Eratosthenes or
	// Euler's seive. After finding all primes, we can construct all x that satisfy
	// the 2 conditions. We store x and the sum of its factors in a hash map,
	// allowing O(1) check for each element in array nums to compute sum of factors.
	// Time complexity - O(pi^2(C) + Clog(logC) + N) or O(pi^2(C) + C + N), pi(x) be
	// the prime-counting function, for num of primes<=X.
	// The time complexity for Siece of Eratosthenes is O(Clog(logC), and for Sieve
	// of Euler is O(C). The time complexity for constructing all 4 factor numbers
	// using the prime table is O(pi(C^1/3)) + O(pi^2(C)) = O(pi^2(C)), and time
	// complexity for checing all nums is O(N).
	// Space complexity - O(C + pi(C)), we need array of length c and array of
	// length pi(C) is used to store all primes.
	private static int sumFourDivisorsPreprocessingSErst(int[] nums) {
		int result = 0;
		int C = 100000;
		int C3 = 46;

		boolean[] isPrime = new boolean[C + 1];
		Arrays.fill(isPrime, true);
		List<Integer> primes = new ArrayList<>();

		// Sieve of Eratosthenes
		for (int i = 2; i <= C; i++) {
			if (isPrime[i]) {
				primes.add(i);
			}
			for (int j = 2 * i; j <= C; j += i) {
				isPrime[j] = false;
			}
		}

		Map<Integer, Integer> factors = new HashMap<>();
		for (int prime : primes) {
			if (prime <= C3) {
				factors.put(prime * prime * prime, 1 + prime + prime * prime + prime * prime * prime);
			} else {
				break;
			}
		}

		for (int i = 0; i < primes.size(); i++) {
			for (int j = i + 1; j < primes.size(); j++) {
				if (primes.get(i) * primes.get(j) <= C) {
					factors.put(primes.get(i) * primes.get(j),
							1 + primes.get(i) + primes.get(j) + primes.get(i) * primes.get(j));
				} else {
					break;
				}
			}
		}

		for (int num : nums) {
			if (factors.containsKey(num)) {
				result += factors.get(num);
			}
		}

		return result;
	}

	private static int sumFourDivisorsEnumerationSEuler(int[] nums) {
		int C = 100000;
		int C3 = 46;
		boolean[] isPrime = new boolean[C + 1];
		Arrays.fill(isPrime, true);

		List<Integer> primes = new ArrayList<>();
		// Sieve of Euler
		for (int i = 2; i <= C; i++) {
			if (isPrime[i]) {
				primes.add(i);
			}
			for (int prime : primes) {
				if (i * prime > C) {
					break;
				}
				isPrime[i * prime] = false;
				if (i % prime == 0) {
					break;
				}
			}

		}

		Map<Integer, Integer> factors = new HashMap<>();
		for (int prime : primes) {
			if (prime <= C3) {
				factors.put(prime * prime * prime, 1 + prime + prime * prime + prime * prime * prime);
			} else {
				break;
			}
		}
		for (int i = 0; i < primes.size(); i++) {
			for (int j = i + 1; j < primes.size(); j++) {
				if (primes.get(i) * primes.get(j) <= C) {
					factors.put(primes.get(i) * primes.get(j),
							1 + primes.get(i) + primes.get(j) + primes.get(i) * primes.get(j));
				} else {
					break;
				}
			}
		}
		int result = 0;
		for (int num : nums) {
			if (factors.containsKey(num)) {
				result += factors.get(num);
			}
		}
		return result;
	}

}
