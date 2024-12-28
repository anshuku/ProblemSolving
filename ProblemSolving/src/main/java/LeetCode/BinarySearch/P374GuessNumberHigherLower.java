package LeetCode.BinarySearch;

/*
 * P374. Guess Number Higher or Lower - Easy
 * 
 * We are playing the Guess Game. The game is as follows:
 * 
 * I pick a number from 1 to n. You have to guess which number I picked.
 * 
 * Every time you guess wrong, I will tell you whether the number 
 * I picked is higher or lower than your guess.
 * 
 * You call a pre-defined API int guess(int num), which returns three possible results:
 * > -1: Your guess is higher than the number I picked (i.e. num > pick).
 * > 1: Your guess is lower than the number I picked (i.e. num < pick).
 * > 0: your guess is equal to the number I picked (i.e. num == pick).
 * 
 * Return the number that I picked.
 * 
 * Approach - Binary Search
 */
abstract class GuessGame {

	static int guess(int num) {
//		int pick = 6;
//		int pick = 1;
//		int pick = 1;
		int pick = 1702766719;

		if (num == pick) {
			return 0;
		} else if (num < pick) {
			return 1;
		} else {
			return -1;
		}
	}

}

class P374GuessNumberHigherLower extends GuessGame {

	public static void main(String[] args) {
//		int num = 10;
//		int num = 1;
//		int num = 2;
		int num = 2126753390;

		int guessed = guessNumber(num);
		System.out.println("The guessed number is: " + guessed);
	}

	public static int guessNumber(int n) {
		int left = 1;
		int right = n;
		while (left <= right) {
			// int mid = (left + right)/2; // unable to solve 2^31-1 cases
			int mid = left + (right - left) / 2; // can solve near to 2^31-1 cases

			if (guess(mid) == 0) {
				return mid;
			} else if (guess(mid) < 0) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return -1;
	}

}
