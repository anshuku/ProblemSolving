package LeetCode.BinarySearch;

/*
 * P275. H-Index II - Medium
 * 
 * Given an array of integers citations where citations[i] is the number 
 * of citations a researcher received for their ith paper and citations is 
 * sorted in non-descending order, return the researcher's h-index.
 * 
 * According to the definition of h-index on Wikipedia: The h-index is 
 * defined as the maximum value of h such that the given researcher has 
 * published at least h papers that have each been cited at least h times.
 * 
 * You must write an algorithm that runs in logarithmic time.
 * 
 * Approach - Binary Search
 */
public class P275HIndexII {

	public static void main(String[] args) {
//		int[] citations = { 0, 1, 3, 5, 6 };
//		int[] citations = { 1, 2, 100 };
		int[] citations = { 0, 1, 2, 2, 3, 3, 3, 4, 5, 6 };
//		int[] citations = { 0 };

		int hIndexBinary = hIndexBinarySearch(citations);
		System.out.println("Binary search: The h-index of researchers is: " + hIndexBinary);

		int hIndexLSFW = hIndexLSForward(citations);
		System.out.println("Linear search forward: The h-index of researchers is: " + hIndexLSFW);

		int hIndexLSBW = hIndexLSBackward(citations);
		System.out.println("Linear search backward: The h-index of researchers is: " + hIndexLSBW);
	}

	// Binary search - lower bound
	// This problem can be re-phrased as: Given a sorted citations array, we find
	// the first number citations[i], where citations[i] >= n-i. We can use binary
	// search as it's a sorted list. We want the lower bound here since we want the
	// 1st citations[mid] >= n - mid. We compare n-mid to citations[mid](pivot),
	// 1. citations[mid] == n - mid, there is (n-mid) papers with an equal or higher
	// citation count than citations[mid]. It's optimal, if we move to the right,
	// the next paper is going to have max(0, n - mid - 1) papers with >= c. Also,
	// citations[mid + 1] > (n-mid-1), and this isn't h-index. If we move right, we
	// can have smaller or equal h-index(sub-optimal). So, return citations[mid].
	// 2. if citations[mid] < n - mid, the target needs to be >= n - mid, we need to
	// look at the sublist on right, start = mid + 1.
	// 3. if citations[mid] > n - mid, the target needs to be >= n - mid, we need to
	// look at the sublist on left, end = mid - 1.
	// We return n - mid(count of indices beginning at mid throguh end of array),
	// instead of a value at some postion in array.
	// Time complexity - O(logN), for binary search.
	// Space complexity - O(1)
	public static int hIndexBinarySearch(int[] citations) {
		int n = citations.length;
		int start = 0;
		int end = n - 1;

		// We need to find the leftmost index such that: citations[index] >= n - mid
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
		// There is no exact match, so there's exactly (n-left) papers that have
		// citations <= citiations[left]
		return n - start;
	}

	// Linear search
	// For a paper with citation number c at index i, c = citations[i]. The number
	// of papers with a citation number > c = n - i - 1 and including the current
	// paper n-i papers are cited >= c times. As per H-Index, we find the 1st paper
	// at index i where citation number c >= n-i or H-Index.
	// Time complexity - O(n), in worst case iterate the entire list.
	// Space complexity - O(1)
	private static int hIndexLSForward(int[] citations) {
		int n = citations.length;
		for (int i = 0; i < n; i++) {
			if (citations[i] >= n - i) {
				return n - i;
			}
		}
		return 0;
	}

	// Linear search
	private static int hIndexLSBackward(int[] citations) {
		int n = citations.length;
		int maxH = n;
		for (int i = n - 1; i >= 0; i--) {
			int papers = n - i;
			if (citations[i] >= papers) {
				maxH = i;
			}
		}
		return n - maxH;
	}

}
