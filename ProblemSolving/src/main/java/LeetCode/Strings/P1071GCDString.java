package LeetCode.Strings;

public class P1071GCDString {

	public static void main(String[] args) {

		String str1 = "ABCABCABC";
		String str2 = "ABC";

		//System.out.println("subs" + str1.substring(str2.length()));

		// String gcdString = gcdOfStrings(str1, str2);

		String gcdString = gcdOfStringsAlt(str1, str2);

		System.out.println("The GCD of 2 Strings are " + gcdString);

	}

	private static String gcdOfStrings(String str1, String str2) {

		if (str1.equals(str2)) {
			return str1;
		}
		if (str1.length() < str2.length()) {
			return gcdOfStrings(str2, str1);
		}
		if (str1.length() > str2.length() && str1.startsWith(str2)) {
			return gcdOfStrings(str1.substring(str2.length()), str2);
		}

		return "";
	}

	private static String gcdOfStringsAlt(String str1, String str2) {

		if (!(str1 + str2).equals(str2 + str1)) {
			return "";
		}
		int len = gcdOfStringsLen(str1.length(), str2.length());

		return str1.substring(0, len);
	}

	private static int gcdOfStringsLen(int a, int b) {

		if (b == 0) {
			return a;
		}
		return gcdOfStringsLen(b, a % b);

	}

}
