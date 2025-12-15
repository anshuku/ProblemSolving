package LeetCode.BinarySearch;

/*
 * P74. Search a 2D Matrix - Medium
 * 
 * You are given an m x n integer matrix matrix with the following two properties:
 * > Each row is sorted in non-decreasing order.
 * > The first integer of each row is greater than the last integer of the previous row.
 * 
 * Given an integer target, return true if target is in matrix or false otherwise.
 * 
 * You must write a solution in O(log(m * n)) time complexity.
 */
public class P74Search2DMatrix {

	public static void main(String[] args) {
		int[][] matrix = { { 1, 3, 5, 7 }, { 10, 11, 16, 20 }, { 23, 30, 34, 60 } };
		int target = 3;

		boolean isPresent = searchMatrix(matrix, target);
		System.out.println("The given target is present in the matrix: " + isPresent);
	}

	// Binary Search - matrix
	// Input matrix m*n is considered as a sorted array of length m*n.
	// This sorted array allows binary search as the element index in this virtual
	// array can be easily transformed into the row and col in initial matrix.
	// row = idx/n and col = idx%n.
	// Time complexity - O(log(m*n))
	// Space complexity - O(1)
	public static boolean searchMatrix(int[][] matrix, int target) {
		int m = matrix.length;
		int n = matrix[0].length;

		int start = 0;
		int end = m * n - 1;

		while (start <= end) {
			int mid = start + (end - start) / 2;
			int row = mid / n;
			int col = mid % n;
			if (matrix[row][col] == target) {
				return true;
			} else if (matrix[row][col] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return false;
	}
}
