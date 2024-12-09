package LeetCode.BinarySearchTree;

import LeetCode.BinarySearchTree.P700SearchBinarySearchTree.TreeNode;

/*
 * P450. Delete Node in a BST - Medium
 * 
 * Given a root node reference of a BST and a key, delete the node with the 
 * given key in the BST. Return the root node reference (possibly updated) of the BST.
 * 
 * Basically, the deletion can be divided into two stages:
 * - Search for a node to remove.
 * - If the node is found, delete the node.
 * 
 * Approach - Recursive; Iterative
 */
public class P450DeleteNodeBST {

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

		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(2);
		root.left.right = new TreeNode(4);
		root.right.right = new TreeNode(7);

		printInOrder(root);
		System.out.println();

		int key = 5;
		TreeNode deleteIterative = deleteNodeIterative(root, key);

		System.out.println("Iterative delete:");
		printInOrder(root);
		System.out.println();

		key = 4;
		TreeNode deleteRecursiveOpt = deleteNodeRecursiveOpt(root, key);

		System.out.println("Recursive optimized delete:");
		printInOrder(root);
		System.out.println();

		key = 3;
		TreeNode deleteRecursive = deleteNodeRecursive(root, key);

		System.out.println("Recursive delete:");
		printInOrder(root);
		System.out.println();
	}

	private static TreeNode deleteNodeIterative(TreeNode root, int key) {
		if (root == null) {
			return root;
		}
		TreeNode parent = null;
		TreeNode curr = root;
		while (curr != null && curr.val != key) {
			parent = curr;
			if (key < curr.val) {
				curr = curr.left;
			} else {
				curr = curr.right;
			}
		}
		if (curr == null) {
			return root;
		}
		if (curr.left == null || curr.right == null) {
			TreeNode newNode;
			if (curr.left == null) {
				newNode = curr.right;
			} else {
				newNode = curr.left;
			}
			if (parent == null) {
				return newNode;
			}
			
			if (parent.left == curr) {
				parent.left = newNode;
			} else {
				parent.right = newNode;
			}
		} else {
			TreeNode p = null;
			TreeNode succ = curr.right;
			while (succ.left != null) {
				p = succ;
				succ = succ.left;
			}
			if (p != null) {
				p.left = succ.right;
			} else {
				curr.right = succ.right;
			}
			curr.val = succ.val;
		}
		return root;
	}

	private static TreeNode deleteNodeRecursiveOpt(TreeNode root, int key) {
		if (root == null) {
			return root;
		}
		if (key < root.val) {
			root.left = deleteNodeRecursiveOpt(root.left, key);
			return root;
		}
		if (key > root.val) {
			root.right = deleteNodeRecursiveOpt(root.right, key);
			return root;
		}
		if (root.left == null) {
			return root.right;
		}
		if (root.right == null) {
			return root.left;
		}
		TreeNode parent = root;
		TreeNode succ = root.right;
		while (succ.left != null) {
			parent = succ;
			succ = succ.left;
		}
		if (parent.left == succ) {
			parent.left = succ.right;
		} else {
			parent.right = succ.right;
		}
		root.val = succ.val;
		return root;
	}

	private static void printInOrder(TreeNode root) {
		if (root != null) {
			printInOrder(root.left);
			System.out.print(root.val + "->");
			printInOrder(root.right);
		}
	}

	public static TreeNode deleteNodeRecursive(TreeNode root, int key) {
		if (root == null) {
			return root;
		}
		if (key < root.val) {
			root.left = deleteNodeRecursive(root.left, key);
		} else if (key > root.val) {
			root.right = deleteNodeRecursive(root.right, key);
		} else {
			if (root.left == null) {
				return root.right;
			}
			if (root.right == null) {
				return root.left;
			}
			TreeNode successor = getSuccessor(root);
			root.val = successor.val;
			root.right = deleteNodeRecursive(root.right, successor.val);
		}
		return root;
	}

	private static TreeNode getSuccessor(TreeNode root) {
		TreeNode curr = root.right;
		while (curr.left != null) {
			curr = curr.left;
		}
		return curr;
	}

}
