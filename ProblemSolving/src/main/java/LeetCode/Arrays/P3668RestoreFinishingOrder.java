package LeetCode.Arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * P3668. Restore Finishing Order - Easy
 * 
 * You are given an integer array order of length n and an integer array friends.
 * > order contains every integer from 1 to n exactly once, representing 
 * the IDs of the participants of a race in their finishing order.
 * > friends contains the IDs of your friends in the race sorted in strictly 
 * increasing order. Each ID in friends is guaranteed to appear in the order array.
 * 
 * Return an array containing your friends' IDs in their finishing order.
 * 
 * Approach - HashMap, Iteration
 */
public class P3668RestoreFinishingOrder {

	public static void main(String[] args) {
		int[] order = { 3, 1, 2, 5, 4 };
		int[] friends = { 1, 3, 4 };

		int[] finish = recoverOrderArray(order, friends);
		System.out.println("Array: The array containing friends IDs in finishing order: " + Arrays.toString(finish));

		int[] finishSet = recoverOrderSet(order, friends);
		System.out.println("TSet: he array containing friends IDs in finishing order: " + Arrays.toString(finishSet));
	}

	// We iterate through order and check if order[i] is present in friends, if it's
	// present we add it to result then break the inner loop and check for next
	// order.
	// Time complexity - O(n*m)
	// Space complexity - O(1)
	private static int[] recoverOrderArray(int[] order, int[] friends) {
		int[] finish = new int[friends.length];
		int i = 0;
		for (int o : order) {
			for (int f : friends) {
				if (o == f) {
					finish[i++] = o;
					break;
				}
			}
		}
		return finish;
	}

	// Time complexity - O(n + m)
	// Space complexity - O(n)
	public static int[] recoverOrderSet(int[] order, int[] friends) {
		int[] finish = new int[friends.length];
		Set<Integer> set = new HashSet<>();
		for (int num : friends) {
			set.add(num);
		}
		int j = 0;
		for (int i = 0; i < order.length; i++) {
			if (set.contains(order[i])) {
				finish[j++] = order[i];
			}
		}
		return finish;
	}
}
