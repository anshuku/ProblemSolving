package LeetCode.Trees;

import java.util.ArrayList;
import java.util.List;

/*
 * P236. Lowest Common Ancestor of a Binary Tree - Medium
 * 
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * 
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined 
 * between two nodes p and q as the lowest node in T that has both p and q as descendants 
 * (where we allow a node to be a descendant of itself).”
 * 
 * Approach - DFS
 */
public class P236LowestCommonAncestorBinaryTree {

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

		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(5);
		root.right = new TreeNode(1);
		root.left.left = new TreeNode(6);
		root.left.right = new TreeNode(2);
		root.left.right.left = new TreeNode(7);
		root.left.right.right = new TreeNode(4);
		root.right = new TreeNode(1);
		root.right.left = new TreeNode(0);
		root.right.right = new TreeNode(8);

		// Works for both root == p and root.val == p.val condition
//		TreeNode p = root.left;
//		TreeNode q = root.right;

		// Works for only root == p and root.val == p.val condition
		TreeNode p = new TreeNode(0);
		TreeNode q = new TreeNode(8);

		TreeNode lca = lowestCommonAncestor(root, p, q);
		System.out.println("The lowest common ancesor is - " + lca.val);

		TreeNode lcaList = lowestCommonAncestorList(root, p, q);
		System.out.println("The lowest common ancesor is - " + lcaList.val);
	}

	private static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || root.val == p.val || root.val == q.val) {
			return root;
		}
		TreeNode left = lowestCommonAncestor(root.left, p, q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);
		// From the current root's left and right both p and q are found
		// Return the current root and it'll be answer later on
		if (left != null && right != null) {
			return root;
		}
		// In case anyone among left and right is not null, means
		// the not null side has one among p or q or both
		return left != null ? left : right;
	}

	public static TreeNode lowestCommonAncestorList(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null) {
			return root;
		}
		List<Integer> pList = new ArrayList<>();
		dfs(root, p, pList);
		if (pList.contains(q.val)) {
			return q;
		}
		List<Integer> qList = new ArrayList<>();
		dfs(root, q, qList);
		if (qList.contains(p.val)) {
			return p;
		}
		if (pList.size() > qList.size()) {
			for (int i = qList.size() - 2; i >= 0; i++) {
				if (pList.contains(qList.get(i))) {
					return new TreeNode(qList.get(i));
				}
			}
		} else {
			for (int i = pList.size() - 2; i >= 0; i++) {
				if (qList.contains(pList.get(i))) {
					return new TreeNode(pList.get(i));
				}
			}
		}
		return root;
	}

	private static void dfs(TreeNode root, TreeNode node, List<Integer> list) {
		if (root == null || list.contains(node.val)) {
			return;
		}
		list.add(root.val);
		if (root.val == node.val) {
			return;
		}
		dfs(root.left, node, list);
		dfs(root.right, node, list);
		if (!list.contains(node.val)) {
			list.remove(list.size() - 1);
		}
	}

}
