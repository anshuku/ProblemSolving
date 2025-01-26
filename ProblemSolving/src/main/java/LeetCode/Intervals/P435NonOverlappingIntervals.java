package LeetCode.BitManipulation;

import java.util.Arrays;
import java.util.LinkedList;

/*
 * P435. Non-overlapping Intervals - Medium
 * 
 * Given an array of intervals intervals where intervals[i] = [starti, endi], return the 
 * minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
 * 
 * Note that intervals which only touch at a point are non-overlapping. 
 * For example, [1, 2] and [2, 3] are non-overlapping.
 * 
 * Approach - Sorting, Greedy | Sorting, DP | Sorting, Memo, Recursion
 */
public class P435NonOverlappingIntervals {

	public static void main(String[] args) {

//		int[][] intervals = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 3 } };

		int[][] intervals = { { 37375, 49899 }, { 22258, 44640 }, { -14767, -3918 }, { -34539, -7567 },
				{ -47784, -17138 }, { -48074, 26945 }, { -25572, -23190 }, { -14760, -13789 }, { -37611, 22403 },
				{ 35753, 39064 }, { -3275, 3621 }, { 47510, 47751 }, { -29030, 13243 }, { -718, 12308 },
				{ -39291, 30751 }, { 47889, 48037 }, { 28383, 34407 }, { 37651, 43232 }, { -29917, -11872 },
				{ 38693, 43349 }, { -17552, -9121 }, { 837, 43449 }, { -25759, -21905 }, { -40574, -14652 },
				{ 10631, 21853 }, { -68, 33036 }, { 32790, 35116 }, { 7454, 39101 }, { 45963, 49238 }, { 32301, 44934 },
				{ 20463, 31792 }, { -33077, 17782 }, { -18705, 49086 }, { 46339, 49046 }, { -11219, 48610 },
				{ -35115, 25649 }, { -32719, 13205 }, { -12085, 14025 }, { 12256, 33338 }, { 14804, 23412 },
				{ 18284, 23055 }, { -38277, -14703 }, { 14789, 16385 }, { 12329, 16121 }, { -18717, 43059 },
				{ 29583, 47767 }, { -39615, 41782 }, { 30372, 31126 }, { 22904, 24541 }, { 41137, 46230 },
				{ 37545, 42951 }, { -19078, 21851 }, { 27550, 42976 }, { -20655, 47404 }, { -9774, 24359 },
				{ 1790, 14394 }, { -10713, 24293 }, { 24448, 48237 }, { -27587, 992 }, { -11505, 31335 },
				{ -17125, 13320 }, { 36507, 41160 }, { -22153, -4399 }, { -34700, 27080 }, { 18647, 33264 },
				{ 17849, 47534 }, { -14233, 32412 }, { 30566, 47190 }, { -4810, 29051 }, { 34116, 46701 },
				{ -2004, 14808 }, { -14940, 25300 }, { -12065, 16826 }, { -45380, -38216 }, { -17894, 48619 },
				{ 22661, 26170 }, { 44859, 46786 }, { 21000, 32244 }, { -72, 7426 }, { -28302, 27088 },
				{ -8253, 28475 }, { -41445, -39231 }, { -30760, -577 }, { -44632, 43796 }, { -23423, -15493 },
				{ -12034, 11580 }, { 41634, 44963 }, { 23608, 28359 }, { 39263, 45688 }, { -7732, 6046 },
				{ 31420, 48101 }, { -1925, -1502 }, { 1556, 23267 }, { 47416, 49739 }, { -14695, 49088 },
				{ -46590, -16276 }, { -26918, 19649 }, { 39650, 45019 }, { -23311, -4637 }, { 29013, 31109 },
				{ -17535, 37033 }, { -38124, -13856 }, { 46582, 49552 }, { -33030, -26997 }, { 48826, 49234 },
				{ -39911, -28282 }, { 41705, 47727 }, { 12773, 27704 }, { -19569, -12433 }, { 18645, 29165 },
				{ 23725, 42177 }, { 2201, 37851 }, { -23691, 3603 }, { 49220, 49555 }, { 48632, 49665 },
				{ 40781, 42864 }, { 41922, 44448 }, { -19122, 44799 }, { 42383, 43039 }, { -8028, 19792 },
				{ -14569, 4230 }, { -8289, 25787 }, { -44310, 7798 }, { -49868, -11199 }, { -21370, 48365 },
				{ 45804, 46951 }, { 35803, 38405 }, { 16118, 31200 }, { -31215, -18830 }, { -32868, 38201 },
				{ 14645, 27943 }, { -19501, 17150 }, { -40029, 35348 }, { 25985, 40447 }, { 3806, 42534 },
				{ -26276, 5704 }, { -31229, 13571 }, { 22549, 29387 }, { 17092, 20450 }, { 31548, 44450 },
				{ -28700, 46168 }, { -35241, 1053 }, { -31629, 16443 }, { -39168, 36495 }, { -11801, 41065 },
				{ 3756, 13894 }, { -9154, 29939 }, { -33736, -1829 }, { -35836, -27299 }, { 17558, 42149 },
				{ -17683, -1564 }, { 28813, 38451 }, { 1645, 6386 }, { 16995, 37211 }, { 31376, 42940 },
				{ 28972, 31211 }, { 16055, 46869 }, { 43066, 49463 }, { -10319, 1947 }, { 21440, 36905 },
				{ -30061, -9311 }, { 13832, 39276 }, { -45715, -3261 }, { -47232, -28450 }, { 31916, 47953 },
				{ -12557, 31223 }, { -36973, -18111 }, { -3578, 18337 }, { -29324, 41366 }, { 15330, 32002 },
				{ -5371, 9176 }, { 24227, 30653 }, { 1024, 44480 }, { 40771, 46381 }, { 24068, 48134 },
				{ -11685, 47572 }, { 22253, 42282 }, { 30913, 48977 }, { -24450, -8573 }, { -42497, 28294 },
				{ -24393, 8064 }, { 37984, 38483 }, { -16295, 27894 }, { -20772, -11395 }, { 5432, 49961 },
				{ -34993, -2663 }, { 4349, 33224 }, { 41564, 49634 }, { 7991, 23172 }, { -26979, -22683 },
				{ 37403, 45594 }, { 25719, 27615 }, { 17743, 43865 }, { -21796, 4441 }, { 17014, 30704 },
				{ 6361, 31261 }, { 5349, 20577 }, { -24211, 15311 }, { -17046, 23994 }, { -48822, 27594 },
				{ -49578, -13803 }, { 7703, 34942 }, { -18155, 39759 }, { 31964, 39962 }, { -42350, -3231 },
				{ -12174, 31278 }, { -2917, 27547 }, { 26, 2742 }, { 3343, 16557 }, { 43918, 45501 },
				{ -43341, -36564 }, { 48702, 49657 }, { -4223, 32346 }, { -27909, 21045 }, { -33179, -19118 },
				{ 37495, 42052 }, { -10327, 38594 }, { -18652, 31361 }, { -47900, -3284 }, { 3734, 47634 },
				{ 18534, 30322 }, { -20731, 33256 }, { -24658, 23163 }, { -33660, -25364 }, { 3002, 26341 },
				{ 39236, 41225 }, { 35565, 44563 }, { -6790, 28331 }, { 13443, 39246 }, { -25081, 13841 },
				{ -41610, 45902 }, { -14348, -9613 }, { 15502, 49072 }, { -15955, 19800 }, { 19330, 41822 },
				{ 5674, 16060 }, { -48364, -24131 }, { -16504, -5086 }, { -11592, -7655 }, { -9760, 628 },
				{ -39921, -36666 }, { 4900, 7762 }, { 17079, 22726 }, { 33538, 49545 }, { 32792, 44545 },
				{ 32261, 37375 }, { -3235, 22006 }, { -2779, -1724 }, { -11003, 34444 }, { -46671, 43544 },
				{ -26612, 33513 }, { -23663, -22917 }, { 16810, 32776 }, { -20170, 13866 }, { 1281, 19428 },
				{ -15030, 32897 }, { 16890, 18282 }, { 8919, 33215 }, { -40208, 21962 }, { -11562, -839 },
				{ 39786, 46517 }, { 36939, 48254 }, { 15118, 41815 }, { 49148, 49176 }, { -17190, -6505 },
				{ -8060, 39378 }, { -35094, -9733 }, { -29320, 47789 }, { -28489, -15932 }, { -16373, 3508 },
				{ -7484, 6932 }, { 33425, 48496 }, { -33414, 18287 }, { 38662, 41922 }, { -11397, 37230 },
				{ 25077, 44046 }, { 13174, 46423 }, { -22310, 21893 }, { -41568, 33737 }, { -36412, 37100 },
				{ -33320, 10278 }, { 13183, 48841 }, { 25229, 28582 }, { 40373, 41709 }, { -20629, -14404 },
				{ -23821, -16054 }, { -22421, 31547 }, { -43408, 48027 }, { -3257, 24251 }, { 46883, 47994 },
				{ -27974, -6034 }, { -11013, -8983 }, { -22237, -10027 }, { -35699, 38235 }, { -40094, -15772 },
				{ -18855, 6701 }, { -9412, -1048 }, { 12621, 26276 }, { 45995, 46157 }, { -45262, -27143 },
				{ 46088, 46601 }, { 39577, 46861 }, { 37443, 46858 }, { 24946, 33810 }, { -46049, -41940 },
				{ -43458, -39294 }, { 39015, 42604 }, { -40664, -15364 }, { 27659, 31775 }, { -12802, 27512 },
				{ 25220, 25639 }, { -29565, -10893 }, { 19177, 46676 }, { 17536, 24096 }, { 48397, 49033 },
				{ 44029, 47051 }, { 41516, 44290 }, { 43929, 46333 }, { -48273, -26089 }, { -28566, -5710 },
				{ 48854, 49291 }, { 22710, 43255 }, { 46381, 48610 }, { 5364, 12478 }, { 27413, 31098 },
				{ -35281, 40830 }, { -11984, -10394 }, { 49072, 49664 }, { -42561, -3669 }, { -1553, 30954 },
				{ 19339, 36991 }, { -31564, 7644 }, { 36981, 44963 }, { 22756, 43272 }, { 1538, 30266 },
				{ 43804, 45660 }, { 47864, 49216 }, { 12179, 27964 }, { -14357, 17601 }, { 11225, 37917 },
				{ 49943, 49993 }, { -37100, -9557 }, { -30316, 22383 }, { -15413, 38195 }, { 4499, 18003 },
				{ -34572, 39685 }, { 6704, 29489 }, { -36862, -3924 }, { -11246, -4824 }, { -48519, 23933 },
				{ 38125, 46959 }, { -20949, 29543 }, { -5660, 46582 }, { -4300, 37695 }, { 43802, 45600 },
				{ -2791, 10090 } };

		int intervalCountGreedyEnd = eraseOverlapIntervalsGreedyEnd(intervals);
		System.out.println("Greedy End: The number of overlapping intervals to erase: " + intervalCountGreedyEnd);

		int intervalCountGreedySt = eraseOverlapIntervalsGreedyStart(intervals);
		System.out.println("Greedy Start: The number of overlapping intervals to erase: " + intervalCountGreedySt);

		int intervalCountEndDP = eraseOverlapIntervalsEndDP(intervals);
		System.out.println("End Point DP: The number of overlapping intervals to erase: " + intervalCountEndDP);

		int intervalCountStartDP = eraseOverlapIntervalsStartDP(intervals);
		System.out.println("Start Point DP: The number of overlapping intervals to erase: " + intervalCountStartDP);

		int intervalCountBFDP = eraseOverlapIntervalsBFDP(intervals);
		System.out.println("Brute Force DP: The number of overlapping intervals to erase: " + intervalCountBFDP);

		int intervalCountBF = eraseOverlapIntervalsBF(intervals);
		System.out.println("Brute Force: The number of overlapping intervals to erase: " + intervalCountBF);

		int intervalCount = eraseOverlapIntervals(intervals);
		System.out.println("The number of overlapping intervals to erase: " + intervalCount);
	}

	// Greedy based on end points
	// Sort the intervals based on end points
	// We use prev pointer to keep track of last interval included starting from 0
	// In case of no overlap we consider otherwise we drop the current interval.
	// There are 3 cases from i = 1 to n-1 onwards:
	// 1. Current interval doesn't overlap with prev's interval - update prev to i
	// 2. Current interval overlaps and it's start < prev's interval start
	//    In this case the current interval occupies more space hence 
	//    remove the current interval by increasing count and keep prev.
	// 3. Current interval overlaps and it's start > prev's interval start
	//    In this case the current interval occupies more space in right
	//    remove the current interval by increasing count and keep prev.
	// Time complexity - O(nlogn) for sorting intervals.
	// Space complexity - O(1) depending on the sorting logic.
	private static int eraseOverlapIntervalsGreedyEnd(int[][] intervals) {
		int n = intervals.length;
		Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
		int count = 0;
//		int count = 1;
		int prev = 0;
		for (int i = 1; i < n; i++) {
			// Count to exclude
			if (intervals[prev][1] > intervals[i][0]) {
				count++;
			} else {
				prev = i;
			}
			
			// Count to include
//			if(intervals[i][0] >= intervals[prev][1]) {
//				prev = i;
//				count++;
//			}
		}
		return count;
		// include
//		return n - count;
	}

	// Greedy based on starting points
	// Sort the intervals based on start (end point also works - why?).
	// end point works - (intervals[prev][1] <= intervals[i][1]), no update of prev
	// We use a prev pointer to keep track of last interval included starting from 0
	// There are 3 cases from i = 1 to n-1 onwards:
	// 1. Current interval is not overlapping with prev pointer - update prev to i
	// 2. Ovelap and current interval's end < prev's interval end
	//    In this case include the current interval by updating prev to i, And
	//    Delete the prev's interval to accomodate more intervals, increase count by 1
	// 3. Overlap and current interval's end > prev's interval end
	//    In this case simply remove current interval and increase count by 1
	//    No need to update prev pointer as we need to include it in future.
	// Time complexity - O(nlogn) for sorting intervals.
	// Space complexity - O(1) depending on the sorting logic.
	private static int eraseOverlapIntervalsGreedyStart(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // but can be a[1] - b[1]
		int n = intervals.length;
		int prev = 0, count = 0;
		for (int i = 1; i < n; i++) {
			if (intervals[prev][1] > intervals[i][0]) {
				if (intervals[prev][1] > intervals[i][1]) { // for a[1] - b[1] always false
					prev = i;
				}
				count++;
			} else {
				prev = i;
			}
		}
		return count;
	}

	// DP + ending point
	// Sort the interval based on start/end point.
	// Find the number of intervals to be included then subtract from total.
	// dp[i+1], it may happen that (i+1)th index is included or may not
	// If included then find the first index before i+1 which is non overlapping
	// Add 1 to it, since i+1 interval is included, remove all overlaps with i+1.
	// Don't need to go back to index 0, as dp[j] stores the max intervals uptil j.
	// dp[i + 1] = max(dp[i], dp[j] + 1) where j is last non overlapping index.
	// Time complexity - O(n^2) for 2 nested loops to fill dp array.
	// Space complexity - O(n) for dp array.
	private static int eraseOverlapIntervalsEndDP(int[][] intervals) {
		int n = intervals.length;
		Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // a[0] - b[0]
		int[] dp = new int[n];
		// Base case we include the 1st interval
		dp[0] = 1;
		for (int i = 1; i < n; i++) {
			int max = 0;
			for (int j = i - 1; j >= 0; j--) {
				if (intervals[j][1] <= intervals[i][0]) {
					max = dp[j];
					break;
				}
			}
			dp[i] = Math.max(dp[i - 1], max + 1);
		}
		return n - dp[n - 1];
	}

	// DP + starting point
	// Sort the interval based on start/end point.
	// Find the number of intervals to be included then subtract from total
	// dp[i] stores the maximum number of intervals included without deleting till i
	// dp[i+1] can't consider dp[i] since there can be overaps.
	// Find the max value in j = 0 to i in dp[i] such that no overap. Add 1 to max.
	// dp[i] = max(dp[j]) + 1, j<=i and inteval[j] and interval[i] don't overlap
	// Time complexity - O(n^2) for 2 nested loops to fill dp array.
	// Space complexity - O(n) for dp array.
	private static int eraseOverlapIntervalsStartDP(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // a[0] - b[0]
		int n = intervals.length;
		int[] dp = new int[n];
		// Base case - we include the 1st interval
		dp[0] = 1;
		int ans = 1;
		for (int i = 1; i < n; i++) {
			int max = 0;
			for (int j = i - 1; j >= 0; j--) {
				if (intervals[j][1] <= intervals[i][0]) {
					max = Math.max(max, dp[j]);
				}
			}
			dp[i] = max + 1;
			ans = Math.max(ans, dp[i]);
		}
		return n - ans;
	}

	// Memoization to optimize recursion
	private static int eraseOverlapIntervalsBFDP(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		int n = intervals.length;
		int[][] dp = new int[n][n];
		int num = eraseIntervals(intervals, -1, 0, dp);
		return num;
	}

	private static int eraseIntervals(int[][] intervals, int prev, int curr, int[][] dp) {
		if (curr == intervals.length) {
			return 0;
		}
		if (dp[prev + 1][curr] != 0) {
			return dp[prev + 1][curr];
		}
		int taken = Integer.MAX_VALUE, notTaken;
		if (prev == -1 || intervals[prev][1] <= intervals[curr][0]) {
			taken = eraseIntervals(intervals, curr, curr + 1, dp);
		}
		notTaken = eraseIntervals(intervals, prev, curr + 1, dp) + 1;
		return dp[prev + 1][curr] = Math.min(taken, notTaken);
	}

	// Recursion - interval removal in combinations
	// Sort the intervals
	// We try to remove the intervals in different combinations and find min
	// There are 2 scenarios - either the given interval is taken or not
	// The given interval is taken in case of prev = -1 or the
	// previous interval does not overlap with current interval
	// It may happen that the current interval is not needed later
	// We can remove it and add 1. The result is minimum of taken and not taken.
	// Time complexity - O(2^n), there are 2^m combinations of subsets.
	// Space complexity - O(n), the max depth of recursion is m.
	private static int eraseOverlapIntervalsBF(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		return eraseIntervals(intervals, -1, 0);
	}

	private static int eraseIntervals(int[][] intervals, int prev, int curr) {
		if (curr == intervals.length) {
			return 0;
		}
		int taken = Integer.MAX_VALUE, notTaken;
		if (prev == -1 || intervals[prev][1] <= intervals[curr][0]) {
			taken = eraseIntervals(intervals, curr, curr + 1);
		}
		// Add 1 since current interval is not taken.
		notTaken = eraseIntervals(intervals, prev, curr + 1) + 1;
		return Math.min(taken, notTaken);
	}

	// Incorrect since it merges interval
	// [1,3][3,4] instead of keeping [1,2][2,3][3,4]
	// We've to delete minimum intervals and not merge them
	public static int eraseOverlapIntervals(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

		LinkedList<int[]> list = new LinkedList<>();
		for (int[] interval : intervals) {
			if (!list.isEmpty() && list.getLast()[1] > interval[0]) {
				list.getLast()[1] = Math.max(list.getLast()[1], interval[1]);
			} else {
				list.add(interval);
			}
		}
		return intervals.length - list.size();
	}

}
