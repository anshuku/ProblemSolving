package LeetCode.BinarySearch;

/*
 * P441. Arranging Coins - Easy
 * 
 * You have n coins and you want to build a staircase with these coins. The staircase consists 
 * of k rows where the ith row has exactly i coins. The last row of the staircase may be incomplete.
 * 
 * Given the integer n, return the number of complete rows of the staircase you will build.
 * 
 * Approach - Binary Search, Math
 */
public class P441ArrangingCoins {

	public static void main(String[] args) {
//		int n = 1;
//		int n = 5;
//		int n = 8;
//		int n = 15;
//		int n = 16;
//		int n = 2147395599;
//		int n = 2147395600;
		int n = 2147483647;

		int rowsMath = arrangeCoinsMath(n);
		System.out.println("Math: The numbers of rows filled by coins: " + rowsMath);

		int rowsBS = arrangeCoinsBS(n);
		System.out.println("Binary Search: The numbers of rows filled by coins: " + rowsBS);
	}

	// Math
	// For the constraint k*(k+1)/2 <= N or k(k+1) <= 2N
	// x^2 + x <= 2N, We can use completing the squares
	// For y = ax^2 + bx + c, y = a(x + b/2a)^2 + (c - b^2/4a)
	// (k+1/2)^2 - 1/4 <= 2N or k <= rt(2n+1/4) - 1/2
	// Time complexity - O(1)
	// Space complexity - O(1)
	private static int arrangeCoinsMath(int n) {
		return (int) (Math.sqrt((long) 2 * n + 0.25) - 0.5);
	}

	// Binary search
	// If answer is k, we've managed to complete k rows of coins.
	// The completed rows contain in total 1+2+...+k = k*(k+1)/2 coins.
	// We need to find maximum k sych that k*(k+1)/2 <= N.
	// Time complexity - O(logN)
	// Space complexity - O(1)
	public static int arrangeCoinsBS(int n) {
		long start = 1;
		long end = n / 2 + 1;
		while (start <= end) {
			long mid = start + (end - start) / 2;
			if (mid * (mid + 1) / 2 == n) {
				return (int) mid;
			} else if (mid * (mid + 1) / 2 < n) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return (int) end;
	}

}
