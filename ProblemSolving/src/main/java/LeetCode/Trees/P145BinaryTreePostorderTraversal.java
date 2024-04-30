package LeetCode.Trees;

import java.util.ArrayList;
import java.util.List;

public class P145BinaryTreePostorderTraversal {

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

//		list = postOrderTraversal(root);
		
		list = postOrderTraversalNew(root);
		
		System.out.println("Post order traversal " + list);
	}

	private static List<Integer> postOrderTraversal(TreeNode node) {
		if (node != null) {
			postOrderTraversal(node.left);
			postOrderTraversal(node.right);
			list.add(node.val);
		}
		return list;
	}
	

	private static List<Integer> postOrderTraversalNew(TreeNode node) {
		List<Integer> list = new ArrayList<>();
		traversal(node, list);
		return list;
	}

	private static void traversal(TreeNode node, List<Integer> list) {
		
		if(node == null) {
			return;
		}
		traversal(node.left, list);
		traversal(node.right, list);
		list.add(node.val);	
		
	}

}
