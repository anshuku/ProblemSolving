package LeetCode.LinkedLists;

/*
 * P2095. Delete the Middle Node of a Linked List - Medium
 * You are given the head of a linked list. Delete the middle node, 
 * and return the head of the modified linked list.
 * 
 * The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start 
 * using 0-based indexing, where ⌊x⌋ denotes the largest integer less than or equal to x.
 * 
 * For n = 1, 2, 3, 4, and 5, the middle nodes are 0, 1, 1, 2, and 2, respectively.
 * 
 * Approach - Slow and Fast pointers, Counting nodes
 * 
 * fast != null && fast.next != null:
 * In even number of nodes fast will become null when slow reaches mid
 * In odd number of nodes fast.next will become null(fast - last node) when slow reaches mid
 */
public class P2095DeleteMiddleNodeLinkedList {

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
//		ListNode head = new ListNode(1);
//		head.next = new ListNode(3);
//		head.next.next = new ListNode(4);
//		head.next.next.next = new ListNode(7);
//		head.next.next.next.next = new ListNode(1);
//		head.next.next.next.next.next = new ListNode(2);
//		head.next.next.next.next.next.next = new ListNode(6);

//		ListNode head = new ListNode(1);

//		ListNode head = new ListNode(1);
//		head.next = new ListNode(3);

		ListNode head = new ListNode(1);
		head.next = new ListNode(3);
		head.next.next = new ListNode(4);
		head.next.next.next = new ListNode(7);

//		ListNode nodeNIP = deleteMiddleNotInPlace(head);
//
//		System.out.println("Not In Place: The linked list after deleting middle node is - ");
//		while (nodeNIP != null) {
//			System.out.print(nodeNIP.val + "->");
//			nodeNIP = nodeNIP.next;
//		}
//		System.out.println();

		ListNode node = deleteMiddle(head);

		System.out.println("The linked list after deleting middle node is - ");
		while (node != null) {
			System.out.print(node.val + "->");
			node = node.next;
		}
		System.out.println();

		ListNode nodeCounter = deleteMiddleCounter(head);

		System.out.println("Counter: The linked list after deleting middle node is - ");
		while (nodeCounter != null) {
			System.out.print(nodeCounter.val + "->");
			nodeCounter = nodeCounter.next;
		}

	}

	private static ListNode deleteMiddleNotInPlace(ListNode head) {
		if (head.next == null) {
			return null;
		}
		if (head.next.next == null) {
			head.next = null;
			return head;
		}
		ListNode fast = head;
		ListNode slow = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		slow.val = slow.next.val;
		slow.next = slow.next.next;
		return head;
	}

	public static ListNode deleteMiddle(ListNode head) {// 1 3 4 7 1 2 6 | 1 3 4 7
		// for node with only one node
		if (head == null || head.next == null) {
			return head.next;
		}

		ListNode fast = head;
		ListNode slow = head;
		ListNode before = null;

		while (fast != null && fast.next != null) {
			before = slow;
			slow = slow.next;
			fast = fast.next.next;
		}
		before.next = slow.next;

		return head;
	}

	private static ListNode deleteMiddleCounter(ListNode head) {
		if (head == null || head.next == null) {
			return null;
		}
		ListNode curr = head;
		int total = 0;
		while (curr != null) {
			total++;
			curr = curr.next;
		}

		curr = head;
		ListNode temp = null;
		int mid = total / 2;
		int i = 0;
		while (curr != null) {
			if (i == mid) {
				temp.next = curr.next;
			} else {
				temp = curr;
				curr = curr.next;
			}
			i++;
		}
		return head;
	}

}
