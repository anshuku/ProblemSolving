package LeetCode.Trees;

import java.util.ArrayList;
import java.util.List;

/*
 * P872. Leaf-Similar Trees - Easy
 * 
 * Consider all the leaves of a binary tree, from left to right order, 
 * the values of those leaves form a leaf value sequence.
 * 
 * Approach - DFS
 */
public class P872LeafSimilarTrees {

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

		TreeNode root1 = new TreeNode(3);
		root1.left = new TreeNode(5);
		root1.right = new TreeNode(1);
		root1.left.left = new TreeNode(6);
		root1.left.right = new TreeNode(2);
		root1.left.right.left = new TreeNode(7);
		root1.left.right.right = new TreeNode(4);
		root1.right.left = new TreeNode(9);
		root1.right.right = new TreeNode(8);

		TreeNode root2 = new TreeNode(3);
		root2.left = new TreeNode(5);
		root2.right = new TreeNode(1);
		root2.left.left = new TreeNode(6);
		root2.left.right = new TreeNode(7);
		root2.right.left = new TreeNode(4);
		root2.right.right = new TreeNode(2);
		root2.right.right.left = new TreeNode(9);
		root2.right.right.right = new TreeNode(8);

//		TreeNode root1 = new TreeNode(1);
//		root1.left = new TreeNode(2);
//		root1.right = new TreeNode(3);
//
//		TreeNode root2 = new TreeNode(1);
//		root2.left = new TreeNode(3);
//		root2.right = new TreeNode(2);

		boolean isLeafSimilar = isLeafSimilar(root1, root2);

		System.out.println("The leaves are similar - " + isLeafSimilar);

	}

	// Time Complexity - O(n+m),
	// The dfs function visits each nodes only once in both trees
	// For list comparison, O(L) <= O(min(n,m)) time is taken
	// Space Complexity - O(n+m)
	// The recursive dfs calls will take stack space for each node.
	// In worst case for skewed/completely unbalanced binary tree(~linked list)
	// Recursive depth is n and m, O(n) for 1st tree and O(m) for 2nd tree.
	// For storing the nodes in the list, the worst case is for full binary tree
	// In case of full binary tree, number of leaf nodes = n/2 where n is nodes
	public static boolean isLeafSimilar(TreeNode root1, TreeNode root2) {
		List<Integer> list1 = new ArrayList<>();
		getLeaves(root1, list1);
		List<Integer> list2 = new ArrayList<>();
		getLeaves(root2, list2);
		return list1.equals(list2);

		// No need to use below
		// if(list1.size() != list2.size()){
		// return false;
		// }
		// int i = 0;
		// for(int val : list1){
		// if(val != list2.get(i++)){
		// return false;
		// }
		// }
		// return true;

	}

	private static void getLeaves(TreeNode root, List<Integer> list) {
		if (root == null) {
			return;
		}
		if (root.left == null && root.right == null) {
			list.add(root.val);
		}
		getLeaves(root.left, list);
		getLeaves(root.right, list);
	}

}
