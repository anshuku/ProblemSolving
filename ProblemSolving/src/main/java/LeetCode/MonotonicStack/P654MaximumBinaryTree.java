package LeetCode.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/*
 * P654. Maximum Binary Tree - Medium
 * 
 * You are given an integer array nums with no duplicates. A maximum binary 
 * tree can be built recursively from nums using the following algorithm:
 * 
 * > Create a root node whose value is the maximum value in nums.
 * > Recursively build the left subtree on the subarray prefix to the left of the maximum value.
 * > Recursively build the right subtree on the subarray suffix to the right of the maximum value.
 * 
 * Return the maximum binary tree built from nums.
 * 
 * Constraints:
 * > 1 <= nums.length <= 1000
 * > 0 <= nums[i] <= 1000
 * > All integers in nums are unique.
 * 
 * Approach - Divide and Conquer - Recursion, Monotonic Stack
 * 
 * Maximum Binary Tree is also called Cartesian Tree.
 * The recursive solution is intuitive and follows the problem definition directly, while the monotonic stack 
 * solution is the optimal approach and is preferred when asked for the most efficient solution in interviews.
 */
public class P654MaximumBinaryTree {

	static class TreeNode {

		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}

	}

	public static void main(String[] args) {
		int[] nums = { 3, 2, 1, 6, 0, 5 };

		TreeNode nodeDnQ = constructMaximumBinaryTreeDnQ(nums);
		System.out.println("Divide and Conquer:");
		System.out.println("pre order traversal:");
		preOrderTraversal(nodeDnQ);
		System.out.println();
		System.out.println("in order traversal:");
		inOrderTraversal(nodeDnQ);
		System.out.println();
		System.out.println("post order traversal:");
		postOrderTraversal(nodeDnQ);
		System.out.println();

		TreeNode nodeDnQAlt = constructMaximumBinaryTreeAltDnQAlt(nums);
		System.out.println("Divide and Conquer Alternate:");
		System.out.println("pre order traversal:");
		preOrderTraversal(nodeDnQAlt);
		System.out.println();
		System.out.println("in order traversal:");
		inOrderTraversal(nodeDnQAlt);
		System.out.println();
		System.out.println("post order traversal:");
		postOrderTraversal(nodeDnQAlt);
		System.out.println();

		TreeNode nodeMStack = constructMaximumBinaryTreeMStack(nums);
		System.out.println("Monotonic Stack:");
		System.out.println("pre order traversal:");
		preOrderTraversal(nodeMStack);
		System.out.println();
		System.out.println("in order traversal:");
		inOrderTraversal(nodeMStack);
		System.out.println();
		System.out.println("post order traversal:");
		postOrderTraversal(nodeMStack);
		System.out.println();
	}

	// Divide and Conquer: Recursive Solution
	// We use a recursive function construct which returns the maximum binary tree
	// consisting of numbers within the indices l and r in the given nums array
	// (excluding the rth element). The algorithm consists of below steps:
	// 1. We start with function call construct(nums, 0, n).
	// 2. Find the index maxi, of the largest element in the current range of
	// indices [l:r-1]. Make this largest element, nums[maxI] as the local root
	// node.
	// 3. Determine the left child using construct(nums, l, maxI). Doing this
	// recursivly finds the largest element in the subarray left to the current
	// largest element. This is the left subtree.
	// 4. We determine the right child using construct(nums, maxI + 1, r). This is
	// the right subtree.
	// 5. We return the root node to the calling function.
	// Time complexity - O(n^2), the function construct is called n times at most.
	// At each level of the recursive tree, we traverse over all the n elements to
	// find the max element. In the average case which happens for balanced tree ,
	// there will be logn levels leading to O(nlogn) time. In worst case, the depth
	// of recursive tree can grow upto n times, which happens in case of sorted nums
	// array.
	// Space complexity - O(n), the size of recursive stack can go up n levels in
	// worst case. In average case which happens for balanced tree the size will be
	// logn for n elements in nums, leading to average space complexity of O(logn).
	public static TreeNode constructMaximumBinaryTreeDnQ(int[] nums) {
		return construct(nums, 0, nums.length);
	}

	private static TreeNode construct(int[] nums, int l, int r) {
		if (l == r) {
			return null;
		}

		int maxI = l;

		for (int i = l; i < r; i++) {
			if (nums[maxI] < nums[i]) {
				maxI = i;
			}
		}

		TreeNode node = new TreeNode(nums[maxI]);
		node.left = construct(nums, l, maxI);
		node.right = construct(nums, maxI + 1, r);

		return node;
	}

	// Divide and Conquer: Recursive solution
	// We use a recursive function construct which returns the maximum binary tree
	// of the elements in the range l to r(including).
	private static TreeNode constructMaximumBinaryTreeAltDnQAlt(int[] nums) {
		return constructAlt(nums, 0, nums.length - 1);
	}

	private static TreeNode constructAlt(int[] nums, int l, int r) {
		if (l > r) {
			return null;
		}

		int maxI = l;

		for (int i = l; i <= r; i++) {
			if (nums[maxI] < nums[i]) {
				maxI = i;
			}
		}

		TreeNode node = new TreeNode(nums[maxI]);
		node.left = constructAlt(nums, l, maxI - 1);
		node.right = constructAlt(nums, maxI + 1, r);

		return node;
	}

	// Monotonically Decreasing Stack
	// We don't need to find the max element, we process the array once. We maintain
	// a monotonically decreasing stack of tree nodes. For every new number:
	// > Create a node with this new number.
	// > Pop all smaller nodes.
	// > Make the last popped node as the left child of current node.
	// > If a larger node exists on the stack, the current node becomes its right
	// child.
	// > Push the current node into the stack.
	// Why it works:
	// For every node:
	// > The 1st greater element on its left remains in the stack.
	// > The 1st greater element on its right doesn't exists yet and this element
	// must be inserted before. Then if the later element is unable to pop this
	// element, then that element becomes the right child of this node.
	// At the end, the bottom of the stack is the root.
	// Time complexity - O(n) as each node is pushed and popped at most once.
	// Space compelxity - O(n), as the stack can grow max till O(n).
	private static TreeNode constructMaximumBinaryTreeMStack(int[] nums) {
		Deque<TreeNode> stack = new ArrayDeque<>();
//		Stack<TreeNode> stack = new Stack<>();

		for (int num : nums) {
			TreeNode current = new TreeNode(num);

			while (!stack.isEmpty() && stack.peek().val < num) {
				current.left = stack.pop();
			}

			if (!stack.isEmpty()) {
				stack.peek().right = current;
			}

			stack.push(current);
		}
		return stack.getLast(); // or stack.pollLast();
//		return stack.getFirst();
	}

	private static void postOrderTraversal(TreeNode node) {
		if (node != null) {
			postOrderTraversal(node.left);
			postOrderTraversal(node.right);
			System.out.print(node.val + " ");
		}
	}

	private static void inOrderTraversal(TreeNode node) {
		if (node != null) {
			inOrderTraversal(node.left);
			System.out.print(node.val + " ");
			inOrderTraversal(node.right);
		}
	}

	private static void preOrderTraversal(TreeNode node) {
		if (node != null) {
			System.out.print(node.val + " ");
			preOrderTraversal(node.left);
			preOrderTraversal(node.right);
		}
	}
}
