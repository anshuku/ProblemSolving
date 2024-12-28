package LeetCode.BinarySearch;

import java.util.Arrays;

/*
 * P2300. Successful Pairs of Spells and Potions - Medium
 * 
 * You are given two positive integer arrays spells and potions, of length n and m 
 * respectively, where spells[i] represents the strength of the ith spell and potions[j] 
 * represents the strength of the jth potion.
 * 
 * You are also given an integer success. A spell and potion pair is considered 
 * successful if the product of their strengths is at least success.
 * 
 * Return an integer array pairs of length n where pairs[i] is the number of potions that 
 * will form a successful pair with the ith spell.
 * 
 * Approach - Sorting, Binary search
 */
public class P2300SuccessfulPairsSpellsPotions {

	public static void main(String[] args) {

//		int[] spells = { 5, 1, 3 };
//		int[] potions = { 1, 2, 3, 4, 5 };
//		long success = 7;

		int[] spells = { 5, 1, 3, 3, 3, 4, 4 };
		int[] potions = { 1, 2, 2, 3, 4, 3, 4, 5 };
		long success = 7;

//		int[] spells = { 3, 1, 2 };
//		int[] potions = { 8, 5, 8 };
//		long success = 16;

//		int[] spells = { 40, 11, 24, 28, 40, 22, 26, 38, 28, 10, 31, 16, 10, 37, 13, 21, 9, 22, 21, 18, 34, 2, 40, 40,
//				6, 16, 9, 14, 14, 15, 37, 15, 32, 4, 27, 20, 24, 12, 26, 39, 32, 39, 20, 19, 22, 33, 2, 22, 9, 18, 12,
//				5 };
//		int[] potions = { 31, 40, 29, 19, 27, 16, 25, 8, 33, 25, 36, 21, 7, 27, 40, 24, 18, 26, 32, 25, 22, 21, 38, 22,
//				37, 34, 15, 36, 21, 22, 37, 14, 31, 20, 36, 27, 28, 32, 21, 26, 33, 37, 27, 39, 19, 36, 20, 23, 25, 39,
//				40 };
//		long success = 600;

		int[] pairsSortFrequeuncy = successfulPairsFrequency(spells, potions, success);
		System.out.println("Frequeuncy Sum Array: The possible pairs are " + Arrays.toString(pairsSortFrequeuncy));

		int[] pairsSortBinarySearch = successfulPairsSortBS(spells, potions, success);
		System.out.println("Sort Binary Search: The possible pairs are " + Arrays.toString(pairsSortBinarySearch));

		int[] pairsSortBinarySearchExact = successfulPairsSortBSExact(spells, potions, success);
		System.out.println(
				"Sort Binary Search exact: The possible pairs are " + Arrays.toString(pairsSortBinarySearchExact));

		int[] pairsBruteForce = successfulPairsBF(spells, potions, success);
		System.out.println("Brute Force: The possible pairs are " + Arrays.toString(pairsBruteForce));
	}

	private static int[] successfulPairsFrequency(int[] spells, int[] potions, long success) {
		int n = spells.length;
		int m = potions.length;
		int max = 0;
		for (int p : potions) {
			if (max < p) {
				max = p;
			}
		}
		int[] count = new int[max + 1];
		for (int p : potions) {
			count[p]++;
		}
		for (int i = max - 1; i >= 0; i--) {
			count[i] += count[i + 1];
		}
		int[] pairs = new int[n];
		for (int i = 0; i < n; i++) {
			int spell = spells[i];
			long potion = (success + spell - 1) / spell; // For repeating spells/potions
			if (potion <= max) {
				pairs[i] = count[(int) potion];
			}
		}
		return pairs;
	}

	private static int[] successfulPairsSortBS(int[] spells, int[] potions, long success) {
		int n = spells.length;
		int m = potions.length;
		int[] pairs = new int[n];
		Arrays.sort(potions);

		for (int i = 0; i < n; i++) {
			int left = 0;
			int right = m - 1;
			if ((long) spells[i] * potions[left] >= success) {
				pairs[i] = m;
			}
			if ((long) spells[i] * potions[left] < success) {
				pairs[i] = 0;
			}
			while (left <= right) {
				int mid = left + (right - left) / 2;
				long product = (long) spells[i] * potions[mid];
				if (product >= success) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}
			pairs[i] = m - left;
		}

		return pairs;
	}

	// TLE for multiple mids;
	private static int[] successfulPairsSortBSExact(int[] spells, int[] potions, long success) {
		int n = spells.length;
		int m = potions.length;

		int pairs[] = new int[n];
		Arrays.sort(potions);

		for (int i = 0; i < n; i++) {
			int left = 0;
			int right = m - 1;
			boolean found = false;
			int mid = 0;
			while (left <= right) {
				mid = left + (right - left) / 2;
				long product = (long) spells[i] * potions[mid];
				if (product == success) {
					found = true;
					right = mid - 1; // Fails for potions = { 8, 5, 8 }
				} else if (product < success) {
					left = mid + 1;
				} else {
					right = mid - 1;
				}
			}
			if (found) {
				// mid can be an index 2 in 1 2 2 2 3
				// Here the 1st 2 is ignored so need to find the first index
				// TLE for multiple mids;
//				while(mid - 1>=0 && potions[mid] == potions[mid-1]) {
//					mid--;
//				}
				pairs[i] = m - mid;
			} else {
				pairs[i] = m - left;
			}
		}
		return pairs;
	}

	// Time Limit exceeds(TLE) for large datasets.
	public static int[] successfulPairsBF(int[] spells, int[] potions, long success) {
		int n = spells.length;
		int m = potions.length;

		int[] pairs = new int[n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				long product = (long) spells[i] * potions[j];
				if (product >= success) {
					pairs[i]++;
				}
			}
		}
		return pairs;
	}

}
