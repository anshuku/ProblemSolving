package LeetCode.Trees;

import java.util.ArrayList;
import java.util.List;

public class P94BinaryTreeInorderTraversal {
	
	static class TreeNode{
		
		int val;
		TreeNode left;
		TreeNode right;
		
		TreeNode(){
			
		}
		
		TreeNode(int val){
			this.val = val;
		}
		
		TreeNode(int val, TreeNode left, TreeNode right){
			this.val = val;
			this.left = left;
			this.right = right;
		}
		
	}
	
	static List<Integer> list = new ArrayList<>();
	
	public static void main(String [] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		List<Integer> list = inorderTraversal(root);
//		List<Integer> listNew = inorderTraversalNew(root);
		
		System.out.println("In order traversal " + list);
//		System.out.println("In order traversal " + listNew);
	}
	
	private static List<Integer> inorderTraversalNew(TreeNode node) {
		
		traversal(node);
		return list;
	}

	private static void traversal(TreeNode node) {
		if(node == null) {
			return;
		}
		inorderTraversalNew(node.left);
		list.add(node.val);
		inorderTraversalNew(node.right);
	}

	public static List<Integer> inorderTraversal(TreeNode node) {
        if (node != null) {
            inorderTraversal(node.left);
            list.add(node.val);
            inorderTraversal(node.right);
        }
        return list;
    }
	

}
