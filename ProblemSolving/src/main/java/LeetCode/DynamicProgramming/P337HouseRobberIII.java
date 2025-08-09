package LeetCode.DynamicProgramming;

import java.awt.Label;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * P337. House Robber III - Medium
 * 
 * The thief has found himself a new place for his thievery again. 
 * There is only one entrance to this area, called root.
 * 
 * Besides the root, each house has one and only one parent house. 
 * After a tour, the smart thief realized that all houses in this place 
 * form a binary tree. It will automatically contact the police if two 
 * directly-linked houses were broken into on the same night.
 * 
 * Given the root of the binary tree, return the maximum amount 
 * of money the thief can rob without alerting the police.
 * 
 * Approach - DP, Graph
 */
public class P337HouseRobberIII {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	public static void main(String[] args) {
//		TreeNode root = new TreeNode(3);
//		root.left = new TreeNode(2);
//		root.right = new TreeNode(3);
//		root.left.right = new TreeNode(3);
//		root.right.right = new TreeNode(1);
//
//		TreeNode root = new TreeNode(3);
//		root.left = new TreeNode(4);
//		root.right = new TreeNode(5);
//		root.left.left = new TreeNode(1);
//		root.left.right = new TreeNode(3);
//		root.right.right = new TreeNode(1);

		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(2);
		root.right = new TreeNode(20);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(4);

		int robbedSumLR = robRecursiveArr(root);
		System.out.println("Recursion Array: The maximum robbed sum is " + robbedSumLR);

		int robbedSumMap = robRecursiveMap(root);
		System.out.println("Recursion Map: The maximum robbed sum is " + robbedSumMap);

		int robbedSumMemo = robRecursiveMemoized(root);
		System.out.println("Recursion Memo: The maximum robbed sum is " + robbedSumMemo);

		int robbedSumDPChildParent = robDPLeftRight(root);
		System.out.println("DP Left Right Child: The maximum robbed sum is " + robbedSumDPChildParent);

		int robbedSumDP = robDPNode(root);
		System.out.println("DP Node: The maximum robbed sum is " + robbedSumDP);

		int robbedSumDPNode = robDPNodeExtra(root);
		System.out.println("DP Parent Node: The maximum robbed sum is " + robbedSumDPNode);

	}

	// DFS Recursion with Array Memoization
	// It helps to avoid working with grandchildren nodes by involving only the
	// child nodes. The workaround is to use a flag parentRobbed = true/false
	// But in that scenario few functions are redundantly called again and again.
	// To avoid that combine the results into one. We return the results of
	// helper(node.left, true) and helper(node.left, false) in a 2 element array.
	// We return an array of 2 elements where the first element represents the
	// maxiumum amount of money the thief can rob starting from the current node
	// and robbing it(index 0). And 2nd element represents the maxiumum amount of
	// money the thief can rob starting from the current node and not robbing it(1).
	// Base case: The node is null and we return 0.
	// Time complexity - O(n) since we visit all nodes once
	// Space complexity - O(n) as we need stacks for recursion, and maximum depths
	// of recursion is height of tree, O(N) is worst case and O(logn) in best case.
	private static int robRecursiveArr(TreeNode node) {
		int[] result = recursiveLR(node);
		// result[0] - max money when node is robbed
		// result[1] - max money when node is not robbed
		return Math.max(result[0], result[1]);
	}

	private static int[] recursiveLR(TreeNode node) {
		if (node == null) {
			return new int[] { 0, 0 };
		}
		int[] left = recursiveLR(node.left);
		int[] right = recursiveLR(node.right);

		// If we rob this node we cannot rob it's children or 2nd element of array.
		int robbed = node.val + left[1] + right[1];
		// Else we are free to rob it's children or not.
		int notRobbed = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
		return new int[] { robbed, notRobbed };
	}

	static Map<TreeNode, Integer> dp;

	// Recursion with Map Memoization - Brute Force
	// With help of the helper function we check if the node is null
	// We return 0. Otherwise the robber can either:
	// a) Rob house - node.val + may rob from node.left's and node.right's children.
	// b) Do not rob house - may rob from node.left and node.right
	// Here we touch the grandchildren of a node which requires extra effor + code.
	// The best practice is to make node.left and node.right handle grandchildren.
	public static int robRecursiveMap(TreeNode node) {
		dp = new HashMap<>();
		return helper(node);
	}

	private static int helper(TreeNode node) {
		if (node == null) {
			return 0;
		}
		if (dp.containsKey(node)) {
			return dp.get(node);
		}
		int notRobbed = helper(node.left) + helper(node.right);
		int robbed = node.val + (node.left != null ? helper(node.left.left) + helper(node.left.right) : 0)
				+ (node.right != null ? helper(node.right.left) + helper(node.right.right) : 0);
		int val = Math.max(notRobbed, robbed);
		dp.put(node, val);
		return val;
	}

	static Map<TreeNode, Integer> robbedMap;
	static Map<TreeNode, Integer> notRobbedMap;

	// Recursion with memoization
	// To avoid redundant calculation Memoization with map can be used.
	// Whether to rob a house or not can be determined via parentRobbed flag.
	// Time complexity - O(n) as the helper function is ran for all node once
	// and second calculation is prevented with the help of a map.
	// Space complexity - O(n) as we need two maps with size O(n) to store the
	// results and also we need O(n) space for recursion stack.
	private static int robRecursiveMemoized(TreeNode root) {
		robbedMap = new HashMap<>();
		notRobbedMap = new HashMap<>();

		return recursive(root, false);
	}

	private static int recursive(TreeNode node, boolean parentRobbed) {
		if (node == null) {
			return 0;
		}
		if (parentRobbed) {
			if (robbedMap.containsKey(node)) {
				return robbedMap.get(node);
			}
			int val = recursive(node.left, false) + recursive(node.right, false);
			robbedMap.put(node, val);
			return val;
		} else {
			if (notRobbedMap.containsKey(node)) {
				return notRobbedMap.get(node);
			}
			int rob = node.val + recursive(node.left, true) + recursive(node.right, true);
			int notRob = recursive(node.left, false) + recursive(node.right, false);
			int val = Math.max(rob, notRob);
			notRobbedMap.put(node, val);
			return val;
		}
	}

	// DP Bottom Up with Graphs and DP arrays - left and right child
	// dpRob[i] = max money one can rob starting from node i and robbing it.
	// dpNotRob[i] = max money one can rob starting from node i and not robbing it.
	// node i? we don't have an index i for each node. We need to index or map the
	// nodes of tree to some integers. One such mapping is BFS: left to right and
	// top to bottom. This is used in many situations such as heap and segment tree,
	// where we store the tree in an array. We create an array of node's values and
	// a hashmap(graph) storing relationship between those integers - parent/child.
	// We then perform dp. DP involves basic cases and transition equations.
	// Basic case: leaf nodes where dpRob[i] = node.val and dpNotRob[i] = 0.
	// Transition equation: where we calculate dpRob[i] and dpNotRob[i].
	// When we rob node i, then we cannot rob all of it's child nodes
	// dpRob[i] = tree[i] + Sum dpNotRob[child]
	// When we do not rob node i, then we can choose to rob it's child or not.
	// dpNotRob[i] = Sum Math.max(dpRob[child], dpNotRob[child])
	// Since child index is always greater than parent's index in our mapping, we
	// can iterate the dp arrays backward.
	// Time complexity - O(n) since we visit all the nodes once to form the tree
	// array, and then iterate the two DP array which both have length O(n).
	// Space complexity - O(n) since we need an array of length O(n) to store tree,
	// and two DP arrays of length n. Also, size of other data structures = O(n).
	private static int robDPLeftRight(TreeNode root) {
		// This will hold the value of each node in the tree by index.
		List<Integer> tree = new ArrayList<>();
		// Graph to track parent -> child relationships
		Map<Integer, List<Integer>> graph = new HashMap<>();
		int index = 0;

		Queue<Integer> indices = new LinkedList<>();
		Queue<TreeNode> nodes = new LinkedList<>();

		tree.add(root.val);
		indices.add(index);
		nodes.add(root);
		graph.put(index, new ArrayList<>());

		while (!nodes.isEmpty()) {
			int parentIndex = indices.poll();
			TreeNode node = nodes.poll();
			// Left child
			// Directly add the left child's index and value
			if (node.left != null) {
				index++;
				graph.put(index, new ArrayList<>());
				graph.get(parentIndex).add(index);
				indices.add(index);
				nodes.add(node.left);
				tree.add(node.left.val);
			}
			// Right child
			// Directly add the right child's index and value
			if (node.right != null) {
				index++;
				graph.put(index, new ArrayList<>());
				graph.get(parentIndex).add(index);
				indices.add(index);
				nodes.add(node.right);
				tree.add(node.right.val);
			}
		}

		int n = tree.size();
		int[] dpRob = new int[n];
		int[] dpNotRob = new int[n];

		// Post order traversal to ensure child is processed before parent.
		for (int i = n - 1; i >= 0; i--) {
			dpRob[i] = tree.get(i);
			for (int childIndex : graph.get(i)) {
				dpRob[i] += dpNotRob[childIndex];
				dpNotRob[i] += Math.max(dpRob[childIndex], dpNotRob[childIndex]);
			}
		}

		return Math.max(dpRob[0], dpNotRob[0]);
	}

	// Dynamic Programming with Graphs and DP arrays(rob and not_rob)
	private static int robDPNode(TreeNode root) {
		if (root == null) {
			return 0;
		}
		// Transform tree into array based tree.
		// Stores node values in BFS index order.
		List<Integer> tree = new ArrayList<>();

		// Graph: index -> list of children indices
		Map<Integer, List<Integer>> graph = new HashMap<>();

		// Start before root
		int index = -1;
		// Dummy parent for root.
		graph.put(index, new ArrayList<>());

		// Use two Queues to store node and index.
		Queue<Integer> indices = new LinkedList<>();
		indices.add(index);
		Queue<TreeNode> nodes = new LinkedList<>();
		nodes.add(root);

		while (nodes.size() > 0) {
			int parentIndex = indices.poll();
			TreeNode node = nodes.poll();
			// Parent node and value is added
			// index increment happens only when the current node is non-null.
			if (node != null) {
				index++;
				tree.add(node.val);
				graph.put(index, new ArrayList<>());
				graph.get(parentIndex).add(index);

				// Left child
				nodes.add(node.left);
				// Left child's parent index
				indices.add(index);

				// Right child
				nodes.add(node.right);
				// Right child's parent index
				indices.add(index);
			}
		}
		int n = tree.size();
		int[] dpRob = new int[n];
		int[] dpNotRob = new int[n];
		for (int i = n - 1; i >= 0; i--) {
			dpRob[i] = tree.get(i);
			for (int childIndex : graph.get(i)) {
				dpRob[i] += dpNotRob[childIndex];
				dpNotRob[i] += Math.max(dpRob[childIndex], dpNotRob[childIndex]);
			}
		}
		return Math.max(dpRob[0], dpNotRob[0]);
	}

	private static int robDPNodeExtra(TreeNode root) {
		List<Integer> tree = new LinkedList<>();
		Map<Integer, List<Integer>> graph = new HashMap<>();
		int index = -1;
		Queue<Integer> indices = new LinkedList<>();
		Queue<TreeNode> nodes = new LinkedList<>();
		indices.add(++index);
		graph.put(index, new ArrayList<>());
		nodes.add(root);
		tree.add(root.val);
		while (!nodes.isEmpty()) {
			int parentIndex = indices.poll();
			TreeNode node = nodes.poll();
			if (node != null) {
				TreeNode left = node.left;
				// size is used as an index generator.
				int leftIndex = tree.size();
				graph.put(leftIndex, new ArrayList<>());
				graph.get(parentIndex).add(leftIndex);
				indices.add(leftIndex);
				tree.add(left == null ? 0 : left.val); // dummy 0 for nulls
				nodes.add(left);

				TreeNode right = node.right;
				int rightIndex = tree.size();
				graph.put(rightIndex, new ArrayList<>());
				graph.get(parentIndex).add(rightIndex);
				indices.add(rightIndex);
				tree.add(right == null ? 0 : right.val); // dummy 0 for nulls
				nodes.add(right);
			}
		}

		int n = tree.size();
		int[] dpRob = new int[n];
		int[] dpNotRob = new int[n];

		// Post order traversal to ensure child is processed before parent.
		List<Integer> postOrder = getPostOrder(0, graph);
		for (int i : postOrder) {
			dpRob[i] = tree.get(i);
			for (int childIndex : graph.get(i)) {
				dpRob[i] += dpNotRob[childIndex];
				dpNotRob[i] += Math.max(dpRob[childIndex], dpNotRob[childIndex]);
			}
		}

//		for (int i = n - 1; i >= 0; i--) {
//			dpRob[i] = tree.get(i);
//			for (int childIndex : graph.get(i)) {
//				dpRob[i] += dpNotRob[childIndex];
//				dpNotRob[i] += Math.max(dpRob[childIndex], dpNotRob[childIndex]);
//			}
//		}
		return Math.max(dpRob[0], dpNotRob[0]);
	}

	private static List<Integer> getPostOrder(int node, Map<Integer, List<Integer>> graph) {
		List<Integer> result = new ArrayList<>();
		postOrderDfs(node, graph, result);
		return result;
	}

	private static void postOrderDfs(int node, Map<Integer, List<Integer>> graph, List<Integer> result) {
		for (int child : graph.getOrDefault(node, new ArrayList<>())) {
			postOrderDfs(child, graph, result);
		}
		result.add(node);
	}
}
