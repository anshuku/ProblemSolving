package LeetCode.DynamicProgramming;

/*
 * P2140. Solving Questions With Brainpower - Medium
 * 
 * You are given a 0-indexed 2D integer array questions where questions[i] = [pointsi, brainpoweri].
 * 
 * The array describes the questions of an exam, where you have to process the questions in 
 * order (i.e., starting from question 0) and make a decision whether to solve or skip each question. 
 * Solving question i will earn you pointsi points but you will be unable to solve each of the next 
 * brainpoweri questions. If you skip question i, you get to make the decision on the next question.
 * 
 * For example, given questions = [[3, 2], [4, 3], [4, 4], [2, 5]]:
 * > If question 0 is solved, you will earn 3 points but you will be unable to solve questions 1 and 2.
 * > If instead, question 0 is skipped and question 1 is solved, you 
 * will earn 4 points but you will be unable to solve questions 2 and 3.
 * 
 * Return the maximum points you can earn for the exam.
 * 
 * Approach - DP
 */
public class P2140SolvingQuestionsWithBrainpower {

	public static void main(String[] args) {
//		int[][] questions = { { 3, 2 }, { 4, 3 }, { 4, 4 }, { 2, 5 } };

		int[][] questions = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 } };

		long maxPointsTabulation = mostPointsTabulation(questions);
		System.out.println("Tabulation: The maximum points that can be earned is: " + maxPointsTabulation);

		long maxPointsMemoized = mostPointsMemoized(questions);
		System.out.println("Memoized: The maximum points that can be earned is: " + maxPointsMemoized);

	}

	// DP Tabulation Iterative - Bottom up
	// For each question questions[i] there are 2 options:
	// 1. Solve it, earn points questions[i][0], skip following questions
	// questions[i][1]. 2. Skip it. Both choices affect the options on the remaining
	// questions so we can use DP. dp[] array with length n = number of questions.
	// dp[i] = maximum points one can get by processing questions in suffix subarray
	// questions[i ~ n-1]. dp array is filled backwards in a non-decreasing way.
	// dp[i] >= dp[i+1], (for i < n-1 as i+1 = n-1).
	// This is because dp[i] and dp[i+1] are optimal points to have for questions[k
	// ~ n-1] and dp[i] has 1 more questions. For dp[i], if we solve the problem, it
	// means we've to skip the following skip[i] problems. The max points is
	// determined by: 1. The score of question i or points[i]. 2. The max score we
	// can get in the range dp[i + skip[i] + 1 ~ n] as we've to skip at least
	// skip[i] following questions. We use dp[i + skip[i] + 1] even if we don't
	// necessarily have to solve question i + skip[i] + 1 and we can skip more.
	// This is because dp is non - decreasing so dp[i + skip[i] + 1] is max value in
	// range dp[i + skip[i] + 1 ~ n]. Solving problem i, we've max points as
	// points[i] + dp[i + skip[i] + 1]. Otherwise, if we skip problem i, the maximum
	// points we get is same as the case for i + 1. dp[i] = dp[i+1].
	// dp[i] = max(dp[i+1], points[i] + dp[i + skip[i] + 1]). For boundary condition
	// if i + skip[i] + 1 >= n, it means that after skipping skip[i] questions there
	// are no avaialable points so take it as 0. We return dp[0] after update, as
	// it's the optimal solution for whole question array questions[ 0 ~ n - 1].
	// Time complexity - O(n) as we iterate over questions and update dp[i].
	// Space complexity - O(n), as we initialize dp array of size n.
	private static long mostPointsTabulation(int[][] questions) {
		int n = questions.length;
		long[] dp = new long[n + 1];
		for (int i = n - 1; i >= 0; i--) {
			int take = questions[i][0];
			if (i + questions[i][1] + 1 < n) {
				take += dp[i + questions[i][1] + 1];
			}
			dp[i] = Math.max(dp[i + 1], take);
		}
		return dp[0];
	}

	// DP Memoization - Top Down
	// Here, each time a recursive function calls itself, it reduces the given
	// problem into subproblems. The recursion continues until the base cases, where
	// the subproblem can be solved without further recursion. We define dfs(i) as
	// maximum points we get by processing the problems in the range [i ~ n-1].
	// dfs(i) is larger of the points of the two options:
	// dfs(i) = max(dfs(i+1), points[i] + dfs(i + skip[i] + 1))
	// Once we move from dfs(i) to either dfs(i+1) or dfs(i + skip[i] + 1), the
	// function calls itself for smaller subproblems. When base case i >= n is
	// reached we return 0 without further recursion. There are many repeated calls
	// to dfs. To avoid repeated computation we use dp array as memory.
	// Time complexity - O(n), it's proportional to number of function calls, so it
	// is O(n), as we've dp memory for calcuating points, dfs(i) is called once.
	// Space complexity - O(n), as it's dependent upon the max depth of recursion
	// tree. We've upto n quesions so it has depth of O(n). Each function call takes
	// O(1) space. For dp array we've O(n) space.
	public static long mostPointsMemoized(int[][] questions) {
		int n = questions.length;
		long[] dp = new long[n];
		return recursive(questions, dp, 0);
	}

	private static long recursive(int[][] questions, long[] dp, int start) {
		if (start >= questions.length) {
			return 0;
		}
		if (dp[start] != 0) {
			return dp[start];
		}
		long take = questions[start][0] + recursive(questions, dp, start + questions[start][1] + 1);
		long notTake = recursive(questions, dp, start + 1);
		return dp[start] = Math.max(take, notTake);
	}

}
