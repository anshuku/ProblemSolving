package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.List;

public class P1431KidsWithGreatestCandies {

	public static void main(String[] args) {
		
		int[] candies = {2,3,5,1,3};
		int extraCandies = 3;
		List<Boolean> list = kidsWithCandies(candies, extraCandies);
		
		for(Boolean val : list) {
			System.out.print(val + " ");
		}

	}
	
	public static List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> list = new ArrayList<Boolean>(candies.length);
        //int max = Arrays.stream(candies).max().getAsInt();
        int max = Integer.MIN_VALUE;
        for(int i: candies){
            if(i >= max){
                max = i;
            }
        }
        for(int i : candies){
            if((i+extraCandies) >= max){
                list.add(true);
            } else {
                list.add(false);
            }
        }
        return list;
    }

}
