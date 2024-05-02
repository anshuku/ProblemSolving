package LeetCode.Trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class P145BinaryTreePostorderTraversal {

	static List<Integer> list = new ArrayList<>();

	static class TreeNode {

		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {

		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}

	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);

//		List<Integer> list = postOrderTraversal(root);

//		List<Integer> list = postOrderTraversalList(root);

//		List<Integer> list = postOrderTraversalAddAll(root);

//		List<Integer> list = postOrderTraversalStack(root);

		List<Integer> list = postOrderTraversalLinkedStack(root);

		System.out.println("Post order traversal " + list);
	}

	private static List<Integer> postOrderTraversal(TreeNode node) {
		if (node != null) {
			postOrderTraversal(node.left);
			postOrderTraversal(node.right);
			list.add(node.val);
		}
		return list;
	}

	private static List<Integer> postOrderTraversalList(TreeNode node) {
		List<Integer> list = new ArrayList<>();
		traversal(node, list);
		return list;
	}

	private static void traversal(TreeNode node, List<Integer> list) {

		if (node == null) {
			return;
		}
		traversal(node.left, list);
		traversal(node.right, list);
		list.add(node.val);

	}

	private static List<Integer> postOrderTraversalAddAll(TreeNode node) {
		List<Integer> list = new ArrayList<>();
		// Breaks the recursion when node is null
		if (node == null) {
			return list;
		}
		// The list.addAll() can take list values from past recursive calls
		list.addAll(postOrderTraversalAddAll(node.left));
		list.addAll(postOrderTraversalAddAll(node.right));
		list.add(node.val);
		// Returns the node value list just before recursion end(node is null)
		return list;
	}

	private static List<Integer> postOrderTraversalStack(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		if (root == null) {
			return list;
		}
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		while (!stack.empty()) {
			TreeNode node = stack.pop();
			list.add(node.val);
			if (node.left != null) {
				stack.push(node.left);
			}
			if (node.right != null) {
				stack.push(node.right);
			}
		}
		Collections.reverse(list);
		return list;
	}

	private static List<Integer> postOrderTraversalLinkedStack(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		// can use addFirst(), push() and pop() via LinkedList parent reference
		LinkedList<TreeNode> stack = new LinkedList<>();
		TreeNode currentNode = root;
		while (currentNode != null || !stack.isEmpty()) {
			if (currentNode != null) {
				list.add(currentNode.val);
				// addFirst or push
				stack.addFirst(currentNode);
				currentNode = currentNode.right;
			} else {
				currentNode = stack.pop();
				currentNode = currentNode.left;
			}
		}
		Collections.reverse(list);
		return list;
	}

}
