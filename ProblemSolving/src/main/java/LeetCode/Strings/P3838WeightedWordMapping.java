package LeetCode.Strings;

/*
 * P3838. Weighted Word Mapping - Easy
 * 
 * You are given an array of strings words, where each string 
 * represents a word containing lowercase English letters.
 * 
 * You are also given an integer array weights of length 26, where 
 * weights[i] represents the weight of the ith lowercase English letter.
 * 
 * The weight of a word is defined as the sum of the weights of its characters.
 * 
 * For each word, take its weight modulo 26 and map the result to a lowercase English 
 * letter using reverse alphabetical order (0 -> 'z', 1 -> 'y', ..., 25 -> 'a').
 * 
 * Return a string formed by concatenating the mapped characters for all words in order.
 * 
 * Approach - Simulation
 */
public class P3838WeightedWordMapping {

	public static void main(String[] args) {
		String[] words = { "abcd", "def", "xyz" };
		int[] weights = { 5, 3, 12, 14, 1, 2, 3, 2, 10, 6, 6, 9, 7, 8, 7, 10, 8, 9, 6, 9, 9, 8, 3, 7, 7, 2 };

//		String[] words = { "a", "b", "c" };
//		int[] weights = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

//		String[] words = { "abcd" };
//		int[] weights = { 7, 5, 3, 4, 3, 5, 4, 9, 4, 2, 2, 7, 10, 2, 5, 10, 6, 1, 2, 2, 4, 1, 3, 4, 4, 5 };

		String mapped = mapWordWeights(words, weights);
		System.out.println("The string formed from the mapped characters are: " + mapped);
	}

	// Simulation
	// Time complexity - O(n).
	// Space complexity - O(1).
	public static String mapWordWeights(String[] words, int[] weights) {
		int n = words.length;
		char[] mapped = new char[n];

		for (int i = 0; i < n; i++) {
			int weight = 0;

			for (char c : words[i].toCharArray()) {
				weight += weights[c - 'a'];
			}

			weight %= 26;
//			mapped[i] = (char) ('z' - weight);

			// 0 -> 25
			// 1 -> 24
			// 24 -> 1
			// 25 -> 0
			weight = 25 - weight;
			mapped[i] = (char) ('a' + weight);
		}

		return new String(mapped);
	}
}
