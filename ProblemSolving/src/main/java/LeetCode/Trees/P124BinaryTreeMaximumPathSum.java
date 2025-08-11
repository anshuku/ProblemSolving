package LeetCode.Trees;

/*
 * P124. Binary Tree Maximum Path Sum - Hard
 * 
 * A path in a binary tree is a sequence of nodes where each pair of adjacent 
 * nodes in the sequence has an edge connecting them. A node can only appear in the 
 * sequence at most once. Note that the path does not need to pass through the root.
 * 
 * The path sum of a path is the sum of the node's values in the path.
 * 
 * Given the root of a binary tree, return the maximum path sum of any non-empty path.
 * 
 * Approach - DP: Recursion
 * 
 * When traversing trees, DFS is preferred over BFS because it 
 * can examine each path before moving on to the next.
 */
public class P124BinaryTreeMaximumPathSum {

	static int maxPathSum = Integer.MIN_VALUE;

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
		TreeNode root = new TreeNode(0);
		root.left = new TreeNode(1);
		root.right = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right.left = new TreeNode(5);

		maxPathSum(root);
		System.out.println("The Max path sum is " + maxPathSum);

	}

	// Post Order DFS - process the children before the parent.
	// There is at least one node. In a path, except for starting and ending nodes,
	// every node is connected to two other nodes in the sequence. These two nodes
	// could either be the node's children, or one of them could be a child, and
	// the other could be the parent node. Each node has no more than 2 connections
	// in the sequence. A node value can be +ve/-ve path sum can also be +ve/-ve.
	// Every combination of two nodes can form a path by using the sequence of all
	// nodes between them. Every node in itself can also be considered as a path.
	// The entire tree must be traversed to find the maximum path sum. We're
	// interested in a set of nodes that form a continuous sequence(path).
	// When traversing trees, DFS is preferred over BFS because it can examine each
	// path before moving on to the next.
	// If the max path sum passes through the root, there are 4 cases:
	// a) Path from root and goes down to root's left child(may till bottom).
	// b) Path from root and goes down to root's right child(may till bottom).
	// c) Path from root and goes down towards both left and right child.
	// d) Path doesn't involve any child and root itself has path with max sum.
	// It's makes sense to 1st consider a path sum contributed by a left or right
	// subtree only if it's positive(gain in path sum) else we can ignore it.
	// Hence it's post order traversal where we process the child before parent.
	// It may happen that the max path sum doesn't include the root. For that,
	// In addition to returning the path sum contributed by the subtree, the
	// recursive function also keeps track of max sum. If root is null return 0.
	// maxPathSum(global) considers current val(node.val) + leftSum + rightSum.
	// It takes care of all 4 possibilites where there can be maxPathSum.
	// as it allows a path to go through the current node including both sides.
	// return statement Math.max(leftSum, rightSum) + node.val only picks
	// one branch because when going up the parent we can't take both sides -
	// otherwise it'll no longer be a single path and create a fork(3 connections)
	// The path would consist of at most one child of the root.
	// Time complexity - O(n), each node in the tree is visited only once. We
	// perform constant time operations - 2 recursive calls and finding max value.
	// Space complexity - O(n), we don't use any auxiliary data structures, but the
	// recursive call stack can go as deep as tree's height(worst n - linked list).
	public static int maxPathSum(TreeNode node) {
		if (node == null) {
			return 0;
		}
		int value = node.val;

		// Math.max(leftSum, 0) as the path can end anywhere and
		// the path sum may become negative and decrease total path sum.
		// Padding with 0 helps us to avoid/cut the left/right subtrees.
		// If the best path form left/right child is +ve we keep it.
		int leftSum = Math.max(maxPathSum(node.left), 0);

		int rightSum = Math.max(maxPathSum(node.right), 0);

		maxPathSum = Math.max(maxPathSum, leftSum + rightSum + value);

		return Math.max(leftSum, rightSum) + value;
	}

}
