package LeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class P56MergeIntervals {

	public static void main(String[] args) {
		
		int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
		
		int [][] mergedArr = mergeOptmizedComparatorNew2(intervals);
		
		for (int i = 0; i < mergedArr.length; i++) {
			System.out.print(Arrays.toString(mergedArr[i]) + " ");
		}

	}
	
	public static int[][] mergeOptmizedComparatorNew2(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);
        List<int []> list = new ArrayList<>();
        for(int [] interval: intervals){
            if(list.isEmpty() || list.get(list.size() - 1)[1] < interval[0]){
                list.add(interval);
            } else {
                list.get(list.size() - 1)[1] = Math.max(list.get(list.size() - 1)[1], interval[1]);
            }
        }
        return list.toArray(new int[list.size()][2]);
    }
	
	public static int[][] mergeOptmizedComparatorNew(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int []> list = new ArrayList<>();
        for(int [] interval: intervals){
            if(list.isEmpty() || list.get(list.size() - 1)[1] < interval[0]){
                list.add(interval);
            } else {
                list.get(list.size() - 1)[1] = Math.max(list.get(list.size() - 1)[1], interval[1]);
            }
        }
        return list.toArray(new int[list.size()][2]);
    }
	
	public static int[][] mergeOptmizedComparator(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int []>(){
            @Override
            public int compare(int[] a, int[] b){
                return Integer.compare(a[0], b[0]);
            }
        });
        List<int []> list = new ArrayList<>();
        for(int [] interval: intervals){
            if(list.isEmpty() || list.get(list.size() - 1)[1] < interval[0]){
                list.add(interval);
            } else {
                list.get(list.size() - 1)[1] = Math.max(list.get(list.size() - 1)[1], interval[1]);
            }
        }
        return list.toArray(new int[list.size()][2]);
    }
	
	public static int[][] mergeUnoptmizedComparator(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int []>(){
            @Override
            public int compare(int[] a, int[] b){
                if(a[0] > b[0]){
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        List<int []> list = new ArrayList<>();
        for(int [] interval: intervals){
            if(list.isEmpty() || list.get(list.size() - 1)[1] < interval[0]){
                list.add(interval);
            } else {
                list.get(list.size() - 1)[1] = Math.max(list.get(list.size() - 1)[1], interval[1]);
            }
        }
        return list.toArray(new int[list.size()][2]);
    }

}
