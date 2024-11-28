package LeetCode.HashMapSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

/*
 * 36. Valid Sudoku - Medium
 * 
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 * Note:
 * A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 * Only the filled cells need to be validated according to the mentioned rules.
 * 
 * Approach - HashMap/HashSet/Char Array
 */
public class P36ValidSudoku {

	public static void main(String[] args) {

//		char[][] board = 
//			{
//				 {'5','3','.','.','7','.','.','.','.'}
//				,{'6','.','.','1','9','5','.','.','.'}
//				,{'.','9','8','.','.','.','.','6','.'}
//				,{'8','.','.','.','6','.','.','.','3'}
//				,{'4','.','.','8','.','3','.','.','1'}
//				,{'7','.','.','.','2','.','.','.','6'}
//				,{'.','6','.','.','.','.','2','8','.'}
//				,{'.','.','.','4','1','9','.','.','5'}
//				,{'.','.','.','.','8','.','.','7','9'}
//			};

		char[][] board = { { '8', '3', '.', '.', '7', '.', '.', '.', '.' },
				{ '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
				{ '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
				{ '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
				{ '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };

		boolean isValidArr = isValidSudokuArr(board);
		System.out.println("Array: The sudoku is valid - " + isValidArr);

		boolean isValidSet = isValidSudokuSet(board);
		System.out.println("Set: The sudoku is valid - " + isValidSet);

		boolean isValidMap = isValidSudokuMap(board);
		System.out.println("Map: The sudoku is valid - " + isValidMap);
	}

	private static boolean isValidSudokuArr(char[][] board) {
		for (int i = 0; i < 9; i++) {
			int[] rows = new int[9];
			int[] cols = new int[9];
			int[] squares = new int[9];
			for (int j = 0; j < 9; j++) {
				if (board[i][j] != '.' && ++rows[board[i][j] - '1'] > 1) {
					return false;
				}
				if (board[j][i] != '.' && ++cols[board[j][i] - '1'] > 1) {
					return false;
				}
				int rowIndex = 3 * (i / 3) + j / 3;
				int colIndex = 3 * (i % 3) + j % 3;

				if (board[rowIndex][colIndex] != '.' && ++squares[board[rowIndex][colIndex] - '1'] > 1) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean isValidSudokuSet(char[][] board) {
		for (int i = 0; i < 9; i++) {
			Set<Character> rows = new HashSet<>();
			Set<Character> cols = new HashSet<>();
			Set<Character> squares = new HashSet<>();
			for (int j = 0; j < 9; j++) {
				if (board[i][j] != '.' && !rows.add(board[i][j])) {
					return false;
				}
				if (board[j][i] != '.' && !cols.add(board[j][i])) {
					return false;
				}
				int rowIndex = 3 * (i / 3) + j / 3;
				int colIndex = 3 * (i % 3) + j % 3;
				if (board[rowIndex][colIndex] != '.' && !squares.add(board[rowIndex][colIndex])) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isValidSudokuMap(char[][] board) {
		Map<Integer, List<Character>> rows = new HashMap<>();
		Map<Integer, List<Character>> cols = new HashMap<>();
		Map<Pair<Integer, Integer>, List<Character>> squares = new HashMap<>();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == '.') {
					continue;
				}

				if (rows.get(i) == null) {
					rows.put(i, new ArrayList<>());
				}
				if (cols.get(j) == null) {
					cols.put(j, new ArrayList<>());
				}
				if (squares.get(Pair.of(i / 3, j / 3)) == null) {
					squares.put(Pair.of(i / 3, j / 3), new ArrayList<>());
				}

				if (rows.get(i).contains(board[i][j]) || cols.get(j).contains(board[i][j])
						|| squares.get(Pair.of(i / 3, j / 3)).contains(board[i][j])) {
					return false;
				}
				rows.get(i).add(board[i][j]);
				cols.get(j).add(board[i][j]);
				squares.get(Pair.of(i / 3, j / 3)).add(board[i][j]);
			}
		}

		return true;
	}
}
