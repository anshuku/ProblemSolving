package LeetCode.Backtracking;

import java.util.Arrays;

/*
 * P37. Sudoku Solver - Hard
 * 
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * A sudoku solution must satisfy all of the following rules:
 * Each of the digits 1-9 must occur exactly once in each row.
 * Each of the digits 1-9 must occur exactly once in each column.
 * Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
 * The '.' character indicates empty cells.
 * 
 * Approach - Backtracking
 */
public class P37SudokuSolver {

	public static void main(String[] args) {
		
		char[][] board = 
		{
			 {'5','3','.','.','7','.','.','.','.'}
			,{'6','.','.','1','9','5','.','.','.'}
			,{'.','9','8','.','.','.','.','6','.'}
			,{'8','.','.','.','6','.','.','.','3'}
			,{'4','.','.','8','.','3','.','.','1'}
			,{'7','.','.','.','2','.','.','.','6'}
			,{'.','6','.','.','.','.','2','8','.'}
			,{'.','.','.','4','1','9','.','.','5'}
			,{'.','.','.','.','8','.','.','7','9'}
		};
		
		solveSudoku(board);
		
		for(char[] row: board) {
			System.out.println(Arrays.toString(row));
		}

	}
	
	public static void solveSudoku(char[][] board) {
		solve(board, 0, 0);
	}

	private static boolean solve(char[][] board, int row, int col) {
		for(int i = row; i<9; i++, col = 0) {
			for(int j = col; j<9; j++) {
				if(board[i][j] == '.') {
					for(char c = '1'; c<= '9'; c++) {
						if(isValid(board, i, j, c)) {
							board[i][j] = c;
							if(solve(board, i, j+1)) {
								return true;
							} else {
								board[i][j] = '.';
							}
						}
					}
					return false;
				}
			}
		}
		return true;
		
	}

	private static boolean isValid(char[][] board, int row, int col, char c) {
		for(int i = 0; i<9; i++) {
			if(board[i][col] == c) {
				return false;
			}
			if(board[row][i] == c) {
				return false;
			}
			if(board[3*(row/3) + i/3][3*(col/3) + i%3] == c) {
				return false;
			}
		}
		return true;
	}

}
