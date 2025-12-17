package LeetCode.BinarySearch;

/*
 * P240. Search a 2D Matrix II - Medium
 * 
 * Write an efficient algorithm that searches for a value target in an 
 * m x n integer matrix matrix. This matrix has the following properties:
 * 
 * > Integers in each row are sorted in ascending from left to right.
 * > Integers in each column are sorted in ascending from top to bottom.
 * 
 * Approach - Binary Search, Divide and Conquer, Search space reduction
 */
public class P240Search2DMatrixII {

	public static void main(String[] args) {
		int[][] matrix = { { 1, 4, 7, 11, 15 }, { 2, 5, 8, 12, 19 }, { 3, 6, 9, 16, 22 }, { 10, 13, 14, 17, 24 },
				{ 18, 21, 23, 26, 30 } };
		int target = 5;
//		int target = 20;

//		int[][] matrix = { { 1, 1 } };
//		int target = 2;

//		int[][] matrix = { { -1, 3 } };
//		int target = 3;

		boolean isPresentSearchSpace = searchMatrixSearchSpace(matrix, target);
		System.out.println("Search Space: The given target is present in the matrix: " + isPresentSearchSpace);

		boolean isPresentBSDnC = searchMatrixBSDnC(matrix, target);
		System.out.println("D and C BS: The given target is present in the matrix: " + isPresentBSDnC);

		boolean isPresentDnC = searchMatrixDnC(matrix, target);
		System.out.println("D and C: The given target is present in the matrix: " + isPresentDnC);

		boolean isPresentBS = searchMatrixBS(matrix, target);
		System.out.println("BS: The given target is present in the matrix: " + isPresentBS);
	}

	// Search space reduction
	// As the rows and columns are sorted from left to right and top to bottom
	// respectively, we can prune m(1 column) or n(1 row) elements while comparing
	// the target. Algo: We use (row, col) initialized with (m, 0) pointing at
	// bottom left of the matrix. Then, until we find target and return true
	// otherwise, if the currently pointed value is larger than target we move one
	// row "up" as it's lesser. Otherwise, if current value is smaller than target
	// we move one column "right". This happens because we the rows are sorted from
	// left to right and the columns are sorted top to bottom.
	// We can also start from top right, but other two corners won't give result as
	// pruning row/col might give wrong result.
	// Time complexity - O(m + n), one every iteration when we don't return true,
	// either row or col is decremented/incremented by 1. Since row can be
	// decremented m times and col can be incremented n times, the loop can't run
	// for more than n+m iterations.
	// Space complexity - O(1)
	private static boolean searchMatrixSearchSpace(int[][] matrix, int target) {
		int m = matrix.length - 1;
		int n = matrix[0].length - 1;
		int row = m;
		int col = 0;
		while (row >= 0 && col <= n) {
			if (matrix[row][col] == target) {
				return true;
			} else if (matrix[row][col] < target) {
				col++;
			} else {
				row--;
			}
		}
		return false;
	}

	// Divide and Conquer - Binary Search
	private static boolean searchMatrixBSDnC(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}

		int m = matrix.length - 1;
		int n = matrix[0].length - 1;

		return divideAndConquerBS(matrix, target, 0, n, 0, m);
	}

	// Time complexity - we assume n ~ m and |matrix| = n^2 = x for
	// applying master's method. Runtime of D and C as a recurrence relation:
	// T(x) = 2*T(x/4) + log(rt(x)) // rt(x) = n
	private static boolean divideAndConquerBS(int[][] matrix, int target, int left, int right, int up, int down) {
		if (left > right || up > down) {
			return false;
		}
		if (target < matrix[up][left] || target > matrix[down][right]) {
			return false;
		}
		int midCol = left + (right - left) / 2;
		// u is the lower bound row and at the end it'll be the insertion point just
		// after the required row.
		int u = up;
		int d = down;
		// We perform column wise binary search for the mid column to identify split
		// point via u. This divides matrix into 2 valid monotonic submatrices.
		while (u <= d) {
			int midRow = u + (d - u) / 2;
			if (matrix[midRow][midCol] == target) {
				return true;
			}
			if (matrix[midRow][midCol] < target) {
				u = midRow + 1;
			} else {
				d = midRow - 1;
			}
		}
		// u is the first row where target < matrix[u][midCol]
		return divideAndConquerBS(matrix, target, left, midCol - 1, u, down)
				|| divideAndConquerBS(matrix, target, midCol + 1, right, up, u - 1);
	}

	// Divide and Conquer
	// We can divide the sorted 2D matrix into 4 sorted submatrices, 2 of which
	// might contain target and 2 will not. This algo is recursive and needs base
	// and recursive cases.
	// Base case: For a 2D array, there are 2 ways to discard if element is present:
	// 1) the matrix has 0 area or it contains no element. 2) target < matrix
	// smallest(top left corner) or target > matrix greatest(bottom right corner).
	// Recursive Case: If base condition is not met, the array has positive area and
	// target maybe present. We seek along the matrix's middle column for an index
	// row such that matrix[row - 1][mid] < target < matrix[row][mid]. If we find
	// target during the process, we return true. The existing matrix can be
	// partitioned into four submatrices around this row index. The top-left and
	// bottom-right submatrices cannot contain target(base case) so we can discard.
	// The bottom-left and top-right submatrices are sorted 2-d matrices, we
	// recursively apply this algo to these 2.
	// Time complexity - O(nlogn), we assume n ~ m and |matrix| = n^2 = x for
	// applying master's method. Runtime of D and C as a recurrence relation:
	// T(x) = 2*T(x/4) + rt(x) // rtx = n o O(n)
	// The first term 2*T(x/4), as we recurse on 2 submatrices of rough 1/4 size,
	// while rt(x) comes from time spent seeking along a O(n) length column for the
	// partition point. The master method variable, a=2; b=4; c=0.5, log b (a) = c.
	// So recurrence falls under case 2 of master method
	// T(x) = O(x^c * logx) = O(n*log(n^2)) = O(n*logn)
	// Space complexity - O(logn), we use recursion which uses memory proportional
	// to the height of its recursion tree. Since this approach discards half of
	// matrix at each level of recursion and makes 2 calls, the tree height < logn.
	private static boolean searchMatrixDnC(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}

		int m = matrix.length - 1;
		int n = matrix[0].length - 1;

		return divideAndConquer(matrix, target, 0, n, 0, m);
	}

	private static boolean divideAndConquer(int[][] matrix, int target, int left, int right, int up, int down) {
		// IF the submatrix has no height or no width
		if (left > right || up > down) {
			return false;
		}
		// Target is smaller than smallest or larget than largest element in submatrix
		// Prune impossible ranges
		if (target < matrix[up][left] || target > matrix[down][right]) {
			return false;
		}
		// Find row such that matrix[row-1][mid] < target < matrix[row][mid]
		int row = up;
		int mid = left + (right - left) / 2; // middle column
		while (row <= down && target >= matrix[row][mid]) {
			if (target == matrix[row][mid]) {
				return true;
			}
			row++;
		}
		// Recursively search the possible 2 submatrices
		return divideAndConquer(matrix, target, left, mid - 1, row, down)
				|| divideAndConquer(matrix, target, mid + 1, right, up, row - 1);
	}

	// Binary Search
	// We iterate over the matrix diagonals by scanning the diagonal index i, until
	// limit = min(m, n) via binary search. We iterate over the diagonals.
	// For each diagonal i, binary search row i and binary search col i. This works
	// since each row is sorted left -> right and each col is sorted top -> bottom.
	// For vertical true, we search across the rows and for false we search across
	// the columns.
	// Time complexity - O(log(n!)) or O(min(m,n)*log(max(m,n))). The main loop runs
	// for min(m, n) iterations where m i s rows and n is cols. On each iteration,
	// we perform 2 binary searches on array slices of length m-i and n-i. So each
	// iteration of loop runs in O(log(m-i) + log(n-i) where i = current iteration.
	// It becomes O(2*log(n-i)) = O(log(n-i) in worst case n ~ m. When m >> n, n
	// will dominate m in asymptotic analysis. When summing the runtimes of all
	// iterations, we get: O(logn + O(log(n-1) + log(n-2) +.. + log(1)) = O(log(n!))
	// log(n!) = O(nlogn). As there are n terms, each no greater than logn. So
	// asymptotic runtime is certainly no worse than O(nlogn) algorithm.
	// Space complexity - O(1)
	public static boolean searchMatrixBS(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}

		int m = matrix.length;
		int n = matrix[0].length;

		int limit = Math.min(m, n);

		for (int i = 0; i < limit; i++) {
			// search row i
			if (binarySearch(matrix, target, i, true)) {
				return true;
			}
			// search col i
			if (binarySearch(matrix, target, i, false)) {
				return true;
			}
		}

		return false;
	}

	private static boolean binarySearch(int[][] matrix, int target, int start, boolean vertical) {
		int left = start;
		int right = vertical ? matrix[0].length - 1 : matrix.length - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (vertical) {// search the row = start
				if (matrix[start][mid] == target) {
					return true;
				} else if (matrix[start][mid] < target) {
					left = mid + 1;
				} else {
					right = mid - 1;
				}
			} else {// search the column = start
				if (matrix[mid][start] == target) {
					return true;
				} else if (matrix[mid][start] < target) {
					left = mid + 1;
				} else {
					right = mid - 1;
				}
			}
		}

		return false;
	}

}
