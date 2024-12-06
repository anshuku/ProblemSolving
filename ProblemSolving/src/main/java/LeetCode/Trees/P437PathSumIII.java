package LeetCode.Trees;

import java.util.HashMap;
import java.util.Map;

/*
 * P437. Path Sum III - Medium
 * 
 * Given the root of a binary tree and an integer targetSum, return the number of 
 * paths where the sum of the values along the path equals targetSum.
 * 
 * The path does not need to start or end at the root or a leaf, but it must 
 * go downwards (i.e., traveling only from parent nodes to child nodes).
 * 
 * Approach - DFS, Map storing prefix sum
 */
public class P437PathSumIII {

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

		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(5);
		root.right = new TreeNode(-3);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(2);
		root.left.left.left = new TreeNode(3);
		root.left.left.left.left = new TreeNode(5);
		root.left.left.right = new TreeNode(-2);
		root.left.right.right = new TreeNode(1);
		root.right.right = new TreeNode(11);

		int targetSum = 8;

//		TreeNode root = new TreeNode(0);
//		root.left = new TreeNode(1);
//		root.right = new TreeNode(1);
//		
//		int targetSum = 1;

		int pathCountMap = pathSumMap(root, targetSum);

		System.out.println("Map: The number of paths with target sum is - " + pathCountMap);

		int pathCountAll = pathSumAll(root, targetSum);

		System.out.println("All paths: The number of paths with target sum is - " + pathCountAll);

	}

	private static int pathSumMap(TreeNode root, int targetSum) {
		Map<Long, Integer> map = new HashMap<>();
		map.put(0l, 1);
		return pathSumCountMap(root, targetSum, 0, map);
	}

	private static int pathSumCountMap(TreeNode root, int targetSum, long currentSum, Map<Long, Integer> map) {
		if (root == null) {
			return 0;
		}
		currentSum += root.val;

		// Get the number of valid paths ending at this node
		int count = map.getOrDefault(currentSum - targetSum, 0); // targetSum - currentSum is incorrect

		// Put should happen after getting count, else it fails for 0 target sum
		map.put(currentSum, map.getOrDefault(currentSum, 0) + 1);

		// Recurse on left and right subtrees
		count += pathSumCountMap(root.left, targetSum, currentSum, map);
		count += pathSumCountMap(root.right, targetSum, currentSum, map);

		// Backtrack - remove the current sum(count) from the map
		map.put(currentSum, map.get(currentSum) - 1);
		return count;
	}

	static int count = 0;

	// Checks all possible paths and sums
	public static int pathSumAll(TreeNode root, int targetSum) {
		if (root == null) {
			return 0;
		}
		pathSumHelper(root, targetSum, 0);
		pathSumAll(root.left, targetSum);
		pathSumAll(root.right, targetSum);
		return count;
	}

	// [1000000000,1000000000,null,294967296,null,1000000000,null,1000000000,null,1000000000]
	// long currentSum can handle large sum
	private static void pathSumHelper(TreeNode root, int targetSum, long currentSum) {
		if (root == null) {
			return;
		}
		currentSum += root.val;
		if (currentSum == targetSum) {
			count++;
		}
		pathSumHelper(root.left, targetSum, currentSum);
		pathSumHelper(root.right, targetSum, currentSum);
	}

}
