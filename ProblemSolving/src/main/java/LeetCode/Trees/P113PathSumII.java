package LeetCode.Trees;

import java.util.ArrayList;
import java.util.List;

/*
 * Given the root of a binary tree and an integer targetSum, return all root-to-leaf 
 * paths where the sum of the node values in the path equals targetSum. 
 * Each path should be returned as a list of the node values, not node references.
 * A root-to-leaf path is a path starting from the root and ending at any leaf node. 
 * A leaf is a node with no children.
 */
public class P113PathSumII {

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
		int targetSum = 8;

//		List<List<Integer>> paths = pathSum(root, targetSum);

		List<List<Integer>> paths = pathSumCurr(root, targetSum);
		System.out.println("the path for target sum is " + paths);
	}

	private static List<List<Integer>> pathSumCurr(TreeNode root, int targetSum) {
		List<List<Integer>> paths = new ArrayList<>();
		List<Integer> currPath = new ArrayList<>();
		pathSumDfsRecursive(paths, currPath, root, 0, targetSum);
		return paths;
	}

	private static void pathSumDfsRecursive(List<List<Integer>> paths, List<Integer> currPath, TreeNode node, int sum,
			int targetSum) {

		if (node == null) {
			return;
		}
		sum += node.val;
		currPath.add(node.val);
		if (node.left == null && node.right == null && sum == targetSum) {
			paths.add(new ArrayList<>(currPath));
			currPath.remove(currPath.size() - 1);
			return;
		}
		pathSumDfsRecursive(paths, currPath, node.left, sum, targetSum);
		pathSumDfsRecursive(paths, currPath, node.right, sum, targetSum);
		currPath.remove(currPath.size() - 1);
	}

	private static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
		List<List<Integer>> paths = new ArrayList<>();
		List<Integer> currPath = new ArrayList<>();
		pathDfs(paths, currPath, root, targetSum);
		return paths;
	}

	private static void pathDfs(List<List<Integer>> paths, List<Integer> currPath, TreeNode node, int targetSum) {
		if (node == null) {
			return;
		}
		currPath.add(node.val);
		// Avoid currPath.stream().reduce((a, b) -> a + b).get() as it's slow
		if (node.left == null && node.right == null && getSum(currPath) == targetSum) {
			paths.add(new ArrayList<>(currPath));
		}
		// if (node.left/right != null) check is not needed as node == null is
		// sufficient
		pathDfs(paths, currPath, node.left, targetSum);
		pathDfs(paths, currPath, node.right, targetSum);
		currPath.remove(currPath.size() - 1);
	}

	private static int getSum(List<Integer> currPath) {
		int count = 0;
		for (int i = 0; i < currPath.size(); i++) {
			count += currPath.get(i);
		}
		return count;
	}

}
