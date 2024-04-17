package LeetCode.Strings;

public class P1768MergeStringsAlternately {

	public static void main(String[] args) {

		long val = System.currentTimeMillis();

		String stringAlt = mergeAlternately("abc", "pqr");
		
		System.out.println("String is " + stringAlt);
		
		long val2 = System.currentTimeMillis();

		System.out.println("Time taken " + (val2 - val));

	}

	public static String mergeAlternately(String word1, String word2) {
		int a = word1.length();
        int b = word2.length();

        StringBuilder s = new StringBuilder();
        int i = 0, j = 0;
        boolean flag = true;
        while(i < a && j < b){
            if(flag){
                s.append(word1.charAt(i++));
            } else{
                s.append(word2.charAt(j++));
            }
            flag = !flag;
        }
        if(i < a){
            s.append(word1.substring(i));
        }
        if(j < b){
            s.append(word2.substring(j));
        }
        return s.toString();
	}
}
