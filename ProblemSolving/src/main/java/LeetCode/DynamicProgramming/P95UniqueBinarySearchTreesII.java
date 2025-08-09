package LeetCode.DynamicProgramming;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 * P95. Unique Binary Search Trees II - Medium
 * 
 * Given an integer n, return all the structurally unique BST's (binary search trees), 
 * which has exactly n nodes of unique values from 1 to n. Return the answer in any order.
 * 
 * Approach - DP: Recursion, Memoization
 */
public class P95UniqueBinarySearchTreesII {

	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}

	}

	static class Pair {
		int start;
		int end;

		Pair(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	public static void main(String[] args) {
		int n = 3;

		List<TreeNode> uniqueBSTsRecursive = generateTreesRecursive(n);

		System.out.println("Recursion: The unique Binary Search Trees are : ");
		for (TreeNode node : uniqueBSTsRecursive) {
			System.out.println("Tree: ");
			inOrderTraversal(node);
			System.out.println("");
		}

		List<TreeNode> uniqueBSTsMemoized = generateTreesMemoized(n);

		System.out.println("Memoization:The unique Binary Search Trees are : ");
		for (TreeNode node : uniqueBSTsMemoized) {
			System.out.println("Tree: ");
			inOrderTraversal(node);
			System.out.println("");
		}
	}

	public static void inOrderTraversal(TreeNode node) {
		if (node != null) {
			inOrderTraversal(node.left);
			System.out.print(node.val + " ");
			inOrderTraversal(node.right);
		}
	}

	// Recursion
	// Pickup a number from 1,2,3...n and use it as the root of the tree.
	// There are i-1 elements for construction of left subtree and
	// n-i elements for construction of right subtree.
	// So G(i-1) is the number of left subtrees and G(n-i) elements for
	// construction of right subtrees. wher G is Catalan number.
	// We've a root i and two lists for the possible left and right subtrees.
	// The final step is to loop over both lists to link left and right subtrees
	// to the root.
	// Time complexity - We construct all possible trees with given root. It is done
	// n times. so n*Gn. Gn grows as 4^n/n^(3/2). So 4^n/n^(1/2).
	// 4^n/n^(3/2) trees objects are generated as output.
	// Space complexity - n*Gn, as we keep Gn trees with n elements each.
	// which gives 4^n/n^(1/2).
	public static List<TreeNode> generateTreesRecursive(int n) {
		return generateTreesRecursive(1, n);
	}

	private static LinkedList<TreeNode> generateTreesRecursive(int start, int end) {
		LinkedList<TreeNode> allTrees = new LinkedList<>();
		// Base case when start > end, return an empty list
		// null value helps in forming left and right nodes
		// as they are mandatory
		if (start > end) {
			allTrees.add(null);
			return allTrees;
		}
		// pick up a root
		for (int i = start; i <= end; i++) {
			// Construct all left subtrees with i as root.
			LinkedList<TreeNode> leftTrees = generateTreesRecursive(start, i - 1);
			// Construct all right subtrees with i as root.
			LinkedList<TreeNode> rightTrees = generateTreesRecursive(i + 1, end);

			// Connect left and right trees to the root i.
			// The outer and inner loop can be interchanged.
			for (TreeNode leftNode : leftTrees) {
				for (TreeNode rightNode : rightTrees) {
					TreeNode currentNode = new TreeNode(i);
					currentNode.left = leftNode;
					currentNode.right = rightNode;
					allTrees.add(currentNode);
				}
			}
		}
		return allTrees;
	}

	private static LinkedList<TreeNode> generateTreesMemoized(int n) {
		Map<Pair, LinkedList<TreeNode>> dp = new HashMap<>();
		return generateTreesMemoized(1, n, dp);
	}

	private static LinkedList<TreeNode> generateTreesMemoized(int start, int end, Map<Pair, LinkedList<TreeNode>> dp) {
		LinkedList<TreeNode> allTrees = new LinkedList<>();
		if (start > end) {
			allTrees.add(null);
			return allTrees;
		}
		if (dp.containsKey(new Pair(start, end))) {
			return dp.get(new Pair(start, end));
		}
		for (int i = start; i <= end; i++) {
			LinkedList<TreeNode> leftTrees = generateTreesMemoized(start, i - 1, dp);
			LinkedList<TreeNode> rightTrees = generateTreesMemoized(i + 1, end, dp);
			for (TreeNode rightNode : rightTrees) {
				for (TreeNode leftNode : leftTrees) {
					TreeNode node = new TreeNode(i);
					node.left = leftNode;
					node.right = rightNode;
					allTrees.add(node);
				}
			}
		}
		dp.put(new Pair(start, end), allTrees);
		return allTrees;
	}
}
