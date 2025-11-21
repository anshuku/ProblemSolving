package CSES;

import java.util.Scanner;

/*
 * P2 Missing Number
 * 
 * You are given all numbers between 1,2,\ldots,n except one. Your task is to find the missing number.
 * 
 * Input
 * The first input line contains an integer n.
 * 
 * The second line contains n-1 numbers. Each number is distinct and between 1 and n (inclusive).
 * 
 * Output
 * Print the missing number.
 * 
 * Constraints
 * 
 * 2 <= n <= 2 * 10^5
 * 
 * Example
 * Input:
 * 5
 * 2 3 1 5
 * 
 * Output:
 * 4
 */
public class P2MissingNumber {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long n = Long.parseLong(sc.nextLine());
		String s = sc.nextLine();

		printMissingNumber(n, s);
		sc.close();
	}

	private static void printMissingNumber(long n, String s) {
		// long n can support n*(n+1) but int cannot
		// first n*(n+1) is calculated and stored in the original capacity of n's
		// datatype.
		long total = (n * (n + 1)) / 2;
		long curr = 0;
		String[] strArr = s.split(" ");
		for (String val : strArr) {
			int num = Integer.parseInt(val);
			curr += num;
		}
		System.out.print(total - curr);
	}

}
