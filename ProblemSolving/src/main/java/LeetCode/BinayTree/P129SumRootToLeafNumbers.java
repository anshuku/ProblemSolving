package LeetCode.BinayTree;

public class P129SumRootToLeafNumbers {

	static int total = 0;

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

		TreeNode node = new TreeNode();
		node.val = 1;
		node.left = new TreeNode(2);
		node.right = new TreeNode(3);
		int num = sumRootToLeafNumbers(node);

		System.out.println("Sum toot to leaf numbers - " + num);
	}

	public static int sumRootToLeafNumbers(TreeNode root) {
		traverse(root, 0);
		return total;
	}

	public static void traverse(TreeNode node, int val) {
		if (node == null) {
			return;
		}

		int newVal = val * 10 + node.val;
		System.out.println("newVal - " + newVal);

		if (node.left == null && node.right == null) {
			total += newVal;
			return;
		} else {
			traverse(node.left, newVal);
			traverse(node.right, newVal);
		}
	}
}