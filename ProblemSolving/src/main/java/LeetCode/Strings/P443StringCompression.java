package LeetCode.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class P443StringCompression {

	public static void main(String[] args) {
		char[] chars = { 'a', 'a', 'a', 'b', 'b', 'a', 'a' };
//		char[] chars = { 'a', 'b', 'b', 'a', 'a', 'a', 'c' };

		P443StringCompression psc = new P443StringCompression();

		int len = psc.compress(chars);

//		int len = psc.compressLessVar(chars);

//		int len = psc.compressSB(chars);

//		int len = psc.compressCharacterWise(chars);

		System.out.println("Length is " + len);
	}

	private int compress(char[] chars) {
		int i = 0;// iterates through all the unique characters in chars a single time
		int start = 0;// keeps track of start index in resultant char array only
		int end = 0;// keeps track of end index in current char array and iterates through all its
					// elements
		int res = 0;// if the extra values present at the end of initial char array needs to be
					// removed
		int size = chars.length;
		while (i < size) {
			char c = chars[i];
			int count = 0;
			while (end < size && chars[end] == c) {
				count++;
				end++;
			}
			chars[start++] = c;
			if (count > 1) {
				int pointer = start;
				while (count > 0) {
					chars[start++] = (char) (count % 10 + '0');
					count = count / 10;
				}
				reverse(chars, pointer, start - 1);

//				for (char countChar : Integer.toString(count).toCharArray()) {
//					chars[start++] = countChar;
//				}

//				for (char countChar : String.valueOf(count).toCharArray()) {
//					chars[start++] = countChar;
//				}

				// exponential alternative way for appending count
//				int temp = count;
//				int exp = 0;
//				while (temp >= 10) {
//					temp = temp / 10;
//					exp++;
//				}
//				while (exp >= 0) {
//					temp = count;
//					temp = (temp / (int) Math.pow(10, exp));
//					chars[start] = (char) ((temp % 10) + '0');
//					exp--;
//					start++;
//				}

				// exponential linked list way for appending count
//				LinkedList<Integer> st = new LinkedList<>();
//				while (count > 0) {
//					st.addFirst(count % 10);
//					count = count / 10;
//				}
//				for (int num : st) {
//					chars[start++] = (char) (num + '0');
//				}
			}

			i = end;
			res = start;

		}
		System.out.println("The start index is " + start);
		System.out.println("The end index is " + end);
		while (start < size) {
			chars[start++] = ' ';
		}
		System.out.println("The result array is " + Arrays.toString(chars));
		return res;
	}

	private void reverse(char[] chars, int start, int end) {
		while (start < end) {
			char temp = chars[start];
			chars[start] = chars[end];
			chars[end] = temp;
			start++;
			end--;
		}
	}

	// Most optimized - 0ms
	private void alternativeWayCount(int count, char[] chars, int start) {
		int pointer = start;
		while (count > 0) {
			chars[start++] = (char) (count % 10 + '0');
			count = count / 10;
		}
		reverse(chars, pointer, start - 1);
	}

	// optimized - 1ms
	private void alternativeWayCharArrayCount(int count, char[] chars, int start) {
		for (char num : Integer.toString(count).toCharArray()) {
			chars[start++] = num;
		}

//		for (char num : String.valueOf(count).toCharArray()) {
//			chars[start++] = num;
//		}
	}

	// optimized - 1ms
	private void alternativeWayPowCount(int count, char[] chars, int start) {
		int temp = count;
		int exp = 0;
		while (temp >= 10) {
			temp = temp / 10;
			exp++;
		}
		while (exp >= 0) {
			temp = count;
			temp = (temp / (int) Math.pow(10, exp));
			chars[start] = (char) ((temp % 10) + '0');
			exp--;
			start++;
		}
	}

	// Not optimized - 2ms
	private void alternativeWayLLCount(int count, char[] chars, int start) {
		LinkedList<Integer> list = new LinkedList<>();
		while (count > 0) {
			list.addFirst(count % 10);
			count = count / 10;
		}
		for (int num : list) {
			chars[start++] = (char) (num + '0');
		}
	}

	// Most space optimized
	private int compressLessVar(char[] chars) {
		int i = 0, res = 0;
		int size = chars.length;
		while (i < size) {
			int len = 1;
			while (i + len < size && chars[i + len] == chars[i]) {
				len++;
			}
			chars[res++] = chars[i];
			if (len > 1) {
				int count = len;
				int ptr = res;
				while (count > 0) {
					chars[res++] = (char) (count % 10 + '0');
					count = count / 10;
				}
				reverse(chars, ptr, res - 1);

//					for(char c: Integer.toString(len).toCharArray()) {
//						chars[res++] = c;
//					}
			}
			i += len;
		}
		System.out.println("The result array is " + Arrays.toString(chars));
		return res;
	}

	// Iterate through the chars
	// check 1st seen
	public int compressSB(char[] chars) {
		int n = chars.length;
		if (n == 1) {
			return 1;
		}
		int count = 1, i = 1;
		StringBuilder sb = new StringBuilder();
		sb.append(chars[0]);
		while (i < n) {
			if (chars[i] == chars[i - 1]) {
				count++;
			} else {
				if (count > 1) {
					sb.append(count);
				}
				sb.append(chars[i]);
				count = 1;
			}
			i++;
		}
		if (count > 1) {
			sb.append(count);
		}
		for (int j = 0; j < sb.length(); j++) {
			chars[j] = sb.charAt(j);
		}

//		char[] compressedChars = sb.toString().toCharArray();
//		System.arraycopy(compressedChars, 0, chars, 0, compressedChars.length);

		System.out.println("The result string is " + sb);
		System.out.println("The result array is " + Arrays.toString(chars));
		return sb.length();

	}

	public int compressCharacterWise(char[] chars) {

		List<String> list = new ArrayList<>();
		for (char c : chars) {
			list.add(String.valueOf(c));
		}
		final List<String> list2 = new ArrayList<>();

		list.stream().forEach(s -> {
			if (Collections.frequency(list, s) > 1) {
				list2.add(s + Collections.frequency(list, s));
			} else {
				list2.add(s);
			}
		});

		List<String> list3 = new ArrayList<>();
		list3 = list2.stream().distinct().collect(Collectors.toList());

		System.out.println("list is " + list3.toString());
		String[] arr = list3.toArray(new String[list3.size()]);
		System.out.println("array is " + Arrays.toString(arr));

		String str = list3.stream().reduce((a, b) -> a + b).get();
		System.out.println("String is " + str);

		int i = 0;
		for (char c : str.toCharArray()) {
			chars[i++] = c;
		}
		chars = Arrays.copyOf(chars, str.length());
		return chars.length;
	}

}
