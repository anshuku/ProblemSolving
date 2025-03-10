package LeetCode.Strings;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P2379. Minimum Recolors to Get K Consecutive Black Blocks - Easy
 * 
 * You are given a 0-indexed string blocks of length n, where blocks[i] is 
 * either 'W' or 'B', representing the color of the ith block. The characters 
 * 'W' and 'B' denote the colors white and black, respectively.
 * 
 * You are also given an integer k, which is the desired number of consecutive black blocks.
 * 
 * In one operation, you can recolor a white block such that it becomes a black block.
 * 
 * Return the minimum number of operations needed such that there 
 * is at least one occurrence of k consecutive black blocks.
 * 
 * Approach - Sliding window
 */
public class P2379MinimumRecolorsGetKConsecutiveBlackBlocks {

	public static void main(String[] args) {
		String blocks = "WBBWWBBWBW";
		int k = 7;

//		String blocks = "WBWBBBW";
//		int k = 2;

		int minRecolorsArr = minimumRecolorsArr(blocks, k);
		System.out.printf("Array: The minimum recolurs for %d consecutive black blocks: " + minRecolorsArr, k);

		int minRecolorsBF = minimumRecolorsBF(blocks, k);
		System.out.printf("\nBrute Force: The minimum recolurs for %d consecutive black blocks: " + minRecolorsBF, k);

		int minRecolorsQueue = minimumRecolorsQueue(blocks, k);
		System.out.printf("\nQueue: The minimum recolurs for %d consecutive black blocks: " + minRecolorsQueue, k);
	}

	// Sliding window approach - Array
	// Time complexity - O(n)
	// Space complexity - O(1)
	private static int minimumRecolorsArr(String blocks, int k) {
		int n = blocks.length();
		char[] charArr = blocks.toCharArray();
		int whites = 0;
		for (int i = 0; i < k; i++) {
			if (charArr[i] == 'W') {
				whites++;
			}
		}
		int min = whites;
		for (int i = 0; i < n - k; i++) {
			if (charArr[i] == 'W') {
				whites--;
			}
			if (charArr[i + k] == 'W') {
				whites++;
			}
			min = Math.min(min, whites);
		}
		return min;
	}

	public static int minimumRecolorsBF(String blocks, int k) {
		int n = blocks.length();
		char[] charArr = blocks.toCharArray();
		int min = Integer.MAX_VALUE;
		for (int i = 0; i <= n - k; i++) {
			int curr = 0;
			for (int j = i; j < i + k; j++) {
				if (charArr[j] == 'W') {
					curr++;
				}
			}
			min = Math.min(min, curr);
		}
		return min;
	}

	// Sliding window approach - Queue
	// Time complexity - O(n)
	// Space complexity - O(k)
	private static int minimumRecolorsQueue(String blocks, int k) {
		int n = blocks.length();
		char[] charArr = blocks.toCharArray();
		Queue<Character> queue = new LinkedList<>();
		int whites = 0;
		for (int i = 0; i < k; i++) {
			if (charArr[i] == 'W') {
				whites++;
			}
			queue.add(charArr[i]);
		}
		int min = whites;
		for (int i = k; i < n; i++) {
			if (queue.poll() == 'W') {
				whites--;
			}
			if (charArr[i] == 'W') {
				whites++;
			}
			queue.add(charArr[i]);
			min = Math.min(min, whites);
		}
		return min;
	}

}
