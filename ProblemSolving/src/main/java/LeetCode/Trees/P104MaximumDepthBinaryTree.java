package LeetCode.Trees;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class P104MaximumDepthBinaryTree {

	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

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
		root.right.left.left = new TreeNode(7);

		P104MaximumDepthBinaryTree pmd = new P104MaximumDepthBinaryTree();
		int depth = pmd.maxDepth(root);

//		int depth = pmd.maxDepthQueue(root);

//		int depth = pmd.maxDepthDeque(root);

		System.out.println("The max depth for the tree is " + depth);
	}

	public int maxDepth(TreeNode node) {
		if (node == null) {
			return 0;
		}
		return Math.max(maxDepth(node.left), maxDepth(node.right)) + 1;
	}

	private int maxDepthQueue(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int depth = 0;
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			int size = q.size();
			while (size-- > 0) {
				TreeNode node = q.poll();
				if (node.left != null) {
					q.add(node.left);
				}
				if (node.right != null) {
					q.add(node.right);
				}
			}
			depth++;
		}
		return depth;
	}

	private int maxDepthDeque(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int depth = 0;
		Deque<TreeNode> deque = new ArrayDeque<>();
		deque.offerLast(root);
		while (!deque.isEmpty()) {
			int size = deque.size();
			while (size-- > 0) {
				TreeNode node = deque.pollFirst();
				if (node.left != null) {
					deque.offerLast(node.left);
				}
				if (node.right != null) {
					deque.offerLast(node.right);
				}
			}
			depth++;
		}
		return depth;
	}

}
