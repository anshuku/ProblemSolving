package LeetCode.Trees;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class P2331EvaluateBooleanBinaryTree {
	static class TreeNode {

		int val;
		TreeNode left;
		TreeNode right;

		public TreeNode() {
			super();
		}

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
		TreeNode root = new TreeNode(2);
		root.left = new TreeNode(3);
		root.right = new TreeNode(1);
		root.left.left = new TreeNode(0);
		root.left.right = new TreeNode(1);

		P2331EvaluateBooleanBinaryTree peb = new P2331EvaluateBooleanBinaryTree();

		boolean evaluate = peb.evaluateTree(root);

//		boolean evaluate = peb.evaluateTreeVar(root);

//		boolean evaluate = peb.evaluateTreeTernary(root);

//		boolean evaluate = peb.evaluateTreeStack(root);
		System.out.println("The evaluated Binary tree has value " + evaluate);

	}

	public boolean evaluateTree(TreeNode node) {
		if (node.val == 0 || node.val == 1) {
			return node.val == 1;
		} else if (node.val == 2) {
			return evaluateTree(node.left) || evaluateTree(node.right);
		} else if (node.val == 3) {
			return evaluateTree(node.left) && evaluateTree(node.right);
		}
		return false;
	}

	// Space optimized
	private boolean evaluateTreeVar(TreeNode root) {
		// Handles the case for leaf node
		if (root.left == null) {
			return root.val == 1;
		}
		var l = evaluateTreeVar(root.left);
		var r = evaluateTreeVar(root.right);

		boolean evaluate;
//		if (root.val == 2) {
//			return l || r;
//		} else {
//			return l && r;
//		}

		if (root.val == 2) {
			evaluate = l || r;
		} else {
			evaluate = l && r;
		}
		return evaluate;
	}

	// Most space optimized
	private boolean evaluateTreeTernary(TreeNode root) {
		if (root.left != null || root.right != null) {
			return root.val == 2 ? evaluateTreeTernary(root.left) || evaluateTreeTernary(root.right)
					: evaluateTreeTernary(root.left) && evaluateTreeTernary(root.right);
		} else {
			return root.val == 0 ? false : true;
		}
	}

	private boolean evaluateTreeStack(TreeNode root) {
		Stack<TreeNode> st = new Stack<>();
		st.push(root);
		Map<TreeNode, Boolean> evaluated = new HashMap<>();
		while (!st.empty()) {
			TreeNode topNode = st.peek();
			// if the top node is a leaf node then insert it into evaluated map,
			// pop the node from stack and continue
			if (topNode.left == null && topNode.right == null) {
				st.pop();
				evaluated.put(topNode, topNode.val == 1);
				continue;
			}
			// if left and right children are evaluated then evaluate the current node and
			// pop it from the stack
			if (evaluated.containsKey(topNode.left) && evaluated.containsKey(topNode.right)) {
				st.pop();
				if (topNode.val == 2) {
					evaluated.put(topNode, evaluated.get(topNode.left) || evaluated.get(topNode.right));
				} else {
					evaluated.put(topNode, evaluated.get(topNode.left) && evaluated.get(topNode.right));
				}
			} else {
				// if the map does not have the child nodes and the node is not leaf node,
				// add both the left and right children to stack.
				// peek means the current top node remains in the stack.
				st.push(topNode.left);
				st.push(topNode.right);
			}
		}
		return evaluated.get(root);
	}

}
