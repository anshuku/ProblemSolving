package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*
 * P279. Perfect Squares - Medium
 * 
 * Given an integer n, return the least number of perfect square numbers that sum to n.
 * A perfect square is an integer that is the square of an integer; 
 * in other words, it is the product of some integer with itself. 
 * For example, 1, 4, 9, and 16 are perfect squares while 3 and 11 are not.
 * 
 * Approach - DP, Math, BFS, enumeration
 * 
 * Populating dp[i] = Integer.MAX_VALUE; takes more time, better to assign val with MAX.
 * 
 * The correctness of Greedy algorithm can be done with contradiction.
 * BFS finds the shortest path in an unweighted graph.
 */
public class P279PerfectSquares {

	public static void main(String[] args) {
		int n = 17;

//		int n = 11;

		int minSquaresMaths = numSquaresMaths(n);
		System.out.println("Maths: The minimum number of squares needed is " + minSquaresMaths);

		int minSquaresGreedy = numSquaresGreedy(n);
		System.out.println("Greedy: The minimum number of squares needed is " + minSquaresGreedy);

		int minSquaresArr = numSquaresArr(n);
		System.out.println("Array: The minimum number of squares needed is " + minSquaresArr);

		int minSquaresArrAlt = numSquaresArrAlt(n);
		System.out.println("Array Alt: The minimum number of squares needed is " + minSquaresArrAlt);

		int minSquaresList = numSquaresList(n);
		System.out.println("List: The minimum number of squares needed via list is " + minSquaresList);

		int minSquaresBfs = numSquaresBfs(n);
		System.out.println("BFS: The minimum number of squares needed via list is " + minSquaresBfs);

		int minSquaresRecursive = numSquaresRecursive(n);
		System.out.println("Recursive: The minimum number of squares needed is " + minSquaresRecursive);

	}

	// Mathematics - there are 4 cases
	// 1770 Josheph Louis Lagrange, Lagrange's 4-square theorem/Bachet's conjecture
	// n = a0^2 + a1^2 + a2^2 + a3^2 for all natural n, and a0,a1,a2,a3 E integers.
	// 3 = 1^2 + 1^2 + 1^2 + 0^2, 31 = 5^2 + 2^2 + 1^2 + 1^2.
	// The theorem sets an upper bound, numSquares(n) <= 4. Number 0 is also
	// considered as square number. Number 3 can be decomposed into 3/4 squares.
	// So Lagrange's theorem doesn't tell use the least squares by at most.
	// 1797 Adrien-Marie Legendre completed it with 3 square theorem.
	// n = a0^2 + a1^2 + a2^2 for all natural n, n =/= 4^k(8*m + 7), k,m E I.
	// This theorem gave the necessary condition for n to be made up of 4 squares.
	// This is true for numbers of the form 4^k(8*m + 7) as it can't have 3 squares.
	// Further, the number n can't have 2 squares as well and neither it's a square.
	// for 2 square possibility: n = a0^2 + a1^2, here a2 can be 0 and it can have 3
	// squares, which is negation of 3 square theorem. Hence, if a number doesn't
	// meet the 3 square theorem, it can only be decomposed as sum of 4 squares.
	// For 1, 2 and 3 squares possibility, we can use 3 square theroem.
	// For numbers which satisfy 3 square theorem, at first we need to check if the
	// number can be decomposed int 1/2 squares then it's a sum fo 3 squares. For 1
	// square, check if the number is square number itself. n == int(sqrt(n))^2.
	// For 2 squares, use the enumeration approach as there is no mathematical way.
	// Time complexity - O(rt(n)), in main loop we check if number can be decomposed
	// into sum of 2 squares, in other cases it's constant time. for 4^k, it's
	// O(k + rt(n))
	// Space complexity - O(1)
	private static int numSquaresMaths(int n) {
		// 4-square and 3-square theorems,
		while (n % 4 == 0) {
			n /= 4;
		}
		// n = 4^k*(8m+7) means it must be a sum of 4 square numbers.
		if (n % 8 == 7) {
			return 4;
		}
		if (isSquareNumber(n)) {
			return 1;
		}
		// enumeration to check if number can be decomposed into sum of 2 squares.
		for (int i = 1; i * i <= n; i++) {
			if (isSquareNumber(n - i * i)) {
				return 2;
			}
		}
		// bottom case of 3-square theorem.
		return 3;
	}

	private static boolean isSquareNumber(int n) {
		int sqrt = (int) Math.sqrt(n);
		return n == sqrt * sqrt;
	}

	static Set<Integer> squareSet;

	// Greedy enumeration
	// Starting from combination of single number to multiple numbers, once we
	// find that a combination sums upto n, then that combination must be answer as
	// we greedily find the combination from small to large.
	// isNumDivisible(n, count) returns whether number "n" can be divided by "count"
	// number of square numbers rather than returning exact size of combination.
	// numSquares(n) = arg min(count E {1,2,3...})(isNumDivisible(n, count))
	// isNumDivisible(n, count) also takes recursion form
	// isNumDivisible(n, count) = isNumDivisible(n - k, count - 1), there
	// exists (mirror E) k E {Square numbers}
	// isNumDivisible(n,count) reaches it's base case(count = 1) faster than
	// numSquares(n). Prepare a set of square numbers less than n.
	// - In main loop, iterate the size of combination(count) from 1 to n, check if
	// number n can be divided by sum of the combination isNumDivisible(n, count).
	// - isNumDivisible(n, count) is implemented in form of recursion.
	// - In base case count == 1, we just need to check if number n is square number
	// itself. This is done with the help of square numbers set.
	// The Greedy strategy of performing the calls constructs n-ary tree level by
	// level from top to bottom in a BFS manner. At each level of the N-ary tree we
	// enumerate the combinations which are of the same size. It's BFS because
	// before exhausting all the possibilites of decomposing n with fixed number of
	// squares, we'd not explore any potential combination that needs more elements.
	// The trace of the call stack forms a N-ary tree, where each node represents a
	// call to the isNumDivisible(n, count) function.
	// Time complexity - O([rt(n)^(k+1) - 1] / [rt(n) - 1] = O(n^k/2), where k is
	// maximum number of recursion that could happen. This formula is same as
	// finding the max number of nodes in complete n-ary tree. Trace of recursive
	// calls forms an n-ary tree, where n is number of squares in set or rt(n).
	// In worst case entire tree needs to be traversed to find the solution.
	// 1 + rt(n) + rt(n)^2 + rt(n)^3 + ... + rt(n)^k
	// Space complexity - O(rt(n)), we kept list of square nums of size rt(n).
	// In addition we need additional space for recursive call stack <=4.
	private static int numSquaresGreedy(int n) {
		squareSet = new HashSet<>();
		for (int sq = 1; sq * sq <= n; sq++) {
			squareSet.add(sq * sq);
		}
		int count = 1;
		for (; count <= n; count++) {
			if (isNumDivisible(n, count)) {
				return count;
			}
		}
		return 0;
	}

	private static boolean isNumDivisible(int i, int count) {
		if (count == 1) {
			return squareSet.contains(i);
		}
		for (int sq : squareSet) {
			if (isNumDivisible(i - sq, count - 1)) {
				return true;
			}
		}
		return false;
	}

	// Dynamic Programming
	// The stack overflow issue in recursion can be solved by reusing the results of
	// intermediate sub-solutions to calculate the final solutions.
	// We store the result of numSquares(n-k) which can be reused later.
	// 1D DP is used to store intermediate results, which is reused.
	// dp[0] = 0 helps in case when remainder n-k happens to be a square number.
	static int numSquaresArr(int n) {
		int[] dp = new int[n + 1];
		int sq = 1;
		int[] arr = new int[(int) Math.sqrt(n)];
		for (int i = 1; i <= n; i++) {
			if (sq * sq == i) {
				dp[i] = 1;
				arr[sq - 1] = i;
				sq++;
			} else {
				int val = Integer.MAX_VALUE;
				for (int j = 0; j < sq - 1; j++) {
					val = Math.min(val, dp[i - arr[j]]);
				}
				dp[i] = val + 1;
			}
		}
		return dp[n];
	}

	// Dynamic Programming
	// In preparation step we pre calculate the list of square numbers < n.
	// Time complexity - O(n*root(n)), as the main loop runs n times and inner loop
	// runs maximum of root(n) times.
	// Space complexity - O(n) for storing the dp array having intermediate
	// solutions.
	private static int numSquaresArrAlt(int n) {
		int[] dp = new int[n + 1];
		int sqLen = (int) Math.sqrt(n);
		int[] sqArr = new int[sqLen];
		for (int i = 0; i < sqLen; i++) {
			sqArr[i] = (i + 1) * (i + 1);
		}
		for (int i = 1; i <= n; i++) {
			int val = Integer.MAX_VALUE;
			for (int j = 0; j < sqLen; j++) {
				if (i < sqArr[j]) {
					break;
				}
				val = Math.min(val, dp[i - sqArr[j]] + 1);
			}
			dp[i] = val;
		}
		return dp[n];
	}

	public static int numSquaresList(int n) {
		int dp[] = new int[n + 1];
		List<Integer> list = new ArrayList<>();
		int sq = 1;
		for (int i = 1; i <= n; i++) {
			if (sq * sq == i) {
				list.add(i);
				dp[i] = 1;
				sq++;
			} else {
				int val = Integer.MAX_VALUE;
				for (int j = 0; j < list.size(); j++) {
					val = Math.min(val, dp[i - list.get(j)]);
				}
				dp[i] = val + 1;
			}
		}
		return dp[n];
	}

	// BFS + Greedy
	// Given an N-ary tree, where each node represents a remainder of number n,
	// subtracting a combination of square numbers, we need to find a node which:
	// a) The value of the node(remainder) should be square number as well.
	// b) The distance between node and root should be minimal among all nodes.
	// Approach: We find a list(for easy traversal avoid set) of square numbers <=n.
	// Then, we use a queue to store remainders to be enumerated at each level.
	// In main loop, we iterate over the queue remainders and find if it's one of
	// the square number provided by the inner loop. If it's a square we return that
	// level. If it's not a square number and less than the square number we break
	// the square number loop and check other remainders. If it's not a square
	// number and more than the current square number then find the remainders after
	// subtracting each permissible square number and store it in a new queue for
	// iteration at next level. The queue is of set type to remove redundancy at
	// same level.
	// Time complexity - O(rt(n)^(k+1) - 1 / rt(n) - 1) = O(n^k/2), where k is
	// height of binary tree.
	// Space complexity - O(n ^ k/2), which is the max numver of nodes at level k.
	// These nodes(remainders to visit for a given level) are saved in queue
	// variable and is more than space required for storing all the square numbers.
	private static int numSquaresBfs(int n) {
		List<Integer> squareNums = new ArrayList<>();
		for (int sq = 1; sq * sq <= n; sq++) {
			squareNums.add(sq * sq);
		}
		int level = 0;
		Set<Integer> queue = new HashSet<>();
		Set<Integer> visited = new HashSet<>();
		queue.add(n);
		visited.add(n);
		while (!queue.isEmpty()) {
			level++;
			Set<Integer> newQueue = new HashSet<>();
			for (int remainder : queue) {
				for (int square : squareNums) {
					if (remainder == square) {
						return level;
					} else if (remainder < square) {
						break;
					} else {
						int value = remainder - square;
//						newQueue.add(remainder - square);
						if (!visited.contains(value)) {
							newQueue.add(value);
							visited.add(value);
						}
					}
				}
			}
			queue = newQueue;
		}
		return level;
	}

	// Brute force enumeration
	// It's given a list of square numbers, finding the combination that sum upto n,
	// and the combination should contain the least numbers among all solutions.
	// For this combination problem, brute force enumeration can be used to
	// enumerate all possible combinations and find the minimal one of them.
	// numSquares(n) = min(numSquares(n-k) + 1) for all k in square numbers.
	// To calculate numSquares(n), first we need to calculate all the values before
	// n. i.e., numSquares(n-k) for all k in square numbers. Keeping the solution
	// for number: n-k helps avoid recursion and stack overflow.
	private static int numSquaresRecursive(int n) {
		int sq = 1;
		// To maintain insertion order use LinkedHashSet.
		Set<Integer> set = new LinkedHashSet<>();
		for (int i = 1; i <= n; i++) {
			if (sq * sq == i) {
				set.add(i);
				sq++;
			}
		}
		int[] dp = new int[n + 1];
		return minNumSquares(n, set, dp);
	}

	private static int minNumSquares(int i, Set<Integer> perfectSquaresSet, int[] dp) {
		// if i itself is a perfect square, it takes exactly one number to make it.
		if (perfectSquaresSet.contains(i)) {
			return 1;
		}
		if (dp[i] != 0) {
			return dp[i];
		}
		int val = Integer.MAX_VALUE;
		// Find minimal value among all possible numbers.
		for (int sq : perfectSquaresSet) {
			// minNumSquares(i - sq), the argument must remain positive.
			// next square is bigger than current remainder.
			// The bigger square can't contribute anything so early breakout.
			if (i < sq) {
				break;
			}
			// Taking sq out of i, recursively compute the minimum number of
			// squares for remaining i - sq. Add 1 as we just used 1 square.
			// 1 → minNumSquares(11) + 1
			// 4 → minNumSquares(8) + 1
			// 9 → minNumSquares(3) + 1
			// Each of the recursive calls break further
			// down until it hits a perfect square(base case).
			int newNum = minNumSquares(i - sq, perfectSquaresSet, dp) + 1;
			// Keep track of smallest number of squares found.
			val = Math.min(val, newNum);
		}
		return dp[i] = val;
	}

}
