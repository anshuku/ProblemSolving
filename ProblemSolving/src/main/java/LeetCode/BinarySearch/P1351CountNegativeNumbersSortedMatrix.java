package LeetCode.BinarySearch;

/*
 * P1351. Count Negative Numbers in a Sorted Matrix - Easy
 * 
 * Given a m x n matrix grid which is sorted in non-increasing order both 
 * row-wise and column-wise, return the number of negative numbers in grid.
 * 
 * Approach - Binary Search reverse, Linear Traversal
 */
public class P1351CountNegativeNumbersSortedMatrix {

	public static void main(String[] args) {
		int[][] grid = { { 4, 3, 2, -1 }, { 3, 2, 1, -1 }, { 1, 1, -1, -2 }, { -1, -1, -2, -3 } };
//		int[][] grid = { { 3, 2 }, { 1, 0 } };

		int negativesLinearTraverse = countNegativesLinearTraverse(grid);
		System.out.println("Linear Traverse: number of negative numbers in the grid are " + negativesLinearTraverse);

		int negativesBinarySearch = countNegativesBinarySearch(grid);
		System.out.println("Binary Search: number of negative numbers in the grid are " + negativesBinarySearch);

	}

	// Linear Traversal
	// The problem states that the number are sorted both row wise and column wise
	// This means if the ith row has first negative number at index x, then the
	// first negative number for (i+1)th row can never be greater than x.
	// It means if we know the index of the first negative element of any row,
	// then the next row's first negative element will be present at the same or
	// left side of the previous row's first negative element's index.
	// Hence we traverse right to left in each row starting from previous row's 1st
	// negative element's index to find the current row's first negative element's
	// index.
	// Time complexity - O(m+n), at worst case we iterate from one row and one
	// column or m+n elements of the matrix. In other words, if we start from top
	// right corner of the grid, we can only move left or down. We cannot move more
	// than m+n times without exiting the grid.
	// Space complexity - O(1)
	private static int countNegativesLinearTraverse(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;
		// Index which gives the current rows index with last positive value
		int currRowNegatives = n - 1;
		int negatives = 0;

		for (int i = 0; i < m; i++) {
			while (currRowNegatives >= 0 && grid[i][currRowNegatives] < 0) {
				currRowNegatives--;
			}
			negatives += n - currRowNegatives - 1;
		}
		return negatives;
	}

	// Upper bound reverse Binary Search
	// 4 3 2 -1
	// -1 2 3 4
	//
	// -1 -1 -2 -3
	// -3 -2 -1 -1
	//
	// -1 -2 -2 -3
	// -3 -2 -2 -1
	// We use binary search and reduce search space [left, right]:
	// > If the element at the mid-position is non-negative then it means elements
	// from left to mid all are non negative so search in right side of mid
	// start = mid + 1 -> right. Here key is 0. Otherwise if the element at mid is
	// negative, it means elements from mid to right all are negative so we can
	// discard those, first negative element will be present in elements from left
	// to mid.
	// Time complexity - O(m*logn)
	// Space complexity - O(1)
	public static int countNegativesBinarySearch(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;
		int negatives = 0;

		for (int i = 0; i < m; i++) {
			int[] arr = grid[i];
			int start = 0;
			int end = n - 1;
			int key = -1;

			while (start <= end) {
				int mid = start + (end - start) / 2;
				if (arr[mid] <= key) {
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			}

			negatives += n - start;
		}

		return negatives;
	}

}
