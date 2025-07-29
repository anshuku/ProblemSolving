package LeetCode.DynamicProgramming;

/*
 * P96. Unique Binary Search Trees - Medium
 * 
 * Given an integer n, return the number of structurally unique BST's (binary search trees) 
 * which has exactly n nodes of unique values from 1 to n.
 * 
 * Approach - DP, Mathematical Deduction
 */
public class P96UniqueBinarySearchTrees {

	public static void main(String[] args) {
//		int n = 1;
		int n = 3;
//		int n = 2;

		int numTreesMaths = numTreesMaths(n);
		System.out.println("Mathematical: The number of structurally unique BSTs: " + numTreesMaths);

		int numTreesDP = numTreesDP(n);
		System.out.println("DP: The number of structurally unique BSTs: " + numTreesDP);
	}

	// Mathematical deduction - Catalan Numbers
	// The number of structurally unique BSTs with n node is given by Cn
	// Cn = Catalan Numbers = [1/(n+1)]*2nCn.
	// C0 = 1
	// Cn+1 = 2*(2i+1)/(i+2)*Cn.
	// Time complexity - O(n)
	// Space complexity - O(1)
	private static int numTreesMaths(int n) {
		// To prevent overflow
		long G = 1;
		for (int i = 0; i < n; i++) {
			G = G * 2 * (2 * i + 1) / (i + 2);
		}
		return (int) G;
	}

	// DP - G(n) and F(i, n)
	// G(n) is number of unique BSTs with n nodes.
	// F(i, n) is number of unique BSTs with i as root, 1<=i<=n.
	// Number of unique BST G(n) is sum of BST F(i,n), enumerating each number i as
	// root 1<=i<=n. G(n) = Sum F(i,n) for i = 1 to n.
	// Base case, there is only 1 combination to construct a BST out of sequence of
	// length 1(only root) and nothing(empty tree). G(0) = 1 and G(1) = 1.
	// F(3, 7) to construct unique BST with 3 as root. Create subtree from 1,2 (G2)
	// and create right subtree 4,5,6,7(G4). Combine the two via cartesian product.
	// F(3,7) = G(2) * G(4).
	// F(i,n) = G(i-1)*G(n-i) or product of number of BSTs possible from left and
	// right child. Left subtree has i-1 nodes and right subtree has n-i nodes.
	// G(n) = Sum G(i-1)*G(n-i) for i = 1 to n
	// Time complexity - O(n^2) for computing G(n) or sum i = 2 to n for G(n)
	// Space complexity - O(N) for G(n)
	public static int numTreesDP(int n) {
		int[] G = new int[n + 1];
		G[0] = 1;
		G[1] = 1;
		for (int i = 2; i <= n; i++) {
			for (int j = 1; j <= i; j++) {
				G[i] += G[j - 1] * G[i - j];
			}
		}
		return G[n];
	}

}
