package LeetCode;

import java.util.Arrays;

public class P392IsSubsequence {

	public static void main(String[] args) {
		
		String s = "cnadbcmabssadcac";
		String t = "ahbgdc";
		boolean isSubs = isSubsequence(s, t);

		System.out.print("The subsequence is " + isSubs);
	}
	
	public static boolean isSubsequence(String s, String t) {
        int sLen = s.length();
        if(sLen == 0){
            return true;
        }
        int i = 0, j = 0;
        int tLen = t.length();
        while(i<tLen && j<sLen){
            if(t.charAt(i) == s.charAt(j)){
                i++;
                j++;
            } else {
                i++;
            }
            if(j == sLen){
                return true;
            }
        }
        return false;
    }
	
	
	public static boolean isSubsequenceDP(String s, String t) {
        int sLen = s.length();
        int tLen = t.length();
        int dp[][] = new int[tLen+1][sLen+1];

        for(int i = 1; i<tLen+1; i++){
            for(int j = 1; j<sLen+1; j++){
                if(t.charAt(i-1) != s.charAt(j-1)){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                } else {
                    dp[i][j] = 1 + dp[i-1][j-1];
                }
            }
        }
        System.out.println("dp col " + "-" + "    " + Arrays.toString(s.split("")));
        System.out.println("dp row " + "-" + " " + Arrays.toString(dp[0]));
        for(int i = 1; i<tLen+1; i++){
            System.out.println("dp row " + t.charAt(i-1) + " " + Arrays.toString(dp[i]));
        }
        System.out.println("dp array " + dp[tLen][sLen]);
        
        int i = tLen;
        int j = sLen;
        StringBuilder sb = new StringBuilder();
        
        while(i>0 && j>0) {
        	if(t.charAt(i-1) == s.charAt(j-1)) {
        		sb.append(t.charAt(i-1));
        		i--;
        		j--;
        	} else if(t.charAt(i-1) != s.charAt(j-1)) {
        		if(dp[i-1][j] > dp[i][j-1]) {
        			i--;
        		} else {
        			j--;
        		}
        	}
        }
        System.out.println("Max Subsequence " + sb.reverse().toString());
        
        return dp[tLen][sLen] == sLen;
    }

}
