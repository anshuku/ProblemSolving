package CSES;

import java.util.Scanner;

/*
 * P1 Weird Algorithm
 * 
 * Consider an algorithm that takes as input a positive integer n. If n is even, the algorithm 
 * divides it by two, and if n is odd, the algorithm multiplies it by three and adds one. 
 * The algorithm repeats this, until n is one. For example, the sequence for n=3 is as follows:
 * 3 -> 10 -> 5 -> 16 -> 8 -> 4 -> 2 -> 1
 * 
 * Your task is to simulate the execution of the algorithm for a given value of n.
 * 
 * Approach - While
 */
public class P1WeirdAlgorithm {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		long n = s.nextLong();

		printSequence(n);

	}

	private static void printSequence(long n) {
		while (n != 1) {
			System.out.print(n + " ");
			if (n % 2 == 1) {
				n = n * 3 + 1;
			} else {
				n /= 2;
			}
		}
		System.out.print(n);

	}

}
