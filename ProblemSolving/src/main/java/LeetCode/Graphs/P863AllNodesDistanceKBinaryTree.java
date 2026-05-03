package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * P863. All Nodes Distance K in Binary Tree - Medium
 * 
 * Given the root of a binary tree, the value of a target node target, and an integer k, 
 * return an array of the values of all nodes that have a distance k from the target node.
 * 
 * You can return the answer in any order.
 * 
 * Approach - N-Ary Tree: DFS, BFS, HashMap Graph
 */
public class P863AllNodesDistanceKBinaryTree {

	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	static class Node {
		int val;
		Node left;
		Node right;
		Node parent;

		Node(int val) {
			this.val = val;
		}
	}

	static List<Integer> distanceKNodes;
	static Node kNode;

	static Map<Integer, List<Integer>> graph;

	public static void main(String[] args) {
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(5);
		root.right = new TreeNode(1);
		root.left.left = new TreeNode(6);
		root.left.right = new TreeNode(2);
		root.right.left = new TreeNode(0);
		root.right.right = new TreeNode(8);
		root.left.right.left = new TreeNode(7);
		root.left.right.right = new TreeNode(4);

		TreeNode target = root.left;
		int k = 2;

//		TreeNode root = new TreeNode(1);
//		TreeNode target = root;
//		int k = 3;

//		TreeNode root = new TreeNode(0);
//		root.right = new TreeNode(1);
//		root.right.right = new TreeNode(2);
//		root.right.right.right = new TreeNode(3);
//		root.right.right.right.right = new TreeNode(4);
//		TreeNode target = root;
//		int k = 2;

		List<Integer> distanceKNodesParent = distanceKParent(root, target, k);
		System.out.println("Parent Pointers: The nodes at distance K is: " + distanceKNodesParent);

		List<Integer> distanceKNodesDFS = distanceKDFS(root, target, k);
		System.out.println("DFS: The nodes at distance K is: " + distanceKNodesDFS);

		List<Integer> distanceKNodesDFSAlt = distanceKDFSAlt(root, target, k);
		System.out.println("DFS Alt: The nodes at distance K is: " + distanceKNodesDFSAlt);

		List<Integer> distanceKNodesBFS = distanceKBFS(root, target, k);
		System.out.println("BFS: The nodes at distance K is: " + distanceKNodesBFS);

	}

	// Implementing Parent Pointers: Undirected graph
	// For the tree, each node only has pointers to its left and right child nodes,
	// making the tree traversal only applicable to the subtree rooted at the target
	// node. To accss the parent. We need to establish additional connection beyond
	// the child node pointers. We implement a parent pointer to each node(apart
	// from the root node) recursively.
	// Time complexity - O(n), addParent and dfs/bfs calls to process the left and
	// right subtrees of the current node curr. Each node is visited once.
	// Space complexity - O(n), visited stores a max of O(n) nodes. The recursive
	// solution uses the call stack to keep track of the current subtree being
	// processed. The maximum depth of the call stack is equal to the height of the
	// given tree. In worst case, the given binary tree may be a degenerate binary
	// tree and the stack can hold upto n calls.
	public static List<Integer> distanceKParent(TreeNode root, TreeNode target, int k) {
		distanceKNodes = new ArrayList<>();
		if (k == 0) {
			distanceKNodes.add(target.val);
			return distanceKNodes;
		}

		Node rootNode = new Node(root.val);
		rootNode = prepareNodes(root, rootNode, target.val);

		addParent(rootNode, null);

//		bfs(k);

		Set<Node> visited = new HashSet<>();
		dfs(kNode, visited, k);

		return distanceKNodes;
	}

	private static void dfs(Node node, Set<Node> visited, int k) {
		if (node == null || visited.contains(node)) {
			return;
		}

		visited.add(node);

		if (k == 0) {
			distanceKNodes.add(node.val);
			return;
		}

		dfs(node.left, visited, k - 1);
		dfs(node.right, visited, k - 1);
		dfs(node.parent, visited, k - 1);

	}

	private static void bfs(int k) {
		Queue<Node> queue = new LinkedList<>();
		queue.offer(kNode);

		Set<Node> visited = new HashSet<>();
		visited.add(kNode);

		int distance = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();

			while (size-- > 0) {
				Node newNode = queue.poll();

				if (distance == k) {
					distanceKNodes.add(newNode.val);
					continue;
				}
				if (newNode.left != null && !visited.contains(newNode.left)) {
					queue.offer(newNode.left);
					visited.add(newNode.left);
				}
				if (newNode.right != null && !visited.contains(newNode.right)) {
					queue.offer(newNode.right);
					visited.add(newNode.right);
				}
				if (newNode.parent != null && !visited.contains(newNode.parent)) {
					queue.offer(newNode.parent);
					visited.add(newNode.parent);
				}
			}
			distance++;
		}

	}

	private static Node prepareNodes(TreeNode root, Node node, int target) {
		if (root == null) {
			return node;
		}

		if (node.val == target) {
			kNode = node;
		}

		if (root.left != null) {
			node.left = new Node(root.left.val);
			prepareNodes(root.left, node.left, target);
		}

		if (root.right != null) {
			node.right = new Node(root.right.val);
			prepareNodes(root.right, node.right, target);
		}
		return node;
	}

	private static void addParent(Node root, Node parent) {
		if (root == null) {
			return;
		}
		root.parent = parent;
		addParent(root.left, root);
		addParent(root.right, root);
	}

	// DFS on Equivalent Graph
	// We transform the given binary tree into an equivalent graph, where each
	// pointer is treated as an undirected edge. The graph retains all the connected
	// nodes from the original binary tree, including pointers from children to
	// parents. We can then perfrom a regular search in this graph - DFS. In the
	// equivalent graph, we only need to recursively visit all unvisited nodes of
	// the current node, which include nodes that are equivalent to the left and
	// right children and the parent in the original tree. We use a hash set to keep
	// track of all the visited nodes. Whenever we find an unvisited neighbor node,
	// we addd it to hash set so it won't be visited anymore.
	// Time complexity - O(n), where n = number of nodes in the binary tree.
	// buildGraph method recursively calls itself to process the left and right
	// subtrees of the current node curr. Each node is visited once. dfs method
	// recursively calls itself to process the unvisited neighbors of the current
	// node curr. Each node is visited once.
	// Space complexity - O(n), we use hash map graph to store all n-1 edges, O(n).
	// A hash set visited records the visited nodes, which takes O(n) in worst case.
	// The recusive dfs uses the call stack to keep track of the current subtree
	// being proccessed. The max depth of the call stack is equal to the height of
	// the given tree. In worst case where there is degenerate binary tree, the call
	// stack can hold upto n calls, resulting in O(n) space.
	private static List<Integer> distanceKDFS(TreeNode root, TreeNode target, int k) {
		distanceKNodes = new ArrayList<>();
		if (k == 0) {
			distanceKNodes.add(target.val);
			return distanceKNodes;
		}

		graph = new HashMap<>();

		buildGraph(root, null);

		Set<Integer> visited = new HashSet<>();

		dfs(target.val, k, visited);
		return distanceKNodes;
	}

	// Recursively build the undirected graph from the given binary tree.
	private static void buildGraph(TreeNode node, TreeNode parent) {
		if (node != null && parent != null) {
			graph.computeIfAbsent(parent.val, k -> new ArrayList<>()).add(node.val);
			graph.computeIfAbsent(node.val, k -> new ArrayList<>()).add(parent.val);
//			int curVal = node.val, parentVal = parent.val;
//			graph.putIfAbsent(curVal, new ArrayList<>());
//			graph.putIfAbsent(parentVal, new ArrayList<>());
//			graph.get(curVal).add(parentVal);
//			graph.get(parentVal).add(curVal);
		}

		if (node.left != null) { // if (node != null && node.left != null)
			buildGraph(node.left, node);
		}
		if (node.right != null) {
			buildGraph(node.right, node);
		}
	}

	private static void dfs(int target, int k, Set<Integer> visited) {
		if (visited.contains(target)) {
			return;
		}

		visited.add(target);
		if (k == 0) {
			distanceKNodes.add(target);
			return;
		}

		for (int neighbor : graph.getOrDefault(target, new ArrayList<>())) {
			if (!visited.contains(neighbor)) {
				dfs(neighbor, k - 1, visited);
			}
		}

	}

	private static List<Integer> distanceKDFSAlt(TreeNode root, TreeNode target, int k) {
		distanceKNodes = new ArrayList<>();

		if (k == 0) {
			distanceKNodes.add(target.val);
			return distanceKNodes;
		}

		graph = new HashMap<>();

		buildGraph(root, null);

		Set<Integer> visited = new HashSet<>();
		visited.add(target.val);

		dfsAlt(target.val, k, visited);

		return distanceKNodes;
	}

	private static void dfsAlt(int node, int k, Set<Integer> visited) {
		if (k == 0) {
			distanceKNodes.add(node);
			return;
		}

		for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
			if (!visited.contains(neighbor)) {
				visited.add(neighbor);
				dfsAlt(neighbor, k - 1, visited);
			}
		}
	}

	// BFS on Equivalent Graph
	// We transform the given binary tree into an equivalent graph, where each
	// pointer is treated as an undirected edge. The graph retains all the connected
	// nodes from the original binary tree, including pointers from children to
	// parents. We can then perfrom a regular search in this graph - BFS. We start
	// with the node target with distance = 0, then we mark all its unvisited
	// neighbor nodes with distance = 1 to be visited soon, once we visit a node
	// with distance = 1, we mark all its unvisited neighbor nodes with distance = 2
	// as well and so on. We use a queue to store all nodes to be visited without
	// mixing the order as it follows FIFO order. This allows one to explore all
	// nodes with the current distance to the target node, before moving on to nodes
	// with larger distances. Start from target, d = 0, then Nodes with d = 1, then
	// Nodes with d = 2. We use a hash set to keep track of all the visited nodes.
	// Whenever we find an unvisited neighbor node, we add it to the hash set so it
	// won't be visited anymore.
	// Time complexity - O(n), where n = number of nodes in the binary tree.
	// buildGraph method recursively calls itself to process the left and right
	// subtrees of the current node curr. Each node is visited once.
	// In a typical BFS, the time taken is O(V+E) where V = vertices and E = edges.
	// There are n nodes and n-1 edges in the binary tree. Each node is added to the
	// queue and popped from the queue once,it takes O(n) to handle all nodes.
	// Space complexity - O(n), we use hash map graph to store all n-1 edges, O(n).
	// A hash set visited records the visited nodes, which takes O(n) in worst case.
	// There maybe upto n nodes stored in queue. Hence overall space is O(n).
	private static List<Integer> distanceKBFS(TreeNode root, TreeNode target, int k) {
		distanceKNodes = new ArrayList<>();
		if (k == 0) {
			distanceKNodes.add(target.val);
			return distanceKNodes;
		}

		graph = new HashMap<>();

		buildGraph(root, null);

		bfs(target, k);
		return distanceKNodes;
	}

	private static void bfs(TreeNode target, int k) {
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(target.val);

		Set<Integer> visited = new HashSet<>();
		visited.add(target.val);

		int distance = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();

			while (size-- > 0) {
				int node = queue.poll();

				// If the current nodes is at a distance of k from target node, add it to the
				// answer and continue to next node.
				if (distance == k) {
					distanceKNodes.add(node);
					continue;
				}

				// Add all unvisited neighbours of the current node to the queue.
				for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
					if (!visited.contains(neighbor)) {
						queue.offer(neighbor);
						visited.add(neighbor);
					}
				}
			}
			distance++;
		}
	}

}
