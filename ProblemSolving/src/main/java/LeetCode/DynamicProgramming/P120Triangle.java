package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P120. Triangle - Medium
 * 
 * Given a triangle array, return the minimum path sum from top to bottom.
 * 
 * For each step, you may move to an adjacent number of the row below. More formally, if you are 
 * on index i on the current row, you may move to either index i or index i + 1 on the next row.
 * 
 * Approach - DP
 * 
 * In Place algorithms are not useful in multi threaded environments
 * No Exclusice access - Another thread might not expect the array to be modified.
 * Exclusice access - The array might be resued later or another thread might access it
 * post the lock has been released.
 * 
 * Better to keep the input list/array as read only.
 * 
 * Collections.min(Collection) gives the minimum out the given list.
 */
public class P120Triangle {

	public static void main(String[] args) {

		List<List<Integer>> triangle = new ArrayList<>();
		triangle.add(new ArrayList<>());
		triangle.get(0).add(2);
		triangle.add(new ArrayList<>());
		triangle.get(1).add(3);
		triangle.get(1).add(4);
		triangle.add(new ArrayList<>());
		triangle.get(2).add(6);
		triangle.get(2).add(5);
		triangle.get(2).add(7);
		triangle.add(new ArrayList<>());
		triangle.get(3).add(4);
		triangle.get(3).add(1);
		triangle.get(3).add(8);
		triangle.get(3).add(3);

//		List<List<Integer>> triangle = new ArrayList<>();
//		triangle.add(new ArrayList<>());
//		triangle.get(0).add(-2);

		int minTotal2DArr = minimumTotalRecursive2DArr(triangle);
		System.out.println("2D Array: The minimum path sum for triangle is: " + minTotal2DArr);

		int minTotal1DDP = minimumTotal1DDP(triangle);
		System.out.println("1D DP: The minimum path sum for triangle is: " + minTotal1DDP);

		int minTotal2DDP = minimumTotal2DDP(triangle);
		System.out.println("2D DP: The minimum path sum for triangle is: " + minTotal2DDP);

		int minTotalArr = minimumTotalRecursiveArr(triangle);
		System.out.println("Array of List: The minimum path sum for triangle is: " + minTotalArr);

		int minTotalList = minimumTotalRecursiveList(triangle);
		System.out.println("List of List: The minimum path sum for triangle is: " + minTotalList);

		int minTotal2ListsRev = minimumTotal2ListsRev(triangle);
		System.out.println("2 Lists Reverse: The minimum path sum for triangle is: " + minTotal2ListsRev);

		int minTotalInPlaceRev = minimumTotalInPlaceRev(triangle);
		System.out.println("In Place Reverse: The minimum path sum for triangle is: " + minTotalInPlaceRev);

		int minTotal2Lists = minimumTotal2Lists(triangle);
		System.out.println("2 Lists: The minimum path sum for triangle is: " + minTotal2Lists);

		int minTotalInPlace = minimumTotalInPlace(triangle);
		System.out.println("In Place: The minimum path sum for triangle is: " + minTotalInPlace);

		int minTotalMapMemo = minimumTotalMapMemo(triangle);
		System.out.println("Map Memo: The minimum path sum for triangle is: " + minTotalMapMemo);
	}

	private static int minimumTotalRecursive2DArr(List<List<Integer>> triangle) {
		int n = triangle.size();
		int[][] dp = new int[n][n];
		for (int[] arr : dp) {
			Arrays.fill(arr, Integer.MAX_VALUE);
		}
		return recursive(0, 0, n, triangle, dp);
	}

	private static int recursive(int i, int j, int n, List<List<Integer>> triangle, int[][] dp) {
		if (i == n - 1) {
			return triangle.get(i).get(j);
		}
		if (dp[i][j] != Integer.MAX_VALUE) {
			return dp[i][j];
		}
		int mid = recursive(i + 1, j, n, triangle, dp);
		int right = recursive(i + 1, j + 1, n, triangle, dp);
		return dp[i][j] = triangle.get(i).get(j) + Math.min(mid, right);
	}

	private static int minimumTotal1DDP(List<List<Integer>> triangle) {
		int n = triangle.size();
		int[] dp = new int[n + 1];
		for (int i = n - 1; i >= 0; i--) {
			int[] curr = new int[i + 1];
			for (int j = 0; j <= i; j++) {
				curr[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
			}
			dp = curr;
		}
		return dp[0];
	}

	private static int minimumTotal2DDP(List<List<Integer>> triangle) {
		int n = triangle.size();
		int[][] dp = new int[n + 1][n + 1];
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j <= i; j++) {
				dp[i][j] = triangle.get(i).get(j) + Math.min(dp[i + 1][j], dp[i + 1][j + 1]);
			}
		}
		return dp[0][0];
	}

	private static int minimumTotalRecursiveArr(List<List<Integer>> triangle) {
		int n = triangle.size();
		List<Integer>[] dp = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			dp[i] = new ArrayList<>();
			for (int j = 0; j <= i; j++) {
				dp[i].add(Integer.MAX_VALUE);
			}
		}
		return recursive(0, 0, n, triangle, dp);
	}

	private static int recursive(int i, int j, int n, List<List<Integer>> triangle, List<Integer>[] dp) {
		if (i == n - 1) {
			return triangle.get(i).get(j);
		}
		if (dp[i].get(j) != Integer.MAX_VALUE) {
			return dp[i].get(j);
		}
		int mid = recursive(i + 1, j, n, triangle, dp);
		int right = recursive(i + 1, j + 1, n, triangle, dp);
		int val = triangle.get(i).get(j) + Math.min(mid, right);
		dp[i].add(j, val);
		return val;
	}

	public static int minimumTotalRecursiveList(List<List<Integer>> triangle) {
		int n = triangle.size();
		List<List<Integer>> dp = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			dp.add(new ArrayList<>());
			for (int j = 0; j <= i; j++) {
				dp.get(i).add(Integer.MAX_VALUE);
			}
		}
		return recurive(0, 0, n, triangle, dp);
	}

	private static int recurive(int i, int j, int n, List<List<Integer>> triangle, List<List<Integer>> dp) {
		// Not needed since the size of further row list increases.
//		if(j == n) {
//			return Integer.MAX_VALUE;
//		}
		if (i == n - 1) {
			return triangle.get(i).get(j);
		}
		if (dp.get(i).get(j) != Integer.MAX_VALUE) {
			return dp.get(i).get(j);
		}
		int mid = recurive(i + 1, j, n, triangle, dp);
		int right = recurive(i + 1, j + 1, n, triangle, dp);
		int val = triangle.get(i).get(j) + Math.min(mid, right);
		dp.get(i).set(j, val); // dp.get(i).add(j, val)
		return val;
	}

	private static int minimumTotal2ListsRev(List<List<Integer>> triangle) {
		int n = triangle.size();
		List<Integer> dp = triangle.get(n - 1);
		for (int i = n - 2; i >= 0; i--) {
			List<Integer> curr = new ArrayList<>();
			for (int j = 0; j <= i; j++) {
				int sum = triangle.get(i).get(j) + Math.min(dp.get(j), dp.get(j + 1));
				curr.add(sum);
			}
			dp = curr;
		}
		return dp.get(0);
	}

	private static int minimumTotalInPlaceRev(List<List<Integer>> triangle) {
		int n = triangle.size();
		for (int i = n - 2; i >= 0; i--) {
			for (int j = 0; j <= i; j++) {
				int sum = triangle.get(i).get(j) + Math.min(triangle.get(i + 1).get(j), triangle.get(i + 1).get(j + 1));
				triangle.get(i).set(j, sum);
			}
		}
		return triangle.get(0).get(0);
	}

	// Time complexity - O(n^2)
	// Space complexity - O(n)
	private static int minimumTotal2Lists(List<List<Integer>> triangle) {
		int n = triangle.size();
		List<Integer> prev = triangle.get(0);
		for (int i = 1; i < n; i++) {
			List<Integer> curr = new ArrayList<Integer>();
			for (int j = 0; j <= i; j++) {
				int minSum = Integer.MAX_VALUE;
				if (j > 0) {
					minSum = prev.get(j - 1);
				}
				if (j < i) {
					minSum = Math.min(minSum, prev.get(j));
				}
				int sum = triangle.get(i).get(j) + minSum;
				curr.add(sum);// curr.add(j, sum);
			}
			prev = curr;
		}
		return Collections.min(prev);
	}

	// Time complexity - O(n^2)
	// Space complexity - O(1) since it's in place
	private static int minimumTotalInPlace(List<List<Integer>> triangle) {
		int n = triangle.size();
		for (int i = 1; i < n; i++) {
			for (int j = 0; j <= i; j++) {
				int minSum = Integer.MAX_VALUE;
				if (j > 0) {
					minSum = triangle.get(i - 1).get(j - 1);
				}
				if (j < i) {
					minSum = Math.min(minSum, triangle.get(i - 1).get(j));
				}
				int pathSum = triangle.get(i).get(j) + minSum;
				triangle.get(i).set(j, pathSum);
			}
		}
		return Collections.min(triangle.get(n - 1));
	}

	static Map<String, Integer> memo;

	// Time complexity - O(n^2) for n^2 cells
	// Space complexity - O(n^2) for n^2 cells
	private static int minimumTotalMapMemo(List<List<Integer>> triangle) {
		memo = new HashMap<>();
		int n = triangle.size();
		return recursive(0, 0, n, triangle);
	}

	private static int recursive(int i, int j, int n, List<List<Integer>> triangle) {
		String key = i + ":" + j;
		if (memo.containsKey(key)) {
			return memo.get(key);
		}
		int path = triangle.get(i).get(j);
		if (i < n - 1) {
			path += Math.min(recursive(i + 1, j, n, triangle), recursive(i + 1, j + 1, n, triangle));
		}
		memo.put(key, path);
		return path;
	}

}
