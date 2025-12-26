package LeetCode.BinarySearch;

import java.util.Arrays;

/*
 * P274. H-Index - Medium
 * 
 * Given an array of integers citations where citations[i] is the number of citations 
 * a researcher received for their ith paper, return the researcher's h-index.
 * 
 * According to the definition of h-index on Wikipedia: The h-index is 
 * defined as the maximum value of h such that the given researcher has 
 * published at least h papers that have each been cited at least h times.
 * 
 * Approach - Sorting: comparision and counting, Binary search
 */
public class P274HIndex {

	public static void main(String[] args) {
		int[] citations = { 3, 0, 6, 1, 5 };
//		int[] citations = { 1, 3, 1 };
//		int[] citations = { 0 };

		int hIndexCount = hIndexCount(citations);
		System.out.println("Count: The h-index for the researcher is: " + hIndexCount);

		int hIndexBinarySearch = hIndexBinarySearch(citations);
		System.out.println("Binary Search: The h-index for the researcher is: " + hIndexBinarySearch);

		int hIndexLinearSearch = hIndexLinearSearch(citations);
		System.out.println("Linear Search: The h-index for the researcher is: " + hIndexLinearSearch);

		int hIndexLinearSearchRev = hIndexLinearSearchRev(citations);
		System.out.println("Linear Search Reverse: The h-index for the researcher is: " + hIndexLinearSearchRev);
	}

	// Counting sort
	// Since comparision sort takes O(nlogn) time, we need a non-comparision based
	// sorting method which takes lower time - counting sort.
	// Counting sort involves counting the number of objects that've each distinct
	// key value, and using arithemetic on those tallies(count) to determine the
	// positions of each key value in the output sequence. The running time is
	// linear in the number of items and the difference between the max and min
	// keys, so it's only suitable for direct use in situations where the variation
	// in keys is not significantly greater than number of items.
	// In this problem, keys = citations of each paper which can be >> number of
	// papers n. To use counting sort, any citation > n can be replaced by n and the
	// h-index will not change post replacement as h-index is upper bouned by total
	// number of papers n i.e. h <= n. This replacement is equivalent to cutting off
	// area where y > n. This area cut off will not change the largest squar/hIndex.
	// Once we get counts array for keys, we get a sorted citations by traversing
	// the counts array. Now the process is simple as earlier. One can do better, as
	// we don't even need to get sorted citations. We can find h-index by using
	// paper counts. For citations=[1,3,2,3,100],k=[0,1,2,3,4,5],count=[0,1,1,2,0,1]
	// sk=[5,5,4,3,1,1], sk is defined as sum of all counts with citation >= k or
	// number of papers having >= k citations. We replace 100 with n=5, citations=
	// [1,3,2,3,5]. The first k from right to left(5->0) that have k <= s is hIndex.
	// We can calculate sk when we traverse the count array(values), we only need
	// one pass and it costs O(n) time.
	// Time complexity - O(n), as we find count array in O(n) time and we also find
	// h-index while traversing count array only once.
	// Space complexity - O(n), for count array.
	// There is no possibility of multiple h-index as the as the dashed line y=x
	// crosses the histogram only once as the sorted bars ar monotonic. Also by
	// definition of h-index there is only 1 k.
	private static int hIndexCount(int[] citations) {
		int n = citations.length;
		int[] count = new int[n + 1];
		// counting papers for each(with a particular) citation number
		for (int c : citations) {
			count[Math.min(c, n)]++;
		}
		int k = n;
		for (int i = count[n]; i < k; i += count[k]) {
			k--;
		}
		return k;
	}

	// Binary Search
	public static int hIndexBinarySearch(int[] citations) {
		Arrays.sort(citations);
		int n = citations.length;
		int start = 0;
		int end = n - 1;

		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (citations[mid] == n - mid) {
				return citations[mid];
			}
			if (citations[mid] < n - mid) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return n - start;
	}

	// Linear Search
	private static int hIndexLinearSearch(int[] citations) {
		int n = citations.length;
		Arrays.sort(citations);
		for (int i = 0; i < n; i++) {
			if (citations[i] >= n - i) {
				return n - i;
			}
		}
		return 0;
	}

	// Linear search
	// Plotting a histogram where the y-axis represents the number of citations for
	// each paper and x-axis is number of papers. Sorting in descending order,
	// h-index is the length of largest square in the histogram.
	// Algo: For the largest square length, we first sort the citations array in
	// descending order. After sorting, if citations[i] > i, then papers 0 to i all
	// have at least i + 1 citations(> comparision +1 may be equal). To find h-index
	// we search largest i such that citations[i] > i, so h-index is i+1(i start=0).
	// For ciations[i] > i, i + 1 papers(from paper 0 ot i) have citations at least
	// i+1 and n-i-1 papers(from paper i+1 to paper n-1) have citations no more than
	// i+1. By definitions of h-index, h = i+1.
	// We can find the h-index via binary search as well, but as sorting has nlogn
	// time complexity so it dominates it. Even, linear sorting can be used.
	// Time complexity - O(nlogn), due to comparision sorting domination
	// Space complexity - O(1), due to heap sort for sorting.
	private static int hIndexLinearSearchRev(int[] citations) {
		int n = citations.length;
		Arrays.sort(citations);
		int i = 0;
		while (i < n && citations[n - i - 1] > i) {
			i++;
		}
		return i; // After while loop, i = i + 1.
	}

}
