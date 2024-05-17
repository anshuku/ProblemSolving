package LeetCode.Arrays;

/*
 * Given an array of integers arr, and three integers a, b and c. You need to find the number of good triplets.
 * 
 * A triplet (arr[i], arr[j], arr[k]) is good if the following conditions are true:
 * 
 * 0 <= i < j < k < arr.length
 * |arr[i] - arr[j]| <= a
 * |arr[j] - arr[k]| <= b
 * |arr[i] - arr[k]| <= c
 * Where |x| denotes the absolute value of x.
 * Return the number of good triplets.
 * 
 * Here, brute force method is applicable.
 */
public class P1534CountGoodTriplets {

	public static void main(String[] args) {
		int[] arr = { 3, 0, 1, 1, 9, 7 };
		int a = 7;
		int b = 2;
		int c = 3;

		P1534CountGoodTriplets pct = new P1534CountGoodTriplets();
		int count = pct.countGoodTriplets(arr, a, b, c);
		System.out.println("The count of good triplets " + count);
	}

	public int countGoodTriplets(int[] arr, int a, int b, int c) {
		int n = arr.length;
		int count = 0;
		for (int i = 0; i < n - 2; i++) {
			for (int j = i + 1; j < n - 1; j++) {
				if (Math.abs(arr[i] - arr[j]) <= a) {
					for (int k = j + 1; k < n; k++) {
						if (Math.abs(arr[i] - arr[k]) <= b && Math.abs(arr[j] - arr[k]) <= c) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

}
