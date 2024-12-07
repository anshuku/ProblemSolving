package LeetCode.Trees;

import java.util.HashMap;
import java.util.Map;

/*
 * P1372. Longest ZigZag Path in a Binary Tree - Medium
 * 
 * You are given the root of a binary tree.
 * A ZigZag path for a binary tree is defined as follow:
 * - Choose any node in the binary tree and a direction (right or left).
 * - If the current direction is right, move to the right child 
 *   of the current node; otherwise, move to the left child.
 * - Change the direction from right to left or from left to right.
 * - Repeat the second and third steps until you can't move in the tree.
 * 
 * Zigzag length is defined as the number of nodes visited - 1. (A single node has a length of 0).
 * Return the longest ZigZag path contained in that tree.
 * 
 * Approach - DFS
 */
public class P1372LongestZigZagPathBinaryTree {

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
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(3);
		root.right.right = new TreeNode(4);
		root.right.right.left = new TreeNode(5);
		root.right.right.right = new TreeNode(6);
		root.right.right.left.right = new TreeNode(7);
		root.right.right.left.right.right = new TreeNode(8);

		int maxDfs = longestZigZagDFS(root);

		System.out.println("DFS: The max zig zag path in the binary tree is - " + maxDfs);

		P1372LongestZigZagPathBinaryTree.maxZigZag = 0;

		int maxDfsFlag = longestZigZagDFSFlag(root);

		System.out.println("DFS Flag: The max zig zag path in the binary tree is - " + maxDfsFlag);

		P1372LongestZigZagPathBinaryTree.maxZigZag = 0;

		int maxWhile = longestZigZagWhile(root);

		System.out.println("While: The max zig zag path in the binary tree is - " + maxWhile);

	}

	private static int longestZigZagDFS(TreeNode root) {
		dfs(root, 0, 0);
		return maxZigZag;
	}

	private static void dfs(TreeNode root, int left, int right) {
		if (root == null) {
			return;
		}
		maxZigZag = Math.max(maxZigZag, Math.max(left, right));
		if (root.left != null) {
			dfs(root.left, right + 1, 0);
		}
		if (root.right != null) {
			dfs(root.right, 0, left + 1);
		}
	}

	// Time complexity - O(n), since all nodes are visited for n-1 edges
	// Space complexity - O(n), recursion stack can have at most n elements
	private static int longestZigZagDFSFlag(TreeNode root) {
		dfsFlag(root, true, 0);
//		dfsFlag(root, false, 0);//Not needed
		return maxZigZag;
	}

	private static void dfsFlag(TreeNode root, boolean goLeft, int length) {
		if (root == null) {
			return;
		}
		maxZigZag = Math.max(maxZigZag, length);
		if (goLeft) {
			dfsFlag(root.left, false, length + 1);
			dfsFlag(root.right, true, 1);
		} else {
			dfsFlag(root.left, false, 1);
			dfsFlag(root.right, true, length + 1);
		}
	}

	static int maxZigZag = 0;

	public static int longestZigZagWhile(TreeNode root) {
		if (root == null) {
			return 0;
		}
		maxZigZag(root, 1, 0);
		maxZigZag(root, 0, 0);
		longestZigZagWhile(root.left);
		longestZigZagWhile(root.right);
		return maxZigZag;
	}

	private static void maxZigZag(TreeNode root, int direction, int length) {
		TreeNode curr = root;
		while (curr != null) {
			if (direction == 0 && curr.left != null) {
				direction = 1;
				length++;
				curr = curr.left;
			} else if (direction == 1 && curr.right != null) {
				direction = 0;
				length++;
				curr = curr.right;
			} else {
				break;
			}
		}
		maxZigZag = Math.max(maxZigZag, length);
	}

}
