package LeetCode.Trees;

public class P226InvertBinaryTree {

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

		traverse(root);
		System.out.println("The inverted tree ->");
		TreeNode invertedRoot = invertTree(root);

//		TreeNode invertedRoot = invertTreeTemp(root);

//		TreeNode invertedRoot = invertTreeTempLR(root);
		traverse(invertedRoot);
	}

	private static void traverse(TreeNode node) {
		if (node == null) {
			return;
		}
		System.out.println("The node is " + node.val);
		traverse(node.left);
		traverse(node.right);
	}

	public static TreeNode invertTree(TreeNode root) {
		TreeNode currNode = root;
		invertTreeDfs(currNode);
		return root;
	}

	private static void invertTreeDfs(TreeNode node) {
		if (node == null) {
			return;
		}
		TreeNode temp = node.left;
		node.left = node.right;
		node.right = temp;
		invertTreeDfs(node.left);
		invertTreeDfs(node.right);
	}

	private static TreeNode invertTreeTemp(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode temp = root.left;
		root.left = root.right;
		root.right = temp;
		invertTreeTemp(root.left);
		invertTreeTemp(root.right);
		return root;
	}

	private static TreeNode invertTreeTempLR(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode temp = root.left;
		root.left = invertTreeTempLR(root.right);
		root.right = invertTreeTempLR(temp);
		return root;
	}
}
