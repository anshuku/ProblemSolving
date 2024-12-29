package LeetCode.BinarySearch;

/*
 * P875. Koko Eating Bananas - Medium
 * 
 * Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. 
 * The guards have gone and will come back in h hours.
 * 
 * Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses 
 * some pile of bananas and eats k bananas from that pile. If the pile has less than 
 * k bananas, she eats all of them instead and will not eat any more bananas during this hour.
 * 
 * Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.
 * 
 * Return the minimum integer k such that she can eat all the bananas within h hours.
 * 
 * Approach - Binary Search
 */
public class P875KokoEatingBananas {

	public static void main(String[] args) {

		int[] piles = { 3, 6, 7, 11 };
		int h = 8;

//		int[] piles = { 30, 11, 23, 4, 20 };
//		int h = 5;

//		int[] piles = {30,11,23,4,20};
//		int h = 6;

//		int[] piles = { 805306368, 805306368, 805306368 };
//		int h = 1000000000;

//		int[] piles = { 332484035, 524908576, 855865114, 632922376, 222257295, 690155293, 112677673, 679580077,
//				337406589, 290818316, 877337160, 901728858, 679284947, 688210097, 692137887, 718203285, 629455728,
//				941802184 };
//		int h = 823855818;

//		int[] piles = { 831235932, 437082868, 576572631, 529869153, 55330371, 511060323, 581115062, 931692072,
//				600856556, 519045509, 504164418, 431105822, 580257183 };
//		int h = 964065706;

		int minSpeed = minEatingSpeed(piles, h);
		System.out.println("Left<=Right: The min eating speed is: " + minSpeed);

		int minSpeedEqualMid = minEatingSpeedMid(piles, h);
		System.out.println("Left<Right: The min eating speed is: " + minSpeedEqualMid);
	}

	// Time complexity: O(n*logn)
	// Space complexity: O(1)
	public static int minEatingSpeed(int[] piles, int h) {
		int n = piles.length;

		int max = 0;
		for (int bananas : piles) {
			if (max < bananas) {
				max = bananas;
			}
		}
		int left = 1; // for very high values of h
		int right = max;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			long hours = 0;

			for (int i = 0; i < n; i++) {
				hours += (piles[i] + mid - 1) / mid;// Math.ceil(piles[i] / mid)
			}
			if (hours <= h) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}

		return left;
	}

	private static int minEatingSpeedMid(int[] piles, int h) {
		int n = piles.length;
		int maxPiles = 0;
		for (int bananas : piles) {
			if (maxPiles < bananas) {
				maxPiles = bananas;
			}
		}
		int left = 1;
		int right = maxPiles;
		while (left < right) {
			int mid = left + (right - left) / 2;
			long hours = 0;
			for (int pile : piles) {
				hours += (pile + mid - 1) / mid;
//				if (hours > h) {//useful when we don't want to use long hours
//					break;
//				}
			}
			if (hours <= h) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		return left;
	}

}
