package LeetCode.Backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* 
 * P47. Permutations II - Medium
 * 
 * Given a collection of numbers, nums, that might contain duplicates, 
 * return all possible unique permutations in any order.
 * 
 * Approach - Backtracking: Map, Set
 */
public class P47PermutationsII {

	public static void main(String[] args) {
//		int[] nums = { 1, 1, 2 };
//		int[] nums = { 1, 2, 3 };
		int[] nums = { 2, 1, 1, 2, 1 };
//		int[] nums = { 2, 1, 3, 3, 1, 2, 1 };

		List<List<Integer>> listMap = permuteUniqueMap(nums);
		System.out.println("Permute Map: Permutations are " + listMap);

		List<List<Integer>> listArray = permuteUniqueArray(nums);
		System.out.println("Permute Array: Permutations are " + listArray);

	}

	// Backtracking with Group of Numbers
	// Here the input array can contain duplicates. We can solve this problem with
	// backtracking algorithm. However, we need some adaptation to ensure that the
	// enumerated solutions do not have any duplicates.
	// Backtracking is a general algorithm for finding all (or some) solutions to
	// some problems with constraints. It incrementally builds candidates to the
	// solutions, and abandons a candidate as soon as it determines that the
	// candidate cannot possibly lead to a solution.
	// To generate a permutation of the array, we could follow the Depth-First
	// Search (DFS) approach, or more precisely the backtracking technique.
	// The idea is that we pick the numbers one by one. For a permutation of length
	// N, we would then need N stages to generate a valid permutation. At each
	// stage, we need to pick one number into the permutation, out of the remaining
	// available numbers. Later at the same stage, we will try out all available
	// choices. By trying out, we progressively build up candidates to the solution,
	// and revert each choice with another alternative until there is no more
	// choice.
	// Example: Given an input of [1,1,2], at the 1st stage, we've 2 choices to pick
	// a number as the 1st number in the final permutation, i.e 1 and 2.
	// Note: The reason that we've only 2 choices instead of 3, is that there is a
	// duplicate in the given input. Picking any of the duplicate numbers as the 1st
	// number of the permutation would lead us to the same permutation at the end.
	// Should the numbers in the array be all unique, we would then have the same
	// number of choices as the length of the array.
	// Here, we need to revisit each of the stages, and make a different choice in
	// order to try out all possibilities. The reversion of the choices is called
	// backtracking.
	// A key insight to avoid generating any redundant permutation is that at each
	// step rather than viewing each number as a candidate, we consider each unique
	// number as the true candidate. For instance, at the beginning of input
	// [1,1,2], we've only 2 candidates instead of 3.
	// Algorithm:
	// In order to find out all the unique numbers at each stage, we can build a
	// hash table (counter), with each unique number as the key and occurrence as
	// the corresponding value.
	// To implement the algorithm, first we define a function called backtrack(comb,
	// counter) which generates all permutations, starting from the current
	// combination (comb) and the remaining numbers (counter).
	// Note: In backtracking algo, usually some explorations lead to dead end, and
	// we have to abandon those explorations in the middle.
	// However, due to specificity of this problem and our exploration strategy,
	// each exploration will result in a valid permitation (no vain).
	// Claude:
	// The key insight: the map approach doesn't pick "a duplicate at index i" - it
	// picks "the value 3" (say) once, as a single decision, regardless of how many
	// 3's exist in the array. Here's why that avoid duplicate permutations:
	// The core idea:
	// At each recursion level, one chooses "what's the next number in the
	// permutation?" with a HashMap<Integer, Integer>, the keys are distinct values.
	// So if nums = [1,1,2], the counter is {1:2, 2:1} - only 2 keys, not 3.
	// When one iterates counter.entrySet(), one loop over {1,2} - one never get a
	// chance to "choose 1" twice at same recursion level. One choose the value 1
	// one time, decrement its count, and recurse. Then we move on to value 2.
	// Compare this to the swap-based approach: it treats the two 1's at positions 0
	// and 1 as distinct, swappable entities. So at index 0, swapping in nums[0] (a
	// 1) and swapping in nums[1] (also a 1, but a "different" 1 by position) are
	// treated as 2 separate branches - even though they produce identical
	// permutations. That's why one generate duplicates and need the Set to filter
	// them out afterward (expensive: hashing full lists, discarding work).
	// Why the map naturally prevents duplicates
	// Since duplicate values collapse into a single key with a count, the algorithm
	// effectively asks "how many distinct values could go next?" instead of "how
	// many array slots could go next?" That distinction is the deduplication - no
	// post-processing needed, no wasted branches explored.
	// Analogy:
	// Think of it like picking marbles from a bag: if you have 2 identical red
	// marbles and 1 blue marble, the map approach asks "red or blue?" (2 choices).
	// The swap approach asks "marble at position 0,1,or 2?" (3 choices) - but 2 of
	// those choices produce the same physical marble, red, so you've wasted an
	// exploration path and now must deduplicate after the fact.
	// The whole trick is - dedup by grouping by value, not on position.
	// Time complexity - O(∑ k = 1 -> N P(N,k)) where P(N,k) = N!/(N-k)! =
	// N*(N-1)*...*(N-k+1) is so-called k-permutations of N or partial permutation.
	// * The execution of the backtracking algorithm will unfold itself as a tree,
	// where each node is an invocation of the recursive function backtrack(comb,
	// counter). The total number of steps to complete the exploration is exactly
	// the number of nodes in the tree. Therefore, the time complexity of the
	// algorithm is linked directly with the size of the tree.
	// * We estimate the number of nodes in the tree. Each level of the tree
	// corresponds to a specific stage of the exploration. At each stage, the number
	// of candidates to explore is bounded. For instance, at the 1st stage, at most
	// we would have N candidates to explore, i.e. the number of nodes at this level
	// would be N. Moving on to the next stage, for each of the nodes in the 1st
	// stage, we would have N-1 child nodes. Therefore, the number of nodes at this
	// stage would be N*(N-1). So on and so forwards.
	// * By summing up all the nodes across the stages, we would then obtain the
	// total number of nodes as ∑ k = 1 -> N P(N, k) where P(N, k) = N!/(N-k)! =
	// N*(N-1)*...(N-k+1). As a result the time complexity is O(∑ k = 1->N P(N,k)).
	// * Loose upper bound of the time complexity: It takes N steps to generate a
	// single permutation. Since there are in total N! possible permutations, at
	// most it would take us N*N! steps to generate all permutations, assuming there
	// is no overlap (which is not true).
	// Space complexity - O(n), We build hash table from input numbers in O(n) time
	// in worst case where every number is unique. The recursion used in algorithm
	// consumes extra space due to function call stack. The depth of recursion can
	// go on till the length of 1 permutation which is O(n). During the exploration,
	// we keep a candidate of permutation along the way which takes O(n) space.
	// Note: We didn't take into account the space needed to hold the result.
	// Otherwise, it would be O(N*N!).
	private static List<List<Integer>> permuteUniqueMap(int[] nums) {
		List<Integer> list = new ArrayList<>();
		List<List<Integer>> result = new ArrayList<>();

		Map<Integer, Integer> map = new HashMap<>();

		// Count the occurrence of each number
		for (int num : nums) {
			map.put(num, map.getOrDefault(num, 0) + 1);
		}

		permutations(list, nums.length, map, result);
		return result;
	}

	private static void permutations(List<Integer> list, int n, Map<Integer, Integer> map, List<List<Integer>> result) {
		if (list.size() == n) {
			// Make a deep copy of the resulting permutation, since the permutation would be
			// backtracked later.
			result.add(new ArrayList<>(list));
			return;
		}

		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int num = entry.getKey();
			int count = entry.getValue();

			if (count == 0) {
				continue;
			}

			// Add this number into the current combination
			list.add(num);
			map.put(num, count - 1);

			// Continue the exploration
			permutations(list, n, map, result);

			// Revert the choice for the next exploration
			list.removeLast();
			map.put(num, count);
		}

	}

	// Backtracking
	// Backtracking is an algorithm for finding all solutions by exploring all
	// potential candidates. If the solution candidate turns to be not a solution
	// (or at least not the last one), backtracking algorithm discards it by making
	// some changes on the previous step, i.e. backtracks and then try again.
	// Time complexity - O(∑ k = 1 -> N P(N, k)) where P(N, k) = N!/(N-k)! =
	// N*(N-1)*...(N-k+1) is so-called k permutations of n or partial permutation.
	// Here, first+1 = k for the expression simplicity. The formula is: for each k
	// (each first) one performs N*(N-1)*..(N-k+1) operations, and k is going
	// through the range of values from 1 to N (and first from 0 to N-1).
	// Let's do a rough estimation of the result:
	// N! <= ∑ k = 1 -> N N!/(N-k)! = ∑ k = 1 -> N P(N, k) <= N*N!, i.e. the
	// algorithm performs better than O(N*N!) and a bit slower than O(N!).
	// Space complexity - O(N!) since one has to keep N! solutions
	public static List<List<Integer>> permuteUniqueArray(int[] nums) {
		Set<List<Integer>> ans = new HashSet<>();
		permute(nums, ans, 0);
		return new ArrayList<>(ans);
	}

	private static void permute(int[] nums, Set<List<Integer>> result, int index) {
		if (nums.length == index) {
			List<Integer> list = new ArrayList<>();
			for (int num : nums) {
				list.add(num);
			}
			result.add(list);
			return;
		} else {
			for (int i = index; i < nums.length; i++) {
				// Place i-th integer first in the current permutation.
				swap(nums, index, i);
				// Use next integers to complete the permutations
				permute(nums, result, index + 1);
				// Backtrack
				swap(nums, index, i);
			}
		}
	}

	private static void swap(int[] nums, int s, int e) {
		if (s == e) {
			return;
		}
		int temp = nums[s];
		nums[s] = nums[e];
		nums[e] = temp;
	}

}
