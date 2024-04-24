package LeetCode.Strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	
	
	// Approach 1 - Sliding window and Set
    // Iterate through the characters of the string
    // Get the character at right pointer
    // check the character at the char set:
    // - if not present add the character to set
    // - if present then
    //  > move left pointer while removing all characters between left and right in set until we reach the repeating character.
    //  > add the current repeating character to set.
    // check max length = r-l+1
    // move right pointer
    // Time complexity - O(n)
    // Space complexity - O(1)
	public static int lengthOfLongestSubstringSet(String s) {
        int length = 0;
        int left = 0;
        int n = s.length();
        Set<Character> set = new HashSet<>();
        for (int right = 0; right < n; right++) {
            char c = s.charAt(right);
            while (set.contains(c)) {
                set.remove(s.charAt(left));
                left++;
            }
            length = Math.max(length, right - left + 1);
            set.add(c);
        }
        return length;
    }
	
	
	// Approach 2 - Sliding Window and Hashing
    // Iterate through the characters of the string
    // Get the character at right pointer and populate map with the count of
    // appearance for that character
    // check the character at the char map
    // - if count is not 2 ignore
    // - if count is 2
    //  > add -1 to the count for each character while moving left pointer between left and right, until we reach the repeating character.
    //  > add the current repeating character and 1 to set.
    // check max length = r-l+1
    // move right pointer
    // Time complexity - O(n)
    // Space complexity - O(1)
	public static int lengthOfLongestSubstringHashing(String s) {
        int length = 0;
        int left = 0;
        int n = s.length();
        Map<Character, Integer> map = new HashMap<>();
        for (int right = 0; right < n; right++) {
            char c = s.charAt(right);
            map.put(c, map.getOrDefault(c, 0) + 1);
            while (map.get(c) == 2) {
                char leftChar = s.charAt(left);
                map.put(leftChar, map.get(leftChar) - 1);
                left++;
            }
            length = Math.max(length, right - left + 1);
        }
        return length;
    }
	
	
	// Approach 3 - The last position where each character was seen
    // Iterate through the characters of the string
    // Get the character at the right pointer
    // Check if map contains the key already
    // if present then:
    // - check if the last index where it was present is greater than or equal to left
    //  > if the above condition satisfies then update left to it's current value + 1
    // find maxlength
    // insert the map with character at the right pointer
	public int lengthOfLongestSubstringLastSeen(String s) {
        int length = 0;
        int left = 0;
        int n = s.length();
        Map<Character, Integer> map = new HashMap<>();
        for (int right = 0; right < n; right++) {
            char c = s.charAt(right);
            if (map.containsKey(c) && map.get(c) >= left) {
                left = map.get(c) + 1;
            }
            map.put(c, right);
            length = Math.max(length, right - left + 1);
        }
        return length;
    }

}
