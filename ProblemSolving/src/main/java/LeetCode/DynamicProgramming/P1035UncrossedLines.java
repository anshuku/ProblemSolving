package LeetCode.DynamicProgramming;

import java.util.Arrays;

/*
 * P1035. Uncrossed Lines - Medium
 * 
 * You are given two integer arrays nums1 and nums2. We write the integers of 
 * nums1 and nums2 (in the order they are given) on two separate horizontal lines.
 * 
 * We may draw connecting lines: a straight line connecting two numbers nums1[i] and nums2[j] such that:
 * 
 * nums1[i] == nums2[j], and
 * the line we draw does not intersect any other connecting (non-horizontal) line.
 * 
 * Note that a connecting line cannot intersect even at the endpoints 
 * (i.e., each number can only belong to one connecting line).
 * 
 * Return the maximum number of connecting lines we can draw in this way.
 * 
 * Approach - DP
 */
public class P1035UncrossedLines {

	public static void main(String[] args) {
		int[] nums1 = { 2, 5, 1, 2, 5 };
		int[] nums2 = { 10, 5, 2, 1, 5, 2 };

//		int[] nums1 = { 1, 3, 7, 1, 7, 5 };
//		int[] nums2 = { 1, 9, 2, 5, 1 };

//		int[] nums1 = { 1, 4, 2 };
//		int[] nums2 = { 1, 2, 4 };

//		int[] nums1 = { 284, 986, 1094, 1985, 652, 434, 637, 467, 109, 1632, 336, 1522, 1200, 324, 1438, 869, 577, 1011,
//				475, 300, 1014, 1990, 555, 683, 668, 1660, 966, 154, 887, 1623, 1420, 385, 372, 1502, 243, 1418, 40,
//				1996, 1178, 1209, 360, 291, 1810, 456, 1565, 949, 171, 459, 1439, 1071, 527, 1180, 573, 753, 1912, 415,
//				570, 540, 1708, 772, 1294, 42, 1989, 944, 906, 370, 1231, 1178, 1400, 455, 1152, 1116, 1054, 189, 367,
//				239, 324, 423, 668, 391, 536, 1393, 1462, 781, 1883, 1795, 462, 202, 1578, 14, 759, 433, 60, 1430, 1387,
//				1288, 765, 392, 1734, 7, 1335, 1625, 701, 168, 746, 348, 266, 1301, 1512, 1151, 1949, 182, 1476, 217,
//				255, 812, 1060, 1685, 983, 1711, 1562, 1658, 1401, 1329, 1701, 1027, 129, 140, 35, 1098, 1334, 919, 277,
//				1645, 308, 800, 1551, 766, 140, 1075, 1931, 1895, 1823, 165, 1442, 1605, 1937, 1527, 1997, 1850, 1990,
//				733, 1486, 1653, 813, 1375, 1126, 1366, 1048, 1927, 1913, 1068, 311, 270, 598, 1099, 630, 1613, 1034,
//				780, 1181, 1143, 1010, 136, 435, 1957, 1216, 1161, 1164, 1638, 582, 112, 762, 1919, 241, 1463, 1805,
//				1345, 1721, 546, 1879, 731, 639, 780, 565, 1663, 574, 566, 692, 580 };
//		int[] nums2 = { 1703, 241, 410, 448, 928, 1780, 1054, 217, 1997, 928, 1865, 1404, 1152, 320, 352, 1882, 1561,
//				504, 143, 635, 1860, 880, 1394, 1112, 989, 1113, 829, 1474, 833, 1906, 449, 521, 860, 745, 159, 432, 99,
//				828, 1272, 172, 1213, 1174, 505, 1110, 1353, 1464, 1014, 962, 1442, 1887, 1960, 1796, 1836, 845, 1286,
//				812, 598, 286, 295, 484, 276, 606, 514, 1376, 1546, 630, 296, 1859, 281, 1903, 167, 1509, 1878, 1127,
//				1497, 1610, 172, 1704, 1032, 1344, 1679, 394, 1951, 456, 1660, 1709, 315, 1278, 370, 1687, 189, 1124,
//				172, 1907, 1249, 313, 1148, 848, 431, 503, 824, 664, 549, 1393, 1461, 1909, 166, 939, 646, 1149, 304,
//				480, 644, 1287, 1807, 1219, 1280, 1423, 1991, 75, 653, 1512, 1949, 423, 1980, 1642, 1537, 9, 905, 1830,
//				459, 1890, 133, 1837, 1478, 301, 1283, 1037, 20, 966, 382, 458, 1559, 888, 578, 1431, 268, 490, 1619,
//				731, 1621, 1127, 72, 148, 262, 1621, 644, 468, 907, 609, 544, 156, 446, 1541, 1182, 1357, 1714, 8, 1929,
//				383, 1524, 1298, 658, 562, 1397, 39, 1830, 1814, 254, 427, 1041, 1431, 1845, 342, 836, 1140, 386, 1279,
//				307, 296, 235, 551, 1862, 821, 1328, 1323, 783, 861, 1604, 179 };

		int maxLinesTabulation1D = maxUncrossedLinesTabulation1D(nums1, nums2);
		System.out.println("Tabulation 1d: The max number of connecting lines: " + maxLinesTabulation1D);

		int maxLinesTabulation2D = maxUncrossedLinesTabulation2D(nums1, nums2);
		System.out.println("Tabulation 2d: The max number of connecting lines: " + maxLinesTabulation2D);

		int maxLinesRecursive = maxUncrossedLinesRecursive(nums1, nums2);
		System.out.println("Recursive: The max number of connecting lines: " + maxLinesRecursive);
	}

	// Time complexity - O(m*n)
	// Space complexity - O(n)
	private static int maxUncrossedLinesTabulation1D(int[] nums1, int[] nums2) {
		int m = nums1.length;
		int n = nums2.length;
		int[] dp = new int[n + 1];
		for (int i = 0; i < m; i++) {
			int[] curr = new int[n + 1];
			for (int j = 0; j < n; j++) {
				if (nums1[i] == nums2[j]) {
					curr[j + 1] = 1 + dp[j];
				} else {
					curr[j + 1] = Math.max(dp[j + 1], curr[j]);
				}
			}
			dp = curr; // dp = curr.clone();
		}
		return dp[n]; // or return curr[m] if saved externally
	}

	// DP tabulation - Bottom Up
	// Build answers to subproblems iteratively first and then use it to solve
	// larger problems. dp[i][j] represents the maximum numbers of lines we can
	// draw by choosing first i numbers from nums1 and first j numbers from nums2.
	// Time complexity - O(m*n)
	// Space complexity - O(m*n)
	private static int maxUncrossedLinesTabulation2D(int[] nums1, int[] nums2) {
		int m = nums1.length;
		int n = nums2.length;
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (nums1[i] == nums2[j]) {
					dp[i + 1][j + 1] = 1 + dp[i][j];
				} else {
					dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
				}
			}
		}
		return dp[m][n];
	}

	// DP recursive - Top Down
	// Time complexity - O(m*n), m*n time is taken for initializing dp array,
	// there are m*n states to iterate over. The recursive function maybe called
	// multiple times to for each state, but due to memoization, each state is
	// computed only once.
	// Space complexity - O(m*n), for storing the dp array. The recursion stack can
	// grow to a maxiumum size of O(m+n). While creating the recursion tree, we see
	// that at each node two branches are formed(when last two numbers aren't same).
	// The recursion stack will only have one call out of the two branches. The
	// height of such a tree will be max(m,n) because at each level we're
	// decrementing the number of elements under consideration by one. Hence,
	// recursion stack will have maxiumum of O(m+n) elements.
	public static int maxUncrossedLinesRecursive(int[] nums1, int[] nums2) {
		int m = nums1.length;
		int n = nums2.length;
		int[][] dp = new int[m][n];
		for (int[] arr : dp) {
			Arrays.fill(arr, -1);
		}
		// solve(i, j) returns the maximum number of lines we can draw by choosing
		// the first i numbers from nums1 and first j numbers from nums2.
		return solve(nums1, nums2, m - 1, n - 1, dp);
	}

	private static int solve(int[] nums1, int[] nums2, int i, int j, int[][] dp) {
		if (i < 0 || j < 0) {
			return 0;
		}
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		// We add 1 to include the line between these two numbers and recursively
		// solve the problem ignoring the last number of both arrays.
		if (nums1[i] == nums2[j]) {
			dp[i][j] = 1 + solve(nums1, nums2, i - 1, j - 1, dp);
		} else {
			// otherwise recursively search for the maximum number of lines that can
			// be drawn ignoring the last number from each array. Take max of these two.
			dp[i][j] = Math.max(solve(nums1, nums2, i - 1, j, dp), solve(nums1, nums2, i, j - 1, dp));
		}
		return dp[i][j];
	}
}
