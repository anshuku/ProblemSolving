package LeetCode.BinarySearch.P528RandomPickWithWeight;

import java.util.Random;

/*
 * P528. Random Pick with Weight - Medium
 * 
 * You are given a 0-indexed array of positive integers w where w[i] describes the weight of the ith index.
 * 
 * You need to implement the function pickIndex(), which randomly picks an index in the range 
 * [0, w.length - 1] (inclusive) and returns it. The probability of picking an index i is w[i] / sum(w).
 * 
 * For example, if w = [1, 3], the probability of picking index 0 is 1 / (1 + 3) = 0.25 
 * (i.e., 25%), and the probability of picking index 1 is 3 / (1 + 3) = 0.75 (i.e., 75%).
 * 
 * Approach - Binary seach, Prefix Sum
 */
public class P528RandomPickWithWeightBinarySearch {

	int[] prefix;
	Random random;

	// Binary search
	// The time taken for pickIndex() can be reduced with binary search to O(logN).
	// As the prefix sum is sorted in ascending order. We replace the linear search
	// with binary search. One can use built-in binary search functions as well.
	// Prefix Sum = Number Line model, w = [2,5,3] and prefix = [2,7,10], total = 10
	// Number line is: index 0 -> (0,2]; index 1 -> (2,7]; index 2 -> (7,10].
	// Case 1 - Float version
	// float randomNum = random.nextFloat(); [0.0, 1.0)
	// float target = randomNum * total; [0.0, total), target [0, 10)
	// Number line becomes: index 0 -> [0,2); index 1 -> [2,7); index 2 -> [7,10).
	// So 1st prefix strictly > target, for 0.0 pick index 0; 1.999 -> index 0
	// 2.0 -> index 1; 6.999 -> 1; 7.0 -> 2. Interval type = left closed,right open.
	// Case 2 - Int version
	// int target = random.nextInt(total) + 1, target [1, total]
	// Number line becomes: index 0 -> [1,2]; index 1 -> [3,7]; index 2 -> [8,10].
	// Now we must pick 1st prefix >= target, Interval type = closed, closed
	// So, when random starts from 0, use < in linear and <= in binary
	// When random starts from 1, use <= in linear and < in binary.
	// If we use <= in linear float version(start=0), then index 0 gets more p.
	// When < in linear int(start=1), then last index gets more p, p=probabilty.
	// Time complexity - O(N), for constructor O(N) and for pickIndex() O(logN).
	// Space complexity - O(1)
	public P528RandomPickWithWeightBinarySearch(int[] w) {
		random = new Random();
		int n = w.length;
		prefix = new int[n];
		prefix[0] = w[0];
		for (int i = 1; i < n; i++) {
			prefix[i] = prefix[i - 1] + w[i];
		}
	}

	public int pickIndex() {
		int n = prefix.length;
		int start = 0;
		int end = n - 1;
		// [1, 4] | 1 2 3 4 <=
//		int target = random.nextInt(prefix[n - 1]) + 1;
//		while (start <= end) {
//			int mid = start + (end - start) / 2;
//			if (prefix[mid] < target) {
//				start = mid + 1;
//			} else {
//				end = mid - 1;
//			}
//		}
		// [0.0f, 1.0f)
		float randomNum = random.nextFloat();
		// [0, 4.0f) <
		float target = randomNum * prefix[n - 1];
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (prefix[mid] <= target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return start;
	}

	public static void main(String[] args) {
		int[] w = { 1, 3 };
		P528RandomPickWithWeightBinarySearch solution = new P528RandomPickWithWeightBinarySearch(w);

		int weightIndex = solution.pickIndex();
		System.out.println("The weight picked randomly is: " + weightIndex);
	}

}
