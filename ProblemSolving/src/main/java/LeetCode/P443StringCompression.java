package LeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class P443StringCompression {

	public static void main(String[] args) {
		char[] chars = { 'a', 'a', 'b', 'b', 'c', 'c', 'c' };
		
		int len = compress(chars);
		
		//int len = compressCharacterWise(chars);

		System.out.println("Length is " + len);
	}
	
	public static int compress(char[] chars) {

		int [] countArr = new int[chars.length];
		for(int i = 0; i<chars.length; i++) {
			//countArr[i]
		}
		
		return countArr.length;

	}

	public static int compressCharacterWise(char[] chars) {

		List<String> list = new ArrayList<>();
		for (char c : chars) {
			list.add(String.valueOf(c));
		}
		final List<String> list2 = new ArrayList<>();

		list.stream().forEach( s -> {
			if(Collections.frequency(list, s) > 1) {
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
		
		String str =  list3.stream().reduce((a, b) -> a+b).get();
		System.out.println("String is " + str);

        int i = 0;
        for(char c: str.toCharArray()){
            chars[i++] = c;
        }
		chars = Arrays.copyOf(chars, str.length());
		return chars.length;

	}

}
