package LeetCode.DynamicProgramming;

import java.util.ArrayList;
import java.util.List;

/*
 * P118. Pascal's Triangle - Easy 
 * 
 * Given an integer numRows, return the first numRows of Pascal's triangle.
 * 
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 * 
 * Approach - DP
 */
public class P118PascalsTriangle {

	public static void main(String[] args) {
		int numRows = 5;

		List<List<Integer>> pascalsTriangle = generate(numRows);
		System.out.printf("The Pascal's triangle for given numRows = %d is: " + pascalsTriangle, numRows);
	}

	// Time complexity - O(n^2)
	// Overall the loop runs 1,2,3,.. numRows times
	// 1+2+3+..numRows = n(n+1)/2 = O(n^2)
	// Spacme complexity - O(1)
	public static List<List<Integer>> generate(int numRows) {
		List<List<Integer>> pascalTriangle = new ArrayList<>();
		for (int i = 0; i < numRows; i++) {
			List<Integer> row = new ArrayList<>();
			for (int j = 0; j < i + 1; j++) {
				if (j == 0 || j == i) {
					row.add(1);
				} else {
					row.add(pascalTriangle.get(i - 1).get(j - 1) + pascalTriangle.get(i - 1).get(j));
				}
			}
			pascalTriangle.add(row);
		}
		return pascalTriangle;
	}

}
