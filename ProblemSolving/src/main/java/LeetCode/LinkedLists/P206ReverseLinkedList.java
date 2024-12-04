package LeetCode.LinkedLists;

import java.util.ArrayList;
import java.util.List;

/*
 * 206. Reverse Linked List - Easy
 * 
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 * 
 * Approach - Prev and Next pointer; Recursion and temporary node
 */
public class P206ReverseLinkedList {

	static class ListNode {
		int val;
		ListNode next;

		ListNode() {

		}

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
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);

//		ListNode result = reverseLinkedList(head);

		ListNode result = reverseLinkedListRecursive(head);

//		ListNode result = reverseLinkedListIterative(head);

		while (result != null) {
			System.out.print(result.val + "->");
			result = result.next;
		}
	}

	private static ListNode reverseLinkedListIterative(ListNode head) {

		ListNode curr = head; // 1 2 3 4 5
		ListNode next = null;
		ListNode prev = null;
		while (curr != null) {// n cn p c
			next = curr.next; // 2 3 4 5 -> 3 4 5 -> 4 5 -> 5 ->
			curr.next = prev; // 1 -> 2 1 -> 3 2 1 -> 4 3 2 1 -> 5 4 3 2 1
			prev = curr; // 1 -> 2 1 -> 3 2 1 -> 4 3 2 1 -> 5 4 3 2 1
			curr = next; // 2 3 4 5 -> 3 4 5 -> 4 5 -> 5 ->

		}
		return prev;
	}

	private static ListNode reverseLinkedListRecursive(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode temp = reverseLinkedListRecursive(head.next);

		head.next.next = head;

		head.next = null;

		return temp;
	}

	private static ListNode reverseLinkedList(ListNode head) {
		ListNode node = head;
		int counter = 0;
		List<ListNode> nodes = new ArrayList<>();
		while (node.next != null) {
			counter++;
			nodes.add(new ListNode(node.val));
			node = node.next;
		}
		System.out.println("The value of node is " + node.val + " counter is " + counter);
		System.out.println("size of nodes is " + nodes.size());
		ListNode result = node;
		for (int i = counter - 1; i >= 0; i--) {
			node.next = nodes.get(i);
			node = node.next;
		}
		return result;
	}

}
