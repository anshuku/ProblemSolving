package LeetCode.LinkedLists;

import java.util.ArrayList;
import java.util.List;

/*
 * P2130. Maximum Twin Sum of a Linked List - Medium
 * 
 * In a linked list of size n, where n is even, the ith node (0-indexed) of the 
 * linked list is known as the twin of the (n-1-i)th node, if 0 <= i <= (n / 2) - 1.
 * - For example, if n = 4, then node 0 is the twin of node 3, and node 1 is 
 *   the twin of node 2. These are the only nodes with twins for n = 4.
 * 
 * The twin sum is defined as the sum of a node and its twin.
 * 
 * Given the head of a linked list with even length, return the maximum twin sum of the linked list.
 * 
 * Approach - List; LinkedList middle, reverse
 * 
 * While using LinkedList middle and reverse logic
 * Either reverse the first half while finding the middle node
 * Or First find the middle node then reverse the second half.
 */
public class P2130MaximumTwinSumLinkedList {

	static class ListNode {
		int val;

		ListNode next;

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}

	public static void main(String[] args) {

		ListNode head = new ListNode(1);
		head.next = new ListNode(3);
		head.next.next = new ListNode(4);
		head.next.next.next = new ListNode(7);

		int pairSumReverse1stHalf = pairSumReverseFirstHalf(head);
		System.out.println("LL Reverse 1st Half: The max pair sum is - " + pairSumReverse1stHalf);

//		int pairSumReverse2ndHalf = pairSumReverseSecondHalf(head);
//		System.out.println("LL Reverse 2nd Half: The max pair sum is - " + pairSumReverse2ndHalf);
//
//		int pairSumList = pairSumList(head);
//		System.out.println("List: The max pair sum is - " + pairSumList);
	}

	private static int pairSumReverseFirstHalf(ListNode head) {
		ListNode fast = head;
		ListNode slow = head;
		ListNode next = null;
		ListNode prev = null;
		while (fast != null) {// && fast.next != null not needed, since even nodes
			fast = fast.next.next;
			next = slow.next;
			slow.next = prev;
			prev = slow;
			slow = next;
		}
		int max = 0;
		while (slow != null) {// or prev != null, both are equally divided
			max = Math.max(max, slow.val + prev.val);
			slow = slow.next;
			prev = prev.next;
		}
		return max;
	}

	private static int pairSumReverseSecondHalf(ListNode head) {
		ListNode fast = head;
		ListNode slow = head;
		ListNode before = null;
		while (fast != null) {// && fast.next != null not needed, since even nodes
			before = slow;
			slow = slow.next;
			fast = fast.next.next;
		}
		ListNode curr = slow;
		ListNode prev = null;
		ListNode next = null;
		while (curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		before.next = prev;
		int max = 0;
		while (prev != null) {
			max = Math.max(max, head.val + prev.val);
			head = head.next;
			prev = prev.next;
		}
		return max;
	}

	public static int pairSumList(ListNode head) {
		List<Integer> list = new ArrayList<>();
		while (head != null) {
			list.add(head.val);
			head = head.next;
		}
		int left = 0, right = list.size() - 1;
		int max = 0;
		while (left < right) {
			max = Math.max(max, list.get(left) + list.get(right));
			left++;
			right--;
		}
		return max;
	}

}
