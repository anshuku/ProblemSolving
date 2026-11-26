package LeetCode.LinkedLists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/*
 * P1019. Next Greater Node In Linked List - Medium
 * 
 * You are given the head of a linked list with n nodes.
 * 
 * For each node in the list, find the value of the next greater node. That is, for each node, 
 * find the value of the first node that is next to it and has a strictly larger value than it.
 * 
 * Return an integer array answer where answer[i] is the value of the next greater node of the 
 * ith node (1-indexed). If the ith node does not have a next greater node, set answer[i] = 0.
 * 
 * Approach - Monotonic Stack
 */
public class P1019NextGreaterNodeInLinkedList {

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int val) {
			this.val = val;
		}
	}

	public static void main(String[] args) {
//		ListNode head = new ListNode(2);
//		head.next = new ListNode(1);
//		head.next.next = new ListNode(5);

		ListNode head = new ListNode(2);
		head.next = new ListNode(7);
		head.next.next = new ListNode(4);
		head.next.next.next = new ListNode(3);
		head.next.next.next.next = new ListNode(5);

		Integer[] nextLargerMStack1Pass = nextLargerNodesMStack1Pass(head);
		System.out.println("Monotonic Stack 1 Pass: The next larger node for the nodes are: "
				+ Arrays.toString(nextLargerMStack1Pass));

		int[] nextLargerMStack2Pass = nextLargerNodesMStack2Pass(head);
		System.out.println("Monotonic Stack 2 Pass: The next larger node for the nodes are: "
				+ Arrays.toString(nextLargerMStack2Pass));
	}

	// Monotonic Stack, 1 Pass - Non-increasing
	// We reduce the number of iterations from the previous appraoch. Earlier we
	// stored node values from the linked list head into list in the 1st iteration
	// and then found the next greater value in the 2nd iteration. Here, we only use
	// 1 iteration by recording the value from the head and updating stack in the
	// same iteration step.
	// We don't know the size of the linked list head, thus we can't initialize an
	// array of equal size. So, we start with an empty list next and increment its
	// size during the iteration.
	// We don't use an array to store all values from head, so we should store both
	// the index and the value of each node to stack. Then we can get the value of
	// each node from the index without referring the original head or an array.
	// Time complexity - O(n)
	// Space complexity - O(n)
	private static Integer[] nextLargerNodesMStack1Pass(ListNode head) {
		List<Integer> next = new ArrayList<>();
		Stack<int[]> stack = new Stack<>();

		// We use an integer index for representing the index.
		int index = 0;

		while (head != null) {
			// The default next larger value for head.val is 0.
			next.add(0);

			while (!stack.isEmpty() && stack.peek()[1] < head.val) {
				int i = stack.pop()[0];
				next.set(i, head.val);
			}

			// Add both the index and the value to stack.
			stack.push(new int[] { index, head.val });
			index++;
			head = head.next;
		}

//		return next.stream().mapToInt(i -> i).toArray();
		return next.toArray(new Integer[0]);
	}

	// Monotonic Stack - Non-increasing
	// We copy the values from the nodes present in the linked list to an array.
	// Time complexity - O(n)
	// Space complexity - O(n)
	public static int[] nextLargerNodesMStack2Pass(ListNode head) {
		List<Integer> list = new ArrayList<>();

		while (head != null) {
			list.add(head.val);
			head = head.next;
		}

		int n = list.size();

		int[] next = new int[n];

		// We want to set the next greater value for each index, so we push the index i
		// instead of the value values[i] to the stack. When we pop an index from the
		// stack, we can directly update the next greater value for this index.
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < n; i++) {
			while (!stack.isEmpty() && list.get(stack.peek()) < list.get(i)) {
				next[stack.pop()] = list.get(i);
			}
			stack.push(i);
		}
		return next;
	}

}
