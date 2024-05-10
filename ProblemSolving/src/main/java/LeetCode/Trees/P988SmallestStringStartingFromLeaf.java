package LeetCode.Trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class P988SmallestStringStartingFromLeaf {

	static String smallestString = "";
	// chs array stores the smallest string starting from leaf.
	static char[] chs = null;
	// l is lowest bound for chs array.
	static int l = 0;

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
//		TreeNode root = new TreeNode(1);
//		root.left = new TreeNode(2);
//		root.right = new TreeNode(3);
//		root.left.left = new TreeNode(4);
//		root.left.right = new TreeNode(5);
//		root.right.left = new TreeNode(6);
//		root.right.left.left = new TreeNode(7);
//		root.right.left.right = new TreeNode(2);
//		root.right.left.left.right = new TreeNode(1);
//		root.right.left.left.right.left = new TreeNode(2);

//		TreeNode root = new TreeNode(3);
//		root.left = new TreeNode(2);
//		root.left.left = new TreeNode(2);
//		root.left.left.left = new TreeNode(1);
//		root.left.left.right = new TreeNode(2);
//		root.left.left.right.left = new TreeNode(1);
//		root.left.right = new TreeNode(3);
//		root.left.right.left = new TreeNode(2);
//		root.left.right.left.right = new TreeNode(2);
//		root.left.right.left.right.left = new TreeNode(1);
//		root.right = new TreeNode(2);
//		root.right.left = new TreeNode(2);
//		root.right.left.left = new TreeNode(2);
//		root.right.left.left.right = new TreeNode(1);
//		root.right.left.left.right.left = new TreeNode(1);
//		root.right.right = new TreeNode(2);
//		root.right.right.left = new TreeNode(1);
//		root.right.right.left.left = new TreeNode(1);
//		root.right.right.left.left.left = new TreeNode(1);
//		root.right.right.right = new TreeNode(1);

		TreeNode root = new TreeNode(2);
		root.left = new TreeNode(2);
		root.left.left = new TreeNode(1);
		root.left.left.left = new TreeNode(0);
		root.right = new TreeNode(1);
		root.right.right = new TreeNode(0);

//		String val = smallestStringStartingFromLeaf(root);

//		String val = smallestStringStartingFromLeafString(root);

//		String val = smallestStringStartingFromLeafStringBuilder(root);

		String val = smallestStringStartingFromLeafCharArr(root);

		System.out.println("The smallest String starting from leaf is " + val);
	}

	private static String smallestStringStartingFromLeaf(TreeNode root) {
		List<String> list = new ArrayList<>();
		List<Character> charList = new ArrayList<>();
		smallestStringStartingFromLeafDfs(list, charList, root);
		System.out.println("The list is " + list);

		Collections.sort(list);
		return list.get(0);
	}

	private static void smallestStringStartingFromLeafDfs(List<String> list, List<Character> charList, TreeNode node) {
		if (node == null) {
			return;
		}
		charList.add((char) ('a' + node.val));
		if (node.left == null && node.right == null) {
			list.add(getStringFromChars(charList));
			charList.remove(charList.size() - 1);
			return;
		}
		smallestStringStartingFromLeafDfs(list, charList, node.left);
		smallestStringStartingFromLeafDfs(list, charList, node.right);
		charList.remove(charList.size() - 1);
	}

	private static String getStringFromChars(List<Character> charList) {
		StringBuilder sb = new StringBuilder();
		for (int i = charList.size() - 1; i >= 0; i--) {
			sb.append(charList.get(i));
		}
		return sb.toString();
	}

	private static String smallestStringStartingFromLeafString(TreeNode root) {

		smallestStringStartingFromLeafStringDfs(root, "");
		return smallestString;
	}

	private static void smallestStringStartingFromLeafStringDfs(TreeNode node, String currString) {
		if (node == null) {
			return;
		}
		currString = (char) ('a' + node.val) + currString;
		if (node.left == null && node.right == null) {
			if (smallestString.isEmpty() || currString.compareTo(smallestString) < 0) {
				smallestString = currString;
			}
		}
		smallestStringStartingFromLeafStringDfs(node.left, currString);
		smallestStringStartingFromLeafStringDfs(node.right, currString);
	}

	// Fastest since there is StringBuilder
	private static String smallestStringStartingFromLeafStringBuilder(TreeNode root) {
		StringBuilder result = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		smallestStringStartingFromLeafStringBuilderDfs(root, result, sb);
		return result.toString();
	}

	private static void smallestStringStartingFromLeafStringBuilderDfs(TreeNode node, StringBuilder result,
			StringBuilder sb) {
		if (node == null) {
			return;
		}
		sb.insert(0, (char) ('a' + node.val));
		if (node.left == null && node.right == null) {
			if (result.isEmpty() || result.compareTo(sb) > 0) {
				result.setLength(0);
				result.insert(0, sb.toString());
			}
		}
		smallestStringStartingFromLeafStringBuilderDfs(node.left, result, sb);
		smallestStringStartingFromLeafStringBuilderDfs(node.right, result, sb);
		sb.deleteCharAt(0);
	}

	private static String smallestStringStartingFromLeafCharArr(TreeNode root) {
		int depth = depth(root);
		System.out.println("The depth is " + depth);
		smallestStringStartingFromLeafCharArrDfs(root, new char[depth], depth - 1);
		return new String(chs, l, depth - l);
	}

	private static int depth(TreeNode node) {
		if (node == null) {
			return 0;
		}
		return Math.max(depth(node.left), depth(node.right)) + 1;
	}

	private static void smallestStringStartingFromLeafCharArrDfs(TreeNode node, char[] t, int i) {
		if (node == null) {
			return;
		}
		t[i] = (char) ('a' + node.val);
		if (node.left == null && node.right == null) {
			if (chs == null) {
				chs = Arrays.copyOf(t, t.length);
				l = i;
			} else {
				for (int k = 0, d = Math.max(l, i); k + d < t.length; k++) {
					if (chs[l + k] < t[i + k]) {
						return;
					} else if (chs[l + k] > t[i + k]) {
						chs = Arrays.copyOf(t, t.length);
						l = i;
						return;
					}
				}
				if (l < i) {
					chs = Arrays.copyOf(t, t.length);
					l = i;
				}
			}
			return;
		}
		smallestStringStartingFromLeafCharArrDfs(node.left, t, i - 1);
		smallestStringStartingFromLeafCharArrDfs(node.right, t, i - 1);
	}
}
