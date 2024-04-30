package LeetCode.Trees;

import java.util.ArrayList;
import java.util.List;

public class P144BinaryTreePreorderTraversal {

	static List<Integer> list = new ArrayList<>();

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
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);	
		
//		list = preOrderTraversal(root);
		
		// List defined inside main is faster than static list at class level in case of preOrderTraversalNew()
		// List<Integer> list = new ArrayList<>();

		list = preOrderTraversalNew(root);
		System.out.println("Pre order traversal " + list);

	}

	private static List<Integer> preOrderTraversal(TreeNode node) {
		if (node != null) {
			list.add(node.val);
			preOrderTraversal(node.left);
			preOrderTraversal(node.right);
		}
		return list;
	}

	private static List<Integer> preOrderTraversalNew(TreeNode node) {
		List<Integer> list = new ArrayList<>();
		traversal(node, list);
		return list;
	}

	private static void traversal(TreeNode node, List<Integer> list) {
		if (node == null) {
			return;
		}
		list.add(node.val);
		traversal(node.left, list);
		traversal(node.right, list);
	}

}
