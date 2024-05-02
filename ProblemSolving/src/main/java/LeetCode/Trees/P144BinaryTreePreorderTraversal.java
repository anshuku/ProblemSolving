package LeetCode.Trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class P144BinaryTreePreorderTraversal {

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

		List<Integer> list = preOrderTraversal(root);

//		List<Integer> list = preOrderTraversalList(root);

//		List<Integer> list = preOrderTraversalListAddAll(root);

//		List<Integer> list = preOrderTraversalStack(root);

//		List<Integer> list = preOrderTraversalLinkedStack(root);

		System.out.println("Pre order traversal " + list);

	}

	private static List<Integer> preOrderTraversal(TreeNode node) {
		if (node != null) {
			list.add(node.val);
			preOrderTraversal(node.left);
			preOrderTraversal(node.right);
		}
		return list;
	}

	private static List<Integer> preOrderTraversalList(TreeNode node) {
		// List defined inside main is faster than static list at class level in case of
		// preOrderTraversalList()
		List<Integer> list = new ArrayList<>();
		traversal(node, list);
		return list;
	}

	private static void traversal(TreeNode node, List<Integer> list) {
		if (node == null) {
			return;
		}
		list.add(node.val);
		traversal(node.left, list);
		traversal(node.right, list);
	}

	// Most optimized in terms of time and space
	private static List<Integer> preOrderTraversalListAddAll(TreeNode node) {
		List<Integer> list = new ArrayList<>();
		// Breaks the recursion when node is null
		if (node == null) {
			return list;
		}
		list.add(node.val);
		// The list.addAll() can take list values from past recursive calls
		list.addAll(preOrderTraversalListAddAll(node.left));
		list.addAll(preOrderTraversalListAddAll(node.right));
		// Returns the node value list just before recursion end(node is null)
		return list;
	}

	private static List<Integer> preOrderTraversalStack(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		if (root == null) {
			return list;
		}
		Stack<TreeNode> stack = new Stack<>();
		stack.add(root);

		while (!stack.empty()) {
			TreeNode node = stack.pop();
			list.add(node.val);

			// The stack operation pushed node.right first so that node.left comes at top.
			// This obeys preorder traversal.
			if (node.right != null) {
				stack.push(node.right);
			}
			if (node.left != null) {
				stack.push(node.left);
			}
		}
		return list;
	}

	private static List<Integer> preOrderTraversalLinkedStack(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		// can use addFirst(), push() and pop() via LinkedList parent reference
		LinkedList<TreeNode> stack = new LinkedList<>();
		TreeNode currentNode = root;
		while (currentNode != null || !stack.isEmpty()) {
			if (currentNode != null) {
				list.add(currentNode.val);
				// addFirst or push
				stack.push(currentNode);
				currentNode = currentNode.left;
			} else {
				currentNode = stack.pop();
				currentNode = currentNode.right;
			}
		}
		return list;
	}

}
