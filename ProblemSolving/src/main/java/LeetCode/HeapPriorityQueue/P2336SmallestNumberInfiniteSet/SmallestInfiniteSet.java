package LeetCode.HeapPriorityQueue.P2336SmallestNumberInfiniteSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/*
 * P2336. Smallest Number in Infinite Set - Medium
 * 
 * You have a set which contains all positive integers [1, 2, 3, 4, 5, ...].
 * Implement the SmallestInfiniteSet class:
 * 
 * - SmallestInfiniteSet() Initializes the SmallestInfiniteSet object to contain all positive integers.
 * 
 * - int popSmallest() Removes and returns the smallest integer contained in the infinite set.
 * 
 * - void addBack(int num) Adds a positive integer num back into the infinite set, 
 *   if it is not already in the infinite set.
 * 
 * Approach - Min Heap
 * 
 * Min Heap - new PriorityQueue<Integer>((a, b) -> a - b)
 */
public class SmallestInfiniteSet {

	int currentSmallest;
	PriorityQueue<Integer> priorityQueue;
	Set<Integer> set;// Using set is optional, can use queue.contains() as well

	public SmallestInfiniteSet() {
		currentSmallest = 1;
		// Min heap
		priorityQueue = new PriorityQueue<Integer>((a, b) -> a - b);
		set = new HashSet<Integer>();
	}

	public int popSmallest() {
		int result = this.currentSmallest;
		if (!priorityQueue.isEmpty()) {
			result = priorityQueue.poll();
			set.remove(result);
		} else {
			currentSmallest++;
		}
		return result;
	}

	public void addBack(int num) {
		if (currentSmallest <= num || set.contains(num)) {
			return;
		}
		priorityQueue.offer(num);
		set.add(num);
	}

	public static void main(String[] args) {
		SmallestInfiniteSet sni = new SmallestInfiniteSet();
//		System.out.println(Arrays.toString(sni.heap));
		sni.addBack(2);
		int smallest1 = sni.popSmallest();
		int smallest2 = sni.popSmallest();
		int smallest3 = sni.popSmallest();
		sni.addBack(1);
		int smallest4 = sni.popSmallest();
		int smallest5 = sni.popSmallest();
		int smallest6 = sni.popSmallest();

		System.out.println("The smallest values are: " + smallest1 + " " + smallest2 + " " + smallest3 + " " + smallest4
				+ " " + smallest5 + " " + smallest6);

	}

}
