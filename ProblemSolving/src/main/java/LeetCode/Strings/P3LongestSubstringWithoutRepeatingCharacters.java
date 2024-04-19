package LeetCode.Strings;

import java.util.HashMap;
import java.util.Map;

//Given a string s, find the length of the longest substring without repeating characters.
public class P3LongestSubstringWithoutRepeatingCharacters {

	public static void main(String[] args) {
		String s = "abcdeabcfgbb";
		int len = lengthOfLongestSubstring(s);
		System.out.println("The length of longest substring " + len);
	}

	public static int lengthOfLongestSubstring(String s) {
		Map<Character, Integer> map = new HashMap<>();
		int left = 0, right = 0, length = 0;

		while (right < s.length()) {
			char c = s.charAt(right);
			if (map.containsKey(c)) {
				if (map.get(c) >= left) {
					left = map.get(c) + 1;
				}
				map.put(c, right);
			} else {
				map.put(c, right);
			}
			length = Math.max(length, right - left + 1);
			right++;
		}
		return length;
	}

}
