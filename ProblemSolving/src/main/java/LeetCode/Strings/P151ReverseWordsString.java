package LeetCode.Strings;

import java.util.ArrayList;
import java.util.List;

public class P151ReverseWordsString {

	public static void main(String[] args) {
		String s = "the sky is blue";
		String reverseWords = reverseWords(s);
		System.out.print("The reverse words is - " + reverseWords);
	}
	
	public static String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int j = 0;
        int n = s.length();
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            StringBuilder word = new StringBuilder();
            if (c != ' ') {
                j = i;
                while (c != ' ') {
                    if (j == 0) {
                        break;
                    }
                    j--;
                    c = s.charAt(j);

                }
                sb.append(s.substring(j, i + 1).trim()).append(" ");
                i = j;
            }
        }
        return sb.toString().trim();
    }
	
	public static String reverseWordsAppend(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            StringBuilder word = new StringBuilder();
            if(c!=' '){
                while(c != ' '){
                    word.append(c);
                    if(i==0){
                        break;
                    }
                    i--;
                    c = s.charAt(i);
                    
                }
                sb.append(word.reverse()).append(" ");
            }
        }
        return sb.toString().trim();
    }
	
	public static String reverseWordsConditions(String s) {
        String[] strArr = s.trim().split(" ");
        List<String> list = new ArrayList<>();
        for (String str : strArr) {
            str = str.trim();
            if (str.length() > 0) {
                list.add(str);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i)).append(" ");
        }
        return sb.toString().trim();

    }

}
