package LeetCode.LinkedLists.LRUCache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

	static class Node {
		int key, value;

		Node next, prev;

		Node(int key, int value) {
			this.key = key;
			this.value = value;
		}

		Node() {
			this(0, 0);
		}
	}

	Map<Integer, Node> map;
	int count, capacity;

	Node head, tail;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		map = new HashMap<>();

		head = new Node();
		tail = new Node();

		// Head's next should be tail and tail's prev should be head.
		head.next = tail;
		tail.prev = head;

	}

	public void put(int key, int value) {
		Node node = map.get(key);

		if (node == null) {
			node = new Node(key, value);
			map.put(key, node);
			add(node);
			count++;

			// After inserting the node, remove the least recently used node
			if (count > capacity) {
				// Least recently used node is in tail
				Node currTail = tail.prev;
				// Remove the entry from map based on key.
				map.remove(currTail.key);
				remove(currTail);
				--count;
			}
		} else {
			node.value = value;
			// Node need to update the map via a put, since value is updated
			// remove and insert the current node to make it most recent
			update(node);
		}
	}

	public int get(int key) {
		Node node = map.get(key);
		if (node == null) {
			return -1;
		}
		update(node);
		return node.value;
	}

	// Update - remove the node and then insert
	private void update(Node node) {
		remove(node);
		add(node);
	}

	private void add(Node node) {
		Node curr = head.next;
		head.next = node;
		node.next = curr;
		node.prev = head;
		curr.prev = node;
	}

	private void remove(Node node) {
		Node after = node.next;
		Node before = node.prev;

		before.next = after;
		after.prev = before;
	}

	public static void main(String[] args) {
		LRUCache lru = new LRUCache(2);
		lru.put(1, 1);
		lru.put(2, 2);

		int val = lru.get(1);

		lru.put(3, 3);
		lru.put(1, 10);
	}

}
