package LeetCode.Trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * Given the root of a binary tree, return the level order traversal of its nodes' values. 
 * (i.e., from left to right, level by level).
 */
public class P102BinaryTreeLevelOrderTraversal {

	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		public TreeNode() {
			super();
		}

		public TreeNode(int val) {
			super();
			this.val = val;
		}

		public TreeNode(int val, TreeNode left, TreeNode right) {
			super();
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
		root.right.left = new TreeNode(6);

		List<List<Integer>> list = levelOrderTraversal(root);

//		List<List<Integer>> list = levelOrderTraversalheight(root);

		System.out.print("The Level order traversal for the Binary tree is " + list);

	}

	// BFS Via queue
	private static List<List<Integer>> levelOrderTraversal(TreeNode root) {
		List<List<Integer>> list = new ArrayList<>();
		if (root == null) {
			return list;
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		// To avoid creating the current list again and again/reuse it.
		List<Integer> currList = new ArrayList<>();
		while (!queue.isEmpty()) {
			int size = queue.size();// 1, 2, 4
			while (size-- > 0) {
				// Reduces the size of queue
				TreeNode node = queue.poll();
				currList.add(node.val);
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}
			// new ArrayList as the old list is cleared and reference clearance persists.
			list.add(new ArrayList<>(currList));
			currList.clear();
		}
		return list;
	}

	// DFS: Uses height and recursion for level order traversal list
	private static List<List<Integer>> levelOrderTraversalheight(TreeNode root) {

		List<List<Integer>> list = new ArrayList<>();
		levelOrderTraversalRecursive(list, 0, root);
		return list;
	}

	private static void levelOrderTraversalRecursive(List<List<Integer>> list, int height, TreeNode node) {
		if (node == null) {
			return;
		}
		if (list.size() == height) {
			List<Integer> currList = new ArrayList<>();
			currList.add(node.val);
			list.add(currList);
		} else {
			list.get(height).add(node.val);
		}
		levelOrderTraversalRecursive(list, height + 1, node.left);
		levelOrderTraversalRecursive(list, height + 1, node.right);
	}
}
