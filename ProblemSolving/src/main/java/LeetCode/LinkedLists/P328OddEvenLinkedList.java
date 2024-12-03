package LeetCode.LinkedLists;

/*
 * P328. Odd Even Linked List - Medium
 * 
 * Given the head of a singly linked list, group all the nodes with odd indices together 
 * followed by the nodes with even indices, and return the reordered list.
 * 
 * The first node is considered odd, and the second node is even, and so on.
 * 
 * Note that the relative order inside both the even and odd groups should remain as it was in the input.
 * 
 * You must solve the problem in O(1) extra space complexity and O(n) time complexity.
 * 
 * 
 */
public class P328OddEvenLinkedList {

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
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);

		ListNode oddEvenNode = oddEvenList(head);

		System.out.println("The linked list after odd even order is - ");
		while (oddEvenNode != null) {
			System.out.print(oddEvenNode.val + "->");
			oddEvenNode = oddEvenNode.next;
		}
	}

	public static ListNode oddEvenList(ListNode head) {

		if (head == null || head.next == null || head.next.next == null) {
			return head;
		}

		ListNode odd = head;
		ListNode even = head.next;
		ListNode evenHead = even;

		// 1 2 3 4 5
		while (even != null && even.next != null) {
			// Ensure odd's pointer to next is updated first, then update even's next
			odd.next = even.next;// 1 3 4 5 | 3 5
			odd = odd.next;// 3 4 5 | 5
			even.next = odd.next;// 2 4 5 | 4 // Must come after moving odd pointer
			even = even.next;// 4 5 |

			// odd's pointer to next can be updated post even's next
//			odd.next = odd.next.next;// updates the head
//			odd = odd.next;// It doesn't updates the head but odd's pointer
//			even.next = even.next.next;// updates head's next
//			even = even.next;

//			odd.next = odd.next.next;
//			even.next = even.next.next;
//			odd = odd.next;
//			even = even.next;
		}
		odd.next = evenHead;

		return head;
	}

}
