package LeetCode.BinarySearchTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P653. Two Sum IV - Input is a BST - Easy
 * 
 * Given the root of a binary search tree and an integer k, 
 * return true if there exist two elements in the BST such that their sum is equal to k, or false otherwise.
 * 
 * Approach - DFS, Iteration
 * 
 * Time complexity - O(n)
 * Space complexity - O(n)
*/
public class P653TwoSumIVInputBST {

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

		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(2);
		root.left.right = new TreeNode(4);
		root.right.right = new TreeNode(7);

		int target = 9;

		List<Integer> list = new ArrayList<>();

//		populateList(root, list);

		inOrderTraversal(root, list);

		System.out.println("The list is " + list);

//		boolean isPresent = isTargetPresent(list, target);

		boolean isPresent = checkTarget(list, target);

		System.out.println("The target is present " + isPresent);

	}

	private static void populateList(TreeNode node, List<Integer> list) {
		if (node != null) {
			list.add(node.val);
		}

		if (node.left != null) {
			populateList(node.left, list);
		}

		if (node.right != null) {
			populateList(node.right, list);
		}
	}

	private static boolean isTargetPresent(List<Integer> list, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		int i = 0;
		while (i < list.size()) {
			if (map.containsKey(target - list.get(i))) {
				return true;
			} else {
				map.put(list.get(i), i);
				i++;
			}
		}
		return false;
	}

	// Populates a list of sorted values
	private static void inOrderTraversal(TreeNode node, List<Integer> list) {
		if (node == null) {
			return;
		}
		inOrderTraversal(node.left, list);
		list.add(node.val);
		inOrderTraversal(node.right, list);

	}

	private static boolean checkTarget(List<Integer> list, int target) {
		int i = 0, j = list.size() - 1;

		while (i < j) {
			if (list.get(i) + list.get(j) == target) {
				return true;
			} else if (list.get(i) + list.get(j) < target) {
				i++;
			} else {
				j--;
			}
		}
		return false;
	}

}
