
import java.util.Scanner;

/*
 * You are given a DNA sequence: a string consisting of characters A, C, G, and T. 
 * Your task is to find the longest repetition in the sequence. This 
 * is a maximum-length substring containing only one type of character.
 * 
 * Input
 * The only input line contains a string of n characters.
 * 
 * Output
 * Print one integer: the length of the longest repetition.
 * 
 * Constraints
 * 1 <= n <= 10^6
 * 
 * Example
 * Input: ATTCGGGA
 * Output: 3
 * 
 * Approach - 
 */
public class P3Repetitions {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();

		sc.close();
		maxLengthSequence(str);
	}

	private static void maxLengthSequence(String str) {
		char[] charArr = str.toCharArray();
		int curr = 1;
		int res = 1;
		for (int i = 1; i < charArr.length; i++) {
			if (charArr[i] != charArr[i - 1]) {
				curr = 0;
			}
			curr++;
			res = Math.max(res, curr);
		}
		System.out.println(res);
	}

}
