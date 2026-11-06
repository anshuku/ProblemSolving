package LeetCode.BinarySearchTree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/*
 * P1008. Construct Binary Search Tree from Preorder Traversal - Medium
 * 
 * Given an array of integers preorder, which represents the preorder traversal 
 * of a BST (i.e., binary search tree), construct the tree and return its root.
 * 
 * It is guaranteed that there is always possible to find a binary 
 * search tree with the given requirements for the given test cases.
 * 
 * A binary search tree is a binary tree where for every node, any 
 * descendant of Node.left has a value strictly less than Node.val, and any 
 * descendant of Node.right has a value strictly greater than Node.val.
 * 
 * A preorder traversal of a binary tree displays the value of the 
 * node first, then traverses Node.left, then traverses Node.right.
 * 
 * Constraints:
 * > 1 <= preorder.length <= 100
 * > 1 <= preorder[i] <= 1000
 * > All the values of preorder are unique.
 * 
 * Approach - Recursion, Monotonic Stack, Inorder array
 */
public class P1008ConstructBinarySearchTreeFromPreorderTraversal {

	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	public static void main(String[] args) {
		int[] preorder = { 8, 5, 1, 7, 10, 12 };

		TreeNode bstFromPreorderRecursion = bstFromPreorderRecursion(preorder);
		System.out.println("Recursion: The Binary Search Tree from preorder traversal is: ");
//		inOrderTraversal(bstFromPreorderRecursion);
		bfsOrderTraversal(bstFromPreorderRecursion);
		System.out.println();

		TreeNode bstFromPreorderMStack = bstFromPreorderMStack(preorder);
		System.out.println("Monotonic Stack: The Binary Search Tree from preorder traversal is: ");
//		inOrderTraversal(bstFromPreorderRecursion);
		bfsOrderTraversal(bstFromPreorderMStack);
		System.out.println();

		TreeNode bstFromPreorderInorder = bstFromPreorderInorder(preorder);
		System.out.println("Inorder: The Binary Search Tree from preorder traversal is: ");
//		inOrderTraversal(bstFromPreorderInorder);
		bfsOrderTraversal(bstFromPreorderInorder);
		System.out.println();

		TreeNode bstFromPreorderInorderIndexArray = bstFromPreorderInorderIndexArray(preorder);
		System.out.println("Index Array: The Binary Search Tree from preorder traversal is: ");
//		inOrderTraversal(bstFromPreorderInorder);
		bfsOrderTraversal(bstFromPreorderInorderIndexArray);
		System.out.println();

		TreeNode bstFromPreorderInorderAlt = bstFromPreorderInorderAlt(preorder);
		System.out.println("Inorder Alt: The Binary Search Tree from preorder traversal is: ");
//		inOrderTraversal(bstFromPreorderInorder);
		bfsOrderTraversal(bstFromPreorderInorderAlt);
	}

	// Recursion
	// In previous inorder approach, the inorder array was used to check if the
	// element could be placed in this subtree. Here, we use BST property to verify
	// with the help of lower and upper elements for each element. This helps avoid
	// inorder traversal and reduces time.
	// We use upper and lower limits initially as -ve and +ve inifinity as one can
	// always place the root (from preorder)
	// Time complexity - O(n), as we visit each node exactly once.
	// Space complexity - O(n), for the entire tree construction via recursion.
	public static TreeNode bstFromPreorderRecursion(int[] preorder) {
		int n = preorder.length;
		index = 0;
		return bstFromPreorderRecursion(preorder, n, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	static int index = 0;

	private static TreeNode bstFromPreorderRecursion(int[] preorder, int n, int min, int max) {
		// If all the preorder elements got used up then the tree is constructed.
		if (index == n) {
			return null;
		}

		int val = preorder[index];

		// If current value is not in the right limits we return null as it doesn't
		// meets the BST requirements.
		if (val < min || val > max) {
			return null;
		}

		index++; // must increment post value validation

		// If current value is in limits, we place it with root = TreeNode(val)
		TreeNode root = new TreeNode(val);

		// We then proceed to construct recursively the left and right subtrees.
		root.left = bstFromPreorderRecursion(preorder, n, min, val);
		root.right = bstFromPreorderRecursion(preorder, n, val, max);

		return root;
	}

	// Monotonic stack
	// The recursion can be converted to iteration with help of a monotonically
	// decreasing stack.
	// We pick the 1st preorder element as the root element and push it into stack.
	// Then we iterate from the next element onwards of preorder array. We pick the
	// last element of stack as parent node and current element of preorder as child
	// node. We then adjust the parent node by popping out of stack all elements
	// with the value smaller than the child value. At the same time, we change the
	// parent node at each pop, node = stack.pop(). Post this, if node.val <
	// child.val - we put the child as a right child of the node: node.right =
	// child. Else we put it as a left child: node.left = child. After this, we put
	// the child node into the stack. We return root at the end.
	// Time complexity - O(n), as we visit each node exactly once.
	// Space complexity - O(n), to keep the stack and the tree.
	private static TreeNode bstFromPreorderMStack(int[] preorder) {
		int n = preorder.length;

		TreeNode root = new TreeNode(preorder[0]);

		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);

		for (int i = 1; i < n; i++) {
			// We take the last element of the stack/deque as a parent
			TreeNode node = stack.peek();
			// Create a child from next preorder element
			TreeNode child = new TreeNode(preorder[i]);

			// Adjust the parent
			while (!stack.isEmpty() && stack.peek().val < child.val) {
				node = stack.pop();
			}

			// Follow BST logic to create a parent-child link
			if (node.val < child.val) {
				node.right = child;
			} else { // Add the child into deque/deque
				node.left = child;
			}

			stack.push(child);
		}

		return root;
	}

	// Preorder + Inorder Traversal
	// We construct inorder array from preorder array by sorting it. We peek
	// elements one by one from preorder array and try to put them as left or right
	// child if possible. If we can't place then put null as child and proceed. This
	// possibility of using an element as a child is checked via an inorder array.
	// If the inorder array contains no elements for this subtree, then the element
	// couldn't be used here and one should use null as a child.
	// Why global instance variable (prone to concurrent thread inconsitencies)?
	// We need to share the updated preorder index between the left and right
	// recursive calls. So, if we pass preIndex as a primitive int in the recursive
	// calls, then Java passes it by values. Each recursive call gets its own copy.
	// So, while populating root.left via getBST(left, index, 1), where 1 is updated
	// preIndex from 1st call. Once it finishes and comes back to original calling
	// 1st method, then right subtree get preIndex = 1, which leads to overwriting
	// of values. Hence, where the left recursive call finishes, the updated
	// preIndex inside that call is not reflected in the parent method.
	// So root.right = getBST(index + 1, right, 1) starts again from index 1 instead
	// of continuing from next unprocessed preorder element. With preIndex as a
	// global instance variable, all recursive calls share the same variable. The
	// left subtree advances the same preIndex that the right subtree subsequently
	// uses. Using global variable keeps the recursion clean.
	// When recursive calls need to consume elements sequentially from a common
	// traversal, they must share the traversal pointer or explicitly return its
	// updated value. This pattern is useful in many tree construction problems,
	// including preorder/inorder and preorder/postorder construction.
	// Why do we use the root's inorder index to divide the array into left and
	// right subtrees?
	// We need information from both preorder and inorder to reconstruct the tree.
	// For a BST, the traversals are:
	// Preorder (Root -> Left -> Right) we always pick the root first
	// Inorder (Left -> Root -> Right) one can know the left and right subtrees,
	// So, Preorder tells us which node to create next with preIndex support
	// Inorder tells where the left subtree ends and right subtree begins.
	// We use the inorder index to divide the subtrees.
	// With Preorder array we create the roots ([8, 5, 1, 7, 10, 12]) say 8 and with
	// inorder array ([1, 5, 7, 8, 10, 12]) we know what the left and right subtree
	// are. Here, in inorder array, everything before 8 is left subtree [1, 5, 7]
	// and everything after 8 belongs to its right subtree [10, 12]. The index
	// obtained from inorder map int index = idxMap.get(rootVal); tells us the
	// boundaries of the 2 subtrees.
	// We can't use preorder array to get the index to divide the subtrees as it
	// only tells the visiting sequence. It doesn't directly tells one how many
	// nodes belong to the left and right subtree.
	// Preorder array, Inorder array and global preIndex works together.
	// preorder - provides the next root to construct.
	// inorder - identifies the boundaries of left and right subtrees.
	// indexMap - finds the root's position in inorder array.
	// preIndex - tracks which preorder element must be consumed next
	// left, right - defines the current subtree's inorder range.
	// Preorder tells which is the root
	// Inorder tells who belongs in the root's left and right subtrees.
	// Global preIndex tells which node should be constructed.
	// These 3 work together to construct entire Binary Search Tree.
	// Note - Sorting preorder to create inorder works as the input is a BST with
	// distinct values. Inorder traversal of a BST is sorted, so sorting preorder
	// gives the same inorder sequence.
	// Time complexity - O(nlogn), where O(nlogn) to sort the preorder array and
	// O(n) to construct the binary tree via recursion.
	// Space complexity - O(n) for the inorder traversal, creating index map and the
	// tree.
	private static TreeNode bstFromPreorderInorder(int[] preorder) {
		int n = preorder.length;
		preIndex = 0;

		int[] inorder = Arrays.copyOf(preorder, n);
		Arrays.sort(inorder);

		Map<Integer, Integer> map = new HashMap<>();

		// The root's(preorder[preIdx]) inorder index is used to divide the array into
		// left and right subtrees
		for (int i = 0; i < n; i++) {
			map.put(inorder[i], i);
		}

		return bstFromPreorderInorder(0, n - 1, preorder, map);
	}

	// Picks root from preorder. We start from 1st preorder element
	static int preIndex = 0;

	private static TreeNode bstFromPreorderInorder(int start, int end, int[] preorder, Map<Integer, Integer> map) {
		// If there is no element to construct subtrees
		if (start > end) {
			return null;
		}

		// We use preIndex element as a root.
		int rootVal = preorder[preIndex++];

		TreeNode root = new TreeNode(rootVal);

		// Root splits inorder list into left and right subtrees.
		int index = map.get(rootVal);

		root.left = bstFromPreorderInorder(start, index - 1, preorder, map);
		root.right = bstFromPreorderInorder(index + 1, end, preorder, map);

		return root;
	}

	// Preorder + Inorder Traversal + PreIndex array
	// We can use an array to maintain the index as arrays are reference types in
	// Java. This allows all recursive calls to share the same array object, so
	// updating preIndex[0] is visible to every call.
	private static TreeNode bstFromPreorderInorderIndexArray(int[] preorder) {
		int n = preorder.length;

		int[] inorder = Arrays.copyOf(preorder, n);
		Arrays.sort(inorder);

		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			map.put(inorder[i], i);
		}

		return bstFromPreorderInorderIndexArray(0, n - 1, preorder, map, new int[] { 0 });
	}

	private static TreeNode bstFromPreorderInorderIndexArray(int start, int end, int[] preorder,
			Map<Integer, Integer> map, int[] preIndex) {
		if (start > end) {
			return null;
		}

		int rootVal = preorder[preIndex[0]++];

		TreeNode root = new TreeNode(rootVal);

		int index = map.get(rootVal);

		root.left = bstFromPreorderInorderIndexArray(start, index - 1, preorder, map, preIndex);
		root.right = bstFromPreorderInorderIndexArray(index + 1, end, preorder, map, preIndex);

		return root;
	}

	private static TreeNode bstFromPreorderInorderAlt(int[] preorder) {
		int n = preorder.length;

		preIndex = 0;
		int[] inorder = Arrays.copyOf(preorder, n);

		Arrays.sort(inorder);

		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < n; i++) {
			map.put(inorder[i], i);
		}

		return bstFromPreorderInorderAlt(0, n, map, preorder);
	}

	private static TreeNode bstFromPreorderInorderAlt(int start, int end, Map<Integer, Integer> map, int[] preorder) {
		if (start >= end) {
			return null;
		}

		int rootVal = preorder[preIndex++];

		TreeNode root = new TreeNode(rootVal);

		int index = map.get(rootVal);

		root.left = bstFromPreorderInorderAlt(start, index, map, preorder);
		root.right = bstFromPreorderInorderAlt(index + 1, end, map, preorder);

		return root;
	}

	private static void bfsOrderTraversal(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);

		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				TreeNode node = queue.poll();
				System.out.print(node.val + " ");

				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
	}

	private static void inOrderTraversal(TreeNode root) {
		if (root != null) {
			inOrderTraversal(root.left);
			System.out.print(root.val + " ");
			inOrderTraversal(root.right);
		}

	}
}
