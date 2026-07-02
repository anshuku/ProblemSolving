package LeetCode.BinarySearch;

/*
 * P3971. Maximum Total Value - Hard
 * 
 * You are given two integer arrays value and decay, and an integer m.
 * 
 * > value[i] represents the initial value at index i.
 * > decay[i] represents how much the value decreases after each selection of index i.
 * 
 * You may select any index multiple times. The total number of selections across all indices must not exceed m.
 * 
 * If you select index i for the t(th) time, where t is 1-indexed, the value gained is value[i] - decay[i] * (t - 1).
 * 
 * Return the maximum total value you can obtain. Since the answer may be large, return it modulo 10^9 + 7.
 * 
 * Constraints:
 * > 1 <= value.length == decay.length <= 10^5
 * > 1 <= value[i], decay[i] <= 10^9​​​​​​​
 * > 1 <= m <= 10^9
 * 
 * Approach - Binary Search + Greedy + Arithmetic Sum
 */
public class P3971MaximumTotalValue {

	private static final int modulo = 1_000_000_007;

	public static void main(String[] args) {
		int[] value = { 6, 5, 4 };
		int[] decay = { 2, 1, 1 };
		int m = 4;

//		int[] value = { 7, 2, 2 };
//		int[] decay = { 3, 2, 1 };
//		int m = 2;

//		int[] value = { 4, 3 };
//		int[] decay = { 5, 4 };
//		int m = 5;

		int maxValueBinarySearch = maxTotalValueBinarySearch(value, decay, m);
		System.out.println("Binary Search: The max value one can obtain: " + maxValueBinarySearch);

		int maxValueBruteForce = maxTotalValueBruteForce(value, decay, m);
		System.out.println("Brute Force: The max value one can obtain: " + maxValueBruteForce);
	}

	// Binary Search + Greedy + Arithmetic Sum
	// For every index i, the values one can obtain are value[i], value[i] -
	// decay[i], value[i] - 2*decay[i], value[i] - 3*decay[i]... So each index
	// generates an AP. We need to choose at most m numbers from the union of all
	// these progressions such that the sum is max. This is similar to selecting the
	// largest m numbers from infinitely many decreasing sequences.
	// Key Idea: Suppose we know threshold X(which we'll get via binary search). We
	// select every reward >= X. For each index, value - decay*(t - 1) >= X
	// t <= (value - X)/decay + 1. Hence, count = floor((value - X)/decay) + 1.
	// Provided value >= X, otherwise count = 0. So, we can compute
	// > How many values are at least X and > Their total sum in O(n).
	// Sum of first k terms: If k selections, then v, v-d, v-2d...
	// Sum=k*(2v - (k-1)*d)/2, as it has negative common difference.
	// Binary Search: We binary search largest threshold T such that count(T) >= m.
	// Why largest? Because we want the cutoff as high as possible.
	// Handling duplicates: Suppose count(T) = 15, m = 10. Then, we've taken 15
	// values >= T. Some of them are exactly T. We only need 10 values. Extra values
	// are all equal T. So, answer = Sum(T) - (count - m)*T. This is the standard
	// trick used in may "top k values" problems.
	// The formula totalSum -= (totalCount - m) * threshold; is important, as for
	// example: threshold = 8, Selected 12, 10, 9, 8, 8, 8. But we only need 5
	// numbers. One extra 8 is removed. The formula removes exactly the excess
	// threshold-valued terms.
	// Time complexity - Binary search range 1...10^9 or 1e9. About 31 iterations,
	// Each iteration O(n). Total O(n log 1e9) = 3 million operations
	// Space complexity - O(1).
	private static int maxTotalValueBinarySearch(int[] value, int[] decay, int m) {
		int n = value.length;

		long start = 1;
		long end = 1000000000;

		// We want to find the upper bound for threshold
		while (start <= end) {
			long mid = start + (end - start) / 2;

			// Enough numbers remain, increase threshold.
			if (count(value, decay, mid) >= m) {
				start = mid + 1;
			} else {// Otherwise threshold too high, lower it.
				end = mid - 1;
			}
		}

		// Upper bound will reach next value so we subtract 1 to get the last value
		long threshold = start - 1;

		long totalCount = 0;
		long totalSum = 0;

		for (int i = 0; i < n; i++) {
			if (value[i] >= threshold) {
				long k = (value[i] - threshold) / decay[i] + 1;

				totalCount += k;

				totalSum += getArithmeticSum(value[i], decay[i], k);
			}
		}

		totalSum -= (totalCount - m) * threshold;

		// Below isn't needed as the values are positive(threshold >= 1)
		// sum(T) >= (count - m) * threshold. Hence, totalSum is never negative before
		// taking % MOD. We can directly return (int) totalSum % modulo;
		// if (totalSum < 0) is a defensive pattern often used after %, but it's not
		// required here.
//		totalSum %= modulo;
//		
//		if (totalSum < 0) {
//			totalSum += modulo;
//		}

		return (int) totalSum % modulo;
	}

	private static long getArithmeticSum(int value, int decay, long k) {
		// a + (a-d) + (a-2d) +... = k*(first + last)/2 where last = v - (k-1)*d
		return k * (2 * value - (k - 1) * decay) / 2;
	}

	private static long count(int[] value, int[] decay, long threshold) {
		long count = 0;

		for (int i = 0; i < value.length; i++) {
			if (value[i] >= threshold) { // The progression contributes only then.
				// Number of terms, v, v-d, v-2d,... >= x (t(n) >= x or v - (n-1)*d >= x)
				count += (value[i] - threshold) / decay[i] + 1;
			}
		}
		return count;
	}

	// Brute Force - TLE
	// Here as per constraint, value.length <= 10^5 and m <= 10^9. In worst case,
	// the sum can reach 10^^14 >> int max. Hence, brute force will lead to TLE.
	public static int maxTotalValueBruteForce(int[] value, int[] decay, int m) {
		int n = value.length;

		long result = 0;

		while (m > 0) {
			int max = 0;
			int maxIndex = -1;
			for (int i = 0; i < n; i++) {
				if (max < value[i]) {
					max = value[i];
					maxIndex = i;
				}
			}
			if (max > 0) {
				result += value[maxIndex];
				result %= modulo;
				value[maxIndex] -= decay[maxIndex];
			} else {
				break;
			}
			m--;
		}
		return (int) result;
	}
}
