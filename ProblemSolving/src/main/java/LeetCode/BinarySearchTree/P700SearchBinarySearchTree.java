package LeetCode.BinarySearchTree;

/*
 * P700. Search in a Binary Search Tree - Easy
 * 
 * You are given the root of a binary search tree (BST) and an integer val.
 * Find the node in the BST that the node's value equals val and return the 
 * subtree rooted with that node. If such a node does not exist, return null.
 * 
 * Approach - Iterative; Recursive
 */
public class P700SearchBinarySearchTree {

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
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.right = new TreeNode(7);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);

		TreeNode nodeIterative = searchBSTIterative(root, 2);
		System.out.println("Iterative: The node found - " + nodeIterative.val);

		TreeNode nodeRecursive = searchBSTRecursive(root, 2);
		System.out.println("Iterative: The node found - " + nodeRecursive.val);
	}

	public static TreeNode searchBSTIterative(TreeNode root, int val) {
		while (root != null) {
			if (root.val == val) {
				return root;
			} else if (root.val > val) {
				root = root.left;
			} else {
				root = root.right;
			}
		}
		return root;
	}

	private static TreeNode searchBSTRecursive(TreeNode root, int val) {
		if (root == null || root.val == val) {
			return root;
		}
		if (val < root.val) {
			return searchBSTRecursive(root.left, val);
		}
		return searchBSTRecursive(root.right, val);
	}
}
