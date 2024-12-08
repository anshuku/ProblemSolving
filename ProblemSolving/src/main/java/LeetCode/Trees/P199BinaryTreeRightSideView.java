package LeetCode.Trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * P199. Binary Tree Right Side View - Medium
 * 
 * Given the root of a binary tree, imagine yourself standing on the right side of it, 
 * return the values of the nodes you can see ordered from top to bottom.
 * 
 * Approach - BFS and DFS
 * 
 * list.set(index, value) method throws Exception if there is no value present already.
 */
public class P199BinaryTreeRightSideView {

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
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(4);

		List<Integer> resultDfs = rightSideViewDfs(root);

		System.out.println("DFS: Binary Tree right side view is - " + resultDfs);

		List<Integer> resultBfs = rightSideViewBfs(root);

		System.out.println("BFS: Binary Tree right side view is - " + resultBfs);
	}

	private static List<Integer> rightSideViewDfs(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		if (root == null) {
			return result;
		}
		dfs(root, result, 0);
		return result;
	}

	private static void dfs(TreeNode root, List<Integer> result, int level) {
		if (root == null) {
			return;
		}
		// If the level is visited for first time, add the node's value
		if (level == result.size()) {
			result.add(root.val);
		}
		// Visit the right subtree first then left subtree for right side view
		// Visit the left subtree first then right subtree for left side view
		dfs(root.left, result, level + 1);
		dfs(root.right, result, level + 1);
	}

	public static List<Integer> rightSideViewBfs(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		if (root == null) {
			return result;
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				TreeNode node = queue.poll();
				// This is the last and rightmost node at a level
				if (size == 0) {
					result.add(node.val);
				}
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}
		}
		return result;

//		while (!queue.isEmpty()) {
//			List<Integer> list = new ArrayList<>();
//			int size = queue.size();
//			while (size-- > 0) {
//				TreeNode node = queue.poll();
//				list.add(node.val);
//				if (node.left != null) {
//					queue.add(node.left);
//				}
//				if (node.right != null) {
//					queue.add(node.right);
//				}
//			}
//			result.add(list.get(list.size() - 1));
//		}
//		return result;

	}
}
