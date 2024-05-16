package LeetCode.Trees;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Stream;

public class P111MinimumDepthBinaryTree {

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
		root.right.left.left = new TreeNode(7);

//		root.left = new TreeNode(2);
//		root.left.left = new TreeNode(3);
//		root.left.left.left = new TreeNode(4);
//		root.left.left.left.left = new TreeNode(5);
//		root.left.left.left.left.left = new TreeNode(6);

		P111MinimumDepthBinaryTree pdb = new P111MinimumDepthBinaryTree();

		int minDepth = pdb.minDepth(root);

//		int minDepth = pdb.minDepthLeftRight(root);

//		int minDepth = pdb.minDepthBfs(root);

//		int minDepth = pdb.minDepthStream(root);

		System.out.println("The minimum depth for the tree is " + minDepth);
	}

	// Return value captures current depth
	public int minDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int left = minDepth(root.left);
		int right = minDepth(root.right);
		if (left == 0) {
			return right + 1;
		} else if (right == 0) {
			return left + 1;
		} else if (left < right) {
			return left + 1;
		} else if (left > right) {
			return right + 1;
		} else {// For equality case
			return left + 1;
		}
	}

	// Return value captures current min depth and current value captures current depth
	private int minDepthLeftRight(TreeNode root) {
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return 1;
		}
		if (root.left == null) {
			return minDepthLeftRight(root.right) + 1;
		}
		if (root.right == null) {
			return minDepthLeftRight(root.left) + 1;
		}
		int l = minDepthLeftRight(root.left) + 1;
		int r = minDepthLeftRight(root.right) + 1;
		return Math.min(l, r);
	}

	// Most optimized time complexity
	private int minDepthBfs(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int depth = 1;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				TreeNode node = queue.poll();
				if (node.left == null && node.right == null) {
					return depth;
				}
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}
			depth++;
		}
		return depth;
	}
	
	int depth = 1;

	private int minDepthStream(TreeNode root) {
		if (root == null) {
			return 0;
		}
		long count = Stream.iterate(List.of(root), this::hasNext, this::next).count();
		System.out.println("The count for streaming nodes is " + count);
		return depth;
	}

	boolean hasNext(List<TreeNode> list) {
		if (list.stream().filter(Objects::nonNull).anyMatch(node -> node.left == null && node.right == null)) {
			return false;
		}
		if (list.stream().anyMatch(Objects::nonNull)) {
			depth++;
			return true;
		} else {
			return false;
		}
	}

	List<TreeNode> next(List<TreeNode> list) {
		return list.stream().flatMap(this::flat).toList();

	}

	Stream<TreeNode> flat(TreeNode node) {
		if (node == null) {
			Stream.empty();
		}
		return Stream.of(node.left, node.right);
	}
}
