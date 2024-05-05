package LeetCode.Trees;

/*
 * Time Complexity: O(n)
 * Space Complexity: O(height)
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class P257BinaryTreePaths {

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
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);

		List<String> list = binaryTreePath(root);

//		List<String> list = binaryTreePathDFS(root);

//		List<String> list = binaryTreePathSB(root);

		System.out.println("The list is " + list);
	}

	private static List<String> binaryTreePathSB(TreeNode node) {
		StringBuilder str = new StringBuilder();
		List<String> list = new ArrayList<>();
		traverseSB(node, list, str);
		return list;
	}

	private static void traverseSB(TreeNode node, List<String> list, StringBuilder sb) {
		if (node == null) {
			return;
		}
		int len = sb.length();
		sb.append(node.val);
		if(node.left == null && node.right == null) {
			list.add(sb.toString());
		} else {
			sb.append("->");
			traverseSB(node.left, list, sb);
			traverseSB(node.right, list, sb);
		}
		sb.setLength(len);
	}

	private static List<String> binaryTreePath(TreeNode node) {
		List<String> list = new ArrayList<>();
		traverse(node, list, "");
		return list;
	}

	private static void traverse(TreeNode node, List<String> list, String str) {
		if (node == null) {
			return;
		}
		str += node.val + "->";

		if (node.left == null && node.right == null) {
			list.add(str.substring(0, str.length() - 2));
		} else {
			traverse(node.left, list, str);
			traverse(node.right, list, str);
		}
	}

	private static List<String> binaryTreePathDFS(TreeNode node) {

		List<String> list = new ArrayList<>();
		List<Integer> currPath = new ArrayList<>();

		traverseDfs(list, currPath, node);

		return list;
	}

	private static void traverseDfs(List<String> list, List<Integer> currPath, TreeNode node) {
		currPath.add(node.val);

		if (node.left != null) {
			traverseDfs(list, currPath, node.left);
		}
		if (node.right != null) {
			traverseDfs(list, currPath, node.right);
		}
		if (node.left == null && node.right == null) {
			list.add(toPath(currPath));
		}
		//Remove last integer in curr path (number and not ->)
		currPath.remove(currPath.size() - 1);
	}

	private static String toPath(List<Integer> currPath) {
		String str = "" + currPath.get(0);
		for (int i = 1; i < currPath.size(); i++) {
			str = str.concat("->").concat(String.valueOf(currPath.get(i)));
		}
		return str;
	}

	private static String stringBuilderPath(List<Integer> currPath) {
		StringBuilder str = new StringBuilder();
		str.append("" + currPath.get(0));
		for (int i = 1; i < currPath.size(); i++) {
			str.append("->").append(currPath.get(i));
		}
		return str.toString();
	}

}
