package LeetCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class P345ReverseVowelsString {

	public static void main(String[] args) {
		
		String s = "hello";
		String reverseVowels = reverseVowels(s);
		System.out.print("Reversed vowels - " + reverseVowels);


	}
	
	public static String reverseVowelsChar(String s) {
		int left = 0;
        int right = s.length()-1;
        //Set<Character> set = new HashSet<>(Arrays.asList('a','e','i','o','u','A','E','I','O','U'));
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
        
        while(left < right){
            char l = s.charAt(left);
            char r = s.charAt(right);
            while(left < right && !set.contains(l)){
                left++;
                l = s.charAt(left);
            }
            while(left < right && !set.contains(r)){
                right--;
                r = s.charAt(right);
            }
            if(set.contains(l) && set.contains(r)){
            	charArr[left++] = r;
            	charArr[right--] = l;
            }
        }
        return new String(charArr);
	}
	
	public static String reverseVowels(String s) {
        int left = 0;
        int right = s.length()-1;
        //Set<String> set = new HashSet<>(Arrays.asList("a","e","i","o","u","A","E","I","O","U"));
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

        while(left < right){
            String l = strArr[left];
            String r = strArr[right];
            while(left < right && !set.contains(l)){
                left++;
                l = strArr[left];
            }
            while(left < right && !set.contains(r)){
                right--;
                r = strArr[right];
            }
            if(set.contains(l) && set.contains(r)){
            	strArr[left++] = r;
            	strArr[right--] = l;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(String str: strArr){
            sb.append(str);
        }
        return sb.toString();
        
    }

}
