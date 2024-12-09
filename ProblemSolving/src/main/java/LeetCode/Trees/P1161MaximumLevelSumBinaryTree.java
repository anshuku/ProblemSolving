package LeetCode.Trees;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
 * P1161. Maximum Level Sum of a Binary Tree - Medium
 * 
 * Given the root of a binary tree, the level of its root is 1, 
 * the level of its children is 2, and so on.
 * 
 * Return the smallest level x such that the sum of all the values of nodes at level x is maximal.
 * 
 * Approach - BFS with map/int[]/variables; DFS with map/int[]
 * 
 * Using global variables is not thread safe and needs to be avoided
 */
public class P1161MaximumLevelSumBinaryTree {

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
		root.left = new TreeNode(7);
		root.right = new TreeNode(0);
		root.left.left = new TreeNode(7);
		root.left.right = new TreeNode(-8);

//		TreeNode root = new TreeNode(989);
//		root.right = new TreeNode(10250);
//		root.right.left = new TreeNode(98693);
//		root.right.right = new TreeNode(-89388);
//		root.right.right.right = new TreeNode(-32127);

		int maxSumDfsArr = maxLevelSumDfsArr(root);
		System.out.println("DFS Array: The smallest level with maximum sum is - " + maxSumDfsArr);

		int maxSumDfsMap = maxLevelSumDfsMap(root);
		System.out.println("DFS Map: The smallest level with maximum sum is - " + maxSumDfsMap);

		int maxSumBfsVar = maxLevelSumBfsVar(root);
		System.out.println("BFS Variable: The smallest level with maximum sum is - " + maxSumBfsVar);

		int maxSumBfsArr = maxLevelSumBfsArr(root);
		System.out.println("BFS Array: The smallest level with maximum sum is - " + maxSumBfsArr);

		int maxSumBfsMap = maxLevelSumBfsMap(root);
		System.out.println("BFS Map: The smallest level with maximum sum is - " + maxSumBfsMap);

	}

	// Not thread save and needs to be calculated within the dfs function
//	static int maxLevel = 0;

	private static int maxLevelSumDfsArr(TreeNode root) {
		int[] map = new int[10001];
		int maxLevel = dfs(root, map, 0);
		int max = Integer.MIN_VALUE;
		int minLevel = 0;
		for (int i = 0; i < maxLevel; i++) {
			if (map[i] > max) {
				max = map[i];
				minLevel = i;
			}
		}
		return minLevel + 1;
	}

	private static int dfs(TreeNode root, int[] map, int level) {
		if (root == null) {
			return 0;
		}
		map[level] += root.val;
		int maxLeft = dfs(root.left, map, level + 1);
		int maxRight = dfs(root.right, map, level + 1);
		return Math.max(maxLeft, maxRight) + 1;
	}

	private static int maxLevelSumDfsMap(TreeNode root) {
		Map<Integer, Integer> map = new HashMap<>();
		dfsMap(root, map, 0);
		int max = Integer.MIN_VALUE;
		int minLevel = 0;
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i) > max) {
				max = map.get(i);
				minLevel = i;
			}
		}
		return minLevel + 1;
	}

	private static void dfsMap(TreeNode root, Map<Integer, Integer> map, int level) {
		if (root == null) {
			return;
		}
		map.put(level, map.getOrDefault(level, 0) + root.val);
		dfsMap(root.left, map, level + 1);
		dfsMap(root.right, map, level + 1);
	}

	private static int maxLevelSumBfsVar(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		int level = 0;
		int minLevel = 0;
		int max = Integer.MIN_VALUE;
		while (!queue.isEmpty()) {
			int size = queue.size();
			int val = 0;
			while (size-- > 0) {
				TreeNode node = queue.poll();
				val += node.val;
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}
			level++;
			if (max < val) {
				max = val;
				minLevel = level;
			}
		}
		return minLevel;
	}

	private static int maxLevelSumBfsArr(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		int[] map = new int[10001]; // Max node count is 10^4
		int level = 0;
		int max = Integer.MIN_VALUE;
		int minLevel = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				TreeNode node = queue.poll();
				map[level] += node.val;
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}
			if (max < map[level]) {
				max = map[level];
				minLevel = level;
			}
			level++;
		}
		// smallest level can be checked with help of another variable
//		for (int i = 0; i < level; i++) {
//			if (map[i] == max) {
//				return i + 1;
//			}
//		}
		return minLevel + 1;
	}

	public static int maxLevelSumBfsMap(TreeNode root) {
		Map<Integer, Integer> map = new HashMap<>();
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		int level = 0;
		int max = Integer.MIN_VALUE;
		int minLevel = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				TreeNode node = queue.poll();
				map.put(level, map.getOrDefault(level, 0) + node.val);
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
			if (max < map.get(level)) {
				max = map.get(level);
				minLevel = level;
			}
			level++;
		}
		// max can be checked inside the queue iteration
//		int max = Integer.MIN_VALUE;
//		for (int val : map.values()) {
//			if (max < val) {
//				max = val;
//			}
//		}
		// smallest level can be checked with help of another variable
//		for (int i = 0; i < map.size(); i++) {
//			if (map.get(i) == max) {
//				return i;
//			}
//		}
		return minLevel + 1;
	}

}
