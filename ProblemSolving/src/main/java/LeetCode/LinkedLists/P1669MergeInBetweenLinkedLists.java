package LeetCode.LinkedLists;

/*
 * P1669. Merge In Between Linked Lists - Medium
 * 
 * You are given two linked lists: list1 and list2 of sizes n and m respectively.
 * 
 * Remove list1's nodes from the ath node to the bth node, and put list2 in their place.
 * 
 * The blue edges and nodes in the following figure indicate the result:
 * 
 * Build the result list and return its head.
 * 
 * Approach - Reference, Temporary variable, Node Traversal
 */
public class P1669MergeInBetweenLinkedLists {

	// Definition for singly-linked list.
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

		ListNode node6 = new ListNode(5);
		ListNode node5 = new ListNode(9, node6);
		ListNode node4 = new ListNode(6, node5);
		ListNode node3 = new ListNode(13, node4);
		ListNode node2 = new ListNode(1, node3);
		ListNode list1 = new ListNode(10, node2);

//		while(list1 != null) {
//			System.out.println("The node is " + list1.val);
//			list1 = list1.next;
//		}

		ListNode node8 = new ListNode(1000002);
		ListNode node7 = new ListNode(1000001, node8);
		ListNode list2 = new ListNode(1000000, node7);

//		while(list2 != null) {
//			System.out.println("The node is " + list2.val);
//			list2 = list2.next;
//		}

		int a = 3, b = 4;

		mergeInBetween(list1, a, b, list2);

		while (list1 != null) {
			System.out.println("The node is " + list1.val);
			list1 = list1.next;
		}

	}

	public static ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {

		ListNode listBeforeAthNode = null;
		ListNode bthNode = null;

		ListNode curr = list1;

		for (int i = 0; i <= b; i++) {
			if (i == a - 1) {
				listBeforeAthNode = list1;
			}
			if (i == b) {
				bthNode = list1;
			}
			list1 = list1.next;
		}

		ListNode curr2 = list2;

		while (list2.next != null) {
			list2 = list2.next;
		}

		list2.next = bthNode.next;

		listBeforeAthNode.next = curr2;

		return curr;
	}

}
