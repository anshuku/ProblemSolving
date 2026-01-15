package LeetCode.Trees;

/*
 * P543. Diameter of Binary Tree - Easy
 * 
 * Given the root of a binary tree, return the length of the diameter of the tree.
 * 
 * The diameter of a binary tree is the length of the longest path between 
 * any two nodes in a tree. This path may or may not pass through the root.
 * 
 * The length of a path between two nodes is represented by the number of edges between them.
 * 
 * Approach - Recursion | DFS + Object
 */
public class P543DiameterBinaryTree {

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

	static class Info {
		int diameter;
		int height;

		Info(int diameter, int height) {
			this.diameter = diameter;
			this.height = height;
		}
	}

	static int maxSum = 0;

	public static void main(String[] args) {

		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);

//		int maxSum = diameterOfBinaryTree(root);
//		The global maxSum can be overridden by local maxSum so avoid it
		diameterOfBinaryTree(root);
		System.out.println("The max diameter is " + maxSum);

		Info info = diameterOfBinaryTreeInfo(root);
		System.out.println("The max diameter via Info is " + info.diameter);

	}

	private static Info diameterOfBinaryTreeInfo(TreeNode node) {
		if (node == null) {
			return new Info(0, 0);
		}
		Info leftData = diameterOfBinaryTreeInfo(node.left);
		Info rightData = diameterOfBinaryTreeInfo(node.right);

		int currDiameter = Math.max(Math.max(leftData.diameter, rightData.diameter),
				leftData.height + rightData.height);

		int currHeight = Math.max(leftData.height, rightData.height) + 1;

		return new Info(currDiameter, currHeight);

	}

	public static int diameterOfBinaryTree(TreeNode node) {
		if (node == null) {
			return 0;
		}
		int leftSum = diameterOfBinaryTree(node.left);
		int rightSum = diameterOfBinaryTree(node.right);
		maxSum = Math.max(maxSum, leftSum + rightSum);
		return Math.max(leftSum, rightSum) + 1;
	}

}
