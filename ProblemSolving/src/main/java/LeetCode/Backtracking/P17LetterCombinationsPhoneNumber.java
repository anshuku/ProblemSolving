package LeetCode.Backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P17. Letter Combinations of a Phone Number - Medium
 * 
 * Given a string containing digits from 2-9 inclusive, return all possible letter 
 * combinations that the number could represent. Return the answer in any order.
 * 
 * A mapping of digits to letters (just like on the telephone buttons) is given below. 
 * Note that 1 does not map to any letters.
 * 
 * Approach - HashMaps, Backtracking
 */
public class P17LetterCombinationsPhoneNumber {

	static String[][] lettersArr = { {}, {}, { "a", "b", "c" }, { "d", "e", "f" }, { "g", "h", "i" }, { "j", "k", "l" },
			{ "m", "n", "o" }, { "p", "q", "r", "s" }, { "t", "u", "v" }, { "w", "x", "y", "z" } };

	static String[] letters = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };

	static Map<String, String> map = new HashMap<String, String>();

	public static void main(String[] args) {

		String digits = "234";

//		String digits = "2";

//		String digits = "";

		for (String[] arr : lettersArr) {
			System.out.println(Arrays.toString(arr));
		}

		map.put("2", "abc");
		map.put("3", "def");
		map.put("4", "ghi");
		map.put("5", "jkl");
		map.put("6", "mno");
		map.put("7", "pqrs");
		map.put("8", "tuv");
		map.put("9", "wxyz");

		List<String> combinations2DArrBT = letterCombinations2DArrBT(digits);

		System.out.println("Backtracking 2D Array: The possible letter combinations are - " + combinations2DArrBT);

		List<String> combinationsArrBT = letterCombinationsArrBT(digits);

		System.out.println("Backtracking Array: The possible letter combinations are - " + combinationsArrBT);

		List<String> combinationsMapBT = letterCombinationsMapBT(digits);

		System.out.println("Backtracking Map: The possible letter combinations are - " + combinationsMapBT);

		List<String> combinationsMapList = letterCombinationsMapList(digits);

		System.out.println("Map List: The possible letter combinations are - " + combinationsMapList);

		List<String> combinationsMapArr = letterCombinationsMapArr(digits);

		System.out.println("Map Array: The possible letter combinations are - " + combinationsMapArr);

	}

	private static List<String> letterCombinations2DArrBT(String digits) {
		List<String> merged = new ArrayList<String>();
		if (digits.length() == 0) {
			return merged;
		}
		recursive2DArr(digits, new StringBuilder(), 0, merged);
		return merged;
	}

	private static void recursive2DArr(String digits, StringBuilder str, int start, List<String> merged) {
		if (str.length() == digits.length()) {
			merged.add(str.toString());
			return;
		}
		for (int i = 0; i < lettersArr[digits.charAt(start) - '0'].length; i++) {
			str.append(lettersArr[digits.charAt(start) - '0'][i]);
			recursive2DArr(digits, str, start + 1, merged);
			str.deleteCharAt(str.length() - 1);
		}

	}

	private static List<String> letterCombinationsArrBT(String digits) {
		List<String> merged = new ArrayList<String>();
		if (digits.length() == 0) {
			return merged;
		}
		recursiveArr(digits, new StringBuilder(), 0, merged);
		return merged;
	}

	private static void recursiveArr(String digits, StringBuilder str, int start, List<String> merged) {
		if (str.length() == digits.length()) {
			merged.add(new String(str));
			// put return or else block
		} else {
			for (int i = 0; i < letters[digits.charAt(start) - '0'].length(); i++) {
				str.append(letters[digits.charAt(start) - '0'].charAt(i));
				recursiveArr(digits, str, start + 1, merged);
				str.deleteCharAt(str.length() - 1);
			}
		}

	}

	private static List<String> letterCombinationsMapBT(String digits) {
		List<String> merged = new ArrayList<String>();
		if (digits.length() == 0) {
			return merged;
		}
		recursive(digits, new StringBuilder(), 0, merged);
		return merged;
	}

	private static void recursive(String digits, StringBuilder str, int start, List<String> merged) {
		if (str.length() == digits.length()) {
			merged.add(str.toString());
		} else {
			for (int i = 0; i < map.get(String.valueOf(digits.charAt(start))).length(); i++) {
				str.append(map.get(String.valueOf(digits.charAt(start))).charAt(i));
				recursive(digits, str, start + 1, merged);
				str.deleteCharAt(str.length() - 1);
			}
		}
	}

	private static List<String> letterCombinationsMapList(String digits) {
		if (digits.length() == 0) {
			return new ArrayList<String>();
		}

		List<String> result = new ArrayList<>();

		if (digits.length() == 1) {
			return Arrays.asList(map.get(digits).split(""));
		}

		String a = String.valueOf(digits.charAt(0));
		String b = String.valueOf(digits.charAt(1));

		List<String> merged = mergeStringList(Arrays.asList(map.get(a).split("")), Arrays.asList(map.get(b).split("")));
		for (int i = 2; i < digits.length(); i++) {
			String c = String.valueOf(digits.charAt(i));
			merged = mergeStringList(merged, Arrays.asList(map.get(c).split("")));
		}
		return merged;
	}

	private static List<String> mergeStringList(List<String> list1, List<String> list2) {
		int m = list1.size();
		int n = list2.size();
		List<String> merged = new ArrayList<String>();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				merged.add(list1.get(i) + list2.get(j));
			}
		}
		return merged;
	}

	public static List<String> letterCombinationsMapArr(String digits) {
		if (digits.length() == 0) {
			return new ArrayList<String>();
		}

		List<String> result = new ArrayList<>();

		if (digits.length() == 1) {
			return Arrays.asList(map.get(digits).split(""));
		}

		String a = String.valueOf(digits.charAt(0));
		String b = String.valueOf(digits.charAt(1));
		String[] merged = mergeStringsArr(map.get(a).split(""), map.get(b).split(""));
		for (int i = 2; i < digits.length(); i++) {
			String c = String.valueOf(digits.charAt(i));
			merged = mergeStringsArr(merged, map.get(c).split(""));
		}
		return Arrays.asList(merged);
	}

	private static String[] mergeStringsArr(String[] str1, String[] str2) {
		int m = str1.length;
		int n = str2.length;
		String[] merged = new String[m * n];
		int k = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				merged[k++] = str1[i] + str2[j];
			}
		}
		return merged;
	}
}
