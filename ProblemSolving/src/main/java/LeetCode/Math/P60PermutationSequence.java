package LeetCode.Math;

import java.util.ArrayList;
import java.util.List;

/*
 * P60. Permutation Sequence - Hard
 * 
 * The set [1, 2, 3, ..., n] contains a total of n! unique permutations.
 * 
 * By listing and labeling all of the permutations in order, we get the following sequence for n = 3:
 * 
 * 1. "123" 2. "132" 3. "213" 4. "231" 5. "312" 6. "321"
 * 
 * Given n and k, return the kth permutation sequence.
 * 
 * Approach - Math: Permutations, Combinations, Factorial Number System: Permutation 
 */
public class P60PermutationSequence {

	public static void main(String[] args) {
//		int n = 3;
//		int k = 3;

		int n = 4;
		int k = 9;

		String kthPermutationFactorialSystem = getPermutationFactorialSystem(n, k);
		System.out
				.println("Factorial Number System: The kth permutation sequence is: " + kthPermutationFactorialSystem);

		String kthPermutationBoolean = getPermutationBoolean(n, k);
		System.out.println("Boolean: The kth permutation sequence is: " + kthPermutationBoolean);

		String kthPermutationAlphabets = getPermutationAlphabets(n, k);
		System.out.println("Alphabets: The kth permutation sequence is: " + kthPermutationAlphabets);
	}

	// Factorial Number system
	// Here, we use a elegant idea that is based on the mapping. It's much easier to
	// generate a number than combinations / permutations. We generate numbers, and
	// then map them to combinations / subsets / permutations.
	// This sort of encoding is widely used in password-cracking algorithms.
	// One could map a subset with a binary bitmask of length n. The ith 0 means
	// "the element number i is absent" and ith 1 means "the element i is present".
	// One could do the same for permutations, mapping permutation with the integer
	// in Factorial Number System Representation.
	// Why do we need Factorial Number System
	// Usually standard decimal or binary positional system could meet our needs.
	// Example, each subset could be described by a number in binary representation:
	// k = ∑ m = 0 -> N - 1 km * 2^m. 0 <= km <= 1.
	// 123 -> 000 | 001 -> 3 | 010 -> 2 | 100 -> 1 | 011 -> 23 | 101 -> 13 | 110 ->
	// 12 | 111 -> 123
	// The problem with permutations is that there is a much more permutations than
	// subsets, N! grows up much faster than 2^N.
	// Therefore, the solution space provided by binary positional system with
	// constant base(2^m) cannot match with the number of permutations.
	// Here, factorial number system helps, it's a positional system with
	// non-constant base m!.
	// k = ∑ m = 0 -> N - 1 km * m!, 0 <= km <= m. The magnitude of weights is not
	// constant as well and depends on base: 0 <= km <= m for the base m!, i.e. k0 =
	// 0, 0 <= k1 <= 1, 0 <= k2 <=2, etc.
	// Permutation | Permutation Number | Factorial Number System representation
	// 123 | 0 = 0*2! + 0*1! + 0*0! | 000
	// 132 | 1 = 0*2! + 1*1! + 0*0! | 010
	// 213 | 2 = 1*2! + 0*1! + 0*0! | 100
	// 231 | 3 = 1*2! + 1*1! + 0*0! | 110
	// 312 | 4 = 2*2! + 0*1! + 0*0! | 200
	// 321 | 5 = 2*2! + 1*1! + 0*0! | 210
	// We could now map all permutations, from permutation number 0:
	// k = 0 = ∑ m = 0 -> N - 1 0*m! to permutation number N! - 1:
	// k = N! - 1 = ∑ m = 0 -> N - 1 m*m!.
	// Hence, we've a way to encode permutation number into factorial
	// representation. Now let us use this factorial representation to construct the
	// permutation itself.
	// How to construct the Permutation from its Factorial Representation:
	// Let us pick up N = 3, which corresponds to the input array nums = [1,2,3],
	// and construct its permutation number k = 3. Since we number the permutations
	// from 0 to N! - 1 (and not from 1 to N! as in the problem), we take the
	// permutation number k = 2.
	// Let us first construct the factorial representation of k = 2:
	// k = 2 = 1*2! + 0*1! + 0*0! = (1, 0, 0)
	// The coefficients in factorial representation are indices of elements in the
	// input array. These are not direct indices, but the indices after the removal
	// of already used elements. This is due to the fact that each element should be
	// used in permutation only once.
	// 213 | 2 = 1*2! + 0*1! + 0*0! | 100
	// For k = 2, the 1st coefficient or weight in factorial representation is 1,
	// i.e. the 1st element in the permutation is nums[1] = 2. We use it and then
	// delete it from nums, since each element should be used only once
	// Next coefficient in factorial representation is 0. Let's use nums[0] = 1 in
	// the permutation and then delete it from nums.
	// Next coefficient in factorial representation is 0. Let's use nums[0] = 3 in
	// the permutation and then delete it from nums. We get the permutation as 213.
	// Algorithm:
	// * Generate input array nums of numbers from 1 to N.
	// * Compute all factorial bases from 0 to (N - 1)!.
	// * Decrease k by 1 to make it fit into (0, N!-1) interval.
	// * Compute factorial representation of k. Use factorial coefficients to
	// construct the permutation. Return the final permutation at end.
	// Time complexity - O(n^2), because to delete elements from the list in a loop,
	// one has to perform n + (n-1) + ... + 1 = n*(n-1)/2 operations.
	// Space complexity - O(n) for nums list and factorial array.
	private static String getPermutationFactorialSystem(int n, int k) {
		int[] factorial = new int[n];
		factorial[0] = 1;

		// Easier to perform remove
		List<Integer> nums = new ArrayList<>();

		// Compute factorial system bases 0!, 1!, ..., (n-1)!
		// Also generate nums 1, 2, 3, ..., n
		for (int i = 1; i < n; i++) {
			factorial[i] = factorial[i - 1] * i;

			nums.add(i);
		}

		nums.add(n);

		// Fit k in the interval 0 to (N! - 1)
		k--;

		StringBuilder sb = new StringBuilder();

		// Compute factorial representation of k.
		// Iterate from backward to get the highest number as prefix first based on k.
		for (int i = n - 1; i >= 0; i--) {
			int idx = k / factorial[i];

			sb.append(nums.get(idx));

			nums.remove(idx);

			k -= idx * factorial[i];
		}

		return sb.toString();
	}

	private static String getPermutationBoolean(int n, int k) {
		int[] factorial = new int[n + 1];
		factorial[0] = 1;

		for (int i = 1; i <= n; i++) {
			factorial[i] = factorial[i - 1] * i;
		}

		boolean[] isPlaced = new boolean[n + 1];

		int start = 1;

		StringBuilder sb = new StringBuilder();

		for (int pos = 0; pos < n; pos++) {
			for (int i = 1; i <= n; i++) {
				if (isPlaced[i]) {
					continue;
				}

				isPlaced[i] = true;

				int ways = factorial[n - 1 - pos];

				if (start + ways > k) {
					sb.append(i);
					break;
				}

				isPlaced[i] = false;
				start += ways;
			}
		}

		return sb.toString();
	}

	public static String getPermutationAlphabets(int n, int k) {
		int[] count = new int[n + 1];

		for (int i = 1; i <= n; i++) {
			count[i]++;
		}

		int start = 1;

		StringBuilder sb = new StringBuilder();

		for (int pos = 0; pos < n; pos++) {
			for (int i = 1; i <= n; i++) {
				if (count[i] == 0) {
					continue;
				}

				count[i]--;

				long ways = permutations(n - 1 - pos, count, k, n);

				if (start + ways > k) {
					sb.append(i);
					break;
				}

				count[i]++;
				start += ways;
			}
		}

		return sb.toString();
	}

	private static long permutations(int remaining, int[] count, int k, int n) {
		long ways = 1;

		for (int i = 1; i <= n; i++) {
			if (count[i] == 0) {
				continue;
			}

			ways *= combinations(remaining, count[i], k);

			if (ways > k) {
				break;
			}

			remaining -= count[i];
		}
		return ways;
	}

	private static long combinations(int n, int m, int k) {
		m = Math.min(m, n - m);

		long ways = 1;

		for (int i = 1; i <= m; i++) {
			ways = ways * (n - i + 1) / i;

			if (ways > k) {
				return k + 1;
			}
		}
		return ways;
	}

}
