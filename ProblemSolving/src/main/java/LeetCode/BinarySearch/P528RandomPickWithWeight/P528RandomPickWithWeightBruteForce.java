package LeetCode.BinarySearch.P528RandomPickWithWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * P528. Random Pick with Weight - Medium
 * 
 * You are given a 0-indexed array of positive integers w where w[i] describes the weight of the ith index.
 * 
 * You need to implement the function pickIndex(), which randomly picks an index in the range 
 * [0, w.length - 1] (inclusive) and returns it. The probability of picking an index i is w[i] / sum(w).
 * 
 * For example, if w = [1, 3], the probability of picking index 0 is 1 / (1 + 3) = 0.25 
 * (i.e., 25%), and the probability of picking index 1 is 3 / (1 + 3) = 0.75 (i.e., 75%).
 * 
 * Approach - Brute Force
 * 
 * We use a technique where we do sampling over a set of data. In Machine Learning like in Decision Tree, 
 * we sample a batch of data and feed them into the model, rather than taking entire data set.
 */
public class P528RandomPickWithWeightBruteForce {

	List<Integer> list;
	Random random;

	// Brute Force
	// We generate a list containing an index i, w[i] times
	// Total list size = cumulative sum of w array.
	public P528RandomPickWithWeightBruteForce(int[] w) {
		list = new ArrayList<>();
		random = new Random();
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i]; j++) {
				list.add(i);
			}
		}
	}

	// We randomly generate an index between [0, cumulative weight - 1]
	// We then find the value at this index which gives the original index in w.
	// Higher the weight value, the higher is the chance to get it.
	public int pickIndex() {
		// Here, we get valid indices form 0 to list.size() - 1
		int index = random.nextInt(list.size());
		return list.get(index);
	}

	public static void main(String[] args) {
		int[] w = { 1, 3 };
		P528RandomPickWithWeightBruteForce solution = new P528RandomPickWithWeightBruteForce(w);

		int weightIndex = solution.pickIndex();
		System.out.println("The index picked randomly is: " + weightIndex);
	}

}
