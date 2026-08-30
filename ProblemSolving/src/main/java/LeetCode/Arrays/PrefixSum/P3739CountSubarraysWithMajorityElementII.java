package LeetCode.Arrays.PrefixSum;

/*
 * P3739. Count Subarrays With Majority Element II - Hard
 * 
 * You are given an integer array nums and an integer target.
 * 
 * Return the number of subarrays of nums in which target is the majority element.
 * 
 * The majority element of a subarray is the element that appears 
 * strictly more than half of the times in that subarray.
 * 
 * Constraints:
 * > 1 <= nums.length <= 10​​​​​​​^5
 * > 1 <= nums[i] <= 10​​​​​​​^9
 * > 1 <= target <= 10^9
 * 
 * Approach - Prefix Sum
 */
public class P3739CountSubarraysWithMajorityElementII {

	public static void main(String[] args) {
		int[] nums = { 1, 2, 2, 3 };
		int target = 2;

		long count = countMajoritySubarrays(nums, target);
		System.out.println("The count of majority subarrays is: " + count);
	}

	// Prefix Sum
	// We transform the array by treating target as +1 and other elements as -1.
	// After the transformation, target is the majority element of a subarray
	// nums[l...r] if and only if the sum of the transformed subarray > 0. We take s
	// as prefix sum of the transformed array, where s has length n + 1. The
	// transformed sum of subarray nums[l..r] is s[r+1] - s[r]. The condition that
	// this sum > 0 is s[r+1] > s[l]. For each r, we count the number of indices l
	// satisfying 0 <= l <= r and s[l] < s[r+1]. The naive approach is to check all
	// l for each r but it'd lead to time of O(n^2).
	// One can see that every prefix sum lies in the range [-n,n]. We use a counting
	// array pre, where pre[v] records how many times the prefix sum value v has
	// appeared so far. For the current prefix sum s[r+1], the number of valid
	// indices l (count) is exactly the sum of all entries in pre corresponding to
	// values strictly smaller than s[r+1], which is a prefix sum of pre at s[r].
	// Computing this prefix sum from scratch for every r would still be too
	// expensive. One can observe that between consecutive positions, the prefix sum
	// only changes by +1 or -1. Therefore, the upper bound of the prefix-sum query
	// changes by only 1 position (for r). This allows to maintain the result
	// incrementally using a variable presum, updating it in O(1) time per step:
	// * When the current transformed value is +1, we've s[r+1] = s[r] + 1. The
	// query expands by 1 value, so we add pre[s[r]] to presum.
	// * When the current transformed value is -1, we've s[r+1] = s[r] - 1. The
	// query shrinks by 1 value, so we subtract pre[s[r+1]] from presum.
	// After updating presum, we record the current prefix sum s[r+1] in pre and add
	// presum to the answer.
	// In the below implementation we use count to represent the current prefix sum.
	// Since prefix sums may be -ve and arrays do not support negative indices, all
	// prefix-sum values are shifted by n when stored in pre.
	// 1. What is presum?
	// Suppose we're processing index r. From the prefix sum observation, we need to
	// count the number of indices l such that 0 <= l <= r and s[l] < s[r+1], where
	// s is the prefix sum array of the transformed array. Here, we don't store the
	// entire prefix sum array. Instead, it keeps the current prefix sum value in a
	// variable count. We also maintain an array pre, where
	// pre[x] = number of times prefix sum = x has appeared so far.
	// Therefore, for the current prefix sum count, we need to count how many
	// previous prefix sums are < count:
	// count of s[-n],..., s[0], s[1], ..., s[r] such that s[i] < count
	// This quantity is exactly: presum = Σ pre[x] for all x < count.
	// presum represents the number of valid starting positions l for the current r.
	// Why is presum initially 0?
	// Before processing any element: count = 0 and pre[0] = 1 as the empty prefix
	// sum s[0] is 0 or count = 0, the number of times prefix sum 0 has appeared so
	// far = 1 or pre[0] = 1. By definition presum = Σ pre[x] for all x < 0.
	// At this point, the only nonzero frequency is pre[0] = 1, so there are no
	// prefix sums smaller than 0. Therefore, presum = 0.
	// Why does the code use count = n?
	// The actual prefix sum can range from [-n,n]. Since array indices cannot be
	// negative, the implementation shifts every prefix sum by n:
	// shifted sum = original sum + n. Now the range becomes [0, 2n] which can be
	// stored directly in an array. So in the code count = n and pre[n] = 1
	// corresponds to the original prefix sum value 0.
	// 2. Why does presum change by only pre[count]?
	// The key observation is that every transformed value is either +1 or -1.
	// Therefore, the prefix sum can change by 1 at each step.
	// Case 1: new_count = prev_count + 1
	// new presum = Σ pre[x] (x < new_count) = Σ pre[x] (x < prev count + 1)
	// The new range contains everything from the old range plus the value
	// prev_count:
	// new presum = Σ pre[x] (x < prev_count) + pre[prev_count]
	// = prev_presum + pre[prev_count]
	// Hence, presum += pre[prev_count]
	// Case 2: new_count = prev_count - 1
	// new presum = Σ pre[x] (x < new_count) = Σ pre[x] (x < prev_count - 1)
	// Compared to old range, we must remove all occurrences of prev count - 1:
	// new presum = Σ pre[x] (x < prev count) - pre[prev_count - 1]
	// = prev_presum - pre[prev_count - 1]
	// Hence, presum -= pre[prev_count - 1]
	// The whole trick works because the prefix sum changes only by +-1, so the
	// boundary of the query: Σ pre[x] (x < count) moves only by 1 position at each
	// step. This allows presum to be updated in O(1) time instead of recomputing
	// the entire sum or using a Fenwick Tree.
	// Time complexity - O(n)
	// Space complexity - O(n) for pre array.
	public static long countMajoritySubarrays(int[] nums, int target) {
		int n = nums.length;

		// Represents the occurrence count of prefix sums -n, -(n-1), ... -1, 0, 1,... n
		// with index offset by n.
		int[] pre = new int[2 * n + 1];
		pre[n] = 1;

		int count = n;

		long preSum = 0;

		long answer = 0;

		for (int i = 0; i < n; i++) {
			if (nums[i] == target) {
				preSum += pre[count];
				count++;
				pre[count]++;
			} else {
				count--;
				preSum -= pre[count];
				pre[count]++;
			}
			answer += preSum;
		}

		return answer;
	}

//  0 1 1 -1 -1 1
//count 0 1 2  1  0 1 
//presu 0 1 2  1  0 2
//sum of count of prefix sum till count=1 now remove count but count reached 2 but now it's 1 so 
//presum need to be found for count = 1> count = 0 so we need to remove count of prefix sum 1 from presum to get presum till count 0
//pre[c 2 2 1

//  0 -1 -1  1 1 -1
//count 0 -1 -2 -1 0 -1 
//presu 0  0  0  1 3  1 
//pre[c 2  3  1    

//2 2 4

//4 4 2 2
}
