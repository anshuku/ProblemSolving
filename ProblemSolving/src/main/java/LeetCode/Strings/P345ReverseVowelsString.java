package LeetCode.Strings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * Given a string s, reverse only all the vowels in the string and return it.
 * The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both lower and upper cases, more than once.
 * Difficulty: Easy
 */
public class P345ReverseVowelsString {

	public static void main(String[] args) {

		String s = "hello";

		String reverseVowelsCharArr = reverseVowelsCharArr(s);

		System.out.println("Reversed vowels - " + reverseVowelsCharArr);

		String reverseVowelsCharSet = reverseVowelsCharSet(s);

		System.out.println("Reversed vowels - " + reverseVowelsCharSet);

		String reverseVowels = reverseVowels(s);

		System.out.println("Reversed vowels - " + reverseVowels);

	}

	// Fastest
	private static String reverseVowelsCharArr(String s) {

		boolean[] vowels = new boolean[128];
		for (char c : "aeiouAEIOU".toCharArray()) {
			vowels[c] = true;
		}
		char[] charArr = s.toCharArray();
		int left = 0, right = s.length() - 1;
		while (left < right) {
			while (left < right && !vowels[charArr[left]]) {
				left++;
			}
			while (left < right && !vowels[charArr[right]]) {
				right--;
			}
			if (left < right) {
				char temp = charArr[left];
				charArr[left] = charArr[right];
				charArr[right] = temp;
				left++;
				right--;
			}
		}
		return new String(charArr);
	}

	public static String reverseVowelsCharSet(String s) {
		int left = 0;
		int right = s.length() - 1;
		// Set<Character> set = new
		// HashSet<>(Arrays.asList('a','e','i','o','u','A','E','I','O','U'));
		Set<Character> set = new HashSet<>();
		set.add('a');
		set.add('e');
		set.add('i');
		set.add('o');
		set.add('u');
		set.add('A');
		set.add('E');
		set.add('I');
		set.add('O');
		set.add('U');

		char[] charArr = s.toCharArray();

		while (left < right) {
			char l = s.charAt(left);// charArr[left]
			char r = s.charAt(right);// charArr[right]
			while (left < right && !set.contains(l)) {
				left++;
				l = s.charAt(left);
			}
			while (left < right && !set.contains(r)) {
				right--;
				r = s.charAt(right);
			}
			if (set.contains(l) && set.contains(r)) {
				charArr[left++] = r;
				charArr[right--] = l;
			}
		}
		return new String(charArr);// return String.valueOf(charArr)
	}

	public static String reverseVowels(String s) {
		int left = 0;
		int right = s.length() - 1;
		// Set<String> set = new
		// HashSet<>(Arrays.asList("a","e","i","o","u","A","E","I","O","U"));
		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("e");
		set.add("i");
		set.add("o");
		set.add("u");
		set.add("A");
		set.add("E");
		set.add("I");
		set.add("O");
		set.add("U");

		String[] strArr = s.split("");

		while (left < right) {
			String l = strArr[left];
			String r = strArr[right];
			while (left < right && !set.contains(l)) {
				left++;
				l = strArr[left];
			}
			while (left < right && !set.contains(r)) {
				right--;
				r = strArr[right];
			}
			if (set.contains(l) && set.contains(r)) {
				strArr[left++] = r;
				strArr[right--] = l;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (String str : strArr) {
			sb.append(str);
		}
		return sb.toString();

	}

}
