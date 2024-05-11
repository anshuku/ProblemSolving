package LeetCode.Trees;

public class P112PathSum {

	static boolean hasSum;

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
		root.right.left.right = new TreeNode(2);
		root.right.left.left.right = new TreeNode(1);
		root.right.left.left.right.left = new TreeNode(2);
		int targetSum = 7;

		boolean hasSumPresent = hasPathSum(root, targetSum);
		System.out.println("The sum is present - " + hasSumPresent);

//		hasPathSumDfs(root, targetSum, 0);
//		System.out.println("The sum is present - " + hasSum);
	}

	private static boolean hasPathSum(TreeNode root, int targetSum) {
		if (root == null) {
			return false;
		}
		if (root.left == null && root.right == null && root.val == targetSum) {
			return true;
		}
		return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
	}

	public static void hasPathSumDfs(TreeNode node, int targetSum, int currSum) {
		if (node == null || hasSum) {
			return;
		}
		currSum += node.val;
		if (node.left == null && node.right == null && currSum == targetSum) {
			hasSum = true;
			return;
		}
		hasPathSumDfs(node.left, targetSum, currSum);
		hasPathSumDfs(node.right, targetSum, currSum);
		currSum -= node.val;
	}

}
