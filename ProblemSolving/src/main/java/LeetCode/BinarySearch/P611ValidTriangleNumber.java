package LeetCode.BinarySearch;

import java.util.Arrays;

/*
 * P611. Valid Triangle Number - Medium
 * 
 * Given an integer array nums, return the number of triplets chosen from the array 
 * that can make triangles if we take them as side lengths of a triangle.
 * 
 * Approach - Binary Search, Linear Scan
 * 
 * The binary search looks like it should be O(n^2) but it still behaves much slower
 * than linear scan. It's due to the fact that Big-O ignores constant costs, branch behavior,
 * and cache locality. 2-pointer liner scan is cache-perfect, binary search is cache-hostile.
 * 
 * The binary search is not O(n^2) in CPU reality, even though start is not reset. This is due to:
 * Random jumps in memory, Multiple unpredictable branches, Integer divisions, No cache prefetching.
 * 
 * The linear scan is not expenisve as there is: Sequential memory access, No division, Very predictable 
 * branches, Perfect CPU cache line prefetch, Zero pointer bouncing, No pipeline stalls
 * 
 * CPU usage is perfect in linear scan but poor in binary search. Also each random mid jump in binary search 
 * leads to CPU cache line waste. But for linear scan cache hit every time due to coniguous memory.
 * 
 * 2 pointer linear scan is hardware optimal while binary search is algorithmically elegant but CPU-inefficient.
 * 
 * So, knowing computer architecture matters more than Big-O.
 */
public class P611ValidTriangleNumber {

	public static void main(String[] args) {
		int[] nums = { 2, 2, 3, 4 };
//		int[] nums = { 4, 2, 3, 4 };
//		int[] nums = { 7, 0, 0, 0 };
//		int[] nums = { 0, 1, 1, 1 };

		int trianglesLinear = triangleNumberLinearScan(nums);
		System.out.println("Linear Scan: The number of triangles formed are: " + trianglesLinear);

		int trianglesBinary = triangleNumberBinarySearch(nums);
		System.out.println("Binary Search: The number of triangles formed are: " + trianglesBinary);

		int trianglesBF = triangleNumberBruteForce(nums);
		System.out.println("Brute Force: The number of triangles formed are: " + trianglesBF);

	}

	// Linear Scan
	// Once we sort the nums array, we need to find the right limit of index k for a
	// pair of indices(i, j) chosen to find the count the elements satisfying
	// nums[i] + nums[j] > nums[k] for the triplet to form a triangle. We can find
	// this right limit by simply traversing the index k's values starting from k =
	// j + 1 for a pair(i,j) and stopping at the 1st value of k not satisfying the
	// inequality. Again the count of elements nums[k] statisfying inequality for
	// (i,j) = k - j - 1. Further, when we choose a higher value of index j for
	// particular i chosen, we need not start from index j+1 instead we can start
	// from the value of k where we left for the last index j which helps saving
	// redundant computations.
	// Time complexity - O(n^2), loop of k and j will be executed O(n^2) times, as
	// we do not reinitialize the value of k for a new value of j chosen for same i.
	// Space complexity - O(logn), due to sorting.
	private static int triangleNumberLinearScan(int[] nums) {
		int n = nums.length;
		int triangles = 0;
		Arrays.sort(nums);
		for (int i = 0; i < n - 2; i++) {
			int k = i + 2;
			for (int j = i + 1; j < n - 1 && nums[i] != 0; j++) {
				while (k < n && nums[i] + nums[j] > nums[k]) {
					k++;
				}
				triangles += k - j - 1;
			}
		}
		return triangles;
	}

	// Binary Search
	// Once we sort the array, if we consider a triplet (a,b,c) such that a<=b<=c,
	// we need not check all the 3 inequalities for triangle condition. Only 1
	// condition a+b > c is sufficient as c >= b and c >= a. Thus, adding any number
	// to c > a or b considered alone. Thus the conditions c + a > b and c + b > a,
	// need not be checked. Thus, for every pair (nums[i], nums[j]) from the start
	// of the array, such that j > i which leads nums[j] >= nums[i], we can find out
	// the count of the elements nums[k] (k > j), such that nums[i] + nums[j] >
	// nums[k]. While finding the last index k(for nums[k]), the value of nums[k]
	// could increase or remain same but it won't decrease. There will be a right
	// limit on the value of index k, such that nums[i] + nums[j] > nums[k]. If we
	// get this right limit of k(nums[k] > nums[i] + nums[j]), all the elements in
	// nums array in the range(j+1, k-1)(both included) satisfies the inequality.
	// We find count of elements = (k-1) - (j+1) + 1 - k - j - 1. For sorted nums,
	// we can use binary search to find the right limit of k.
	// Also once the right k for particular (i,j) is chosen, when we choose a higher
	// j for same i, we need not start searching for the right limit k(i,j+1) from
	// the index j + 2. Instead, we can start off from index k directly as the
	// previous k < k(i,j) will also satisfy nums[i] + nums[j] > nums[k] for new j.
	// This helps to limit range of binary search for k to shorter values.
	// Time complexity - O(n^2*logn), in worst case inner loop executes nlogn time
	// as binary search is applied n times.
	// Space complexity - O(logn) for sorting.
	private static int triangleNumberBinarySearch(int[] nums) {
		int n = nums.length;
		int triangles = 0;
		Arrays.sort(nums);
		for (int i = 0; i < n - 2; i++) {
			int start = i + 2;
			for (int j = i + 1; j < n - 1 && nums[i] != 0; j++) {
//				int start = j + 1;
				int end = n - 1;
				int a = nums[i];
				int b = nums[j];
				// find the last side which satisfied a + b > c
				while (start <= end) {
					int mid = start + (end - start) / 2;
					if (nums[mid] < a + b) {
						start = mid + 1;
					} else {
						end = mid - 1;
					}
				}
				// end or start-1 is the last side < a+b
				triangles += start - (j + 1);
			}
		}
		return triangles;
	}

	// Brute force
	// The condition for the triplets(a,b,c) representing the side lengths of
	// triangle, is that sum of any 2 sides > 3rd side alone or a+b > c.
	// We consider every possible triplet in the given nums array and check if the
	// triplet satisfies the 3 equalities. At end count gives the result.
	// Time complexity - O(n^3)
	// Space complexity - O(1)
	public static int triangleNumberBruteForce(int[] nums) {
		int n = nums.length;
		int triangles = 0;
		for (int i = 0; i < n - 2; i++) {
			for (int j = i + 1; j < n - 1; j++) {
				for (int k = j + 1; k < n; k++) {
					if (nums[i] + nums[j] > nums[k] && nums[i] + nums[k] > nums[j] && nums[j] + nums[k] > nums[i]) {
						triangles++;
					}
				}
			}
		}
		return triangles;
	}
}
