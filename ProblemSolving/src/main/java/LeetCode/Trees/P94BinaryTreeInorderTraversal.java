package LeetCode.Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import LeetCode.Trees.P144BinaryTreePreorderTraversal.TreeNode;

public class P94BinaryTreeInorderTraversal {

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

	static List<Integer> list = new ArrayList<>();

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);

		List<Integer> list = inOrderTraversal(root);

//		List<Integer> list = inOrderTraversalList(root);

//		List<Integer> list = inOrderTraversalAddAll(root);

//		List<Integer> list = inOrderTraversalStack(root);

		System.out.println("In order traversal " + list);
	}

	public static List<Integer> inOrderTraversal(TreeNode node) {
		if (node != null) {
			inOrderTraversal(node.left);
			list.add(node.val);
			inOrderTraversal(node.right);
		}
		return list;
	}

	private static List<Integer> inOrderTraversalList(TreeNode node) {

		List<Integer> list = new ArrayList<>();
		traversal(node, list);
		return list;
	}

	private static void traversal(TreeNode node, List<Integer> list) {
		if (node == null) {
			return;
		}
		traversal(node.left, list);
		list.add(node.val);
		traversal(node.right, list);
	}

	private static List<Integer> inOrderTraversalAddAll(TreeNode node) {
		List<Integer> list = new ArrayList<>();
		// Breaks the recursion when node is null
		if (node == null) {
			return list;
		}
		// The list.addAll() can take list values from past recursive calls
		list.addAll(inOrderTraversalAddAll(node.left));
		list.add(node.val);
		list.addAll(inOrderTraversalAddAll(node.right));
		// Returns the node value list just before recursion end(node is null)
		return list;
	}

	private static List<Integer> inOrderTraversalStack(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		Stack<TreeNode> stack = new Stack<>();
		TreeNode currentNode = root;
		while (currentNode != null || !stack.empty()) {
			while (currentNode != null) {
				stack.push(currentNode);
				currentNode = currentNode.left;
			}
			currentNode = stack.pop();
			list.add(currentNode.val);
			currentNode = currentNode.right;
		}

		return list;
	}

}
