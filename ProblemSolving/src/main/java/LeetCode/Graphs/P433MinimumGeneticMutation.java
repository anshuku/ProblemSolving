package LeetCode.Graphs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

/*
 * P433. Minimum Genetic Mutation - Medium
 * 
 * A gene string can be represented by an 8-character long string, with choices from 'A', 'C', 'G', and 'T'.
 * 
 * Suppose we need to investigate a mutation from a gene string startGene to a gene string 
 * endGene where one mutation is defined as one single character changed in the gene string.
 * 
 * For example, "AACCGGTT" --> "AACCGGTA" is one mutation.
 * 
 * There is also a gene bank bank that records all the valid gene 
 * mutations. A gene must be in bank to make it a valid gene string.
 * 
 * Given the two gene strings startGene and endGene and the gene 
 * bank bank, return the minimum number of mutations needed to mutate 
 * from startGene to endGene. If there is no such a mutation, return -1.
 * 
 * Note that the starting point is assumed to be valid, so it might not be included in the bank.
 * 
 * Approach - BFS
 */
public class P433MinimumGeneticMutation {

	public static void main(String[] args) {
//		String startGene = "AACCGGTT";
//		String endGene = "AACCGGTA";
//		String[] bank = { "AACCGGTA" };

//		String startGene = "AACCGGTT";
//		String endGene = "AAACGGTA";
//		String[] bank = { "AACCGGTA", "AACCGCTA", "AAACGGTA" };

		String startGene = "AAAAAAAA";
		String endGene = "ACAAAAAA";
		String[] bank = { "CAAAAAAA", "CCAAAAAA", "ACAAAAAA" };

		int minMutation = minMutation(startGene, endGene, bank);
		System.out.println("BFS: The minimum number of mutations to mutate from start to end gene: " + minMutation);

		int minMutationPair = minMutationPair(startGene, endGene, bank);
		System.out.println(
				"BFS Pair: The minimum number of mutations to mutate from start to end gene: " + minMutationPair);

	}

	// BFS
	// This problem is of Graph. Each gene string is a node, and mutations are the
	// edges. 2 nodes will have an edge(are neighbors) if they differ by 1
	// character. The added constratints are that the characters must be one of
	// "ACGT", and each node must be in bank. Here BFS will be used over DFS as we
	// find the shortest path. In BFS all nodes at a distance x from start will be
	// visited before any node at distance x + 1 will be visited. Once we find the
	// target(end), we know that we found it in shortest number of steps.
	// Algo:
	// We perform BFS starting from node startGene. Keep track of number of steps or
	// mutations taken so far and return it once we find endGene. Only traverse to
	// nodes that are in bank. Neighbors can be found by iterating over each node
	// and replacing one of the characters with a character from "ACGT". To check if
	// node is in bank, we use a set from bank for O(1) checks. Here, since 0 <=
	// bank.length <= 10. With such a small constraint, it may be slower to use set
	// due to hashing. We can keep bank as an array. Initialize a queue for BFS and
	// a set seen to prevent visiting a node more than once. Initially, the queue
	// and set should hold start. We perform BFS, at each node, if node == endGene,
	// return mutations. Else, iterate over all neighbors. For each neighbor, if
	// it's not in seen and neighbor is in bank, add it to queue and seen. If we
	// finish BFS and couldn't find endGene, then the task is impossible, return -1.
	// Time Complexity - Where B = bank.length. We may not convert bank to set due
	// to the low size. Even if we did convert, it'll still cost O(B). Checking if a
	// neighbor is in bank costs O(B) with an array. Here, BFS runs in constant time
	// due to problem limits of gene lenght = 8, and the strings can only have 4
	// characters or O(4^8). If the gene string could've length n and could've m
	// kind of characters. The number of possible nodes = m^n, as for each of the n
	// characters, there are m options. If we've to convert bank to a set before
	// BFS. In that case, the time complexity would be O(nB + m^n*m^2*m). Converting
	// bank costs O(n*B), then there are m^n states that we could visit. At each
	// state we perform nested loops which iterates n*m times, and we also perform
	// string operations which costs O(n).
	// Space complexity - O(1), as the problem limits the input, so we use constant
	// space. If the gene string could've length n and could've m kind of
	// characters, the space complexity is O(n*B + m^n). Converting bank to set
	// would take O(n*B) space. The seen set could grow m^n size if all states are
	// visited.
	private static int minMutation(String startGene, String endGene, String[] bank) {
		Set<String> geneBank = new HashSet<>(Arrays.asList(bank));

		final char[] bases = { 'A', 'C', 'G', 'T' };

		Queue<String> queue = new LinkedList<>();
		queue.offer(startGene);

		Set<String> visited = new HashSet<>();
		visited.add(startGene);

		int mutations = 0;

		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				String gene = queue.poll();
				if (endGene.equals(gene)) {
					return mutations;
				}
				char[] geneArr = gene.toCharArray();
				for (int i = 0; i < 8; i++) {
					char old = geneArr[i];
					for (char base : bases) {
						geneArr[i] = base;
						String newGene = new String(geneArr);
						if (!visited.contains(newGene) && geneBank.contains(newGene)) {
							queue.add(newGene);
							visited.add(newGene);
						}
					}
					geneArr[i] = old;
				}
			}
			mutations++;
		}
		return -1;
	}

	public static int minMutationPair(String startGene, String endGene, String[] bank) {
		Set<String> geneBank = new HashSet<>();
		for (String gene : bank) {
			geneBank.add(gene);
		}
		if (!geneBank.contains(endGene)) {
			return -1;
		}

		final String[] bases = { "A", "C", "G", "T" };

		Queue<Pair<String, Integer>> queue = new LinkedList<>();
		queue.offer(Pair.of(startGene, 0));

		Set<String> visited = new HashSet<>();
		visited.add(startGene);

		while (!queue.isEmpty()) {
			Pair<String, Integer> pair = queue.poll();
			String gene = pair.getKey();
			int mutation = pair.getValue();
			if (endGene.equals(gene)) {
				return mutation;
			}

			for (String base : bases) { // Better to iterate the bases 1st
				for (int i = 0; i < 8; i++) {
					String newGene = gene.substring(0, i) + base + gene.substring(i + 1);
					if (!visited.contains(newGene) && geneBank.contains(newGene)) {
						queue.offer(Pair.of(newGene, mutation + 1));
						visited.add(newGene);
					}
				}
			}
		}
		return -1;
	}

}
