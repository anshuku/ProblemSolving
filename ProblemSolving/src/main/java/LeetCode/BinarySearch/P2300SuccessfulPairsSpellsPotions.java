package LeetCode.BinarySearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
 * Approach - Sorting, Binary search, 2 Pointers
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

		int[] pairsSort2Pointers = successfulPairsSort2P(spells, potions, success);
		System.out.println("Sort 2 Pointers: The possible pairs are " + Arrays.toString(pairsSort2Pointers));

		int[] pairsSortBinarySearchExact = successfulPairsSortBSExact(spells, potions, success);
		System.out.println(
				"Sort Binary Search exact: The possible pairs are " + Arrays.toString(pairsSortBinarySearchExact));

		int[] pairsBruteForce = successfulPairsBF(spells, potions, success);
		System.out.println("Brute Force: The possible pairs are " + Arrays.toString(pairsBruteForce));
	}

	private static int[] successfulPairsFrequency(int[] spells, int[] potions, long success) {
		int n = spells.length;
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

	// Binary search + Sorting
	// For a given spell, say spell * a = success. So for all x, where x >= a, spell
	// * x will be >= success. If we know the minPotion in potions array, whose
	// product with a given spell is >= success, then all potions >= minPotions also
	// forms successful pairs with it. Now, spell * minPotion >= success
	// minPotion >= success/spell, minPotion = ceil(success / spell). To use Binary
	// search we sort potions array and then search the 1st index = index of
	// minPotion or the element just > minPortion.
	// Time complexity - O((m+n)*logm), where n is length of spells array and m is
	// number of elements in potions array. Sorting potions array takes O(m*logm)
	// time. Then each element of spells array we perform binary search which takes
	// O(logm) time. For n element it takes O(nlogm). Overall O(mlogm + nlogm)
	// Space complexity - O(logm), in Java Arrays.sort() is implemented using a
	// variant of Quick Sort Algorithm which takes O(logm) time.
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

	// 2 Pointers + Sorting
	// Say, for spell = a and minPotion = b, we've spell*minPotion = success.
	// For spell>a, the minimum required potion is minPotion <= b(result>=success)
	// So, if spells and potions are sorted in increasing order, and we know where
	// the minPotion for ith spell is present in potions array, then minPotion for
	// (i+1)th spell will be present at the same or smaller index than ith spell in
	// potions. We start with 2 pointers, 1 pointing to the smallest spell and other
	// pointing to the largest potion. If the product of current spell and potion is
	// >= success, then we keep on decreasing 2nd pointer to point to a smaller
	// potion. Thus, we will have the 2nd pointer in index given by minPotion and we
	// can count the number of potions that form the successful pairs with the
	// current spell based on its index.
	// Algo: We create a list of pairs sortedSpells with 1st element being spell
	// strength and 2nd element being its original index in spells array. Sort the
	// sortedSpells and potions arrays in ascending order. We initialize potionIndex
	// = potions.length - 1, to keep track of index of current potion in potions.
	// For each spell and its original index in the sortedSpells list: While we
	// haven't run out of potions and the product of the current spell strength and
	// potion at potionIndex >= success, decrement potionIndex by 1. We stop at
	// minPotion for current spell. Calculate the number of successful pairs for
	// current spell as m - (potionIndex + 1) and store the result at pairs[index].
	private static int[] successfulPairsSort2P(int[] spells, int[] potions, long success) {
		int n = spells.length;
		int m = potions.length;
		List<int[]> spellsList = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			spellsList.add(new int[] { spells[i], i });
		}
		spellsList.sort(Comparator.comparingInt(a -> a[0]));
		Arrays.sort(potions);
		int[] pairs = new int[n];
		int potionIndex = m - 1;
		// For each spell we find the respective minPotion index.
		for (int[] spell : spellsList) {
			while (potionIndex >= 0 && (long) spell[0] * potions[potionIndex] >= success) {
				potionIndex--;
			}
			pairs[spell[1]] = m - potionIndex - 1;
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
