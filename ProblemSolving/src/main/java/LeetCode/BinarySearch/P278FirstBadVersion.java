package LeetCode.BinarySearch;

/*
 * P278. First Bad Version - Easy
 * 
 * You are a product manager and currently leading a team to develop 
 * a new product. Unfortunately, the latest version of your product 
 * fails the quality check. Since each version is developed based on 
 * the previous version, all the versions after a bad version are also bad.
 * 
 * Suppose you have n versions [1, 2, ..., n] and you want to find out 
 * the first bad one, which causes all the following ones to be bad.
 * 
 * You are given an API bool isBadVersion(version) which returns 
 * whether version is bad. Implement a function to find the first 
 * bad version. You should minimize the number of calls to the API.
 * 
 * Approach - Binary Search
 */
public class P278FirstBadVersion extends VersionControl {

	public static void main(String[] args) {
		int n = 6;

		int firstBad = firstBadVersion(n);
		System.out.println("The first bad version is: " + firstBad);
	}

	// Binary search - lower bound
	public static int firstBadVersion(int n) {
		int start = 1;
		int end = n;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			// When the version is bad we check the previous version, if it's bad.
			if (isBadVersion(mid)) {
				end = mid - 1;
			} else {
				// When the versions both including and before mid are good.
				start = mid + 1;
			}
		}
		return start;
	}

}

class VersionControl {

	static int bad = 3;

	static boolean isBadVersion(int version) {

		return version >= bad;
	}

}
