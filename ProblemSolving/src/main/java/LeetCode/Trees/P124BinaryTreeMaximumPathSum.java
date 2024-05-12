package LeetCode.Trees;

public class P124BinaryTreeMaximumPathSum {

	static int maxPathSum = Integer.MIN_VALUE;

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
		TreeNode root = new TreeNode(0);
		root.left = new TreeNode(1);
		root.right = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right.left = new TreeNode(5);

		maxPathSum(root);
		System.out.println("The Max path sum is " + maxPathSum);

	}

	public static int maxPathSum(TreeNode node) {
		if (node == null) {
			return 0;
		}
		int value = node.val;

		int leftSum = Math.max(maxPathSum(node.left), 0);

		int rightSum = Math.max(maxPathSum(node.right), 0);
		
		maxPathSum = Math.max(maxPathSum, leftSum + rightSum + value);

		return Math.max(leftSum, rightSum) + value;
	}

}
