package LeetCode.DynamicProgramming;

/*
 * P221. Maximal Square - Medium
 * 
 * Given an m x n binary matrix filled with 0's and 1's, find 
 * the largest square containing only 1's and return its area.
 * 
 * Approach - DP Matrix, Brute Force - Matrix traversal
 */
public class P221MaximalSquare {

	public static void main(String[] args) {
//		char[][] matrix = { { '1', '0', '1', '0', '0' }, { '1', '0', '1', '1', '1' }, { '1', '1', '1', '1', '1' },
//				{ '1', '0', '0', '1', '0' } };
//		char[][] matrix = { { '0', '1' },{ '1', '0' } };

		char[][] matrix = { { '1', '1', '1', '1', '1' }, { '1', '1', '1', '1', '1' }, { '0', '0', '0', '0', '0' },
				{ '1', '1', '1', '1', '1' }, { '1', '1', '1', '1', '1' } };

		int maxSquare1DDP1Var = maximalSquare1DDP1Var(matrix);
		System.out.println("1D DP 1Var: The maximum square present is: " + maxSquare1DDP1Var);

		int maxSquare1DDP = maximalSquare1DDP(matrix);
		System.out.println("1D DP: The maximum square present is: " + maxSquare1DDP);

		int maxSquare2DDP = maximalSquare2DDP(matrix);
		System.out.println("2D DP: The maximum square present is: " + maxSquare2DDP);

		int maxSquareBF = maximalSquareBF(matrix);
		System.out.println("Brute Force: The maximum square present is: " + maxSquareBF);
	}

	// Time complexity - O(m*n)
	// Space complexity - O(m)
	private static int maximalSquare1DDP1Var(char[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		int[] dp = new int[n + 1];
		int maxSquare = 0, prev = 0;
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				int temp = dp[j];// will act as dp[i-1][j-1]
				if (matrix[i - 1][j - 1] == '1') {
					dp[j] = Math.min(prev, Math.min(dp[j], dp[j - 1])) + 1;
					maxSquare = Math.max(maxSquare, dp[j]);
				} else {
					// othewise dp[i][j] will become dp[i-1][j]
					// even if the value should be 0
					dp[j] = 0;
				}
				prev = temp;
			}
		}
		return maxSquare * maxSquare;
	}

	// Time complexity - O(m*n)
	// Space complexity - O(m)
	private static int maximalSquare1DDP(char[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		int[] dp = new int[n + 1];
		int maxSquare = 0;
		for (int i = 1; i <= m; i++) {
			int[] curr = new int[n + 1];
			for (int j = 1; j <= n; j++) {
				if (matrix[i - 1][j - 1] == '1') {
					curr[j] = Math.min(curr[j - 1], Math.min(dp[j], dp[j - 1])) + 1;
					maxSquare = Math.max(maxSquare, curr[j]);
				}
			}
			dp = curr;
		}
		return maxSquare * maxSquare;
	}

	// Time complexity - O(m*n)
	// Space complexity - O(m*n)
	private static int maximalSquare2DDP(char[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		int[][] dp = new int[m + 1][n + 1];
		int maxLen = 0;
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (matrix[i - 1][j - 1] == '1') {
					dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
					maxLen = Math.max(maxLen, dp[i][j]);
				}
			}
		}
		return maxLen * maxLen;
	}

	// Time complexity - O((mn)^2) in worst case all numbers are 1.
	// Space complexity - O(1) as no extra space is used.
	public static int maximalSquareBF(char[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		int maxSquare = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == '1') {
					int len = 1;
					boolean flag = true;
					while (i + len < m && j + len < n && flag) {
						// moving right from len's row down
						for (int k = j; k <= j + len; k++) {
							if (matrix[i + len][k] == '0') {
								flag = false;
								break;
							}
						}
						// moving down from len's col right
						for (int k = i; k <= i + len; k++) {
							if (matrix[k][j + len] == '0') {
								flag = false;
								break;
							}
						}
						if (flag) {
							len++;
						}
					}
					maxSquare = Math.max(maxSquare, len);
				}
			}
		}
		return maxSquare * maxSquare;
	}

}
