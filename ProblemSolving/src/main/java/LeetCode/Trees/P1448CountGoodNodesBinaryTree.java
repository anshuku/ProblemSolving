package LeetCode.Trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import LeetCode.Trees.P129SumRootToLeafNumbers.TreeNode;

/*
 * P1448. Count Good Nodes in Binary Tree - Medium
 * 
 * Given a binary tree root, a node X in the tree is named good if in the path 
 * from root to X there are no nodes with a value greater than X.
 * 
 * Return the number of good nodes in the binary tree.
 * 
 * Approach - DFS
 */
public class P1448CountGoodNodesBinaryTree {

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

//		TreeNode root = new TreeNode(3);
//		root.left = new TreeNode(1);
//		root.right = new TreeNode(4);
//		root.left.left = new TreeNode(3);
//		root.right.left = new TreeNode(1);
//		root.right.right = new TreeNode(5);

		TreeNode root = new TreeNode(2);
		root.right = new TreeNode(4);
		root.right.left = new TreeNode(10);
		root.right.right = new TreeNode(8);
		root.right.right.left = new TreeNode(4);

		int goodNodesCount = goodNodesCount(root);

		System.out.println("Count: The number of good nodes is - " + goodNodesCount);

		int goodNodesResult = goodNodesResult(root, -10000);

		System.out.println("Max: The number of good nodes is - " + goodNodesResult);

		P1448CountGoodNodesBinaryTree.count = 0;

		int goodNodesList = goodNodesList(root);

		System.out.println("List: The number of good nodes is - " + goodNodesList);

	}

	private static int goodNodesCount(TreeNode root) {
		nodesCount(root, -10000);
		return count;
	}

	private static void nodesCount(TreeNode root, int max) {
		if (root == null) {
			return;
		}
		if (root.val >= max) {
			max = root.val;
			count++;
		}
		nodesCount(root.left, max);
		nodesCount(root.right, max);
	}

	private static int goodNodesResult(TreeNode root, int max) {
		if (root == null) {
			return 0;
		}
		int result = root.val >= max ? 1 : 0;
		// MAx is applicable only in node's leaf
		result += goodNodesResult(root.left, Math.max(max, root.val));
		result += goodNodesResult(root.right, Math.max(max, root.val));
		return result;
	}

	static int count = 0;

	public static int goodNodesList(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		countNodes(root, list);
		return count;
	}

	private static void countNodes(TreeNode root, List<Integer> list) {
		if (root == null) {
			return;
		}
		int max = Integer.MIN_VALUE;
		for (int num : list) {
			if (max < num) {
				max = num;
			}
		}
		if (root.val >= max) {
			count++;
		}
		list.add(root.val);
		countNodes(root.left, list);
		countNodes(root.right, list);
		list.remove(list.size() - 1);
	}

}
