package LeetCode.BinarySearchTree;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P938. Range Sum of BST - Easy
 * 
 * Given the root node of a binary search tree and two integers low and high, 
 * return the sum of values of all nodes with a value in the inclusive range [low, high].
 * 
 * Approach - inorder, preorder, post-order traversal;
 */
public class P938RangeSumBST {

	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(5);
		root.right = new TreeNode(15);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(7);
		root.right.right = new TreeNode(18);

		int low = 7;
		int high = 15;
		int sum = rangeSumBST(root, low, high);

		System.out.printf("The sum within the range via inorder traversal %d and %d is " + sum, low, high);
		System.out.println();
		System.out.println("counter " + counter);

		P938RangeSumBST.sum = 0;
		P938RangeSumBST.counter = 0;

		sum = rangeSumBSTOptimized(root, low, high);

		System.out.printf("The sum within the range via optimized search %d and %d is %d\n", low, high, sum);
		System.out.println("counter " + counter);

		P938RangeSumBST.sum = 0;
		P938RangeSumBST.counter = 0;

		sum = rangeSumBSTBFS(root, low, high);

		System.out.printf("The sum within the range via BFS Queue %d and %d is %d\n", low, high, sum);
		System.out.println("counter " + counter);

		P938RangeSumBST.sum = 0;
		P938RangeSumBST.counter = 0;

		sum = rangeSumBSTDFS(root, low, high);

		System.out.printf("The sum within the range via DFS recursion %d and %d is %d\n", low, high, sum);
		System.out.println("counter " + counter);

	}

	// Time complexity is same as inorder traversal
	private static int rangeSumBSTDFS(TreeNode root, int low, int high) {
		counter++;
		if (root == null) {
			return 0;
		}
		if (root.val >= low && root.val <= high) {
			return root.val + rangeSumBSTDFS(root.left, low, high) + rangeSumBSTDFS(root.right, low, high);
		} else {
			return rangeSumBSTDFS(root.left, low, high) + rangeSumBSTDFS(root.right, low, high);
		}
	}

	// Slow but space optimized since it's iterative
	private static int rangeSumBSTBFS(TreeNode root, int low, int high) {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		int sum = 0;
		counter++;
		while (!queue.isEmpty()) {
			counter++;
			TreeNode node = queue.poll();
			if (node == null) {
				continue;
			}
			if (node.val >= low && node.val <= high) {
				sum += node.val;
			}
			queue.add(node.left);
			queue.add(node.right);
		}
		return sum;
	}

	static int counter = 0;

	// Fast algo with lower counter value
	private static int rangeSumBSTOptimized(TreeNode root, int low, int high) {
		counter++;
		if (root == null) {
			return sum;
		}
		if (root.val >= low && root.val <= high) {
			sum += root.val;
		}
		if (root.val >= low) {
			rangeSumBSTOptimized(root.left, low, high);
		}
		if (root.val <= high) {
			rangeSumBSTOptimized(root.right, low, high);
		}
		return sum;
	}

	static int sum = 0;

	private static int rangeSumBST(TreeNode root, int low, int high) {
		counter++;
		if (root != null) {
			rangeSumBST(root.left, low, high);
			if (root.val >= low && root.val <= high) {
				sum += root.val;
			}
			rangeSumBST(root.right, low, high);
		}
		return sum;
	}

}
